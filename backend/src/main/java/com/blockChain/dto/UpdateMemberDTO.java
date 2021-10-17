package com.blockChain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberDTO {
	private String memberPw;
	private String memberEmail;
	private String memberNick;
	private Long LikeCelebPk; 
	
}
