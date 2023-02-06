package com.multi.ukids.kinder.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KReview {
	private int no;
	private int kinNo;
	private int memberNo;
	private String id;
	private String content;
	private Date createDate;
	private Date modifyDate;
}
