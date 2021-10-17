package com.blockChain.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blockChain.repository.AuctionRepo;
import com.blockChain.repository.ProductRepo;
import com.blockChain.repository.SalesRepo;

@Service
@Transactional
public class SearchSvcImpl implements SearchSvcInter{
	@Autowired
	private SalesRepo salesRepo;

	@Autowired
	private ProductRepo cardRepo;
	@Autowired
	private AuctionRepo auctionRepo;
	@Override
	public Map<String,Object> searchAll(String word){
		 Map<String, Object> res = new HashMap<String,Object>();
		 res.put("cardList", cardRepo.searchCard(word).get());
		 res.put("cardPackList", salesRepo.searchSales(word).get());
		 res.put("auctionList", auctionRepo.searchAuction(word).get());
		 return res;
	}
}
