document.addEventListener("DOMContentLoaded", () => {
	const contextPath = document.body.dataset.url || "";
	
	    const checkAll = document.getElementById("checkAll");
    const deleteCheckboxes = document.querySelectorAll(".delete-checkbox");
    const editButton = document.querySelector(".edit-btn");
    const deleteButton = document.querySelector(".delete-btn");

    // 전체 선택/해제 체크박스 이벤트
    checkAll.addEventListener("change", () => {
        deleteCheckboxes.forEach(checkbox => {
            checkbox.checked = checkAll.checked;
        });
    });

    // 수정 버튼 클릭 이벤트
    editButton.addEventListener("click", () => {
        const selectedCheckboxes = Array.from(deleteCheckboxes).filter(checkbox => checkbox.checked);

        if (selectedCheckboxes.length === 1) {
            const projectId = selectedCheckboxes[0].value;
            console.log(`수정할 프로젝트 ID: ${projectId}`);
			window.location.href = `${contextPath}/project/${projectId}/form`;
        } else if (selectedCheckboxes.length === 0) {
            alert("수정할 항목을 선택하세요.");
        } else {
            alert("수정은 한 번에 하나의 항목만 가능합니다.");
        }
    });


    // 삭제 버튼 클릭 이벤트
    deleteButton.addEventListener("click", () => {
        const selectedCheckboxes = Array.from(deleteCheckboxes).filter(checkbox => checkbox.checked);

        if (selectedCheckboxes.length > 0) {
            const projectIds = selectedCheckboxes.map(checkbox => checkbox.value);
            console.log(`삭제할 프로젝트 ID 목록: ${projectIds.join(", ")}`);

            fetch(`${contextPath}/project/${projectIds.join(",")}/delete`, { method: "PUT" })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.status === "success") {
                        alert(data.message);
                        selectedCheckboxes.forEach(checkbox => {
                            const projectRow = checkbox.closest("tr");
                            if (projectRow) {
                                projectRow.remove(); // DOM에서 삭제
                            }
                        });
                    } else {
                        alert(data.message);
                    }
                })
                .catch(error => {
                    console.error("삭제 요청 실패:", error);
                    alert("삭제 요청 처리 중 문제가 발생했습니다.");
                });
        } else {
            alert("삭제할 항목을 선택하세요.");
        }
    });
	

});
