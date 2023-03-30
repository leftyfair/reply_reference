package com.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardVO {
	private int bno;
	private String title;
	private String content;
	private String writer;
	private String imageFileName;
	private Date writeDate;
	private int replyCount;
}
