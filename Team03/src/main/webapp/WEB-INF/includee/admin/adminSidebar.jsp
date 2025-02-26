<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Sidebar scroll-->
<div>
	<div
		class="brand-logo d-flex align-items-center justify-content-between">
		<a href="<c:url value="/admin" />" class="text-nowrap logo-img"> <img
			src="${pageContext.request.contextPath}/resources/Modernize-bootstrap-free-main/assets/images/logos/StackUpLogo.png"
			width="180" alt="" />
		</a>
		<div class="close-btn d-xl-none d-block sidebartoggler cursor-pointer"
			id="sidebarCollapse">
			<i class="ti ti-x fs-8"></i>
		</div>
	</div>
	<!-- Sidebar navigation-->
	<nav class="sidebar-nav scroll-sidebar" data-simplebar="">
		<ul id="sidebarnav">
			<li class="nav-small-cap"><i
				class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
				class="hide-menu">홈</span></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value="/index.do" />" aria-expanded="false"> <span>
						<i class="ti ti-layout-dashboard"></i>
				</span> <span class="hide-menu">회원 메인페이지</span>
			</a></li>
			<!--       <li class="sidebar-item"> -->
			<%--         <a class="sidebar-link" href="<c:url value="/admin" />" aria-expanded="false"> --%>
			<!--           <span> -->
			<!--             <i class="ti ti-layout-dashboard"></i> -->
			<!--           </span> -->
			<!--           <span class="hide-menu">관리자 페이지</span> -->
			<!--         </a> -->
			<!--       </li> -->
			<li class="nav-small-cap"><i
				class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
				class="hide-menu">회원</span></li>
<!-- 			<li class="sidebar-item"><a class="sidebar-link" -->
<%-- 				href="<c:url value='/adminmember/list' />" aria-expanded="false"> <span> <i --%>
<!-- 						class="ti ti-article"></i> -->
<!-- 				</span> <span class="hide-menu">회원 관리</span> -->
<!-- 			</a></li> -->
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value='/admin/report' />" aria-expanded="false"> <span>
						<i class="ti ti-file-description"></i>
				</span> <span class="hide-menu">신고 관리</span>
			</a></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value='/admin/blacklist' />" aria-expanded="false">
					<span> <i class="ti ti-file-description"></i>
				</span> <span class="hide-menu">블랙리스트 관리</span>
			</a></li>
			<li class="nav-small-cap"><i
				class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
				class="hide-menu">기업</span></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value='/admincompany/list' />" aria-expanded="false">
					<span> <i class="ti ti-article"></i>
				</span> <span class="hide-menu">기업 관리</span>
			</a></li>
			<!--       <li class="sidebar-item"> -->
			<%--         <a class="sidebar-link" href="<c:url value='/admincompany/list' />" aria-expanded="false"> --%>
			<!--           <span> -->
			<!--             <i class="ti ti-file-description"></i> -->
			<!--           </span> -->
			<!--           <span class="hide-menu">채용공고 등록</span> -->
			<!--         </a> -->
			<!--       </li> -->

			<li class="nav-small-cap"><i
				class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
				class="hide-menu">검색필터</span></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value='/admin/filter' />" aria-expanded="false"> <span>
						<i class="ti ti-file-description"></i>
				</span> <span class="hide-menu">검색필터 관리</span>
			</a></li>


			<li class="nav-small-cap"><i
				class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
				class="hide-menu">상품 결제</span></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value="/admin/products/ad" />" aria-expanded="false">
					<span> <i class="ti ti-article"></i>
				</span> <span class="hide-menu">결제 옵션 관리</span>
			</a></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value="/ad/buy" />" aria-expanded="false"> <span>
						<i class="ti ti-file-description"></i>
				</span> <span class="hide-menu">결제 내역 관리</span>
			</a></li>

			<li class="nav-small-cap"><i
				class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
				class="hide-menu">검사 및 테스트</span></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value="/admin/test/list" />" aria-expanded="false">
					<span> <i class="ti ti-article"></i>
				</span> <span class="hide-menu">검사 및 테스트 관리</span>
			</a></li>

			<!-- <li class="nav-small-cap">
    <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
    <span class="hide-menu">입사테스트</span>
  </li>
  <li class="sidebar-item">
    <a class="sidebar-link" href="./ui-buttons.html" aria-expanded="false">
      <span>
        <i class="ti ti-article"></i>
      </span>
      <span class="hide-menu">입사테스트 관리</span>
    </a>
  </li> -->
			<!-- <li class="sidebar-item">
    <a class="sidebar-link" href="./ui-forms.html" aria-expanded="false">
      <span>
        <i class="ti ti-file-description"></i>
      </span>
      <span class="hide-menu">지원자 기록 조회 및 평가 </span>
    </a>
  </li> -->

<!-- 			<li class="nav-small-cap"><i -->
<!-- 				class="ti ti-dots nav-small-cap-icon fs-4"></i> <span -->
<!-- 				class="hide-menu">프리랜서(수정중)</span></li> -->
<!-- 			<li class="sidebar-item"><a class="sidebar-link" -->
<!-- 				href="./ui-buttons.html" aria-expanded="false"> <span> <i -->
<!-- 						class="ti ti-article"></i> -->
<!-- 				</span> <span class="hide-menu">프리랜서 관리</span> -->
<!-- 			</a></li> -->
<!-- 			<li class="sidebar-item"><a class="sidebar-link" -->
<!-- 				href="./ui-forms.html" aria-expanded="false"> <span> <i -->
<!-- 						class="ti ti-file-description"></i> -->
<!-- 				</span> <span class="hide-menu">프로젝트 관리 </span> -->
<!-- 			</a></li> -->
			<li class="nav-small-cap"><i
				class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
				class="hide-menu">커뮤니티</span></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value='/admin/board' />" aria-expanded="false"> <span>
						<i class="ti ti-file-description"></i>
				</span> <span class="hide-menu">커뮤니티 관리</span>
			</a></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value='/admin/resource' />" aria-expanded="false">
					<span> <i class="ti ti-file-description"></i>
				</span> <span class="hide-menu">자료실 관리</span>
			</a></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value='/admin/review' />" aria-expanded="false"> <span>
						<i class="ti ti-file-description"></i>
				</span> <span class="hide-menu">면접후기 관리</span>
			</a></li>
			<li class="nav-small-cap"><i
				class="ti ti-dots nav-small-cap-icon fs-4"></i> <span
				class="hide-menu">고객센터</span></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value='/admin/notice' />" aria-expanded="false"> <span>
						<i class="ti ti-article"></i>
				</span> <span class="hide-menu">공지사항 관리</span>
			</a></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value='/admin/faq' />" aria-expanded="false"> <span>
						<i class="ti ti-file-description"></i>
				</span> <span class="hide-menu">FAQ 관리</span>
			</a></li>
			<li class="sidebar-item"><a class="sidebar-link"
				href="<c:url value='/admin/inquiry' />" aria-expanded="false"> <span>
						<i class="ti ti-file-description"></i>
				</span> <span class="hide-menu">문의하기 관리</span>
			</a></li>
		</ul>
	</nav>
	<!-- End Sidebar navigation -->
</div>
<!-- End Sidebar scroll-->
<script>
	// 사이드바의 링크와 현재 페이지의 경로 부분을 비교하여 sidebar-link a 태그에  active 클래스 부여 
  document.addEventListener('DOMContentLoaded', function() {
    // 현재 URL의 경로 가져오기 (쿼리스트링 제거)
    const currentPath = window.location.pathname;

    // 모든 sidebar-link 찾기
    const sidebarLinks = document.querySelectorAll('.sidebar-link');

    sidebarLinks.forEach(link => {
        const linkPath = new URL(link.href).pathname; // 링크의 경로만 추출
        if (linkPath === currentPath) {
            link.classList.add('active');
        }
    });
});
</script>