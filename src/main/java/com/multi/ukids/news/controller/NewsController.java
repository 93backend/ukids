package com.multi.ukids.news.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.multi.ukids.news.api.NaverSearchAPI;
import com.multi.ukids.news.model.vo.News;

@Controller
public class NewsController {
	
	
	private static List<News> list = null; 
	private static List<News> newsList1 = null; 
//	private static List<News> newsList2 = null; 
//	private static List<News> newsList3 = null; 
//	private static List<News> newsList4 = null; 
//	private static List<News> newsList5 = null; 
	
	@GetMapping("/news")
	public String mainPage(Model model) {
		if(newsList1 == null) {
			newsList1 = NaverSearchAPI.getNewsList("육아 지원",  20, 1);
//			newsList2 = NaverSearchAPI.getNewsList("유치원", 10, 1);
//			newsList3 = NaverSearchAPI.getNewsList("어린이집", 15, 1);
//			newsList4 = NaverSearchAPI.getNewsList("아동", 15, 1);
//			newsList5 = NaverSearchAPI.getNewsList("육아", 15, 1);
			list = new ArrayList<>();
			list.addAll(newsList1);
//			list.addAll(newsList2);
//			list.addAll(newsList3);
//			list.addAll(newsList4);
//			list.addAll(newsList5);
		}
		
		
		model.addAttribute("newsList1",newsList1);
//		model.addAttribute("newsList2",newsList2);
//		model.addAttribute("newsList3",newsList3);
//		model.addAttribute("newsList4",newsList4);
//		model.addAttribute("newsList5",newsList5);
		model.addAttribute("list",list);
		return "news";
		
	}
}
