package com.blockChain.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(Token_OwnerPK.class)
public class Token_Owner {
	
    // 토큰번호
	@Id
	@ManyToOne
    @JoinColumn(name = "TOKEN_NO")
    private Token token;

    // 소유자
	@Id
	@ManyToOne
    @JoinColumn(name = "MEMBER_NO")
    private Member member;
//	@Column(name="AUCTION_START",updatable = false,
//	  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//	@CreationTimestamp
	@Column(name="OWN_DATE")
	private LocalDateTime ownDate;
	
	@Column(name="ON_AUCTION")
    @ColumnDefault("0")
	private Long onAuction;
	
}
