package com.multi.ukids.toy.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.toy.model.mapper.ToyMapper;
import com.multi.ukids.toy.model.vo.Pay;
import com.multi.ukids.toy.model.vo.T_Review;
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

	public Toy findByNo(int no) {
		return mapper.selectToyByNo(no);
	}

	@Transactional(rollbackFor = Exception.class)
	public int saveToyReview(T_Review toyReview) {
		return mapper.insertToyReview(toyReview);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteToyReview(int no) {
		return mapper.deleteToyReview(no);
	}
	public List<T_Review> selectToyReviewByNo(int toyNo){		
		return mapper.selectToyReviewByNo(toyNo);
	}

	public List<Toy> selectSimilarToy(Toy toy) {
		return mapper.selectSimilarToy(toy);
	}

	public void insertPay(Pay pay) {
		mapper.insertPay(pay);
	}
	public int updateToyType(int toyNo) {
		return mapper.updateToyType(toyNo);
	}
	
	public Pay selectPay(int payNo) {
		return mapper.selectPay(payNo);
	}
	
	
	public List<Pay> selectPayDate(int toyNo) {
		return mapper.selectPayDate(toyNo);
	}

	public int deletePay(int payNo) {
		return mapper.deletePay(payNo);
		
	}
	
}
