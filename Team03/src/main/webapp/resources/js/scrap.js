$(function() {
	const contextPath = document.body.dataset.url || "";
	
	// ========== 관심공고(공고 스크랩) AJAX ==========
	$('.scrap-btn').on('click', function() {
		const button = $(this);
		const employNo = button.data('pk-no');
		const currentStatus = button.data('scrap-status');
		const newStatus = (currentStatus === 'Y' || currentStatus === '') ? 'N' : 'Y';
		
		$.ajax({
			url: contextPath + '/employScrap',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				employNo: employNo,
				empscrapDel: newStatus
			}),
			success: function(response) {
				if (response.status === "success") {
					// 상태 업데이트
					button.data('scrap-status', newStatus);
					button.find('i').toggleClass('bi-star bi-star-fill');
				}else if(response.status === "comp"){
					alertMessage("일반회원만 이용 가능합니다.", "warning");
				}else if(response.status === "anonymous"){
					alertMessage("로그인 후 이용 가능합니다.", "warning");
				}
			},
			error: function() {
				alertMessage("서버에서 문제가 발생했습니다. 잠시 후 다시 시도해주세요.", "error");
			}
		});
	});

	// ========== 관심기업(기업 스크랩) AJAX ==========
	$('.inter-btn').on('click', function() {
		const button = $(this);
		const compId = button.data('pk-no');
		const currentStatus = button.data('scrap-status');
		const newStatus = (currentStatus === 'Y' || currentStatus === '') ? 'N' : 'Y';
		const elements = $(`[data-pk-no="${compId}"]`); 
		
		$.ajax({
			url: contextPath + '/interComp',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				compId: compId,
				interCompDel: newStatus
			}),
			success: function(response) {
				if (response.status === "success") {
					button.data('scrap-status', newStatus);
					elements.find('i').toggleClass('bi-heart bi-heart-fill');
				}else if(response.status === "comp"){
					alertMessage("일반회원만 이용 가능합니다.", "warning");
				}else if(response.status === "anonymous"){
					alertMessage("로그인 후 이용 가능합니다.", "warning");
				}
			},
			error: function() {
				alertMessage("서버에서 문제가 발생했습니다. 잠시 후 다시 시도해주세요.", "error");
			}
		});
	});
});