<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.Calendar"%>
<!DOCTYPE html>

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/talent/talentList.css">


<%
// 현재 연도 계산
Calendar calendar = Calendar.getInstance();
int currentYear = calendar.get(Calendar.YEAR);
request.setAttribute("currentYear", currentYear);
%>

<div class="container border mt-5">
	<div class="d-flex justify-content-between align-items-center mb-3">
		<ul class="nav nav-tabs flex-grow-1" id="filterTabs" role="tablist">
			<li class="nav-item" role="presentation">
				<button class="nav-link active" id="job-type-tab"
					data-bs-toggle="tab" data-bs-target="#job-type" type="button"
					role="tab" aria-controls="job-type" aria-selected="true">직종/직무선택</button>
			</li>
			<li class="nav-item" role="presentation">
				<button class="nav-link" id="region-tab" data-bs-toggle="tab"
					data-bs-target="#region" type="button" role="tab"
					aria-controls="region" aria-selected="false">지역선택</button>
			</li>
			<li class="nav-item" role="presentation">
				<button class="nav-link" id="skill-search-tab" data-bs-toggle="tab"
					data-bs-target="#skill-search" type="button" role="tab"
					aria-controls="skill-search" aria-selected="false">보유스킬</button>
			</li>
			<li class="nav-item" role="presentation">
				<button class="nav-link" id="lang-search-tab" data-bs-toggle="tab"
					data-bs-target="#lang-search" type="button" role="tab"
					aria-controls="lang-search" aria-selected="false">어학능력</button>
			</li>
			<li class="nav-item" role="presentation">
				<button class="nav-link" id="cer-search-tab" data-bs-toggle="tab"
					data-bs-target="#cer-search" type="button" role="tab"
					aria-controls="cer-search" aria-selected="false">국가공인 자격증</button>
			</li>
			<li class="nav-item" role="presentation">
				<button class="nav-link" id="univ-search-tab" data-bs-toggle="tab"
					data-bs-target="#univ-search" type="button" role="tab"
					aria-controls="univ-search" aria-selected="false">최종 학력</button>
			</li>
		</ul>
	</div>

	<div class="tab-content" id="filterTabsContent">
		<div class="tab-pane fade show active p-3 border" id="job-type"
			role="tabpanel" aria-labelledby="job-type-tab">
			<div class="row">
				<div class="col-md-6">
					<label for="validationCustom01" class="form-label fw-bold">직군</label>
					<select class="form-select form-control"
						aria-label="small select example" required>
						<option value="">직군를 선택해 주세요.</option>
						<c:forEach var="code" items="${codeList}">
							<c:if test="${code.codeParent == 'job'}">
								<option value="${code.codeCd}">${code.codeNm}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-6">
					<label for="validationCustom02" class="form-label fw-bold">직무</label>
					<select name="freeJobSelect" class="form-select form-control"
						required>
						<option value="">직무를 선택해 주세요.</option>
					</select>
				</div>
			</div>
		</div>

		<div class="tab-pane fade p-3 border" id="region" role="tabpanel"
			aria-labelledby="region-tab">
			<div class="row">
				<div class="col-md-6">
					<label for="regionSelect" class="form-label fw-bold">시,도</label> <select
						id="regionSelect" class="form-select form-control" required>
						<option value="">시,도를 선택해 주세요.</option>
						<c:forEach var="code" items="${codeList}">
							<c:if
								test="${not empty code.codeParent && code.codeParent eq 'city'}">
								<option value="${code.codeCd}">${code.codeNm}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-6">
					<label for="districtSelect" class="form-label fw-bold">시,
						군, 구</label> <select id="districtSelect" class="form-select form-control"
						required>
						<option value="">시, 군, 구를 선택해 주세요.</option>
					</select>
				</div>
			</div>
		</div>

		<div class="tab-pane fade p-3 border" id="skill-search"
			role="tabpanel" aria-labelledby="skill-search-tab">
			<div class="row">
				<div class="col-md-6">
					<label for="skillSelect" class="form-label fw-bold">스킬</label> <select
						id="skillSelect" class="form-select form-control" required>
						<option value="">보유 스킬을 선택 해주세요.</option>
						<c:forEach var="code" items="${codeList}">
							<c:if
								test="${code.codeParent=='SKILLTYPE' && code.codeCd.contains('sk')}">
								<option value="${code.codeCd}">${code.codeNm}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>

		<div class="tab-pane fade p-3 border" id="lang-search" role="tabpanel"
			aria-labelledby="lang-search-tab">
			<div class="row">
				<div class="col-md-6">
					<label for="langSelect" class="form-label fw-bold">어학 능력</label> <select
						id="langSelect" class="form-select form-control" required>
						<option value="">언어를 선택 해주세요.</option>
						<c:forEach var="code" items="${codeList}">
							<c:if
								test="${code.codeParent=='lang' && code.codeCd.contains('lang')}">
								<option value="${code.codeCd}">${code.codeNm}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>

		<div class="tab-pane fade p-3 border" id="cer-search" role="tabpanel"
			aria-labelledby="cer-search-tab">
			<div class="row">
				<div class="col-md-4">
					<label for="cerSelect" class="form-label fw-bold">국가 자격증</label> <select
						id="cerSelect" class="form-select form-control" required>
						<option value="">자격증을 선택 해주세요.</option>
						<c:forEach var="code" items="${codeList}">
							<c:if
								test="${code.codeParent=='certificate' && code.codeCd.contains('cer')}">
								<option value="${code.codeCd}">${code.codeNm}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-4">
					<label for="cerSelect02" class="form-label fw-bold">자격 등급</label> <select
						id="cerSelect02" class="form-select form-control" required>
						<option value="">자격 등급을 선택해 주세요.</option>
					</select>
				</div>
				<div class="col-md-4">
					<label for="cerSelect03" class="form-label fw-bold">종목</label> <select
						id="cerSelect03" class="form-select form-control" required>
						<option value="">종목을 선택해 주세요.</option>
					</select>
				</div>
			</div>
		</div>

		<div class="tab-pane fade p-3 border" id="univ-search" role="tabpanel"
			aria-labelledby="univ-search-tab">
			<div class="row">
				<div class="col-md-4">
					<label for="univSelect" class="form-label fw-bold">학력 사항</label> <select
						id="univSelect" class="form-select form-control" required>
						<option value="">학력을 선택 해주세요.</option>
						<c:forEach var="code" items="${codeList}">
							<c:if
								test="${code.codeParent=='univ-type' && code.codeCd.contains('univ-type')}">
								<option value="${code.codeCd}">${code.codeNm}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>

	</div>

	<form id="searchbtnForm" method="get"
		action="${pageContext.request.contextPath }/talent/list">
		<div class="selected-conditions mt-3">
			<h5>선택된 조건:</h5>
			<div id="conditionsContainer" class="d-flex flex-wrap gap-2"></div>
		</div>
		<button type="submit" class="btn btn-primary mt-3">검색</button>
		<button type="button" class="btn btn-secondary mt-3"
			id="resetConditions">초기화</button>
	</form>

</div>

<div class="container my-4">
    <!-- 검색 결과가 존재할 때 -->
    <c:if test="${!empty memList}">
        <!-- 카드 그리드 -->
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-2 g-3">
			<c:forEach items="${memList}" var="member">
				<div class="col">
					<div class="card shadow-sm border border-primary rounded"
						style="min-height: 200px;">
						<div class="card-body p-4" data-mem-id="${member.memId }">
							<!-- 이름 및 성별/나이 -->
							<div
								class="d-flex justify-content-between align-items-center mb-2">
								<h5 class="card-title text-primary fw-bold mb-0"
									style="font-size: 1.25rem;">${member.memNm}</h5>
								<p class="text-secondary mb-0" style="font-size: 1rem;">
									${member.memGen eq 'M' ? '남자' : '여자'} /
									${fn:substring(member.memRegno, 0, 4)}년생 ${currentYear - fn:substring(member.memRegno, 0, 4)}세
								</p>
							</div>
							<!-- 세부 정보 -->
							<div class="row row-cols-2 g-2" style="font-size: 1rem;">
								<!-- 경력사항 -->
								<div class="col">
									<strong class="text-dark">경력 : </strong>
									<c:if test="${!empty  member.careerList}">
										<c:forEach items="${member.careerList}" var="career"
											varStatus="status">
                                        ${career.careerJobTitle}
                                        <c:if test="${!status.last}">, </c:if>
										</c:forEach>
									</c:if>
									<c:if test="${empty  member.careerList}">없음</c:if>
								</div>
								<!-- 스킬 -->
								<div class="col">
									<strong class="text-dark">스킬 : </strong>
									<c:if test="${!empty member.compList}">
									<c:forEach items="${member.compList}" var="skill"
										varStatus="status">
										${skill.compSkillDetail}
										<c:if test="${!status.last}">, </c:if>
									</c:forEach>
									</c:if>
									<c:if test="${empty member.compList}">없음</c:if>
								</div>

								<!-- 자격증 -->
								<div class="col">
									<strong class="text-dark">자격증 : </strong>
									<c:if test="${!empty member.certrList}">
									<c:forEach items="${member.certrList}" var="cert"
										varStatus="status">
										${cert.certNm}
										<c:if test="${!status.last}">, </c:if>
									</c:forEach>
									</c:if>
									<c:if test="${empty member.certrList}">없음</c:if>
								</div>
								<!-- 언어 -->
								<div class="col">
									<strong class="text-dark">언어 : </strong>
									<c:if test="${!empty member.languageList}">
									<c:forEach items="${member.languageList}" var="lang"
										varStatus="status">
										${lang.langNm}
										<c:if test="${!status.last}">, </c:if>
									</c:forEach>
									</c:if>
									<c:if test="${empty member.languageList}">없음</c:if>
								</div>

								<!-- 학력사항 -->
								<div class="col">
									<strong class="text-dark">학력 : </strong>
									<c:if test="${!empty member.uniList}">
									<c:forEach items="${member.uniList}" var="uni"
										varStatus="status">
										${uni.uniType}
										<c:if test="${!status.last}">, </c:if>
									</c:forEach>
									</c:if>
									<c:if test="${empty member.uniList}">없음</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
        </div>
    </c:if>

    <!-- 검색 결과가 없을 때 -->
    <c:if test="${empty memList}">
        <div class="alert alert-warning text-center" role="alert">
            검색된 조건이 없습니다!
        </div>
    </c:if>
</div>

<div class="paging-area">${pagingHTML }</div>

<form id="searchForm">
	<input type="hidden" name="page" placeholder="page" />
</form>


<script type="text/javascript">
    const codeList = [
        <c:forEach var="code" items="${codeList}">
            {
                "codeCd": "${code.codeCd}",
                "codeNm": "${code.codeNm}",
                "codeParent": "${code.codeParent}"
            }<c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];
</script>
<script
	src="${pageContext.request.contextPath }/resources/js/talent/talentList.js"></script>
