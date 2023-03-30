<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<script src="${contextPath}/resources/js/reply/ReplyService.js"></script>
<script src="${contextPath}/resources/js/board/detail.js"></script>

<div class="feedback" style="display:none;">이벤트 트리거 테스트</div>

<div class="container">
	<div class="text-center jumbotron bg-light">
		<h1>게시글조회</h1>
	</div>
	<form id="viewForm" enctype="multipart/form-data">
		<table class="table">
			<tr>
				<th>글번호</th>
				<td>${board.bno }<input type="hidden" name="bno" value="${board.bno }"></td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${board.writeDate}</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${board.writer}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" class="form-control" value="${board.title}" readonly="readonly"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea rows="10" name="content" class="form-control" readonly="readonly">${board.content}</textarea></td>
			</tr>
			<tr>
				<th>첨부이미지</th>
				<td colspan="3">
					<input type="file" name="imageFileName" class="form-control viewMode" >
					<div class="my-2">
						<c:if test="${not empty board.imageFileName }">
							<input type="hidden" name="originFileName" value="${board.imageFileName }">
							<div class="preview">
								<img class="originImg" src="${contextPath}/fileDownload?no=${board.bno}&imageFileName=${board.imageFileName}&path=board">
							</div>
						</c:if>
						<c:if test="${empty board.imageFileName }">
							<div class="preview">
								<p><b>등록된 이미지 없음</b></p>
							</div>
						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<c:if test="${auth.id eq board.writer or auth.grade eq 'ROLE_ADMIN'}">
						<button type="button" class="btn btn-info toModForm">수정하기</button>
						<button type="button" class="btn btn-danger remove">삭제</button>
					</c:if>
					<button type="button" class="btn btn-secondary toList">목록</button>
				</td>
			</tr>
			<tr class="viewMode">
				<c:if test="${auth.id eq board.writer or auth.grade eq 'ROLE_ADMIN'}">
					<td colspan="4">
						<button type="button" class="btn btn-info modify">수정</button>
						<button type="button" class="btn btn-danger backViewMode">취소</button>
					</td>
				</c:if>
			</tr>
		</table>
	</form>
	
	<div class="replyForm">
		<table class="table">
			
			<tr>
				<th colspan="2">
					<ul class="d-flex justify-content-between">
						<li>댓글을 작성해주세요</li>
						<li class="form-inline">작성자 : <input type="text" class="reply_writer form-control ml-2" value="${auth.id}" readonly="readonly"></li>
					</ul>
				</th>
			</tr>
			
			<tr>
				<td class="col-1 text-center">내용</td>
				<td>
					<textarea rows="5" class="form-control reply_content"></textarea>
				</td>
			</tr>
			
			<tr class="text-right">
				<td colspan="2"><button class="btn btn-primary reply_write">댓글등록</button></td>
			</tr>
			
		</table>
	</div>
	<div class="replyList">
		<div class="card">
			<div class="card-header bg-warning text-white">댓글목록</div>
			<div class="card-body">
			
			</div>
		</div>
	</div>
</div>

<!-- The Modal -->
  <div class="modal fade" id="feedback">
    <div class="modal-dialog">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">댓글 등록</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
        	<h4>댓글등록성공</h4>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
        </div>
        
      </div>
    </div>
  </div>

<%@ include file="../layout/footer.jsp" %>
