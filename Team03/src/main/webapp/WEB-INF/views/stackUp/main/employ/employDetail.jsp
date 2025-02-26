<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/employ/employDetail.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/scrap.css" />

<section class="section-employ">
	<div class="job-container">
		<!-- 상단 회사 정보 -->
		<div class="comp-header">
			<div class="comp-header-left">
				<div class="comp-header-left-top">
					<div class="comp-name"><a href="<c:url value='/company/${employ.compId}'/>">${employ.compNm}</a></div>
					<button class="inter-btn" data-pk-no="${employ.compId}" data-scrap-status="${employ.interCompDel}">
						<i class="bi ${empty employ.interCompDel || employ.interCompDel eq 'Y' ? 'bi-heart' : 'bi-heart-fill'}"></i>
					</button>
				</div>
				<!-- 채용공고 제목 -->
				<div class="job-title">${employ.employTitle}</div>
			</div>
			<div class="comp-header-right">
				<button class="scrap-btn" data-pk-no="${employ.employNo}" data-scrap-status="${employ.empscrapDel}">
					<i class="bi ${empty employ.empscrapDel || employ.empscrapDel eq 'Y' ? 'bi-star' : 'bi-star-fill'}"></i>
				</button>
				<c:choose>
					<c:when test="${employ.employApplication eq 'APLC01'}">
						<button class="btn btn-primary btn-lg apply-btn stackUp-btn" data-bs-toggle="modal" data-bs-target="#apply-modal">
							바로 지원
						</button>
					</c:when>
					<c:when test="${employ.employApplication eq 'APLC02'}">
						<button class="btn btn-primary btn-lg apply-btn url-btn" onclick="location.href='${employ.employUrl}'">
							${employ.employApplicationNm}
						</button>
					</c:when>
				</c:choose>
			</div>
		</div>
		<!-- 공고 상세정보 -->
		<div class="job-meta">
			<!-- 왼쪽 영역 -->
			<div class="meta-left">
				<div class="meta-item">
					고용형태 : <span>
								<c:forTokens var="type" items="${employ.employTypeNm}" delims="," varStatus="sttus">
									<c:if test="${sttus.first}">
										${type}
									</c:if>
									<c:if test="${sttus.index > 1}">
							            외 ${sttus.index - 1}건
							        </c:if>
								</c:forTokens>
							</span>
				</div>
				<div class="meta-item">
					근무지역 : <span>
								<c:forEach items="${employ.fieldList}" var="field" varStatus="sttus">
									<c:if test="${sttus.first}">
										${field.filedRegionNm}&nbsp;${field.filedRegionGunguNm}
									</c:if>
									<c:if test="${sttus.index > 1}">
							            외 ${sttus.index - 1}건
							        </c:if>
								</c:forEach>
							</span>
				</div>
				<div class="meta-item">
					급여조건 : <span>
								<c:choose>
									<c:when test="${employ.employSalaryYn eq 'Y'}">
										회사내규 또는 협의
									</c:when>
									<c:otherwise>
										${employ.employSalaryNm}
									</c:otherwise>
								</c:choose>
							</span>
				</div>
			</div>
			<!-- 오른쪽 영역 -->
			<div class="meta-right">
				<div class="meta-item">
					경력 : <span>
							<c:forTokens var="exp" items="${employ.employExperience}" delims="," varStatus="sttus">
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
                                       경력무관
                                   </c:when>
								</c:choose>
							</c:forTokens>
						</span>
				</div>
				<div class="meta-item">
					학력 : <span>${employ.employEducationNm}</span>
				</div>
			</div>
		</div>
		<div class="detail-container">
			<div class="detail-img">
				<img class="detail-img" src="${pageContext.request.contextPath}/resources/images/empImg.png">
			</div>
			<div class="detail-field">
				<div class="field-header">
					<img class="field-header-img" src="${pageContext.request.contextPath}/resources/images/employDetail.png">
					<h4>모집분야</h4>
				</div>
				<div class="field-body field-body-table">
					<table class="table field-table">
						<thead class="table-light">
							<tr>
								<th class="th1">모집부분</th>
								<th class="th2">담당업무</th>
								<th class="th3">자격요건</th>
								<th class="th4">인원</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="field" items="${employ.fieldList}">
								<tr>
							        <td>${field.filedNm}</td>
							        <td>
							        	<strong>[담당업무]</strong><br>
							        	${field.filedJobs}
							        	<br><br>
							        	<strong>[근무지]</strong><br>
							        	${field.filedRegionNm} > ${field.filedRegionGunguNm}
							        </td>
							        <td>
							        	<strong>[우대조건]</strong><br>
							        	${field.filedPreference}
							        	<c:if test="${not empty field.filterList}">
							        		<br><br>
								        	<strong>[필수조건]</strong><br>
							        		<c:forEach var="filter" items="${field.filterList}">
							        			${filter.filterTitleCdNm} : ${filter.filterContCdNm}<br>
							        		</c:forEach>
							        	</c:if>
							        </td>
							        <td>${field.filedPersonnel}명</td>
								</tr>
								<tr>
									<td/>
									<td colspan="3">
										<strong>[전형단계]</strong><br>
										<c:forEach var="proc" items="${field.procedure}" varStatus="sttus">
											${proc.procedureCdNm}
											${not sttus.last ? ' > ' : ''}
										</c:forEach>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="detail-field">
				<div class="field-header">
					<img class="field-header-img" src="${pageContext.request.contextPath}/resources/images/employDetail.png">
					<h4>근무조건</h4>
				</div>
				<div class="field-body">
					<span>고용형태 : ${employ.employTypeNm}</span>
					<br>
					<span>
						급여조건 : <c:choose>
									<c:when test="${employ.employSalaryYn eq 'Y'}">
										회사내규 또는 협의
									</c:when>
									<c:otherwise>
										${employ.employSalaryNm}
									</c:otherwise>
								</c:choose>
					</span>
					<br>
					<span>근무시간 : ${employ.employWorkdayNm} > ${employ.employSwhNm} ~ ${employ.employEwhNm}</span>
				</div>
			</div>
			<div class="detail-field">
				<div class="field-header">
					<img class="field-header-img" src="${pageContext.request.contextPath}/resources/images/employDetail.png">
					<h4>접수기간 및 방법</h4>
				</div>
				<div class="apply-cont">
					<div class="field-body apply-area">
						<div class="apply-area-left">
							<div id="countdown"></div><br>
							<span>시작 : ${employ.formatStartDate}</span><br>
							<span>마감 : ${employ.formatEndDate}</span><br><br>
							<span>마감일은 기업의 사정으로 인해 조기마감 또는 변경될 수 있습니다.</span><br>
						</div>
						<div class="apply-area-right">
							<span>접수방법 : ${employ.employApplicationNm}</span><br>
							<c:if test="${employ.employApplicationNm eq '홈페이지 지원'}">
								<span>접수URL : ${employ.employUrl}</span>
							</c:if>
						</div>
					</div>
					<div class="field-body apply-btn-area">
						<c:choose>
							<c:when test="${employ.employApplication eq 'APLC01'}">
								<button class="btn btn-primary btn-lg apply-btn stackUp-btn" data-bs-toggle="modal" data-bs-target="#apply-modal">
									바로 지원
								</button>
							</c:when>
							<c:when test="${employ.employApplication eq 'APLC02'}">
								<button class="btn btn-primary btn-lg apply-btn url-btn" onclick="location.href='${employ.employUrl}'">
									${employ.employApplicationNm}
								</button>
								<div class=""><strong>${employ.compNm}</strong> 홈페이지에서 지원을 받습니다.</div>
							</c:when>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--공고 지원 Modal-->
<div class="modal fade" id="apply-modal" tabindex="-1" aria-labelledby="apply-modal" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5"
					id="exampleModalCenteredScrollableTitle">${employ.compNm} 입사지원</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<label for="fieldSelect">모집 분야</label>
				<select id="fieldSelect" class="form-select">
					<option value="">모집 분야 선택</option>
					<c:forEach var="field" items="${employ.fieldList}">
						<option value="${field.filedNo}">${field.filedNm}</option>
					</c:forEach>
				</select>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary submit-btn">바로지원</button>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="loginCompany" value="${loginCompany}">
<input type="hidden" id="loginMember" value="${loginMember}">
<script>
	const endDateStr = `${employ.employEd}`;
</script>
<script src="${pageContext.request.contextPath}/resources/js/employ/employDetail.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/scrap.js"></script>