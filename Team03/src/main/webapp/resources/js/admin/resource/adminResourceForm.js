/**
 * 
 */
 function updateTitleInput() {
         var selectBox = document.getElementById('resourceType');
        var selectedValue = selectBox.options[selectBox.selectedIndex].value; // value 속성만 가져옴
        
        if (selectedValue !== "") {
            document.getElementById('resourceTitle').value = selectBox.options[selectBox.selectedIndex].text;
        } else {
            document.getElementById('resourceTitle').value = ""; // "선택하세요"가 선택되지 않을 때 입력란 초기화
        }
    }

