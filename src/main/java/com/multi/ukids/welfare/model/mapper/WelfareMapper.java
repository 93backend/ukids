package com.multi.ukids.welfare.model.mapper; 

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.welfare.model.vo.Welfare;

@Mapper
public interface WelfareMapper {
	List<Welfare> selectMainWelfareList(Map<String, Object> map);
	List<Welfare> selectWelfareList(Map<String, String> map);
	int selectWelfareCount(Map<String, String> map);	
	Welfare selectWelfareByNo(int no);
	List<Welfare> selectNearWelfareList(Welfare welfare);
}
