$(async () => {
    // CKEditor 초기화
    const editor = await ClassicEditor
        .create(document.querySelector('#noticeCont')) // CKEditor 대상 지정
        .catch(error => {
            console.error(error);
        });

    // "내용 자동 입력" 버튼 클릭 시 이벤트 핸들러
    $('#autoFillBtn').on('click', function () {
        if (editor) {
            // 제목 자동 입력
            $('#title').val('문의합니다.');

            // 내용 자동 입력
            const defaultContent = `
                <p>안녕하세요,</p>
                <p>회원 프로필 수정과 이력서 업로드와 관련하여 몇 가지 문의드립니다.</p>
                <p>1. 회원 정보 수정: 기본 정보(이름, 연락처 등)를 수정하려면 어디에서 해야 하나요?<br>
                   2. 이력서 업로드: 파일 업로드 시 지원되는 파일 형식과 용량 제한은 어떻게 되나요?<br>
                   3. 기업이 보는 정보: 업로드된 이력서와 프로필 중 기업에서 확인 가능한 정보는 무엇인가요?</p>
                <p>추가로, 프로필 작성 시 추천 사항이나 주의사항이 있다면 안내 부탁드립니다.</p>
                <p>감사합니다.</p>
            `;
            editor.setData(defaultContent); // CKEditor에 내용 채우기
        }
    });

    // 폼 제출 시, CKEditor 데이터를 <textarea>에 반영
    $('form').on('submit', function () {
        if (editor) {
            // CKEditor에서 작성된 내용을 가져와서 <textarea>에 반영
            const editorData = editor.getData();
            $('#noticeCont').val(editorData); // <textarea>에 CKEditor 데이터 동기화
        }
    });
});
