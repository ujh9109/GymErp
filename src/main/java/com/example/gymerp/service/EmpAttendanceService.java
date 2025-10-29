package com.example.gymerp.service;

import java.sql.Timestamp;
import java.util.List;
import com.example.gymerp.dto.EmpAttendanceDto;

public interface EmpAttendanceService {

    // 전체 근태 목록 조회
    List<EmpAttendanceDto> getAllEmpAttendances();

    // 직원별 근태 목록 조회
    List<EmpAttendanceDto> getEmpAttendancesByEmpNum(int empNum);

    // 단일 근태 조회
    EmpAttendanceDto getEmpAttendanceById(int attNum);

    // 출근 등록
    int addEmpAttendance(EmpAttendanceDto dto);

    // 퇴근 시간 업데이트
    int updateEmpAttendanceCheckOut(int attNum, Timestamp checkOut);

    // 근태 수정
    int updateEmpAttendance(EmpAttendanceDto dto);

    // 근태 삭제
    int deleteEmpAttendance(int attNum);
}
