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


// 버튼을 텍스트로 변경하는 함수
let updateProposalStatus = function(projectId, status) {
    const $acceptBtn = $(`#acceptBtn[data-project-id="${projectId}"]`);
    const $rejectBtn = $(`#rejectBtn[data-project-id="${projectId}"]`);

    if (status === "accepted") {
        $acceptBtn.replaceWith('<div class="proposal-status-container"><div class="proposal-status-text accepted-text">수락한 제안입니다</div></div>');
        $rejectBtn.remove();
    } else if (status === "rejected") {
        $rejectBtn.replaceWith('<div class="proposal-status-container"><div class="proposal-status-text rejected-text">거절한 제안입니다</div></div>');
        $acceptBtn.remove();
    }
};

// 제안 수락 버튼 클릭 이벤트
$("#acceptBtn").on("click", function() {
    const projectId = $(this).data("project-id");
    const contextPath = document.body.dataset.url || "";

    $.ajax({
        url: `${contextPath}/freeOffer/accept`,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({ projectNo: projectId }),
        success: function(response) {
            alertMessage("제안이 수락되었습니다!", "success");
            updateProposalStatus(projectId, "accepted");
        },
        error: function(xhr, status, error) {
            alertMessage("제안 수락에 실패했습니다.", "error");
        }
    });
});

// 제안 거절 버튼 클릭 이벤트
$("#rejectBtn").on("click", function() {
    const projectId = $(this).data("project-id");
    const contextPath = document.body.dataset.url || "";

    $.ajax({
        url: `${contextPath}/freeOffer/reject`,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({ projectNo: projectId }),
        success: function(response) {
            alertMessage("제안이 거절되었습니다!", "success");
            updateProposalStatus(projectId, "rejected");
        },
        error: function(xhr, status, error) {
            alertMessage("제안 거절에 실패했습니다.", "error");
        }
    });
});

// 페이지 로드 시 제안 상태를 확인하고 버튼 업데이트
$(document).ready(function() {
    $(".proposal-status").each(function() {
        const projectId = $(this).data("project-id");
        const status = $(this).data("status"); // "accepted" 또는 "rejected" 값 기대

        if (status === "accepted" || status === "rejected") {
            updateProposalStatus(projectId, status);
        }
    });
});
