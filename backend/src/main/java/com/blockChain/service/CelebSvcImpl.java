package com.blockChain.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blockChain.domain.Celeb;
import com.blockChain.domain.Celeb_Group;
import com.blockChain.dto.ArtistListDTO;
import com.blockChain.dto.CelebDTO;
import com.blockChain.dto.CelebGroupDTO;
import com.blockChain.dto.CelebGroupListDTO;
import com.blockChain.dto.GroupDTO;
import com.blockChain.repository.CelebRepo;
import com.blockChain.repository.Celeb_GroupRepo;

@Service
@Transactional
public class CelebSvcImpl implements CelebSvcInter{
	@Autowired
	private CelebRepo celebRepo;
	@Autowired
	private Celeb_GroupRepo cgRepo;
	
	@Override
	public Map<String,Object> sltCelebDTObyGroup(){
		Map<String, Object> res = new HashMap<String,Object>();
		Optional<List<CelebGroupListDTO>> dto = cgRepo.celebGroupList();
		res.put("res", dto.get());
		return res;
	}
	
	@Override
	public Map<String,Object> artistList(){
		Map<String, Object> res = new HashMap<String,Object>();
		List<ArtistListDTO> celebList = celebRepo.sltArtistListDTO().get();
		res.put("res", celebList);
		return res;

	}
//	@Override
//	public Map<String,Object> sltCelebDTObyGroup(){
//		Map<String, Object> res = new HashMap<String,Object>();
//		 ModelMapper mm = new ModelMapper(); 
//		List<Celeb_Group> groupList = cgRepo.findAll();
//		List<CelebGroupDTO> dto = new ArrayList<CelebGroupDTO>();
//		for (int i = 0; i< groupList.size();i++) {
//			
//			CelebGroupDTO cgDTO = new CelebGroupDTO();
//			GroupDTO groupDTO = mm.map(groupList.get(i), GroupDTO.class);
//			Optional<List<CelebDTO>> celebList= celebRepo.sltCelebDTOByGroup(groupDTO.getGroupNo());
//			cgDTO.setCelebList(celebList.get());
//			cgDTO.setGroup(groupDTO);
//			dto.add(cgDTO);
//		}
//		res.put("res", dto);
//		return res;
//	}

}
