package com.blockChain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionDTO {
	private Long auctionNo;
	private String auctionTitle;
	private String auctionDetail;
	private Long price;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
