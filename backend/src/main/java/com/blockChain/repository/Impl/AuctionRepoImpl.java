package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.Auction;
import com.blockChain.domain.Celeb_Group;
import com.blockChain.domain.QAuction;
import com.blockChain.domain.QAuction_Order;
import com.blockChain.domain.QBid;
import com.blockChain.domain.QCeleb_Group;
import com.blockChain.domain.QMember;
import com.blockChain.domain.QProduct;
import com.blockChain.domain.QProduct_Grade;
import com.blockChain.domain.QProduct_Media;
import com.blockChain.domain.QProduct_Token;
import com.blockChain.domain.QSales;
import com.blockChain.domain.QSales_Order;
import com.blockChain.domain.QToken;
import com.blockChain.domain.Sales;
import com.blockChain.dto.AuctionDTO;
import com.blockChain.dto.AuctionGroupListDTO;
import com.blockChain.dto.AuctionRegistedByMemberDTO;
import com.blockChain.dto.BidDTO;
import com.blockChain.dto.CardDTO;
import com.blockChain.dto.MemberDTO;
import com.blockChain.repository.AuctionRepoCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuctionRepoImpl implements AuctionRepoCustom{
	private final JPAQueryFactory queryFactory;
	
	@Override
	public Optional<List<AuctionGroupListDTO>> sltAuctionByGroup(Long groupNo){
		QAuction qa = QAuction.auction;
		QMember qm = QMember.member;
		QProduct_Token qpt = QProduct_Token.product_Token;
		QProduct qp = QProduct.product;
		QSales qs = QSales.sales;
		QProduct_Media qpm= QProduct_Media.product_Media;
		QProduct_Grade qpg = QProduct_Grade.product_Grade;
		QBid qb = QBid.bid;
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qa.auctionState.eq("SELL"));
		if(groupNo != 0L){
			builder.and(qp.celeb.group.groupNo.eq(groupNo));
		}
		List<AuctionGroupListDTO> res = queryFactory.select(Projections.constructor(
				AuctionGroupListDTO.class
				, Projections.constructor(
						MemberDTO.class
						, qa.member.memberNo
						, qa.member.memberNick)
				, Projections.constructor(CardDTO.class
						, qpt.product.productNo
						, qpt.product.productNm
						, qpm.productMediaAdres
						, qa.token.tokenNo
						, qa.token.tokenSeriarlizeNo
						, qp.productGrade.productGradeNo
						, qp.productGrade.productGrade
						)
				, Projections.constructor(AuctionDTO.class
						, qa.auctionNo
						, qa.auctionName
						, qa.auctionDetail
						, qa.auctionImmeprice
						, qa.auctionStart
						, qa.auctionDeadline)
		
						
				)).from(qa)
				.join(qpt).on(qa.token.tokenNo.eq(qpt.token.tokenNo))
				.join(qp).on(qpt.product.productNo.eq(qp.productNo))
				.join(qpm).on(qp.productNo.eq(qpm.product.productNo))
				.where(builder)
				.orderBy(qa.auctionStart.desc())
				.fetch();
		return Optional.ofNullable(res);
	}
	
	@Override
	public Optional<List<AuctionGroupListDTO>> searchAuction(String word){
		QAuction qa = QAuction.auction;
		QMember qm = QMember.member;
		QProduct_Token qpt = QProduct_Token.product_Token;
		QProduct qp = QProduct.product;
		QSales qs = QSales.sales;
		QProduct_Media qpm= QProduct_Media.product_Media;
		QProduct_Grade qpg = QProduct_Grade.product_Grade;
		QBid qb = QBid.bid;
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qa.auctionName.contains(word));
		builder.and(qa.auctionState.eq("SELL"));
		List<AuctionGroupListDTO> res = queryFactory.select(Projections.constructor(
				AuctionGroupListDTO.class
				, Projections.constructor(
						MemberDTO.class
						, qa.member.memberNo
						, qa.member.memberNick)
				, Projections.constructor(CardDTO.class
						, qpt.product.productNo
						, qpt.product.productNm
						, qpm.productMediaAdres
						, qa.token.tokenNo
						, qa.token.tokenSeriarlizeNo
						, qp.productGrade.productGradeNo
						, qp.productGrade.productGrade
						)
				, Projections.constructor(AuctionDTO.class
						, qa.auctionNo
						, qa.auctionName
						, qa.auctionDetail
						, qa.auctionImmeprice
						, qa.auctionStart
						, qa.auctionDeadline)
		
						
				)).from(qa)
				.join(qpt).on(qa.token.tokenNo.eq(qpt.token.tokenNo))
				.join(qp).on(qpt.product.productNo.eq(qp.productNo))
				.join(qpm).on(qp.productNo.eq(qpm.product.productNo))
				.where(builder)
				.orderBy(qa.auctionStart.asc())
				.fetch();
		return Optional.ofNullable(res);
	}
	@Override
	public Optional<AuctionGroupListDTO> sltOneByNo(Long auctionNo){
		QAuction qa = QAuction.auction;
		QMember qm = QMember.member;
		QProduct_Token qpt = QProduct_Token.product_Token;
		QProduct qp = QProduct.product;
		QSales qs = QSales.sales;
		QProduct_Media qpm= QProduct_Media.product_Media;
		QProduct_Grade qpg = QProduct_Grade.product_Grade;
		QBid qb = QBid.bid;
		AuctionGroupListDTO res = queryFactory.select(Projections.constructor(
				AuctionGroupListDTO.class
				, Projections.constructor(
						MemberDTO.class
						, qa.member.memberNo
						, qa.member.memberNick
						)
				, Projections.constructor(CardDTO.class
						, qpt.product.productNo
						, qpt.product.productNm
						, qpm.productMediaAdres
						, qa.token.tokenNo
						, qa.token.tokenSeriarlizeNo
						, qp.productGrade.productGradeNo
						, qp.productGrade.productGrade
						)
				, Projections.constructor(AuctionDTO.class
						, qa.auctionNo
						, qa.auctionName
						, qa.auctionDetail
						, qa.auctionImmeprice
						, qa.auctionStart
						, qa.auctionDeadline)

				)).from(qa)
				.join(qpt).on(qa.token.tokenNo.eq(qpt.token.tokenNo))
				.join(qp).on(qpt.product.productNo.eq(qp.productNo))
				.join(qpm).on(qp.productNo.eq(qpm.product.productNo))
				.where(qa.auctionNo.eq(auctionNo).and(qa.auctionState.eq("SELL")))
				.fetchOne();
		return Optional.ofNullable(res);
	}
	@Override
	public Optional<List<AuctionRegistedByMemberDTO>> auctionRegistedByMember(Long memberNo){
		QAuction qa = QAuction.auction;
		QProduct_Media qpm= QProduct_Media.product_Media;
		QProduct qp = QProduct.product;
		QMember qm = QMember.member;
		QSales_Order qso = QSales_Order.sales_Order;
		QAuction_Order qao = QAuction_Order.auction_Order;
		QProduct_Token qpt = QProduct_Token.product_Token;
		QToken qt = QToken.token;
		return Optional.ofNullable(queryFactory.select(Projections.constructor(AuctionRegistedByMemberDTO.class
				, qa.auctionNo
				, qp.productNm
				, qpm.productMediaAdres
				, qa.auctionImmeprice
				, qa.auctionState
				, qm.memberNo
				, qm.memberNick
				, qao.auctionOrderDate
				)).from(qa)
				.where(qa.member.memberNo.eq(memberNo))
				.join(qt).on(qa.token.eq(qt))
				.join(qpt).on(qt.eq(qpt.token))
				.join(qp).on(qpt.product.eq(qp))
				.join(qpm).on(qp.eq(qpm.product))
				.leftJoin(qao).on(qa.eq(qao.auction))
				.leftJoin(qm).on(qao.member.eq(qm))
				.orderBy(qa.auctionStart.desc())
				.fetch());
	}
	
	@Override
	public Long countAuctionRegistedByMember(Long memberNo){
		QAuction qa = QAuction.auction;
		return queryFactory.selectFrom(qa).where(qa.member.memberNo.eq(memberNo)).fetchCount();
	}
	@Override
	public  Optional<Auction> checkAuctionToken(Long tokenNo){
		QAuction qa = QAuction.auction;
		return Optional.ofNullable(queryFactory
				.selectFrom(qa)
				.where(qa.token.tokenNo.eq(tokenNo)
						.and(qa.auctionState.eq("SELL")))
				.fetchFirst());
	}
}
