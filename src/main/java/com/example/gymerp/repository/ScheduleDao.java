package com.example.gymerp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.gymerp.dto.ScheduleDto;

public interface ScheduleDao {
    /* 전체 일정 조회 (관리자용) */
    public List<ScheduleDto> selectAll();

    /* 특정 일정 상세조회 */
    public ScheduleDto selectByShNum(@Param("shNum") int shNum);

    /* 직원별 일정 조회 */
    public List<ScheduleDto> selectByEmpNum(@Param("empNum") int empNum);

    /* 날짜 범위별 조회 (월간 캘린더) */
    public List<ScheduleDto> selectByDateRange(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    /* 일정 등록 */
    public int insert(ScheduleDto schedule);

    /* 일정 수정 */
    public int update(ScheduleDto schedule);

    /* 일정 삭제 */
    public int delete(@Param("shNum") int shNum);
}
