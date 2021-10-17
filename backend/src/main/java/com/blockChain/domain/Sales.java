package com.blockChain.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class Sales {
	
    // 판매번호 
	@Id
	@GeneratedValue
	@Column(name="SALES_NO")
    private Long salesNo;


    // 판매상세
	@Column(name="SALES_DETAIL",length=5000)
    private String salesDetail;

    // 판매가격 
	@Column(name="SALES_PRICE")
    private Long salesPrice;

    // 판매명 
	@Column(name="SALES_NM",length=300)
    private String salesNm;
    // 판매구분 
	@Column(name="SALES_DIV",length=3)
    private String salesDiv;
	
	@Column(name="SALES_IMG")
	private String imgUrl;
	
}
