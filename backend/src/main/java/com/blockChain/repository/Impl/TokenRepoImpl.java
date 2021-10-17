package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.QMember;
import com.blockChain.domain.QProduct_Token;
import com.blockChain.domain.QSales;
import com.blockChain.domain.QSales_Product;
import com.blockChain.domain.QToken;
import com.blockChain.domain.QToken_Owner;
import com.blockChain.domain.Sales;
import com.blockChain.domain.Token;
import com.blockChain.repository.TokenRepoCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TokenRepoImpl implements TokenRepoCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Optional<List<Token>> sltMultiBySales(Sales sales){
		QToken qt = QToken.token;
		QProduct_Token qpt = QProduct_Token.product_Token;
		QSales_Product qsp =QSales_Product.sales_Product;
		QSales qs = QSales.sales;
		QToken_Owner qto = QToken_Owner.token_Owner;
		return Optional.ofNullable(queryFactory.select(qt)
				.from(qpt)
				.join(qt).on(qpt.token.eq(qt))
				.join(qsp).on(qpt.product.eq(qsp.product))
				.leftJoin(qto).on(qt.eq(qto.token))
				.where(qsp.sales.eq(sales).and(qto.token.isNull()))
				.fetch());
	}
}
