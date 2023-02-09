package com.multi.ukids.hospital.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.hospital.model.service.HospitalService;
import com.multi.ukids.hospital.model.vo.Hospital;
import com.multi.ukids.hospital.model.vo.NightCare;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HospitalController {
	@Autowired
	private HospitalService hospitalService;
	
	//이미지 랜덤 인덱스 생성
	public int[] createRdImgIdx(int length, int count) {
		Random rd = new Random();
		int[] idx = new int[length];
		
		for (int i=0; i<idx.length; i++) {
			idx[i] = rd.nextInt(count) + 1;
			for (int j=0; j<i; j++) {
				if (idx[i] == idx[j]) {
					i--;
					break;
				}
			}
		}
		
		return idx;
	}
	
	//평일 진료시간 형식 변경
	public void changeWdTime(Hospital item) {
		if (item.getTime1s().equals("-") == false) {
			item.setTime1s(item.getTime1s().substring(0, 2) + ":" + item.getTime1s().substring(2));
			item.setTime1c(item.getTime1c().substring(0, 2) + ":" + item.getTime1c().substring(2));
		}
	}
	//주말 진료시간 형식 변경
	public void changeWeTime(Hospital item) {
		if (item.getTime7s().equals("-") == false) { 
			item.setTime7s(item.getTime7s().substring(0, 2) + ":" + item.getTime7s().substring(2));
			item.setTime7c(item.getTime7c().substring(0, 2) + ":" + item.getTime7c().substring(2));
		}
	}
	
	@GetMapping("/hospital-main")
	public String HospitalMain(Model model, @RequestParam Map<String, String> param) {
		int page = 1;
		
		try {
			page = Integer.parseInt(param.get("page"));
		} catch (Exception e) {}
		
		//병원 목록
		int count = hospitalService.getHospitalCount(param) + hospitalService.getNightCareCount(param);
		PageInfo pageInfo1 = param.containsKey("nightcore") ? new PageInfo(page, 5, count, 0) : new PageInfo(page, 5, count, 9);
		PageInfo pageInfo2 = param.containsKey("nightcore") ? new PageInfo(page, 5, count, 12) : new PageInfo(page, 5, count, 3);
		List<Hospital> hpList = hospitalService.getHospitalList(pageInfo1, param);    //소아과진료
		List<NightCare> ncList = hospitalService.getNightCareList(pageInfo2, param);  //야간진료
		
		//병원 랜덤 이미지
		int[] hpImgIdx = createRdImgIdx(9, 18);
		int[] ncImgIdx = createRdImgIdx(12, 12);
		
		//일반병원 진료시간 형식 변경
		for (Hospital item : hpList) {
			changeWdTime(item);
			changeWeTime(item);
		}
		
		model.addAttribute("count", count);
		model.addAttribute("hpList", hpList);
		model.addAttribute("ncList", ncList);
		model.addAttribute("param", param);
		model.addAttribute("pageInfo", pageInfo1);
		model.addAttribute("hpImgIdx", hpImgIdx);
		model.addAttribute("ncImgIdx", ncImgIdx);
		
		log.info("hpList : " + hpList.size());
		log.info("ncList : " + ncList.size());
		
		return "hospital-main";
	}
	
	@GetMapping("/hospital-detail")
	public String HospitalDetail(Model model, 
			@RequestParam("no") int no, 
			@RequestParam("img") int imgIdx,
			@RequestParam("type") String type
	) {		
		Hospital hospital = hospitalService.findHpByNo(no);
		NightCare nightcare = hospitalService.findNcByNo(no);
		
		String formattedEr = "";
		String formattedOpDate = "";
		
		List<Hospital> nearHospitalList = new ArrayList<>();
		List<NightCare> nearNightCareList = new ArrayList<>();
		Map<String, Object> temp = new HashMap<>(); 
		
		if (type.equals("hp")) {
			//일반병원 상세정보 응급실운영여부, 진료시간 형식 변경
			formattedEr = Integer.parseInt(hospital.getEr()) > 0 ? "운영" : "미운영";
			changeWdTime(hospital);
			changeWeTime(hospital);
			
			//근처어린이병원(일반병원) 목록
			temp.put("post1", hospital.getPost1());
			temp.put("no", hospital.getNo());
			nearHospitalList = hospitalService.getNearHospitalList(temp);
			nearNightCareList = hospitalService.getNearNightCareList(temp);
		}
		if (type.equals("nc")) {
			//야간진료병원 개설일자 형식 변경
			formattedOpDate = nightcare.getOpenDate().substring(0, 4) + "."  + nightcare.getOpenDate().substring(4, 6) + "." + nightcare.getOpenDate().substring(6);
			
			//근처어린이병원(야간진료병원) 목록
			temp.put("post1", nightcare.getPost1());
			temp.put("no", nightcare.getNo());
			nearHospitalList = hospitalService.getNearHospitalList(temp);
			nearNightCareList = hospitalService.getNearNightCareList(temp);
		}
		
		//근처어린이병원(일반병원) 진료시간 형식 변경
		for (Hospital item : nearHospitalList) {
			changeWdTime(item);
			changeWeTime(item);
		}
		
		Collections.shuffle(nearHospitalList);
		Collections.shuffle(nearNightCareList);
		
		//근처어린이병원 랜덤이미지
		int[] nearHpImgIdx = createRdImgIdx(9, 18);
		int[] nearNcImgIdx = createRdImgIdx(3, 12);
		
		model.addAttribute("type", type);
		model.addAttribute("hospital", hospital);
		model.addAttribute("nightcare", nightcare);
		model.addAttribute("formattedEr", formattedEr);
		model.addAttribute("formattedOpDate", formattedOpDate);
		model.addAttribute("imgIdx", imgIdx);
		model.addAttribute("nearHospitalList", nearHospitalList);
		model.addAttribute("nearNightCareList", nearNightCareList);
		model.addAttribute("nearHpImgIdx", nearHpImgIdx);
		model.addAttribute("nearNcImgIdx", nearNcImgIdx);
		
		log.info("nearHospitalList : " + nearHospitalList.size());
		log.info("nearNightCareList : " + nearNightCareList.size());
		
		return "hospital-detail";
	}
}
