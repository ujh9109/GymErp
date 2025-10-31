package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.gymerp.dto.ScheduleDto;

@Mapper
public interface ScheduleDao {

	// 전체 스케줄 조회
	List<ScheduleDto> selectAllSchedules();

	// 1. 특정 날짜 스케줄 조회
	List<ScheduleDto> findByDate(@Param("date") String date);

	// 2. 특정 직원 스케줄 조회 (선택적)
	List<ScheduleDto> findByEmpNum(@Param("empNum") int empNum);

	// 3. 단건 조회
	ScheduleDto findByShNum(@Param("shNum") int shNum);

	// 4. 스케줄 등록
	int insert(ScheduleDto schedule);

	// 5. 스케줄 수정
	int update(ScheduleDto schedule);

	// 6. 스케줄 삭제
	int delete(@Param("shNum") int shNum);
}
