<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/freelancer/freelancerDetail.css"/>
<section class="Section_freelancer">
	<div class="freelancer-container">
		<article class="freeDetail-article">
			<div class="freeDetail-body">
				<div class="freeDetail-profile">
					<div class="freeDetail-img">
						<c:choose>
							<c:when test="${freelancer.memImageAtchFileNo == 0}">
								<img src="<c:url value='/resources/images/basic-Image.png'/>" />
							</c:when>
							<c:otherwise>
								<c:forEach items="${freelancer.memAtchFile.fileDetails }" var="fileDetail">
									<img src="<c:url value='/images/MemberImage/${fileDetail.streFileNm}' />"  alt="회원 프로필">
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="freeDetail-top">
						<div>
							<span><strong>${freelancer.memNm }</strong></span>
							<div class="freeDetail-freeStyle">
								<c:forEach items="${codeList}" var="code">
									<c:if test="${freelancer.freeStyle eq code.codeCd }">
										<span class="freeList-freeStyle">${code.codeNm }</span>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<div class="freeDetail-content">
					<div>
						<ul>
							<li class="freeDetail-li">
								<c:forEach items="${codeList}" var="code">
									<c:if test="${freelancer.freeField eq code.codeCd }">
										<strong>직군/직무 : ${code.codeNm } > </strong>
									</c:if>
									<c:forTokens items="${freelancer.freeJob}" delims="," var="freeJob">
										<c:if test="${freeJob eq code.codeCd }">
											${code.codeNm }
										</c:if>
									</c:forTokens>
								</c:forEach>
							</li>
							<li class="freeDetail-li">
								<strong>경력 : </strong> ${freelancer.freeCareer}년
							</li>
						</ul>
					</div>
					<div class="freeDetail-skills">
						<label>보유 스킬</label>
						<ul>
							<c:forEach items="${freelancer.freeskills}" var="freeskill">
						        <li class="freeDetail-freeskills">${freeskill.freeskillsTypeNm} · ${freeskill.freeskillsProfNm}</li>
						    </c:forEach>
						</ul>
					</div>
					<!-- 기업회원이 아닐 경우 블러 -->
					<c:if test="${loginCompany eq null}">
						<div class="blur-container">
							<div class="blur">
								<div class="freeDetail-freeDetail">
									<label>상세 소개</label>
									<ul>
										<li>
											기업회원 등록 후 전체 내용을 확인할 수 있어요.<br> 로그인/기업회원 등록 후 전체 내용을 확인할 수 있어요.<br>
											기업회원 등록 후 전체 내용을 확인할 수 있어요.<br> 로그인/기업회원 등록 후 전체 내용을 확인할 수 있어요.
										</li>
									</ul>
								</div>
								<div class="freeDetail-file">
									<div>
										<label>포트폴리오 / 이력서</label>
										<ul>
											<li>
												기업회원 등록 후 전체 내용을 확인할 수 있어요.<br> 로그인/기업회원 등록 후 전체 내용을 확인할 수 있어요.<br>
												기업회원 등록 후 전체 내용을 확인할 수 있어요.<br> 로그인/기업회원 등록 후 전체 내용을 확인할 수 있어요.
											</li>
										</ul>
									</div>
								</div>
							</div>
							<c:if test="${loginCompany eq null}">
						        <div class="blur-overlay">
						            <p>기업/로그인 후<br>전체 내용을 확인할 수 있어요</p>
						            <button class="btn btn-outline-primary login-btn">로그인 하기</button>
						        </div>
						    </c:if>
						</div>
					</c:if>
					<!-- 기업회원이 아닐 경우 블러 끝 -->
					<!-- 기업회원일 경우 -->
					<c:if test="${loginCompany ne null}">
						<div class="notBlur-container">
							<div class="freeDetail-freeDetail">
								<label>상세 소개</label>
								<ul>
									<li>${freelancer.freeDetail }</li>
								</ul>
							</div>
							<div class="freeDetail-file">
								<div>
									<label>포트폴리오 / 이력서</label>
									<ul>
										<c:forEach items="${freelancer.atchFile.fileDetails }" var="fd" varStatus="vs">
											<c:url value='/freelancer/${freelancer.memId }/file/${fd.atchFileNo }/${fd.fileSn }' var="downUrl"/>
											<li class="freeDetail-freefiles">
												<a href="${downUrl }">
													${fd.orignlFileNm }
													<img class="download-img" src="${pageContext.request.contextPath}/resources/images/downloadIcon.png">
												</a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
						<!-- 기업회원일 경우 끝 -->
					</c:if>
				</div>
			</div>
		</article>
		<aside class="freeDetail-aside">
			<div class="freeDetail-offer">
				<form id="freeOffer-form" action="${pageContext.request.contextPath}/freeOffer" method="post">
					<input type="hidden" name="memId" value="${freelancer.memId}">
					<input type="hidden" id="loginCompany" value="${loginCompany}">
					<input type="hidden" id="loginMember" value="${loginMember}">
					<h5>해당 프리랜서가 마음에 드시나요?</h5>
					<p>지금 바로 프로젝트를 제안해 보세요. <br>프리랜서가 수락 시 매칭에 성공할 수 있습니다.</p>
					
					<!-- 프로젝트 목록이 비어있을 경우 -->
					<c:if test="${empty projectList }">
				        <select class="form-select" disabled="disabled">
				            <option>프로젝트를 먼저 등록해 주세요.</option>
				        </select>
				        <p class="p-text">* 모집 중인 프로젝트가 없습니다.</p>
						<div class="text-center">
					        <button type="button" class="btn btn-primary project-btn">프로젝트 등록하기</button>
						</div>
					</c:if>
					
					<!-- 프로젝트 목록이 있을 경우 -->
					<c:if test="${not empty projectList }">
						<select class="form-select" name="projectNo">
						    <c:forEach items="${projectList}" var="project" >
								<option value="${project.projectNo}">${project.projectName}</option>
							</c:forEach>
						</select>
						<div class="text-center">
							<button type="submit" class="btn btn-primary">제안하기</button>
						</div>
					</c:if>
				</form>
			</div>
		</aside>
	</div>
	<a id="goTo-freelancerList" type="button" class="btn btn-outline-primary" href="${pageContext.request.contextPath}/freelancer/list">
		프리랜서 목록 가기
	</a>
</section>
<script src="${pageContext.request.contextPath}/resources/js/freelancer/freeOffer.js"></script>