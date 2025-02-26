class SearchFormHelper {
    constructor(formId) {
        this.form = document.getElementById(formId);
    }

    // Hidden input을 추가하는 함수
    addHiddenInput(name, values) {
        const existingInputs = this.form.querySelectorAll(`input[name^="searchMap['${name}']"]`);
        existingInputs.forEach(input => input.remove()); // 기존 입력 제거

        values.forEach((value, index) => {
            const hiddenInput = document.createElement("input");
            hiddenInput.type = "hidden";
            hiddenInput.name = `searchMap['${name}'][${index}]`;
            hiddenInput.value = value;
            this.form.appendChild(hiddenInput);
        });
    }

    // 선택된 항목 박스를 생성하는 함수
    createSelectedBox(container, value, text, callback, type) {
        const selBox = document.createElement("div");
        selBox.className = "selected-box";
        selBox.setAttribute("data-value", value);
        selBox.setAttribute("data-type", type); // data-type 속성 추가
        selBox.innerHTML = `
            ${text}
            <button type="button" class="remove-btn">X</button>
        `;
        selBox.querySelector(".remove-btn").addEventListener("click", () => {
            callback(value);
            selBox.remove();
        });

        container.appendChild(selBox);
    }

    // 특정 value의 박스를 제거하는 함수
    removeSelectedBox(container, value) {
        const box = container.querySelector(`.selected-box[data-value="${value}"]`);
        if (box) box.remove();
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const formHelper = new SearchFormHelper("searchForm");

    const fieldSelect = document.querySelector("select[name='freeField']");
    const jobSelect = document.querySelector("select[name='freeJob']");
    const selContainer = document.querySelector('.selected-container');
    const modalConfirmBtn = document.querySelector("#freeJob-modal .btn-primary");
    const skillSearchInput = document.getElementById("skillSearchInput");
    const skillList = document.getElementById("skillList");
    const skillItems = skillList.querySelectorAll(".skill-item");

    const freeStyleBtn = document.getElementById("freeStyle-btn");
    const freeStyleDropdown = document.getElementById("freeStyle-dropdown");

    let selectedFieldValue = null;
    let selectedFieldText = null;
    let selectedJobs = new Map();
    const selectedSkills = new Set();

    // === 직군 변경 ===
    fieldSelect.addEventListener("change", () => {
        selectedFieldValue = fieldSelect.value;
        selectedFieldText = fieldSelect.options[fieldSelect.selectedIndex]?.text || "직군/직무";
		// 직군과 직무 관련 박스만 제거
	    Array.from(selContainer.children).forEach((box) => {
	        const type = box.getAttribute("data-type");
	        if (type === "field" || type === "job") {
	            box.remove();
	        }
	    });

        jobSelect.innerHTML = `<option value="">직무를 선택해 주세요.</option>`;
        selectedJobs.clear();
        formHelper.addHiddenInput("freeField", selectedFieldValue ? [selectedFieldValue] : []);

        if (!selectedFieldValue) return;
		console.log(selectedFieldValue);
        const filteredJobs = codeList.filter(code => code.codeParent === selectedFieldValue);
        filteredJobs.forEach(job => {
            const option = document.createElement("option");
            option.value = job.codeCd;
            option.textContent = job.codeNm;
            jobSelect.appendChild(option);
        });
    });
	
	modalConfirmBtn.addEventListener("click", () => {
	    // 직군만 선택되고 직무가 선택되지 않은 경우
	    if (selectedFieldValue && selectedJobs.size === 0) {
	        // 직군 관련 작은 박스 제거
	        Array.from(selContainer.children).forEach((box) => {
	            if (box.getAttribute("data-type") === "field" || box.getAttribute("data-type") === "job") {
	                box.remove();
	            }
	        });
	
	        // 직군 작은 박스 추가
	        formHelper.createSelectedBox(selContainer, selectedFieldValue, selectedFieldText, () => {
	            selectedFieldValue = null;
	            selectedFieldText = null;
	            formHelper.addHiddenInput("freeField", []);
	        }, "field");
	
	        // Hidden Input 업데이트
	        formHelper.addHiddenInput("freeField", [selectedFieldValue]);
	        formHelper.addHiddenInput("freeJob", []); // 직무 초기화
	    }
	});

    // === 직무 선택 ===
    jobSelect.addEventListener("change", () => {
        const selectedValue = jobSelect.value;
        const selectedText = jobSelect.options[jobSelect.selectedIndex]?.text;

        if (!selectedValue || selectedJobs.has(selectedValue)) return;

        formHelper.removeSelectedBox(selContainer, selectedFieldValue);
        selectedJobs.set(selectedValue, selectedText);

        formHelper.createSelectedBox(selContainer, selectedValue, `${selectedFieldText} > ${selectedText}`, (value) => {
            selectedJobs.delete(value);
            updateHiddenInputs();

            if (selectedJobs.size === 0 && selectedFieldValue) {
                formHelper.createSelectedBox(selContainer, selectedFieldValue, selectedFieldText, () => {
                    selectedFieldValue = null;
                    selectedFieldText = null;
                    formHelper.addHiddenInput("freeField", []);
                }, "field");
            }
        }, "job");

        updateHiddenInputs();
    });

    modalConfirmBtn.addEventListener("click", () => {
        if (selectedFieldValue && selectedJobs.size === 0) {
            formHelper.clearSelectedBoxExcept(selContainer, "skill");
            formHelper.createSelectedBox(selContainer, selectedFieldValue, selectedFieldText, () => {
                selectedFieldValue = null;
                selectedFieldText = null;
                formHelper.addHiddenInput("freeField", []);
            }, "field");

            updateHiddenInputs();
        }
    });

    function updateHiddenInputs() {
        formHelper.addHiddenInput("freeJob", Array.from(selectedJobs.keys()));
        formHelper.addHiddenInput("freeField", selectedFieldValue ? [selectedFieldValue] : []);
    }

    // === 근무 형태 관련 ===
    freeStyleBtn.addEventListener("click", (e) => {
	    e.stopPropagation();
	    const isDropdownVisible = freeStyleDropdown.style.display === "block";
	
	    document.querySelectorAll(".sel-dropdown").forEach(dropdown => dropdown.style.display = "none");
	
	    freeStyleDropdown.style.display = isDropdownVisible ? "none" : "block";
	    freeStyleBtn.querySelector(".arrow").textContent = isDropdownVisible ? "▼" : "▲";
	});
	
	freeStyleDropdown.addEventListener("click", (e) => {
	    if (e.target.classList.contains("sel-item")) {
	        const selectedValue = e.target.getAttribute("data-value");
	        const selectedText = e.target.textContent;
	
	        // Hidden input과 작은 박스 업데이트
	        formHelper.addHiddenInput("freeStyle", [selectedValue]);
	
	        // 근무 형태는 중복되지 않으므로 기존 박스 제거 후 새 박스 추가
	        const existingBox = selContainer.querySelector(`.selected-box[data-type="freeStyle"]`);
	        if (existingBox) existingBox.remove();
	
	        formHelper.createSelectedBox(selContainer, selectedValue, `근무 형태 > ${selectedText}`, () => {
	            formHelper.addHiddenInput("freeStyle", []);
	        }, "freeStyle");
	
	        freeStyleDropdown.style.display = "none";
	    }
	});
	
	document.addEventListener("click", () => {
	    freeStyleDropdown.style.display = "none";
	    freeStyleBtn.querySelector(".arrow").textContent = "▼";
	});

    // === 스킬 관련 ===
    skillSearchInput.addEventListener("click", (event) => {
        event.stopPropagation();
        skillList.style.display = "block";
    });

    skillSearchInput.addEventListener("input", (event) => {
        const query = event.target.value.toLowerCase().trim();

        skillItems.forEach((item) => {
            const text = item.textContent.toLowerCase();
            item.style.display = text.includes(query) ? "" : "none";
        });
    });

    skillList.addEventListener("click", (event) => {
        if (event.target.classList.contains("skill-item")) {
            const selectedValue = event.target.getAttribute("data-value");
            const selectedText = "스킬 > "+event.target.textContent;

            if (!selectedSkills.has(selectedValue)) {
                selectedSkills.add(selectedValue);
                formHelper.createSelectedBox(selContainer, selectedValue, selectedText, (value) => {
                    selectedSkills.delete(value);
                    updateHiddenSkills();
                }, "skill");
                updateHiddenSkills();
            }
            skillList.style.display = "none";
        }
    });

    document.addEventListener("click", (event) => {
        if (!skillSearchInput.contains(event.target) && !skillList.contains(event.target)) {
            skillList.style.display = "none";
        }
    });

    function updateHiddenSkills() {
        formHelper.addHiddenInput("freeSkills", Array.from(selectedSkills));
    }
});
/*
*
*
*/
function paging(page) {
    console.log(page);

    // 페이지 번호를 searchForm에 업데이트
    searchForm.page.value = page;

    // searchMap hidden input 갱신
    const searchMapFields = $(".selected-container .selected-box"); // 선택된 검색 조건들
    searchForm.querySelectorAll("input[name^='searchMap']").forEach(input => input.remove()); // 기존 searchMap input 제거

    // 기존 선택된 검색 조건들 추가
    searchMapFields.each(function (index, element) {
        const fieldType = $(element).attr("data-type"); // data-type (예: freeField, freeJob, freeSkills 등)
        const fieldValue = $(element).attr("data-value");

        if (fieldType && fieldValue) {
            const hiddenInput = document.createElement("input");
            hiddenInput.type = "hidden";
            hiddenInput.name = `searchMap['${fieldType}'][${index}]`;
            hiddenInput.value = fieldValue;
            searchForm.appendChild(hiddenInput);
        }
    });

    // 폼 제출
    searchForm.requestSubmit();
}

document.addEventListener("DOMContentLoaded", ()=>{
	const $searchForm = $("#searchForm"); // 폼 객체
    const $searchBtn = $(".search-btn"); // 검색 버튼

    $searchBtn.on("click", function () {
        const $parent = $(this).parents(".search-area");

        // 기존 일반 필드 처리 (searchType, searchWord)
        $parent.find(":input[name]").each(function (index, ipt) {
            const inputName = ipt.name;
            const inputValue = ipt.value;

            if (searchForm[inputName]) {
                searchForm[inputName].value = inputValue;
            } else {
                // hidden input 추가
                $searchForm.append(`<input type="hidden" name="${inputName}" value="${inputValue}">`);
            }
        });

        // searchMap 처리
        const searchMapFields = $(".selected-container .selected-box"); // 선택된 검색 조건들
        $searchForm.find("input[name^='searchMap']").remove(); // 기존 searchMap input 제거

        searchMapFields.each(function (index, element) {
            const fieldType = $(element).attr("data-type"); // data-type (예: freeField, freeJob, freeSkills 등)
            const fieldValue = $(element).attr("data-value");

            if (fieldType && fieldValue) {
                $searchForm.append(`<input type="hidden" name="searchMap['${fieldType}'][${index}]" value="${fieldValue}">`);
            }
        });

        // 폼 제출
        console.log("Submitting form with updated searchMap...");
        $searchForm.submit();
    });
});
