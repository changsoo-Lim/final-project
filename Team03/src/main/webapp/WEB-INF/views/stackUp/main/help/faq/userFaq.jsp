<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    request.setAttribute("pageTitle", "개인회원 FAQ");
    request.setAttribute("currentPage", "/help/faq/user");
    request.setAttribute("contentPage", "/WEB-INF/views/stackUp/main/help/faq/faqListContent.jsp");
    request.setAttribute("faqType", "user");
%>
<jsp:include page="/WEB-INF/views/stackUp/main/help/layout.jsp" />
