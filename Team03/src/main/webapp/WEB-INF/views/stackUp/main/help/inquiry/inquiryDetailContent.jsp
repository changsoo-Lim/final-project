<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<h3 class="mb-5">문의하기</h3>

<!-- 탭 메뉴 -->
<ul class="nav nav-tabs mb-3" id="inquiryTabs">
	<li class="nav-item">
		<a href="${pageContext.request.contextPath}/help/inquiry" class="nav-link "> 
			문의 등록 
		</a>
	</li>
	<li class="nav-item">
		<a href="${pageContext.request.contextPath}/help/inquiry/list" class="nav-link active">
			내 문의 내역
		</a>
	</li>
	
</ul>

<div class="card">
	<div class="card-header">
		<h4>${inquiry.noticeTitle}</h4>
		<small class="text-muted">${inquiry.formattedNoticeDt}</small>
	</div>
	<div class="card-body">
		<p>${inquiry.noticeCont}</p>
	</div>
</div>
<div class="file-container">
    <div class="file-list">
    <p> 첨부파일 </p>
        <c:forEach items="${inquiry.file.fileDetails}" var="fd" varStatus="vs">
            <c:url value='/help/${noticeNo}/file/${fd.atchFileNo}/${fd.fileSn}' var="downUrl"/>
             <a href="${downUrl}">${fd.orignlFileNm} (${fd.fileFancysize})</a>
             ${not vs.last ? '|' :  ''}
        </c:forEach>
    </div>
</div>
<!-- 관리자 답변 -->
<c:choose>
	<c:when test="${inquiry.noticeInquiriesYn eq 'Y'}">
		<div class="card mt-3">
			<div class="card-header">
				<h4>상담원 답변</h4>
				<small class="text-muted">${inquiry.formattedInquiriesDt}</small>
			</div>
			<div class="card-body2">
				<p>${inquiry.noticeInquiriesCont}</p>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="alert alert-primary mt-3">
			<strong>답변 대기 중</strong>
		</div>
	</c:otherwise>
</c:choose>

<div class="mt-3">
	<a href="${pageContext.request.contextPath}/help/inquiry/list"
		class="btn btn-secondary">이전</a>
</div>
