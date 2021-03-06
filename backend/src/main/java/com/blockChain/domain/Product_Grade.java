package com.blockChain.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class Product_Grade {
	@Id
	@GeneratedValue
	@Column(name="PRODUCT_GRADE_NO")
	private Long productGradeNo;

	@Column(name="PRODUCT_GRADE_NM",length=45)
	private String productGrade;
	
	@Column(name="PRODUCT_SHEETS")
    private Long productSheets;
	
	@Column(name="PRODUCT_PERCENT")
	private Long productPercent;
}
