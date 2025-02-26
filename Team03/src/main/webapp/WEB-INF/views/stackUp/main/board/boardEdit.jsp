<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main/board/boardEdit.css"> 

<div class="container">
    <h3 class="page-title">커뮤니티 수정 폼</h3>

    <form:form method="post" enctype="multipart/form-data" modelAttribute="board" class="board-form">
        <div class="form-group">
            <label for="boardTitle"><strong>제목</strong></label>
            <form:input type="text" id="boardTitle" path="boardTitle" class="form-control" />
            <form:errors path="boardTitle" cssClass="text-danger"/>
        </div>
		<hr>
        <div class="form-group">
            <label for="boardCont"><strong>내용</strong></label>
            <form:textarea id="boardCont" path="boardCont" rows="5" class="form-control" />
            <form:errors path="boardCont" cssClass="text-danger"/>
            <div id="charCount" class="char-count">0자</div> <!-- 글자 수를 표시할 영역 -->
        </div>
		<hr>
        <!-- 기존 파일들 표시 -->
        <div class="form-group">
            <strong>기존 파일</strong>
            <div class="file-list">
                <c:forEach items="${board.file.fileDetails}" var="fd" varStatus="vs">
                    <div class="file-item">
                        <span>${fd.orignlFileNm} (${fd.fileFancysize})</span>
                        <a href="javascript:;" data-atch-file-no="${fd.atchFileNo}" data-file-sn="${fd.fileSn}" class="btn btn-danger btn-sm">삭제</a>
                    </div>
                    ${not vs.last ? '|' : ''}
                </c:forEach>
            </div>
        </div>
		<hr>
        <!-- 새 파일 업로드 -->
        <div class="form-group">
            <label for="uploadFiles"><strong>파일 업로드</strong></label>
            <input type="file" name="uploadFiles" id="uploadFiles" class="form-control" multiple />
        </div>

        <!-- 수정 완료 버튼 -->
        <div class="button-container">
            <button type="submit" class="btn btn-primary">저장</button>
            <a href="<c:url value='/main/board' />" class="btn btn-secondary">목록으로</a>
        </div>
    </form:form>
</div>
<script src="${pageContext.request.contextPath}/resources/ckeditor5-36.0.0/packages/ckeditor5-build-classic/build/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/board/boardEdit.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/board/boardForm.js"></script>
