package com.multi.ukids.toy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.member.model.vo.Member;
import com.multi.ukids.toy.model.service.ToyService;
import com.multi.ukids.toy.model.vo.T_Review;
import com.multi.ukids.toy.model.vo.Toy;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("") // 요청 url의 상위 url을 모두 처리할때 사용
@Controller
public class ToyController {
	@Autowired
	private ToyService toyService;
	
	@GetMapping("/toy-main")
	   public String toyMain(Model model,Integer page, @RequestParam(required=false) String searchValue,@RequestParam(required=false) List<String> searchType) {
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
	      } catch (Exception e) {}	      
	      System.out.println("맵 투스트링 : "+searchMap.toString());
	      	      
	      int toyCount = toyService.getToyCount(searchMap);
	      System.out.println("장난감갯수 : "+toyCount);
	      PageInfo pageInfo = new PageInfo(page, 5, toyCount, 9);
	      List<Toy> list = toyService.getToyList(pageInfo,searchMap);
	      List<String> cate = toyService.getCateList();
	      
	      model.addAttribute("cate", cate);
	      model.addAttribute("list", list);
	      model.addAttribute("searchType", searchType);
	      model.addAttribute("searchValue", searchValue);
	      model.addAttribute("pageInfo", pageInfo);
	      return "toy-main";
	      
	   }
	
	
	@GetMapping("/toy-detail")
	public String toyDetail(Model model, @RequestParam("no") int no) {		
		Toy toy = toyService.findByNo(no);
		List<T_Review> reviewList = toyService.selectToyReviewByNo(no);
		String tcm = toy.getToyCategoryM();
		
		List<Toy> smilarToy = toyService.selectSimilarToy(toy);
		System.out.println(toy.getToyCategoryM());
		
		model.addAttribute("toy", toy);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("smilarToy", smilarToy);
		return "toy-detail";
	}

	@PostMapping("/toyReview")
	public String writeReply(Model model, 
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@ModelAttribute T_Review toyReview
			) {
		toyReview.setMemberNo(loginMember.getMemberNo());
		log.info("리뷰 작성 요청 Review : " + toyReview);
		
		int result = toyService.saveToyReview(toyReview);
		
		if(result > 0) {
			model.addAttribute("msg", "리뷰가 등록되었습니다.");
		}else {
			model.addAttribute("msg", "리뷰 등록에 실패하였습니다.");
		}
		model.addAttribute("location", "/toy-detail?no="+toyReview.getToyNo());
		return "common/msg";
	}

	@GetMapping("/toyReviewDel")
	public String deleteReply(Model model, 
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			int no, int toyNo
			){
		log.info("리뷰 삭제 요청");
		int result = toyService.deleteToyReview(no);
		
		if(result > 0) {
			model.addAttribute("msg", "리뷰 삭제가 정상적으로 완료되었습니다.");
		}else {
			model.addAttribute("msg", "리뷰 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/toy-detail?no=" + toyNo);
		return "/common/msg";
	}


}
