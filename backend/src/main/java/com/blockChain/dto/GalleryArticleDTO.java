package com.blockChain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryArticleDTO {
	private MemberDTO member;
	private Long galleryArticleNo;
	private String galleryArticleContent;
	private Long likes;
}
