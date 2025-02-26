

document.addEventListener("DOMContentLoaded", () => {
	const benefitItems = document.querySelectorAll('.bftItem');
	const bftNo = document.getElementById("bftNo");
	const Title = document.getElementById('bftTitle');
	const Content = document.getElementById('bftcont');
	const CompId = document.getElementById('compId');
	const delbtn = document.getElementById('deletebtn');


	/*const staticBackdrop = document.getElementById('staticBackdrop');*/

	/*	// 모달이 닫힐 때 실행될 이벤트 등록
		staticBackdrop.addEventListener('hidden.bs.modal', function() {
			// 폼 요소 초기화
			const form = staticBackdrop.querySelector('form');
			if (form) {
				form.reset(); // 폼 내부의 모든 입력 값을 초기화
			}
		});
	*/



	delbtn.addEventListener("click", async () => {

		const data = {
			cmpbftNo : bftNo.value,
			compId : compId.value
		};
		
		console.log(bftNo.value, compId.value);
		
		console.log("딜리트 실행 중");

		const response = await fetch('delete', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(data) // JSON 형식으로 변환 후 전송
		});

		// 응답 확인
		if (response.ok) {
			const result = await response.text(); // 서버로부터의 응답 텍스트
			alert('삭제되었습니다.');
			console.log('서버 응답:', result);
			location.reload(); // 페이지 새로고침
		} else {
			const error = await response.json(); // 서버 오류 메시지 확인
			alert(`삭제 실패: ${error.message}`);
		}

	})


	benefitItems.forEach(item => {
		item.addEventListener('click', async () => {
			const cmpbftNo = item.getAttribute('data-cmpbft-no');

			let bftVO = await fetch(`${cmpbftNo}/detail`, {
				method: "get"
				, headers: {
					'Content-Type': 'application/json'
				}
			});

			if (!bftVO.ok) {
				console.log(bftVO.statusText);
			}
			let json = await bftVO.json();
			let benefit = json.benefit;
			bftNo.value = benefit.cmpbftNo;
			Title.value = benefit.cmpbftTile;
			Content.value = benefit.cmpbftCont;
			CompId.value = benefit.compId;

			/*myModal.show();*/

		});
	});
})







