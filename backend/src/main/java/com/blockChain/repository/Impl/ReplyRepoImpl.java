package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.QReply;
import com.blockChain.dto.ReplyDTO;
import com.blockChain.repository.ReplyRepoCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReplyRepoImpl implements ReplyRepoCustom{
	private final JPAQueryFactory queryFactory;
	
	
	@Override
	public Optional<List<ReplyDTO>>sltReviewList(Long cardPK){
		QReply qr = QReply.reply;
		System.out.println(cardPK);
		List<ReplyDTO> aa = queryFactory.select(Projections.constructor(ReplyDTO.class
				, qr.replyNo
				, qr.member.memberNick
				, qr.member.memberNo
				, qr.sales.salesNm
				, qr.replyContent
				, qr.replyDate))
				.from(qr)
				.where(qr.sales.salesNo.eq(cardPK))
				.orderBy(qr.replyDate.desc())
				.fetch();
		System.out.println(aa.toString());
		return Optional.ofNullable(aa);
	}
}
