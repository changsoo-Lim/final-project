<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/help/style.css">

<div class="d-flex justify-content-between align-items-center mb-3">
	<h3 class="mb-3">문의하기</h3>
	<div class="search-area d-flex">
		<form:select path="condition.searchType" class="form-select me-2">
			<form:option value="noticeTitle" label="제목"></form:option>
			<form:option value="noticeCont" label="본문"></form:option>
		</form:select>
		<form:input path="condition.searchWord" class="form-control me-2"
			placeholder="검색어 입력" />
		<button type="submit" class="btn btn-primary search-btn">검색</button>
	</div>
</div>

<!-- 탭 메뉴 -->
<ul class="nav nav-tabs mb-3 justify-content-center" id="inquiryTabs">
	<li class="nav-item col-6 text-center"><a
		href="${pageContext.request.contextPath}/help/inquiry"
		class="nav-link ${currentPage eq '/help/inquiry' ? 'active' : ''}">
			문의 등록 </a></li>
	<li class="nav-item col-6 text-center"><a
		href="${pageContext.request.contextPath}/help/inquiry/list"
		class="nav-link ${currentPage eq '/help/inquiry/list' ? 'active' : ''}">
			내 문의 내역 </a></li>
</ul>

<form id="searchForm"
	action="${pageContext.request.contextPath}/help/inquiry/list"
	method="get" style="display: none;">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input type="text" name="page" placeholder="page" />
</form>
<div class="notice-container">
	<div class="table-responsive">
		<table
			class="table table-hover table-bordered align-middle text-center">
			<thead class="table-secondary">
				<tr>
					<th scope="col" class="col-1">번호</th>
					<th scope="col" class="col-7">제목</th>
					<th scope="col" class="col-2">답변 상태</th>
					<th scope="col" class="col-2">작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="inquiry" items="${inquiryList}" varStatus="status">
					<tr class="text-center">
						<td>${inquiry.rowNumberDesc}</td>
						<td class="text-start text-truncate title-column" title="${inquiry.noticeTitle}">
						    <a href="${pageContext.request.contextPath}/help/inquiry/${inquiry.noticeNo}" title="${inquiry.noticeTitle}">
						        ${inquiry.noticeTitle}
						    </a>
						</td>
						<td>
	                        <c:choose>
	                            <c:when test="${inquiry.noticeInquiriesYn eq 'Y'}">답변 완료</c:when>
	                            <c:otherwise>
	                            	<span class="text-primary">답변 대기</span>
	                            </c:otherwise>
	                        </c:choose>
                        </td>
						<td class="text-center">${inquiry.formattedNoticeDt}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!-- 페이징 영역 -->
<div class="paging-area">${pagingHtml}</div>
