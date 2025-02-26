<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${introDetail}" var="introDetail">
	<div class="mb-3">
		<input type="text" id="introDetailTitle" name="introDetailTitle" class="form-control" value="${introDetail.introDetailTitle}" readonly/>
		<span class="text-danger">${errors.introDetailTitle}</span>
	</div>
	<div class="mb-3">
		<textarea id="introDetailCont" name="introDetailCont" class="form-control" rows="10">${introDetail.introDetailCont}</textarea>
		<span class="text-danger">${errors.introDetailCont}</span>
	</div>
</c:forEach>
	<input type="hidden" name="introDetailNo" value="${introDetailNo}"/>
	<a href="<c:url value='/intro/${introDetailNo}/edit' />" class="btn btn-primary btn-sm">수정하러가기</a>