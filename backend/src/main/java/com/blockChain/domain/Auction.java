package com.blockChain.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter @Setter
public class Auction {
    // 경매번호
	@Id
	@GeneratedValue
	@Column(name="AUCTION_NO")
    private Long auctionNo;

    // 작성자
	@ManyToOne
    @JoinColumn(name = "MEMBER_NO")
    private Member member;

    // 토큰번호
	@ManyToOne
    @JoinColumn(name = "TOKEN_NO")
    private Token token;

    // 경매명
	@Column(name="AUCTION_NAME")
    private String auctionName;

    // 경매상세
	@Column(name="AUCTION_DETAIL",length=1000)
    private String auctionDetail;

    // 경매즉시구매가
	@Column(name="IMMEPRICE")
    private Long auctionImmeprice;

    // 경매시작시간 
//	@Column(name="AUCTION_START",updatable = false,
//			  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//	@CreationTimestamp
	@Column(name="AUCTION_START")
    private LocalDateTime auctionStart;

    // 경매종료시간 
	@Column(name="AUCTION_DEADLINE")
    private LocalDateTime auctionDeadline;
	
	@Column(name="AUCTION_STATE", length=5)
	private String auctionState;
}
