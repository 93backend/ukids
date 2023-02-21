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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	
	private static final int MAX_SIZE = 4;
	
	// 유치원 검색
	@GetMapping("/kinder-main")
	public String kinderMain(Model model, HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@SessionAttribute(name = "kinderCompare", required = false) List<Integer> kinderCompare,
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
		boolean[] wish = new boolean[list.size()];
		if(loginMember != null) {
			wishNo = kinderService.getKinderWishLsit(loginMember.getMemberNo());
			for(int i = 0; i < list.size(); i++) {
				int flag = 0;
				for(int n : wishNo) {
					if(list.get(i).getKinNo() == n) {
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
		
		if(session.getAttribute("kinderCompare") == null) {
			List<Integer> initCompare = new ArrayList<>();
			session.setAttribute("kinderCompare", initCompare);
		}
		
		boolean[] compare = new boolean[list.size()];
		if(kinderCompare != null) {
			for(int i = 0; i < list.size(); i++) {
				int flag = 0;
				for(int n : kinderCompare) {
					if(list.get(i).getKinNo() == n) {
						flag = 1;
						break;
					}
				}
				if(flag == 1) {
					compare[i] = true;
				}
			}
		}
		log.info("비교 목록 : " + kinderCompare);
		
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
		@SessionAttribute(name = "kinderCompare", required = false) List<Integer> kinderCompare,
		@RequestParam("no") int no, @RequestParam("i") int i
	) {
		log.info("유치원 상세보기");
		
		Kinder kinder = kinderService.findByNo(no);
		int claim = kinderService.getClaimCount(no);
		List<KReview> review = kinderService.getReviewList(no);
		int reviewCnt = kinderService.getReviewCount(no);
		
		int wish = 0;
		if(loginMember != null) {
			KWish w = new KWish();
			w.setKinNo(no);
			w.setMemberNo(loginMember.getMemberNo());
			wish = kinderService.getWish(w);
		}
		
		boolean compare = false;
		if(kinderCompare != null) {
			int flag = 0;
			for(int n : kinderCompare) {
				if(no == n) {
					flag = 1;
					break;
				}
			}
			if(flag == 1) {
				compare = true;
			}
		}
		log.info("비교 목록 : " + kinderCompare);
		
		int classCnt = kinder.getClcnt3() + kinder.getClcnt4() + kinder.getClcnt5() + kinder.getMixclcnt() + kinder.getShclcnt();
		int childCnt = kinder.getPpcnt3() + kinder.getPpcnt4() + kinder.getPpcnt5() + kinder.getMixppcnt() + kinder.getShppcnt();
		
		String edate = kinder.getEdate().substring(0, 4) + "-" + kinder.getEdate().substring(4, 6) + "-" + kinder.getEdate().substring(6);
		String odate = kinder.getOdate().substring(0, 4) + "-" + kinder.getOdate().substring(4, 6) + "-" + kinder.getOdate().substring(6);
		
		model.addAttribute("kinder", kinder);
		model.addAttribute("claim", claim);
		model.addAttribute("wish", wish);
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
	public ResponseEntity<Map<String, Object>> addCompare(Model model, HttpSession session, int no,
		@SessionAttribute(name = "kinderCompare", required = false) List<Integer> kinderCompare
	) {
		log.info("유치원 비교 추가 : " + no );
		
		if(kinderCompare.size() < MAX_SIZE) {
			kinderCompare.add(no);
		}
		
		boolean add;
		if(kinderCompare.contains(no) == true) {
			add = true;
		} else {
			add = false;
		}
		log.info(kinderCompare.toString());
		
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("add", add);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	// 비교 삭제
	@PostMapping("/kinder-deleteCompare")
	public ResponseEntity<Map<String, Object>> deleteCompare(Model model, HttpSession session, int no,
		@SessionAttribute(name = "kinderCompare", required = false) List<Integer> kinderCompare
	) {
		log.info("유치원 비교 삭제 : " + no );
		
		int index = kinderCompare.indexOf(no);
		kinderCompare.remove(index);
		
		boolean remove;
		if(kinderCompare.contains(no) == false) {
			remove = true;
		} else {
			remove = false;
		}
		log.info(kinderCompare.toString());
		
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("remove", remove);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	@GetMapping("/kinder-deleteCompare")
	public String deleteCompare2(Model model, HttpSession session, int no,
		@SessionAttribute(name = "kinderCompare", required = false) List<Integer> kinderCompare
	) {
		log.info("유치원 비교 삭제 : " + no );
		
		int index = kinderCompare.indexOf(no);
		kinderCompare.remove(index);
		
		if(kinderCompare.contains(no) == false) {
			model.addAttribute("msg", "비교 목록에서 삭제되었습니다.");
		} else {
			model.addAttribute("msg", "삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/compare-kinder");
		
		return "common/msg";
	}
	
	// 비교 화면
	@SuppressWarnings("unchecked")
	@GetMapping("/compare-kinder")
	public String compareKinder(Model model, HttpSession session,
		@SessionAttribute(name = "kinderCompare", required = false) List<Integer> kinderCompare
	) {
		log.info("유치원 비교 화면");
		log.info("비교 목록 : " + kinderCompare);
		
		if(session.getAttribute("kinderCompare") == null) {
			List<Integer> initCompare = new ArrayList<>();
			session.setAttribute("kinderCompare", initCompare);
			kinderCompare = initCompare;
		}
		
		List<Kinder> list = new ArrayList<Kinder>();
		int[] claim = new int[kinderCompare.size()];
		if(kinderCompare.size() > 0) {
			for(int i = 0; i < kinderCompare.size(); i++) {
				Kinder kinder = kinderService.findByNo(kinderCompare.get(i));
				list.add(kinder);
				claim[i] = kinderService.getClaimCount(kinderCompare.get(i));
			}
		}
		
		
		@SuppressWarnings("rawtypes")
		List<List> infoList = new  ArrayList<>();
		if(list.size() > 0) {
			List<Object> l = new ArrayList<>();
// ======================================= 요약 정보 =======================================
			// 설립일
			l.add("설립일");
			for(Kinder k : list) {
				l.add(k.getEdate());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 개원일
			l.add("개원일"); 
			for(Kinder k : list) {
				l.add(k.getOdate());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 대표자명
			l.add("대표자명");
			for(Kinder k : list) {
				l.add(k.getRppnname());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 원장명
			l.add("원장명");
			for(Kinder k : list) {
				l.add(k.getLdgrname());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 설립유형
			l.add("설립유형");
			for(Kinder k : list) {
				l.add(k.getEstablish());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 관할 행정기관
			l.add("관할 행정기관");
			for(Kinder k : list) {
				l.add(k.getOfficeedu());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 운영기관
			l.add("운영기관");
			for(Kinder k : list) {
				l.add(k.getSubofficeedu());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 전화번호
			l.add("전화번호");
			for(Kinder k : list) {
				l.add(k.getTelno());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 홈페이지
			l.add("홈페이지");
			for(Kinder k : list) {
				l.add(k.getHpaddr());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 주소
			l.add("주소");
			for(Kinder k : list) {
				l.add(k.getAddr());
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 불편사항신고 이력
			l.add("불편사항신고 이력");
			for(int n : claim) {
				l.add(n + " 건");
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 제공 서비스 =======================================
			// 특수학급
			l.add("특수학급");
			for(Kinder k : list) {
				if(k.getShclcnt() != 0) {
					l.add("운영");
				} else {
					l.add("미운영");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 방과후 활동
			l.add("방과후 활동");
			for(Kinder k : list) {
				if(k.getAfsc_pros_lsn_dcnt() != 0) {
					l.add("운영");
				} else {
					l.add("미운영");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 통학 차량
			l.add("통학 차량");
			for(Kinder k : list) {
				if(k.getVhcl_oprn_yn().equals("Y")) {
					l.add("운영");
				} else {
					l.add("미운영");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 급식
			l.add("급식");
			for(Kinder k : list) {
				if(k.getMas_mspl_dclr_yn().equals("Y")) {
					l.add("운영");
				} else {
					l.add("미운영");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 체육장
			l.add("체육장");
			for(Kinder k : list) {
				if(k.getPhgrindrarea().equals("㎡") && k.getPhgrindrarea().equals("0㎡")) {
					l.add("운영");
				} else {
					l.add("미운영");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// CCTV
			l.add("CCTV");
			for(Kinder k : list) {
				if(k.getCctv_ist_yn().equals("Y")) {
					l.add("설치");
				} else {
					l.add("미설치");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 위생관리 및 안전점검 =======================================
			// 정기소독관리 대상
			l.add("정기소독관리 대상");
			for(Kinder k : list) {
				if(k.getFxtm_dsnf_trgt_yn().equals("Y")) {
					l.add("대상");
				} else {
					l.add("비대상");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 놀이시설안전검사 대상
			l.add("놀이시설안전검사 대상");
			for(Kinder k : list) {
				if(k.getPlyg_ck_yn().equals("Y")) {
					l.add("대상");
				} else {
					l.add("비대상");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 소방대피훈련 시행 기관
			l.add("소방대피훈련 시행 기관");
			for(Kinder k : list) {
				if(k.getFire_avd_yn().equals("Y")) {
					l.add("시행");
				} else {
					l.add("미시행");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 소방안전점검 완료
			l.add("소방안전점검 완료");
			for(Kinder k : list) {
				if(k.getFire_safe_yn().equals("Y")) {
					l.add("완료");
				} else {
					l.add("미완료");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 가스점검 완료
			l.add("가스점검 완료");
			for(Kinder k : list) {
				if(k.getGas_ck_yn().equals("Y")) {
					l.add("완료");
				} else {
					l.add("미완료");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 전기설비점검 완료
			l.add("전기설비점검 완료");
			for(Kinder k : list) {
				if(k.getElect_ck_yn().equals("Y")) {
					l.add("완료");
				} else {
					l.add("미완료");
				}
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 학급 현황 =======================================
			// 특수 학급
			l.add("특수 학급");
			for(Kinder k : list) {
				l.add(k.getShclcnt() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 혼합 학급
			l.add("혼합 학급");
			for(Kinder k : list) {
				l.add(k.getMixclcnt() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 3세 학급
			l.add("만 3세 학급");
			for(Kinder k : list) {
				l.add(k.getClcnt3() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 4세 학급
			l.add("만 4세 학급");
			for(Kinder k : list) {
				l.add(k.getClcnt4() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 5세 학급
			l.add("만 5세 학급");
			for(Kinder k : list) {
				l.add(k.getClcnt5() + " 개");
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 아동 현황 =======================================
			// 특수 아동
			l.add("특수 아동");
			for(Kinder k : list) {
				l.add(k.getShppcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 혼합
			l.add("혼합");
			for(Kinder k : list) {
				l.add(k.getMixppcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 3세 아동
			l.add("만 3세 아동");
			for(Kinder k : list) {
				l.add(k.getPpcnt3() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 4세 아동
			l.add("만 4세 아동");
			for(Kinder k : list) {
				l.add(k.getPpcnt4() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 5세 아동
			l.add("만 5세 아동");
			for(Kinder k : list) {
				l.add(k.getPpcnt5() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 모집 정원 현황 =======================================
			// 특수 학급 정원
			l.add("특수 학급 정원");
			for(Kinder k : list) {
				l.add(k.getSpcnfpcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 혼합 학급 정원
			l.add("혼합 학급 정원");
			for(Kinder k : list) {
				l.add(k.getMixfpcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 3세 학급 정원
			l.add("만 3세 학급 정원");
			for(Kinder k : list) {
				l.add(k.getAg3fpcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 4세 학급 정원
			l.add("만 4세 학급 정원");
			for(Kinder k : list) {
				l.add(k.getAg4fpcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 만 5세 학급 정원
			l.add("만 5세 학급 정원");
			for(Kinder k : list) {
				l.add(k.getAg5fpcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 교직원 현황 =======================================
			// 원장
			l.add("원장");
			for(Kinder k : list) {
				l.add(k.getDrcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 원감
			l.add("원감");
			for(Kinder k : list) {
				l.add(k.getAdcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 수석교사
			l.add("수석교사");
			for(Kinder k : list) {
				l.add(k.getHdst_thcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 보직교사
			l.add("보직교사");
			for(Kinder k : list) {
				l.add(k.getAsps_thcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 일반교사
			l.add("일반교사");
			for(Kinder k : list) {
				l.add(k.getGnrl_thcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 특수교사
			l.add("특수교사");
			for(Kinder k : list) {
				l.add(k.getSpcn_thcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 보건교사
			l.add("보건교사");
			for(Kinder k : list) {
				l.add(k.getNtcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 영양교사
			l.add("영양교사");
			for(Kinder k : list) {
				l.add(k.getNtrt_thcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 기간제교사
			l.add("기간제교사");
			for(Kinder k : list) {
				l.add(k.getShcnt_thcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 교사자격 현황 =======================================
			// 수석교사
			l.add("수석교사");
			for(Kinder k : list) {
				l.add(k.getHdst_tchr_qacnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 정교사 1급
			l.add("정교사 1급");
			for(Kinder k : list) {
				l.add(k.getRgth_gd1_qacnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 정교사 2급
			l.add("정교사 2급");
			for(Kinder k : list) {
				l.add(k.getRgth_gd2_qacnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 준교사
			l.add("준교사");
			for(Kinder k : list) {
				l.add(k.getAsth_qacnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
// ======================================= 근속연수 현황 =======================================
			// 1년 미만 교사
			l.add("1년 미만 교사");
			for(Kinder k : list) {
				l.add(k.getYy1_undr_thcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 1년 이상 ~ 2년 미만 교사
			l.add("1년 이상 ~ 2년 미만 교사");
			for(Kinder k : list) {
				l.add(k.getYy1_abv_yy2_undr_thcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 2년 이상 ~ 4년 미만 교사
			l.add("2년 이상 ~ 4년 미만 교사");
			for(Kinder k : list) {
				l.add(k.getYy2_abv_yy4_undr_thcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 4년 이상 ~ 6년 미만 교사
			l.add("4년 이상 ~ 6년 미만 교사");
			for(Kinder k : list) {
				l.add(k.getYy4_abv_yy6_undr_thcnt() + " 명");
			}
			infoList.add(l);
			l = new ArrayList<>();
			// 6년 이상 교사
			l.add("6년 이상 교사");
			for(Kinder k : list) {
				l.add(k.getYy6_abv_thcnt() + " 명");
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
		
		log.info("정보 목록 : " + infoList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String info = gson.toJson(infoList);
		
		
		int count = list.size();
		model.addAttribute("list", list);
		model.addAttribute("info", info);
		model.addAttribute("count", count);
		
		return "compare-kinder";
	}
	
}
