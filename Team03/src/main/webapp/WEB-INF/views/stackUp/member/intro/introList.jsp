<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table class="table table-striped">
	<tr>
		<th>자기소개서 제목</th>
		<td align="right">
			<c:url value="/intro/new" var="insertURL" />
			<a href="${insertURL}" class="btn btn-outline-primary btn-sm">작성</a>
		</td>
	</tr>
	<c:choose>
	    <c:when test="${not empty intro}">
	        <c:forEach items="${intro}" var="intro">
	            <tr>
	                <th>
	                    <c:url value="/intro/${intro.introNo}/edit" var="detailURL" />
	                    <a href="${detailURL}">${intro.introTitle}</a>
	                </th>
	                <td align="right" class="intro-item">
						<input type="hidden" id="introNo" required value="${intro.introNo}"/>
						<input type="hidden" name="_method" required value="delete"/>
                    	<button class="btn btn-outline-danger btn-sm delBtn">삭제</button>
	                </td>
	            </tr>
	        </c:forEach>
	    </c:when>
	    <c:otherwise>
	        <tr>
	            <td colspan="2" style="text-align: center;">자기소개서를 작성해주세요!</td>
	        </tr>
	    </c:otherwise>
	</c:choose>
</table>
<script src="${pageContext.request.contextPath}/resources/js/intro_detail/introList.js"></script>