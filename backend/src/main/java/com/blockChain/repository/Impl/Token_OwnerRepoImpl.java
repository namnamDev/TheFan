package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.Member;
import com.blockChain.domain.QToken_Owner;
import com.blockChain.domain.Token;
import com.blockChain.domain.Token_Owner;
import com.blockChain.repository.Token_OwnerRepoCustom;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Token_OwnerRepoImpl implements Token_OwnerRepoCustom{
	private final JPAQueryFactory queryFactory;
	
	//토큰을 가지고 있는 회원중복제거후 조회 
	@Override
	public Optional<List<Member>> sltMultiOwner(){
		QToken_Owner qto = QToken_Owner.token_Owner;
		
		return Optional.ofNullable(queryFactory.select(qto.member).from(qto).distinct().fetch());
	}
	//회원이 갖고있는 토큰 조회
	@Override
	public Optional<List<Token_Owner>> sltMultiTokenByMember(Long memberNo){
		QToken_Owner qto = QToken_Owner.token_Owner;
		return  Optional.ofNullable(queryFactory.selectFrom(qto).where(qto.member.memberNo.eq(memberNo)).fetch());
	}
	@Override
	public Optional<Token_Owner> sltToken(Long tokenNo){
		QToken_Owner qto = QToken_Owner.token_Owner;
		return  Optional.ofNullable(queryFactory.selectFrom(qto).where(qto.token.tokenNo.eq(tokenNo)).fetchOne());
	}
	
	@Override
	public Optional<Token_Owner> sltByTokenMember(Long memberNo,Long tokenNo){
		QToken_Owner qto = QToken_Owner.token_Owner;
		return  Optional.ofNullable(queryFactory
				.selectFrom(qto)
				.where(qto.member.memberNo.eq(memberNo).and(qto.token.tokenNo.eq(tokenNo)))
				.fetchOne());
	}
//	@Override
//	public void updateToken(Token_Owner to) {
//		queryFactory.selectFrom()
//	}
	
}
