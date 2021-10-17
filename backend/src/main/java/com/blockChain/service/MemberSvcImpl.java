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
		 memberRepo.checkId(memberId).ifPresent(m ->{throw new IllegalStateException("이미 존재하는 아이디입니다.");});
		 memberRepo.checkEmail(memberEmail).ifPresent(m ->{throw new IllegalStateException("이미 존재하는 이메일입니다.");});
		 memberRepo.checkNick(memberNick).ifPresent(m->{throw new IllegalStateException("이미 존재하는 별명입니다.");});
		 Member member = new Member();
		 Optional<Member_Grade> mg = mgRepo.sltByNM("팬");
		 String pw = (String)req.get("memberPw");
		 member.setMemberEmail(memberEmail);
		 member.setMemberGrade(mg.get());
		 member.setMemberId(memberId);
		 member.setMemberNick(memberNick);
		 member.setMemberPw(passwordEncoder.encode(pw));
		 member.setMemberPhone((String)req.get("memberPhone"));
		 Member savedMember = memberRepo.save(member);
		 res.put("msg", "회원가입 성공");
		 Long celebNo =  ((Integer) req.get("likeCeleb")).longValue();
		 Optional<Celeb> celebOne = celebRepo.findById(celebNo);
		 System.out.println(celebNo);
		 System.out.println(celebOne);
		 System.out.println(celebOne.toString());
		 Celeb_Like cl = new Celeb_Like();
		 cl.setCeleb(celebOne.get());
		 cl.setMember(savedMember);
		 Celeb_Like savedCL = clRepo.save(cl);
		 res.put("celebLike","샐럽 좋아요 성공 : "+savedCL.getCeleb().getCelebNo());
//		 for(int i =0;i<celebNo.size();i++) {
//			 cnt++;
//			 Optional<Celeb> celebOne =celebRepo.findById(Long.valueOf(celebNo.get(i).toString()));
//			 Celeb_Like cl = new Celeb_Like();
//			 cl.setCeleb(celebOne.get());
//			 cl.setMember(savedMember);
//			 Celeb_Like savedCL = clRepo.save(cl);
//			 res.put("celebLike"+cnt,"샐럽 좋아요 성공 : "+savedCL.getCeleb().getCelebNo());
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

		// 유저 정보 검증

		// -------- 토큰 생성
		// 유저 id, password를 통해 UsernamePasswordAuthenticationToken객체 생성
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				member.getMemberId(), member.getMemberPw());
		System.out.println(member.getMemberId() + " " + member.getMemberPw());
		// authenticationToken를 이용해서 authenticate메소드가 실행이 될때
		// 아까만든 CustomUserDetailsService의 loadUserByUsername 메소드가 실행됨
		// 그 결과값을 가지고 Authentication객체가 생성됨
		System.out.println(authenticationToken);
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);//Authentication객체를 SecurityContext에 저장

		// memberName 가져와서 토큰만들때 집어넣음
		String memberId = memberRepo.checkId(member.getMemberId()).get().getMemberId();
		System.out.println(memberId);
		// Authentication를 이용해 jwt토큰 생성
		LoginTokenDTO jwt = tokenProvider.generateTokenDto(authentication, memberId);
		System.out.println(jwt);
		// -------- 토큰 생성완료

		// RefreshToken 저장
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
			memberRepo.checkId(memberId).ifPresent(m ->{throw new IllegalStateException("이미 존재하는 아이디입니다.");});
			res.put("msg", "아이디 중복체크 통과");
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
			 memberRepo.checkNick(memberNick).ifPresent(m->{throw new IllegalStateException("이미 존재하는 별명입니다.");});
			res.put("msg", "닉네임 중복체크 통과");
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
			 memberRepo.checkEmail(memberEmail).ifPresent(m ->{throw new IllegalStateException("이미 존재하는 이메일입니다.");});
			res.put("msg", "이메일 중복체크 통과");
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
			Member member = memberRepo.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
			String memberEmail = (String)req.get("memberEmail");
			String memberNick = (String)req.get("memberNick");
			String memberPw = (String)req.get("memberPw");
			System.out.println(memberEmail +" "+memberNick+" "+memberPw);
			if (memberEmail.equals(member.getMemberEmail())){
				System.out.println("email not changed");
			} else {
				memberRepo.checkEmail(memberEmail).ifPresent(m ->{throw new IllegalStateException("이미 존재하는 이메일입니다.");});
				member.setMemberEmail(memberEmail);
			}
			System.out.println(memberNick+ " "+member.getMemberNick());
			if (memberNick.equals(member.getMemberNick())) {
				System.out.println("nick not changed");
			} else {
				memberRepo.checkNick(memberNick).ifPresent(m->{throw new IllegalStateException("이미 존재하는 별명입니다.");});
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
			Optional<Celeb> celeb = Optional.ofNullable(celebRepo.findById(tempCelebNo.longValue()).orElseThrow(() -> new IllegalStateException("존재하지 않는 샐럽입니다.")));
			if(cl.isPresent()) {
				clRepo.delete(cl.get());
				clRepo.flush();
			}
			tempcl.setCeleb(celeb.get());
			tempcl.setMember(member);
			memberRepo.save(member);
			clRepo.save(tempcl);
			res.put("msg", "회원수정 완료");
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
			Member member = memberRepo.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
			MypageDTO mypage = memberRepo.myPage(member.getMemberNo());
			//내 구매내역
			res.put("countSalesOrder", soRepo.countSalesOrderByMember(member.getMemberNo()));
			res.put("countSalesLike", slRepo.CountLikeByMember(member.getMemberNo()));
			res.put("countAuctionRegist", auctionRepo.countAuctionRegistedByMember(member.getMemberNo()));
			//내 관심상품
			//내가 등록한 경매제품
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
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
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
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
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
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
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
