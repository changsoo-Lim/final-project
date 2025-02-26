<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>    

<div>
   <div class="brand-logo d-flex align-items-center justify-content-between">
   	
     <a href="${pageContext.request.contextPath}/company" class="text-nowrap logo-img">
       <img src="${pageContext.request.contextPath}/resources/Modernize-bootstrap-free-main/assets/images/logos/StackUpLogo.png" width="180" alt="" />
     </a>
     <div class="close-btn d-xl-none d-block sidebartoggler cursor-pointer" id="sidebarCollapse">
       <i class="ti ti-x fs-8"></i>
     </div>
   </div>
   <!-- Sidebar navigation-->
   <nav class="sidebar-nav scroll-sidebar" data-simplebar="">
     <ul id="sidebarnav">
       <li class="nav-small-cap">
         <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
         <span class="hide-menu">홈</span>
       </li>
       <li class="sidebar-item">
         <a class="sidebar-link" href="<c:url value="/index.do" />" aria-expanded="false">
           <span>
             <i class="ti ti-layout-dashboard"></i>
           </span>
           <span class="hide-menu">회원 메인페이지</span>
         </a>
       </li>
<!--        <li class="sidebar-item"> -->
<%--          <a class="sidebar-link" href="<c:url value="/company" />" aria-expanded="false"> --%>
<!--            <span> -->
<!--              <i class="ti ti-layout-dashboard"></i> -->
<!--            </span> -->
<!--            <span class="hide-menu">마이페이지</span> -->
<!--          </a> -->
<!--        </li> -->
<%--        <li class="sidebar-item">
         <a class="sidebar-link" href="<c:url value="/company/compId들어가야함~"/>" aria-expanded="false">
           <span>
             <i class="ti ti-layout-dashboard"></i>
           </span>
           <span class="hide-menu">기업디테일??</span>
         </a>
       </li> --%>
       
       <li class="nav-small-cap">
         <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
         <span class="hide-menu">기업 정보</span>
       </li>
       <li class="sidebar-item">
         <a class="sidebar-link" href="<c:url value="/company/form"/>" aria-expanded="false">
           <span>
             <i class="ti ti-article"></i>
           </span>
           <span class="hide-menu">기업정보 관리</span>
         </a>
       </li>
       
       <li class="nav-small-cap">
     <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
	     <span class="hide-menu">테스트</span>
	   </li>
	   <li class="sidebar-item">
	     <a class="sidebar-link" href="<c:url value="/company/test/list" />" aria-expanded="false">
	       <span>
	         <i class="ti ti-article"></i>
	       </span>
	       <span class="hide-menu">검사 및 테스트 관리</span>
	     </a>
	   </li>

	<li class="nav-small-cap">
         <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
         <span class="hide-menu">채용 공고</span>
       </li>
       <li class="sidebar-item">
         <a class="sidebar-link" href="<c:url value="/employ/list/comp"/>" aria-expanded="false">
           <span>
             <i class="ti ti-article"></i>
           </span>
           <span class="hide-menu">채용공고 조회</span>
         </a>
       </li>
       
       <li class="sidebar-item">
         <a class="sidebar-link" href="${pageContext.request.contextPath}/employ/form" aria-expanded="false">
           <span>
             <i class="ti ti-file-description"></i>
           </span>
           <span class="hide-menu">채용공고 등록</span>
         </a>
       </li>
       <li class="nav-small-cap">
         <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
         <span class="hide-menu">지원자</span>
       </li>
       <li class="sidebar-item">
         <a class="sidebar-link" href='<c:url value="/company/apply/list"  />' aria-expanded="false">
           <span>
             <i class="ti ti-article"></i>
           </span>
           <span class="hide-menu">지원자 관리</span>
         </a>
       </li>
       <li class="sidebar-item">
         <a class="sidebar-link" href='<c:url value="/vchat"  />' aria-expanded="false">
           <span>
             <i class="ti ti-file-description"></i>
           </span>
           <span class="hide-menu">화상회의 관리</span>
         </a>
       </li>
       
       <li class="nav-small-cap">
         <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
         <span class="hide-menu">상품 결제</span>
       </li>
       <li class="sidebar-item">
         <a class="sidebar-link" href="<c:url value='/buy' />" aria-expanded="false">
           <span>
             <i class="ti ti-article"></i>
           </span>
           <span class="hide-menu">결제 옵션 관리</span>
         </a>
       </li>
       <li class="sidebar-item">
         <a class="sidebar-link" href="${pageContext.request.contextPath}/buy/<security:authentication property="principal.realUser.userId" />/list" aria-expanded="false">
           <span>
             <i class="ti ti-file-description"></i>
           </span>
           <span class="hide-menu">결제 내역 관리</span>
         </a>
       </li>
       
       <li class="nav-small-cap">
         <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
         <span class="hide-menu">인재 채용</span>
       </li>
       
       <li class="sidebar-item">
         <a class="sidebar-link" href="${pageContext.request.contextPath}/talent/list" aria-expanded="false">
         
           <span>
             <i class="ti ti-article"></i>
           </span>
           <span class="hide-menu">인재 검색</span>
         </a>
       </li>
       <li class="sidebar-item">
         <a class="sidebar-link" href="${pageContext.request.contextPath}/resumeview" aria-expanded="false">
           <span>
             <i class="ti ti-file-description"></i>
           </span>
           <span class="hide-menu">인재 조회 기록</span>
         </a>
       </li>
       <li class="sidebar-item">
         <a class="sidebar-link" href='<c:url value="/position" />' aria-expanded="false">
           <span>
             <i class="ti ti-file-description"></i>
           </span>
           <span class="hide-menu">포지션 제안 관리</span>
         </a>
       </li>
       <li class="sidebar-item">
         <a class="sidebar-link" href='<c:url value="/interComp/memberlist" />' aria-expanded="false">
           <span>
             <i class="ti ti-file-description"></i>
           </span>
           <span class="hide-menu">관심 등록 인재 관리</span>
         </a>
       </li>
     
   <li class="nav-small-cap">
     <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
     <span class="hide-menu">프리랜서</span>
   </li>
   <li class="sidebar-item">
     <a class="sidebar-link" href="<c:url value="/freelancer/list"  />" aria-expanded="false">
       <span>
         <i class="ti ti-article"></i>
       </span>
       <span class="hide-menu">프리랜서 관리</span>
     </a>
   </li>
   <li class="sidebar-item">
     <a class="sidebar-link" href='<c:url value="/project"  />' aria-expanded="false">
       <span>
         <i class="ti ti-file-description"></i>
       </span>
       <span class="hide-menu">프로젝트 관리 </span>
     </a>
   </li>
   <li class="nav-small-cap">
     <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
     <span class="hide-menu">고객센터</span>
   </li>
   <li class="sidebar-item">
     <a class="sidebar-link" href='<c:url value="/help/notice"  />' aria-expanded="false">
       <span>
         <i class="ti ti-file-description"></i>
       </span>
       <span class="hide-menu">공지사항</span>
     </a>
   </li>
   <li class="sidebar-item">
     <a class="sidebar-link" href='<c:url value="/help/faq/corporation"  />' aria-expanded="false">
       <span>
         <i class="ti ti-file-description"></i>
       </span>
       <span class="hide-menu">FAQ</span>
     </a>
   </li>
   <li class="sidebar-item">
     <a class="sidebar-link" href='<c:url value="/help/inquiry"  />' aria-expanded="false">
       <span>
         <i class="ti ti-article"></i>
       </span>
       <span class="hide-menu">문의하기</span>
     </a>
   </li>
   </ul>
 </nav>
   <!-- End Sidebar navigation -->
</div>
<!-- End Sidebar scroll-->