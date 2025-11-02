package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.EtcDto;

public interface EtcService {
	
	/**
     * Etc 일정 등록
     * 
     * 
     */
    int createEtc(EtcDto etcDto);

    /**
     * Etc 단건 일정 조회
     * 
     * 
     */
    EtcDto getEtc(int etcNum);

    /**
     * Etc 일정 수정
     * 
     *
     */
    int updateEtc(EtcDto etcDto);

    /**
     * Etc 일정 삭제
     * 
     * 
     */
    int deleteEtc(int etcNum);
    
    
    /**
     * Etc 일정 조회
     * 
     * 
     */
    List<EtcDto> getAllEtc();
}
