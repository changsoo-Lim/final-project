/**
 * 
 */

$(()=>{
	const baseUrl = document.body.dataset.url;
	$('#checkReset').on('click', function(){
		window.history.back();
	});
	
	$('#passUpdateForm').on('submit', function(e){
		e.preventDefault();
		let userPass = $('#userPass').val();
		let userPassCheck = $('#userPassCheck').val();
		const url = $('#passUpdateForm').attr("action");
		console.log(userPass);
		console.log(userPassCheck);
		if(userPass !== userPassCheck){
			sweatAlert("warning", "새로운 비밀번호와 새로운 비밀번호가 맞지 않습니다.");
			return;
		}
	
		
		$.ajax({
            type: "PUT",
            url: `${url}`,
            data: { userPass: userPass },
            success: function(response) {
				console.log(response);
                if (response == 'ok') {
					Swal.fire({
					  icon: "success",
					  title: "비밀번호가 변경되었습니다.",
					  text: "감사합니다.",
					  timer: 3000
					}).then(() => {
					    window.location.href = `${baseUrl}/member/mypage`;
					});
                } else {
                    sweatAlert("warning", "비밀번호가 맞지 않습니다. 다시 입력해주세요.");
                }
            },
            error: function() {
                sweatAlert("error", "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
        });
	});
	
	$('#userPass').on('input', function() {
		let password = $(this).val();
		let passwordCheck = $('#userPassCheck').val();
		let regexPass = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/;
		    
		if(!regexPass.test(password)) {
			$('#error-message-pass').show();
		} else {
			$('#error-message-pass').hide();
		}
		
		if(passwordCheck != "" && password != passwordCheck){
			$('#error-message-passCheck').show();
		} else {
			$('#error-message-passCheck').hide();
		}
	});
	
	$('#userPassCheck').on('input', function() {
		let password = $('#userPass').val();
		let passwordCheck = $(this).val();
		if(passwordCheck != "" && password != passwordCheck){
			$('#error-message-passCheck').show();
		} else {
			$('#error-message-passCheck').hide();
		}
	});
	
});