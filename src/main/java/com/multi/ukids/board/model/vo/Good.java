package com.multi.ukids.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Good {
	private int goodNo;
	private int boardNo;
	private int memberNo;
	
	public Good(int boardNo, int memberNo) {
		super();
		this.boardNo = boardNo;
		memberNo = memberNo;
	}
}
