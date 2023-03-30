package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.common.ConnectionUtil;
import com.domain.BoardVO;
import com.domain.MemberVO;
import com.domain.MemberVO.MemberGrade;

public class MemberDAO {
	
	private DataSource dataSource;
	
	public MemberDAO() {
		dataSource = ConnectionUtil.getDataSource();
	}

	public void insertMember(MemberVO vo) {
		String query = "INSERT INTO t_member(mno, id, pwd, name, email) VALUES(mno_seq.NEXTVAL, ?, ?, ?, ?)";
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
		){
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPwd());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 로그인 체크
	public boolean loingCheck(MemberVO vo) {
		boolean result = false;
		String query = "SELECT DECODE(COUNT(*),1,'TRUE','FALSE') AS RESULT FROM t_member WHERE id=? AND pwd=?";
		
		try (
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
			){
				pstmt.setString(1, vo.getId());
				pstmt.setString(2, vo.getPwd());
				try (ResultSet rs = pstmt.executeQuery();){
					if(rs.next()) {
						result = Boolean.parseBoolean(rs.getString("RESULT"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return result;
	}
	
	// 회원등급 조회
	public MemberGrade findMemberGradeById(String id) {
		MemberGrade grade = null;
		String query = "SELECT GRADE FROM t_member WHERE id=?";
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
		){
			pstmt.setString(1, id);
			try(ResultSet rs = pstmt.executeQuery();) {
				if(rs.next()) {
					grade = MemberGrade.valueOf(rs.getString("grade"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return grade;
		
	}
}
