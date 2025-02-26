<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main/board/boardForm.css"> 

<div class="container">
<h3 class="page-title">커뮤니티 등록 폼</h3>

	<div class="board-detail-container">
	    <form:form method="post" enctype="multipart/form-data" modelAttribute="board">
	        
	        <div class="title-container">
	            <label for="boardTitle"><strong>제목</strong></label>
	            <form:input type="text" path="boardTitle" class="form-control" id="boardTitle"  />
	            <form:errors path="boardTitle" cssClass="text-danger" />
	        </div>
	
	        <hr>
	
	       
	        <div class="content-container">
	            <label for="boardCont"><strong>내용</strong></label>
	            <form:textarea path="boardCont" class="form-control" id="boardCont" rows="10" required="required" oninput="updateCharCount()"  />
	            <form:errors path="boardCont" cssClass="text-danger" />
	            <div id="charCount" class="char-count">0자</div> <!-- 글자 수를 표시할 영역 -->
	        </div>
	
	        <hr>
	
	        
	        <div class="file-container">
	            <strong>파일:</strong>
	            <input type="file" name="uploadFiles" multiple class="form-control" />
	        </div>
	
	        <hr>
	
	        
	        <div class="button-container">
	            <button type="submit" class="btn btn-primary ">등록</button>
	            <a href="<c:url value='/admin/board'/>" class="btn btn-primary " style="float: right;">목록</a>
	        </div>
	    </form:form>
	</div>
</div>

<script src="${pageContext.request.contextPath}/resources/ckeditor5-36.0.0/packages/ckeditor5-build-classic/build/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/board/boardForm.js"></script>