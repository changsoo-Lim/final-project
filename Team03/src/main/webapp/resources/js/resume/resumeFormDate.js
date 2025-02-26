// 오늘 날짜 이후 선택 불가
const today = new Date();
const yyyy = today.getFullYear();
const mm = String(today.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 필요
const dd = String(today.getDate()).padStart(2, '0');
const maxDate = `${yyyy}-${mm}-${dd}`;

document.getElementById('qualificationDt').setAttribute('max', maxDate); // 검정고시 합격일자
document.getElementById('highschoolPeriod').setAttribute('max', maxDate); // 고등학교 
document.getElementById('uniSdt').setAttribute('max', maxDate); // 대학교 시작
document.getElementById('uniEdt').setAttribute('max', maxDate); // 대학교 끝
document.getElementById('careerStartDate').setAttribute('max', maxDate);
document.getElementById('careerEndDate').setAttribute('max', maxDate);
document.getElementById('certDate').setAttribute('max', maxDate);
document.getElementById('awardDate').setAttribute('max', maxDate);
document.getElementById('eduSdt').setAttribute('max', maxDate);
document.getElementById('eduEdt').setAttribute('max', maxDate);
document.getElementById('langTestDate').setAttribute('max', maxDate);
document.getElementById('expSdt').setAttribute('max', maxDate);
document.getElementById('expEdt').setAttribute('max', maxDate);
document.getElementById('activitySdt').setAttribute('max', maxDate);
document.getElementById('activityEdt').setAttribute('max', maxDate);


function validateDate(startInputId, endInputId) {
    const startInput = document.querySelector(`#${startInputId}`);
    const endInput = document.querySelector(`#${endInputId}`);

    endInput.addEventListener("change", function () {
        const startDate = new Date(startInput.value);
        const endDate = new Date(endInput.value);

        if (endDate < startDate) {
            Swal.fire({
                icon: "error",
                text: "종료일은 시작일보다 빠를 수 없습니다.",
            });
            endInput.value = ""; // 종료일을 초기화
        }
    });
}

validateDate("uniSdt", "uniEdt"); // 대학교 시작일 및 종료일
validateDate("eduSdt", "eduEdt"); // 교육 시작일 및 종료일
validateDate("expSdt", "expEdt"); // 해외연수 시작일 및 종료일

// 대학교 시작일 및 종료일 선택 불가
const inputUniSdt = document.querySelector("#uniSdt");
const inputUniEdt = document.querySelector("#uniEdt");

function validateUni() {
    // uniTableBody 안의 모든 행을 선택
    const rows = document.querySelectorAll('#uniTableBody tr');

    // 입력값을 Date 객체로 변환
    const start = new Date(inputUniSdt.value);
    const end = new Date(inputUniEdt.value);


    // 각 행에서 sdt와 edt 값을 가져오기
    let isInvalid = false; // 기간이 겹치는지 여부
    rows.forEach(row => {
        let uniSdt = row.querySelector('.date-column[data-uni-sdt]').getAttribute('data-uni-sdt');
        let uniEdt = row.querySelector('.date-column[data-uni-edt]').getAttribute('data-uni-edt');

        // uniSdt와 uniEdt를 YYYY-MM-DD 형식으로 변환
        uniSdt = `${uniSdt.slice(0, 4)}-${uniSdt.slice(4, 6)}-${uniSdt.slice(6)}`;
        uniEdt = `${uniEdt.slice(0, 4)}-${uniEdt.slice(4, 6)}-${uniEdt.slice(6)}`;

        // Date 객체로 변환
        const sdtDate = new Date(uniSdt);
        const edtDate = new Date(uniEdt);

        // 입력된 날짜가 기존 기간 안에 포함되어 있는지 확인
        if (
            (start >= sdtDate && start <= edtDate) || // 입력 시작 날짜가 기존 기간에 포함
            (end >= sdtDate && end <= edtDate) ||   // 입력 종료 날짜가 기존 기간에 포함
            (start <= sdtDate && end >= edtDate)    // 입력 기간이 기존 기간을 포함
        ) {
            isInvalid = true;
        }
    });

    if (isInvalid) {
		Swal.fire({
            icon: "error",
            text: "선택한 기간이 기존 기간과 겹칩니다.",
        });
        clearInputs(inputUniSdt,inputUniEdt);
    }
}

// 경력사항 시작일 및 종료일 선택 불가
const inputCareerStartDate = document.querySelector("#careerStartDate");
const inputCareerEndDate = document.querySelector("#careerEndDate");

function validateCareer() {
    // uniTableBody 안의 모든 행을 선택
    const rows = document.querySelectorAll('#careerTableBody tr');

    // 입력값을 Date 객체로 변환
    const start = new Date(inputCareerStartDate.value);
    const end = new Date(inputCareerEndDate.value);


    // 각 행에서 sdt와 edt 값을 가져오기
    let isInvalid = false; // 기간이 겹치는지 여부
    rows.forEach(row => {
        let careerSdt = row.querySelector('.date-column[data-career-sdt]').getAttribute('data-career-sdt');
        let careerEdt = row.querySelector('.date-column[data-career-edt]').getAttribute('data-career-edt') || null;

        careerSdt = `${careerSdt.slice(0, 4)}-${careerSdt.slice(4, 6)}-${careerSdt.slice(6)}`;
        const sdtDate = new Date(careerSdt);
        let edtDate = null;

        if (careerEdt && careerEdt.trim() !== "") {
            careerEdt = `${careerEdt.slice(0, 4)}-${careerEdt.slice(4, 6)}-${careerEdt.slice(6)}`;
            edtDate = new Date(careerEdt);
			// 잘못된 날짜 형식인 경우 edtDate를 null로 처리
            if (isNaN(edtDate.getTime())) {
                edtDate = null;
            }
        }

        if ((!edtDate && start >= sdtDate) || (!edtDate&& start <= sdtDate))  {
            isInvalid = true;
            Swal.fire({
                icon: "error",
                text: "재직 중인 경력이 존재합니다.",
            });
            return; // 함수 종료
        }

        if (
            edtDate && (
                (start >= sdtDate && start <= edtDate) ||
                (end >= sdtDate && end <= edtDate) ||
                (start <= sdtDate && end >= edtDate)
            )
        ) {
            isInvalid = true;
            Swal.fire({
                icon: "error",
                text: "선택한 기간이 기존 기간과 겹칩니다.",
            });
            return; // 함수 종료
        }
    });

    if (isInvalid) {
        clearInputs(inputCareerStartDate,inputCareerEndDate);
    }
}

// 교육수료사항 시작일 및 종료일 선택 불가
const inputEduSdt = document.querySelector("#eduSdt");
const inputEduEdt = document.querySelector("#eduEdt");

function validateEdu() {
    // uniTableBody 안의 모든 행을 선택
    const rows = document.querySelectorAll('#eduTableBody tr');

    // 입력값을 Date 객체로 변환
    const start = new Date(inputEduSdt.value);
    const end = new Date(inputEduEdt.value);


    // 각 행에서 sdt와 edt 값을 가져오기
    let isInvalid = false; // 기간이 겹치는지 여부
    rows.forEach(row => {
        let eduSdt = row.querySelector('.edu-eduSdt[data-edu-sdt]').getAttribute('data-edu-sdt');
        let eduEdt = row.querySelector('.edu-eduEdt[data-edu-edt]').getAttribute('data-edu-edt');

        // uniSdt와 uniEdt를 YYYY-MM-DD 형식으로 변환
        eduSdt = `${eduSdt.slice(0, 4)}-${eduSdt.slice(4, 6)}-${eduSdt.slice(6)}`;
        eduEdt = `${eduEdt.slice(0, 4)}-${eduEdt.slice(4, 6)}-${eduEdt.slice(6)}`;

        // Date 객체로 변환
        const sdtDate = new Date(eduSdt);
        const edtDate = new Date(eduEdt);

        // 입력된 날짜가 기존 기간 안에 포함되어 있는지 확인
        if (
            (start >= sdtDate && start <= edtDate) || // 입력 시작 날짜가 기존 기간에 포함
            (end >= sdtDate && end <= edtDate) ||   // 입력 종료 날짜가 기존 기간에 포함
            (start <= sdtDate && end >= edtDate)    // 입력 기간이 기존 기간을 포함
        ) {
            isInvalid = true;
        }
    });

    if (isInvalid) {
		Swal.fire({
            icon: "error",
            text: "선택한 기간이 기존 기간과 겹칩니다.",
        });
        clearInputs(inputEduSdt,inputEduEdt);
    }
}

// 해외연수 및 경험 시작일 및 종료일 선택 불가
const inputExpSdt = document.querySelector("#expSdt");
const inputExpEdt = document.querySelector("#expEdt");

function validateExp() {
    // uniTableBody 안의 모든 행을 선택
    const rows = document.querySelectorAll('#expTableBody tr');

    // 입력값을 Date 객체로 변환
    const start = new Date(inputExpSdt.value);
    const end = new Date(inputExpEdt.value);

    // 각 행에서 sdt와 edt 값을 가져오기
    let isInvalid = false; // 기간이 겹치는지 여부
    rows.forEach(row => {
        let expSdt = row.querySelector('.exp-expdt[data-exp-sdt]').getAttribute('data-exp-sdt');
        let expEdt = row.querySelector('.exp-expdt[data-exp-edt]').getAttribute('data-exp-edt');

        // uniSdt와 uniEdt를 YYYY-MM-DD 형식으로 변환
        expSdt = `${expSdt.slice(0, 4)}-${expSdt.slice(4, 6)}-${expSdt.slice(6)}`;
        expEdt = `${expEdt.slice(0, 4)}-${expEdt.slice(4, 6)}-${expEdt.slice(6)}`;

        // Date 객체로 변환
        const sdtDate = new Date(expSdt);
        const edtDate = new Date(expEdt);

        // 입력된 날짜가 기존 기간 안에 포함되어 있는지 확인
        if (
            (start >= sdtDate && start <= edtDate) || // 입력 시작 날짜가 기존 기간에 포함
            (end >= sdtDate && end <= edtDate) ||   // 입력 종료 날짜가 기존 기간에 포함
            (start <= sdtDate && end >= edtDate)    // 입력 기간이 기존 기간을 포함
        ) {
            isInvalid = true;
        }
    });

    if (isInvalid) {
		Swal.fire({
            icon: "error",
            text: "선택한 기간이 기존 기간과 겹칩니다.",
        });
        clearInputs(inputExpSdt,inputExpEdt);
    }
}

// 봉사활동 및 주요활동 시작일 및 종료일 선택 불가
const inputActivitySdt = document.querySelector("#activitySdt");
const inputActivityEdt = document.querySelector("#activityEdt");

function validateActivity() {
    const rows = document.querySelectorAll('#actTableBody tr');
    const start = new Date(inputActivitySdt.value);
    const end = new Date(inputActivityEdt.value);

    let isInvalid = false;

    rows.forEach(row => {
        let actSdt = row.querySelector('.act-actdt[data-activity-sdt]').getAttribute('data-activity-sdt');
        let actEdt = row.querySelector('.act-actdt[data-activity-edt]')?.getAttribute('data-activity-edt') || null;

        actSdt = `${actSdt.slice(0, 4)}-${actSdt.slice(4, 6)}-${actSdt.slice(6)}`;
        const sdtDate = new Date(actSdt);
        let edtDate = null;

        if (actEdt && actEdt.trim() !== "") {
            actEdt = `${actEdt.slice(0, 4)}-${actEdt.slice(4, 6)}-${actEdt.slice(6)}`;
            edtDate = new Date(actEdt);
			// 잘못된 날짜 형식인 경우 edtDate를 null로 처리
            if (isNaN(edtDate.getTime())) {
                edtDate = null;
            }
        }

        if ((!edtDate && start >= sdtDate) || (!edtDate&& start <= sdtDate))  {
            isInvalid = true;
            Swal.fire({
                icon: "error",
                text: "참여 중인 활동이 존재합니다.",
            });
            return; // 함수 종료
        }

        if (
            edtDate && (
                (start >= sdtDate && start <= edtDate) ||
                (end >= sdtDate && end <= edtDate) ||
                (start <= sdtDate && end >= edtDate)
            )
        ) {
            isInvalid = true;
            Swal.fire({
                icon: "error",
                text: "선택한 기간이 기존 기간과 겹칩니다.",
            });
            return; // 함수 종료
        }
    });

    if (isInvalid) {
        clearInputs(inputActivitySdt, inputActivityEdt);
    }
}

// 입력값을 비우는 함수
function clearInputs(inputStart,inputEnd) {
    inputStart.value = "";
    inputEnd.value = "";
}

// 대학교 이벤트 리스너 추가
inputUniSdt.addEventListener("change", validateUni);
inputUniEdt.addEventListener("change", validateUni);

// 경력사항 이벤트 리스너 추가
inputCareerStartDate.addEventListener("change", validateCareer);
inputCareerEndDate.addEventListener("change", validateCareer);

// 교육수료사항 이벤트 리스너 추가
inputEduSdt.addEventListener("change", validateEdu);
inputEduEdt.addEventListener("change", validateEdu);

// 해외연수 및 경험 이벤트 리스너 추가
inputExpSdt.addEventListener("change", validateExp);
inputExpEdt.addEventListener("change", validateExp);

// 봉사활동 이벤트 리스너 추가
inputActivitySdt.addEventListener("change", validateActivity);
inputActivityEdt.addEventListener("change", validateActivity);
