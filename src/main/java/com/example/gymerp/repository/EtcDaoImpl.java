package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.EtcDto;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class EtcDaoImpl implements EtcDao {

	private final SqlSession sqlSession;

	  // Etc 일정 등록
    @Override
    public int insertEtc(EtcDto etcDto) {
        return sqlSession.insert("EtcMapper.insertEtc", etcDto);
    }

    // 특정 Etc 일정 조회
    @Override
    public EtcDto selectEtcByNum(int etcNum) {
        return sqlSession.selectOne("EtcMapper.selectEtcByNum", etcNum);
    }

    // Etc 일정 수정
    @Override
    public int updateEtc(EtcDto etcDto) {
        return sqlSession.update("EtcMapper.updateEtc", etcDto);
    }

    // Etc 일정 삭제
    @Override
    public int deleteEtc(int etcNum) {
        return sqlSession.delete("EtcMapper.deleteEtc", etcNum);
    }

    // 일정 전체 조회
    @Override
    public List<EtcDto> selectAll() {
        return sqlSession.selectList("EtcMapper.selectAllEtc");
    }

}
