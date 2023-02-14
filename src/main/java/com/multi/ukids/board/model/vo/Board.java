package com.multi.ukids.board.model.vo;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int boardNo;				// 게시판번호
	private int memberNo;				// 회원번호
	private String id;					// 아이디
	private String title;				// 제목
	private String content;				// 내용
	private int good;					// 추천수
	private String type;				// 구분(공지사항, 게시
	private String originalFileName; 	// 기존 파일명
	private String renamedFileName;		// 변경 파일명
	private int readcount;				// 조회수
	private String status;				// 상태(Y: default, N: 삭제)
	private Date createDate;			// 생성일
	private Date modifyDate;			// 수정일
	private List<B_Reply> replies;		// 댓글
}
