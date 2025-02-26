<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container my-5">
     <!-- 기업 정보 섹션 -->
    <div class="container p-4 border rounded bg-Light shadow-sm">
        <div class="row">
            <!-- 왼쪽: 이미지 -->
            <div class="col-md-3 text-center">
                <img
                    src="<c:url value='/images/MemberImage/${review.companyVO.file.fileDetails[0].streFileNm}' />"
                    alt="기업 로고" class="img-fluid rounded shadow-sm" id="logoImg">
            </div>
            
            <!-- 오른쪽: 기업명 및 상세 정보 (2단 배치) -->
            <div class="col-md-9">
                <h1 class="fs-3 fw-bold text-primary">${review.companyVO.compNm}</h1>
                <div class="row mt-3">
                    <div class="col-md-6">
                        <p><strong>기업구분:</strong> ${review.companyVO.compType}</p>
                        <p><strong>직종:</strong> ${review.companyVO.compInd}</p>
                    </div>
                    <div class="col-md-6">
                        <p><strong>직무:</strong> ${review.companyVO.compJob}</p>
                        <p><strong>직무 상세:</strong> ${review.companyVO.compJobDetail}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 기업 정보와 리뷰 섹션 간의 여백 -->
    <div class="mt-4"></div>
    <!-- 리뷰 섹션 -->
	<div class="row">
	    <div class="col-12">
	        <div class="card shadow-sm">
	            <div class="card-header d-flex justify-content-between bg-gray-100">
	                <h5 class="fw-bold"><strong>제목:</strong> ${review.reviewTitle}</h5>
	                <div class="text-end">
	                    <span id="memId" class="small">
	                        <strong>작성자:</strong> ${review.memId.substring(0, 2)}****
	                    </span>
	                    &nbsp;|&nbsp;
	                    <span id="reviewDt" class="small">
	                        <strong>작성일:</strong> ${review.reviewDt.substring(0, 4)}-${review.reviewDt.substring(4, 6)}-${review.reviewDt.substring(6, 8)}
	                    </span>
	                </div>
	            </div>
	            <div class="card-body">
	                <p class="fw-bold">내용</p>
	                <textarea class="form-control rounded-3 p-3" rows="13" readonly>${review.reviewCont}</textarea>
	            </div>
	        </div>
	    </div>
	</div>
</div>