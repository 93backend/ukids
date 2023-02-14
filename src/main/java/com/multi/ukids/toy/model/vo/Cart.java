package com.multi.ukids.toy.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	private int no;
	private int toyNo;
	private int memberNo;
	public String toyNm1;
	public String toyImg;
	private Date startDate;
	private Date endDate;
	private String toyPay;
	private int realPay;
	private int date;
	private String toyType;
}
