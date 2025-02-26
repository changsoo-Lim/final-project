// =================================== 함수 ===================================
// Alert 함수
let alertMessage = function(msg, type) {
	Swal.fire({
		title: '',
		text: msg,
		icon: type,
		timer: 1500,
		customClass: {
			popup: 'sweet-size'
		},
		showConfirmButton: false
	});
};
// Confirm 함수
let confirmMessage = function(msg, title, result) {
    Swal.fire({
        title: title,
        text: msg,
        icon: 'question',
        showCancelButton: true,
        confirmButtonClass: 'btn-danger',
        confirmButtonText: '예',
        cancelButtonText: '아니오',
        customClass: {
            confirmButton: 'btn btn-danger'
        }
    }).then(function(response) {
        if (response.isConfirmed) { // 사용자가 "예"를 클릭했을 때
            result(true); // 콜백에 true 전달
        } else { // 사용자가 "아니오"를 클릭했을 때
            result(false); // 콜백에 false 전달
        }
    });
};

$(function() {
	const contextPath = document.body.dataset.url || "";
	
	$(document).on('click', '.submit-btn', function() {
		let employNo = $(this).attr('data-employNo');
    	let filedNo = $(`#fieldSelect${employNo}`).val();
        confirmMessage("지원하시겠습니까?", "", function(result) {
            if (result) {
				$.ajax({
		            url: contextPath + '/company/apply/applyInsert',
		            type: 'POST',
		            data: JSON.stringify({
						filedNo : filedNo
					}),
					contentType: 'application/json; charset=UTF-8',
					dataType: 'json',
		            success: function(response) {
		                if (response.status === "success") {
		                    alertMessage(response.message, 'success');
							location.reload();
		                } else if(response.status === "fail"){
		                    alertMessage(response.message, 'error');
		                } else{
		                    alertMessage(response.message, 'error');
		                }
		            },
		            error: function() {
		                alertMessage("서버에서 문제가 발생했습니다. 잠시 후 다시 시도해주세요.", "error");
		            }
		        });
            } else {
                return false;
            }
        });
	});
});