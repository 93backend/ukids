package com.multi.ukids.kinder.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.kinder.model.service.KinderService;
import com.multi.ukids.kinder.model.vo.KAdmission;
import com.multi.ukids.kinder.model.vo.KReview;
import com.multi.ukids.kinder.model.vo.KWish;
import com.multi.ukids.kinder.model.vo.Kinder;
import com.multi.ukids.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class KinderController {
	@Autowired
	private KinderService kinderService;
	
	private static List<Integer> kinderCompareNo = new ArrayList<>();
	private static final int MAX_SIZE = 4;
	
	// 유치원 검색
	@GetMapping("/kinder-main")
	public String kinderMain(Model model, HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@RequestParam Map<String, Object> param,
		@RequestParam(required = false) String[] build,
		@RequestParam(required = false) String[] classroom,
		@RequestParam(required = false) String[] teacher,
		@RequestParam(required = false) String[] service,
		@RequestParam(required = false) String[] facility
	) {
		log.info("유치원 검색");
		
		int page = 1;
		try {
			if(build != null) {
				param.put("build", Arrays.asList(build));
			}
			if(classroom != null) {
				for(int i = 0; i < classroom.length; i++) {
					param.put(classroom[i], classroom[i]);
				}
			}
			if(teacher != null) {
				for(int i = 0; i < teacher.length; i++) {
					param.put(teacher[i], teacher[i]);
				}
			}
			if(service != null) {
				for(int i = 0; i < service.length; i++) {
					param.put(service[i], service[i]);
				}
			}
			if(facility != null) {
				for(int i = 0; i < facility.length; i++) {
					param.put(facility[i], facility[i]);
				}
			}
			if(param.get("page") != null) {
				page = Integer.parseInt((String) param.get("page"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int count = kinderService.getKinderCount(param);
		PageInfo pageInfo = new PageInfo(page, 5, count, 12);
		List<Kinder> list = kinderService.getKinderList(pageInfo, param);
		
		int[] wishNo = {};
		if(loginMember != null) {
			wishNo = kinderService.getKinderWishLsit(loginMember.getMemberNo());
		}
		String wish = Arrays.toString(wishNo);
		log.info("찜 목록 : " + wish);
		
		String compare = null;
		if(kinderCompareNo != null) {
			compare = kinderCompareNo.toString();
		}
		log.info("비교 목록 : " + compare);
		
		int[] img = new int[12];
		
		for(int i = 0; i < img.length; i++) {
			img[i] = (int)(Math.random()*30);
			for(int j = 0; j < i; j++) {
				if(img[i] == img[j]) {
					i--;
					break;
				}
			}
		}
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("wish", wish);
		model.addAttribute("compare", compare);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("param", param);
		model.addAttribute("img", img);
		
		return "/kinder-main";
	}
	
	// 유치원 상세보기
	@GetMapping("/kinder-detail")
	public String kinderDetail(Model model, HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@RequestParam("no") int no, @RequestParam("i") int i
	) {
		log.info("유치원 상세보기");
		
		Kinder kinder = kinderService.findByNo(no);
		int claim = kinderService.getClaimCount(no);
		List<KReview> review = kinderService.getReviewList(no);
		int reviewCnt = kinderService.getReviewCount(no);
		
		int wishCnt = 0;
		if(loginMember != null) {
			KWish wish = new KWish();
			wish.setKinNo(no);
			wish.setMemberNo(loginMember.getMemberNo());
			wishCnt = kinderService.getWish(wish);
		}
		
		String compare = null;
		if(kinderCompareNo != null) {
			compare = kinderCompareNo.toString();
		}
		log.info("비교 목록 : " + compare);
		
		int classCnt = kinder.getClcnt3() + kinder.getClcnt4() + kinder.getClcnt5() + kinder.getMixclcnt() + kinder.getShclcnt();
		int childCnt = kinder.getPpcnt3() + kinder.getPpcnt4() + kinder.getPpcnt5() + kinder.getMixppcnt() + kinder.getShppcnt();
		
		String edate = kinder.getEdate().substring(0, 4) + "-" + kinder.getEdate().substring(4, 6) + "-" + kinder.getEdate().substring(6);
		String odate = kinder.getOdate().substring(0, 4) + "-" + kinder.getOdate().substring(4, 6) + "-" + kinder.getOdate().substring(6);
		
		model.addAttribute("kinder", kinder);
		model.addAttribute("claim", claim);
		model.addAttribute("wishCnt", wishCnt);
		model.addAttribute("compare", compare);
		model.addAttribute("review", review);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("classCnt", classCnt);
		model.addAttribute("childCnt", childCnt);
		model.addAttribute("edate", edate);
		model.addAttribute("odate", odate);
		model.addAttribute("i", i);
		
		return "kinder-detail";
	}
	
	// 리뷰 쓰기
	@PostMapping("/kinder-writeReview")
	public String writeReview (Model model, HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@ModelAttribute KReview review,
		@RequestParam Map<String, String> param
	) {
		log.info("유치원 리뷰 쓰기");
	
		int i = Integer.parseInt(param.get("i"));
		int no = review.getKinNo();
		
		review.setMemberNo(loginMember.getMemberNo());
		
		int result = kinderService.saveReview(review);
		
		if(result > 0) {
			model.addAttribute("msg", "해당 유치원에 리뷰가 등록 되었습니다.");
		} else {
			model.addAttribute("msg", "해당 유치원 리뷰 등록에 실패하였습니다.");
		}
		
		model.addAttribute("location", "/kinder-detail?no=" + no +"&i=" + i);
		
		return "common/msg";
	}
	
	// 리뷰 삭제
	@RequestMapping("/kinder-deleteReview")
	public String deleteReview(Model model,  HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@RequestParam Map<String, String> param
	) {
		log.info("유치원 리뷰 삭제");
		
		int no = Integer.parseInt(param.get("no"));
		int kinNo = Integer.parseInt(param.get("kinNo"));
		int i = Integer.parseInt(param.get("i"));
		
		int result = kinderService.deleteReview(no);
		
		if(result > 0) {
			model.addAttribute("msg", "리뷰가 삭제되었습니다.");
		} else {
			model.addAttribute("msg", "리뷰 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/kinder-detail?no=" + kinNo +"&i=" + i);
		return "common/msg";
	}
	
	// 입소 신청
	@PostMapping("/kinder-enroll")
	public String enroll(Model model,  HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@ModelAttribute KAdmission admission,
		@RequestParam Map<String, String> param
	) {
		log.info("유치원 입소 신청");
		
		int i = Integer.parseInt(param.get("i"));
		String hopeDate = param.get("hDate");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyy-MM-dd");
		
		admission.setMemberNo(loginMember.getMemberNo());
		try {
			admission.setHopeDate(format.parse(hopeDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int result = kinderService.saveAdmission(admission);
		if(result > 0) {
			model.addAttribute("msg", "입소 신청되었습니다.");
		} else {
			model.addAttribute("msg", "입소 신청에 실패하였습니다.");
		}
		model.addAttribute("location", "/kinder-detail?no=" + admission.getKinNo() +"&i=" + i);
		return "common/msg";
	}
	
	// 찜 추가
	@PostMapping("/kinder-addWish")
	public ResponseEntity<Map<String, Object>> addWish(int no, int memberNo) {
		log.info("유치원 찜 추가 : " + no + " " + memberNo);
		
		KWish wish = new KWish();
		wish.setKinNo(no);
		wish.setMemberNo(memberNo);
		
		int result = kinderService.saveWish(wish);
		
		boolean add;
		if(result > 0) {
			add = true;
		} else {
			add = false;
		}
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("add", add);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	// 찜 삭제
	@PostMapping("/kinder-deleteWish")
	public ResponseEntity<Map<String, Object>> deleteWish(int no, int memberNo) {
		log.info("유치원 찜 삭제 : " + no + " " + memberNo);
		
		KWish wish = new KWish();
		wish.setKinNo(no);
		wish.setMemberNo(memberNo);
		
		int result = kinderService.deleteWish(wish);
		
		boolean remove;
		if(result > 0) {
			remove = true;
		} else {
			remove = false;
		}
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("remove", remove);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	// 비교 추가
	@PostMapping("/kinder-addCompare")
	public ResponseEntity<Map<String, Object>> addCompare(int no) {
		log.info("유치원 비교 추가 : " + no );
		
		if(kinderCompareNo.size() < MAX_SIZE) {
			kinderCompareNo.add(no);
		}
		
		boolean add;
		if(kinderCompareNo.contains(no) == true) {
			add = true;
		} else {
			add = false;
		}
		log.info(kinderCompareNo.toString());
		System.out.println(add);
		
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("add", add);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	// 비교 삭제
	@PostMapping("/kinder-deleteCompare")
	public ResponseEntity<Map<String, Object>> deleteCompare(int no) {
		log.info("유치원 비교 삭제 : " + no );
		
		int index = kinderCompareNo.indexOf(no);
		kinderCompareNo.remove(index);
		
		boolean remove;
		if(kinderCompareNo.contains(no) == false) {
			remove = true;
		} else {
			remove = false;
		}
		log.info(kinderCompareNo.toString());
		System.out.println(remove);
		
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("remove", remove);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
}
