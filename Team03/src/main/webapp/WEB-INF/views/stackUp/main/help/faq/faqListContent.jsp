<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/help/style.css">

<div class="d-flex justify-content-between align-items-center mb-3">
	<h3 class="mb-3">FAQ</h3>
	<div class="search-area d-flex">
		<c:choose>
			<c:when test="${faqType eq 'user'}">
				<form:select path="condition.searchType" class="form-select me-2">
					<form:option value="noticeTitle" label="제목"></form:option>
					<form:option value="noticeCont" label="본문"></form:option>
				</form:select>
			</c:when>
			<c:when test="${faqType eq 'corporation'}">
				<form:select path="condition.searchType" class="form-select me-2">
					<form:option value="noticeTitle" label="제목"></form:option>
					<form:option value="noticeCont" label="본문"></form:option>
				</form:select>
			</c:when>
		</c:choose>
		<form:input path="condition.searchWord" class="form-control me-2"
			placeholder="검색어 입력" />
		<button type="submit" class="btn btn-primary search-btn">검색</button>
	</div>
</div>

<!-- 탭 메뉴 -->
<ul class="nav nav-tabs mb-3 justify-content-center" id="faqTabs">
	<li class="nav-item col-6 text-center"><a
		href="${pageContext.request.contextPath}/help/faq/user"
		class="nav-link ${faqType eq 'user' ? 'active' : ''}">개인회원</a></li>
	<li class="nav-item col-6 text-center"><a
		href="${pageContext.request.contextPath}/help/faq/corporation"
		class="nav-link ${faqType eq 'corporation' ? 'active' : ''}">기업회원</a></li>
</ul>

<form id="searchForm"
	action="${pageContext.request.contextPath}/help/faq/${faqType}"
	method="get" style="display: none;">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input type="text" name="page" placeholder="page" />
</form>

<!-- FAQ 콘텐츠 -->
<div class="faq-container">
	<div class="accordion accordion-flush" id="faqAccordion">
		<c:forEach var="faq" items="${faqList}" varStatus="status">
			<div class="accordion-item">
				<!-- 제목 -->
				<h2 class="accordion-header" id="heading-${faq.noticeNo}">
					<button class="accordion-button collapsed" type="button"
						data-bs-toggle="collapse"
						data-bs-target="#collapse-${faq.noticeNo}"
						aria-expanded="false" aria-controls="collapse-${faq.noticeNo}">
						${faq.noticeTitle}</button>
				</h2>
				<!-- 내용 -->
				<div id="collapse-${faq.noticeNo}"
					class="accordion-collapse collapse"
					aria-labelledby="heading-${faq.noticeNo}"
					data-bs-parent="#faqAccordion">
					<div class="accordion-body">${faq.noticeCont}</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>

<!-- 페이징 영역 -->
<div class="paging-area text-center mt-4">${pagingHtml}</div>
