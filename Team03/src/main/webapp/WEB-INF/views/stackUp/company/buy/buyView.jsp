<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/buy/buyView.css" />
<div class="container mt-5">
	<h2 class="mb-4 text-center">메인 광고 상품구매</h2>
	<div class="row">
		<div class="mb-3">
		    <label for="prodNm" class="col-form-label">적용시킬 공고:</label>
		    <select id="employNo" name="employNo" class="form-select">
		        <option value="">공고선택</option>
		        <c:forEach items="${empList}" var="emp">
		            <option value="${emp.employNo}">${emp.employTitle} [${emp.employSd.substring(0,4)}-${emp.employSd.substring(4,6)}-${emp.employSd.substring(6,8)}] ~ [${emp.employEd.substring(0,4)}-${emp.employEd.substring(4,6)}-${emp.employEd.substring(6,8)}]</option>
		        </c:forEach>
		    </select>
		    <!-- 빨간 경고문 추가 -->
		    <small class="text-danger">* 배너 광고일 경우 선택하지 않아도 됩니다.</small>
		</div>
		<div class="col-md-7 d-flex flex-column gap-3">
			<c:forEach items="${prodList}" var="prod" varStatus="ad">
			    <div class="border rounded p-3 shadow-sm d-flex align-items-center justify-content-between clickable"
			    onclick="showImage(this, '/images/ProdImage/${prod.file.fileDetails[0].streFileNm}', '${prod.productNm }')">
			        <div>
			            <input type="hidden" name="productCd" id="productCd_${ad.index}" value="${prod.productCd}">
			            <input type="hidden" id="price_${ad.index}" value="${prod.productPrice}">
			            <h5 id="productNm" class="fw-bold text-primary mb-2">${prod.productNm}</h5>
			            <div class="d-flex gap-2 mb-2" id="spanDiv_${ad.index}">
			            </div>
			            <div class="d-flex align-items-center gap-2">
			                <span>상품 시작 기간:</span>
			                <input type="date" id="dateInput_${ad.index}" class="form-control form-control-sm" style="width: auto;">
			                <span>일수:</span>
			                <input type="number" id="daysInput_${ad.index}" class="form-control form-control-sm days-input" min="1" max="99" value="1" style="width: auto;" data-index="${ad.index}">
			            </div>
			        </div>
			        <div class="text-end">
			            <div class="fw-bold text-dark mb-2" id="productPrice_${ad.index}">
			                <fmt:formatNumber value="${prod.productPrice}" type="number" groupingUsed="true" /> ₩
			            </div>
			            <button type="button" id="" class="btn btn-outline-primary modalBtn" data-bs-toggle="modal" data-bs-target="#exampleModal1">상품 구매</button>
			        </div>
			    </div>
			</c:forEach>


			<!-- 혜택 카드 -->
			<div class="row row-cols-1 row-cols-md-2 g-4">
				<div class="col">
					<div class="card border-primary shadow-sm h-55">
						<div class="card-body text-center">
							<h6 class="fw-bold text-dark">첫 구매 페이백</h6>
							<p class="text-muted small">채용광고 첫 구매 시 결제금액 100% S-Point로 적립</p>
						</div>
					</div>
				</div>
				<div class="col">
					<div class="card border-primary shadow-sm h-55">
						<div class="card-body text-center">
							<h6 class="fw-bold text-dark">S-Point 5% 적립</h6>
							<p class="text-muted small">10만원 이상 결제 시 결제금액의 5% 포인트 적립</p>
						</div>
					</div>
				</div>
				<div class="col">
					<div class="card border-primary shadow-sm h-55">
						<div class="card-body text-center">
							<h6 class="fw-bold text-dark">광고 구매 당일 무료</h6>
							<p class="text-muted small">채용광고를 구매한 당일은 무료로 광고가 적용됩니다.</p>
						</div>
					</div>
				</div>
				<div class="col">
					<div class="card border-primary shadow-sm h-55">
						<div class="card-body text-center">
							<h6 class="fw-bold text-dark">광고 주말 1일 무료</h6>
							<p class="text-muted small">채용광고를 주말 포함하여 구매할 경우 1일 추가 제공</p>
						</div>
					</div>
				</div>
			</div>

			<!-- 유의사항 -->
			<div class="border rounded p-4 shadow-sm bg-light">
				<h5 class="fw-bold text-danger mb-4">
					<i class="fas fa-exclamation-circle me-2"></i>유의사항
				</h5>
				<ul class="list-unstyled">
					<li class="mb-3"><strong>[주의]</strong> 유료 상품을 이용 중이신 경우, 광고를
						조기 마감하더라도 남은 기간에 대한 차액은 환불되지 않습니다.</li>
					<li class="mb-3">기업로고가 등록되어 있지 않을 경우 로고영역에 기본 아이콘이 적용됩니다.</li>
					<li class="mb-3"></li>
					<li class="mb-3"></li>
					<li class="mb-3"></li>
					<li></li>
				</ul>
			</div>
		</div>
		<!-- 오른쪽 이미지 박스 -->
		<div class="col-md-5 border rounded p-3 shadow-sm" style="width: 400px; height: 1000px; overflow: auto;">
			<img id="product-image" src=""	alt="" class="img-fluid rounded"  style="width: 100%; height: auto;">
		</div>
	</div>
</div>
<!-- modal -->
<div class="modal fade" id="exampleModal1" tabindex="-1" aria-labelledby="exampleModalLabel1">
	<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" style="max-width: 90%; width: 500px;">
		<div class="modal-content" style="max-height: 90vh; overflow-y: auto;">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalLabel1">선택한 상품</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<form:form method="post" modelAttribute="buy" enctype="multipart/form-data">
				<div class="modal-body">
					<form:input type="hidden" path="employNo" class="employNo" />
				    <div class="mb-3">
				        <label for="prodNm" class="col-form-label">상품명:</label>
				        <input type="hidden" name="productCd" id="productCd" value=""/>
				        <input class="form-control" id="prodNm" readonly />
				    </div>
				    <div class="mb-3">
				        <label for="buyQty" class="col-form-label">상품 구매 일수:</label>
				        <form:input path="buyQty" type="text" class="form-control" id="buyQty" readonly="true" />
				    </div>
				    <div class="mb-3">
				        <label for="buySdt" class="col-form-label">상품 시작 일자:</label>
				        <form:input path="buySdt" type="text" class="form-control" id="buySdt" readonly="true" />
				    </div>
				    <div class="mb-3">
				        <label for="buyEdt" class="col-form-label">상품 종료 일자:</label>
				        <form:input path="buyEdt" type="text" class="form-control" id="buyEdt" readonly="true" />
				    </div>
				    <div class="mb-3">
				        <label for="buyAmount" class="col-form-label">결제 금액:</label>
				        <form:input path="buyAmount" type="text" class="form-control" id="buyAmount" readonly="true" />
				    </div>
				    <div class="mb-3">
				        <label for="buyMethod" class="col-form-label">결제수단:</label>
				        <form:select path="buyMethod" class="form-select" id="buyMethod" required="required">
				            <form:option value="">선택</form:option>
				            <form:option value="CARD">카드</form:option>
				            <form:option value="BANK">계좌이체</form:option>
				        </form:select>
				    </div>
				    <div class="mb-3">
				        <label for="uploadFiles" class="col-form-label">배너 광고 이미지:</label>
				        <form:input path="uploadFiles" type="file" id="uploadFiles" class="form-control" />
				        <span class="text-danger sm">※배너 광고시 이미지파일 첨부 부탁드립니다.</span>
				    </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
					<button type="submit" class="btn btn-primary" id="buyBtn">결제</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/buy/buyView.js"></script>
