function paging(page) {
	console.log(page);
	searchListForm.page.value = page;
	searchListForm.requestSubmit();
}

document.addEventListener("DOMContentLoaded", () => {
	const districtSelect = document.getElementById("districtSelect");
	const regionSelect = document.getElementById("regionSelect");
	const conditionsContainer = document.getElementById("conditionsContainer");
	const fieldSelect = document.querySelector("select[aria-label='small select example']");
	const jobSelect = document.querySelector("select[name='freeJobSelect']");
	const skillSelect = document.getElementById("skillSelect");
	const langSelect = document.getElementById("langSelect");
	const cerSelect = document.getElementById("cerSelect");
	const cerSelect02 = document.getElementById("cerSelect02");
	const cerSelect03 = document.getElementById("cerSelect03");
	const univSelect = document.getElementById("univSelect");
	const memCards = document.querySelectorAll(".card-body.p-4");
	const path = document.body.dataset.url; // base URL

	// 클릭 이벤트 
	memCards.forEach(card => {
		card.addEventListener("click", (event) => {
			const memId = card.getAttribute("data-mem-id"); // data-mem-id 값 가져오기
			window.location.href = `${path}/talent/${memId}/detail`;
		});
	});



	document.getElementById("resetConditions").addEventListener("click", () => {
		conditionsContainer.innerHTML = ""; // 조건 초기화
		document.getElementById("searchForm").reset(); // 폼 리셋
	});



	document.getElementById("searchbtnForm").addEventListener("submit", (event) => {
		// 기존 hidden input 초기화
		const existingInputs = document.querySelectorAll("#searchbtnForm input[type='hidden']");
		existingInputs.forEach(input => input.remove());

		// 조건 컨테이너에서 조건 읽기
		const conditions = document.querySelectorAll("#conditionsContainer > div");
		conditions.forEach(condition => {
			const category = condition.getAttribute("data-category"); // 카테고리 추출
			const value = condition.getAttribute("data-value"); // 값 추출

			if (category && value) {
				const hiddenInput = document.createElement("input");
				hiddenInput.type = "hidden";
				hiddenInput.name = category; // 카테고리를 name으로 설정
				hiddenInput.value = value; // 값을 value로 설정
				document.getElementById("searchbtnForm").appendChild(hiddenInput); // form에 추가
			}
		});
	});



	// 조건 추가 함수
	function addCondition(category, value, label) {
		const existingCondition = Array.from(conditionsContainer.children).find(
			child => child.getAttribute("data-value") === value
		);
		if (existingCondition) return;

		const conditionItem = document.createElement("div");
		conditionItem.className = "badge bg-secondary text-white p-1 d-flex align-items-center small"; // p-1과 small 추가
		conditionItem.setAttribute("data-category", category);
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
		});


		conditionItem.appendChild(deleteButton);
		conditionsContainer.appendChild(conditionItem);
	}

	// 셀렉트 박스 데이터 로드
	function loadOptions(selectElement, options, placeholder) {
		selectElement.innerHTML = `<option value="">${placeholder}</option>`;
		options.forEach(option => {
			const opt = document.createElement("option");
			opt.value = option.codeCd;
			opt.textContent = option.codeNm;
			selectElement.appendChild(opt);
		});
	}

	// 지역 선택 이벤트
	districtSelect.addEventListener("change", () => {
		const selectedDistrict = districtSelect.value;
		const selectedDistrictText = districtSelect.options[districtSelect.selectedIndex]?.text;
		const selectedRegionText = regionSelect.options[regionSelect.selectedIndex]?.text;

		if (selectedDistrict) {
			const combinedName = `${selectedRegionText} ${selectedDistrictText}`;
			addCondition("region", selectedDistrict, `지역: ${combinedName}`);
		}
	});

	regionSelect.addEventListener("change", () => {
		const selectedRegion = regionSelect.value;
		if (selectedRegion) {
			const districts = codeList.filter(code => code.codeParent === selectedRegion);
			loadOptions(districtSelect, districts, "군, 구를 선택해 주세요.");
		}
	});

	// 직무 선택 이벤트
	fieldSelect?.addEventListener("change", () => {
		const selectedField = fieldSelect.value;
		if (selectedField) {
			const filteredJobs = codeList.filter(code => code.codeParent === selectedField);
			loadOptions(jobSelect, filteredJobs, "직무를 선택해 주세요.");
		}
	});

	jobSelect.addEventListener("change", () => {
		const selectedJob = jobSelect.value;
		const selectedJobText = jobSelect.options[jobSelect.selectedIndex]?.text;
		if (selectedJob) {
			addCondition("job", selectedJob, `직무: ${selectedJobText}`);
		}
	});

	// 보유 스킬 선택 이벤트
	skillSelect.addEventListener("change", () => {
		const selectedSkill = skillSelect.value;
		const selectedSkillText = skillSelect.options[skillSelect.selectedIndex]?.text;
		if (selectedSkill) {
			addCondition("skill", selectedSkill, `보유스킬: ${selectedSkillText}`);
		}
	});

	// 언어 능력 선택 이벤트
	langSelect.addEventListener("change", () => {
		const selectedLang = langSelect.value;
		const selectedLangText = langSelect.options[langSelect.selectedIndex]?.text;
		if (selectedLang) {
			addCondition("language", selectedLang, `언어능력: ${selectedLangText}`);
		}
	});

	// 자격증 선택 이벤트
	cerSelect.addEventListener("change", () => {
		const selectedCer = cerSelect.value;
		if (selectedCer) {
			const filteredGrades = codeList.filter(code => code.codeParent === selectedCer);
			loadOptions(cerSelect02, filteredGrades, "자격 등급을 선택해 주세요.");
		}
	});

	cerSelect02.addEventListener("change", () => {
		const selectedGrade = cerSelect02.value;
		if (selectedGrade) {
			const filteredCategories = codeList.filter(code => code.codeParent === selectedGrade);
			loadOptions(cerSelect03, filteredCategories, "종목을 선택해 주세요.");
		}
	});

	cerSelect03.addEventListener("change", () => {
		const selectedCategory = cerSelect03.value;
		const selectedCategoryText = cerSelect03.options[cerSelect03.selectedIndex]?.text;
		if (selectedCategory) {
			addCondition("certificate", selectedCategory, `자격증: ${selectedCategoryText}`);
		}
	});

	// 학력 선택 이벤트
	univSelect.addEventListener("change", () => {
		const selectedUniv = univSelect.value;
		const selectedUnivText = univSelect.options[univSelect.selectedIndex]?.text;
		if (selectedUniv) {
			addCondition("univ", selectedUniv, `학력: ${selectedUnivText}`);
		}
	});
});
