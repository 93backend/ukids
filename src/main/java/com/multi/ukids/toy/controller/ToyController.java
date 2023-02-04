package com.multi.ukids.toy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.toy.model.service.ToyService;
import com.multi.ukids.toy.model.vo.Toy;



@RequestMapping("") // 요청 url의 상위 url을 모두 처리할때 사용
@Controller
public class ToyController {
	@Autowired
	private ToyService service;
	
	@GetMapping("/toy-main")
	   public String list(Model model,Integer page, @RequestParam(required=false) String searchValue,@RequestParam(required=false) List<String> searchType) {
	    if(page == null) {
	    	page = 1;
	    }
	      Map<String, Object> searchMap = new HashMap<String, Object>(); // 문자열, 리스트를 같이 저장할 수 있도록 <키(문자열), 값(Object)> 으로 저장, Map은 Key로 값(Value)를 추출 가능
	      try {
	         if(searchValue != null && searchValue.length() > 0) {
	            searchMap.put("searchValue", searchValue); // Map에 검색어 스트링으로 저장 key:"searchValue",
	            searchMap.put("searchType", searchType); // Map에 검색분류 리스트로 저장
	         }
	         if(searchType != null) {
		         searchMap.put("searchType", searchType); 
	         }
	         System.out.println("검색목록 : " + searchValue);
	         System.out.println("페이지 : "+ page);
	      } catch (Exception e) {}
	      
	      System.out.println("맵 투스트링 : "+searchMap.toString());
	      	      
	      int toyCount = service.getToyCount(searchMap);
	      System.out.println("장난감갯수 : "+toyCount);
	      PageInfo pageInfo = new PageInfo(page, 5, toyCount, 9);
	      List<Toy> list = service.getToyList(pageInfo,searchMap);
	      List<String> cate = service.getCateList();
	      
	      model.addAttribute("cate", cate);
	      model.addAttribute("list", list);
	      model.addAttribute("searchType", searchType);
	      model.addAttribute("searchValue", searchValue);
	      model.addAttribute("pageInfo", pageInfo);
	      return "toy-main";
	      
	   }
	
	
	@GetMapping("/toy-detail")
	public String view() {

		return "toy-detail";
	}






}
