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
public class Sales_Order {
	
    // 판매목록번호 
	@Id
	@GeneratedValue
	@Column(name="SALES_ORDER_NO")
    private Long salesOrderNo;

    // 판매번호
	@ManyToOne
	@JoinColumn(name="SALES_NO")
    private Sales sales;

    // 구매자
	@ManyToOne
	@JoinColumn(name="MEMBER_NO")
    private Member member;

    // 구매일 
	@Column(name="SALES_ORDER_DATE",updatable = false,
			  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime salesOrderDate;


}
