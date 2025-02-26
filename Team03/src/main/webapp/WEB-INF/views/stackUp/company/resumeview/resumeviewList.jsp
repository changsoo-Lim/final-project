<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<link>


<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/resumeView/resumeViewList.css">

<div class="container my-4">
	<!-- 페이지 제목 -->
	<div class="mb-4">
		<h1 class=" fw-bold">인재 조회 기록</h1>
		<p class="text-secondary" style="font-size: 1rem;">오늘 조회했던 인재만 확인할
			수 있습니다.</p>
	</div>


	<div class="container my-4">
		<!-- 검색 결과가 존재할 때 -->
		<c:if test="${!empty memList}">
			<!-- 카드 그리드 -->
			<div
				class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-2 g-3">
				<c:forEach items="${memList}" var="member">
					<div class="col">
						<div class="card shadow-sm border border-primary rounded"
							style="min-height: 200px;">
							<div class="card-body p-4" data-mem-id="${member.memId }">
								<!-- 이름 및 성별/나이 -->
								<div
									class="d-flex justify-content-between align-items-center mb-2">
									<h5 class="card-title text-primary fw-bold mb-0"
										style="font-size: 1.25rem;">${member.memNm}</h5>
									<p class="text-secondary mb-0" style="font-size: 1rem;">
										${member.memGen eq 'M' ? '남자' : '여자'} /
										${fn:substring(member.memRegno, 0, 4)}년생 ${currentYear - fn:substring(member.memRegno, 0, 4)}세
									</p>
								</div>
								<!-- 세부 정보 -->
								<div class="row row-cols-2 g-2" style="font-size: 1rem;">
									<!-- 경력사항 -->
									<div class="col">
										<strong class="text-dark">경력:</strong>
										<c:if test="${!empty  member.careerList}">
											<c:forEach items="${member.careerList}" var="career"
												varStatus="status">
                                        ${career.careerJobTitle}
                                        <c:if test="${!status.last}">, </c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty  member.careerList}">없음</c:if>
									</div>
									<!-- 스킬 -->
									<div class="col">
										<strong class="text-dark">스킬:</strong>
										<c:if test="${!empty member.compList}">
											<c:forEach items="${member.compList}" var="skill"
												varStatus="status">
										${skill.compSkillDetail}
										<c:if test="${!status.last}">, </c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty member.compList}">없음</c:if>
									</div>

									<!-- 자격증 -->
									<div class="col">
										<strong class="text-dark">자격증:</strong>
										<c:if test="${!empty member.certrList}">
											<c:forEach items="${member.certrList}" var="cert"
												varStatus="status">
										${cert.certNm}
										<c:if test="${!status.last}">, </c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty member.certrList}">없음</c:if>
									</div>
									<!-- 언어 -->
									<div class="col">
										<strong class="text-dark">언어 : </strong>
										<c:if test="${!empty member.languageList}">
											<c:forEach items="${member.languageList}" var="lang"
												varStatus="status">
										${lang.langNm}
										<c:if test="${!status.last}">, </c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty member.languageList}">없음</c:if>
									</div>

									<!-- 학력사항 -->
									<div class="col">
										<strong class="text-dark">학력:</strong>
										<c:if test="${!empty member.uniList}">
											<c:forEach items="${member.uniList}" var="uni"
												varStatus="status">
										${uni.uniType}
										<c:if test="${!status.last}">, </c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty member.uniList}">없음</c:if>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>

		<!-- 검색 결과가 없을 때 -->
		<c:if test="${empty memList}">
			<div class="alert alert-warning text-center" role="alert">조회된
				기록이 없습니다!</div>
		</c:if>
	</div>
	
</div>
	<div class="paging-area">${pagingHTML }</div>

	<form id="searchForm">
		<input type="hidden" name="page" placeholder="page" />
	</form>

	<script
		src="${pageContext.request.contextPath }/resources/js/resumeView/resumeViewList.js"></script>