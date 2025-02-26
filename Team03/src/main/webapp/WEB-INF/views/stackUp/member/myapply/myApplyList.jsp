<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="<c:url value="/resources/css/member/myapply/myApplyList.css" />">
<div class="apply-container">
	<h3 class="mb-4">입사지원 내역</h3>
	<!-- 테이블 영역 -->
	<div>
		<div class="list-area">
			<div class="d-flex justify-content-between align-items-center mb-3">
				<div class="total-count">
					<span>총 ${totalCount} 건</span>
					<!-- 서버에서 전달받은 총 게시글 수를 표시 -->
				</div>
				<div class="search-area d-flex align-items-center gap-2">
					<form:select path="condition.searchType" class="form-select w-auto">
						<form:option value="compNm" label="기업명"></form:option>
						<form:option value="employTitle" label="공고제목"></form:option>
						<form:option value="filedNm" label="모집분야명"></form:option>
					</form:select>
					<form:input path="condition.searchWord" class="form-control w-auto"
						placeholder="검색어 입력" />
					<button type="submit" class="btn btn-primary search-btn">검색</button>
				</div>
			</div>
			<table class="table table table-hover text-center align-middle">
				<thead>
					<tr class="table-primary">
						<th scope="col">지원 일자</th>
						<th scope="col">기업명</th>
						<th scope="col">공고명</th>
						<th scope="col">모집명</th>
						<th scope="col">절차상태</th>
						<th scope="col">열람여부</th>
						<th scope="col">지원취소</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty myApplyList }">
						<c:forEach items="${myApplyList }" var="myApply">
							<tr>
								<td>${myApply.formattedAppDate }</td>
								<td class="truncate">${myApply.compNm }</td>

								<td class="truncate">
									<a href="${pageContext.request.contextPath}/employ/detail/${myApply.employNo }"> 
										${myApply.employTitle }
									</a>
								</td>
								<td class="truncate">${myApply.filedNm }</td>

								<td>
									<c:forEach items="${myApplyCodeList}" var="myApplyCode">
										<c:if test="${myApplyCode.codeCd eq myApply.appProcStatus }">
				                   			${myApplyCode.codeNm}
				                   		</c:if>
									</c:forEach>
								</td>
								<td><c:choose>
										<c:when test="${myApply.appOpenYn eq 'Y'}">
											<span class="status-box read">열람</span>
										</c:when>
										<c:otherwise>
											<span class="status-box not-read">미열람</span>
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${myApply.appCancelYn eq 'N'}">
											<span class="status-box cancel-btn"
												data-applyNo="${myApply.applyNo}">취소</span>
										</c:when>
										<c:otherwise>
											<span class="status-box cancel">취소됨</span>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty myApplyList }">
						<tr>
							<td colspan="8">지원 내역 없음.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		<div class="paging-area">${pagingHTML }</div>
	</div>
</div>
<form id="searchForm"
	action="${pageContext.request.contextPath}/member/myapply" method="get"
	style="display: none">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input type="text" name="page" placeholder="page" />
</form>
<script>
	const
</script>
<script src="${pageContext.request.contextPath}/resources/js/apply/myApplyList.js"></script>
