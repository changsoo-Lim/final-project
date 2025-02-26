<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="row">
	<div class="col-12">
		<h3 class="fw-bold mb-2">비밀번호 확인</h3>
	</div>
</div>
<hr style="border: 2px solid black;">
<form id="passCheckForm" action="<c:url value="/member/info/${pageName}/passCheck" />" method="post" autocomplete="off" > 
	<div class="row mt-6 mb-6">
		<div class="col-md-2"></div>
		<!-- 입력 필드 영역 (오른쪽) -->
		<div class="col-md-10">
			<div class="row mb-3">
				<label for="memNm" class="col-md-2 col-form-label fw-bold" >아이디</label>
				<div class="col-md-4 align-content-center">
					<span><strong>${user.userId}</strong></span>
				</div>
			</div>
			<div class="row mb-3">
				<label for="memRegno" class="col-md-2 col-form-label fw-bold" >비밀번호</label>
				<div class="col-md-4">
					<input type="password" class="form-control" name="userPass" id="userPass" />
				</div>
			</div>
		</div>
	</div>
	<hr style="border: 1px solid black;">
	<div class="text-end">
		<input type="submit" class="btn btn-primary" id="checkSubmit" value="확인" />
		<input type="button" class="btn btn-danger" id="checkReset" value="취소" />
	</div>
</form>
<%-- <a href="<c:url value="/passUpdate/form" />" >비밀번호 확인</a> --%>

<script src='<c:url value="/resources/js/member/passCheck.js" />' ></script>