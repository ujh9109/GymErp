package com.example.gymerp.repository;

import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.example.gymerp.dto.EmpAttendanceDto;

public interface EmpAttendanceDao {

    // 전체 근태 목록
    List<EmpAttendanceDto> selectAllEmpAttendances();

    // 근태 단건 조회
    EmpAttendanceDto selectEmpAttendanceById(@Param("attNum") Long attNum);

    // 직원별 근태 목록
    List<EmpAttendanceDto> selectEmpAttendancesByEmpNum(@Param("empNum") Long empNum);

    // 출근(등록)
    int insertEmpAttendance(EmpAttendanceDto dto);

    // 퇴근 시간 업데이트
    int updateEmpAttendanceCheckOut(@Param("attNum") Long attNum, @Param("checkOut") Timestamp checkOut);

    // 전체 수정
    int updateEmpAttendance(EmpAttendanceDto dto);

    // 삭제
    int deleteEmpAttendance(@Param("attNum") Long attNum);
}
