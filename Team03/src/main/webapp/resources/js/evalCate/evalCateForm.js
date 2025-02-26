document.addEventListener('DOMContentLoaded', () => {
	memList();

})
const path = window.location.pathname;
const split = path.split('/');
const baseUrl = split[1];
const fieldNo = split[4];
const vchatNo = split[5];
console.log("baseUrl:", baseUrl);
console.log("fieldNo:", fieldNo);
console.log("vchatNo:", vchatNo);

function memList() {
    fetch(`/${baseUrl}/evalCate/${fieldNo}/${vchatNo}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("데이터를 불러오는 데 실패했습니다.");
            }
            return response.json();
        })
        .then(data => {
            const selectMember = document.getElementById('member');
            selectMember.innerHTML = ''; // 기존 옵션 초기화
            // 데이터 순회하여 옵션 동적 생성
            data.forEach(mem => {  // data는 배열, mem은 각 ApplyVO 객체
                let interviewStage = ''; // 면접 단계 표시 문자열
                if (mem.appProcStatus === 'AP06') {
                    interviewStage = ' [1차면접]';
                } else if (mem.appProcStatus === 'AP07') {
                    interviewStage = ' [2차면접]';
                }

                if (mem.interviews && mem.member) { // interviews와 member가 존재할 때만 처리
                    mem.interviews.forEach(interview => {
                        const option = document.createElement('option');
                        option.value = interview.intvNo; // 인터뷰 번호를 value로 설정
                        option.textContent = `${mem.member.memNm}${interviewStage}`; // 회원 이름 + 면접 단계
                        selectMember.appendChild(option); // 옵션 추가
                    });
                }
            });
        })
        .catch(error => {
            console.error("Error loading preference list:", error);
        });
}

// 복제폼 만들기
const evalCateForm = document.querySelector(".evalCateForm");
const evalCateFormList = document.querySelector(".evalCateFormList");
const evalAddBtn = document.querySelector(".evalAddBtn");

let formIndex = 0; // 폼 인덱스를 위한 변수

// 초기 추가 버튼 클릭 이벤트 연결
evalAddBtn.addEventListener("click", addNewForm);

// 초기 폼에 직접 입력 드롭다운 이벤트 연결
const initialEvalCateNm = evalCateForm.querySelector(".evalCateNm");
const initialDirectInputContainer = evalCateForm.querySelector(".directInputContainer");

initialEvalCateNm.addEventListener("change", function() {
	toggleDirectInput(this, initialDirectInputContainer);
});

function addNewForm() {
	// 원본 폼 복제
	const clonedForm = evalCateForm.cloneNode(true);
	formIndex++; // 폼 인덱스 증가

	// 라디오 버튼 name 속성 고유화 및 초기화
	const radios = clonedForm.querySelectorAll('input[type="radio"][name="evalCateScore"]');
	radios.forEach((radio) => {
		radio.name = `evalCateScore-${formIndex}`; // 고유한 name 속성 설정
		radio.checked = false; // 선택 상태 초기화
	});

	// 입력 필드 초기화
	const inputs = clonedForm.querySelectorAll("input, select, textarea");
	inputs.forEach((input) => {
		if (input.type !== "radio" && input.type !== "checkbox") {
			input.value = ""; // 일반 input, select, textarea 값 초기화
		}
	});

	// 드롭다운 초기화 및 직접 입력 필드 숨김
	const clonedEvalCateNm = clonedForm.querySelector(".evalCateNm");
	const clonedDirectInputContainer = clonedForm.querySelector(".directInputContainer");
	clonedEvalCateNm.value = ""; // 드롭다운 초기 상태로 설정
	clonedDirectInputContainer.style.display = "none"; // 직접 입력 필드 숨김

	// 삭제 버튼 표시
	const deleteBtn = clonedForm.querySelector(".evalDelBtn");
	deleteBtn.style.display = "block";

	// 삭제 버튼 클릭 이벤트 연결
	deleteBtn.addEventListener("click", function() {
		clonedForm.remove(); // 폼 전체 삭제
	});

	// 복제된 폼의 드롭다운 변경 이벤트 연결
	clonedEvalCateNm.addEventListener("change", function() {
		toggleDirectInput(this, clonedDirectInputContainer);
	});

	// 복제된 폼의 추가 버튼 클릭 이벤트 연결
	const clonedAddBtn = clonedForm.querySelector(".evalAddBtn");
	clonedAddBtn.addEventListener("click", addNewForm);

	// 복제된 폼을 리스트에 추가
	evalCateFormList.appendChild(clonedForm);
}

// 직접 입력 필드 토글 함수
function toggleDirectInput(selectElement, directInputContainer) {
	const selectedValue = selectElement.value;
	if (selectedValue === "direct") {
		directInputContainer.style.display = "block";
	} else {
		directInputContainer.style.display = "none";
	}
}

const evalCateBtn = document.querySelector("#evalCateBtn");

evalCateBtn.addEventListener("click", function(e) {
	e.preventDefault();

	// 기본 폼 데이터 수집
	let baseForm = collectFormData(evalCateForm);

	// 배열 생성 및 기본 폼 데이터 추가
	let evalDetails = [];
	evalDetails.push(baseForm);

	// 복제된 폼 데이터 수집
	let copiedForms = evalCateFormList.querySelectorAll(".evalCateForm");

	copiedForms.forEach((form) => {
		let formOne = collectFormData(form);
		evalDetails.push(formOne);
	});

	let evalCateData = evalDetails;
	
	const intvNo = document.querySelector("#member").value;
	let evalData = {
	    vchatNo: vchatNo,
	    intvNo: intvNo
	};
	// 디버깅: 콘솔에 요청 데이터 출력
	console.log("요청 데이터:", evalCateData);

	// 데이터 전송
	fetch(`/${baseUrl}/eval`, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(evalData),
	})
		.then((response) => {
			if (!response.ok) {
	            throw new Error('네트워크 응답이 실패했습니다.');
	        }
	        return response.json(); // 응답을 JSON으로 파싱
		})
		.then((data) => {
			let evalNo = data.evalNo;
			if (data.success) {
				// 데이터 전송
				fetch(`/${baseUrl}/evalCate/new/${fieldNo}/${evalNo}`, {
					method: "POST",
					headers: {
						"Content-Type": "application/json",
					},
					body: JSON.stringify(evalCateData),
				})
					.then((response) => {
						if (response.ok) {
							return response.json();
						}
					})
					.then((data) => {
						if (data.success) {
								fetch(`/${baseUrl}/interview/${intvNo}`, {
								method: "PUT",
								headers: {
									"Content-Type": "application/json",
								},
							})
								.then((response) => {
									if (response.ok) {
										return response.json();
									}
								})
								.then((data) => {
									if (data.success) {
									    Swal.fire({
									        icon: 'success',
									        title: '평가 완료',
									        text: '평가가 성공적으로 완료되었습니다.',
									        confirmButtonText: '확인'
									    }).then(() => {
									        window.close(); // SweetAlert 확인 버튼을 누른 후 창 닫기
									    });
									}
								})
								.catch((error) => {
									console.error("Fetch Error: ", error);
									alert("요청 처리 중 오류가 발생했습니다.");
								});
						}
					})
					.catch((error) => {
						console.error("Fetch Error: ", error);
						alert("요청 처리 중 오류가 발생했습니다.");
					});
			}
		})
		.catch((error) => {
			console.error("Fetch Error: ", error);
			alert("요청 처리 중 오류가 발생했습니다.");
		});
	
});

function collectFormData(form) {
	let evalCateNm = form.querySelector(".evalCateNm").value;
	let directInput = form.querySelector(".directInputContainer input").value.trim();

	// directInput에 값이 있으면 evalCateNm을 직접 입력 값으로 대체
	if (evalCateNm === "direct" && directInput !== "") {
		evalCateNm = directInput;
	}

	// 폼 내부에서 선택된 라디오 버튼 값 가져오기
	let evalCateScore = form.querySelector('input[type="radio"]:checked');
	evalCateScore = evalCateScore ? evalCateScore.value : null; // 선택되지 않은 경우 null 반환

	return {
		evalCateNm: evalCateNm,
		evalCateCont: form.querySelector("#evalCateCont").value,
		evalCateScore: evalCateScore,
	};
}