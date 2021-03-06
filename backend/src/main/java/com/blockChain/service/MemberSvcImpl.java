package com.blockChain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blockChain.config.SecurityUtil;
import com.blockChain.domain.Celeb;
import com.blockChain.domain.Celeb_Like;
import com.blockChain.domain.Member;
import com.blockChain.domain.Member_Grade;
import com.blockChain.domain.RefreshToken;
import com.blockChain.dto.MypageDTO;
import com.blockChain.dto.SalesDTO;
import com.blockChain.dto.SalesOrderDTO;
import com.blockChain.dto.AuctionAddImgDTO;
import com.blockChain.dto.AuctionDTO;
import com.blockChain.dto.LoginTokenDTO;
import com.blockChain.jwt.TokenProvider;
import com.blockChain.repository.AuctionRepo;
import com.blockChain.repository.Auction_LikeRepo;
import com.blockChain.repository.CelebRepo;
import com.blockChain.repository.Celeb_LikeRepo;
import com.blockChain.repository.MemberRepo;
import com.blockChain.repository.Member_GradeRepo;
import com.blockChain.repository.RefreshTokenRepository;
import com.blockChain.repository.Sales_LikeRepo;
import com.blockChain.repository.Sales_OrderRepo;

@Service
@Transactional
public class MemberSvcImpl implements MemberSvcInter{

	@Autowired
	private MemberRepo memberRepo;
	@Autowired
	private Member_GradeRepo mgRepo;
	@Autowired
	private CelebRepo celebRepo;
	@Autowired
	private Celeb_LikeRepo clRepo;
	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;
	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private Sales_OrderRepo soRepo;
	@Autowired
	private Sales_LikeRepo slRepo;
	@Autowired
	private Auction_LikeRepo alRepo;
	@Autowired
	private AuctionRepo auctionRepo;
	@Override
	public Map<String,Object> signup(Map<String, Object> req){
		 Map<String, Object> res = new HashMap<String,Object>();
		 String memberId = (String)req.get("memberId");
		 String memberEmail = (String)req.get("memberEmail");
		 String memberNick = (String)req.get("memberNick");
		 try {
		 memberRepo.checkId(memberId).ifPresent(m ->{throw new IllegalStateException("?????? ???????????? ??????????????????.");});
		 memberRepo.checkEmail(memberEmail).ifPresent(m ->{throw new IllegalStateException("?????? ???????????? ??????????????????.");});
		 memberRepo.checkNick(memberNick).ifPresent(m->{throw new IllegalStateException("?????? ???????????? ???????????????.");});
		 Member member = new Member();
		 Optional<Member_Grade> mg = mgRepo.sltByNM("???");
		 String pw = (String)req.get("memberPw");
		 member.setMemberEmail(memberEmail);
		 member.setMemberGrade(mg.get());
		 member.setMemberId(memberId);
		 member.setMemberNick(memberNick);
		 member.setMemberPw(passwordEncoder.encode(pw));
		 member.setMemberPhone((String)req.get("memberPhone"));
		 Member savedMember = memberRepo.save(member);
		 res.put("msg", "???????????? ??????");
		 Long celebNo =  ((Integer) req.get("likeCeleb")).longValue();
		 Optional<Celeb> celebOne = celebRepo.findById(celebNo);
		 System.out.println(celebNo);
		 System.out.println(celebOne);
		 System.out.println(celebOne.toString());
		 Celeb_Like cl = new Celeb_Like();
		 cl.setCeleb(celebOne.get());
		 cl.setMember(savedMember);
		 Celeb_Like savedCL = clRepo.save(cl);
		 res.put("celebLike","?????? ????????? ?????? : "+savedCL.getCeleb().getCelebNo());
//		 for(int i =0;i<celebNo.size();i++) {
//			 cnt++;
//			 Optional<Celeb> celebOne =celebRepo.findById(Long.valueOf(celebNo.get(i).toString()));
//			 Celeb_Like cl = new Celeb_Like();
//			 cl.setCeleb(celebOne.get());
//			 cl.setMember(savedMember);
//			 Celeb_Like savedCL = clRepo.save(cl);
//			 res.put("celebLike"+cnt,"?????? ????????? ?????? : "+savedCL.getCeleb().getCelebNo());
//		 }
		 } catch(IllegalStateException e) {
				res.put("success", false);
				res.put("msg", e.getMessage());
				return res;
		 }
		 return res;
	}
	@Override
	public LoginTokenDTO login(Member member) {

		// ?????? ?????? ??????

		// -------- ?????? ??????
		// ?????? id, password??? ?????? UsernamePasswordAuthenticationToken?????? ??????
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				member.getMemberId(), member.getMemberPw());
		System.out.println(member.getMemberId() + " " + member.getMemberPw());
		// authenticationToken??? ???????????? authenticate???????????? ????????? ??????
		// ???????????? CustomUserDetailsService??? loadUserByUsername ???????????? ?????????
		// ??? ???????????? ????????? Authentication????????? ?????????
		System.out.println(authenticationToken);
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);//Authentication????????? SecurityContext??? ??????

		// memberName ???????????? ??????????????? ????????????
		String memberId = memberRepo.checkId(member.getMemberId()).get().getMemberId();
		System.out.println(memberId);
		// Authentication??? ????????? jwt?????? ??????
		LoginTokenDTO jwt = tokenProvider.generateTokenDto(authentication, memberId);
		System.out.println(jwt);
		// -------- ?????? ????????????

		// RefreshToken ??????
		RefreshToken refreshToken = RefreshToken.builder().key(authentication.getName()).value(jwt.getRefreshToken())
				.build();
		System.out.println(refreshToken);
		refreshTokenRepository.save(refreshToken);

		return jwt;
	}
	@Override
	public Map<String,Object> checkId(Map<String, Object> req){
		Map<String, Object> res = new HashMap<String,Object>();
		String memberId = (String)req.get("memberId");
		try {
			memberRepo.checkId(memberId).ifPresent(m ->{throw new IllegalStateException("?????? ???????????? ??????????????????.");});
			res.put("msg", "????????? ???????????? ??????");
			res.put("success", true);

		} catch(IllegalStateException e) {
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		return res;
	}
	@Override
	public Map<String,Object> checkNick(Map<String, Object> req){
		Map<String, Object> res = new HashMap<String,Object>();
		 String memberNick = (String)req.get("memberNick");
		try {
			 memberRepo.checkNick(memberNick).ifPresent(m->{throw new IllegalStateException("?????? ???????????? ???????????????.");});
			res.put("msg", "????????? ???????????? ??????");
			res.put("success", true);
		} catch(IllegalStateException e) {
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		return res;
	}
	@Override
	public Map<String,Object> checkEmail(Map<String, Object> req){
		Map<String, Object> res = new HashMap<String,Object>();
		String memberEmail = (String)req.get("memberEmail");
		try {
			 memberRepo.checkEmail(memberEmail).ifPresent(m ->{throw new IllegalStateException("?????? ???????????? ??????????????????.");});
			res.put("msg", "????????? ???????????? ??????");
			res.put("success", true);

		} catch(IllegalStateException e) {
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		return res;
	}
	@Override
	public Map<String,Object> updateMember(Map<String, Object> req){
		Map<String, Object> res = new HashMap<String,Object>();
		try{
			Member member = memberRepo.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			String memberEmail = (String)req.get("memberEmail");
			String memberNick = (String)req.get("memberNick");
			String memberPw = (String)req.get("memberPw");
			System.out.println(memberEmail +" "+memberNick+" "+memberPw);
			if (memberEmail.equals(member.getMemberEmail())){
				System.out.println("email not changed");
			} else {
				memberRepo.checkEmail(memberEmail).ifPresent(m ->{throw new IllegalStateException("?????? ???????????? ??????????????????.");});
				member.setMemberEmail(memberEmail);
			}
			System.out.println(memberNick+ " "+member.getMemberNick());
			if (memberNick.equals(member.getMemberNick())) {
				System.out.println("nick not changed");
			} else {
				memberRepo.checkNick(memberNick).ifPresent(m->{throw new IllegalStateException("?????? ???????????? ???????????????.");});
				member.setMemberNick(memberNick);

			}
			if (memberPw == null) {
				System.out.println("pw not changed");
			}else {
				member.setMemberPw(passwordEncoder.encode(memberPw));
			}
			Integer tempCelebNo = (Integer)req.get("celebNo");
			Optional<Celeb_Like> cl = clRepo.sltByMember(member.getMemberNo());
			Celeb_Like tempcl = new Celeb_Like();
			Optional<Celeb> celeb = Optional.ofNullable(celebRepo.findById(tempCelebNo.longValue()).orElseThrow(() -> new IllegalStateException("???????????? ?????? ???????????????.")));
			if(cl.isPresent()) {
				clRepo.delete(cl.get());
				clRepo.flush();
			}
			tempcl.setCeleb(celeb.get());
			tempcl.setMember(member);
			memberRepo.save(member);
			clRepo.save(tempcl);
			res.put("msg", "???????????? ??????");
			res.put("success", true);
			}catch(IllegalStateException e) {
				res.put("success", false);
				res.put("msg", e.getMessage());
				return res;
		 }
		
		return res;
	}
	@Override
	public Map<String,Object> myPage(){
		Map<String, Object> res = new HashMap<String,Object>();
		try{
			Member member = memberRepo.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			MypageDTO mypage = memberRepo.myPage(member.getMemberNo());
			//??? ????????????
			res.put("countSalesOrder", soRepo.countSalesOrderByMember(member.getMemberNo()));
			res.put("countSalesLike", slRepo.CountLikeByMember(member.getMemberNo()));
			res.put("countAuctionRegist", auctionRepo.countAuctionRegistedByMember(member.getMemberNo()));
			//??? ????????????
			//?????? ????????? ????????????
			res.put("mypage", mypage);
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		return res;
	}
	@Override
	public Map<String,Object>orderList(){
		Map<String, Object> res = new HashMap<String,Object>();
	    Long nowLoginMemberNo=0L;
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		
		try{
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			Optional<List<SalesOrderDTO>>orderListbyMem = soRepo.sltMultiByMember(member.getMemberNo());
			res.put("res", orderListbyMem);
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
			return res;
	}
	@Override
	public Map<String,Object>salesLikeList(){
		Map<String, Object> res = new HashMap<String,Object>();
	    Long nowLoginMemberNo=0L;
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		try{
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
			Optional<List<SalesDTO>> sltList= slRepo.likeList(member.getMemberNo());
			res.put("res", sltList);
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
			return res;
	}
	
	@Override
	public Map<String,Object>AuctionLikeList(){
		Map<String, Object> res = new HashMap<String,Object>();
	    Long nowLoginMemberNo=0L;
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		try{
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
//			
			Optional<List<AuctionAddImgDTO>> sltList= alRepo.sltByMember(member.getMemberNo());
			res.put("res", sltList);
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		return res;
	}
}
