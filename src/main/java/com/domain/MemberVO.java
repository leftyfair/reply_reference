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
public class MemberVO {
	private int mno;
	private String id;
	private String pwd;
	private String name;
	private String email;
	private Date date;
	private MemberGrade grade;
	
	// 등급 여러가지 넣고 싶으면 쭉 나열하면 됨
	public enum MemberGrade {
		ROLE_ADMIN, ROLE_MEMBER
	}
	
}
