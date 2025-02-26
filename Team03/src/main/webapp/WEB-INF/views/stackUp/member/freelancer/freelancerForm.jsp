<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/freelancer/freelancerForm.css"/>
<section class="Section_freelancer">
	<article>
		<div class="free-header">
			<div>
				<h3>프리랜서 등록</h3>
				<p class="fs-6">등록하신 정보는 프로젝트 매칭 이외의 용도로는 활용되지 않습니다.</p>
			</div>
			<div>
				<button type="button" class="btn btn-primary input-btn">데이터입력</button>
			</div>
		</div>
		<hr>
	</article>
	<!--validation form-->
	<form:form class="row g-3 needs-validation" method="post" enctype="multipart/form-data" modelAttribute="newFreelancer">
		<input type="hidden" name="memId" value="${user.userId }">
		<h4>기본 정보</h4>
		<ul class="col-md-6">
			<li>
				<label class="form-label fw-bold" data-required="true">직군</label>
				<!--default select-->
				<form:select path="freeField" class="form-select form-control" aria-label="small select example" required="required">
					<option value="">직군를 선택해 주세요.</option>
					<c:forEach var="code" items="${codeList}">
						<c:if test="${code.codeParent == 'job'}">
							<form:option value="${code.codeCd}">${code.codeNm}</form:option>
						</c:if>
					</c:forEach>
				</form:select>
				<div class="invalid-feedback">직군을 선택해 주세요.</div>
			</li>
		</ul>
		<ul class="col-md-6">
			<li>
				<label class="form-label fw-bold" data-required="true">직무</label>
				<!--default select-->
				<select name="freeJobSelect" class="form-select form-control" aria-label="small select example" required="required">
						<option value="">직무를 선택해 주세요.</option>
				</select>
				<div class="invalid-feedback">직무를 선택해 주세요.</div>
			</li>
		</ul>
		<div class="selected-jobs-container"></div>
		<input type="hidden" name="freeJob" id="freeJobHidden" />
		<hr>
		<h4>근무 정보</h4>
		<ul class="col-md-6">
			<li>
				<label class="form-label fw-bold" data-required="true">근무 방식</label>
				<!--default select-->
				<form:select path="freeStyle" class="form-select form-control" aria-label="small select example" required="required">
					<form:option value="">근무방식을 선택해 주세요.(상주/원격)</form:option>
					<c:forEach var="code" items="${codeList}">
			            <c:if test="${code.codeParent == 'FREESTYLE'}">
					        <form:option value="${code.codeCd}">${code.codeNm}</form:option>
					    </c:if>
			        </c:forEach>
				</form:select>
				<div class="invalid-feedback">근무 방식를 선택해 주세요.</div>
			</li>
		</ul>
		<ul class="col-md-6">
			<li>
				<label class="form-label fw-bold" data-required="true">근무 형태</label>
				<!--default select-->
				<form:select path="freeType" class="form-select form-control" aria-label="small select example" required="required">
					<form:option value="">근무 형태를 선택해 주세요.</form:option>
					<c:forEach var="code" items="${codeList}">
			            <c:if test="${code.codeParent == 'FREETYPE'}">
					        <form:option value="${code.codeCd}">${code.codeNm}</form:option>
					    </c:if>
			        </c:forEach>
				</form:select>
				<div class="invalid-feedback">근무 형태를 선택해 주세요.</div>
			</li>
		</ul>
		<ul class="col-md-6">
			<li>
				<label class="form-label fw-bold" data-required="true">희망 급여</label>
				<div class="salary-container d-flex align-items-center">
					<!-- 드롭다운 메뉴 -->
					<form:select id="freeSalarytype" path="freeSalarytype" class="form-select form-control salary-select" aria-label="small select example" required="required">
						<c:forEach var="code" items="${codeList}">
				            <c:if test="${code.codeParent == 'SALARYTYPE'}">
						        <form:option value="${code.codeCd}">${code.codeNm}</form:option>
						    </c:if>
				        </c:forEach>
					</form:select>
					<!-- 숫자 입력 필드 -->
					<form:input id="freeSalary" path="freeSalary" class="form-control salary-input" placeholder="희망 급여" required="required"/>원
				</div>
				<div class="invalid-feedback">희망 급여를 입력해 주세요.</div>
			</li>
		</ul>
		<ul class="col-md-6">
			<li>
				<label class="form-label fw-bold" data-required="true">프로젝트 시작 가능일</label>
			</li>
			<li>
				<form:input path="freeSdt" type="date" class="form-control" required="required"/>
				<div class="invalid-feedback">프로젝트 시작 가능일을 입력해 주세요.</div>
			</li>
		</ul>
		<hr>
		<h4>경력 및 보유스킬</h4>
		<ul class="col-md-6">
			<li>
				<label class="form-label fw-bold" data-required="true">프리랜서 경험</label>
			</li>
			<li class="freelancer-yn-radio">
				<div id="experienceYes">
					<input type="radio" id="experienceY" name="freeExperience" value="Y" checked="checked"/>
		        	<label for="experienceY">있음</label>
				</div>
				<div id="experienceNo">
			        <input type="radio" id="experienceN" name="freeExperience" value="N"/>
			        <label for="experienceN">없음</label>
				</div>
			</li>
			<li>
				<p class="fs-6">* 프리랜서/단기 계약직 업무를 진행해보신 경험을 공유해 주세요.</p>
			</li>
		</ul>
		<ul class="col-md-6">
			<li>
				<label class="form-label fw-bold" data-required="true">직무 경력</label>
				<form:input path="freeCareer" type="number" class="form-control" placeholder="경력" required="required"/>
				<div class="invalid-feedback">경력을 입력해 주세요.</div>
			</li>
			<li>
				<p class="fs-6">* 선택하신 직무 관련 경력만 입력해 주세요.</p>
			</li>
		</ul>
		<ul class="col-md-12 skill-ul">
			<li>
				<label class="form-label fw-bold" data-required="true">보유 스킬</label>
			</li>
			<li>
				<p class="fs-6">* 직군/직무와 연관된 본인의 스킬과 사용 툴을 입력해 주세요.</p>
			</li>
			<li class="freelancer-skill">
			    <div class="freelancer-skill-top">
			        <label>스킬</label>
			        <button type="button" class="skill-del-btn">
			            <img src="${pageContext.request.contextPath}/resources/images/trashIcon.svg" alt="삭제">
			        </button>
			    </div>
			    <div class="freelancer-skill-container">
			        <!-- 스킬 선택 -->
			        <div class="freelancer-skill-type">
			            <form:select path="freeskills[0].freeskillsType" class="form-select form-control skill-dropdown" required="required">
			                <option value="">스킬 선택</option>
			                <c:forEach var="code" items="${codeList}">
					            <c:if test="${code.codeParent == 'SKILLTYPE'}">
							        <form:option value="${code.codeCd}">${code.codeNm}</form:option>
							    </c:if>
					        </c:forEach>
			            </form:select>
			        </div>
			        <!-- 숙련도 선택 -->
			        <div class="freelancer-skill-prof">
			        	<c:set var="isFirstSKILLPROF" value="true" scope="page"/>
						<c:forEach var="code" items="${codeList}">
						    <c:if test="${code.codeParent == 'SKILLPROF'}">
						        <input type="radio" id="freeProficiency0_${code.codeCd}" name="freeskills[0].freeskillsProf" value="${code.codeCd}"
						            <c:if test="${isFirstSKILLPROF}">checked="checked"</c:if>>
						        <label for="freeProficiency0_${code.codeCd}">${code.codeNm}</label>
						        
						        <!-- 첫 번째 SKILLPROF 코드를 처리한 뒤 false로 설정 -->
						        <c:set var="isFirstSKILLPROF" value="false" scope="page"/>
						    </c:if>
						</c:forEach>
			        </div>
			    </div>
			</li>
			<li class="d-grid gap-2">
				<button class="skill-add-Btn" type="button">보유 스킬 추가</button>
			</li>
		</ul>
		<hr>
		<h4>상세 경력</h4>
		<p class="fs-6">* 이력서/포트폴리오 업로드, 다양한 경험 작성 시 매칭 성사율이 올라갑니다.</p>
		<ul class="col-md-6">
			<li>
				<label class="form-label fw-bold" data-required="true">상세 소개</label>
			</li>
			<li>
				<form:textarea id="freeDetail" path="freeDetail" class="form-control" maxlength="500" required="required"/>
				<div class="textLengthWrap">
					<span class="textCount">0자</span>
					<span class="textTotal">/500자</span>
				</div>
			</li>
		</ul>
		<ul class="col-md-6">
			<li>
				<label class="form-label fw-bold">이력서 / 포트폴리오 업로드</label>
			</li>
			<li>
				<input type="file" class="d-none" id="uploadFiles" name="uploadFiles" multiple="multiple"  />
				<button class="file-add-Btn" type="button" onclick="document.getElementById('uploadFiles').click();">
					<span>+파일추가</span>
				</button>
				<div data-target="fileInfo"></div>
			</li>
		</ul>
		<div class="col-12">
			<button class="btn btn-primary" type="submit">등록하기</button>
		</div>
	</form:form>
</section>
<div id="skillTemplate" class="freelancer-skill" style="display: none;">
	<div class="freelancer-skill-top">
		<label>스킬</label>
		<button type="button" class="skill-del-btn">
			<img
				src="${pageContext.request.contextPath}/resources/images/trashIcon.svg" alt="삭제">
		</button>
	</div>
	<div class="freelancer-skill-container">
		<div class="freelancer-skill-type">
			<select class="form-select form-control skill-dropdown" required="required">
				<option value="">스킬 선택</option>
				<c:forEach var="code" items="${codeList}">
					<c:if test="${code.codeParent == 'SKILLTYPE'}">
						<option value="${code.codeCd}">${code.codeNm}</option>
					</c:if>
				</c:forEach>
			</select>
		</div>
		<div class="freelancer-skill-prof">
			<c:forEach var="code" items="${codeList}">
				<c:if test="${code.codeParent == 'SKILLPROF'}">
					<input type="radio" value="${code.codeCd}">
					<label>${code.codeNm}</label>
				</c:if>
			</c:forEach>
		</div>
	</div>
</div>
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
<script src="${pageContext.request.contextPath}/resources/js/freelancer/freelancer.js"> </script>
