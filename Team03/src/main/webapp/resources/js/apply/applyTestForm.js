function formatDate(dateString) {
	const date = new Date(dateString);
	const year = date.getFullYear();
	const month = String(date.getMonth() + 1).padStart(2, '0');
	const day = String(date.getDate()).padStart(2, '0');
	return `${year}${month}${day}`;
}

document.addEventListener("DOMContentLoaded", function() {
	const baseUrl = document.body.dataset.url || window.location.origin;
	const testTypeSelect = document.getElementById("testTypeSelect");
	const testOwnerInputs = document.querySelectorAll("input[name='testOwner']");
	const testNameSelect = document.getElementById("testNameSelect");
	const applyTestButton = document.getElementById("applyTestButton");

	//const selectedItems = new Set(); // 선택된 지원자 데이터

	// 1) 지원자 선택 로직
	document.addEventListener("click", function(e) {
		const kanbanItem = e.target.closest(".kanban-item");
		if (!kanbanItem || kanbanItem.classList.contains("rejected")) return; // 불합격자 선택 불가

		// Ctrl/Command 키를 누르면서 클릭한 경우에만 선택 상태를 관리
		if (e.ctrlKey || e.metaKey) {
			if (selectedItems.has(kanbanItem)) {
				selectedItems.delete(kanbanItem);
				kanbanItem.classList.remove("selected");
			} else {
				selectedItems.add(kanbanItem);
				kanbanItem.classList.add("selected");
			}
			updateSelectedApplicants(); // 선택된 지원자 업데이트
		} else {
			// 기본 좌클릭: 상세 모달 창 띄우기
			showApplicantDetails(kanbanItem.dataset.id);
		}
	});

	// 선택된 지원자 UI 업데이트
	function updateSelectedApplicants() {
		const selectedApplicantsDiv = document.getElementById("selectedApplicants2");

		// 불합격 상태 지원자 자동 제거
		const validItems = Array.from(selectedItems).filter(item => !item.classList.contains("rejected"));
		selectedItems.clear();
		validItems.forEach(item => selectedItems.add(item));

		if (validItems.length === 0) {
			selectedApplicantsDiv.innerHTML = '<span class="text-muted">선택된 지원자가 없습니다.</span>';
			return;
		}

		const applicants = validItems.map(item => {
			const name = item.dataset.name || "지원자";
			return `<span class="badge bg-secondary me-1">${name}</span>`;
		});

		selectedApplicantsDiv.innerHTML = applicants.join("");
	}

	// 2) 시험 이름 목록 로드
	async function loadTestNames() {
		const testType = testTypeSelect.value;
		const testOwner = document.querySelector("input[name='testOwner']:checked")?.value;

		if (!testType || !testOwner) {
			testNameSelect.innerHTML = '<option value="" disabled selected>시험 이름을 선택하세요</option>';
			return;
		}

		try {
			const response = await fetch(`${baseUrl}/company/apply/tests?testType=${testType}&testOwner=${testOwner}`);
			if (!response.ok) throw new Error("시험 이름을 가져오는 데 실패했습니다.");
			const data = await response.json();

			testNameSelect.innerHTML = '<option value="" disabled selected>시험 이름을 선택하세요</option>';
			data.forEach(test => {
				testNameSelect.innerHTML += `<option value="${test.testNo}">${test.testNm}</option>`;
			});
		} catch (error) {
			Swal.fire("오류", error.message, "error");
		}
	}

	testTypeSelect.addEventListener("change", loadTestNames);
	testOwnerInputs.forEach(input => input.addEventListener("change", loadTestNames));

	// 3) 시험 적용 버튼
	applyTestButton.addEventListener("click", async function() {
		const selectedTestNo = testNameSelect.value;
		const startDate = document.getElementById("startDate").value;
		const endDate = document.getElementById("endDate").value;

		// 유효 지원자만 선택
		const validItems = Array.from(selectedItems).filter(item => !item.classList.contains("rejected"));

		if (!selectedTestNo || !startDate || !endDate || validItems.length === 0) {
			Swal.fire("경고", "모든 필드를 입력하고 유효한 지원자를 선택해주세요.", "warning");
			return;
		}

		const candidates = validItems.map(item => ({
			applyNo: item.dataset.id,
			candidateSdt: formatDate(startDate),
			candidateEdt: formatDate(endDate),
			testNo: selectedTestNo,
		}));

		try {
			const response = await fetch(`${baseUrl}/company/apply/applyTest`, {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(candidates),
			});

			if (!response.ok) throw new Error("시험 적용 중 오류가 발생했습니다.");
			
			const testModal = bootstrap.Modal.getInstance(document.getElementById("testModal"));
			if (testModal) {
				testModal.hide();
			}

			Swal.fire({
				title: '테스트 등록 완료!',
				text: '지원자에게 안내 메일을 발송하시겠습니까?',
				icon: "success",
				showCancelButton: true,
				confirmButtonText: "메일 작성",
				cancelButtonText: "취소",
			}).then((result) => {
				if (result.isConfirmed) {
					openEmailModal(
						testNameSelect.options[testNameSelect.selectedIndex].text,
						testTypeSelect.options[testTypeSelect.selectedIndex].text,
						candidates
					);
				}
			});
		} catch (error) {
			Swal.fire("오류", error.message, "error");
		}
	});

	// 4) 메일 모달 열기
	function openEmailModal(testName, testType, candidates) {
		const emailSubjectInput = document.getElementById("emailSubject");
		emailSubjectInput.value = `[시험 일정 안내] - ${testType}`;

		const defaultBody = `
            안녕하세요, 지원자님.<br>
            아래와 같이 시험이 배정되었습니다.<br>
			마이페이지 -> 검사/테스트 -> 요청받은 검사/테스트에서 아래 일정에 따라 테스트를 완료해 주시길 바랍니다.<br><br>
            - 시험 유형: ${testType}<br>
            - 시험 기간: ${candidates[0]?.candidateSdt} ~ ${candidates[0]?.candidateSdt}<br><br>
            지원자님의 성공적인 결과를 기원합니다. 감사합니다.
        `;

		if (window.editor) {
			window.editor.setData(defaultBody);
		} else {
			document.getElementById("emailBody").value = defaultBody;
		}

		const recipients = candidates.map(candidate => {
			const item = document.querySelector(`.kanban-item[data-id="${candidate.applyNo}"]`);
			const name = item?.dataset.name || "지원자";
			const email = item?.dataset.email || "이메일 없음";
			return `${name} (${email})`;
		});

		const emailRecipientHeader = document.getElementById("emailRecipientHeader");
		emailRecipientHeader.innerHTML = recipients.length
			? recipients.map(r => `<span class="badge bg-secondary me-1">${r}</span>`).join("")
			: '<span class="text-muted">선택된 지원자가 없습니다.</span>';

		document.getElementById("applyNoInput").value = JSON.stringify(candidates.map(candidate => candidate.applyNo));

		const mailModal = new bootstrap.Modal(document.getElementById("sendEmailModal"));
		mailModal.show();
	}

	// 5) 메일 발송 버튼
	document.getElementById("sendEmailButton").addEventListener("click", async function() {
		const subject = document.getElementById("emailSubject").value.trim();
		const body = window.editor?.getData().trim() || document.getElementById("emailBody").value.trim();
		const applyNos = JSON.parse(document.getElementById("applyNoInput").value || "[]");

		if (!subject || !body || applyNos.length === 0) {
			Swal.fire("경고", "메일 제목, 내용 또는 수신자가 없습니다.", "warning");
			return;
		}

		try {
			const response = await fetch(`${baseUrl}/company/apply/sendMailBatch`, {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify({ applyNos, subject, body }),
			});

			if (!response.ok) throw new Error("메일 발송 중 오류가 발생했습니다.");

			Swal.fire("성공", "메일이 성공적으로 발송되었습니다.", "success");
			const mailModal = bootstrap.Modal.getInstance(document.getElementById("sendEmailModal"));
			mailModal.hide();
		} catch (error) {
			Swal.fire("오류", error.message, "error");
		}
	});

	// 6) 메일 모달 닫을 때 초기화
	document.getElementById("sendEmailModal").addEventListener("hide.bs.modal", function() {
		document.getElementById("emailSubject").value = "";
		if (window.editor) {
			window.editor.setData("");
		} else {
			document.getElementById("emailBody").value = "";
		}
		document.getElementById("emailRecipientHeader").innerHTML = '<span class="text-muted">선택된 지원자가 없습니다.</span>';
		document.getElementById("applyNoInput").value = "";
	});
});
