package com.blockChain.service;

import java.util.Map;

public interface AuctionSvcInter {

	Map<String, Object> sltAuctionByGroup(Long groupNo);

	Map<String, Object> insertAuction(Map<String, Object> req);

	Map<String, Object> sltLikeCount(Long auctionPk);

	Map<String, Object> insertLike(Map<String, Object> req);

	Map<String, Object> sltOneByNo(Long auctionNo);

	Map<String, Object> beforeInsertAuction();

	Map<String, Object> auctionRegistedByMember();

	Map<String, Object> sltMultiAuctionOrderByMember();

	Map<String, Object> buyAuction(Map<String, Object> req);

	Map<String, Object> editAuction(Map<String, Object> req);

	Map<String, Object> deleteAuction(Map<String, Object> req);

}
