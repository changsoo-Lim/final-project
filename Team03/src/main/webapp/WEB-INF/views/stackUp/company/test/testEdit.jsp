<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="<c:url value="/resources/css/company/test/testEdit.css" />">
<%-- <pre>${testQuestnList }</pre> --%>
<form action="<c:url value="/company/test/${test.testNo}" />" id="testForm" enctype="multipart/form-data" method="POST" >
	<input type="hidden" name="_method" value="PUT" />
	<div class="container mt-4" style="position:relative;">
	    <div style="position:absolute; width:120%; max-width: 80vw; left: -100px; right: 0; margin: 0 auto;">
	        <div class="d-flex">
	            <!-- 좌측: 검사 유형 선택 -->
	            <div class="col-xl-5 me-7">
	                <h2 class="mb-4">검사 수정</h2>
	                    <!-- 검사 유형 선택 -->
	                    <div class="mb-3">
	                        <label for="testCd" class="form-label">검사 유형</label>
	                        <select id="testCd" name="test.testCd" class="form-select">
	                            <c:forEach var="code" items="${testCode}">
	                                <option value="${code.codeCd}" ${test.testCd == code.codeCd ? 'selected' : ''}>
	                                    ${code.codeNm}
	                                </option>
	                            </c:forEach>
	                        </select>
	                        <label for="testNm" class="form-label mt-2">시험명</label>
	                        <input type="text" name="test.testNm" id="testNm" class="form-control" value="${test.testNm != null ? test.testNm : ''}" />
	                        <label for="testTime" class="form-label mt-2">시험시간 (분)</label>
							<input type="number" name="test.testTime" id="testTime" class="form-control" placeholder="예: 30" value="${test.testTime != null ? test.testTime : ''}" />
	                    </div>
		                <div class="mb-3 d-flex justify-content-between">
		                    <!-- 문제 추가 버튼 -->
		                    <button id="addQuestion" class="btn btn-primary" type="button">문제 추가</button>
		                    <!-- 저장/취소 버튼 -->
		                    <div class="d-flex">
		                        <button type="submit" form="testForm" id="saveTest" class="btn btn-success me-2">저장</button>
		                        <button type="button" id="resetForm" class="btn btn-secondary me-2" onclick="location.href='<c:url value="/company/test/list" />'">취소</button>
		                        <button type="button" form="testForm" id="deleteTest" class="btn btn-danger" data-testNo="${test.testNo }" onclick="">삭제</button>
		                    </div>
		                </div>
	                
	                <!-- 문제 네비게이션 -->
	                <h2 class="mb-4">문제 네비게이션</h2>
	                <div>
	                	<button type="button" id="showAllQuestions" class="btn btn-link" >모두 보기</button>
	                </div>
                    <!-- 문제 네비게이션 버튼 영역-->
	                <div id="questionsNavigation" class="mb-3 d-flex flex-wrap justify-content-center align-items-center">
	                    
	                </div>
	            </div>
	
	            <!-- 동적 문제 추가 영역 -->
	            <div class="col-xl-6">
	                <div id="questionsArea">
	                	
	                </div>	
	            </div>
	        </div>
	    </div>
	</div>
</form>

<!-- 이미지 미리보기 모달 -->
<div class="modal fade" id="imagePreviewModal" tabindex="-1" aria-labelledby="imagePreviewModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="imagePreviewModalLabel">이미지 미리보기</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body text-center">
                <img id="imagePreview" class="img-fluid" alt="미리보기 이미지" style="max-height: 300px; object-fit: contain;" />
                <div id="imageFileName" class="mt-2 text-muted"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" id="cancelButton" data-bs-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" id="uploadNewButton">새 업로드</button>
                <button type="button" class="btn btn-danger" data-atch-file-no="" data-file-sn="" id="deleteButton">삭제</button> <%-- data-atch-file-no="${fd.atchFileNo}" data-file-sn="${fd.fileSn}" --%>
            </div>
        </div>
    </div>
</div>

<script>
    const testQuestnList = ${testQuestnList};
</script>

<script src='<c:url value="/resources/js/company/test/testEdit.js" />'></script>
