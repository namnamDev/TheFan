package com.blockChain.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blockChain.config.SecurityUtil;
import com.blockChain.domain.Member;
import com.blockChain.domain.Product;
import com.blockChain.domain.Reply;
import com.blockChain.domain.Sales;
import com.blockChain.domain.Sales_Like;
import com.blockChain.domain.Sales_Order;
import com.blockChain.domain.Sold_Bundle_Inside;
import com.blockChain.domain.Token;
import com.blockChain.domain.Token_Owner;
import com.blockChain.dto.CardAddCountDTO;
import com.blockChain.dto.CardDTO;
import com.blockChain.dto.CardGenerateDTO;
import com.blockChain.dto.MypageDTO;
import com.blockChain.dto.SalesDTO;
import com.blockChain.repository.MemberRepo;
import com.blockChain.repository.ProductRepo;
import com.blockChain.repository.ReplyRepo;
import com.blockChain.repository.SalesRepo;
import com.blockChain.repository.Sales_LikeRepo;
import com.blockChain.repository.Sales_OrderRepo;
import com.blockChain.repository.Sold_Bundle_InsideRepo;
import com.blockChain.repository.TokenRepo;
import com.blockChain.repository.Token_OwnerRepo;
import com.blockChain.repository.Impl.ProductRepoImpl;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
@Transactional
public class SalesSvcImpl implements SalesSvcInter{
	private final String MW = "MW";
	private final String group = "GR";
	private final String year = "YR";
	private final String celeb = "CL";
	@Autowired
	private SalesRepo salesRepo;
	@Autowired
	private ReplyRepo replyRepo;
	@Autowired
	private MemberRepo memberRepo;
	@Autowired
	private Sales_LikeRepo slRepo;
	@Autowired
	private TokenRepo tokenRepo;
	@Autowired
	private Sales_OrderRepo soRepo;
	@Autowired
	private Sold_Bundle_InsideRepo sbiRepo;
	@Autowired
	private Token_OwnerRepo toRepo;
	@Autowired
	private ProductRepo productRepo;
	@Override
	public Map<String,Object>sltSalesByMW(){
		Map<String, Object> res = new HashMap<String,Object>();
		String[] order = {MW,group,celeb,year};
		List<SalesDTO> dto = new ArrayList<SalesDTO>();
		for (int i = 0 ; i < order.length;i++){
			Optional<List<SalesDTO>>salesDTOList = salesRepo.slyBySalesDiv(order[i]);
			for (SalesDTO g:salesDTOList.get()) {
				dto.add(g);
				}
		}
		res.put("res", dto);
		return res;
	}
	
	@Override
	public Map<String,Object>sltReviewList(long cardpackPK){
		Map<String, Object> res = new HashMap<String,Object>();
		System.out.println("here2");
		res.put("res", replyRepo.sltReviewList(cardpackPK));
		return res;
	}
	
	@Override
	public Map<String,Object>insertReview(long cardpackPK,Map<String, Object> req){
		Map<String, Object> res = new HashMap<String,Object>();		
		try {
			Member member = memberRepo.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			Optional<Sales> sales = Optional.ofNullable(salesRepo.findById(cardpackPK).orElseThrow(()->new IllegalStateException("???????????? ?????? ?????????????????????.")));
			Reply reply = new Reply();
			reply.setMember(member);
			reply.setReplyContent((String)req.get("reviewContent"));
			reply.setSales(sales.get());
			reply.setReplyDate(LocalDateTime.now());
			Reply saved = replyRepo.save(reply);
			res.put("success", true);
			res.put("msg", "??????????????? ?????????????????????.");
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
			
		}
		return res;
	}
	@Override
	public Map<String,Object>sltLikeCount(long cardpackPK){
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
				Optional<Sales_Like> checkLike = slRepo.checkLike(cardpackPK, member.get().getMemberNo());
				if(checkLike.isEmpty()) {
					res.put("islike", false);
				}else {
					res.put("islike", true);
				}
			}
			res.put("peoplelike", slRepo.likeCount(cardpackPK));
			
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
			Long cardpackPK = Long.valueOf((Integer)req.get("cardpackPK"));
			Sales sales = salesRepo.findById(cardpackPK).orElseThrow(() -> new IllegalStateException("?????? ???????????? ???????????? ????????????"));;
			Optional<Sales_Like> checkLike = slRepo.checkLike(cardpackPK, member.getMemberNo());
			Sales_Like sl = new Sales_Like();
			sl.setMember(member);
			sl.setSales(sales);
			if (checkLike.isEmpty()){
				res.put("success", true);
				res.put("msg", "????????? ??????");
				slRepo.save(sl);
			}else {
				res.put("success", true);
				res.put("msg", "??????????????? ??????");
				slRepo.delete(sl);
			}
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		return res;
	}
	@Override
	public Map<String,Object>gainCardList(Long cardpackPK){
		Map<String, Object> res = new HashMap<String,Object>();
//		Optional<List<CardGenerateDTO>> cardList = salesRepo.gainCardList(cardpackPK);
		Optional<List<CardAddCountDTO>> cardList = salesRepo.gainCardListAddNum(cardpackPK);
		List<CardAddCountDTO> cardListGet = cardList.get();
		for (int i = 0; i<cardListGet.size(); i++) {
			Long no = salesRepo.countLeftCard(cardListGet.get(i).getCardNo());
		}
		res.put("res", cardList);
		return res;
	}
	@Override
	public Map<String,Object>buyCardPack(Long cardpackPk){
		Map<String, Object> res = new HashMap<String,Object>();
		//????????????
	    Long nowLoginMemberNo=0L;
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		//????????????
//		List<Product>cardList = salesRepo.cardListByPack(cardpackPk);
		try{
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			Sales sales = salesRepo.findById(cardpackPk).orElseThrow(() -> new IllegalStateException("???????????? ?????? ??????????????????."));
			
			//???????????? ??????!
			List<Token>tokens = tokenRepo.sltMultiBySales(sales).orElseThrow(() -> new IllegalStateException("?????? ????????? ?????????????????????."));
			int sizes = tokens.size();
			if(sizes>0) {}
			
			if(sizes == 0 ) {
				res.put("success", false);
				res.put("msg", "?????? ????????? ????????????.");
			}
			Collections.shuffle(tokens);
			int CardpackSize = 5;
			//3???????????? 4?????? ????????? 3 - 3/5*1  
			if (sizes < CardpackSize) {
				CardpackSize= sizes;
			}
			List<Token> chooseTokens = tokens.subList(0, CardpackSize); //?????? 5??? ?????????
			Sales_Order salesOrder = new Sales_Order();
			salesOrder.setMember(member);
			salesOrder.setSales(sales);
			
			List<CardDTO> resCardList = new ArrayList<CardDTO>();
			Sales_Order savedOrder = soRepo.save(salesOrder); // ???????????? ??????
			for (int i =0; i<chooseTokens.size();i++) {
				Token tempToken = chooseTokens.get(i);
				Sold_Bundle_Inside sbi = new Sold_Bundle_Inside();
				sbi.setSalesOrder(savedOrder);
				sbi.setToken(tempToken);
				sbiRepo.save(sbi); //?????? ???????????? ??????
				Token_Owner to = new Token_Owner();
				to.setMember(member);
				to.setToken(tempToken);
				to.setOwnDate(LocalDateTime.now());
				to.setOnAuction(0L);
				Token_Owner savedTo = toRepo.save(to); // ?????? ????????? ??????
				//????????? ?????? ????????????
				resCardList.add(productRepo.sltByTokenNoAddToken(savedTo.getToken()));
//				resTokens.add(savedTo.getToken());
			}
			res.put("cardList", resCardList);
			System.out.println(tokens.size());
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		
		//?????? ??????????????? ???????????? 5??? ??????
		//?????? ???????????? ???????????? ????????? ???????????? cardDTO??? ????????????
		//cardDTO res??? ?????? ??????
		
		
		return res;
	}
}
