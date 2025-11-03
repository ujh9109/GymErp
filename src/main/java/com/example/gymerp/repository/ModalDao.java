package com.example.gymerp.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.example.gymerp.dto.ServiceDto;

@Mapper
public interface ModalDao {

    /* ================================
       서비스 상품 선택 모달
    ================================= */

    // 서비스 상품 목록 조회
    List<ServiceDto> getServiceModalList(ServiceDto dto);

    // 서비스 상품 전체 개수 조회
    int getServiceModalCount(ServiceDto dto);

    /* ================================
       서비스 상품 선택 모달 끝
    ================================= */
}
