package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.common.ConnectionUtil;
import com.domain.ReplyVO;

public class ReplyDao {

	private DataSource dataSource;
	
	public ReplyDao() {
		dataSource = ConnectionUtil.getDataSource();
	}
	
	public List<ReplyVO> replyList(int bno) {
		List<ReplyVO> list = new ArrayList<ReplyVO>();
		String query = "SELECT * FROM reply_tbl WHERE bno=?";
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
		){
			pstmt.setInt(1, bno);
			try(ResultSet rs = pstmt.executeQuery();) {
				while(rs.next()) {
					ReplyVO vo = ReplyVO.builder()
							.rno(rs.getInt("rno"))
							.bno(rs.getInt("bno"))
							.reply(rs.getString("reply"))
							.writer(rs.getString("writer"))
							.replyDate(rs.getDate("replyDate"))
							.modifyDate(rs.getDate("modifyDate")).build();
					list.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	public ReplyVO find(int rno) {
		String query = "SELECT FROM reply_tbl where rno=?";
		ReplyVO vo = null;
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
		){
			pstmt.setInt(1, rno);
			try(ResultSet rs = pstmt.executeQuery();) {
				if(rs.next()) {
					vo = ReplyVO.builder()
						.rno(rs.getInt("rno"))
						.reply(rs.getString("reply"))
						.writer(rs.getString("writer"))
						.replyDate(rs.getDate("replyDate"))
						.modifyDate(rs.getDate("modifyDate")).build();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	public void insert(ReplyVO vo) {
		String query = "INSERT INTO reply_tbl(rno, bno, reply, writer) VALUES(seq_reply.nextval, ?, ?, ?)";
		String query2 = "UPDATE board_tbl SET replyCount=replyCount+1 WHERE bno=?";
		try (Connection conn = dataSource.getConnection();){
			try (
				PreparedStatement pstmt = conn.prepareStatement(query);
				PreparedStatement pstmt2 = conn.prepareStatement(query2);
			){
				conn.setAutoCommit(false);
				pstmt.setInt(1, vo.getBno());
				pstmt.setString(2, vo.getReply());
				pstmt.setString(3, vo.getWriter());
				pstmt.executeUpdate();
				pstmt2.setInt(1, vo.getBno());
				pstmt2.executeUpdate();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int rno) {
		String query = "delete from reply_tbl where rno=?";
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
		){
			pstmt.setInt(1, rno);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
