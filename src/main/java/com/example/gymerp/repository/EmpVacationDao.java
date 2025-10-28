package com.example.gymerp.repository;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.example.gymerp.dto.EmpVacationDto;

public interface EmpVacationDao {

    // 전체 휴가 목록
    List<EmpVacationDto> selectAllEmpVacations();

    // 휴가 단건 조회
    EmpVacationDto selectEmpVacationById(@Param("vacNum") Long vacNum);

    // 직원별 휴가 목록
    List<EmpVacationDto> selectEmpVacationsByEmpNum(@Param("empNum") Long empNum);

    // 휴가 등록
    int insertEmpVacation(EmpVacationDto dto);

    // 휴가 수정 (전체 필드 변경)
    int updateEmpVacation(EmpVacationDto dto);

    // 휴가 상태만 변경
    int updateEmpVacationState(@Param("vacNum") Long vacNum, @Param("vacState") String vacState);

    // 휴가 삭제
    int deleteEmpVacation(@Param("vacNum") Long vacNum);
}
