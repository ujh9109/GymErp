package com.example.gymerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.ServiceDto;
import com.example.gymerp.repository.ModalDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModalServiceImpl implements ModalService {@Override
	public List<ServiceDto> getServiceModalList(ServiceDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getServiceModalCount(ServiceDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ProductDto> getProductModalList(String keyword, int page, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getProductModalCount(String keyword) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<EmpDto> getEmployeeModalList(String keyword, int page, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getEmployeeModalCount(String keyword) {
		// TODO Auto-generated method stub
		return 0;
	}

}
