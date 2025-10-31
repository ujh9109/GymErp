package com.example.gymerp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EmpScheduleDto;
import com.example.gymerp.repository.EmpScheduleDao;
import com.example.gymerp.repository.EmpVacationDao;
import com.example.gymerp.repository.EtcDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpScheduleServiceImpl implements EmpScheduleService {

	private final EmpScheduleDao empScheduleDao;
	private final EtcDao etcDao; // Etc 일정 관련 DAO 필요시 사용
	private final EmpVacationDao empvacationDao;

	/** 전체 일정 조회 */
	@Override
	public List<EmpScheduleDto> getAllSchedules() {
		return empScheduleDao.selectAll();
	}

	/** PK 기준 단건 조회 */
	@Override
	public EmpScheduleDto getScheduleByCalNum(int calNum) {
		return empScheduleDao.selectByCalNum(calNum);
	}

	/** 직원 + 날짜 범위로 일정 조회 */
	@Override
	public List<EmpScheduleDto> getSchedulesByEmpAndDate(int empNum, LocalDateTime startDate, LocalDateTime endDate) {
		// 날짜 범위를 LocalDateTime으로 변환 후 DAO 호출
		return empScheduleDao.selectByEmpAndDate(empNum, startDate, endDate);
	}

	/** 일정 등록 (PT / VACATION / ETC 구분) */
	@Override
	@Transactional
	public int addSchedule(EmpScheduleDto dto) {
		// ETC 일정이면 Etc 테이블에도 저장
		if ("ETC".equalsIgnoreCase(dto.getRefType()) && dto.getEtc() != null) {
			etcDao.insertEtc(dto.getEtc());
			dto.setRefId(dto.getEtc().getEtcNum());
		}
		return empScheduleDao.insert(dto);
	}

	 /** ETC 일정 등록 전용 */
    @Override
    @Transactional
    public int createEtcSchedule(EmpScheduleDto dto) {

        // 1️ 직원번호 검증
        if (dto.getEmpNum() <= 0) {
            throw new IllegalArgumentException("유효하지 않은 직원번호(empNum=" + dto.getEmpNum() + ")");
        }

        // 2️ ETC 일정 존재 시 EmpNum 세팅
        if (dto.getEtc() != null) {
            dto.getEtc().setEmpNum(dto.getEmpNum());

            // 3️ Etc 테이블 먼저 insert
            etcDao.insertEtc(dto.getEtc());

            // 4️ EmpSchedule 참조 정보 설정
            dto.setRefType("ETC");
            dto.setRefId(dto.getEtc().getEtcNum());
            dto.setStartTime(dto.getEtc().getStartTime());
            dto.setEndTime(dto.getEtc().getEndTime());
            dto.setMemo(dto.getEtc().getEtcMemo());
            dto.setColor("#FFCC00");
        }else {
            throw new IllegalArgumentException("기타 일정 정보가 없습니다.");
        }

        // 5️ EmpSchedule insertEtc() 실행 (shNum 컬럼 제외)
        return empScheduleDao.insertEtc(dto);
    }
    
    
    /** VACATION 일정 등록 전용 */
    @Override
    @Transactional
	public int createEmpVacationSchedule(EmpScheduleDto dto) {
		
    	// 1️ 직원번호 검증
        if (dto.getEmpNum() <= 0) {
            throw new IllegalArgumentException("유효하지 않은 직원번호(empNum=" + dto.getEmpNum() + ")");
        }

        // 2️ ETC 일정 존재 시 EmpNum 세팅
        if (dto.getVacation() != null) {
            dto.getVacation().setEmpNum(dto.getEmpNum());

            // 3️ Etc 테이블 먼저 insert
            empvacationDao.insertEmpVacation(dto.getVacation());

            // 4️ EmpSchedule 참조 정보 설정
            dto.setRefType("VACATION");
            dto.setRefId(dto.getVacation().getVacNum());  // 생성된 휴가 PK
            dto.setStartTime(dto.getVacation().getVacStartedAt().toLocalDate().atStartOfDay());
            dto.setEndTime(dto.getVacation().getVacEndedAt().toLocalDate().atStartOfDay().plusDays(1));
            dto.setMemo(dto.getVacation().getVacContent());
            dto.setColor("#FFA500"); // 예: 주황색 (휴가 구분 색상)
        } else {
            throw new IllegalArgumentException("휴가 정보가 없습니다.");
        }

        // 5️ EmpSchedule insertEtc() 실행 (shNum 컬럼 제외)
        return empScheduleDao.insertEmpVacation(dto);
    }
	


	/** 일정 수정 */
	@Override
	@Transactional
	public int updateSchedule(EmpScheduleDto dto) {
		// ETC 일정 수정 시 Etc 테이블도 수정
		if ("ETC".equalsIgnoreCase(dto.getRefType()) && dto.getEtc() != null) {
			etcDao.updateEtc(dto.getEtc());
		}
		return empScheduleDao.update(dto);
	}

	/** 일정 삭제 */
	@Override
	@Transactional
	public int deleteSchedule(int calNum) {
		EmpScheduleDto schedule = empScheduleDao.selectByCalNum(calNum);
		if (schedule != null && "ETC".equalsIgnoreCase(schedule.getRefType())) {
			etcDao.deleteEtc(schedule.getRefId());
		}
		return empScheduleDao.delete(calNum);
	}

	
}
