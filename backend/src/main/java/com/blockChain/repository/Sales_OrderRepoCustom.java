package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.dto.SalesOrderDTO;

public interface Sales_OrderRepoCustom {
	Optional<List<SalesOrderDTO>> sltMultiByMember(Long memberNo);

	Long countSalesOrderByMember(Long memberNo);

}
