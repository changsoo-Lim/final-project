<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("pageTitle", "문의 등록");
    request.setAttribute("currentPage", "/help/inquiry");
    request.setAttribute("contentPage", "/WEB-INF/views/stackUp/main/help/inquiry/inquiryFormContent.jsp");
%>
<jsp:include page="/WEB-INF/views/stackUp/main/help/layout.jsp" />