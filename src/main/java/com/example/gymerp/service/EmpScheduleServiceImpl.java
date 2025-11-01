package com.example.gymerp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EmpScheduleDto;
import com.example.gymerp.dto.EmpVacationDto;
import com.example.gymerp.dto.EtcDto;
import com.example.gymerp.dto.PtRegistrationDto;
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
		return empScheduleDao.scheduleSelectAll();
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
		if (dto.getEmpNum() <= 0)
			throw new IllegalArgumentException("유효하지 않은 직원번호");

		 // ETC 테이블 등록 전 직원번호 세팅
	    EtcDto etc = dto.getEtc();
	    etc.setEmpNum(dto.getEmpNum()); // 🔥 핵심 포인트
	    etcDao.insertEtc(etc); // 여기서 etcNum 생성됨

	    // EmpSchedule 등록
	    EmpScheduleDto schedule = new EmpScheduleDto();
	    schedule.setEmpNum(dto.getEmpNum());
	    schedule.setRefType("ETC");
	    schedule.setRefId(dto.getEtc().getEtcNum());
	    schedule.setStartTime(dto.getEtc().getStartTime());
	    schedule.setEndTime(dto.getEtc().getEndTime());
	    schedule.setMemo(dto.getEtc().getEtcMemo());
	    schedule.setColor("#FFCC00");

	    return empScheduleDao.createEmpEtc(schedule);
	}

	/** VACATION 일정 등록 전용 */
	@Override
	@Transactional
	public int createEmpVacationSchedule(EmpScheduleDto dto) {
		 // 1️ 유효성 검사
	    if (dto.getEmpNum() <= 0) {
	        throw new IllegalArgumentException("유효하지 않은 직원번호(empNum=" + dto.getEmpNum() + ")");
	    }
	    if (dto.getVacation() == null) {
	        throw new IllegalArgumentException("휴가 정보가 없습니다.");
	    }

	    // 2️ EmpVacation 테이블 등록
	    dto.getVacation().setEmpNum(dto.getEmpNum());
	    empVacationDao.insertEmpVacation(dto.getVacation());

	    // 3️ EmpSchedule 등록 정보 세팅
	    EmpScheduleDto schedule = new EmpScheduleDto();
	    schedule.setEmpNum(dto.getEmpNum());
	    schedule.setRefType("VACATION");
	    schedule.setRefId(dto.getVacation().getVacNum()); 
	    schedule.setStartTime(dto.getVacation().getVacStartedAt().toLocalDate().atStartOfDay());
	    schedule.setEndTime(dto.getVacation().getVacEndedAt().toLocalDate().atStartOfDay().plusDays(1));
	    schedule.setMemo(dto.getVacation().getVacContent());
	    schedule.setColor("#FFA500");

	    // 4️ EmpSchedule 테이블 등록
	    empScheduleDao.createEmpVacation(schedule);

	    return schedule.getCalNum();
		
	}

	/** REGISTRATION 일정 등록 전용 (PT 예약용) */
	@Override
	@Transactional
	public int createEmpRegistrationSchedule(EmpScheduleDto dto) {
		// 1️ 유효성 검사
	    if (dto.getEmpNum() <= 0) {
	        throw new IllegalArgumentException("유효하지 않은 트레이너 번호(empNum=" + dto.getEmpNum() + ")");
	    }
	    if (dto.getRegistration() == null) {
	        throw new IllegalArgumentException("PT 등록 정보가 없습니다.");
	    }
	    if (dto.getRegistration().getMemNum() <= 0) {
	        throw new IllegalArgumentException("유효하지 않은 회원번호(memNum=" + dto.getRegistration().getMemNum() + ")");
	    }

	    // 2️ regTime, lastTime null 방어 처리
	    LocalDateTime regStart = dto.getRegistration().getRegTime();
	    LocalDateTime regEnd = dto.getRegistration().getLastTime();

	    if (regStart == null) regStart = LocalDateTime.now();
	    if (regEnd == null) regEnd = regStart.plusHours(1); // 기본 1시간 PT

	    // 3️ Registration 테이블 등록 
	    dto.getRegistration().setEmpNum(dto.getEmpNum());
	    empScheduleDao.insertPtRegistration(dto.getRegistration());

	    // 4️ EmpSchedule 등록 정보 세팅
	    EmpScheduleDto schedule = new EmpScheduleDto();
	    schedule.setEmpNum(dto.getEmpNum());
	    schedule.setRefType("REGISTRATION");
	    schedule.setRefId(dto.getRegistration().getRegNum()); // PT 예약 번호 참조
	    schedule.setStartTime(regStart);
	    schedule.setEndTime(regEnd);
	    schedule.setMemo(dto.getRegistration().getRegNote());
	    schedule.setColor("#007BFF");

	    // 5️ EmpSchedule 등록
	    empScheduleDao.createEmpRegistration(schedule);

	    return schedule.getCalNum();

	
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
