package com.multi.ukids.playground.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.playground.model.vo.Playground;
import com.multi.ukids.welfare.model.vo.Welfare;

@Mapper
public interface PlaygroundMapper {
	List<Playground> selectMainPlaygroundList(Map<String, Object> map);
	List<Playground> selectPlaygroundList(Map<String, String> map);
}
