document.addEventListener('DOMContentLoaded', function () {
	const contextPath = document.body.dataset.url || "";
    console.log('DOMContentLoaded event fired');
    initializeEventSource();
	
    // 알림 아이콘 클릭 시 알림 창 토글 및 DB에서 알림 리스트 조회
    const bellIcons = document.querySelectorAll('#notificationButton');
    let activeNotificationPanel = null; // 현재 열려 있는 알림 창을 추적

    bellIcons.forEach(bellIcon => {
        bellIcon.addEventListener('click', function (event) {
            event.preventDefault(); // 기본 동작 방지
            const panel = bellIcon.closest('nav').querySelector('.notification-panel');

            // 알림 패널 토글 (열기/닫기)
            const isPanelVisible = panel.style.display === 'block';
            toggleNotificationPanel(isPanelVisible, panel);

            if (!isPanelVisible) {
                activeNotificationPanel = panel; // 활성화된 알림 창 설정
                resetNotificationBadge(); // 알림 창 열 때 배지 초기화
                fetchNotifications(); // DB에서 알림 리스트 조회
            } else {
                activeNotificationPanel = null; // 닫힌 후 비활성화
            }
        });
    });

    // 스크롤 감지하여 알림 창 닫기
    window.addEventListener('scroll', () => {
        if (activeNotificationPanel && activeNotificationPanel.style.display === 'block') {
            toggleNotificationPanel(true, activeNotificationPanel); // 알림 창 닫기
            activeNotificationPanel = null; // 닫힌 후 비활성화
        }
    });
});

function initializeEventSource() {
    const baseUrl = document.body.dataset.url;

    let eventSource = createEventSource();

    function createEventSource() {
        const source = new EventSource(`${baseUrl}/notifications/stream`);

        // 알림 수신 시 처리
        source.addEventListener('notification', function (event) {
            console.log('Received notification:', event.data);
            const data = JSON.parse(event.data);
            const JSONdata = JSON.parse(data);
            const { content, url, time } = JSONdata;

            // 알림 토스트로 표시
            showToastNotification(content, `${baseUrl}${url}`, time);

            incrementNotificationBadge(); // 알림 배지 증가
            showNotificationBadge();
        });

        // SSE 연결 에러 시 재시도
        source.onerror = function () {
            console.error('알림 서버와의 연결이 끊어졌습니다. 재연결을 시도합니다.');
            source.close();
            setTimeout(() => eventSource = createEventSource(), 3000);
        };

        return source;
    }
}

// 토스트 알림 표시 함수
function showToastNotification(content, url, time) {
	const baseUrl = document.body.dataset.url;
    const toastContainer = document.querySelector('.toast-container');
    const newToast = document.createElement('div');

    newToast.className = 'toast show';
    newToast.setAttribute('role', 'alert');
    newToast.setAttribute('aria-live', 'assertive');
    newToast.setAttribute('aria-atomic', 'true');
    newToast.innerHTML = `
        <div class="toast-header">
            <strong class="me-auto">알림</strong>
            <small>${new Date(time).toLocaleString()}</small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">
            <a href="${url}" class="text-decoration-none" style="color: inherit;">${content}</a>
        </div>
    `;

    toastContainer.appendChild(newToast);

    // 5초 후 토스트 자동 제거
    setTimeout(() => {
        newToast.remove();
    }, 5000);
}

function fetchNotifications() {
    const baseUrl = document.body.dataset.url;
    fetch(`${baseUrl}/notifications/list`)
        .then(response => response.json())
        .then(data => {
            console.log('Fetched notifications:', data);

            // 모든 notificationList 요소 선택 (원본과 복제본 포함)
            const notificationLists = document.querySelectorAll('#notificationList');

            // 모든 "알림 더보기" 버튼 선택 (원본과 복제본 포함)
            const moreNotificationsBtns = document.querySelectorAll('#moreNotificationsBtn');

            // 각 리스트 초기화
            notificationLists.forEach(list => (list.innerHTML = ''));

            // 최대 5개의 알림만 표시
            const limitedNotifications = data.slice(0, 5);

            limitedNotifications.forEach(notification => {
			    const { notificCont, notificUrl, notificDt } = notification;
			    const newNotification = document.createElement('li');
			    newNotification.className = 'list-group-item border-0 p-0 mb-2';
			    const timeAgo = formatTimeAgo(new Date(notificDt)); // 시간 차이 포맷팅
			
			    newNotification.innerHTML = `
			        <a href="${baseUrl}${notificUrl}" class="text-decoration-none d-flex align-items-center p-3 border rounded shadow-sm" style="color: inherit;">
			            <div class="flex-grow-1">
			                <div class="fw-bold text-primary">${notificCont}</div>
			                <small class="text-muted">${timeAgo}</small>
			            </div>
			            <i class="bi bi-chevron-right"></i>
			        </a>
			    `;
			
			    // 각 리스트에 알림 항목 추가
			    notificationLists.forEach(list => list.appendChild(newNotification.cloneNode(true)));
			});

            // 알림이 5개를 초과할 경우 "알림 더보기" 버튼 표시
            if (data.length > 5) {
                moreNotificationsBtns.forEach(btn => {
                    btn.classList.remove('d-none');
                    btn.onclick = () => showAllNotifications(data, baseUrl);
                });
            } else {
                moreNotificationsBtns.forEach(btn => btn.classList.add('d-none'));
            }
        })
        .catch(error => {
            console.error('Failed to fetch notifications:', error);
        });
}



// 전체 알림을 모달에 표시하는 함수
function showAllNotifications(data, baseUrl) {
    const allNotificationsList = document.querySelector('#allNotificationsList');
    allNotificationsList.innerHTML = ''; // 기존 알림 리스트 초기화

    data.forEach(notification => {
        const { notificCont, notificUrl, notificDt } = notification;
        const newNotification = document.createElement('li');
	    newNotification.className = 'list-group-item border-0 p-0 mb-2';
	    const timeAgo = formatTimeAgo(new Date(notificDt)); // 시간 차이 포맷팅
	
	    newNotification.innerHTML = `
	        <a href="${baseUrl}${notificUrl}" class="text-decoration-none d-flex align-items-center p-3 border rounded shadow-sm" style="color: inherit;">
	            <div class="flex-grow-1">
	                <div class="fw-bold text-primary">${notificCont}</div>
	                <small class="text-muted">${timeAgo}</small>
	            </div>
	            <i class="bi bi-chevron-right"></i>
	        </a>
	    `;
        allNotificationsList.appendChild(newNotification);
    });

    // 모달 창 열기
    const modal = new bootstrap.Modal(document.getElementById('notificationsModal'));
	modal.show();
}


// 알림 배지 숫자로 표시
let notificationCount = 0;

function incrementNotificationBadge() {
    notificationCount += 1;
    updateNotificationBadge();
}

function resetNotificationBadge() {
    notificationCount = 0;
    updateNotificationBadge();
}

function updateNotificationBadge() {
    const badge = document.querySelector('#notificationBadge');
    if (badge) {
        if (notificationCount > 0) {
            badge.style.display = 'inline-block';
            badge.textContent = notificationCount; // 배지에 숫자 표시
        } else {
            badge.style.display = 'none';
        }
    }
}

// 알림 배지 표시 함수
function showNotificationBadge() {
    updateNotificationBadge();
}

// 알림 창 토글 함수
function toggleNotificationPanel(forceOpen = false, panel = null) {
    if (!panel) return;

    if (forceOpen) {
        panel.style.display = 'none'; // 알림 창 닫기
        return;
    }

    if (panel.style.display === 'none' || panel.style.display === '') {
        panel.style.display = 'block'; // 알림 창 열기
        resetNotificationBadge(); // 알림 창이 열리면 배지 초기화
    } else {
        panel.style.display = 'none'; // 알림 창 닫기
    }
}

// 상대적인 시간 포맷팅 함수
function formatTimeAgo(date) {
    const now = new Date();
    const diff = now - date; // 시간 차이 (밀리초 단위)
    const diffMinutes = Math.floor(diff / (1000 * 60)); // 분 단위
    const diffHours = Math.floor(diff / (1000 * 60 * 60)); // 시간 단위
    const diffDays = Math.floor(diff / (1000 * 60 * 60 * 24)); // 일 단위

    if (diffMinutes < 1) {
        return '방금 전';
    } else if (diffMinutes < 60) {
        return `${diffMinutes}분 전`;
    } else if (diffHours < 24) {
        return `${diffHours}시간 전`;
    } else if (diffDays < 7) {
        return `${diffDays}일 전`;
    } else {
        return date.toLocaleDateString(); // 7일 이상이면 날짜로 표시
    }

}
