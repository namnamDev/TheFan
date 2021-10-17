package com.blockChain.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class Transaction {

	// 트랜잭션 ID
	@Id
	@GeneratedValue
	@Column(name="TRANSACTION_NO")
	private Long txNo;

	// hash
	@Column(name="TRANSACTION_HASH", length = 500)
	private String txHash;

	// nonce
	@Column(name="TRANSACTION_NONCE", length = 500)
	private String txNonce;
	
	// block_hash
	@Column(name="TRANSACTION_BHASH", length = 500)
	private String txBHash;
	
	// block_number
	@Column(name="TRANSACTION_BNO", length = 500)
	private String txBNO;
	
	// index
	@Column(name="TRANSACTION_INDEX", length = 500)
	private String txIdx;
	
	// from_hash
	@Column(name="TRANSACTION_FROM_HASH", length = 500)
	private String txFromHash;
	
	// to_hash
	@Column(name="TRANSACTION_TO_HASH", length = 500)
	private String txToHash;
	
	// value
	@Column(name="TRANSACTION_VALUE", length = 500)
	private String txValue;
	
	// gas_price
	@Column(name="TRANSACTION_GAS_PRICE", length = 500)
	private String txGPrice;
	
	// gas
	@Column(name="TRANSACTION_GAS", length = 500)
	private String txGas;
	
	// input
	@Column(name="TRANSACTION_INPUT", length = 500)
	private String txInput;
	
	// creates
	@Column(name="TRANSACTION_CREATES", length = 500)
	private String txCreates;

	// publicKey
	@Column(name="TRANSACTION_PUBLICKEY", length = 500)
	private String txPublicKey;
	
	// raw
	@Column(name="TRANSACTION_RAW", length = 500)
	private String txRaw;

	// r
	@Column(name="TRANSACTION_R", length = 500)
	private String txR;
	
	// s
	@Column(name="TRANSACTION_S", length = 500)
	private String txS;
	
	// v
	@Column(name="TRANSACTION_V", length = 500)
	private String txV;
	
	// stored_at
	@Column(name="TRANSACTION_STORED_AT", length = 500)
	private String txStoredAt;

}
