<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/resources/css/review/reviewList.css"> 
<div class="container">
	<br>
	<div>
		<h2 class="Title">📝 면접 후기 📝</h2>
		<p>최신 면접 후기를 확인하고, 기업별 면접 후기 정보를 제공합니다.</p>
	</div>

    <div class="search-area d-flex justify-content-end position-relative">
        <form:input path="condition.searchWord" id="compId" class="form-control w-auto"/>
        <button type="submit" class="btn btn-primary search-btn">검색</button>
    </div>

	<!-- 총 게시물 수 -->
	<div class="total-count">
		<p>총 ${totalCount} 건</p>
	</div>

	<hr>

	<!-- 면접 후기 리스트 -->
	<div class="row gy-3">
	    <c:if test="${not empty review}">
	        <c:forEach items="${review}" var="review" varStatus="status">
	            <div class="col-lg-4">
	                <div class="card border-0 shadow rounded mb-3">
	                    <div class="card-body">
	                        <!-- 후기 제목 -->
	                        <h5 class="card-title fw-bold">
	                            <a href="<c:url value='/review/${review.reviewNo}' />" class="text-decoration-none text-primary">
	                                ${review.reviewTitle}
	                            </a>
	                        </h5>
	                        
	                        <!-- 작성일 표시 -->
	                        <p class="card-text text-muted small">작성일: 
	                            ${review.reviewDt.substring(0, 4)}-${review.reviewDt.substring(4, 6)}-${review.reviewDt.substring(6, 8)}
	                        </p>
	                    </div>
	                </div>
	            </div>
	        </c:forEach>
	    </c:if>
	</div>
	<br>
	<hr>
	<!-- 페이징 영역 -->
	<div class="paging-area">${pagingHTML}</div>


	<!-- 숨겨진 검색 폼 (페이징 처리에 사용) -->
	<form id="searchForm" style="display: none">
		<form:input path="condition.searchType" placeholder="searchType" />
		<form:input path="condition.searchWord" placeholder="searchWord" />
		<input type="text" name="page" placeholder="page" />
	</form>
</div>