package com.blockChain.service;

import java.util.Map;

public interface GallerySvcInter {

	Map<String, Object> galleryMain();

	Map<String, Object> galleryList(Long memberNo, Long arraydiv1, Long celebPk, Long arraydiv2);

	Map<String, Object> galleryInsert(Map<String, Object> req);

	Map<String, Object> sltLikeCount(Long galleryPk);

	Map<String, Object> insertLike(Map<String, Object> req);

}
