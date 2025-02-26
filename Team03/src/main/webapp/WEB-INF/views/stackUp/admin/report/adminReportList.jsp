<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/resources/css/admin/report/adminReportList.css"> 
    <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" rel="stylesheet">
<!-- 관리자 커뮤니티 -->    
<div class="container">

<h2>신고 내역</h2>
    <div class="search-area ">
        <form:select path="condition.searchType" class="form-select w-auto">
            <form:option value="reportReason" label="신고사유"></form:option>
            <form:option value="repornCont" label="신고내용"></form:option>
        </form:select>
        <form:input path="condition.searchWord" class="form-control w-auto"  />
        <button type="submit" class="btn btn-primary search-btn">검색</button>
    </div>
	
	
	
	
	<!-- 신고 목록 테이블 -->
<table class="table table-hover table-bordered align-middle">
    <thead class="table-light">
        <tr>
            <th>번호</th>
            <th>신고대상</th>
            <th>신고내용</th>
            <th>신고사유</th>
            <th>신고자</th>
            <th>신고일시</th>
            <th>상태</th>
            <th>블랙 추가</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${adminReportList}" var="report">
            <tr>
                <td>${report.rnum}</td>
                <td>${report.reportedMemId}</td>
                <td>${report.reportCont}</td>
                <td>${report.reportReason}</td>
                <td>${report.reportMemId}</td>
                <td>${report.reportDt}</td>
                <td>
                    <c:choose>
                        <c:when test="${report.memStatus == 'N'}"><span class="status-normal">정상</span></c:when>
                        <c:when test="${report.memStatus == 'B'}"><span class="status-red">블랙</span></c:when>
                        <c:otherwise><span class="status-unknown">상태 미정</span></c:otherwise>
                    </c:choose>
                </td>
                <!-- 블랙리스트 추가 버튼 -->
                <td>
                
                    <button 
                       class="btn btn-primary btn-sm open-blacklist-modal" 
                        data-report-no="${report.reportNo}" 
                        data-mem-id="${report.reportedMemId}" 
                        data-report-cont="${report.reportCont}"
                        data-report-reason="${report.reportReason}" 
                        data-bs-toggle="modal" 
                        data-bs-target="#blacklistModal">
                        블랙 추가
                    </button>
                </td>
            </tr>
            
        </c:forEach>
    </tbody>
    <tfoot>
		<tr>
			<td colspan="9">
				<div class="paging-area">
					${pagingHTML }
					
				</div>
			</td>
		</tr>
	</tfoot>
</table>
            <!-- 블랙리스트 추가 Modal -->
<div class="modal fade" id="blacklistModal" tabindex="-1" aria-labelledby="blacklistModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="blacklistModalLabel">블랙리스트 추가</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="<c:url value='/admin/report/${report.reportNo }' />" method="post" id="blacklistForm">
                    <!-- 신고 대상 ID -->
                    <div class="mb-3">
                    <label for="blacklistReason" class="form-label">정지 대상 아이디:</label>
                    <input type="hidden" id="MemId" name="MemId" value="${report.reportedMemId }" readonly="readonly"/>
                    <span id="spanMemId"></span>
                    </div>
                    <div class="mb-3">
                        <label for="blacklistReason" class="form-label">정지 사유</label>
                        <select class="form-select" id="blacklistReason" name="blacklistReason" required>
                            <option value="욕설">욕설</option>
                            <option value="광고">광고</option>
                            <option value="허위 정보">허위 정보</option>
                            <option value="기타">기타</option>
                        </select>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">저장</button>
                </form>
            </div>
        </div>
    </div>
</div>
<form id="searchForm" style="display:none">
    <form:input  path="condition.searchType" placeholder="searchType"/>
    <form:input  path="condition.searchWord" placeholder="searchWord"/>
    <input type="text" name="page" placeholder="page"/>

</form>
</div>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="${pageContext.request.contextPath}/resources/js/admin/report/adminReportEdit.js"></script>
<c:if test="${not empty message}">
   <script type="text/javascript">
      Swal.fire({
         icon: 'success', // 성공 알림 아이콘
         title: '완료',
         text: "${message}",
         confirmButtonText: '확인'
      });
   </script>
</c:if>
<c:if test="${not empty errorMessage}">
   <script type="text/javascript">
      Swal.fire({
         icon: 'error', // 오류 알림 아이콘
         title: '오류',
         text: "${errorMessage}",
         confirmButtonText: '확인'
      });
   </script>
</c:if>
