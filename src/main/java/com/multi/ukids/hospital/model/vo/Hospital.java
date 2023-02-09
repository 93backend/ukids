package com.multi.ukids.hospital.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {
	private int no;				//primary key
	private String hpid;		//기관ID
	private String hpAddr; 		//주소
	private String divName;		//병원분류명
	private String hpName; 		//기관명
	private String tel; 		//대표전화
	private String erTel; 		//응급실전화
	private String er; 			//응급실운영여부
	private String time1c;      //진료시간(월요일)C
	private String time2c;      //진료시간(화요일)C
	private String time3c;      //진료시간(수요일)C
	private String time4c;      //진료시간(목요일)C
	private String time5c;      //진료시간(금요일)C
	private String time6c;      //진료시간(토요일)C  
	private String time7c;      //진료시간(일요일)C  
	private String time8c;      //진료시간(공휴일)C  
	private String time1s;      //진료시간(월요일)S 
	private String time2s;      //진료시간(화요일)S  
	private String time3s;      //진료시간(수요일)S  
	private String time4s;      //진료시간(목요일)S  
	private String time5s;      //진료시간(금요일)S  
	private String time6s;      //진료시간(토요일)S  
	private String time7s;      //진료시간(일요일)S  
	private String time8s;      //진료시간(공휴일)S  
	private String post1; 		//우편번호1
	private String post2; 		//우편번호2
	private double lon; 		//병원경도
	private double lat; 		//병원위도
}
