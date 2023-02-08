package com.multi.ukids.wish.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.wish.model.vo.Wish;

@Mapper
public interface WishMapper {
	List<Wish> selectNurseryWishList(Map<String, String> map);
	int selectNurseryWishCount(int no);
	Wish selectNurseryWishByNo(int no);
	int updateReadCountNursery(Wish wish);
	int insertNurseryWish(Wish wish);
	int updateNurseryWish(Wish wish);
	int deleteNurseryWish(int no);
	
	List<Wish> selectKinderWishList(Map<String, String> map);
	int selectKinderWishCount(int no);
	Wish selectKinderWishByNo(int no);
	int updateReadCountKinder(Wish wish);
	int insertKinderWish(Wish wish);
	int updateKinderWish(Wish wish);
	int deleteKinderWish(int no);
}
