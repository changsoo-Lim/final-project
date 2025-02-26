<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/deleteForm/deleteForm.css" />" >

<div class="row">
	<div class="col-12">
		<h3 class="fw-bold mb-2">StackUp!(스택업)  회원 탈퇴</h3>
	</div>
</div>
<hr style="border: 2px solid black;">
<div class="d-flex flex-column">
	<div class="col-xl-12 ms-9">
		<div class="card shadow-sm mb-4 w-82">
		   <div class="card-body">
		   		<h4>지금까지 이용해주셔서 감사 드립니다.</h4>
		   		<h5 class="mt-5" >탈퇴 아이디 복구 불가</h5>
		   		<ul>
		   			<li class="fs-6">탈퇴하신 아이디는 복구가 불가능합니다</li>
		   			<li class="fs-6">추후 회원가입 시에 동일한 아이디로 재가입 되지 않습니다.</li>
		   		</ul>
		   		<h5 class="mt-5" >서비스 이용 기록 삭제 안내</h5>
		   		<ul>
		   			<li class="fs-6"><Strong>개인 회원</Strong>은 이력서 정보, 구직 활동내역, 프리랜서 정보 등 모두 삭제됩니다.</li>
		   			<li class="fs-6"><Strong>기업 회원</Strong>은 채용공고 정보, 유료 서비스, 지원자 관리 정보, 테스트 기록 등 모두 삭제됩니다.</li>
		   			<li class="fs-6"><strong class="text-danger">삭제된 데이터는 복구되지 않습니다.</strong> </li>
		   		</ul>
		   		<h5 class="mt-5" >등록한 게시물 삭제 불가 안내</h5>
		   		<ul>
		   			<li class="fs-6">회원탈퇴 시 등록한 게시물은 삭제되지 않으므로, 삭제를 원하시면 회원탈퇴 전에 삭제해 주시기 바랍니다.</li>
		   		</ul>
		   </div>
		</div>
	</div>
	<div class="mb-3">
		<div class="text-center">
			<input type="checkbox" id="deleteCheck" class="form-check-input" />
			<label for="deleteCheck">
				안내 사항을 모두 확인하였으며, 이에 동의합니다.
			</label>
		</div>
	</div>
	<div class="col-xl-12">
		<div class="text-center">
			<form id="deleteUserForm" action="<c:url value="/member/info/deleteUser" />" >
				<input type="hidden" id="userCd" value="${user.userCd}" />
				<input type="submit" id="deleteBtn" class="btn btn-primary" value="탈퇴하기" />			
			</form>
		</div>
	</div>
</div>
<%-- <a href="<c:url value="/info/formpost" />" >수정</a> --%>
<script src='<c:url value="/resources/js/deleteForm/deleteForm.js" />' ></script>
