<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="container d-flex flex-column overflow-hidden">
 <div class="row align-items-center justify-content-center min-vh-100 text-center">
    <div class="col-lg-6 col-12">
       <div class="position-relative mb-7">
          <div class="scene d-none d-lg-block" data-relative-input="true">
             <div class="position-absolute top-0" data-depth="0.5">
                <img src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/error/stars.svg" alt />
             </div>
          </div>
          <div class="scene d-none d-lg-block" data-relative-input="true">
             <div class="position-absolute" data-depth="0.1">
                <img src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/error/rocket.svg" alt />
             </div>
          </div>
          <div class="scene d-none d-lg-block" data-relative-input="true">
             <div class="position-absolute top-0 start-50 translate-middle"
                style="margin-top: -80px; margin-left: -80px" data-depth="0.1">
                <img src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/error/globe.svg" alt />
             </div>
          </div>
          <div class="scene d-none d-lg-block" data-relative-input="true">
             <div class="position-absolute start-50" data-depth="0.1">
                <img src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/error/astronut.svg" alt
                   style="top: -110px; position: absolute; bottom: 0" />
               </div>
            </div>
            <div class="position-relative z-n1">
               <img src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/logo/StackUpLogo.png" alt class="img-fluid" />
            </div>
            <div class="scene d-none d-lg-block" data-relative-input="true">
               <div class="position-absolute start-100 bottom-0" style data-depth="0.1">
                  <img src="${pageContext.request.contextPath}/resources/block-theme-1-2-2/dist/assets/images/error/planet.svg" alt />
               </div>
            </div>
         </div>

         <h2 class="mb-5">StackUp의 회원이 되신걸 환영합니다.</h2>
         <a href="<c:url value="login" />" class="btn btn-primary me-5">로그인</a>
         <a href="<c:url value="/index.do" />" class="btn btn-primary">메인화면</a>
      </div>
   </div>
</div>
<div class="position-absolute end-0 bottom-0 m-4 d-none">
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