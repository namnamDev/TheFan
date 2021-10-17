package com.blockChain.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryCardDTO {
//	private CardDTO card;
    private Long cardNo; 
    private String cardNM;
    private String cardImgUrl;
    private Long cardGradeNo;
    private String cardGradeNM;
    private List<TokenDTO> token;
//    private Long tokenNo;
//    private String tokenSer;
	
}
