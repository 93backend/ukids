package com.multi.ukids.toy.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
	private int payNo;
	private int toyNo;
	private int memberNo;
	private String toyNm1;
	private String toyImg;
	private String toyPay;
	private String price;
	private String status;    // 'Y' : 대여중, 'A' : 주문완료, 'N' : 반납완료, 'R' : 반납중, 'T' : 배송중
	private Date startDate;
	private Date endDate;
	private int realPrice;
	private int date;
}
