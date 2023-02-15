package com.multi.ukids.nursery.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NAdmission {
	private int no;
	private int nuNo;
	private int memberNo;
	private String crname;
	private String status;
	private Date hopeDate;
	private Date enrollDate;
	private String newEnrollDate;
	private String newHopeDate;
	private String memberName;
}
