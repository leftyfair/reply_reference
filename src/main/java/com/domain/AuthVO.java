package com.domain;

import com.domain.MemberVO.MemberGrade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthVO {
	private String id;
	private MemberGrade grade;
}
