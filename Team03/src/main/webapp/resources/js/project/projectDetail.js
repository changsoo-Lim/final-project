/**
 * 
 */

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



// 더보기/닫기 처리
document.getElementById("read-more-btn").addEventListener("click", function() {
	document.getElementById("project-content-short").style.display = "none";
	document.getElementById("project-content-full").style.display = "block";
});

document.getElementById("read-less-btn").addEventListener("click", function() {
	document.getElementById("project-content-full").style.display = "none";
	document.getElementById("project-content-short").style.display = "block";
});

// 전체 선택 체크박스
document.getElementById("select-all").addEventListener("click", function() {
	const isChecked = this.checked;
	document.querySelectorAll(".select-item").forEach(function(checkbox) {
		checkbox.checked = isChecked;
	});
});

// 전체 선택 체크박스
document.getElementById("select-all").addEventListener("click", function () {
    const isChecked = this.checked;
    const checkboxes = document.querySelectorAll(".select-item");

    checkboxes.forEach(function (checkbox) {
        if (!checkbox.disabled) { // disabled 상태 체크박스는 제외
            checkbox.checked = isChecked;
        } else {
            checkbox.checked = false; // 명시적으로 체크 해제
        }
    });
    toggleCancelButton(); // 버튼 활성화 상태 업데이트
});




// 개별 체크박스 선택 시 버튼 활성화 상태 업데이트
document.querySelectorAll(".select-item").forEach(function(checkbox) {
	checkbox.addEventListener("change", toggleCancelButton);
});

// 제안 취소 버튼 활성화/비활성화
function toggleCancelButton() {
	const selectedItems = document.querySelectorAll(".select-item:checked");
	const cancelButton = document.getElementById("cancel-proposals-btn");
	cancelButton.disabled = selectedItems.length === 0;
}

// 제안 취소 버튼 클릭 이벤트
document.getElementById("cancel-proposals-btn").addEventListener("click", function() {
	const selectedItems = document.querySelectorAll(".select-item:checked");
	const selectedIds = Array.from(selectedItems).map(item => item.dataset.id); // 선택된 회원 ID 추출
	const contextPath = document.body.dataset.url || "";
	const projectNo = document.getElementById("projectNo").value;

	if (selectedIds.length === 0) {
		console.log("실행했냐??")
		alertMessage("선택된 항목이 없습니다.", "error");
		//return;
	}

	confirmMessage("제안을 취소 하시겠습니까?", "", function(result) {

		if (result) {

			// Ajax 요청
			$.ajax({
				url: `${contextPath}/project/${projectNo}/deletemember`, // 서버 요청 URL
				type: 'PUT', // 요청 방식
				contentType: 'application/json', // 전송 데이터 형식
				data: JSON.stringify(selectedIds), // 전송 데이터
				success: function(response) {
					// 요청 성공 시 실행할 코드
					alertMessage("요청이 성공적으로 처리되었습니다.", "success");
					//location.reload(); // 페이지 새로고침 (필요시)
					// 선택된 항목들 처리
					selectedItems.forEach(function(item) {
						const row = item.closest("tr"); // 현재 체크박스의 부모 행(tr) 찾기
						const statusCell = row.querySelector("td:nth-child(3)"); // 상태 칸 찾기

						item.checked = false; // 체크 해제
						item.disabled = true; // 체크박스 비활성화
						statusCell.textContent = "제안 취소"; // 상태 업데이트
					});

					// 전체 선택 체크박스 상태 업데이트
					document.getElementById("select-all").checked = false;

					// 버튼 다시 비활성화
					toggleCancelButton();

				},
				error: function(error) {
					// 요청 실패 시 실행할 코드
					alertMessage("요청 처리 중 에러가 발생했습니다.", "error");
				}
			});
		} else {
			return;
		}
	})

});
