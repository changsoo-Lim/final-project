<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/help/notice.css">
<div class="d-flex justify-content-between align-items-center mb-4">
    <h3 class="fw-bold">FAQ 등록</h3>
    <a href="${pageContext.request.contextPath}/admin/faq" class="btn btn-secondary">목록</a>
</div>

<form:form id="faqForm" method="post" action="${pageContext.request.contextPath}/admin/faq/form" enctype="multipart/form-data" modelAttribute="faq">
    <div class="mb-3">
        <label class="form-label fw-bold me-3">FAQ 유형</label>
        <div class="form-check form-check-inline me-3">
            <input type="radio" id="typeGeneral" name="noticeType" value="NT02" class="form-check-input" checked>
            <label for="typeGeneral" class="form-check-label">일반회원</label>
        </div>
        <div class="form-check form-check-inline">
            <input type="radio" id="typeCorporate" name="noticeType" value="NT03" class="form-check-input">
            <label for="typeCorporate" class="form-check-label">기업회원</label>
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
                <h5 class="modal-title" id="previewModalLabel">FAQ 미리보기</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <h4 id="modalPreviewTitle" class="fw-bold"></h4>
                <hr>
                <p id="modalPreviewContent" style="white-space: pre-wrap;"></p>
                <div id="modalPreviewType" class="mt-3 text-muted"></div>
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