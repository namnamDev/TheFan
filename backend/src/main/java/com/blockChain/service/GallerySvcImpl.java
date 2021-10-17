package com.blockChain.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.blockChain.config.SecurityUtil;
import com.blockChain.domain.GalleryArticle;
import com.blockChain.domain.Member;
import com.blockChain.domain.Member_Gall_Like;
import com.blockChain.domain.Sales;
import com.blockChain.domain.Sales_Like;
import com.blockChain.repository.GalleryArticleRepo;
import com.blockChain.repository.MemberRepo;
import com.blockChain.repository.Member_Gall_LikeRepo;

@Service
@Transactional
public class GallerySvcImpl implements GallerySvcInter{
	@Autowired
	private MemberRepo memberRepo;
	@Autowired
	private GalleryArticleRepo gaRepo;
	@Autowired
	private Member_Gall_LikeRepo mglRepo;
//	private 
	@Override
	public Map<String,Object>galleryMain(){
		Map<String, Object> res = new HashMap<String,Object>();
		res.put("res", gaRepo.galleryArticleMain().get());
		return res;
	}
	@Override
	public Map<String,Object>galleryList(Long memberNo,Long arraydiv1, Long celebPk,Long arraydiv2){
		Map<String, Object> res = new HashMap<String,Object>();
		res.put("res",memberRepo.galleryList(memberNo, arraydiv1, celebPk, arraydiv2));
		return res;
	}
	@Override
	public Map<String,Object>galleryInsert(Map<String,Object>req){
		Map<String, Object> res = new HashMap<String,Object>();
		try {
			Member member = memberRepo.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
			String galleryArticleContent = (String) req.get("galleryArticleContent");
			GalleryArticle ga = new GalleryArticle();
			ga.setMember(member);
			ga.setGalleryArticleContent(galleryArticleContent);
			gaRepo.save(ga);
			res.put("success", true);
			res.put("msg", "입력성공");
			
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		return res;
	}
	@Override
	public Map<String,Object>sltLikeCount(Long galleryPk){
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
				Optional<Member_Gall_Like> checkLike = gaRepo.checkLike(galleryPk, member.get().getMemberNo());
				if(checkLike.isEmpty()) {
					res.put("islike", false);
				}else {
					res.put("islike", true);
				}
			}
			res.put("peoplelike", gaRepo.likeCount(galleryPk));
			
		return res;
	}
	@Override
	public Map<String,Object>insertLike(Map<String,Object> req){
		Map<String, Object> res = new HashMap<String,Object>();
		Long nowLoginMemberNo=0L;// 샘플 0 
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		
		try {
			Member member = memberRepo.findById(nowLoginMemberNo).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
			Long galleryPK = Long.valueOf((Integer)req.get("galleryNo"));
			Member gallery = memberRepo.findById(galleryPK).orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다"));;
			Optional<Member_Gall_Like> checkLike = gaRepo.checkLike(galleryPK, member.getMemberNo());
			Member_Gall_Like sl = new Member_Gall_Like();
			sl.setFromMember(member);
			sl.setToMember(gallery);
			if (checkLike.isEmpty()){
				res.put("success", true);
				res.put("msg", "좋아요 성공");
				mglRepo.save(sl);
			}else {
				res.put("success", true);
				res.put("msg", "좋아요취소 성공");
				mglRepo.delete(sl);
			}
		}catch(IllegalStateException e){
			res.put("success", false);
			res.put("msg", e.getMessage());
			return res;
		}
		return res;
	}
	
	
}
