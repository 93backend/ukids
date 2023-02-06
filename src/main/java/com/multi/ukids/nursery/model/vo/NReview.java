package com.multi.ukids.nursery.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NReview {
	private int no;
	private int nuNo;
	private int memberNo;
	private String id;
	private String content;
	private Date createDate;
	private Date modifyDate;
}
