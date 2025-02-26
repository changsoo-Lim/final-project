<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
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
            </div>
            <div class="info-item">
                <strong>회원 아이디:</strong>
                <span>${board.memId}</span>
            </div>
        </div>

        <hr>

        
        <div class="title-container">
            <label for="boardTitle"><strong>제목</strong></label>
            <p  id="boardTitle"  disabled>${board.boardTitle}</p>
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

    <hr>
		
		<div class="button-container">
<%-- 			<a href="<c:url value='/amdin/board/${boardNo}/adminBoardEdit' />" class="btn btn-primary btn-sm ">게시글 수정</a> --%>
				<form action="<c:url value='/admin/board/${boardNo}' />" method="POST" onsubmit="return confirm('정말 삭제하시겠습니까?');">
			        <button type="submit" class="btn btn-danger ">삭제</button>
			    </form>
			<a href="<c:url value='/admin/board'/>" class="btn btn-primary ">목록</a>
		</div>
		<br>
		<br>
<!-- 			<p> -->
<!-- 			   <button class="btn btn-light mx-2 btn-sm " type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample"> -->
<!-- 			    댓글보기 -->
<!-- 			   </button>  -->
<!-- 			</p>	 -->
<!-- 	<div class="collapse" id="collapseExample"> -->
	  	<div class="card card-body">
	  		
	  		
	  		
	  		<h4>댓글</h4>
	            <c:if test="${not empty commentList}">
	                <table class="table table-borderless">
	
	                    <tbody>
	                        <c:forEach items="${commentList}" var="comment">
	                            <tr>
	                                <td>${comment.memId}</td>
	                                <td>${comment.commentCont}</td>
	                                <td>${fn:replace(comment.commentDate, 'T', ' ')}</td>
	                                <td>
							            <!-- 댓글 삭제 버튼 -->
							            <form action="<c:url value='/admin/board/${board.boardNo}/comment/delete' />" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
							                <input type="hidden" name="commentDate" value="${comment.commentDate}" />
							                <button type="submit" class="btn btn-danger btn-sm">삭제</button>
							            </form>
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
        <form action="<c:url value='/admin/board/${board.boardNo}/comment' />" method="post">
            <div class="mb-3">
                <label for="commentCont" class="form-label">댓글 내용</label>
                <textarea class="form-control" id="commentCont" name="commentCont" rows="3" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">댓글 등록</button>
        </form>
	  
	    </div>
<!-- 	</div> -->
</div>