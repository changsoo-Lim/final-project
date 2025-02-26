<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet" href="<c:url value="/resources/css/company/test/testList.css" />">
<h2 class="mb-4">검사 및 테스트 관리</h2>
<div class="tabs mb-4">
	<ul class="nav nav-tabs" id="myTab" role="tablist">
		<c:forEach var="code" items="${testCode}">
		  <li class="nav-item" role="presentation">
		    <button 
			    class="tab-button nav-link ${testCd == code.codeCd ? 'active' : '' }" 
			    id="${code.codeCd}-tab" 
			    data-bs-toggle="tab" 
			    data-testCd="${code.codeCd}" 
			    data-bs-target="#${code.codeCd}-tab-pane" 
			    type="button" 
			    role="tab" 
			    aria-controls="${code.codeCd}-tab-pane" 
			    aria-selected="${testCd == code.codeCd ? 'true' : 'false'}">
			    ${code.codeNm}
			</button>
		  </li>
	  </c:forEach>
	</ul>
</div>
<div id="tab-content">
	<h3 class="mb-3">
		<c:forEach var="code" items="${testCodes}">
	        <c:if test="${testCd == code.codeCd}">
	            ${code.codeNm}
	        </c:if>
	    </c:forEach>
	</h3>
	<div class="d-flex justify-content-between align-items-center mb-3">
		<h6 class="total-count">총 ${totalCount} 건</h6>
		<div class="search-area d-flex align-items-center gap-2">
			<form:select path="condition.searchType" class="form-select w-auto">
				<form:option value="compId" label="아이디"></form:option>
				<form:option value="testNm" label="테스트명"></form:option>
			</form:select>
			<form:input path="condition.searchWord" class="form-control w-auto" placeholder="검색어 입력" />
			<button type="submit" class="btn btn-primary search-btn">검색</button>
		</div>
	</div>
	<form id="deleteForm" action="<c:url value="/company/test/deleteMultiple" />" method="post">
		<div class="table-responsiveNo">
			<input type="hidden" name="_method" value="DELETE" />
			<table class="table table-hover table-bordered text-center align-middle">
				<thead class="table-light">
					<tr>
						<th scope="col"><input type="checkbox" id="checkAll" class="cbx_chkAll" /></th>
						<th scope="col" class="col-1">번호</th>
						<th scope="col" class="col-7">시험명</th>
						<th scope="col" class="col-2">시험종류</th>
						<th scope="col" class="col-2">등록아이디</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty testList}">
							<c:forEach var="test" items="${testList}">
								<tr>
									<td><input type="checkbox" name="testNo"
										value="${test.testNo}" class="delete-checkbox chk" /></td>
									<td>${test.rnum}</td>
									<td class="text-start text-truncate" title="${test.testNm}">
										<a href="<c:url value="/company/test/${test.testNo}" />" class="text-decoration-none text-dark">
											${test.testNm} 
										</a>
									</td>
									<td>
										<c:forEach var="code" items="${testCode}">
									        <c:if test="${test.testCd == code.codeCd}">
									            ${code.codeNm}
									        </c:if>
									    </c:forEach>
									</td>
									<td>
										${test.compId}
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6" class="text-center py-3">등록된 테스트가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<div class="d-flex justify-content-between align-items-center mt-3">
			<div class="flex-grow-1 d-flex justify-content-center">
				${pagingHTML}
			</div>
			<div class="d-flex gap-2">
				<a href="<c:url value="/company/test/form" />" class="btn btn-primary">테스트 등록</a>
				<button type="submit" form="deleteForm" class="btn btn-danger" id="deleteSelected">선택 삭제</button>
			</div>
		</div>
	</form>
</div>

<form id="searchForm" action="<c:url value="/company/test/list" />" method="get" style="display:none">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input name="testCd" id="testCd" value="${testCd}" placeholder="testCd X" />
	<input type="text" name="page" placeholder="page" />
</form>

<script src='<c:url value="/resources/js/company/test/testList.js" />'></script>