package com.multi.ukids.nursery.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.nursery.model.mapper.NurseryMapper;
import com.multi.ukids.nursery.model.vo.NReview;
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
	
	public Nursery findByNo(int no) {
		return mapper.selectNurseryByNo(no);
	}
	
	public int getClaimCount(int no) {
		return mapper.selectNurseryClaimCount(no);
	}
	
	public List<NReview> getReviewList(int no) {
		return mapper.selectNurseryReviewList(no);
	}
	
	public int getReviewCount(int no) {
		return mapper.selectNurseryReviewCount(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveReview(NReview review) {
		return mapper.insertNurseryReview(review);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteReview(int no) {
		return mapper.deleteNurseryReview(no);
	}

}
