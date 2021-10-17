package com.blockChain.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blockChain.service.AuctionSvcInter;

@RestController
@RequestMapping("/api/auction")
public class AuctionController {
	@Autowired
	AuctionSvcInter auctionSvc;
	@GetMapping("/{groupNo}/list")
	public Map<String, Object> firstData(
			@PathVariable(name="groupNo")
			Long groupNo){
		return auctionSvc.sltAuctionByGroup(groupNo);
		
	}
	@PostMapping("/insert")
	public Map<String, Object> insertAuction(
			@RequestBody Map<String, Object> req
			){
		return auctionSvc.insertAuction(req);
	}
	@GetMapping("/likecheck/{auctionPk}")
	public Map<String,Object> sltLikeCount(@PathVariable(name="auctionPk") Long auctionPk){
		return auctionSvc.sltLikeCount(auctionPk);
	}
	@PostMapping("/like")
	public Map<String,Object>insertLike(@RequestBody Map<String, Object> req){
		return auctionSvc.insertLike(req);
		
	}
	@GetMapping("/{auctionNo}/detail")
	public Map<String, Object> auctionDetail(
			@PathVariable(name="auctionNo")
			Long auctionNo){
		return auctionSvc.sltOneByNo(auctionNo);
		
	}
	@GetMapping("/beforeInsert")
	public Map<String, Object> insertAuction(){
		return auctionSvc.beforeInsertAuction();
	}
	@PostMapping("/buy")
	public Map<String, Object> buyAuction(@RequestBody Map<String, Object> req){
		return auctionSvc.buyAuction(req);
	}
	@PutMapping("/edit")
	public Map<String, Object> editAuction(@RequestBody Map<String, Object> req){
		return auctionSvc.editAuction(req);
	}
	@DeleteMapping("/delete")
	public Map<String, Object> deleteAuction(@RequestBody Map<String, Object> req){
		return auctionSvc.deleteAuction(req);
	}
}
