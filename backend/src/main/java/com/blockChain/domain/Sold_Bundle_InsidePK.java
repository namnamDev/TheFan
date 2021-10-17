package com.blockChain.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Sold_Bundle_InsidePK implements Serializable{
	private Long salesOrder;
	private Long token;

}
