<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div class="container">
	<h1>📒자료 등록 페이지</h1>
	
	<form:form method="post" enctype="multipart/form-data" modelAttribute="resource">
		
		
			<!-- 셀렉트 박스 추가 -->
	    <div class="select-container">
	        <label for="resourceType"><strong>파일 형식</strong></label>
	        <select id="resourceType" class="form-control" onchange="updateTitleInput()">
	            <option value="">선택하세요</option>
	            <option value="Excel">[Excel] </option>
	            <option value="Hwp">[Hwp] </option>
	            <option value="Docx">[Docx] </option>
	            <option value="PDF">[PDF] </option>
	        </select>
	    </div>
<hr>		
		<div class="title-container">
            <label for="resourceTitle"><strong>양식 제목</strong></label>
            <form:input type="text" path="resourceTitle" class="form-control" id="resourceTitle" required="required" />
            <form:errors path="resourceTitle" cssClass="text-danger" />
        </div>
<hr>
        <div class="file-container">
           <strong>파일:</strong>
           <input type="file" name="uploadFiles" multiple class="form-control" onchange="updateImagePreview(this)" required="required" />
     	</div> 
     	<pre>1. 이미지 먼저 파일 등록 </pre>
     	<pre>2. 수정 클릭 [워드,엑셀,한글] 파일 등록 </pre>
     	<br>
     	<div class="button-container">
            <button type="submit" class="btn btn-primary " >등록</button>
            <a href="<c:url value='/admin/resource'/>" class="btn btn-secondary " style="float: right;">목록</a>
        </div>
     	
	</form:form>
	
</div>
<script src="${pageContext.request.contextPath}/resources/js/admin/resource/adminResourceForm.js"></script>