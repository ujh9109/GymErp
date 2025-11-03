// src/main/java/com/example/gymerp/repository/EmpVacationDaoImpl.java
package com.example.gymerp.repository;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.EmpVacationDto;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class EmpVacationDaoImpl implements EmpVacationDao {

    private final SqlSession session;

    // 전체 휴가 목록
    @Override
    public List<EmpVacationDto> selectAllEmpVacations() {
        return session.selectList("EmpVacationMapper.selectAllEmpVacations");
    }

    // 휴가 단건 조회
    @Override
    public EmpVacationDto selectEmpVacationById(int vacNum) {
        return session.selectOne("EmpVacationMapper.selectEmpVacationById", vacNum);
    }

    // 직원별 휴가 목록
    @Override
    public List<EmpVacationDto> selectEmpVacationsByEmpNum(int empNum) {
        return session.selectList("EmpVacationMapper.selectEmpVacationsByEmpNum", empNum);
    }

    //기간(달력 범위) 조회: [from, to]와 겹치는 휴가
    @Override
    public List<EmpVacationDto> selectEmpVacationsByRange(int empNum, Date from, Date to) {
        Map<String, Object> p = new HashMap<>();
        p.put("empNum", empNum);
        p.put("from", from);
        p.put("to", to);
        return session.selectList("EmpVacationMapper.selectEmpVacationsByRange", p);
    }

    // 휴가 등록
    @Override
    public int insertEmpVacation(EmpVacationDto dto) {
        return session.insert("EmpVacationMapper.insertEmpVacation", dto);
    }

    // 휴가 수정(전체 필드)
    @Override
    public int updateEmpVacation(EmpVacationDto dto) {
        return session.update("EmpVacationMapper.updateEmpVacation", dto);
    }

    // 휴가 상태만 변경
    @Override
    public int updateEmpVacationState(int vacNum, String vacState) {
        Map<String, Object> p = new HashMap<>();
        p.put("vacNum", vacNum);
        p.put("vacState", vacState);
        return session.update("EmpVacationMapper.updateEmpVacationState", p);
    }

    // 휴가 삭제
    @Override
    public int deleteEmpVacation(int vacNum) {
        return session.delete("EmpVacationMapper.deleteEmpVacation", vacNum);
    }

	
    
    
}
