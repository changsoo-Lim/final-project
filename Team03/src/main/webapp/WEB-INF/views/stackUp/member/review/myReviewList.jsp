<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<div>
		<h2 class="Title"> 면접 후기 관리</h2>
	</div>
	<div class="search-area d-flex justify-content-end position-relative">
	    <a href="<c:url value='/review/write' />"class="btn btn-primary">등록</a>
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
	                        <c:choose>
							    <c:when test="${review.reviewStatus == 'Y'}">
							        <h5 class="card-title fw-bold">
			                            <a href="<c:url value='/review/${review.reviewNo}' />" class="text-decoration-none text-primary">
			                                ${review.reviewTitle}
			                            </a>
			                        </h5>
							    </c:when>
							    <c:otherwise>
							       <h5 class="card-title fw-bold text-primary">
			                                ${review.reviewTitle}
			                        </h5>
							    </c:otherwise>
							</c:choose>
	                        
	                        
	                        <!-- 승인 상태 표시 -->
							<c:choose>
							    <c:when test="${review.reviewStatus == 'Y'}">
							        <span class="badge bg-success text-white py-1 px-2 rounded-pill">
							            승인 완료
							        </span>
							    </c:when>
							    <c:otherwise>
							        <span class="badge bg-danger text-white py-1 px-2 rounded-pill">
							            승인 대기
							        </span>
							    </c:otherwise>
							</c:choose>
	                        
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
</div>
<script src='<c:url value="/resources/js/review/myReview.js" />'></script>