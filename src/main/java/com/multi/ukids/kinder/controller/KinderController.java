package com.multi.ukids.kinder.controller;

import java.util.Arrays;
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
import com.multi.ukids.kinder.model.service.KinderService;
import com.multi.ukids.kinder.model.vo.KReview;
import com.multi.ukids.kinder.model.vo.Kinder;
import com.multi.ukids.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class KinderController {
	@Autowired
	private KinderService kinderService;
	
	@GetMapping("/kinder-main")
	public String kinderMain(Model model, 
			@RequestParam Map<String, Object> param,
			@RequestParam(required = false) String[] build,
			@RequestParam(required = false) String[] classroom,
			@RequestParam(required = false) String[] teacher,
			@RequestParam(required = false) String[] service,
			@RequestParam(required = false) String[] facility
	) {
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
		
		int[] img = new int[12];
		
		img[0] = page;
		
		for(int i = 0; i < img.length; i++) {
			if(i != 0) {
				img[i] = img[i-1] + 2;
			}
			img[i] %= 30;
		}
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("param", param);
		model.addAttribute("img", img);
		
		return "/kinder-main";
	}
	
	@GetMapping("/kinder-detail")
	public String kinderDetail(Model model, @RequestParam("no") int no, @RequestParam("i") int i) {
		
		Kinder kinder = kinderService.findByNo(no);
		int claim = kinderService.getClaimCount(no);
		List<KReview> review = kinderService.getReviewList(no);
		int reviewCnt = kinderService.getReviewCount(no);
		
		int classCnt = kinder.getClcnt3() + kinder.getClcnt4() + kinder.getClcnt5() + kinder.getMixclcnt() + kinder.getShclcnt();
		int childCnt = kinder.getPpcnt3() + kinder.getPpcnt4() + kinder.getPpcnt5() + kinder.getMixppcnt() + kinder.getShppcnt();
		
		String edate = kinder.getEdate().substring(0, 4) + "-" + kinder.getEdate().substring(4, 6) + "-" + kinder.getEdate().substring(6);
		String odate = kinder.getOdate().substring(0, 4) + "-" + kinder.getOdate().substring(4, 6) + "-" + kinder.getOdate().substring(6);
		
		model.addAttribute("kinder", kinder);
		model.addAttribute("claim", claim);
		model.addAttribute("review", review);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("classCnt", classCnt);
		model.addAttribute("childCnt", childCnt);
		model.addAttribute("edate", edate);
		model.addAttribute("odate", odate);
		model.addAttribute("i", i);
		
		return "kinder-detail";
	}
	
	@PostMapping("/kinder-writeReview")
	public String writeReview (Model model, HttpSession session,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@ModelAttribute KReview review,
			@RequestParam Map<String, String> param
		) {
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
	
	@RequestMapping("/kinder-deleteReview")
	public String deleteReview(Model model,  HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@RequestParam Map<String, String> param
	) {
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
}
