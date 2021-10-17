package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.domain.Auction_Like;
import com.blockChain.dto.AuctionAddImgDTO;
import com.blockChain.dto.AuctionDTO;

public interface Auction_LikeRepoCustom {

	Optional<List<AuctionAddImgDTO>> sltByMember(Long memberNo);

	Long likeCount(Long auctionPk);

	Optional<Auction_Like> checkLike(Long auctionPk, Long memberPk);

}
