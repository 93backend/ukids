package com.multi.ukids.toy.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pay {
	private int payNo;		
    private int memberNo;		      //회원
    private int toyNo;		          //장난감       
	private String price;	          //금액        
	private String name;		      //이름        
	private String phone;	          //연락처       
	private String postCode;		  //우편번호      
	private String address;	          //주소   
	private String address2;	      //주소2	
	private String request;	          //요청사항      
	private String method;	          //결제수단      
	private Date startDate;		      //대여기간(시작)  
	private Date endDate;	          //대여기간(반납)  
	
	public Pay(int memberNo, int toyNo, String price, String name, String phone, String postCode, String address,
			String address2, String request, String method, Date startDate, Date endDate) {
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
	}
	
}

