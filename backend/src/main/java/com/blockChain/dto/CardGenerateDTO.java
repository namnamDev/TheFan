package com.blockChain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardGenerateDTO {
    private Long cardNo; 
    private String cardNM;
    private String cardImgUrl;
    private Long cardGradeNo;
    private String cardGradeNM;
}
