<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container mt-5">
    <h2 class="text-center mb-4 fw-bold">구매 내역</h2>

    <div class="filter-container mb-4 text-end">
        <label for="sortSelect" class="me-2 fw-bold">정렬 기준:</label>
        <select id="sortSelect" class="form-select d-inline-block" style="width: 200px;">
            <option value="buyDate" selected>결제일순</option>
            <option value="buySdt">시작일순</option>
            <option value="buyEdt">종료일순</option>
        </select>
    </div>

    <div id="buyListContainer" class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach items="${buyList}" var="buy">
            <div class="col">
                <div class="card h-100 shadow border-0" 
                     data-buy-date="${buy.buyDate}" 
                     data-buy-sdt="${buy.buySdt}" 
                     data-buy-edt="${buy.buyEdt}">
                    <div class="card-body">
                        <h5 class="card-title fw-bold">${buy.prod.productNm}</h5>
                        <c:if test="${not empty buy.employNo}">
	                        <span class="badge bg-warning text-dark mb-2 d-inline-block">채용공고: ${buy.employ.employTitle}</span>
                        </c:if>
                        <ul class="list-unstyled mt-3">
                            <li><strong>기업명: </strong> ${buy.company.compNm}</li>
                            <li><strong>결제금액: </strong> 
                                <fmt:formatNumber value="${buy.buyAmount}" type="number" groupingUsed="true" /> 원
                            </li>
                            <li><strong>결제수단: </strong> ${buy.buyMethod}</li>
                            <li><strong>결제일시: </strong> ${buy.buyDate}</li>
                            <li><strong>상품적용일수: </strong> ${buy.buyQty}일</li>
                            <li><strong>상품 시작일: </strong> 
                                ${fn:substring(buy.buySdt, 0, 4)}-${fn:substring(buy.buySdt, 4, 6)}-${fn:substring(buy.buySdt, 6, 8)}
                            </li>
                            <li><strong>상품 종료일: </strong> 
							    <span class="buy-end-date" data-date="${buy.buyEdt}">
							        ${fn:substring(buy.buyEdt, 0, 4)}-${fn:substring(buy.buyEdt, 4, 6)}-${fn:substring(buy.buyEdt, 6, 8)}
							    </span>
							</li>
                        </ul>
                    </div>
                    <div class="card-footer text-center bg-light">
                    	<c:if test="${not empty buy.employNo}">
	                        <a href="<c:url value='/employ/detail/${buy.employNo}' />" class="btn btn-outline-primary btn-sm view-employ-btn">채용공고 보기</a>
                    	</c:if>
                    	<c:if test="${empty buy.employNo}">
	                        <a href="<c:url value='/index.do' />" class="btn btn-outline-primary btn-sm view-employ-btn">메인페이지 보기</a>
                    	</c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/buy/buyList.js"></script>