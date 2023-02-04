package com.multi.ukids.member.model.vo;

import java.util.Date;

import com.multi.ukids.member.model.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	private int memberNo;
	private String id;
	private String password;
	private String role;
	private String name;
	private String postCode;
	private String address;
	private String email;
	private String phone;
	private int kidNum;
	private int kidAge1; 
	private int kidAge2; 
	private int kidAge3;
	private String field;
	private String fieldCd;
	private String kakaoToken;
	private String STATUS;
	private Date enrollDate;
	private Date modifyDate;
	private String address2;  // 상세 주소
}
