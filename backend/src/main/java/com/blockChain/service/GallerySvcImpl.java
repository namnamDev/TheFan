package com.blockChain.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blockChain.repository.MemberRepo;

@Service
@Transactional
public class GallerySvcImpl implements GallerySvcInter{
	@Autowired
	private MemberRepo memberRepo;
	
	@Override
	public Map<String,Object>galleryMain(){
		Map<String, Object> res = new HashMap<String,Object>();
		res.put("res", memberRepo.galleryMain().get());
		return res;
	}
}