package com.multi.ukids.mypage.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.multi.ukids.toy.model.vo.Pay;
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
	
	
	// ============================= mypage1 =============================
	@PostMapping("/member/update")
	public String update(Model model, 
			@ModelAttribute Member updateMember, // request에서 온 값
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, // 세션 값
			String password
			) {
		log.info("update 요청, updateMember : " + updateMember);
		if(loginMember == null) {
			model.addAttribute("msg","잘못된 접근입니다.");
			model.addAttribute("location","/");
			return "common/msg";
		}
		
		updateMember.setMemberNo(loginMember.getMemberNo());
		int result = memberService.save(updateMember);
		int result2 = memberService.updatePwd(loginMember, password);
		
		if(result > 0 && result2 > 0) {
			model.addAttribute("loginMember", memberService.findById(loginMember.getId())); // DB에서 있는 값을 다시 세션에 넣어주는 코드
			model.addAttribute("msg", "회원정보를 수정하였습니다.");
			model.addAttribute("location", "/mypage");
		}else {
			model.addAttribute("msg", "회원정보 수정에 실패하였습니다.");
			model.addAttribute("location", "/mypage");
		}
		return "common/msg";
	}
	
	@RequestMapping("/member/delete")
	public String delete(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember) {
		int result = memberService.delete(loginMember.getMemberNo());
		if(result > 0) {
			model.addAttribute("msg", "회원탈퇴에 성공하였습니다.");
			model.addAttribute("location","/logout");
		}else {
			model.addAttribute("msg", "회원탈퇴에 실패하였습니다.");
			model.addAttribute("location","/member/view");
		}
		return  "/common/msg";
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
		
		// USER
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
		
		
		// ADMIN
		int allClaimCount = service.getANurseryClaimCount() + service.getANurseryClaimCount();
		
		model.addAttribute("allClaimCount", allClaimCount);
		
		return "mypage";
	}
	
	
	// ============================= mypage2 =============================
	@GetMapping("/mypage-2")
	public String mypageView2( Model model, 
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, // 세션 값
			@RequestParam Map<String, String> param) {

		if (loginMember == null) {
			return "/login";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		int nPage = 1;
		int kPage = 1;
		int tnPage = 1;
		int tkPage = 1;
		try {
			nPage = Integer.parseInt(param.get("nPage"));
		} catch (Exception e) {}
		try {
			kPage = Integer.parseInt(param.get("kPage"));
		} catch (Exception e) {}
		try {
			tnPage = Integer.parseInt(param.get("tnPage"));
		} catch (Exception e) {}
		try {
			tkPage = Integer.parseInt(param.get("tkPage"));
		} catch (Exception e) {}
		
		// nursery
		int ncount = service.getNAdmissionCount(param);
		PageInfo npageInfo = new PageInfo(nPage, 1, ncount, 4);
		List<NAdmission> nlist = service.getNAdmissionList(npageInfo, param);
		
		System.out.println("nlist(입소신청) : " + nlist);
		model.addAttribute("nlist", nlist);
		model.addAttribute("npageInfo", npageInfo);
		
		// kinder
		int kcount = service.getKAdmissionCount(param);
		PageInfo kpageInfo = new PageInfo(kPage, 1, kcount, 4);
		List<KAdmission> klist = service.getKAdmissionList(kpageInfo, param);
		
		System.out.println("klist(입소신청) : " + klist);
		model.addAttribute("klist", klist);
		model.addAttribute("kpageInfo", kpageInfo);
		
		// nurseryTeacher
		int tncount = service.getTNAdmissionCount(param);
		PageInfo tnPageInfo = new PageInfo(tnPage, 5, tncount, 8);
		List<NAdmission> tnlist = service.getTNAdmissionList(tnPageInfo, param);
		
		System.out.println("tnlist(입소신청) : " + tnlist);
		model.addAttribute("tnlist", tnlist);
		model.addAttribute("tnPageInfo", tnPageInfo);
		
		
		int tkcount = service.getTKAdmissionCount(param);
		PageInfo tkpageInfo = new PageInfo(tkPage, 5, tkcount, 8);
		List<KAdmission> tklist = service.getTKAdmissionList(tkpageInfo, param);
		
		System.out.println("tklist(입소신청) : " + tklist);
		model.addAttribute("tklist", tklist);
		model.addAttribute("tkPageInfo", tkpageInfo);
		
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
	
	@GetMapping("mypage-add-info-nu")
	public ResponseEntity<List<NAdmission>> getNAdmissionInfo(Model model, 
			@SessionAttribute(name = "loginMember", required = false) Member loginMember) throws ParseException {
		Map<String, String> param = new HashMap<>();
		SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd");
		param.put("memberNo", "" + loginMember.getMemberNo());
		int ncount = service.getNAdmissionCount(param);
		System.out.println(ncount);
		PageInfo npageInfo = new PageInfo(1, 1000, ncount, 1000);
		List<NAdmission> nlist = service.getNAdmissionList(npageInfo, param);
		for (NAdmission n : nlist) {
			String newEnrollDate = newDate.format(n.getEnrollDate());
			n.setNewEnrollDate(newEnrollDate);
			String newHopeDate = newDate.format(n.getHopeDate());
			n.setNewHopeDate(newHopeDate);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(nlist);
	}
	
	@GetMapping("mypage-add-info-kin")
	public ResponseEntity<List<KAdmission>> getKAdmissionInfo(Model model, 
			@SessionAttribute(name = "loginMember", required = false) Member loginMember) throws ParseException {
		Map<String, String> param = new HashMap<>();
		SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd");
		param.put("memberNo", "" + loginMember.getMemberNo());
		int kcount = service.getKAdmissionCount(param);
		System.out.println(kcount);
		PageInfo kpageInfo = new PageInfo(1, 1000, kcount, 1000);
		List<KAdmission> klist = service.getKAdmissionList(kpageInfo, param);
		for (KAdmission k : klist) {
			String newEnrollDate = newDate.format(k.getEnrollDate());
			k.setNewEnrollDate(newEnrollDate);
			String newHopeDate = newDate.format(k.getHopeDate());
			k.setNewHopeDate(newHopeDate);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(klist);
	}
	
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
	
	@RequestMapping("/updateTNurseryAdmissionY")
	public String updateTNurseryAdmissionY(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		int no = Integer.parseInt(param.get("no"));
		
		int result = service.updateTNurseryAdmissionY(no);
		
		if(result > 0) {
			model.addAttribute("msg", "입소확정처리가 정상적으로 진행되었습니다.");
		} else {
			model.addAttribute("msg", "입소확정처리 진행에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-2");
		
		return "common/msg";
	}
	
	@RequestMapping("/updateTNurseryAdmissionN")
	public String updateTNurseryAdmissionN(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		int no = Integer.parseInt(param.get("no"));
		
		int result = service.updateTNurseryAdmissionN(no);
		
		if(result > 0) {
			model.addAttribute("msg", "입소거절처리가 정상적으로 진행되었습니다.");
		} else {
			model.addAttribute("msg", "입소거절처리 진행에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-2");
		
		return "common/msg";
	}
	
	@RequestMapping("/updateTKinderAdmissionY")
	public String updateTKinderAdmissionY(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		int no = Integer.parseInt(param.get("no"));
		
		int result = service.updateTKinderAdmissionY(no);
		
		if(result > 0) {
			model.addAttribute("msg", "입소확정처리가 정상적으로 진행되었습니다.");
		} else {
			model.addAttribute("msg", "입소확정처리 진행에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-2");
		
		return "common/msg";
	}
	
	@RequestMapping("/updateTKinderAdmissionN")
	public String updateTKinderAdmissionN(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		int no = Integer.parseInt(param.get("no"));
		
		int result = service.updateTKinderAdmissionN(no);
		
		if(result > 0) {
			model.addAttribute("msg", "입소거절처리가 정상적으로 진행되었습니다.");
		} else {
			model.addAttribute("msg", "입소거절처리 진행에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-2");
		
		return "common/msg";
	}
		
	
	// ============================= mypage3 =============================
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
			model.addAttribute("msg", "어린이집 찜목록이 전체 삭제되었습니다.");
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
			model.addAttribute("msg", "유치원이 찜목록이 전체 삭제되었습니다.");
		}
		model.addAttribute("location", "/mypage-3");
		
		return "/common/msg";
	}
	
	
	
	// ============================= mypage4 =============================
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
		int tnPage = 1;
		int tkPage = 1;
		int anPage = 1;
		int akPage = 1;
		
		try {
			nPage = Integer.parseInt(param.get("nPage"));
		} catch (Exception e) {}
		try {
			kPage = Integer.parseInt(param.get("kPage"));
		} catch (Exception e) {}
		try {
			tnPage = Integer.parseInt(param.get("tnPage"));
		} catch (Exception e) {}
		try {
			tkPage = Integer.parseInt(param.get("tkPage"));
		} catch (Exception e) {}
		try {
			anPage = Integer.parseInt(param.get("anPage"));
		} catch (Exception e) {}
		try {
			akPage = Integer.parseInt(param.get("akPage"));
		} catch (Exception e) {}
		
		// nursery
		int ncount = service.getNurseryClaimCount(param);
		PageInfo npageInfo = new PageInfo(nPage, 5, ncount, 4);
		List<Claim> nlist = service.getNurseryClaimList(npageInfo, param);
		
		System.out.println("nlist(불편사항) : " + nlist);
		model.addAttribute("nlist", nlist);
		model.addAttribute("nPageInfo", npageInfo);
		
		// kinder
		int kcount = service.getKinderClaimCount(param);
		PageInfo kpageInfo = new PageInfo(kPage, 5, kcount, 4);
		List<Claim> klist = service.getKinderClaimList(kpageInfo, param);
		
		System.out.println("klist(불편사항) : " + klist);
		model.addAttribute("klist", klist);
		model.addAttribute("kPageInfo", kpageInfo);
		
		// nursery-teacher
		int tncount = service.getTNurseryClaimCount(param);
		PageInfo tnpageInfo = new PageInfo(tnPage, 5, tncount, 8);
		List<Claim> tnlist = service.getTNurseryClaimList(tnpageInfo, param);
		
		System.out.println("tnlist(불편사항) : " + tnlist);
		model.addAttribute("tnlist", tnlist);
		model.addAttribute("tnPageInfo", tnpageInfo);
		
		// kinder-teacher
		int tkcount = service.getTKinderClaimCount(param);
		PageInfo tkpageInfo = new PageInfo(tkPage, 5, tkcount, 8);
		List<Claim> tklist = service.getTKinderClaimList(tkpageInfo, param);
		
		System.out.println("tklist(불편사항) : " + tklist);
		model.addAttribute("tklist", tklist);
		model.addAttribute("tkPageInfo", tkpageInfo);
		
		// nursery-admin
		int ancount = service.getANurseryClaimCount();
		PageInfo anpageInfo = new PageInfo(anPage, 5, ancount, 4);
		List<Claim> anlist = service.getANurseryClaimList(anpageInfo, param);
		
		System.out.println("anlist(불편사항) : " + anlist);
		model.addAttribute("anlist", anlist);
		model.addAttribute("anPageInfo", anpageInfo);
		
		// kinder-admin
		int akcount = service.getAKinderClaimCount();
		PageInfo akpageInfo = new PageInfo(akPage, 5, akcount, 4);
		List<Claim> aklist = service.getAKinderClaimList(akpageInfo, param);
		
		System.out.println("aklist(불편사항) : " + aklist);
		model.addAttribute("aklist", aklist);
		model.addAttribute("akPageInfo", akpageInfo);
		
		
		
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
	
	@RequestMapping("/updateTNurseryWish")
	public String tnclaimUpdate(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			int no
			) {
		log.info("claim 확인");
		
		int result = service.updateTNurseryClaim(no);
		
		if(result > 0) {
			model.addAttribute("msg", "해당 불편사항이 확인 완료되었습니다.");
		}else {
			model.addAttribute("msg", "해당 불편사항이 확인에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-4");
		
		return "/common/msg";
	}
	
	@RequestMapping("/updateTKinderClaim")
	public String tkclaimUpdate(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			int no
			) {
		log.info("claim 확인");
		
		int result = service.updateTKinderClaim(no);
		
		if(result > 0) {
			model.addAttribute("msg", "해당 불편사항이 확인 완료되었습니다.");
		}else {
			model.addAttribute("msg", "해당 불편사항이 확인에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-4");
		
		return "/common/msg";
	}
	
	
	
	// ============================= mypage5 =============================
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

		System.out.println("list : " + list);
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
	
	
	
	
	// ============================= mypage6 =============================
	@GetMapping("/mypage-6")
	public String mypageView6(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember, 
			@RequestParam Map<String, String> param) {
		if (loginMember == null) {
			return "/login";
		}
		param.put("memberNo", "" + loginMember.getMemberNo());
		
		//추가부분
		
		int mno = loginMember.getMemberNo();
		List<Pay> palist = service.getRentalList2(mno);
	    log.info("sssssssssssssssssss" + palist);	      
	    model.addAttribute("paylist", palist);
	    
	    List<Pay> adminlist = service.getRentalList3();
	    model.addAttribute("adminlist", adminlist);
	    System.out.println(adminlist);
		
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
		int toyNo = Integer.parseInt(param.get("toyNo"));
		
		int result = service.updateRental(toyNo);
		
		if(result > 0) {
			model.addAttribute("msg", "반납처리가 정상적으로 진행되었습니다.");
		} else {
			model.addAttribute("msg", "반납처리 진행에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-6");
		
		return "common/msg";
	}
	
	@RequestMapping("/mypage-status-t")
	public String updateToyStatusT(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		int toyNo = Integer.parseInt(param.get("toyNo"));
		
		int result = service.updateToyStatusT(toyNo);
		
		if(result > 0) {
			model.addAttribute("msg", "배송중처리가 정상적으로 진행되었습니다.");
		} else {
			model.addAttribute("msg", "배송중처리 진행에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-6");
		
		return "common/msg";
	}
	
	@RequestMapping("/mypage-status-y")
	public String updateToyStatusY(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		int toyNo = Integer.parseInt(param.get("toyNo"));
		
		int result = service.updateToyStatusY(toyNo);
		
		if(result > 0) {
			model.addAttribute("msg", "대여중처리가 정상적으로 진행되었습니다.");
		} else {
			model.addAttribute("msg", "대여중처리 진행에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-6");
		
		return "common/msg";
	}
	
	@RequestMapping("/mypage-status-n")
	public String updateToyStatusN(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		int toyNo = Integer.parseInt(param.get("toyNo"));
		
		int result2 = service.updateToyType(toyNo);
		int result = service.updateToyStatusN(toyNo);
		
		if(result > 0 && result2 > 0) {
			model.addAttribute("msg", "반납완료처리가 정상적으로 진행되었습니다.");
		} else {
			model.addAttribute("msg", "반납완료처리 진행에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-6");
		
		return "common/msg";
	}
	
	@RequestMapping("/mypage-rental-delete")
	public String rentalDelete(Model model,  HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> param
	) {
		int toyNo = Integer.parseInt(param.get("toyNo"));
		
		int result = service.updateToyType(toyNo);
		int result2 = service.updateToyStatus(toyNo);
		
		if(result > 0 && result2 > 0) {
			model.addAttribute("msg", "대여가 취소 되었습니다.");
		} else {
			model.addAttribute("msg", "대여취소에 실패하였습니다.");
		}
		model.addAttribute("location", "/mypage-6");
		
		return "common/msg";
	}
	
	
	
	
	// ============================= mypage7 =============================
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
			model.addAttribute("msg", "장바구니 목록이 전체 삭제되었습니다.");
		}
		model.addAttribute("location", "/mypage-7");
		
		return "/common/msg";
	}
	

}
