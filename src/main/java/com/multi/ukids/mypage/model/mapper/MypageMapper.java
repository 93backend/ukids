package com.multi.ukids.mypage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.kinder.model.vo.KAdmission;
import com.multi.ukids.nursery.model.vo.NAdmission;
import com.multi.ukids.toy.model.vo.Cart;
import com.multi.ukids.wish.model.vo.Wish;


@Mapper
public interface MypageMapper {
	
	// mypage2 - 입소 신청 조회
	List<KAdmission> selectKAdmissionList(Map<String, Object> map);
	int selectKAdmissionCount(Map<String, Object> map);
	int deleteKAdmission(int no);
	
	List<NAdmission> selectNAdmissionList(Map<String, Object> map);
	int selectNAdmissionCount(Map<String, Object> map);
	int deleteNAdmission(int no);
	
	// mypage3 - 찜
	List<Wish> selectNurseryWishList(Map<String, Object> map);
	int selectNurseryWishCount(Map<String, Object> map);
	int updateNurseryWish(Wish wish);
	int deleteNurseryWish(int no);
	
	List<Wish> selectKinderWishList(Map<String, Object> map);
	int selectKinderWishCount(Map<String, Object> map);
	int updateKinderWish(Wish wish);
	int deleteKinderWish(int no);
	
	
	// mypage7
	List<Cart> selectCartList(Map<String, Object> map);
	int selectCartCount(Map<String, Object> map);
	Cart selectCartByNo(int no);
	int insertCart(Cart cart);
	int updateCart(Cart cart);
	int deleteCart(int no);
}
