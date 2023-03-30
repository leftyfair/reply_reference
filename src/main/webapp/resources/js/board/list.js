$(function() {
	
	let listForm = $('#listForm');
	$('.title').on('click', function(e){
		e.preventDefault();
		$('[name="bno"]').remove();
		let bnoData = "<input type='hidden' name='bno' value='"+$(this).attr('href')+"'/>";
		console.log(bnoData);
		listForm.append(bnoData)
				.attr('action', `${contextPath}/board/detail`)
				.submit();
	});
});