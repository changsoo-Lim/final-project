<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<h3 class="mb-3">회원 리스트 </h3>
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
	action="${pageContext.request.contextPath}/adminmember/list" method="get" style="display: none;">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input type="text" name="page" placeholder="page" />
</form>
<div class="table-responsive">
	<form id="deleteForm" action="${pageContext.request.contextPath}/adminmember/list" method="post">
		<table class="table table-hover table-bordered text-center align-middle">
			<thead class="table-light">
				<tr>
					<th><input type="checkbox" id="checkAll"   /></th>
					<th scope="col" class="col-1">번호</th>
					<th scope="col" class="col-3">회원명</th>
					<th scope="col" class="col-3">아이디</th>
					<th scope="col" class="col-3">회원가입 일</th>
					<th scope="col" class="col-1">상태</th>
					<th scope="col" class="col-1">관리</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty memList}">
						<c:forEach var="user" items="${memList}">
							<tr>
								<td>
		                    		<input type="checkbox" name="memId"  value="${user.member.memId}" class="delete-checkbox"/>
	                    		</td>
								<td>${user.member.rnum }</td>
								<td class="text-center text-truncate">
									<a href="${pageContext.request.contextPath}/talent/${user.member.memId}/detail" class="text-decoration-none text-dark">
										${user.member.memNm }
									</a>
								</td>
								<td>${user.member.memId }</td>
								<td>${user.userJoindt }</td>
								<td>${user.member.memStatus }</td>
								<td><button type="button" form="deleteForm" class="btn btn-info"  >수정</button></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6" class="text-center py-3">회원이 없습니다.</td>
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
	<div class="text-right">
		<button type="button" form="deleteForm" class="btn btn-danger"  >삭제</button>
	</div>
</div>
