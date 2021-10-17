package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.Auction_Like;
import com.blockChain.domain.QAuction;
import com.blockChain.domain.QAuction_Like;
import com.blockChain.domain.QMember;
import com.blockChain.domain.QProduct;
import com.blockChain.domain.QProduct_Media;
import com.blockChain.domain.QProduct_Token;
import com.blockChain.domain.QSales_Like;
import com.blockChain.domain.Sales_Like;
import com.blockChain.dto.AuctionAddImgDTO;
import com.blockChain.dto.AuctionDTO;
import com.blockChain.repository.Auction_LikeRepoCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Auction_LikeRepoImpl implements Auction_LikeRepoCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Optional<List<AuctionAddImgDTO>> sltByMember(Long memberNo){
		QAuction_Like qal = QAuction_Like.auction_Like;
		QAuction qa = QAuction.auction;
		QMember qm = QMember.member;
		QProduct qp = QProduct.product;
		QProduct_Token qpt = QProduct_Token.product_Token;
		QProduct_Media qpm = QProduct_Media.product_Media;
		return Optional.ofNullable(queryFactory.select(Projections.constructor(AuctionAddImgDTO.class
				, qa.auctionNo
				, qa.auctionName
				, qa.auctionDetail
				, qpm.productMediaAdres
				, qa.auctionImmeprice
				, qa.auctionStart
				, qa.auctionDeadline))
				.from(qal)
				.join(qa).on(qal.auction.eq(qa))
				.join(qm).on(qal.member.eq(qm))
				.join(qpt).on(qpt.token.eq(qa.token))
				.join(qp).on(qp.eq(qpt.product))
				.join(qpm).on(qp.eq(qpm.product))
				.where(qm.memberNo.eq(memberNo))
				.orderBy(qa.auctionStart.desc())
				.fetch());
	}
	@Override
	public Optional<Auction_Like> checkLike(Long auctionPk, Long memberPk) {
		QAuction_Like qal = QAuction_Like.auction_Like;
		return Optional.ofNullable(queryFactory
				.selectFrom(qal)
				.where(qal.member.memberNo.eq(memberPk)
						.and(qal.auction.auctionNo.eq(auctionPk)))
				.fetchFirst());
	}
	@Override
	public Long likeCount(Long auctionPk) {
		QAuction_Like qal = QAuction_Like.auction_Like;
		return queryFactory
				.selectFrom(qal)
				.where(qal.auction.auctionNo.eq(auctionPk)).fetchCount();
	}
}
