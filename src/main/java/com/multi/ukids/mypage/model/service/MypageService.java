package com.multi.ukids.mypage.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.kinder.model.vo.KAdmission;
import com.multi.ukids.mypage.model.mapper.MypageMapper;
import com.multi.ukids.nursery.model.vo.NAdmission;

@Service
public class MypageService {
	@Autowired
	private MypageMapper mapper;
	
	public List<KAdmission> selectKAdmissionList(PageInfo pageInfo, Map<String, Object> map) {
		map.put("limit", ""+pageInfo.getListLimit());
		map.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectKAdmissionList(map);
	}
	
	public int selectKAdmissionCount(Map<String, Object> map) {
		return mapper.selectKAdmissionCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteKAdmission(int no) {
		return mapper.deleteKAdmission(no);
	}
	
	public List<NAdmission> selectNAdmissionList(PageInfo pageInfo, Map<String, Object> map) {
		map.put("limit", ""+pageInfo.getListLimit());
		map.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectNAdmissionList(map);
	}
	
	public int selectNAdmissionCount(Map<String, Object> map) {
		return mapper.selectNAdmissionCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteNAdmission(int no) {
		return mapper.deleteNAdmission(no);
	}
}
