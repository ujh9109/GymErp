package com.example.gymerp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.PtRegistrationDto;
import com.example.gymerp.dto.ScheduleDto;
import com.example.gymerp.repository.ScheduleDao;
import com.example.gymerp.repository.LogDao;

import lombok.RequiredArgsConstructor;

/**
 * 📦 ScheduleServiceImpl
 * ------------------------------------------------------------
 * Gym ERP의 핵심 서비스 중 하나.
 * "일정(SCHEDULE)" 데이터를 관리하면서,
 * PT 일정 등록/삭제 시에는 자동으로 PT 등록(REGISTRATION)과
 * PT 로그(PT_LOG)도 함께 처리하는 통합 로직을 담당한다.
 */
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    /* ============================= 💾 의존성 주입 ============================= */
    private final ScheduleDao scheduleDao;                  // 일정 테이블 접근 (SCHEDULE)
    private final PtRegistrationService ptRegistrationService; // PT 등록 테이블 접근 (REGISTRATION)
    private final LogDao logDao;                            // PT 로그 테이블 접근 (PT_LOG)

    /* ============================= 📖 일정 조회 ============================= */

    /** 전체 일정 목록 조회 */
    @Override
    public List<ScheduleDto> getAllSchedules() {
        return scheduleDao.selectAll();
    }

    /** 단일 일정 조회 (shNum으로 조회) */
    @Override
    public ScheduleDto getScheduleById(int shNum) {
        return scheduleDao.selectByShNum(shNum);
    }

    /** 특정 직원(empNum)의 일정 조회 */
    @Override
    public List<ScheduleDto> getSchedulesByEmpNum(int empNum) {
        return scheduleDao.selectByEmpNum(empNum);
    }

    /** 기간별 일정 조회 */
    @Override
    public List<ScheduleDto> getSchedulesByDateRange(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate) {
        return scheduleDao.selectByDateRange(startDate, endDate);
    }

    /* ============================= 🟢 일정 등록 ============================= */

    /**
     * 일정 등록
     * ------------------------------------------------------------
     * 1️⃣ 일반 일정은 SCHEDULE 테이블에만 등록
     * 2️⃣ PT 일정(SCHEDULE-PT)은 REGISTRATION + PT_LOG까지 함께 등록
     */
    @Transactional
    @Override
    public int createSchedule(ScheduleDto schedule) {
        // 1️⃣ 일정 기본 등록
        int result = scheduleDao.insert(schedule);
        System.out.println("[일정 등록 완료] shNum=" + schedule.getShNum() + ", codeBid=" + schedule.getCodeBid());

        // 2️⃣ PT 일정인 경우만 추가 로직 수행
        if ("SCHEDULE-PT".equalsIgnoreCase(schedule.getCodeBid())) {
            
            // 회원이 선택되지 않은 경우 → PT 등록 생략
            if (schedule.getMemNum() == null) {
                System.out.println("[PT 등록 생략] memNum=null");
                return result;
            }

            // 3️⃣ REGISTRATION 테이블에 PT 예약 등록
            PtRegistrationDto reg = PtRegistrationDto.builder()
                    .empNum((long) schedule.getEmpNum())                                 // 직원 번호
                    .memNum(schedule.getMemNum() == null ? null : schedule.getMemNum().longValue()) // 회원 번호
                    .shNum((long) schedule.getShNum())                                   // 일정 번호
                    .regNote(schedule.getMemo())                                         // 메모
                    .build();

            ptRegistrationService.insertPtRegistration(reg);
            System.out.println("[PT 예약 등록 완료] regNum=" + reg.getRegNum());

            // 4️ PT_LOG 테이블에 "소비" 로그 등록 (-1)
            PtLogDto consumeLog = PtLogDto.builder()
                    .memNum(schedule.getMemNum() == null ? null : schedule.getMemNum().longValue())
                    .empNum((long) schedule.getEmpNum())
                    .regId(reg.getRegNum() == null ? null : reg.getRegNum().longValue())
                    .status("소비")   // 소비 로그
                    .countChange(-1L) // 회차 차감
                    .build();

            logDao.insertPtConsumeLog(consumeLog);
            System.out.println("[PT 소비 로그 등록 완료]");
        }

        return result;
    }

    /* ============================= 🔵 일정 수정 ============================= */

    /** 일정 수정 */
    @Transactional
    @Override
    public int updateSchedule(ScheduleDto schedule) {
        int updated = scheduleDao.update(schedule);
        System.out.println("[일정 수정 완료] shNum=" + schedule.getShNum());
        return updated;
    }

    /* ============================= 🔴 일정 삭제 ============================= */

    /**
     * 일정 삭제
     * ------------------------------------------------------------
     * 1️⃣ PT 일정(SCHEDULE-PT)일 경우:
     *     - 관련 PT_LOG에 "예약취소" 로그 등록 (+1)
     *     - REGISTRATION 테이블에서도 해당 PT 등록 삭제
     * 2️⃣ 그 외 일정은 단순히 SCHEDULE에서 삭제
     */
    @Transactional
    @Override
    public int deleteSchedule(int shNum) {
        // 1️⃣ 삭제 대상 조회
        ScheduleDto target = scheduleDao.selectByShNum(shNum);
        if (target == null) {
            throw new IllegalArgumentException("존재하지 않는 일정입니다.");
        }

        System.out.println("[일정 삭제 요청] shNum=" + shNum + ", type=" + target.getCodeBid());

        // 2️ PT 일정인 경우
        if ("SCHEDULE-PT".equalsIgnoreCase(target.getCodeBid())) {
            Integer regNum = ptRegistrationService.findRegNumByShNum(shNum);

            if (regNum != null) {
                // 2-1️ PT_LOG에 예약취소 로그 추가 (+1)
                PtLogDto cancelLog = PtLogDto.builder()
                        .memNum(target.getMemNum() == null ? null : target.getMemNum().longValue())
                        .empNum((long) target.getEmpNum())
                        .regId(regNum == null ? null : regNum.longValue())
                        .status("예약취소") // 예약취소 로그
                        .countChange(1L)    // 회차 복원 (+1)
                        .build();

                logDao.insertPtCancelLog(cancelLog);
                System.out.println("[PT 예약취소 로그 등록 완료] regNum=" + regNum);

                // 2-2️ REGISTRATION 테이블에서 PT 등록 삭제
                ptRegistrationService.deletePtRegistration(regNum);
                System.out.println("[PT 등록 데이터 삭제 완료]");
            } else {
                // REGISTRATION 테이블에 해당 PT 데이터가 없을 경우
                System.out.println("[PT 등록번호 없음 → 로그 등록 생략]");
            }
        }

        // 3️ SCHEDULE 테이블에서 일정 삭제
        int deleted = scheduleDao.delete(shNum);
        System.out.println("[일정 삭제 완료] deleted=" + deleted);

        return deleted;
    }
}