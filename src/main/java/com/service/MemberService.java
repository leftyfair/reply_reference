package com.service;

import com.dao.MemberDAO;
import com.domain.MemberVO;
import com.domain.MemberVO.MemberGrade;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberService {
	
	private MemberDAO dao;
	
	// 회원가입
	public void memberJoin(MemberVO vo) {
		dao.insertMember(vo);
	}
	
	// 로그인 체크
	public boolean loginService(MemberVO vo) {
		return dao.loingCheck(vo);
	}

	// 회원등급
	public MemberGrade getMemberGrade(String id) {
		// TODO Auto-generated method stub
		return dao.findMemberGradeById(id);
	}
}
