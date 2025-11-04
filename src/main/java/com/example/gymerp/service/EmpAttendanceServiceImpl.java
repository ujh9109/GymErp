// src/main/java/com/example/gymerp/service/EmpAttendanceServiceImpl.java
package com.example.gymerp.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EmpAttendanceDto;
import com.example.gymerp.repository.EmpAttendanceDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpAttendanceServiceImpl implements EmpAttendanceService {

    private final EmpAttendanceDao dao;

    
 // EmpAttendanceServiceImpl.java
    @Override
    public List<EmpAttendanceDto> getAllByDate(Date date) {
        return dao.selectAllByDate(date);
    }

    
    @Override
    public List<EmpAttendanceDto> getAllEmpAttendances() {
        return dao.selectAllEmpAttendances();
    }

    @Override
    public List<EmpAttendanceDto> getEmpAttendancesByEmpNum(int empNum) {
        return dao.selectEmpAttendancesByEmpNum(empNum);
    }

    @Override
    public EmpAttendanceDto getEmpAttendanceById(int attNum) {
        return dao.selectEmpAttendanceById(attNum);
    }

    @Override
    public int checkIn(int empNum) {
        EmpAttendanceDto dto = new EmpAttendanceDto();
        dto.setEmpNum(empNum);     // ✅ 반드시 여기서 확정
        dto.setAttState("WORKING");// (XML에서 날짜/시간 기본값 처리)
        return dao.insertEmpAttendance(dto);
    }

    @Override
    public int updateEmpAttendanceCheckOut(int attNum, Timestamp checkOut) {
        return dao.updateEmpAttendanceCheckOut(attNum, checkOut);
    }

    @Override
    public int updateEmpAttendance(EmpAttendanceDto dto) {
        return dao.updateEmpAttendance(dto);
    }

    @Override
    public int deleteEmpAttendance(int attNum) {
        return dao.deleteEmpAttendance(attNum);
    }

    @Override
    public List<EmpAttendanceDto> getEmpAttendancesByRange(int empNum, Date from, Date to) {
        if (empNum <= 0) {
            // 전직원: DAO에 전직원용 쿼리가 없다면 컨트롤러에서 empNum 반복 호출로 대체하거나,
            // 쿼리를 empNum 조건 없이도 동작하도록 별도 작성 필요.
            // 우선 간단히 "모든 직원"을 반환하도록 기존 DAO를 임시로 재사용하려면
            // DAO/Mapper에 전직원용 selectAllByRange 를 추가하는 게 베스트.
            // 임시: 전체에서 날짜 필터가 필요하면 새 쿼리를 추가하세요.
        }
        return dao.selectEmpAttendancesByRange(empNum, from, to);
    }
}
