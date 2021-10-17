package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.domain.Sales;
import com.blockChain.domain.Token;

public interface TokenRepoCustom {

	Optional<List<Token>> sltMultiBySales(Sales sales);

}
