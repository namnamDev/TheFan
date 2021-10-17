package com.blockChain.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blockChain.service.WalletSvcInter;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

	@Autowired
	private WalletSvcInter ws;
	
	//지갑 생성
	@PostMapping
	public Map<String, Object> create(@RequestBody Map<String, Object> req) {
		return ws.createWallet(req);
	}
	
	//지갑 조회
	@GetMapping
	public Map<String, Object> find(){
		return ws.findWallet();
	}
	
	//이더 충전
	@GetMapping("/recharge")
	public Map<String, Object> recharge(@RequestBody Map<String, Object> req){
		return ws.recharge(req);
	}
}
