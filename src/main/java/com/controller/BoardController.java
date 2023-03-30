package com.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.FileUpload;
import com.dao.BoardDAO;
import com.domain.BoardVO;
import com.service.BoardService;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private BoardService service;
	private FileUpload multiReq;
	
	@Override
	public void init() throws ServletException {
		BoardDAO dao = new BoardDAO();
		service = new BoardService(dao);
		multiReq = new FileUpload("board/");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String pathInfo = request.getPathInfo();
		final String PREFIX = "/WEB-INF/views/board/";
		final String SUFFIX = ".jsp";
		
		RequestDispatcher rd = null;
		String nextPage = null;
		
		if(pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
			List<BoardVO> boardList = service.boardList();
			request.setAttribute("list", boardList);
			nextPage = "list";
		} else if(pathInfo.equals("/detail")) {
			String paramBno = request.getParameter("bno");
			int bno = Integer.parseInt(paramBno);
			BoardVO findBoard = service.findBoard(bno);
			request.setAttribute("board", findBoard);
			nextPage = "detail";
		} else if(pathInfo.equals("/writeForm")) {
			nextPage = "writeForm";
		} else if(pathInfo.equals("/write")) {
			Map<String, String> req = multiReq.getMultipartRequest(request);
			String imageFileName = req.get("imageFileName");
			
			BoardVO vo = BoardVO.builder()
					.title(req.get("title"))
					.content(req.get("content"))
					.writer(req.get("writer"))
					.imageFileName(req.get("imageFileName")).build();
			int boardNo = service.addBoard(vo);
			
			if(imageFileName != null && imageFileName.length() > 0) {
				multiReq.uploadImage(boardNo, imageFileName);
			}
			
			response.sendRedirect(contextPath + "/board");
			return;
		} else if(pathInfo.equals("/modBoard")) {
			Map<String, String> req = multiReq.getMultipartRequest(request);
			String paramBno = req.get("bno");
			int bno = Integer.parseInt(paramBno);
			String title = req.get("title");
			String content = req.get("content");
			String imageFileName = req.get("imageFileName");
			
			BoardVO vo = BoardVO.builder()
					.bno(bno)
					.title(title)
					.content(content)
					.imageFileName(imageFileName).build();
			service.modBoard(vo);
			
			if(imageFileName != null) {
				String originFileName = req.get("originFileName");
				
				multiReq.uploadImage(bno, imageFileName);
				if(originFileName != null) {
					multiReq.deleteOriginImage(paramBno, originFileName);
				}
			}
			response.sendRedirect(contextPath + "/board");
			return;
		} else if(pathInfo.equals("/removeBoard")) {
			Map<String, String> req = multiReq.getMultipartRequest(request);
			String paramBno = req.get("bno");
			int bno = Integer.parseInt(paramBno);
			service.removeBoard(bno);
			multiReq.deleteAllImage(bno);
			response.sendRedirect(contextPath + "/board");
			return;
		} else {
			System.out.println("잘못된 요청");
			return;
		}
		rd = request.getRequestDispatcher(PREFIX + nextPage + SUFFIX);
		rd.forward(request, response);
	}

}
