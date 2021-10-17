package com.blockChain.service;

import java.util.Map;

public interface AdminSvcInter {

	Map<String, Object> insertCeleb();

	Map<String, Object> insertCelebGroup();

	Map<String, Object> totalData();

	Map<String, Object> insertMember();

	Map<String, Object> insertMemberGrade();

	Map<String, Object> insertCelebLike();

	Map<String, Object> insertToken();

	Map<String, Object> insertTokenOwner();

	Map<String, Object> insertProduct();

	Map<String, Object> insertSales();

	Map<String, Object> insertProductGrade();

	Map<String, Object> insertProductMedia();

	Map<String, Object> insertProductToken();

	Map<String, Object> insertSalesProduct();

	Map<String, Object> insertSL();

	Map<String, Object> reply();

	Map<String, Object> insertAuction();

	Map<String, Object> insertBid();

	Map<String, Object> buyCardPack();

}
