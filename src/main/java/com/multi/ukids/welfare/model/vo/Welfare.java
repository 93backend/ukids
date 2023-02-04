package com.multi.ukids.welfare.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Welfare {
	private int cwNo;				//primary key
	private String fcltCd;			//시설코드
	private String fcltNm; 			//시설명
	private String fcltEngNm; 		//시설영문명
	private String rprsNm;			//대표자명
	private String homepageAddr; 	//홈페이지주소
	private String fcltMailAddr; 	//시설이메일주소
	private String fcltZipcd;		//시설우편번호
	private String fcltAddr;		//시설주소
	private String fcltDtl_1Addr; 	//시설상세1주소
	private String fcltTelNo;		//시설전화번호
	private String faxNo;			//팩스번호
	private String estbDe; 			//설립일자
	private String cprNm;			//법인명
	private String cfbNm; 			//업종명

}
