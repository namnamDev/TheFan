package com.blockChain.repository.Impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.Celeb_Like;
import com.blockChain.domain.QCeleb_Like;
import com.blockChain.repository.Celeb_LikeRepoCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Celeb_LikeRepoImpl implements Celeb_LikeRepoCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Optional<Celeb_Like> sltByMember(Long memberNo){
		QCeleb_Like qcl = QCeleb_Like.celeb_Like;
		return Optional.ofNullable(queryFactory.selectFrom(qcl).where(qcl.member.memberNo.eq(memberNo)).fetchFirst());
	}
}
