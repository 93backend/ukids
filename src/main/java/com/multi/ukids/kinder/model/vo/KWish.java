package com.multi.ukids.kinder.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KWish {
	private int no;
	private int kinNo;
	private int memberNo;
	private String kindername;
	private String addr;
}
