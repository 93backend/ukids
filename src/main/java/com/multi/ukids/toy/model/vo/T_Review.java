package com.multi.ukids.toy.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class T_Review {
	private int no;	
    private int toyNo;	
    private int memberNo;	
    private String content;	
    private Date createDate;	
	private Date modifyDate;
	private String memberId;
}
