<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- Account CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/memberForm/style.css">
   <main class="position-relative">
      <div class="pattern-square"></div>
      <!--Pageheader start-->
      <section class="py-5 py-lg-4">
         <div class="container">
            <div class="row">
               <div class="col-xl-4 offset-xl-4 col-md-12 col-12">
                  <div class="text-center">
                     <a href='<c:url value="/index.do" />'><img src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/logo/StackUpLogo.png" alt="stackUp로고" class="mb-3" id="logo" /></a>
                     <h1 class="mb-1">기업 회원가입</h1>
                  </div>
               </div>
            </div>
         </div>
      </section>
      <!--Pageheader end-->
      <!--Sign up start-->
      <button type="button" class="btn btn-sm btn-primary" id="insertInputBtn2" style="position: absolute; right: 0px;">데이터 입력</button>
      <section>
         <div class="container">
            <div class="row justify-content-center mb-6">
               <div class="col-xl-5 col-lg-6 col-md-8 col-12">
                  <div class="card shadow-sm mb-3">
                     <div class="card-body">
                     <c:url value="/insertUser/company" var="compAccountUrl" />
                       <form:form class="needs-validation" action="${compAccountUrl}" novalidate="true"  method="POST" modelAttribute="user" >
                       	 <form:hidden path="userCd" value="COMPANY" />
                       	 <form:hidden path="certStatusVO.emailYn" value="N" />
                       	 <form:hidden path="certStatusVO.HpYn" value="Y" />
                         <div class="shadow-none p-3 mb-1 bg-light rounded">
	                       <input type="checkbox" class="cbx_chkAll form-check-input me-2" id="flexCheckDefault1" />
						   <label class="form-check-label" for="flexCheckDefault">
						     모두 확인, <span class="text-primary">동의</span>합니다.
						   </label>
                     	 </div>
	                     <div class="alert alert-info" role="alert">
	                       <div>
						     <input type="checkbox" class="chk chk-submit form-check-input me-2" id="flexCheckDefault3"  />
						     <label class="form-check-label" for="flexCheckDefault">
						       <strong><span class="text-danger">(필수)</span></strong> 스택업 회원 이용약관 동의
						     </label>
						   </div>
						   <div class="mt-3">
						     <input type="checkbox" class="chk chk-submit form-check-input me-2" id="flexCheckDefault4" />
							 <label class="form-check-label" for="flexCheckDefault">
							   <strong><span class="text-danger">(필수)</span></strong> 개인정보 수집 및 이용 동의
							 </label>
						   </div>
					     </div>
                         <div class="mb-3 row">
                           <div class="col-xl-8">
                            <label for="signupCompNumInput" class="form-label">
                              사업자등록번호
                              <span class="required-asterisk">*</span>
                            </label>
                            <input type="text" class="form-control" id="signupCompNumInput" name="companyVO.compNum"  placeholder="사업자등록번호" required autocomplete="off" />
                            </div>
                            <div class="col-xl-4 mt-1comma9">
                           	 <input type="button" class="btn btn-primary m-19" id="compNumCheck" value="등록번호 확인" />
                           </div>
                         </div>
                         <div class="mb-3">
                            <label for="signupCompNmInput" class="form-label">
                              회사명
                              <span class="required-asterisk">*</span>
                            </label>
                            <input type="text" class="form-control" id="signupCompNmInput" name="companyVO.compNm"  placeholder="회사명" required autocomplete="off" />
                         </div>
                         <div class="mb-3">
                            <label for="signupCompRepInput" class="form-label">
                              대표자명
                              <span class="required-asterisk">*</span>
                            </label>
                            <input type="text" class="form-control" id="signupCompRepInput" name="companyVO.compRepresentative"  placeholder="대표자명" required autocomplete="off" />
                         </div>
                         <div class="mb-3">
                            <label for="signupCompTypeInput" class="form-label">
                              기업구분
                              <span class="required-asterisk">*</span>
                            </label>
                            <form:select path="companyVO.compType" class="form-select" id="signupCompTypeInput">
								<form:option value="" label="기업구분 선택" />
								<c:forEach items="${companySizeCodeList}" var="companySizeCode">
									<form:option value="${companySizeCode['codeCd'] }" label="${companySizeCode['codeNm'] }" />
								</c:forEach>
							</form:select>
                         </div>
                         <div class="mb-3">
                            <label for="signupCompIndInput" class="form-label">
                              직종
                              <span class="required-asterisk">*</span>
                            </label>
                            <form:select path="companyVO.compInd" class="form-select" id="signupCompIndInput">
                            	<form:option value="" label="직종 선택" />
                            	
							</form:select>
                         </div>
                         <div class="mb-3">
                            <label for="signupCompJobInput" class="form-label">
                              직무
                              <span class="required-asterisk">*</span>
                            </label>
                            <form:select path="companyVO.compJob" class="form-select" id="signupCompJobInput">
                            	<form:option value="" label="직무 선택" />
                            	
							</form:select>
                         </div>
                         <div class="mb-3">
                            <label for="signupCompJobDetailInput" class="form-label">
                              직무상세
                              <span class="required-asterisk">*</span>
                            </label>
                            <form:select path="companyVO.compJobDetail" class="form-select" id="signupCompJobDetailInput">
                            	<form:option value="" label="직무상세 선택" />
                            	
							</form:select>
                         </div>
                         <div class="mb-3 row">
                           <div class="col-xl-8">
                              <label for="signupZipCodeInput" class="form-label">
                                회사주소
                                <span class="required-asterisk">*</span>
                              </label>
                              <form:input type="text" class="form-control" id="signupZipCodeInput" path="companyVO.compZip" placeholder="우편번호" required="true" autocomplete="off" />
                              <form:errors path="memberVO.memZip" cssClass="text-danger"/>
                              <div class="invalid-feedback">우편번호를 입력해주세요</div>
						   </div>
                           <div class="col-xl-4 mt-1comma9">
                           	   <input type="button" class="btn btn-primary m-19" onclick="kakaoAddress();" value="우편번호 찾기" />
                           </div>
                         </div>
                         <div class="mb-3">
                             <form:input type="text" class="form-control" id="signupAddr1Input" path="companyVO.compAddr1" placeholder="기본주소" required="true" autocomplete="off" />
                             <form:errors path="memberVO.memAddr1" cssClass="text-danger"/>
                             <div class="invalid-feedback">주소를 입력해주세요</div>
                         </div>
                         <div class="mb-3">
                            <form:input type="text" class="form-control" id="signupAddr2Input" path="companyVO.compAddr2" placeholder="상세주소" autocomplete="off" />
                            <form:errors path="memberVO.memAddr1" cssClass="text-danger"/>
                            <div class="invalid-feedback">상세주소를 입력해주세요</div>
                         </div>
                         <div class="mb-3 row">
                           <div class="col-xl-8">
                             <label for="signupIDInput" class="form-label">
                               아이디
                               <span class="required-asterisk">*</span>
                             </label>
                             <form:input type="text"  class="form-control" id="signupIDInput" path="userId" placeholder="4~20자리의 영문 소문자, 숫자만 사용가능" required="true" autocomplete="off" />
                             <form:errors path="userId" cssClass="text-danger"/>
                             <div class="validateId">아이디 : 4~20자의 영문 소문자, 숫자만 사용 가능합니다.</div>
                             <div class="invalid-feedback">아이디를 입력해주세요</div>                           
                             
                           </div>
                           <div class="col-xl-4 mt-1comma9">
                           	 <input type="button" class="btn btn-primary m-19" id="idCheck" value="아이디 확인" />
                           </div>
                         </div>
                         <div class="mb-3">
                           <label for="formSignUpPassword" class="form-label">
                             비밀번호
                             <span class="required-asterisk">*</span>
                           </label>
                           <div class="password-field position-relative">
                             <form:password class="form-control fakePassword" path="userPass" id="formSignUpPassword"  placeholder="8~20자의 영문, 소문자, 특수문자 조합" required="true" />
                             <form:errors path="userPass" cssClass="text-danger"/>
                             <span><i class="bi bi-eye-slash passwordToggler"></i></span>
                             <div class="validateId">비밀번호 : 영문 소문자, 숫자, 특수문자(!@#$%^&*()) 한자리씩 포함하고 8자~20자만 사용 가능합니다.</div>
                             <div class="invalid-feedback">비밀번호를 입력해주세요</div>
                           </div>
                         </div>
                         <div class="mb-3">
                           <label for="formSignUpConfirmPassword" class="form-label">
                             비밀번호 확인
                             <span class="required-asterisk">*</span>
                           </label>
                           <div class="password-field position-relative">
                             <input type="password" class="form-control fakePassword"  id="formSignUpConfirmPassword" placeholder="비밀번호 확인" required />
                             <span><i class="bi bi-eye-slash passwordToggler"></i></span>
                             <div class="invalid-feedback">비밀번호가 일치하지 않습니다.</div>
                           </div>
                         </div>
                         <div class="mb-3">
                            <label for="signupCompTelInput" class="form-label">
                              사무실 전화번호
                              <span class="required-asterisk">*</span>
                            </label>
                            <input type="text" class="form-control" id="signupCompTelInput" name="companyVO.compPhone"  placeholder="사무실 전화번호" required autocomplete="off" />
                         </div>
                         <div class="mb-3">
                           <label for="signupEmailInput" class="form-label">
                             담당자 이메일
                             <span class="required-asterisk">*</span>
                           </label>
                           <form:input type="email" class="form-control" id="signupEmailInput" path="companyVO.compEmail"  placeholder="담당자 이메일" required="true" autocomplete="off" />
                           <div class="invalid-feedback">담당자 이메일을 입력해주세요</div>
                         </div>
                         <div class="mb-3 row">
                           <div class="col-xl-8">
                             <label for="signupPhoneInput" class="form-label">
                               담당자 휴대폰번호
                               <span class="required-asterisk">*</span>
                             </label>
                             <form:input type="text" class="form-control" id="signupPhoneInput" path="companyVO.compMobile"  placeholder="'-' 없이 숫자만 입력" required="true" autocomplete="off" />
                             <div class="invalid-feedback">담당자 휴대폰번호를 입력해주세요</div>                           
                           </div>
                           <div class="col-xl-4">
                           	 <input type="button" class="btn btn-primary m-19" id="certification" data-user="company" value="인증번호 받기" />
                           </div>
                         </div>
                         <div class="mb-3 row certDiv" id="certInputDiv">
                         	<div class="col-xl-8">
                              <label for="signupCertInput" class="form-label">
                                인증번호 확인
                                <span class="required-asterisk">*</span>
                              </label>
                                <input type="text" class="form-control" id="signupCertInput" required autocomplete="off" />
                              <div class="invalid-feedback">인증번호를 입력해주세요</div>
                            </div>
                            <div class="col-xl-4">
                           	 <input type="button" class="btn btn-primary m-19" id="certNumBtn" value="인증하기" />
                           </div>
                         </div>
                         <div class="mt-5 d-grid">
                            <button class="btn btn-primary" id="btn-submit" type="submit">가입하기</button>
                         </div>
                       </form:form>
                     </div>
                  </div>
                  <span>
                     이미 회원가입을 하셨나요?
                     <a href='<c:url value="/login" />' class="text-primary">로그인</a>
                  </span>
               </div>
            </div>
         </div>
      </section>
      <!--Sign up end-->
      <div class="position-absolute end-0 bottom-0 m-4">
         <div class="dropdown">
            <button class="btn btn-light btn-icon rounded-circle d-flex align-items-center" type="button"
               aria-expanded="false" data-bs-toggle="dropdown" aria-label="Toggle theme (auto)">
               <i class="bi theme-icon-active"></i>
               <span class="visually-hidden bs-theme-text">Toggle theme</span>
            </button>
            <ul class="dropdown-menu dropdown-menu-end shadow" aria-labelledby="bs-theme-text">
               <li>
                  <button type="button" class="dropdown-item d-flex align-items-center" data-bs-theme-value="light"
                     aria-pressed="false">
                     <i class="bi theme-icon bi-sun-fill"></i>
                     <span class="ms-2">Light</span>
                  </button>
               </li>
               <li>
                  <button type="button" class="dropdown-item d-flex align-items-center" data-bs-theme-value="dark"
                     aria-pressed="false">
                     <i class="bi theme-icon bi-moon-stars-fill"></i>
                     <span class="ms-2">Dark</span>
                  </button>
               </li>
               <li>
                  <button type="button" class="dropdown-item d-flex align-items-center active"
                     data-bs-theme-value="auto" aria-pressed="true">
                     <i class="bi theme-icon bi-circle-half"></i>
                     <span class="ms-2">Auto</span>
                  </button>
               </li>
            </ul>
         </div>
      </div>
   </main>
<!-- Page JS File -->
<script>const companyJson = ${companyJson}</script>
<script src="${pageContext.request.contextPath }/resources/js/users/Account.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/users/companyForm.js"></script>