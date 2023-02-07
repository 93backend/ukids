package com.multi.ukids.mypage.controller;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.multi.ukids.board.model.service.BoardService;
import com.multi.ukids.board.model.vo.Board;
import com.multi.ukids.claim.model.service.ClaimService;
import com.multi.ukids.claim.model.vo.Claim;
import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.kinder.model.service.KinderService;
import com.multi.ukids.kinder.model.vo.KAdmission;
import com.multi.ukids.member.model.service.MemberService;
import com.multi.ukids.member.model.vo.Member;
import com.multi.ukids.mypage.model.service.MypageService;
import com.multi.ukids.nursery.model.service.NurseryService;
import com.multi.ukids.nursery.model.vo.NAdmission;

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
	private ClaimService claimService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardservice;
	
	@Autowired
	private MypageService service;
	
	@PostMapping("/member/update")
	public String update(Model model, 
			@ModelAttribute Member updateMember, // request에서 온 값
			@SessionAttribute(name = "loginMember", required = false) Member loginMember // 세션 값
			) {
		log.info("update 요청, updateMember : " + updateMember);
		if(loginMember == null) {
			model.addAttribute("msg","잘못된 접근입니다.");
			model.addAttribute("location","/");
			return "common/msg";
		}
		
		updateMember.setMemberNo(loginMember.getMemberNo());
		int result = memberService.save(updateMember);
		
		if(result > 0) {
			model.addAttribute("loginMember", memberService.findById(loginMember.getId())); // DB에서 있는 값을 다시 세션에 넣어주는 코드
			model.addAttribute("msg", "회원정보를 수정하였습니다.");
			model.addAttribute("location", "/mypage");
		}else {
			model.addAttribute("msg", "회원정보 수정에 실패하였습니다.");
			model.addAttribute("location", "/mypage");
		}
		return "common/msg";
	}

	@GetMapping("/mypage")
	public String mypageView() {
		log.info("회원 정보 페이지 요청");
		return "mypage";
	}
	
	@GetMapping("/mypage-2")
	public String mypageView2( Model model, 
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, // 세션 값
			@RequestParam Map<String, Object> param
			) {
		
		int page = 1;
		
		if (loginMember == null) {
			return "/";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		// nursery
		int ncount = service.selectNAdmissionCount(param);
		PageInfo npageInfo = new PageInfo(page, 1, ncount, 4);
		List<NAdmission> nlist = service.selectNAdmissionList(npageInfo, param);
		
		System.out.println("nlist : " + nlist);
		model.addAttribute("nlist", nlist);
		model.addAttribute("pageInfo", npageInfo);
		model.addAttribute("param", param);		
		
		// kinder
		int kcount = service.selectKAdmissionCount(param);
		PageInfo kpageInfo = new PageInfo(page, 1, kcount, 4);
		List<KAdmission> klist = service.selectKAdmissionList(kpageInfo, param);
		
		System.out.println("klist : " + klist);
		model.addAttribute("klist", klist);
		model.addAttribute("pageInfo", kpageInfo);
		model.addAttribute("param", param);
		

		return "mypage-2";
	}
	
	@PostMapping("/nAdmission/search")
	public String searchNAdmission(Model model,
			@RequestParam Map<String, Object> param
			) {
		int count = service.selectNAdmissionCount(param);
		PageInfo pageInfo = new PageInfo(1, 1, count, count);
		List<NAdmission> list = service.selectNAdmissionList(pageInfo, param);
		
		model.addAttribute("list", list);
		
		return "nAdmission-list";
	}
	
	@RequestMapping("/deleteKAdmission")
	public String deleteKAdmission(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			int no
			) {
		log.info("유치원 신청 삭제 요청");
		if (loginMember == null) {
			return "/";
		}
		int result = service.deleteKAdmission(no);
		
		if(result > 0) {
			model.addAttribute("msg", "유치원 신청 삭제가 정상적으로 완료되었습니다.");
		}else {
			model.addAttribute("msg", "유치원 신청 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-2");
		
		return "/common/msg";
	}
		
	@GetMapping("/mypage-3")
	public String mypageView3() {

		return "mypage-3";
	}
	
	@GetMapping("/mypage-4")
	public String mypageView4(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, 
			@RequestParam Map<String, String> param) {
		if (loginMember == null) {
			return "/";
		}
		
		return "mypage-4";
	}
	
	@GetMapping("/mypage-5")
	public String mypageView5() {

		return "mypage-5";
	}
	
	@GetMapping("/mypage-6")
	public String mypageView6() {

		return "mypage-6";
	}
	
	@GetMapping("/mypage-7")
	public String mypageView7() {

		return "mypage-7";
	}
	
//	@GetMapping("/mypage2")
//	public String list(
//			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//			Model model, @RequestParam Map<String, String> param) {
//		log.info("리스트 요청, param : " + param);
//		int page = 1;
//		Map<String, String> searchMap = new HashMap<String, String>();
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
//		
//		return "/mypage2";
//	}
	
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
//	public String myArticle(
//			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//			@ModelAttribute Board board,
//			Model model, @RequestParam Map<String, String> param) {
//		log.info("리스트 요청, param : " + param);
//		int page = 1;
//		Map<String, String> searchMap = new HashMap<String, String>();
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
//		
//		searchMap.put("memberNo", "" + loginMember.getMemberNo());
//		int boardCount = BoardService.getBoardCount4(searchMap);
//		PageInfo pageInfo = new PageInfo(page, 5, boardCount, 15);
//		List<Board> list = boardservice.getBoardList4(pageInfo, searchMap);
//		
//		model.addAttribute("list", list);
//		model.addAttribute("param", param);
//		model.addAttribute("pageInfo", pageInfo);
//		
//		return "/mypage5";
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
