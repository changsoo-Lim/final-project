<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div class="container">
	<h1>📒자료 수정 페이지</h1>
	
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
        <!-- 기존 파일들 표시 -->
        <div class="form-group">
            <strong>기존 파일</strong>
            <div class="file-list">
                <c:forEach items="${resource.file.fileDetails}" var="fd" varStatus="vs">
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
<br>
     	<div class="button-container">
            <button type="submit" class="btn btn-primary " >저장</button>
            <a href="<c:url value='/admin/resource'/>" class="btn btn-secondary " style="float: right;">목록</a>
        </div>
     	
	</form:form>
	
</div>
<script src="${pageContext.request.contextPath}/resources/js/admin/resource/adminResourceEdit.js"></script>