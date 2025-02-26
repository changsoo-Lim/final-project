$(document).ready(function() {
    // 댓글 수정 버튼 클릭 시 폼을 보여줌
    $(".edit-btn").click(function() {
        var commentDate = $(this).data("comment-date");
        var boardNo = $(this).data("board-no");

        // 수정 폼을 화면에 보이게 함
        $("#comment-edit-form").show();

        // commentDate를 String 포맷으로 변환 (yyyy-MM-dd HH:mm:ss 형식으로)
        var formattedDate = new Date(commentDate).toISOString().slice(0, 19).replace("T", " "); // "2024-12-17 15:30:00" 형식

        // 수정 폼에 기존 댓글 내용 채우기 (AJAX로 기존 댓글 내용 가져올 수도 있음)
        $.ajax({
            url: '/main/board/' + boardNo + '/comment/' + formattedDate + '/edit',
            method: 'GET',
            success: function(response) {
                // 서버에서 받은 댓글 데이터를 폼에 채워넣기
                $("#commentCont").val(response.commentCont);
            }
        });
    });

    // 댓글 수정 후 저장 버튼 클릭
    $("#save-comment-btn").click(function() {
        var updatedCommentCont = $("#commentCont").val();
        var boardNo = $(".edit-btn").data("board-no"); // 수정 버튼의 boardNo 사용
        var commentDate = $(".edit-btn").data("comment-date"); // 수정 버튼의 commentDate 사용

        // commentDate를 String 포맷으로 변환 (yyyy-MM-dd HH:mm:ss 형식으로)
        var formattedDate = new Date(commentDate).toISOString().slice(0, 19).replace("T", " "); // "2024-12-17 15:30:00" 형식

        // 수정된 내용을 서버로 전송 (AJAX)
        $.ajax({
            url: '/main/board/' + boardNo + '/comment/' + formattedDate + '/edit',
            method: 'POST',
            data: {
                commentCont: updatedCommentCont,
            },
            success: function(response) {
                // 수정이 성공하면 댓글 내용을 업데이트
                if (response.status === 'success') {
                    // 수정된 댓글을 페이지에 반영
                    $("td[data-comment-date='" + formattedDate + "']").text(updatedCommentCont);
                    alert('댓글이 수정되었습니다.');
                    // 수정 폼 숨기기
                    $("#comment-edit-form").hide();
                } else {
                    alert('댓글 수정 실패');
                }
            },
            error: function(xhr, status, error) {
                console.log("Error: ", error);  // 오류 내용 확인
                alert('댓글 수정에 실패했습니다.');
            }
        });
    });
});