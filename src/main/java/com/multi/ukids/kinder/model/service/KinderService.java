package com.multi.ukids.kinder.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.kinder.model.mapper.KinderMapper;
import com.multi.ukids.kinder.model.vo.Kinder;

@Service
public class KinderService {
	@Autowired
	private KinderMapper mapper;
	
	public List<Kinder> getKinderList(PageInfo pageInfo, Map<String, Object> map) {
		map.put("limit", ""+pageInfo.getListLimit());
		map.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectKinderList(map);
	}
	
	public int getKinderCount(Map<String, Object> map) {
		return mapper.selectKinderCount(map);
	}

}
