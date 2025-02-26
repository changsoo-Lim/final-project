<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/intro/intro.css"/>
<div class="mb-3" id="introEdit">
	<input type="hidden" id="introNo" name="introNo" value="${intro.introNo}" required />
	<input type="text" id="introTitle" name="introTitle" class="form-control border-primary text-primary bg-light fw-bold" value="${intro.introTitle}" />
	<span class="text-danger">${errors.introTitle}</span>
</div>
<br>
<div class="mb-3" id="introDetailEditList">
<c:forEach items="${introDetail}" var="introDetail">
	<div class="mb-3" id="introDetailEdit">
		<button type="button" id="delBtn" class="btn btn-outline-danger" style="float: right; display:none;">삭제</button>
		<input type="hidden" id="introDetailNo" name="introDetailNo" value="${introDetail.introDetailNo}" required />
		<input type="text" id="introDetailTitle" name="introDetailTitle" class="form-control" value="${introDetail.introDetailTitle}" placeholder="자기소개서 제목을 입력하세요." required/>
		<span class="text-danger">${errors.introDetailTitle}</span>
		
		<textarea id="introDetailCont" name="introDetailCont" class="form-control" rows="13" placeholder="자기소개서 내용을 입력하세요." required >${introDetail.introDetailCont}</textarea>
   		<small id="charCount"></small>
		<span class="text-danger">${errors.introDetailCont}</span>
		
		<button class="btn btn-Info btn-sm" id="checkBtn">맞춤법 검사</button>
		<p id="spellingCheck" class="form-control" style="display:none; white-space: pre-wrap;"></p>
		<p id="emCheck" style="display:none;">
			<em class="red_text">●</em>맞춤법
			<em class="green_text">●</em>띄어쓰기
			<em class="violet_text">●</em>표준어의심
			<em class="blue_text">●</em>통계적교정
		</p>
		<button class="btn btn-Info btn-sm" id="checkedBtn" style="display:none;">맞춤법검사 완료</button>
	</div>
</c:forEach>
</div>
<div id="introForm" class="introForm"></div>
<button class="btn btn-secondary" type="submit" id="addFormBtn">추가</button>
<button class="btn btn-primary" type="submit" id="editBtn" style="float: right;">수정</button>
<a href="<c:url value='/intro'/>" class="btn btn-secondary"  style="float: right;">목록</a>

<script src="${pageContext.request.contextPath}/resources/js/intro_detail/intro_detailEdit.js"></script>