package com.blockChain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MypageDTO {
	private Long memberNo;
	private String memberId;
	private String memberEmail;
	private String memberNick;
	private Long celebNo;
	private Long gradeNo;
	private String gradeNm;
}
