package com.blockChain.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blockChain.service.CelebSvcInter;
@RestController
@RequestMapping("/api/main")
public class MainController {
	@Autowired
	CelebSvcInter celebSvc;
	@GetMapping("/celebgrouplist")
	public Map<String,Object> celebGroupList(){
		return celebSvc.sltCelebDTObyGroup();
	}
	
	@GetMapping("/artistlist")
	public Map<String,Object> artistlist(){
		return celebSvc.artistList();
	}
}
