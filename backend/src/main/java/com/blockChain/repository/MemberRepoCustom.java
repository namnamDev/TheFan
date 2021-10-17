package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.domain.Member;
import com.blockChain.dto.GalleryArticleDTO;
import com.blockChain.dto.GalleryCardDTO;
import com.blockChain.dto.MypageDTO;

public interface MemberRepoCustom {

	Optional<Member> checkId(String id);

	Optional<Member> checkEmail(String email);

	Optional<Member> checkNick(String email);

	MypageDTO myPage(Long memberNo);

	List<GalleryCardDTO> galleryList(Long memberNo, Long arraydiv1, Long celebPk, Long arraydiv2);

	List<GalleryCardDTO> getCanRegiAuction(Long memberNo);

}
