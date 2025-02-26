/**
 * 
 */

$(document).ready(function(){
	const $form = $('#infoForm');
	const baseUrl = document.body.dataset.url;
	let code = "123456";
	// 수정버튼 클릭 시 수정
	$('#infoSubmit').on('click', function(){
		let formData = new FormData($form[0]);
		
		// formData 값 확인
		/*for (var pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }*/
		axios.put(`${baseUrl}/member/info/updateInfo`, formData)
		.then(function (response){
			if(response.status == 200){
				console.log(response);
				toastAll("success", "회원정보가 수정되었습니다.");
			}
		}).catch(error => {
		    if (error.response) {
		        // 서버가 응답했지만 상태 코드는 2xx가 아님
		        console.error('Status:', error.response.status);
				toastAll("error", "회원정보가 수정되지 않았습니다. <br/> 다시 수정해주세요.");
		    } else if (error.request) {
		        // 요청이 전송되었지만 응답이 없음
		        console.error('Error request:', error.request);
				toastAll("error", "회원정보가 수정되지 않았습니다. <br/> 다시 수정해주세요.");
		    }
		});
	});
		
	// 휴대폰번호 인증 모달 열기
	$('.memHpInput').on('focus', function(){
		$(this).blur();
		$('#phoneForm')[0].reset();
		$('.certDivPhone').hide();
		$('#memInfoPhone').modal('show');
	});
	
	// memHp label 클릭 시 포커스 이벤트를 무시
	$('label[for="memHp"]').on('click', function(e) {
	    e.preventDefault();
	});
	
	// 휴대폰 인증번호 받기
	$('.getforgetCertificationPhone').on('click', async function(){
		const $memHp = $('.forgetPhoneInput').val();
		console.log("memHp : " + $memHp);
		let result = false;
		
		const $certDiv = $('.certDivPhone');
		
		let smsUrl = `${baseUrl}/sms/send-one/${$memHp}`;
		let phoneCheckUrl = `${baseUrl}/member/info/phoneCheck/${$memHp}`;
		
		await axios.get(phoneCheckUrl)
		.then(response => {
			console.log(response.data);
			if(response.data.result == 'fail'){
				sweatAlert("error", "이미 등록된 휴대폰번호 입니다. <br/> 다시 입력해주세요.");
				return;					
			} else {
				$certDiv.css("display", "flex").show();
				result = true;				
			}
			
		}).catch(error => {
		    if (error.response) {
		        // 서버가 응답했지만 상태 코드는 2xx가 아님
		        console.error('Status:', error.response.status);
		    } else if (error.request) {
		        // 요청이 전송되었지만 응답이 없음
		        console.error('Error request:', error.request);
		    }
		});
		
		if(result){
			$certDiv.css("display", "flex").show();	
			toastAll("success", "문자가 전송되었습니다.");
			// 문자 발송
			/*await axios.post(smsUrl)
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
			});*/
		/*} else {
			sweatAlert("error", "휴대폰번호 형식이 아닙니다. <br/> 하이픈('-')을 제외하고 숫자 11자리를 입력해주세요.");
		}*/
		}
	});
	
	// 휴대폰 문자 인증번호 발송 후 인증하기 버튼 클릭
	 $('.forgetCertificationPhoneBtn').on('click', function(){
		const $forgetCertInput = $('.forgetCertPhoneInput').val();
		if(code == $forgetCertInput){
			// 인증완료
			$('#memHp').val($('.forgetPhoneInput').val());
			$('.certPN').replaceWith('<input type="button" class="btn btn-primary certPY" value="인증완료" />');
			$('#hpYn').val('Y');
			toastAll('success', '휴대폰 인증이 성공하였습니다.');
			$('#memInfoPhone').modal('hide');
			
		} else {
			toastAll('error', '휴대폰 인증이 실패하셨습니다.');
		}
	});
	
	
	
	// 이메일 인증 모달 열기
	$('.memEmailInput').on('focus', function(){
		$(this).blur();
		$('#emailForm')[0].reset();
		$('.certDivEmail').hide();
		$('#memInfoEmail').modal('show');
	});
	
	// memEmail label 클릭 시 포커스 이벤트를 무시
	$('label[for="memEmail"]').on('click', function(e) {
	    e.preventDefault();
	});
	
	// 이메일 인증번호 받기
	$('.getforgetCertificationEmail').on('click', async function(){
		const $memEmail = $('.forgetEmailInput').val();
		console.log("memEmail : " + $memEmail);
		let result = false;
		const $certDiv = $('.certDivEmail');
		
		let emailUrl = `${baseUrl}/email/send/${$memEmail}`;
		let emailCheckUrl = `${baseUrl}/member/info/emailCheck/${$memEmail}`;
		
		await axios.get(emailCheckUrl)
		.then(response => {
			console.log(response.data);
			if(response.data.result == 'fail'){
				sweatAlert("error", "이미 등록된 이메일 입니다. <br/> 다시 입력해주세요.");
				return;					
			} else {
				$certDiv.css("display", "flex").show();
				result = true;				
			}
			
		}).catch(error => {
		    if (error.response) {
		        // 서버가 응답했지만 상태 코드는 2xx가 아님
		        console.error('Status:', error.response.status);
		    } else if (error.request) {
		        // 요청이 전송되었지만 응답이 없음
		        console.error('Error request:', error.request);
		    }
		});
		
		if(result){
			// 이메일 발송
			await axios.post(emailUrl)
			.then(response => {
				code = response.data.code;
				console.log(code);
				$certDiv.css("display", "flex").show();
				toastAll("success", "이메일이 전송되었습니다.");
			}).catch(error => {
			    if (error.response) {
			        // 서버가 응답했지만 상태 코드는 2xx가 아님
			        console.error('Status:', error.response.status);
			    } else if (error.request) {
			        // 요청이 전송되었지만 응답이 없음
			        console.error('Error request:', error.request);
			    }
			});	
		}
	});
	
	// 이메일 인증번호 발송 후 인증하기 버튼 클릭
	 $('.forgetCertificationEmailBtn').on('click', function(){
		const $forgetCertInput = $('.forgetCertEmailInput').val();
		console.log(code);
		console.log($forgetCertInput)
		if(code == $forgetCertInput){
			// 인증완료
			$('#memEmail').val($('.forgetEmailInput').val());							
			 $('.certEN').replaceWith('<input type="button" class="btn btn-primary certEY" value="인증완료" />');
			$('#emailYn').val('Y');
			toastAll('success', '이메일 인증이 성공하였습니다.');
			$('#memInfoEmail').modal('hide');
			
			// 이메일 인증 정보 등록
			
		} else {
			toastAll('error', '이메일 인증이 실패하셨습니다.');
		}
	});
	
});

const resetCertPhone =  function () {
	
}