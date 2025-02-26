<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Dashboard - NiceAdmin Bootstrap Template</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <tiles:insertAttribute name="preScript" />
  <c:if test="${not empty message }">
   <script type="text/javascript">
      alert("${message}");
   </script>
  </c:if>

</head>

<body>

  <!-- ======= Header ======= -->
  <!-- <header id="header" class="header fixed-top d-flex align-items-center"> -->
  <nav id="header" class="navbar navbar-expand-lg navbar-light w-100">
    <tiles:insertAttribute name="header" />
  </nav>
  <!-- </header> --><!-- End Header -->

  <!-- ======= Sidebar ======= -->
  <%-- <aside id="sidebar" class="sidebar">
   
   <tiles:insertAttribute name="sidebar"/>
      
  </aside><!-- End Sidebar--> --%>

  <main id="main" class="main">

   <tiles:insertAttribute name="content" />

  </main><!-- End #main -->

  <!-- ======= Footer ======= -->
  <footer id="footer" class="pt-7">
  
    <tiles:insertAttribute name="footer"/>
    
  </footer><!-- End Footer -->

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <tiles:insertAttribute name="postScript" />

</body>

</html>