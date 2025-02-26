document.addEventListener("DOMContentLoaded", () => {
	const path = document.body.dataset.url; // base URL
	const compId = document.querySelector('input[data-compId]').getAttribute('data-compId');
	const modal = new bootstrap.Modal(document.getElementById('addModal')); // 모달 인스턴스 생성
	const benefitListContainer = document.getElementById('benefit-list'); // 모달 내 리스트 컨테이너
	const updateButton = document.getElementById('update-button'); // 수정 버튼
	const reviewCards = document.querySelectorAll('.review-card');
	const savebtn = document.getElementById('savebtn');
	const emailInput = document.getElementById("compEmail");
	const mobileInput = document.getElementById("compMobile");
	const phoneInput = document.getElementById("compPhone");
	const saveIntroBtn = document.getElementById("saveIntroBtn");
	const introTextarea = document.getElementById("companyIntro");

	let initialCheckedItems = []; // 초기 체크 상태
	let updatedCheckedItems = []; // 변경된 상태
	let removedItems = []; // 체크 해제된 항목

	// 카테고리 블록 클릭 이벤트
	const benefitEvent = document.querySelectorAll('.category-block.mb-4');
	
	
	
		saveIntroBtn.addEventListener("click", function() {
		// 서버에 저장 요청 (AJAX 예제)
		const updatedContent = introTextarea.value;

		$.ajax({
			url: `${path}/admincompany/updatecontent`, // 서버 업데이트 경로
			type: "PUT",
			contentType: "application/json",
			data: JSON.stringify({
				compId : compId, 
				compCont: updatedContent }),
			success: function(response) {
				if (response.success) {
					alert(response.message); // 성공 메시지
				} else {
					alert("저장 실패: " + response.message); // 실패 메시지
				}
			},
			error: function() {
				alert("서버 오류가 발생했습니다. 다시 시도해주세요.");
			},
		});
	});
	
	
	// info 수정 버튼 
	savebtn.addEventListener("click", () => {

		$.ajax({
			url: `${path}/admincompany/updateinfo`,
			type: 'PUT',
			contentType: 'application/json', // JSON 형식 명시
			data: JSON.stringify({
				compId : compId,
				compEmail: emailInput.value || null, // 값이 없으면 null로 처리
				compMobile: mobileInput.value || null,
				compPhone: phoneInput.value || null
			}),
			success: function(response) {
				if (response.success) {
					alert(response.message);
				} else {
					alert(response.message);
				}
			},
			error: function(xhr) {
				alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
			}
		});

	});


	// review 클릭 이벤트
	reviewCards.forEach(card => {
		card.addEventListener('click', () => {
			const reviewId = card.getAttribute('data-review-id');
			// 서버로 요청을 보내는 fetch 예제
			window.location.href = `${path}/review/${reviewId}`
		});
	});

	benefitEvent.forEach((element) => {
		const category = element.dataset.categorie;
		const encodedCategory = category.replace(/\//g, "_SLASH_");

		element.addEventListener("click", () => {
			// AJAX 요청 1: 복리후생 리스트 가져오기
			$.ajax({
				url: `${path}/benefit/${compId}/${encodedCategory}`,
				type: "GET",
				dataType: "json",
				success: function(bftResponse) {
					// AJAX 요청 2: 코드 리스트 가져오기
					$.ajax({
						url: `${path}/benefit/${encodedCategory}/getcode`,
						type: "GET",
						dataType: "json",
						success: function(codeResponse) {
							const bftList = bftResponse.cmp_BftVOList || [];
							const codeList = codeResponse.codeVOList || [];

							// 초기 상태 저장
							initialCheckedItems = bftList.map((item) => ({
								cmpbftTile: item.cmpbftTile,
								cmpbftNo: item.cmpbftNo,
							}));
							updatedCheckedItems = [...initialCheckedItems];
							removedItems = []; // 새 카테고리 클릭 시 초기화

							// 기존 리스트 초기화
							benefitListContainer.innerHTML = "";

							// 체크박스 상태를 반영하여 데이터 출력
							codeList.forEach((item) => {
								const matchedItem = initialCheckedItems.find(
									(bft) => bft.cmpbftTile === item.codeCd
								);
								const isChecked = !!matchedItem; // 초기 체크 상태 확인

								const div = document.createElement("div");
								div.className = "form-check";

								const checkbox = document.createElement("input");
								checkbox.type = "checkbox";
								checkbox.value = item.codeCd;
								checkbox.id = `benefit-${item.codeCd}`;
								checkbox.className = "form-check-input";
								checkbox.checked = isChecked;

								// 변경된 상태 추적
								checkbox.addEventListener("change", () => {
									if (checkbox.checked) {
										// 체크 상태로 변경된 경우
										updatedCheckedItems.push({
											cmpbftTile: item.codeCd,
											cmpbftNo: matchedItem ? matchedItem.cmpbftNo : null,
										});
										removedItems = removedItems.filter(
											(obj) => obj.cmpbftTile !== item.codeCd
										); // 해제된 리스트에서 제거
									} else {
										// 체크 해제된 경우
										if (matchedItem) {
											removedItems.push({
												cmpbftTile: item.codeCd,
												cmpbftNo: matchedItem.cmpbftNo,
											});
										}
										updatedCheckedItems = updatedCheckedItems.filter(
											(obj) => obj.cmpbftTile !== item.codeCd
										);
									}
								});

								const label = document.createElement("label");
								label.textContent = item.codeNm;
								label.className = "form-check-label";
								label.htmlFor = `benefit-${item.codeCd}`;

								div.appendChild(checkbox);
								div.appendChild(label);
								benefitListContainer.appendChild(div);
							});

							// 모달 표시
							modal.show();
						},
						error: function(xhr, status, error) {
							console.error("Failed to fetch codeList:", status, error);
							benefitListContainer.innerHTML = "<p>코드 데이터를 불러오지 못했습니다.</p>";
							modal.show();
						},
					});
				},
				error: function(xhr, status, error) {
					console.error("Failed to fetch bftList:", status, error);
					benefitListContainer.innerHTML = "<p>복리후생 데이터를 불러오지 못했습니다.</p>";
					modal.show();
				},
			});
		});
	});

	// 수정 버튼 클릭 이벤트
	updateButton.addEventListener("click", () => {
		// 삭제 요청에 필요한 항목: removedItems만 사용
		const itemsToDelete = removedItems.map((item) => item.cmpbftNo);

		// 새로 체크된 항목: updatedCheckedItems 중 cmpbftNo가 없는 항목
		const itemsToInsert = updatedCheckedItems
			.filter((item) => !item.cmpbftNo) // cmpbftNo가 없는 경우
			.map((item) => ({
				compId: compId,
				cmpbftTile: item.cmpbftTile,
			}));

		console.log("삭제할 항목:", itemsToDelete);
		console.log("삽입할 항목:", itemsToInsert);

		let deleteRequestDone = false;
		let insertRequestDone = false;

		// 삭제 요청
		if (itemsToDelete.length > 0) {
			$.ajax({
				url: `${path}/benefit/${compId}/delete`,
				type: "PUT",
				contentType: "application/json",
				data: JSON.stringify(itemsToDelete),
				success: function(response) {
					console.log("삭제 성공:", response.message);
					deleteRequestDone = true;
					if (deleteRequestDone && insertRequestDone) {
						location.reload();
					}
				},
				error: function(xhr, status, error) {
					console.error("삭제 요청 실패:", status, error);
				},
			});
		} else {
			deleteRequestDone = true;
		}

		// 삽입 요청
		if (itemsToInsert.length > 0) {
			$.ajax({
				url: `${path}/benefit/${compId}/bftinsert`,
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify(itemsToInsert),
				success: function(response) {
					console.log("삽입 성공:", response.message);
					insertRequestDone = true;
					if (deleteRequestDone && insertRequestDone) {
						location.reload();
					}
				},
				error: function(xhr, status, error) {
					console.error("삽입 요청 실패:", status, error);
				},
			});
		} else {
			insertRequestDone = true;
		}

		// 모달 닫기
		modal.hide();
	});
});


// 카카오 주소 api

function sample4_execDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var roadAddr = data.roadAddress; // 도로명 주소 변수
			var extraRoadAddr = ''; // 참고 항목 변수

			// 법정동명이 있을 경우 추가한다. (법정리는 제외)
			// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
			if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
				extraRoadAddr += data.bname;
			}
			// 건물명이 있고, 공동주택일 경우 추가한다.
			if (data.buildingName !== '' && data.apartment === 'Y') {
				extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
			}
			// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
			if (extraRoadAddr !== '') {
				extraRoadAddr = ' (' + extraRoadAddr + ')';
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('sample4_postcode').value = data.zonecode;
			/*document.getElementById("sample4_roadAddress").value = roadAddr;*/
			document.getElementById("sample4_jibunAddress").value = data.jibunAddress;

			// 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
			if (roadAddr !== '') {
				document.getElementById("sample4_extraAddress").value = extraRoadAddr;
			} else {
				document.getElementById("sample4_extraAddress").value = '';
			}

			var guideTextBox = document.getElementById("guide");
			// 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
			if (data.autoRoadAddress) {
				var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
				guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
				guideTextBox.style.display = 'block';

			} else if (data.autoJibunAddress) {
				var expJibunAddr = data.autoJibunAddress;
				guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
				guideTextBox.style.display = 'block';
			} else {
				guideTextBox.innerHTML = '';
				guideTextBox.style.display = 'none';
			}

			if (state === 'FORCE_CLOSE') {
				//사용자가 브라우저 닫기 버튼을 통해 팝업창을 닫았을 경우, 실행될 코드를 작성하는 부분입니다.
				alert("닫기")
			} else if (state === 'COMPLETE_CLOSE') {
				//사용자가 검색결과를 선택하여 팝업창이 닫혔을 경우, 실행될 코드를 작성하는 부분입니다.
				//oncomplete 콜백 함수가 실행 완료된 후에 실행됩니다.
				alert("종료")
			}
		}
	}).open();
}



document.getElementById("logoButton").addEventListener("click", function() {
	document.getElementById("logoInput").click(); // 숨겨진 파일 선택 창 열기
});


let selectedFile = null; // 선택된 파일 저장 변수
// 파일 선택 이벤트 처리
document.getElementById("logoInput").addEventListener("change", function() {
	const logoImg = document.getElementById("logoImg");
	const file = this.files[0];
	if (file) {
		selectedFile = file; // 파일을 저장
		const reader = new FileReader();

		reader.onload = function(event) {
			// 랜덤 쿼리 파라미터 추가
			logoImg.src = event.target.result;
		};

		reader.readAsDataURL(file); // 파일을 읽어서 미리보기 설정
	} else {
		alert("파일을 선택하지 않았습니다.");
	}
});

document.getElementById("logoUpdate").addEventListener("click", function() {
	const path = document.body.dataset.url;

	if (!selectedFile) {
		alert("저장할 파일을 선택해주세요.");
		return;
	}
	const compId = document.querySelector('input[data-compId]').getAttribute('data-compId');
	const formData = new FormData();
	formData.append("compId",compId);
	formData.append("uploadFiles", selectedFile); // 선택된 파일을 폼 데이터에 추가

	$.ajax({
		url: `${path}/admincompany/updatelogo/`, // 서버 URL
		type: "POST",
		data: formData,
		processData: false,
		contentType: false,
		success: function(response) {
			console.log(response);
			if (response.status) {
				alert("로고가 성공적으로 저장되었습니다.");
			} else {
				alert(`저장 실패: ${response.message}`);
			}
		},
		error: function() {
			alert("서버 오류가 발생했습니다. 다시 시도해주세요.");
		},
	});
});

