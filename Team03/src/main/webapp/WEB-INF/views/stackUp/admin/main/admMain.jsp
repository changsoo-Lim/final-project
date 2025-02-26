<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/main/adminMain.css" />

<div class="dashboard">
	<!-- Section 1: 미처리된 작업 -->
	<div class="section large-section">
      	<h2 class="mb-0 text-center">미처리 된 작업</h2>
	    <div class="col-lg-10 offset-lg-1">
	        <div class="row g-5 text-center">
	            <!-- 미처리된 신고 -->
	            <div class="col-md-3 col-6">
	                <div class="text-center">
	                    <div class="d-flex justify-content-center">
	                        <h2 class="counter mb-0" id="unprocessedReports" data-count="0">0</h2>
	                        <span class="ms-1 fs-2 text-dark">건</span>
	                    </div>
	                    <a href="${pageContext.request.contextPath}/admin/report" class="btn btn-primary c">신고 관리</a>
	                </div>
	            </div>
	
	            <!-- 미처리된 면접 후기 -->
	            <div class="col-md-3 col-6">
	                <div class="text-center">
	                    <div class="d-flex justify-content-center">
	                        <h2 class="counter mb-0" id="unprocessedReviews" data-count="0">0</h2>
	                        <span class="ms-1 fs-2 text-dark">건</span>
	                    </div>
	                    <a href="${pageContext.request.contextPath}/admin/review" class="btn btn-primary c">면접 후기 관리</a>
	                </div>
	            </div>
	
	            <!-- 미처리된 문의 -->
	            <div class="col-md-3 col-6">
	                <div class="text-center">
	                    <div class="d-flex justify-content-center">
	                        <h2 class="counter mb-0" id="unprocessedInquiries" data-count="0">0</h2>
	                        <span class="ms-1 fs-2 text-dark">건</span>
	                    </div>
	                    <a href="${pageContext.request.contextPath}/admin/inquiry" class="btn btn-primary c">문의 답변 관리</a>
	                </div>
	            </div>
	
	            <!-- 예비 공간 버튼 -->
				<div class="col-md-3 col-6">
				    <div class="text-center">
				        <a href="${pageContext.request.contextPath}/admincompany/list" class="btn btn-primary sm">채용공고 관리</a>
				        <a href="${pageContext.request.contextPath}/admin/member" class="btn btn-primary sm">회원 관리</a>
				        <a href="${pageContext.request.contextPath}/admin/notice" class="btn btn-primary sm">고객센터 관리</a>
				        <a href="${pageContext.request.contextPath}/admin/resource" class="btn btn-primary sm">자료실 관리</a>
				    </div>
				</div>
	        </div>
	    </div>
	</div>

	<!-- Section 2: 채용 공고 -->
	<div class="section">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2 class="mb-0">월 별 채용공고 현황</h2>
			<select id="employYearSelect" class="form-select w-auto"></select>
		</div>

		<div class="table">
			<table id="employTable" class="table table-bordered">
				<thead>
					<tr>
						<th>월</th>
						<th>총 공고 수</th>
						<th>진행 중 공고 수</th>
						<th>종료된 공고 수</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>

	<!-- Section 2: 회원 현황 -->
	<div class="section">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2 class="mb-0">월 별 회원 현황</h2>
			<select id="userYearSelect" class="form-select w-auto">
			</select>
		</div>
		<div class="table">
			<table id="userTable" border="1">
				<thead>
					<tr>
						<th>월</th>
						<th>신규 가입자 수</th>
						<th>일반 회원(구직자) 수</th>
						<th>기업 회원 수</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>

	<!-- Section 3: 지역별 공고 수 -->
	<div class="section">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2 class="mb-3">지역별 공고 수</h2>
			<div class="d-flex align-items-center">
				<select id="regionYearSelect" class="form-select w-auto"></select>
				<select id="regionMonthSelect" class="form-select w-auto"></select>
			</div>
		</div>
		<div class="chart">
			<canvas id="regionChart"></canvas>
		</div>
	</div>


	<!-- Section 4: 회원 가입 수 -->
	<div class="section">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2 class="mb-3">회원 가입 수</h2>
			<select id="userYearConunt" class="form-select w-auto"></select>
		</div>
		<div class="chart">
			<canvas id="usersChart"></canvas>
		</div>
	</div>

</div>

<script
	src="${pageContext.request.contextPath }/resources/js/admin/main/adminMain.js"></script>
