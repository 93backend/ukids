package com.multi.ukids.welfare.controller; 

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
	
	@Autowired
	private WelfareService welfareService;
	
	//이미지 랜덤 인덱스 생성
	public int[] createRdImgIdx(int length, int range, int imgIdx) {
		Random rd = new Random();
		int[] idx = new int[length];
		
		for (int i=0; i<idx.length; i++) {
			idx[i] = rd.nextInt(range) + 1;
			for (int j=0; j<i; j++) {
				if (idx[i] == idx[j] || idx[i] == imgIdx) {
					i--;
					break;
				}
			}
		}
		
		return idx;
	}
	
	@GetMapping("/welfare-main")
	public String welfareMain(Model model, @RequestParam Map<String, String> param) {
		int page = 1;
		
		try {
			page = Integer.parseInt(param.get("page"));
		} catch (Exception e) {}
		
		//아동복지시설 목록
		int count = welfareService.getWelfareCount(param);
		PageInfo pageInfo = new PageInfo(page, 5, count, 9);
		List<Welfare> list = welfareService.getWelfareList(pageInfo, param);
		
		//아동복지시설 랜덤 이미지
		int[] imgIdx = createRdImgIdx(9, 20, 0);
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("param", param);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("imgIdx", imgIdx);
		
		log.info("param : " + param.toString());
		log.info("count : " + Integer.toString(count));
		log.info(Arrays.toString(imgIdx));
		
		return "welfare-main";
	}
	
	@GetMapping("/welfare-detail")
	public String welfareDetail(Model model, @RequestParam("no") int no, @RequestParam("img") int imgIdx) {
		Welfare welfare = welfareService.findByNo(no);
		if(welfare == null) {
			return "redirect:error";
		}
		
		//상세정보 설립일자, 이메일 형식 변경
		String estbDe = welfare.getEstbDe();
		String formattedEstbDe = estbDe.substring(0, 4) + "."  + estbDe.substring(4, 6) + "." + estbDe.substring(6, 8);
		String mailAddr = welfare.getFcltMailAddr();
		String formattedMailAddr = "";
		if (mailAddr.length() > 4) {
			formattedMailAddr = mailAddr.substring(0, 8) + "@facility.com";
		} else {
			formattedMailAddr = mailAddr.substring(0, 4) + "@facility.com";
		}
		
		//근처아동복지시설 목록
		List<Welfare> nearWelfareList = welfareService.getNearWelfareList(welfare);
		Collections.shuffle(nearWelfareList);
		
		//근처아동복지시설 랜덤 이미지
		int[] nearWfImgIdx = createRdImgIdx(9, 20, imgIdx);
		
		model.addAttribute("welfare", welfare);
		model.addAttribute("imgIdx", imgIdx);
		model.addAttribute("formattedEstbDe", formattedEstbDe);
		model.addAttribute("formattedMailAddr", formattedMailAddr);
		model.addAttribute("nearWelfareList", nearWelfareList);
		model.addAttribute("nearWfImgIdx", nearWfImgIdx);
		
		return "welfare-detail";
	}
	
}