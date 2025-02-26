/**
 * 
 */
const idRegex = /^[a-zA-Z\d]{4,20}$/;
// 패스워드 정규식 패턴
const passwordRegex = /^(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*()])[A-Za-z\d!@#$%^&*()]{8,20}$/;
// 휴대폰 번호 정규식 패턴
const hpRegex = /^(010|011)\d{7,8}$/;


// 빈값 체크 함수
const isEmpty = function(value){
    if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
        return true
    }else{
        return false
    }
};

// 휴대폰 번호 확인
/*const isValidPhoneNumber = function(phoneNumber){
    const regex = /^(010|011)\d{7,8}$/;
    return regex.test(phoneNumber);
}*/


const accountPossible = function(createIdPossible, createHpPossible, createRequiredCheck, passwordCheck){
	let allCreate = createIdPossible && createHpPossible && createRequiredCheck && passwordCheck;
	if(allCreate){
		$('#btn-submit').prop('disabled', false);
	} else {
		$('#btn-submit').prop('disabled', true);
	}
}

const validateId = function(id) {
    return idRegex.test(id);
}

const validatePassword = function(password) {
    return passwordRegex.test(password);
}

const validateHp = function(hp) {
    return hpRegex.test(hp);
}

const formatCompNum = function(compNum){
	return `${compNum.slice(0, 3)}-${compNum.slice(3, 5)}-${compNum.slice(5)}`;
}

const companyValidate =  function(compNum){
	var data = {
	    "b_no": [`${compNum}`] // 사업자번호 "xxxxxxx" 로 조회 시,
	   }; 
	$.ajax({
	  url: "https://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=cGZXwCp9A5uxF9%2Boo1Hfvp2hNyEL%2BYjAiZRc7Jw%2B%2FIMMWSnAQ0OGohkCX20mU50jNObwjESndnLSm8N5P23k9w%3D%3D",  // serviceKey 값을 xxxxxx에 입력
	  type: "POST",
	  data: JSON.stringify(data), // json 을 string으로 변환하여 전송
	  dataType: "JSON",
	  contentType: "application/json",
	  accept: "application/json",
	  success: function(result) {
	      console.log(result);
	  },
	  error: function(result) {
	      console.log(result.responseText); //responseText의 에러메세지 확인
	  }
	});
} 

$(document).ready(function(){
	const baseUrl = document.body.dataset.url;
	const $idCheck = $('#idCheck');
	const $idInput = $("#signupIDInput");
	const $btnSubmit = $('#btn-submit');
	let $certDiv = $('#certInputDiv');
	let $certNumBtn = $('#certNumBtn');
	let $certInput = $('#signupCertInput');
	let $certification = $("#certification");
	let $signupPhoneInput = $('#signupPhoneInput');
	let createIdPossible = false;
	let createHpPossible = false;
	let createRequiredCheck = false;
	let passwordCheck = false;
	let code = "123456";
	let $userHp = null;
	let $idVal;
	
	$btnSubmit.prop('disabled', true);
	// 필수체크박스는 클릭해야 가입하기 버튼 클릭 할 수 있다.
	$('.chk-submit, .cbx_chkAll').on('change', function() {
		let allChecked = $(".chk-submit").length === $(".chk-submit:checked").length;
		if(!allChecked){
			createRequiredCheck = false;
		} else {
			createRequiredCheck = true;
		}
		accountPossible(createIdPossible, createHpPossible, createRequiredCheck, passwordCheck);
	});
	
	// 아이디 확인 버튼 클릭 시 아이디 중복확인
	$idCheck.on('click', function(){
		$idVal = $idInput.val();
		let url = baseUrl+'/idCheck/'+$idVal;
		
		axios.get(url)
		.then(response => {
			if(isEmpty(response.data)){
				createIdPossible = true;
				toastAll('success', '해당 아이디는 사용가능합니다.');
			} else {
				createIdPossible = false;
				toastAll('error', '아이디가 중복입니다. <br/> 다른 아이디를 사용해주세요.');
			}
			accountPossible(createIdPossible, createHpPossible, createRequiredCheck, passwordCheck);
		});
	});
	
	// 아이디 변경하면 아이디 확인이 풀림
	$idInput.on('keyup', function() {
		createIdPossible = false;
	})
	
	// 아이디 입력 시 정규식 패턴 확인
	/*$idInput.on('keyup', function() {
		console.log($('.validateId'));
		$('.validateId').show();
		if(isEmpty($(this).val())){
			if(validateId(this.val())){
				$('.validateId').show();
			} else {
				$('.validateId').hide();
			}
		}
	})
	// 비밀번호 입력 시 정규식 패턴 확인
	$('#formSignUpPassword').on('keyup', function() {
		console.log($('.validatePassword'));
		if(isEmpty($(this).val())){
			if(validatePassword($(this).val())){
				$('.validatePassword').show();
			} else {
				$('.validatePassword').hide();
			}
		}
	})*/
	// 비밀번호 입력 시 비밀번호와 비밀번호 확인 값 체크
	$('#formSignUpConfirmPassword, #formSignUpPassword').on('keyup', function() {
	    var password = $('#formSignUpPassword').val();
	    var confirmPassword = $('#formSignUpConfirmPassword').val();
	
		if (password !== confirmPassword && confirmPassword !== "") {
	      $('#formSignUpConfirmPassword').addClass('is-invalid');
		  passwordCheck = false;
	    } else {
	      $('#formSignUpConfirmPassword').removeClass('is-invalid');
		  passwordCheck = true;
	    }
		accountPossible(createIdPossible, createHpPossible, createRequiredCheck, passwordCheck);
	});
	
	// 인증번호 받기 버튼 클릭 후 문자 메시지와 리턴(6자리 랜덤숫자 100000부터 999999까지)
	// 인증번호 확인 inputDiv Element show()
	$certification.on('click', async function(){
		$userHp = $signupPhoneInput.val();
		let userType = $(this).data('user');
		let phoneCheckUrl = `${baseUrl}/${userType}/phone/${$userHp}`;
		let smsUrl = `${baseUrl}/sms/send-one/${$userHp}`;
		if(validateHp($userHp)){
			// 휴대폰번호 중복 확인
			await axios.get(phoneCheckUrl)
			.then(async response => {
				console.log(response.data);
				if(response.data != "fail"){
					sweatAlert("error", "이미 등록된 휴대폰번호 입니다. <br/> 다시 입력해주세요.");
					return;					
				}
				
				$certDiv.css("display", "flex").show();
				toastAll("success", "문자가 전송되었습니다.");
				
				// 문자 발송
				await axios.post(smsUrl)
				.then(response => {
					code = response.data;
					console.log(code);
					$certDiv.css("display", "flex").show();
					toastAll("success", "문자가 전송되었습니다.");
				}).catch(error => {
				    if (error.response) {
				        // 서버가 응답했지만 상태 코드는 2xx가 아님
				        console.error('Status:', error.response.status);
				    } else if (error.request) {
				        // 요청이 전송되었지만 응답이 없음
				        console.error('Error request:', error.request);
				    }
				});
			}).catch(error => {
			    if (error.response) {
			        // 서버가 응답했지만 상태 코드는 2xx가 아님
			        console.error('Status:', error.response.status);
			    } else if (error.request) {
			        // 요청이 전송되었지만 응답이 없음
			        console.error('Error request:', error.request);
			    }
			});
		} else {
			sweatAlert("error", "휴대폰번호 형식이 아닙니다. <br/> 하이픈('-')을 제외하고 숫자 11자리를 입력해주세요.");
		}
	});
	
	// 휴대폰 문자 인증번호 발송 후 인증하기 버튼 클릭
	 $certNumBtn.on('click', function(){
		if(code == $certInput.val()){
			createHpPossible = true;
			$certNumBtn.prop('disabled', true);
			$certNumBtn.val("인증완료");
			$certification.prop('disabled', true);
			if($userHp != $signupPhoneInput.val() ){
				window.location.href = "/stackUp/index.do";
			}
			toastAll('success', '휴대폰 인증이 완료되었습니다.');
		} else {
			createHpPossible = false;
			toastAll('error', '휴대폰 인증이 실패하셨습니다.');
		}
		accountPossible(createIdPossible, createHpPossible, createRequiredCheck, passwordCheck);
	});
	
	// 기업 사업자등록번호 진위여부
	/*$("#compNumCheck").on('click', function(){
		//let compNum = formatCompNum($('#signupCompNumInput').val());
		let compNum = $('#signupCompNumInput').val();
		companyValidate(compNum);
	});*/
	
	$("#insertInputBtn").on('click', function(){
		$('.chk').prop('checked', true);
		$('.cbx_chkAll').prop('checked', true);
		$('#signupIDInput').val('mem001');
		$('#formSignUpPassword').val('java123!');
		$('#formSignUpConfirmPassword').val('java123!');
		$('#signupFullNameInput').val('임창수');
		$('#signupBirthDateInput').val('19930216');
		$('#signupZipCodeInput').val('34871');
		$('#signupAddr1Input').val('대전 중구 오류동 193-1');
		$('#signupAddr2Input').val('대덕인재개발원');
		$('#signupEmailInput').val('mem001@naver.com');
		$('#signupPhoneInput').val('01058103813');
	});
	
	$("#insertInputBtn2").on('click', function(){
		/*$('.chk').prop('checked', true);
		$('.cbx_chkAll').prop('checked', true);*/
		$('#signupCompNumInput').val('1248100998');
		$('#signupCompTypeInput').val('company-size-3');
		$('#signupCompIndInput').val('in02');
		$('#signupCompJobInput').val('in202');
		$('signupCompJobDetailInput').val('in20212');
		$('#signupIDInput').val('client002');
		$('#formSignUpPassword').val('java1234');
		$('#formSignUpConfirmPassword').val('java1234');
		/*$('#signupAddr2Input').val('현대자동차(주)');*/
		$('#signupEmailInput').val('email@naver.com');
		$('#signupCertInput').val('');
		$('#signupPhoneInput').val('010123450');
		
	});
});

