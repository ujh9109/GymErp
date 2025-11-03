package com.example.gymerp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.PtRegistrationDto;
import com.example.gymerp.dto.ScheduleDto;
import com.example.gymerp.repository.LogDao;
import com.example.gymerp.repository.PtRegistrationMapper;
import com.example.gymerp.repository.ScheduleDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {


    private final ScheduleDao scheduleDao;
    private final PtRegistrationMapper ptRegistrationMapper;
	private final LogDao logDao;

    // 전체 일정 조회 
    @Override
    public List<ScheduleDto> getAllSchedules() {
        return scheduleDao.selectAll();
    }


	// 단건 조회
	@Override
	public ScheduleDto getScheduleById(int shNum) {
		return scheduleDao.selectByShNum(shNum);
	}

	// 직원별 일정 조회
	@Override
	public List<ScheduleDto> getSchedulesByEmpNum(int empNum) {
		return scheduleDao.selectByEmpNum(empNum);
	}

	// 날짜 범위 조회
	@Override
	public List<ScheduleDto> getSchedulesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
		return scheduleDao.selectByDateRange(startDate, endDate);
	}


    //일정 등록
    @Override
    @Transactional
    public int createSchedule(ScheduleDto schedule) {
        if (schedule.getEmpNum() == null || schedule.getEmpNum() <= 0) {
            throw new IllegalArgumentException("직원번호가 유효하지 않음");
        }

        // SCHEDULE 테이블에 일정 등록
        int result = scheduleDao.insert(schedule); // shNum이 selectKey로 채워짐

        // PT 일정이면서 회원 정보도 있을 때만 REGISTRATION 생성
        if (("PT".equalsIgnoreCase(schedule.getRefType()) ||
             "SCHEDULE-PT".equalsIgnoreCase(schedule.getCodeBid()))
            && schedule.getMemNum() != null) {

            PtRegistrationDto regDto = new PtRegistrationDto();
            regDto.setEmpNum(schedule.getEmpNum());
            regDto.setShNum(schedule.getShNum());
            regDto.setRegNote(schedule.getMemo());
            regDto.setMemNum(schedule.getMemNum());

            ptRegistrationMapper.insertPtRegistration(regDto);
        }

        return result; // insert 한 번만 호출
    }


    //일정 수정 
    @Override
    public int updateSchedule(ScheduleDto schedule) {
        if (schedule.getShNum() <= 0) {
            throw new IllegalArgumentException("수정할 일정 번호가 유효하지 않음");
        }
        return scheduleDao.update(schedule);
    }

	// 일정 삭제 (PT 취소 로그 포함)
	@Override
	public int deleteSchedule(int shNum) {
		 if (shNum <= 0) {
		        throw new IllegalArgumentException("삭제할 일정 번호가 유효하지 않습니다.");
		    }

		    // 삭제 대상 일정 조회
		    ScheduleDto target = scheduleDao.selectByShNum(shNum);
		    if (target == null) {
		        throw new IllegalArgumentException("존재하지 않는 일정입니다.");
		    }

		    // 로그 대신 출력문 사용
		    System.out.println("삭제대상 codeBid = " + target.getCodeBid());
		    System.out.println("삭제대상 memNum = " + target.getMemNum());
		    System.out.println("삭제대상 empNum = " + target.getEmpNum());

		    // PT 일정이면 → 예약취소 로그 남기기
		    String code = target.getCodeBid();
		    if (code != null && (code.equalsIgnoreCase("PT") || code.equalsIgnoreCase("SCHEDULE-PT"))) {

		        Integer memNumI = target.getMemNum(); // Integer (nullable)
		        Integer empNumI = target.getEmpNum(); // Integer (nullable)

		        // 회원 연결이 안된 PT 일정이면 로그 생략
		        if (memNumI == null || memNumI == 0) {
		            System.out.println("⚠️ PT 일정(" + shNum + ")에 연결된 회원이 없습니다. 취소 로그를 남기지 않습니다.");
		        } else {
		            Long memNumL = memNumI.longValue();
		            Long empNumL = (empNumI != null) ? empNumI.longValue() : null;

		            PtLogDto cancelLog = PtLogDto.builder()
		                    .memNum(memNumL)
		                    .empNum(empNumL)
		                    .status("예약취소")
		                    .countChange(1L)
		                    .createdAt(LocalDateTime.now())
		                    .build();

		            System.out.println("🟢 PT 취소 로그 등록 시도: " + cancelLog);
		            logDao.insertPtCancelLog(cancelLog);
		        }
		    }

		    //  일정 삭제
		    return scheduleDao.delete(shNum);
	}
}
