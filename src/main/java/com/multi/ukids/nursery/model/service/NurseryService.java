package com.multi.ukids.nursery.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.nursery.model.mapper.NurseryMapper;
import com.multi.ukids.nursery.model.vo.Nursery;


@Service
public class NurseryService {
	@Autowired
	private NurseryMapper mapper;
	
	public List<Nursery> getNurseryList(PageInfo pageInfo, Map<String, Object> map) {
		map.put("limit", ""+pageInfo.getListLimit());
		map.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectNurseryList(map);
	}
	
	public int gettNurseryCount(Map<String, Object> map) {
		return mapper.selectNurseryCount(map);
	}

}
