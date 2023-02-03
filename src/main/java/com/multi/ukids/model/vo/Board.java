package com.multi.ukids.model.vo;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int boardNo; 				// 게시글 번호(PK)
	private int memberNo; 				// 회원번호(FK) 
	private String title;				// 제목
	private String content;				// 내용
	private int good;					// 좋아요
	private String type;				// 분류(자유게시판, 공지사항)
	private String originalFileName;	// 기존 파일명
	private String renamedFileName;		// 수정 파일명
	private int readCount;				// 조회수
	private String status;				// 상태(Y: default, N: 삭제)
	private Date createDate; 			// 생성일
	private Date modifyDate;			// 수정일
	private List<Reply> replies;		// 댓글
	
}
