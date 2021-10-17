package com.blockChain.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import com.blockChain.config.SecurityUtil;
import com.blockChain.domain.Member;
import com.blockChain.domain.Wallets;
import com.blockChain.repository.MemberRepo;
import com.blockChain.repository.WalletRepo;

@Service
@Transactional
public class WalletSvcImpl implements WalletSvcInter {

	@Autowired
	private WalletRepo walletRepo;
	@Autowired
	private MemberRepo memberRepo;
	
	final String MASTER_WALLET_ADD = "0x39dce082172253d8d816b0e9aa48345a72a2179a";
	final String MASTER_WALLET_PATH = "C:\\Users\\HAJI\\UTC--2021-09-27T04-28-24.331534396Z--39dce082172253d8d816b0e9aa48345a72a2179a";
	final String MASTER_WALLET_PWD = "1234";
	
	@Override
	public Map<String, Object> createWallet(Map<String, Object> req) {
		Map<String, Object> res = new HashMap<String,Object>();
			
		try {
			Member member = memberRepo.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
			System.out.println((String)req.get("walletAdd"));
			
			String walletAdd = (String)req.get("walletAdd");
			System.out.println(walletAdd);
			
			Wallets wallet = new Wallets();
			wallet.setWalletAdd(walletAdd);
			wallet.setWalletBal(new BigDecimal(0));
			wallet.setMember(member);
			wallet.setWalletCash(0L);
			wallet.setWalletRC(0L);
			walletRepo.save(wallet);
			
			res.put("success", true);
			res.put("msg", "지갑등록 성공");
		}catch(Exception e) {
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}

	@Override
	public Map<String, Object> findWallet() {
		Map<String, Object> res = new HashMap<String,Object>();
		
		try {
			Web3j web3 = Web3j.build(new HttpService("http://13.125.37.55:9991"));
			System.out.println("Successfuly connected to Ethereum");
			
			Member member = memberRepo.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
			System.out.println(member.getMemberNo());
			
			Wallets wallet = walletRepo.findByWallet(member.getMemberNo()).orElseThrow(() -> new NoSuchElementException("지갑 정보가 없습니다"));
			System.out.println("wallet address : " + wallet.getWalletAdd());
//			Wallets wallet = new Wallets();
//			wallet.setWalletAdd(MASTER_WALLET_ADD);
//			wallet.setWalletBal(new BigDecimal(0));
//			wallet.setMember(member);
//			wallet.setWalletCash(0L);
//			wallet.setWalletRC(0L);
			
			//wei 조회
			EthGetBalance balanceWei = web3.ethGetBalance(wallet.getWalletAdd(), DefaultBlockParameterName.LATEST).send();
			System.out.println("balance in wei: " + balanceWei);

			//ether 조회
			BigDecimal balanceInEther = Convert.fromWei(balanceWei.getBalance().toString(), Unit.ETHER);
			System.out.println("balance in ether: " + balanceInEther);
			
			if(wallet.getWalletBal() != balanceInEther) {
				wallet.setWalletBal(balanceInEther);
				walletRepo.save(wallet);
			}
			res.put("address", wallet.getWalletAdd());
			res.put("walletBal", balanceInEther.toString() );
			res.put("success", true);
			res.put("msg", "지갑 조회 성공");
		}catch(Exception e) {
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}

	@Override
	public Map<String, Object> recharge(Map<String, Object> req) {
		
		Map<String, Object> res = new HashMap<String,Object>();
		
		try {
			Member member = memberRepo.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
			//Wallets wallet = walletRepo.findByWallet(member.getMemberNo()).orElseThrow(() -> new NoSuchElementException("지갑 정보가 없습니다"));
			String walletAdd = (String)req.get("walletAdd");
			String rechargeEth = (String)req.get("rechargeEth");
			
			Web3j web3 = Web3j.build(new HttpService("http://13.125.37.55:8545"));
			System.out.println("Successfuly connected to Ethereum");
			
			// 1. 계정로드 및 nonce 가져오기
			Credentials credentials = WalletUtils.loadCredentials(MASTER_WALLET_PWD, MASTER_WALLET_PATH);
			System.out.println("Successfuly credentials");
			//Get Nonce
			EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
			BigInteger nonce =  ethGetTransactionCount.getTransactionCount();
			System.out.println("Successfuly Get Nonce");
			// 2. 받는 사람 및 보낼 금액 설정
			// Recipient account
			String recipientAddress = walletAdd;

			// Value to Transfer
			BigInteger value = Convert.toWei(rechargeEth, Unit.ETHER).toBigInteger();
			
			// 3. 가스 매개변수 구성
			// A transfer cost 21,000 units of gas
			BigInteger gasLimit = BigInteger.valueOf(21000);

			// I am willing to pay 1Gwei (1,000,000,000 wei or 0.000000001 ether) for each unit of gas consumed by the transaction.
			BigInteger gasPrice = Convert.toWei("1", Unit.GWEI).toBigInteger();
			
			// 4. 원시 트랜잭션 준비
			RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
				    nonce,
				    gasPrice,
				    gasLimit,
				    recipientAddress,
				    value);
			
			// 5. 서명
			// 서명 부분에는 트랜잭션에 암호로 서명하는 데 사용되는 (키 쌍) 원시tx 필요
			// Sign the transaction
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);

			// Convert it to Hexadecimal String to be sent to the node
			String hexValue = Numeric.toHexString(signedMessage);
			
			// 6. JSON-RPC 통신
			// Send transaction
			EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();

			// Get the transaction hash
			String transactionHash = ethSendTransaction.getTransactionHash();
			System.out.println(transactionHash);
			// 7 . 거래가 채굴될 때 까지 기다림		
			// Wait for transaction to be mined
			System.out.println("start mine");
			Optional<TransactionReceipt> transactionReceipt = null;
			do {
			  EthGetTransactionReceipt ethGetTransactionReceiptResp = web3.ethGetTransactionReceipt(transactionHash).send();
			  transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();

			  Thread.sleep(3000); // Retry after 3 sec
			} while(!transactionReceipt.isPresent());
			
			
			res.put("success", true);
			res.put("msg", "이더 충전 성공");
		}catch(Exception e) {
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	
	

}
