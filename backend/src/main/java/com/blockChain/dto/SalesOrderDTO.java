package com.blockChain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderDTO {
	//상품 이름, 상품 이미지, 구매일, 상품 가격
	private Long salesNo;
	private String sales;
	private String salesImg;
	private LocalDateTime buyDate;
	private Long price;
}
