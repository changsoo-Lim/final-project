<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 제목 -->
<div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="mb-3">공지사항 상세조회</h3>
    <a href="${pageContext.request.contextPath}/admin/notice" class="btn btn-secondary">목록</a>
</div>

<!-- 상세 내용 테이블 -->
	<table class="table table-bordered">
	    <tr>
	        <th class="w-25 bg-light text-center">번호</th>
	        <td>${notice.noticeNo}</td>
	    </tr>
	    <tr>
	        <th class="bg-light text-center">제목</th>
	        <td>${notice.noticeTitle}</td>
	    </tr>
	    <tr>
	        <th class="bg-light text-center">등록일</th>
	        <td>${notice.formattedNoticeDt}</td>
	    </tr>
	    <tr>
	        <th class="bg-light text-center">고정 여부</th>
	        <td>
	            <c:choose>
	                <c:when test="${notice.noticePinned eq 'Y'}">고정</c:when>
	                <c:otherwise>일반</c:otherwise>
	            </c:choose>
	        </td>
	    </tr>
	    <tr>
	        <th class="bg-light text-center">작성자</th>
	        <td>${notice.memId}</td>
	    </tr>
	    <tr>
	        <th class="bg-light text-center">첨부파일</th>
	        <td>
	            <c:if test="${not empty notice.file.fileDetails}">
	                <ul class="list-unstyled mb-0">
	                    <c:forEach items="${notice.file.fileDetails}" var="fd" varStatus="vs">
	               <c:url value='/${noticeNo}/file/${fd.atchFileNo}/${fd.fileSn}' var="downUrl"/>
	               <a href="${downUrl}">${fd.orignlFileNm} (${fd.fileFancysize})</a>
	               ${not vs.last ? '|' : ''}
	           </c:forEach>
	                </ul>
	            </c:if>
	            <c:if test="${empty notice.file.fileDetails}">
	                첨부파일 없음
	            </c:if>
	        </td>
	    </tr>
	    <tr>
	        <th class="bg-light text-center">내용</th>
	        <td style="white-space: pre-wrap;">${notice.noticeCont}</td>
	    </tr>
	</table>

<!-- 하단 버튼 -->
<div class="d-flex justify-content-end mt-3 gap-2">
    <a href="${pageContext.request.contextPath}/admin/notice/${notice.noticeNo}/edit" class="btn btn-warning">수정</a>
    <form action="${pageContext.request.contextPath}/admin/notice/${notice.noticeNo}/delete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
        <button type="submit" class="btn btn-danger">삭제</button>
    </form>
</div>
