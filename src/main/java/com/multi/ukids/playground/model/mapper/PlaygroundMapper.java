package com.multi.ukids.playground.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.playground.model.vo.Playground;

@Mapper
public interface PlaygroundMapper {
	List<Playground> selectPlaygroundList(Map<String, String> map);
}
