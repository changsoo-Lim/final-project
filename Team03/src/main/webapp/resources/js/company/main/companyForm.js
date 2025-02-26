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
	let currentCategory = null;
	let cate = null;

	let initialCheckedItems = []; // 초기 체크 상태
	let updatedCheckedItems = []; // 변경된 상태
	let removedItems = []; // 체크 해제된 항목

	// 카테고리 블록 클릭 이벤트
	const benefitEvent = document.querySelectorAll('.category-block.mb-4');


	const introTextarea = document.getElementById("companyIntro");
	const editButton = document.getElementById("editIntroBtn");

	// 수정 버튼 클릭
	editButton.addEventListener("click", function() {
		const updatedContent = introTextarea.value;

		$.ajax({
			url: `${path}/company/updatecontent`, // 서버 업데이트 경로
			type: "PUT",
			contentType: "application/json",
			data: JSON.stringify({ compCont: updatedContent }),
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
	savebtn.addEventListener("click", () => {
		// 두 번째 클릭: AJAX 요청
		$.ajax({
			url: `${path}/company/updateinfo`,
			type: 'PUT',
			contentType: 'application/json', // JSON 형식 명시
			data: JSON.stringify({
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
		
		// 별 표시 함수
	function renderStars(container, rating, maxStars = 5) {
	    container.innerHTML = ""; // 기존 내용 초기화
	    for (let i = 1; i <= maxStars; i++) {
	        const star = document.createElement("span");
	        star.classList.add("star");
	        if (i <= rating) {
	            star.classList.add("yellow"); // 노란 별
	        } else {
	            star.classList.add("gray"); // 회색 별
	        }
	        star.textContent = "★";
	        container.appendChild(star);
	    }
	}
	
	// 모든 별 컨테이너 업데이트
	document.querySelectorAll(".stars-container").forEach(container => {
	    const rating = parseFloat(container.getAttribute("data-rating"));
	    renderStars(container, rating);
	});

	
	
	
	

	benefitEvent.forEach((element) => {
		const category = element.dataset.categorie;
		const encodedCategory = category.replace(/\//g, "_SLASH_");

		element.addEventListener("click", () => {
			currentCategory = encodedCategory;
			cate = category;
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
						error: function(status, error) {
							console.error("Failed to fetch codeList:", status, error);
							benefitListContainer.innerHTML = "<p>코드 데이터를 불러오지 못했습니다.</p>";
							modal.show();
						},
					});
				},
				error: function(status, error) {
					console.error("Failed to fetch bftList:", status, error);
					benefitListContainer.innerHTML = "<p>복리후생 데이터를 불러오지 못했습니다.</p>";
					modal.show();
				},
			});
		});
	});

	// 수정 버튼 클릭 이벤트
	updateButton.addEventListener("click", () => {
		const itemsToDelete = removedItems.map((item) => item.cmpbftNo);
		const itemsToInsert = updatedCheckedItems
			.filter((item) => !item.cmpbftNo)
			.map((item) => ({
				compId: compId,
				cmpbftTile: item.cmpbftTile,
			}));

		console.log("삭제할 항목:", itemsToDelete);
		console.log("삽입할 항목:", itemsToInsert);

		// 삭제 요청
		const deleteRequest = itemsToDelete.length > 0
			? $.ajax({
				url: `${path}/benefit/${compId}/delete`,
				type: "PUT",
				contentType: "application/json",
				data: JSON.stringify(itemsToDelete),
			})
			: Promise.resolve(); // 삭제할 항목이 없으면 즉시 완료되는 Promise 반환

		// 삽입 요청
		const insertRequest = itemsToInsert.length > 0
			? $.ajax({
				url: `${path}/benefit/${compId}/bftinsert`,
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify(itemsToInsert),
			})
			: Promise.resolve(); // 삽입할 항목이 없으면 즉시 완료되는 Promise 반환

		// 모든 요청 완료 후 다시 데이터 요청
		Promise.all([deleteRequest, insertRequest])
			.then(() => {
				// 복리후생 데이터를 다시 요청
				return $.ajax({
					url: `${path}/benefit/${compId}/${currentCategory}`,
					type: "GET",
					dataType: "json",
				});
			})
			.then((bftResponse) => {
				console.log("복리후생 데이터 응답:", bftResponse);

				// 코드 리스트를 요청
				return $.ajax({
					url: `${path}/benefit/${currentCategory}/getcode`,
					type: "GET",
					dataType: "json",
				}).then((codeResponse) => {
					console.log("코드 리스트 응답:", codeResponse);

					// 현재 카테고리의 <ul> 요소 선택
					const benefitListContainer = document.getElementById(`benefits-${cate}`);
					if (!benefitListContainer) {
						console.error(`benefits-${cate} 요소를 찾을 수 없습니다.`);
						return;
					}

					// 기존 리스트 초기화
					benefitListContainer.innerHTML = "";

					const bftList = bftResponse.cmp_BftVOList || [];
					const codeList = codeResponse.codeVOList || [];

					// 매칭된 항목만 출력
					const matchedItems = codeList.filter((item) =>
						bftList.some((bft) => bft.cmpbftTile === item.codeCd)
					);

					let count = 0;

					matchedItems.forEach((item, index) => {
						if (count < 5) {
							const li = document.createElement("li");
							li.textContent = item.codeNm;
							benefitListContainer.appendChild(li);
							count++;
						}
					});

					// "더보기" 처리
					if (matchedItems.length > 5) {
						const moreLi = document.createElement("li");
						moreLi.textContent = "더보기...";
						moreLi.className = "text-muted";
						moreLi.style.cursor = "pointer";

						// "더보기" 클릭 이벤트
						moreLi.addEventListener("click", () => {
							benefitListContainer.innerHTML = ""; // 기존 리스트 초기화

							// 모든 항목 출력
							matchedItems.forEach((item) => {
								const li = document.createElement("li");
								li.textContent = item.codeNm;
								benefitListContainer.appendChild(li);
							});
						});

						benefitListContainer.appendChild(moreLi);
					}

					console.log(`복리후생 UI(${currentCategory})가 업데이트되었습니다.`);
				});
			})
			.catch((error) => {
				console.error("요청 중 오류 발생:", error);
			})
			.finally(() => {
				modal.hide(); // 모든 작업 완료 후 모달 닫기
			});



	});

});

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

	const formData = new FormData();
	formData.append("uploadFiles", selectedFile); // 선택된 파일을 폼 데이터에 추가

	$.ajax({
		url: `${path}/company/updatelogo`, // 서버 URL
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
