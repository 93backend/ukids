package com.multi.ukids.welfare.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.welfare.model.mapper.WelfareMapper;
import com.multi.ukids.welfare.model.vo.Welfare;

@Service
public class WelfareService {
	@Autowired
	private WelfareMapper mapper;
	
	public List<Welfare> getWelfareList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectWelfareList(param);
	}
	
	public int getWelfareCount(Map<String, String> param) {
		return mapper.selectWelfareCount(param);
	}


}