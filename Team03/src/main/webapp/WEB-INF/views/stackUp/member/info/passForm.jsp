<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/member/updatePass.css" />" >

<div class="row">
	<div class="col-12">
		<h3 class="fw-bold mb-2">비밀번호 수정</h3>
		<ul class="mt-3">
			<li>
				<p class="mb-0 fw-bold text-danger fs-6">비밀번호는 8~20자 영문, 숫자, 특수문자를 사용할 수 있습니다.</p>			
			</li>
			<li>
				<p class="fw-bold text-primary fs-6">비밀번호는 주기적(최소 6개월)으로 변경해주시기 바랍니다.</p>			
			</li>
		</ul>
		
		
	</div>
</div>
<hr style="border: 2px solid black;">
<form id="passUpdateForm" action="<c:url value="/member/info/updatePass" />" method="post" autocomplete="off" > 
	<div class="row mt-6 mb-6">
		<div class="col-md-2"></div>
		<div class="col-md-10">
			<div class="row mb-3">
				<label for="userPass" class="col-md-3 col-form-label fw-bold pe-0" >새로운 비밀번호</label>
				<div class="col-md-4 ps-0">
					<input type="password" class="form-control" name="userPass" id="userPass" />
				</div>
				<span class="mt-1 text-danger font13" id="error-message-pass" style="display:none">8~20자의 영문, 숫자, 특수문자 조합으로 입력해 주세요.</span>
			</div>
			<div class="row mb-3">
				<label for="userPassCheck" class="col-md-3 col-form-label fw-bold pe-0" >새로운 비밀번호 확인</label>
				<div class="col-md-4 ps-0">
					<input type="password" class="form-control" name="userPassCheck" id="userPassCheck" />
				</div>
				<span class="mt-1 text-danger font13" id="error-message-passCheck" style="display:none">새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.</span>
			</div>
		</div>
	</div>
	<hr style="border: 1px solid black;">
	<div class="text-end">
		<input type="submit" class="btn btn-primary" id="updateSubmit" value="저장" />
		<input type="button" class="btn btn-danger" id="updateReset" value="취소" />
	</div>
</form>
<%-- <a href="<c:url value="/info/formpost" />" >수정</a> --%>
<script src='<c:url value="/resources/js/member/passForm.js" />' ></script>
