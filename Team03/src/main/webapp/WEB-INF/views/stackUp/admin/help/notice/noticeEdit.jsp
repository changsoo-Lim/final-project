<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/help/notice.css">

<div class="d-flex justify-content-between align-items-center mb-4">
    <h3 class="fw-bold">공지사항 수정</h3>
    <a href="${pageContext.request.contextPath}/admin/notice" class="btn btn-secondary">목록</a>
</div>

<!-- 공지사항 수정 폼 -->
<form:form id="noticeEditForm" method="post" action="${pageContext.request.contextPath}/admin/notice/${notice.noticeNo}/edit" enctype="multipart/form-data" modelAttribute="notice">
    <div class="mb-3">
        <label class="form-label fw-bold me-3">고정 여부</label>
        <div class="form-check form-check-inline me-3">
            <input type="radio" id="pinnedYes" name="noticePinned" value="Y" 
                class="form-check-input" ${notice.noticePinned == 'Y' ? 'checked' : ''}>
            <label for="pinnedYes" class="form-check-label">고정</label>
        </div>
        <div class="form-check form-check-inline">
            <input type="radio" id="pinnedNo" name="noticePinned" value="N" 
                class="form-check-input" ${notice.noticePinned == 'N' ? 'checked' : ''}>
            <label for="pinnedNo" class="form-check-label">일반</label>
        </div>
    </div>

    <div class="mb-3">
        <label for="noticeTitle" class="form-label fw-bold">제목</label>
        <form:input path="noticeTitle" id="noticeTitle" class="form-control" placeholder="공지사항 제목을 입력하세요" />
    </div>

    <div class="mb-3">
        <label for="noticeCont" class="form-label fw-bold">내용</label>
        <form:textarea path="noticeCont" id="noticeCont" class="form-control" rows="6" placeholder="공지사항 내용을 입력하세요"></form:textarea>
    </div>
    
    <div class="mb-3">
        <label for="uploadFiles" class="form-label fw-bold">첨부파일</label>
        <input type="file" id="uploadFiles" name="uploadFiles" class="form-control" multiple>
    </div>
    
    <div class="form-group">
	    <strong>기존 파일</strong>
	    <div class="file-list">
	        <c:forEach items="${notice.file.fileDetails}" var="fd" varStatus="vs">
	            <div class="file-item">
	                <span>${fd.orignlFileNm} (${fd.fileFancysize})</span>
	                <a href="javascript:;" data-atch-file-no="${fd.atchFileNo}" data-file-sn="${fd.fileSn}" class="btn btn-danger btn-sm delete-file">삭제</a>
	                <input type="hidden" name="remainingFiles" value="${fd.atchFileNo}_${fd.fileSn}" />
	            </div>
	        </c:forEach>
	    </div>
	</div>
</form:form>

<!-- 버튼 영역 -->
<div class="d-flex justify-content-end gap-2">
    <!-- 저장 버튼 -->
    <button form="noticeEditForm" type="submit" class="btn btn-success">저장</button>

    <!-- 삭제 버튼 -->
    <form method="post" action="${pageContext.request.contextPath}/admin/notice/${notice.noticeNo}/delete" onsubmit="return confirm('정말 삭제하시겠습니까?');" class="m-0">
        <button type="submit" class="btn btn-danger">삭제</button>
    </form>
</div>

<script src="${pageContext.request.contextPath}/resources/ckeditor5-36.0.0/packages/ckeditor5-build-classic/build/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/admin/help/notice.js"></script>