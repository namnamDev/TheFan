package com.blockChain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {
	private Long salesNo;
	private String salesNM;
	private String salesDetail;
	private String salesImg;
	private Long salesPrice;
	private String salesDiv;
}
