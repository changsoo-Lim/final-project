<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" href="<c:url value="/resources/css/member/test/testDetail.css" />">

<div class="container mt-5">
    <div class="card shadow-lg p-4">
        <!-- 검사 유형별 배경색 -->
        <div class="card-header text-center" style="background-color: 
            <c:choose>
                <c:when test="${candidate.testCd == 'TE01'}">#f5f5f5</c:when> 
                <c:when test="${candidate.testCd == 'TE02'}">#e0f7fa</c:when> 
                <c:when test="${candidate.testCd == 'TE03'}">#e8eaf6</c:when> 
                <c:when test="${candidate.testCd == 'TE04'}">#ede7f6</c:when> 
                <c:otherwise>#ffffff</c:otherwise>
            </c:choose>;
        ">
            <h3>${candidate.testNm}</h3>
        </div>

        <div class="card-body">
        	<table class="table table-centered mlr-13 w-50">
        		<tr>
        			<td>
        				<strong class="m-2">시험 종류 : </strong>
        			</td>
        			<td>
        				<c:forEach var="code" items="${testCd}">
		                    <c:if test="${candidate.testCd == code.codeCd}">
		                        <span>${code.codeNm}</span>
		                    </c:if>
		                </c:forEach>
        			</td>
        		</tr>
        		<tr>
        			<td>
        				<strong class="m-2">시험 시간 : </strong> 
        			</td>
        			<td>
        				<span>${(candidate.testTime / 60).intValue()} 분</span>
        			</td>
        		</tr>
        		<tr>
        			<td>
        				<strong class="m-2">문항 수 : </strong> 
        			</td>
        			<td>
        				<span>${testQuestnCount} 문항</span>
        			</td>
        		</tr>
        	</table>
            <!-- 시험 정보 -->
            

            <!-- 안내 메시지 -->
            <div class="alert alert-info text-center">
                테스트를 시작하기 전에 인터넷 연결과 환경을 확인하세요.
            </div>
        </div>

        <!-- 테스트 시작 버튼 -->
        <div class="card-footer text-center">
            <button class="btn btn-lg btn-success" data-test="<c:url value='/member/test/conduct/' />${candidate.candidateNo}" onclick="openTestWindow(this);">
                테스트 시작
            </button>
        </div>
    </div>
</div>

<script src='<c:url value="/resources/js/member/testDetail.js" />'></script>
