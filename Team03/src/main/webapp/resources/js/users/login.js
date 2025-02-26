/**
 * 
 */

$(document).ready(function(){
	const baseUrl = document.body.dataset.url;
	let code = "123456";
	let userType = "member";
	let userId;
	
	// 인증번호 받기 버튼 클릭
	$('.getforgetCertification').on('click', async function(){
		const $form = $(this).closest('form');
		const $userHp = $form.find('.forgetPhoneInput').val();
		const $certDiv = $form.find('.certDiv');
		console.log("phoneInput : "+$userHp);
		console.log("userType : "+userType);
		
		//let $userHp = $phoneInput.val();
		let phoneCheckUrl = `${baseUrl}/${userType}/phone/${$userHp}`;
		let smsUrl = `${baseUrl}/sms/send-one/${$userHp}`;
		// if(validateHp($userHp)){
			// 휴대폰번호 매칭 회원 확인
			await axios.get(phoneCheckUrl)
			.then(response => {
				console.log(response.data);
				if(response.data == null || response.data == "fail"){
					sweatAlert("error", "등록되지 않은 휴대폰번호입니다. 다시 입력해주세요.");
					$certDiv.hide();
					return;					
				} else {
					userId = response.data;			
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
			$certDiv.css("display", "flex").show();
			toastAll("success", "문자가 전송되었습니다.");
			// 문자 발송
			/*await axios.post(smsUrl)
			.then(response => {
				code = response.data;
				console.log(code);
				$certDiv.show();
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
		
	});
	
	// 휴대폰 문자 인증번호 발송 후 인증하기 버튼 클릭
	 $('.forgetCertificationBtn').on('click', function(){
		const $form = $(this).closest('form');
		const $forgetCertInput = $form.find('.forgetCertInput').val();
		const $tab = $(this).closest('#home-tab-pane, #profile-tab-pane, #home-tab-pane1, #profile-tab-pane1');	
		const $searchId = $tab.find('.searchId');
		const $rowContent = $tab.find('.rowContent');
		const $successCertDiv = $tab.find('.successCertDiv');
		const dataPass = $(this).data('pass');
		console.log("dataPass : " + dataPass);
		if(code == $forgetCertInput){
			if(dataPass != 'userPass'){
				$searchId.val(userId);				
			}
			
			// 인증완료
			$rowContent.hide();
			$successCertDiv.css("display", "flex").show();
			
		} else {
			toastAll('error', '휴대폰 인증이 실패하셨습니다.');
		}
	});
	
	// 비밀번호 재설정 
	$('#changeComPass, #changeMemPass').on('click', async function(){
		const $form = $(this).closest('form');
		const $modal = $(this).closest('.modal');
		let formData = new FormData($form[0]);
		formData.append('userId', userId);
		for (var pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }

		await axios.put(`${baseUrl}/changePass`, formData, {
			headers : {
				'Content-Type' : 'multipart/form-data'
			}
		}).then(response => {
			console.log(response);
			if(response.data == 'true'){
				sweatAlert("success", "비밀번호 재설정에 성공했습니다.<br/>로그인을 진행해주세요.");
				$modal.modal('hide');
			} else {
				sweatAlert("error", "비밀번호 재설정에 실패했습니다.<br/>다시 설정해주세요.");
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
		
	});
	
	// 탭 클릭 시 초기화
	$(".idPassNav").on('click', function(){
		/*$('#exampleModalCenteredScrollable')
		$(this).parent().parent().find()
		console.log("실행");
		const $tab = $(this).closest('#home-tab-pane, #profile-tab-pane, #home-tab-pane1, #profile-tab-pane1');
		console.log($tab.html());
		const $memberDiv = $tab.find('.memberDiv');
		const $companyDiv = $tab.find('.CompanyDiv');
		const $successCertDiv = $tab.find('.successCertDiv');
		const $idForm = $tab.find('.idForm');
		console.log($idForm.html());
		
		userType = $(this).data('user');
		
		if(userType=='member'){
			console.log("member")
			$memberDiv.css("display", "flex").show();
			$successCertDiv.hide();
			$idForm.trigger('reset');
		}*/
		
		userType = $(this).data('user');
		var $modalRoot = $(this).closest('#exampleModalCenteredScrollable, #exampleModalCenteredScrollable2'); 

	    // 여러 개의 요소를 각각 선택
	    var $memberDivs = $modalRoot.find('.memberDiv');
	    var $companyDivs = $modalRoot.find('.CompanyDiv');
	    var $successCertDivs = $modalRoot.find('.successCertDiv');
	    var $idForms = $modalRoot.find('.idForm');
	    var $certDivs = $modalRoot.find('.certDiv');
	    var $changePassForms = $modalRoot.find('.changePassForm');
	
		if(userType=='member'){
			$memberDivs.each(function () {
				$(this).css("display", "flex").show();
		    });	
		} else if (userType='company'){
		    $companyDivs.each(function () {
		        $(this).css("display", "flex").show();
		    });			
		}
	
	    $successCertDivs.each(function () {
	        $(this).hide();
	    });
	
	    $idForms.each(function () {
	        $(this).trigger('reset');
	    });
	
	    $certDivs.each(function () {
	        $(this).hide();
	    });

		$changePassForms.each(function () {
	        $(this).trigger('reset');
	    });
		
	});
	
	// 아이디 찾기 및 비밀번호 재설정 버튼 클릭 시 아이디 찾기 / 비밀번호 재설정 화면, 입력값, 탭 초기화
	$('#exampleModalCenteredScrollable, #exampleModalCenteredScrollable2').on('show.bs.modal', function(){
		let $modalRoot = $(this);
		$modalRoot.find('.idPassNav').each(function (index, element) {
	        if ($(element).hasClass('active')) {
	            userType = $(this).data('user');
	        }
	    });
	    let $memberDivs = $modalRoot.find('.memberDiv');
	    let $companyDivs = $modalRoot.find('.CompanyDiv');
	    let $successCertDivs = $modalRoot.find('.successCertDiv');
	    let $idForms = $modalRoot.find('.idForm');
	    let $certDivs = $modalRoot.find('.certDiv');
		if(userType=='member'){
			$memberDivs.each(function () {
				$(this).css("display", "flex").show();
		    });	
		} else if (userType='company'){
		    $companyDivs.each(function () {
		        $(this).css("display", "flex").show();
		    });			
		}
	    $successCertDivs.each(function () {
	        $(this).hide();
	    });
	    $idForms.each(function () {
	        $(this).trigger('reset');
	    });
	    $certDivs.each(function () {
	        $(this).hide();
	    });
	});
	
});
