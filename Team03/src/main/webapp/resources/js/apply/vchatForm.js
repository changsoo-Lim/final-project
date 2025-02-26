/**
 * 1. 필터링 로직 (기존 코드)
 * 2. 방 생성 이벤트 (기존 + 메일 모달 오픈 추가)
 * 3. 메일 모달 오픈 로직
 */
document.addEventListener("DOMContentLoaded", function() {
	//const selectedItems = new Set(); // 전역 Set, 선택된 칸반 항목 관리

	// [중요] 동적 생성될 가능성이 있는 .kanban-item에 대한 이벤트 위임
	document.addEventListener("click", function(e) {
		const kanbanItem = e.target.closest(".kanban-item");
		if (!kanbanItem || kanbanItem.classList.contains("rejected")) return; // 불합격자는 선택 불가

		// Ctrl/Command 키를 누르면서 클릭한 경우에만 선택 상태를 관리
		if (e.ctrlKey || e.metaKey) {
			if (selectedItems.has(kanbanItem)) {
				selectedItems.delete(kanbanItem);
				kanbanItem.classList.remove("selected");
			} else {
				selectedItems.add(kanbanItem);
				kanbanItem.classList.add("selected");
			}
		}
	});

	// --------------------------
	// 1) 필터링 로직 (기존 코드)
	// --------------------------
	function filterOptions($targetSelect, filterClass) {
		const $options = $targetSelect.find("option");
		if (filterClass) {
			$options.hide();
			$options.filter(`:first, .${filterClass}`).show();
		} else {
			$options.hide();
			$options.filter(":first").show();
		}
		$targetSelect.val("");
	}

	function setupFilterEvent($sourceSelect, $targetSelect, $hiddenInput = null) {
		$sourceSelect.on("change", (event) => {
			const selectedValue = event.target.value;
			const selectedOption = $sourceSelect.find("option:selected");
			const filterClass = selectedValue ? selectedOption.data("code") : null;

			filterOptions($targetSelect, filterClass);

			if ($hiddenInput) {
				$hiddenInput.val(selectedValue || "");
			}
		});

		// 초기 로드 시 상세 옵션 숨기기
		if ($sourceSelect.val() === "") {
			filterOptions($targetSelect, null);
		}
	}

	const $empSelect = $("#emp");
	const $filedNoSelect = $("#filedNo");
	const $hiddenInput = $("#selectedEmployCode");

	setupFilterEvent($empSelect, $filedNoSelect, $hiddenInput);

	// 채용 공고 선택 시 면접 제목 자동 셋팅
	document.getElementById("emp").addEventListener("change", function() {
		const selectedOption = this.options[this.selectedIndex];
		const title = selectedOption.getAttribute("data-title");
		document.getElementById("vchatTitle").value = title ? `${title} [면접]` : "";
	});

	// --------------------------
	// 2) 방 생성 + DB 저장 + 메일 모달
	// --------------------------
	const vchatAddBtn = document.querySelector("#vchatAddBtn");
	const baseUrl = document.body.dataset.url;

	vchatAddBtn.addEventListener("click", function(e) {
		e.preventDefault();

		const vchatTitle = document.querySelector("#vchatTitle").value;
		const vchatRid = document.querySelector("#vchatRid").value;
		const vchatRpass = document.querySelector("#vchatRpass").value;
		const filedNo = document.querySelector("#filedNo").value;

		// 간단한 유효성 검사
		if (!vchatTitle || !vchatRid || !vchatRpass || !filedNo) {
			Swal.fire('에러!', '모든 필드를 채워주세요.', 'error');
			return;
		}
		const validItems = Array.from(selectedItems).filter(item => !item.classList.contains("rejected"));

		if (validItems.length === 0) {
			Swal.fire('경고!', '유효한 지원자를 선택해주세요.', 'warning');
			return;
		}

		// 1) 구르미 API에 방 생성 요청
		fetch(`${baseUrl}/vchat/room`, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({
				roomTitle: vchatTitle,
				roomUrlId: vchatRid,
				callType: 'SFU',
				liveMode: false,
				maxJoinCount: 4,
				liveMaxJoinCount: 100,
				passwd: vchatRpass,
				layoutType: '4',
				sfuIncludeAll: true
			})
		})
			.then(res => res.json())
			.then(res => {
				console.log('구르미 API 응답:', res);
				let res2 = JSON.parse(res);
				// JS 객체 상태 (JSON.parse(res) 제거)
				if (res2.resultCode === 'GRM_200') {
					// 방 생성 성공 시 DB 저장
					return fetch(`${baseUrl}/vchat`, {
						method: 'POST',
						headers: { 'Content-Type': 'application/json' },
						body: JSON.stringify({
							filedNo,
							vchatTitle,
							vchatRid,
							vchatRpass,
							vchatUrl: res2.data?.room?.roomId // 구르미 roomId
						})
					});
				} else {
					throw new Error(`구르미 방 생성 실패: ${res.description || '원인 미상'}`);
				}
			})
			.then(dbRes => dbRes.json())
			.then(dbRes => {
				console.log("DB 저장 응답:", dbRes);
				if (dbRes.status === "success") {
					// 화상채팅 모달 닫기
					const vchatModal = bootstrap.Modal.getInstance(document.getElementById("exampleModal1"));
					if (vchatModal) {
						vchatModal.hide();
					}

					// SweetAlert → "메일작성"
					Swal.fire({
						title: '화상 면접 등록 완료!',
						text: '지원자에게 안내 메일을 발송하시겠습니까?',
						icon: 'success',
						confirmButtonText: '메일작성'
					}).then(() => {
						// validItems 전달
						openEmailModalForVchat(vchatTitle, vchatRpass, validItems);
					});
				} else {
					throw new Error(`DB 저장 실패: ${dbRes.message || '원인 미상'}`);
				}
			})
			.catch(err => {
				console.error("방 생성 중 오류 발생:", err);
				Swal.fire('에러!', err.message, 'error');
			});
	});

	// 메일 모달 열기 함수
	function openEmailModalForVchat(vchatTitle, vchatRpass, validItems) {
		const emailSubjectInput = document.getElementById("emailSubject");
		emailSubjectInput.value = `[화상면접 안내] - 지원자님의 ${vchatTitle} 일정입니다.`;

		const defaultBodyTemplate = `
        안녕하세요, 지원자님.<br>
        마이페이지 -> 면접 관리 -> 면접 현황에서 아래 일정에 따라 화상 면접에 임해주시길 바랍니다.<br><br>
        - 제목: ${vchatTitle}<br>
        - 비밀번호: ${vchatRpass}<br>
        - 면접 일시: {면접일시}<br><br>
        지원자님의 합격을 응원합니다. 감사합니다.
    `;

		const selectedApplyNos = [];
		const recipientList = [];

		// 유효한 지원자 정보 가져오기
		let interviewDateTime = "미정"; // 면접 일시(한 번만 사용)
	    validItems.forEach((item, index) => {
	        const name = item.dataset.name;
	        const email = item.dataset.email;
	        const id = item.dataset.id;
	        // 첫 번째 지원자의 면접 일시만 쓴다 (또는 특정 기준으로 하나만)
	        if (index === 0) {
	            interviewDateTime = item.querySelector(".interview-schedule")?.textContent || "미정";
	        }
	
	        if (name && email) {
	            selectedApplyNos.push({
	                applyNo: parseInt(id, 10)
	            });
	            recipientList.push(`${name} (${email})`);
	        }
	    });

		// 수신자 목록 표시
	    const emailRecipientHeader = document.getElementById("emailRecipientHeader");
	    emailRecipientHeader.innerHTML = recipientList.length
	        ? recipientList.map((r) => `<span class="badge bg-secondary me-1">${r}</span>`).join("")
	        : '<span class="text-muted">수신 대상이 없습니다.</span>';
	
	    // 면접 일시를 단일 문자열로 치환
	    const fullEmailBody = defaultBodyTemplate.replace("{면접일시}", interviewDateTime);
	
	    // CKEditor나 기본 텍스트 영역에 본문 설정
	    if (window.editor) {
	        window.editor.setData(fullEmailBody);
	    } else {
	        document.getElementById("emailBody").value = fullEmailBody;
	    }

		// Hidden input에 지원자 ID 저장
		document.getElementById("applyNoInput").value = JSON.stringify(
			selectedApplyNos.map((item) => item.applyNo)
		);

		// 메일 모달 열기
		const mailModal = new bootstrap.Modal(document.getElementById("sendEmailModal"));
		mailModal.show();
	}

	// 메일 모달 닫힐 때 CKEditor/입력값 초기화
	const mailModalElement = document.getElementById("sendEmailModal");
	if (mailModalElement) {
		mailModalElement.addEventListener("hidden.bs.modal", function() {
			document.getElementById("emailSubject").value = "";
			if (window.editor) {
				window.editor.setData("");
			} else {
				document.getElementById("emailBody").value = "";
			}
		});
	}
});

