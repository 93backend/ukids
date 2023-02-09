package com.multi.ukids.book.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.ukids.book.model.mapper.BookMapper;
import com.multi.ukids.book.model.vo.Book;
import com.multi.ukids.common.util.PageInfo;

@Service
public class BookService {
	@Autowired
	private BookMapper mapper;
	
	public List<Book> getBookList(PageInfo pageInfo,Map<String, Object> paramMap){
		paramMap.put("limit", "" + pageInfo.getListLimit());
		paramMap.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectBookList(paramMap);
	}


	public int getBookCount(Map<String, Object> paramMap) {
		return mapper.selectBookCount(paramMap);
	}
	
	
}