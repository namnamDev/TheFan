package com.blockChain.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Auction_Order {
    // 경매목록번호 
	@Id
	@GeneratedValue
	@Column(name="AUCTION_ORDER_NO")
    private Long AUCTION_ORDER_NO;

    // 회원번호 
	@ManyToOne
	@JoinColumn(name="MEMBER_NO")
    private Member member;

    // 경매번호
	@ManyToOne
	@JoinColumn(name="AUCTION_NO")
    private Auction auction;

    // 낙찰가 
//	@JoinColumn(name="AUCTION_ORDER_PRICE")
//    private Long auctionOrderPrice;

    // 주문일 
//	@Column(name="AUCTION_ORDER_DATE",updatable = false,
//			  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Column(name="AUCTION_ORDER_DATE",updatable = false)
    private LocalDateTime auctionOrderDate;

    // 토큰번호
//	@OneToOne
//	@JoinColumn(name="TOKEN_NO")
//    private Token token;

}
