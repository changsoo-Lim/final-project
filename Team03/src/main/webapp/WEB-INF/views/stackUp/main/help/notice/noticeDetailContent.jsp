<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/help/style.css">

<h3 class="mb-5">공지사항</h3>
<div class="card">
    <div class="card-header">
        <p>${notice.noticeTitle}</p>
        <small class="text-muted text-end">${notice.formattedNoticeDt}</small>
    </div>
    <div class="card-bodyND">
        <p>${notice.noticeCont}</p>
    </div>
</div>
<div class="file-container">
    <div class="file-list">
    <p> 첨부파일 </p>
        <c:forEach items="${notice.file.fileDetails}" var="fd" varStatus="vs">
            <c:url value='/adminNotice/${noticeNo}/file/${fd.atchFileNo}/${fd.fileSn}' var="downUrl"/>
             <a href="${downUrl}">${fd.orignlFileNm} (${fd.fileFancysize})</a>
             ${not vs.last ? '|' : ''}
        </c:forEach>
    </div>
</div>
<div class="text-start mt-3">
    <a href="${pageContext.request.contextPath}/help/notice"
    class="btn btn-secondary">이전</a>
</div>