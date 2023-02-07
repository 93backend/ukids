package com.multi.ukids.kinder.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.kinder.model.mapper.KinderMapper;
import com.multi.ukids.kinder.model.vo.KAdmission;
import com.multi.ukids.kinder.model.vo.KReview;
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
	
	public Kinder findByNo(int no) {
		return mapper.selectKinderByNo(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveAdmission(KAdmission admission) {
		return mapper.insertKinderAdmission(admission);
	}
	
	public int getClaimCount(int no) {
		return mapper.selectKinderClaimCount(no);
	}
	
	public List<KReview> getReviewList(int no) {
		return mapper.selectKinderReviewList(no);
	}
	
	public int getReviewCount(int no) {
		return mapper.selectKinderReviewCount(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveReview(KReview review) {
		return mapper.insertKinderReview(review);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteReview(int no) {
		return mapper.deleteKinderReview(no);
	}
}
