<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<form:form class="form" method="put" modelAttribute="member"
	id="infoForm">
	<form:hidden path="memId" />
	<div class="row">
		<div class="col-12">
			<h3 class="fw-bold mb-2">회원정보 수정</h3>
		</div>
	</div>
	<hr style="border: 2px solid black;">
	<div class="row">
		<div class="col-md-2"></div>
		<!-- 입력 필드 영역 (오른쪽) -->
		<div class="col-md-10">
			<div class="row mb-3">
				<label for="memNm" class="col-md-3 col-form-label fw-bold"
					data-required="true">이름</label>
				<div class="col-md-9">
					<form:input type="text" class="form-control w-50" id="memNm"
						path="memNm" placeholder="한글" autocomplete="off" required="true" />
				</div>
			</div>
			<div class="row mb-3">
				<label for="memRegno" class="col-md-3 col-form-label fw-bold"
					data-required="true">생년월일</label>
				<div class="col-md-9">
					<div class="mt-2 d-flex gap-2">
						<form:input type="text" class="form-control w-50" id="memRegno"
							path="memRegno" placeholder="ex) 19850213" autocomplete="off"
							required="true" />
						<form:select path="memDateType" class="form-select w-auto">
							<form:option value="" label="선택" />
							<c:forEach items="${codeList}" var="code">
								<c:if test="${code['codeParent'] eq 'date_type' }">
									<form:option value="${code['codeCd'] }"
										label="${code['codeNm'] }" />
								</c:if>
							</c:forEach>
						</form:select>


					</div>
					<div class="mt-2">
						<c:forEach items="${codeList}" var="code" varStatus="status">
							<c:if test="${code['codeParent'] eq 'gender' }">
								<div class="form-check form-check-inline">
									<c:if test="${status.first }">
										<form:radiobutton class="form-check-input" id="memGen"
											path="memGen" value="${code['codeCd'] }" />
										<label class="form-check-label" for="memGen1">${code['codeNm'] }</label>
									</c:if>
									<c:if test="${!status.first }">
										<form:radiobutton class="form-check-input" id="memGen"
											path="memGen" value="${code['codeCd'] }" />
										<label class="form-check-label" for="memGen2">${code['codeNm'] }</label>
									</c:if>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="row mb-3">
				<label for="memHp" class="col-md-3 col-form-label fw-bold"
					data-required="true">휴대폰번호</label>
				<div class="col-md-9 d-flex gap-2">
					<form:input type="text" class="form-control w-50 memHpInput" id="memHp"
						path="memHp" placeholder="ex) 01012341234" autocomplete="off"
						required="true" readonly="true" />
					<form:hidden path="certStatus.hpYn" id="hpYn"/>
					<c:if test="${member['certStatus']['hpYn'] eq 'Y' }">
						<input type="button" class="btn btn-primary certPY" value="인증완료" />
					</c:if>
					<c:if test="${member['certStatus']['hpYn'] eq 'N' }">
						<input type="button" class="btn btn-primary certPN" value="미인증" />
					</c:if>
				</div>
			</div>
			<div class="row mb-3">
				<label for="memEmail" class="col-md-3 col-form-label fw-bold"
					data-required="true">이메일</label>
				<div class="col-md-9 d-flex gap-2">
					<form:input type="email" class="form-control w-50 memEmailInput" id="memEmail"
						path="memEmail" placeholder="이메일 주소를 입력하세요" autocomplete="off"
						required="true" />
					<form:hidden path="certStatus.emailYn" id="emailYn"/>
					<c:if test="${member['certStatus']['emailYn'] eq 'Y' }">
						<input type="button" class="btn btn-primary certEY" value="인증완료" />
					</c:if>
					<c:if test="${member['certStatus']['emailYn'] eq 'N' }">
						<input type="button" class="btn btn-secondary certEN" value="미인증" />
					</c:if>
				</div>
			</div>
			<div class="row mb-3">
				<label for="memAddr1" class="col-md-3 col-form-label fw-bold"
					data-required="true">주소</label>
				<div class="col-md-9">
					<div class="d-flex gap-2">
						<form:input type="text" class="form-control mb-2 w-50"
							id="signupZipCodeInput" path="memZip" placeholder="우편번호"
							autocomplete="off" required="true" />
						<input type="button" class="btn btn-primary mb-2"
							onclick="kakaoAddress();" value="주소 찾기" />
					</div>
					<div>
						<form:input type="text" class="form-control mb-2 w-75"
							id="signupAddr1Input" path="memAddr1" placeholder="기본주소"
							autocomplete="off" required="true" />
						<form:input type="text" class="form-control w-75"
							id="signupAddr2Input" path="memAddr2" placeholder="상세주소"
							autocomplete="off" />

					</div>
				</div>
			</div>
			<div class="row mb-3">
				<label for="memUrl" class="col-md-3 col-form-label fw-bold">홈페이지/SNS</label>
				<div class="col-md-9">
					<form:input type="text" class="form-control w-75" id="memUrl"
						path="memUrl" placeholder="http://www.example.com"
						autocomplete="off" />
				</div>
			</div>
		</div>
	</div>
	<hr style="border: 1px solid black;">
	<div class="text-end">
		<a href="#" class="btn btn-primary" id="infoSubmit">저장</a>
	</div>
</form:form>
<!-- 회원정보 수정 -->

<!-- 휴대폰번호 인증 Modal-->
<div class="modal fade" id="memInfoPhone" tabindex="-1"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalCenter">
					휴대폰 번호 입력
				</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<h6>휴대폰번호를 입력하여 문자를 받으시고, SMS로 전송된 인증번호를 입력해주세요 :)</h6>
				<form class="needs-validation mt-3  idForm" novalidate id="phoneForm">
					<div class="row">
						<div class="col-xl-3 mt-2">
							<p>휴대폰 번호</p>
						</div>
						<div class="col-xl-5 mb-3 p-0">
							<input type="text" class="form-control forgetPhoneInput"
								placeholder="'-' 없이 숫자만 입력" autocomplete="off" />
						</div>
						<div class="col-xl-4">
							<button class="btn btn-primary col-12 getforgetCertificationPhone"
								id="resetMemPass" type="button">인증번호 받기</button>
						</div>
					</div>
					<div class="row certDivPhone" style="display: none;">
						<div class="col-xl-3 mt-2">
							<p>인증번호</p>
						</div>
						<div class="col-xl-5 mb-3 p-0">
							<input type="text" class="form-control forgetCertPhoneInput"
								placeholder="숫자만 입력" required autocomplete="off" />
						</div>
						<div class="col-xl-4">
							<button class="btn btn-primary forgetCertificationPhoneBtn"
								type="button" data-pass="userPass">인증</button>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>

<!-- 이메일 인증 Modal-->
<div class="modal fade" id="memInfoEmail" tabindex="-1"
	aria-labelledby="exampleModalLabel1" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalCenter">
					이메일 입력
				</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<h6>이메일을 입력하여 인증번호를 받으시고, 이메일로 전송된 인증번호를 입력해주세요 :)</h6>
				<form class="needs-validation mt-3  idForm" novalidate id="emailForm">
					<div class="row">
						<div class="col-xl-2 mt-2">
							<p>이메일</p>
						</div>
						<div class="col-xl-6 mb-3 p-0">
							<input type="text" class="form-control forgetEmailInput"
								placeholder="이메일주소 입력" autocomplete="off" />
						</div>
						<div class="col-xl-4">
							<button class="btn btn-primary col-12 getforgetCertificationEmail"
								id="resetMemPass" type="button">인증번호 받기</button>
						</div>
					</div>
					<div class="row certDivEmail" style="display: none;">
						<div class="col-xl-2 mt-2 pe-0">
							<p>인증번호</p>
						</div>
						<div class="col-xl-6 mb-3 p-0">
							<input type="text" class="form-control forgetCertEmailInput"
								placeholder="숫자만 입력" required autocomplete="off" />
						</div>
						<div class="col-xl-4">
							<button class="btn btn-primary forgetCertificationEmailBtn"
								type="button" data-pass="userPass">인증</button>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>





<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/member/memberInfo.js"></script>