<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<h3 class="mb-3">관심등록한 인재 리스트 </h3>
<div class="d-flex justify-content-between align-items-center mb-3">
	<h6 class="total-count">총 ${memList[0].total} 건</h6>
<div class="search-area d-flex align-items-center gap-2">
		<form:select path="condition.searchType" class="form-select w-auto">
			<form:option value="noticeTitle" label="제목"></form:option>
			<form:option value="noticeCont" label="본문"></form:option>
		</form:select>
		<form:input path="condition.searchWord" class="form-control w-auto" placeholder="검색어 입력" />
		<button type="button" class="btn btn-primary search-btn">검색</button>
	</div>
</div>
<form id="searchForm"
	action="${pageContext.request.contextPath}/interComp/memberlist" method="get" style="display: none;">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input type="text" name="page" placeholder="page" />
</form>
<div class="table-responsive">
	<form id="deleteForm" action="${pageContext.request.contextPath}/interComp/memberlist" method="post">
		<table class="table table-hover table-bordered text-center align-middle">
			<thead class="table-light">
				<tr>
					<th scope="col" class="col-1">번호</th>
					<th scope="col" class="col-1">회원명</th>
					<th scope="col" class="col-3">희망 직무</th>
					<th scope="col" class="col-5">희망 근무지역</th>
					<th scope="col" class="col-5">관심등록 일시</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty memList}">
						<c:forEach var="mem" items="${memList}">
							<tr>
								<td>${mem.rnum }</td>
								<td class="text-start text-truncate">
									<a href="${pageContext.request.contextPath}/talent/${mem.memId}/detail" class="text-decoration-none text-dark">
										${mem.member.memNm }
									</a>
								</td>
								<td>${mem.member.workCondList[0].workCondType}</td>
								<td>
									<c:forEach items="${mem.member.workCondList[0].workCity }" var="item" varStatus="i">
										${item.sidoCd} &nbsp; ${item.gugunCd} <c:if test="${! i.last }">,</c:if>
									</c:forEach>
										
								</td>
								<td>${mem.interCompDt }</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6" class="text-center py-3">관심등록한 회원이 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</form>
</div>
<div class="d-flex justify-content-between align-items-center mt-3">
	<div class="flex-grow-1 d-flex justify-content-center">
		${pagingHTML}
	</div>
</div>
