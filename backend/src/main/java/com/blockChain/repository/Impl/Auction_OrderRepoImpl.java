package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.Auction_Order;
import com.blockChain.domain.QAuction;
import com.blockChain.domain.QAuction_Order;
import com.blockChain.domain.QMember;
import com.blockChain.domain.QProduct;
import com.blockChain.domain.QProduct_Media;
import com.blockChain.domain.QProduct_Token;
import com.blockChain.domain.QToken;
import com.blockChain.dto.AuctionOrderDTO;
import com.blockChain.repository.Auction_OrderRepoCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Auction_OrderRepoImpl implements Auction_OrderRepoCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Optional<List<AuctionOrderDTO>> sltMultiAuctionOrderByMember(Long memberNo){
		QAuction qa = QAuction.auction;
		QProduct_Media qpm= QProduct_Media.product_Media;
		QProduct qp = QProduct.product;
		QMember qm = QMember.member;
		QAuction_Order qao = QAuction_Order.auction_Order;
		QProduct_Token qpt = QProduct_Token.product_Token;
		QToken qt = QToken.token;
		return Optional.ofNullable(queryFactory.select(Projections.constructor(AuctionOrderDTO.class
				, qa.auctionNo
				, qp.productNm
				, qpm.productMediaAdres
				, qa.auctionImmeprice
				, qm.memberNo
				, qm.memberNick
				, qao.auctionOrderDate
				)).from(qao)
				.where(qao.member.memberNo.eq(memberNo))
				.join(qa).on(qao.auction.eq(qa))
				.join(qm).on(qa.member.eq(qm))
				.join(qt).on(qa.token.eq(qt))
				.join(qpt).on(qt.eq(qpt.token))
				.join(qp).on(qpt.product.eq(qp))
				.join(qpm).on(qp.eq(qpm.product))
				.orderBy(qao.auctionOrderDate.desc())
				.fetch());
	}
	@Override
	public Optional<Auction_Order> sltByAuctionNo(Long auctionNo) {
		QAuction_Order qao = QAuction_Order.auction_Order;

		return Optional.ofNullable(queryFactory.selectFrom(qao).where(qao.auction.auctionNo.eq(auctionNo)).fetchOne());
	}
}
