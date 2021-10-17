package com.blockChain.service;

import java.util.Map;

public interface SalesSvcInter {

	Map<String, Object> sltSalesByMW();

	Map<String, Object> sltReviewList(long cardpackPK);

	Map<String, Object> insertReview(long cardpackPK, Map<String, Object> req);

	Map<String, Object> sltLikeCount(long cardpackPK);

	Map<String, Object> insertLike(Map<String, Object> req);

	Map<String, Object> gainCardList(Long cardpackPK);

	Map<String, Object> buyCardPack(Long cardpackPK);

}
