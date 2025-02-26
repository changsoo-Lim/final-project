<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<sec:authorize access="!isAnonymous()">
	<sec:authentication property="principal.realUser" var="User" />
	<sec:authorize access="hasAuthority('ROLE02')">
		<sec:authentication property="principal.realUser.memberVO" var="loginMember" />
	</sec:authorize>
	<sec:authorize access="hasAuthority('ROLE03')">
		<sec:authentication property="principal.realUser.companyVO" var="loginCompany"/>
	</sec:authorize>
</sec:authorize>
<c:set var="user" value="${User}" scope="session" />
<c:set var="loginMember" value="${loginMember}" scope="session" />
<c:set var="loginCompany" value="${loginCompany}" scope="session" /> 
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>StackUp! - 스택업</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <tiles:insertAttribute name="preScript" />
  <c:if test="${not empty message }">
   <script type="text/javascript">
      alert("${message}");
   </script>
  </c:if>

</head>

<body data-url="${pageContext.request.contextPath}">
<!--  개발이 끝나면 삭제 시작 -->
<%-- <sec:authentication property="authorities" var="auths" />
<ul style="position:fixed; right:10px; z-index:9999;">
  <c:forEach var="auth" items="${auths}">
    <li>${auth.authority}</li>
    <sec:authorize access="hasAuthority('ROLE02')">
    	<li>이름 : ${loginMember.memNm }</li>
    </sec:authorize>
	<sec:authorize access="hasAuthority('ROLE03')">
		<li>이름 : ${loginCompany.compNm }</li>	
	</sec:authorize>
  </c:forEach>
</ul> --%>
<!--  개발이 끝나면 삭제 끝 -->

  <!-- ======= Header ======= -->
  <!-- <header id="header" class="header fixed-top d-flex align-items-center"> -->
  <nav id="header" class="navbar navbar-expand-lg navbar-light w-100">
    <tiles:insertAttribute name="header" />
  </nav>
  <!-- </header> --><!-- End Header -->

  <!-- ======= Sidebar ======= -->
  <section class="py-lg-7 py-5 bg-light-subtle">
  	<div class="container">
         <div class="row">
        	<!-- ======= Sidebar ======= -->
			<tiles:insertAttribute name="sidebar"/>
			<!-- End Sidebar-->
			<!-- ======= Main ======= -->
			<div class="col-lg-9 col-md-8">
				<tiles:insertAttribute name="content" />
			</div>
			<!-- End #main -->
   		</div>
	</div>
  </section>
  <!-- Scrollable Modal (알림 더보기 모달) -->
	<div class="modal fade" id="notificationsModal" tabindex="-1" aria-labelledby="notificationsModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-scrollable">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="notificationsModalLabel">전체 알림</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <ul id="allNotificationsList" class="list-group"></ul>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	      </div>
	    </div>
	  </div>
	</div>
  <!-- ======= Footer ======= -->
  <footer id="footer" class="pt-7">
  
    <tiles:insertAttribute name="footer"/>
    
  </footer><!-- End Footer -->

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <tiles:insertAttribute name="postScript" />
	<!-- Toast Container -->
	<div class="toast-container position-fixed bottom-0 end-0 p-3"></div>
</body>

</html>