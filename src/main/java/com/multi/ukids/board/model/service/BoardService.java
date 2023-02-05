package com.multi.ukids.board.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.ukids.board.model.mapper.BoardMapper;
import com.multi.ukids.common.util.PageInfo;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper mapper;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	// mypage
//	public int getBoardCount4(Map<String, String> param) {
//		return mapper.selectBoardCount4(param);
//	}
//	
//	public List<Board> getBoardList4(PageInfo pageInfo, Map<String, String> param){
//		param.put("limit", "" + pageInfo.getListLimit());
//		param.put("offset", "" + (pageInfo.getStartList() - 1));
//		return mapper.selectBoardList4(param);
//	}
}
