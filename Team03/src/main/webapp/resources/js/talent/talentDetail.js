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





document.addEventListener("DOMContentLoaded", () => {
	const timelineItems = document.querySelectorAll('.timeline-item');
	const modal = new bootstrap.Modal(document.getElementById('addModal')); // 모달 인스턴스 생성
	const employSelect = document.getElementById('employSelect'); // 상위 공고 셀렉트 박스
	const fieldSelect = document.getElementById('fieldSelect'); // 하위 모집 분야 셀렉트 박스
	const updateBtn = document.getElementById('update-button');
	const contextPath = document.body.dataset.url || "";

	// 클릭 이벤트 추가
	timelineItems.forEach(item => {
		item.addEventListener('click', () => {
			// 기존에 선택된 항목에서 'selected' 클래스 제거
			timelineItems.forEach(i => i.classList.remove('selected'));

			// 클릭된 항목에 'selected' 클래스 추가
			item.classList.add('selected');
		});
	});

	// 포지션 제안 버튼 이벤트 리스너
	const proposalBtn = document.querySelector('.proposal-btn');
	if (proposalBtn) {
		proposalBtn.addEventListener('click', proposalButtonHandler);
	}

	// Top 버튼 이벤트 리스너 
	const topBtn = document.querySelector('.top');
	if (topBtn) {
		topBtn.addEventListener('click', scrollToTop);
	}


	// 페이지 최상단으로 스크롤하는 함수
	function scrollToTop() {
		window.scrollTo({ top: 0, behavior: 'smooth' });
	}

	// 공통 초기화 함수
	function resetModalFields() {
		employSelect.value = ""; // 상위 공고 초기화
		fieldSelect.innerHTML = `<option value="">모집 분야를 선택하세요</option>`; // 하위 모집 분야 초기화
		fieldSelect.disabled = true; // 비활성화
	}


	// 포지션 제안 버튼 클릭 이벤트 처리
	function proposalButtonHandler() {
		resetModalFields();
		modal.show();

		// AJAX 작업 또는 추가 이벤트 처리 로직 작성
	}



	// 상위 공고 선택 시 하위 모집 분야 업데이트
	employSelect.addEventListener("change", (event) => {
		const selectedEmployNo = event.target.value;

		// 선택된 employNo에 해당하는 fieldList 찾기
		const selectedEmploy = employList.find(employ => employ.employNo === selectedEmployNo);

		// 하위 모집 분야 초기화
		fieldSelect.innerHTML = `<option value="">모집 분야를 선택하세요</option>`;

		// 하위 모집 분야가 있을 경우 추가
		if (selectedEmploy && selectedEmploy.fieldList.length > 0) {
			selectedEmploy.fieldList.forEach(field => {
				const option = document.createElement("option");
				option.value = field.filedNo;
				option.textContent = field.filedNm;
				fieldSelect.appendChild(option);
			});

			// 활성화
			fieldSelect.disabled = false;
		} else {
			// 하위 모집 분야 없을 경우 비활성화
			fieldSelect.disabled = true;
		}
	});

	// 초기화
	fieldSelect.disabled = true;

	updateBtn.addEventListener("click", () => {
		// 상위 공고와 하위 모집 분야 셀렉트 박스 가져오기
		const employSelect = document.getElementById("employSelect");
		const fieldSelect = document.getElementById("fieldSelect");

		// 선택 값 확인
		const selectedEmploy = employSelect.value;
		const selectedField = fieldSelect.value;

		// 검증 로직 추가
		if (!selectedEmploy) {
			alertMessage("상위 공고를 선택해주세요.", "warning")

			return; // 선택되지 않았으면 함수 종료
		}

		if (!selectedField) {
			alertMessage("하위 모집 분야를 선택해주세요.", "warning")
			return; // 선택되지 않았으면 함수 종료
		}

		// 모두 선택되었을 때 확인 메시지 표시
		confirmMessage("포지션 제안을 하시겠습니까?", "공고 정보", function(result) {
			if (result) {
				const memId = document.getElementById("memId").value;
				
				const requestData = {
    					memId: memId,
    					filedNo: selectedField };

				$.ajax({
					url: `${contextPath}/position/insert`,
					method: "POST",
					contentType: "application/json", // JSON 데이터 전송
					data: JSON.stringify(requestData), // JSON 문자열로 변환하여 전송
					success: function(response) {
						if(response.status === 'success'){
							alertMessage("포지션 제안이 성공적으로 완료되었습니다.", "success")	
						}else{
							alertMessage("이미 제한한 포지션 입니다.", "error")	
						}
					},
					error: function(xhr, status, error) {
						console.error("오류 발생:", error);
						alertMessage("서버 오류 발생 다시 시도 해주세요.", "error")
					}
				});
				modal.hide();
			}
		});
	});

});


function scrollToSection(sectionId) {
	// 선택한 섹션으로 스크롤
	const section = document.getElementById(sectionId);
	if (section) {
		section.scrollIntoView({ behavior: 'smooth', block: 'start' });
	}

	// 모든 타임라인 항목에서 'selected' 클래스 제거
	const timelineItems = document.querySelectorAll('.timeline-item');
	timelineItems.forEach(item => item.classList.remove('selected'));

	// 클릭한 타임라인 항목에 'selected' 클래스 추가
	const clickedItem = document.querySelector(`.timeline-item[onclick="scrollToSection('${sectionId}')"]`);
	if (clickedItem) {
		clickedItem.classList.add('selected');
	}
}
/*
// 페이지 최상단으로 스크롤하는 함수
function scrollToTop() {
	window.scrollTo({ top: 0, behavior: 'smooth' });
}

// 포지션 제안 버튼 클릭 이벤트 처리
function proposalButtonHandler() {
	console.log("포지션 제안 버튼 클릭 이벤트 발생");
	modal.show();

	// AJAX 작업 또는 추가 이벤트 처리 로직 작성
}*/