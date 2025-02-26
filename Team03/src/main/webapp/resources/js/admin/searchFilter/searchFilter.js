document.addEventListener("DOMContentLoaded", () => {
    const codeList = JSON.parse(document.getElementById("codeListData").textContent);

    const rootCodeSelect = document.getElementById("rootCodeSelect");
    const inputFieldsContainer = document.getElementById("inputFieldsContainer");
    const codeForm = document.getElementById("codeForm");
    const showFormButton = document.getElementById("showFormButton");

    const editModal = new bootstrap.Modal(document.getElementById('editModal'));
    const modalCodeCd = document.getElementById("modalCodeCd");
    const modalCodeNm = document.getElementById("modalCodeNm");
    const modalCodeParent = document.getElementById("modalCodeParent");
    const saveChangesButton = document.getElementById("saveChangesButton");

    let currentEditingCode = null;

    // 루트 선택 박스에 옵션 추가
    codeList.filter(code => code.codeParent === null).forEach(code => {
        const option = document.createElement("option");
        option.value = code.codeCd;
        option.textContent = code.codeNm;
        rootCodeSelect.appendChild(option);
    });

    // 루트 선택 박스의 변경 이벤트 처리
    rootCodeSelect.addEventListener("change", (event) => {
        const selectedValue = event.target.value;

        // 동적 요소 및 수정 창 숨기기
        resetDynamicElements();
        closeEditModal(); // 수정 창 닫기

        if (selectedValue) {
            const selectedCode = codeList.find(code => code.codeCd === selectedValue);
            displaySelectedAsInput(selectedCode, inputFieldsContainer, true);
            updateDynamicFields(selectedValue, inputFieldsContainer);
        }
    });

    // 동적으로 생성된 요소를 모두 초기화하는 함수
    function resetDynamicElements() {
        inputFieldsContainer.innerHTML = "";
    }

    // 선택된 코드를 입력 필드로 표시하고 수정 및 삭제 버튼 추가하는 함수
    function displaySelectedAsInput(selectedCode, parentContainer, isRoot = false) {
        if (document.getElementById(`inputFields-${selectedCode.codeCd}`)) {
            return;
        }

        if (selectedCode) {
            const div = document.createElement("div");
            div.className = "input-group mb-3";
            div.id = `inputFields-${selectedCode.codeCd}`;

            // 공통코드
            const labelCd = document.createElement("label");
            labelCd.textContent = "공통코드: ";
            labelCd.className = "input-group-text";

            const inputCd = document.createElement("input");
            inputCd.type = "text";
            inputCd.className = "form-control";
            inputCd.value = selectedCode.codeCd !== null ? selectedCode.codeCd : '';
            inputCd.readOnly = true;

            // 코드명
            const labelNm = document.createElement("label");
            labelNm.textContent = "코드명: ";
            labelNm.className = "input-group-text";

            const inputNm = document.createElement("input");
            inputNm.type = "text";
            inputNm.className = "form-control";
            inputNm.value = selectedCode.codeNm !== null ? selectedCode.codeNm : '';
			inputNm.readOnly = true;	
			
            // 부모코드
            const labelParentCd = document.createElement("label");
            labelParentCd.textContent = "부모코드: ";
            labelParentCd.className = "input-group-text";

            const inputParentCd = document.createElement("input");
            inputParentCd.type = "text";
            inputParentCd.className = "form-control";
            inputParentCd.value = selectedCode.codeParent !== null ? selectedCode.codeParent : '';
			inputParentCd.readOnly = true;
			
            // 삭제 버튼
            const deleteButton = document.createElement("button");
            deleteButton.textContent = "삭제";
            deleteButton.className = "btn btn-danger mt-2";
            deleteButton.addEventListener("click", () => {
                deleteCode(selectedCode.codeCd);
            });

            // 수정 버튼
            const editButton = document.createElement("button");
            editButton.textContent = "수정";
            editButton.className = "btn btn-warning mt-2";
            editButton.addEventListener("click", () => {
                openEditModal(selectedCode);
            });

            div.appendChild(labelCd);
            div.appendChild(inputCd);
            div.appendChild(labelNm);
            div.appendChild(inputNm);
            div.appendChild(labelParentCd);
            div.appendChild(inputParentCd);
            div.appendChild(editButton);
            div.appendChild(deleteButton);

            parentContainer.appendChild(div);
        }
    }

    // 자식 코드를 위한 동적 필드 업데이트 함수
    function updateDynamicFields(parentCode, parentContainer) {
        const childCodes = codeList.filter(code => code.codeParent === parentCode);

        if (childCodes.length === 0) {
            return;
        }

        const childSelectDiv = document.createElement("div");
        childSelectDiv.className = "mb-3 childFields";

        const label = document.createElement("label");
        label.className = "form-label fw-bold";
        label.textContent = "하위 공통코드";

        const childSelect = document.createElement("select");
        childSelect.className = "form-select";
        childSelect.required = true;

        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "하위 공통코드를 선택해주세요.";
        childSelect.appendChild(defaultOption);

        childCodes.forEach(code => {
            const option = document.createElement("option");
            option.value = code.codeCd;
            option.textContent = code.codeNm;
            childSelect.appendChild(option);
        });

        childSelectDiv.appendChild(label);
        childSelectDiv.appendChild(childSelect);
        parentContainer.appendChild(childSelectDiv);

        childSelect.addEventListener("change", (event) => {
            const selectedValue = event.target.value;
            const selectedCode = codeList.find(code => code.codeCd === selectedValue);

            displaySelectedAsInput(selectedCode, parentContainer);
            updateDynamicFields(selectedValue, parentContainer);
        });
    }

    // 수정 모달 열기 함수
    function openEditModal(selectedCode) {
        currentEditingCode = selectedCode;
        modalCodeCd.value = selectedCode.codeCd;
        modalCodeNm.value = selectedCode.codeNm;
        modalCodeParent.value = selectedCode.codeParent;
        editModal.show();
    }

    // 수정 모달 닫기 함수
    function closeEditModal() {
        editModal.hide();
        currentEditingCode = null;
    }

    // 저장 버튼 클릭 이벤트
    saveChangesButton.addEventListener("click", () => {
        if (currentEditingCode) {
            const updatedData = {
                codeCd: modalCodeCd.value,
                codeNm: modalCodeNm.value,
                codeParent: modalCodeParent.value,
            };
			
            updateCode(updatedData);
        }
    });

    // 수정 코드 서버로 전송
    function updateCode(data) {
        const baseUrl = document.body.dataset.url;
        fetch(`${baseUrl}/admin/filter/update`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(response => {
                if (response.ok) {
                    Swal.fire({
					    title: '성공!',
					    text: '코드가 성공적으로 수정되었습니다.',
					    icon: 'success',
					    confirmButtonText: '확인'
					});
                    location.reload();
                } else {
                    alert("수정에 실패했습니다. 다시 시도해주세요.");
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("수정 중 오류가 발생했습니다.");
            });
    }

    function deleteCode(codeCd) {
    const baseUrl = document.body.dataset.url;
    fetch(`${baseUrl}/admin/filter/delete/${codeCd}`, {
        method: 'POST',
    })
    .then(response => {
        if (response.ok) {
				Swal.fire({
					    title: '성공!',
					    text: '코드가 성공적으로 삭제되었습니다.',
					    icon: 'success',
					    confirmButtonText: '확인'
					});
			location.reload();
            // 삭제된 코드의 DOM 요소 제거
            const elementToRemove = document.getElementById(`inputFields-${codeCd}`);
            if (elementToRemove) {
                elementToRemove.remove();
            }
        } else {
            alert("삭제에 실패했습니다. 다시 시도해주세요.");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("삭제 중 오류가 발생했습니다.");
    });
}

    // 폼 표시/숨김 버튼의 클릭 이벤트 처리
    showFormButton.addEventListener("click", () => {
        if (codeForm.style.display === "block") {
            codeForm.style.display = "none";
            showFormButton.textContent = "새 공통 코드추가";
        } else {
            codeForm.style.display = "block";
            showFormButton.textContent = "닫기";
        }
    });
});

document.addEventListener("DOMContentLoaded", () => {
    const codeList = JSON.parse(document.getElementById("codeListData").textContent);
    const autocompleteResultsCd = document.getElementById("autocomplete-results-codeCd");
    const autocompleteResultsNm = document.getElementById("autocomplete-results-codeNm");
    const autocompleteResultsParent = document.getElementById("autocomplete-results-codeParent");

    const closeAllAutocompleteResults = () => {
        autocompleteResultsCd.innerHTML = "";
        autocompleteResultsNm.innerHTML = "";
        autocompleteResultsParent.innerHTML = "";
    };

    // 새 공통코드 입력 필드에 입력 이벤트 처리
    document.getElementById("codeCd").addEventListener("input", function() {
        const query = this.value.toLowerCase();

        if (query) {
            const matchingCodes = codeList.filter(code => 
                code.codeCd.toLowerCase().includes(query) ||
                code.codeNm.toLowerCase().includes(query) ||
                (code.codeParent && code.codeParent.toLowerCase().includes(query))
            );
            displayAutocompleteResults(matchingCodes, autocompleteResultsCd);
        } else {
            autocompleteResultsCd.innerHTML = "";
        }
    });

    // 새 코드명 입력 필드에 입력 이벤트 처리
    document.getElementById("codeNm").addEventListener("input", function() {
        const query = this.value.toLowerCase();

        if (query) {
            const matchingCodes = codeList.filter(code => 
                code.codeCd.toLowerCase().includes(query) ||
                code.codeNm.toLowerCase().includes(query) ||
                (code.codeParent && code.codeParent.toLowerCase().includes(query))
            );
            displayAutocompleteResults(matchingCodes, autocompleteResultsNm);
        } else {
            autocompleteResultsNm.innerHTML = "";
        }
    });

    // 새 부모코드 입력 필드에 입력 이벤트 처리
    document.getElementById("codeParent").addEventListener("input", function() {
        const query = this.value.toLowerCase();

        if (query) {
            const matchingCodes = codeList.filter(code => 
                code.codeCd.toLowerCase().includes(query) ||
                code.codeNm.toLowerCase().includes(query) ||
                (code.codeParent && code.codeParent.toLowerCase().includes(query))
            );
            displayAutocompleteResults(matchingCodes, autocompleteResultsParent);
        } else {
            autocompleteResultsParent.innerHTML = "";
        }
    });

    // 자동완성 결과를 표시하는 함수
    function displayAutocompleteResults(codes, container) {
        container.innerHTML = "";

        if (codes.length > 0) {
            const maxResults = 10; // 최대 20개 결과

            codes.slice(0, maxResults).forEach(code => {
                const resultItem = document.createElement("div");
                resultItem.className = "autocomplete-item";
                resultItem.textContent = `${code.codeCd} (${code.codeNm})`;

                resultItem.addEventListener("click", function() {
                    const inputField = container.previousElementSibling;
                    inputField.value = code.codeCd;
                    container.innerHTML = "";
                });

                container.appendChild(resultItem);
            });

            // 스크롤바 추가 (필요 시)
            if (codes.length > maxResults) {
                container.style.maxHeight = "270px"; // 컨테이너의 최대 높이 설정
                container.style.overflowY = "auto"; // 스크롤바 활성화
            }
        }
    }

    // 클릭 이벤트를 활용하여 자동완성 닫기
    document.addEventListener("click", (event) => {
        if (!event.target.matches(".form-control, .autocomplete-item")) {
            closeAllAutocompleteResults();
        }
    });
});

