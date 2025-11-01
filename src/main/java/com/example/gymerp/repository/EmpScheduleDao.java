package com.example.gymerp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.gymerp.dto.EmpScheduleDto;
import com.example.gymerp.dto.EmpVacationDto;
import com.example.gymerp.dto.EtcDto;
import com.example.gymerp.dto.PtRegistrationDto;

public interface EmpScheduleDao {
    
    /** ============================= 일정 조회 ============================= */

    // 1. 전체 일정 조회 (PT / VACATION / ETC 상세 포함)
    List<EmpScheduleDto> scheduleSelectAll();

    // 2. PK 단건 조회
    EmpScheduleDto selectByCalNum(@Param("calNum") int calNum);

    // 3. 직원 + 날짜 범위 일정 조회
    List<EmpScheduleDto> selectByEmpAndDate(
        @Param("empNum") int empNum,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );


    /** ============================= 일정 등록 ============================= */

    // ETC 일정 등록
    int insertEtc(EtcDto dto); 
    
    int createEmpEtc(EmpScheduleDto dto);

    // Vacation 일정 등록
    int insertEmpVacation(EmpVacationDto dto);
    
    int createEmpVacation(EmpScheduleDto dto);

    // Registration 테이블 등록 (회원 정보 포함)
    int insertPtRegistration(PtRegistrationDto dto);
    
    int createEmpRegistration(EmpScheduleDto dto);


    /** ============================= 일정 수정 및 삭제 ============================= */

    // 일정 수정
    int update(EmpScheduleDto empSchedule);

    // 일정 삭제
    int delete(@Param("calNum") int calNum);
}
