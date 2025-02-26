<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main/board/boardDetail.css"> 

		
		
<div class="container">
    <h3 class="page-title">커뮤니티 상세페이지</h3>

    <div class="board-detail-container">
        <!-- 게시판 번호와 회원 아이디 -->
        <div class="info-container">
            <div class="info-item">
                <strong>게시글 번호:</strong>
                <span>${board.boardNo}</span>
                <strong>  |  회원 아이디:</strong>
                <span>${fn:substring(board.memId, 0, 3)}${fn:substring('**********', 0, fn:length(board.memId) - 3)}</span>
            </div>
            <div class="info-item">
            <button class="btn btn-warning btn-sm" type="button" data-bs-toggle="modal"
             data-bs-target="#reportModal" data-report-target="board" data-report-id="${board.memId}">
		        신고하기
		    </button>
            </div>
        </div>
<hr>
        <div class="title-container">
            <label for="boardTitle"><strong>제목</strong></label>
            <p id="boardTitle"  disabled>${board.boardTitle}</p>
        </div>

        <hr>

        <div class="content-container">
            <label for="boardCont"><strong>내용</strong></label>
            <p id="boardCont" rows="10" disabled>${board.boardCont}</p>
        </div>

        <hr>

        
        <div class="file-container">
            <strong>파일:</strong>
            <div class="file-list">
                <c:forEach items="${board.file.fileDetails}" var="fd" varStatus="vs">
                    <c:url value='/board/${boardNo}/file/${fd.atchFileNo}/${fd.fileSn}' var="downUrl"/>
	                    <a href="${downUrl}">${fd.orignlFileNm} (${fd.fileFancysize})</a>
	                    ${not vs.last ? '|' : ''}
                </c:forEach>
            </div>
        </div>
    </div>

		<div class="button-container">
			<c:if test="${currentUserId == board.memId}">
					<a href="<c:url value='/main/board/${boardNo}/boardEdit' />" class="btn btn-primary btn-sm ">수정</a>
						<form action="<c:url value='/main/board/${board.boardNo}' />"  id="deleteForm"  method="post" >
					        <button type="submit" class="btn btn-danger btn-sm " id="deleteButton">삭제</button>
					    </form>
			 </c:if>
					<a href="<c:url value='/main/board'/>" class="btn btn-primary btn-sm ">목록</a>
		</div>
			
		    <!-- 신고 Modal -->
<div class="modal fade" id="reportModal" tabindex="-1" aria-labelledby="reportModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="reportModalLabel">신고하기</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="<c:url value='/main/board/report/${board.boardNo}' />" method="post" onsubmit="return reportSubmit()">
                    <!-- 로그인한 사용자 아이디를 신고자에 설정 -->
                    <input type="hidden" name="reportMemId" value="${currentUserId}" />
                    
                    <!-- 신고 대상자의 아이디 -->
                    <input type="hidden" name="reportedMemId" value="" />
                    
                    <div><span>신고대상자: </span><span id="reportTarget"></span></div>
                    <br>
                    <div class="mb-3">
                        <label for="reportReason" class="form-label">신고 사유</label>
                        <select class="form-select" id="reportReason" name="reportReason" required>
                            <option value="욕설">욕설</option>
                            <option value="광고">광고</option>
                            <option value="허위 정보">허위 정보</option>
                            <option value="기타">기타</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="reportCont" class="form-label">신고 내용</label>
                        <textarea class="form-control" id="reportCont" name="reportCont" rows="3" required></textarea>
                        <br>
                        <p class="fs-6 font-monospace">신고약관에 해당하지 않는 허위신고는 제재 대상입니다.</p>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">신고 제출</button>
                </form>
            </div>
        </div>
    </div>
</div>
		<br>
		<br>
		<br>
<hr>
	  		<h4>댓글</h4>
	            <c:if test="${not empty commentList}">
	                <table class="table table-borderless">
	                    <tbody>
	                        <c:forEach items="${commentList}" var="comment">
	                            <tr data-memid="${comment.memId }">
	                            <td>${fn:substring(comment.memId, 0, 3)}${fn:substring('**********', 0, fn:length(comment.memId) - 3)}</td>
<%-- 	                                <td>${comment.memId}</td> --%>
	                                <td>${comment.commentCont}</td>
	                                <td>${comment.formattedCommentDate}</td>
	                                <td>
	                                <div class="comment-actions">
		                                 <!-- 댓글 신고 버튼 -->
								            <button class="btn btn-warning btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#reportModal"
								            data-report-target="comment" data-report-id="${comment.memId}">
								                신고
								            </button>
								            <!-- 댓글 삭제 버튼 -->
								             <c:if test="${currentUserId == comment.memId}">
									            <form  action="<c:url value='/main/board/${board.boardNo}/comment/delete' />" method="post" >
									                <input type="hidden" name="commentDate" value="${comment.commentDate}" />
									                <button type="submit" class="btn btn-danger btn-sm">삭제</button>
									            </form>
								            </c:if>
							          </div>
							        </td>
	                            </tr>
	                        </c:forEach>
	                    </tbody>
	                </table>
	            </c:if>
	            
	            <c:if test="${empty commentList}">
	                <p>댓글이 없습니다.</p>
	            </c:if>
	  			
	  			
	  			 
        <h4>댓글 등록</h4>
        <form  action="<c:url value='/main/board/${board.boardNo}/comment' />" method="post">
            <div class="mb-3">
                <label for="commentCont" class="form-label">댓글 내용</label>
                <textarea class="form-control" id="commentCont" name="commentCont" rows="3" required></textarea>
            	
            </div>
            <button type="submit" class="btn btn-primary btn-sm">댓글 등록</button>
<%--             <a href="<c:url value='/main/board'/>" class="btn btn-primary btn-sm " >목록</a> --%>
            
        </form>
	  
	    </div>
<!-- 	</div> -->
<!-- </div> -->
<script src="${pageContext.request.contextPath}/resources/js/board/boardDetail.js"></script>
