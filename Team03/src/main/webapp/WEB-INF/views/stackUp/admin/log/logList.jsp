<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <title>Log Viewer</title>
   

<h1>로그 목록</h1>
    <table border="1">
        <thead>
            <tr>
                <th>로그 번호</th>
                <th>회원 ID</th>
                <th>수행된 작업</th>
                <th>작업 시간</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="log" items="${logList}">
                <tr>
                    <td>${log.logNo}</td>
                    <td>${log.userId}</td>
                    <td>${log.action}</td>
                    <td>${log.actionTimestamp}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    
