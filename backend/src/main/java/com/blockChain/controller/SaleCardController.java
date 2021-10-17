package com.blockChain.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blockChain.service.SalesSvcInter;

@RestController
@RequestMapping("/api/saleCard")
public class SaleCardController {
	
	@Autowired
	SalesSvcInter salesSvc;
	@GetMapping
	public Map<String,Object> sltSalesByMW(){
		return salesSvc.sltSalesByMW();
	}
	
}
