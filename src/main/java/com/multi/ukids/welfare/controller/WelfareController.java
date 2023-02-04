package com.multi.ukids.welfare.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.ukids.welfare.controller.WelfareController;
import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.welfare.model.service.WelfareService;
import com.multi.ukids.welfare.model.vo.Welfare;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WelfareController {
	
	@Autowired
	private WelfareService welfareService;
	
	@GetMapping("/welfare-main")
	public String welfareMain(Model model, @RequestParam Map<String, String> paramMap) {
		int page = 1;
		
		try {
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}
		
		int welfareCount = welfareService.getWelfareCount(paramMap);
		PageInfo pageInfo = new PageInfo(page, 5, welfareCount, 9);
		List<Welfare> list = welfareService.getWelfareList(pageInfo, paramMap);
		
		model.addAttribute("count", welfareCount);
		model.addAttribute("list", list);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("pageInfo", pageInfo);
		
		log.info("paramMap : " + paramMap.toString());
		log.info("welfareCount : " + Integer.toString(welfareCount));
		
		paramMap.put("searchValue", " ");
		
		return "welfare-main";
	}
	
	@GetMapping("/welfare-detail")
	public String welfareDetail(Model model) {
		return "welfare-detail";
	}
	
}