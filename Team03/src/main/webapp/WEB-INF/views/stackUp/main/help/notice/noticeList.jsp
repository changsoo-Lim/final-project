<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    request.setAttribute("pageTitle", "공지사항");
    request.setAttribute("currentPage", "/help/notice");
    request.setAttribute("contentPage", "/WEB-INF/views/stackUp/main/help/notice/noticeListContent.jsp");
%>
<jsp:include page="/WEB-INF/views/stackUp/main/help/layout.jsp" />
