package com.multi.ukids.news.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
	private int nNo;
	private String title;
	private String originallink;
	private String link;
	private String description;
	private Date pubDate; // "EEE, d MMM yyyy HH:mm:ss Z"
}
