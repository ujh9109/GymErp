package com.example.gymerp.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.EmpAttendanceDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpAttendanceDaoImpl implements EmpAttendanceDao {
    private final SqlSessionTemplate session;

    @Override
    public List<EmpAttendanceDto> selectEmpAttendancesByEmpNum(int empNum) {
        return session.selectList("EmpAttendance.mapper.selectEmpAttendancesByEmpNum", empNum); // ✅
    }

    @Override
    public EmpAttendanceDto selectEmpAttendanceById(int attNum) {
        return session.selectOne("EmpAttendance.mapper.selectEmpAttendanceById", attNum); // ✅
    }

    @Override
    public List<EmpAttendanceDto> selectAllEmpAttendances() {
        return session.selectList("EmpAttendance.mapper.selectAllEmpAttendances"); // ✅
    }

    @Override
    public int insertEmpAttendance(EmpAttendanceDto dto) {
        return session.insert("EmpAttendance.mapper.insertEmpAttendance", dto); // ✅
    }

    @Override
    public int updateEmpAttendanceCheckOut(int attNum, Timestamp checkOut) {
        Map<String,Object> p = new HashMap<>();
        p.put("attNum", attNum);
        p.put("checkOut", checkOut);
        return session.update("EmpAttendance.mapper.updateEmpAttendanceCheckOut", p); // ✅
    }

    @Override
    public int updateEmpAttendance(EmpAttendanceDto dto) {
        return session.update("EmpAttendance.mapper.updateEmpAttendance", dto); // ✅
    }

    @Override
    public int deleteEmpAttendance(int attNum) {
        return session.delete("EmpAttendance.mapper.deleteEmpAttendance", attNum); // ✅
    }

    @Override
    public List<EmpAttendanceDto> selectEmpAttendancesByRange(int empNum, Date from, Date to) {
        Map<String,Object> p = Map.of("empNum", empNum, "from", from, "to", to);
        return session.selectList("EmpAttendance.mapper.selectEmpAttendancesByRange", p); // ✅
    }
}
