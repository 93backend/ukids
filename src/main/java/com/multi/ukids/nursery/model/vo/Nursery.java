package com.multi.ukids.nursery.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nursery {
	private int no;					// INT AUTO_INCREMENT PRIMARY KEY,
	private String stcode;			// VARCHAR(100), -- 어린이집코드
	private String crname; 			// VARCHAR(100), -- 어린이집명
	private String crtypename; 		// VARCHAR(100), -- 어린이집유형
	private String crstatusname; 	// VARCHAR(100), -- 운영현황
	
	private String zipcode;			// VARCHAR(100), -- 우편번호
	private String craddr; 			// VARCHAR(100), -- 상세주소
	private String crtelno;			// VARCHAR(100), -- 전화번호
	private String crfaxno;			// VARCHAR(100), -- 팩스번호
	private String crhome; 			// VARCHAR(200), -- 홈페이지주소
	
	private int nrtrroomcnt;		// INT, -- 보육실수
	private int nrtrroomsize;		// INT, -- 보육실 면적
	private int plgrdco;			// INT, -- 놀이터수
	private int cctvinstlcnt;		// INT, -- CCTV총설치수
	private int chcrtescnt;			// INT, -- 보육교직원수
	
	private int crcapat;			// INT, -- 정원
	private int crchcnt;			// INT, -- 현원
	private double la; 				// DOUBLE, -- 시설 위도(좌표값)
	private double lo; 				// DOUBLE, -- 시설 경도(좌표값)
	private String crcargbname;		// VARCHAR(100), -- 통학차량운영여부
	
	private String crcnfmdt; 		// VARCHAR(100), -- 인가일자
	private String crpausebegindt; 	// VARCHAR(100), -- 휴지시작일자
	private String crpauseenddt;	// VARCHAR(100), -- 휴지종료일자
	private String crabldt;			// VARCHAR(100), -- 폐지일자
	private String crspec; 			// VARCHAR(100), -- 제공서비스
	
	private int class_cnt_00;		// INT, -- 반수-만0세
	private int class_cnt_01;		// INT, -- 반수-만1세
	private int class_cnt_02;		// INT, -- 반수-만2세
	private int class_cnt_03;		// INT, -- 반수-만3세
	private int class_cnt_04;		// INT, -- 반수-만4세
	
	private int class_cnt_05;		// INT, -- 반수-만5세
	private int class_cnt_m2;		// INT, -- 반수-영아혼합(만0~2세)
	private int class_cnt_m5;		// INT, -- 반수-유아혼합(만3~5세)
	private int class_cnt_sp;		// INT, -- 반수-특수장애
	private int class_cnt_tot;		// INT, -- 반수-총계
	
	private int child_cnt_00;		// INT, -- 아동수-만0세
	private int child_cnt_01;		// INT, -- 아동수-만1세
	private int child_cnt_02;		// INT, -- 아동수-만2세
	private int child_cnt_03;		// INT, -- 아동수-만3세
	private int child_cnt_04;		// INT, -- 아동수-만4세
	
	private int child_cnt_05;		// INT, -- 아동수-만5세
	private int child_cnt_m2;		// INT, -- 아동수-영아혼합(만0~2세)
	private int child_cnt_m5;		// INT, -- 아동수-유아혼합(만3~5세)
	private int child_cnt_sp;		// INT, -- 아동수-특수장애
	private int child_cnt_tot;		// INT, -- 아동수-총계
	
	private int em_cnt_0y;			// INT, -- 근속년수-1년미만
	private int em_cnt_1y;			// INT, -- 근속년수-1년이상~2년미만
	private int em_cnt_2y;			// INT, -- 근속년수-2년이상~4년미만
	private int em_cnt_4y;			// INT, -- 근속년수-4년이상~6년미만
	private int em_cnt_6y;			// INT, -- 근속년수-6년이상
	
	private int em_cnt_a1;			// INT, -- 교직원현황-원장
	private int em_cnt_a2;			// INT, -- 교직원현황-보육교사
	private int em_cnt_a3;			// INT, -- 교직원현황-특수교사
	private int em_cnt_a4;			// INT, -- 교직원현황-치료교사
	private int em_cnt_a5;			// INT, -- 교직원현황-영양사
	
	private int em_cnt_a6;			// INT, -- 교직원현황-간호사
	private int em_cnt_a10;			// INT, -- 교직원현황-간호조무사
	private int em_cnt_a7;			// INT, -- 교직원현황-조리원
	private int em_cnt_a8;			// INT, -- 교직원현황-사무직원
	private int em_cnt_tot;			// INT, -- 교직원현황-총계
	
	private String Crrepname;		// VARCHAR(100) -- 대표자명

}
