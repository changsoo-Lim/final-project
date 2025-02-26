<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/resources/css/admin/searchFilter/searchFilter.css">
<h1>🔍검색필터 관리</h1>
<hr>
<h2>Code List</h2>

			<!-- Drop-down for root codes -->
			<div class="row">
			    <div class="col-md-6">
			        <label for="rootCodeSelect" class="form-label fw-bold">공통코드</label>
			        <select id="rootCodeSelect" class="form-select form-control" aria-label="small select example" required>
			            <option value="">공통코드를 선택해주세요.</option>
			        </select>
			    </div>
			</div>
	<!-- 추가 버튼 추가 -->
<div class="row mt-3">
    <div class="col-md-6">
        <button id="showFormButton" class="btn btn-primary">새로운 공통 코드추가</button>
    </div>
</div>

<!-- 등록 폼 -->
<form id="codeForm" action="${pageContext.request.contextPath}/admin/filter/add" method="post" style="display: none;">
    <div class="form-row">
        <!-- 코드 입력 필드 -->
        <div class="form-group">
            <label for="codeCd">새 공통코드</label>
            <input type="text" id="codeCd" name="codeCd" class="form-control" required placeholder="공통코드를 입력하세요.">
            <div id="autocomplete-results-codeCd" class="autocomplete-container"></div>
        </div>
        <br>
        <!-- 코드명 입력 필드 -->
        <div class="form-group">
            <label for="codeNm">새 코드명</label>
            <input type="text" id="codeNm" name="codeNm" class="form-control" required placeholder="공통코드명을 입력하세요.">
             <div id="autocomplete-results-codeNm" class="autocomplete-container"></div>
        </div>
        <br>
        <!-- 부모 코드 입력 필드 -->
        <div class="form-group">
            <label for="codeParent">새 부모 코드</label>
            <input type="text" id="codeParent" name="codeParent" class="form-control" placeholder="부모 코드를 입력하세요.">
            <div id="autocomplete-results-codeParent" class="autocomplete-container"></div>
        </div>
    </div>
    <br>
    <!-- 저장 버튼 -->
    <div class="form-group submit-btn">
        <button type="submit" class="btn btn-primary">저장</button>
    </div>
</form>
<hr>	
<!--------------------------------------모달-------------------------------------------------->
<div id="editModal" class="modal fade" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="editModalLabel">공통코드 수정</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editForm">
                    <div class="mb-3">
                        <label for="modalCodeCd" class="form-label">공통코드</label>
                        <input type="text" id="modalCodeCd" class="form-control" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="modalCodeNm" class="form-label">코드명</label>
                        <input type="text" id="modalCodeNm" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label for="modalCodeParent" class="form-label">부모코드</label>
                        <input type="text" id="modalCodeParent" class="form-control">
                    </div>
                    <button type="button" id="saveChangesButton" class="btn btn-primary w-100">저장</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!----------------------------------------모달------------------------------------------------>

	<div id="inputFieldsContainer"></div>


<script type="application/json" id="codeListData">
    ${codeListJson}
</script>
<script src="${pageContext.request.contextPath}/resources/js/admin/searchFilter/searchFilter.js"></script>
