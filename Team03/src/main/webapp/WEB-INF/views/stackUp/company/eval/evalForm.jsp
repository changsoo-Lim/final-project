<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>평가표</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Modernize-bootstrap-free-main/assets/css/styles.min.css" />
    <link href="${pageContext.request.contextPath}/resources/css/sweatAlert2/sweetalert2.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h4 class="card-title text-center mb-4">평가표</h4>
                        <div class="mb-3">
	                            <label for="member" class="form-label fw-bold">지원자 :</label>
	                            <select id="member" name="member" class="form-select evalCateNm">
									
	                            </select>
	                        </div>
						<div class="evalCateForm">
	                        <div class="mb-3">
	                            <label for="evalCateNm" class="form-label fw-bold">평가 항목 선택 :</label>
	                            <select id="evalCateNm" name="evalCateNm" class="form-select evalCateNm">
	                                <option value="">항목 선택</option>
	                                <c:forEach items="${interviewList}" var="interview">
	                                    <option value="${interview.codeCd}">${interview.codeNm}</option>
	                                </c:forEach>
	                                <option value="direct">직접입력</option>
	                            </select>
	                        </div>
	
	                        <div class="mb-3 directInputContainer" id="directInputContainer" style="display: none;">
	                            <label for="directInput" class="form-label fw-bold">직접 입력 :</label>
	                            <input type="text" id="directInput" name="directInput" class="form-control directInput" placeholder="직접 입력하세요" />
	                        </div>
	
	                        <div class="mb-3">
	                            <label for="evalCateCont" class="form-label fw-bold">평가 내용 :</label>
	                            <input type="text" id="evalCateCont" name="evalCateCont" class="form-control" placeholder="평가 내용을 작성하세요" />
	                        </div>
	
	                        <div class="mb-3">
	                            <label class="form-label fw-bold">평가 점수 :</label>
	                            <div class="d-flex gap-3">
	                                <div class="form-check">
	                                    <input type="radio" id="score1" name="evalCateScore" value="1" class="form-check-input" />
	                                    <label for="score1" class="form-check-label">1</label>
	                                </div>
	                                <div class="form-check">
	                                    <input type="radio" id="score2" name="evalCateScore" value="2" class="form-check-input" />
	                                    <label for="score2" class="form-check-label">2</label>
	                                </div>
	                                <div class="form-check">
	                                    <input type="radio" id="score3" name="evalCateScore" value="3" class="form-check-input" />
	                                    <label for="score3" class="form-check-label">3</label>
	                                </div>
	                                <div class="form-check">
	                                    <input type="radio" id="score4" name="evalCateScore" value="4" class="form-check-input" />
	                                    <label for="score4" class="form-check-label">4</label>
	                                </div>
	                                <div class="form-check">
	                                    <input type="radio" id="score5" name="evalCateScore" value="5" class="form-check-input" />
	                                    <label for="score5" class="form-check-label">5</label>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="d-flex justify-content-between">
							    <button  class="btn btn-primary evalAddBtn">추가</button>
							    <button  class="btn btn-danger evalDelBtn" style="display: none;">삭제</button>
							</div>
                        </div>
                        <br>
						<div class="evalCateFormList"></div>
                        <!-- 제출 버튼 -->
                        <div class="text-center">
                            <button id="evalCateBtn" type="submit" class="btn btn-primary w-50">제출하기</button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/evalCate/evalCateForm.js"></script>
    <script src="${pageContext.request.contextPath }/resources/js/sweatAlert2/sweetalert2.all.min.js"></script>
</body>
</html>
