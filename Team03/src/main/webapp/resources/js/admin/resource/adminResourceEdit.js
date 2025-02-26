/**
 * 
 */
document.addEventListener("DOMContentLoaded", () => {
			const baseUrl = document.body.dataset.url;

	document.querySelectorAll("[data-atch-file-no][data-file-sn]").forEach(el => {
		el.addEventListener("click", async (e) => {
			e.preventDefault();
			let atchFileNo = el.dataset.atchFileNo;
			let fileSn = el.dataset.fileSn;
			try {
				let resp = await fetch(`${baseUrl}/resource/123/file/${atchFileNo}/${fileSn}`, {
					method: "DELETE",
					headers: {
						"accept": "application/json"
					}
				});

				if (resp.ok) {
					let obj = await resp.json();
					if (obj.success) {
						el.parentElement.remove();
					}
				} else {
					console.error('Failed to delete file');
				}
			} catch (error) {
				console.error('Error:', error);
			}
		});
	});
	// 글자 수 업데이트 이벤트 추가
    const boardContElement = document.getElementById('resourceCont');
    if (boardContElement) {
        boardContElement.addEventListener('input', updateCharCount);  // 'input' 이벤트를 사용해 글자 수 업데이트
    }
});

function updateCharCount() {
        var content = document.getElementById('resourceCont').value;
        var charCount = content.length; // 글자 수를 구함
        document.getElementById('charCount').textContent = charCount + '자'; // 글자 수를 표시
    }


function updateTitleInput() {
         var selectBox = document.getElementById('resourceType');
        var selectedValue = selectBox.options[selectBox.selectedIndex].value; // value 속성만 가져옴
        
        if (selectedValue !== "") {
            document.getElementById('resourceTitle').value = selectBox.options[selectBox.selectedIndex].text;
        } else {
            document.getElementById('resourceTitle').value = ""; // "선택하세요"가 선택되지 않을 때 입력란 초기화
        }
    }
