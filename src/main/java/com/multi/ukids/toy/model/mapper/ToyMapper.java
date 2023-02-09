package com.multi.ukids.toy.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.toy.model.vo.Toy;
import com.multi.ukids.toy.model.vo.Pay;
import com.multi.ukids.toy.model.vo.T_Review;


@Mapper
public interface ToyMapper {
	List<Toy> selectToyList(Map<String, Object> paramMap);
	List<String> selectCateList();
	int selectToyCount(Map<String, Object> paramMap);
	Toy selectToyByNo(int no);
	int insertToyReview(T_Review toyReview);
	int deleteToyReview(int no);
	List<T_Review> selectToyReviewByNo(int toyNo);
	List<Toy> selectSimilarToy(Toy toy);
	void insertPay(Pay pay);
	Pay selectPay (int payNo);
	List<Pay> selectPayDate (int toyNo);
	int deletePay(int payNo);
	int updateToyType(int toyNo);
	
}
