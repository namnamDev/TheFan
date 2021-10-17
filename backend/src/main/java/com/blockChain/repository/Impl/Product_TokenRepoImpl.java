package com.blockChain.repository.Impl;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.QProduct_Token;
import com.blockChain.repository.Product_TokenRepoCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Product_TokenRepoImpl implements Product_TokenRepoCustom{
	private final JPAQueryFactory queryFactory;
	
	@Override
	public Long countCardByCardNo(Long CardNo){
		QProduct_Token qpt = QProduct_Token.product_Token;
		return queryFactory.selectFrom(qpt)
				.where(qpt.product.productNo.eq(CardNo))
				.fetchCount();
	}
}
