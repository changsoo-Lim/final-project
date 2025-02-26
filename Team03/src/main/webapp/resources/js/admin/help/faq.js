document.addEventListener("DOMContentLoaded", function () {

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
                sweatAlert('warning','삭제할 항목을 하나 이상 선택하세요.');
            } else {
				e.preventDefault();
				
				Swal.fire({
                    title: '삭제 여부',
                    text: '정말 삭제하시겠습니까? ',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonText: '확인',
                    cancelButtonText: '취소',
                    reverseButtons: true,
                }).then((result) => {
                    if (result.isConfirmed) {
                        $('#deleteForm').submit();
                    }
                });
			}
        });
    }

	// 삭제 버튼 개별 처리
	document.querySelectorAll('.delete-single-btn').forEach(button => {
		button.addEventListener('click', function () {
				const noticeNo = this.dataset.noticeNo;
				if (!noticeNo) {
					sweatAlert('warning','삭제할 항목의 번호가 없습니다.');
					return;
				}
				Swal.fire({
	                title: '삭제',
	                text: '정말 삭제하시겠습니까?',
	                icon: 'question',
	                showCancelButton: true, // 취소 버튼 표시
	                confirmButtonText: '확인',
	                cancelButtonText: '취소',
	                reverseButtons: true, // 버튼 순서 변경 (취소가 왼쪽으로)
	            }).then( async (result) => {
	            if (!result.isConfirmed) {
					return;
	            } else {
					const contextPath = document.getElementById('contextPath')?.value || '';
					const url = `${contextPath}/admin/faq/${noticeNo}/delete`;
	
					fetch(url, { method: 'POST' })
					.then(response => {
						if (!response.ok) {
							throw new Error(`HTTP error! Status: ${response.status}`);
						}
						return response.text();
					})
					.then(result => {
						sweatAlert('error','삭제가 완료되었습니다.');
						location.reload();
					})
					.catch(error => {
						console.error('삭제 요청 오류:', error);
						sweatAlert('error','삭제 요청 중 오류가 발생했습니다.');
					});
				}
			});
		});
	});
});

