package com.multi.ukids.toy.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.toy.model.mapper.ToyMapper;
import com.multi.ukids.toy.model.vo.Toy;

@Service
public class ToyService {
	@Autowired
	private ToyMapper mapper;

	
	public List<Toy> getToyList(PageInfo pageInfo,Map<String, Object> paramMap){
		paramMap.put("limit", "" + pageInfo.getListLimit());
		paramMap.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectToyList(paramMap);
	}
	
	public List<String> getCateList() {
		return mapper.selectCateList();
	}

	public int getToyCount(Map<String, Object> paramMap) {
		return mapper.selectToyCount(paramMap);
	}


}
