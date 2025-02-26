<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <title>StackUp! - 스택업 검사 및 테스트</title>
   <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
   <!-- Favicon icon-->
   <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/favicon/apple-touch-icon.png">
   <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/favicon/favicon-32x32.png">
   <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/favicon/favicon-16x16.png">
   <link rel="manifest" href="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/favicon/site.webmanifest">
   <link rel="mask-icon" href="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/favicon/block-safari-pinned-tab.svg" color="#8b3dff">
   <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/favicon/favicon.ico">
   <meta name="msapplication-TileColor" content="#8b3dff">
   <meta name="msapplication-config" content="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/favicon/tile.xml">
   <!-- Color modes -->
   <script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/js/vendors/color-modes.js"></script>
   
   <!--  sweatAlert2 CSS -->
   <link href="${pageContext.request.contextPath}/resources/css/sweatAlert2/sweetalert2.min.css" rel="stylesheet">
   
   
   <!-- Libs CSS -->
   <link href="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/libs/simplebar/dist/simplebar.min.css" rel="stylesheet">
   <link href="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/libs/bootstrap-icons/font/bootstrap-icons.min.css" rel="stylesheet">

   <!-- Scroll Cue -->
   <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/libs/scrollcue/scrollCue.css">

   <!-- Box icons -->
   <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/fonts/css/boxicons.min.css">

   <!-- Theme CSS -->
   <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/css/theme.min.css">
   
   <!-- jQuery JS File -->  
  <script src="${pageContext.request.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
  <meta content="" name="description">
  <meta content="" name="keywords">
  <link rel="stylesheet" href="<c:url value="/resources/css/member/test/testConduct.css" />">
</head>
<body data-url="${pageContext.request.contextPath}">
	<h2 class="fw-bold p-3 mb-7 test-conduct-header">검사/테스트 응시</h2>
	<%-- <a href="<c:url value="/member/test/complete" />" class="btn btn-primary mb-3">완료</a> --%>
	<input type="hidden" value="${candidateNo }" id="candidateNo" > 
	<div class="container container1">
	    <!-- 왼쪽: 문제 영역 -->
	    <div class="left-section card">
	        <div class="card-body">
	            <!-- 문제 제목 -->
	            <h2 id="question-title" class="card-title text-primary">문제 1</h2>
	            <!-- 문제 지문 -->
	            <p id="question-text" class="card-text">여기에 문제의 지문이 표시됩니다.</p>
	            <!-- 문제 이미지 -->
	            <img id="question-image" src="" alt="문제 이미지" class="img-fluid rounded mb-3" style="display: none;">
	
	            <!-- 선택지 영역 -->
	            <div id="question-options" class="mb-4">
	            </div>
	        </div>
	    </div>
	
	    <!-- 오른쪽: 타이머 및 문제 탐색 -->
	    <div class="right-section">
	        <div class="timer-container">
	            <h3>남은 시간</h3>
	            <div class="circle-timer">
	                <svg viewBox="0 0 36 36" class="circular-chart blue">
	                    <path class="circle-bg"
	                          d="M18 2.0845
	                             a 15.9155 15.9155 0 0 1 0 31.831
	                             a 15.9155 15.9155 0 0 1 0 -31.831" />
	                    <path class="circle"
	                          d="M18 2.0845
	                             a 15.9155 15.9155 0 0 1 0 31.831
	                             a 15.9155 15.9155 0 0 1 0 -31.831" />
	                </svg>
	                <div class="timer-text">
	                    <span id="timer">30:00</span>
	                </div>
	            </div>
	
	            <div class="question-navigation mt-3">
	                <h3>문항 바로가기</h3>
	                <div id="question-buttons" class="d-flex flex-wrap gap-2"></div>
	            </div>
	
	            <div class="navigation-buttons mt-3">
	                <button id="prev-button" class="btn btn-secondary">이전</button>
	                <button id="next-button" class="btn btn-secondary">다음</button>
          			<button id="finish-button" class="btn btn-primary">시험 종료</button>
	            </div>
	        </div>
	    </div>
	</div>
<script src='<c:url value="/resources/js/member/testConduct.js" />'></script>
<script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/libs/simplebar/dist/simplebar.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/libs/headhesive/dist/headhesive.min.js"></script>
<!-- Theme JS -->
<script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/js/theme.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/js/vendors/password.js"></script>
<script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/libs/scrollcue/scrollCue.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/js/vendors/scrollcue.js"></script>
<!-- <script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/js/vendors/jarallax.js"></script>  -->
<script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/libs/parallax-js/dist/parallax.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/js/vendors/parallax.js"></script>
<!--Swiper JS -->
<script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/libs/swiper/swiper-bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/js/vendors/swiper.js"></script>

<!-- sweatAlert JS -->
<script src="${pageContext.request.contextPath }/resources/js/sweatAlert2/sweetalert2.all.min.js"></script>

<!-- Commons JS -->
<script src="${pageContext.request.contextPath }/resources/js/utils/commons.js"></script>

<!-- Pagination JS File -->
<script src="${pageContext.request.contextPath }/resources/js/utils/paging.js"></script>

<!-- Import Axios JS File  -->
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

</body>
</html>

