const selectedItems = new Set();

document.addEventListener("DOMContentLoaded", async function() {

	const baseUrl = document.body.dataset.url || window.location.origin;
	const sweetAlert = function(icon, html) {
		Swal.fire({
			position: "center-center",
			icon: icon,
			html: html
		});
	}
	const toastAll = function(icon, html) {
		const toast = Swal.mixin({
			toast: true,
			position: 'center',
			showConfirmButton: false,
			timer: 1700,
			timerProgressBar: true,
			didOpen: (toast) => {
				toast.addEventListener('mouseenter', Swal.stopTimer)
				toast.addEventListener('mouseleave', Swal.resumeTimer)
			}
		});
		toast.fire({
			icon: icon,
			html: html
		});
	}

	const employSelect = document.getElementById("employSelect");
	const fieldSelect = document.getElementById("fieldSelect");
	const searchInput = document.getElementById("searchInput");
	const confirmButton = document.getElementById("confirmButton");
	const kanbanBoard = document.getElementById("kanbanBoard");

	const updatedStatuses = new Map(); // 드래그 이동 등으로 변경된 지원자 상태(ApplyNo→NewStatus)
	// 다중 선택된 kanban-item
	let draggedItems = [];             // 드래그 중인 항목
	const selectAllState = new Map();  // "전체 선택" 버튼 상태 추적

	const container = document.querySelector(".container-fluid");
	if (container) {
		container.removeAttribute("class");
	}

	try {
		window.editor = await ClassicEditor.create(document.querySelector('#emailBody'));
	} catch (e) {
		console.error("CKEditor 오류:", e);
	}

	// 면접 일정 버튼 클릭 처리 (이벤트 위임 방식)
	document.body.addEventListener("click", function(event) {
		if (event.target && event.target.classList.contains("add-interview-button")) {
			const selectedItemsArray = Array.from(selectedItems); // 전역 selectedItems 사용
			if (selectedItemsArray.length === 0) {
				sweetAlert("오류", "면접 일정을 설정할 지원자를 선택하세요.", "error");
				return;
			}

			// 선택된 지원자 목록 표시
			const applicantsHtml = selectedItemsArray.map(item => {
				const name = item.dataset.name || "이름 없음";
				return `<div class="badge bg-secondary me-1">${name}</div>`;
			}).join("");

			const selectedApplicantsContainer = document.getElementById("selectedApplicants");
			selectedApplicantsContainer.innerHTML = applicantsHtml;

			// 모달 열기 전 입력 필드 초기화
			document.getElementById("einterviewDate").value = "";
			document.getElementById("einterviewTime").value = "";

			const interviewModal = new bootstrap.Modal(document.getElementById("interviewScheduleModal"));
			interviewModal.show();
		}
	});
	const timeSelect = document.getElementById("einterviewTime");

	// 00:00 ~ 23:30까지 30분 단위 옵션 생성
	for (let hour = 0; hour < 24; hour++) {
		for (let minute = 0; minute < 60; minute += 30) {
			const hourStr = hour.toString().padStart(2, "0");
			const minuteStr = minute.toString().padStart(2, "0");
			const option = document.createElement("option");
			option.value = `${hourStr}:${minuteStr}`;
			option.textContent = `${hourStr}:${minuteStr}`;
			timeSelect.appendChild(option);
		}
	}
	// 면접 일정 저장 버튼 처리
	const saveInterviewButton = document.getElementById("saveInterviewSchedule");
	if (saveInterviewButton) {
		saveInterviewButton.addEventListener("click", function() {
			const interviewDateField = document.getElementById("einterviewDate");
			const interviewDate = interviewDateField.value;
			const interviewTime = timeSelect.value;


			if (!interviewDate || !interviewTime) {
				Swal.fire({
					icon: "error",
					title: "오류",
					text: "면접 일자와 시간을 입력하세요.",
				});
				return;
			}

			const interviewDatetime = `${interviewDate}T${interviewTime}`;
			const selectedItemsArray = Array.from(selectedItems);
			const interviewData = selectedItemsArray.map(item => ({
				applyNo: item.dataset.id,
				intvDt: interviewDatetime,
				intvYn: "N",
			}));

			fetch(`${baseUrl}/company/apply/interview`, {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(interviewData),
			})
				.then(response => response.json())
				.then(result => {
					if (result.status === "success") {
						Swal.fire({
							icon: "success",
							title: "성공",
							text: "면접 일정이 저장되었습니다.",
						});

						// UI 업데이트: 면접 일정 추가
						selectedItemsArray.forEach(item => {
							const interviewScheduleElement = item.querySelector(".interview-schedule");
							if (interviewScheduleElement) {
								interviewScheduleElement.textContent = formatDateTime(interviewDatetime);
							}
						});

						const interviewModal = bootstrap.Modal.getInstance(document.getElementById("interviewScheduleModal"));
						interviewModal.hide();
					} else {
						throw new Error(result.message || "면접 일정 저장 실패");
					}
				})
				.catch(error => {
					console.error("면접 일정 저장 실패:", error);
					Swal.fire({
						icon: "error",
						title: "오류",
						text: "면접 일정 저장 중 오류가 발생했습니다.",
					});
				});
		});
	}

	// 채용공고 리스트 불러와서 employSelect에 넣기
	fetch(`${baseUrl}/company/apply/employ`)
		.then(res => res.json())
		.then(employList => {
			// 기존 옵션 초기화
			employSelect.innerHTML = `<option value="" disabled selected>채용공고 선택</option>`;

			// 중복 방지를 위해 Set 사용
			const existingOptions = new Set();
			employList.forEach(e => {
				if (!existingOptions.has(e.employNo)) {
					const opt = document.createElement("option");
					opt.value = e.employNo;
					opt.textContent = e.employTitle;
					employSelect.appendChild(opt);

					// 추가한 값 기록
					existingOptions.add(e.employNo);
				}
			});
		})
		.catch(err => console.error("채용공고 목록 로드 실패:", err));

	// employSelect 변경 시 → 해당 employNo의 모집분야 fields 불러오기
	employSelect.addEventListener("change", function() {
		const employNo = this.value;

		// 채용공고 변경 시 칸반 컬럼 초기화
		kanbanBoard.innerHTML = "";

		fieldSelect.innerHTML = `<option value="" disabled selected>모집 분야 선택</option>`;
		if (!employNo) return;

		fetch(`${baseUrl}/company/apply/fields?employNo=${employNo}`)
			.then(res => res.json())
			.then(fields => {
				fields.forEach(f => {
					const opt = document.createElement("option");
					opt.value = f.filedNo;
					opt.textContent = f.filedNm;
					fieldSelect.appendChild(opt);
				});
			})
			.catch(err => console.error("모집분야 로드 실패:", err));
	});

	// 모집분야 변경 시 → 칸반데이터 로드
	fieldSelect.addEventListener("change", function() {
		const filedNo = this.value;
		loadKanbanData(filedNo);
		let originalKanbanData = null; // 초기 상태 저장
	});

	async function loadKanbanData(filedNo) {
		try {
			// 상태 목록 & 지원자 목록 병렬로
			const [statusRes, applicantsRes] = await Promise.all([
				fetch(`${baseUrl}/company/apply/statusList?filedNo=${filedNo}`),
				fetch(`${baseUrl}/company/apply?filedNo=${filedNo}`)
			]);
			if (!statusRes.ok || !applicantsRes.ok) {
				throw new Error("상태/지원자 데이터를 불러올 수 없습니다.");
			}
			const statusList = await statusRes.json();
			const applicants = await applicantsRes.json();

			// 초기 상태 저장
			originalKanbanData = { statusList, applicants };

			// 기존 칸반 보드 초기화
			kanbanBoard.innerHTML = "";
			selectedItems.clear();
			updatedStatuses.clear();

			renderKanbanBoard(statusList, applicants);
			attachGlobalEvents(); // 검색, confirmButton 등 다시 세팅
		} catch (err) {
			console.error("칸반 데이터 로드 실패:", err);
		}
	}
	const cancelButton = document.getElementById("cancelButton");
	if (cancelButton) {
		cancelButton.addEventListener("click", function() {
			if (updatedStatuses.size === 0) {
				sweetAlert("info", "변경된 내용이 없습니다.");
				return;
			}

			if (!originalKanbanData) {
				sweetAlert("error", "초기 데이터를 찾을 수 없습니다.");
				return;
			}

			// 초기 상태로 복구
			const { statusList, applicants } = originalKanbanData;

			kanbanBoard.innerHTML = ""; // 기존 보드 초기화
			selectedItems.clear();      // 선택 항목 초기화
			updatedStatuses.clear();    // 업데이트 상태 초기화

			renderKanbanBoard(statusList, applicants); // 초기 상태로 렌더링
			toastAll("info", "변경 사항이 초기화되었습니다.");
		});
	}

	// 칸반보드를 동적으로 그리면서, 기존 로직에서 필요한 data-* 속성, 이벤트 바인딩까지
	function renderKanbanBoard(statusList, applicants) {
		// applicants를 status별로 그룹화
		const grouped = {};
		applicants.forEach(app => {
			const st = app.appProcStatus || "UNDEF";
			if (!grouped[st]) grouped[st] = [];
			grouped[st].push(app);
		});

		// 상태 목록 순회하며, 각 컬럼을 만들고 아이템 붙이기
		statusList.forEach(st => {
			const column = document.createElement("div");
			column.className = "kanban-column card flex-shrink-0";
			column.dataset.status = st.procedureCd;

			// 컬럼 헤더
			const header = document.createElement("div");
			header.className = "card-header bg-primary text-white d-flex justify-content-between align-items-center";
			header.innerHTML = `
                <div class="d-flex align-items-center gap-2">
                    <span class="status-name">${st.codeNm}</span>
                    <span class="status-count">0</span>
                </div>
                <button class="btn btn-sm btn-light select-all-button" data-status="${st.procedureCd}">전체 선택</button>
            `;
			column.appendChild(header);

			// 아이템 컨테이너
			const itemsContainer = document.createElement("div");
			itemsContainer.className = "kanban-items p-3";
			column.appendChild(itemsContainer);

			// "액션 영역" (불합격, 문자, 메일버튼 등)
			const actions = document.createElement("div");
			actions.className = "kanban-actions p-3 bg-light hidden border-top";
			actions.innerHTML = `
                <p class="selected-count fw-bold text-secondary">
                    <span id="selectedCount">0</span>명 선택됨
                </p>
                <div class="d-flex flex-wrap justify-content-center text-center gap-2">
                    <div class="d-flex justify-content-around gap-2">
                        <button class="reject-button btn btn-danger btn-sm">불합격</button>
                        <button class="action-button btn btn-primary btn-sm">문자 발송</button>
                        <button class="btn btn-sm btn-primary open-email-modal">메일 발송</button>
                    </div>
                    <div class="d-flex justify-content-around gap-2">
                        <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#testModal">테스트</button>
                        <button id="interviewBtn" class="btn btn-sm btn-primary add-interview-button">면접 일정</button>
						<button class="btn btn-sm btn-primary conference-button" data-bs-toggle="modal" data-bs-target="#exampleModal1">화상 면접</button>
                    </div>
                </div>
            `;
			column.appendChild(actions);


			// 해당 상태코드의 지원자 목록 DOM 생성
			const appList = grouped[st.procedureCd] || [];
			appList.forEach(app => {
				const item = document.createElement("div");
				item.className = "kanban-item card mb-1 p-3";

				// 불합격인 경우
				if (app.appPassYn === "N") {
					item.classList.add("rejected");
				}
				// 지원 취소 상태인 경우
				if (app.appCancelYn === "Y") {
					item.classList.add("canceled");
					item.style.opacity = "0.5"; // 시각적으로 구분하기 위해 투명도 적용
					item.style.pointerEvents = "none"; // 클릭, 드래그 등 이벤트 비활성화
				} else {
					item.draggable = true; // 지원 취소되지 않은 경우만 드래그 가능
				}

				// data-* 속성
				item.dataset.id = app.applyNo;
				item.dataset.phone = app.member?.memHp || "";
				item.dataset.email = app.member?.memEmail || "";
				item.dataset.name = app.member?.memNm || "";

				// 내부 HTML
				item.innerHTML = `
				    <div class="d-flex justify-content-between">
				        <p class="mb-2"><strong>${app.member?.memNm || "-"}</strong></p>
				        <p class="mb-2 text-end">${formatDate(app.appDate)}</p>
				    </div>
				    <div class="d-flex justify-content-between">
				        <p class="app-status ${app.appProcStatus == 'AP08' ? 'text-primary' : ''}">
				            ${app.appCancelYn === "Y" ? "지원 취소" : app.appPassYn === "N" ? "불합격" : st.codeNm}
				        </p>
				        <p class="interview-schedule text-muted" data-id="${app.applyNo}">
				            ${app.intvDt ? formatDateTime(app.intvDt) : ""}
				        </p>
				    </div>
				`;

				// 아이템 이벤트(클릭, 드래그 등) 바인딩 (취소된 항목 제외)
				if (app.appCancelYn !== "Y") {
					attachItemEvents(item);
				}

				itemsContainer.appendChild(item);
			});

			kanbanBoard.appendChild(column);
		});

		// "전체 선택" 버튼과 드래그/드랍 수용을 위한 컬럼 이벤트 바인딩
		attachColumnEvents();
		updateColumnCounts();
	}

	// "아이템 이벤트" 바인딩 함수
	function attachItemEvents(item) {
		item.addEventListener("click", function(e) {
			const column = this.closest(".kanban-column");

			if (e.ctrlKey || e.metaKey) {
				// 다중 선택
				if (selectedItems.has(this)) {
					selectedItems.delete(this);
					this.classList.remove("selected");
				} else {
					selectedItems.add(this);
					this.classList.add("selected");
				}
				updateActionVisibility(column); // 다중 선택일 때만 액션 영역 업데이트
			} else {
				// 좌클릭 시 상세정보 모달만 띄우기
				showApplicantDetails(this.dataset.id);

				// 화면 가운데로 스크롤
				this.scrollIntoView({
					behavior: "smooth", // 부드럽게 스크롤
					block: "center",    // 화면 중앙 정렬
					inline: "center",   // 가로 중앙 정렬
				});
			}
		});

		// 드래그 시작
		item.addEventListener("dragstart", function(e) {
			// 현재 아이템의 상태 확인
			const status = this.closest(".kanban-column")?.dataset.status;

			// 최종 합격 상태에서는 드래그 불가
			if (status === "AP08") {
				e.preventDefault();
				sweetAlert("error", "최종합격 상태에서는 이동할 수 없습니다.");
				return;
			}

			// 이미 선택된 항목 중에서 .rejected 는 제외
			draggedItems = Array.from(selectedItems).filter((si) => !si.classList.contains("rejected"));

			// 만약 현재 클릭한 item이 selectedItems에 없으면 추가
			if (!draggedItems.includes(this)) {
				draggedItems.push(this);
			}

			// .rejected인 아이템은 드래그 불가
			if (this.classList.contains("rejected")) {
				e.preventDefault();
				sweetAlert("error", "불합격 상태의 지원자는 이동할 수 없습니다.");
				return;
			}

			draggedItems.forEach((d) => d.classList.add("dragging"));
		});

		// 드래그 종료
		item.addEventListener("dragend", function() {
			draggedItems.forEach((d) => d.classList.remove("dragging"));
			draggedItems = [];
		});
	}

	// 컬럼(상태) 이벤트 바인딩 (드래그오버, 드랍, 전체선택 버튼)
	function attachColumnEvents() {
		const columns = document.querySelectorAll(".kanban-column");

		columns.forEach(col => {
			// 드래그 오버
			col.addEventListener("dragover", function(e) {
				e.preventDefault();
				// 스크롤 처리
				const rect = kanbanBoard.getBoundingClientRect();
				const buffer = 200;
				const mouseX = e.clientX;
				if (mouseX < rect.left + buffer) {
					kanbanBoard.scrollLeft -= 30;
				} else if (mouseX > rect.right - buffer) {
					kanbanBoard.scrollLeft += 30;
				}
			});

			// 드랍
			col.addEventListener("drop", function(e) {
				const targetStatus = col.dataset.status;
				const originStatus = draggedItems[0]?.closest(".kanban-column")?.dataset.status;
				if (targetStatus === originStatus) {
					// 같은 컬럼이면 무시
					draggedItems.forEach(d => d.classList.remove("dragging"));
					draggedItems = [];
					return;
				}

				const itemsContainer = col.querySelector(".kanban-items");
				draggedItems.forEach(d => {
					const applyNo = d.dataset.id;
					itemsContainer.appendChild(d);
					d.classList.remove("selected", "dragging");
					d.classList.add("pending");
					updatedStatuses.set(applyNo, targetStatus);
					selectedItems.delete(d);
				});
				draggedItems = [];

				updateActionVisibility(col);
				updateColumnCounts();
				document.querySelectorAll(".kanban-actions").forEach(a => a.classList.add("hidden"));
			});

			// "전체 선택" 버튼
			const selectAllBtn = col.querySelector(".select-all-button");
			if (selectAllBtn) {
				selectAllBtn.addEventListener("click", function() {
					// 기존: const items = col.querySelectorAll(".kanban-item");
					// 변경: ".kanban-item" 중 .canceled는 제외
					const items = col.querySelectorAll(".kanban-item:not(.canceled)");

					const isAllSelected = selectAllState.get(col) || false;

					if (!isAllSelected) {
						items.forEach(i => {
							// 여기서 .rejected 도 제외하려면 :not(.rejected)로 필터링 or if문
							// if (!i.classList.contains("rejected")) { ... }

							if (!selectedItems.has(i)) {
								selectedItems.add(i);
								i.classList.add("selected");
							}
						});
						selectAllState.set(col, true);
					} else {
						items.forEach(i => {
							if (selectedItems.has(i)) {
								selectedItems.delete(i);
								i.classList.remove("selected");
							}
						});
						selectAllState.set(col, false);
					}
					updateActionVisibility(col);
				});
			}
		});
	}

	// 검색, 상태 업데이트(confirmButton), 불합격처리, 문자/메일 발송 등
	function attachGlobalEvents() {
		// 검색
		searchInput.addEventListener("input", function() {
			const query = this.value.trim().toLowerCase();
			const items = document.querySelectorAll(".kanban-item");
			let found = false;

			items.forEach(item => {
				const name = item.dataset.name?.toLowerCase() || "";
				if (name.includes(query)) {
					// 검색어가 일치하면 해당 항목 표시 및 강조
					item.style.display = "block";
					item.classList.add("highlight");
					if (!found) {
						// 처음으로 일치하는 항목으로 스크롤
						item.scrollIntoView({
							behavior: "smooth", // 부드럽게 스크롤
							block: "center", // 화면 중앙으로 스크롤
						});
						found = true;
					}
				} else {
					// 검색어가 일치하지 않으면 숨김 및 강조 제거
					item.style.display = "none";
					item.classList.remove("highlight");
				}
			});
		});

		// "저장"(상태 업데이트) 버튼
		confirmButton.addEventListener("click", async function() {
			if (updatedStatuses.size === 0) {
				sweetAlert("error", "변경된 항목이 없습니다.");
				return;
			}

			const updates = Array.from(updatedStatuses).map(([applyNo, newStatus]) => ({
				applyNo, newStatus
			}));

			try {
				const res = await fetch(`${baseUrl}/company/apply/updateStatusBatch`, {
					method: "POST",
					headers: { "Content-Type": "application/json" },
					body: JSON.stringify(updates),
				});

				if (!res.ok) throw new Error("상태 업데이트 실패");

				const updatedApplicantsRes = await fetch(`${baseUrl}/company/apply?filedNo=${fieldSelect.value}`);
				const updatedApplicants = await updatedApplicantsRes.json();

				// 저장 성공 시 `updatedStatuses` 초기화
				updatedStatuses.clear();

				// `originalKanbanData`를 업데이트된 데이터로 갱신
				originalKanbanData = {
					statusList: originalKanbanData.statusList, // 상태 리스트는 동일
					applicants: updatedApplicants,
				};

				toastAll("success", "상태 업데이트 완료");

				// UI 반영
				updates.forEach(u => {
					const item = document.querySelector(`.kanban-item[data-id="${u.applyNo}"]`);
					if (item) {
						item.classList.remove("pending");
						const stCol = document.querySelector(`.kanban-column[data-status="${u.newStatus}"]`);
						const newStatusName = stCol?.querySelector(".status-name")?.textContent || "";
						const appStatus = item.querySelector(".app-status");
						if (appStatus) {
							appStatus.textContent = newStatusName;
							if (u.newStatus === "AP08") {
								appStatus.classList.add("text-primary");
							} else {
								appStatus.classList.remove("text-primary");
							}
						}
					}
				});

				selectedItems.forEach(i => i.classList.remove("selected"));
				selectedItems.clear();
				updateColumnCounts();
			} catch (e) {
				console.error(e);
				sweetAlert("error", "상태 업데이트 실패");
			}
		});

		document.querySelectorAll(".reject-button").forEach(btn => {
			btn.addEventListener("click", function() {
				const arr = Array.from(selectedItems); // 선택된 항목 배열화
				if (arr.length === 0) {
					sweetAlert("error", "불합격 처리할 지원자를 선택하세요.");
					return;
				}

				// 최종합격 상태 항목 확인
				const finalApproved = arr.filter(item => {
					const status = item.closest(".kanban-column")?.dataset.status;
					return status === "AP08"; // 최종합격 상태 코드
				});
				if (finalApproved.length > 0) {
					sweetAlert("error", "최종합격 상태의 지원자는 불합격 처리할 수 없습니다.");
					return;
				}

				// 이미 불합격 상태인 항목
				const alreadyRejected = arr.filter(i => i.classList.contains("rejected"));
				if (alreadyRejected.length > 0) {
					sweetAlert("error", "이미 불합격 상태인 지원자가 포함되어 있습니다.");
					return;
				}

				// 유효 항목
				const valid = arr.filter(i => !i.classList.contains("rejected"));
				if (valid.length === 0) {
					sweetAlert("error", "선택 항목 중 불합격 처리할 수 있는 항목이 없습니다.");
					return;
				}

				// 불합격 처리 전 확인 창
				Swal.fire({
					title: "불합격 처리",
					html: "불합격 처리 후 원상태로 되돌릴 수 없습니다.<br>계속하시겠습니까?",
					icon: "warning",
					showCancelButton: true,
					confirmButtonText: "예",
					cancelButtonText: "아니요",
					reverseButtons: true,
				}).then(result => {
					if (result.isConfirmed) {
						// 불합격 처리 실행
						const rejectData = valid.map(v => ({
							applyNo: v.dataset.id,
							currentStatus: v.closest(".kanban-column")?.dataset.status
						}));
						fetch(`${baseUrl}/company/apply/rejectApplicantsBatch`, {
							method: "POST",
							headers: { "Content-Type": "application/json" },
							body: JSON.stringify(rejectData),
						})
							.then(res => {
								if (!res.ok) throw new Error("불합격 처리 실패");

								// UI 업데이트: 선택된 항목을 불합격 상태로 표시
								valid.forEach(item => {
									item.classList.add("rejected");
									item.classList.remove("selected"); // 선택 상태 해제
									const appStatus = item.querySelector(".app-status");
									if (appStatus) {
										appStatus.textContent = "불합격";
										appStatus.classList.remove("text-primary");
									}
								});

								// 선택 상태 초기화
								selectedItems.clear(); // 선택 상태 초기화
								document.querySelectorAll(".kanban-item.selected").forEach(item => {
									item.classList.remove("selected"); // UI에서 선택 상태 제거
								});

								// 액션 영역 초기화
								document.querySelectorAll(".kanban-actions").forEach(actions => {
									actions.classList.add("hidden"); // 액션 영역 숨김
								});
								document.querySelectorAll("#selectedCount").forEach(countEl => {
									countEl.textContent = "0"; // 선택된 항목 수 초기화
								});

								toastAll("success", "불합격 처리가 완료되었습니다.");
							})
							.catch(e => {
								console.error(e);
								sweetAlert("error", "불합격 처리 실패");
							});
					} else {
						// 취소 버튼 클릭 시
						toastAll("info", "불합격 처리가 취소되었습니다.");
					}
				});
			});
		});

		// 문자 발송
		document.querySelectorAll(".action-button").forEach(btn => {
			btn.addEventListener("click", function() {
				const arr = Array.from(selectedItems);
				if (arr.length === 0) {
					sweetAlert("error", "문자를 발송할 지원자를 선택하세요.");
					return;
				}
				const phoneDetails = [];
				const phoneNumbers = [];
				arr.forEach(i => {
					const name = i.dataset.name;
					const phone = i.dataset.phone;
					const fmPhone = formatPhoneNumber(phone);
					if (name && fmPhone) {
						phoneDetails.push(`${name} (${fmPhone})`);
						phoneNumbers.push(fmPhone);
					}
				});
				if (phoneDetails.length === 0) {
					sweetAlert("error", "선택된 지원자 중 연락처 정보가 없습니다.");
					return;
				}
				document.getElementById("smsRecipientHeader").innerHTML =
					phoneDetails.map(d => `<span class="badge bg-secondary me-1">${d}</span>`).join("");
				document.getElementById("phoneNumbersInput").value = JSON.stringify(phoneNumbers);

				// 문자 모달 열기
				const smsModal = new bootstrap.Modal(document.getElementById("sendSmsModal"));
				smsModal.show();
			});
		});

		// 문자 모달 내부 글자수 카운트
		const smsMessage = document.getElementById("smsMessage");
		const smsCharCount = document.getElementById("smsCharCount");
		if (smsMessage && smsCharCount) {
			smsMessage.addEventListener("input", function() {
				smsCharCount.textContent = this.value.length;
			});
		}

		// 문자 발송 버튼
		const sendSmsButton = document.getElementById("sendSmsButton");
		if (sendSmsButton) {
			sendSmsButton.addEventListener("click", async function() {
				const phoneNumbers = JSON.parse(document.getElementById("phoneNumbersInput").value || "[]");
				const message = smsMessage.value.trim();
				if (phoneNumbers.length === 0 || !message) {
					sweetAlert("error", "수신자 또는 메시지가 비어있습니다.");
					return;
				}
				try {
					const resp = await fetch(`${baseUrl}/company/apply/sendSmsBatch`, {
						method: "POST",
						headers: { "Content-Type": "application/json" },
						body: JSON.stringify({ phoneNumbers, message })
					});
					const result = await resp.json();
					if (resp.ok && result.message.includes("성공")) {
						toastAll('success', "문자가 성공적으로 발송되었습니다.");
					} else {
						sweetAlert(result.message || "error", "문자 발송 실패");
					}
				} catch (error) {
					console.error("문자 발송 오류:", error);
					sweetAlert("error", "문자 발송 중 오류 발생");
				}
				// 모달 닫기
				const smsModal = bootstrap.Modal.getInstance(document.getElementById("sendSmsModal"));
				smsModal.hide();
			});
		}

		// 메일 발송 모달 열기
		document.querySelectorAll(".open-email-modal").forEach(btn => {
			btn.addEventListener("click", async function() {
				const arr = Array.from(selectedItems);
				if (arr.length === 0) {
					sweetAlert("error", "메일을 발송할 지원자를 선택하세요.");
					return;
				}

				const applyNos = [];
				const recipientList = [];

				arr.forEach(i => {
					const name = i.dataset.name;
					const email = i.dataset.email;
					const id = i.dataset.id;
					if (name && email) {
						applyNos.push(parseInt(id, 10));
						recipientList.push(`${name} (${email})`);
					}
				});

				// 수신 차단자 목록 조회
				let blockedList = [];
				try {
					const res = await fetch(`${baseUrl}/company/apply/selectBlockedApplicants`);
					if (res.ok) {
						blockedList = await res.json();
						console.log(blockedList); // 확인용 콘솔 출력
					} else {
						console.error("수신 차단자 목록 조회 실패");
					}
				} catch (err) {
					console.error("수신 차단자 조회 중 오류:", err);
				}

				// 받는 사람 정보 업데이트
				document.getElementById("emailRecipientHeader").innerHTML =
					recipientList.length > 0
						? recipientList.map(r => `<span class="badge bg-secondary me-1">${r}</span>`).join("")
						: '<span class="text-muted">선택된 이메일이 표시되지 않았습니다.</span>';

				// 수신 차단자 정보 업데이트
				document.getElementById("emailBlockedList").innerHTML =
					blockedList.length > 0
						? blockedList
							.map(r => `<li>${r.memNm} (${r.memEmail})</li>`)
							.join("")
						: '';

				// 선택된 지원자 ID 저장
				document.getElementById("applyNoInput").value = JSON.stringify(applyNos);

				// 모달 열기
				const mailModal = new bootstrap.Modal(document.getElementById("sendEmailModal"));
				mailModal.show();
			});
		});

		// 메일 발송 버튼
		const sendEmailButton = document.getElementById("sendEmailButton2");
		if (sendEmailButton) {
			sendEmailButton.addEventListener("click", async function() {

				const subject = document.getElementById("emailSubject").value.trim();
				const editorData = editor?.getData()?.trim() || "";
				const applyNos = JSON.parse(document.getElementById("applyNoInput").value || "[]");

				if (!subject || !editorData) {
					sweetAlert("error", "제목과 내용을 모두 입력해주세요.");
					return;
				}
				if (applyNos.length === 0) {
					sweetAlert("error", "메일을 발송할 지원자를 선택해주세요.");
					return;
				}

				try {
					const resp = await fetch(`${baseUrl}/company/apply/sendMailBatch`, {
						method: 'POST',
						headers: {
							'Content-Type': 'application/json; charset=UTF-8'
						},
						body: JSON.stringify({ applyNos, subject, body: editorData })
					});
					const result = await resp.json();

					if (resp.ok) {
						// 발송 성공, 실패, 수신 차단 목록 업데이트
						const successList = result.successEmails || [];
						const failedList = result.failedEmails || [];
						const blockedList = result.blockedApplicants || [];

						document.getElementById("emailSuccessList").innerHTML =
							successList.length > 0
								? successList.map(email => `<li>${email}</li>`).join("")
								: '<li class="text-muted">발송 성공한 이메일이 없습니다.</li>';

						document.getElementById("emailFailedList").innerHTML =
							failedList.length > 0
								? failedList.map(email => `<li>${email}</li>`).join("")
								: '<li class="text-muted">발송 실패한 이메일이 없습니다.</li>';

						document.getElementById("emailBlockedList").innerHTML =
							blockedList.length > 0
								? blockedList.map(applicant => `<li>${applicant}</li>`).join("")
								: '<li class="text-muted">수신 차단된 사용자가 없습니다.</li>';

						Swal.fire({
							icon: "success",
							title: "메일 발송 완료",
							html: `
                        <p>성공: ${result.sentCount || 0}건</p>
                        <p>실패: ${result.failedCount || 0}건</p>
                        <p>수신 차단: ${result.blockedCount || 0}건</p>
                    `
						});
					} else {
						sweetAlert("error", result.message || "메일 발송 실패");
					}
				} catch (e) {
					console.error("메일 발송 오류:", e);
					sweetAlert("error", "메일 발송 중 오류 발생");
				}
			});
		}

	}
	const mailModalElement = document.getElementById("sendEmailModal");

	if (mailModalElement) {
		mailModalElement.addEventListener("hidden.bs.modal", function() {
			// 입력 필드 리셋
			document.getElementById("emailSubject").value = "";
			editor?.setData(""); // CKEditor 내용 리셋
			document.getElementById("emailRecipientHeader").innerHTML =
				'<span class="text-muted">선택된 이메일이 표시되지 않았습니다.</span>';
			document.getElementById("emailBlockedList").innerHTML =
				'<li class="text-muted">수신 차단된 사용자가 없습니다.</li>';
			document.getElementById("emailSuccessList").innerHTML = "";
			document.getElementById("emailFailedList").innerHTML = "";
			document.getElementById("applyNoInput").value = "";
		});
	}
	// 칼럼별 개수 업데이트, 상세보기, 날짜포맷, 연락처포맷
	function updateColumnCounts() {
		const columns = document.querySelectorAll(".kanban-column");
		columns.forEach(col => {
			const items = col.querySelectorAll(".kanban-item");
			const cnt = col.querySelector(".status-count");
			if (cnt) cnt.textContent = `${items.length}`;
		});
	}

	function updateActionVisibility(column) {
		const selectedCount = column.querySelectorAll(".kanban-item.selected").length;
		const actions = column.querySelector(".kanban-actions");
		const sc = column.querySelector("#selectedCount");
		if (!actions || !sc) return;
		sc.textContent = selectedCount;
		if (selectedCount > 0) {
			actions.classList.remove("hidden");
		} else {
			actions.classList.add("hidden");
		}
	}

	// 지원자 상세보기
	async function showApplicantDetails(applyNo) {
		try {
			// 기본 정보 API 호출
			const response = await fetch(`${baseUrl}/company/apply/detail?applyNo=${applyNo}`);
			if (!response.ok) throw new Error("기본 정보 요청 실패");
			const data = await response.json();

			// 모달 초기화 및 데이터 채우기
			const modal = document.getElementById("detailModal");
			modal.dataset.applyNo = applyNo; // applyNo 저장
			updateBasicInfo(modal, data); // 기본 정보 업데이트
			initializeAccordion(modal); // 아코디언 초기화

			// 모달 닫힐 때 초기화
			modal.addEventListener("hidden.bs.modal", () => resetModal(modal));

			// 모달 열기
			const bsModal = new bootstrap.Modal(modal);
			bsModal.show();
		} catch (error) {
			console.error(error);
			sweetAlert("error", "상세 정보를 가져오는 중 오류가 발생했습니다.");
		}
	}

	// 기본 정보를 업데이트하는 함수
	function updateBasicInfo(modal, data) {
		modal.querySelector("#modalName").textContent = data.member?.memNm || "-";
		modal.querySelector("#modalPhone").textContent = formatPhoneNumber(data.member?.memHp || "-");
		modal.querySelector("#modalEmail").textContent = data.member?.memEmail || "-";
		modal.querySelector("#modalField").textContent = data.field
			? data.field.map(f => f.filedNm).join(", ")
			: "-";
		modal.querySelector("#modalEmployTitle").textContent = data.employ?.employTitle || "-";
		modal.querySelector("#modalStatus").textContent = data.codeNm || "-";
		modal.querySelector("#modalDate").textContent = formatDateTime(data.appDate);
		modal.querySelector("#modalChangeDate").textContent = data.appProcChangeDt ? formatDateTime(data.appProcChangeDt) : "N/A";
		//modal.querySelector("#modalResumeViewYn").textContent = data.appOpenYn == 'Y' ? "열람" : "미열람";

		// 합격 여부 업데이트
		const passYnEl = modal.querySelector("#modalPassYn");
		passYnEl.textContent = data.appPassYn === "Y" ? "합격" : data.appPassYn === "N" ? "불합격" : "진행중";
		passYnEl.className =
			data.appPassYn === "Y"
				? "badge fs-5 px-4 py-2 bg-success text-white"
				: data.appPassYn === "N"
					? "badge fs-5 px-4 py-2 bg-danger text-white"
					: "badge fs-5 px-4 py-2 bg-warning text-dark";

		// 이력서 정보 업데이트
		const modalFiles = modal.querySelector("#modalFiles");
		if (data.file?.fileDetails && data.file.fileDetails.length > 0) {
			const fileListHtml = data.file.fileDetails.map(fd => {
				const downUrl = `/stackUp/apply/${data.applyNo}/file/${fd.atchFileNo}/${fd.fileSn}`;
				const applicantName = data.member?.memNm || "이름 없음";
				return `
                <li class="freeDetail-freefiles d-flex align-items-center mb-2">
                    <a href="${downUrl}" target="_blank" class="text-decoration-none file-download" data-apply-no="${data.applyNo}">
                        ${applicantName}님의 이력서입니다.
                    </a>
                </li>
            `;
			}).join("");

			modalFiles.innerHTML = `
                    <ul class="list-unstyled">
                        ${fileListHtml}
                    </ul>
                `;

			// PDF 클릭 이벤트 추가
			modalFiles.querySelectorAll(".file-download").forEach(el => {
				el.addEventListener("click", function(event) {
					const applyNo = this.getAttribute("data-apply-no");
					updateAppOpenYn(applyNo); // 열람 여부 업데이트
				});
			});
		} else {
			modalFiles.textContent = "첨부된 파일이 없습니다.";
		}
	}

	// 아코디언 초기화 및 이벤트 등록
	function initializeAccordion(modal) {
		// 면접 정보
		document.getElementById("interview-tab")?.addEventListener("click", () => loadInterviewInfo(modal));

		// 테스트 정보
		document.getElementById("test-tab")?.addEventListener("click", () => loadTestInfo(modal));
	}

	// 모달 초기화 함수
	function resetModal(modal) {
		// 기본 정보 초기화
		modal.querySelector("#modalName").textContent = "-";
		modal.querySelector("#modalPhone").textContent = "-";
		modal.querySelector("#modalEmail").textContent = "-";
		modal.querySelector("#modalField").textContent = "-";
		modal.querySelector("#modalEmployTitle").textContent = "-";
		modal.querySelector("#modalStatus").textContent = "-";
		modal.querySelector("#modalDate").textContent = "-";
		modal.querySelector("#modalChangeDate").textContent = "-";
		modal.querySelector("#modalPassYn").textContent = "진행중";
		modal.querySelector("#modalPassYn").className = "badge fs-5 px-4 py-2 bg-warning text-dark";
		modal.querySelector("#modalFiles").textContent = "첨부된 파일이 없습니다.";

		// 아코디언 초기화
		modal.querySelector("#interviewAccordion").innerHTML = "";
		modal.querySelector("#testAccordion").innerHTML = "";
	}

	async function loadInterviewInfo(modal) {
		const applyNo = modal.dataset.applyNo;
		if (!applyNo) return;

		try {
			const response = await fetch(`${baseUrl}/company/apply/interviewDetail?applyNo=${applyNo}`);
			if (!response.ok) throw new Error("면접 정보 요청 실패");
			const data = await response.json();

			const interviewAccordion = document.getElementById("interviewAccordion");
			interviewAccordion.innerHTML = ""; // 기존 데이터 초기화
			let rnum = 1;

			if (!data || data.length === 0) {
				// 데이터가 없을 경우 기본 메시지 표시
				interviewAccordion.innerHTML = `
                <div class="alert alert-info text-center" role="alert">
                    면접 정보가 없습니다.
                </div>
            `;
				return;
			}

			data.forEach((apply, applyIndex) => {
				const interviews = apply.interviews || [];

				if (interviews.length === 0) {
					// 면접 정보가 없는 경우 메시지 표시
					interviewAccordion.innerHTML = `
                    <div class="alert alert-info text-center" role="alert">
                        면접 정보가 없습니다.
                    </div>
                `;
					return;
				}

				interviews.forEach((interview, interviewIndex) => {
					const evalList = interview.evalVO || [];
					const interviewId = `interview_${applyIndex}_${interviewIndex}`;
					let evalNum = 1;

					const evalDetails = evalList.map((evalItem, evalIndex) => {
						const evalId = `eval_${applyIndex}_${interviewIndex}_${evalIndex}`;
						const evalCateDetails = evalItem.evalCateVO || [];

						const evalCateHtml = evalCateDetails.map((cate) => `
                        <li class="list-group-item">
                            <strong>${cate.code?.codeNm || "항목명 없음"}:</strong> ${cate.evalCateCont || "내용 없음"} (${cate.evalCateScore || 0}점)
                        </li>
                    `).join("");

						return `
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="heading_${evalId}">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse_${evalId}" aria-expanded="false" aria-controls="collapse_${evalId}">
                                    ${evalNum++}차 평가
                                </button>
                            </h2>
                            <div id="collapse_${evalId}" class="accordion-collapse collapse" aria-labelledby="heading_${evalId}" data-bs-parent="#${interviewId}">
                                <div class="accordion-body">
                                    <ul class="list-group">
                                        ${evalCateHtml || "<li class='list-group-item'>평가 항목이 없습니다.</li>"}
                                    </ul>
                                </div>
                            </div>
                        </div>
                    `;
					}).join("");

					const interviewHtml = `
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="heading_${interviewId}">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse_${interviewId}" aria-expanded="false" aria-controls="collapse_${interviewId}">
                                ${rnum++} 차 면접 - 날짜: ${new Date(interview.intvDt).toLocaleString()}
                            </button>
                        </h2>
                        <div id="collapse_${interviewId}" class="accordion-collapse collapse" aria-labelledby="heading_${interviewId}" data-bs-parent="#interviewAccordion">
                            <div class="accordion-body">
                                <div class="accordion" id="${interviewId}">
                                    ${evalDetails || "<p>평가 데이터가 없습니다.</p>"}
                                </div>
                            </div>
                        </div>
                    </div>
                `;

					interviewAccordion.innerHTML += interviewHtml;
				});
			});
		} catch (error) {
			console.error(error);
			const interviewAccordion = document.getElementById("interviewAccordion");
			interviewAccordion.innerHTML = `
            <div class="alert alert-danger text-center" role="alert">
                면접 정보를 가져오는 중 오류가 발생했습니다.
            </div>
        `;
		}
	}

	// 테스트 정보를 로드하는 함수
	async function loadTestInfo(modal) {
		const applyNo = modal.dataset.applyNo;
		if (!applyNo) return;

		try {
			const response = await fetch(`${baseUrl}/company/apply/testDetail?applyNo=${applyNo}`);

			if (!response.ok) {
				// 404 또는 다른 오류가 발생한 경우 처리
				console.warn(`테스트 정보 요청 실패: ${response.status} - ${response.statusText}`);
				handleNoTestData(); // 정보가 없는 경우 처리
				return;
			}

			const data = await response.json();

			const testAccordion = document.getElementById("testAccordion");
			testAccordion.innerHTML = ""; // 기존 내용 초기화

			// 테스트 정보가 있는지 확인
			if (data?.test?.length) {
				data.test.forEach((testItem, index) => {
					const isActive = index === 0 ? "active" : "";

					const candidate = data.candidate.find(c => c.testNo === testItem.testNo) || {};

					const codeCd = testItem.testCd ?? "알 수 없음 코드";
					const codeNm = testItem.codeNm ?? "알 수 없음 이름";
					const candidateScore = candidate.candidateScore ?? "N/A";
					const candidateYn = candidate.candidateYn === "Y" ? "응시" : "미응시";
					const candidateCdt = candidate.candidateCdt ? formatDateTime(candidate.candidateCdt) : "N/A";

					const accordionItem = `
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="heading${codeCd}">
                            <button class="accordion-button ${isActive}" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${codeCd}" aria-expanded="${index === 0}" aria-controls="collapse${codeCd}">
                                ${codeNm}
                            </button>
                        </h2>
                        <div id="collapse${codeCd}" class="accordion-collapse collapse ${isActive ? "show" : ""}" aria-labelledby="heading${codeCd}" data-bs-parent="#testAccordion">
                            <div class="accordion-body">
                                <ul class="list-group">
                                    <li class="list-group-item"><strong>점수:</strong> ${candidateScore}</li>
                                    <li class="list-group-item"><strong>응시 여부:</strong> ${candidateYn}</li>
                                    <li class="list-group-item"><strong>응시 일시:</strong> ${candidateCdt}</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                `;
					testAccordion.innerHTML += accordionItem;
				});
			} else {
				handleNoTestData(); // 정보가 없는 경우 처리
			}
		} catch (error) {
			console.error("테스트 정보 요청 중 오류 발생:", error);
			handleNoTestData(); // 요청 오류 시 처리
		}
	}

	// 정보가 없는 경우 처리 함수
	function handleNoTestData() {
		const testAccordion = document.getElementById("testAccordion");
		testAccordion.innerHTML = `
        <div class="alert alert-info text-center" role="alert">
            테스트 정보가 없습니다.
        </div>
    `;
	}


	// APP_OPEN_YN 값을 Y로 업데이트하는 함수
	function updateAppOpenYn(applyNo) {
		fetch(`${baseUrl}/company/apply/updateOpenYn`, {
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				applyNo: applyNo,
				appOpenYn: "Y",
			}),
		})
			.then(res => {
				if (!res.ok) throw new Error("열람 여부 업데이트 실패");
				console.log(`지원번호 ${applyNo}의 APP_OPEN_YN이 'Y'로 변경되었습니다.`);
			})
			.catch(e => {
				console.log("Sending applyNo:", applyNo);
				console.error(e);
			});
	}

	function formatDate(dateString) {
		if (!dateString) return "-";
		const appDate = new Date(dateString);
		const now = new Date();
		const diffMin = Math.floor((now - appDate) / 60000);
		const diffHr = Math.floor(diffMin / 60);
		if (diffMin < 5) return "방금";
		if (diffHr < 24) return "오늘";
		return appDate.toISOString().split("T")[0]; // YYYY-MM-DD
	}

	function formatDateTime(dateString) {
		if (!dateString) return "N/A";
		const d = new Date(dateString);
		const y = d.getFullYear();
		const M = String(d.getMonth() + 1).padStart(2, "0");
		const day = String(d.getDate()).padStart(2, "0");
		const hh = String(d.getHours()).padStart(2, "0");
		const mm = String(d.getMinutes()).padStart(2, "0");
		return `${y}-${M}-${day} ${hh}:${mm}`;
	}


	function formatPhoneNumber(phone) {
		const cleaned = phone.replace(/\D/g, "");
		const match = cleaned.match(/^(\d{3})(\d{4})(\d{4})$/);
		if (match) {
			return `${match[1]}-${match[2]}-${match[3]}`;
		}
		return phone;
	}

});