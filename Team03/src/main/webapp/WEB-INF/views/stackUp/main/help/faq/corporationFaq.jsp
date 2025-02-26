<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    request.setAttribute("pageTitle", "기업회원 FAQ");
    request.setAttribute("currentPage", "/help/faq/corporation");
    request.setAttribute("contentPage", "/WEB-INF/views/stackUp/main/help/faq/faqListContent.jsp");
    request.setAttribute("faqType", "corporation");
%>
<jsp:include page="/WEB-INF/views/stackUp/main/help/layout.jsp" />
