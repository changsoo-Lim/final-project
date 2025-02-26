<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mainHeader/style.css" />
   <!-- Navbar -->
         <div class="container px-3">
            <a class="navbar-brand" href="${pageContext.request.contextPath }/index.do"><img style="width: 150px;" src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/logo/StackUpLogo.png" alt /></a>
            <button class="navbar-toggler offcanvas-nav-btn" type="button">
               <i class="bi bi-list"></i>
            </button>
            <div class="offcanvas offcanvas-start offcanvas-nav" style="width: 20rem">
               <div class="offcanvas-header">
                  <a href="./index.html" class="text-inverse"><img src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/logo/logo.svg" alt /></a>
                  <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
               </div>
               <div class="offcanvas-body pt-0 align-items-center">
                  <ul class="navbar-nav mx-auto align-items-lg-center">
                     <li class="nav-item dropdown me-4">
                        <a class="nav-link dropdown-toggle" href="" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">채용정보</a>
                        <ul class="dropdown-menu">
                           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/employ/list">지역별</a></li>
                           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/employ/list">직종별</a></li>
                           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/employ/list">업종별</a></li>
                           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/employ/list">상세조건별</a></li>
                        </ul>
                     </li>
                     <li class="nav-item dropdown">
                     </li>
                     <li class="nav-item dropdown me-4">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">프리랜서</a>
                        <ul class="dropdown-menu">
                           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/freelancer/list">프리랜서 조회</a></li>
                           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/freelancer/new">프리랜서 등록</a></li>
<!--                            <li><a class="dropdown-item" href="./landing-sass-v1.html">프로젝트 조회</a></li> -->
<!--                            <li><a class="dropdown-item" href="./landing-sass-v2.html">프로젝트 등록</a></li> -->
                        </ul>
                     </li>
                     
                     <li class="nav-item dropdown me-4">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">정보게시판</a>
                        <ul class="dropdown-menu">
                           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/main/resource">자료실</a></li>
                           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/main/board">커뮤니티</a></li>
                           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/review">면접후기</a></li>
                        </ul>
                     </li>
                    
                     <li class="nav-item dropdown me-4">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">고객센터</a>
                        <ul class="dropdown-menu">
                           <li><a class="dropdown-item" href='<c:url value="/help/faq/user" />'>FAQ</a></li>
                           <li><a class="dropdown-item" href='<c:url value="/help/notice" />'>공지사항</a></li>
                           <li><a class="dropdown-item" href='<c:url value="/help/inquiry" />'>문의하기</a></li>
<!--                            <li><a class="dropdown-item" href="./account-billing.html"></a></li> -->
<!--                            <li class="dropdown-submenu dropend"> -->
<!--                               <a class="dropdown-item dropdown-toggle" href="#">Authentication</a> -->
<!--                               <ul class="dropdown-menu"> -->
<!--                                  <li class="dropdown-header">Simple</li> -->

<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./signin.html">Sign In</a> -->
<!--                                  </li> -->
<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./signup.html">Sign Up</a> -->
<!--                                  </li> -->
<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./forget-password.html">Forget Password</a> -->
<!--                                  </li> -->
<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./reset-password.html">Reset Password</a> -->
<!--                                  </li> -->
<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./otp-varification.html">OTP Varification</a> -->
<!--                                  </li> -->
<!--                                  <li> -->
<!--                                     <hr class="dropdown-divider" /> -->
<!--                                  </li> -->
<!--                                  <li class="dropdown-header">Side Cover</li> -->

<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./signin-v2.html">Sign In</a> -->
<!--                                  </li> -->

<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./signup-v2.html">Sign Up</a> -->
<!--                                  </li> -->

<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./forget-password-v2.html">Forget Password</a> -->
<!--                                  </li> -->
<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./reset-password-v2.html">Reset Password</a> -->
<!--                                  </li> -->
<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./otp-varification-v2.html">OTP Varification</a> -->
<!--                                  </li> -->
<!--                               </ul> -->
<!--                            </li> -->
<!--                            <li class="dropdown-submenu dropend"> -->
<!--                               <a class="dropdown-item dropdown-toggle" href="#">Utility</a> -->
<!--                               <ul class="dropdown-menu"> -->
<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./404-error.html">404 Error</a> -->
<!--                                  </li> -->
<!--                                  <li> -->
<!--                                     <a class="dropdown-item" href="./changelog.html">Changelog</a> -->
<!--                                  </li> -->
<!--                               </ul> -->
<!--                            </li> -->
                        </ul>
                     </li>
                  </ul>
                  
                    <!-- 회원 알림 / 회원 메뉴  -->
					<sec:authorize access="hasAuthority('ROLE02') or hasAuthority('ROLE03')">
	                  <div class="mt-3 mt-lg-0 d-flex align-items-center">
	                  	<!-- 알림 아이콘 -->
						<div class="dropdown align-items-center d-flex">
						    <a href="#" class="me-4 position-relative" id="notificationButton" aria-expanded="false">
						        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-bell" viewBox="0 0 18 18">
						            <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4 4 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4 4 0 0 0-3.203-3.92zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5 5 0 0 1 13 6c0 .88.32 4.2 1.22 6z"/>
						        </svg>
						        <span id="notificationBadge" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger" style="display: none;"></span>
						    </a>
						</div>
						
						<!-- 알림 창 -->
						<div id="notificationPanel" class="notification-panel shadow" style="display: none; position: fixed; top: 60px; right: 100px; width: 350px; background: white; border: 1px solid #ddd; border-radius: 5px; z-index: 1050;">
						    <div class="p-3 border-bottom">
						        <h5 class="mb-0">알림</h5>
						    </div>
						    <!-- 알림 리스트 -->
							<ul id="notificationList" class="list-group"></ul>
							
							<!-- 알림 더보기 버튼 -->
							<button id="moreNotificationsBtn" class="btn btn-link d-none">알림 더보기 +</button>
							
						</div>
						
						<div class="dropdown">
		                  	<a href="#" class="me-2" id="dropdownMenuButton" aria-expanded="false"  >
		                  		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill mb-1" viewBox="0 0 16 16">
								  <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
								</svg>
			                  	<span class="me-3 mt-1">
			                  	  <sec:authorize access="hasAuthority('ROLE02')">
			                  	    ${loginMember.memNm}
			                  	  </sec:authorize>
			                  	  <sec:authorize access="hasAuthority('ROLE03')">
			                  	    ${loginCompany.compNm}
			                  	  </sec:authorize>
			                    </span>
		                  	</a>
						  <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						    <sec:authorize access="hasAuthority('ROLE02')">
						      <li><a class="dropdown-item" href='<c:url value="/member/mypage" />'>마이페이지</a></li>
		                  	  <li><a class="dropdown-item" href='<c:url value="/member/info/infoForm/passCheck" />'>회원정보 관리</a></li>
							  <li><a class="dropdown-item" href='<c:url value="/resume/new" />'>이력서 관리</a></li>
							  <li><a class="dropdown-item" href='<c:url value="/logout" />'>로그아웃</a></li>
		                  	</sec:authorize>
		                  	<sec:authorize access="hasAuthority('ROLE03')">
		                  	  <li><a class="dropdown-item" href='<c:url value="/company" />'>기업 페이지</a></li>
						      <li><a class="dropdown-item" href='<c:url value="/company/client001" />'>기업정보 관리</a></li>
							  <li><a class="dropdown-item" href='<c:url value="/resume/new" />'>채용공고 관리</a></li>
							  <li><a class="dropdown-item" href='<c:url value="/logout" />'>로그아웃</a></li>
		                  	</sec:authorize>
						  </ul>				
						</div>
					  </div>
					</sec:authorize>
					
					<!-- Admin Page Url  -->
                  	<sec:authorize access="hasAuthority('ROLE01')">
                  	  <a href='<c:url value="/admin" />' class=" mx-2">관리자 페이지</a>
				 	</sec:authorize>
				 	<!-- Company Page Url  -->					
					<sec:authorize access="hasAuthority('ROLE03')">
					  <a href='<c:url value="/company" />' class=" mx-2">기업 페이지</a>
					</sec:authorize>
					
					<!-- Not Anonymous Logout -->
					<sec:authorize access="!isAnonymous()">
					  <a href='<c:url value="/logout" />' class="btn btn-light mx-2">로그아웃</a>
					</sec:authorize>
					
					<!-- Anonymous Button -->
					<sec:authorize access="hasAuthority('ROLE_ANONYMOUS')">
					  <a href='<c:url value="/login" />' class="btn btn-light mx-2">로그인</a>
					  <a href='<c:url value="/memberForm" />' class="btn btn-primary">회원가입</a>
					</sec:authorize>
					
                  </div>
               </div>
            </div>
         </div>
<script src="${pageContext.request.contextPath}/resources/js/sse/sse.js"></script>