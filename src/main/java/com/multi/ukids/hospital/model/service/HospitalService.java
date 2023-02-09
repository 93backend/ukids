package com.multi.ukids.hospital.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.ukids.common.util.PageInfo;
import com.multi.ukids.hospital.model.mapper.HospitalMapper;
import com.multi.ukids.hospital.model.vo.Hospital;
import com.multi.ukids.hospital.model.vo.NightCare;

@Service
public class HospitalService {
	@Autowired
	private HospitalMapper mapper;
	
	public List<Hospital> getHospitalList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectHospitalList(param);
	}
	public List<NightCare> getNightCareList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectNightCareList(param);
	}
	
	
	public int getHospitalCount(Map<String, String> param) {
		return mapper.selectHospitalCount(param);
	}
	public int getNightCareCount(Map<String, String> param) {
		return mapper.selectNightCareCount(param);
	}
	
	
	public Hospital findHpByNo(int no) {
		return mapper.selectHospitalByNo(no);
	}
	public NightCare findNcByNo(int no) {
		return mapper.selectNightCareByNo(no);
	}
	
	
	public List<Hospital> getNearHospitalList(Map<String, Object> temp) {
		return mapper.selectNearHospitalList(temp);
	}
	public List<NightCare> getNearNightCareList(Map<String, Object> temp) {
		return mapper.selectNearNightCareList(temp);
	}
	
	
}
