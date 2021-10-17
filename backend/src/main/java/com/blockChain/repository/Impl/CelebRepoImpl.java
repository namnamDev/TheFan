package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.Celeb;
import com.blockChain.domain.QCeleb;
import com.blockChain.domain.QCeleb_Group;
import com.blockChain.dto.ArtistListDTO;
import com.blockChain.dto.CelebDTO;
import com.blockChain.dto.GroupDTO;
import com.blockChain.repository.CelebRepoCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CelebRepoImpl implements CelebRepoCustom{
	private final JPAQueryFactory queryFactory;
	QCeleb qcg = QCeleb.celeb;
	QCeleb_Group qg = QCeleb_Group.celeb_Group;
	@Override
	public Optional<Celeb> sltCeleb(String NM){
		
		return Optional.ofNullable(queryFactory.selectFrom(qcg).where(qcg.celebNm.eq(NM)).fetchFirst());
		
	}
	@Override
	public Optional<List<Celeb>> sltCelebByGroup(Long GroupNo){
		return Optional.ofNullable(queryFactory.selectFrom(qcg).where(qcg.group.groupNo.eq(GroupNo)).fetch());
	}
	
	@Override
	public Optional<List<CelebDTO>> sltCelebDTOByGroup(Long GroupNo){
		return Optional.ofNullable(queryFactory.select(Projections.constructor(CelebDTO.class
				, qcg.celebNo
				, qcg.celebNm
				, qcg.celebMw
				, Projections.constructor(GroupDTO.class
						,qcg.group.groupNo
						,qcg.group.groupNm
						,qcg.group.groupNofp)
				, qcg.celebDebut
				, qcg.celebRetire
				)).from(qcg)
				.where(qcg.group.groupNo.eq(GroupNo))
				.fetch());
	}
	@Override
	public Optional<List<ArtistListDTO>> sltArtistListDTO(){
		return Optional.ofNullable(queryFactory.select(Projections.constructor(ArtistListDTO.class
				,qcg.celebNo
				,qcg.celebNm
				,qcg.group.groupNo))
				.from(qcg)
				.orderBy(qcg.group.groupNm.asc())
				.fetch());
	}
//	@Override
//	public Optional<List<CelebDTO>> sltCelebDTOByGroup(Long GroupNo){
//		return Optional.ofNullable(queryFactory.select(Projections.constructor(CelebDTO.class
//				, qcg.celebNo
//				, qcg.celebNm
//				, qcg.celebMw
//				, Projections.constructor(GroupDTO.class
//						,qcg.group.groupNo
//						,qcg.group.groupNm
//						,qcg.group.groupNofp)
//				, qcg.celebDebut
//				, qcg.celebRetire
//				)).from(qcg)
//				.where(qcg.group.groupNo.eq(GroupNo))
//				.fetch());
//	}
}
