package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.domain.Sales_Like;
import com.blockChain.dto.SalesDTO;
import com.querydsl.core.types.dsl.BooleanExpression;

public interface Sales_LikeRepoCustom {

	Optional<Sales_Like> checkLike(Long salesPk, Long memberPk);

	Long likeCount(Long salesPk);

	Optional<List<SalesDTO>> likeList(Long memberPk);

	Long CountLikeByMember(Long memberNo);

}
