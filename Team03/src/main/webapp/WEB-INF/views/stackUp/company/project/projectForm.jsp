<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/project/projectForm.css" />
	<div class="form-container">
	<button type="button" class="btn btn-primary" id="dataBtn">데이터 입력</button>
	<c:if test="${empty projectVO.projectNo }">
		<h1>프로젝트 등록</h1>
	</c:if>
	<c:if test="${!empty projectVO.projectNo }">
		<h1>프로젝트 수정</h1>
	</c:if>


	<c:set var="actionUrl">
		<c:choose>
			<c:when test="${empty projectVO.projectNo}">
            ${pageContext.request.contextPath}/project/insert
        </c:when>
			<c:otherwise>
            ${pageContext.request.contextPath}/project/${projectVO.projectNo}/update
        </c:otherwise>
		</c:choose>
	</c:set>

	<form:form action="${actionUrl}" method="POST" modelAttribute="projectVO">

		<!-- 프로젝트명 -->
		<div class="form-group">
			<label for="projectName">프로젝트명</label>
			<form:input path="projectName" type="text" id="projectName"
				name="projectName" required="true" />
		</div>

		<!-- 프로젝트 내용 -->
		<div class="form-group">
			<label for="projectDescription">프로젝트 내용</label>
			<form:textarea path="projectCont" id="projectDescription"
				name="projectDescription" rows="4" required="true"
				placeholder=""></form:textarea>
		</div>

		<!-- 프로젝트 지원 마감일 -->
		<div class="form-group">
			<label for="projectApplyEdt">프로젝트 지원 마감일</label>
			<form:input path="projectApplyEdt" type="date" id="deadline"
				name="deadline" required="true" />
		</div>

		<!-- 프로젝트 시작일 -->
		<div class="form-group">
			<label for="projectSdt">프로젝트 시작일</label>
			<form:input path="projectSdt" type="date" id="startDate"
				name="startDate" required="true" disabled="true" />
		</div>

		<!-- 프로젝트 마감일 -->
		<div class="form-group">
			<label for="projectEdt">프로젝트 마감일</label>
			<form:input path="projectEdt" type="date" id="endDate" name="endDate"
				required="true" disabled="true" />
		</div>

		<!-- 예상 기간 (개월) -->
		<div class="form-group">
			<label for="duration">예상 기간 (개월)</label>
			<form:input path="projectDeadline" type="number" id="duration"
				name="duration" min="1" readonly="true" />
		</div>

		<!-- 재택 여부 (라디오) -->
		<div class="form-group">
			<label>재택 여부</label>
			<div class="radio-group-horizontal">
				<div class="radio-item">
					<form:radiobutton path="projectWfh" id="onsite" name="remoteOption"
						value="FS01" required="true" />
					<label for="onsite">상주</label>
				</div>
				<div class="radio-item">
					<form:radiobutton path="projectWfh" id="remote" name="remoteOption"
						value="FS02" required="true" />
					<label for="remote">원격</label>
				</div>
				<div class="radio-item">
					<form:radiobutton path="projectWfh" id="negotiable"
						name="remoteOption" value="FS03" required="true" />
					<label for="negotiable">모두 가능</label>
				</div>
			</div>
		</div>

		<!-- 근무지 선택 -->
		<div class="form-group" id="workLocationGroup" style="display: none;">
			<label for="workLocation">근무지</label>
			<div class="row">
				<div class="col-md-6">
					<select id="regionSelect" class="form-select form-control">
						<option value="">시,도를 선택해 주세요.</option>
						<c:forEach var="code" items="${codeList}">
							<c:if
								test="${not empty code.codeParent && code.codeParent eq 'city'}">
								<option value="${code.codeCd}">${code.codeNm}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-6">
					<select id="districtSelect" class="form-select form-control">
						<option value="">시, 군, 구를 선택해 주세요.</option>
					</select>
				</div>
			</div>
			<div id="regionConditionsContainer" class="conditions-container"></div>
		</div>

		<!-- 모집 인원 -->
		<div class="form-group">
			<label for="teamSize">모집 인원</label>
			<form:input path="projectRecruit" type="number" id="teamSize"
				name="teamSize" placeholder="명" min="0" required="true" />
		</div>

		<!-- 월 단가 (만원) -->
		<div class="form-group">
			<label for="monthlyRate">월 단가 (만원)</label>
			<form:input path="projectSalary" type="number" id="monthlyRate"
				name="monthlyRate" min="0" required="true" />
		</div>

		<!-- 직무 -->
		<div class="form-group">
			<div class="row">
				<label for="monthlyRate">직무</label>
				<div class="col-md-6">
					<select class="form-select form-control"
						aria-label="small select example" id="jobGun">
						<option value="">직군를 선택해 주세요.</option>
						<c:forEach var="code" items="${codeList}">
							<c:if test="${code.codeParent == 'job'}">
								<option value="${code.codeCd}">${code.codeNm}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-6">
					<select name="freeJobSelect" class="form-select form-control" id="jobMu">
						<option value="">직무를 선택해 주세요.</option>
					</select>
				</div>
			</div>
			<div id="jobConditionsContainer" class="conditions-container"></div>
		</div>

		<!-- 필요 스킬/스펙 -->
		<div class="form-group">
			<label for="requiredSkills">필요 스킬/스펙</label>
			<div class="row">
				<div class="col-md-6">
					<select id="skillSelect" class="form-select form-control">
						<option value="">스킬을 선택 해주세요.</option>
						<c:forEach var="code" items="${codeList}">
							<c:if
								test="${code.codeParent=='SKILLTYPE' && code.codeCd.contains('sk')}">
								<option value="${code.codeCd}">${code.codeNm}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
			<div id="skillConditionsContainer" class="conditions-container"></div>
		</div>

		<!-- 숨겨진 필드들 (지역/직무/스킬) -->
		<form:input path="projectRegion" type="hidden" id="regionHidden" />
		<form:input path="projectJob" type="hidden" id="jobHidden" />
		<form:input path="projectSkills" type="hidden" id="skilleHidden" />

		<c:choose>
			<c:when test="${empty projectVO.projectNo}">
				<button type="submit">등록</button>
			</c:when>
			<c:otherwise>
				<button type="submit">수정</button>
			</c:otherwise>
		</c:choose>
	</form:form>
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

<script
	src="${pageContext.request.contextPath }/resources/js/project/projectForm.js"></script>