        // ClassicEditor 초기화
        ClassicEditor
            .create(document.querySelector('#editor'), {
                toolbar: ['undo', 'redo', '|', 'bold', 'italic', '|', 'fontSize', 'fontFamily', 'fontColor', 'fontBackgroundColor']
            })
            .then(editor => {
                console.log('CKEditor5 초기화 성공!', editor);
            })
            .catch(error => {
                console.error('에디터 초기화 오류:', error);
            });