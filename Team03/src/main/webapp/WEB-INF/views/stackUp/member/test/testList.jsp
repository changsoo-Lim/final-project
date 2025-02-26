<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="<c:url value="/resources/css/member/test/testList.css" />">
<div class="container mt-5">
    <h2 class="mb-4">검사/테스트 리스트</h2>
    <!-- 검색 영역 -->
    <div class="card mb-4">
        <div class="card-body">
            <form id="searchForm" class="row g-3" method="get" action='<c:url value="/member/test/list" />'>
                <div class="col-md-3">
                	<input type="hidden" name="page" placeholder="page" />
                    <label for="testCd" class="form-label">검사 종류</label>
                    <select id="testCd" name="testCd" class="form-select">
                        <option value="">전체</option>
                    	<c:forEach var="code" items="${testCode}">
				            <option value="${code.codeCd}" ${code.codeCd == param.testCd ? 'selected' : ''} >${code.codeNm}</option>
					    </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="employTitle" class="form-label">채용공고명</label>
                    <input type="text" id="employTitle" name="employTitle" class="form-control" placeholder="채용공고명 입력" value="${param.employTitle}">
                </div>
                <div class="col-md-3">
                    <label for="compNm" class="form-label">회사명</label>
                    <input type="text" id="compNm" name="compNm" class="form-control" placeholder="회사명 입력" value="${param.compNm}">
                </div>
                <div class="col-md-3">
                    <label for="candidateYn" class="form-label">응시 여부</label>
                    <select id="candidateYn" name="candidateYn" class="form-select">
                        <option value="">전체</option>
                        <option value="N" ${'N' == param.candidateYn ? 'selected' : ''}>응시하지 않음</option>
                        <option value="Y" ${'Y' == param.candidateYn ? 'selected' : ''}>응시 완료</option>
                    </select>
                </div>
                <div class="col-12 d-flex justify-content-end">
                    <button type="submit" id="searchBtn" class="btn btn-primary">검색</button>
                    <button type="reset" class="btn btn-secondary ms-2" id="resetBtn">초기화</button>
                </div>
            </form>
        </div>
    </div>

    <!-- 테이블 영역 -->
    <div class="card">
        <div class="card-body">
            <table class="table table-striped text-center align-middle">
                <thead>
                    <tr>
                    	<th scope="col">번호</th>
                        <th scope="col">검사 종류</th>
                        <th scope="col">채용공고명</th>
                        <th scope="col">모집분야명</th>
                        <th scope="col">회사명</th>
                        <th scope="col">응시 여부</th>
                        <th scope="col">응시일</th>
                        <th scope="col">응시하기</th>
                    </tr>
                </thead>
                <tbody id="testTableBody">
                    <c:choose>
						<c:when test="${not empty candidateList}">
							<c:forEach var="candidate" items="${candidateList}">
								<tr>
									<td>${candidate.rnum}</td>
									<td>
										<c:forEach var="code" items="${testCode}">
									        <c:if test="${candidate.testCd == code.codeCd}">
									            ${code.codeNm}
									        </c:if>
									    </c:forEach>
									</td>
									<td>${candidate.employTitle}</td>
									<td>${candidate.filedNm}</td>
									<td>${candidate.compNm}</td>
									<td>
										<c:if test="${candidate.candidateYn == 'Y' }">
											응시 완료
										</c:if>
										<c:if test="${candidate.candidateYn == 'N' }">
											응시하지 않음
										</c:if>
									</td>
									<td>
										<c:if test="${candidate.candidateCdt == null || candidate.candidateCdt == ''   }">
											-
										</c:if>
										<c:if test="${candidate.candidateCdt != null || candidate.candidateCdt.length > 0   }">
											${candidate.candidateCdt.substring(0, 4)}- 
											${candidate.candidateCdt.substring(5, 7)}- 
											${candidate.candidateCdt.substring(8, 11)}
										</c:if>
									</td>
									<td>
										
										<a href="<c:url value='/member/test/${candidate.candidateNo}' />" class="btn btn-sm btn-info" ${'Y' == candidate.candidateYn ? 'disabled' : ''}>
										    응시하기 <!-- ${candidate.candidateNo} -->
										</a>
										
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="8" class="text-center py-3">등록된 테스트가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
                </tbody>
            </table>
        </div>
    </div>
    <div class="d-flex justify-content-between align-items-center mt-3">
		<div class="flex-grow-1 d-flex justify-content-center">
			${pagingHTML}
		</div>
	</div>
</div>
<script src='<c:url value="/resources/js/member/testList.js" />'></script>
