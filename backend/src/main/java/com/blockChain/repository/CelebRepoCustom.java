package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.domain.Celeb;
import com.blockChain.dto.ArtistListDTO;
import com.blockChain.dto.CelebDTO;

public interface CelebRepoCustom {

	Optional<Celeb> sltCeleb(String NM);


	Optional<List<CelebDTO>> sltCelebDTOByGroup(Long GroupNo);


	Optional<List<Celeb>> sltCelebByGroup(Long GroupNo);


	Optional<List<ArtistListDTO>> sltArtistListDTO();

}
