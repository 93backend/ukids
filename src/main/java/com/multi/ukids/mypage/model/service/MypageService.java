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
import com.multi.ukids.toy.model.vo.Cart;
import com.multi.ukids.wish.model.vo.Wish;

@Service
public class MypageService {
	@Autowired
	private MypageMapper mapper;
	
	// mypage2
	public List<KAdmission> getKAdmissionList(PageInfo pageInfo, Map<String, Object> map) {
		map.put("limit", ""+pageInfo.getListLimit());
		map.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectKAdmissionList(map);
	}
	
	public int getKAdmissionCount(Map<String, Object> map) {
		return mapper.selectKAdmissionCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteKAdmission(int no) {
		return mapper.deleteKAdmission(no);
	}
	
	public List<NAdmission> getNAdmissionList(PageInfo pageInfo, Map<String, Object> map) {
		map.put("limit", ""+pageInfo.getListLimit());
		map.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectNAdmissionList(map);
	}
	
	public int getNAdmissionCount(Map<String, Object> map) {
		return mapper.selectNAdmissionCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteNAdmission(int no) {
		return mapper.deleteNAdmission(no);
	}
	
	
	
	
	
	// mypage3
	public List<Wish> selectNurseryWishList(PageInfo pageInfo, Map<String, Object> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectNurseryWishList(param);
	}
	
	public List<Wish> selectKinderWishList(PageInfo pageInfo, Map<String, Object> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectKinderWishList(param);
	}
	
	public int selectNurseryWishCount(Map<String, Object> map) {
		return mapper.selectNurseryWishCount(map);
	}
	public int selectKinderWishCount(Map<String, Object> map) {
		return mapper.selectKinderWishCount(map);
	}

	
	@Transactional(rollbackFor = Exception.class)
	public int deleteNurseryWish(int no, String rootPath) {
		return mapper.deleteNurseryWish(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteKinderWish(int no, String rootPath) {
		return mapper.deleteKinderWish(no);
	}
	
	
	
	// mypage7
	public List<Cart> getCartList(PageInfo pageInfo,Map<String, Object> paramMap){
		paramMap.put("limit", "" + pageInfo.getListLimit());
		paramMap.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectCartList(paramMap);
	}
	
	public int getCartCount(Map<String, Object> map) {
		return mapper.selectCartCount(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveCart(Cart cart) {
		int result = 0;
		if(cart.getNo() == 0) {
			result = mapper.insertCart(cart);
		}else {
			result = mapper.updateCart(cart);
		}
		return result;
	}
	
	public Cart findByNo(int no) {
		Cart cart = mapper.selectCartByNo(no);
		return cart;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteCart(int no, String rootPath) {
		return mapper.deleteCart(no);
	}
	
}
