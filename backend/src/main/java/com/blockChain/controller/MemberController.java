package com.blockChain.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blockChain.domain.Member;
import com.blockChain.dto.LoginTokenDTO;
import com.blockChain.dto.UpdateMemberDTO;
import com.blockChain.service.AuctionSvcInter;
import com.blockChain.service.MemberSvcInter;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	@Autowired
	private MemberSvcInter ms;
	@Autowired
	private AuctionSvcInter auctionSvc;
	@PostMapping("/signup")
	public Map<String,Object> signup(@RequestBody Map<String, Object> req){
		System.out.println(req);
		return ms.signup(req);
	}
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Member member) {
		Map<String, Object> ret = new HashMap<>();
		LoginTokenDTO token;
		try {
			token = ms.login(member);
		} catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}

		ret.put("success", "True");
		ret.put("msg", "로그인 성공");
		ret.put("token", token);

		return ret;
	}
	
	@PostMapping("/checkId")
	public Map<String, Object>checkId(@RequestBody  Map<String, Object> req){
		return ms.checkId(req);
		
	}
	
	@PostMapping("/checkNick")
	public Map<String, Object>checkNick(@RequestBody  Map<String, Object> req){
		return ms.checkNick(req);
		
	}
	
	@PostMapping("/checkEmail")
	public Map<String, Object>checkEmail(@RequestBody  Map<String, Object> req){
		return ms.checkEmail(req);
		
	}
	
	@PutMapping("/update")
	public Map<String,Object>updateMember(@RequestBody Map<String, Object> req){
		return ms.updateMember(req);
	}
	@GetMapping("/mypage")
	public Map<String, Object>myPage(){
		return ms.myPage();
	}
	@GetMapping("/order")
	public Map<String, Object>orderList(){
		
		return ms.orderList();
	}
	@GetMapping("/salesLike")
	public Map<String, Object>likeSaleList(){
		
		return ms.salesLikeList();
	}
	
	@GetMapping("/auctionLike")
	public Map<String, Object>likeAuctionList(){
		
		return ms.AuctionLikeList();
	}
	@GetMapping("/registedAuctionList")
	public Map<String, Object>registedAuctionList(){
		
		return auctionSvc.auctionRegistedByMember();
	}
	
	@GetMapping("/auctionOrderList")
	public Map<String, Object>sltMultiAuctionOrderByMember(){
		
		return auctionSvc.sltMultiAuctionOrderByMember();
	}
	
	
}	


