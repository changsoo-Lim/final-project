<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/status/statusView.css" />
<h4>🔔 알림 수신 여부 설정</h4>
<hr/>

<div class="toggle-container">
    <div>
        <strong>광고성 SMS 수신 여부</strong>
        <small class="text-danger">SMS 수신을 거부하면 채용 정보 및 이벤트 혜택을 놓칠 수 있습니다.</small>
    </div>
    <div>
        <input type="checkbox" id="statusSms" onchange="toggleCheckbox(this)" ${status.statusSms == 'Y' ? 'checked' : ''}>
        <label for="statusSms" class="custom-toggle">🔔 ON</label>
    </div>
</div>
<hr/>

<div class="toggle-container">
    <div>
        <strong>Email 수신 여부</strong>
        <small class="text-danger">Email 수신을 거부하면 지원 관련 안내를 받지 못할 수 있습니다.</small>
    </div>
    <div>
        <input type="checkbox" id="statusEmail" onchange="toggleCheckbox(this)" ${status.statusEmail == 'Y' ? 'checked' : ''}>
        <label for="statusEmail" class="custom-toggle">🔔 ON</label>
    </div>
</div>
<hr/>

<div class="toggle-container">
    <div>
        <strong>추천 공고 수신 여부</strong>
        <small class="text-danger">추천 공고 수신을 거부하면 맞춤형 채용 공고를 받을 수 없습니다.</small>
    </div>
    <div>
        <input type="checkbox" id="statusRec" onchange="toggleCheckbox(this)" ${status.statusRec == 'Y' ? 'checked' : ''}>
        <label for="statusRec" class="custom-toggle">🔔 ON</label>
    </div>
</div>
<hr/>

<div class="toggle-container">
    <div>
        <strong>포지션 제안 수신 여부</strong>  
        <small class="text-danger">포지션 제안 수신을 거부하면 기업의 제안을 놓칠 수 있습니다.</small>  
    </div>
    <div>
        <input type="checkbox" id="statusPosition" onchange="toggleCheckbox(this)" ${status.statusPosition == 'Y' ? 'checked' : ''}>
        <label for="statusPosition" class="custom-toggle">🔔 ON</label>
    </div>
</div>
<hr/>

<div class="toggle-container">
    <div>
        <strong>게시판 댓글 수신 여부</strong>  
        <small class="text-danger">댓글 수신을 거부하면 중요한 피드백을 확인하지 못할 수 있습니다.</small>  
    </div>
    <div>
        <input type="checkbox" id="statusComment" onchange="toggleCheckbox(this)" ${status.statusComment == 'Y' ? 'checked' : ''}>
        <label for="statusComment" class="custom-toggle">🔔 ON</label>
    </div>
</div>
<hr/>

<div class="toggle-container">
    <div>
        <strong>이력서 열람 수신 여부</strong>  
        <small class="text-danger">이력서 열람 수신을 거부하면 기업이 회원님의 이력서를 확인할 수 없습니다.</small>  
    </div>
    <div>
        <input type="checkbox" id="statusResume" onchange="toggleCheckbox(this)" ${status.statusResume == 'Y' ? 'checked' : ''}>
        <label for="statusResume" class="custom-toggle">🔔 ON</label>
    </div>
</div>
<hr/>

<script src="${pageContext.request.contextPath}/resources/js/status/statusView.js"></script>
