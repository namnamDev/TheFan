package com.blockChain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionOrderDTO {
	Long auctionNo;
	String cardNm;
	String cardImg;
	Long price;
	Long memberNo;
	String memberNick;
	LocalDateTime buyDate;
}
