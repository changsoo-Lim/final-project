<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/project/projectDetail.css" />
<input type="hidden" value="${projectVO.projectNo }" id="projectNo">
<h4>프로젝트 디테일</h4>

<div class="container">
	<!-- 상단 상태 + 제목 -->
	<div class="status-header">
		<h1>${projectVO.projectName }</h1>
		<div class="status-badges">
			<span class="badge badge-status"> ${projectVO.projectStatus }</span>
			<span class="badge badge-deadline"> ${projectVO.projectEdt }
				까지</span>
		</div>
	</div>

	<!-- 기본 정보 섹션 -->
	<div class="info-grid">
		<div class="info-item">
			<strong>필요 인력:</strong> ${projectVO.projectRecruit} 명
		</div>
		<div class="info-item">
			<strong>예상 기간:</strong> ${projectVO.projectDeadline } 개월
		</div>
		<div class="info-item">
			<strong>근무지:</strong>
			<c:choose>
				<c:when test="${projectVO.projectWfh eq 'FS02'}">재택 근무</c:when>
				<c:when test="${projectVO.projectWfh eq 'FS01'}">${projectVO.projectRegion}</c:when>
				<c:when test="${projectVO.projectWfh eq 'FS03'}">${projectVO.projectRegion} (상주, 재택 모두 가능)</c:when>
			</c:choose>
		</div>
		<div class="info-item">
			<strong>월 단가:</strong> ${projectVO.projectSalary} 만원
		</div>
	</div>

	<!-- 프로젝트 내용 섹션 -->
	<div class="project-content">
		<div class="section-title">프로젝트 내용</div>
		<div id="project-content-short">
			<pre>${fn:substring(projectVO.projectCont, 0, 50)}...</pre>
			<button id="read-more-btn">더보기</button>
		</div>
		<div id="project-content-full" style="display: none;">
			<pre>${projectVO.projectCont}</pre>
			<button id="read-less-btn">닫기</button>
		</div>
	</div>

	<!-- 참여신청 섹션 -->
	<div class="applicants-container">
		<div class="section-title">
		
			프로젝트 제안 현황:
				<c:if test="${not empty projectVO.freeOfferList[0].memId }">
					<c:out value="${fn:length(projectVO.freeOfferList)}" />
				</c:if>
				<c:if test="${empty projectVO.freeOfferList[0].memId }">
					0
				</c:if>
			명
		</div>
		<table class="applicants-list">
			<thead>
				<tr>
					<th><input type="checkbox" id="select-all" /></th>
					<th>이름</th>
					<th>상태</th>
					<th>이메일</th>
					<th>전화번호</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="offer" items="${projectVO.freeOfferList}">
					<tr>
						<td><input type="checkbox" class="select-item"
							data-id="${offer.member.memId}" <c:if test="${offer.freeOfferStatus ne '제안'}">disabled</c:if>/></td>
						<td><a href="${pageContext.request.contextPath}/talent/${offer.member.memId}/detail">${offer.member.memNm}</a></td>
						<td>${offer.freeOfferStatus}</td>
						<td>${offer.member.memEmail}</td>
						<td>${offer.member.memHp}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div style="text-align: right; margin-top: 20px;">
			<button id="cancel-proposals-btn" disabled>제안 취소</button>
		</div>
	</div>
</div>

<script
	src="${pageContext.request.contextPath }/resources/js/project/projectDetail.js"></script>