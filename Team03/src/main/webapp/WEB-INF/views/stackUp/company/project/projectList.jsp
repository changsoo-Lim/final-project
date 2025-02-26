<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/project/projectList.css" />












<h3 class="mb-3">포지션 제안 리스트</h3>
<div class="d-flex justify-content-between align-items-center mb-3">
	<h6 class="total-count">총 ${proList[0].total} 건</h6>
	<div class="search-area d-flex align-items-center gap-2">
		<form:select path="condition.searchType" class="form-select w-auto">
			<form:option value="noticeTitle" label="제목"></form:option>
			<form:option value="noticeCont" label="본문"></form:option>
		</form:select>
		<form:input path="condition.searchWord" class="form-control w-auto"
			placeholder="검색어 입력" />
		<button type="button" class="btn btn-primary search-btn">검색</button>
		<div class="text-right">
			<a class="btn btn-info"
				href="${pageContext.request.contextPath }/project/form">프로젝트 등록</a>
		</div>
	</div>
</div>
<form id="searchForm"
	action="${pageContext.request.contextPath}/project" method="get"
	style="display: none;">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input type="text" name="page" placeholder="page" />
</form>
<div class="table-responsive">
	<form id="deleteForm"
		action="${pageContext.request.contextPath}/project" method="post">
		<table
			class="table table-hover table-bordered text-center align-middle">
			<thead class="table-light">
				<tr>
					
                 	<th scope="col" class="col-0"><input type="checkbox" id="checkAll"/></th>
					<th scope="col" class="col-1">번호</th>
					<th scope="col" class="col-4">프로젝트명</th>
					<th scope="col" class="col-2">공고 종료일</th>
					<th scope="col" class="col-2">프로젝트 시작일</th>
					<th scope="col" class="col-1">상태</th>
					<th scope="col" class="col-1">수락 인원</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty proList }">
					<c:forEach items="${proList }" var="proVO">
						<tr>
							<td>
                  			<input type="checkbox" name="memId"  value="${proVO.projectNo}" class="delete-checkbox"/>
                 			</td>
							<td>${proVO.rnum }</td>
							<td>
							<a href="${pageContext.request.contextPath}/project/${proVO.projectNo}/detail" class="text-decoration-none text-dark">
										${proVO.projectName }</a>
							</td>
							<td>${proVO.projectApplyEdt }</td>
							<td>${proVO.projectSdt }</td>
							<td>${proVO.projectStatus }</td>
							<td>${proVO.offerCnt } 명</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty proList }">
					<tr>
						<td colspan="7"><h2>등록된 프로젝트가 없습니다</h2>
							<p>새로운 프로젝트를 등록하고 시작해보세요!</p></td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</form>
</div>
<div class="d-flex justify-content-between align-items-center mt-3">
	<div class="flex-grow-1 d-flex justify-content-center">
		${pagingHTML}</div>
	<div class="text-right">
		<button type="button" class="btn btn-primary edit-btn"
			data-project-id="${proVO.projectNo}">수정</button>
		<button type="button" class="btn btn-danger delete-btn"
			data-project-id="${proVO.projectNo}">삭제</button>
	</div>
</div>

<script
	src="${pageContext.request.contextPath }/resources/js/project/projectList.js"></script>

