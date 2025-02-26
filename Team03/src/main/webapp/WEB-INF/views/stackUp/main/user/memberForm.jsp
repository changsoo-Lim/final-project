<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
                     <h1 class="mb-1">개인 회원가입</h1>
                  </div>
               </div>
            </div>
         </div>
      </section>
      <!--Pageheader end-->
      <!--Sign up start-->
      <button type="button" class="btn btn-sm btn-primary" id="insertInputBtn" style="position: absolute; right: 0px;">데이터 입력</button>
      <section>
         <div class="container">
            <div class="row justify-content-center mb-6">
               <div class="col-xl-5 col-lg-6 col-md-8 col-12">
                  <div class="card shadow-sm mb-3">
                     <div class="card-body">
                     <c:url value="/insertUser/member" var="memAccountUrl" />
                       <form:form class="needs-validation" action="${memAccountUrl}" novalidate="true"  method="POST" modelAttribute="user" >
                         <form:hidden path="userCd" value="MEMBER" />
                         <form:hidden path="memberVO.memClassify" value="USER002" />
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
						     <input type="checkbox" class="chk chk-submit form-check-input me-2" />
							 <label class="form-check-label" for="flexCheckDefault">
							   <strong><span class="text-danger">(필수)</span></strong> 만 15세 이상입니다.
							 </label>	                     	
	                       </div>
	                       <div class="mt-3">
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
						   <div class="mt-3">
						     <form:checkbox class="chk form-check-input me-2" value="Y" id="flexCheckDefault5" path="statusVO.statusSms" />
							 <label class="form-check-label" for="flexCheckDefault">
							   (선택) 채용 소식 및 지원 관련 수신 동의 - SMS
							 </label>
						   </div>
						   <div class="mt-3">
						     <form:checkbox class="chk form-check-input me-2" value="Y" id="flexCheckDefault6" path="statusVO.statusEmail" />
							 <label class="form-check-label" for="flexCheckDefault">
							   (선택) 채용 소식 및 지원 관련 수신 동의 - 이메일
							 </label>
						   </div>
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
                            <label for="signupFullNameInput" class="form-label">
                              이름
                              <span class="required-asterisk">*</span>
                            </label>
                            <form:input type="text" class="form-control" id="signupFullNameInput" path="memberVO.memNm"  placeholder="이름(실명)" required="true" autocomplete="off" />
                            <form:errors path="memberVO.memNm" cssClass="text-danger"/>
                            <div class="invalid-feedback">이름을 입력해주세요</div>
                         </div>
                         <div class="mb-3 row">
                         	<label for="signupGenderInput" class="form-label">
                              성별
                              <span class="required-asterisk">*</span>
                            </label>
                         	<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                         	  <c:forEach items="${codeList}" var="code" varStatus="status">
								  <c:if test="${status.first }">
								    <form:radiobutton class="btn-check" id="btnradio1" path="memberVO.memGen" value="${code['codeCd'] }" checked="true" />
								    <label class="btn btn-outline-primary" for="btnradio1">${code['codeNm'] }</label>
								  </c:if>
								  <c:if test="${!status.first }">
								    <form:radiobutton class="btn-check" id="btnradio2" path="memberVO.memGen" value="${code['codeCd'] }" />
								    <label class="btn btn-outline-primary" for="btnradio2">${code['codeNm'] }</label>
							      </c:if>
							  </c:forEach>
						    </div>
                            <div class="invalid-feedback">성별을 선택해주세요</div>
                         </div>
                         <div class="mb-3">
                            <label for="signupBirthDateInput" class="form-label">
                              생년월일
                              <span class="required-asterisk">*</span>
                            </label>
                            <form:input type="text" class="form-control" id="signupBirthDateInput" path="memberVO.memRegno"  placeholder="숫자 8자리  ex)19971015" required="true" autocomplete="off" />
                            <form:errors path="memberVO.memRegno" cssClass="text-danger"/>
                            <div class="invalid-feedback">생년월일을 입력해주세요</div>
                         </div>
                         <div class="mb-3 row">
                           <div class="col-xl-8">
                              <label for="signupZipCodeInput" class="form-label">
                                우편번호
                                <span class="required-asterisk">*</span>
                              </label>
                              <form:input type="text" class="form-control" id="signupZipCodeInput" path="memberVO.memZip" required="true" autocomplete="off" />
                              <form:errors path="memberVO.memZip" cssClass="text-danger"/>
                              <div class="invalid-feedback">우편번호를 입력해주세요</div>
						   </div>
                           <div class="col-xl-4 mt-1comma9">
                           	   <input type="button" class="btn btn-primary m-19" onclick="kakaoAddress();" value="우편번호 찾기" />
                           </div>
                         </div>
                         <div class="mb-3">
                             <label for="signupAddr1Input" class="form-label">
                               기본주소
                               <span class="required-asterisk">*</span>
                             </label>
                             <form:input type="text" class="form-control" id="signupAddr1Input" path="memberVO.memAddr1" required="true" autocomplete="off" />
                             <form:errors path="memberVO.memAddr1" cssClass="text-danger"/>
                             <div class="invalid-feedback">주소를 입력해주세요</div>
                         </div>
                         <div class="mb-3">
                            <label for="signupAddr2Input" class="form-label">
                              상세주소
                              <span class="required-asterisk">*</span>
                            </label>
                            <form:input type="text" class="form-control" id="signupAddr2Input" path="memberVO.memAddr2" autocomplete="off" />
                            <form:errors path="memberVO.memAddr1" cssClass="text-danger" />
                            <div class="invalid-feedback">상세주소를 입력해주세요</div>
                         </div>
                         <div class="mb-3">
                           <label for="signupEmailInput" class="form-label">
                             이메일
                             <span class="required-asterisk">*</span>
                           </label>
                           <form:input type="email" class="form-control" id="signupEmailInput" path="memberVO.memEmail"  placeholder="이메일" required="true" autocomplete="off" />
                           <div class="invalid-feedback">이메일을 입력해주세요</div>
                         </div>
                         <div class="mb-3 row">
                           <div class="col-xl-8">
                             <label for="signupPhoneInput" class="form-label">
                               휴대폰번호
                               <span class="required-asterisk">*</span>
                             </label>
                             <form:input type="text" class="form-control" id="signupPhoneInput" path="memberVO.memHp"  placeholder="'-' 없이 숫자만 입력" required="true" autocomplete="off" />
                             <div class="invalid-feedback">휴대폰번호를 입력해주세요</div>                           
                           </div>
                           <div class="col-xl-4 mt-1comma9">
                           	 <input type="button" class="btn btn-primary m-19" id="certification" data-user="member" value="인증번호 받기" />
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
                            <div class="col-xl-4 mt-1comma9">
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
<script src="${pageContext.request.contextPath }/resources/js/users/Account.js"></script>