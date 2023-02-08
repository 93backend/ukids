package com.multi.ukids.kinder.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.kinder.model.vo.KAdmission;
import com.multi.ukids.kinder.model.vo.KReview;
import com.multi.ukids.kinder.model.vo.KWish;
import com.multi.ukids.kinder.model.vo.Kinder;

@Mapper
public interface KinderMapper {
	// 검색
	List<Kinder> selectKinderList(Map<String, Object> map);
	int selectKinderCount(Map<String, Object> map);
	int[] selectKinderWishList(int memberNo);
	
	// 상세
	Kinder selectKinderByNo(int no);
	int insertKinderAdmission(KAdmission admission);
	int selectKinderClaimCount(int no);
	int selectKinderWish(KWish wish);
	int insertKinderWish(KWish wish);
	int deleteKinderWish(KWish wish);
	List<KReview> selectKinderReviewList(int no);
	int selectKinderReviewCount(int no);
	int insertKinderReview(KReview review);
	int deleteKinderReview(int no);
}
