<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/scrap/memScrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/scrap.css" />

<div class="tab-body">
	<div class=tab-top>
		<div class="d-flex justify-content-between align-items-center mb-3">
			<div class="total-count">
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
		<c:if test="${not empty empScrap}">
			<c:forEach var="emp" items="${empScrap}">
				<tr class="emp-tr">
					<td class="emp-td">
					  	<ul class="view-ul">
							<li class="view-li">
								<div class="view-first">
									<div class="tab-company">
										<span><a href="<c:url value='/company/${emp.compId}'/>">${emp.compNm}</a></span>
										<button class="inter-btn" data-pk-no="${emp.compId}" data-scrap-status="${emp.interCompDel}">
											<i class="bi ${empty emp.interCompDel || emp.interCompDel eq 'Y' ? 'bi-heart' : 'bi-heart-fill'}"></i>
										</button>
									</div>
								</div>
								<div class="view-mid">
									<div class="tab-subject">
										<a href="<c:url value='/employ/detail/${emp.employNo}'/>">${emp.employTitle}</a>
										<button class="scrap-btn" data-pk-no="${emp.employNo}" data-scrap-status="${emp.empscrapDel}">
											<i class="bi ${empty emp.empscrapDel || emp.empscrapDel eq 'Y' ? 'bi-star' : 'bi-star-fill'}"></i>
										</button>
									</div>
									<div class="tab-jobs">
										<c:forTokens var="exp" items="${emp.employExperience}" delims="," varStatus="sttus">
											<em> 
												<c:choose>
													<c:when test="${sttus.first and exp eq 'mt01'}">
				                                       신입 /
				                                   </c:when>
													<c:when test="${exp eq 'mt01'}">
				                                       신입
				                                   </c:when>
													<c:when test="${exp eq 'mt02'}">
				                                       경력
				                                   </c:when>
													<c:when test="${exp eq 'all'}">
				                                       (경력무관)
				                                   </c:when>
												</c:choose>
											</em>
										</c:forTokens>
										<em>&nbsp;|&nbsp;</em>
										<em>${emp.employEducationNm}</em>
										<em>&nbsp;|&nbsp;</em>
										<c:forEach items="${emp.fieldList}" var="field" varStatus="sttus">
											<c:if test="${sttus.first}">
												<em>${field.filedRegionNm}&nbsp;${field.filedRegionGunguNm}</em>
											</c:if>
										</c:forEach>
									</div>
								</div>
								<div class="view-last">
									<c:choose>
										<c:when test="${emp.employDday > 0}">
											<span class="d-day">D-${emp.employDday}</span>
										</c:when>
										<c:when test="${emp.employDday == 0}">
											<span class="d-day">오늘 마감</span>
										</c:when>
									</c:choose>
									<c:if test="${emp.employApplication eq 'APLC01'}">
										<button class="btn btn-primary apply-btn stackUp-btn" data-bs-toggle="modal" data-bs-target="#apply-modal${emp.employNo}">
											바로 지원
										</button>
									</c:if>
								</div>
							</li>
						</ul>
					</td>
				</tr>
		
			<!--공고 지원 Modal-->
			<div class="modal fade" id="apply-modal${emp.employNo}" tabindex="-1" aria-labelledby="apply-modal" aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
					<div class="modal-content">
						<div class="modal-header">
							<h1 class="modal-title fs-5">${emp.compNm} 입사지원</h1>
							<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="modal-body">
							<label for="fieldSelect${emp.employNo}">모집 분야</label>
							<select id="fieldSelect${emp.employNo}" class="form-select">
								<option value="">모집 분야 선택</option>
								<c:forEach var="field" items="${emp.fieldList}">
									<option value="${field.filedNo}">${field.filedNm}</option>
								</c:forEach>
							</select>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary submit-btn" data-employNo="${emp.employNo}">바로지원</button>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>
</table>
<c:if test="${empty empScrap}">
	스크랩한 공고가 없습니다.
</c:if>
</div>
<div class="paging-area">${pagingHTML }</div>
<form id="searchForm" action="${pageContext.request.contextPath}/employScrap" method="get" style="display: none">
	<form:input path="condition.searchType" placeholder="searchType" />
	<form:input path="condition.searchWord" placeholder="searchWord" />
	<input type="text" name="page" placeholder="page" />
</form>
<script src="${pageContext.request.contextPath}/resources/js/member/mypage.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/scrap.js"></script>