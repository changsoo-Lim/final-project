<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/help/notice.css">
<%-- 메인 컨테이너 --%>
<div class="d-flex justify-content-between align-items-center mb-4">
    <h3 class="fw-bold">공지사항 등록</h3>
    <a href="${pageContext.request.contextPath}/admin/notice" class="btn btn-secondary">목록</a>
</div>

<%-- 공지사항 등록 폼 --%>
<form:form id="noticeForm" method="post" action="${pageContext.request.contextPath}/admin/notice/form" enctype="multipart/form-data" modelAttribute="notice">
    <div class="mb-3">
	    <label class="form-label fw-bold me-3">고정 여부</label>
	    <div class="form-check form-check-inline me-3">
	        <input type="radio" id="pinnedYes" name="noticePinned" value="Y" class="form-check-input">
	        <label for="pinnedYes" class="form-check-label">고정</label>
	    </div>
	    <div class="form-check form-check-inline">
	        <input type="radio" id="pinnedNo" name="noticePinned" value="N" class="form-check-input" checked>
	        <label for="pinnedNo" class="form-check-label">일반</label>
    </div>
    <div class="mb-3">
        <label for="noticeTitle" class="form-label fw-bold">제목</label>
        <form:input path="noticeTitle" id="noticeTitle" class="form-control" placeholder="공지사항 제목을 입력하세요" />
    </div>
    <div class="mb-3">
        <label for="noticeCont" class="form-label fw-bold">내용</label>
        <form:textarea path="noticeCont" id="noticeCont" class="form-control" rows="6" placeholder="공지사항 내용을 입력하세요"></form:textarea>
    </div>
</div>
    <div class="mb-3">
        <label for="uploadFiles" class="form-label fw-bold">첨부파일</label>
        <input type="file" id="uploadFiles" name="uploadFiles" class="form-control" multiple>
    </div>
    <%-- 버튼 영역 --%>
    <div class="d-flex justify-content-end gap-2">
<!--         <button type="button" id="previewBtn" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#previewModal">미리보기</button> -->
        <button type="submit" class="btn btn-success">저장</button>
    </div>
</form:form>

<!-- Modal -->
<div class="modal fade" id="previewModal" tabindex="-1" aria-labelledby="previewModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="previewModalLabel">공지사항 미리보기</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <h4 id="modalPreviewTitle" class="fw-bold"></h4>
                <hr>
                <p id="modalPreviewContent" style="white-space: pre-wrap;"></p>
                <div id="modalPreviewFile" class="mt-3 text-muted"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/ckeditor5-36.0.0/packages/ckeditor5-build-classic/build/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/admin/help/preview.js"></script>