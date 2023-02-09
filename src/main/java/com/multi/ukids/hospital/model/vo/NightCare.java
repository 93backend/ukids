package com.multi.ukids.hospital.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NightCare {
	private int no;				//primary key
	private String hpAddr; 		//주소
	private String divName;		//병원분류명
	private String hpName; 		//기관명
	private String tel; 		//대표전화
	private String post1; 		//우편번호
	private String openDate;	//개설일자
}
