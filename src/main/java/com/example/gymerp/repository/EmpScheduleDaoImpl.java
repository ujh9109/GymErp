package com.example.gymerp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.EmpScheduleDto;
import com.example.gymerp.dto.EmpVacationDto;
import com.example.gymerp.dto.EtcDto;
import com.example.gymerp.dto.PtRegistrationDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpScheduleDaoImpl implements EmpScheduleDao {

    private final SqlSession sqlSession;

    /** ============================= 일정 조회 ============================= */

    @Override
    public List<EmpScheduleDto> scheduleSelectAll() {
    	return sqlSession.selectList("EmpScheduleDao.scheduleSelectAll");
        
    }

    @Override
    public EmpScheduleDto selectByCalNum(int calNum) {
        return sqlSession.selectOne("EmpScheduleDao.selectByCalNum", calNum);
    }

    @Override
    public List<EmpScheduleDto> selectByEmpAndDate(int empNum, LocalDateTime startDate, LocalDateTime endDate) {
        return sqlSession.selectList(
            "EmpScheduleDao.selectByEmpAndDate",
            Map.of("empNum", empNum, "startDate", startDate, "endDate", endDate)
        );
    }

    /** ============================= 일정 등록 ============================= */
    
    //기타 일정
    @Override
    public int insertEtc(EtcDto dto) {
        return sqlSession.insert("EtcMapper.insertEtc", dto);
    }
    
    @Override
    public int createEmpEtc(EmpScheduleDto dto) {
        return sqlSession.insert("EmpScheduleDao.createEmpEtc", dto);
    }
    
    // 휴가
    @Override
    public int insertEmpVacation(EmpVacationDto dto) {
        return sqlSession.insert("EmpVacationMapper.insertEmpVacation", dto);
    }
    
    @Override
	public int createEmpVacation(EmpScheduleDto dto) {
    	
    	return sqlSession.insert("EmpScheduleDao.createEmpVacation", dto);
	}

    //PT 일정
    @Override
    public int insertPtRegistration(PtRegistrationDto dto) {
        return sqlSession.insert("PtRegistrationMapper.insertPtRegistration", dto);
    }
    
    @Override
	public int createEmpRegistration(EmpScheduleDto dto) {
    	return sqlSession.insert("EmpScheduleDao.createEmpRegistration", dto);
	}

    /** ============================= 일정 수정 및 삭제 ============================= */

    @Override
    public int update(EmpScheduleDto empSchedule) {
        return sqlSession.update("EmpScheduleDao.update", empSchedule);
    }

    @Override
    public int delete(int calNum) {
        return sqlSession.delete("EmpScheduleDao.delete", calNum);
    }

	

	
}
