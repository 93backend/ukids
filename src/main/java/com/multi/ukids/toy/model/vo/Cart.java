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
	private Date startdate;
	private Date endDate;
}
