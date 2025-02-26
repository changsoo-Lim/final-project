<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/admin/resource/adminResourceDetail.css"> 

<h1>📘자료실 관리 상세페이지</h1>
			<div class="conts">
				<div class="job_info_detail">
					<div class="top-cnt">
					<br>
						<div class="title-container">
				            <label for="resourceTitle"><strong>이력서 양식 제목 :</strong></label>
				            <p id="resourceTitle" value="${resource.resourceTitle}" disabled>${resource.resourceTitle}</p>
				        </div>
						<div class="file-container">
						
				            <strong>파일:</strong>
				            <div class="file-list">
				            		<!-- 파일명 출력 -->
							        <c:forEach items="${resource.file.fileDetails}" var="fd" varStatus="vs">
							        <!-- 파일 확장자가 이미지(jpg, jpeg, png, gif 등)인 경우 제외 -->
								    <c:if test="${not (fn:endsWith(fd.orignlFileNm, 'jpg') or fn:endsWith(fd.orignlFileNm, 'jpeg') 
								        or fn:endsWith(fd.orignlFileNm, 'png') or fn:endsWith(fd.orignlFileNm, 'gif'))}">
								            <c:url value='/resource/${resourceNo}/file/${fd.atchFileNo}/${fd.fileSn}' var="downUrl"/>
								            <a href="${downUrl}">
								                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-file-earmark-arrow-down" viewBox="0 0 16 16">
								                    <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293z"/>
								                    <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
								                </svg>다운로드 | ${fd.orignlFileNm} (${fd.fileFancysize})
								            </a>
								            ${not vs.last ? '|' : ''}
								            <p> | 총 다운로드 횟수 ${fd.fileDwncnt} 회</p>
						            </c:if>
							        </c:forEach>
							        <hr>
							
							        <!-- 이미지 출력 -->
							        <c:forEach items="${resource.file.fileDetails}" var="fd">
							        	<!-- 파일 확장자가 엑셀(xlsx, xls), 한글(hwp), 워드(doc, docx)인 경우 이미지를 제외 -->
								        <c:if test="${not (fn:endsWith(fd.orignlFileNm, 'xlsx') or fn:endsWith(fd.orignlFileNm, 'xls') 
										        or fn:endsWith(fd.orignlFileNm, 'hwp') or fn:endsWith(fd.orignlFileNm, 'doc') 
										        or fn:endsWith(fd.orignlFileNm, 'docx'))}">
								            <img class="img-center" src="<c:url value="/images/resource/${fd.streFileNm}" />" alt="${fd.orignlFileNm}">
							            </c:if>
							        </c:forEach>
				            

				            </div>
				        </div>
				        <div>
					        <img src="/stackUp/resources/Modernize-bootstrap-free-main/assets/images/logos/StackUpLogo.png" class="rogo"  alt="">
					        <br>
					        <br>
					        <p class="textMessage">본 정보는 누구나 자유롭게 복제, 배포, 전송할 수 있습니다.</p>
				        </div>
				            <div class="stackup-banner">
								<a href="${pageContext.request.contextPath}/resume/new">
								<p style="color: white">쉽고, 빠르게 작성하고 입사지원까지 편리한 Stack Up!      온라인 이력서로 취업확률 높이러 가기!</p>
								</a>
							</div>
					</div>
				</div>
			</div>
			<div class="button-container">
			<a href="<c:url value='/admin/resource/${resourceNo}/adminResourceEdit' />" class="btn btn-primary ">수정</a>
				<form action="<c:url value='/admin/resource/${resource.resourceNo}' />" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
			        <button  type="submit" class="btn btn-danger"  >삭제</button>
			    </form>
			    <a href="<c:url value='/admin/resource'/>" class="btn btn-primary " >목록</a>
			</div>