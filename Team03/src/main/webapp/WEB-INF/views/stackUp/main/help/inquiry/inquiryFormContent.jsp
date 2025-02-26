<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/help/style.css">

<h3 class="mb-5">문의하기</h3>

<!-- 탭 메뉴 -->
<ul class="nav nav-tabs mb-3 justify-content-center" id="inquiryTabs">
    <li class="nav-item col-6 text-center">
        <a href="${pageContext.request.contextPath}/help/inquiry"
           class="nav-link ${currentPage eq '/help/inquiry' ? 'active' : ''}">
            문의 등록
        </a>
    </li>
    <li class="nav-item col-6 text-center">
        <a href="${pageContext.request.contextPath}/help/inquiry/list"
           class="nav-link ${currentPage eq '/help/inquiry/list' ? 'active' : ''}">
            내 문의 내역
        </a>
    </li>
</ul>

<!-- 폼 영역 -->
<div class="notice-form-container">
    <form:form action="${pageContext.request.contextPath}/help/inquiry" enctype="multipart/form-data" method="post" modelAttribute="noticeType">
        <!-- 문의 제목 입력 -->
        <div class="mb-3">
            <label for="title" class="form-label fw-bold">문의 제목</label>
            <form:input path="noticeTitle" id="title" class="form-control" placeholder="제목을 입력하세요" />
        </div>

        <!-- 문의 내용 입력 -->
        <div class="mb-3">
            <label for=content class="form-label fw-bold">문의 내용</label>
            <form:textarea path="noticeCont" id="noticeCont" class="form-control" rows="13" placeholder="내용을 입력하세요" />
        </div>
		
		<div class="mb-3">
	        <label for="uploadFiles" class="form-label fw-bold">첨부파일</label>
	        <input type="file" id="uploadFiles" name="uploadFiles" class="form-control" multiple>
	    </div>
	    
        <!-- 버튼 영역 -->
        <div class="d-flex justify-content-end gap-2">
        
        	<!-- 자동 내용 입력 버튼 -->
			<button type="button" id="autoFillBtn" class="btn btn-info">데이터 입력</button>
			
            <button type="submit" class="btn btn-primary">등록</button>
            <a href="${pageContext.request.contextPath}/help/faq/user" class="btn btn-secondary">취소</a>
        </div>
    </form:form>
</div>

<script src="${pageContext.request.contextPath}/resources/ckeditor5-36.0.0/packages/ckeditor5-build-classic/build/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/help/inquiryForm.js"></script>
