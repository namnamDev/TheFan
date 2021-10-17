package com.blockChain.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString

/*
 * 이더리움 네트워크 연동 시
 * 필요한 URL 정보와 몇 번째 블록까지 동기화되어 있는지 대한 정보 저장
 * 
 */

public class Eth_Info {

	// 네트워크 url
	@Id
	@Column(name="ETHNET_URL",length=500)
	private String ethnetURL;
	
	// 마지막 블록 넘버
	@Column(name="LATEST_BNO",length=500)
	private String latestBno;
}
