package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.dto.ReplyDTO;

public interface ReplyRepoCustom {

	Optional<List<ReplyDTO>> sltReviewList(Long cardPK);

}
