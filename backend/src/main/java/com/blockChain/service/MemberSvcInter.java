package com.blockChain.service;

import java.util.Map;

import com.blockChain.domain.Member;
import com.blockChain.dto.LoginTokenDTO;

public interface MemberSvcInter {


	Map<String, Object> signup(Map<String, Object> req);
	public LoginTokenDTO login(Member member);
	Map<String, Object> checkId(Map<String, Object> req);
	Map<String, Object> checkNick(Map<String, Object> req);
	Map<String, Object> checkEmail(Map<String, Object> req);
	Map<String, Object> updateMember(Map<String, Object> req);
	Map<String, Object> myPage();
	Map<String, Object> orderList();
	Map<String, Object> salesLikeList();
	Map<String, Object> AuctionLikeList();

}
