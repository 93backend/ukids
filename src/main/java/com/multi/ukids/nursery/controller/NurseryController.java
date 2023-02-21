package com.multi.ukids.nursery.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
		boolean[] wish = new boolean[list.size()];
		if(loginMember != null) {
			wishNo = nurseryService.getNurseryWishLsit(loginMember.getMemberNo());
			for(int i = 0; i < list.size(); i++) {
				int flag = 0;
				for(int n : wishNo) {
					if(list.get(i).getNo() == n) {
						flag = 1;
						break;
					}
				}
				if(flag == 1) {
					wish[i] = true;
				}
			}
		}
		log.info("찜 목록 : " + wishNo);
		
		if(session.getAttribute("nurseryCompare") == null) {
			List<Integer> initCompare = new ArrayList<>();
			session.setAttribute("nurseryCompare", initCompare);
		}
		
		boolean[] compare = new boolean[list.size()];
		if(nurseryCompare != null) {
			for(int i = 0; i < list.size(); i++) {
				int flag = 0;
				for(int n : nurseryCompare) {
					if(list.get(i).getNo() == n) {
						flag = 1;
						break;
					}
				}
				if(flag == 1) {
					compare[i] = true;
				}
			}
		}
		log.info("비교 목록 : " + nurseryCompare);
		
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
		
		int wish = 0;
		if(loginMember != null) {
			NWish w = new NWish();
			w.setNuNo(no);
			w.setMemberNo(loginMember.getMemberNo());
			wish = nurseryService.getWish(w);
		}
		
		boolean compare = false;
		if(nurseryCompare != null) {
			int flag = 0;
			for(int n : nurseryCompare) {
				if(no == n) {
					flag = 1;
					break;
				}
			}
			if(flag == 1) {
				compare = true;
			}
		}
		log.info("비교 목록 : " + nurseryCompare);
		
		int classCnt = nursery.getClass_cnt_00() + nursery.getClass_cnt_01() + nursery.getClass_cnt_02() + nursery.getClass_cnt_03() + nursery.getClass_cnt_04() + nursery.getClass_cnt_05();
		int childCnt = nursery.getChild_cnt_00() + nursery.getChild_cnt_01() + nursery.getChild_cnt_02() + nursery.getChild_cnt_03() + nursery.getChild_cnt_04() + nursery.getChild_cnt_05();
		
		model.addAttribute("nursery", nursery);
		model.addAttribute("claim", claim);
		model.addAttribute("wish", wish);
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
	@SuppressWarnings("unchecked")
	@GetMapping("/compare-nursery")
	public String compareNursery(Model model, HttpSession session,
		@SessionAttribute(name = "nurseryCompare", required = false) List<Integer> nurseryCompare
	) {
		log.info("어린이집 비교 화면");
		log.info("비교 목록 : " + nurseryCompare);
		
		if(session.getAttribute("nurseryCompare") == null) {
			List<Integer> initCompare = new ArrayList<>();
			session.setAttribute("nurseryCompare", initCompare);
			nurseryCompare = initCompare;
		}
		
		List<Nursery> list = new ArrayList<>();
		int[] claim = new int[nurseryCompare.size()];
		if(nurseryCompare.size() > 0) {
			for(int i = 0; i < nurseryCompare.size(); i++) {
				Nursery nursery = nurseryService.findByNo(nurseryCompare.get(i));
				list.add(nursery);
				claim[i] = nurseryService.getClaimCount(nurseryCompare.get(i));
			}
		}
		
		@SuppressWarnings("rawtypes")
		List<List> infoList = new  ArrayList<>();
		
		if(list.size() > 0) {
			List<Object> l = new ArrayList<>();
// ======================================= 요약 정보 =======================================
			// 설립일
			l.add("설립일");
			for(Nursery n : list) {
				l.add(n.getCrcnfmdt());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 개원일
			l.add("개원일");
			for(Nursery n : list) {
				l.add(n.getCrcnfmdt());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 대표자명
			l.add("대표자명");
			for(Nursery n : list) {
				l.add(n.getCrrepname());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 설립유형
			l.add("설립유형");
			for(Nursery n : list) {
				l.add(n.getCrtypename());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 전화번호
			l.add("전화번호");
			for(Nursery n : list) {
				l.add(n.getCrtelno());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 홈페이지
			l.add("홈페이지");
			for(Nursery n : list) {
				l.add(n.getCrhome());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 주소
			l.add("주소");
			for(Nursery n : list) {
				l.add(n.getCraddr());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 불편사항신고 이력
			l.add("불편사항신고 이력");
			for(int c : claim) {
				l.add(c + " 건");
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 제공 서비스 =======================================
			// 특수학급
			l.add("특수학급");
			for(Nursery n : list) {
				if(n.getClass_cnt_sp() != 0) {
					l.add("운영");
				} else {
					l.add("미운영");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 방과후 활동
			l.add("방과후 활동");
			for(Nursery n : list) {
				if(n.getCrspec().contains("방과후")) {
					l.add("운영");
				} else {
					l.add("미운영");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 통학 차량
			l.add("통학 차량");
			for(Nursery n : list) {
				if(n.getCrcargbname().equals("운영")) {
					l.add("운영");
				} else {
					l.add("미운영");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 급식
			l.add("급식");
			for(Nursery n : list) {
				if(n.getEm_cnt_a7() != 0) {
					l.add("운영");
				} else {
					l.add("미운영");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 체육장
			l.add("체육장");
			for(Nursery n : list) {
				if(n.getPlgrdco() != 0) {
					l.add("운영");
				} else {
					l.add("미운영");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// CCTV
			l.add("CCTV");
			for(Nursery n : list) {
				if(n.getCctvinstlcnt() != 0) {
					l.add("설치");
				} else {
					l.add("미설치");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 학급 현황 =======================================
			// 특수 학급
			l.add("특수 학급");
			for(Nursery n : list) {
				l.add(n.getClass_cnt_sp() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 영아 혼합 학급
			l.add("영아 혼합 학급 (만 0세~2세)");
			for(Nursery n : list) {
				l.add(n.getClass_cnt_m2() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 유아 혼합 학급
			l.add("유아 혼합 학급 (만 3세~5세)");
			for(Nursery n : list) {
				l.add(n.getClass_cnt_m5() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 0세 학급
			l.add("만 0세 학급");
			for(Nursery n : list) {
				l.add(n.getClass_cnt_00() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 1세 학급
			l.add("만 1세 학급");
			for(Nursery n : list) {
				l.add(n.getClass_cnt_01() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 2세 학급
			l.add("만 2세 학급");
			for(Nursery n : list) {
				l.add(n.getClass_cnt_02() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 3세 학급
			l.add("만 3세 학급");
			for(Nursery n : list) {
				l.add(n.getClass_cnt_03() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 4세 학급
			l.add("만 4세 학급");
			for(Nursery n : list) {
				l.add(n.getClass_cnt_04() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 5세 학급
			l.add("만 5세 학급");
			for(Nursery n : list) {
				l.add(n.getClass_cnt_05() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 아동 현황 =======================================
			// 특수 아동
			l.add("특수 아동");
			for(Nursery n : list) {
				l.add(n.getChild_cnt_sp() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 영아 혼합 아동
			l.add("영아 혼합 (만 0세~2세)");
			for(Nursery n : list) {
				l.add(n.getChild_cnt_m2() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 유아 혼합 아동
			l.add("유아 혼합 (만 3세~5세)");
			for(Nursery n : list) {
				l.add(n.getChild_cnt_m5() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 0세 아동
			l.add("만 0세 아동");
			for(Nursery n : list) {
				l.add(n.getChild_cnt_00() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 1세 아동
			l.add("만 1세 아동");
			for(Nursery n : list) {
				l.add(n.getChild_cnt_01() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 2세 아동
			l.add("만 2세 아동");
			for(Nursery n : list) {
				l.add(n.getChild_cnt_02() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 3세 아동
			l.add("만 3세 아동");
			for(Nursery n : list) {
				l.add(n.getChild_cnt_03() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 4세 아동
			l.add("만 4세 아동");
			for(Nursery n : list) {
				l.add(n.getChild_cnt_04() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 5세 아동
			l.add("만 5세 아동");
			for(Nursery n : list) {
				l.add(n.getChild_cnt_05() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 교직원 현황 =======================================
			// 원장
			l.add("원장");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_a1() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 보육교사
			l.add("보육교사");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_a2() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 특수교사
			l.add("특수교사");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_a3() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 치료교사
			l.add("치료교사");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_a4() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 영양사
			l.add("영양사");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_a5() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 간호사
			l.add("간호사");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_a6() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 간호조무사
			l.add("간호조무사");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_a10() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 조리원
			l.add("조리원");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_a7() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 근속연수 현황 =======================================
			// 1년 미만 교사
			l.add("1년 미만 교사");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_0y() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 1년 이상 ~ 2년 미만 교사
			l.add("1년 이상 ~ 2년 미만 교사");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_1y() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 2년 이상 ~ 4년 미만 교사
			l.add("2년 이상 ~ 4년 미만 교사");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_2y() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 4년 이상 ~ 6년 미만 교사
			l.add("4년 이상 ~ 6년 미만 교사");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_4y() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 6년 이상 교사
			l.add("6년 이상 교사");
			for(Nursery n : list) {
				l.add(n.getEm_cnt_6y() + " 명");
			}
			infoList.add(l);
		}
		
		for(@SuppressWarnings("rawtypes") List l : infoList) {
			if(l.size() < 5) {
				for(int i = l.size(); i < 5; i++) {
					l.add("-");
				}
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String info = gson.toJson(infoList);
		
		log.info("어린이집 비교 정보 목록 : " + infoList);
		
		int count = list.size();
		model.addAttribute("list", list);
		model.addAttribute("info", info);
		model.addAttribute("count", count);
		
		return("compare-nursery");
	}
	
}
