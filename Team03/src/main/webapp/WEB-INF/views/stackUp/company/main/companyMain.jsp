<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />


<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/company/companyMain.css">

<c:set var="activeCount" value="0" />
<c:set var="endedCount" value="0" />

<c:forEach items="${empList}" var="emp">
	<c:if test="${emp.employDday >= 0}">
		<c:set var="activeCount" value="${activeCount + 1}" />
	</c:if>
	<c:if test="${emp.employDday < 0}">
		<c:set var="endedCount" value="${endedCount + 1}" />
	</c:if>
</c:forEach>

<c:set var="ongoingProjectCount" value="0" />
<c:set var="endedProjectCount" value="0" />

<c:forEach items="${proList}" var="project">
	<c:if test="${project.projectStatus == '공고중'}">
		<c:set var="ongoingProjectCount" value="${ongoingProjectCount + 1}" />
	</c:if>
	<c:if test="${project.projectStatus == '공고종료'}">
		<c:set var="endedProjectCount" value="${endedProjectCount + 1}" />
	</c:if>
</c:forEach>


<div class="container">
	<!-- 기업 기본 정보 -->
	<div class="company-profile">
		<!-- 로고 -->
		<div>
			<c:choose>
				<c:when
					test="${not empty company.file.fileDetails and not empty company.file.fileDetails[0].streFileNm}">
					<img
						src="<c:url value='/images/memberImage/${company.file.fileDetails[0].streFileNm}' />"
						alt="기업 로고" class="company-logo img-fluid" id="logoImg">
				</c:when>
				<c:otherwise>
					<img src="<c:url value='/resources/images/basic-Image.png' />"
						alt="기본 이미지" class="company-logo img-fluid" id="logoImg">
				</c:otherwise>
			</c:choose>
		</div>

		<!-- 기업 정보 -->
		<div class="info-container">
			<table class="info-table">
				<tbody>
					<tr>
						<td class="company-label">기업 이름</td>
						<td>${company.compNm}</td>
						<td class="company-label">주소</td>
						<td>${company.compAddr1}</td>
					</tr>
					<tr>
						<td class="company-label">홈페이지</td>
						<td><a href="" target="_blank" id="homeUrl">www.example.com</a></td>
						<td class="company-label">기업 규모</td>
						<td id="empno">사원수 150명</td>
					</tr>
					<tr>
						<td class="company-label">설립년도</td>
						<td id="bDate">2010년</td>
						<td class="company-label">주요 사업 분야</td>
						<td>${company.compInd}&nbsp;${company.compJob}</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>




	<div class="stats-section">
		<!-- 첫 줄: 공고 관리, 프로젝트 관리 -->
		<div class="top-row">
			<div class="stats-card">
				<h3>공고 관리</h3>
				<div>
					<p>
						진행 중 공고: <strong>${activeCount}개</strong>
					</p>
					<p>
						종료된 공고: <strong>57개</strong>
					</p>
				</div>
				<a class="btn btn-outline-info" href="${pageContext.request.contextPath }/">공고 보기</a>
			</div>
			<div class="stats-card">
				<h3>프로젝트 관리</h3>
				<div>
					<p>
						진행 중 프로젝트: <strong>${ongoingProjectCount}개</strong>
					</p>
					<p>
						종료된 프로젝트: <strong>46개</strong>
					</p>
				</div>
				<a class="btn btn-outline-info" href="${pageContext.request.contextPath }/project">프로젝트 보기</a>
			</div>
		</div>

		<!-- 두 번째 줄: 지원자 현황 -->
		<div class="stats-card full-width-card">
			<h3>지원자 현황</h3>
			<div>
				<label for="jobSelect">채용 공고</label> <select id="jobSelect">
					<option value="">-- 공고를 선택하세요 --</option>
					<c:forEach items="${empList }" var="emp">
						<option value="${emp.employNo }">${emp.employTitle }</option>
					</c:forEach>
					<!-- 공고 ID와 제목 추가 -->
				</select>
			</div>

			<!-- 모집 분야 선택 -->
			<div>
				<label for="fieldSelect">모집 분야</label> <select id="fieldSelect">
					<option value="">-- 분야를 선택하세요 --</option>
				</select>
			</div>

			<!-- 지원자 수 표시 -->
			<div>
				<h4>
					지원자 수: <span id="applicantCount">0</span>명
				</h4>
			</div>

		</div>
	</div>


	</div>

	<br>
	<div>
		<h2>평균 연봉 비교</h2>
		<canvas id="chart2"></canvas>
	</div>
	<br> <br>
	<div>
		<h2>연도별 매출</h2>
		<canvas id="salesChart"></canvas>
	</div>


<!-- 숨김 영역: display:none 적용 -->
<div style="display: none;">
	<!-- 예시: JS에서 document.getElementById("sales")를 찾을 때 사용 -->
	<span id="sales"></span> <span id="salesP"></span>

	<!-- 설립일 표기를 위해 필요한 요소 -->
	<span id="etbDate"></span>

	<!-- 사원수 기준일 표기를 위해 필요한 요소 -->
	<span id="empnoP"></span>

	<!-- 평균 급여 표기를 위해 필요한 요소 -->
	<span id="AvgSlry"></span>

	<!-- 회사 연혁 목록을 표시하기 위해 필요한 요소 -->
	<ul id="historyTimeline"></ul>

	<!-- "더보기" / "줄이기" 버튼 -->
	<button id="toggleButton">더보기</button>

	<!-- 기업 소개글(줄바꿈 처리용) -->
	<div id="introText" data-full-text=""></div>
	<button id="toggleIntro">더보기</button>

	<!-- 리뷰 카드 / 별점 / 공고 카드 예시 (JS가 요소를 찾아가는 용도) -->
	<div class="review-card" data-review-id="999"></div>
	<div class="stars-container" data-rating="3"></div>
	<div class="position-card" data-empNo="123"></div>
</div>




<script>
		const bizno = `${company.compNum}`;
		const companyData = ${companyData};
		const fullText = `${company.compCont}`;
	    let empData = [
	        <c:forEach items="${empList}" var="emp" varStatus="empStatus">
	            {
	                "employNo": "${emp.employNo}",
	                "employTitle": "${emp.employTitle}",
	                "fieldList": [
	                    <c:forEach items="${emp.fieldList}" var="field" varStatus="fieldStatus">
	                        {
	                            "filedNo": "${field.filedNo}",
	                            "filedNm": "${field.filedNm}",
	                            "filedRegion": "${field.filedRegion}",
	                            "filedPersonnel": "${field.filedPersonnel}",
	                            "applyCount" : "${field.applyCount}"
	                        }
	                        <c:if test="${!fieldStatus.last}">,</c:if>
	                    </c:forEach>
	                ]
	            }
	            <c:if test="${!empStatus.last}">,</c:if>
	        </c:forEach>
	    ];
	    console.log(empData); // 디버깅용 출력
	</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/company/main/companyMain.js">
		
	</script>