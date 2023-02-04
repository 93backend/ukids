package com.multi.ukids.toy.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.toy.model.vo.Toy;


@Mapper
public interface ToyMapper {
	List<Toy> selectToyList(Map<String, Object> paramMap);
	List<String> selectCateList();
	int selectToyCount(Map<String, Object> paramMap);

}
