package com.blockChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blockChain.domain.GalleryArticle;

public interface GalleryArticleRepo extends JpaRepository<GalleryArticle,Long>,GalleryArticleRepoCustom{

}
