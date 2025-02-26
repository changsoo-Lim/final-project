document.addEventListener("DOMContentLoaded", () => {
	const ingBox = document.querySelector("#ingBox");
	const startDateInput = document.querySelector("#careerStartDate");
	const endDateInput = document.querySelector("#careerEndDate");

	// 재직중 체크박스 상태 변경 시 경력 기간 계산
	ingBox.addEventListener("change", calculateWorkDuration);

	// 시작일 또는 종료일 변경 시 경력 기간 계산
	startDateInput.addEventListener("change", calculateWorkDuration);
	endDateInput.addEventListener("change", calculateWorkDuration);

	const navLinks = document.querySelectorAll("ul.nav.nav-tabs .nav-link");

	navLinks.forEach((link) => {
		link.addEventListener("click", (event) => {
			event.preventDefault();
			const targetId = link.getAttribute("data-bs-target");
			const targetSection = document.querySelector(targetId);

			// 스크롤 이동
			targetSection.scrollIntoView({
				behavior: "smooth", // 부드러운 스크롤
				block: "start" // 섹션 상단으로 이동
			});
		});
	});
	const currentUrl = window.location.href;
	if (currentUrl == "http://localhost/stackUp/resume/new?hope=true") {
		document.getElementById('disabled-tab').click();
	}

	toggleCareerDiv(); // 라디오 버튼 상태에 따라 초기 상태 설정

});

function toggleEmploymentStatus() {
	const $ingBox = $("#ingBox");
	const $endDateInput = $("#careerEndDate");
	const $ingDateSpan = $("#ingDate");
	const $careerReasonDiv = $("#careerReasonDiv");

	if ($ingBox.is(":checked")) {
		$endDateInput.hide().val(""); // 종료일 숨기고 값 초기화
		$ingDateSpan.show(); // "현재 재직 중" 표시
		$careerReasonDiv.hide(); // 퇴직 사유 숨기기
	} else {
		$endDateInput.show(); // 종료일 표시
		$ingDateSpan.hide(); // "현재 재직 중" 숨기기
		$careerReasonDiv.show(); // 퇴직 사유 표시
	}
}

function calculateWorkDuration() {
	const startDateInput = document.querySelector("#careerStartDate");
	const endDateInput = document.querySelector("#careerEndDate");
	const ingBox = document.querySelector("#ingBox");
	const resultSpan = document.querySelector("#ingSpan");

	const startDateValue = startDateInput?.value;
	let startDate = null;
	let endDate = null;

	if (startDateValue) {
		startDate = new Date(startDateValue);
	}

	// 종료일이 입력되었거나 "재직중"이 체크된 경우 처리
	if (ingBox.checked) {
		endDate = new Date(); // 오늘 날짜로 종료일 설정
	} else if (endDateInput?.value) {
		endDate = new Date(endDateInput.value);
	}

	if (startDate && endDate) {
		if (endDate < startDate) {
			Swal.fire({
				icon: "error",
				text: "종료일은 시작일보다 빠를 수 없습니다.",
			});
			endDateInput.value = "";
		} else {
			const yearDiff = endDate.getFullYear() - startDate.getFullYear();
			const monthDiff = endDate.getMonth() - startDate.getMonth();
			const dayDiff = endDate.getDate() - startDate.getDate();

			let totalMonths = yearDiff * 12 + monthDiff;
			if (dayDiff < 0) {
				totalMonths -= 1;
			}

			const years = Math.floor(totalMonths / 12);
			const months = totalMonths % 12;

			if (years > 0 && months > 0) {
				resultSpan.innerText = `${years}년 ${months}개월`;
			} else if (years > 0) {
				resultSpan.innerText = `${years}년`;
			} else {
				resultSpan.innerText = `${months}개월`;
			}
			resultSpan.style.color = "green";
		}
	} else {
		resultSpan.innerText = "";
	}
}



function toggleFields() {

	const highschoolFields = $('#highschoolFields');
	const schoolDateFields = $('#schoolDateFields');
	const majorFields = $('#majorFields');
	const examPassDate = $('#examPassDate');

	highschoolFields.toggle();
	schoolDateFields.toggle();
	majorFields.toggle();
	examPassDate.toggle();

}

function toggleCareerDiv() {
	const careerNew = document.querySelector('#careerNew'); // 신입 라디오 버튼
	const careerField = document.querySelector('#careerField'); // 경력 상세정보 div
	const careerTable = document.querySelector('#careerTable'); // 경력 상세정보 div

	if (careerNew.checked) {
		careerField.style.display = "none"; // 신입 선택 시 숨기기
		careerTable.style.display = "none"; // 신입 선택 시 숨기기
	} else {
		careerField.style.display = "block"; // 경력 선택 시 보이기
		careerTable.style.display = "block"; // 경력 선택 시 보이기
	}
}

// 함수 - 체크박스 상태에 따라 폼 보이기/숨기기 및 li 연동
function toggleForm(checkboxId, formId) {
	const checkbox = document.getElementById(checkboxId);
	const form = document.getElementById(formId);
	const liItem = document.querySelector(`#categoryList li[data-id="${checkboxId}"]`);

	checkbox.addEventListener('change', function() {
		if (checkbox.checked) {
			// 폼 보이기
			form.style.display = 'block';
			if (liItem) {
				liItem.classList.add('active'); // li 활성화
			}

			// 스크롤 이동
			form.scrollIntoView({
				behavior: 'auto',
				block: 'start',
			});
		} else {
			form.style.display = 'none';
			if (liItem) {
				liItem.classList.remove('active'); // li 비활성화
			}
		}
	});
}

// 각 체크박스와 폼 연결
toggleForm('btncheck1', 'cretForm'); // 선택사항(자격증)
toggleForm('btncheck2', 'compForm'); // 선택사항(컴활)
toggleForm('btncheck3', 'prefForm'); // 선택사항(취업우대 및 특이사항)
toggleForm('btncheck4', 'awardForm'); // 선택사항(수상경력)
toggleForm('btncheck5', 'eduForm'); // 선택사항(교육 수료사항)
toggleForm('btncheck6', 'langForm'); // 선택사항(어학능력)
toggleForm('btncheck7', 'expForm'); // 선택사항(해외연수 및 경험)
toggleForm('btncheck8', 'actForm'); // 선택사항(봉사활동 및 주요활동)
toggleForm('btncheck9', 'porForm'); // 선택사항(포트폴리오(URL))

// li 클릭 시 체크박스 토글 및 탭 버튼 클릭 이벤트 트리거
document.querySelectorAll("#categoryList li").forEach((item) => {
	item.addEventListener("click", function() {
		const checkboxId = this.dataset.id; // li의 data-id 속성으로 체크박스 ID 가져오기
		const checkbox = document.getElementById(checkboxId);

		// 클릭된 li의 active 상태 토글
		if (this.classList.contains("active")) {
			// 이미 active 상태인 경우 비활성화
			this.classList.remove("active");
			if (checkbox) {
				checkbox.checked = false; // 체크박스 해제
				checkbox.dispatchEvent(new Event("change")); // change 이벤트 트리거
			}
		} else {
			// active 상태가 아닌 경우 활성화
			this.classList.add("active");
			if (checkbox) {
				checkbox.checked = true; // 체크박스 선택
				checkbox.dispatchEvent(new Event("change")); // change 이벤트 트리거
			}
		}

		// 탭 버튼 클릭 이벤트 발생
		const tabButton = document.getElementById("contact-tab"); // 탭 버튼 ID
		if (tabButton && tabButton.getAttribute("aria-selected") === "false") {
			tabButton.click(); // aria-selected="false"일 때만 탭 버튼 클릭
		}
	});
});

// 봉사활동
function toggleaAtivityStatus() {
	const activityBox = document.getElementById('activityBox').checked;
	const activityEdt = document.getElementById('activityEdt');
	const activitySpan = document.getElementById('activitySpan');
	if (activityBox) {
		activityEdt.style.display = "none"; // 종료일 숨기기
		activityEdt.value = ""; // 종료일 초기화
		activitySpan.style.display = "inline"; // "현재까지" 보여주기
	} else {
		activityEdt.style.display = "block"; // 종료일 표시
		activitySpan.style.display = "none"; // "현재까지" 숨기기
	}
}

function searchQualifications() {
	const searchInput = document.getElementById('certInput').value.toLowerCase();
	const certList = document.getElementById('certList');
	const certItems = document.querySelectorAll('.cert-item');

	let hasMatch = false;

	// 리스트 초기화
	certItems.forEach(item => {
		const itemName = item.textContent.toLowerCase();
		if (itemName.includes(searchInput)) {
			item.style.display = ''; // 검색 결과 표시
			hasMatch = true;
		} else {
			item.style.display = 'none'; // 일치하지 않으면 숨기기
		}
	});

	// 검색어가 없거나 일치하는 결과가 없으면 리스트 숨김
	if (searchInput === '' || !hasMatch) {
		certList.style.display = 'none';
	} else {
		certList.style.display = 'block'; // 검색 결과 표시
	}
}

// 항목 클릭 시 값 설정
document.getElementById('certList').addEventListener('click', function(e) {
	if (e.target && e.target.classList.contains('cert-item')) {
		const selectedItem = e.target;
		const certInput = document.getElementById('certInput');
		const certNm = document.getElementById('certNm');
		const certIssuer = document.getElementById('certIssuer');

		// 텍스트 필드에는 사용자에게 표시될 이름을 설정
		certInput.value = selectedItem.textContent;
		certIssuer.value = "산업인력공단";
		// 숨겨진 필드에는 코드 값을 설정
		certNm.value = selectedItem.getAttribute('data-code');

		// 리스트 숨김
		document.getElementById('certList').style.display = 'none';
	}
});

document.querySelector("#careerSalary").addEventListener("input", function(event) {
	const input = event.target;
	let value = input.value.replace(/,/g, "");

	if (!isNaN(value) && value !== "") {
		// 숫자일 경우
		input.value = parseInt(value, 10).toLocaleString("en-US");
	} else {
		// 숫자가 아닐 경우
		input.value = "";
	}
});


// 이미지 미리보기
document.querySelector('input[name="uploadFiles"]').addEventListener('change', function(event) {
	const file = event.target.files[0];
	if (file) {
		const reader = new FileReader();
		reader.onload = function(e) {
			// 업로드한 이미지를 미리보기로 표시
			document.getElementById('profileImage').src = e.target.result;
		};
		reader.readAsDataURL(file);
	} else {
		alert("파일을 선택해주세요.");
	}
});


// 이미지 등록 및 미리보기
document.getElementById('fileBtn').addEventListener('change', function(event) {
	const file = event.target.files[0];
	const baseUrl = document.body.dataset.url;
	if (file) {
		const reader = new FileReader();


		reader.onload = function(e) {
			document.getElementById('profileImage').src = e.target.result;
		};
		reader.readAsDataURL(file);

		// 파일을 서버로 전송
		const formData = new FormData();
		formData.append("uploadFiles", file);

		fetch(`${baseUrl}/resume/image`, {
			method: "POST",
			body: formData,
		})
			.then(response => {
				if (response.ok) {
					return response.json();
				}
			})
			.then(data => {
				if (data.status === "success") {
					Swal.fire({
						icon: "success",
						text: "이미지가 성공적으로 등록되었습니다.",
					});
				}
			})
			.catch(error => {
				console.error("이미지 등록 중 오류 발생:", error);
			});
	}
});

// 모든 폼 데이터 초기화
document.getElementById("resetBtn").addEventListener("click", function() {
	// 모든 폼 초기화
	clearFormField("uniField");
	clearFormField("careerField");
	clearFormField("certField");
	clearFormField("compField");
	clearFormField("awardField");
	clearFormField("eduField");
	clearFormField("langField");
	clearFormField("langTestField");
	clearFormField("expField");
	clearFormField("actField");
	toggleEmploymentStatus();
	toggleaAtivityStatus();
});

// 발표시 데이터 입력
document.querySelector("#dataBtn").addEventListener("click", function() {
	// 고등학교
	document.querySelector("#highschoolNm").value = "대전고등학교";			// 고등학교명
	document.querySelector("#highschoolLocation").value = "대전";			// 소재지
	document.querySelector("#highschoolPeriod").value = "2018-02-01";		// 재학기간
	document.querySelector("#highschoolStatus").value = "graduation-03";	// 졸업
	document.querySelector("#highschoolMajor").value = "high-major-01";		// 문과

	// 대학교
	document.querySelector("#uniNm").value = "대전대학교";						 // 대학교명
	document.querySelector("#uniType").value = "univ-type-02";					// 대학 (2,3년,4년, 대학원)
	document.querySelector("#uniSdt").value = "2018-03-02";						// 대학 재학기간 시작일자
	document.querySelector("#uniSStatus").value = "admission-01";				// 대학 입학여부
	document.querySelector("#uniEdt").value = "2023-01-15";						// 대학 재학기간 종료일자
	document.querySelector("#uniEStatus").value = "graduation-03";				// 대학 종료여부
	document.querySelector("#uniMajorCategory").value = "univ-major-05";		// 대학 전공 (계열)
	document.querySelector("#uniMajorNm").value = "컴퓨터공학과";				 // 대학 전공명
	document.querySelector("#uniMajorDegree").value = "univ-degree-01";			// 대학 석박사
	document.querySelector("#uniGpa").value = 4.5;								// 대학 학점
	document.querySelector("#uniProjectNm").value = "캡스톤 디자인";		     // 대학 수업 및 프로젝트 명
	document.querySelector("#uniProjectDesc").value = "이미지 처리를 통한 TTS 기능을 탑재한 웹사이트";			// 고등학교명
	
	// 경력사항
	document.querySelector("#careerOld").click();
	document.querySelector("#careerCompanyNm").value = "대덕인재개발원";
	document.querySelector("#careerIndustryType").value = "in03";
	document.querySelector("#careerSubIndustry").value = "in301";
	document.querySelector("#careerCompanySize").value = "company-size-5";
	document.querySelector("#careerCompanyType").value = "company-type-1";
	document.querySelector("#careerListed").value = "company-listing01";
	document.querySelector("#careerCity").value = "city06";
	document.querySelector("#careerDistrict").value = "city06-05";
	document.querySelector("#careerStartDate").value = "2024-02-02";
	document.querySelector("#ingBox").click();
	calculateWorkDuration();
	document.querySelector("#careerJobTitle").value = "연근대학교 웹사이트 도메인 구축";
	document.querySelector("#careerJobDesc").value = 
        `2024-11 ~ 2025-01\n연근대학교 도메인 구축 및 로그인 기능 구현`;
	document.querySelector("#careerPosition").value = "주임";
	document.querySelector("#careerDepartment").value = "대덕인재개발원 개발부";
	document.querySelector("#careerType").value = "work-type-1";
	document.querySelector("#careerSalary").value = "3,000";
	
	// 자격증
	document.querySelector("#certInput").value = "정보처리기사";
	document.querySelector("#certNm").value = "cer01004086";
	document.querySelector("#certIssuer").value = "산업인력공단";
	document.querySelector("#certDate").value = "2025-01-17";
	document.querySelector("#certPassCd").value = "cret01";

	// 교육수료사항
	document.querySelector("#eduTitle").value = "전자정부프레임워크 풀스택 개발자과정 22회차";
	document.querySelector("#eduInstitution").value = "대덕인재개발원";
	document.querySelector("#eduSdt").value = "2024-06-03";
	document.querySelector("#eduEdt").value = "2025-01-17";
	document.querySelector("#eduDesc").value = "406호 여러분 다들 고생하셨습니다!!!!!!!!!!!!!!!!";
	
	// 어학능혁
	document.querySelector("#langNm").value = "lang10";
	document.querySelector("#langSpeakingLevel").value = "speak02";
	document.querySelector("#langReadingLevel").value = "read02";
	document.querySelector("#langWritingLevel").value = "write02";
	
	// 희망근무조건
	const fakeForm = document.querySelector("#workFieldList");
    document.querySelector("select[name='sidoCd']").value = "city09";
    document.querySelector("select[name='gugunCd']").value = "city09-02";
	document.querySelector("#workAddBtn").click();
    fakeForm.querySelector("select[name='sidoCd']").value = "city06";
    fakeForm.querySelector("select[name='gugunCd']").value = "city06-05";
	document.querySelector("#workCondRemote").click();
	document.querySelector("#workCondJobType").value = "웹개발,모바일,서비스,솔루션,SI";
	document.querySelector("#workCondJobType").dispatchEvent(new Event("input")); // input 이벤트 트리거
	document.querySelector("#workCondJobType").dispatchEvent(new Event("change")); // change 이벤트 트리거
	
	// 체크할 값들을 배열로 변환
	const workCondTypes = "work-type-1,work-type-5,work-type-8".split(",");

	// 모든 체크박스를 순회하면서 값이 일치하는 체크박스를 체크
	document.querySelectorAll("input[name='workCondType']").forEach(checkbox => {
	    checkbox.checked = workCondTypes.includes(checkbox.value);
	    checkbox.dispatchEvent(new Event("change")); // 강제로 change 이벤트 트리거
	});


	document.querySelector("#workCondSalary").value = "salary-3000-3500";
})