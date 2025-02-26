// 상세보기 버튼 클릭 시 모달 띄우기
document.querySelectorAll('#reviewTable tbody .open-modal-btn').forEach(function (button) {
    button.addEventListener('click', function (event) {
        event.stopPropagation(); // 버튼 클릭 시 부모 tr 이벤트 전파 방지

        const row = this.closest('tr'); // 버튼이 속한 tr 가져오기
        const reviewNo = row.getAttribute('data-review-no');
        const reviewTitle = row.getAttribute('data-review-title');
        const reviewCont = row.getAttribute('data-review-cont');
        const reviewStatus = row.getAttribute('data-review-status');

        // 모달에 데이터 채우기
        document.getElementById('modalReviewNo').value = reviewNo;
        document.getElementById('modalReviewTitle').value = reviewTitle;
        document.getElementById('modalReviewCont').value = reviewCont;
        document.getElementById('modalReviewStatus').value = reviewStatus;

        // 승인 버튼 표시/숨김 처리
        const approveBtn = document.getElementById('approveBtn');
        if (reviewStatus === 'Y') {
            approveBtn.style.display = 'none'; // 승인된 경우 버튼 숨김
        } else {
            approveBtn.style.display = 'inline-block'; // 승인되지 않은 경우 버튼 표시
        }

		// 모달 열기 (Bootstrap 모달 호출)
        const reviewModal = new bootstrap.Modal(document.getElementById('reviewModal'));
        reviewModal.show();
    });
});

// 승인 버튼 클릭 시 이벤트 처리
document.getElementById('approveBtn').addEventListener('click', function () {
    const reviewNo = document.getElementById('modalReviewNo').value;
	const baseUrl = document.body.dataset.url;
    // 승인 요청 fetch 처리
    fetch(`${baseUrl}/admin/review/${reviewNo}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            return response.json(); // JSON 응답 처리
        } else {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
    })
    .then(data => {
        if (data.success) {
            Swal.fire({
                title: '승인 완료',
                text: data.message || '승인이 성공적으로 처리되었습니다.',
                icon: 'success',
                confirmButtonText: '확인'
            }).then(() => {
                location.reload();
            });
        } else {
            Swal.fire({
                title: '승인 실패',
                text: data.message || '승인에 실패했습니다. 다시 시도해주세요.',
                icon: 'error',
                confirmButtonText: '확인'
            });
        }
    })
    .catch(error => {
        Swal.fire({
            title: '오류 발생',
            text: error.message || '오류가 발생했습니다. 관리자에게 문의하세요.',
            icon: 'error',
            confirmButtonText: '확인'
        });
    });
});
// 모든 .star-rating 요소에 대해 별을 생성하여 표시
document.querySelectorAll('.star-rating').forEach(function (element) {
    var rating = parseInt(element.getAttribute('data-rating')); // 평점 값 가져오기
    var maxStars = 5; // 최대 별 개수
    var starsHtml = '';

    // 별 생성 (⭐과 ☆)
    for (var i = 1; i <= maxStars; i++) {
        if (i <= rating) {
            starsHtml += '⭐'; // 채워진 별
        } else {
            starsHtml += '☆'; // 빈 별
        }
    }

    element.innerHTML = starsHtml; // 별 표시
});