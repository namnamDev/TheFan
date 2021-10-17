package com.blockChain.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(Sold_Bundle_InsidePK.class)
public class Sold_Bundle_Inside {

    // 판매번호 
	@Id
	@ManyToOne
    @JoinColumn(name = "SALES_ORDER_NO")
    private Sales_Order salesOrder;

    // 토큰
	@Id
	@ManyToOne
    @JoinColumn(name = "TOKEN")
    private Token token;
}
