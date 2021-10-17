package com.blockChain.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blockChain.service.AdminSvcInter;

@RestController
@RequestMapping("/api/Admin")
public class AdminController {
	
	@Autowired
	AdminSvcInter adminSvc;
	@PostMapping
	public Map<String, Object> firstData(){
		return adminSvc.totalData();
		
	}
}
