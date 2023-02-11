package com.multi.ukids.playground.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.ukids.playground.model.mapper.PlaygroundMapper;
import com.multi.ukids.playground.model.vo.Playground;

@Service
public class PlaygroundService {
	@Autowired
	private PlaygroundMapper mapper;
	
	public List<Playground> getPlaygroundList(Map<String, String> param) {
		return mapper.selectPlaygroundList(param);
	}
	
}
