package com.multi.ukids.claim.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
	private int no;
	private int knNo;
	private int memberNo;
	private String knName;
	private String id;
	private String title;
	private String content;
	private String originalFileName;
	private String renamedFileName;
	private int readCount;
	private String status;
	private String confirm;
	private Date createDate;
	private Date modifyDate;
}
