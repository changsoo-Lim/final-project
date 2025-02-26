/**
 * 전역에서 사용할 선택된 칸반 아이템
 */
const selectedItems = new Set();

/**
 * 페이지 로드 시점 (DOMContentLoaded) 전체 초기화
 */
document.addEventListener("DOMContentLoaded", async function() {
    // ---------- 전역 설정 ----------
    const baseUrl = document.body.dataset.url || window.location.origin;

    // SweetAlert 유틸
    function sweetAlert(icon, html) {
        Swal.fire({
            position: "center-center",
            icon: icon,
            html: html
        });
    }
    // Toast 유틸
    function toastAll(icon, html) {
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
        toast.fire({ icon, html });
    }

    // DOM 요소 참조
    const employSelect   = document.getElementById("employSelect");
    const fieldSelect    = document.getElementById("fieldSelect");
    const searchInput    = document.getElementById("searchInput");
    const confirmButton  = document.getElementById("confirmButton");
    const kanbanBoard    = document.getElementById("kanbanBoard");
    const cancelButton   = document.getElementById("cancelButton");
    const container      = document.querySelector(".container-fluid");

    // 드래그/드롭 상태
    let draggedItems = [];
    const updatedStatuses = new Map(); 
    const selectAllState  = new Map();  

    // CKEditor 초기화
    try {
        window.editor = await ClassicEditor.create(document.querySelector('#emailBody'));
    } catch (e) {
        console.error("CKEditor 오류:", e);
    }

    // 컨테이너 클래스 제거(디자인 등)
    if (container) container.removeAttribute("class");

    // ---------- 면접 일정 이벤트 ----------
    // (이벤트 위임) body에서 add-interview-button 클릭 시
    document.body.addEventListener("click", function(event) {
        if (event.target && event.target.classList.contains("add-interview-button")) {
            const selectedItemsArray = Array.from(selectedItems);
            if (selectedItemsArray.length === 0) {
                sweetAlert("error", "면접 일정을 설정할 지원자를 선택하세요.");
                return;
            }
            // 선택된 지원자 목록 표시
            displaySelectedApplicants("#selectedApplicants", selectedItemsArray);

            // 모달 열기 전 입력 필드 초기화
            document.getElementById("einterviewDate").value = "";
            document.getElementById("einterviewTime").value = "";

            const interviewModal = new bootstrap.Modal(document.getElementById("interviewScheduleModal"));
            interviewModal.show();
        }
    });

    // 면접 시간 옵션 생성 (00:00 ~ 23:30, 30분 단위)
    const timeSelect = document.getElementById("einterviewTime");
    for (let hour = 0; hour < 24; hour++) {
        for (let minute = 0; minute < 60; minute += 30) {
            const hourStr = String(hour).padStart(2, "0");
            const minuteStr = String(minute).padStart(2, "0");
            const opt = document.createElement("option");
            opt.value = `${hourStr}:${minuteStr}`;
            opt.textContent = `${hourStr}:${minuteStr}`;
            timeSelect.appendChild(opt);
        }
    }

    // 면접 일정 저장
    const saveInterviewButton = document.getElementById("saveInterviewSchedule");
    if (saveInterviewButton) {
        saveInterviewButton.addEventListener("click", function() {
            const interviewDateField = document.getElementById("einterviewDate");
            const interviewDate = interviewDateField.value;
            const interviewTime = timeSelect.value;

            if (!interviewDate || !interviewTime) {
                Swal.fire({ icon: "error", title: "오류", text: "면접 일자와 시간을 입력하세요." });
                return;
            }

            const interviewDatetime = `${interviewDate}T${interviewTime}`;
            const selectedItemsArray = Array.from(selectedItems);
            const interviewData = selectedItemsArray.map(item => ({
                applyNo: item.dataset.id,
                intvDt: interviewDatetime,
                intvYn: "N",
            }));

            // 백엔드에 저장
            fetch(`${baseUrl}/company/apply/interview`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(interviewData),
            })
            .then(response => response.json())
            .then(result => {
                if (result.status === "success") {
                    Swal.fire({ icon: "success", title: "성공", text: "면접 일정이 저장되었습니다." });

                    // UI 업데이트
                    selectedItemsArray.forEach(item => {
                        const el = item.querySelector(".interview-schedule");
                        if (el) el.textContent = formatDateTime(interviewDatetime);
                    });

                    const interviewModal = bootstrap.Modal.getInstance(document.getElementById("interviewScheduleModal"));
                    interviewModal.hide();
                } else {
                    throw new Error(result.message || "면접 일정 저장 실패");
                }
            })
            .catch(error => {
                console.error("면접 일정 저장 실패:", error);
                Swal.fire({ icon: "error", title: "오류", text: "면접 일정 저장 중 오류가 발생했습니다." });
            });
        });
    }

    // ---------- 채용공고 & 모집분야 로드 ----------
    fetch(`${baseUrl}/company/apply/employ`)
        .then(res => res.json())
        .then(employList => {
            // 초기화
            employSelect.innerHTML = `<option value="" disabled selected>채용공고 선택</option>`;
            const existingOptions = new Set();
            employList.forEach(e => {
                if (!existingOptions.has(e.employNo)) {
                    const opt = document.createElement("option");
                    opt.value = e.employNo;
                    opt.textContent = e.employTitle;
                    employSelect.appendChild(opt);
                    existingOptions.add(e.employNo);
                }
            });
        })
        .catch(err => console.error("채용공고 목록 로드 실패:", err));

    employSelect.addEventListener("change", function() {
        const employNo = this.value;
        kanbanBoard.innerHTML = ""; // 칸반 초기화
        fieldSelect.innerHTML = `<option value="" disabled selected>모집 분야 선택</option>`;
        if (!employNo) return;

        // 모집분야 로드
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

    fieldSelect.addEventListener("change", function() {
        const filedNo = this.value;
        loadKanbanData(filedNo);
    });

    // ---------- 칸반 데이터 로드 및 렌더 ----------
    let originalKanbanData = null; // 초기 상태 저장

    async function loadKanbanData(filedNo) {
        try {
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

            // 칸반 초기화
            kanbanBoard.innerHTML = "";
            selectedItems.clear();
            updatedStatuses.clear();

            renderKanbanBoard(statusList, applicants);
            attachGlobalEvents();
        } catch (err) {
            console.error("칸반 데이터 로드 실패:", err);
        }
    }

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
            kanbanBoard.innerHTML = "";
            selectedItems.clear();
            updatedStatuses.clear();

            renderKanbanBoard(statusList, applicants);
            toastAll("info", "변경 사항이 초기화되었습니다.");
        });
    }

    function renderKanbanBoard(statusList, applicants) {
        // 1) applicants를 status별로 그룹화
        const grouped = {};
        applicants.forEach(app => {
            const st = app.appProcStatus || "UNDEF";
            if (!grouped[st]) grouped[st] = [];
            grouped[st].push(app);
        });

        // 2) 각 상태별 컬럼 생성
        statusList.forEach(st => {
            const column   = document.createElement("div");
            column.className = "kanban-column card flex-shrink-0";
            column.dataset.status = st.procedureCd;

            const header   = document.createElement("div");
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

            // 액션 영역 (불합격, 문자, 메일 등)
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

            // 3) 아이템들 붙이기
            const appList = grouped[st.procedureCd] || [];
            appList.forEach(app => {
                const item = document.createElement("div");
                item.className = "kanban-item card mb-1 p-3";

                // 불합격
                if (app.appPassYn === "N") {
                    item.classList.add("rejected");
                }
                // 지원 취소
                if (app.appCancelYn === "Y") {
                    item.classList.add("canceled");
                    item.style.opacity = "0.5";
                    item.style.pointerEvents = "none";
                } else {
                    item.draggable = true; 
                }

                // data-* 속성
                item.dataset.id    = app.applyNo;
                item.dataset.phone = app.member?.memHp || "";
                item.dataset.email = app.member?.memEmail || "";
                item.dataset.name  = app.member?.memNm || "";

                item.innerHTML = `
                    <div class="d-flex justify-content-between">
                        <p class="mb-2"><strong>${app.member?.memNm || "-"}</strong></p>
                        <p class="mb-2 text-end">${formatDate(app.appDate)}</p>
                    </div>
                    <div class="d-flex justify-content-between">
                        <p class="app-status ${app.appProcStatus === 'AP08' ? 'text-primary' : ''}">
                            ${app.appCancelYn === "Y" ? "지원 취소" 
                             : app.appPassYn === "N" ? "불합격" 
                             : st.codeNm}
                        </p>
                        <p class="interview-schedule text-muted" data-id="${app.applyNo}">
                            ${app.intvDt ? formatDateTime(app.intvDt) : ""}
                        </p>
                    </div>
                `;
                // 이벤트 바인딩
                if (app.appCancelYn !== "Y") attachItemEvents(item);
                itemsContainer.appendChild(item);
            });

            kanbanBoard.appendChild(column);
        });

        // 칼럼 관련 이벤트
        attachColumnEvents();
        updateColumnCounts();
    }

    // ---------- 아이템/컬럼 이벤트 ----------
    function attachItemEvents(item) {
        item.addEventListener("click", function(e) {
            const column = item.closest(".kanban-column");

            if (e.ctrlKey || e.metaKey) {
                // 다중 선택
                if (selectedItems.has(item)) {
                    selectedItems.delete(item);
                    item.classList.remove("selected");
                } else {
                    selectedItems.add(item);
                    item.classList.add("selected");
                }
                updateActionVisibility(column);
            } else {
                // 단일 클릭 → 상세보기
                showApplicantDetails(item.dataset.id);
                item.scrollIntoView({ behavior: "smooth", block: "center", inline: "center" });
            }
        });

        // 드래그 시작
        item.addEventListener("dragstart", function(e) {
            const status = item.closest(".kanban-column")?.dataset.status;
            if (status === "AP08") {
                e.preventDefault();
                sweetAlert("error", "최종합격 상태에서는 이동할 수 없습니다.");
                return;
            }
            // 이미 선택된 항목 중 .rejected는 제외
            draggedItems = Array.from(selectedItems).filter(si => !si.classList.contains("rejected"));

            // 현재 item이 selected가 아니면 추가
            if (!draggedItems.includes(item)) {
                draggedItems.push(item);
            }
            if (item.classList.contains("rejected")) {
                e.preventDefault();
                sweetAlert("error", "불합격 상태의 지원자는 이동할 수 없습니다.");
                return;
            }
            draggedItems.forEach(d => d.classList.add("dragging"));
        });

        // 드래그 종료
        item.addEventListener("dragend", function() {
            draggedItems.forEach(d => d.classList.remove("dragging"));
            draggedItems = [];
        });
    }

    function attachColumnEvents() {
        const columns = document.querySelectorAll(".kanban-column");
        columns.forEach(col => {
            // 드래그 오버
            col.addEventListener("dragover", function(e) {
                e.preventDefault();
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
                    // 동일 컬럼이면 무시
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

            // 전체선택 버튼
            const selectAllBtn = col.querySelector(".select-all-button");
            if (selectAllBtn) {
                selectAllBtn.addEventListener("click", function() {
                    const items = col.querySelectorAll(".kanban-item:not(.canceled)");
                    const isAllSelected = selectAllState.get(col) || false;

                    if (!isAllSelected) {
                        items.forEach(i => {
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

    // ---------- 전역 이벤트 (검색, 상태 저장, 불합격 처리, 문자/메일) ----------
    function attachGlobalEvents() {
        // 검색
        searchInput.addEventListener("input", function() {
            const query = this.value.trim().toLowerCase();
            const items = document.querySelectorAll(".kanban-item");
            let found = false;
            items.forEach(item => {
                const name = (item.dataset.name || "").toLowerCase();
                if (name.includes(query)) {
                    item.style.display = "block";
                    item.classList.add("highlight");
                    if (!found) {
                        item.scrollIntoView({ behavior: "smooth", block: "center" });
                        found = true;
                    }
                } else {
                    item.style.display = "none";
                    item.classList.remove("highlight");
                }
            });
        });

        // 저장 (상태 업데이트)
        confirmButton.addEventListener("click", async function() {
            if (updatedStatuses.size === 0) {
                sweetAlert("error", "변경된 항목이 없습니다.");
                return;
            }
            const updates = Array.from(updatedStatuses).map(([applyNo, newStatus]) => ({ applyNo, newStatus }));

            try {
                const res = await fetch(`${baseUrl}/company/apply/updateStatusBatch`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(updates),
                });
                if (!res.ok) throw new Error("상태 업데이트 실패");

                const updatedApplicantsRes = await fetch(`${baseUrl}/company/apply?filedNo=${fieldSelect.value}`);
                const updatedApplicants = await updatedApplicantsRes.json();

                // 성공
                updatedStatuses.clear();
                originalKanbanData = { 
                    statusList: originalKanbanData.statusList, 
                    applicants: updatedApplicants 
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

        // 불합격 처리
        document.querySelectorAll(".reject-button").forEach(btn => {
            btn.addEventListener("click", function() {
                const arr = Array.from(selectedItems);
                if (arr.length === 0) {
                    sweetAlert("error", "불합격 처리할 지원자를 선택하세요.");
                    return;
                }
                // 최종합격 상태 항목?
                const finalApproved = arr.filter(item => {
                    const status = item.closest(".kanban-column")?.dataset.status;
                    return status === "AP08";
                });
                if (finalApproved.length > 0) {
                    sweetAlert("error", "최종합격 상태의 지원자는 불합격 처리할 수 없습니다.");
                    return;
                }
                // 이미 불합격?
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

                Swal.fire({
                    title: "불합격 처리",
                    html: "불합격 처리 후 원상태로 되돌릴 수 없습니다.<br>계속하시겠습니까?",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonText: "예",
                    cancelButtonText: "아니요",
                    reverseButtons: true,
                }).then(result => {
                    if (!result.isConfirmed) {
                        toastAll("info", "불합격 처리가 취소되었습니다.");
                        return;
                    }
                    // 실제 처리
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
                        // UI 업데이트
                        valid.forEach(item => {
                            item.classList.add("rejected");
                            item.classList.remove("selected");
                            const appStatus = item.querySelector(".app-status");
                            if (appStatus) {
                                appStatus.textContent = "불합격";
                                appStatus.classList.remove("text-primary");
                            }
                        });
                        selectedItems.clear();
                        document.querySelectorAll(".kanban-item.selected").forEach(item => item.classList.remove("selected"));
                        document.querySelectorAll(".kanban-actions").forEach(a => a.classList.add("hidden"));
                        document.querySelectorAll("#selectedCount").forEach(el => el.textContent = "0");
                        toastAll("success", "불합격 처리가 완료되었습니다.");
                    })
                    .catch(e => {
                        console.error(e);
                        sweetAlert("error", "불합격 처리 실패");
                    });
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
                // UI
                document.getElementById("smsRecipientHeader").innerHTML =
                    phoneDetails.map(d => `<span class="badge bg-secondary me-1">${d}</span>`).join("");
                document.getElementById("phoneNumbersInput").value = JSON.stringify(phoneNumbers);

                const smsModal = new bootstrap.Modal(document.getElementById("sendSmsModal"));
                smsModal.show();
            });
        });

        // 문자 모달: 글자수 카운트
        const smsMessage = document.getElementById("smsMessage");
        const smsCharCount = document.getElementById("smsCharCount");
        if (smsMessage && smsCharCount) {
            smsMessage.addEventListener("input", () => smsCharCount.textContent = smsMessage.value.length);
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
                        toastAll("success", "문자가 성공적으로 발송되었습니다.");
                    } else {
                        sweetAlert("error", result.message || "문자 발송 실패");
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
                    } else {
                        console.error("수신 차단자 목록 조회 실패");
                    }
                } catch (err) {
                    console.error("수신 차단자 조회 중 오류:", err);
                }
                // 받는 사람 / 차단 목록
                document.getElementById("emailRecipientHeader").innerHTML =
                    recipientList.length
                        ? recipientList.map(r => `<span class="badge bg-secondary me-1">${r}</span>`).join("")
                        : '<span class="text-muted">선택된 이메일이 표시되지 않았습니다.</span>';

                document.getElementById("emailBlockedList").innerHTML =
                    blockedList.length
                        ? blockedList.map(r => `<li>${r.memNm} (${r.memEmail})</li>`).join("")
                        : '';

                document.getElementById("applyNoInput").value = JSON.stringify(applyNos);

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
                        method: "POST",
                        headers: { "Content-Type": "application/json; charset=UTF-8" },
                        body: JSON.stringify({ applyNos, subject, body: editorData })
                    });
                    const result = await resp.json();
                    if (resp.ok) {
                        // 발송 결과
                        const successList = result.successEmails || [];
                        const failedList  = result.failedEmails || [];
                        const blockedList = result.blockedApplicants || [];

                        document.getElementById("emailSuccessList").innerHTML =
                            successList.length
                                ? successList.map(email => `<li>${email}</li>`).join("")
                                : '<li class="text-muted">발송 성공한 이메일이 없습니다.</li>';

                        document.getElementById("emailFailedList").innerHTML =
                            failedList.length
                                ? failedList.map(email => `<li>${email}</li>`).join("")
                                : '<li class="text-muted">발송 실패한 이메일이 없습니다.</li>';

                        document.getElementById("emailBlockedList").innerHTML =
                            blockedList.length
                                ? blockedList.map(a => `<li>${a}</li>`).join("")
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

    // ---------- 메일 모달 닫힐 때 초기화 ----------
    const mailModalElement = document.getElementById("sendEmailModal");
    if (mailModalElement) {
        mailModalElement.addEventListener("hidden.bs.modal", function() {
            document.getElementById("emailSubject").value = "";
            editor?.setData("");
            document.getElementById("emailRecipientHeader").innerHTML = '<span class="text-muted">선택된 이메일이 표시되지 않았습니다.</span>';
            document.getElementById("emailBlockedList").innerHTML = '<li class="text-muted">수신 차단된 사용자가 없습니다.</li>';
            document.getElementById("emailSuccessList").innerHTML = "";
            document.getElementById("emailFailedList").innerHTML = "";
            document.getElementById("applyNoInput").value = "";
        });
    }

    // ---------- 유틸 함수들 ----------
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

    async function showApplicantDetails(applyNo) {
        try {
            const response = await fetch(`${baseUrl}/company/apply/detail?applyNo=${applyNo}`);
            if (!response.ok) throw new Error("기본 정보 요청 실패");
            const data = await response.json();

            const modal = document.getElementById("detailModal");
            modal.dataset.applyNo = applyNo;
            updateBasicInfo(modal, data);
            initializeAccordion(modal);

            modal.addEventListener("hidden.bs.modal", () => resetModal(modal));

            const bsModal = new bootstrap.Modal(modal);
            bsModal.show();
        } catch (error) {
            console.error(error);
            sweetAlert("error", "상세 정보를 가져오는 중 오류가 발생했습니다.");
        }
    }

    function updateBasicInfo(modal, data) {
        modal.querySelector("#modalName").textContent        = data.member?.memNm || "-";
        modal.querySelector("#modalPhone").textContent       = formatPhoneNumber(data.member?.memHp || "-");
        modal.querySelector("#modalEmail").textContent       = data.member?.memEmail || "-";
        modal.querySelector("#modalField").textContent       = data.field ? data.field.map(f => f.filedNm).join(", ") : "-";
        modal.querySelector("#modalEmployTitle").textContent = data.employ?.employTitle || "-";
        modal.querySelector("#modalStatus").textContent      = data.codeNm || "-";
        modal.querySelector("#modalDate").textContent        = formatDateTime(data.appDate);
        modal.querySelector("#modalChangeDate").textContent  = data.appProcChangeDt ? formatDateTime(data.appProcChangeDt) : "N/A";

        const passYnEl = modal.querySelector("#modalPassYn");
        passYnEl.textContent = data.appPassYn === "Y" ? "합격" : data.appPassYn === "N" ? "불합격" : "진행중";
        passYnEl.className =
            data.appPassYn === "Y" ? "badge fs-5 px-4 py-2 bg-success text-white"
          : data.appPassYn === "N" ? "badge fs-5 px-4 py-2 bg-danger text-white"
          : "badge fs-5 px-4 py-2 bg-warning text-dark";

        const modalFiles = modal.querySelector("#modalFiles");
        if (data.file?.fileDetails?.length > 0) {
            const fileListHtml = data.file.fileDetails.map(fd => {
                const downUrl = `/stackUp/apply/${data.applyNo}/file/${fd.atchFileNo}/${fd.fileSn}`;
                const nm = data.member?.memNm || "이름 없음";
                return `
                    <li class="freeDetail-freefiles d-flex align-items-center mb-2">
                        <a href="${downUrl}" target="_blank" class="text-decoration-none file-download" data-apply-no="${data.applyNo}">
                            ${nm}님의 이력서입니다.
                        </a>
                    </li>
                `;
            }).join("");
            modalFiles.innerHTML = `<ul class="list-unstyled">${fileListHtml}</ul>`;

            modalFiles.querySelectorAll(".file-download").forEach(el => {
                el.addEventListener("click", function() {
                    updateAppOpenYn(this.dataset.applyNo);
                });
            });
        } else {
            modalFiles.textContent = "첨부된 파일이 없습니다.";
        }
    }

    function initializeAccordion(modal) {
        document.getElementById("interview-tab")?.addEventListener("click", () => loadInterviewInfo(modal));
        document.getElementById("test-tab")?.addEventListener("click", () => loadTestInfo(modal));
    }

    function resetModal(modal) {
        modal.querySelector("#modalName").textContent        = "-";
        modal.querySelector("#modalPhone").textContent       = "-";
        modal.querySelector("#modalEmail").textContent       = "-";
        modal.querySelector("#modalField").textContent       = "-";
        modal.querySelector("#modalEmployTitle").textContent = "-";
        modal.querySelector("#modalStatus").textContent      = "-";
        modal.querySelector("#modalDate").textContent        = "-";
        modal.querySelector("#modalChangeDate").textContent  = "-";
        modal.querySelector("#modalPassYn").textContent      = "진행중";
        modal.querySelector("#modalPassYn").className        = "badge fs-5 px-4 py-2 bg-warning text-dark";
        modal.querySelector("#modalFiles").textContent       = "첨부된 파일이 없습니다.";

        modal.querySelector("#interviewAccordion").innerHTML = "";
        modal.querySelector("#testAccordion").innerHTML      = "";
    }

    async function loadInterviewInfo(modal) {
        const applyNo = modal.dataset.applyNo;
        if (!applyNo) return;
        try {
            const response = await fetch(`${baseUrl}/company/apply/interviewDetail?applyNo=${applyNo}`);
            if (!response.ok) throw new Error("면접 정보 요청 실패");
            const data = await response.json();

            const interviewAccordion = document.getElementById("interviewAccordion");
            interviewAccordion.innerHTML = "";
            let rnum = 1;

            if (!data || data.length === 0) {
                interviewAccordion.innerHTML = `
                    <div class="alert alert-info text-center" role="alert">
                        면접 정보가 없습니다.
                    </div>
                `;
                return;
            }

            data.forEach((apply, idx) => {
                const interviews = apply.interviews || [];
                if (interviews.length === 0) {
                    interviewAccordion.innerHTML = `
                        <div class="alert alert-info text-center" role="alert">
                            면접 정보가 없습니다.
                        </div>
                    `;
                    return;
                }

                interviews.forEach((interview, ix) => {
                    const evalList  = interview.evalVO || [];
                    const interviewId = `interview_${idx}_${ix}`;
                    let evalNum = 1;

                    const evalDetails = evalList.map((evalItem, eIx) => {
                        const evalId = `eval_${idx}_${ix}_${eIx}`;
                        const evalCateDetails = evalItem.evalCateVO || [];
                        const evalCateHtml = evalCateDetails.map(cate => `
                            <li class="list-group-item">
                                <strong>${cate.code?.codeNm || "항목명 없음"}:</strong> 
                                ${cate.evalCateCont || "내용 없음"} 
                                (${cate.evalCateScore || 0}점)
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
            document.getElementById("interviewAccordion").innerHTML = `
                <div class="alert alert-danger text-center" role="alert">
                    면접 정보를 가져오는 중 오류가 발생했습니다.
                </div>
            `;
        }
    }

    async function loadTestInfo(modal) {
        const applyNo = modal.dataset.applyNo;
        if (!applyNo) return;
        try {
            const response = await fetch(`${baseUrl}/company/apply/testDetail?applyNo=${applyNo}`);
            if (!response.ok) {
                console.warn(`테스트 정보 요청 실패: ${response.status} - ${response.statusText}`);
                handleNoTestData(); 
                return;
            }
            const data = await response.json();
            const testAccordion = document.getElementById("testAccordion");
            testAccordion.innerHTML = "";

            if (data?.test?.length) {
                data.test.forEach((testItem, idx) => {
                    const isActive    = idx === 0 ? "active" : "";
                    const candidate   = data.candidate.find(c => c.testNo === testItem.testNo) || {};
                    const codeCd      = testItem.testCd  ?? "알 수 없음 코드";
                    const codeNm      = testItem.codeNm  ?? "알 수 없음 이름";
                    const candidateScore = candidate.candidateScore ?? "N/A";
                    const candidateYn    = candidate.candidateYn === "Y" ? "응시" : "미응시";
                    const candidateCdt   = candidate.candidateCdt ? formatDateTime(candidate.candidateCdt) : "N/A";

                    const accordionItem = `
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="heading${codeCd}">
                                <button class="accordion-button ${isActive}" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${codeCd}" aria-expanded="${idx === 0}" aria-controls="collapse${codeCd}">
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
                handleNoTestData();
            }
        } catch (error) {
            console.error("테스트 정보 요청 중 오류 발생:", error);
            handleNoTestData();
        }
    }

    function handleNoTestData() {
        const testAccordion = document.getElementById("testAccordion");
        testAccordion.innerHTML = `
            <div class="alert alert-info text-center" role="alert">
                테스트 정보가 없습니다.
            </div>
        `;
    }

    function updateAppOpenYn(applyNo) {
        fetch(`${baseUrl}/company/apply/updateOpenYn`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ applyNo: applyNo, appOpenYn: "Y" })
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

    // 날짜 포맷
    function formatDate(dateString) {
        if (!dateString) return "-";
        const appDate = new Date(dateString);
        const now = new Date();
        const diffMin = Math.floor((now - appDate) / 60000);
        const diffHr  = Math.floor(diffMin / 60);
        if (diffMin < 5) return "방금";
        if (diffHr < 24) return "오늘";
        return appDate.toISOString().split("T")[0]; // YYYY-MM-DD
    }

    function formatDateTime(dateString) {
        if (!dateString) return "N/A";
        const d  = new Date(dateString);
        const y  = d.getFullYear();
        const M  = String(d.getMonth() + 1).padStart(2, "0");
        const day= String(d.getDate()).padStart(2, "0");
        const hh = String(d.getHours()).padStart(2, "0");
        const mm = String(d.getMinutes()).padStart(2, "0");
        return `${y}-${M}-${day} ${hh}:${mm}`;
    }

    function formatPhoneNumber(phone) {
        const cleaned = phone.replace(/\D/g, "");
        const match = cleaned.match(/^(\d{3})(\d{4})(\d{4})$/);
        return match ? `${match[1]}-${match[2]}-${match[3]}` : phone;
    }

    // 선택된 지원자 목록 표시 (면접/테스트 등에 재활용 가능)
    function displaySelectedApplicants(containerSelector, items) {
        const container = document.querySelector(containerSelector);
        if (!container) return;

        const names = items.map(item => item.dataset.name || "이름 없음");
        container.innerHTML = names.map(nm => `<div class="badge bg-secondary me-1">${nm}</div>`).join("");
    }

});
