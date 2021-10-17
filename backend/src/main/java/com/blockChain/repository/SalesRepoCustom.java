package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.domain.Product;
import com.blockChain.domain.Sales;
import com.blockChain.domain.Sales_Product;
import com.blockChain.dto.SalesDTO;
import com.blockChain.dto.CardAddCountDTO;
import com.blockChain.dto.CardDTO;
import com.blockChain.dto.CardGenerateDTO;

public interface SalesRepoCustom {

	Optional<Sales> sltBySalesNM(String NM);

	Optional<List<SalesDTO>> slyBySalesDiv(String Div);

	Optional<List<CardGenerateDTO>> gainCardList(Long salesPK);

	Optional<Sales> sltByContainSalesNM(String NM);

	Optional<List<SalesDTO>> searchSales(String NM);

	Optional<List<CardAddCountDTO>> gainCardListAddNum(Long salesPK);

	Long countLeftCard(Long cardPK);

	List<Product> cardListByPack(Long cardPackPk);

}
