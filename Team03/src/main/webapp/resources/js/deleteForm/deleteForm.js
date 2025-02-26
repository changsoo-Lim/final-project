/**
 * 
 */

$(()=>{
	$('#deleteUserForm').on('submit', function(e){
		e.preventDefault();
		let $userCd = $('#userCd').val();
		const baseUrl = document.body.dataset.url;
		console.log($userCd);
		let $deleteCheck = $('#deleteCheck');
		const url = $('#deleteUserForm').attr("action");
		
		if (!$deleteCheck.is(":checked")) {
			return;
		}
		
		axios.delete(url,{
			data : {userCd : $userCd} 
		})
		.then(function(response){
			console.log(response);
			if(response.data == 'ok'){
				console.log(response);
				Swal.fire({
				  icon: "success",
				  title: "회원탈퇴",
				  text: "회원탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.",
				  timer: 3000
				}).then(() => {
				    window.location.href = `${baseUrl}/logout`;
				});
			}
		})
		.catch(error => {
		    if (error.response) {
		        console.error('Status:', error.response.status);
		    } else if (error.request) {
		        console.error('Error request:', error.request);
		    }
		});
	});
});