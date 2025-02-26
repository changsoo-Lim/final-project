<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/talent/talentDetail.css">

<%
// 현재 연도 계산
Calendar calendar = Calendar.getInstance();
int currentYear = calendar.get(Calendar.YEAR);
request.setAttribute("currentYear", currentYear);
%>

<input type="hidden" value="${member.memId }" id="memId" />
<section id="basic-info"></section>
<div class="resume-wrapper">
	<div class="row">
		<div class="col-12">
			<h3 class="fw-bold mb-2">기본정보</h3>
		</div>
	</div>
	<hr style="border: 2px solid black;">

	<div class="row text-center">
		<!-- 사진 등록 영역 (왼쪽) -->
		<div class="col-md-3 text-center border-end">
			<!-- 사진 미리보기 -->
			<div class="mb-3">
				<c:choose>
					<c:when
						test="${not empty member.file.fileDetails and not empty member.file.fileDetails[0].streFileNm}">
						<img
							src="<c:url value='/images/memberImage/${member.file.fileDetails[0].streFileNm}' />"
							alt="이미지 설명" class="img-fluid rounded border" id="logoImg"
							style="width: 150px; height: 200px;">
					</c:when>
					<c:otherwise>
						<img src="<c:url value='/resources/images/basic-Image.png' />"
							alt="기본 이미지" class="img-fluid rounded border" id="logoImg"
							style="width: 150px; height: 200px;">
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<!-- 입력 필드 영역 (오른쪽) -->
		<div class="col-md-9">
			<div class="row mb-3">
				<label for="memNm" class="col-md-3 col-form-label fw-bold"
					data-required="true">이름</label>
				<div class="col-md-9">
					<input type="text" class="form-control" id="memNm" name="memNm"
						value="${member.memNm }" readonly>
				</div>
			</div>

			<c:choose>
				<c:when test="${member.memGen eq 'M'}">
					<c:set value="남자" var="memGen" />
				</c:when>
				<c:when test="${member.memGen eq 'F'}">
					<c:set value="여자" var="memGen" />
				</c:when>
			</c:choose>

			<div class="row mb-3">
				<label for="memRegno" class="col-md-3 col-form-label fw-bold"
					data-required="true">성별/나이</label>
				<div class="col-md-9">
					<c:set var="birthYear"
						value="${fn:substring(member.memRegno, 0, 4)}" />
					<c:set var="age" value="${currentYear - birthYear}" />

					<input type="text" class="form-control" id="memRegno"
						name="memRegno" readonly
						value="${memGen } / ${birthYear}년생  ${age}세">
				</div>
			</div>
			<div class="row mb-3">
				<label for="memHp" class="col-md-3 col-form-label fw-bold"
					data-required="true">휴대폰번호</label>
				<div class="col-md-9">

					<c:set var="hp" value="${member.memHp}" />
					<c:set var="hpPart1" value="${fn:substring(hp, 0, 3)}" />
					<c:set var="hpPart2" value="${fn:substring(hp, 3, 7)}" />
					<c:set var="hpPart3" value="${fn:substring(hp, 7, fn:length(hp))}" />

					<input type="text" class="form-control" id="memHp" name="memHp"
						value="${hpPart1}-${hpPart2}-${hpPart3}" readonly>
				</div>
			</div>
			<div class="row mb-3">
				<label for="memEmail" class="col-md-3 col-form-label fw-bold"
					data-required="true">이메일</label>
				<div class="col-md-9">
					<input type="email" class="form-control" id="memEmail"
						name="memEmail" value="${member.memEmail }" readonly>
				</div>
			</div>
			<div class="row mb-3">
				<label for="memAddr1" class="col-md-3 col-form-label fw-bold">주소</label>
				<div class="col-md-9">
					<input type="text" class="form-control mb-2" id="memAddr1"
						name="memAddr1" value="${member.memAddr1 }" readonly> <input
						type="text" class="form-control" id="memAddr2" name="memAddr2"
						value="${member.memAddr2 }" readonly>
				</div>
			</div>

			<c:choose>
				<c:when test="${empty member.memUrl}">
					<c:set value="등록된 포트폴리오가 없습니다." var="memUrl" />
				</c:when>
				<c:when test="${not empty member.memUrl}">
					<c:set value="${member.memUrl}" var="memUrl" />
				</c:when>
			</c:choose>

			<div class="row mb-3">
				<label for="memUrl" class="col-md-3 col-form-label fw-bold">홈페이지/SNS</label>
				<div class="col-md-9">
					<input type="text" class="form-control" id="memUrl" name="memUrl"
						value="${memUrl}" readonly>
				</div>
			</div>
		</div>
	</div>
	<hr style="border: 1px solid black;">
	<!-- 기본정보 -->
	<section id="academic-info"></section>
	<br>
	<div class="row mb-3">
		<div class="col-10">
			<h3 class="fw-bold mb-2">학력사항</h3>
			<p class="text-muted small"></p>
		</div>
	</div>
	<hr style="border: 2px solid black;">


	<c:if test="${!empty member.highSchoolList[0].highschoolNo}">
		<div class="row mb-3 align-items-center" id="highschoolFields">
			<label for="highschoolNm" class="col-md-2 col-form-label fw-bold">고등학교
				명</label>
			<div class="col-md-4">
				<input type="text" class="form-control" id="highschoolNm"
					name="highschoolNm"
					value="${member.highSchoolList[0].highschoolNm}" readonly>
			</div>
			<label for="highschoolLocation"
				class="col-md-2 col-form-label fw-bold text-center">소재지</label>
			<div class="col-md-4">
				<input type="text" class="form-control" id="highschoolLocation"
					name="highschoolLocation"
					value="${member.highSchoolList[0].highschoolLocation}" readonly>
			</div>
		</div>

		<div class="row mb-3 align-items-center" id="schoolDateFields">
			<!-- 입학일자 -->
			<label for="highschoolSd" class="col-md-2 col-form-label fw-bold"
				data-required="true">재학기간</label>
			<div class="col-md-4">
				<input type="text" class="form-control" id="highschoolSd"
					name="highschoolSd"
					value="${member.highSchoolList[0].highschoolPeriod}" readonly>
			</div>

			<label for="highschoolSd"
				class="col-md-2 col-form-label fw-bold text-center"
				data-required="true">전공</label>
			<div class="col-md-4">
				<input type="text" class="form-control" id="highschoolSd"
					name="highschoolSd"
					value="${member.highSchoolList[0].highschoolMajor}" readonly>
			</div>
		</div>
	</c:if>
	<c:if test="${empty member.highSchoolList[0].highschoolNo}">
		<div class="row mb-3 align-items-center" id="examPassDate" style="">
			<label for="qualificationDt" class="col-md-5 col-form-label fw-bold"
				data-required="true">등록된 고등학교 학력 사항이 없습니다.</label>
		</div>
	</c:if>
	<!-- 검정고시 -->

	<hr style="border: 2px solid black;">

	<!-- 대학교 -->
	<div id="uniField">
		<c:if test="${!empty member.uniList[0].uniNo}">
			<c:forEach items="${member.uniList}" var="uni">
				<div class="row mb-3"></div>
				<div class="row mb-3 align-items-center">
					<label for="uniNm" class="col-md-2 col-form-label fw-bold">대학교</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="uniNm" name="uniNm"
							value="${uni.uniNm }" readonly>
					</div>
					<label for="uniNm"
						class="col-md-2 col-form-label fw-bold text-center">대학 구분</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="uniType"
							name="uniType" value="${uni.uniType }" readonly>
					</div>
				</div>

				<div class="row mb-3 align-items-center" id="schoolDateFields">
					<!-- 입학일자 -->
					<label for="uniSdt" class="col-md-2 col-form-label fw-bold"
						data-required="true">재학기간</label>
					<div class="col-md-3">
						<input type="text" class="form-control" id="uniSStatus"
							name="uniSStatus" value="${uni.uniSdt }" readonly>
					</div>
					<div class="col-md-2">
						<input type="text" class="form-control" id="uniSStatus"
							name="uniSStatus" value="입학" readonly>
					</div>

					<!-- 졸업일자 -->
					<div class="col-md-3">
						<input type="text" class="form-control" id="uniEdt" name="uniEdt"
							value="${uni.uniEdt }" readonly>
					</div>
					<div class="col-md-2">
						<input type="text" class="form-control" id="uniEStatus"
							name="uniEStatus" value="졸업" readonly>
					</div>
				</div>
				<div class="row mb-3">
					<!-- 전공 라벨 -->
					<label for="uniMajorCategory"
						class="col-md-2 col-form-label fw-bold" data-required="true">전공</label>

					<!-- 전공 계열 선택 -->
					<div class="col-md-3">
						<input type="text" class="form-control" id="uniMajorCategory"
							name="uniMajorCategory" value="${uni.uniMajorCategory }" readonly>
					</div>

					<!-- 전공명 입력 -->
					<div class="col-md-3">
						<input type="text" class="form-control" id="uniMajorName"
							name="uniMajorName" value="${uni.uniMajorNm }" readonly>
					</div>

					<div class="col-md-2">
						<label for="uniSdt" class="col-md-2 col-form-label fw-bold" data-required="true">학점</label>
					</div>
					<!-- GPA 선택 -->
					<div class="col-md-2">
						<input type="text" id="uniGpa" name="uniGpa" class="form-control"
							readonly value="${uni.uniGpa }">
					</div>
				</div>
				<hr style="border: 1px solid black;">
			</c:forEach>
		</c:if>
		<c:if test="${empty member.uniList[0].uniNo}">
			<div class="row mb-3">
				<label for="uniProjectNm" class="col-md-5 col-form-label fw-bold">
					대학 학력사항 정보가 없습니다. </label>
			</div>
			<hr style="border: 1px solid black;">
		</c:if>

	</div>

	<!-- 학력사항 -->
	<section id="career"></section>
	<br>

	<div class="row mb-3">
		<div class="col-12">
			<h3 class="fw-bold mb-2">경력사항</h3>
		</div>
	</div>

	<hr style="border: 2px solid black;">

	<c:if test="${!empty member.careerList[0].careerNo }">
		<c:forEach items="${member.careerList }" var="care">
			<!-- 경력 선택 시 보이는 DIV -->
			<div id="careerDetails" style="">

				<div class="row mb-3">
					<label for="career" class="col-md-2 col-form-label fw-bold">회사명</label>
					<div class="col-md-7">
						<input type="text" class="form-control" id="careerCompanyNm"
							name="careerCompanyNm" readonly value="${care.careerCompanyNm }">
					</div>
				</div>
				<hr>
				<div class="row mb-3">
					<div class="col-md-3 col-form-label fw-bold">
						<input type="text" class="form-control" id="careerIndustryType"
							name="careerIndustryType" readonly
							value="${care.careerIndustryType }">
					</div>

					<div class="col-md-3 col-form-label fw-bold">
						<input type="text" class="form-control" id="careerSubIndustry"
							name="careerSubIndustry" readonly
							value="${care.careerSubIndustry }">
					</div>

					<div class="col-md-2 col-form-label fw-bold">
						<input type="text" class="form-control" id="careerCompanySize"
							name="careerCompanySize" readonly
							value="${care.careerCompanySize }">
					</div>

					<div class="col-md-2 col-form-label fw-bold">
						<input type="text" class="form-control" id="careerCompanyType"
							name="careerCompanyType" readonly
							value="${care.careerCompanyType }">
					</div>

					<div class="col-md-2 col-form-label fw-bold">
						<input type="text" class="form-control" id="careerListed"
							name="careerListed" readonly value="${care.careerListed }">
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-3 col-form-label fw-bold">
						<input type="text" class="form-control" id="careerCity"
							name="careerCity" readonly value="${care.careerCity }">
					</div>

					<div class="col-md-3 col-form-label fw-bold">
						<input type="text" class="form-control" id="careerDistrict"
							name="careerDistrict" readonly value="${care.careerDistrict }">
					</div>
				</div>
				<hr>
				<div class="row mb-3">
					<label for="career" class="col-md-2 col-form-label fw-bold"
						data-required="true">근무기간</label>

					<div class="col-md-3">
						<input type="text" class="form-control" id="careerStartDate"
							name="careerStartDate" readonly value="${care.careerStartDate }">
					</div>
					<label for="career"
						class="col-md-1 col-form-label fw-bold text-center"> <span>~</span>
					</label>
					<div class="col-md-3">
						<c:if test="${empty care.careerEndDate }">
							<input type="text" class="form-control" id="careerEndDate"
								name="careerEndDate" readonly value="재직중" />
						</c:if>
						<c:if test="${!empty care.careerEndDate }">
							<input type="text" class="form-control" id="careerEndDate"
								name="careerEndDate" readonly value="${care.careerEndDate }" />
						</c:if>
					</div>
				</div>

				<div class="row mb-3">
					<label for="careerPosition" class="col-md-2 col-form-label fw-bold">직급/직책</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="careerPosition"
							name="careerPosition" readonly value="${care.careerPosition }">
					</div>

					<label for="careerDepartment"
						class="col-md-2 col-form-label fw-bold">근무부서</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="careerDepartment"
							name="careerDepartment" readonly
							value="${care.careerDepartment }">
					</div>
				</div>

				<div class="row mb-3">
					<label for="careerType" class="col-md-2 col-form-label fw-bold">근무형태</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="careerType"
							name="careerType" readonly value="${care.careerType }">
					</div>
					<label for="careerSalary" class="col-md-2 col-form-label fw-bold">연봉</label>
					<div class="col-md-2">
						<input type="text" class="form-control" id="careerSalary"
							name="careerSalary" readonly value="${care.careerSalary }">
					</div>
					<span class="col-md-2 col-form-label fw-bold">만원</span>
				</div>
				<div class="row mb-3" id="careerReasonDiv">
					<label for="careerReason" class="col-md-2 col-form-label fw-bold">퇴사사유</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="careerReason"
							name="careerReason" readonly value="${care.careerReason }">
					</div>
				</div>
			</div>

			<hr style="border: 1px solid black;">
		</c:forEach>
	</c:if>
	<c:if test="${empty member.careerList[0].careerNo }">
		<div class="row mb-3" id="careerReasonDiv">
			<div class="col-md-6">
				<label class="col-md-6 col-form-label fw-bold">등록된 경력이 없습니다.</label>
			</div>
		</div>
		<hr style="border: 1px solid black;">
	</c:if>
	<br> <br>

	<section id="certification"></section>

	<div>
		<div class="row mb-3">
			<div class="col-12">
				<h3 class="fw-bold mb-2">자격증</h3>
			</div>
		</div>
		<hr style="border: 2px solid black;">

		<c:if test="${!empty member.certrList[0].certNo }">
			<c:forEach items="${member.certrList}" var="cert">
				<div class="row mb-3">
					<label for="certNm" class="col-md-2 col-form-label fw-bold"
						data-required="true">자격증명</label>
					<div class="col-md-8">
						<input type="text" class="form-control" id="certNm" name="certNm"
							readonly value="${cert.certNm }">
					</div>
				</div>
				<div class="row mb-3">
					<label for="certIssuer" class="col-md-2 col-form-label fw-bold">발행처</label>
					<div class="col-md-8">
						<input type="text" class="form-control" id="certIssuer"
							name="certIssuer" readonly value="${cert.certIssuer }">
					</div>
				</div>
				<div class="row mb-3">
					<label for="certDate" class="col-md-2 col-form-label fw-bold">취득일자</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="certDate"
							name="certDate" readonly value="${cert.certDate }">
					</div>
					<div class="col-md-4">
						<input type="text" class="form-control" id="certPassCd"
							name="certPassCd" readonly value="${cert.certPassCd }">
					</div>
				</div>
				<hr style="border: 1px solid black;">
			</c:forEach>
		</c:if>

		<c:if test="${empty member.certrList[0].certNo }">
			<div class="col-md-6">
				<label class="col-md-6 col-form-label fw-bold">등록된 자격증이 없습니다</label>
			</div>

			<hr style="border: 1px solid black;">
		</c:if>

	</div>



	<section id="computer-skills"></section>
	<br>
	<div id="compField">
		<div class="row mb-3">
			<div class="col-12">
				<h3 class="fw-bold mb-2">컴퓨터 활용능력</h3>
			</div>
		</div>
		<hr style="border: 2px solid black;">

		<c:if test="${!empty member.compList[0].compSkillNo }">
			<c:forEach items="${member.compList }" var="comp">

				<div class="row mb-3">
					<label for="compuSkill" class="col-md-3 col-form-label fw-bold"
						data-required="true">분야 및 활용능력</label>

					<div class="row mb-3">
						<div class="col-md-3">
							<input type="text" class="form-control" name="compSkillField"
								id="compSkillField" readonly value="${comp.compSkillField }">
						</div>
						<div class="col-md-3">
							<input type="text" class="form-control" name="compSkillDetail"
								id="compSkillDetail" readonly value="${comp.compSkillDetail }">
						</div>
						<div class="col-md-3">
							<input type="text" class="form-control" name="compSkillLevel"
								id="compSkillLevel" readonly value="${comp.compSkillLevel }">
						</div>
					</div>
				</div>
				<hr style="border: 1px solid black;">
			</c:forEach>
		</c:if>
		<c:if test="${empty member.compList[0].compSkillNo }">

			<div class="row mb-6">
				<label class="col-md-6 col-form-label fw-bold" data-required="true">등록된
					컴퓨터 활용 능력이 없습니다.</label>
			</div>
			<hr style="border: 1px solid black;">
		</c:if>
	</div>


	<section id="preferences"></section>
	<br>
	<div class="row mb-3">
		<div class="col-12">
			<h3 class="fw-bold mb-2">취업우대 및 특이사항</h3>
		</div>
	</div>
	<hr style="border: 2px solid black;">

	<c:if test="${!empty member.prefList[0].prefNo }">
		<c:forEach items="${member.prefList}" var="pre">
			<div class="row mb-3">
				<div class="col-md-12">
					<label for="prefMilitary" class="col-md-2 col-form-label fw-bold">병역사항</label>
					<div class="form-check form-check-inline">
						<input type="text" class="form-control" name="prefMilitary"
							readonly value="${pre.prefMilitary }" />
					</div>
				</div>
			</div>

			<div class="row mb-3">
				<div class="col-md-12">
					<label for="prefPatriot" class="col-md-2 col-form-label fw-bold">보훈대상</label>
					<div class="form-check form-check-inline">
						<c:if test="${pre.prefPatriot eq 'N' }">
							<input type="text" class="form-control" name="prefPatriot"
								readonly value="비대상" />
						</c:if>
						<c:if test="${pre.prefPatriot eq 'Y' }">
							<input type="text" class="form-control" name="prefPatriot"
								readonly value="대상" />
						</c:if>
					</div>
				</div>
			</div>

			<div class="row mb-3">
				<div class="col-md-12">
					<label for="prefDisability" class="col-md-2 col-form-label fw-bold">장애여부</label>
					<div class="form-check form-check-inline">
						<c:if test="${pre.prefDisability eq 'N' }">
							<input type="text" class="form-control" name="prefDisability"
								readonly value="비장애인" />
						</c:if>
						<c:if test="${pre.prefDisability eq 'Y' }">
							<input type="text" class="form-control" name="prefDisability"
								readonly value="장애인" />
						</c:if>
					</div>
				</div>
			</div>

			<div class="row mb-3">
				<div class="col-md-12">
					<label for="prefEmploySupport"
						class="col-md-2 col-form-label fw-bold">고용지원금</label>
					<div class="form-check form-check-inline">
						<c:if test="${pre.prefEmploySupport eq 'N' }">
							<input type="text" class="form-control" name="prefEmploySupport"
								readonly value="비대상자" />
						</c:if>
						<c:if test="${pre.prefEmploySupport eq 'Y' }">
							<input type="text" class="form-control" name="prefEmploySupport"
								readonly value="대상자" />
						</c:if>
					</div>
				</div>

			</div>

			<div class="row mb-3">
				<label for="prefHobbies" class="col-md-2 col-form-label fw-bold">취미,관심</label>
				<div class="col-md-10">
					<input type="text" class="form-control" id="prefHobbies"
						name="prefHobbies" readonly value="${pre.prefHobbies }">
				</div>
			</div>

			<div class="row mb-3">
				<label for="prefSkills" class="col-md-2 col-form-label fw-bold">특기</label>
				<div class="col-md-10">
					<input type="text" class="form-control" id="prefSkills"
						name="prefSkills" readonly value="${pre.prefSkills }">
				</div>
			</div>
			<hr style="border: 1px solid black;">
		</c:forEach>
	</c:if>

	<c:if test="${empty member.prefList[0].prefNo }">
		<div class="row mb-6">
			<label class="col-md-6 col-form-label fw-bold" data-required="true">등록된
				취업우대 및 특이사항이 없습니다.</label>
		</div>

		<hr style="border: 1px solid black;">
	</c:if>


	<section id="awards"></section>
	<br>
	<div class="row mb-3">
		<div class="col-12">
			<h3 class="fw-bold mb-2">수상경력</h3>
		</div>
	</div>
	<hr style="border: 2px solid black;">

	<c:if test="${!empty member.awardList[0].awardNo }">
		<c:forEach items="${member.awardList}" var="award">


			<div>
				<div class="row mb-3">
					<label for="awardTitle" class="col-md-2 col-form-label fw-bold"
						data-required="true">수상내역</label>
					<div class="col-md-8">
						<input type="text" class="form-control" id="awardTitle"
							name="awardTitle" readonly value="${award.awardTitle }">
					</div>
				</div>
				<div class="row mb-3">
					<label for="awardIssuer" class="col-md-2 col-form-label fw-bold">수여기관</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="awardIssuer"
							name="awardIssuer" readonly value="${award.awardIssuer }">
					</div>
					<label for="awardDate" class="col-md-2 col-form-label fw-bold">수상날짜</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="awardDate"
							name="awardDate" readonly value="${award.awardDate }">
					</div>
				</div>
			</div>
			<hr style="border: 1px solid black;">
		</c:forEach>
	</c:if>
	<c:if test="${empty member.awardList[0].awardNo }">
		<div class="row mb-6">
			<label class="col-md-6 col-form-label fw-bold" data-required="true">등록된
				수상경력이 없습니다.</label>
		</div>
		<hr style="border: 1px solid black;">
	</c:if>

	<br>
	<div class="row mb-3">
		<div class="col-12">
			<h3 class="fw-bold mb-2">교육수료사항</h3>
		</div>
	</div>
	<hr style="border: 2px solid black;">
	<div>
		<c:if test="${!empty member.eduList[0].eduNo }">
			<c:forEach items="${member.eduList}" var="edu">
				<div class="row mb-3">
					<label for="eduTitle" class="col-md-2 col-form-label fw-bold"
						data-required="true">과정명</label>
					<div class="col-md-8">
						<input type="text" class="form-control" id="eduTitle"
							name="eduTitle" readonly value="${edu.eduTitle }">
					</div>
				</div>
				<div class="row mb-3">
					<label for="eduInstitution" class="col-md-2 col-form-label fw-bold">교육기관</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="eduInstitution"
							name="eduInstitution" readonly value="${edu.eduInstitution }">
					</div>
				</div>
				<div class="row mb-3">
					<label for="eduDate" class="col-md-2 col-form-label fw-bold">교육기간</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="eduSdt" name="eduSdt"
							readonly value="${edu.eduSdt }">
					</div>
					<label for="eduDate" class="col-md-1 col-form-label fw-bold">~</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="eduEdt" name="eduEdt"
							readonly value="${edu.eduEdt }">
					</div>
				</div>
				<hr style="border: 1px solid black;">
			</c:forEach>
		</c:if>
		<c:if test="${empty member.eduList[0].eduNo }">
			<div class="row mb-6">
				<label class="col-md-6 col-form-label fw-bold" data-required="true">등록된
					교육수료사항이 없습니다.</label>
			</div>
			<hr style="border: 1px solid black;">
		</c:if>
	</div>
	<section id="language-skills"></section>
	<br>
	<div class="row mb-3">
		<div class="col-md-12">
			<h3 class="fw-bold mb-2">어학능력</h3>
		</div>
	</div>

	<hr style="border: 2px solid black;">

	<c:if test="${!empty member.languageList[0].langNo }">
		<c:forEach items="${member.languageList}" var="language">
			<div>

				<div class="row mb-3">
					<label for="lang" class="col-md-2 col-form-label fw-bold">어학능력</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="langNm" name="langNm"
							readonly value="${language.langNm }">
					</div>
					<label for="lang" class="col-md-2 col-form-label fw-bold">회화수준</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="langSpeakingLevel"
							name="langSpeakingLevel" readonly
							value="${language.langSpeakingLevel }">
					</div>
				</div>
				<div class="row mb-3">
					<label for="lang" class="col-md-2 col-form-label fw-bold">독해수준</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="langReadingLevel"
							name="langReadingLevel" readonly
							value="${language.langReadingLevel }">
					</div>
					<label for="lang" class="col-md-2 col-form-label fw-bold">작문수준</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="langWritingLevel"
							name="langWritingLevel" readonly
							value="${language.langWritingLevel }">
					</div>
				</div>
			</div>
			<hr style="border: 1px solid black;">
		</c:forEach>
	</c:if>


	<br>
	<c:if test="${empty member.languageList[0].langNo }">
		<label class="col-md-6 col-form-label fw-bold" data-required="true">등록된
			어학능력이 없습니다.</label>
		<hr style="border: 1px solid black;">
	</c:if>


	<div class="row mb-3">
		<div class="col-md-12">
			<h3 class="fw-bold mb-2">어학시험</h3>
		</div>
	</div>

	<hr style="border: 2px solid black;">


	<c:if test="${!empty member.langTestList[0].langTestNo }">
		<c:forEach items="${member.langTestList}" var="langTest">
			<div>
				<div class="row mb-3">
					<label for="lang" class="col-md-2 col-form-label fw-bold">공인시험명</label>
					<div class="col-md-3">
						<input type="text" class="form-control" id="langTestName"
							name="langTestName" readonly value="${langTest.langTestName }">
					</div>
					<label for="lang" class="col-md-2 col-form-label fw-bold">점수
						및 등급</label>
					<div class="col-md-3">
						<input type="text" class="form-control" id="langTestScore"
							name="langTestScore" readonly value="${langTest.langTestLevel }">
					</div>
				</div>
				<div class="row mb-3">
					<label for="lang" class="col-md-2 col-form-label fw-bold">취득일자</label>
					<div class="col-md-3">
						<input type="text" class="form-control" id="langTestDate"
							name="langTestDate" readonly value="${langTest.langTestDate }">
					</div>
				</div>
			</div>
			<div>
				<hr style="border: 1px solid black;">
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${empty member.langTestList[0].langTestNo }">
		<div>
			<label class="col-md-6 col-form-label fw-bold" data-required="true">등록된
				어학시험이 없습니다.</label>
			<hr style="border: 1px solid black;">
		</div>
	</c:if>




	<br>
	<div class="row mb-3">
		<div class="col-12">
			<h3 class="fw-bold mb-2">해외연수 및 경험</h3>
		</div>
	</div>
	<hr style="border: 2px solid black;">

	<c:if test="${!empty member.experienceList[0].expNo}">
		<c:forEach items="${member.experienceList}" var="expe">


			<div>
				<div class="row mb-3">
					<label for="expCountry" class="col-md-2 col-form-label fw-bold"
						data-required="true">연수국가</label>
					<div class="col-md-3">
						<input type="text" class="form-control" id="expCountry"
							name="expCountry" readonly value="${expe.expCountry }">
					</div>
				</div>
				<div class="row mb-3">
					<label for="expDate" class="col-md-2 col-form-label fw-bold">연수기간</label>
					<div class="col-md-3">
						<input type="text" id="expSdt" name="expSdt" class="form-control"
							readonly value="${expe.expSdt }">
					</div>
					<div class="col-md-1 text-center">~</div>
					<div class="col-md-3">
						<input type="text" id="expEdt" name="expEdt" class="form-control"
							readonly value="${expe.expEdt }">
					</div>
				</div>
				<div class="row mb-3">
					<label for="expDesc" class="col-md-2 col-form-label fw-bold">목적
						및 내용</label>
					<div class="col-md-10">
						<textarea id="eduDesc" name="expDesc" class="form-control"
							readonly>${expe.expDesc }</textarea>
					</div>
				</div>
			</div>
			<hr style="border: 1px solid black;">
		</c:forEach>
	</c:if>

	<c:if test="${empty member.experienceList[0].expNo}">
		<label class="col-md-6 col-form-label fw-bold" data-required="true">등록된
			해외연수 및 경험이 없습니다.</label>
		<hr style="border: 1px solid black;">
	</c:if>
	<section id="activities"></section>

	<br>
	<div class="row mb-3">
		<div class="col-12">
			<h3 class="fw-bold mb-2">봉사활동 및 주요활동</h3>
		</div>
	</div>
	<hr style="border: 2px solid black;">

	<c:if test="${!empty member.activityList[0].activityNo }">
		<c:forEach items="${member.activityList}" var="activ">

			<div>
				<div class="row mb-3">
					<label for="activity" class="col-md-2 col-form-label fw-bold"
						data-required="true">기관,단체명</label>
					<div class="col-md-8">
						<input type="text" class="form-control" id="activityOrganization"
							name="activityOrganization" readonly
							value="${activ.activityOrganization }">
					</div>
				</div>
				<div class="row mb-3">
					<label for="activity" class="col-md-2 col-form-label fw-bold">활동내용</label>
					<div class="col-md-10">
						<textarea id="activityDesc" name="activityDesc"
							class="form-control" readonly>${activ.activityDesc }</textarea>
					</div>
				</div>
				<div class="row mb-3">
					<div class="row mb-3">
						<label for="activityDate" class="col-md-2 col-form-label fw-bold"
							data-required="true">활동기간</label>

						<div class="col-md-3">
							<input type="text" class="form-control" id="activitySdt"
								name="activitySdt" readonly value="${activ.activitySdt }">
						</div>
						<label for="career"
							class="col-md-1 col-form-label fw-bold text-center"> <span>~</span>
						</label>
						<div class="col-md-3">
							<c:if test="${!empty activ.activityEdt}">
								<input type="text" class="form-control" id="activityEdt"
									name="activityEdt" readonly value="${activ.activityEdt}" />
							</c:if>
							<c:if test="${empty activ.activityEdt}">
								<input type="text" class="form-control" id="activityEdt"
									name="activityEdt" readonly value="참여중" />
							</c:if>
						</div>
					</div>
				</div>
			</div>
			<hr style="border: 1px solid black;">
		</c:forEach>
	</c:if>

	<c:if test="${empty member.activityList[0].activityNo }">
		<label class="col-md-6 col-form-label fw-bold" data-required="true">등록된
			봉사활동 및 주요활동이 없습니다.</label>
		<hr style="border: 1px solid black;">
	</c:if>

	<section id="working-conditions"></section>
	<br>
	<div class="row mb-3">
		<div class="col-12">
			<h3 class="fw-bold mb-2">희망근무조건</h3>
		</div>
	</div>
	<hr style="border: 2px solid black;">

	<c:if test="${!empty member.workCondList[0].workCondNo }">
		<c:forEach items="${member.workCondList }" var="cond">

			<div id="workCity">
				<div class="row mb-6">
					<label for="Work_Cond" class="col-md-2 col-form-label fw-bold"
						data-required="true">근무지역</label>
					<div class="col-6">

						<c:if test="${not empty cond.workCity}">
							<c:set var="workcity" value="" scope="page" />
							<c:forEach items="${cond.workCity}" var="city">
								<c:set var="workcity"
									value="${workcity}${city.sidoCd} ${city.gugunCd}, "
									scope="page" />
							</c:forEach>
							<input type="text" class="form-control" readonly
								value="${workcity}" />
						</c:if>
						<c:if test="${empty cond.workCity}">
							<input type="text" class="form-control" readonly
								value="등록된 근무지역이 없습니다." />
						</c:if>
					</div>
				</div>
			</div>

			<div class="row mb-3">
				<label for="Work_Cond" class="col-md-2 col-form-label fw-bold">재택근무
					여부</label>
				<div class="col-md-2">
					<c:if test="${cond.workCondRemote eq 'N' }">
						<input type="text" class="form-control" id="workCondRemote"
							name="workCondRemote" readonly value="불가능">
					</c:if>
					<c:if test="${cond.workCondRemote eq 'Y' }">
						<input type="text" class="form-control" id="workCondRemote"
							name="workCondRemote" readonly value="가능">
					</c:if>
				</div>
			</div>
			<div class="row mb-3">
				<label for="Work_Cond" class="col-md-2 col-form-label fw-bold"
					data-required="true">직무기술</label>
				<div class="col-md-6">
					<input type="text" class="form-control" id="workCondJobType"
						name="workCondJobType" readonly value="${cond.workCondJobType }">
				</div>
			</div>

			<div class="row mb-3">
				<label for="Work_Cond" class="col-md-2 col-form-label fw-bold"
					data-required="true">근무형태</label>
				<div class="col-md-6">
					<input type="text" class="form-control" id="workCondType"
						name="workCondType" readonly value="${cond.workCondType }">
				</div>
			</div>
			<div class="row mb-3">
				<label for="Work_Cond" class="col-md-2 col-form-label fw-bold"
					data-required="true">희망연봉</label>
				<div class="col-md-6">
					<input type="text" class="form-control" id="workCondSalary"
						name="workCondSalary" readonly value="${cond.workCondSalary }">
				</div>
			</div>

			<hr style="border: 1px solid black;">

		</c:forEach>
	</c:if>
	<c:if test="${empty member.workCondList[0].workCondNo }">
		<label class="col-md-6 col-form-label fw-bold" data-required="true">등록된
			희망근무조건이 없습니다.</label>
		<hr style="border: 1px solid black;">
	</c:if>

	<section id="self-introduction"></section>
	<br>
	<div class="row mb-3">
		<div class="col-12">
			<h3 class="fw-bold mb-2">자기소개서</h3>
		</div>
	</div>
	<hr style="border: 2px solid black;">
	<div class="container my-4">
		<c:if test="${empty member.intro[0].introNo}">
			<p class="text-muted">등록된 자기소개서가 없습니다.</p>
		</c:if>
		<!-- 자기소개서 -->
		<c:if test="${!empty member.intro[0].introNo}">
			<c:forEach items="${member.intro}" var="introItem">
				<div class="border p-4 rounded mb-3">
					<!-- introItem의 대제목 출력 -->
					<h4 class="fw-bold border-bottom pb-2 mb-3">${introItem.introTitle}</h4>
					<c:if test="${!empty introItem.introDetail[0].introDetailNo }">
						<c:forEach items="${introItem.introDetail}" var="detail">
							<!-- 상세내용 반복 영역 -->
							<div class="row mb-3">
								<label for="selfIntroductionTitle"
									class="col-md-2 col-form-label fw-bold">제목</label>
								<div class="col-md-10">
									<input type="text" class="form-control"
										id="selfIntroductionTitle" name="selfIntroductionTitle"
										readonly value="${detail.introDetailTitle }">
								</div>
							</div>
							<div class="row mb-3">
								<label for="selfIntroductionContent"
									class="col-md-2 col-form-label fw-bold">내용</label>
								<div class="col-md-10">
									<textarea id="selfIntroductionContent"
										name="selfIntroductionContent" class="form-control" rows="8"
										readonly>${detail.introDetailCont }</textarea>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<!-- 상세내용이 없을 때 메시지 -->
					<c:if test="${empty introItem.introDetail[0].introDetailNo }">
						<p class="text-muted">등록된 자기소개서 상세 내용이 없습니다.</p>
					</c:if>
				</div>
				<hr style="border: 1px solid black;">
			</c:forEach>
		</c:if>

	</div>

</div>

<div class="timeline-navigation">
	<div class="timeline-item" onclick="scrollToSection('basic-info')">
		<div class="circle"></div>
		<span>기본정보</span>
	</div>
	<div class="timeline-item" onclick="scrollToSection('academic-info')">
		<div class="circle"></div>
		<span>학력사항</span>
	</div>
	<div class="timeline-item" onclick="scrollToSection('career')">
		<div class="circle"></div>
		<span>경력사항</span>
	</div>
	<div class="timeline-item" onclick="scrollToSection('certification')">
		<div class="circle"></div>
		<span>자격증</span>
	</div>
	<div class="timeline-item" onclick="scrollToSection('computer-skills')">
		<div class="circle"></div>
		<span>컴퓨터 활용능력</span>
	</div>
	<div class="timeline-item" onclick="scrollToSection('preferences')">
		<div class="circle"></div>
		<span>우대 및 특이사항</span>
	</div>
	<div class="timeline-item" onclick="scrollToSection('awards')">
		<div class="circle"></div>
		<span>수상경력</span>
	</div>
	<div class="timeline-item" onclick="scrollToSection('language-skills')">
		<div class="circle"></div>
		<span>어학능력</span>
	</div>
	<div class="timeline-item" onclick="scrollToSection('activities')">
		<div class="circle"></div>
		<span>대외활동</span>
	</div>
	<div class="timeline-item"
		onclick="scrollToSection('working-conditions')">
		<div class="circle"></div>
		<span>근무조건</span>
	</div>
	<div class="timeline-item"
		onclick="scrollToSection('self-introduction')">
		<div class="circle"></div>
		<span>자기소개서</span>
	</div>
	<button class="proposal-btn btn btn-primary m-1"
		onclick="scrollToSection('position-proposal')">포지션 제안</button>
	<button class="top btn btn-secondary m-1" onclick="scrollToTop()">Top</button>
</div>


<div class="modal fade" id="addModal" tabindex="-1"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalCenter">포지션 제안</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<div>
					<label for="employSelect">공고 선택</label> <select id="employSelect"
						class="form-select">
						<option value="">공고를 선택하세요</option>
						<c:forEach items="${empList }" var="item">
							<option value="${item.employNo }">${item.employTitle }</option>
						</c:forEach>
					</select>
				</div>

				<div class="mt-3">
					<label for="fieldSelect">모집 분야 선택</label> <select id="fieldSelect"
						class="form-select" disabled>
						<option value="">모집 분야를 선택하세요</option>
					</select>
				</div>
			</div>

			<div class="add-modal-footer d-flex justify-content-end p-3">
				<button type="button" class="btn btn-secondary me-2"
					data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" id="update-button">제안</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
    const employList = [
        <c:forEach var="employ" items="${empList}">
            {
                employNo: "${employ.employNo}",
                employTitle: "${employ.employTitle}",
                fieldList: [
                    <c:forEach var="field" items="${employ.fieldList}" varStatus="status">
                        {
                            filedNo: "${field.filedNo}",
                            filedNm: "${field.filedNm}"
                        }<c:if test="${!status.last}">,</c:if>
                    </c:forEach>
                ]
            }<c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];
</script>
<script
	src="${pageContext.request.contextPath }/resources/js/talent/talentDetail.js"></script>