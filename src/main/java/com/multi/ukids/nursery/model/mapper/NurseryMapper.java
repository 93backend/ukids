package com.multi.ukids.nursery.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.nursery.model.vo.Nursery;

@Mapper
public interface NurseryMapper {
	List<Nursery> selectNurseryList(Map<String, Object> map);
	int selectNurseryCount(Map<String, Object> map);
	Nursery selectNurseryByNo(int no);
	int selectNurseryClaimCount(int no);
}
