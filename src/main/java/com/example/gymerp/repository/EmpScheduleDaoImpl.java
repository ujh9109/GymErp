package com.example.gymerp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.EmpScheduleDto;
import com.example.gymerp.dto.PtRegistrationDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpScheduleDaoImpl implements EmpScheduleDao {

    private final SqlSession sqlSession;

    /** ============================= 일정 조회 ============================= */

    // 전체 일정 조회 (PT / Vacation / Etc 상세 포함)
    @Override
    public List<EmpScheduleDto> selectAll() {
        return sqlSession.selectList("EmpScheduleMapper.selectAll");
    }

    // 단건 조회 (PK 기준)
    @Override
    public EmpScheduleDto selectByCalNum(int calNum) {
        return sqlSession.selectOne("EmpScheduleMapper.selectByCalNum", calNum);
    }

    // 직원 + 날짜 범위로 일정 조회
    @Override
    public List<EmpScheduleDto> selectByEmpAndDate(int empNum, LocalDateTime startDate, LocalDateTime endDate) {
        return sqlSession.selectList("EmpScheduleMapper.selectByEmpAndDate",
                Map.of("empNum", empNum, "startDate", startDate, "endDate", endDate));
    }


    /** ============================= 일정 등록 ============================= */

    // ETC 일정 등록
    @Override
    public int insertEmpEtc(EmpScheduleDto dto) {
        return sqlSession.insert("EmpScheduleMapper.insertEmpEtc", dto);
    }

    // VACATION 일정 등록
    @Override
    public int insertEmpVacation(EmpScheduleDto dto) {
        return sqlSession.insert("EmpScheduleMapper.insertEmpVacation", dto);
    }

    // PT REGISTRATION 일정 등록 (EmpSchedule 테이블)
    @Override
    public int insertEmpRegistration(EmpScheduleDto dto) {
        return sqlSession.insert("EmpScheduleMapper.insertEmpRegistration", dto);
    }

    // PT REGISTRATION 상세 등록 (Registration 테이블)
    @Override
    public int insertRegistration(PtRegistrationDto dto) {
        return sqlSession.insert("EmpScheduleMapper.insertRegistration", dto);
    }


    /** ============================= 일정 수정 및 삭제 ============================= */

    // 일정 수정
    @Override
    public int update(EmpScheduleDto empSchedule) {
        return sqlSession.update("EmpScheduleMapper.update", empSchedule);
    }

    // 일정 삭제
    @Override
    public int delete(int calNum) {
        return sqlSession.delete("EmpScheduleMapper.delete", calNum);
    }
}
