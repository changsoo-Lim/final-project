// 페이지 로드 시 상태가 'B'인 경우 버튼을 '블랙 완료'로 변경하고 비활성화
document.querySelectorAll('.table tbody tr').forEach(row => {
    const statusElement = row.querySelector('td:nth-child(7) span'); // 상태 셀 찾기
    const blacklistButton = row.querySelector('.open-blacklist-modal'); // 블랙리스트 추가 버튼 찾기

    if (statusElement && statusElement.classList.contains('status-red')) {
        if (blacklistButton) {
            blacklistButton.textContent = "블랙 완료";
            blacklistButton.disabled = true; // 버튼 비활성화
        }
    }
});

document.querySelectorAll('.open-blacklist-modal').forEach(button => {
    button.addEventListener('click', function () {
        const baseUrl = document.body.dataset.url;
        const memId = this.getAttribute('data-mem-id');
        const reportNo = this.getAttribute('data-report-no');
        
        // 모달 데이터 채우기
        document.getElementById('MemId').value = memId;

		document.getElementById('spanMemId').textContent = memId;
        console.log(reportNo);
        
        // Form action 업데이트
        const form = document.getElementById('blacklistForm');
        form.action = `${baseUrl}/admin/report/${reportNo}`;

        // 상태가 'B'이면 버튼을 비활성화하고 텍스트를 '블랙 완료'로 변경
        const statusElement = this.closest('tr').querySelector('td:nth-child(7) span'); // 상태를 나타내는 셀
        if (statusElement && statusElement.classList.contains('status-red')) {
            this.disabled = true;
            this.textContent = "블랙 완료";
        }
    });
});

