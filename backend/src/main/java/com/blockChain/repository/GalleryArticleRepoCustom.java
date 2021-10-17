package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.domain.Member_Gall_Like;
import com.blockChain.domain.Member_Grade;
import com.blockChain.dto.GalleryArticleDTO;

public interface GalleryArticleRepoCustom {

	Optional<List<GalleryArticleDTO>> galleryArticleMain();

	Optional<Member_Gall_Like> checkLike(Long galleryPk, Long memberPk);

	Long likeCount(Long galleryPk);


}
