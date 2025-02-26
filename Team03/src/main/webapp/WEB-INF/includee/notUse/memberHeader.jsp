<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<style ref>

</style>
   <!-- Navbar -->
         <div class="container px-3">
            <a class="navbar-brand" href="./index.html"><img style="width: 150px;" src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/logo/StackUpLogo.png" alt /></a>
            <button class="navbar-toggler offcanvas-nav-btn" type="button">
               <i class="bi bi-list"></i>
            </button>
            <div class="offcanvas offcanvas-start offcanvas-nav" style="width: 20rem">
               <div class="offcanvas-header">
                  <a href="./index.html" class="text-inverse"><img src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/logo/logo.svg" alt /></a>
                  <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
               </div>
               <div class="offcanvas-body pt-0 align-items-center">
                  <ul class="navbar-nav mx-auto align-items-lg-center">
                     <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">채용정보</a>
                        <ul class="dropdown-menu">
                           <li><a class="dropdown-item" href="./index.html">Landing Overview</a></li>
                           <li><a class="dropdown-item" href="./landing-sass-v1.html">Saas v.1</a></li>
                           <li><a class="dropdown-item" href="./landing-sass-v2.html">Sass v.2</a></li>
                           <li>
                              <a class="dropdown-item" href="./landing-it-company.html">IT Company</a>
                           </li>
                           <li>
                              <a class="dropdown-item" href="./landing-seo.html">Seo Agency</a>
                           </li>
                           <li><a class="dropdown-item" href="./landing-accounting.html">Accounting</a></li>
                           <li><a class="dropdown-item" href="./landing-finance.html">Finance</a></li>
                           <li><a class="dropdown-item" href="./landing-jamstack-agancy.html">Jamstack Agency</a></li>
                           <li><a class="dropdown-item" href="./landing-conference.html">Conference</a></li>
                           <li><a class="dropdown-item" href="./landing-personal.html">Personal</a></li>
                        </ul>
                     </li>
                     <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">채용달력</a>
                        <ul class="dropdown-menu">
                           <li><a class="dropdown-item" href="./index.html">Landing Overview</a></li>
                           <li><a class="dropdown-item" href="./landing-sass-v1.html">Saas v.1</a></li>
                           <li><a class="dropdown-item" href="./landing-sass-v2.html">Sass v.2</a></li>
                           <li>
                              <a class="dropdown-item" href="./landing-it-company.html">IT Company</a>
                           </li>
                           <li>
                              <a class="dropdown-item" href="./landing-seo.html">Seo Agency</a>
                           </li>
                           <li><a class="dropdown-item" href="./landing-accounting.html">Accounting</a></li>
                           <li><a class="dropdown-item" href="./landing-finance.html">Finance</a></li>
                           <li><a class="dropdown-item" href="./landing-jamstack-agancy.html">Jamstack Agency</a></li>
                           <li><a class="dropdown-item" href="./landing-conference.html">Conference</a></li>
                           <li><a class="dropdown-item" href="./landing-personal.html">Personal</a></li>
                        </ul>
                     </li>
                     <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">프리랜서</a>
                        <ul class="dropdown-menu">
                           <li><a class="dropdown-item" href="./index.html">Landing Overview</a></li>
                           <li><a class="dropdown-item" href="./landing-sass-v1.html">Saas v.1</a></li>
                           <li><a class="dropdown-item" href="./landing-sass-v2.html">Sass v.2</a></li>
                           <li>
                              <a class="dropdown-item" href="./landing-it-company.html">IT Company</a>
                           </li>
                           <li>
                              <a class="dropdown-item" href="./landing-seo.html">Seo Agency</a>
                           </li>
                           <li><a class="dropdown-item" href="./landing-accounting.html">Accounting</a></li>
                           <li><a class="dropdown-item" href="./landing-finance.html">Finance</a></li>
                           <li><a class="dropdown-item" href="./landing-jamstack-agancy.html">Jamstack Agency</a></li>
                           <li><a class="dropdown-item" href="./landing-conference.html">Conference</a></li>
                           <li><a class="dropdown-item" href="./landing-personal.html">Personal</a></li>
                        </ul>
                     </li>
                    
                     <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">커뮤니티</a>
                        <ul class="dropdown-menu">
                           <li><a class="dropdown-item" href="./account-profile.html">Profile</a></li>
                           <li><a class="dropdown-item" href="./account-security.html">Security</a></li>
                           <li><a class="dropdown-item" href="./account-billing.html">Billing</a></li>
                           <li><a class="dropdown-item" href="./account-team.html">Team</a></li>
                           <li><a class="dropdown-item" href="./account-notification.html">Notifications</a></li>
                           <li><a class="dropdown-item" href="./account-app-integration.html">Integration</a></li>
                           <li><a class="dropdown-item" href="./account-device-session.html">Session</a></li>
                           <li><a class="dropdown-item" href="./account-social-links.html">Social</a></li>
                           <li><a class="dropdown-item" href="./account-appearance.html">Appearance</a></li>
                           <li class="dropdown-submenu dropend">
                              <a class="dropdown-item dropdown-toggle" href="#">Authentication</a>
                              <ul class="dropdown-menu">
                                 <li class="dropdown-header">Simple</li>

                                 <li>
                                    <a class="dropdown-item" href="./signin.html">Sign In</a>
                                 </li>
                                 <li>
                                    <a class="dropdown-item" href="./signup.html">Sign Up</a>
                                 </li>
                                 <li>
                                    <a class="dropdown-item" href="./forget-password.html">Forget Password</a>
                                 </li>
                                 <li>
                                    <a class="dropdown-item" href="./reset-password.html">Reset Password</a>
                                 </li>
                                 <li>
                                    <a class="dropdown-item" href="./otp-varification.html">OTP Varification</a>
                                 </li>
                                 <li>
                                    <hr class="dropdown-divider" />
                                 </li>
                                 <li class="dropdown-header">Side Cover</li>

                                 <li>
                                    <a class="dropdown-item" href="./signin-v2.html">Sign In</a>
                                 </li>

                                 <li>
                                    <a class="dropdown-item" href="./signup-v2.html">Sign Up</a>
                                 </li>

                                 <li>
                                    <a class="dropdown-item" href="./forget-password-v2.html">Forget Password</a>
                                 </li>
                                 <li>
                                    <a class="dropdown-item" href="./reset-password-v2.html">Reset Password</a>
                                 </li>
                                 <li>
                                    <a class="dropdown-item" href="./otp-varification-v2.html">OTP Varification</a>
                                 </li>
                              </ul>
                           </li>
                           <li class="dropdown-submenu dropend">
                              <a class="dropdown-item dropdown-toggle" href="#">Utility</a>
                              <ul class="dropdown-menu">
                                 <li>
                                    <a class="dropdown-item" href="./404-error.html">404 Error</a>
                                 </li>
                                 <li>
                                    <a class="dropdown-item" href="./changelog.html">Changelog</a>
                                 </li>
                              </ul>
                           </li>
                        </ul>
                     </li>
                  </ul>
                  <div class="mt-3 mt-lg-0 d-flex align-items-center">
                     <a href="<c:url value="/login" />" class="btn btn-light mx-2">로그인</a>
                     <a href="<c:url value="/join" />" class="btn btn-primary">회원가입</a>
                  </div>
               </div>
            </div>
         </div>
