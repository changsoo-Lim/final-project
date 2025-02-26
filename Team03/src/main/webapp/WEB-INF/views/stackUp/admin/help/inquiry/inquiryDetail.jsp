<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/help/inquiry.css">
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

<div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="mb-3">문의하기 상세조회</h3>
    <a href="${pageContext.request.contextPath}/admin/inquiry" class="btn btn-secondary">목록</a>
</div>

<table class="table table-bordered">
    <tbody>
        <tr>
            <th class="w-25 bg-light text-center">문의 번호</th>
            <td>${inquiry.noticeNo}</td>
        </tr>
        <tr>
            <th class="bg-light text-center">작성일시</th>
            <td>${inquiry.formattedNoticeDt}</td>
        </tr>
        <tr>
            <th class="bg-light text-center">작성자</th>
            <td>${inquiry.memId}</td>
        </tr>
        <tr>
            <th class="bg-light text-center">제목</th>
            <td>${inquiry.noticeTitle}</td>
        </tr>
        <tr>
            <th class="bg-light text-center">내용</th>
            <td>${inquiry.noticeCont}</td>
        </tr>
        <tr>
            <th class="bg-light text-center">첨부 파일</th>
            <td>
                <c:forEach items="${inquiry.file.fileDetails}" var="fd" varStatus="vs">
		            <c:url value='/help/${noticeNo}/file/${fd.atchFileNo}/${fd.fileSn}' var="downUrl"/>
		             <a href="${downUrl}">${fd.orignlFileNm} (${fd.fileFancysize})</a>
		             ${not vs.last ? '|' : ''}
		        </c:forEach>
            </td>
        </tr>
        <tr>
            <th class="bg-light text-center">답변 상태</th>
            <td>
                <c:choose>
                    <c:when test="${inquiry.noticeInquiriesYn == 'Y'}">
                        <span class="badge bg-success">답변 완료</span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge bg-warning text-dark">답변 대기</span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </tbody>
</table>

<div class="mt-3">
    <label for="answerContent" class="form-label">
        <c:choose>
            <c:when test="${inquiry.noticeInquiriesYn == 'Y'}">답변 수정</c:when>
            <c:otherwise>답변 등록</c:otherwise>
        </c:choose>
    </label>
    <textarea id="answerContent" class="form-control" rows="5">
        <c:choose>
            <c:when test="${inquiry.noticeInquiriesYn == 'Y'}">${inquiry.noticeInquiriesCont}</c:when>
            <c:otherwise></c:otherwise>
        </c:choose>
    </textarea>
</div>

<div class="d-flex justify-content-end gap-2 mt-3">
    <button id="answerButton" data-notice-no="${inquiry.noticeNo}" 
        class="btn <c:choose><c:when test='${inquiry.noticeInquiriesYn == "Y"}'>btn-primary</c:when><c:otherwise>btn-success</c:otherwise></c:choose>">
        <c:choose>
            <c:when test="${inquiry.noticeInquiriesYn == 'Y'}">답변 수정</c:when>
            <c:otherwise>답변 등록</c:otherwise>
        </c:choose>
    </button>
    <button id="deleteInquiry" data-notice-no="${inquiry.noticeNo}" class="btn btn-danger">문의 삭제</button>
</div>
<script src="${pageContext.request.contextPath}/resources/ckeditor5-36.0.0/packages/ckeditor5-build-classic/build/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/admin/help/inquiry.js"></script>
