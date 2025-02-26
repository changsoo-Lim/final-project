document.getElementById("deadline").addEventListener("change", function () {
    const deadlineInput = document.getElementById("deadline");
    const startDateInput = document.getElementById("startDate");

    if (deadlineInput.value) {
        startDateInput.value = "";
        startDateInput.disabled = false;

        const deadline = new Date(deadlineInput.value);
        startDateInput.min = deadline.toISOString().split("T")[0];
    } else {
        startDateInput.value = "";
        startDateInput.disabled = true;
    }
});

document.getElementById("startDate").addEventListener("change", function () {
    const startDateInput = document.getElementById("startDate");
    const endDateInput = document.getElementById("endDate");

    if (startDateInput.value) {
        endDateInput.value = "";
        endDateInput.disabled = false;
        const startDate = new Date(startDateInput.value);
        endDateInput.min = startDate.toISOString().split("T")[0];
    } else {
        endDateInput.value = "";
        endDateInput.disabled = true;
    }
    calculateDuration();
});

document.getElementById("endDate").addEventListener("change", function () {
    const startDateInput = document.getElementById("startDate");
    const endDateInput = document.getElementById("endDate");

    const startDate = new Date(startDateInput.value);
    const endDate = new Date(endDateInput.value);

    if (endDate < startDate) {
        alert("마감일은 시작일 이후로 설정해야 합니다.");
        endDateInput.value = "";
    } else {
        calculateDuration();
    }
});

// --------------------- 예상 기간 계산 함수 ---------------------
function calculateDuration() {
    const startDateInput = document.getElementById("startDate");
    const endDateInput = document.getElementById("endDate");
    const durationInput = document.getElementById("duration");

    if (startDateInput.value && endDateInput.value) {
        const start = new Date(startDateInput.value);
        const end = new Date(endDateInput.value);
        const diffTime = end - start;
        const diffDays = diffTime / (1000 * 60 * 60 * 24);
        const diffMonths = Math.ceil(diffDays / 30);
        durationInput.value = diffMonths;
    } else {
        durationInput.value = "";
    }
}

// --------------------- DOMContentLoaded 스크립트 ---------------------
document.addEventListener("DOMContentLoaded", () => {
    const deadlineInput = document.getElementById("deadline");
    const startDateInput = document.getElementById("startDate");
    const endDateInput = document.getElementById("endDate");

    const districtSelect = document.getElementById("districtSelect");
    const regionSelect = document.getElementById("regionSelect");
    const fieldSelect = document.querySelector("select[aria-label='small select example']");
    const jobSelect = document.querySelector("select[name='freeJobSelect']");
    const skillSelect = document.getElementById("skillSelect");

    const regionConditionsContainer = document.getElementById("regionConditionsContainer");
    const jobConditionsContainer = document.getElementById("jobConditionsContainer");
    const skillConditionsContainer = document.getElementById("skillConditionsContainer");

    const projectApplyEdt = document.getElementById("deadline");
    const codeMap = codeList.reduce((acc, cur) => {
        acc[cur.codeCd] = cur.codeNm;
        return acc;
    }, {});

    const checkedRadio = document.querySelector('input[name="projectWfh"]:checked');
    const workLocationGroup = document.getElementById("workLocationGroup");

    // 라디오 버튼 초기화
    if (checkedRadio) {
        if (checkedRadio.value === "FS01" || checkedRadio.value === "FS03") {
            workLocationGroup.style.display = "block";
            regionSelect.setAttribute("required", "required");
            districtSelect.setAttribute("required", "required");
        } else {
            workLocationGroup.style.display = "none";
            regionSelect.removeAttribute("required");
            districtSelect.removeAttribute("required");
        }
    }

    // 초기 날짜 입력 값 설정
    if (deadlineInput.value) {
        startDateInput.disabled = false;
        startDateInput.min = deadlineInput.value;
    }
    if (startDateInput.value) {
        endDateInput.disabled = false;
        endDateInput.min = startDateInput.value;
    }
    if (endDateInput.value) {
        calculateDuration();
    }

    // 마감일 설정
    if (projectApplyEdt) {
        const today = new Date();
        const yyyy = today.getFullYear();
        const mm = String(today.getMonth() + 1).padStart(2, "0");
        const dd = String(today.getDate()).padStart(2, "0");
        const todayString = `${yyyy}-${mm}-${dd}`;
        projectApplyEdt.min = todayString;
    }

    // 지역 값 초기화
    const regionVal = document.getElementById("regionHidden").value;

    if (regionVal) {
        const regionPairs = regionVal.split(",").reduce((acc, cur, index, array) => {
            if (index % 2 === 0) {
                acc.push([cur, array[index + 1]]);
            }
            return acc;
        }, []);

        regionPairs.forEach(([parentCode, childCode]) => {
            const parentName = codeMap[parentCode] || parentCode;
            const childName = codeMap[childCode] || childCode;

            const combinedName = `${parentName} ${childName}`;
            const combinedValue = `${parentCode},${childCode}`;

            addCondition(
                regionConditionsContainer,
                combinedValue,
                `지역: ${combinedName}`,
                "regionHidden",
                true
            );
        });

        regionSelect.disabled = true;
        districtSelect.disabled = true;
    }

    // 직무 값 초기화
    const jobVal = document.getElementById("jobHidden").value;

    if (jobVal) {
        const jobCodes = jobVal.split(",");
        jobCodes.forEach(code => {
            const codeNm = codeMap[code] || code;
            addCondition(jobConditionsContainer, code, `직무: ${codeNm}`, "jobHidden");
        });
    }

    // 스킬 값 초기화
    const skillVal = document.getElementById("skilleHidden").value;

    if (skillVal) {
        const skillCodes = skillVal.split(",");
        skillCodes.forEach(code => {
            const codeNm = codeMap[code] || code;
            addCondition(
                skillConditionsContainer,
                code,
                `보유스킬: ${codeNm}`,
                "skilleHidden"
            );
        });
    }

    // 지역 선택 이벤트
    if (districtSelect && regionSelect) {
        regionSelect.addEventListener("change", () => {
            const selectedRegion = regionSelect.value;
            if (selectedRegion) {
                const districts = codeList.filter(cd => cd.codeParent === selectedRegion);
                loadOptions(districtSelect, districts, "군, 구를 선택해 주세요.");
            }
        });

        districtSelect.addEventListener("change", () => {
            const selectedDistrict = districtSelect.value;
            const selectedDistrictText = districtSelect.options[districtSelect.selectedIndex]?.text;
            const selectedRegion = regionSelect.value;
            const selectedRegionText = regionSelect.options[regionSelect.selectedIndex]?.text;

            if (selectedDistrict && selectedRegion) {
                const combinedValue = `${selectedRegion},${selectedDistrict}`;
                const combinedName = `${selectedRegionText} ${selectedDistrictText}`;

                addCondition(
                    regionConditionsContainer,
                    combinedValue,
                    `지역: ${combinedName}`,
                    "regionHidden",
                    true
                );

                regionSelect.disabled = true;
                districtSelect.disabled = true;
            }
        });
    }

    // 라디오 버튼 이벤트
    document.querySelectorAll('input[name="projectWfh"]').forEach(radio => {
        radio.addEventListener("change", () => {
            const regionConditionsContainer = document.getElementById("regionConditionsContainer");
            const regionHiddenInput = document.getElementById("regionHidden");

            if (radio.value === "FS01" || radio.value === "FS03") {
                workLocationGroup.style.display = "block";
                regionSelect.setAttribute("required", "required");
                districtSelect.setAttribute("required", "required");
            } else {
                workLocationGroup.style.display = "none";
                regionSelect.removeAttribute("required");
                districtSelect.removeAttribute("required");
                regionSelect.value = "";
                districtSelect.value = "";

                regionConditionsContainer.innerHTML = "";
                regionHiddenInput.value = "";

                regionSelect.disabled = false;
                districtSelect.disabled = false;
            }
        });
    });

    // 직무 선택 이벤트
    if (fieldSelect && jobSelect) {
        fieldSelect.addEventListener("change", () => {
            const selectedField = fieldSelect.value;
            if (selectedField) {
                const filteredJobs = codeList.filter(cd => cd.codeParent === selectedField);
                loadOptions(jobSelect, filteredJobs, "직무를 선택해 주세요.");
            }
        });

        jobSelect.addEventListener("change", () => {
            const selectedJob = jobSelect.value;
            const selectedJobText = jobSelect.options[jobSelect.selectedIndex]?.text;

            if (selectedJob) {
                addCondition(jobConditionsContainer, selectedJob, `직무: ${selectedJobText}`, "jobHidden");
            }
        });
    }

    // 스킬 선택 이벤트
    if (skillSelect) {
        skillSelect.addEventListener("change", () => {
            const selectedSkill = skillSelect.value;
            const selectedSkillText = skillSelect.options[skillSelect.selectedIndex]?.text;

            if (selectedSkill) {
                const existingBadges = Array.from(skillConditionsContainer.children).map(
                    badge => badge.getAttribute("data-value")
                );

                if (!existingBadges.includes(selectedSkill)) {
                    addCondition(
                        skillConditionsContainer,
                        selectedSkill,
                        `보유스킬: ${selectedSkillText}`,
                        "skilleHidden"
                    );
                } else {
                    alert("이미 선택된 스킬입니다.");
                }
            }
        });
    }

    // 공용 함수
    function addCondition(container, value, label, inputId, isRegion = false) {
        if (isRegion && container.children.length > 0) {
            alert("지역은 한 번에 하나만 선택할 수 있습니다.");
            return;
        }

        const conditionItem = document.createElement("div");
        conditionItem.className = "badge bg-secondary text-white p-1 d-flex align-items-center small";
        conditionItem.setAttribute("data-value", value);

        const conditionText = document.createElement("span");
        conditionText.textContent = label;
        conditionItem.appendChild(conditionText);

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "X";
        deleteButton.className = "btn btn-sm btn-danger ms-2";
        deleteButton.style.padding = "0 5px";
        deleteButton.addEventListener("click", () => {
            conditionItem.remove();
            updateHiddenInput(container, inputId);

            if (isRegion) {
                regionSelect.disabled = false;
                districtSelect.disabled = false;
                regionSelect.value = "";
                districtSelect.value = "";
            }
        });

        conditionItem.appendChild(deleteButton);
        container.appendChild(conditionItem);

        updateHiddenInput(container, inputId);

        if (isRegion) {
            regionSelect.disabled = true;
            districtSelect.disabled = true;
        }
    }

    function updateHiddenInput(container, inputId) {
        const conditions = Array.from(container.children);
        const values = conditions.map(condition => condition.getAttribute("data-value"));
        const hiddenInput = document.getElementById(inputId);
        if (hiddenInput) {
            hiddenInput.value = values.join(",");
        }
    }

    function loadOptions(selectElement, options, placeholder) {
        selectElement.innerHTML = `<option value="">${placeholder}</option>`;
        options.forEach(optData => {
            const opt = document.createElement("option");
            opt.value = optData.codeCd;
            opt.textContent = optData.codeNm;
            selectElement.appendChild(opt);
        });
    }
});

// 데이터 입력
document.querySelector("#dataBtn").addEventListener("click",function(){
	document.querySelector("#projectName").value = "대덕 프로젝트";
	document.querySelector("#projectDescription").value = "대덕 교육자 관리 프로그램";
	document.querySelector("#deadline").value = "2025-01-17";
	document.querySelector("#startDate").value = "2025-02-17";
	document.querySelector("#endDate").value = "2025-03-17";
	document.querySelector("#duration").value = 1;
	document.querySelector("#onsite").click();
	document.querySelector("#regionSelect").click();
	document.querySelector("#regionSelect").value = "city06";
	document.querySelector("#regionSelect").dispatchEvent(new Event("change")); // change 이벤트 트리거
	document.querySelector("#districtSelect").value = "city06-00";
	document.querySelector("#districtSelect").dispatchEvent(new Event("change")); // change 이벤트 트리거
	document.querySelector("#teamSize").value = 1;
	document.querySelector("#monthlyRate").value = 300;
	document.querySelector("#jobGun").click();
	document.querySelector("#jobGun").value = "jo02";
	document.querySelector("#jobGun").dispatchEvent(new Event("change")); // change 이벤트 트리거
	document.querySelector("#jobMu").value = "jo02183";
	document.querySelector("#jobMu").dispatchEvent(new Event("change")); // change 이벤트 트리거
	document.querySelector("#skillSelect").click();
	document.querySelector("#skillSelect").value = "sk0043";
	document.querySelector("#skillSelect").dispatchEvent(new Event("change")); // change 이벤트 트리거
})
