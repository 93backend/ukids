package com.multi.ukids.wish.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wish {
	private int no;
	private int knNo;
	private int memberNo;
	private String knName;
	private String knAddr;
}
