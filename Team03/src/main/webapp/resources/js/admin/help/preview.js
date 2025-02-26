$( async ()=> {
    const editor = await ClassicEditor
            .create(document.querySelector('#noticeCont'))
            .catch(error => {
                console.error(error);
            });
   
   $('#noticeCont').on('click', function(){
       var editorData = editor.getData();  // CKEditor 데이터 가져오기
      document.getElementById('noticeCont').value = editorData;  // textarea에 CKEditor 데이터 넣기
   })
})

const faqSingleDelete = async function (e) {
	
    const result = await Swal.fire({
        title: '삭제',
        text: '정말 삭제하시겠습니까?',
        icon: 'question',
        showCancelButton: true, // 취소 버튼 표시
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        reverseButtons: true, // 버튼 순서 변경 (취소가 왼쪽으로)
    });

    if (result.isConfirmed) {
        document.getElementById('deleteForm').submit();
    }
};

document.addEventListener("DOMContentLoaded", function () {
    const previewBtn = document.getElementById("previewBtn");
    const titleInput = document.getElementById("noticeTitle");
    const contentInput = document.getElementById("noticeCont");
    const fileInput = document.getElementById("uploadFiles");

    if (previewBtn) {
        previewBtn.addEventListener("click", function () {
            // 제목과 내용을 미리보기로 설정
            const previewTitle = document.getElementById("modalPreviewTitle");
            const previewContent = document.getElementById("modalPreviewContent");
            const previewFile = document.getElementById("modalPreviewFile");

            previewTitle.textContent = titleInput.value || "제목 없음";
            previewContent.textContent = contentInput.value || "내용 없음";

            // 파일명 미리보기
            if (fileInput.files.length > 0) {
                const fileName = fileInput.files[0].name;
                previewFile.textContent = `첨부파일: ${fileName}`;
            } else {
                previewFile.textContent = "첨부파일 없음";
            }
        });
    }
});
