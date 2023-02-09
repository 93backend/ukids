package com.multi.ukids.book.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.book.model.vo.Book;

@Mapper
public interface BookMapper {
	List<Book> selectBookList(Map<String, Object> paramMap);
	int selectBookCount(Map<String, Object> paramMap);
	
}