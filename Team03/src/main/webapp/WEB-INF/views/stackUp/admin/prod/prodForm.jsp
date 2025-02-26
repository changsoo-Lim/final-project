<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/prod/prodForm.css" />

<div class="container mt-5">
    <h2 class="mb-4">공고 상품관리</h2>
    <form:form method="post" modelAttribute="prod" enctype="multipart/form-data" class="row g-3">
        <!-- 왼쪽: 입력 폼 -->
        <div class="col-md-7" id="prodField">
            <table class="table table-bordered">
			    <tbody>
			        <tr>
			            <th class="text-end align-middle" style="width: 20%;">상품명</th>
			            <td style="width: 80%;">
			            	<input type="hidden" name="productCd" id="productCd" value="">
			                <form:input path="productNm" id="productNm" cssClass="form-control" required="required" placeholder="상품 이름을 입력해주세요." />
			                <form:errors path="productNm" element="span" class="text-danger" />
			            </td>
			        </tr>
			        <tr>
			            <th class="text-end align-middle">상품 적용 기간 (일)</th>
			            <td>
			                <form:input path="productPeriod" id="productPeriod" cssClass="form-control" required="required" placeholder="상품 적용 기간을 입력해주세요." />
			                <form:errors path="productPeriod" element="span" class="text-danger" />
			            </td>
			        </tr>
			        <tr>
			            <th class="text-end align-middle">상품 가격(원)</th>
			            <td>
			                <form:input path="productPrice" id="productPrice" cssClass="form-control" required="required" placeholder="ex) 2000000 ( ,는 입력하지 않습니다.)"/>
			                <form:errors path="productPrice" element="span" cssClass="text-danger" />
			            </td>
			        </tr>
			        <tr>
			            <th class="text-end align-middle">상품 타입</th>
			            <td>
			                <form:select path="productType" id="productType" cssClass="form-select" required="required">
			                    <form:option value="">광고 선택</form:option>
			                    <form:option value="BANNER">배너광고</form:option>
			                    <form:option value="MAIN">메인광고</form:option>
			                </form:select>
			                <form:errors path="productType" element="span" cssClass="text-danger" />
			            </td>
			        </tr>
			        <tr>
			            <th class="text-end align-middle">파일 등록</th>
			            <td>
			            	<form:input type="hidden" path="atchFileNo" id="atchFileNo"/> 
			                <input type="file" name="uploadFiles" id="uploadFiles" class="form-control" onchange="previewImage(event)" />
			                <form:errors path="uploadFiles" element="span" cssClass="text-danger" />
			            </td>
			        </tr>
			    </tbody>
			</table>
			<div class="text-end mt-3 position-relative">
			    <form:button type="submit" id="addBtn" class="btn btn-primary">등록</form:button>
			    <form:button type="submit" id="editBtn" class="btn btn-primary" style="display:none;">수정</form:button>
			</div>
			<br>
	        <table class="table table-hover" id="prodTalble">
	            <thead>
	                <tr>
	                    <th class="prod-productNm" >상품명</th>
	                    <th class="prod-productPeriod" >적용 기간 단위(일)</th>
	                    <th class="prod-productPrice" >가격 (원)</th>
	                    <th class="prod-productType" >상품 타입</th>
	                    <th class="prod-del" ></th>
	                </tr>
	            </thead>
	            <tbody>
	                <c:if test="${empty prodList}">
	                    <tr>
	                        <td colspan="4" class="text-center">등록된 상품이 없습니다.</td>
	                    </tr>
	                </c:if>
	                <c:if test="${not empty prodList}">
	                    <c:forEach items="${prodList}" var="product">
	                        <tr data-product-cd="${product.productCd}">
	                            <td class="prod-productNm">${product.productNm}</td>
	                            <td class="prod-productPeriod">${product.productPeriod}일</td>
	                            <td class="prod-productPrice">
									<fmt:formatNumber value="${product.productPrice}" type="number" pattern="#,###" />
								</td>
	                            <td class="prod-productType">${product.productType}</td>
	                            <td class="prod-del">
	                            	<input type="hidden" class="productCd" required value="${product.productCd}" />
	                            	<button id="delBtn" type="button" class="btn btn-danger sm">삭제</button>
	                            </td>
	                        </tr>
	                    </c:forEach>
	                </c:if>
	            </tbody>
	        </table>
        </div>
        <!-- 오른쪽: 이미지 미리보기 -->
        <div class="col-md-5">
            <div class="image-preview" id="imagePreview">
				
            </div>
        </div>
    </form:form>
</div>

<script src="${pageContext.request.contextPath}/resources/js/prod/prodForm.js"></script>