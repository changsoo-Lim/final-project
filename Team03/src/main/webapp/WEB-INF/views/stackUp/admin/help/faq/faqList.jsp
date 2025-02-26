<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/help/styleAd.css">

<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

<h3 class="mb-3">FAQ 관리</h3>
<div class="d-flex justify-content-between align-items-center mb-3">
	<h6 class="total-count">총 ${FaqTotalCount} 건</h6>
	<div class="search-area d-flex align-items-center gap-2">
		<form:select path="condition.searchType" class="form-select w-auto">
			<form:option value="noticeTitle" label="제목"></form:option>
			<form:option value="noticeCont" label="본문"></form:option>
		</form:select>
		<form:input path="condition.searchWord" class="form-control w-auto" placeholder="검색어 입력" />
		<button type="submit" class="btn btn-primary search-btn">검색</button>
	</div>
</div>
<form id="searchForm"
	action="${pageContext.request.contextPath}/admin/faq" method="get" style="display: none;">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input type="text" name="page" placeholder="page" />
</form>
<div class="table-responsive">
	<form id="deleteForm" action="${pageContext.request.contextPath}/admin/faq/deleteMultiple" method="post">
		<table class="table table-hover table-bordered text-center align-middle">
			<thead class="table-light">
				<tr>
					<th scope="col">
						<input type="checkbox" id="checkAll" />
					</th>
					<th scope="col" class="col-1">번호</th>
					<th scope="col" class="col-1">구분</th>
					<th scope="col" class="col-8">제목</th>
					<th scope="col" class="col-2">작성일시</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty faqList}">
						<c:forEach var="faq" items="${faqList}">
							<tr>
								<td>
									<input type="checkbox" name="noticeNo" value="${faq.noticeNo}" class="delete-checkbox" />
								</td>
								<td>${faq.rowNumberDesc}</td>
								<td>
									<c:choose>
										<c:when test="${faq.noticeType == 'NT02'}">일반회원</c:when>
										<c:when test="${faq.noticeType == 'NT03'}">기업회원</c:when>
										<c:otherwise>알 수 없음</c:otherwise>
									</c:choose>
								</td>
								<td class="text-start text-truncate" title="${faq.noticeTitle}">
									<a href="${pageContext.request.contextPath}/admin/faq/${faq.noticeNo}/edit" class="text-decoration-none text-dark">
										${faq.noticeTitle}
									</a>
								</td>
								<td>${faq.formattedNoticeDt}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6" class="text-center py-3">등록된 FAQ가 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</form>
</div>

<div class="d-flex justify-content-between align-items-center mt-3">
	<div class="flex-grow-1 d-flex justify-content-center">
		${pagingHtml}
	</div>
	<div class="d-flex gap-2">
		<a href="${pageContext.request.contextPath}/admin/faq/form" class="btn btn-primary">FAQ 등록</a>
		<button type="submit" form="deleteForm" class="btn btn-danger bulk-delete-btn">선택 삭제</button>
	</div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/admin/help/faq.js"></script>