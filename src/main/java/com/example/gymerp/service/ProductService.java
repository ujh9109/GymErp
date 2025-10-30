package com.example.gymerp.service;

import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.ProductListResponse;

public interface ProductService {

	public ProductListResponse getProducts(int pageNum, ProductDto dto);

	public void save(ProductDto dto);
	public void modifyProduct(ProductDto dto);
	public ProductDto getDetail(int productId);
	public void updateProductStatus(int productId, boolean isActive);
}
