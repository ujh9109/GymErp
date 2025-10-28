// src/main/java/com/example/gymerp/repository/EmpAttendanceDaoImpl.java
package com.example.gymerp.repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.EmpAttendanceDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpAttendanceDaoImpl implements EmpAttendanceDao {

    private final SqlSession session;

    // 전체 근태 목록
    @Override
    public List<EmpAttendanceDto> selectAllEmpAttendances() {
        return session.selectList("EmpAttendanceMapper.selectAllEmpAttendances");
    }

    // 근태 단건 조회
    @Override
    public EmpAttendanceDto selectEmpAttendanceById(Long attNum) {
        return session.selectOne("EmpAttendanceMapper.selectEmpAttendanceById", attNum);
    }

    // 직원별 근태 목록
    @Override
    public List<EmpAttendanceDto> selectEmpAttendancesByEmpNum(Long empNum) {
        return session.selectList("EmpAttendanceMapper.selectEmpAttendancesByEmpNum", empNum);
    }

    // 출근(등록)
    @Override
    public int insertEmpAttendance(EmpAttendanceDto dto) {
        return session.insert("EmpAttendanceMapper.insertEmpAttendance", dto);
    }

    // 퇴근시간만 업데이트
    @Override
    public int updateEmpAttendanceCheckOut(Long attNum, Timestamp checkOut) {
        Map<String, Object> p = new HashMap<>();
        p.put("attNum", attNum);
        p.put("checkOut", checkOut);
        return session.update("EmpAttendanceMapper.updateEmpAttendanceCheckOut", p);
    }

    // 전체 수정이 필요할 때
    @Override
    public int updateEmpAttendance(EmpAttendanceDto dto) {
        return session.update("EmpAttendanceMapper.updateEmpAttendance", dto);
    }

    // 삭제
    @Override
    public int deleteEmpAttendance(Long attNum) {
        return session.delete("EmpAttendanceMapper.deleteEmpAttendance", attNum);
    }
}
