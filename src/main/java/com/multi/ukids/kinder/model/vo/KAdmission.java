package com.multi.ukids.kinder.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KAdmission {
	private int no;
	private int kinNo;
	private int memberNo;
	private String kinderName;
	private String status;
	private Date hopeDate;
	private Date enrollDate;
}
