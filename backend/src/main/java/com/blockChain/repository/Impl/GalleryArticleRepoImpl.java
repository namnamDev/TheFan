package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.Member;
import com.blockChain.domain.Member_Gall_Like;
import com.blockChain.domain.QCeleb_Like;
import com.blockChain.domain.QGalleryArticle;
import com.blockChain.domain.QMember;
import com.blockChain.domain.QMember_Gall_Like;
import com.blockChain.domain.QMember_Grade;
import com.blockChain.domain.QSales_Like;
import com.blockChain.domain.Sales_Like;
import com.blockChain.dto.GalleryArticleDTO;
import com.blockChain.dto.MemberDTO;
import com.blockChain.dto.MypageDTO;
import com.blockChain.repository.GalleryArticleRepoCustom;
import com.blockChain.repository.MemberRepoCustom;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GalleryArticleRepoImpl implements GalleryArticleRepoCustom{
	private final JPAQueryFactory queryFactory;
	
	@Override
	public Optional<List<GalleryArticleDTO>> galleryArticleMain(){
		QMember qm = QMember.member;
		QMember_Gall_Like pql = QMember_Gall_Like.member_Gall_Like;
		QGalleryArticle qg =  QGalleryArticle.galleryArticle;
		return Optional.ofNullable(queryFactory.select(Projections.constructor(GalleryArticleDTO.class
				,Projections.constructor(
						MemberDTO.class
						, qm.memberNo
						, qm.memberNick)
				, qg.galleryArticleNo
				, qg.galleryArticleContent
				, JPAExpressions.select(pql.count())
					.from(pql)
					.where(pql.toMember.memberNo.eq(qm.memberNo))
				))
				.from(qg)
				.join(qm).on(qg.member.memberNo.eq(qm.memberNo))
				.orderBy(qg.galleryArticleNo.desc())
				.fetch());
	}
	@Override
	public Optional<Member_Gall_Like> checkLike(Long galleryPk, Long memberPk) {
		QMember_Gall_Like pql = QMember_Gall_Like.member_Gall_Like;
		return Optional.ofNullable(queryFactory
				.selectFrom(pql)
				.where(pql.fromMember.memberNo.eq(memberPk)
						.and(pql.toMember.memberNo.eq(galleryPk)))
				.fetchFirst());
	}
	@Override
	public Long likeCount(Long galleryPk) {
		QMember_Gall_Like pql = QMember_Gall_Like.member_Gall_Like;
		
		return queryFactory.selectFrom(pql)
				.where(pql.toMember.memberNo.eq(galleryPk)).fetchCount();
	}
}
