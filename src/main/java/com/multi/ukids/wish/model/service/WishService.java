package com.multi.ukids.wish.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.wish.model.mapper.WishMapper;
import com.multi.ukids.wish.model.vo.Wish;

public class WishService {
	@Autowired
	private WishMapper mapper;
	
	public List<Wish> selectNurseryWishList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectNurseryWishList(param);
	}
	
	public List<Wish> selectKinderWishList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectKinderWishList(param);
	}
	
	public int selectNurseryWishCount(int no) {
		return mapper.selectNurseryWishCount(no);
	}
	public int selectKinderWishCount(int no) {
		return mapper.selectKinderWishCount(no);
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public int saveNurseryWish(Wish wish) {
		int result = 0;
		if(wish.getNo() == 0) {
			result = mapper.insertNurseryWish(wish);
		} else {
			result = mapper.updateNurseryWish(wish);
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveKinderClaim(Wish wish) {
		int result = 0;
		if(wish.getNo() == 0) {
			result = mapper.insertKinderWish(wish);
		} else {
			result = mapper.updateKinderWish(wish);
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteNurseryWish(int no, String rootPath) {
		return mapper.deleteNurseryWish(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteKinderWish(int no, String rootPath) {
		return mapper.deleteKinderWish(no);
	}
	
}
