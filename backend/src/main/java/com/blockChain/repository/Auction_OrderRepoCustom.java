package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.domain.Auction_Order;
import com.blockChain.dto.AuctionOrderDTO;

public interface Auction_OrderRepoCustom {

	Optional<List<AuctionOrderDTO>> sltMultiAuctionOrderByMember(Long memberNo);

	Optional<Auction_Order> sltByAuctionNo(Long auctionNo);

}
