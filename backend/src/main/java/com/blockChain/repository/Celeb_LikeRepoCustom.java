package com.blockChain.repository;

import java.util.Optional;

import com.blockChain.domain.Celeb_Like;

public interface Celeb_LikeRepoCustom {

	Optional<Celeb_Like> sltByMember(Long memberNo);

}
