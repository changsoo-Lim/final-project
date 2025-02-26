<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<div class="container">
    <div class="row">
        <div class="col-6">
            <div class="form-group mb-3">
                <label for="emp" class="form-label">채용 공고:</label>
                <select name="emp" id="emp" class="form-select" required>
                    <option value="">공고 선택</option>
                    <c:forEach items="${list}" var="employ">
                        <option value="${employ.employNo}" data-code="${employ.employNo}">${employ.employTitle}</option>
                    </c:forEach>
                </select>
                <input type="hidden" id="selectedEmployCode" name="employNo" value="">
            </div>
        </div>

        <div class="col-6">
            <div class="form-group mb-3">
                <label for="filedNo" class="form-label">모집 분야:</label>
                <select name="filedNo" id="filedNo" class="form-select" required>
                    <option value="">분야 선택</option>
                    <c:forEach items="${list}" var="employ">
                        <c:forEach items="${employ.fieldList}" var="field">
                            <option value="${field.filedNo}" class="${employ.employNo}">${field.filedNm}</option>
                        </c:forEach>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
</div>

<hr>
<div class="container mt-4">
    <div class="row">
        <c:forEach items="${vchatList}" var="vchat">
            <div class="col-md-4 mb-4 vchat-card" data-field="${vchat.filedNo}">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <input type="hidden" id="vchatNo" value="${vchat.vchatNo}">
                        <input type="hidden" id="employNo" value="${vchat.fieldVO.employNo}">
                        <input type="hidden" id="fieldNo" value="${vchat.filedNo}">
                        <h5 class="card-title">${vchat.vchatTitle}</h5>
                        <p class="card-text">화상 채팅에 참여하려면 아래 버튼을 클릭하세요.</p>
                        <input type="hidden" id="apiUserId" value="<security:authentication property='principal.realUser.userId'/>">
                        <input type="hidden" id="username" value="<security:authentication property='principal.realUser.memberVO.memNm'/>">
                        <input type="hidden" id="roomId" value="${vchat.vchatUrl}">
                        <button id="vchatBtn" class="btn btn-primary">채팅방 입장</button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>


<script src="${pageContext.request.contextPath}/resources/js/vchat/vchatMemList.js"></script>