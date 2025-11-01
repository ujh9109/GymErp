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

	// 일정 등록
	@Override
	public int insertEtc(EtcDto etcDto) {
		return sqlSession.insert("EtcDao.insertEtc", etcDto);
	}

	// 단건 조회
	@Override
	public EtcDto selectEtcByNum(int etcNum) {
		return sqlSession.selectOne("EtcDao.selectEtcByNum", etcNum);
	}

	// 수정
	@Override
	public int updateEtc(EtcDto etcDto) {
		return sqlSession.update("EtcDao.updateEtc", etcDto);
	}

	// 삭제
	@Override
	public int deleteEtc(int etcNum) {
		return sqlSession.delete("EtcDao.deleteEtc", etcNum);
	}

	// 전체 조회
	@Override
	public List<EtcDto> selectAll() {
		return sqlSession.selectList("EtcDao.selectAllEtc");
	}
}
