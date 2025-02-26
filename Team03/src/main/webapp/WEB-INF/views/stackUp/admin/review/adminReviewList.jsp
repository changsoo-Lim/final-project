<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/resources/css/review/reviewList.css"> 
<div class="container">
    <br>
    <div>
        <h2 class="Title"> 면접 후기 관리 </h2>
    </div>

	<div class="search-area mb-4 d-flex justify-content-end align-items-center">
	    <form:select path="condition.searchType" class="form-select w-auto me-2">
	        <form:option value="reviewStatus" label="승인여부"></form:option>
	    </form:select>
	    <form:select path="condition.searchWord" class="form-select w-auto me-2">
	        <form:option value="" label="선택"></form:option>
	        <form:option value="Y" label="완료"></form:option>
	        <form:option value="N" label="대기"></form:option>
	    </form:select>
	
	    <button type="submit" class="btn btn-primary search-btn">검색</button>
	</div>

    <!-- 총 게시물 수 -->
    <div class="total-count">
        <p>총 ${totalCount} 건</p>
    </div>

    <hr>

    <!-- 면접 후기 리스트 테이블 -->
	<div class="table-responsive">
	    <table class="table table-bordered table-hover text-center align-middle" id="reviewTable">
	        <thead class="table-light">
	            <tr>
	                <th scope="col">#</th>
	                <th scope="col">제목</th>
	                <th scope="col">내용</th>
	                <th scope="col">별점</th>
	                <th scope="col">작성일</th>
	                <th scope="col">승인 여부</th>
	            </tr>
	        </thead>
	        <tbody>
			    <c:if test="${not empty reviewList}">
			        <c:forEach items="${reviewList}" var="review" varStatus="status">
			            <tr 
			                data-review-no="${review.reviewNo}" 
			                data-review-title="${review.reviewTitle}" 
			                data-review-cont="${review.reviewCont}" 
			                data-review-status="${review.reviewStatus}">
			                
			                <!-- RNUM (인덱스 번호) -->
			                <th scope="row">${review.rnum}</th>
			
			                <!-- 제목 -->
			                <td class="text-start">
			                    <c:choose>
			                        <c:when test="${review.reviewStatus eq 'Y'}">
			                           <a href="<c:url value='/review/${review.reviewNo}' />" class="text-decoration-none text-primary fw-bold">
			                            ${review.reviewTitle}
			                           </a>
			                        </c:when>
			                        <c:otherwise>
			                            <span class="text-decoration-none fw-bold">${review.reviewTitle}</span>
			                        </c:otherwise>
			                    </c:choose>
			                </td>
			
			                <!-- 내용 -->
			                <td class="reviewCont">
			                    ${fn:substring(review.reviewCont, 0, 15)}...
			                    <button type="button" class="btn btn-link p-0 open-modal-btn">상세보기</button>
			                </td>
			
			                <!-- 별점 -->
			                <td class="star-rating" data-rating="${review.reviewRating}"></td>
			
			                <!-- 작성일 -->
			                <td>${review.reviewDt.substring(0, 4)}-${review.reviewDt.substring(4, 6)}-${review.reviewDt.substring(6, 8)}</td>
			
			                <!-- 승인 여부 -->
			                <td>
			                    <c:choose>
			                        <c:when test="${review.reviewStatus eq 'Y'}">
			                            <span class="badge bg-success">승인 완료</span>
			                        </c:when>
			                        <c:otherwise>
			                            <span class="badge bg-danger">승인 대기</span>
			                        </c:otherwise>
			                    </c:choose>
			                </td>
			            </tr>
			        </c:forEach>
			    </c:if>
			</tbody>
	    </table>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="reviewModal" tabindex="-1" aria-labelledby="reviewModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="reviewModalLabel">면접 후기 상세</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <!-- 제목 -->
	        <input type="hidden" id="modalReviewNo">
	        <p><strong>제목:</strong> <input id="modalReviewTitle" class="form-control" readonly></p>
	        
	        <p><strong>내용:</strong></p>
	        <textarea id="modalReviewCont" class="form-control" rows="10" readonly></textarea>
	        
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	        <input type="hidden" id="modalReviewStatus">
	        <button type="button" class="btn btn-primary" id="approveBtn">승인</button>
	      </div>
	    </div>
	  </div>
	</div>

    <br>
    <hr>

    <!-- 페이징 영역 -->
    <div class="paging-area">${pagingHTML}</div>

    <!-- 숨겨진 검색 폼 (페이징 처리에 사용) -->
    <form id="searchForm" style="display:none">
        <form:input path="condition.searchType" placeholder="searchType" />
        <form:input path="condition.searchWord" placeholder="searchWord" />
        <input type="text" name="page" placeholder="page" />
    </form>
</div>
<script src='<c:url value="/resources/js/review/adminReviewList.js" />'></script>