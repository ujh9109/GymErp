package com.example.gymerp.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.EmpScheduleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpScheduleDaoImpl implements EmpScheduleDao{
	
	private final SqlSession sqlSession;

	//전체 일정 조회 (PT / Vacation / Etc 상세 포함)
	@Override
	public List<EmpScheduleDto> selectAll() {
		
		return sqlSession.selectList("EmpScheduleMapper.selectAll");
	}

	//단건 조회 (PK 기준)
	@Override
	public EmpScheduleDto selectByCalNum(int calNum) {
		
		return sqlSession.selectOne("EmpScheduleMapper.selectByCalNum", calNum);
	}

	//직원 + 날짜 범위로 일정 조회
	@Override
	public List<EmpScheduleDto> selectByEmpAndDate(int empNum, LocalDate startDate, LocalDate endDate) {
		        HashMap<String, Object> params = new HashMap<>();
		        params.put("empNum", empNum);
		        params.put("startDate", startDate);
		        params.put("endDate", endDate);
		        return sqlSession.selectList("EmpScheduleMapper.selectByEmpAndDate", params);
	}

	//일정 수정 
	@Override
	public int update(EmpScheduleDto empSchedule) {
		 return sqlSession.update("EmpScheduleMapper.update", empSchedule);
	}

	//일정 삭제
	@Override
	public int delete(int calNum) {
		return sqlSession.delete("EmpScheduleMapper.delete", calNum);
	}
	
	 @Override
	    public int insert(EmpScheduleDto dto) {
	        return sqlSession.insert("EmpScheduleMapper.insert", dto);
	    }


}
