package com.multi.ukids.kinder.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.kinder.model.vo.Kinder;

@Mapper
public interface KinderMapper {
	List<Kinder> selectKinderList(Map<String, Object> map);
	int selectKinderCount(Map<String, Object> map);
	Kinder selectKinderByNo(int no);

}
