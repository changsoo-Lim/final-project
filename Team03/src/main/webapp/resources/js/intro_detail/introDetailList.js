document.addEventListener("DOMContentLoaded", function () {
    // 모든 수정 버튼에 이벤트 리스너 추가
    const editButtons = document.querySelectorAll(".open-edit-modal");
    const modalIntroDetailNo = document.getElementById("modalIntroDetailNo");
    const modalIntroDetailTitle = document.getElementById("modalIntroDetailTitle");
    const modalIntroDetailCont = document.getElementById("modalIntroDetailCont");

    editButtons.forEach((button) => {
        button.addEventListener("click", function () {
            // 버튼의 데이터 속성에서 값 가져오기
            const id = this.getAttribute("data-id");
            const title = this.getAttribute("data-title");
            const content = this.getAttribute("data-content");

            // 모달의 입력 필드에 값 설정
            modalIntroDetailNo.value = id;
            modalIntroDetailTitle.value = title;
            modalIntroDetailCont.value = content;
        });
    });

    // 수정 버튼 클릭 시 폼 제출
    const saveButton = document.querySelector(".save-edit");
    const editForm = document.getElementById("editForm");

    saveButton.addEventListener("click", function () {
        editForm.action = `/intro/${modalIntroDetailNo.value}/edit`;
        editForm.method = "post";
        editForm.submit();
    });
});
