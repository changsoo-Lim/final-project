<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/freelancer/memberProjectDetail.css" />
<h4>프로젝트 디테일</h4>
<div class="freelancer-layout">
	<div class="freelancer-project-container">
		<!-- 상단 상태 + 제목 -->
		<div class="freelancer-status-header">
			<h1>${projectVO.projectName }</h1>
			<div class="freelancer-status-badges">
				<span class="freelancer-badge freelancer-badge-status">
					${projectVO.projectStatus }</span> <span
					class="freelancer-badge freelancer-badge-deadline">
					${projectVO.projectEdt } 까지</span>
			</div>
		</div>

		<!-- 기본 정보 섹션 -->
		<div class="freelancer-info-grid">
			<div class="freelancer-info-item">
				<strong>필요 인력</strong> ${projectVO.projectRecruit} 명
			</div>
			<div class="freelancer-info-item">
				<strong>예상 기간</strong> ${projectVO.projectDeadline } 개월
			</div>
			<div class="freelancer-info-item">
				<strong>근무지</strong>
				<c:choose>
					<c:when test="${projectVO.projectWfh eq 'FS02'}"> 재택 근무 </c:when>
					<c:when test="${projectVO.projectWfh eq 'FS01'}"> ${projectVO.projectRegion} </c:when>
					<c:when test="${projectVO.projectWfh eq 'FS03'}"> ${projectVO.projectRegion} (상주, 재택 모두 가능) </c:when>
				</c:choose>
			</div>
			<div class="freelancer-info-item">
				<strong>월 단가</strong> ${projectVO.projectSalary} 만원
			</div>
		</div>

		<!-- 프로젝트 내용 섹션 -->
		<div class="freelancer-project-content">
			<div class="freelancer-section-title">프로젝트 내용</div>
			<ol>
				<li>프로젝트명 : ${projectVO.projectName}</li>
				<li>담당역할 및 업무범위 : ${projectVO.projectJob}</li>
				<li>필요 스킬
					<ol>
						<c:forEach var="skill"
							items="${fn:split(projectVO.projectSkills, ',')}"
							varStatus="status">
							<li>${fn:trim(skill)}</li>
						</c:forEach>
					</ol>
				</li>
				<li>필요인력: ${projectVO.projectRecruit} 명</li>
				<li>근무지: <c:choose>
						<c:when test="${projectVO.projectWfh eq 'FS02'}"> 재택 근무 </c:when>
						<c:when test="${projectVO.projectWfh eq 'FS01'}"> ${projectVO.projectRegion} </c:when>
						<c:when test="${projectVO.projectWfh eq 'FS03'}"> ${projectVO.projectRegion} (상주, 재택 모두 가능) </c:when>
					</c:choose>
				</li>
				<li>프로젝트 기간 : ${projectVO.projectDeadline} 개월
					(${projectVO.projectSdt} ~ ${projectVO.projectEdt})</li>
				<li>월 단가 : ${projectVO.projectSalary} 만원</li>
				<li>상세 내용</li>
			</ol>
			<pre>${projectVO.projectCont}</pre>
		</div>
	</div>

	<div class="freelancer-company-container">
		<h4>기업 정보</h4>

		<div class="company-logo">
			<c:choose>
				<c:when
					test="${not empty company.file.fileDetails and not empty company.file.fileDetails[0].streFileNm}">
					<img
						src="<c:url value='/images/memberImage/${company.file.fileDetails[0].streFileNm}' />"
						alt="이미지 설명" class="img-fluid rounded" id="logoImg">
				</c:when>
				<c:otherwise>
					<img src="<c:url value='/resources/images/basic-Image.png' />"
						alt="기본 이미지" class="img-fluid rounded" id="logoImg">
				</c:otherwise>
			</c:choose>
		</div>
		<p>
			<strong>기업명</strong> <br>${company.compNm }
		</p>
		<p>
			<strong>주소</strong> <br>${company.compAddr1 }
		</p>
		<p>
			<strong>상세 주소</strong> <br>${company.compAddr2 }
		</p>
		<p>
			<strong>업종</strong> <br>${company.compInd }
		</p>
		<p>
			<strong>담당자 이메일</strong> <br>${company.compEmail }
		</p>

		<p>
			<strong>담당자 전화번호</strong> <br>${company.compMobile }
		</p>

		<h4>지원 및 제안</h4>
		<div class="proposal-status">
			<c:choose>
				<c:when test="${freeOffer.freeOfferStatus == 'OF02'}">
					<div class="proposal-status-text accepted-text"
						style="text-align: center; margin: 0 auto; width: fit-content;">
						수락한 제안입니다</div>
				</c:when>
				<c:when test="${freeOffer.freeOfferStatus == 'OF03'}">
					<div class="proposal-status-text rejected-text"
						style="text-align: center; margin: 0 auto; width: fit-content;">
						거절한 제안입니다</div>
				</c:when>
				<c:when test="${projectVO.projectStatus  == '공고종료'}">
					<div class="proposal-status-text rejected-text"
						style="text-align: center; margin: 0 auto; width: fit-content;">
						종료된 공고 입니다</div>
				</c:when>
				<c:otherwise>
					<button class="accept-button" id="acceptBtn"
						data-project-id="${projectVO.projectNo}">제안 수락</button>
					<button class="reject-button" id="rejectBtn"
						data-project-id="${projectVO.projectNo}">거절</button>
				</c:otherwise>
			</c:choose>
		</div>

	</div>
</div>

<script
	src="${pageContext.request.contextPath }/resources/js/freelancer/memberProjectDetail.js"></script>