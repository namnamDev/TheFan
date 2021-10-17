package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.Product;
import com.blockChain.domain.QCeleb;
import com.blockChain.domain.QProduct;
import com.blockChain.domain.QProduct_Grade;
import com.blockChain.domain.QProduct_Media;
import com.blockChain.domain.QProduct_Token;
import com.blockChain.domain.QSales;
import com.blockChain.domain.QSales_Product;
import com.blockChain.domain.QToken;
import com.blockChain.domain.QToken_Owner;
import com.blockChain.domain.Sales;
import com.blockChain.domain.Sales_Product;
import com.blockChain.dto.CardAddCountDTO;
import com.blockChain.dto.CardDTO;
import com.blockChain.dto.CardGenerateDTO;
import com.blockChain.dto.SalesDTO;
import com.blockChain.repository.SalesRepoCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SalesRepoImpl implements SalesRepoCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Optional<Sales> sltBySalesNM(String NM){
		QSales qsl = QSales.sales;
		Sales res = queryFactory.selectFrom(qsl).where(qsl.salesNm.eq(NM)).fetchOne();
		return Optional.ofNullable(res);
	}
	
	@Override
	public Optional<List<SalesDTO>> slyBySalesDiv(String div){
		QSales qsl = QSales.sales;
		
		return  Optional.ofNullable(queryFactory.select(Projections.constructor(SalesDTO.class
				,qsl.salesNo
				,qsl.salesNm
				,qsl.salesDetail
				,qsl.imgUrl
				,qsl.salesPrice
				,qsl.salesDiv
				))
				.from(qsl)
				.where(qsl.salesDiv.eq(div))
				.fetch());
	}
	
	@Override
	public Optional<List<CardGenerateDTO>>gainCardList(Long salesPK){
		QSales qs = QSales.sales;
		QProduct qp = QProduct.product;
		QProduct_Media qpm= QProduct_Media.product_Media;
		QSales_Product qsp = QSales_Product.sales_Product;
		return Optional.ofNullable(queryFactory.select(Projections.constructor(
				CardGenerateDTO.class
				, qp.productNo
				, qp.productNm
				, qpm.productMediaAdres
				, qp.productGrade.productGradeNo
				, qp.productGrade.productGrade
				))
				.from(qsp)
				.join(qp).on(qsp.product.productNo.eq(qp.productNo))
				.join(qpm).on(qp.productNo.eq(qpm.product.productNo))
				.orderBy(qp.productGrade.productGradeNo.asc())
				.where(qsp.sales.salesNo.eq(salesPK)).fetch());
	}
	@Override
	public Optional<Sales> sltByContainSalesNM(String NM){
		QSales qsl = QSales.sales;
		Sales res = queryFactory.selectFrom(qsl).where(qsl.salesNm.contains(NM)).fetchOne();
		return Optional.ofNullable(res);
	}
	@Override
	public Optional<List<SalesDTO>> searchSales(String NM){
		QSales qs = QSales.sales;
		QProduct qp = QProduct.product;
//		QProduct_Token qpt = QProduct_Token.product_Token;
		QSales_Product qsp = QSales_Product.sales_Product;
		QCeleb qc = QCeleb.celeb;
		return  Optional.ofNullable(queryFactory.select(Projections.constructor(SalesDTO.class
				,qs.salesNo
				,qs.salesNm
				,qs.salesDetail
				,qs.imgUrl
				,qs.salesPrice
				,qs.salesDiv
				))
				.from(qs)
				.join(qsp).on(qs.eq(qsp.sales))
				.join(qp).on(qsp.product.eq(qp))
				.join(qc).on(qp.celeb.eq(qc))
//				.where(qs.salesNm.contains(NM))
				.where(qc.celebNm.contains(NM))
				.groupBy(qs)
				.fetch());
	}
	
	@Override
	public Optional<List<CardAddCountDTO>>gainCardListAddNum(Long salesPK){
		QSales qs = QSales.sales;
		QProduct qp = QProduct.product;
		QProduct_Media qpm= QProduct_Media.product_Media;
		QSales_Product qsp = QSales_Product.sales_Product;
		QProduct_Token qpt = QProduct_Token.product_Token;
		QToken_Owner qto = QToken_Owner.token_Owner;
		QToken qt = QToken.token;
		QProduct_Grade qpg = QProduct_Grade.product_Grade;
		return Optional.ofNullable(queryFactory.select(Projections.constructor(
				CardAddCountDTO.class
				, qp.productNo
				, qp.productNm
				, qpm.productMediaAdres
				, qpg.productGradeNo
				, qpg.productGrade
				, qpt.count().nullif(0L)
				))
				.from(qsp)
				.join(qp).on(qsp.product.productNo.eq(qp.productNo))
				.join(qpm).on(qp.productNo.eq(qpm.product.productNo))
				.join(qpt).on(qp.productNo.eq(qpt.product.productNo))
				.join(qt).on(qpt.token.eq(qt))
				.leftJoin(qto).on(qt.eq(qto.token))
//				.join(qto).on(qt.eq(qto.token))
				.fetchJoin()
				.join(qpg).on(qp.productGrade.eq(qpg))
				.orderBy(qp.productGrade.productGradeNo.asc())
				.orderBy(qp.productNo.asc())
				.groupBy(qp.productNo)
				.where(qsp.sales.salesNo.eq(salesPK).and(qto.member.isNull())).fetch());
	}
	@Override
	public Long countLeftCard (Long cardPK){
		QSales qs = QSales.sales;
		QProduct qp = QProduct.product;
		QProduct_Media qpm= QProduct_Media.product_Media;
		QSales_Product qsp = QSales_Product.sales_Product;
		QProduct_Token qpt = QProduct_Token.product_Token;
		QToken_Owner qto = QToken_Owner.token_Owner;
		QToken qt = QToken.token;
		QProduct_Grade qpg = QProduct_Grade.product_Grade;
		
		Long res = queryFactory.select(qto.count())
				.from(qto)
				.where(qpt.product.productNo.eq(cardPK))
//				.join(qpt).on(qp)
				.leftJoin(qpt).on(qpt.token.eq(qto.token))
				.fetchJoin()
				.fetchCount();
		return cardPK;
		
	}
	
//	@Override
//	public Long countOwnedCard (Long cardPK){
//		QProduct_Token qpt = QProduct_Token.product_Token;
//		QToken_Owner qto = QToken_Owner.token_Owner;
//		Long res = queryFactory.select(qpt.count()).from(qpt)
//				.where(qpt.product.productNo.eq(cardPK))
//				.leftJoin(qto).on(qpt.token.tokenNo.eq(qto.token.tokenNo))
//				.fetchCount();
//		System.out.println(res);
//		return cardPK;
//		
//	}
	@Override
	public List<Product>cardListByPack(Long cardPackPk){
		QSales_Product qsp = QSales_Product.sales_Product;
		QProduct qp = QProduct.product;
		return queryFactory.selectFrom(qp).where(qsp.sales.salesNo.eq(cardPackPk)).fetch();
	}
}
