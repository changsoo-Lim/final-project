<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/requiredAsterisks.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/employ/employForm.css" />
<section class="section_employ">
	<form:form id="employForm" modelAttribute="employ" enctype="multipart/form-data">
		<div class="emp-header">
			<div>
				<c:choose>
		            <c:when test="${empty employ.employNo}">
		                <h2><strong>채용공고 등록</strong></h2>
		            </c:when>
		            <c:otherwise>
		                <h2><strong>채용공고 수정</strong></h2>
		            </c:otherwise>
		        </c:choose>
			</div>
			<div>
				<button type="button" class="btn btn-primary input-btn">데이터입력</button>
			</div>
		</div>
        <p class="fs-6"></p>

        <!-- ======= 모집 내용 ======= -->
		<div class="section">
			<h4><strong>모집 내용</strong></h4>
			<hr>

			<!-- 공고 제목 -->
			<div class="row mb-4 align-items-center">
				<label class="col-md-2 col-form-label" data-required="true">공고 제목</label>
				<div class="col-md-8">
					<form:input class="form-control" id="employTitle" path="employTitle" placeholder="공고 제목을 입력해주세요" required="required" />
				</div>
			</div>

			<!-- 경력 + 학력 -->
			<div class="row mb-4 align-items-center">
				<label class="col-md-2 col-form-label" data-required="true">경력</label>
				<div class="col-md-3 checkbox-group" id="employExperience">
					<c:forEach var="code" items="${memberTypeList}">
						<div class="checkbox-box">
							<form:checkbox path="employExperience" label="${code.codeNm}" value="${code.codeCd}" class="checkbox" />
						</div>
					</c:forEach>
					<div class="checkbox-box">
						<form:checkbox path="employExperience" label="경력무관"  value="all" class="checkbox" />
					</div>
					<input type="hidden" id="skillsHidden" />
				</div>

				<label class="col-md-1 col-form-label" data-required="true">학력</label>
				<div class="col-md-3">
					<form:select class="form-control"
					             path="employEducation"
					             required="required">
						<form:option value="">학력선택</form:option>
						<form:options items="${educationList}" itemLabel="codeNm" itemValue="codeCd"/>
					</form:select>
				</div>
			</div>

			<!-- 고용 형태 -->
            <div class="row mb-4 align-items-center">
                <label class="col-md-2 col-form-label" data-required="true">고용 형태</label>
				<div class="col-md-8 checkbox-group" id="employTypeArea" data-employtype="${employ.employType != null ? employ.employType : ''}">
					<c:forEach var="wt" items="${workTypeList}">
						<label class="checkbox-item">
							<input type="checkbox" name="employType" value="${wt.codeCd}" />
							<span>${wt.codeNm}</span>
						</label>
					</c:forEach>
				</div>
			</div>
		</div> <!-- //모집 내용 -->

		<!-- ======= 모집분야 ======= -->
		<div class="section" id="recruitFieldSection">
			<h4><strong>모집 분야</strong></h4>
			<hr>
			<div id="fieldForm">
				<input type="hidden" id="currentIndex" />

				<!-- 모집명/인원 -->
				<div class="row mb-4 align-items-center">
					<label class="col-md-2 col-form-label" data-required="true">모집명/인원</label>
					<div class="col-md-6">
						<input type="text" id="filedNm"
						       class="form-control field-required"
						       placeholder="예) 풀스택 개발자"/>
					</div>
					<div class="col-md-1">
						<input type="number" id="filedPersonnel"
						       class="form-control field-required"
						       value="0"/>
					</div>
					<div class="col-md-1">명 모집</div>
				</div>

				<!-- 근무지역 -->
				<div class="row mb-4 align-items-center">
					<label class="col-md-2 col-form-label" data-required="true">근무지역</label>
					<div class="col-md-3">
						<select id="filedRegion" class="form-control field-required">
							<option value="">선택</option>
							<c:forEach var="city" items="${cityList}">
								<option value="${city.codeCd}">${city.codeNm}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-3">
						<select id="filedRegionGungu" class="form-control field-required">
							<option value="">선택</option>
							<c:forEach var="gungu" items="${gunguList}">
								<option value="${gungu.codeCd}"
								        class="${gungu.codeParent} gungu">
									${gungu.codeNm}
								</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<!-- 담당업무, 우대조건 -->
				<div class="row mb-4 align-items-center">
					<label class="col-md-2 col-form-label" data-required="true">담당업무</label>
					<div class="col-md-6">
						<textarea id="filedJobs" class="form-control textarea field-required"
						          placeholder="담당업무를 입력해주세요.&#10;&#10;예시)&#10;- 웹 애플리케이션 개발&#10;- 비디오 콘텐츠 제작"></textarea>
					</div>
				</div>
				<div class="row mb-4 align-items-center">
					<label class="col-md-2 col-form-label" data-required="true">우대 조건</label>
					<div class="col-md-6">
						<textarea id="filedPreference" class="form-control textarea field-required"
						          placeholder="우대조건을 입력해주세요.&#10;&#10;예시)&#10;- 관련 분야 경력 3년 이상&#10;- 적극적이고 창의적인 문제 해결 능력 보유자"></textarea>
					</div>
				</div>

				<!-- ========== 필수조건 영역 ========== -->
				<div class="row mb-4 align-items-center">
					<label class="col-md-2 col-form-label">필수 조건</label>
					<div class="col-md-3">
						<select class="form-control" id="filterTitleSelect">
							<option value="">조건 선택</option>
							<c:forEach items="${filterTitleList}" var="filterTitle">
								<option value="${filterTitle.codeCd}">${filterTitle.codeNm}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-3 position-relative">
						<div class="search-bar">
							<input type="text"
							       id="filterContSearchInput"
							       class="form-control"
							       placeholder="항목 선택"/>
						</div>
						<ul id="filterCont"
						    class="filterCont-dropdown"
						    style="display:none;">
							<c:forEach var="code" items="${filterContList}">
								<li class="filterCont-item"
								    data-parent="${code.codeParent}"
								    data-value="${code.codeCd}"
								    data-text="${code.codeNm}">
									${code.codeNm}
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="row mb-4 align-items-center">
					<label class="col-md-2 col-form-label"></label>
					<div id="selectedFilters" class="col-md-6"></div>
				</div>
				<!-- //필수조건 영역 -->
				
				<!-- 채용절차 -->
				<div class="row mb-4 align-items-center">
					<label class="col-md-2 col-form-label">채용절차</label>
					<div class="col-md-6">
						<div class="processContainer" id="processContainer">
							<!-- 서류(AP02) -->
							<div class="draggable fixed row mb-4 align-items-center" data-proccd="AP02" draggable="false">
								<div class="el">서류</div>
							</div>
							<!-- 중간절차 -->
							<div class="proc-container"></div>
							<!-- 최종합격(AP08) -->
							<div class="draggable fixed row mb-4 align-items-center" data-proccd="AP08" draggable="false">
								<div class="el">최종합격</div>
							</div>
							<div class="d-grid gap-2">
								<button type="button"
								        class="btn btn-outline-primary"
								        id="openProcessModalBtn">+절차추가
								</button>
							</div>
						</div>
					</div>
				</div>

				<!-- 버튼들 -->
				<div class="row mb-4 align-items-center">
					<label class="col-md-2"></label>
					<div class="col-md-6 field-btns">
						<button type="button" class="btn btn-primary" id="addFieldBtn">추가/수정</button>
						<button type="button" class="btn btn-outline-primary" id="resetFormBtn">양식초기화</button>
					</div>
				</div>
			</div>

			<!-- (B) 하단 목록 -->
			<div class="row mb-4 align-items-center">
				<label class="col-md-2"></label>
				<ul id="addedFieldList" class="col-md-6"></ul>
			</div>
			<!-- (C) hidden inputs => fieldList[i]... -->
			<div id="hiddenInputsContainer"></div>
		</div>

		<!-- 모달: 채용절차 선택 -->
		<div class="modal fade" id="processModal" tabindex="-1" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header bg-primary">
						<h5 class="modal-title text-white">채용 절차 선택</h5>
						<button type="button" class="btn-close btn-close-white"
						        data-bs-dismiss="modal"
						        aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<ul id="processOptions">
							<!-- AP01, AP02, AP08 제외 -->
							<c:forEach var="p" items="${appsttusList}">
								<c:if test="${p.codeCd ne 'AP01' && p.codeCd ne 'AP02' && p.codeCd ne 'AP08'}">
									<li class="proc-checkbox">
										<input type="checkbox" class="procCheck checkbox"
										       id="chk_${p.codeCd}"
										       value="${p.codeCd}"/>
										<label for="chk_${p.codeCd}" class="fs-5">
											${p.codeNm}
										</label>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="confirmProcessBtn">확인</button>
						<button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>

		<!-- 근무 조건 -->
		<div class="section">
			<h4><strong>근무 조건</strong></h4>
			<hr>
			<div class="row mb-4 align-items-center">
				<label class="col-md-2 col-form-label" data-required="true">근무요일</label>
				<div class="col-md-2">
					<form:select path="employWorkday" id="employWorkday" class="form-control">
						<form:option value="" label="선택하세요"/>
						<form:options items="${workdayList}"
						             itemValue="codeCd"
						             itemLabel="codeNm"/>
					</form:select>
				</div>

				<label class="col-md-1 col-form-label" data-required="true">근무시간</label>
				<div class="col-md-1">
					<form:select path="employSwh"
					             id="employSwh"
					             class="form-control" required="required">
						<form:option value="" label="선택"/>
						<form:options items="${workHourList}"
						             itemValue="codeCd"
						             itemLabel="codeNm"/>
					</form:select>
				</div>
				~
				<div class="col-md-1">
					<form:select path="employEwh"
								 id="employEwh"
					             class="form-control" required="required">
						<form:option value="" label="선택"/>
						<form:options items="${workHourList}"
						             itemValue="codeCd"
						             itemLabel="codeNm"/>
					</form:select>
				</div>
			</div>

			<div class="row mb-4 align-items-center">
				<label class="col-md-2 col-form-label" data-required="true">급여조건</label>
				<div class="col-md-2">
					<form:select path="employSalary"
					             id="employSalary"
					             class="form-control">
						<form:option value="" label="연봉을 선택하세요"/>
						<form:options items="${salaryRangeList}"
						             itemValue="codeCd"
						             itemLabel="codeNm"/>
					</form:select>
				</div>
				<div class="col-md-2 checkbox-box">
					<form:checkbox path="employSalaryYn"
								   id="employSalaryYn"
					               label="회사내규 또는 협의"
					               value="Y"
					               class="checkbox"/>
				</div>
			</div>
		</div>

		<!-- 접수기간 및 방법 -->
		<div class="section">
			<h4><strong>접수기간 및 방법</strong></h4>
			<hr>
			<div class="row mb-4 align-items-center">
				<label class="col-md-2 col-form-label" data-required="true">모집마감일</label>
				<div class="col-md-2">
					<form:input path="employEd"
								id="employEd"
					            type="date"
					            class="form-control"
					            required="required"/>
				</div>
			</div>
			<div class="row mb-4 align-items-center">
				<label class="col-md-2 col-form-label" data-required="true">접수방법</label>
				<div class="col-md-4 checkbox-group" id="employApplicationArea" data-selected="${employ.employApplication}">
					<c:forEach items="${APLCList}" var="APLC">
						<div class="checkbox-box">
							<form:radiobutton path="employApplication"
							                  label="${APLC.codeNm}"
							                  value="${APLC.codeCd}"
							                  required="required"
							                  class="checkbox"/>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="row mb-4 align-items-center" id="employUrlContainer">
				<label class="col-md-2 col-form-label"></label>
				<div class="col-md-4">
					<input type="text" name="employUrl" id="employUrl" class="form-control"
					placeholder="URL을 입력해 주세요." />
				</div>
			</div>
		</div>

		<!-- 등록/수정 (비동기) -->
		<div class="d-grid gap-12 col-2 mx-auto submit-btn-div">
			<button type="button" id="saveBtn" class="btn btn-primary btn-lg">
				등록
			</button>
		</div>
	</form:form>
</section>
<script src="${pageContext.request.contextPath}/resources/js/employ/employForm.js"> </script>
<script src="${pageContext.request.contextPath }/resources/js/requiredAsterisks.js"></script>