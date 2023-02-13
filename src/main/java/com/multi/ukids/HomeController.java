package com.multi.ukids;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.kinder.model.service.KinderService;
import com.multi.ukids.kinder.model.vo.Kinder;
import com.multi.ukids.member.model.vo.Member;
import com.multi.ukids.nursery.model.service.NurseryService;
import com.multi.ukids.nursery.model.vo.Nursery;

import jakarta.servlet.http.HttpSession;




@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private KinderService kinderService;
	@Autowired
	private NurseryService nurseryService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpSession session, @SessionAttribute(name = "loginMember", required = false) Member loginMember) {
		
		Map<String, Object> param = new HashMap<>();
		
		if(loginMember != null && loginMember.getRole().equals("ROLE_USER")) { // 사용자 주소
			String[] addrArray = loginMember.getAddress().split(" ");
			param.put("city", addrArray[0]);
			param.put("town", addrArray[1]);
		} 
		else { // 기본값
			param.put("city", "서울");
			param.put("town", "강남구");
		}
		String address = (String) param.get("city") + " " + (String) param.get("town");
		
		
		int nurseryCount = nurseryService.getNurseryCount(param);
		PageInfo pageInfo = new PageInfo(1, 1, nurseryCount, nurseryCount);
		List<Nursery> nursery =  nurseryService.getNurseryList(pageInfo, param);
		
		int kinderCount = kinderService.getKinderCount(param);
		pageInfo = new PageInfo(1, 1, kinderCount, kinderCount);
		List<Kinder> kinder = kinderService.getKinderList(pageInfo, param);
		
		// 국공립 / 사립
		int[] publicCnt = new int[2];
		int[] privateCnt = new int[2];
		for(Nursery n : nursery) {
			if(n.getCrtypename().contains("공립")) {
				publicCnt[0]++;
			} else {
				privateCnt[0]++;
			}
		}
		for(Kinder k : kinder) {
			if(k.getEstablish().contains("공립")) {
				publicCnt[1]++;
			} else {
				privateCnt[1]++;
			}
		}
		
		// 지역별 어린이집 / 유치원
		String[] location = {"서울", "인천", "경기", "강원", "충청", "전라", "경상"};
		int[] locationNCnt = new int[location.length];
		int[] locationKCnt = new int[location.length];
		
		param.remove("city");
		param.remove("town");
		for(int i = 0; i < location.length; i++) {
			param.put("city", location[i]);
			locationNCnt[i] = nurseryService.getNurseryCount(param);
			locationKCnt[i] = kinderService.getKinderCount(param);
		}
		
		model.addAttribute("address", address);
		model.addAttribute("nurseryCount", nurseryCount);
		model.addAttribute("nursery", nursery);
		model.addAttribute("kinderCount", kinderCount);
		model.addAttribute("kinder", kinder);
		model.addAttribute("publicCnt", publicCnt);
		model.addAttribute("privateCnt", privateCnt);
		model.addAttribute("locationNCnt", locationNCnt);
		model.addAttribute("locationKCnt", locationKCnt);
		
		return "index";
	}
	
}
