const addFormBtn = document.getElementById("addFormBtn");
const introForm = document.querySelector("#introForm");
const introDetailForm = document.querySelector("#introDetailForm");
const introListForm = document.querySelector("#introListForm");
const checkBtn = document.querySelector("#checkBtn");
const checkedBtn = document.querySelector("#checkedBtn");

// 맞춤검 검사 로직 함수로 만들기
function spellCheck(formElement) {
	let introDetailCont = formElement.querySelector("#introDetailCont").value;

	// 유효한 passportKey 사용
	const passportKey = "5ed196d426d8d449f3745001d1a13f5c1080f2ac";
	// 네이버 맞춤법 검사 api 주소
	const apiUrl = `https://m.search.naver.com/p/csearch/ocontent/util/SpellerProxy?passportKey=${passportKey}&where=nexearch&color_blindness=0&q=${encodeURIComponent(introDetailCont)}`;

	fetch(apiUrl, {
		method: "GET",
	})
		.then((response) => response.text())
		.then((data) => {
			// JSONP 데이터 파싱
			const jsonData = JSON.parse(data.replace(/^[^(]*\(([\s\S]*)\);?$/)); // 네이버는 JSONP를 사용하기에 JSON으로 바꿔주기
			const correctedHTML = jsonData.message.result.html; // HTML 태그로 하여 css입힐 수 있게

			// 검사 결과 영역 표시 및 업데이트
			const spellingCheckArea = formElement.querySelector("#spellingCheck");
			const checkedBtn = formElement.querySelector("#checkedBtn");
			const emCheck = formElement.querySelector("#emCheck");
			spellingCheckArea.style.display = "block";
			checkedBtn.style.display = "block";
			emCheck.style.display = "block";
			spellingCheckArea.innerHTML = correctedHTML; // HTML 형식으로 삽입
		})
		.catch((error) => {
			console.error("Spell Check Error: ", error);
			alert("맞춤법 검사 중 오류가 발생했습니다.");
		});
}


function spellChecked(formElement) {
	const spellingCheckArea = formElement.querySelector("#spellingCheck");
	const checkedBtn = formElement.querySelector("#checkedBtn");
	const emCheck = formElement.querySelector("#emCheck");

	// HTML 태그 제거 후 원본 textarea에 집어넣기
	const correctedText = spellingCheckArea.innerHTML.replace(/<[^>]+>/g, '');
	const introDetailCont = formElement.querySelector("#introDetailCont");
	introDetailCont.value = correctedText;

	// 검사 결과 영역 및 버튼 숨기기
	spellingCheckArea.style.display = "none";
	checkedBtn.style.display = "none";
	emCheck.style.display = "none";
}

document.addEventListener("DOMContentLoaded", function() {
	const maxLength = 500;

	// 네이버 글자수가 500이 최대여서 500자까지만 입력할 수 있는 로직
	const initializeCharCount = (textarea, charCount) => {
		const updateCharCount = () => {
			const currentLength = textarea.value.length;
			if (currentLength > maxLength) {
				textarea.value = textarea.value.substring(0, maxLength);
			}
			charCount.textContent = `${currentLength} / ${maxLength}`;
		};

		// 초기 글자 수 설정
		updateCharCount();

		// 입력 이벤트 처리
		textarea.addEventListener("input", updateCharCount);
	};

	// 삭제 버튼 이벤트 추가 함수
	const addDeleteEvent = (form) => {
		const delBtn = form.querySelector("#delBtn");
		if (delBtn) {
			delBtn.style.display = "block"; // 삭제 버튼을 보이도록 설정
			delBtn.addEventListener("click", function(event) {
				event.preventDefault();
				form.remove(); // 폼 삭제
			});
		}
	};

	// 초기 폼 처리
	const initialTextarea = document.getElementById("introDetailCont");
	const initialCharCount = document.getElementById("charCount");
	if (initialTextarea && initialCharCount) {
		initializeCharCount(initialTextarea, initialCharCount);
	}

	// "추가" 버튼 클릭 시 복사된 폼 처리
	const addFormBtn = document.getElementById("addFormBtn");
	const introDetailForm = document.getElementById("introDetailForm");
	const introListForm = document.getElementById("introListForm");

	if (addFormBtn && introDetailForm && introListForm) {
		addFormBtn.addEventListener("click", function(event) {
			event.preventDefault();

			let formOne = introDetailForm.cloneNode(true); // form 형식 복사
			formOne.style.display = "block"; // 복사한 form을 눈에 보이게 설정
			formOne.removeAttribute("id"); // ID 중복 방지

			// 입력 필드 초기화
			let inputs = formOne.querySelectorAll("input, textarea");
			inputs.forEach((input) => (input.value = "")); // 추가 버튼 클릭 시 기존 값 초기화

			// 복사된 <p id="spellingCheck"> 초기화
			const clonedSpellingCheck = formOne.querySelector("#spellingCheck");
			const emCheck = formOne.querySelector("#emCheck");
			const checkedBtn = formOne.querySelector("#checkedBtn");
			if (clonedSpellingCheck) {
				clonedSpellingCheck.style.display = "none";
				emCheck.style.display = "none";
				checkedBtn.style.display = "none";
			}

			// 복사된 버튼 이벤트 연결
			let clonedCheckBtn = formOne.querySelector("#checkBtn");
			let clonedCheckedBtn = formOne.querySelector("#checkedBtn");

			if (clonedCheckBtn) {
				clonedCheckBtn.addEventListener("click", function(event) {
					event.preventDefault();
					spellCheck(formOne); // 복사된 폼에서 맞춤법 검사 호출
				});
			}

			if (clonedCheckedBtn) {
				clonedCheckedBtn.addEventListener("click", function(event) {
					event.preventDefault();
					spellChecked(formOne); // 복사된 폼에서 "확인" 버튼 처리 호출
				});
			}

			// 복사된 폼의 textarea 글자 수 제한 설정
			let clonedTextarea = formOne.querySelector("#introDetailCont");
			let clonedCharCount = formOne.querySelector("#charCount");
			if (clonedTextarea && clonedCharCount) {
				initializeCharCount(clonedTextarea, clonedCharCount);
			}

			// 삭제 버튼 이벤트 연결
			addDeleteEvent(formOne);

			// 복사한 form 추가
			introListForm.appendChild(formOne);
		});
	}
});


const sendFormBtn = document.getElementById("sendFormBtn");

sendFormBtn.addEventListener("click", function(event) {
	event.preventDefault();

	// 제목 가져오기 (기본 폼)
	let introTitle = introForm.querySelector("#introTitle").value;

	// 기본 폼 데이터 수집
	let baseForm = {
		introTitle: introTitle,
		introDetailTitle: introDetailForm.querySelector("#introDetailTitle").value,
		introDetailCont: introDetailForm.querySelector("#introDetailCont").value
	};

	// 배열 생성 및 기본 폼 데이터 추가
	let introDetails = [];
	introDetails.push(baseForm);

	// 복사된 폼 데이터 수집 (introListForm 내부의 모든 <div> 요소)
	let copiedForms = introListForm.querySelectorAll("div");

	copiedForms.forEach((form) => {
		let formOne = {
			introDetailTitle: form.querySelector("#introDetailTitle").value,
			introDetailCont: form.querySelector("#introDetailCont").value,
		};
		introDetails.push(formOne);
	});

	// 요청 데이터 구성
	let requestData = {
		introTitle: introTitle,
		introDetails: introDetails,
	};

	// 디버깅: 콘솔에 요청 데이터 출력
	console.log("요청 데이터:", requestData);


	// 데이터 전송
	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(requestData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.success) {
				window.location.href = "/stackUp/intro"; // 성공 시 메인 페이지로 이동
			}
		})
		.catch((error) => {
			console.error("Fetch Error: ", error);
			alert("요청 처리 중 오류가 발생했습니다.");
		});

});



checkBtn.addEventListener("click", function(event) {
	event.preventDefault();
	spellCheck(introDetailForm); // 함수 호출
});

checkedBtn.addEventListener("click", function(event) {
	event.preventDefault();
	spellChecked(introDetailForm); // 함수 호출
});


