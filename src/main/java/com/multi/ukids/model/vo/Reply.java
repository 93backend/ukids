package com.multi.ukids.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
	private int replyNo;		// 리플번호(PK)
	private int boardNo;		// 게시판번호(FK)
	private int memberNo;		// 회원번호(FK)
	private String content;		// 내용
	private String status;		// 상태(Y: default, N: 삭제)
	private Date createDate;	// 작성일
	private Date modifyDate;	// 수정일
}
