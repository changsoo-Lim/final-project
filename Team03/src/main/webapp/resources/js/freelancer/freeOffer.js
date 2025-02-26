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

// icon type 
// success 체크
// error  엑스
// warning 느낌표
// info  i 진짜 i 임
// question ?


document.addEventListener("DOMContentLoaded", () => {
	const baseUrl = document.body.dataset.url;
    let loginCompany = $("#loginCompany").val();
    let loginMember = $("#loginMember").val();
	
	// 프리랜서 디테일 정보 클릭 이벤트
    $(".login-btn").on("click", function(event){
	    if (loginMember !== null && loginMember !== "") {
	        event.preventDefault(); // 기본 이벤트 동작을 막음
	        confirmMessage("로그아웃 하시겠습니까?", "", function(result) {
	            if (result) {
	                alertMessage("로그아웃되었습니다.", "success");
	                setTimeout(function() {
	                    location.href = `${baseUrl}/logout`;
	                }, 1500);
	            } else {
	                return false;
	            }
	        });
	    }else{
			location.href = `${baseUrl}/login`;
		}
	});
	
	// 프로젝트 제안 버튼 이벤트
    $(".project-btn").on("click", function(event) {
        // 기업회원이 아닌 경우
        if (loginCompany === null || loginCompany === "") {
            event.preventDefault();
            alertMessage("기업 로그인 후 등록이 가능합니다.", "warning");
        }else{
			location.href = `${baseUrl}/project/form`;
		}
    });
	
	// 프리랜서 제안 ajax
    $('#freeOffer-form').on('submit', function(event) {
        event.preventDefault();
        $.ajax({
            url: $('#freeOffer-form').attr('action'),
            type: 'POST',
            data: $(this).serialize(), 
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
    });
});
