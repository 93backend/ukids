package com.multi.ukids.kinder.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kinder {

	private int kinNo; 				// INT PRIMARY KEY NOT NULL AUTO_INCREMENT, -- 유치원번호
	private String kinderCode; 		// VARCHAR(100), -- 유치원코드
	private String officeedu; 		// VARCHAR(100), -- 교육청명
	private String subofficeedu; 	// VARCHAR(100), -- 교육지원청명
	private String kindername; 		// VARCHAR(100), -- 유치원명
	private String establish; 		// VARCHAR(100), -- 설립유형
	private String edate; 			// VARCHAR(100), -- 설립일
	private String odate; 			// VARCHAR(100), -- 개원일
	private String addr; 			// VARCHAR(100), -- 주소
	private String telno; 			// VARCHAR(100), -- 전화번호
	private String hpaddr;			// VARCHAR(100), -- 홈페이지
	private String opertime; 		// VARCHAR(100), -- 운영시간
	private int clcnt3; 			// INT, -- 만3세학급수
	private int clcnt4; 			// INT, -- 만4세학급수
	private int clcnt5; 			// INT, -- 만5세학급수
	private int mixclcnt; 			// INT, -- 혼합학급수
	private int shclcnt; 			// INT, -- 특수학급수
	private int ppcnt3; 			// INT, -- 만3세유아수
	private int ppcnt4; 			// INT, -- 만4세유아수
	private int ppcnt5; 			// INT, -- 만5세유아수
	private int mixppcnt; 			// INT, -- 혼합유아수
	private int shppcnt; 			// INT, -- 특수유아수
	private String rppnname; 		// VARCHAR(100), -- 대표자명
	private String ldgrname; 		// VARCHAR(100), -- 원장명
	private String pbnttmng; 		// VARCHAR(100), -- 공시차수
	private int prmstfcnt; 			// INT, -- 인가총정원수
	private int ag3fpcnt; 			// INT, -- 3세모집정원수
	private int ag4fpcnt; 			// INT, -- 4세모집정원수
	private int ag5fpcnt; 			// INT, -- 5세모집정원수
	private int mixfpcnt; 			// INT, -- 혼합모집정원수
	private int spcnfpcnt; 			// INT, -- 특수학급모집정원수
	private String phgrindrarea; 	// VARCHAR(100), -- 체육장
	private int drcnt; 				// INT, -- 원장수
	private int adcnt; 				// INT, -- 원감수
	private int hdst_thcnt; 		// INT, -- 수석교사수
	private int asps_thcnt; 		// INT, -- 보직교사수
	private int gnrl_thcnt; 		// INT, -- 일반교사수
	private int spcn_thcnt; 		// INT, -- 특수교사수
	private int ntcnt; 				// INT, -- 보건교사수
	private int ntrt_thcnt; 		// INT, -- 영양교사수
	private int shcnt_thcnt; 		// INT, -- 기간제교사수
	private int incnt; 				// INT, -- 강사수
	private int owcnt; 				// INT, -- 사무직원수
	private int hdst_tchr_qacnt; 	// INT, -- 수석교사자격수
	private int rgth_gd1_qacnt; 	// INT, -- 정교사1급자격수
	private int rgth_gd2_qacnt; 	// INT, -- 정교사2급자격수
	private int asth_qacnt; 		// INT, -- 준교사자격수
	private String mas_mspl_dclr_yn; 	// VARCHAR(100), -- 집단급식소신고여부
	private String vhcl_oprn_yn; 		// VARCHAR(100), -- 차량운영여부
	private int yy1_undr_thcnt; 		// INT, -- 1년미만교사수
	private int yy1_abv_yy2_undr_thcnt; // INT, -- 1년이상2년미만교사수
	private int yy2_abv_yy4_undr_thcnt; // INT, -- 2년이상4년미만교사수
	private int yy4_abv_yy6_undr_thcnt; // INT, -- 4년이상6년미만교사수
	private int yy6_abv_thcnt; 			// INT, -- 6년이상교사수
	private String fxtm_dsnf_trgt_yn; 	// VARCHAR(100), -- 정기소독 의무대상시설여부
	private String fire_avd_yn; 		// VARCHAR(100), -- 소방대피훈련여부
	private String gas_ck_yn; 			// VARCHAR(100), -- 가스점검여부
	private String fire_safe_yn; 		// VARCHAR(100), -- 소방안전점검여부
	private String elect_ck_yn; 		// VARCHAR(100), -- 전기설비점검여부
	private String plyg_ck_yn; 			// VARCHAR(100), -- 놀이시설 안전검사 대상여부
	private String cctv_ist_yn; 		// VARCHAR(100), -- CCTV 설치여부
	private int cctv_ist_total; 		// INT, -- CCTV 총 설치수
	private int afsc_pros_lsn_dcnt; 	// INT

}
