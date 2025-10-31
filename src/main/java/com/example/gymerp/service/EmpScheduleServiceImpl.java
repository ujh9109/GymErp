package com.example.gymerp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EmpScheduleDto;
import com.example.gymerp.repository.EmpScheduleDao;
import com.example.gymerp.repository.EmpScheduleDaoImpl;
import com.example.gymerp.repository.EmpVacationDao;
import com.example.gymerp.repository.EtcDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpScheduleServiceImpl implements EmpScheduleService {

    private final EmpScheduleDaoImpl empScheduleDao;
    private final EtcDao etcDao;
    private final EmpVacationDao empVacationDao;

    /** ============================= 일정 조회 ============================= */

    @Override
    public List<EmpScheduleDto> getAllSchedules() {
        return empScheduleDao.selectAll();
    }

    @Override
    public EmpScheduleDto getScheduleByCalNum(int calNum) {
        return empScheduleDao.selectByCalNum(calNum);
    }

    @Override
    public List<EmpScheduleDto> getSchedulesByEmpAndDate(int empNum, LocalDateTime startDate, LocalDateTime endDate) {
        return empScheduleDao.selectByEmpAndDate(empNum, startDate, endDate);
    }


    /** ============================= 일정 등록 ============================= */

    /** ETC 일정 등록 전용 */
    @Override
    @Transactional
    public int createEtcSchedule(EmpScheduleDto dto) {
        if (dto.getEmpNum() <= 0) {
            throw new IllegalArgumentException("유효하지 않은 직원번호(empNum=" + dto.getEmpNum() + ")");
        }
        if (dto.getEtc() == null) {
            throw new IllegalArgumentException("기타 일정 정보가 없습니다.");
        }

        // ETC 테이블 등록
        dto.getEtc().setEmpNum(dto.getEmpNum());
        etcDao.insertEtc(dto.getEtc());

        // EmpSchedule 설정
        dto.setRefType("ETC");
        dto.setRefId(dto.getEtc().getEtcNum());
        dto.setStartTime(dto.getEtc().getStartTime());
        dto.setEndTime(dto.getEtc().getEndTime());
        dto.setMemo(dto.getEtc().getEtcMemo());
        dto.setColor("#FFCC00");

        return empScheduleDao.insertEmpEtc(dto);
    }

    /** VACATION 일정 등록 전용 */
    @Override
    @Transactional
    public int createEmpVacationSchedule(EmpScheduleDto dto) {
        if (dto.getEmpNum() <= 0) {
            throw new IllegalArgumentException("유효하지 않은 직원번호(empNum=" + dto.getEmpNum() + ")");
        }
        if (dto.getVacation() == null) {
            throw new IllegalArgumentException("휴가 정보가 없습니다.");
        }

        dto.getVacation().setEmpNum(dto.getEmpNum());
        empVacationDao.insertEmpVacation(dto.getVacation());

        // EmpSchedule 설정
        dto.setRefType("VACATION");
        dto.setRefId(dto.getVacation().getVacNum());
        dto.setStartTime(dto.getVacation().getVacStartedAt().toLocalDate().atStartOfDay());
        dto.setEndTime(dto.getVacation().getVacEndedAt().toLocalDate().atStartOfDay().plusDays(1));
        dto.setMemo(dto.getVacation().getVacContent());
        dto.setColor("#FFA500");

        return empScheduleDao.insertEmpVacation(dto);
    }

    /** REGISTRATION 일정 등록 전용 (PT 예약용) */
    @Override
    @Transactional
    public int createEmpRegistrationSchedule(EmpScheduleDto dto) {
        if (dto.getEmpNum() <= 0) {
            throw new IllegalArgumentException("유효하지 않은 트레이너 번호(empNum=" + dto.getEmpNum() + ")");
        }
        if (dto.getRegistration() == null) {
            throw new IllegalArgumentException("PT 등록 정보가 없습니다.");
        }

        // 🔸 회원 정보 유효성 체크
        if (dto.getRegistration().getMemNum() <= 0) {
            throw new IllegalArgumentException("유효하지 않은 회원번호(memNum=" + dto.getRegistration().getMemNum() + ")");
        }

        // 🔸 Registration 테이블 먼저 insert (트레이너 + 회원)
        dto.getRegistration().setEmpNum(dto.getEmpNum());
        empScheduleDao.insertRegistration(dto.getRegistration());

        // 🔸 EmpSchedule 설정
        dto.setRefType("REGISTRATION");
        dto.setRefId(dto.getRegistration().getRegNum());
        dto.setStartTime(dto.getRegistration().getRegTime());
        dto.setEndTime(dto.getRegistration().getLastTime());
        dto.setMemo(dto.getRegistration().getRegNote());
        dto.setColor("#007BFF");

        return empScheduleDao.insertEmpRegistration(dto);
    }


    /** ============================= 일정 수정 및 삭제 ============================= */

    @Override
    @Transactional
    public int updateSchedule(EmpScheduleDto dto) {
        if ("ETC".equalsIgnoreCase(dto.getRefType()) && dto.getEtc() != null) {
            etcDao.updateEtc(dto.getEtc());
        }
        return empScheduleDao.update(dto);
    }

    @Override
    @Transactional
    public int deleteSchedule(int calNum) {
        EmpScheduleDto schedule = empScheduleDao.selectByCalNum(calNum);

        if (schedule != null) {
            switch (schedule.getRefType().toUpperCase()) {
                case "ETC":
                    etcDao.deleteEtc(schedule.getRefId());
                    break;
                case "VACATION":
                    empVacationDao.deleteEmpVacation(schedule.getRefId());
                    break;
                case "REGISTRATION":
                    // PT 예약 삭제 시 Registration 테이블에서도 삭제 필요하다면
                    // empScheduleDao.deleteRegistration(schedule.getRefId());
                    break;
                default:
                    break;
            }
        }
        return empScheduleDao.delete(calNum);
    }
}
