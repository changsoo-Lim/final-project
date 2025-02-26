<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/freelancer/freeOfferList.css" />
<article>
	<h3 class="mb-4">받은 제안</h3>
</article>

<!--tabs-->
<ul class="nav nav-tabs" id="myTab" role="tablist">
	<li class="nav-item" role="presentation">
		<button class="nav-link active" id="home-tab" data-bs-toggle="tab"
			data-bs-target="#home-tab-pane" type="button" role="tab"
			aria-controls="home-tab-pane" aria-selected="true">
			포지션 제안
		</button>
	</li>
	<li class="nav-item" role="presentation">
		<button class="nav-link" id="profile-tab" data-bs-toggle="tab"
			data-bs-target="#profile-tab-pane" type="button" role="tab"
			aria-controls="profile-tab-pane" aria-selected="false">
			프리랜서 제안
		</button>
	</li>
	<li class="nav-item" role="presentation">
		<button class="nav-link" id="contact-tab" data-bs-toggle="tab"
			data-bs-target="#contact-tab-pane" type="button" role="tab"
			aria-controls="contact-tab-pane" aria-selected="false">
			이력서 열람
		</button>
	</li>
</ul>
<div class="tab-content" id="myTabContent">
	<div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0">
		<div class="tab-body">
			<div class=tab-top>
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
			</div>
			<table class="table">
				<thead>
					<tr class="table-primary">
						<th scope="col"><input type="checkbox"  class="cbx_chkAll"></th>
						<th scope="col">기업명</th>
						<th scope="col">공고명</th>
						<th scope="col">모집분야</th>
						<th scope="col">모집기간</th>
						<th scope="col">제안</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty positionList}">
						<c:forEach items="${positionList}" var="list">
							<tr>
								<td class="td-row"><input type="checkbox" class="chk"/></td>
								<td class="truncate td-compNm">${list.company.compNm}</td>
								<td class="truncate td-empNo">
									<a href="/employ/${list.employ.employNo}">${list.employ.employTitle}</a>
								</td>
								<td class="truncate td-fieldNm">${list.field.filedNm}</td>
								<td class="td-empEd">~ ${list.employ.employEd}</td>
								<td class="td-sttus">
									<c:choose>
										<c:when test="${list.positionStatusCd eq 'OF01'}">
											<span class="status-box accept-btn" data-procSttus="AP02" data-fieldNo="${list.field.filedNo}">수락하기</span>
										</c:when>
										<c:when test="${list.positionStatusCd eq 'OF02'}">
											<span class="status-box completed-btn">지원완료</span>
										</c:when>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty positionList}">
						<tr>
							<td colspan="6">받은 제안이 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab" tabindex="0">
		<div class="tab-body">
			<div class=tab-top>
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
			</div>
			<table class="table table-hover">
				<thead>
					<tr class="table-primary">
						<th scope="col"><input type="checkbox"  class="cbx_chkAll"></th>
						<th scope="col">프로젝트</th>
						<th scope="col">스킬</th>
						<th scope="col">예상 금액</th>
						<th scope="col">시작 · 마감 예정일</th>
						<th scope="col">제안 상태</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty memFreeOfferList}">
						<c:forEach items="${memFreeOfferList}" var="list">
							<tr>
								<th scope="row"><input type="checkbox" class="chk"/></th>
								<td>
									<a href="${pageContext.request.contextPath }/project/${list.project.projectNo}/member">${list.project.projectName}</a>
								</td>
								<td>${list.project.projectSkills}</td>
								<td>${list.project.projectSalary} 만원</td>
								<td>${list.project.projectSdt} ~ ${list.project.projectEdt}</td>
								<td>${list.freeOfferStatus}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<c:if test="${empty memFreeOfferList}">
				제안이 없습니다.
			</c:if>
		</div>
	</div>
	<div class="tab-pane fade" id="contact-tab-pane" role="tabpanel" aria-labelledby="contact-tab" tabindex="0">
		<div class="tab-body">
			<div class=tab-top>
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
			</div>
		</div>
	</div>
	<div class="paging-area">${pagingHTML }</div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/apply/myApplyList.js"></script>