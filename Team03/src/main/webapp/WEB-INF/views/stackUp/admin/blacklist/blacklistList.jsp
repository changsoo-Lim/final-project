<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/resources/css/admin/blacklist/blacklistList.css"> 

<h4>블랙리스트 리스트</h4>

<div class="container">

	<br>
	<div class="d-flex justify-content-between align-items-center mb-3">
	     <!-- 총 게시물 수 표시 -->
	    <h6 class="total-count"><p>총 ${totalCount} 건</p> </h6>
	    <div class="search-area d-flex align-items-center gap-2 ">
	        <form:select path="condition.searchType" class="form-select w-auto">
	            <form:option value="blacklistReason" label="정지사유"></form:option>
	            <form:option value="memId" label="아이디"></form:option>
	        </form:select>
	        <form:input path="condition.searchWord" class="form-control w-auto"  />
	        <button type="submit" class="btn btn-primary search-btn">검색</button>
	    </div>
	 </div>
<form id="deleteForm" action="${pageContext.request.contextPath}/admin/blacklist/deleteMultiple" method="post">
	<table class="table table-hover table-bordered align-middle" >
	    <thead class="table-light">
	        <tr>
	            <th><input type="checkbox" id="checkAll"   /></th>
	            <th>번호</th>
	            <th>아이디</th>
	            <th>정지사유</th>
	            <th>정지일시</th>
	        </tr>
	    </thead>
	    <tbody>
	        <c:if test="${not empty BlacklistList }">
	            <c:forEach items="${BlacklistList }" var="black">
	                <tr>
	                    <td>
	                    	<input type="checkbox" name="blacklistNo"  value="${black.blacklistNo}" class="delete-checkbox"/></td>
	                    <td>${black.rnum }</td>
	                    <td>${black.memId}</td>
	                    <td>${black.blacklistReason}</td>
	                    <td>${black.blacklistDt }</td>
	                </tr>
	            </c:forEach>
	        </c:if>
	        <c:if test="${empty BlacklistList }">
	            <tr>
	                <td colspan="6">블랙리스트 회원 없음.</td>
	            </tr>
	        </c:if>
	    </tbody>
	    <tfoot>
	        <tr>
	            <td colspan="6">
	                <div class="paging-area">
	                    ${pagingHTML }
	                </div>
					<div class="text-right">
						<button type="submit" form="deleteForm" class="btn btn-danger"  >블랙 해제</button>
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
<script src="${pageContext.request.contextPath}/resources/js/admin/blacklist/blacklistDelete.js"></script>
