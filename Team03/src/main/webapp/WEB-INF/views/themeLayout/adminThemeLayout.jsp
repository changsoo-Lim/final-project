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
   <%-- <script type="text/javascript">
      alert("${message}");
   </script>
  </c:if> --%>
  
  <script type="text/javascript">
      Swal.fire({
         icon: 'success', // 성공 알림 아이콘
         title: '완료',
         text: "${message}",
         confirmButtonText: '확인'
      });
   </script>
</c:if>
<c:if test="${not empty errorMessage}">
   <script type="text/javascript">
      Swal.fire({
         icon: 'error', // 오류 알림 아이콘
         title: '오류',
         text: "${errorMessage}",
         confirmButtonText: '확인'
      });
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
  <div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed">
	  <!-- ======= Sidebar ======= -->
	  <aside class="left-sidebar">
	   <tiles:insertAttribute name="sidebar"/>
	  </aside>
	  <!-- End Sidebar-->
	  
	  <!-- ======= Header ======= -->
	  <div class="body-wrapper">
	    <tiles:insertAttribute name="header" />
	  <!-- End Header -->
	  <!-- ======= Main ======= -->
		  <div class="container-fluid">
		   <tiles:insertAttribute name="content" />
		  </div>
	  <!-- End #main -->
	  </div>
  </div>

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <tiles:insertAttribute name="postScript" />

</body>
</html>