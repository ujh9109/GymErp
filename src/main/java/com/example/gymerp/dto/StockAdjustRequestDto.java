package com.example.gymerp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockAdjustRequestDto {

	private String action;   // "ADD" or "SUBTRACT"
    private int quantity; // 수량
    private String notes;    // 사유 or 비고
}