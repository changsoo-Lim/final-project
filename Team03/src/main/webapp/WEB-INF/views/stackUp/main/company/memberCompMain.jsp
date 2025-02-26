<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />


<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/company/memberCompanyMain.css">

<div class="mem-container">
	<div class="header">
		<div class="me-4">
			<c:choose>
				<c:when
					test="${not empty company.file.fileDetails and not empty company.file.fileDetails[0].streFileNm}">
					<img
						src="<c:url value='/images/memberImage/${company.file.fileDetails[0].streFileNm}' />"
						alt="이미지 설명" class="company-logo img-fluid rounded" id="logoImg">
				</c:when>
				<c:otherwise>
					<img src="<c:url value='/resources/images/basic-Image.png' />"
						alt="기본 이미지" class="company-logo img-fluid rounded" id="logoImg">
				</c:otherwise>
			</c:choose>
		</div>
		<div class="info">
			<h1 class="company-name">${company.compNm}</h1>
			<div class="company-info-container">
				<table class="company-info-table">
					<tr>
						<th>업종</th>
						<td>${company.compNm}</td>
						<th>대표자명</th>
						<td>${company.compRepresentative}</td>
					</tr>
					<tr>
						<th>홈페이지</th>
						<td><a href="" target="_blank" id="homeUrl"></a></td>
						<th>사업내용</th>
						<td>${company.compInd}${company.compJob}</td>
					</tr>
					<tr>
						<th>주소</th>
						<td colspan="3">${company.compAddr1}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>


	<div class="summary">
		<div>
			<span>업력</span> <span id="etbDate">29년차</span>
			<p id="bDate"></p>
		</div>
		<div>
			<span>사원수</span> <span id="empno">82명</span>
			<p id="empnoP"></p>
		</div>
		<div>
			<span>매출액</span> <span id="sales">449억 2,197만원</span>
			<p id="salesP"></p>
		</div>
		<div>
			<span> &nbsp;</span> <span id="AvgSlry">10 년</span>
			<p>평균 근속 년수</p>
		</div>
	</div>
	<br>
	<br>
	<!-- 기업 소개 추가 -->
	<h2>우리 회사는 이런 곳입니다</h2>
	<div class="company-intro">
		<div id="introText" data-full-text="${fn:escapeXml(company.compCont)}">
			<c:choose>
				<c:when test="${not empty company.compCont}">
					<c:out value="${company.compCont}" escapeXml="false" />
				</c:when>
				<c:otherwise>
		            기업 소개 내용이 없습니다.
		        </c:otherwise>
			</c:choose>
		</div>
		<br>
		<button id="toggleIntro" class="common-button">더보기</button>
	</div>

	<br>
	<br>
	<h2>우리는 단단하게 성장하고 있어요</h2>
	<div class="timeline">
		<ul id="historyTimeline"></ul>
		<br>
		<button id="toggleButton" class="common-button">더보기</button>
	</div>
	<br>
	<br>
	<h2>우리는 직원의 복리후생도 중요해요</h2>
	<div class="benefit-container">
		<!-- 그룹별 복리후생 출력 -->
		<div class="benefit-group">
			<h3>교육/생활</h3>
			<ul>
				<c:forEach items="${bftList}" var="benefit">
					<c:if test="${fn:substring(benefit.cmpbftTile, 0, 3) == 'bf1'}">
						<li>${benefit.codeNm}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<div class="benefit-group">
			<h3>근무 환경</h3>
			<ul>
				<c:forEach items="${bftList}" var="benefit">
					<c:if test="${fn:substring(benefit.cmpbftTile, 0, 3) == 'bf2'}">
						<li>${benefit.codeNm}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<div class="benefit-group">
			<h3>급여제도</h3>
			<ul>
				<c:forEach items="${bftList}" var="benefit">
					<c:if test="${fn:substring(benefit.cmpbftTile, 0, 3) == 'bf3'}">
						<li>${benefit.codeNm}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<div class="benefit-group">
			<h3>리프레시</h3>
			<ul>
				<c:forEach items="${bftList}" var="benefit">
					<c:if test="${fn:substring(benefit.cmpbftTile, 0, 3) == 'bf4'}">
						<li>${benefit.codeNm}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<div class="benefit-group">
			<h3>선물</h3>
			<ul>
				<c:forEach items="${bftList}" var="benefit">
					<c:if test="${fn:substring(benefit.cmpbftTile, 0, 3) == 'bf5'}">
						<li>${benefit.codeNm}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<div class="benefit-group">
			<h3>조직문화</h3>
			<ul>
				<c:forEach items="${bftList}" var="benefit">
					<c:if test="${fn:substring(benefit.cmpbftTile, 0, 3) == 'bf6'}">
						<li>${benefit.codeNm}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<div class="benefit-group">
			<h3>지원금/보험</h3>
			<ul>
				<c:forEach items="${bftList}" var="benefit">
					<c:if test="${fn:substring(benefit.cmpbftTile, 0, 3) == 'bf7'}">
						<li>${benefit.codeNm}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<div class="benefit-group">
			<h3>출퇴근</h3>
			<ul>
				<c:forEach items="${bftList}" var="benefit">
					<c:if test="${fn:substring(benefit.cmpbftTile, 0, 3) == 'bf8'}">
						<li>${benefit.codeNm}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
	<br><br>
	
	
	
	<div class="review">
				<h2>지원자들의 생생한 면접 경험을 확인하세요!</h2>
				<c:choose>
					<c:when test="${not empty reList[0].reviewNo}">
						<div class="row g-4">
							<c:forEach items="${reList}" var="item">
								<div class="col-lg-4 col-md-6 col-sm-12">
									<div class="card review-card h-100"
										data-review-id="${item.reviewNo}">
										<div class="card-body">
											<h5 class="card-title">${item.reviewTitle}</h5>
											<h6 class="card-subtitle mb-2 text-muted">
											    평점: <span class="stars-container" data-rating="${item.reviewRating}"></span>
											</h6>
											<p class="card-text">
												<small class="text-muted">작성일: ${item.reviewDt}</small>
											</p>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:when>

					<c:otherwise>
						<p class="text-center text-muted">현재 등록된 면접 리뷰가 없습니다.</p>
					</c:otherwise>
				</c:choose>
			</div>
		<br><br>
	
		<div class="positions">
		    <h2>채용중인 공고</h2>
		
		    <c:set var="hasActivePositions" value="false" />
		    <c:forEach items="${empList}" var="emp">
		        <c:if test="${emp.employDday >= 0}">
		            <c:set var="hasActivePositions" value="true" />
		            <div class="position-card" data-empNo="${emp.employNo}">
		                <div>
		                    <h3>${emp.employTitle}</h3>
		                    <p>
		                        ${emp.employEducation} •
		                        <c:forTokens items="${emp.employExperience}" delims="," var="token" varStatus="status">
		                            <em>
		                                <c:choose>
		                                    <c:when test="${status.first and token eq 'mt01'}">신입 /</c:when>
		                                    <c:when test="${token eq 'mt01'}">신입</c:when>
		                                    <c:when test="${token eq 'mt02'}">경력</c:when>
		                                    <c:when test="${token eq 'all'}">(경력무관)</c:when>
		                                </c:choose>
		                            </em>
		                        </c:forTokens>
		                        •
		                        <c:forEach items="${emp.fieldList}" var="field" varStatus="i">
		                            <c:choose>
		                                <c:when test="${i.index eq 0}">${field.filedRegion}${field.filedRegionGungu}</c:when>
		                                <c:when test="${i.last}">외</c:when>
		                            </c:choose>
		                        </c:forEach>
		                    </p>
		                </div>
		                <div>
		                    D -
		                    <c:if test="${emp.employDday eq '0'}">DAY</c:if>
		                    <c:if test="${emp.employDday ne '0'}">${emp.employDday}</c:if>
		                </div>
		            </div>
		        </c:if>
		    </c:forEach>
		    <c:if test="${not hasActivePositions}">
		        <p class="no-positions">현재 진행 중인 공고가 없습니다.</p>
		    </c:if>
		</div>
		
		<br>
		<br>
		
		<div class="endPositions">
		    <h2>종료된 공고</h2>
		
		    <c:set var="hasEndedPositions" value="false" />
		    <c:forEach items="${empList}" var="emp">
		        <c:if test="${emp.employDday < 0}">
		            <c:set var="hasEndedPositions" value="true" />
		            <div class="position-card" data-empNo="${emp.employNo}">
		                <div>
		                    <h3>${emp.employTitle}</h3>
		                    <p>
		                        ${emp.employEducation} •
		                        <c:forTokens items="${emp.employExperience}" delims="," var="token" varStatus="status">
		                            <em>
		                                <c:choose>
		                                    <c:when test="${status.first and token eq 'mt01'}">신입 /</c:when>
		                                    <c:when test="${token eq 'mt01'}">신입</c:when>
		                                    <c:when test="${token eq 'mt02'}">경력</c:when>
		                                    <c:when test="${token eq 'all'}">(경력무관)</c:when>
		                                </c:choose>
		                            </em>
		                        </c:forTokens>
		                        •
		                        <c:forEach items="${emp.fieldList}" var="field" varStatus="i">
		                            <c:choose>
		                                <c:when test="${i.index eq 0}">${field.filedRegion}${field.filedRegionGungu}</c:when>
		                                <c:when test="${i.last}">외</c:when>
		                            </c:choose>
		                        </c:forEach>
		                    </p>
		                </div>
		                <div class="end-date">${emp.employSd}~${emp.employEd}</div>
		            </div>
		        </c:if>
		    </c:forEach>
		    <c:if test="${not hasEndedPositions}">
		        <p class="no-positions">현재 종료된 공고가 없습니다.</p>
		    </c:if>
		</div>



	<br> <br>
	<div>
		<h2>평균 연봉 비교</h2>
		<canvas id="chart2"></canvas>
	</div>
	<br> <br>
	<div>
		<h2>연도별 매출</h2>
        <canvas id="salesChart"></canvas>
    </div>
	
</div>



<script>
	const bizno = `${company.compNum}`;
	const companyData = ${companyData};
	const fullText = `${company.compCont}`;
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/company/main/memberCompanyMain.js">
	
</script>
