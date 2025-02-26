document.addEventListener("DOMContentLoaded", function() {
	const contextPath = document.getElementById('contextPath')?.value || '';

// 체크박스 전체 선택 및 개별 선택
	const checkAll = document.getElementById('checkAll');
	const checkboxes = document.querySelectorAll('.delete-checkbox');

	if (checkAll) {
		checkAll.addEventListener('change', function () {
			const isChecked = this.checked;
			checkboxes.forEach(checkbox => {
				checkbox.checked = isChecked;
			});
		});

		checkboxes.forEach(checkbox => {
			checkbox.addEventListener('change', function () {
				checkAll.checked = Array.from(checkboxes).every(cb => cb.checked);
			});
		});
	}

	// 삭제 폼 제출 시 선택 여부 확인
	const deleteForm = document.getElementById('deleteForm');
	if (deleteForm) {
		deleteForm.addEventListener('submit', function (e) {
			const selectedCheckboxes = document.querySelectorAll('.delete-checkbox:checked');
			if (selectedCheckboxes.length === 0) {
				e.preventDefault();
				alert('삭제할 항목을 하나 이상 선택하세요.');
			}
		});
	}

	// 삭제 버튼 개별 처리
	document.querySelectorAll('.delete-single-btn').forEach(button => {
		button.addEventListener('click', function () {
			const blacklistNo = this.dataset.boardNo;
			if (!blacklistNo) {
				alert('삭제할 항목의 번호가 없습니다.');
				return;
			}

			if (confirm('정말 삭제하시겠습니까?')) {
				const contextPath = document.getElementById('contextPath')?.value || '';
				const url = `${contextPath}/admin/blacklist/${blacklistNo}`;

				fetch(url, { method: 'POST' })
					.then(response => {
						if (!response.ok) {
							throw new Error(`HTTP error! Status: ${response.status}`);
						}
						return response.text();
					})
					.then(result => {
						alert('삭제가 완료되었습니다.');
						location.reload();
					})
					.catch(error => {
						console.error('삭제 요청 오류:', error);
						alert('삭제 요청 중 오류가 발생했습니다.');
					});
			}
		});
	});
});