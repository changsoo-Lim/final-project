<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/help/styleAd.css">

<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

<h3 class="mb-3">공지사항 관리</h3>
	<div class="d-flex justify-content-between align-items-center mb-3">
		<h6 class="total-count">총 ${totalCount} 건</h6>
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
	action="${pageContext.request.contextPath}/admin/notice" method="get" style="display: none;">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input type="text" name="page" placeholder="page" />
</form>
<div class="table-responsiveNo">
    <form id="deleteForm" action="${pageContext.request.contextPath}/admin/notice/deleteMultiple" method="post">
        <table class="table table-hover table-bordered text-center align-middle">
            <thead class="table-light">
                <tr>
                    <th scope="col">
                        <input type="checkbox" id="checkAll" />
                    </th>
                    <th scope="col" class="col-1">번호</th>
                    <th scope="col" class="col-7">제목</th>
                    <th scope="col" class="col-2">작성일시</th>
                    <th scope="col" class="col-2">고정 관리</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty noticeList}">
                        <c:forEach var="notice" items="${noticeList}">
                            <tr class="${notice.noticePinned == 'Y' ? 'table-primary' : ''}">
                                <!-- 체크박스 -->
                                <td>
                                    <input type="checkbox" name="noticeNo" value="${notice.noticeNo}" class="delete-checkbox" />
                                </td>
                                <!-- 번호 -->
                                <td>${notice.rowNumberDesc}</td>
                                <!-- 제목 -->
                                <td class="text-start text-truncate" title="${notice.noticeTitle}">
								    <a href="${pageContext.request.contextPath}/admin/notice/${notice.noticeNo}/edit" 
								       class="text-decoration-none text-dark">
								       ${notice.noticeTitle}
								    </a>
								</td>
                                <!-- 등록일 -->
                                <td>${notice.formattedNoticeDt}</td>
                                <!-- 고정 여부 -->
                                <td>
								    <button type="button" 
								            class="btn btn-sm ${notice.noticePinned == 'Y' ? 'btn-success' : 'btn-secondary'} toggle-pinned-btn" 
								            data-notice-no="${notice.noticeNo}">
								        ${notice.noticePinned == 'Y' ? '고정 해제' : '고정'}
								    </button>
								</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" class="text-center py-3">등록된 공지사항이 없습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </form>
</div>

<div class="d-flex justify-content-between align-items-center mt-3">
    <div class="flex-grow-1 d-flex justify-content-center">
        ${pagingHTML}
    </div>
    <div class="d-flex gap-2">
        <a href="${pageContext.request.contextPath}/admin/notice/form" class="btn btn-primary">공지사항 등록</a>
        <button type="submit" form="deleteForm" class="btn btn-danger">선택 삭제</button>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/admin/help/notice.js"></script>