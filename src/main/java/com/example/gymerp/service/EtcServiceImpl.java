package com.example.gymerp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EtcDto;
import com.example.gymerp.repository.EtcDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EtcServiceImpl implements EtcService{
	
	private final EtcDao etcDao; // DAO 주입

	// Etc 일정 등록
    @Override
    @Transactional
    public int createEtc(EtcDto etcDto) {
    	return etcDao.insertEtc(etcDto);
    }

    // 특정 Etc 일정 조회
    @Override
    public EtcDto getEtc(int etcNum) {
        return etcDao.selectEtcByNum(etcNum);
    }

    // Etc 일정 수정
    @Override
    @Transactional
    public int updateEtc(EtcDto etcDto) {
        return etcDao.updateEtc(etcDto);
    }

    // Etc 일정 삭제
    @Override
    @Transactional
    public int deleteEtc(int etcNum) {
        return etcDao.deleteEtc(etcNum);
    }

	@Override
	public List<EtcDto> getAllEtc() {
		 return etcDao.selectAll();
	}
}
