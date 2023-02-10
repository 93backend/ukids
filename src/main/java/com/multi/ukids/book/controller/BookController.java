package com.multi.ukids.book.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.ukids.book.model.service.BookService;
import com.multi.ukids.book.model.vo.Book;
import com.multi.ukids.common.util.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	   @GetMapping("/book")
	      public String book(Model model,Integer page,
	    		  @RequestParam(required=false) String searchValue,
	    		  @RequestParam(required=false) String searchType,
	    		  @RequestParam(required=false) List<String> age) {
	       if(page == null) {
	          page = 1;
	       }
	         Map<String, Object> searchMap = new HashMap<String, Object>(); 
	         try {
	        	 
	            if(searchValue != null && searchValue.length() > 0) {
	               searchMap.put(searchType, searchValue); 
	            }
	            if(age != null) {
		               searchMap.put("age", age); 
		            }
	         } catch (Exception e) {}         
	                  
	         int bookCount = bookService.getBookCount(searchMap);
	         System.out.println("책 수량 : "+bookCount);
	         PageInfo pageInfo = new PageInfo(page, 5, bookCount, 8);
	         List<Book> list = bookService.getBookList(pageInfo,searchMap);
	         
	         model.addAttribute("list", list);
	         model.addAttribute("searchType", searchType);
	         model.addAttribute("searchValue", searchValue);
	         model.addAttribute("age", age);
	         model.addAttribute("pageInfo", pageInfo);
	         return "book";
	         
	      }
	
	
	   }
	   
