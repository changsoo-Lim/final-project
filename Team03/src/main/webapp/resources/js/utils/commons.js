/**
 * 
 */
// 토스트 출력
// ex) toastAll('success', '휴대폰 인증이 완료되었습니다.');
const toastAll = function(icon, html){
	const toast = Swal.mixin({
	    toast: true,
	    position: 'center',
	    showConfirmButton: false,
	    timer: 1700,
	    timerProgressBar: true,
	    didOpen: (toast) => {
	        toast.addEventListener('mouseenter', Swal.stopTimer)
	        toast.addEventListener('mouseleave', Swal.resumeTimer)
	    }
	});
	toast.fire({
	    icon: icon ,
	    html: html
	});
}

// sweatAlert
// ex) sweatAlert("error", "이미 등록된 휴대폰번호 입니다. <br/> 다시 입력해주세요.");
const sweatAlert = function(icon, html){
	Swal.fire({
	  position: "center",
	  icon: icon,
	  html: html
	});	
}

const kakaoAddress = function(){
	var themeObj = {
	   searchBgColor: "#8B3DFF",
	   queryTextColor: "#FFFFFF", //검색창 글자색
	   postcodeTextColor: "#8B3DFF", //우편번호 글자색
	   emphTextColor: "#8B3DFF", //강조 글자색
	   outlineColor: "#8B3DFF" //테두리
	};
	
	new daum.Postcode({
		theme: themeObj,
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.querySelector("#signupAddr1Input").value = extraAddr;
            } else {
                document.querySelector("#signupAddr1Input").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.querySelector('#signupZipCodeInput').value = data.zonecode;
            document.querySelector("#signupAddr1Input").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.querySelector("#signupAddr2Input").focus();
			
        }
    }).open();
}