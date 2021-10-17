package com.blockChain.controller;

import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blockChain.service.SearchSvcInter;

@RestController
@RequestMapping("/api/search")
public class SearchController {
	@Autowired
	SearchSvcInter searchSvc;
	
	@GetMapping("/all/{word}")
	public Map<String,Object> artistlist(@PathVariable(name="word") String word){
		System.out.println(word);
		return searchSvc.searchAll(word);
	}
}
