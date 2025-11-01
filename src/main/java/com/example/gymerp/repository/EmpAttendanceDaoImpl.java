// src/main/java/com/example/gymerp/repository/EmpAttendanceDaoImpl.java
package com.example.gymerp.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.EmpAttendanceDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpAttendanceDaoImpl implements EmpAttendanceDao {

    private final SqlSession session;

    @Qualifier("empAttendanceDaoImpl")   // ← 구현체 빈 이름 명시
    // 전체 근태 목록
    @Override
    public List<EmpAttendanceDto> selectAllEmpAttendances() {
        return session.selectList("EmpAttendanceMapper.selectAllEmpAttendances");
    }

    // 근태 단건 조회
    @Override
    public EmpAttendanceDto selectEmpAttendanceById(int attNum) {
        return session.selectOne("EmpAttendanceMapper.selectEmpAttendanceById", attNum);
    }

    // 직원별 근태 목록
    @Override
    public List<EmpAttendanceDto> selectEmpAttendancesByEmpNum(int empNum) {
        return session.selectList("EmpAttendanceMapper.selectEmpAttendancesByEmpNum", empNum);
    }

    // ✅ 기간(달력 범위) 조회: [from, to]
    @Override
    public List<EmpAttendanceDto> selectEmpAttendancesByRange(int empNum, Date from, Date to) {
        Map<String, Object> p = new HashMap<>();
        p.put("empNum", empNum);
        p.put("from", from);
        p.put("to", to);
        return session.selectList("EmpAttendanceMapper.selectEmpAttendancesByRange", p);
    }

    // 출근(등록)
    @Override
    public int insertEmpAttendance(EmpAttendanceDto dto) {
        return session.insert("EmpAttendanceMapper.insertEmpAttendance", dto);
    }

    // 퇴근시간만 업데이트
    @Override
    public int updateEmpAttendanceCheckOut(int attNum, Timestamp checkOut) {
        Map<String, Object> p = new HashMap<>();
        p.put("attNum", attNum);
        p.put("checkOut", checkOut);
        return session.update("EmpAttendanceMapper.updateEmpAttendanceCheckOut", p);
    }

    // 전체 수정
    @Override
    public int updateEmpAttendance(EmpAttendanceDto dto) {
        return session.update("EmpAttendanceMapper.updateEmpAttendance", dto);
    }

    // 삭제
    @Override
    public int deleteEmpAttendance(int attNum) {
        return session.delete("EmpAttendanceMapper.deleteEmpAttendance", attNum);
    }
}
