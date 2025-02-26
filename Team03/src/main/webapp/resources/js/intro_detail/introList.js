document.querySelectorAll(".delBtn").forEach(button => {
    button.addEventListener("click", function (e) {
        e.preventDefault();

        const introNo = this.closest(".intro-item").querySelector("#introNo").value;
        const baseUrl = document.body.dataset.url;

        Swal.fire({
            icon: 'warning',
            title: '정말 삭제 하시겠습니까?',
            showCancelButton: true,
            confirmButtonText: '네, 삭제합니다',
            cancelButtonText: '취소',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                fetch(`${baseUrl}/intro/${introNo}`, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ introNo: introNo })
                })
                .then(response => {
                    if (response.ok) {
                        Swal.fire({
                            icon: 'success',
                            title: '삭제 완료',
                            text: '삭제되었습니다.',
                            confirmButtonText: '확인'
                        }).then(() => {
                            window.location.href = ""; // 성공 시 리다이렉트
                        });
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: '삭제 실패',
                            text: '삭제에 실패했습니다. 다시 시도해주세요.',
                            confirmButtonText: '확인'
                        });
                    }
                })
                .catch(error => {
                    console.error("삭제 중 오류 발생:", error);
                    Swal.fire({
                        icon: 'error',
                        title: '오류 발생',
                        text: '오류가 발생했습니다. 관리자에게 문의하세요.',
                        confirmButtonText: '확인'
                    });
                });
            }
        });
    });
});
