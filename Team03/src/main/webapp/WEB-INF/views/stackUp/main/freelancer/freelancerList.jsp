<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/freelancer/freelancerList.css"/>
<section class="Section_freelancer">
	<article>
		<h3>프리랜서 목록</h3>
		<hr>
	</article>
	<div class="search-area" data-pg-target="#searchform" data-pg-fn-name="fnPaging">
		<div class="sel-filter">
			<div>
				<button class="sel-btn" id="freeJob-btn" data-bs-toggle="modal" data-bs-target="#freeJob-modal">
					직군/직무 <span class="arrow">▼</span>
				</button>
			</div>
			<div>
				<button class="sel-btn" id="freeSkill-btn" data-bs-toggle="modal" data-bs-target="#freeSkill-modal">
					스킬 선택 <span class="arrow">▼</span>
				</button>
			</div>
			<div class="freeStyle-btn">
				<button class="sel-btn" id="freeStyle-btn">
					근무 형태 <span class="arrow">▼</span>
				</button>
				<ul class="sel-dropdown" id="freeStyle-dropdown">
					<li class="sel-item" data-value="">전체</li>
					<c:forEach items="${codeList}" var="code">
						<c:if test="${code.codeParent eq 'FREESTYLE' && code.codeCd ne 'FS03'}">
							<li class="sel-item" data-value="${code.codeCd}">${code.codeNm}</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<div>
				<button class="btn btn-primary search-btn">검색</button>
			</div>
		</div>
		<div class="selected-container"></div>
	</div>

	<!--직군/직무 Modal-->
	<div class="modal fade" id="freeJob-modal" tabindex="-1" aria-labelledby="exampleModalCenteredScrollable" aria-hidden="true">
		<div class="modal-dialog modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5">직군/직무 검색</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<ul class="col-md-11">
						<li>
							<select name="freeField" class="form-select form-control" aria-label="small select example">
								<option value="">직군를 선택해 주세요.</option>
								<c:forEach var="code" items="${codeList}">
									<c:if test="${code.codeParent == 'job'}">
										<option value="${code.codeCd}">${code.codeNm}</option>
									</c:if>
								</c:forEach>
							</select>
						</li>
					</ul>
					<ul class="col-md-11">
						<li>
							<select name="freeJob" class="form-select form-control" aria-label="small select example">
									<option value="">직무를 선택해 주세요.</option>
							</select>
						</li>
					</ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal">확인</button>
				</div>
			</div>
		</div>
	</div>
	<!--직군/직무 Modal 끝 -->
	<!--스킬 Modal-->
	<div class="modal fade" id="freeSkill-modal" tabindex="-1" aria-labelledby="exampleModalCenteredScrollable" aria-hidden="true">
		<div class="modal-dialog modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5">스킬</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
				    <div class="search-bar">
				        <input type="text" id="skillSearchInput" class="form-control" placeholder="스킬 검색">
				    </div>
				    <ul id="skillList" class="col-md-11 skill-dropdown">
				        <c:forEach var="code" items="${codeList}">
				            <c:if test="${code.codeParent == 'SKILLTYPE'}">
				                <li class="skill-item" data-value="${code.codeCd}">${code.codeNm}</li>
				            </c:if>
				        </c:forEach>
				    </ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal">확인</button>
				</div>
			</div>
		</div>
	</div>
	<!--스킬 Modal 끝 -->
	
	<c:if test="${not empty freelancerList}">
		<c:forEach items="${freelancerList}" var="freelancer">
			<div class="freeList-container">
				<a class="freeList-a" href="${pageContext.request.contextPath}/freelancer/${freelancer.memId}">
					<div class="freeList-body">
						<div class="freeList-profile">
							<div class="freeList-img">
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
							
						</div>
						<div class="freeList-content">
							<div class="freeList-top">
								<span><c:out value="${freelancer.memNm }"/></span> <span class="freeList-freeStyle">${freelancer.freeStyle }</span>
							</div>
							<div class="freeList-mid">
								<span class="freeList-freeField">${freelancer.freeField}</span>
								<span class="freeList-freeCareer">${freelancer.freeCareer}년</span>
								<span class="freeList-freeJob">
								<c:forEach items="${codeList}" var="code">
									<c:forTokens items="${freelancer.freeJob}" delims="," var="freeJob">
										<c:if test="${freeJob eq code.codeCd }">
											${code.codeNm}
										</c:if>
									</c:forTokens>
								</c:forEach>
								</span>
							</div>
							<div class="freeList-bot">
								<c:forTokens items="${freelancer.freeskillsTypeList }" delims=","
									var="freeskillsType">
									<span class="freeList-freeskillsType">${freeskillsType.trim() }</span>
								</c:forTokens>
							</div>
						</div>
					</div>
				</a>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${empty freelancerList}">
		<div>등록된 프리랜서가 없습니다.</div>
	</c:if>
	<div class="paging-area">${pagingHTML }</div>
</section>
<form:form id="searchForm" method="get" modelAttribute="condition" style="display:none">
	<input type="hidden" name="page" />
</form:form>
<script type="text/javascript">
    const codeList = [
        <c:forEach var="code" items="${codeList}">
            {
                "codeCd": "${code.codeCd}",
                "codeNm": "${code.codeNm}",
                "codeParent": "${code.codeParent}"
            }<c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];
</script>
<script src="${pageContext.request.contextPath}/resources/js/freelancer/freelancerList.js"></script>
