package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.Member;
import com.blockChain.domain.QAuction;
import com.blockChain.domain.QCeleb_Like;
import com.blockChain.domain.QMember;
import com.blockChain.domain.QMember_Gall_Like;
import com.blockChain.domain.QMember_Grade;
import com.blockChain.domain.QProduct;
import com.blockChain.domain.QProduct_Media;
import com.blockChain.domain.QProduct_Token;
import com.blockChain.domain.QToken;
import com.blockChain.domain.QToken_Owner;
import com.blockChain.dto.GalleryArticleDTO;
import com.blockChain.dto.GalleryCardDTO;
import com.blockChain.dto.MemberDTO;
import com.blockChain.dto.MypageDTO;
import com.blockChain.dto.TokenDTO;
import com.blockChain.repository.MemberRepoCustom;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.group.GroupBy;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepoImpl implements MemberRepoCustom{
	private final JPAQueryFactory queryFactory;
	
	//아이디로 조회
	@Override
	public Optional<Member> checkId(String id){
		QMember qm = QMember.member;
		return Optional.ofNullable(queryFactory.selectFrom(qm).where(qm.memberId.eq(id)).fetchFirst());
	}
	//이메일로 조회
	@Override
	public Optional<Member> checkEmail(String email){
		QMember qm = QMember.member;
		return Optional.ofNullable(queryFactory.selectFrom(qm).where(qm.memberEmail.eq(email)).fetchFirst());
	}
	//닉네임으로 조회
	@Override
	public Optional<Member> checkNick(String nick){
		QMember qm = QMember.member;
		return Optional.ofNullable(queryFactory.selectFrom(qm).where(qm.memberNick.eq(nick)).fetchFirst());
	}
	
	@Override
	public MypageDTO myPage(Long memberNo){
		QCeleb_Like qcl = QCeleb_Like.celeb_Like;
		QMember qm = QMember.member;
		QMember_Grade qmg = QMember_Grade.member_Grade;
		return queryFactory.select(Projections.constructor(MypageDTO.class
				, qm.memberNo
				, qm.memberId
				, qm.memberEmail
				, qm.memberNick
				, ExpressionUtils.as(
						JPAExpressions
						.select(qcl.celeb.celebNo)
						.from(qcl)
						.where(qcl.member.memberNo.eq(memberNo))
				, "celebNo")
				, qm.memberGrade.memberGradeNo
				, qm.memberGrade.memberGradeNm
				))
				.from(qm)
				.where(qm.memberNo.eq(memberNo))
				.fetchOne();
				
				
	}
	@Override
	public List<GalleryCardDTO> galleryList(Long memberNo, Long arraydiv1, Long celebPk, Long arraydiv2){
		QMember qm = QMember.member;
		QProduct_Token qpt = QProduct_Token.product_Token;
		QProduct_Media qpm= QProduct_Media.product_Media;
		QProduct qp = QProduct.product;
		QToken qt = QToken.token;
		QToken_Owner qto= QToken_Owner.token_Owner;
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qto.member.memberNo.eq(memberNo));
		if(arraydiv1 == 0L) {//전체불러오기
			
		}
		if(arraydiv1 == 1L) { //그룹별
			builder.and(qp.celeb.group.groupNo.eq(celebPk));
		}
		if(arraydiv1 == 2L) {//멤버별
			builder.and(qp.celeb.celebNo.eq(celebPk));
		}
//		Path<Object> fieldPath = Expressions.path(Object.class, QPerson.person, fieldName);
		
		
		List<GalleryCardDTO> aa = queryFactory.from(qto)
		.join(qt).on(qt.tokenNo.eq(qto.token.tokenNo))
		.join(qpt).on(qpt.token.tokenNo.eq(qt.tokenNo))
		.join(qp).on(qpt.product.productNo.eq(qp.productNo))
		.join(qpm).on(qp.productNo.eq(qpm.product.productNo))
		.where(builder)
		.transform(GroupBy.groupBy(qp.productNo)
				.list(Projections.constructor(GalleryCardDTO.class
				, qp.productNo
				, qp.productNm
				, qpm.productMediaAdres
				, qp.productGrade.productGradeNo
				, qp.productGrade.productGrade
				, GroupBy.list(Projections.constructor(TokenDTO.class
						, qt.tokenNo
						, qt.tokenSeriarlizeNo
						, qto.ownDate
						)
					)
				)));
		
		return aa;
	}
	@Override
	public List<GalleryCardDTO> getCanRegiAuction(Long memberNo){
		QMember qm = QMember.member;
		QProduct_Token qpt = QProduct_Token.product_Token;
		QProduct_Media qpm= QProduct_Media.product_Media;
		QProduct qp = QProduct.product;
		QToken qt = QToken.token;
		QToken_Owner qto= QToken_Owner.token_Owner;
		QAuction qa = QAuction.auction;
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qto.member.memberNo.eq(memberNo));
		builder.and(qto.onAuction.eq(0L)); //내가 가진 토큰들 중에서
//		builder.and(qa.auctionState.notEqualsIgnoreCase("SELL"));
//		builder.and(qa.auctionState.notEqualsIgnoreCase("SELL").or(qa.token.tokenNo.isNull())); // 옥션에 sell아니거나 아예 null이거나
//		builder.and(qa.member.memberNo.eq(memberNo).and(qa.auctionState.notEqualsIgnoreCase("SELL")));
		
		
		List<GalleryCardDTO> aa = queryFactory.from(qto)
		.join(qt).on(qt.tokenNo.eq(qto.token.tokenNo))
		.leftJoin(qa).on(qt.eq(qa.token))
		.join(qpt).on(qpt.token.tokenNo.eq(qt.tokenNo))
		.join(qp).on(qpt.product.productNo.eq(qp.productNo))
		.join(qpm).on(qp.productNo.eq(qpm.product.productNo))
		.where(builder)
		.transform(GroupBy.groupBy(qp.productNo)
				.list(Projections.constructor(GalleryCardDTO.class
				, qp.productNo
				, qp.productNm
				, qpm.productMediaAdres
				, qp.productGrade.productGradeNo
				, qp.productGrade.productGrade
				, GroupBy.list(Projections.constructor(TokenDTO.class
						, qt.tokenNo
						, qt.tokenSeriarlizeNo
						, qto.ownDate
						)
					)
				)));
		System.out.println(qto.member.memberNo.eq(memberNo) + " "+ qto.member.memberNo );
		return aa;
	}
	
	
	
}
