package com.blockChain.service;

import java.util.Map;

import com.blockChain.domain.Wallets;

public interface WalletSvcInter {

	Map<String, Object> createWallet(Map<String, Object> req);
	Map<String, Object> findWallet();
	Map<String, Object> recharge(Map<String, Object> req);
}
