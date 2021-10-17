package com.blockChain.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class GalleryArticle {
	@Id
	@GeneratedValue
	@Column(name="GALLERY_NO")
	private Long galleryArticleNo;
	
	@ManyToOne
    @JoinColumn(name = "GALLERY_HOST_MEMBER")
    private Member member;
	
	@Column(name="GALLERY_CONTENT")
	private String galleryArticleContent;
}
