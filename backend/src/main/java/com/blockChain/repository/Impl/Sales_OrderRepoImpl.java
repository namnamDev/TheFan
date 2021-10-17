package com.blockChain.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blockChain.domain.QSales;
import com.blockChain.domain.QSales_Order;
import com.blockChain.dto.SalesOrderDTO;
import com.blockChain.repository.Sales_OrderRepoCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Sales_OrderRepoImpl implements Sales_OrderRepoCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Optional<List<SalesOrderDTO>> sltMultiByMember(Long memberNo){
		QSales_Order qso = QSales_Order.sales_Order;
		QSales qs = QSales.sales;
		return Optional.ofNullable(queryFactory.select(Projections.constructor(SalesOrderDTO.class
				, qs.salesNo
				, qs.salesNm
				, qs.imgUrl
				, qso.salesOrderDate
				, qs.salesPrice))
				.from(qso)
				.join(qs).on(qs.eq(qso.sales))
				.where(qso.member.memberNo.eq(memberNo))
				.orderBy(qso.salesOrderDate.desc())
				.fetch());
	}
	@Override
	public Long countSalesOrderByMember(Long memberNo){
		QSales_Order qso = QSales_Order.sales_Order;
		return queryFactory.selectFrom(qso)
				.where(qso.member.memberNo.eq(memberNo))
				.fetchCount();
	}
}
