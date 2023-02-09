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

import com.multi.ukids.claim.model.service.ClaimService;
import com.multi.ukids.claim.model.vo.Claim;
import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.kinder.model.vo.KAdmission;
import com.multi.ukids.member.model.service.MemberService;
import com.multi.ukids.member.model.vo.Member;
import com.multi.ukids.mypage.model.service.MypageService;
import com.multi.ukids.nursery.model.vo.NAdmission;
import com.multi.ukids.toy.model.vo.Cart;
import com.multi.ukids.toy.model.vo.Rental;
import com.multi.ukids.wish.model.vo.Wish;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes("loginMember")
@Controller
public class MypageController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ClaimService claimService;
	
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
			@RequestParam Map<String, Object> param) {
		
		if (loginMember == null) {
			return "/login";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		int page = 1;
		try {
			if(param.get("page") != null) {
				page = Integer.parseInt((String) param.get("page"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// nursery
		int ncount = service.getNAdmissionCount(param);
		PageInfo npageInfo = new PageInfo(page, 1, ncount, 4);
		List<NAdmission> nlist = service.getNAdmissionList(npageInfo, param);
		
		System.out.println("nlist(입소신청) : " + nlist);
		model.addAttribute("nlist", nlist);
		model.addAttribute("pageInfo", npageInfo);
		
		// kinder
		int kcount = service.getKAdmissionCount(param);
		PageInfo kpageInfo = new PageInfo(page, 1, kcount, 4);
		List<KAdmission> klist = service.getKAdmissionList(kpageInfo, param);
		
		System.out.println("klist(입소신청) : " + klist);
		model.addAttribute("klist", klist);
		model.addAttribute("pageInfo", kpageInfo);
		

		return "mypage-2";
	}
	
//	@PostMapping("/nAdmission/search")
//	public String searchNAdmission(Model model,
//			@RequestParam Map<String, Object> param
//			) {
//		int count = service.getNAdmissionCount(param);
//		PageInfo pageInfo = new PageInfo(1, 1, count, count);
//		List<NAdmission> list = service.getNAdmissionList(pageInfo, param);
//		
//		model.addAttribute("list", list);
//		
//		return "nAdmission-list";
//	}
	
//	@RequestMapping("/deleteKAdmission")
//	public String deleteKAdmission(Model model,
//			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//			int no
//			) {
//		log.info("유치원 신청 삭제 요청");
//		if (loginMember == null) {
//			return "/";
//		}
//		
//		int result = service.deleteKAdmission(no);
//		
//		if(result > 0) {
//			model.addAttribute("msg", "유치원 신청 삭제가 정상적으로 완료되었습니다.");
//		}else {
//			model.addAttribute("msg", "유치원 신청 삭제에 실패하였습니다.");
//		}
//		model.addAttribute("location", "/mypage-2");
//		
//		return "/common/msg";
//	}
		
	@GetMapping("/mypage-3")
	public String mypageView3(
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			Model model, @RequestParam Map<String, Object> param) {
		
		if (loginMember == null) {
			return "/login";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		int page = 1;
		try {
			if(param.get("page") != null) {
				page = Integer.parseInt((String) param.get("page"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// nursery
		int ncount = service.getNurseryWishCount(param);
		PageInfo npageInfo = new PageInfo(page, 5, ncount, 3);
		List<Wish>  nlist = service.getNurseryWishList(npageInfo, param);

		System.out.println("nlist(찜) : " + nlist);
		model.addAttribute("nlist", nlist);
		model.addAttribute("nPageInfo", npageInfo);
//		
		// kinder
		int kcount = service.getKinderWishCount(param);
		PageInfo kpageInfo = new PageInfo(page, 5, kcount, 3);
		List<Wish> klist = service.getKinderWishList(kpageInfo, param);
//		
		System.out.println("klist(찜) : " + klist);
		model.addAttribute("klist", klist);
		model.addAttribute("kPageInfo", kpageInfo);

		return "mypage-3";
	}
	
	@GetMapping("/mypage-4")
	public String mypageView4(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, 
			@RequestParam Map<String, Object> param) {
		
		if (loginMember == null) {
			return "/login";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		int page = 1;
		try {
			if(param.get("page") != null) {
				page = Integer.parseInt((String) param.get("page"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int ncount = service.getNurseryClaimCount(param);
		PageInfo npageInfo = new PageInfo(page, 5, ncount, 4);
		List<Claim> nlist = service.getNurseryClaimList(npageInfo, param);
		
		System.out.println("nlist(불편사항) : " + nlist);
		model.addAttribute("nlist", nlist);
		model.addAttribute("npageInfo", npageInfo);
		
		int kcount = service.getKinderClaimCount(param);
		PageInfo kpageInfo = new PageInfo(page, 5, kcount, 4);
		List<Claim> klist = service.getKinderClaimList(kpageInfo, param);
		
		System.out.println("klist(불편사항) : " + klist);
		model.addAttribute("klist", klist);
		model.addAttribute("kpageInfo", kpageInfo);
		model.addAttribute("param", param);	
		
		return "mypage-4";
	}
	
	@GetMapping("/mypage-5")
	public String mypageView5() {

		return "mypage-5";
	}
	
	@GetMapping("/mypage-6")
	public String mypageView6(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, 
			@RequestParam Map<String, Object> param) {
		if (loginMember == null) {
			return "/login";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		int page = 1;
		try {
			if(param.get("page") != null) {
				page = Integer.parseInt((String) param.get("page"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int count = service.getRentalCount(param);
		PageInfo pageInfo = new PageInfo(page, 5, count, 6);
		List<Rental> list = service.getRentalList(pageInfo, param);
		
		for (Rental r : list) {
			int p = (Integer.parseInt(r.getToyPay()) / 10);
			if (r.getStartDate() != null || r.getEndDate() != null) {
				long sec = (r.getEndDate().getTime() - r.getStartDate().getTime()) / 1000;
				int date = (int)(sec / (24*60*60));
				r.setDate(date);
				p *= date;
			}
			r.setRealPrice(p);
		}
		
		System.out.println("list : " + list);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("param", param);	

		return "mypage-6";
	}
	
	@GetMapping("/mypage-7")
	public String mypageView7(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, 
			@RequestParam Map<String, Object> param) {
		int page = 1;
		if (loginMember == null) {
			return "/";
		}
		
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		int count = service.getCartCount(param);
		PageInfo pageInfo = new PageInfo(page, 5, count, 10);
		List<Cart> list = service.getCartList(pageInfo, param);
		
		int pay = 0;
		for (Cart c : list) {
			int p = (Integer.parseInt(c.getToyPay()) / 10);
			if (c.getStartdate() != null || c.getEndDate() != null) {
			long sec = (c.getEndDate().getTime() - c.getStartdate().getTime()) / 1000;
			int date = (int)(sec / (24*60*60));
			c.setDate(date);
			p *= date;
			}
			c.setRealPay(p);
			pay += p;
		}
		
		int totalPay = pay + 1000;
		
		System.out.println(pay);
		System.out.println("list" + list);
		model.addAttribute("pay", pay);
		model.addAttribute("totalPay", totalPay);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("param", param);	
		
		
		return "mypage-7";
	}
	
	
	

}
