package com.example.gymerp.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.gymerp.dto.EmpScheduleDto;

public interface EmpScheduleDao {
	
	 // 1. 전체 조회 (PT / VACATION / ETC 상세 포함)
    List<EmpScheduleDto> selectAll();

    // 2. PK 단건 조회
    EmpScheduleDto selectByCalNum(@Param("calNum") int calNum);

    // 3. 직원 + 날짜 범위 조회
    List<EmpScheduleDto> selectByEmpAndDate(
        @Param("empNum") int empNum,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    // 4. 수정 (필요한 컬럼만)
    int update(EmpScheduleDto empSchedule);

    // 5. 삭제
    int delete(@Param("calNum") int calNum);
    
    // 6. 일정 등록
    int insert(EmpScheduleDto dto);
    
    // ETC 일정
    int insertEtc(EmpScheduleDto dto); 
    
    // vacation 일정
    int insertEmpVacation(EmpScheduleDto dto);



}
