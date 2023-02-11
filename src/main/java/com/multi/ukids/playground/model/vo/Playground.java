package com.multi.ukids.playground.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playground {
	private String NO; 			//primary key
	private String pgid; 		//고유ID
	private String divL; 		//대분류명
	private String divM; 		//중분류명
	private String pgName; 		//시설명
	private String pgAddr1;		//시설도로명주소
	private String pgAddr2; 	//지번주소
	private String bName; 		//건물명
	private String tel; 		//전화번호
	private String post; 		//우편번호
	private String url; 		//홈페이지URL
	private String lat; 		//시설위도
	private String lon;			//시설경도
	private String water; 		//물놀이시설여부
	private String dc;			//추가설명

}
