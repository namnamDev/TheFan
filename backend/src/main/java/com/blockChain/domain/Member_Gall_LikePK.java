package com.blockChain.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Member_Gall_LikePK implements Serializable{
	private Long fromMember;
	private Long toMember;

}
