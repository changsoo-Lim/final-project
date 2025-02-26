<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<ul class="nav nav-pills" id="pills-tab" role="tablist">
  <li class="nav-item" role="presentation">
    <button class="nav-link active" id="pills-history-tab" data-bs-toggle="pill"
      data-bs-target="#pills-history" type="button" role="tab" aria-controls="pills-history"
      aria-selected="true">결제 내역</button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link" id="pills-stats-tab" data-bs-toggle="pill"
      data-bs-target="#pills-stats" type="button" role="tab" aria-controls="pills-stats"
      aria-selected="false">결제 통계</button>
  </li>
</ul>
<br>
<div class="tab-content" id="pills-tabContent">
  <!-- 결제 내역 -->
  <div class="tab-pane fade show active" id="pills-history" role="tabpanel" aria-labelledby="pills-history-tab" tabindex="0">
    <div class="container">
      <h2>구매 내역</h2>
      <br>
      <div class="search-area mb-4 d-flex justify-content-end align-items-center">
        <form:select path="condition.searchType" class="form-select w-auto">
          <form:option value="buyMethod" label="결제방식"></form:option>
          <form:option value="compNm" label="기업명"></form:option>
          <form:option value="productType" label="상품타입"></form:option>
          <form:option value="productNm" label="상품이름"></form:option>
        </form:select>
        <form:input path="condition.searchWord" class="form-control w-auto" placeholder="검색어 입력" />
        <button type="submit" class="btn btn-primary search-btn">검색</button>
      </div>
      <div class="total-count">
        <p>총 ${totalCount} 건</p>
      </div>
      <hr>
      <div class="row gy-3">
        <table class="table table-bordered table-hover text-center align-middle">
          <thead class="table-light">
            <tr>
              <th>#</th>
              <th>기업 이름</th>
              <th>채용공고 제목</th>
              <th>결제 방식</th>
              <th>구매 일자</th>
              <th>구매 금액</th>
              <th>상품 이름</th>
              <th>상품 타입</th>
              <th>상품 가격</th>
              <th>적용 일수</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="buy" items="${compBuyList}" varStatus="status">
              <tr>
                <td>${buy.rnum}</td>
                <td>${buy.company.compNm}</td>
                <td>${empty buy.employ.employTitle ? '-' : buy.employ.employTitle}</td>
                <td>${buy.buyMethod}</td>
                <td>${buy.buyDate}</td>
                <td>${buy.buyAmount}</td>
                <td>${buy.prod.productNm}</td>
                <td>${buy.prod.productType}</td>
                <td>
                  <fmt:formatNumber value="${buy.prod.productPrice}" type="number" pattern="#,###"/>
                </td>
                <td>${buy.buyQty}일</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <div class="paging-area">${pagingHTML}</div>
      <form id="searchForm" style="display: none">
        <form:input path="condition.searchType" placeholder="searchType" />
        <form:input path="condition.searchWord" placeholder="searchWord" />
        <input type="text" name="page" placeholder="page" />
      </form>
    </div>
  </div>

	<!-- 결제 통계 -->
	<div class="tab-pane fade" id="pills-stats" role="tabpanel" aria-labelledby="pills-stats-tab" tabindex="0">
	  <div class="container">
	    <h2>결제 통계</h2>
	    <br>
	    <div class="row">
	      <div class="col-md-6">
	        <h4>결제 방식 비율</h4>
	        <canvas id="paymentMethodChart"></canvas>
	      </div>
	      <div class="col-md-6">
	      	<div class="d-flex justify-content-between align-items-center mb-3">
		        <h4>월별 결제 금액</h4>
		        <select id="YearSelect" class="form-select w-auto">
				</select>
	      	</div>
	        <canvas id="monthlyPaymentChart"></canvas>
	      </div>
	    </div>
	  </div>
	</div>
</div>



<script src="${pageContext.request.contextPath}/resources/js/admin/buy/buyList.js"></script>