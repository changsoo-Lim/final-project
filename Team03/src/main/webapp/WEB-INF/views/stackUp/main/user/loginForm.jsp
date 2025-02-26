<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<!-- 로그인 페이지 -->
<!-- loginForm CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/loginForm/loginForm.css">
   <main>
      <!-- 로그인 헤더-->
      <div class="pattern-square"></div>
      <section class="py-5 py-lg-8">
         <div class="container">
            <div class="row">
               <div class="col-xl-4 offset-xl-4 col-md-12 col-12">
                  <div class="text-center">
                     <a href='<c:url value="/index.do" />'><img src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/logo/StackUpLogo.png" alt="brand" class="mb-3" id="logo" /></a>
                     <h1 class="mb-1">Welcome StackUp</h1>
                  </div>
               </div>
            </div>
         </div>
      </section>
      <!-- 로그인 헤더 끝-->
      <!-- signup -->
      <section>
         <div class="container">
            <div class="row justify-content-center">
               <div class="col-xl-5 col-lg-6 col-md-8 col-12">
                  <div class="card shadow-sm mb-6">
                     <div class="card-body">
                        <form class="needs-validation mb-6" novalidate action='<c:url value="/performLogin" />' method="post">
                           <div class="mb-3">
                           	 
                              <label for="signinEmailInput" class="form-label">
                                 ID
                                 <span class="text-danger">*</span>
                              </label>
                              <input type="text" class="form-control" name="userId" id="signinIdInput" value="${cookie.userId.value != null ? cookie.userId.value : ''}" required />
                           </div>
                           <div class="mb-3">
                              <label for="formSignUpPassword" class="form-label">Password</label>
                              <div class="password-field position-relative">
                                 <input type="password" class="form-control fakePassword" name="userPass" id="formSignUpPassword"
                                    required />
                                 <span><i class="bi bi-eye-slash passwordToggler"></i></span>
                                 <div class="invalid-feedback">Please enter password.</div>
                              </div>
                           </div>

                           <div class="mb-4 d-flex align-items-center justify-content-between">
                              <div class="form-check">
                                 <input class="form-check-input" type="checkbox" id="rememberMeCheckbox" name="rememberMeCheckbox" ${cookie.userId.value != null ? 'checked' : ''} />
                                 <label class="form-check-label" for="rememberMeCheckbox">아이디 저장</label>
                              </div>

                              <div>
                                 <a href="#" class="text-primary" data-bs-toggle="modal" data-bs-target="#exampleModalCenteredScrollable">아이디 찾기</a>
                                  <span class="text-primary"> | </span>
                                 <a href="#"  class="text-primary" data-bs-toggle="modal" data-bs-target="#exampleModalCenteredScrollable2">비밀번호 재설정</a>
                              </div>
                           </div>

                           <div class="d-grid">
                              <button class="btn btn-primary" type="submit">
                                 <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgba(255, 255, 255, 1);transform: ;msFilter:;">
                                    <path d="m10.998 16 5-4-5-4v3h-9v2h9z"></path><path d="M12.999 2.999a8.938 8.938 0 0 0-6.364 2.637L8.049 7.05c1.322-1.322 3.08-2.051 4.95-2.051s3.628.729 4.95 2.051S20 10.13 20 12s-.729 3.628-2.051 4.95-3.08 2.051-4.95 2.051-3.628-.729-4.95-2.051l-1.414 1.414c1.699 1.7 3.959 2.637 6.364 2.637s4.665-.937 6.364-2.637C21.063 16.665 22 14.405 22 12s-.937-4.665-2.637-6.364a8.938 8.938 0 0 0-6.364-2.637z"></path>
                                 </svg>
                                 로그인
                              </button>
                           </div>
                           
                        </form>
						
                        <div class="row">
                        	<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6">
	                       		<a href="<c:url value="/memberForm" />" class="btn btn-google col-xl-12">
	                               개인 회원가입
	                            </a>                        	
                        	</div>
                        	<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6">
	                            <a href="<c:url value="/companyForm" />" class="btn btn-google col-xl-12">
	                               기업 회원가입
	                            </a>	                        	
                        	</div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </section>
      <!--Sign up end-->
      <!-- thema  -->
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
      <!-- thema 끝 -->
   </main>
   <!-- 로그인 페이지 끝 -->
   
<!--아이디 찾기 Modal-->
<div class="modal fade" id="exampleModalCenteredScrollable" tabindex="-1"
  aria-labelledby="exampleModalCenteredScrollable" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalCenteredScrollableTitle">아이디 찾기</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body mb-10">
      	<!--tabs-->
		<ul class="nav nav-tabs navforget" id="myTab" role="tablist">
		  <li class="nav-item" role="presentation">
		    <button class="nav-link active idPassNav" id="home-tab" data-bs-toggle="tab"
		      data-bs-target="#home-tab-pane" data-user="member" type="button" role="tab" aria-controls="home-tab-pane"
		      aria-selected="true">개인 회원</button>
		  </li>
		  <li class="nav-item" role="presentation">
		    <button class="nav-link idPassNav" id="profile-tab" data-bs-toggle="tab"
		      data-bs-target="#profile-tab-pane" data-user="company" type="button" role="tab" aria-controls="profile-tab-pane"
		      aria-selected="false">기업 회원</button>
		  </li>
		</ul>
		<!--tabs 긑-->
		<div class="tab-content" id="myTabContent">
		<!-- 개인회원 아이디 찾기 tab -->
		  <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0">
			 <section>
			    <div class="container">
			       <h3 class="mb-3 ms-8 mt-7">개인 아이디 찾기</h3>
			       <div class="row justify-content-center rowContent memberDiv">
			          <div class="col-lg-10 col-md-8 col-12">
			             <div class="card shadow-sm">
			                <div class="card-body">
			                   <form class="needs-validation mb-5 idForm" novalidate>
			                   	<div class="row">
			                      <div class="col-xl-8 mb-3">
			                         <input type="text" class="form-control forgetPhoneInput" placeholder="'-'을 제외하고 휴대폰 번호를 입력해주세요" required autocomplete="off" />
			                      </div>
			                      <div class="col-xl-4">
			                         <button class="btn btn-primary col-12 getforgetCertification" id="getMemId" type="button" id="">인증번호 받기</button>
			                      </div>
			                     </div>
			                     <div class="row mb-3 certDiv" style="display:none;">
			                      <div class="col-xl-8 mb-3">
			                         <input type="text" class="form-control forgetCertInput" placeholder="인증번호" required autocomplete="off" />
			                      </div>
			                      <div class="col-xl-4">
			                         <button class="btn btn-primary col-12 forgetCertificationBtn" type="button" >인증</button>
			                      </div>
			                     </div>
			                   </form>
			                   <div class="text-center">
			                      <a href="#" class="icon-link icon-link-hover" data-bs-dismiss="modal" >
			                         <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
			                            class="bi bi-arrow-left" viewBox="0 0 16 16">
			                            <path fill-rule="evenodd"
			                               d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
			                         </svg>
			                         <span>로그인 이동</span>
			                      </a>
			                   </div>
			                </div>
			             </div>
			          </div>
			       </div>
			       <!-- 개인 회원아이디 출력 -->
			       <div class="row justify-content-center successCertDiv" style="display:none;">
					 <div class="col-lg-10 col-md-8 col-12">
			             <div class="card shadow-sm">
			                <div class="card-body">
			                   <h5>입력하신 정보와 일치하는 아이디를 찾았습니다.</h5>
			                   <div class="row p-2 mb-3">
			                     <div class="col-12">
			                     	<div class="card">
				                     	<div class="card-body">
			                     			<div class="mb-3">
						                         <label for="searchId" class="form-label">
						                            아이디
						                         </label>
						                         <input type="text" class="form-control searchId" value="123445" readonly  />
						                      </div>
				                     	</div>
			                     	</div>
			                     </div>
			                   	
			                   </div>
			                   <div class="text-center">
			                      <a href="#" class="icon-link icon-link-hover" data-bs-dismiss="modal" >
			                         <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
			                            class="bi bi-arrow-left" viewBox="0 0 16 16">
			                            <path fill-rule="evenodd"
			                               d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
			                         </svg>
			                         <span>로그인 이동</span>
			                      </a>
			                   </div>
			                </div>
			             </div>
			          </div>
			       </div>
			       <!-- 개인회원 아이디 출력 끝 -->
			    </div>
			 </section>
		  </div>
		  <!-- 개인회원 아이디 찾기 tab 끝 -->
		  
		  <!-- 기업회원 아이디 찾기 tab -->
		  <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab" tabindex="0">
			 <section>
			    <div class="container">
			       <h3 class="mb-3 ms-8 mt-7">기업 아이디 찾기</h3>
			       <div class="row justify-content-center rowContent CompanyDiv">
			          <div class="col-lg-10 col-md-8 col-12">
			             <div class="card shadow-sm">
			                <div class="card-body">
			                   <form class="needs-validation mb-5 idForm" novalidate>
			                   	<div class="row">
			                      <div class="col-xl-8 mb-3">
			                         <input type="text" class="form-control forgetPhoneInput" placeholder="'-'을 제외하고 휴대폰 번호를 입력해주세요" required autocomplete="off" />
			                      </div>
			                      <div class="col-xl-4">
			                         <button class="btn btn-primary col-12 getforgetCertification" id="getComId" type="button">인증번호 받기</button>
			                      </div>
			                     </div>  
			                     <div class="row mb-3 certDiv" style="display:none;">
			                      <div class="col-xl-8 mb-3">
			                         <input type="text" class="form-control forgetCertInput" placeholder="인증번호" required autocomplete="off" />
			                      </div>
			                      <div class="col-xl-4">
			                         <button class="btn btn-primary col-12 forgetCertificationBtn" type="button">인증</button>
			                      </div>
			                     </div>
			                   </form>
			                   <div class="text-center">
			                      <a href="#" class="icon-link icon-link-hover" data-bs-dismiss="modal" >
			                         <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
			                            class="bi bi-arrow-left" viewBox="0 0 16 16">
			                            <path fill-rule="evenodd"
			                               d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
			                         </svg>
			                         <span>로그인 이동</span>
			                      </a>
			                   </div>
			                </div>
			             </div>
			          </div>
			       </div>
			       <!-- 기업회원 아이디 출력 -->
			       <div class="row justify-content-center successCertDiv" style="display:none;">
					 <div class="col-lg-10 col-md-8 col-12">
			             <div class="card shadow-sm">
			                <div class="card-body">
			                   <h5>입력하신 정보와 일치하는 아이디를 찾았습니다.</h5>
			                   <div class="row p-2 mb-3">
			                     <div class="col-12">
			                     	<div class="card">
				                     	<div class="card-body">
			                     			<div class="mb-3">
						                         <label for="searchId" class="form-label">
						                            아이디
						                         </label>
						                         <input type="text" class="form-control searchId" value="123445" readonly  />
						                      </div>
				                     	</div>
			                     	</div>
			                     </div>
			                   	
			                   </div>
			                   <div class="text-center">
			                      <a href="#" class="icon-link icon-link-hover" data-bs-dismiss="modal" >
			                         <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
			                            class="bi bi-arrow-left" viewBox="0 0 16 16">
			                            <path fill-rule="evenodd"
			                               d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
			                         </svg>
			                         <span>로그인 이동</span>
			                      </a>
			                   </div>
			                </div>
			             </div>
			          </div>
			       </div>
			       <!-- 기업회원 아이디 출력 -->
			    </div>
			 </section>
		  </div>
		  <!-- 기업회원 아이디 찾기 tab -->
		</div>
      </div>
    </div>
  </div>
</div>



<!--비밀번호 재설정 Modal-->
<div class="modal fade" id="exampleModalCenteredScrollable2" tabindex="-1"
  aria-labelledby="exampleModalCenteredScrollable2" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalCenteredScrollableTitle2">비밀번호 재설정</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body mb-10">
      	<!--tabs-->
		<ul class="nav nav-tabs navforget" id="myTab" role="tablist">
		  <li class="nav-item" role="presentation">
		    <button class="nav-link active idPassNav" id="home-tab1" data-bs-toggle="tab"
		      data-bs-target="#home-tab-pane1" data-user="member" type="button" role="tab" aria-controls="home-tab-pane1"
		      aria-selected="true">개인 회원</button>
		  </li>
		  <li class="nav-item" role="presentation">
		    <button class="nav-link idPassNav" id="profile-tab1" data-bs-toggle="tab"
		      data-bs-target="#profile-tab-pane1" data-user="company" type="button" role="tab" aria-controls="profile-tab-pane1"
		      aria-selected="false">기업 회원</button>
		  </li>
		</ul>
		<!-- tabs 끝 -->
		<div class="tab-content" id="myTabContent">
		<!-- 개인회원 비밀번호 재설정 tab -->
		  <div class="tab-pane fade show active" id="home-tab-pane1" role="tabpanel" aria-labelledby="home-tab1" tabindex="0">
			 <section>
			    <div class="container">
			       <h3 class="mb-3 ms-8 mt-7">개인 비밀번호 재설정</h3>
			       <div class="row justify-content-center rowContent memberDiv">
			          <div class="col-lg-10 col-md-8 col-12">
			             <div class="card shadow-sm">
			                <div class="card-body">
			                   <form class="needs-validation mb-5 idForm" novalidate>
			                   	<div class="row">
			                      <div class="col-xl-8 mb-3">
			                         <input type="text" class="form-control forgetPhoneInput" placeholder="'-'을 제외하고 휴대폰 번호를 입력해주세요" required autocomplete="off" />
			                      </div>
			                      <div class="col-xl-4">
			                         <button class="btn btn-primary col-12 getforgetCertification" id="resetMemPass" type="button">인증번호 받기</button>
			                      </div>
			                     </div>
			                     <div class="row mb-3 certDiv" style="display:none;">
			                      <div class="col-xl-8 mb-3">
			                         <input type="text" class="form-control forgetCertInput" placeholder="인증번호" required autocomplete="off" />
			                      </div>
			                      <div class="col-xl-4">
			                         <button class="btn btn-primary col-12 forgetCertificationBtn" type="button" data-pass="userPass" >인증</button>
			                      </div>
			                     </div>
			                   </form>
			                   <div class="text-center">
			                      <a href="#" class="icon-link icon-link-hover" data-bs-dismiss="modal" >
			                         <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
			                            class="bi bi-arrow-left" viewBox="0 0 16 16">
			                            <path fill-rule="evenodd"
			                               d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
			                         </svg>
			                         <span>로그인 이동</span>
			                      </a>
			                   </div>
			                </div>
			             </div>
			          </div>
			       </div>
			       <!-- 개인회원 비밀번호 재설정 -->
			       <div class="row justify-content-center successCertDiv" style="display:none;">
					 <div class="col-lg-10 col-md-8 col-12">
			             <div class="card shadow-sm">
			                <div class="card-body">
			                   <h5>개인 비밀번호 재설정</h5>
			                   <div class="row p-2 mb-3">
			                     <div class="col-12">
			                     	<form:form class="changePassForm" id="changePassFormMem" modelAttribute="user" >
		                     		  <div class="mb-3">
				                        <label for="userPass" class="form-label">
				                           비밀번호
				                        </label>
				                        <div class="password-field position-relative">
					                        <form:password class="form-control searchId fakePassword" path="userPass" id="userPassMem"  placeholder="8~20자의 영문, 소문자, 특수문자 조합" required="true" />
	                             			<form:errors path="userPass" cssClass="text-danger"/>
	                             			<span><i class="bi bi-eye-slash passwordToggler"></i></span>
				                        </div>
					                  </div>
					                  <div class="password-field mb-3">
				                        <label for="userPassCheck" class="form-label">
				                           비밀번호 확인
				                        </label>
				                        <div class="password-field position-relative">
					                        <input type="password" class="form-control passwordCheck fakePassword" id="userPassCheckMem" placeholder="비밀번호 확인" required />
					                        <span><i class="bi bi-eye-slash passwordToggler"></i></span>
				                        </div>
					                  </div>
					                  <div class="mt-5 d-grid">
			                            <button class="btn btn-primary" id="changeMemPass" type="button" >비밀번호 재설정</button>
			                         </div>
			                     	</form:form>
			                     </div>
			                   </div>
			                   <div class="text-center">
			                      <a href="#" class="icon-link icon-link-hover" data-bs-dismiss="modal" >
			                         <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
			                            class="bi bi-arrow-left" viewBox="0 0 16 16">
			                            <path fill-rule="evenodd"
			                               d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
			                         </svg>
			                         <span>로그인 이동</span>
			                      </a>
			                   </div>
			                </div>
			             </div>
			          </div>
			       </div>
			       <!-- 개인회원 비밀번호 재설정 -->
			    </div>
			 </section>
		  </div>
		  <!-- 개인회원 비밀번호 재설정 tab 끝 -->
		  <!-- 기업회원 비밀번호 재설정 tab  -->
		  <div class="tab-pane fade" id="profile-tab-pane1" role="tabpanel" aria-labelledby="profile-tab1" tabindex="0">
			 <section>
			    <div class="container">
			       <h3 class="mb-3 ms-8 mt-7">기업 비밀번호 재설정</h3>
			       <div class="row justify-content-center rowContent CompanyDiv">
			          <div class="col-lg-10 col-md-8 col-12">
			             <div class="card shadow-sm">
			                <div class="card-body">
			                   <form class="needs-validation mb-5 idForm" novalidate>
			                   	<div class="row">
			                      <div class="col-xl-8 mb-3">
			                         <input type="text" class="form-control forgetPhoneInput" placeholder="'-'을 제외하고 휴대폰 번호를 입력해주세요" required autocomplete="off" />
			                      </div>
			                      <div class="col-xl-4">
			                         <button class="btn btn-primary col-12 getforgetCertification" id="resetComPass" type="button">인증번호 받기</button>
			                      </div>
			                     </div>  
			                     <div class="row mb-3 certDiv" style="display:none;">
			                      <div class="col-xl-8 mb-3">
			                         <input type="text" class="form-control forgetCertInput" placeholder="인증번호" required autocomplete="off" />
			                      </div>
			                      <div class="col-xl-4">
			                         <button class="btn btn-primary col-12 forgetCertificationBtn" type="button" data-pass="userPass">인증</button>
			                      </div>
			                     </div>
			                   </form>
			                   <div class="text-center">
			                      <a href="#" class="icon-link icon-link-hover" data-bs-dismiss="modal" >
			                         <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
			                            class="bi bi-arrow-left" viewBox="0 0 16 16">
			                            <path fill-rule="evenodd"
			                               d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
			                         </svg>
			                         <span>로그인 이동</span>
			                      </a>
			                   </div>
			                </div>
			             </div>
			          </div>
			       </div>
			       <!-- 기업회원 비밀번호 재설정 -->
			       <div class="row justify-content-center successCertDiv" style="display:none;">
					 <div class="col-lg-10 col-md-8 col-12">
			             <div class="card shadow-sm">
			                <div class="card-body">
			                   <h5>개인 비밀번호 재설정</h5>
			                   <div class="row p-2 mb-3">
			                     <div class="col-12">
			                     	<form:form class="changePassForm" id="changePassComForm" modelAttribute="user" >
		                     		  <div class="mb-3">
				                        <label for="userPass" class="form-label">
				                           비밀번호
				                        </label>
				                        <div class="password-field position-relative">
					                        <form:password class="form-control searchId fakePassword" path="userPass" id="userPassCom"  placeholder="8~20자의 영문, 소문자, 특수문자 조합" required="true" />
	                             			<form:errors path="userPass" cssClass="text-danger"/>
	                             			<span><i class="bi bi-eye-slash passwordToggler"></i></span>
				                        </div>
					                  </div>
					                  <div class="password-field mb-3">
				                        <label for="userPassCheck" class="form-label">
				                           비밀번호 확인
				                        </label>
				                        <div class="password-field position-relative">
					                        <input type="password" class="form-control passwordCheck fakePassword" id="userPassCheckCom" placeholder="비밀번호 확인" required  />
					                        <span><i class="bi bi-eye-slash passwordToggler"></i></span>
				                        </div>
					                  </div>
					                  <div class="mt-5 d-grid">
			                            <button class="btn btn-primary" id="changeComPass" type="button" >비밀번호 재설정</button>
			                         </div>
			                     	</form:form>
			                     </div>
			                   </div>
			                   <div class="text-center">
			                      <a href="#" class="icon-link icon-link-hover" data-bs-dismiss="modal" >
			                         <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
			                            class="bi bi-arrow-left" viewBox="0 0 16 16">
			                            <path fill-rule="evenodd"
			                               d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
			                         </svg>
			                         <span>로그인 이동</span>
			                      </a>
			                   </div>
			                </div>
			             </div>
			          </div>
			       </div>
			       <!-- 기업회원 비밀번호 재설정 -->
			    </div>
			 </section>
		  </div>
		  <!-- 기업회원 비밀번호 재설정 tab  -->
		</div>
      </div>
    </div>
  </div>
</div>

<!--  login JS -->
<script src="${pageContext.request.contextPath }/resources/js/users/login.js"></script>
