<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Bootstrap 및 커스텀 CSS 링크 추가 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/company/companyList.css">
<div class="container mt-5">
	<h3 class="mb-4">기업 회원 리스트</h3>
	<%-- action="${pageContext.request.contextPath}/admincompany/search" --%>
	<div class="search-area d-flex justify-content-end align-items-center">
		<form:select path="condition.searchType"
			class="form-select me-2 w-auto" name="searchType">
			<form:option value="compNm">기업명</form:option>
			<form:option value="compId">아이디</form:option>
			<form:option value="compNum">사업자번호</form:option>
		</form:select>
		<form:input path="condition.searchWord" type="text" name="searchWord"
			class="form-control me-2" placeholder="검색어를 입력하세요"
			style="max-width: 250px;" />
		<button type="button" class="search-btn btn btn-primary">검색</button>
	</div>

</div>
<br>
<table class="table table-bordered text-center align-middle">
	<thead class="table-light">
		<tr>
			<th class="col-1">순번</th>
			<th class="col-6">기업명</th>
			<th class="col-2">진행중 공고수</th>
			<th class="col-2">아이디</th>
			<th class="col-1">관리</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${!empty compList}">
			<c:forEach var="company" items="${compList}">
				<tr>
					<td>${company.RNUM}</td>
					<td class="text-truncate text-start">
						<!-- 왼쪽 정렬 클래스 추가 --> <a
						href="${pageContext.request.contextPath}/admincompany/${company.compId}/detail">
							${company.compNm} </a>
					</td>
					<td></td>
					<td>${company.compId}</td>
					<td>
						<button type="button" class="btn btn-primary btn-sm">수정</button>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<c:if test="${empty compList}">
	<div class="alert alert-warning text-center" role="alert">검색된 기업이
		없습니다!</div>
</c:if>
<div class="paging-area">${pagingHTML }</div>

<form id="searchForm">
	<form:input path="condition.searchType" type="hidden" name="searchType" />
	<form:input path="condition.searchWord" type="hidden" name="searchWord" />
	<input type="hidden" name="page" placeholder="page" />
</form>


<!-- JavaScript 링크 추가 -->
<script
	src="${pageContext.request.contextPath}/resources/js/admin/company/admComapnyList.js"></script>
