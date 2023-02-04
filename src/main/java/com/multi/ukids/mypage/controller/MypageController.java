package com.multi.ukids.mypage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.multi.ukids.kinder.model.service.KinderService;
import com.multi.ukids.member.model.service.MemberService;
import com.multi.ukids.member.model.vo.Member;
import com.multi.ukids.nursery.model.service.NurseryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes("loginMember")
@Controller
public class MypageController {
	
	@Autowired
	private NurseryService nurseryService;
	
	@Autowired
	private KinderService kinderService;
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/member/update")
	public String update(Model model, 
			@ModelAttribute Member updateMember, // request에서 온 값
			@SessionAttribute(name = "loginMember", required = false) Member loginMember // 세션 값
			) {
		log.info("update 요청, updateMember : " + updateMember);
		if(loginMember == null || loginMember.getId().equals(updateMember.getId()) == false) {
			model.addAttribute("msg","잘못된 접근입니다.");
			model.addAttribute("location","/");
			return "common/msg";
		}
		
		updateMember.setMemberNo(loginMember.getMemberNo());
		int result = memberService.save(updateMember);
		
		if(result > 0) {
			model.addAttribute("loginMember", memberService.findById(loginMember.getId())); // DB에서 있는 값을 다시 세션에 넣어주는 코드
			model.addAttribute("msg", "회원정보를 수정하였습니다.");
			model.addAttribute("location", "/member/view");
		}else {
			model.addAttribute("msg", "회원정보 수정에 실패하였습니다.");
			model.addAttribute("location", "/member/view");
		}
		return "common/msg";
	}

	@GetMapping("/mypage")
	public String mypageView() {
		log.info("회원 정보 페이지 요청");
		return "mypage";
	}
	
	@GetMapping("/mypage2")
	public String list(
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			Model model, @RequestParam Map<String, String> param) {
		log.info("리스트 요청, param : " + param);
		int page = 1;
		Map<String, String> searchMap = new HashMap<String, String>();
		try {
			String searchValue = param.get("searchValue");
			if(searchValue != null && searchValue.length() > 0) {
				String searchType = param.get("searchType");
				searchMap.put(searchType, searchValue);
			}
			page = Integer.parseInt(param.get("page"));
		} catch (Exception e) {}
		
		if (loginMember == null) {
			return "/login";
		}
		
		return "/mypage2";
	}
	
//	@GetMapping("/mypage2")
//	public String mypageView2() {
//		log.info("회원 정보 페이지 요청");
//		return "mypage2";
//	}
	
//	@GetMapping("/mypage3")
//	public String list(
//			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//			Model model, @RequestParam Map<String, String> param) {
//		log.info("리스트 요청, param : " + param);
//		int page = 1;
//		Map<String, String> searchMap = new HashMap()<String, String>();
//		try {
//			String searchValue = param.get("searchValue");
//			if(searchValue != null && searchValue.length() > 0) {
//				String searchType = param.get("searchType");
//				searchMap.put(searchType, searchValue);
//			}
//			page = Integer.parseInt(param.get("page"));
//		} catch (Exception e) {}
//		
//		if (loginMember == null) {
//			return "/login";
//		}
//	}
//	
//	@GetMapping("/mypage4")
//	public String list(
//			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//			Model model, @RequestParam Map<String, String> param) {
//		log.info("리스트 요청, param : " + param);
//		int page = 1;
//		Map<String, String> searchMap = new HashMap()<String, String>();
//		try {
//			String searchValue = param.get("searchValue");
//			if(searchValue != null && searchValue.length() > 0) {
//				String searchType = param.get("searchType");
//				searchMap.put(searchType, searchValue);
//			}
//			page = Integer.parseInt(param.get("page"));
//		} catch (Exception e) {}
//		
//		if (loginMember == null) {
//			return "/login";
//		}
//	}
//	
//	@GetMapping("/mypage5")
//	public String list(
//			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//			Model model, @RequestParam Map<String, String> param) {
//		log.info("리스트 요청, param : " + param);
//		int page = 1;
//		Map<String, String> searchMap = new HashMap()<String, String>();
//		try {
//			String searchValue = param.get("searchValue");
//			if(searchValue != null && searchValue.length() > 0) {
//				String searchType = param.get("searchType");
//				searchMap.put(searchType, searchValue);
//			}
//			page = Integer.parseInt(param.get("page"));
//		} catch (Exception e) {}
//		
//		if (loginMember == null) {
//			return "/login";
//		}
//	}
//	
//	@GetMapping("/mypage6")
//	public String list(
//			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//			Model model, @RequestParam Map<String, String> param) {
//		log.info("리스트 요청, param : " + param);
//		int page = 1;
//		Map<String, String> searchMap = new HashMap()<String, String>();
//		try {
//			String searchValue = param.get("searchValue");
//			if(searchValue != null && searchValue.length() > 0) {
//				String searchType = param.get("searchType");
//				searchMap.put(searchType, searchValue);
//			}
//			page = Integer.parseInt(param.get("page"));
//		} catch (Exception e) {}
//		
//		if (loginMember == null) {
//			return "/login";
//		}
//	}
}
