package com.blockChain.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class Wallets {

		// 지갑번호
	  @Id
	  @GeneratedValue
	  @Column(name="WALLET_NO")
	  private Long walletNo;

	  // 지갑주소
	  @Column(name="WALLET_ADD",length=500)
	  private String walletAdd;
	  
	  // 지갑주인
	  @OneToOne
	  @JoinColumn(name="MEMBER")
	  private Member member;
	  
	  // balance
	  @Column(name="WALLET_BALANCE")
	  private BigDecimal walletBal;
	  
	  // 거래횟수
	  @Column(name="WALLET_RC")
	  private Long walletRC;
	  
	  // cash
	  @Column(name="WALLET_CASH")
	  private Long walletCash;
}
