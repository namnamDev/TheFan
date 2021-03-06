package com.blockChain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blockChain.config.SecurityUtil;
import com.blockChain.domain.Auction;
import com.blockChain.domain.Auction_Like;
import com.blockChain.domain.Auction_Order;
import com.blockChain.domain.Member;
import com.blockChain.domain.Sales;
import com.blockChain.domain.Sales_Like;
import com.blockChain.domain.Token;
import com.blockChain.domain.Token_Owner;
import com.blockChain.dto.AuctionDTO;
import com.blockChain.dto.AuctionGroupListDTO;
import com.blockChain.dto.AuctionOrderDTO;
import com.blockChain.dto.AuctionRegistedByMemberDTO;
import com.blockChain.dto.GalleryCardDTO;
import com.blockChain.repository.AuctionRepo;
import com.blockChain.repository.Auction_LikeRepo;
import com.blockChain.repository.Auction_OrderRepo;
import com.blockChain.repository.MemberRepo;
import com.blockChain.repository.TokenRepo;
import com.blockChain.repository.Token_OwnerRepo;
import com.blockChain.repository.WalletRepo;

@Service
@Transactional
public class AuctionSvcImpl implements AuctionSvcInter{
	@Autowired
	private AuctionRepo auctionRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	@Autowired
	private TokenRepo tokenRepo;
	@Autowired
	private Token_OwnerRepo toRepo;
	@Autowired
	private Auction_LikeRepo alRepo;
	@Autowired
	private Auction_OrderRepo aoRepo;
	@Autowired
	private WalletRepo walletRepo;
	@Override
	public Map<String,Object> sltAuctionByGroup(Long groupNo){
		Map<String, Object> res = new HashMap<String,Object>();
		Optional<List<AuctionGroupListDTO>> auctiongroupList = auctionRepo.sltAuctionByGroup(groupNo);
		res.put("res", auctiongroupList);
		return res;
	}
	@Override
	public Map<String,Object> insertAuction(Map<String,Object> req){
		Map<String, Object> res = new HashMap<String,Object>();
	    Long nowLoginMemberNo=0L;
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		try{
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			Long tokenNo = Long.valueOf((Integer)req.get("tokenNo"));
			
			Token token = tokenRepo.findById(tokenNo).orElseThrow(() -> new IllegalStateException("????????? ???????????? ????????????."));
			Token_Owner tokenOwner = toRepo.sltByTokenMember(member.getMemberNo(), token.getTokenNo()).orElseThrow(() -> new IllegalStateException("??????????????? ????????? ?????? ????????????."));
			auctionRepo.checkAuctionToken(token.getTokenNo()).ifPresent(m->{throw new IllegalStateException("??????????????? ?????? ????????????????????????.");});
			walletRepo.findByWallet(member.getMemberNo()).orElseThrow(()->new IllegalStateException("????????? ?????? ??????????????????"));
			
			System.out.println(auctionRepo.checkAuctionToken(token.getTokenNo()));
			Auction auction = new Auction();
			String auctionTitle = (String)req.get("auctionTitle");
			String auctionDetail = (String)req.get("auctionDetail");
			Long immeprice = Long.valueOf((Integer)req.get("price"));
			auction.setMember(member);
			auction.setToken(token);
			auction.setAuctionImmeprice(immeprice);
			auction.setAuctionName(auctionTitle);
			auction.setAuctionDetail(auctionDetail);
			auction.setAuctionStart(LocalDateTime.now());
			auction.setAuctionDeadline(LocalDateTime.now().plusDays(3));
			auction.setAuctionState("SELL");
			tokenOwner.setOnAuction(1L);
			toRepo.save(tokenOwner);
			auctionRepo.save(auction);
			res.put("success", true);
			res.put("msg", "????????????");
			
			
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		return res;
	}
	@Override
	public Map<String,Object> sltLikeCount(Long auctionPk){
		Map<String, Object> res = new HashMap<String,Object>();
		Long nowLoginMemberNo=0L;

		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		
		
		Optional<Member> member = memberRepo.findById(nowLoginMemberNo);
		if(member.isEmpty()) {
			res.put("islike", false);
		}else {
			Optional<Auction_Like> checkLike = alRepo.checkLike(auctionPk, member.get().getMemberNo());
			if(checkLike.isEmpty()) {
				res.put("islike", false);
			}else {
				res.put("islike", true);
			}
		}
		res.put("peoplelike", alRepo.likeCount(auctionPk));
		
	return res;
	}
	@Override
	public Map<String,Object>insertLike(Map<String,Object> req){
		Map<String, Object> res = new HashMap<String,Object>();
		Long nowLoginMemberNo=0L;// ?????? 0 
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		
		try {
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			Long auctionPk = Long.valueOf((Integer)req.get("auctionNo"));
			Auction auction = auctionRepo.findById(auctionPk).orElseThrow(() -> new IllegalStateException("?????? ???????????? ???????????? ????????????"));;
			Optional<Auction_Like> checkLike = alRepo.checkLike(auctionPk, member.getMemberNo());
			Auction_Like al = new Auction_Like();
			al.setMember(member);
			al.setAuction(auction);
			if (checkLike.isEmpty()){
				res.put("success", true);
				res.put("msg", "????????? ??????");
				alRepo.save(al);
			}else {
				res.put("success", true);
				res.put("msg", "??????????????? ??????");
				alRepo.delete(al);
			}
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		return res;
	}
	@Override
	public Map<String,Object>sltOneByNo(Long auctionNo){
		Map<String, Object> res = new HashMap<String,Object>();
		try {
		AuctionGroupListDTO auction = auctionRepo.sltOneByNo(auctionNo).orElseThrow(() -> new IllegalStateException("?????? ???????????? ???????????? ????????????"));
		res.put("member", auction.getMember());
		res.put("card", auction.getCard());
		res.put("auction", auction.getAuction());
		res.put("sellerWallet", walletRepo.findByMemberNoDTO(auction.getMember().getMemberNo()));
		return res;
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		}
	@Override
	public Map<String,Object>beforeInsertAuction(){
		Map<String, Object> res = new HashMap<String,Object>();
		Long nowLoginMemberNo=0L;// ?????? 0 
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		try {
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			List<GalleryCardDTO> aa = memberRepo.getCanRegiAuction(member.getMemberNo());
			
			System.out.println(aa);
			res.put("res",aa);
		}catch(IllegalStateException e){
				res.put("success", false);
				res.put("msg", e.getMessage());
		}
		return res;
	}
	@Override
	public Map<String,Object>auctionRegistedByMember(){
		Map<String, Object> res = new HashMap<String,Object>();
		Long nowLoginMemberNo=0L;// ?????? 0 
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		try {
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			Optional<List<AuctionRegistedByMemberDTO>> AuctionRegistedByMember = auctionRepo.auctionRegistedByMember(member.getMemberNo());
			res.put("res", AuctionRegistedByMember);
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;

	}
	@Override
	public Map<String,Object>sltMultiAuctionOrderByMember(){
		Map<String, Object> res = new HashMap<String,Object>();
		Long nowLoginMemberNo=0L;// ?????? 0 
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		try {
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			Optional<List<AuctionOrderDTO>> AuctionOrderByMember = aoRepo.sltMultiAuctionOrderByMember(member.getMemberNo());
			res.put("res", AuctionOrderByMember);
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;

	}
	
	@Override
	public Map<String,Object>buyAuction(Map<String,Object> req){
		Map<String, Object> res = new HashMap<String,Object>();
		Long nowLoginMemberNo=0L;// ?????? 0 
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		try {
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			//????????????
			//?????? ????????? SELL?????? ??????order??? ?????????
			//????????????
			Long reqNo = Long.valueOf((Integer) req.get("auctionNo"));
			Auction auction =auctionRepo.findById(reqNo).orElseThrow(() -> new IllegalStateException("?????? ???????????? ???????????? ????????????"));
			Token token = tokenRepo.findById(auction.getToken().getTokenNo()).orElseThrow(() -> new IllegalStateException("?????? ????????? ???????????? ????????????"));
			if (!auction.getAuctionState().equals("SELL")) {IllegalStateException e = new IllegalStateException("?????? ????????? ???????????????.");throw e;};
			
//			aoRepo.sltByAuctionNo(auction.getAuctionNo()).ifPresent(m->{throw new IllegalStateException("?????? ????????? ???????????????.");});
			auction.setAuctionState("SOLD");
			auctionRepo.save(auction);
			//????????????
			Auction_Order ao = new Auction_Order();
			ao.setAuction(auction);
			ao.setMember(member);
			ao.setAuctionOrderDate(LocalDateTime.now());
			aoRepo.save(ao);
			System.out.println(auction);
			System.out.println(token);
			Token_Owner to = toRepo.sltToken(token.getTokenNo()).orElseThrow(() -> new IllegalStateException("?????? ????????? ???????????? ????????????."));
			toRepo.delete(to);
			Token_Owner newTo = new Token_Owner();
			
			newTo.setMember(member);
			newTo.setToken(token);
			newTo.setOnAuction(0L);
			newTo.setOwnDate(LocalDateTime.now());
			toRepo.save(newTo); 
			res.put("success", true);
			res.put("msg", "????????? ?????????????????????.");
			//?????? sold??????
			//??????order??? insert
			//?????? ?????? ??????
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	@Override
	public Map<String,Object>editAuction(Map<String,Object> req){
		Map<String, Object> res = new HashMap<String,Object>();
		Long nowLoginMemberNo=0L;// ?????? 0 
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		
		
		try {
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			Long auctionNo = Long.valueOf((Integer)req.get("auctionNo"));
			String auctionDetail = (String)req.get("auctionDetail");
			String auctionTitle = (String)req.get("auctionTitle");
			Long price = Long.valueOf((Integer)req.get("price"));
			Auction auction= auctionRepo.findById(auctionNo).orElseThrow(() -> new IllegalStateException("?????? ???????????? ???????????? ????????????."));
			if(member.getMemberNo()!=auction.getMember().getMemberNo()) {throw new IllegalStateException("????????? ????????? ?????? ????????? ??? ????????????.");};
			if(!auction.getAuctionState().equals("SELL")){throw new IllegalStateException("?????? ???????????? ???????????? ?????? ???????????????.");};
			auction.setAuctionName(auctionTitle);
			auction.setAuctionDetail(auctionDetail);
			auction.setAuctionImmeprice(price);
			auctionRepo.save(auction);
			res.put("success", true);
			res.put("msg", "??????????????? ?????????????????????.");
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	@Override
	public Map<String,Object>deleteAuction(Map<String,Object> req){
		Map<String, Object> res = new HashMap<String,Object>();
		Long nowLoginMemberNo=0L;// ?????? 0 
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		
		
		try {
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			Long auctionNo = Long.valueOf((Integer)req.get("auctionNo"));
			Auction auction= auctionRepo.findById(auctionNo).orElseThrow(() -> new IllegalStateException("?????? ???????????? ???????????? ????????????."));
			if(member.getMemberNo()!=auction.getMember().getMemberNo()) {throw new IllegalStateException("????????? ????????? ?????? ????????? ??? ????????????.");};
			if(!auction.getAuctionState().equals("SELL")){throw new IllegalStateException("?????? ???????????? ???????????? ????????? ??? ????????????.");};
			auction.setAuctionState("DEL");
			Token_Owner to = toRepo.sltByTokenMember(member.getMemberNo(), auction.getToken().getTokenNo()).orElseThrow(() -> new IllegalStateException("?????? ????????? ???????????? ?????? ????????????."));;
			to.setOnAuction(0L);
			toRepo.save(to);
			auctionRepo.save(auction);
			res.put("success", true);
			res.put("msg", "??????????????? ?????????????????????.");
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
}
