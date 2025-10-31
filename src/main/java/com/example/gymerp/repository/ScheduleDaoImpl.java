package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.ScheduleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScheduleDaoImpl implements ScheduleDao{

	
	@Autowired
	private final SqlSession session;
	
	//전체 스케줄 조회
    @Override
    public List<ScheduleDto> selectAllSchedules() {
        return session.selectList("ScheduleMapper.selectAllSchedules");
    }

    //특정 날짜 스케줄 조회
    @Override
    public List<ScheduleDto> findByDate(String date) {
        return session.selectList("ScheduleMapper.findByDate", date);
    }

    //특정 직원 스케줄 조회
    @Override
    public List<ScheduleDto> findByEmpNum(int empNum) {
        return session.selectList("ScheduleMapper.findByEmpNum", empNum);
    }

    //단건 스케줄 조회
    @Override
    public ScheduleDto findByShNum(int shNum) {
        return session.selectOne("ScheduleMapper.findByShNum", shNum);
    }

    //스케줄 등록
    @Override
    public int insert(ScheduleDto schedule) {
        return session.insert("ScheduleMapper.insert", schedule);
    }

    //스케줄 수정
    @Override
    public int update(ScheduleDto schedule) {
        return session.update("ScheduleMapper.update", schedule);
    }

    //스케줄 삭제
    @Override
    public int delete(int shNum) {
        return session.delete("ScheduleMapper.delete", shNum);
    }
}
