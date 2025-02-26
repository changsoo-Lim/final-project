$(function() {

	$(document).on('click', '.input-btn', function() {
		// 모집내용 ===================================================
		// 공고 제목 설정
		$('#employTitle').val('프론트엔드 개발자 모집');
		// 경력 체크박스 설정
		$('#employExperience input[type="checkbox"]').each(function() {
			if ($(this).val() === 'all' || $(this).val() === 'mt01') {
				$(this).prop('checked', true);
			}
		});

		// 고용형태
		const valuesToCheck = ["work-type-1", "work-type-2"];
		// 모든 체크박스에 대해 설정
		$("input[type='checkbox'][name='employType']").each(function() {
			if (valuesToCheck.includes(this.value)) {
				// 체크박스를 체크 상태로 설정
				this.checked = true;

				// .checkbox-item에 selected 클래스 추가
				$(this).closest(".checkbox-item").addClass("selected");
			}
		});

		// 학력 설정
		$('select[name="employEducation"]').val('EDC01');

		// 모집분야 ===================================================
		// 모집명/인원 설정
		$('#filedNm').val('백엔드 개발자');
		$('#filedPersonnel').val(3);
		// 근무지역
		$("#filedRegion").click();
		$("#filedRegion").val("city01").trigger("change");
		$("#filedRegionGungu").val('city01-00');
		// 담당업무 설정
		$('#filedJobs').val('- 웹 애플리케이션 개발 및 유지보수');
		// 우대 조건 설정
		$('#filedPreference').val('- 관련 경력 2년 이상\n- React 및 Vue.js 경험자');
		// 필수조건
		$('#filterTitleSelect').val('FILTER01').trigger("change");
		let targetItem = $('#filterCont').find(".filterCont-item[data-value='cer01004086']");
		if (targetItem.length) {
			targetItem.trigger("click"); // 클릭 이벤트 트리거
		}

		// 근무조건 ===================================================
		// 근무 요일 및 시간 설정
		$('#employWorkday').val('WD05'); // 월~금 근무
		$('#employSwh').val('WH19'); // 시작 시간
		$('#employEwh').val('WH37'); // 종료 시간
		// 급여 조건 설정
		$('#employSalary').val('salary-3500-4000'); // 예: 'SAL03'는 3000만 원 이상
		$('#employSalaryYn').prop('checked', false); // 회사 내규 또는 협의 여부

		// 접수기간 및 방법 ===================================================
		// 모집 마감일 설정
		$('#employEd').val('2025-01-31');
		// 접수 방법 설정
		$('#employApplicationArea input[type="radio"]').each(function() {
			if ($(this).val() === 'APLC01') { // 이메일 접수 예
				$(this).prop('checked', true);
			}
		});
	});

	const contextPath = document.body.dataset.url || "";

	// 고용형태(employType) Multi-Checkbox 초기화
	let employTypeStr = $("#employTypeArea").data("employtype") || ""; // 수정 모드 데이터
	if (employTypeStr) {
		let employTypeArr = employTypeStr.split(",");
		employTypeArr.forEach(function(cd) {
			$(`#employTypeArea input[name='employType'][value='${cd.trim()}']`)
				.prop("checked", true);
		});
	} else {
		// 등록 모드: 모든 체크박스를 초기화
		$(`#employTypeArea input[name='employType']`).prop("checked", false);
	}

	$(document).on("click", ".checkbox-item", function() {
		const $checkbox = $(this).find("input[type='checkbox']");
		const isChecked = $checkbox.prop("checked");

		// 상태 토글
		$checkbox.prop("checked", !isChecked);
		$(this).toggleClass("selected", !isChecked);
	});

	// 경력(employExperience) Multi-Checkbox 초기화
	let experienceStr = $("#employExperienceArea").data("experience") || ""; // 수정 모드 데이터
	if (experienceStr) {
		let experienceArr = experienceStr.split(",");
		experienceArr.forEach(function(cd) {
			$(`#employExperienceArea input[name='employExperience'][value='${cd.trim()}']`)
				.prop("checked", true);
		});
	} else {
		// 등록 모드: 모든 체크박스를 초기화
		$(`#employExperienceArea input[name='employExperience']`).prop("checked", false);
	}

	/* ==================== 1) 필수조건 (filterTitleSelect + filterCont + selectedFilters) ==================== */
	const codeParentMapping = {
		FILTER01: "cer",    // ex) 자격증
		FILTER02: "univ-type",
		FILTER03: "univ-major",
		FILTER04: "lang",
		FILTER05: "country"
	};
	let dropdownOpen = false;

	// (1) 조건 선택 (select)
	$("#filterTitleSelect").on("change", function() {
		let selCd = $(this).val();
		if (!selCd) {
			$("#filterCont").hide(); dropdownOpen = false;
			return;
		}
		// 필터링
		let parentKey = codeParentMapping[selCd] || "";
		$("#filterCont .filterCont-item").each(function() {
			let p = $(this).data("parent") || "";
			if (parentKey && p.startsWith(parentKey)) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
		$("#filterCont").hide();
		$("#filterContSearchInput").val("");
		dropdownOpen = false;
	});

	// (2) 항목 리스트 열기/닫기
	$("#filterContSearchInput").on("click", function(e) {
		e.stopPropagation();
		let selCd = $("#filterTitleSelect").val();
		if (!selCd) return;

		if (!dropdownOpen) {
			$("#filterCont").show();
			dropdownOpen = true;
		} else {
			$("#filterCont").hide();
			dropdownOpen = false;
		}
	});

	// (3) 항목 검색
	$("#filterContSearchInput").on("input", function() {
		let q = $(this).val().toLowerCase();
		let selCd = $("#filterTitleSelect").val();
		let parentKey = codeParentMapping[selCd] || "";
		$("#filterCont .filterCont-item").each(function() {
			let p = $(this).data("parent") || "";
			let t = ($(this).data("text") || "").toLowerCase();
			if (p.startsWith(parentKey) && t.includes(q)) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
	});

	// (4) 외부 클릭 -> 닫기
	$(document).on("click", function(e) {
		if (!$(e.target).closest("#filterCont, #filterContSearchInput").length) {
			$("#filterCont").hide();
			dropdownOpen = false;
		}
	});

	// (5) 항목 클릭 -> 선택
	$(document).on("click", function(e) {
		// 클릭된 대상이 `.filterCont-item`인지 확인
		if ($(e.target).closest(".filterCont-item").length) {
			const $item = $(e.target).closest(".filterCont-item");

			let cd = $item.data("value");
			let nm = $item.data("text");
			let selCd = $("#filterTitleSelect").val();
			let selTitleNm = $("#filterTitleSelect option:selected").text();

			if (!selCd) return;

			// 중복 방지
			if ($("#selectedFilters input[value='" + cd + "']").length > 0) {
				$("#filterCont").hide();
				dropdownOpen = false;
				return;
			}

			// hidden + 표시
			let hiddenInputTitle = `
            <input type="hidden" name="selectedFilterTitle" value="${selCd}"
                data-codetitle="${selCd}" data-codecd="${cd}">
        `;
			let hiddenInputCont = `
            <input type="hidden" name="selectedFilterCont" value="${cd}"
                data-codetitle="${selCd}" data-codecd="${cd}">
        `;
			let selectedItem = `
            <div class="selected-filter-item" data-codetitle="${selCd}" data-codecd="${cd}">
                <div><strong>${selTitleNm} > </strong> ${nm}</div>
                <button type="button" class="remove-filter" data-codecd="${cd}">
                    <img src="${contextPath}/resources/images/trashIcon.svg" alt="삭제">
                </button>
            </div>
        `;
			$("#selectedFilters").append(hiddenInputTitle + hiddenInputCont + selectedItem);

			$("#filterCont").hide();
			dropdownOpen = false;
		}
	});

	// (6) 선택된 항목 제거
	$("#selectedFilters").on("click", ".remove-filter", function() {
		let cd = $(this).data("codecd");
		$(this).closest(".selected-filter-item").remove();
		$("#selectedFilters input[value='" + cd + "']").remove();
	});

	/* ==================== 2) 채용절차 (모달) + 드래그 ==================== */
	// (A) li.proc-checkbox 클릭 -> checkbox 토글
	$(document).on("click", "li.proc-checkbox", function(e) {
		// 클릭한 요소가 체크박스(input[type='checkbox'])라면 별도 처리하지 않음
		if ($(e.target).is("input[type='checkbox']")) {
			return; // 기본 동작 유지
		}
		if ($(e.target).is("label")) {
			e.preventDefault(); // 기본 동작 차단 (체크박스와의 연결 방지)
		}

		// 체크박스 상태 토글
		const $cb = $(this).closest("li.proc-checkbox").find("input[type='checkbox']");
		const newState = !$cb.prop("checked");
		$cb.prop("checked", newState).trigger("change");
	});

	// (B) 모달 열기
	$("#openProcessModalBtn").on("click", function() {
		// 1) 체크 해제
		$("#processOptions .procCheck").prop("checked", false);
		// 2) 기존 중간절차 => 체크
		$(".proc-container .draggable").each(function() {
			let cd = $(this).data("proccd");
			$("#processOptions .procCheck[value='" + cd + "']").prop("checked", true);
		});
		let modalEl = document.getElementById("processModal");
		let modalObj = bootstrap.Modal.getOrCreateInstance(modalEl);
		modalObj.show();
	});

	// (C) 모달 "확인"
	$("#confirmProcessBtn").on("click", function() {
		let arr = [];
		$("#processOptions .procCheck:checked").each(function() {
			arr.push($(this).val());
		});
		let $procInner = $(".proc-container");
		// 기존 중간절차 제거
		$procInner.find(".draggable").each(function() {
			let cd = $(this).data("proccd");
			if (!arr.includes(cd)) {
				$(this).remove();
			}
		});
		// 새 체크 항목 추가
		$.each(arr, function(i, cd) {
			if ($procInner.find(`.draggable[data-proccd='${cd}']`).length === 0) {
				let lbl = $(`#processOptions .procCheck[value='${cd}']`).next("label").text() || cd;
				let $div = $(`
          <div class="draggable row mb-4 align-items-center"
               data-proccd="${cd}" draggable="true">
            <div class="el">${lbl}</div>
            <button type="button" class="delete-proc-btn">
              <img src="${contextPath}/resources/images/trashIcon.svg" alt="삭제">
            </button>
          </div>
        `);
				$procInner.append($div);
			}
		});
		let modalEl = document.getElementById("processModal");
		let modalObj = bootstrap.Modal.getOrCreateInstance(modalEl);
		modalObj.hide();
	});

	// (D) 드래그 + 삭제버튼
	enableDragAndDrop($(".proc-container"));
	$(".proc-container").on("click", ".delete-proc-btn", function(e) {
		e.stopPropagation();
		$(this).closest(".draggable").remove();
	});

	/* ==================== 3) 모집분야 "추가/수정" ==================== */
	let currentIndex = null;
	let $hiddenContainer = $("#hiddenInputsContainer");

	$("#addFieldBtn").on("click", async function() {
		// 필수 항목 확인
		if (!checkRequired()) {
			await validationAlert("필수항목을 입력해주세요.", "warning");
			focusAndScroll($("#recruitFieldSection")); // 스크롤 및 포커스 적용
			return;
		}

		// 폼 데이터 처리
		let d = collectFormData();
		if (currentIndex === null) {
			createNewItem(d);
		} else {
			updateItem(currentIndex, d);
		}
		resetForm();
	});

	$("#resetFormBtn").on("click", function() {
		resetForm();
	});

	// (A) 하단 목록
	$("#addedFieldList").on("click", ".addedField", function() {
		let idx = $(this).data("index");
		loadItemToForm(idx);
	}).on("click", ".deleteBtn", function(e) {
		e.stopPropagation();
		let idx = $(this).closest("li").data("index");
		deleteItem(idx);
	});

	// (B) 인원 => 음수 방지
	$("#filedPersonnel").on("input", function() {
		let v = $(this).val().replace(/[^\d]/g, '');
		$(this).val(v);
	});
	// (C) 지역->군/구
	$(document).on("change", "#filedRegion", function() {
		let cv = $(this).val();
		let $gungu = $("#filedRegionGungu");
		$gungu.find("option").hide();
		$gungu.find("option[value='']").show();
		if (cv) {
			$gungu.find("." + cv).show();
		} else {
			$gungu.find("option").show();
		}
	});

	// 급여 조건
	// 1. employSalaryYn 체크 시 employSalary 값을 null로 설정
	$("#employSalaryYn").on("change", function() {
		if ($(this).prop("checked")) {
			// employSalaryYn이 체크되면 employSalary를 null로 설정
			$("#employSalary").val("");
		}
	});

	// 2. employSalary 변경 시 employSalaryYn 체크 해제
	$("#employSalary").on("change", function() {
		if ($("#employSalaryYn").prop("checked")) {
			// employSalary 값이 변경되면 employSalaryYn 체크 해제
			$("#employSalaryYn").prop("checked", false);
		}
	});

	// 모집마감일
	var now_utc = Date.now(); // 지금 날짜를 밀리초로
	// getTimezoneOffset()은 현재 시간과의 차이를 분 단위로 반환
	var timeOff = new Date().getTimezoneOffset() * 60000; // 분단위를 밀리초로 변환
	// new Date(now_utc-timeOff).toISOString()은 '2022-05-11T18:09:38.134Z'를 반환
	var today = new Date(now_utc - timeOff).toISOString().split("T")[0];

	// 오늘 날짜 이전으로 선택을 막으려면 min 속성에 오늘 날짜를 설정
	document.getElementById("employEd").setAttribute("min", today);

	// 접수 방법
	const selectedValue = $("#employApplicationArea").data("selected"); // data-selected 속성 값 가져오기
	if (selectedValue) {
		// 선택된 값이 있다면 해당 라디오 버튼을 체크
		const $selectedRadio = $(`#employApplicationArea input[value='${selectedValue}']`);
		if ($selectedRadio.length) {
			// 라디오 버튼이 존재할 경우에만 체크
			$selectedRadio.prop("checked", true);
		} else {
			// 값이 유효하지 않으면 첫 번째 라디오 버튼을 체크
			$("#employApplicationArea input:first").prop("checked", true);
		}
	} else {
		// 등록 시 첫 번째 항목 체크
		$("#employApplicationArea input:first").prop("checked", true);
	}

	// 초기 상태에서 employUrl input은 숨기기
	$("#employUrlContainer").hide();

	// 접수 방법 라디오 버튼 클릭 이벤트 처리
	$("#employApplicationArea input[type='radio']").on("change", function() {
		const selectedValue = $("input[name='employApplication']:checked").val();

		if (selectedValue === "APLC02") {
			// APLC02가 선택되었을 경우 employUrl 필수로 만들고 보여주기
			$("#employUrlContainer").show();
		} else {
			$("#employUrlContainer").hide();
			$("#employUrl").val('');
		}
	});

	// 페이지 로드 시 초기 상태 체크
	const initialValue = $("#employApplicationArea").data("selected");
	if (initialValue === "APLC02") {
		$("#employUrlContainer").show();
	}

	/* ==================== 4) "등록/수정" (비동기) 버튼 ==================== */
	// 유효성 검증 함수
	async function validateForm() {
		let isValid = true;

		// 공고 제목 검사
		const employTitle = $("#employTitle");
		if (employTitle.length > 0 && !employTitle.val().trim()) {
			await validationAlert("공고 제목을 입력해주세요.", "warning");
			focusAndScroll(employTitle);
			isValid = false;
			return false;
		}

		// 경력 검사
		if (!validateCheckboxGroup("employExperience")) {
			await validationAlert("경력을 하나 이상 선택해주세요.", "warning");
			focusAndScroll($("#employExperience"));
			isValid = false;
			return false;
		}

		// 학력 검사
		const employEducation = $("#employEducation");
		if (employEducation.length > 0 && !employEducation.val()) {
			await validationAlert("학력을 선택해주세요.", "warning");
			focusAndScroll(employEducation);
			isValid = false;
			return false;
		}

		// 고용 형태 검사
		if (!validateCheckboxGroup("employType")) {
			await validationAlert("고용형태를 하나 이상 선택해주세요.", "warning");
			focusAndScroll($("#employTypeArea"));
			isValid = false;
			return false;
		}

		// 모집 분야 검사
		if ($("#hiddenInputsContainer").children().length === 0) {
			await validationAlert("모집 분야를 최소 1개 이상 등록해주세요.", "warning");
			focusAndScroll($("#recruitFieldSection"));
			isValid = false;
			return false;
		}

		// 근무요일 검사
		const employWorkday = $("#employWorkday");
		if (employWorkday.length > 0 && !employWorkday.val()) {
			await validationAlert("근무요일을 선택해주세요.", "warning");
			focusAndScroll(employWorkday);
			isValid = false;
			return false;
		}

		// 근무 시작 시간 검사
		const employSwh = $("#employSwh");
		if (employSwh.length > 0 && !employSwh.val()) {
			await validationAlert("근무 시작 시간을 선택해주세요.", "warning");
			focusAndScroll(employSwh);
			isValid = false;
			return false;
		}

		// 근무 종료 시간 검사
		const employEwh = $("#employEwh");
		if (employEwh.length > 0 && !employEwh.val()) {
			await validationAlert("근무 종료 시간을 선택해주세요.", "warning");
			focusAndScroll(employEwh);
			isValid = false;
			return false;
		}

		// 급여 조건 검사
		const employSalary = $("#employSalary");
		const employSalaryYn = $("#employSalaryYn");
		if (employSalary.length > 0 && !employSalary.val() && !employSalaryYn.is(":checked")) {
			await validationAlert("급여 조건을 선택해주세요.", "warning");
			focusAndScroll(employSalary);
			isValid = false;
			return false;
		}

		// 모집마감일 검사
		const employEd = $("input[type='date']");
		if (employEd.length > 0 && !employEd.val()) {
			await validationAlert("모집마감일을 입력해주세요.", "warning");
			focusAndScroll(employEd);
			isValid = false;
			return false;
		}

		// 접수방법 검사
		const employApplication = $("input[name='employApplication']");
		if (employApplication.length > 0 && employApplication.filter(":checked").length === 0) {
			await validationAlert("접수방법을 선택해주세요.", "warning");
			focusAndScroll($("#employApplicationArea"));
			isValid = false;
			return false;
		}

		// APLC02가 선택되었을 때 employUrl 필수 검사
		const selectedApplication = $("input[name='employApplication']:checked").val();
		if (selectedApplication === "APLC02") {
			const employUrl = $("#employUrl");
			if (!employUrl.val().trim()) {
				await validationAlert("URL을 입력해주세요.", "warning");
				focusAndScroll(employUrl);
				isValid = false;
				return false;
			}
		}

		return isValid;
	}

	$("#saveBtn").on("click", async function(e) {
		e.preventDefault();
		const isValid = await validateForm();
		if (isValid) {
			let finalData = gatherFinalJson();
			console.log("최종데이터:", finalData);

			$.ajax({
				url: contextPath + "/employ",
				method: "POST",
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify(finalData),
				dataType: "json",
				success: function(response) {
					if (response.status === "success") {
						alertMessage(response.message, 'success');
						location.href = `${contextPath}/employ/list/comp`
					} else if (response.status === "fail") {
						alertMessage(response.message, 'error');
					} else {
						alertMessage(response.message, 'error');
						console.log(response.errAttrName);
					}
				},
				error: function() {
					alertMessage("서버에서 문제가 발생했습니다. 잠시 후 다시 시도해주세요.", "error");
				}
			});
		}
	});

	/* ==================== 함수들 ==================== */
	// 줄바꿈 처리 로직
	function formatTextareaValue(value) {
		return value ? value.replace(/\n/g, "<br>") : "";
	}

	// (I) 드래그앤드롭
	function enableDragAndDrop($container) {
		let $dragItem = null;
		$container.on("dragstart", ".draggable", function(e) {
			if ($(this).hasClass("fixed")) {
				e.preventDefault(); return;
			}
			e.originalEvent.dataTransfer.setData("text/plain", "");
			e.originalEvent.dataTransfer.effectAllowed = "move";
			$(this).addClass("dragging");
			$dragItem = $(this);
		});
		$container.on("dragend", ".draggable", function(e) {
			e.preventDefault();
			if ($dragItem) {
				$dragItem.removeClass("dragging");
				$dragItem = null;
			}
		});
		$container.on("dragover", function(e) {
			e.preventDefault();
			if (!$dragItem) return;
			let afterElem = getDragAfterElement($container[0], e.originalEvent.clientY);
			if (!afterElem) {
				$container[0].appendChild($dragItem[0]);
			} else {
				$container[0].insertBefore($dragItem[0], afterElem);
			}
		});
		$container.on("drop", function(e) {
			e.preventDefault();
			if ($dragItem) {
				let afterElem = getDragAfterElement($container[0], e.originalEvent.clientY);
				if (!afterElem) {
					$container[0].appendChild($dragItem[0]);
				} else {
					$container[0].insertBefore($dragItem[0], afterElem);
				}
				$dragItem.removeClass("dragging");
				$dragItem = null;
			}
		});
		function getDragAfterElement(container, mouseY) {
			let draggables = [...container.querySelectorAll(".draggable:not(.dragging)")];
			let closest = null;
			let closestOffset = Number.NEGATIVE_INFINITY;
			draggables.forEach(child => {
				let box = child.getBoundingClientRect();
				let offset = mouseY - (box.top + box.height / 2);
				if (offset < 0 && offset > closestOffset) {
					closestOffset = offset; closest = child;
				}
			});
			return closest;
		}
	}

	// (II) 필수항목 체크
	function checkRequired() {
		let ok = true;
		$(".field-required").each(function() {
			if (!$(this).val().trim()) { ok = false; return false; }
		});
		return ok;
	}

	// (III) "추가/수정" 클릭 -> 하나의 모집분야 데이터 구성
	function collectFormData() {
		// (a) 절차
		let procArr = [];
		$(".proc-container .draggable").each(function() {
			let cd = $(this).data("proccd");
			if (cd) procArr.push(cd);
		});
		let procedureCodes = ["AP01", "AP02"].concat(procArr, ["AP08"]);

		// (b) 필수조건
		let filterPairs = []; // {filterTitleCd, filterContCd}
		let $items = $("#selectedFilters .selected-filter-item");
		$items.each(function(i) {
			let tCd = $(this).data("codetitle");
			let cCd = $(this).data("codecd");
			filterPairs.push({
				filterTitleCd: tCd,
				filterContCd: cCd
			});
		});

		return {
			filedNm: $("#filedNm").val().trim(),
			filedPersonnel: $("#filedPersonnel").val().trim(),
			filedRegion: $("#filedRegion").val(),
			filedRegionGungu: $("#filedRegionGungu").val(),
			filedJobs: $("#filedJobs").val(),
			filedPreference: $("#filedPreference").val(),
			filterPairs: filterPairs,
			procedureCodes: procedureCodes
		};
	}

	// (IV) resetForm -> 필수조건 div도 비우기
	function resetForm() {
		$("#filedNm,#filedJobs,#filedPreference").val("");
		$("#filedPersonnel").val("0");
		$("#filedRegion").val("");
		$("#filedRegionGungu").val("");
		$(".proc-container .draggable").remove();
		// 필수조건 초기화
		$("#selectedFilters").empty();

		currentIndex = null;
		$("#currentIndex").val("");
	}

	// (V) create / update (하단 목록 + hidden inputs)
	function findNextIndex() {
		let max = -1;
		$("#addedFieldList li").each(function() {
			let i = parseInt($(this).data("index"), 10);
			if (i > max) max = i;
		});
		return max + 1;
	}
	function createNewItem(d) {
		let newIdx = findNextIndex();
		let $li = $(`
      <li data-index="${newIdx}" class="addedField">
        <span class="addedFieldTitle">
          ${d.filedNm} (모집 ${d.filedPersonnel}명)
        </span>
        <div class="addedFieldBtn">
          <button type="button" class="deleteBtn btn btn-sm btn-outline-primary">삭제</button>
        </div>
      </li>
    `);
		$("#addedFieldList").append($li);
		appendHiddenInputs(newIdx, d);
		reIndex();
	}
	function updateItem(idx, d) {
		let $li = $(`#addedFieldList li[data-index='${idx}']`);
		$li.find(".addedFieldTitle")
			.text(`${d.filedNm} (모집 ${d.filedPersonnel}명)`);

		// hidden 갱신
		let $c = $("#hiddenInputsContainer");
		$c.find(`[name='fieldList[${idx}].filedNm']`).val(d.filedNm);
		$c.find(`[name='fieldList[${idx}].filedPersonnel']`).val(d.filedPersonnel);
		$c.find(`[name='fieldList[${idx}].filedRegion']`).val(d.filedRegion);
		$c.find(`[name='fieldList[${idx}].filedRegionGungu']`).val(d.filedRegionGungu);
		$c.find(`[name='fieldList[${idx}].filedJobs']`).val(d.filedJobs);
		$c.find(`[name='fieldList[${idx}].filedPreference']`).val(d.filedPreference);

		// 기존 필수조건 제거
		$c.find(`[data-filter-parent='${idx}']`).remove();
		addFilterPairsToHidden(idx, d.filterPairs, $c.find(`[data-field-idx='${idx}']`));

		// 절차 제거후 재삽입
		let $parentDiv = $c.find(`[data-field-idx='${idx}']`);
		$parentDiv.find(`[data-proc-parent='${idx}']`).remove();
		appendProcedureHidden(idx, d.procedureCodes, $parentDiv);

		reIndex();
	}
	function loadItemToForm(idx) {
		currentIndex = idx;
		$("#currentIndex").val(idx);
		let $c = $("#hiddenInputsContainer");
		let nm = $c.find(`[name='fieldList[${idx}].filedNm']`).val() || "";
		let per = $c.find(`[name='fieldList[${idx}].filedPersonnel']`).val() || "";
		let reg = $c.find(`[name='fieldList[${idx}].filedRegion']`).val() || "";
		let gun = $c.find(`[name='fieldList[${idx}].filedRegionGungu']`).val() || "";
		let job = $c.find(`[name='fieldList[${idx}].filedJobs']`).val() || "";
		let pre = $c.find(`[name='fieldList[${idx}].filedPreference']`).val() || "";

		$("#filedNm").val(nm);
		$("#filedPersonnel").val(per);
		$("#filedRegion").val(reg).trigger("change");
		$("#filedRegionGungu").val(gun);
		$("#filedJobs").val(job);
		$("#filedPreference").val(pre);

		// 필수조건
		$("#selectedFilters").empty();
		let $filters = $c.find(`[data-filter-parent='${idx}']`);
		// data-filter-idx= j
		let flMap = {};
		$filters.each(function() {
			let fName = $(this).data("filterName");   // filterTitleCd or filterContCd
			let fVal = $(this).val();
			let fIdx = $(this).data("filterIdx");
			if (!flMap[fIdx]) flMap[fIdx] = {};
			flMap[fIdx][fName] = fVal;
		});
		// UI 로 복원
		$.each(flMap, function(j, obj) {
			let tCd = obj.filterTitleCd;
			let cCd = obj.filterContCd;

			let codeTitle = $("#filterTitleSelect option[value='" + tCd + "']").text(); // filterTitle에서 codeNm 찾기
			let codeCont = $("#filterCont li[data-value='" + cCd + "']").text(); // filterCont에서 codeNm 찾기
			let hiddenTitle = `
        <input type="hidden" name="selectedFilterTitle" value="${tCd}"
               data-codetitle="${tCd}" data-codecd="${cCd}">
      `;
			let hiddenCont = `
        <input type="hidden" name="selectedFilterCont" value="${cCd}"
               data-codetitle="${tCd}" data-codecd="${cCd}">
      `;
			let selectedItem = `
        <div class="selected-filter-item" data-codetitle="${tCd}" data-codecd="${cCd}">
			<div><strong>${codeTitle} > </strong> ${codeCont}</div>
          <button type="button" class="remove-filter" data-codecd="${cCd}">
            <img src="${contextPath}/resources/images/trashIcon.svg" alt="삭제">
          </button>
        </div>
      `;
			$("#selectedFilters").append(hiddenTitle + hiddenCont + selectedItem);
		});

		// 절차
		$(".proc-container .draggable").remove();
		let $procInputs = $c.find(`[data-proc-parent='${idx}']`);
		$procInputs.each(function() {
			let cd = $(this).val();
			if (cd === "AP01" || cd === "AP02" || cd === "AP08") return;
			let labelText = $(`#processOptions .procCheck[value='${cd}']`)
				.next("label").text() || cd;
			let $div = $(`
        <div class="draggable row mb-4 align-items-center" data-proccd="${cd}" draggable="true">
          <div class="el">${labelText}</div>
          <button type="button" class="delete-proc-btn">
            <img src="${contextPath}/resources/images/trashIcon.svg" alt="삭제">
          </button>
        </div>
      `);
			$(".proc-container").append($div);
		});
	}
	function deleteItem(idx) {
		$(`#addedFieldList li[data-index='${idx}']`).remove();
		$hiddenContainer.find(`[data-field-idx='${idx}']`).remove();
		if (currentIndex == idx) {
			resetForm();
		}
		reIndex();
	}

	function appendHiddenInputs(idx, d) {
		let $div = $(`<div data-field-idx="${idx}"></div>`);
		$div.append(`<input type="hidden" name="fieldList[${idx}].filedNm" value="${d.filedNm}"/>`);
		$div.append(`<input type="hidden" name="fieldList[${idx}].filedPersonnel" value="${d.filedPersonnel}"/>`);
		$div.append(`<input type="hidden" name="fieldList[${idx}].filedRegion" value="${d.filedRegion}"/>`);
		$div.append(`<input type="hidden" name="fieldList[${idx}].filedRegionGungu" value="${d.filedRegionGungu}"/>`);
		$div.append(`<input type="hidden" name="fieldList[${idx}].filedJobs" value="${d.filedJobs}"/>`);
		$div.append(`<input type="hidden" name="fieldList[${idx}].filedPreference" value="${d.filedPreference}"/>`);
		addFilterPairsToHidden(idx, d.filterPairs, $div);
		appendProcedureHidden(idx, d.procedureCodes, $div);
		$hiddenContainer.append($div);
	}

	function addFilterPairsToHidden(idx, filterPairs, $container) {
		$.each(filterPairs, function(j, fp) {
			let $t = $(`
        <input type="hidden" name="fieldList[${idx}].filterList[${j}].filterTitleCd"
               data-filter-parent="${idx}"
               data-filter-name="filterTitleCd"
               data-filter-idx="${j}"
               value="${fp.filterTitleCd}"/>
      `);
			let $c = $(`
        <input type="hidden" name="fieldList[${idx}].filterList[${j}].filterContCd"
               data-filter-parent="${idx}"
               data-filter-name="filterContCd"
               data-filter-idx="${j}"
               value="${fp.filterContCd}"/>
      `);
			$container.append($t).append($c);
		});
	}

	function appendProcedureHidden(idx, codes, $div) {
		$.each(codes, function(j, cd) {
			let $inp = $(`
        <input type="hidden"
               name="fieldList[${idx}].procedure[${j}].procedureCd"
               data-proc-parent="${idx}"
               value="${cd}"/>
      `);
			$div.append($inp);
		});
	}

	function reIndex() {
		let $lis = $("#addedFieldList li");
		$lis.each(function(newIdx) {
			let oldIdx = $(this).data("index");
			$(this).attr("data-index", newIdx);
			let $div = $("#hiddenInputsContainer").find(`[data-field-idx='${oldIdx}']`);
			$div.attr("data-field-idx", newIdx);

			$div.find("input").each(function() {
				let oldName = $(this).attr("name") || "";
				let newName = oldName.replace(/\[\d+\]/, `[${newIdx}]`);
				$(this).attr("name", newName);

				if ($(this).attr("data-proc-parent") == String(oldIdx)) {
					$(this).attr("data-proc-parent", newIdx);
				}
				if ($(this).attr("data-filter-parent") == String(oldIdx)) {
					$(this).attr("data-filter-parent", newIdx);
				}
			});
		});
	}

	// (V) 비동기 최종 JSON 생성
	function gatherFinalJson() {
		let obj = {};
		// 모집내용
		obj.employTitle = $("#employTitle").val() || "";
		obj.employEducation = $("[name='employEducation']").val() || "";
		obj.employExperience = gatherCheckedValues("employExperience").join(',');
		obj.employType = gatherCheckedValues("employType").join(',');

		// 근무조건
		obj.employWorkday = $("#employWorkday").val() || "";
		obj.employSwh = $("#employSwh").val() || "";
		obj.employEwh = $("#employEwh").val() || "";
		obj.employSalary = $("#employSalary").val() || "";
		obj.employSalaryYn = $("#employSalaryYn").val() || "";

		// 접수방법
		obj.employEd = $("#employEd").val() || "";
		obj.employApplication = $("input[name='employApplication']:checked").val() || "";
		obj.employUrl = $("#employUrl").val() || "";

		// 모집분야
		let fieldArr = [];
		$("#hiddenInputsContainer div[data-field-idx]").each(function() {
			let $d = $(this);
			let idx = $d.data("field-idx");
			let fOne = {
				filedNm: $d.find(`[name='fieldList[${idx}].filedNm']`).val() || "",
				filedPersonnel: $d.find(`[name='fieldList[${idx}].filedPersonnel']`).val() || "",
				filedRegion: $d.find(`[name='fieldList[${idx}].filedRegion']`).val() || "",
				filedRegionGungu: $d.find(`[name='fieldList[${idx}].filedRegionGungu']`).val() || "",
				filedJobs: formatTextareaValue($d.find(`[name='fieldList[${idx}].filedJobs']`).val()) || "",
				filedPreference: formatTextareaValue($d.find(`[name='fieldList[${idx}].filedPreference']`).val()) || "",
				filterList: [],
				procedure: []
			};
			// filterList
			let flMap = {};
			let $filters = $d.find(`[data-filter-parent='${idx}']`);
			$filters.each(function() {
				let fName = $(this).data("filterName");
				let fVal = $(this).val();
				let fIdx = $(this).data("filterIdx");
				if (!flMap[fIdx]) flMap[fIdx] = {};
				flMap[fIdx][fName] = fVal;
			});
			$.each(flMap, function(k, v) {
				fOne.filterList.push({
					filterTitleCd: v.filterTitleCd,
					filterContCd: v.filterContCd
				});
			});
			// procedure
			let $procs = $d.find(`input[name^='fieldList[${idx}].procedure']`);
			$procs.each(function() {
				let cVal = $(this).val();
				fOne.procedure.push({ procedureCd: cVal });
			});
			fieldArr.push(fOne);
		});
		obj.fieldList = fieldArr;
		return obj;
	}

	function gatherCheckedValues(nm) {
		let arr = [];
		$(`input[name='${nm}']:checked`).each(function() {
			arr.push($(this).val());
		});
		return arr;
	}

	// Alert, validate
	let alertMessage = function(msg, type) {
		// 예) SweetAlert2
		Swal.fire({
			text: msg,
			icon: type,
			timer: 1000,
			showConfirmButton: false
		});
	};

	// 유효성 검사 전용 alert 함수 (validationAlert)
	async function validationAlert(msg, type) {
		return new Promise((resolve) => {
			Swal.fire({
				text: msg,
				icon: type,
				timer: 1000,
				showConfirmButton: false,
				didClose: resolve, // 메시지가 닫히면 Promise를 resolve
			});
		});
	}

	function focusAndScroll(element) {
		if (!element || element.length === 0) return; // 요소가 없으면 실행 안 함
		const offset = element.offset().top - 100; // 스크롤 위치 조정
		$("html, body").animate({ scrollTop: offset }, 0, function() {
			element.trigger("focus"); // focus 대신 trigger("focus") 사용
		});
	}

	function validateCheckboxGroup(nm) {
		return $(`input[name='${nm}']:checked`).length > 0;
	}
});
