<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="select mb-4">
	<h2 class="mb-4">지원자 관리</h2>
	<div class="d-flex justify-content-between align-items-center">
		<div class="d-flex gap-3">
			<!-- 채용공고 선택 드롭다운 -->
			<select id="employSelect" class="form-select w-auto">
				<option value="" disabled selected>채용공고 선택</option>
				<c:forEach var="employ" items="${employList}">
					<option value="${employ.employNo}"
						${employ.employNo == selectedEmployNo ? 'selected' : ''}>
						${employ.employTitle}</option>
				</c:forEach>
			</select>

			<!-- 모집 분야 선택 드롭다운 -->
			<select id="fieldSelect" class="form-select w-auto">
				<option value="" disabled selected>모집 분야 선택</option>
				<c:forEach var="field" items="${fieldList}">
					<option value="${field.filedNo}"
						${field.filedNo == selectedFieldNo ? 'selected' : ''}>
						${field.filedNm}</option>
				</c:forEach>
			</select>

			<!-- 지원자 이름 검색 -->
			<input type="text" id="searchInput" class="form-control w-auto"
				placeholder="지원자 이름 검색" />
		</div>
		<div class="d-flex gap-2">
			<button id="cancelButton" class="btn btn-secondary">취소</button>
			<button id="confirmButton" class="btn btn-primary">저장</button>
		</div>
	</div>
</div>

<!-- 칸반 보드 -->
<div id="kanbanBoard"
	class="kanban-container d-flex gap-4 overflow-x-auto">
	<p>채용공고와 모집 분야를 선택하세요.</p>
</div>

<!-- 이메일 발송 모달 -->
<div class="modal fade" id="sendEmailModal" tabindex="-1"
	aria-labelledby="sendEmailModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md custom-modal-size">
		<div class="modal-content shadow-lg">
			<!-- 헤더 -->
			<div class="modal-header bg-primary rounded-top">
				<h5 class="modal-title fw-bold text-white" id="sendEmailModalLabel">이메일
					발송</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<!-- 본문 -->
			<div class="modal-body px-4 py-3">
				<input type="hidden" id="applyNoInput" value="">

				<!-- 받는 사람 -->
				<div class="mb-3">
					<label class="form-label fw-bold fs-5">받는 사람</label>
					<div id="emailRecipientHeader"
						class="d-flex flex-wrap gap-3 p-3 rounded bg-light overflow-auto">
						<span class="text-muted">선택된 이메일이 표시됩니다.</span>
					</div>
				</div>

				<!-- 이메일 제목 -->
				<div class="mb-3">
					<label for="emailSubject" class="form-label fw-bold fs-5">제목</label>
					<input type="text" class="form-control" id="emailSubject"
						placeholder="제목을 입력하세요">
				</div>

				<!-- 이메일 내용 -->
				<div class="mb-3">
					<label for="emailBody" class="form-label fw-bold fs-5">내용</label>
					<textarea class="form-control" id="emailBody" rows="6"
						placeholder="이메일 내용을 입력하세요"></textarea>
				</div>

				<!-- 발송 결과 섹션 -->
				<div class="mt-4">
					<label class="form-label fw-bold fs-5">발송 결과</label>
					<ul class="nav nav-tabs" id="resultTabs">
						<li class="nav-item"><a class="nav-link active"
							id="success-tab" data-bs-toggle="tab" href="#success" role="tab">성공</a>
						</li>
						<li class="nav-item"><a class="nav-link" id="failed-tab"
							data-bs-toggle="tab" href="#failed" role="tab">실패</a></li>
						<li class="nav-item"><a class="nav-link" id="blocked-tab"
							data-bs-toggle="tab" href="#blocked" role="tab">수신 차단</a></li>
					</ul>
					<div class="tab-content mt-3">
						<div class="tab-pane fade show active" id="success"
							role="tabpanel">
							<ul id="emailSuccessList" class="list-unstyled"></ul>
						</div>
						<div class="tab-pane fade" id="failed" role="tabpanel">
							<ul id="emailFailedList" class="list-unstyled"></ul>
						</div>
						<div class="tab-pane fade" id="blocked" role="tabpanel">
							<ul id="emailBlockedList" class="list-unstyled"></ul>
						</div>
					</div>
				</div>
			</div>
			<!-- 푸터 -->
			<div class="modal-footer bg-light">
				<button type="button" class="btn btn-secondary btn-md px-3"
					data-bs-dismiss="modal">닫기</button>
				<button type="button" id="sendEmailButton"
					class="btn btn-primary btn-md px-3">발송</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="detailModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content shadow">
            <!-- 헤더 -->
            <div class="modal-header bg-primary">
                <h5 class="modal-title d-flex align-items-center text-white" id="detailModalLabel">지원자 상세 정보</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <!-- 본문 -->
            <div class="modal-body" style="min-height: 600px; overflow-y: auto;">
                <!-- 지원자 이름과 상태 -->
                <div class="text-center mb-4">
                    <h4 id="modalName" class="fw-bold mb-2">-</h4>
                    <span id="modalPassYn" class="badge fs-5 px-4 py-2 bg-warning text-dark">진행중</span>
                </div>

                <!-- 탭 네비게이션 -->
                <ul class="nav nav-tabs justify-content-center" id="detailTab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="basic-tab" data-bs-toggle="tab" data-bs-target="#basic" type="button" role="tab" aria-controls="basic" aria-selected="true">
                            기본 정보
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="test-tab" data-bs-toggle="tab" data-bs-target="#test" type="button" role="tab" aria-controls="test" aria-selected="false">
                            테스트 정보
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="interview-tab" data-bs-toggle="tab" data-bs-target="#interview" type="button" role="tab" aria-controls="interview" aria-selected="false">
                            면접 정보
                        </button>
                    </li>
                </ul>

                <!-- 탭 콘텐츠 -->
                <div class="tab-content mt-3" id="detailTabContent">
                    <!-- 기본정보 -->
                    <div class="tab-pane fade show active" id="basic" role="tabpanel" aria-labelledby="basic-tab">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item d-flex justify-content-between">
                                <span class="text-muted fw-bold">공고명</span>
                                <span id="modalEmployTitle">-</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span class="text-muted fw-bold">모집분야</span>
                                <span id="modalField">-</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span class="text-muted fw-bold">연락처</span>
                                <span id="modalPhone">-</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span class="text-muted fw-bold">이메일</span>
                                <span id="modalEmail">-</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span class="text-muted fw-bold">지원일시</span>
                                <span id="modalDate">-</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span class="text-muted fw-bold">현재절차</span>
                                <span id="modalStatus">-</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span class="text-muted fw-bold">절차 변경일시</span>
                                <span id="modalChangeDate">-</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span class="text-muted fw-bold">이력서</span>
                                <span id="modalFiles">-</span>
                            </li>
<!--                             <li class="list-group-item d-flex justify-content-between"> -->
<!--                                 <span class="text-muted fw-bold">이력서 열람여부</span> -->
<!--                                 <span id="modalResumeViewYn">미열람</span> -->
<!--                             </li> -->
                        </ul>
                    </div>

                    <!-- 면접 정보 -->
                    <div class="tab-pane fade" id="interview" role="tabpanel" aria-labelledby="interview-tab">
                        <div class="accordion" id="interviewAccordion">
                            <!-- 면접 정보 아코디언 동적 추가 -->
                        </div>
                    </div>

                    <!-- 테스트 정보 -->
                    <div class="tab-pane fade" id="test" role="tabpanel" aria-labelledby="test-tab">
                        <div class="accordion" id="testAccordion">
                            <!-- 테스트 정보 아코디언 동적 추가 -->
                        </div>
                    </div>
                </div>
            </div>

            <!-- 푸터 -->
            <div class="modal-footer bg-light">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>


<!-- 문자 발송 모달 -->
<div class="modal fade" id="sendSmsModal" tabindex="-1"
	aria-labelledby="sendSmsModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg custom-modal-size">
		<div class="modal-content shadow-lg">
			<!-- 헤더 -->
			<div class="modal-header bg-primary rounded-top">
				<h5 class="modal-title fw-bold text-white" id="sendSmsModalLabel">문자
					발송</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<!-- 본문 -->
			<div class="modal-body px-4 py-3">
				<input type="hidden" id="phoneNumbersInput" value="">
				<!-- 받는 사람 -->
				<div class="mb-3">
					<label class="form-label fw-bold fs-5">받는 사람</label>
					<div id="smsRecipientHeader"
						class="d-flex flex-wrap gap-3 p-3 rounded bg-light overflow-auto">
						<span class="text-muted">선택된 이름과 번호가 표시됩니다.</span>
					</div>
				</div>
				<!-- 문자 내용 -->
				<div class="mb-3">
					<label for="smsMessage" class="form-label fw-bold fs-5">내용</label>
					<textarea class="form-control fixed-size-textarea" id="smsMessage"
						maxlength="160" placeholder="보낼 문자 내용을 입력하세요. (최대 160자)"></textarea>
					<small class="form-text text-muted"> <span
						id="smsCharCount">0</span>/160 글자
					</small>
				</div>
			</div>
			<!-- 푸터 -->
			<div class="modal-footer bg-light">
				<button type="button" class="btn btn-secondary btn-md px-3"
					data-bs-dismiss="modal">닫기</button>
				<button type="button" id="sendSmsButton"
					class="btn btn-primary btn-md px-3">발송</button>
			</div>
		</div>
	</div>
</div>

<!-- 화상회의 modal -->
<div class="modal fade" id="exampleModal1" tabindex="-1" aria-labelledby="exampleModalLabel1">
	<div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
		<div class="modal-content" style="max-height: 90vh; overflow-y: auto;">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalLabel1">화상 면접방 생성</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form:form method="post" modelAttribute="vchat">
					<div class="form-group mb-3">
					    <label for="emp" class="form-label">채용 공고:</label>
					    <select name="emp" id="emp" class="form-select" required>
					        <option value="">공고 선택</option>
					        <c:forEach items="${list}" var="employ">
					            <option value="${employ.employNo}" data-code="${employ.employNo}" data-title="${employ.employTitle}">${employ.employTitle}</option>
					        </c:forEach>
					    </select>
					    <input type="hidden" id="selectedEmployCode" name="employNo" value="">
					</div>
					
					<div class="form-group mb-3">
					    <label for="filedNo" class="form-label">모집 분야:</label>
					    <select name="filedNo" id="filedNo" class="form-select" required>
					        <option value="">분야 선택</option>
					        <c:forEach items="${list}" var="employ">
					            <c:forEach items="${employ.fieldList}" var="field">
					                <option value="${field.filedNo}" class="${employ.employNo}">${field.filedNm}</option>
					            </c:forEach>
					        </c:forEach>
					    </select>
					</div>
					<div class="form-group mb-3">
						<label for="vchatTitle" class="form-label">면접 제목:</label>
						<form:input path="vchatTitle" id="vchatTitle" class="form-control" required="required" />
					</div>
					
					<div class="form-group mb-3">
						<label for="vchatRid" class="form-label">아이디:</label>
						<form:input path="vchatRid" id="vchatRid" class="form-control" required="required" />
					</div>
					
					<div class="form-group mb-3">
						<label for="vchatRpass" class="form-label">비밀번호:</label>
						<form:password path="vchatRpass" id="vchatRpass" class="form-control" required="required" />
					</div>
					
					<div class="text-end">
						<button type="submit" class="btn btn-primary" id="vchatAddBtn">생성</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
<!-- 면접 일정 모달 -->
<div class="modal fade" id="interviewScheduleModal" tabindex="-1" aria-labelledby="interviewScheduleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <!-- 헤더 -->
            <div class="modal-header bg-primary">
                <h5 class="modal-title text-white" id="interviewScheduleModalLabel">면접 일정 설정</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- 본문 -->
            
            <div class="modal-body">
                <form id="interviewScheduleForm">
                    <!-- 지원자 정보 -->
                    <div class="mb-3">
                        <label for="selectedApplicants" class="form-label fw-bold">선택된 지원자</label>
                        <div id="selectedApplicants" class="bg-light p-3 rounded">
                            <span class="text-muted">선택된 지원자가 없습니다.</span>
                        </div>
                    </div>
                    <!-- 면접 일자 -->
                    <div class="mb-3">
                        <label for="interviewDate" class="form-label fw-bold">면접 일자</label>
                        <input type="date" id="einterviewDate" class="form-control" required>
                    </div>
                    <!-- 면접 시간 -->
                    <div class="mb-3">
					    <label for="interviewTime" class="form-label fw-bold">면접 시간</label>
					    <select id="einterviewTime" class="form-control" required>
					        <!-- 옵션은 JavaScript로 동적으로 추가 -->
					    </select>
					</div>
                </form>
            </div>
            <!-- 푸터 -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" id="saveInterviewSchedule" class="btn btn-primary">저장</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="testModal" tabindex="-1" aria-labelledby="testModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <!-- 모달 헤더 -->
            <div class="modal-header bg-primary">
                <h5 class="modal-title text-white" id="testModalLabel">시험 적용</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <!-- 모달 본문 -->
            <div class="modal-body">
                <!-- 지원자 정보 -->
                <div class="mb-3">
                    <label for="selectedApplicants" class="form-label fw-bold">선택된 지원자</label>
                    <div id="selectedApplicants2" class="bg-light p-3 rounded">
                        <span class="text-muted">선택된 지원자가 없습니다.</span>
                    </div>
                </div>

                <!-- 시험 유형 선택 -->
                <div class="mb-3">
                    <label class="form-label fw-bold">시험 유형 선택</label>
                    <select id="testTypeSelect" class="form-select">
                        <option value="" disabled selected>시험 유형을 선택하세요</option>
                        <option value="TE01">인성검사</option>
                        <option value="TE02">적성검사</option>
                        <option value="TE03">입사테스트</option>
                        <option value="TE04">코딩테스트</option>
                    </select>
                </div>

                <!-- 시험 소유자 선택 -->
                <div class="mb-3">
                    <label class="form-label fw-bold">시험지 선택</label>
                    <div>
                        <input type="radio" id="adminTest" name="testOwner" value="admin" checked>
                        <label for="adminTest">스택업 제공</label>
                        <input type="radio" id="companyTest" name="testOwner" value="company">
                        <label for="companyTest">자체 시험지 </label>
                    </div>
                </div>

                <!-- 시험 이름 선택 -->
                <div class="mb-3">
                    <label class="form-label fw-bold">시험 이름 선택</label>
                    <select id="testNameSelect" class="form-select">
                        <option value="" disabled selected>시험 이름을 선택하세요</option>
                    </select>
                </div>

                <!-- 시험 시작 및 종료 날짜 -->
                <div class="row">
                    <div class="col-md-6">
                        <label class="form-label fw-bold">시험 시작일자</label>
                        <input type="date" id="startDate" class="form-control" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label fw-bold">시험 종료일자</label>
                        <input type="date" id="endDate" class="form-control" required>
                    </div>
                </div>
            </div>

            <!-- 모달 푸터 -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" id="applyTestButton">적용</button>
            </div>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/resources/ckeditor5-36.0.0/packages/ckeditor5-build-classic/build/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/apply/kanbanboard.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/apply/vchatForm.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/apply/applyTestForm.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/apply/kanbanboard.css">
