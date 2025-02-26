
// 모달이 열릴 때마다 신고 대상자 정보를 설정하는 함수
$('#reportModal').on('show.bs.modal', function(event) {
    var button = $(event.relatedTarget); // 신고 버튼
    var targetType = button.data('report-target'); // "board" 또는 "comment"
    var targetId = button.data('report-id'); // 신고할 대상의 memId

    // 신고 대상자 정보 설정
    var targetText = '';
    if (targetType === 'board') {
        // 게시글 신고일 경우
        targetText = '게시글 작성자: ' + targetId;
    } else if (targetType === 'comment') {
        // 댓글 신고일 경우
        targetText = '댓글 작성자: ' + targetId;
    }

    // 모달 내 신고대상자 표시
    $('#reportTarget').text(targetText);
    
    // 신고 form의 hidden input에 대상 정보를 저장
    $('input[name="reportedMemId"]').val(targetId);
});
// 신고 제출 시
function reportSubmit() {
    const reportReason = document.getElementById('reportReason').value;
    const reportCont = document.getElementById('reportCont').value;

    if (!reportReason || !reportCont) {
        alert("신고 사유와 신고 내용을 모두 입력해주세요.");
        return false; // 폼 제출 막기
    }

    return true; // 폼 제출 허용
}

function reportSubmit() {
    const reportReason = document.getElementById('reportReason').value;
    const reportCont = document.getElementById('reportCont').value;

    if (!reportReason || !reportCont) {
        alert("신고 사유와 신고 내용을 모두 입력해주세요.");
        return false; // 폼 제출 막기
    }

    // 폼 제출 처리
    const form = document.querySelector('#reportModal form');
    const formData = new FormData(form);
    const actionUrl = form.action; // 폼의 action URL 가져오기

    // 신고 데이터 AJAX 요청
    fetch(actionUrl, {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            // 신고가 접수된 후 알림
            Swal.fire({
                title: '신고 접수 완료!',
                text: '신고가 접수되었습니다.',
                icon: 'success',
                confirmButtonColor: '#3085d6',
                confirmButtonText: '확인'
            }).then(() => {
                // 필요 시, 모달 닫기
                const modal = bootstrap.Modal.getInstance(document.getElementById('reportModal'));
                modal.hide(); // 모달 닫기
            });
        } else {
            Swal.fire(
                '오류 발생!',
                '신고 제출 중 문제가 발생했습니다.',
                'error'
            );
        }
    })
    .catch(error => {
        Swal.fire(
            '오류 발생!',
            '서버와 통신 중 문제가 발생했습니다.',
            'error'
        );
    });

    return false; // 폼의 기본 제출 방지
}

document.getElementById("deleteButton").addEventListener("click", async function (event) {
    event.preventDefault(); // 기본 폼 제출 동작 방지

    const resultAlert = await Swal.fire({
        title: '게시글을 삭제하시겠습니까?',
        text: "삭제 후에는 복구할 수 없습니다!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소'
    });

    if (resultAlert.isConfirmed) {
        try {
            const form = document.getElementById("deleteForm");
            const formData = new FormData(form);
            const baseUrl = document.body.dataset.url; // 데이터 속성에서 baseUrl 가져오기

            // AJAX 요청
            const response = await fetch(form.action, {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                await Swal.fire(
                    '삭제 완료!',
                    '게시글이 삭제되었습니다.',
                    'success'
                );

                // 성공적으로 삭제된 후 페이지 이동
                window.location.href = `${baseUrl}/main/board`; // baseUrl 포함
            } else {
                Swal.fire(
                    '오류 발생!',
                    '게시글 삭제 중 문제가 발생했습니다.',
                    'error'
                );
            }
        } catch (error) {
            Swal.fire(
                '오류 발생!',
                '서버와 통신 중 문제가 발생했습니다.',
                'error'
            );
        }
    }
});



