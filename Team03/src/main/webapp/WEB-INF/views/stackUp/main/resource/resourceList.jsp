<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/admin/resource/adminResourceList.css"> 
<div class="container">

	<div>
		<h3 class="Title">한 번에 합격하는 이력서 양식</h3>
		<p>최신 업데이트 되는 기업/직무별 이력서양식!</p>
	</div>
	<div class="search-area d-flex justify-content-end">
					 <!-- 총 게시물 수 표시 -->
						<form:select path="condition.searchType" class="form-select w-auto me-2">
							<form:option value="resourceTitle" label="제목"></form:option>
						</form:select>
						<form:input path="condition.searchWord" class="form-control w-auto me-2" placeholder="검색어 입력"/>
						<button type="submit" class="btn btn-primary search-btn" >검색</button>
				</div>
<!-- 	<div class="total-count"> -->
<%--         <p>총 ${totalCount} 건</p>   --%>
<!--     </div> -->
<hr>
	<div class="row gy-3">
    <c:if test="${not empty resourceList}">
        <c:forEach items="${resourceList}" var="resource" varStatus="status">
            <div class="col-lg-3 ${status.index < 3 && (param.page == 1 || param.page == null) ? 'best-highlight' : ''}">
                <a href="<c:url value='/main/resource/${resource.resourceNo}'/>" class="glightbox rounded-3">
                    <div class="rounded-3 card-lift">
                   <!-- ${resource }  resource -> file -> List<file_detail> -> 파일명  -->
                        <img src='<c:url value="/images/resource/${resource.file.fileDetails[0].streFileNm}" />' class="custom-image-style">
                    </div>
                </a>
                <c:choose>
                    <c:when test="${status.index < 3 && (param.page == 1 || param.page == null)}">
                        <span class="best-badge">👍Best</span> <!-- Best 문구 -->
                    </c:when>
                </c:choose>
                <a class="resource-title" href="<c:url value='resource/${resource.resourceNo}'/>">${resource.resourceTitle}</a>
            </div>
        </c:forEach>
    </c:if>
	
	</div>

	<div class="paging-area">
		${pagingHTML}
	</div>
<hr>
	<form id="searchForm" style="display:none">
		<form:input path="condition.searchType" placeholder="searchType"/>
		<form:input path="condition.searchWord" placeholder="searchWord"/>
		<input type="text" name="page" placeholder="page"/>
	</form>
</div>




