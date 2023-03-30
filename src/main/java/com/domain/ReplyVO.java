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
public class ReplyVO {
	private int rno;
	private int bno;
	private String reply;
	private String writer;
	private Date replyDate;
	private Date modifyDate;
	
}
