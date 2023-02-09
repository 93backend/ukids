package com.multi.ukids.hospital.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.hospital.model.vo.Hospital;
import com.multi.ukids.hospital.model.vo.NightCare;

@Mapper
public interface HospitalMapper {
	List<Hospital> selectHospitalList(Map<String, String> map);
	List<NightCare> selectNightCareList(Map<String, String> map);
	
	int selectHospitalCount(Map<String, String> map);	
	int selectNightCareCount(Map<String, String> map);	
	
	Hospital selectHospitalByNo(int no);
	NightCare selectNightCareByNo(int no);
	
	List<Hospital> selectNearHospitalList(Map<String, Object> temp);
	List<NightCare> selectNearNightCareList(Map<String, Object> temp);
	
}
