<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<script src="${contextPath}/resources/js/board/list.js"></script>

<div class="container">

	<div class="text-center jumbotron bg-light">
		<h1>목록</h1>
	</div>
	
	<form id="listForm">
		<table class="table">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>내용</th>
				<th>작성자</th>
				<th>파일</th>
				<th>작성일</th>
			</tr>
			<c:forEach items="${list}" var="b">
				<tr>
					<td>${b.bno }</td>
					<td>
						<a href="${contextPath }/board/detail?bno=${b.bno}">${b.title }
							<b>${b.replyCount != 0 ? '['+=b.replyCount +=']':''}</b>
						</a>
					</td>
					<td>${b.writer }</td>
					<td>${b.imageFileName }</td>
					<td>${b.writeDate }</td>
				</tr>
			</c:forEach>
		</table>
	</form>
	
	<a href="${contextPath}/board/writeForm" class="btn btn-primary">글쓰기</a>

</div>

<%@ include file="../layout/footer.jsp" %>
