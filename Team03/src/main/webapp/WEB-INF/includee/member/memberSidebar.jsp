<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<div class="col-lg-3 col-md-4">
	<div class="collapse d-md-block w-75" id="collapseAccountMenu">
	    <ul class="nav flex-column nav-account">
	        <li class="nav-item">
	            <a class="nav-link" href='<c:url value="/member/mypage" /> '>
	                <i class="align-bottom bx bx-home"></i>
	                <span class="ms-2">MY홈</span>
	            </a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="#resumeSubmenu" data-bs-toggle="collapse" aria-expanded="false">
	              <i class="align-bottom bx bx-user"></i>
	              <span class="ms-2">이력서/자소서</span>
	              <!-- 화살표 아이콘, 클릭 시 서브메뉴 열기/닫기 -->
	              <i class="bi bi-chevron-down ms-2" id="toggleResumeSubmenu"></i>
	          </a>
	          <div class="collapse" id="resumeSubmenu">
	              <ul class="nav flex-column ps-3">
	                  <li class="nav-item">
	                      <a class="nav-link" href="<c:url value='/resume/new'/>">
	                          <span class="ms-2">이력서 관리</span>
	                      </a>
	                  </li>
	                  <li class="nav-item">
	                      <a class="nav-link" href="<c:url value='/intro'/>">
	                          <span class="ms-2">자소서 관리</span>
	                      </a>
	                  </li>
	              </ul>
	          </div>
	        </li>	
	        <li class="nav-item">
	           <a class="nav-link scrap-tap" href="${pageContext.request.contextPath}/employScrap">
	               <i class="align-bottom bx bx-time"></i>
	               <span class="ms-2">스크랩/관심기업</span>
	           </a>
	        </li>
	        <li class="nav-item">
	            <a class="nav-link" href="${pageContext.request.contextPath}/member/myapply">
	                <i class="align-bottom bx bx-credit-card-front"></i>
	                <span class="ms-2">입사지원 내역</span>
	            </a>
	        </li>
			<li class="nav-item">
				<a class="nav-link" href="${pageContext.request.contextPath}/freeOffer/member/list">
					<i class="align-bottom bx bx-link"></i>
					<span class="ms-2">받은 제안</span>
				</a>
			</li>
			<%-- <li class="nav-item">
	          <a class="nav-link" href="#resumeSubmenu2" data-bs-toggle="collapse" aria-expanded="false">
	             <i class="align-bottom bx bx-link"></i>
	                <span class="ms-2">받은 제안</span>
	                <!-- 화살표 아이콘, 클릭 시 서브메뉴 열기/닫기 -->
	                <i class="bi bi-chevron-down ms-2" id="toggleResumeSubmenu"></i>
	          </a>
	          <div class="collapse" id="resumeSubmenu2">
	                <ul class="nav flex-column ps-3">
	                   <li class="nav-item">
	                      <a class="nav-link" href="./resume-create.html">
	                            <span class="ms-2">포지션 제안 현황</span>
	                      </a>
	                   </li>
	                   <li class="nav-item">
	                      <a class="nav-link" href="${pageContext.request.contextPath}/freeOffer/member/list">
	                            <span class="ms-2">프리랜서 제안 현황</span>
	                      </a>
	                   <li class="nav-item">
	                      <a class="nav-link" href="./resume-manage.html">
	                            <span class="ms-2">이력서 열람 현황</span>
	                      </a>
	                   </li> 
	                </ul>
	          </div>
	        </li> --%>
	        <li class="nav-item">
	          <a class="nav-link" href="${pageContext.request.contextPath}/freelancer/edit">
	                <i class="align-bottom bx bx-user-plus"></i>
	                <span class="ms-2">프리랜서 관리</span>
	            </a>
	        </li> 
	        <li class="nav-item">
	           <a class="nav-link" href="#resumeSubmenu3" data-bs-toggle="collapse" aria-expanded="false">
	              <i class="align-bottom bx bx-box"></i>
	                 <span class="ms-2">검사/테스트</span>
	                 <!-- 화살표 아이콘, 클릭 시 서브메뉴 열기/닫기 -->
	                 <i class="bi bi-chevron-down ms-2" id="toggleResumeSubmenu"></i>
	           </a>
	           <div class="collapse" id="resumeSubmenu3">
	                <ul class="nav flex-column ps-3">
	                   <li class="nav-item">
	                      <a class="nav-link" href="<c:url value="/member/test/list" />">
	                            <span class="ms-2">검사/테스트 현황</span>
	                      </a>
	                   </li>
	                   <li class="nav-item">
	                      <a class="nav-link" href="<c:url value="/member/test/list" />">
	                            <span class="ms-2">요청 받은 검사/테스트</span>
	                      </a>
	                   </li> 
	                </ul>
	            </div>
	        </li>
	        <li class="nav-item">
	             <a class="nav-link" href="#resumeSubmenu4" data-bs-toggle="collapse" aria-expanded="false">
	                <i class="align-bottom bx bx-palette"></i>
	                   <span class="ms-2">면접 관리</span>
	                   <!-- 화살표 아이콘, 클릭 시 서브메뉴 열기/닫기 -->
	                   <i class="bi bi-chevron-down ms-2" id="toggleResumeSubmenu"></i>
	             </a>
	             <div class="collapse" id="resumeSubmenu4">
	                   <ul class="nav flex-column ps-3">
	                      <li class="nav-item">
	                         <a class="nav-link" href="${pageContext.request.contextPath}/vchat/interview">
	                               <span class="ms-2">면접 현황</span>
	                         </a>
	                      </li>
	                      <li class="nav-item">
	                         <a class="nav-link" href="${pageContext.request.contextPath}/review/myReview">
	                               <span class="ms-2">면접 후기</span>
	                         </a>
	                      </li> 
	                   </ul>
	             </div>
	        </li>
	        <li class="nav-item">
	             <a class="nav-link" href="#resumeSubmenu5" data-bs-toggle="collapse" aria-expanded="false">
	                <i class="align-bottom bx bx-user"></i>
	                   <span class="ms-2">회원정보 관리</span>
	                   <!-- 화살표 아이콘, 클릭 시 서브메뉴 열기/닫기 -->
	                   <i class="bi bi-chevron-down ms-2" id="toggleResumeSubmenu"></i>
	             </a>
	             <div class="collapse" id="resumeSubmenu5">
	                   <ul class="nav flex-column ps-3">
	                      <li class="nav-item">
	                         <a class="nav-link" href='<c:url value="/member/info/infoForm/passCheck" />' >
	                               <span class="ms-2">회원정보 수정</span>
	                         </a>
	                      </li>
	                      <li class="nav-item">
	                         <a class="nav-link" href='<c:url value="/member/info/passForm/passCheck" />' >
	                               <span class="ms-2">비밀번호 수정</span>
	                         </a>
	                      </li> 
	                      <li class="nav-item">
	                         <a class="nav-link" href='<c:url value="/member/info/deleteForm/passCheck" />' >
	                               <span class="ms-2">회원탈퇴</span>
	                         </a>
	                      </li> 
	                   </ul>
	             </div>
	        </li>
	        <li class="nav-item">
	            <a class="nav-link" href="${pageContext.request.contextPath}/status">
	                <i class="align-bottom bx bx-bell"></i>
	                <span class="ms-2">알림설정</span>
	            </a>
	        </li>
	        <li class="nav-item">
	            <a class="nav-link" href='<c:url value="/logout" />'>
	                <i class="align-bottom bx bx-log-out"></i>
	                <span class="ms-2">로그아웃</span>
	            </a>
	        </li>
	    </ul>
	</div>
</div>
