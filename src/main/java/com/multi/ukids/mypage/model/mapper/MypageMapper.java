package com.multi.ukids.mypage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.ukids.kinder.model.vo.KAdmission;
import com.multi.ukids.nursery.model.vo.NAdmission;


@Mapper
public interface MypageMapper {
	
	// mypage2 - 입소 신청 조회
	List<KAdmission> selectKAdmissionList(Map<String, Object> map);
	int selectKAdmissionCount(Map<String, Object> map);
	int deleteKAdmission(int no);
	List<NAdmission> selectNAdmissionList(Map<String, Object> map);
	int selectNAdmissionCount(Map<String, Object> map);
	int deleteNAdmission(int no);
	
	// mypage3 - 찜
	
}
