package com.multi.ukids.toy.model.vo;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pay {
	private int payNo;		
    private int memberNo;		      //회원
    private String toyNo;		      //장난감       
	private String price;	          //금액        
	private String name;		      //이름        
	private String phone;	          //연락처       
	private String postCode;		  //우편번호      
	private String address;	          //주소   
	private String address2;	      //주소2	
	private String request;	          //요청사항      
	private String method;	          //결제수단      
	private String startDate;		  //대여기간(시작)  
	private String endDate;	          //대여기간(반납)  
	private String status;
	
	List<Integer> CartNoList;
	private List<Toy> toyList;
	private List<String> startDateList;
	private List<String> endDateList;
	private List<String> statusList;
	private List<Integer> cntList;

	
	public Pay(int memberNo, String toyNo, String price, String name, String phone, String postCode, String address,
			String address2, String request, String method, String startDate, String endDate, String status) {
		super();
		this.memberNo = memberNo;
		this.toyNo = toyNo;
		this.price = price;
		this.name = name;
		this.phone = phone;
		this.postCode = postCode;
		this.address = address;
		this.address2 = address2;
		this.request = request;
		this.method = method;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}
	
}

