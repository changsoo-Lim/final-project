document.addEventListener("DOMContentLoaded", async function () {
    const contextPath = document.getElementById("contextPath")?.value || "";

    // CKEditor 초기화
    let editor;
    try {
        editor = await ClassicEditor.create(document.querySelector('#answerContent'));
    } catch (error) {
        console.error("CKEditor 초기화 오류:", error);
    }

    // CKEditor 데이터 저장
    document.getElementById("answerButton")?.addEventListener("click", async function () {
        const noticeNo = this.dataset.noticeNo;
        const answerContent = editor?.getData().trim();

        if (!answerContent) {
            sweatAlert("warning","답변 내용을 입력해주세요.");
            return;
        }

        const action = this.dataset.action || "register"; // 기본값은 'register'
        const url = `${contextPath}/admin/inquiry/${noticeNo}/answer`;

        try {
            const response = await fetch(url, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ noticeInquiriesCont: answerContent }),
            });

            const data = await response.json();

            if (data.status === "success") {
                sweatAlert("success", action === "register" ? "답변이 등록되었습니다." : "답변이 수정되었습니다.");
                location.reload();
            } else {
                sweatAlert("success", data.message || "작업 실패.");
            }
        } catch (error) {
            console.error("답변 요청 오류:", error);
            sweatAlert("success", "답변 처리 중 오류가 발생했습니다.");
        }
    });

    // 문의 삭제 처리
    document.getElementById("deleteInquiry")?.addEventListener("click", function () {
        const noticeNo = this.dataset.noticeNo;
		
        if (confirm("정말 삭제하시겠습니까?")) {
            const url = `${contextPath}/admin/inquiry/${noticeNo}/delete`;

            fetch(url, { method: "POST", headers: { "Content-Type": "application/json" } })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP 상태 코드: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.status === "success") {
                        sweatAlert(data.message);
                        window.location.href = `${contextPath}/admin/inquiry`;
                    } else {
                        sweatAlert(data.message);
                    }
                })
                .catch(error => {
                    console.error("문의 삭제 오류:", error);
                    sweatAlert("문의 삭제 중 오류가 발생했습니다.");
                });
        }
    });

    // 체크박스 전체 선택 및 개별 선택
    const checkAll = document.getElementById("checkAll");
    const checkboxes = document.querySelectorAll(".delete-checkbox");

    if (checkAll) {
        checkAll.addEventListener("change", function () {
            const isChecked = this.checked;
            checkboxes.forEach(checkbox => {
                checkbox.checked = isChecked;
            });
        });

        checkboxes.forEach(checkbox => {
            checkbox.addEventListener("change", function () {
                checkAll.checked = Array.from(checkboxes).every(cb => cb.checked);
            });
        });
    }

    // 삭제 폼 제출 시 선택 여부 확인
    const deleteForm = document.getElementById("deleteForm");
    if (deleteForm) {
        deleteForm.addEventListener("submit", function (e) {
            const selectedCheckboxes = document.querySelectorAll(".delete-checkbox:checked");
            if (selectedCheckboxes.length === 0) {
                e.preventDefault();
                sweatAlert("삭제할 항목을 선택하세요.");
            }
        });
    }

    // 삭제 버튼 개별 처리
    document.querySelectorAll(".delete-single-btn").forEach(button => {
        button.addEventListener("click", function () {
            const noticeNo = this.dataset.noticeNo;
            if (!noticeNo) {
                sweatAlert("삭제할 항목의 번호가 없습니다.");
                return;
            }

            if (confirm("정말 삭제하시겠습니까?")) {
                const url = `${contextPath}/admin/inquiry/${noticeNo}/delete`;

                fetch(url, { method: "POST" })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`HTTP error! Status: ${response.status}`);
                        }
                        return response.json();
                    })
                    .then(data => {
                        if (data.status === "success") {
                            sweatAlert("삭제가 완료되었습니다.");
                            location.reload();
                        } else {
                            sweatAlert(data.message || "삭제 실패.");
                        }
                    })
                    .catch(error => {
                        console.error("삭제 요청 오류:", error);
                        sweatAlert("삭제 요청 중 오류가 발생했습니다.");
                    });
            }
        });
    });

    // 답변 대기 상태 필터 처리
    const onlyPendingCheckbox = document.getElementById("onlyPending");
    if (onlyPendingCheckbox) {
        onlyPendingCheckbox.addEventListener("change", function () {
            const url = new URL(window.location.href);
            if (this.checked) {
                url.searchParams.set("pending", "true"); // 답변 대기만 보기
            } else {
                url.searchParams.delete("pending"); // 필터 해제
            }
            url.searchParams.delete("page"); // 페이지 번호 초기화
            window.location.href = url.toString(); // 페이지 새로고침
        });
    }
});
