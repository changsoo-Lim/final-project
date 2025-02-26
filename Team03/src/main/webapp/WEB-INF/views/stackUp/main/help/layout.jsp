<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${pageTitle}</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/help/style.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<!-- 공통 사이드바 -->
			<div class="col-lg-3 col-md-4 sidebar">
				<h5>고객센터</h5>
				<div class="list-group">
					<a href="${pageContext.request.contextPath}/help/faq/user"
						class="list-group-item ${currentPage.startsWith('/help/faq') ? 'active' : ''}">
						FAQ </a> 
						
						<a href="${pageContext.request.contextPath}/help/notice"
						class="list-group-item ${currentPage eq '/help/notice' ? 'active' : ''}">
						공지사항 </a> 
						
						<a href="${pageContext.request.contextPath}/help/inquiry"
						class="list-group-item ${currentPage.startsWith('/help/inquiry') ? 'active' : ''}">
						문의하기 </a>
				</div>
			</div>

			<!-- 동적 콘텐츠 영역 -->
			<div class="col-lg-9 col-md-8 content">
				<jsp:include page="${contentPage}" />
			</div>
		</div>
	</div>
</body>
</html>
