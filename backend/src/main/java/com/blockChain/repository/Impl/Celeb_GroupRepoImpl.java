package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.Celeb_Group;
import com.blockChain.domain.QCeleb_Group;
import com.blockChain.dto.CelebDTO;
import com.blockChain.dto.CelebGroupListDTO;
import com.blockChain.repository.Celeb_GroupRepoCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Celeb_GroupRepoImpl implements Celeb_GroupRepoCustom{
	
	private final JPAQueryFactory queryFactory;
	QCeleb_Group Qcg = QCeleb_Group.celeb_Group;
	@Override
	public Optional<Celeb_Group> sltByCelebGroupNM(String NM){
		Celeb_Group res = queryFactory.selectFrom(Qcg).where(Qcg.groupNm.eq(NM)).fetchOne();
//		return Optional.of(res);
		return Optional.ofNullable(res);
	}
	@Override
	public Optional<List<CelebGroupListDTO>> celebGroupList(){
		return Optional.ofNullable(queryFactory.select(Projections.constructor(CelebGroupListDTO.class
				,Qcg.groupNo
				,Qcg.groupNm
				))
				.from(Qcg)
				.orderBy(Qcg.groupNo.asc())
				.fetch());
	}
}
