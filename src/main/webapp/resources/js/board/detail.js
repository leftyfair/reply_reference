$(function() {
	
	// ====== 게시물 관련 ======
	
	$('.viewMode').hide();
	
	let viewForm = $('#viewForm');
	let titleObj = $('input[name="title"]');
	let contentObj = $('textarea[name="content"]');
	let imageFile = "${board.imageFileName}";
	let pTag = $('.preview p').html; 
	
	let originImg = $('.originImg').clone();
	let titleVal = titleObj.val();
	let contentVal = contentObj.val();
	
	// 수정모드
	$('.toModForm').on('click', function() {
		$('input[name="title"], textarea[name="content"]').attr("readonly", false);
		$('.viewMode').show();
		$(this).closest('tr').hide();
	});
	
	// 수정처리
	$('.modify').on('click', function() {
		viewForm.attr({
			'action' : `${contextPath}/board/modBoard`,
			'method' : 'post'
		}).submit();
	});
	
	// 삭제
	$('.remove').on('click', function() {
		viewForm.attr({
			'action' : `${contextPath}/board/removeBoard`,
			'method' : 'post' 
		}).submit();
	});
	
	// 뷰모드
	$('.backViewMode').on('click', function() {
		$('input[name="title"], textarea[name="content"]').attr("readonly", true);
		$('.viewMode').hide ();
		$(this).closest('tr').prev().show();
		$('.preview').html(originImg);
		$('input[type="file"]').val('');
		titleObj.val(titleVal);
		contentObj.val(contentVal);
		if(imageFile == '' || imageFile == null) {
			$('.preview').html(pTag);
		}
	});
	
	// 목록
	$('.toList').on('click', function() {
		viewForm.attr({
			'action' : `${contextPath}/board`,
			'method' : 'get'
		}).empty().submit();
	});
	
	
	// ====== 댓글 관련 ======
	
	let bno = $('input[name="bno"]').val();

	// 댓글 목록
	replyService.list(bno);	

	// 댓글 쓰기	
	$('.reply_write').on('click', function() {
		let writer = $('.reply_writer').val();
		let reply = $('.reply_content').val();
	
		let replyVO = {
			bno : bno,
			reply : reply,
			writer : writer
		}
		replyService.write(replyVO);		
	});
	
	// 수정 버튼 이벤트
	$('.replyList').on('click', '.reply_modBtn', function() {
		let rno = $(this).closest('div').data('rno');
		let reply = $('.reply_content').val();
	
		let replyVO = {
			bno : bno,
			rno : rno,
			reply : reply,
		}		
		replyService.detail(replyVO);
	});
	
	// 삭제 버튼 이벤트
	$('.replyList').on('click', '.reply_delBtn', function() {
		let rno = $(this).closest('div').data('rno');
		
		let replyVO = {
			bno : bno,
			rno : rno
		}
		
		replyService.remove(replyVO);
	});
	
});



