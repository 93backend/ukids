package com.multi.ukids.mypage.controller;

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

import com.multi.ukids.board.model.vo.Board;
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

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes("loginMember")
@Controller
public class MypageController {
	
	final static private String savePath = "c:\\ukids\\";
	
	@Autowired
	private MemberService memberService;
	
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
	public String mypageView(Model model, 
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, // 세션 값
			@RequestParam Map<String, String> param) {
		if (loginMember == null) {
			return "/login";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		log.info("회원 정보 페이지 요청");
		
		int admissionCount = service.getNAdmissionCount(param) + service.getKAdmissionCount(param);
		int wishCount = service.getNurseryWishCount(param) + service.getKinderWishCount(param);
		int claimCount = service.getNurseryClaimCount(param) + service.getKinderClaimCount(param);
		int boardCount = service.getBoardCount(param);
		int rentalCount = service.getRentalCount(param);
		int cartCount = service.getCartCount(param);
		model.addAttribute("admissionCount", admissionCount);
		model.addAttribute("wishCount", wishCount);
		model.addAttribute("claimCount", claimCount);
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("rentalCount", rentalCount);
		model.addAttribute("cartCount", cartCount);
		
		
		return "mypage";
	}
	
	@GetMapping("/mypage-2")
	public String mypageView2( Model model, 
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, // 세션 값
			@RequestParam Map<String, String> param) {

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
		
		int admissionCount = ncount + kcount;
		int wishCount = service.getNurseryWishCount(param) + service.getKinderWishCount(param);
		int claimCount = service.getNurseryClaimCount(param) + service.getKinderClaimCount(param);
		int boardCount = service.getBoardCount(param);
		int rentalCount = service.getRentalCount(param);
		int cartCount = service.getCartCount(param);
		model.addAttribute("admissionCount", admissionCount);
		model.addAttribute("wishCount", wishCount);
		model.addAttribute("claimCount", claimCount);
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("rentalCount", rentalCount);
		model.addAttribute("cartCount", cartCount);
		

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
	@RequestMapping("/deleteNAdmission")
	public String deleteNAdmission(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			int no
			) {
		log.info("어린이집 신청 삭제 요청");
		
		int result = service.deleteNAdmission(no);
		
		if(result > 0) {
			model.addAttribute("msg", "어린이집 신청 삭제가 정상적으로 완료되었습니다.");
		}else {
			model.addAttribute("msg", "어린이집 신청 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-2");
		
		return "/common/msg";
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
	public String mypageView3(
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			Model model, @RequestParam Map<String, String> param) {
		
		if (loginMember == null) {
			return "/login";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		int nPage = 1;
		int kPage = 1;
		
		try {
			nPage = Integer.parseInt(param.get("nPage"));
		} catch (Exception e) {}
		try {
			kPage = Integer.parseInt(param.get("kPage"));
		} catch (Exception e) {}
		
		// nursery
		int ncount = service.getNurseryWishCount(param);
		PageInfo npageInfo = new PageInfo(nPage, 5, ncount, 3);
		List<Wish>  nlist = service.getNurseryWishList(npageInfo, param);

		System.out.println("nlist(찜) : " + nlist);
		model.addAttribute("nlist", nlist);
		model.addAttribute("nPageInfo", npageInfo);
//		
		// kinder
		int kcount = service.getKinderWishCount(param);
		PageInfo kpageInfo = new PageInfo(kPage, 5, kcount, 3);
		List<Wish> klist = service.getKinderWishList(kpageInfo, param);
//		
		System.out.println("klist(찜) : " + klist);
		model.addAttribute("klist", klist);
		model.addAttribute("kPageInfo", kpageInfo);
		
		int admissionCount = service.getNAdmissionCount(param) + service.getKAdmissionCount(param);
		int wishCount = ncount + kcount;
		int claimCount = service.getNurseryClaimCount(param) + service.getKinderClaimCount(param);
		int boardCount = service.getBoardCount(param);
		int rentalCount = service.getRentalCount(param);
		int cartCount = service.getCartCount(param);
		model.addAttribute("admissionCount", admissionCount);
		model.addAttribute("wishCount", wishCount);
		model.addAttribute("claimCount", claimCount);
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("rentalCount", rentalCount);
		model.addAttribute("cartCount", cartCount);

		return "mypage-3";
	}
	
	@RequestMapping("/deleteNurseryWish")
	public String deleteNurseryWish(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			int no
			) {
		log.info("어린이집 신청 삭제 요청");
		
		int result = service.deleteNurseryWish(no);
		
		if(result > 0) {
			model.addAttribute("msg", "해당 어린이집이 찜목록에서 삭제되었습니다.");
		}else {
			model.addAttribute("msg", "어린이집 찜목록 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-3");
		
		return "/common/msg";
	}
	
	@RequestMapping("/deleteKinderWish")
	public String deleteKinderWish(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			int no
			) {
		log.info("유치원 신청 삭제 요청");
		if (loginMember == null) {
			return "/";
		}
		
		int result = service.deleteKinderWish(no);
		
		if(result > 0) {
			model.addAttribute("msg", "해당 유치원이 찜목록에서 삭제되었습니다.");
		}else {
			model.addAttribute("msg", "유치원 찜목록 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-3");
		
		return "/common/msg";
	}
	
	@RequestMapping("/deleteAllNurseryWish")
	public String deleteAllNurseryWish(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember
			) {
		log.info("어린이집 신청 삭제 요청");
		
		int result = service.deleteAllNurseryWish();
		
		if(result > 0) {
			model.addAttribute("msg", "어린이집 찜목록이 전체 삭제되었습니다.");
		}else {
			model.addAttribute("msg", "어린이집 찜목록 전체 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-3");
		
		return "/common/msg";
	}
	
	@RequestMapping("/deleteAllKinderWish")
	public String deleteAllKinderWish(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember
			) {
		log.info("유치원 신청 삭제 요청");
		if (loginMember == null) {
			return "/";
		}
		
		int result = service.deleteAllKinderWish();
		
		if(result > 0) {
			model.addAttribute("msg", "유치원이 찜목록이 전체 삭제되었습니다.");
		}else {
			model.addAttribute("msg", "유치원 찜목록 전체 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-3");
		
		return "/common/msg";
	}
	
	
	
	
	@GetMapping("/mypage-4")
	public String mypageView4(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, 
			@RequestParam Map<String, String> param) {
		
		if (loginMember == null) {
			return "/login";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		int nPage = 1;
		int kPage = 1;
		
		try {
			nPage = Integer.parseInt(param.get("nPage"));
		} catch (Exception e) {}
		try {
			kPage = Integer.parseInt(param.get("kPage"));
		} catch (Exception e) {}
		
		// nursery
		int ncount = service.getNurseryClaimCount(param);
		PageInfo npageInfo = new PageInfo(nPage, 5, ncount, 4);
		List<Claim> nlist = service.getNurseryClaimList(npageInfo, param);
		
//		System.out.println("nlist(불편사항) : " + nlist);
		model.addAttribute("nlist", nlist);
		model.addAttribute("nPageInfo", npageInfo);
		
		int kcount = service.getKinderClaimCount(param);
		PageInfo kpageInfo = new PageInfo(kPage, 5, kcount, 4);
		List<Claim> klist = service.getKinderClaimList(kpageInfo, param);
		
//		System.out.println("klist(불편사항) : " + klist);
		model.addAttribute("klist", klist);
		model.addAttribute("kPageInfo", kpageInfo);
		model.addAttribute("param", param);	
		
		int admissionCount = service.getNAdmissionCount(param) + service.getKAdmissionCount(param);
		int wishCount = service.getNurseryWishCount(param) + service.getKinderWishCount(param);
		int claimCount = ncount + kcount;
		int boardCount = service.getBoardCount(param);
		int rentalCount = service.getRentalCount(param);
		int cartCount = service.getCartCount(param);
		model.addAttribute("admissionCount", admissionCount);
		model.addAttribute("wishCount", wishCount);
		model.addAttribute("claimCount", claimCount);
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("rentalCount", rentalCount);
		model.addAttribute("cartCount", cartCount);
		
		return "mypage-4";
	}
	
	@RequestMapping("/mypage-claim-delete")
	public String claimDelete(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		String type = param.get("type");
		int no = Integer.parseInt(param.get("no"));
		
		int result = 0;
		
		if(type.equals("어린이집")) {
			result = service.deleteNurseryClaim(no, savePath);
		} else {
			result = service.deleteKinderClaim(no, savePath);
		}
		
		if(result > 0) {
			model.addAttribute("msg", "불편사항 신고글 삭제가 정상적으로 완료되었습니다.");
		} else {
			model.addAttribute("msg", "불편사항 신고글 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-4");
		
		return "common/msg";
	}
	
	@GetMapping("/mypage-5")
	public String mypageView5(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, 
			@RequestParam Map<String, String> param) {
		if (loginMember == null) {
			return "/login";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		int page = 1;
		try {
			page = Integer.parseInt(param.get("page"));
		} catch (Exception e) {}
		
		int boardCount = service.getBoardCount(param);
		PageInfo pageInfo = new PageInfo(page, 5, boardCount, 8);
		List<Board> list = service.getBoardList(pageInfo, param);

//		System.out.println("list : " + list);
		model.addAttribute("list", list);
		model.addAttribute("param", param);
		model.addAttribute("pageInfo", pageInfo);
		
		int admissionCount = service.getNAdmissionCount(param) + service.getKAdmissionCount(param);
		int wishCount = service.getNurseryWishCount(param) + service.getKinderWishCount(param);
		int claimCount = service.getNurseryClaimCount(param) + service.getKinderClaimCount(param);
		int rentalCount = service.getRentalCount(param);
		int cartCount = service.getCartCount(param);
		model.addAttribute("admissionCount", admissionCount);
		model.addAttribute("wishCount", wishCount);
		model.addAttribute("claimCount", claimCount);
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("rentalCount", rentalCount);
		model.addAttribute("cartCount", cartCount);
		
		return "mypage-5";
	}
	
	@RequestMapping("/mypage-board-delete")
	public String boardDelete(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		int boardNo = Integer.parseInt(param.get("boardNo"));
		
		int result = service.deleteBoard(boardNo, savePath);
		
		if(result > 0) {
			model.addAttribute("msg", "게시글 삭제가 정상적으로 완료되었습니다.");
		} else {
			model.addAttribute("msg", "게시글 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-5");
		
		return "common/msg";
	}
	
//	@RequestMapping("/delete")
//	public String deleteBoard(Model model,  HttpSession session,
//			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//			int boardNo
//			) {
//		log.info("게시글 삭제 요청 boardNo : " + boardNo);
//		int result = service.deleteBoard(boardNo, savePath);
//		
//		if(result > 0) {
//			model.addAttribute("msg", "게시글 삭제가 정상적으로 완료되었습니다.");
//		}else {
//			model.addAttribute("msg", "게시글 삭제에 실패하였습니다.");
//		}
//		model.addAttribute("location", "/mypage-5");
//		return "common/msg";
//	}
	
	@GetMapping("/mypage-6")
	public String mypageView6(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, 
			@RequestParam Map<String, String> param) {
		if (loginMember == null) {
			return "/login";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		int page = 1;
		try {
			page = Integer.parseInt(param.get("page"));
		} catch (Exception e) {}
		
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
		int num = list.size();
		
		System.out.println("list : " + list);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("num", num);
		model.addAttribute("param", param);	
		
		int admissionCount = service.getNAdmissionCount(param) + service.getKAdmissionCount(param);
		int wishCount = service.getNurseryWishCount(param) + service.getKinderWishCount(param);
		int claimCount = service.getNurseryClaimCount(param) + service.getKinderClaimCount(param);
		int boardCount = service.getBoardCount(param);
		int rentalCount = service.getRentalCount(param);
		int cartCount = service.getCartCount(param);
		model.addAttribute("admissionCount", admissionCount);
		model.addAttribute("wishCount", wishCount);
		model.addAttribute("claimCount", claimCount);
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("rentalCount", rentalCount);
		model.addAttribute("cartCount", cartCount);

		return "mypage-6";
	}
	
	@RequestMapping("/mypage-rental-update")
	public String rentalUpdate(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		int payNo = Integer.parseInt(param.get("payNo"));
		
		int result = service.updateRental(payNo);
		
		if(result > 0) {
			model.addAttribute("msg", "반납처리가 정상적으로 진행되었습니다.");
		} else {
			model.addAttribute("msg", "반납처리 진행에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-6");
		
		return "common/msg";
	}
	
	@RequestMapping("/mypage-rental-delete")
	public String rentalDelete(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		int payNo = Integer.parseInt(param.get("payNo"));
		int toyNo = Integer.parseInt(param.get("toyNo"));
		
		int result = service.deleteRental(payNo);
		int result2 = service.updateToyType(toyNo);
		
		if(result > 0 && result2 > 0) {
			model.addAttribute("msg", "대여가 취소 되었습니다.");
		} else {
			model.addAttribute("msg", "대여취소에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-6");
		
		return "common/msg";
	}
	
	@GetMapping("/mypage-7")
	public String mypageView7(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, 
			@RequestParam Map<String, String> param) {
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
			int p = ((Integer.parseInt(c.getToyPay()) / 1000) * 100);
			if (c.getStartDate() != null || c.getEndDate() != null) {
			long sec = (c.getEndDate().getTime() - c.getStartDate().getTime()) / 1000;
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
		
		
		int admissionCount = service.getNAdmissionCount(param) + service.getKAdmissionCount(param);
		int wishCount = service.getNurseryWishCount(param) + service.getKinderWishCount(param);
		int claimCount = service.getNurseryClaimCount(param) + service.getKinderClaimCount(param);
		int boardCount = service.getBoardCount(param);
		int rentalCount = service.getRentalCount(param);
		int cartCount = service.getCartCount(param);
		model.addAttribute("admissionCount", admissionCount);
		model.addAttribute("wishCount", wishCount);
		model.addAttribute("claimCount", claimCount);
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("rentalCount", rentalCount);
		model.addAttribute("cartCount", cartCount);
		
		
		return "mypage-7";
	}
	
	@RequestMapping("/deleteCart")
	public String deleteCart(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			int no
			) {
		log.info("장바구니 삭제 요청");
		if (loginMember == null) {
			return "/";
		}
		
		int result = service.deleteCart(no);
		
		if(result > 0) {
			model.addAttribute("msg", "장바구니 삭제가 정상적으로 완료되었습니다.");
		}else {
			model.addAttribute("msg", "장바구니 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-7");
		
		return "/common/msg";
	}
	
	@RequestMapping("/deleteAllCart")
	public String deleteAllCart(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember
			) {
		log.info("장바구니 전체 삭제 요청");
		
		int result = service.deleteAllCart();
		
		if(result > 0) {
			model.addAttribute("msg", "장바구니 목록이 전체 삭제되었습니다.");
		}else {
			model.addAttribute("msg", "장바구니 목록 전체 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-7");
		
		return "/common/msg";
	}
	

}
