package com.multi.ukids.nursery.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.nursery.model.vo.NAdmission;
import com.multi.ukids.nursery.model.vo.NReview;
import com.multi.ukids.nursery.model.vo.Nursery;

@Mapper
public interface NurseryMapper {
	List<Nursery> selectNurseryList(Map<String, Object> map);
	int selectNurseryCount(Map<String, Object> map);
	Nursery selectNurseryByNo(int no);
	int insertNurseryAdmission(NAdmission admission);
	int selectNurseryClaimCount(int no);
	List<NReview> selectNurseryReviewList(int no);
	int selectNurseryReviewCount(int no);
	int insertNurseryReview(NReview review);
	int deleteNurseryReview(int no);
}
