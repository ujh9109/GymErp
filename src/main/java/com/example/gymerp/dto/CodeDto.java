package com.example.gymerp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeDto {

	private String codeA;
	private String codeB;
	private String codeAName;
	private String codeBName;
}
