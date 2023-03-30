package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.common.ConnectionUtil;
import com.domain.BoardVO;

public class BoardDAO {
	
	private DataSource dataSource;
	
	public BoardDAO() {
		dataSource = ConnectionUtil.getDataSource();
	}
	
	// 글 목록
	public List<BoardVO> selectAll() {
		List<BoardVO> list = new ArrayList<>();
		String query = "SELECT * FROM board_tbl ORDER BY bno DESC";
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
		){
			while (rs.next()) {
				BoardVO vo = BoardVO.builder()
						.bno(rs.getInt("bno"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.writer(rs.getString("writer"))
						.imageFileName(rs.getString("imageFileName"))
						.writeDate(rs.getDate("writeDate"))
						.replyCount(rs.getInt("replyCount"))
						.build();
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}

	// 글 상세
	public BoardVO selectOne(int bno) {
		BoardVO vo = null;
		String query = "SELECT * FROM board_tbl where bno=?";
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
		){
			pstmt.setInt(1, bno);
			try (ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					vo = BoardVO.builder()
							.bno(rs.getInt("bno"))
							.title(rs.getString("title"))
							.content(rs.getString("content"))
							.writer(rs.getString("writer"))
							.imageFileName(rs.getString("imageFileName"))
							.writeDate(rs.getDate("writeDate")).build();
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vo;
	}
	
	// 번호 생성
	public int getNewBno() {
		int boardNo = 0;
		String query = "SELECT MAX(bno)+1 AS boardNo FROM board_tbl";
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
		){
			if(rs.next()) {
				boardNo = rs.getInt("boardNo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardNo;
	}

	// 글쓰기
	public int insertBoard(BoardVO vo) {
		String query = "insert into board_tbl(bno, title, content, writer, imageFileName) values(?, ?, ?, ?, ?)";
		int boardNo = getNewBno(); 
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
		){
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setString(4, vo.getWriter());
			pstmt.setString(5, vo.getImageFileName());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardNo;
	}
	
	// 수정
	public void updateBoard(BoardVO vo) {
		String imageFileName = vo.getImageFileName();
		int bno = vo.getBno();
		String query = "update board_tbl set title=?, content=?";
		
		if(imageFileName != null) {
			query += ", imageFileName=? where bno=?";
		} else {
			query += " where bno=?";
		}
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
		){
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			if(imageFileName != null) {
				pstmt.setString(3, imageFileName);
				pstmt.setInt(4, bno);
			} else {
				pstmt.setInt(3, bno);
			}
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 삭제
	public void deleteBoard(int bno) {
		String query = "delete from board_tbl where bno=?";
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
		){
			pstmt.setInt(1, bno);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
