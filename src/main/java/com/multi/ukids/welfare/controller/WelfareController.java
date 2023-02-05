package com.multi.ukids.welfare.controller; 

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.welfare.model.service.WelfareService;
import com.multi.ukids.welfare.model.vo.Welfare;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WelfareController {
	
	//아동복지시설
	@Autowired
	private WelfareService welfareService;
	
	@GetMapping("/welfare-main")
	public String welfareMain(Model model, @RequestParam Map<String, String> param) {
		int page = 1;
		
		try {
			page = Integer.parseInt(param.get("page"));
		} catch (Exception e) {}
		
		int welfareCount = welfareService.getWelfareCount(param);
		PageInfo pageInfo = new PageInfo(page, 5, welfareCount, 9);
		List<Welfare> list = welfareService.getWelfareList(pageInfo, param);
		
		model.addAttribute("count", welfareCount);
		model.addAttribute("list", list);
		model.addAttribute("param", param);
		model.addAttribute("pageInfo", pageInfo);
		
//		log.info("param : " + param.toString());
//		log.info("welfareCount : " + Integer.toString(welfareCount));
		
		return "welfare-main";
	}
	
	@GetMapping("/welfare-detail")
	public String welfareDetail(Model model, @RequestParam("no") int no) {
		Welfare welfare = welfareService.findByNo(no);
		if(welfare == null) {
			return "redirect:error";
		}
		
		//상세정보
		String estbDe = welfare.getEstbDe();
		String formattedEstbDe = estbDe.substring(0, 4) + "."  + estbDe.substring(4, 6) + "." + estbDe.substring(6, 8);
		
		//근처아동복지시설
		List<Welfare> nearWelfareList = welfareService.getNearWelfareList(welfare);
		Collections.shuffle(nearWelfareList);
		
		model.addAttribute("welfare", welfare);
		model.addAttribute("formattedEstbDe", formattedEstbDe);
		model.addAttribute("nearWelfareList", nearWelfareList);
		
//		for (Welfare data : nearWelfareList) {
//			log.info(data.toString());
//			log.info("\n");
//		}
		
		return "welfare-detail";
	}
	
//	@GetMapping("/error")
//	public String error() {
//		return "common/error";
//	}
	
}