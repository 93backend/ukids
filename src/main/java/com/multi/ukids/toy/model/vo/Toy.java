package com.multi.ukids.toy.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Toy {
	private int toyNo; 
	private String toyCd;	         //자체상품코드
	private String toyNm1;	         //상품명    
	private String toyNm2;	         //공급사 상품명
	private String toyEx;	         //상품 상세설명
	private String toySearch;	     //검색어설정  
    private String toyPay;		     //금액     
	private String toyImg;	         //이미지    
	private String toyCategoryB;	 //대분류      
    private String toyCategoryM;	 //중분류    	  
	private String toyCategoryS;	 //소분류    	  
    private String toyType;          //대여여부
    private String status;           //대여상태
}
