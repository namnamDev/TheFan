package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.domain.Product;
import com.blockChain.domain.Token;
import com.blockChain.dto.CardDTO;
import com.blockChain.dto.CardGenerateDTO;

public interface ProductRepoCustom {

	Optional<Product> sltByNM(String NM);

	Optional<List<Product>> sltByWM(String WM);

	Optional<List<Product>> sltByCelebNo(Long NO);

	Optional<List<Product>> sltByCelebNM(String NM);

	Optional<List<CardGenerateDTO>> searchCard(String NM);

	CardGenerateDTO sltByTokenNo(Token token);

	CardDTO sltByTokenNoAddToken(Token token);

}
