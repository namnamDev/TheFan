package com.blockChain.dto;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private Long tokenNo;
    private String tokenSeriarlizeNo;
    private LocalDateTime ownDate;
}
