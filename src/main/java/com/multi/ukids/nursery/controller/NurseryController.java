package com.multi.ukids.nursery.controller;

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
import com.multi.ukids.member.model.vo.Member;
import com.multi.ukids.nursery.model.service.NurseryService;
import com.multi.ukids.nursery.model.vo.NAdmission;
import com.multi.ukids.nursery.model.vo.NReview;
import com.multi.ukids.nursery.model.vo.NWish;
import com.multi.ukids.nursery.model.vo.Nursery;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NurseryController {
	@Autowired
	private NurseryService nurseryService;
	
	private static final int MAX_SIZE = 4;
	
	// 어린이집 검색
	@GetMapping("/nursery-main")
	public String nurseryMain(Model model, HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@SessionAttribute(name = "nurseryCompare", required = false) List<Integer> nurseryCompare,
			@RequestParam Map<String, Object> param,
			@RequestParam(required = false) String[] build,
			@RequestParam(required = false) String[] classroom,
			@RequestParam(required = false) String[] teacher,
			@RequestParam(required = false) String[] service
	) {
		log.info("어린이집 검색");
		
		int page = 1;
		try {
			if(build != null) {
				param.put("build", "build");
				for(int i = 0; i < build.length; i++) {
					param.put(build[i], build[i]);
				}
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
			if(param.get("page") != null) {
				page = Integer.parseInt((String) param.get("page"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int count = nurseryService.getNurseryCount(param);
		PageInfo pageInfo = new PageInfo(page, 5, count, 12);
		List<Nursery> list =  nurseryService.getNurseryList(pageInfo, param);
		
		int[] wishNo = {};
		if(loginMember != null) {
			wishNo = nurseryService.getNurseryWishLsit(loginMember.getMemberNo());
		}
		String wish = Arrays.toString(wishNo);
		log.info("찜 목록 : " + wish);
		
		if(session.getAttribute("nurseryCompare") == null) {
			List<Integer> initCompare = new ArrayList<>();
			session.setAttribute("nurseryCompare", initCompare);
		}
		
		String compare = null;
		if(nurseryCompare != null) {
			compare = nurseryCompare.toString();
		}
		log.info("비교 목록 : " + compare);
		
		int[] img = new int[12];
		
		img[0] = page;
		
		for(int i = 0; i < img.length; i++) {
			img[i] = (int)(Math.random()*31);
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
		
		return "nursery-main";
	}
	
	// 어린이집 상세
	@GetMapping("/nursery-detail")
	public String nurseryDetail(Model model, HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@SessionAttribute(name = "nurseryCompare", required = false) List<Integer> nurseryCompare,
		@RequestParam("no") int no, @RequestParam("i") int i
	) {
		log.info("어린이집 상세");
		
		Nursery nursery = nurseryService.findByNo(no);
		int claim = nurseryService.getClaimCount(no);
		List<NReview> review = nurseryService.getReviewList(no);
		int reviewCnt = nurseryService.getReviewCount(no);
		
		int wishCnt = 0;
		if(loginMember != null) {
			NWish wish = new NWish();
			wish.setNuNo(no);
			wish.setMemberNo(loginMember.getMemberNo());
			wishCnt = nurseryService.getWish(wish);
		}
		
		String compare = null;
		if(nurseryCompare != null) {
			compare = nurseryCompare.toString();
		}
		log.info("비교 목록 : " + compare);
		
		int classCnt = nursery.getClass_cnt_00() + nursery.getClass_cnt_01() + nursery.getClass_cnt_02() + nursery.getClass_cnt_03() + nursery.getClass_cnt_04() + nursery.getClass_cnt_05();
		int childCnt = nursery.getChild_cnt_00() + nursery.getChild_cnt_01() + nursery.getChild_cnt_02() + nursery.getChild_cnt_03() + nursery.getChild_cnt_04() + nursery.getChild_cnt_05();
		
		model.addAttribute("nursery", nursery);
		model.addAttribute("claim", claim);
		model.addAttribute("wishCnt", wishCnt);
		model.addAttribute("compare", compare);
		model.addAttribute("review", review);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("classCnt", classCnt);
		model.addAttribute("childCnt", childCnt);
		model.addAttribute("i", i);
		
		return "nursery-detail";
	}
	
	// 리뷰 쓰기
	@PostMapping("/nursery-writeReview")
	public String writeReview(Model model, HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@ModelAttribute NReview review,
		@RequestParam Map<String, String> param
	) {
		log.info("어린이집 리뷰 쓰기");
		
		int i = Integer.parseInt(param.get("i"));
		int no = review.getNuNo();
		
		review.setMemberNo(loginMember.getMemberNo());
		
		int result = nurseryService.saveReview(review);
		
		if(result > 0) {
			model.addAttribute("msg", "해당 어린이집에 리뷰가 등록 되었습니다.");
		} else {
			model.addAttribute("msg", "해당 어린이집 리뷰 등록에 실패하였습니다.");
		}
		
		model.addAttribute("location", "/nursery-detail?no=" + no +"&i=" + i);
		
		return "common/msg";
	}
	
	// 리뷰 삭제
	@RequestMapping("/nursery-deleteReview")
	public String deleteReview(Model model,  HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@RequestParam Map<String, String> param
	) {
		log.info("어린이집 리뷰 삭제");
		
		int no = Integer.parseInt(param.get("no"));
		int nuNo = Integer.parseInt(param.get("nuNo"));
		int i = Integer.parseInt(param.get("i"));
		
		int result = nurseryService.deleteReview(no);
		
		if(result > 0) {
			model.addAttribute("msg", "리뷰가 삭제되었습니다.");
		} else {
			model.addAttribute("msg", "리뷰 삭제에 실패하였습니다.");
		}
		
		model.addAttribute("location", "/nursery-detail?no=" + nuNo +"&i=" + i);
		
		return "common/msg";
	}
	
	// 입소 신청
	@PostMapping("/nursery-enroll")
	public String enroll(Model model,  HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@ModelAttribute NAdmission admission,
		@RequestParam Map<String, String> param
	) {
		log.info("어린이집 입소 신청");
		
		int i = Integer.parseInt(param.get("i"));
		String hopeDate = param.get("hDate");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyy-MM-dd");
		
		admission.setMemberNo(loginMember.getMemberNo());
		try {
			admission.setHopeDate(format.parse(hopeDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int result = nurseryService.saveAdmission(admission);
		if(result > 0) {
			model.addAttribute("msg", "입소 신청되었습니다.");
		} else {
			model.addAttribute("msg", "입소 신청에 실패하였습니다.");
		}
		model.addAttribute("location", "/nursery-detail?no=" + admission.getNuNo() +"&i=" + i);
		return "common/msg";
	}
	
	// 찜 추가
	@PostMapping("/nursery-addWish")
	public ResponseEntity<Map<String, Object>> addWish(int no, int memberNo) {
		log.info("어린이집 찜 추가 : " + no + " " + memberNo);
		
		NWish wish = new NWish();
		wish.setNuNo(no);
		wish.setMemberNo(memberNo);
		
		int result = nurseryService.saveWish(wish);
		
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
	@PostMapping("/nursery-deleteWish")
	public ResponseEntity<Map<String, Object>> deleteWish(int no, int memberNo) {
		log.info("어린이집 찜 삭제 : " + no + " " + memberNo);
		
		NWish wish = new NWish();
		wish.setNuNo(no);
		wish.setMemberNo(memberNo);
		
		int result = nurseryService.deleteWish(wish);
		
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
	@PostMapping("/nursery-addCompare")
	public ResponseEntity<Map<String, Object>> addCompare(Model model, HttpSession session, int no,
		@SessionAttribute(name = "nurseryCompare", required = false) List<Integer> nurseryCompare
	) {
		log.info("어린이집 비교 추가 : " + no );
		
		if(nurseryCompare.size() < MAX_SIZE) {
			nurseryCompare.add(no);
		}
		
		boolean add;
		if(nurseryCompare.contains(no) == true) {
			add = true;
		} else {
			add = false;
		}
		log.info(nurseryCompare.toString());
		
		model.addAttribute("nurseryCompare", nurseryCompare);
		
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("add", add);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	// 비교 삭제
	@PostMapping("/nursery-deleteCompare")
	public ResponseEntity<Map<String, Object>> deleteCompare(Model model, HttpSession session, int no,
		@SessionAttribute(name = "nurseryCompare", required = false) List<Integer> nurseryCompare
	) {
		log.info("어린이집 비교 삭제 : " + no );
		
		int index = nurseryCompare.indexOf(no);
		nurseryCompare.remove(index);
		
		boolean remove;
		if(nurseryCompare.contains(no) == false) {
			remove = true;
		} else {
			remove = false;
		}
		log.info(nurseryCompare.toString());
		
		model.addAttribute("nurseryCompare", nurseryCompare);
		
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("remove", remove);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	@GetMapping("/nursery-deleteCompare") 
	public String deleteCompare2(Model model, HttpSession session, int no,
		@SessionAttribute(name = "nurseryCompare", required = false) List<Integer> nurseryCompare
	) {
		log.info("어린이집 비교 삭제 : " + no );
		
		int index = nurseryCompare.indexOf(no);
		nurseryCompare.remove(index);
		
		if(nurseryCompare.contains(no) == false) {
			model.addAttribute("msg", "비교 목록에서 삭제되었습니다.");
		} else {
			model.addAttribute("msg", "삭제에 실패하였습니다.");
		}
		model.addAttribute("nurseryCompare", nurseryCompare);
		model.addAttribute("location", "/compare-nursery");
		
		return "common/msg";
		
	}
	
	// 비교 화면
	@GetMapping("/compare-nursery")
	public String compareNursery(Model model, HttpSession session,
		@SessionAttribute(name = "nurseryCompare", required = false) List<Integer> nurseryCompare
	) {
		log.info("어린이집 비교 화면");
		log.info("비교 목록 : " + nurseryCompare.toString());
		
		if(session.getAttribute("nurseryCompare") == null) {
			List<Integer> initCompare = new ArrayList<>();
			session.setAttribute("nurseryCompare", initCompare);
			nurseryCompare = initCompare;
		}
		
		List<Nursery> list = new ArrayList<>();
		
		if(nurseryCompare.size() > 0) {
			for(int no : nurseryCompare) {
				Nursery nursery = nurseryService.findByNo(no);
				list.add(nursery);
			}
		}
		int count = list.size();
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		
		return("compare-nursery");
	}
	
}
