package com.multi.ukids.nursery.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.nursery.model.service.NurseryService;
import com.multi.ukids.nursery.model.vo.Nursery;

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
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("param", param);
		
		
		return "nursery-main";
	}
	
	@GetMapping("/nursery-detail")
	public String nurseryDetail(Model model, @RequestParam("no") int no) {
		
		Nursery nursery = nurseryService.findByNo(no);
		
		int classCnt = nursery.getClass_cnt_00() + nursery.getClass_cnt_01() + nursery.getClass_cnt_02() + nursery.getClass_cnt_03() + nursery.getClass_cnt_04() + nursery.getClass_cnt_05();
		int childCnt = nursery.getChild_cnt_00() + nursery.getChild_cnt_01() + nursery.getChild_cnt_02() + nursery.getChild_cnt_03() + nursery.getChild_cnt_04() + nursery.getChild_cnt_05();
		
		model.addAttribute("nursery", nursery);
		model.addAttribute("classCnt", classCnt);
		model.addAttribute("childCnt", childCnt);
		
		
		return "nursery-detail";
		
	}

}
