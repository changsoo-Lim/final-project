<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- Profile Photo -->
<link rel="stylesheet" href="<c:url value="/resources/css/member/mypage.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/scrap.css" />" />
<%-- ${cityCodeList }
${compCodeList }
${workTypeCodeList } --%>
<div class="card border-0 shadow-sm mb-4">
	<div class="card-body">
		<div class="d-flex align-items-center mb-2">
			<div class="col-xl-2">
				<img class="rounded-circle profile" width="84px" src="<c:url value="/images/MemberImage/${memberData.file.fileDetails[0].streFileNm }" />"  alt="${memberData.file.fileDetails[0].orignlFileNm }" onerror="this.onerror=null; this.src='<c:url value='/resources/images/basic-Image.png' />';" >
				<button type="button" class="btn btn-primary btn-small resume-btn" onclick='location.href="<c:url value="/resume/new" />"' >이력서 업데이트</button>
			</div>
			<div class="col-xl-10">
				<div class="d-flex align-items-center ">
					<div class="col-xl-2 mb-2">
						<span class="fs-4"><strong>${memberData.memNm }</strong>님</span>
					</div>
					<div class="text-end col-xl-10 fs-6">
						<span class="fw-bold text-danger text-decoration-underline" >${memberData.status.statusPosition ? '포지션제안 받는 중' : '포지션 제안하지 않음' } <i class="bi bi-chevron-right"></i></span>
					</div>				
				</div>
				<div class="mb-3">
					<a class="text-dark" href="<c:url value="/resume/new" />">
						<span>${memberData.memClassify == 'USER002' ? '신입' : '경력' } | 
							<c:choose>
							    <c:when test="${not empty memberData.uniList && not empty memberData.uniList[0].uniNo}">
							        ${memberData.uniList[0].uniNm} ${memberData.uniList[0].uniMajorNm}
							    </c:when>
							    <c:when test="${not empty memberData.highSchoolList && not empty memberData.highSchoolList[0].highschoolNo}">
							        ${memberData.highSchoolList[0].highschoolNm}
							    </c:when>
							    <c:when test="${not empty memberData.qualificationList && not empty memberData.qualificationList[0].qualificationNo}">
							        검정고시 졸업
							    </c:when>
							    <c:otherwise>
							        학력정보 없음
							    </c:otherwise>
							</c:choose> <i class="bi bi-chevron-right"></i></span>
					</a> 
					
				</div>
				<div class="card">
					<div class="card-body card-body-hope">
						<div class="hope-div">
							<button type="button" class="btn btn-outline-info hope-btn" onclick='location.href="<c:url value="/resume/new" />?hope=true"' >수정</button>
						</div>
						<div class="d-flex desire-work">
							<dl>
							  <dt>근무형태</dt>
							  <dd>
							  	<c:set var="workTypes" value="${memberData.workCondList[0].workCondType}" />
								<c:set var="splitWorkTypes" value="${fn:split(workTypes, ',')}" />
								
								<c:forEach var="workType" items="${splitWorkTypes}" varStatus="outerStatus">
								    <c:forEach var="code" items="${workTypeCodeList}" varStatus="innerStatus">
								        <c:if test="${workType == code.codeCd}">
								            <span>${code.codeNm}</span>
								            <c:if test="${!outerStatus.last}">
								                <span>,</span>
								            </c:if>
								        </c:if>
								    </c:forEach>
								</c:forEach>
							  </dd>
							</dl>
							<dl>  
							  <dt>희망직종</dt>
							  <dd>${memberData.workCondList[0].workCondJobType}</dd>
							</dl>
							<dl>  
							  <dt>직무기술</dt>
							  <dd>
							  	<c:forEach var="code" items="${compCodeList}">
								    <c:if test="${memberData.compList[0].compSkillDetail == code.codeCd}">
								        ${code.codeNm}
								    </c:if>
								</c:forEach>
							  </dd>
							</dl>
							<dl>  
							  <dt>희망연봉</dt>
							  <dd>
							  	
							  	<c:forEach var="code" items="${salaryCodeList}">
								    <c:if test="${memberData.workCondList[0].workCondSalary == code.codeCd}">
								        ${code.codeNm}
								    </c:if>
								</c:forEach>
							  </dd>
							</dl>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="d-flex mt-3 mypage-link">
			<div class="col-xl-2 card">
				<div class="card-body text-center p-3">
					<a href='<c:url value="/member/myapply" />' >
						<div>입사지원</div>
						<div><Strong>${totalApply[0]}</Strong></div>					
					</a>
				</div>
			</div>
			<div class="col-xl-2 card">
				<div class="card-body text-center p-3">
					<a href='<c:url value="/freeOffer/member/list" />' >
						<div>포지션제안</div>
						<div><Strong>${totalPosition[0]}</Strong></div>
					</a>
				</div>
			</div>
			<div class="col-xl-2 card">
				<div class="card-body text-center p-3">
					<a href='<c:url value="/member/test/list" />' >
						<div>테스트 요청</div>
						<div><Strong>${totalCandidate[0]}</Strong></div>
					</a>
				</div>
			</div>
			<div class="col-xl-2 card">
				<div class="card-body text-center p-3">
					<a href='<c:url value="/member/myapply" />' >
						<div>이력서 열람</div>
						<div><Strong>${totalApplyOpen[0]}</Strong></div>
					</a>
				</div>
			</div>
			<div class="col-xl-2 card">
				<div class="card-body text-center p-3">
					<a href='<c:url value="/member/scrap" />' >
						<div>스크랩 공고</div>
						<div><Strong>${totalEmployScrap[0]}</Strong></div>
					</a>
				</div>
			</div>
			<div class="col-xl-2 card">
				<div class="card-body text-center p-3">
					<a href='<c:url value="/member/scrap" />' >
						<div>관심기업 공고</div>
						<div><Strong>${totalInterComp[0]}</Strong></div>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="card border-0 shadow-sm mb-4">
	<div class="card-body">
		<div>
			<ul class="nav nav-tabs navforget" id="mypageTab"  role="tablist">
			  </li>
			  <li class="nav-item"  role="presentation">
			    <button class="nav-link active" id="mypage-tab4" data-bs-toggle="tab"
			      data-bs-target="#mypage-tab-pane4" type="button" role="tab" aria-controls="mypage-tab-pane4"
			      aria-selected="true">추천 공고</button>
			  </li>
			  <li class="nav-item"  role="presentation">
			    <button class="nav-link" id="mypage-tab2" data-bs-toggle="tab"
			      data-bs-target="#mypage-tab-pane2" type="button" role="tab" aria-controls="mypage-tab-pane2"
			      aria-selected="true">스크랩 공고</button>
			  </li>
			  <li class="nav-item"  role="presentation">
			    <button class="nav-link" id="mypage-tab3" data-bs-toggle="tab"
			      data-bs-target="#mypage-tab-pane3" type="button" role="tab" aria-controls="mypage-tab-pane3"
			      aria-selected="true">관심기업 공고</button>
			</ul>
			<div class="tab-content" id="mypageTabContent">
				 <!--  추천공고  -->
			<div class="tab-pane fade  show active" id="mypage-tab-pane4" role="tabpane4" aria-labelledby="mypage-tab4" tabindex="0">
				<c:if test="${empty fieldList}">
					<div class="mt-5">
						<ul class="tab-ul">
						  <li class="tab-li-none">
						    <strong>추천공고가 없습니다.</strong>
						    <span>채용공고 조회, 스크랩, 관심기업 설정 등 입사지원 활동을 시작해보세요!</span>
						    <a href='<c:url value="/employ/list" />' class="btn btn-outline-primary" >
						    	채용정보 조회하기
						    </a>
						  </li>
						</ul>
					</div>
				</c:if>
				<c:if test="${not empty fieldList}">
				  	<div>
						<ul class="tab-ul">
							<c:forEach items="${fieldList}" var="item">
							<li class="tab-li">
								<div class="tab-div">
									<div class="tab-company">
										<a href="/stackUp/company/${item.employ.compId }">${item.employ.compNm }</a>
										<button class="inter-btn" data-pk-no="${item.employ.compId}" data-scrap-status="${item.employ.interCompDel}">
				                        	<i class="bi ${empty item.employ.interCompDel || item.employ.interCompDel eq 'Y' ? 'bi-heart' : 'bi-heart-fill'}"> </i>
				                    	</button>
									</div>
									<div class="tab-subject">
										<a href='<c:url value="/employ/detail/${item.employ.employNo}" />'>${item.employ.employTitle }/(${item.filedNm })</a>
										<button class="scrap-btn" data-pk-no="${item.employ.employNo}" data-scrap-status="${item.employ.empscrapDel}">
					                        <i class="bi ${empty item.employ.empscrapDel || item.employ.empscrapDel eq 'Y' ? 'bi-star' : 'bi-star-fill'}"></i>
					                    </button>
									</div>
									<div class="tab-jobs">
										
										<c:forTokens items="${item.employ.employExperience}" delims="," var="token" varStatus="status">
									    <em>
									        <c:choose>
									            <c:when test="${status.first and token eq 'mt01'}">
									                신입 /
									            </c:when>
									            <c:when test="${token eq 'mt01'}">
									                신입
									            </c:when>
									            <c:when test="${token eq 'mt02'}">
									                경력
									            </c:when>
									            <c:when test="${token eq 'all'}">
									                (경력무관)
									            </c:when>
									        </c:choose>
									    </em>
										</c:forTokens>
										
										<em>&nbsp;|&nbsp;</em>
										<em>${item.employ.employEducation }</em>
										<em>&nbsp;|&nbsp;</em>
										<em>${item.filedRegion }&nbsp;${item.filedRegionGungu }</em>
									</div>
								</div>
								<br>
								<div class="tab-date">
									<span>D-${item.employ.employDday}</span>
									<button class="btn btn-outline-primary" onclick="window.location.href='<c:url value="/employ/detail/${item.employNo}" /> '" >바로지원</button>
								</div>
							</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			  </div>
			  <div class="tab-pane fade" id="mypage-tab-pane2" role="tabpane2" aria-labelledby="mypage-tab2" tabindex="0">
			  	<c:if test="${empty scrapList}">
					<div class="mt-5">
						<ul class="tab-ul">
						  <li class="tab-li-none">
						    <strong>스크랩 공고가 없습니다.</strong>
						    <span>채용공고 조회, 스크랩, 관심기업 설정 등 입사지원 활동을 시작해보세요!</span>
						    <a href='<c:url value="/employ/list" />' class="btn btn-outline-primary" >
						    	채용정보 조회하기
						    </a>
						  </li>
						</ul>
					</div>
				</c:if>
				<c:if test="${not empty scrapList}">
				${scrapList }
				 <div>
					<ul class="tab-ul">
						<li class="tab-li">
							<div class="tab-div">
								<div class="tab-company">
									<span>주식회사 누리마루</span>
									<button class="btn-scrap">
										<i class="bi bi-heart"></i>
										<i class="bi bi-heart-fill"></i>
									</button>
								</div>
								<div class="tab-subject">
									<span>종합자산관리(부동산중개,분양,경공매) 공개채용모집</span>
									<button class="btn-scrap">
										<i class="bi bi-star"></i>
										<i class="bi bi-star-fill"></i>
									</button>
								</div>
								<div class="tab-jobs">
									<em>서울외</em>
									<em>&nbsp;|&nbsp;</em>
									<em>신입/경력(연차무관)</em>
									<em>&nbsp;|&nbsp;</em>
									<em>학력무관</em>
								</div>
							</div>
							<div class="tab-date">
								<span>D-57</span>
								<button class="btn btn-outline-primary">바로지원</button>
							</div>
						</li>
					</ul>
				</div>
				</c:if>
			  </div>
			  <div class="tab-pane fade" id="mypage-tab-pane3" role="tabpane3" aria-labelledby="mypage-tab3" tabindex="0">
			  	<c:if test="${empty interComp}">
					<div class="mt-5">
						<ul class="tab-ul">
						  <li class="tab-li-none">
						    <strong>관심기업 공고가 없습니다.</strong>
						    <span>채용공고 조회, 스크랩, 관심기업 설정 등 입사지원 활동을 시작해보세요!</span>
						    <a href='<c:url value="/employ/list" />' class="btn btn-outline-primary" >
						    	채용정보 조회하기
						    </a>
						  </li>
						</ul>
					</div>
				</c:if>
				<c:if test="${not empty interComp}">
					 <div class="mt-5">
						<ul class="tab-ul">
						  <li class="tab-li-none">
						    <strong>스크랩 공고가 없습니다.</strong>
						    <span>채용공고 조회, 스크랩, 관심기업 설정 등 입사지원 활동을 시작해보세요!</span>
						    <a href='<c:url value="/employ/list" />' class="btn btn-outline-primary" >
						    	채용정보 조회하기
						    </a>
						  </li>
						</ul>
					</div>
				</c:if>
			  </div>
			</div>
		</div>
	</div>
</div>
<script src='<c:url value="/resources/js/member/mypage.js" />' ></script>
<script src='<c:url value="/resources/js/scrap.js" />'></script>