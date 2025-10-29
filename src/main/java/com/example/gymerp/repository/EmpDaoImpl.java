package com.example.gymerp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.EmpDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {
	
	private final SqlSession session;
	
	// 직원 전체 목록 조회
	@Override
	public List<EmpDto> getAllEmp() {
		
		return session.selectList("EmployeeMapper.getAllEmp");
	}

	// 직원 한명의 정보 조회
	@Override
	public EmpDto getEmpByNum(int empNum) {
		
		return session.selectOne("EmployeeMapper.getEmpByNum");
	}
	
	// 직원 등록
	@Override
	public int insertEmp(EmpDto dto) {
		
		return session.insert("EmployeeMapper.insertEmp");
	}

	// 직원 정보 수정
	@Override
	public int updateEmp(EmpDto dto) {
		
		return session.update("EmployeeMapper.updateEmp");
	}

	// 직원 삭제
	@Override
	public int deleteEmp(EmpDto dto) {
		
		return session.delete("EmployeeMapper.deleteEmp");
	}

	// 직원 검색
	@Override
	public List<EmpDto> searchEmp(String keyword, String filter) {
		
		// 여러 파라미터를 전달해야 하므로 Map 사용
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("keyword", keyword);
        paramMap.put("filter", filter);
        return session.selectList("EmployeeMapper.searchEmp", paramMap);
	}
	
}
