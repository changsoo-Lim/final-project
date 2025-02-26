<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/employ/employList.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/scrap.css" />
<section class="section-employ">
	<article>
		<h3>채용공고 목록</h3>
		<hr>
	</article>
	<div class="search-area" data-pg-target="#searchform" data-pg-fn-name="fnPaging">
		<div class="search-container">
			<!--tabs-->
			<ul class="nav nav-tabs" id="myTab" role="tablist">
				<li class="nav-item" role="presentation">
					<button class="nav-link active employ-tab" id="home-tab" data-bs-toggle="tab" data-bs-target="#job-tab-pane" type="button" role="tab"
						aria-controls="job-tab-pane" aria-selected="true">직종별</button>
				</li>
				<li class="nav-item" role="presentation">
					<button class="nav-link employ-tab" id="profile-tab" data-bs-toggle="tab" data-bs-target="#region-tab-pane" type="button" role="tab"
						aria-controls="region-tab-pane" aria-selected="false">지역별</button>
				</li>
				<li class="nav-item" role="presentation">
					<button class="nav-link employ-tab" id="contact-tab" data-bs-toggle="tab" data-bs-target="#detail-tab-pane" type="button" role="tab"
						aria-controls="detail-tab-pane" aria-selected="false">상세조건별</button>
				</li>
			</ul>
			<div class="tab-content" id="myTabContent">
				<div class="tab-pane fade show active" id="job-tab-pane" role="tabpanel" aria-labelledby="job-tab" tabindex="0">
					<div class="tab-cont">
						<!-- 업종 대분류 -->
						<div class="select-box job1-box">
							<ul id="job1-area" class="select-cell job1-cell">
								<c:forEach var="code" items="${industry1List}">
									<li class="items job1-item" data-codeCd="${code.codeCd}" data-codeNm="${code.codeNm}">
										${code.codeNm}
									</li>
								</c:forEach>
							</ul>
						</div>
						<!-- 업종 중분류 -->
						<div class="select-box job2-box">
							<ul id="job2-area" class="select-cell job2-cell" style="display: none;">
								<c:forEach var="code" items="${industry2List}">
									<li class="items job2-item" data-codeCd="${code.codeCd}" data-codeNm="${code.codeNm}" data-parent="${code.codeParent}">
										${code.codeNm}
									</li>
								</c:forEach>
							</ul>
						</div>
						<!-- 업종 소분류 -->
						<div class="select-box job3-box">
							<ul id="job3-area" class="select-cell job3-cell" style="display: none;">
								<c:forEach var="code" items="${industry3List}">
									<li class="items items-css job3-item" data-codeCd="${code.codeCd}" data-codeNm="${code.codeNm}" data-parent="${code.codeParent}" data-key="job">
										<input type="checkbox" name="job" id="${code.codeCd}" class="form-check-input" data-item-val="${code.codeCd}">
										<label for="${code.codeCd}" class="shb-label">
											<span>${code.codeNm}</span>
										</label>
									</li>
								</c:forEach>
							</ul> 
						</div>
					</div>
				</div>
				<div class="tab-pane fade" id="region-tab-pane" role="tabpanel" aria-labelledby="region-tab" tabindex="0">
					<div class="tab-cont">
						<!-- 지역 대분류 -->
						<div class="select-box region-box">
							<ul id="city-region-area" class="select-cell region-cell">
								<c:forEach var="code" items="${cityList}">
									<li class="items region-item" data-codeCd="${code.codeCd}" data-codeNm="${code.codeNm}">
										${code.codeNm}
									</li>
								</c:forEach>
							</ul>
						</div>
						<!-- 지역 중분류 -->
						<div class="select-box gungu-box">
							<ul id="city-gungu-area" class="select-cell gungu-cell" style="display: none;">
								<c:forEach var="code" items="${gunguList}">
									<li class="items items-css gungu-item" data-codeCd="${code.codeCd}" data-codeNm="${code.codeNm}" data-parent="${code.codeParent}" data-key="region">
										<input type="checkbox" name="region" id="${code.codeCd}" class="form-check-input" data-item-val="${code.codeCd}">
										<label for="${code.codeCd}" class="shb-label">
											<span>${code.codeNm}</span>
										</label>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
				<div class="tab-pane fade" id="detail-tab-pane" role="tabpanel" aria-labelledby="detail-tab" tabindex="0">
					<div class="tab-cont">
						<!-- 상세 검색 -->
						<div class="select-box detail1-box">
							<ul id="detail1-area" class="select-cell detail1-cell">
								<li class="items detail1-item" data-codeCd="workType" data-codeNm="고용형태">
									고용형태
								</li>
								<li class="items detail1-item" data-codeCd="workType2" data-codeNm="학력">
									학력
								</li>
								<li class="items detail1-item" data-codeCd="workType3" data-codeNm="연봉">
									연봉
								</li>
								<li class="items detail1-item bft-item" data-codeCd="bft" data-codeNm="복리 후생">
									복리 후생
								</li>
							</ul>
						</div>
						<div class="select-box detail2-box">
							<ul id="detail2-area" class="select-cell detail2-cell" style="display: none;">
								<li class="items detail2-item" data-codeCd="지원금/보험" data-codeNm="지원금/보험" data-parent="bft">
									지원금/보험
								</li>
								<li class="items detail2-item" data-codeCd="급여제도" data-codeNm="급여제도" data-parent="bft">
									급여제도
								</li>
								<li class="items detail2-item" data-codeCd="선물" data-codeNm="선물" data-parent="bft">
									선물
								</li>
								<li class="items detail2-item" data-codeCd="교육/생활" data-codeNm="교육/생활" data-parent="bft">
									교육/생활
								</li>
								<li class="items detail2-item" data-codeCd="근무 환경" data-codeNm="근무 환경" data-parent="bft">
									근무 환경
								</li>
								<li class="items detail2-item" data-codeCd="조직문화" data-codeNm="조직문화" data-parent="bft">
									조직문화
								</li>
								<li class="items detail2-item" data-codeCd="출퇴근" data-codeNm="출퇴근" data-parent="bft">
									출퇴근
								</li>
								<li class="items detail2-item" data-codeCd="리프레시" data-codeNm="리프레시" data-parent="bft">
									리프레시
								</li>
							</ul>
						</div>
						<div class="select-box detail3-box">
							<ul id="detail3-area" class="select-cell detail3-cell" style="display: none;">
								<c:forEach var="code" items="${BFTList}">
									<li class="items items-css detail3-item" data-codeCd="${code.codeCd}" data-codeNm="${code.codeNm}" data-parent="${code.codeParent}" data-key="bft">
										<input type="checkbox" name="bft" id="${code.codeCd}" class="form-check-input" data-item-val="${code.codeCd}">
										<label for="${code.codeCd}" class="shb-label">
											<span>${code.codeNm}</span>
										</label>
									</li>
								</c:forEach>
							</ul>
						</div>
						<div class="select-box detail4-box">
							<ul id="detail4-area" class="select-cell detail4-cell" style="display: none;">
								<c:forEach var="code" items="${workTypeList}">
									<li class="items items-css detail4-item workType1-item" data-codeCd="${code.codeCd}" data-codeNm="${code.codeNm}" data-parent="workType" data-key="workType">
										<input type="checkbox" name="workType" id="${code.codeCd}" class="form-check-input" data-item-val="${code.codeCd}">
										<label for="${code.codeCd}" class="shb-label">
											<span>${code.codeNm}</span>
										</label>
									</li>
								</c:forEach>
								<c:forEach var="code" items="${educationList}">
									<li class="items items-css detail4-item workType2-item" data-codeCd="${code.codeCd}" data-codeNm="${code.codeNm}" data-parent="workType2" data-key="edu">
										<input type="checkbox" name="edu" id="${code.codeCd}" class="form-check-input" data-item-val="${code.codeCd}">
										<label for="${code.codeCd}" class="shb-label">
											<span>${code.codeNm}</span>
										</label>
									</li>
								</c:forEach>
								<li class="items items-css detail4-item salary-item" data-codeCd="Y" data-codeNm="회사내규 또는 협의" data-parent="workType3" data-key="sal2">
									<input type="checkbox" name="sal2" id="employSalaryYn" class="form-check-input" data-item-val="Y">
									<label for="employSalaryYn" class="shb-label">
										<span>회사내규 또는 협의</span>
									</label>
								</li>
								<c:forEach var="code" items="${salaryRangeList}">
									<li class="items items-css detail4-item salary-item" data-codeCd="${code.codeCd}" data-codeNm="${code.codeNm}" data-parent="workType3" data-key="sal">
										<input type="checkbox" name="sal" id="${code.codeCd}" class="form-check-input" data-item-val="${code.codeCd}">
										<label for="${code.codeCd}" class="shb-label">
											<span>${code.codeNm}</span>
										</label>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="search-bot">
			<div class="selected-container"></div>
			<!-- 검색 버튼 -->
			<div class="search-actions">
				<button type="button" class="reset-btn">
					<img alt="초기화아이콘" src="${pageContext.request.contextPath}/resources/images/termSave_new_icon.svg">
					<span>초기화</span>
				</button>
				<button class="btn search-btn btn-primary" id="search-btn">검색하기</button>
			</div>
		</div>
	</div>
	<div class="sort-box">
		<button class="sort-btn" id="sort-btn">
			기본 <span class="arrow">▼</span>
		</button>
		<ul class="sort-dropdown" id="sort-dropdown">
			<li class="sort-item" data-key="sortBy" data-value="viewCnt">조회순</li>
			<li class="sort-item" data-key="sortBy" data-value="recent">최근등록순</li>
			<li class="sort-item" data-key="sortBy" data-value="deadline">마감임박순</li>
		</ul>
	</div>
	<div id="list-area">
		<c:forEach var="emp" items="${employList}">
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
							<button class="btn btn-primary btn-lg apply-btn stackUp-btn" data-bs-toggle="modal" data-bs-target="#apply-modal${emp.employNo}">
								바로 지원
							</button>
						</c:if>
					</div>
				</li>
			</ul>
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
		<c:if test="${empty employList}">
			<div>등록된 채용공고가 없습니다.</div>
		</c:if>
		<div class="paging-area">${pagingHTML}</div>
	</div>
</section>
<input type="hidden" id="loginCompany" value="${loginCompany}">
<input type="hidden" id="loginMember" value="${loginMember}">
<script>
	const endDateStr = `${employ.employEd}`;
</script>
<script src="${pageContext.request.contextPath}/resources/js/employ/employList.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/scrap.js"></script>
