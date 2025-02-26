const editBtn = document.getElementById("editBtn");
const introEdit = document.querySelector("#introEdit");
const introDetailEdit = document.querySelector("#introDetailEdit");
const introDetailEditList = document.querySelector("#introDetailEditList");


function spellCheck(formElement) {
	let introDetailCont = formElement.querySelector("#introDetailCont").value;


	const passportKey = "5ed196d426d8d449f3745001d1a13f5c1080f2ac";
	const apiUrl = `https://m.search.naver.com/p/csearch/ocontent/util/SpellerProxy?passportKey=${passportKey}&where=nexearch&color_blindness=0&q=${encodeURIComponent(introDetailCont)}`;

	fetch(apiUrl, {
		method: "GET",
	})
		.then((response) => response.text())
		.then((data) => {

			const jsonData = JSON.parse(data.replace(/^[^(]*\(([\s\S]*)\);?$/));
			const correctedHTML = jsonData.message.result.html;


			const spellingCheckArea = formElement.querySelector("#spellingCheck");
			const checkedBtn = formElement.querySelector("#checkedBtn");
			const emCheck = formElement.querySelector("#emCheck");
			spellingCheckArea.style.display = "block";
			checkedBtn.style.display = "block";
			emCheck.style.display = "block";
			spellingCheckArea.innerHTML = correctedHTML;
		})
		.catch((error) => {
			console.error("Spell Check Error: ", error);
			alert("맞춤법 검사 중 오류가 발생했습니다.");
		});
}


function handleChecked(formElement) {
	const spellingCheckArea = formElement.querySelector("#spellingCheck");
	const checkedBtn = formElement.querySelector("#checkedBtn");


	const correctedText = spellingCheckArea.innerHTML.replace(/<[^>]+>/g, '');
	const introDetailCont = formElement.querySelector("#introDetailCont");
	introDetailCont.value = correctedText;

	spellingCheckArea.style.display = "none";
	checkedBtn.style.display = "none";
}

document.addEventListener("DOMContentLoaded", function() {
	const maxLength = 500;

	// 글자 수 제한 초기화 함수
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
	const addDeleteEvent = (form, isCopied = false) => {
		const delBtn = form.querySelector("#delBtn");
		if (delBtn) {
			delBtn.style.display = isCopied ? "inline-block" : "none"; // 복사된 폼만 삭제 버튼 보이기
			delBtn.addEventListener("click", function(event) {
				event.preventDefault();
				form.remove(); // 폼 삭제
			});
		}
	};

	// 맞춤법 검사 이벤트 추가
	const addSpellCheckEvent = (form) => {
		const checkBtn = form.querySelector("#checkBtn");
		const checkedBtn = form.querySelector("#checkedBtn");

		if (checkBtn) {
			checkBtn.addEventListener("click", function(event) {
				event.preventDefault();
				spellCheck(form); // 맞춤법 검사 호출
			});
		}

		if (checkedBtn) {
			checkedBtn.addEventListener("click", function(event) {
				event.preventDefault();
				handleChecked(form); // "확인" 버튼 처리 호출
			});
		}
	};

	// 초기 폼 처리
	const introDetailEditList = document.querySelector("#introDetailEditList");
	if (introDetailEditList) {
		const forms = introDetailEditList.querySelectorAll("#introDetailEdit");
		forms.forEach((form) => {
			const textarea = form.querySelector("#introDetailCont");
			const charCount = form.querySelector("#charCount");

			if (textarea && charCount) {
				initializeCharCount(textarea, charCount); // 글자 수 제한 초기화
			}

			addDeleteEvent(form); // 기본 폼은 삭제 버튼 숨김
			addSpellCheckEvent(form); // 맞춤법 검사 이벤트 추가
		});
	}

	// 추가 버튼 처리
	const addFormBtn = document.querySelector("#addFormBtn");
	const introForm = document.querySelector("#introForm");
	const introDetailForm = document.querySelector("#introDetailEdit");

	if (addFormBtn && introForm && introDetailForm) {
		addFormBtn.addEventListener("click", function(event) {
			event.preventDefault();

			const newForm = introDetailForm.cloneNode(true); // 기존 폼 복제
			newForm.style.display = "block"; // 복사된 폼 보이기
			newForm.removeAttribute("id"); // ID 중복 방지

			// 입력 필드 초기화
			let inputs = newForm.querySelectorAll("input, textarea");
			inputs.forEach((input) => {
				input.value = "";
			});

			const textarea = newForm.querySelector("#introDetailCont");
			const charCount = newForm.querySelector("#charCount");

			if (textarea && charCount) {
				initializeCharCount(textarea, charCount); // 글자 수 제한 초기화
			}

			addDeleteEvent(newForm, true); // 복사된 폼에 삭제 버튼 보이기
			addSpellCheckEvent(newForm); // 맞춤법 검사 이벤트 추가

			introForm.appendChild(newForm); // 새로운 폼 추가
		});
	}
});


editBtn.addEventListener("click", function(event) {
	event.preventDefault();

	// 제목 가져오기
	let introTitle = introEdit.querySelector("#introTitle").value;

	// 세부 정보 배열 초기화
	let introDetails = [];

	// 기존 폼 데이터 수집
	const forms = introDetailEditList.querySelectorAll("#introDetailEdit");
	forms.forEach((form) => {
		let formOne = {
			introDetailNo: form.querySelector("#introDetailNo").value,
			introDetailTitle: form.querySelector("#introDetailTitle").value,
			introDetailCont: form.querySelector("#introDetailCont").value,
		};
		introDetails.push(formOne);
	});

	// 복사된 폼 데이터 수집
	const copiedForms = document.querySelectorAll(".introForm");
	if (copiedForms.length > 0) {
		copiedForms.forEach((form) => {
			let introDetailNo = form.querySelector("#introDetailNo")?.value || null;
			let introDetailTitle = form.querySelector("#introDetailTitle")?.value || "";
			let introDetailCont = form.querySelector("#introDetailCont")?.value || "";

			// 유효한 데이터만 추가
			if (introDetailTitle && introDetailCont) {
				let formOne = {
					introDetailNo: introDetailNo,
					introDetailTitle: introDetailTitle,
					introDetailCont: introDetailCont,
				};
				introDetails.push(formOne);
			}
		});
	}

	// PUT과 POST로 나누기
	let putDetails = introDetails.filter((detail) => detail.introDetailNo); // 수정할 데이터
	let postDetails = introDetails.filter((detail) => !detail.introDetailNo); // 삽입할 데이터

	console.log("PUT으로 보낼 데이터:", {
		introTitle: introTitle,
		introDetails: putDetails,
	});
	console.log("POST로 보낼 데이터:", {
		introDetails: postDetails,
	});

	const baseUrl = document.body.dataset.url;
	const introNo = document.querySelector("#introNo").value;

	// PUT과 POST 요청 실행
	Promise.all([
		putDetails.length > 0
			? fetch(`${baseUrl}/intro/${introNo}/edit`, {
				method: "PUT",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify({ introTitle: introTitle, introDetails: putDetails }),
			})
			: Promise.resolve({ ok: true, json: () => Promise.resolve({ success: true }) }), // PUT 요청 없을 때 처리
		postDetails.length > 0
			? fetch(`${baseUrl}/intro/${introNo}/editNew`, {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify({ introTitle: introTitle, introDetails: postDetails }),
			})
			: Promise.resolve({ ok: true, json: () => Promise.resolve({ success: true }) }), // POST 요청 없을 때 처리
	])
		.then((responses) => Promise.all(responses.map((res) => res.json())))
		.then(([putData, postData]) => {
			if (putData.success && postData.success) {
				console.log("수정 및 삽입 성공!");
				window.location.href = "/stackUp/intro"; // 성공 시 페이지 이동
			} else {
				console.error("수정 또는 삽입 실패", { putData, postData });
				alert("수정 또는 삽입 처리 중 일부 오류가 발생했습니다.");
			}
		})
		.catch((error) => {
			console.error("요청 처리 실패: ", error);
			alert("요청 처리 중 오류가 발생했습니다.");
		});
});





const checkBtns = document.querySelectorAll("#checkBtn");
const checkedBtns = document.querySelectorAll("#checkedBtn");

checkBtns.forEach((button, index) => {
	button.addEventListener("click", function(event) {
		event.preventDefault();
		const parentDiv = button.closest("#introDetailEdit");
		spellCheck(parentDiv);
	});
});

checkedBtns.forEach((button, index) => {
	button.addEventListener("click", function(event) {
		event.preventDefault();
		const parentDiv = button.closest("#introDetailEdit");
		handleChecked(parentDiv); // 각 폼의 부모 div를 전달
	});
});

