package com.multi.ukids.nursery.controller;

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

import com.multi.ukids.claim.model.vo.Claim;
import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.member.model.vo.Member;
import com.multi.ukids.nursery.model.service.NurseryService;
import com.multi.ukids.nursery.model.vo.NReview;
import com.multi.ukids.nursery.model.vo.Nursery;

import jakarta.servlet.http.HttpSession;

@Controller
public class NurseryController {
	@Autowired
	private NurseryService nurseryService;
	
	@GetMapping("/nursery-main")
	public String nurseryMain(Model model, 
			@RequestParam Map<String, Object> param,
			@RequestParam(required = false) String[] build,
			@RequestParam(required = false) String[] classroom,
			@RequestParam(required = false) String[] teacher,
			@RequestParam(required = false) String[] service
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
			if(param.get("page") != null) {
				page = Integer.parseInt((String) param.get("page"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int count = nurseryService.gettNurseryCount(param);
		PageInfo pageInfo = new PageInfo(page, 5, count, 12);
		List<Nursery> list =  nurseryService.getNurseryList(pageInfo, param);
		
		int[] img = new int[12];
		
		img[0] = page;
		
		for(int i = 0; i < img.length; i++) {
			if(i != 0) {
				img[i] = img[i-1] + 2;
			}
			img[i] %= 31;
		}
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("param", param);
		model.addAttribute("img", img);
		
		return "nursery-main";
	}
	
	@GetMapping("/nursery-detail")
	public String nurseryDetail(Model model, @RequestParam("no") int no, @RequestParam("i") int i) {
		
		Nursery nursery = nurseryService.findByNo(no);
		int claim = nurseryService.getClaimCount(no);
		List<NReview> review = nurseryService.getReviewList(no);
		int reviewCnt = nurseryService.getReviewCount(no);
		
		int classCnt = nursery.getClass_cnt_00() + nursery.getClass_cnt_01() + nursery.getClass_cnt_02() + nursery.getClass_cnt_03() + nursery.getClass_cnt_04() + nursery.getClass_cnt_05();
		int childCnt = nursery.getChild_cnt_00() + nursery.getChild_cnt_01() + nursery.getChild_cnt_02() + nursery.getChild_cnt_03() + nursery.getChild_cnt_04() + nursery.getChild_cnt_05();
		
		model.addAttribute("nursery", nursery);
		model.addAttribute("claim", claim);
		model.addAttribute("review", review);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("classCnt", classCnt);
		model.addAttribute("childCnt", childCnt);
		model.addAttribute("i", i);
		
		return "nursery-detail";
	}
	
	@PostMapping("/nursery-writeReview")
	public String writeReview (Model model, HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@ModelAttribute NReview review,
		@RequestParam Map<String, String> param
	) {
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
	
	@RequestMapping("/nursery-deleteReview")
	public String deleteReview(Model model,  HttpSession session,
		@SessionAttribute(name = "loginMember", required = false) Member loginMember,
		@RequestParam Map<String, String> param
	) {
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

}
