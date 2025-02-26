<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/help/style.css">

<div class="d-flex justify-content-between align-items-center mb-3">
	<h3 class="mb-3">공지사항</h3>
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

<form id="searchForm"
	action="${pageContext.request.contextPath}/help/notice" method="get"
	style="display: none;">
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
					<th scope="col" class="col-9">제목</th>
					<th scope="col" class="col-2">작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="notice" items="${noticeList}">
					<tr class="${notice.noticePinned == 'Y' ? 'custom-bg-subtle' : ''}">
						<!-- 구분 (공지 or 중요공지) -->
						<td>${notice.noticePinned == 'Y' ? notice.rowNumberDesc : notice.rowNumberDesc}</td>
						<!-- 제목 -->
						<td class="text-start text-truncate title-column" title="${notice.noticeTitle}">
						    <a href="${pageContext.request.contextPath}/help/notice/${notice.noticeNo}" title="${notice.noticeTitle}">
						        ${notice.noticeTitle}
						    </a>
						</td>
						<!-- 등록일 -->
						<td class="text-center">${notice.formattedNoticeDt}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!-- 페이징 영역 -->
<div class="paging-area">
    ${pagingHtml}
</div>