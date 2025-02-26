<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/resources/css/admin/board/adminboardList.css"> 
<!-- 관리자 커뮤니티 -->    
<div class="container">

<h2>커뮤니티 관리</h2>
	<br>
	<div class="d-flex justify-content-between align-items-center mb-3">
	     <!-- 총 게시물 수 표시 -->
	    <h6 class="total-count"><p>총 ${totalCount} 건</p> </h6>
	    <div class="search-area d-flex align-items-center gap-2 ">
	        <form:select path="condition.searchType" class="form-select w-auto">
	            <form:option value="boardTitle" label="게시물제목"></form:option>
	            <form:option value="memId" label="아이디"></form:option>
	        </form:select>
	        <form:input path="condition.searchWord" class="form-control w-auto"  />
	        <button type="submit" class="btn btn-primary search-btn">검색</button>
	    </div>
	 </div>
<form id="deleteForm" action="${pageContext.request.contextPath}/admin/board/deleteMultiple" method="post">
	<table class="table table-hover table-bordered align-middle" >
	    <thead class="table-light">
	        <tr>
	            <th><input type="checkbox" id="checkAll"   /></th>
	            <th>번호</th>
	            <th>제목</th>
	            <th>회원 아이디</th>
	            <th>작성일시</th>
	        </tr>
	    </thead>
	    <tbody>
	        <c:if test="${not empty adminBoardList }">
	            <c:forEach items="${adminBoardList }" var="board">
	                <tr>
	                    <td>
	                    	<input type="checkbox" name="boardNo"  value="${board.boardNo}" class="delete-checkbox"/></td>
	                    <td>
	                        <a href="<c:url value='board/${board.boardNo }'/>">${board.rnum }</a>
	                    </td>
	                    <td>
	                        <!-- 파일 첨부 여부 확인 -->
	                        <c:if test="${not empty board.atchFileNo}">
	                            <!-- 파일 첨부 아이콘을 출력 -->
	                            <div class="paperclip-icon">
	                                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-paperclip text-primary" viewBox="0 0 16 16">
	                                    <path d="M4.5 3a2.5 2.5 0 0 1 5 0v9a1.5 1.5 0 0 1-3 0V5a.5.5 0 0 1 1 0v7a.5.5 0 0 0 1 0V3a1.5 1.5 0 1 0-3 0v9a2.5 2.5 0 0 0 5 0V5a.5.5 0 0 1 1 0v7a3.5 3.5 0 1 1-7 0V3z" />
	                                </svg>
	                            </div>
	                        </c:if>
	                        <a href="<c:url value='board/${board.boardNo }'/>">${board.boardTitle}</a>
	                    </td>
	                    <td>${board.memId }</td>
	                    <td>${board.timeDifference}</td>
	                </tr>
	            </c:forEach>
	        </c:if>
	        <c:if test="${empty adminBoardList }">
	            <tr>
	                <td colspan="5">글 없음.</td>
	            </tr>
	        </c:if>
	    </tbody>
	    <tfoot>
	        <tr>
	            <td colspan="5">
	                <div class="paging-area">
	                    ${pagingHTML }
	                </div>
					<div class="text-right">
						<button type="submit" form="deleteForm" class="btn btn-danger"  >선택 삭제</button>
					    <a href="${pageContext.request.contextPath }/admin/board/adminBoardForm" class="btn btn-primary">새글 등록</a>
					</div>
	            </td>
	        </tr>
	    </tfoot>
	
	</table>
</form>   
<form id="searchForm" style="display:none">
    <form:input  path="condition.searchType" placeholder="searchType"/>
    <form:input  path="condition.searchWord" placeholder="searchWord"/>
    <input type="text" name="page" placeholder="page"/>

</form>
</div>
<script src="${pageContext.request.contextPath}/resources/js/admin/board/deleteSelected.js"></script>
