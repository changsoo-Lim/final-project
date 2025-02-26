<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/help/styleAd.css">
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

<h3 class="mb-3">문의하기 관리</h3>
<div class="d-flex justify-content-between align-items-center mb-3">
	<h6 class="total-count">총 ${InquiryTotalCount} 건</h6>
    <div class="search-area d-flex align-items-center gap-2">
    	<div class="form-check ms-3">
            <input type="checkbox" id="onlyPending" class="form-check-input" ${param.pending eq 'true' ? 'checked' : ''}>
            <label for="onlyPending" class="form-check-label">답변 대기만 보기</label>
        </div>
        <form:select path="condition.searchType" class="form-select w-auto">
			<form:option value="noticeTitle" label="제목"></form:option>
			<form:option value="noticeCont" label="본문"></form:option>
		</form:select>
		<form:input path="condition.searchWord" class="form-control w-auto" placeholder="검색어 입력" />
		<button type="submit" class="btn btn-primary search-btn">검색</button>
	</div>
</div>
<form id="searchForm"
	action="${pageContext.request.contextPath}/admin/inquiry" method="get" style="display: none;">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input type="text" name="page" placeholder="page" />
</form>
<div class="table-responsive">
    <form id="deleteForm" action="${pageContext.request.contextPath}/admin/inquiry/deleteMultiple" method="post">
        <table class="table table-hover table-bordered text-center align-middle">
            <thead class="table-light">
                <tr>
                    <th scope="col">
                        <input type="checkbox" id="checkAll" />
                    </th>
                    <th scope="col" class="col-1">번호</th>
                    <th scope="col" class="col-6">제목</th>
                    <th scope="col" class="col-2">작성자</th>
                    <th scope="col" class="col-2">작성일시</th>
                    <th scope="col" class="col-1">답변 상태</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty inquiryList}">
                        <c:forEach var="inquiry" items="${inquiryList}">
                            <tr>
                                <td>
                                    <input type="checkbox" name="noticeNo" value="${inquiry.noticeNo}" class="delete-checkbox" />
                                </td>
                                <td>${inquiry.rowNumberDesc}</td>
                                <td class="text-start text-truncate" title="${inquiry.noticeTitle}">
                                    <a href="${pageContext.request.contextPath}/admin/inquiry/${inquiry.noticeNo}" class="text-decoration-none text-dark">
                                        ${inquiry.noticeTitle}
                                    </a>
                                </td>
                                <td>${inquiry.memId}</td>
                                <td>${inquiry.formattedNoticeDt}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${inquiry.noticeInquiriesYn eq 'Y'}">답변 완료</c:when>
                                        <c:otherwise>
                                        	<span class="text-primary">답변 대기</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="7" class="text-center py-3">등록된 문의가 없습니다.</td>
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
        <button type="submit" form="deleteForm" class="btn btn-danger bulk-delete-btn">선택 삭제</button>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/admin/help/inquiry.js"></script>

