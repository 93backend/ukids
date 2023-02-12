package com.multi.ukids.book.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	private int no;  				// INT AUTO_INCREMENT PRIMARY KEY
	private int ranking;  			// 순위     
	private String bookname;  		// 도서명    
	private String authors;  		// 저자명    
	private String publisher;  		// 출판사    
	private int publication_year;  	// 출판년도   
	private String class_nm;  		// 분류     
	private int loan_count;  		// 대출 건수  
	private String bookImageURL;  	// 이미지    
	private int age;  				// 연령  
	private String detail;			// 도서 내용
}
