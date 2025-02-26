/**
 * 
 */
$(()=>{
	const baseUrl = document.body.dataset.url;
	$('#checkReset').on('click', function(){
		window.history.back();
	});
	
	$('#passCheckForm').on('submit', function(e){
		e.preventDefault();
		let userPass = $('#userPass').val();
		const url = $('#passCheckForm').attr("action");
		
		$.ajax({
            type: "POST",
            url: `${url}`,
            contentType: "application/json", 
        	data: JSON.stringify({ userPass: userPass }),
            success: function(response) {
                if (response.result == 'true') {
                    window.location.href = `${baseUrl}/member/info/accessPage/${response.pageName}`;
                } else {
                    sweatAlert("warning", "비밀번호가 맞지 않습니다. 다시 입력해주세요.");
                }
            },
            error: function() {
                sweatAlert("error", "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
        });
	})
	
});
