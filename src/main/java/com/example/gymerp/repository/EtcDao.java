package com.example.gymerp.repository;

import java.util.List;

import com.example.gymerp.dto.EtcDto;

public interface EtcDao {
	
	//  일정 등록
    int insertEtc(EtcDto etcDto);

    //  일정 단건 조회
    EtcDto selectEtcByNum(int etcNum);

    //  일정 수정
    int updateEtc(EtcDto etcDto);

    // 일정 삭제
    int deleteEtc(int etcNum);
 
    // ️ 일정 조회
    List<EtcDto> selectAll();
}


