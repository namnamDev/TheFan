package com.blockChain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
	Long reviewPK;
	String reviewUser;
	Long reviewUserPK;
	String reviewCardpack;
	String reviewContent;
	LocalDateTime reviewDate;
}
