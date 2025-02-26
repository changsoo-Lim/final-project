<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

<div class="border mb-4 rounded-3 px-4 py-3">
	<button id="addFamButton" class="btn btn-info">목록 추가</button>
	<br>
	<div id="famList"></div>

	<div class="cjFam bft-box border rounded p-3 mb-3" style="display:none;">
		<button class="revFam btn btn-danger">삭제</button>
		<input type="hidden" class="compId" value="${benefit.compId }">
		<div class="mb-3">
			<label class="form-label" for="textInput">제목</label>
			<input type="text" class="cmpbftTile form-control" placeholder="제목을 입력해주세요">
		</div>
		<div class="mb-3">
			<label for="textarea-input" class="form-label">내용</label>
			<textarea class="cmpbftCont form-control" rows="5"></textarea>
		</div>
	</div>
	<button id="sendFamsButton" class="btn btn-info">생성</button>
	<br>
</div>
<script src="<c:url value='/resources/js/benefit/benefitForm.js' />"></script>
