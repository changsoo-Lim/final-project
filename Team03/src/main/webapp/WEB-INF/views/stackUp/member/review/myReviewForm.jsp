<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div class="container mt-5">
    <div class="mb-4">
        <h2 class="Title">면접 후기 작성</h2>
    </div>
    <form:form method="post" modelAttribute="review" class="needs-validation">
        <!-- 기업 선택 -->
        <div class="mb-3">
            <label for="compId" class="form-label">기업 선택</label>
            <form:select class="form-select" path="compId" id="compId" onchange="updateCompanyName()">
                <option value="">기업선택</option>
                <form:options items="${myApplyList}" itemValue="company.compId" itemLabel="company.compNm" />
            </form:select>
        </div>

        <!-- 평점 선택 (별) -->
		<div class="mb-3">
		    <label class="form-label">평점 선택 (최대 5점)</label>
		    <div id="star-rating" class="d-flex align-items-center">
		        <!-- 별 모양 5개 -->
		        <span class="stars" style="font-size: 24px; cursor: pointer;">
		            ☆☆☆☆☆
		        </span>
		        <form:hidden path="reviewRating" id="reviewRating" />
		        <span class="ms-2 text-muted" id="rating-value">(0점)</span>
		    </div>
		</div>

        <!-- 후기 제목 -->
        <div class="mb-3">
            <label for="reviewTitle" class="form-label">후기 제목</label>
            <form:input type="text" path="reviewTitle" id="reviewTitle" class="form-control" placeholder="면접 후기 제목을 입력하세요." />
        </div>

        <!-- 후기 내용 -->
        <div class="mb-3">
            <label for="reviewCont" class="form-label">후기 내용</label>
            <textarea class="form-control" rows="15" name="reviewCont" id="reviewCont" placeholder="면접 후기를 입력하세요."></textarea>
        </div>

        <!-- 제출 버튼 -->
        <div class="d-grid">
            <button type="submit" class="btn btn-primary">작성 완료</button>
        </div>
    </form:form>
</div>
<script src='<c:url value="/resources/js/review/myReviewForm.js" />'></script>