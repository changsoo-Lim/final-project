/**
 * 
 */
  

$( async ()=> {
    const editor = await ClassicEditor
            .create(document.querySelector('#boardCont'))
            .catch(error => {
                console.error(error);
            });
 // "내용 자동 입력" 버튼 클릭 시 이벤트 핸들러
    $('#autoFillBtn').on('click', function () {
        if (editor) {
            // 제목 자동 입력
            $('#boardTitle').val('와 이 사이트 정말 좋아요.');

            // 내용 자동 입력
            const defaultContent = `
                <p>그쵸??</p> `;
            editor.setData(defaultContent); // CKEditor에 내용 채우기
        }
    });
    // 글자수 카운트 함수
    function updateCharCount() {
        var content = editor.getData();
        var contentLength = content.replace(/<[^>]*>/g, '').length;
        document.getElementById('charCount').textContent = contentLength + '자';
    }

    // CKEditor 데이터가 변경될 때마다 글자수 업데이트
    editor.model.document.on('change:data', updateCharCount);

	
	$('#board').on('click', function(){
		 var editorData = editor.getData();  // CKEditor 데이터 가져오기
		document.getElementById('boardCont').value = editorData;  // textarea에 CKEditor 데이터 넣기
	})
})





 // CKEditor 초기화
/*$( async ()=>{
	console.log(document.getElementById('board'));
	const editor = await ClassicEditor
		    .create(document.querySelector('#boardCont'))
		    .catch(error => {
		        console.error(error);
		    });
	console.log(document.getElementById('board'));
	
	$('#board').on('click', function(){
		 var editorData = editor.getData();  // CKEditor 데이터 가져오기
		document.getElementById('boardCont').value = editorData;  // textarea에 CKEditor 데이터 넣기
	})
})
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	/*$(document).on('submit','#board', function (){
		console.log("submit");
	    var editorData = editor.getData();  // CKEditor 데이터 가져오기
		document.getElementById('boardCont').value = editorData;  // textarea에 CKEditor 데이터 넣기
	})*/