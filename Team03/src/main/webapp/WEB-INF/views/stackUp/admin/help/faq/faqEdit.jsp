<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/help/notice.css">
<div class="d-flex justify-content-between align-items-center mb-4">
    <h3 class="fw-bold">FAQ 수정</h3>
    <a href="${pageContext.request.contextPath}/admin/faq" class="btn btn-secondary">목록</a>
</div>

<form:form id="faqEditForm" method="post" action="${pageContext.request.contextPath}/admin/faq/${faq.noticeNo}/edit" enctype="multipart/form-data" modelAttribute="faq">
	<div class="mb-3">
		<label class="form-label fw-bold me-3">FAQ 유형</label>
	    <div class="form-check form-check-inline me-3">
	        <input type="radio" id="generalMember" name="noticeType" value="NT02"
	        	class="form-check-input" ${faq.noticeType == 'NT02' ? 'checked' : ''}>
	        <label for="generalMember" class="form-check-label">일반회원</label>
		</div>
		<div class="form-check form-check-inline">
	        <input type="radio" id="corporateMember" name="noticeType" value="NT03"
	            class="form-check-input" ${faq.noticeType == 'NT03' ? 'checked' : ''}>
	        <label for="corporateMember" class="form-check-label">기업회원</label>
	    </div>
	</div>

	<div class="mb-3">
	    <label for="noticeTitle" class="form-label fw-bold">제목</label>
	    <form:input path="noticeTitle" id="noticeTitle" class="form-control" placeholder="FAQ 제목을 입력하세요" />
	</div>
	
	<div class="mb-3">
	    <label for="noticeCont" class="form-label fw-bold">내용</label>
	    <form:textarea path="noticeCont" id="noticeCont" class="form-control" rows="6" placeholder="FAQ 내용을 입력하세요"></form:textarea>
	</div>
</form:form>

<!-- 버튼 정렬 -->
<div class="d-flex justify-content-end gap-2">
    <!-- 저장 버튼 -->
    <button form="faqEditForm" type="submit" class="btn btn-success">저장</button>

    <!-- 삭제 버튼 -->
    <form action="${pageContext.request.contextPath}/admin/faq/${faq.noticeNo}/delete" method="post" class="m-0" id="deleteForm">
        <button type="button" class="btn btn-danger" onclick="faqSingleDelete()">삭제</button>
    </form>
</div>

<script src="${pageContext.request.contextPath}/resources/ckeditor5-36.0.0/packages/ckeditor5-build-classic/build/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/admin/help/preview.js"></script>
