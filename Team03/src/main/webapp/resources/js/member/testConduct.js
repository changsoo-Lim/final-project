const baseUrl = document.body.dataset.url;

const questions = [
    {
        id: 1,
        text: "나는 낯선 사람을 만나는 것이 좋다.",
        image: "",
        options: [ 
			{ 446 : "매우 그렇다." },
			{ 447 : "그렇다." },
			{ 448 : "보통이다." },
			{ 449 : "그렇지 않다." },
			{ 450 : "전혀 그렇지 않다." }
		],
        type: "multiple",
    },
    {
        id: 2,
        text: "단순한 업무를 계속하는 것은 적성에 맞지 않는다.",
        image: "",
        options: [ 
			{ 451 : "매우 그렇다." },
			{ 452 : "그렇다." },
			{ 453 : "보통이다." },
			{ 454 : "그렇지 않다." },
			{ 455 : "전혀 그렇지 않다." }
		],
        type: "multiple",
    },
    {
        id: 3,
        text: "타인의 감성을 종종 알아차리지 못하는 편이다.",
        image: "",
        options: [ 
			{ 456 : "매우 그렇다." },
			{ 457 : "그렇다." },
			{ 458 : "보통이다." },
			{ 459 : "그렇지 않다." },
			{ 460 : "전혀 그렇지 않다." }
		],
        type: "multiple",
    },
    {
        id: 4,
        text: "문제내용4",
        image: "",
        options: [ 
			{ 461 : "매우 그렇다." },
			{ 462 : "그렇다." },
			{ 463 : "보통이다." },
			{ 464 : "그렇지 않다." },
			{ 465 : "전혀 그렇지 않다." }
		],
        type: "multiple",
    },
];

let currentQuestion = 0;
const totalSeconds = 30 * 60; // 총 30분
let remainingSeconds = totalSeconds;
const answers = {}; // 사용자의 답안 저장

// 원형 타이머 업데이트 함수
function updateCircle(percentage) {
    const circle = document.querySelector(".circle");
    const dashArray = `${percentage}, 100`; // 진행률
    circle.style.strokeDasharray = dashArray;
}

// 타이머 업데이트 함수
function updateTimer() {
    const timerElement = document.getElementById("timer");
    const minutes = Math.floor(remainingSeconds / 60);
    const seconds = remainingSeconds % 60;
    timerElement.textContent = `${minutes}:${seconds.toString().padStart(2, "0")}`;

    // 진행률 계산
    const percentage = (remainingSeconds / totalSeconds) * 100;
	console.log(percentage);
    updateCircle(percentage);

    if (remainingSeconds > 0) {
        remainingSeconds--;
        setTimeout(updateTimer, 1000);
    } else {
        alert("시간이 종료되었습니다!");
        submitAnswers(); // 시험 종료 처리
    }
}

// 문제 렌더링
function renderQuestion(index) {
    const question = questions[index];
    document.getElementById("question-title").textContent = `문제 ${question.id}`;
    document.getElementById("question-text").textContent = question.text;

    const questionImage = document.getElementById("question-image");
    if (question.image) {
        questionImage.src = question.image;
        questionImage.style.display = "block";
    } else {
        questionImage.style.display = "none";
    }

    const questionOptions = document.getElementById("question-options");
    questionOptions.innerHTML = "";

    if (question.type === "multiple") {
        question.options.forEach((option, i) => {
            const itemNo = Object.keys(option)[0]; // option 객체의 키 (itemNo)
            const itemContent = option[itemNo]; // option 객체의 값 (지문 내용)

            const optionElement = document.createElement("div");
            optionElement.innerHTML = `
                <input type="radio" name="option" id="option${i}" value="${itemNo}">
                <label for="option${i}">${itemContent}</label>`;
            questionOptions.appendChild(optionElement);
        });
    } else if (question.type === "subjective") {
        questionOptions.innerHTML = `
            <input type="text" id="subjective-answer" class="form-control" placeholder="정답을 입력하세요">`;
    }

    // 이전 답안 로드
    if (answers[question.id]) {
        if (question.type === "multiple") {
            document.querySelector(`input[value="${answers[question.id].itemNo}"]`).checked = true;
        } else if (question.type === "subjective") {
            document.getElementById("subjective-answer").value = answers[question.id].answerContent;
        }
    }
}

// 정답 배열로 저장
function saveAnswer() {
    const question = questions[currentQuestion];
    if (question.type === "multiple") {
        const selectedOption = document.querySelector('input[name="option"]:checked');
        answers[question.id] = {
            itemNo: selectedOption ? parseInt(selectedOption.value, 10) : null, // 선택된 itemNo를 저장
            candidateNo: 4, // 예시 값 (사용자 ID 또는 시험 응시 ID)
            answerContent: null, // 객관식은 answerContent가 필요 없음
        };
    } else if (question.type === "subjective") {
        const subjectiveAnswer = document.getElementById("subjective-answer").value;
        answers[question.id] = {
            itemNo: null, // 주관식은 itemNo가 없음
            candidateNo: 4,
            answerContent: subjectiveAnswer, // 주관식 답변
        };
    }
}

// 문제 버튼 생성
function createQuestionButtons() {
    const questionButtons = document.getElementById("question-buttons");
    questions.forEach((_, index) => {
        const button = document.createElement("button");
        button.textContent = index + 1;
        button.className = "btn btn-outline-primary";
        button.addEventListener("click", () => {
            saveAnswer();
            currentQuestion = index;
            renderQuestion(currentQuestion);
        });
        questionButtons.appendChild(button);
    });
}

function submitAnswers() {
    saveAnswer(); // 마지막 답안 저장
    console.log("사용자 답안:", answers);
	const payload = Object.values(answers); // 배열로 변환	
	const candidateNo = $("#candidateNo").val();
	
	
    // 서버로 답안을 전송
    axios.post(`${baseUrl}/member/test/1/submit?candidateNo=${candidateNo}&testType=TE01`, payload)
    .then(response => {
        sweatAlert("success", "시험이 성공적으로 제출되었습니다.");
		// 부모 창으로 리다이렉트
        if (window.opener) {
            window.opener.location.href = `${baseUrl}/member/test/list`;
        }
        // 현재 창 닫기
        window.close();
    })
    .catch(error => {
        sweatAlert("error", "답안 제출 중 문제가 발생했습니다. 다시 시도해주세요.");
    });
}

// 이벤트 바인딩
document.getElementById("prev-button").addEventListener("click", () => {
    if (currentQuestion > 0) {
        saveAnswer();
        currentQuestion--;
        renderQuestion(currentQuestion);
    }
});



// 이전/다음 버튼
document.getElementById("prev-button").addEventListener("click", () => {
    if (currentQuestion > 0) {
        saveAnswer();
        currentQuestion--;
        renderQuestion(currentQuestion);
    }
});

document.getElementById("next-button").addEventListener("click", () => {
    if (currentQuestion < questions.length - 1) {
        saveAnswer();
        currentQuestion++;
        renderQuestion(currentQuestion);
    }
});

// 새로고침 방지 및 페이지 이탈 경고
window.addEventListener("beforeunload", (event) => {
    event.preventDefault();
    // 메시지는 일부 브라우저에서 무시될 수 있음
    event.returnValue = "이 페이지를 떠나면 테스트 진행 상황이 저장되지 않을 수 있습니다.";
});


// 오른쪽 클릭 방지
document.addEventListener("contextmenu", (event) => {
    event.preventDefault();
});

// 개발자 도구 방지 (F12, Ctrl+Shift+I 등)
document.addEventListener("keydown", (event) => {
    // 일반 새로고침(F5, Ctrl+R) 차단
    if ((event.ctrlKey && event.key === "r") || event.key === "F5") {
        event.preventDefault();
    }

    // 강력 새로고침(Ctrl+Shift+R) 차단
    if (event.ctrlKey && event.shiftKey && event.key === "R") {
        event.preventDefault();
    }

    // 기타 개발자 도구 접근 키 차단
    if (
        (event.ctrlKey && event.shiftKey && event.key === "I") || // Ctrl+Shift+I
        (event.ctrlKey && event.shiftKey && event.key === "J") || // Ctrl+Shift+J
        (event.ctrlKey && event.key === "U") || // Ctrl+U
        event.key === "F12" // F12
    ) {
        event.preventDefault();
    }
});

document.getElementById("finish-button").addEventListener("click", submitAnswers);

// 초기화
createQuestionButtons();
renderQuestion(currentQuestion);
updateTimer();
