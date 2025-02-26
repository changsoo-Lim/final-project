<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<!DOCTYPE html>


<h3 class="mb-3">기업 공고 리스트</h3>
<div class="d-flex justify-content-between align-items-center mb-3">
	<h6 class="total-count">총 ${employList[0].totalCnt} 건</h6>
	<div class="search-area d-flex align-items-center gap-2">
		<form:select path="condition.searchType" class="form-select w-auto">
			<form:option value="noticeTitle" label="제목"></form:option>
			<form:option value="noticeCont" label="본문"></form:option>
		</form:select>
		<form:input path="condition.searchWord" class="form-control w-auto"
			placeholder="검색어 입력" />
		<button type="submit" class="btn btn-primary search-btn">검색</button>
	</div>
</div>
<form id="searchForm"
	action="${pageContext.request.contextPath}/employ/list/comp" method="get"
	style="display: none;">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input type="text" name="page" placeholder="page" />
</form>
<div class="table-responsive">
	<form id="deleteForm"
		action="${pageContext.request.contextPath}/admin/faq/deleteMultiple"
		method="post">
		<table
			class="table table-hover table-bordered text-center align-middle">
			<thead class="table-light">
				<tr>
					<th scope="col" class="col-1">번호</th>
					<th scope="col" class="col-5">공고명</th>
					<th scope="col" class="col-3">공고 일자</th>
					<th scope="col" class="col-2">지원자 수</th>
					<th scope="col" class="col-2">상태</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty employList }">
						<c:forEach var="item" items="${employList }">
							<tr>
								<td>${item.rnum }</td>
								<td class="text-start text-truncate"><a
									href="${pageContext.request.contextPath}/employ/detail/${item.employNo}"
									class="text-decoration-none text-dark"> ${item.employTitle }
								</a></td>
								<td>${item.employSd}&nbsp;~&nbsp;${item.employEd}</td>
								<td>${item.applyCnt }</td>
								<td>
								    <c:if test="${item.employDday >= 0}">
								        <span class="badge bg-success p-2" style="font-size: 14px; min-width: 80px; text-align: center;">
								            모집중
								        </span>
								    </c:if>
								    <c:if test="${item.employDday < 0}">
								        <span class="badge bg-danger p-2" style="font-size: 14px; min-width: 80px; text-align: center;">
								            종료
								        </span>
								    </c:if>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6" class="text-center py-3">등록된 공고가 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</form>
</div>
<div class="paging-area">
    ${pagingHTML}
</div>