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
	
	let loginCompany = $("#loginCompany").val();
    let loginMember = $("#loginMember").val();
	$(".submit-btn").on("click", function(event){
    	let filedNo = $("#fieldSelect").val();
	    if (loginMember !== null && loginMember !== "") {
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
	    }else if(loginCompany !== null && loginCompany !== ""){
			 alertMessage("일반회원만 지원가능합니다.", "warning");
		}else{
			location.href = `${contextPath}/login`;
		}
	});
	
	/// 서버 시간과 종료 시간 객체 생성
    const endDate = new Date(`${endDateStr}T23:59:59`);       // 마감 시간

    // 남은 시간 계산 및 화면 업데이트 함수
    function updateCountdown() {
        const now = new Date(); // 클라이언트 현재 시간
        const remainingTime = endDate.getTime() - now.getTime();

        // 남은 시간이 0 이하일 경우 마감 표시
        if (remainingTime <= 0) {
            document.getElementById("countdown").innerHTML = "마감되었습니다.";
            clearInterval(interval);
            return;
        }

        // 남은 시간 계산
        const days = Math.floor(remainingTime / (1000 * 60 * 60 * 24));
        const hours = Math.floor((remainingTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((remainingTime % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((remainingTime % (1000 * 60)) / 1000);

        // 남은 시간 표시
        document.getElementById("countdown").innerHTML =
            `남은 기간 : <span style="color: red;">&nbsp;${days}일 ${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}</span>`;
    }

    // 1초마다 남은 시간 갱신
    const interval = setInterval(updateCountdown, 1000);

    // 페이지 로드 시 즉시 호출
    updateCountdown();
});
