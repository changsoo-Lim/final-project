document.addEventListener("DOMContentLoaded", () => {

	function filterOptions($targetSelect, filterClass) {
		const $options = $targetSelect.find("option");
		if (filterClass) {
			$options.hide(); // 모든 옵션 숨기기
			$options.filter(`:first, .${filterClass}`).show(); // 기본값과 필터된 옵션만 보이기
		} else {
			$options.hide(); // 모든 옵션 숨기기
			$options.filter(":first").show(); // 기본값만 보이기
		}
		$targetSelect.val(""); // 선택 초기화
	}


	function setupFilterEvent($sourceSelect, $targetSelect, $hiddenInput = null) {
		$sourceSelect.on("change", (event) => {
			const selectedValue = event.target.value;
			const selectedOption = $sourceSelect.find("option:selected");
			const filterClass = selectedValue ? selectedOption.data("code") : null;

			filterOptions($targetSelect, filterClass);

			if ($hiddenInput) {
				$hiddenInput.val(filterClass || ""); // hidden input 값 업데이트
			}
		});

		// 초기 로드 시 상세 옵션 숨기기
		if ($sourceSelect.val() === "") {
			filterOptions($targetSelect, null);
		}
	}

	// 경력사항 업종,상세업종
	const $careerIndustryType = $("#careerIndustryType");
	const $careerSubIndustry = $("#careerSubIndustry");
	const $hiddenInput = $("#selectedIndustryCode");

	setupFilterEvent($careerIndustryType, $careerSubIndustry, $hiddenInput);

	// 경력사항 시도 , 구군
	const $careerCity = $("#careerCity");
	const $careerDistrict = $("#careerDistrict");
	const $hiddenInputCity = $("#selectedCityCode");

	setupFilterEvent($careerCity, $careerDistrict, $hiddenInputCity);

	// 컴활
	const $compSkillField = $("#compSkillField");
	const $compSkillDetail = $("#compSkillDetail");
	const $hiddenInputCom = $("#selectedComCode");

	setupFilterEvent($compSkillField, $compSkillDetail, $hiddenInputCom);

	const $sidoCd = $("#sidoCd");
	const $gugunCd = $("#gugunCd");
	const $hiddenInputWork = $("#selectedWorkCode");

	setupFilterEvent($sidoCd, $gugunCd, $hiddenInputWork);


	// ----------------------직종 5개 제한--------------------------
	const workCondJobType = document.querySelector("#workCondJobType");
	const jobTypeCount = document.querySelector("#jobTypeCount");
	const maxWords = 5; // 최대 단어 개수

	// 값 업데이트 로직
	function updateJobTypeCount(value) {
		const words = value.split(",").map(word => word.trim()).filter(word => word !== ""); // 단어 분리 및 정리

		if (words.length > maxWords) {
			// SweetAlert2 경고창 표시
			Swal.fire({
				title: "<strong>단어 개수 초과</strong>",
				icon: "info",
				html: `
                <b>직종</b>은 최대 <b>${maxWords}개</b>까지만 입력 가능합니다.
            `,
				showCloseButton: true,
				focusConfirm: false,
				confirmButtonText: `
                <i class="fa fa-thumbs-up"></i> 확인
            `,
				confirmButtonAriaLabel: "확인",
			});

			// 초과된 단어 제거
			workCondJobType.value = words.slice(0, maxWords).join(", ");
			jobTypeCount.textContent = `(${maxWords}/${maxWords})`;
		} else {
			// 단어 개수 업데이트
			jobTypeCount.textContent = `(${words.length}/${maxWords})`;
		}
	}

	// 입력 이벤트 및 값 변경 감지
	workCondJobType.addEventListener("input", event => updateJobTypeCount(event.target.value));
	workCondJobType.addEventListener("change", event => updateJobTypeCount(event.target.value));

	// 외부에서 값을 설정한 경우 이벤트 트리거
	function setWorkCondJobType(value) {
		workCondJobType.value = value;
		workCondJobType.dispatchEvent(new Event("input")); // 강제로 input 이벤트 트리거
	}


	const workCondType = document.querySelectorAll("input[name='workCondType']");
	const workTypeCount = document.querySelector("#workTypeCount");
	const maxSelections = 3; // 최대 선택 개수

	// 값 업데이트 로직
	function updateWorkTypeCount(event) {
		const selectedCount = Array.from(workCondType).filter(cb => cb.checked).length;

		if (selectedCount > maxSelections) {
			// SweetAlert2 경고창
			Swal.fire({
				title: "<strong>선택 개수 초과</strong>",
				icon: "info",
				html: `
                <b>근무형태</b>는 최대 <b>${maxSelections}개</b>까지만 선택 가능합니다.
            `,
				showCloseButton: true,
				focusConfirm: false,
				confirmButtonText: `
                <i class="fa fa-thumbs-up"></i> 확인
            `,
				confirmButtonAriaLabel: "확인",
			});

			// 선택 초과된 체크박스 취소
			event.target.checked = false;
			return; // 선택 개수 업데이트 방지
		}

		// 선택 개수 업데이트
		workTypeCount.textContent = `(${selectedCount}/${maxSelections})`;
	}

	// 체크박스 변경 감지
	workCondType.forEach(checkbox => {
		checkbox.addEventListener("change", updateWorkTypeCount);
	});

	// 외부에서 값을 설정한 경우 이벤트 트리거
	function setWorkCondType(values) {
		workCondType.forEach(checkbox => {
			checkbox.checked = values.includes(checkbox.value);
		});
		workCondType[0].dispatchEvent(new Event("change")); // 강제로 change 이벤트 트리거
	}
	//-------------------------근무지역-------------------------------
	const workField = document.querySelector("#workField");
	const workFieldList = document.querySelector("#workFieldList");
	const workAddBtn = document.querySelector("#workAddBtn");
	function filterOption($targetSelect, filterClass) {
		const $options = $targetSelect.find("option");
		if (filterClass) {
			$options.hide(); // 모든 옵션 숨기기
			$options.filter(`:first, .${filterClass}`).show(); // 기본값과 필터된 옵션만 보이기
		} else {
			$options.hide(); // 모든 옵션 숨기기
			$options.filter(":first").show(); // 기본값만 보이기
		}
		$targetSelect.val(""); // 선택 초기화
	}

	function filterEvent($sourceSelect, $targetSelect, $hiddenInput = null) {
		$sourceSelect.on("change", (event) => {
			const selectedValue = event.target.value;
			const selectedOption = $sourceSelect.find("option:selected");
			const filterClass = selectedValue ? selectedOption.data("code") : null;

			filterOption($targetSelect, filterClass);

			if ($hiddenInput) {
				$hiddenInput.val(filterClass || ""); // hidden input 값 업데이트
			}
		});

		// 초기 로드 시 상세 옵션 숨기기
		if ($sourceSelect.val() === "") {
			filterOption($targetSelect, null);
		}
	}
	function initializeWorkFormEvents(form) {
		const $clonedWorkCondCity = $(form).find("#sidoCd");
		const $clonedWorkCondDistrict = $(form).find("#gugunCd");
		filterEvent($clonedWorkCondCity, $clonedWorkCondDistrict);
	}

	function toggleElementDisplay(element, displayStyle) {
		if (element) {
			element.style.display = displayStyle;
		}
	}

	// "추가" 버튼 클릭 이벤트
	workAddBtn.addEventListener("click", function() {
		// 현재 폼 개수를 체크
		let existingForms = workFieldList.querySelectorAll(".row").length;

		if (existingForms >= 4) {
			// SweetAlert2 경고창
			Swal.fire({
				title: "<strong>최대 개수 초과</strong>",
				icon: "info",
				html: `
                <b>희망 근무지역</b>은 최대 <b>5개</b>까지 선택 가능합니다.
            `,
				showCloseButton: true,
				focusConfirm: false,
				confirmButtonText: `
                <i class="fa fa-thumbs-up"></i> 확인
            `,
				confirmButtonAriaLabel: "확인",
			});
			return; // 추가 동작 중단
		}

		// 원본 폼 복제
		let clonedForm = workField.cloneNode(true);

		// 복제된 폼 내부의 모든 input, select, textarea 값을 초기화
		let inputs = clonedForm.querySelectorAll("input, select, textarea");
		inputs.forEach(input => {
			if (input.type === "checkbox" || input.type === "radio") {
				input.checked = false; // 체크박스와 라디오 버튼 초기화
			} else {
				input.value = ""; // 일반 input, select, textarea 값 초기화
			}
		});

		// 추가 버튼 및 라벨 처리
		toggleElementDisplay(clonedForm.querySelector("#workAddBtn"), "none");
		toggleElementDisplay(clonedForm.querySelector("#workLable"), "none");
		toggleElementDisplay(clonedForm.querySelector("#workLableFake"), "block");

		// 삭제 버튼 설정
let deleteBtn = clonedForm.querySelector("#workDelBtn");
if (deleteBtn) {
    deleteBtn.style.display = "block"; // 삭제 버튼 보이기
    deleteBtn.addEventListener("click", function () {
        Swal.fire({
            title: "정말 삭제하시겠습니까?",
            icon: "warning",
            showCancelButton: true, // 취소 버튼 추가
            confirmButtonText: "네, 삭제합니다.",
            cancelButtonText: "아니요, 취소합니다.",
        }).then((result) => {
            if (result.isConfirmed) {
				let workCityNo = clonedForm.querySelector("#workCityNo").value.trim();

				if (workCityNo) {
				    // workCityNo 값이 존재하면 서버에 DELETE 요청
				    fetch(`${baseUrl}/workCity/${workCityNo}`, {
				        method: "DELETE",
				        headers: {
				            "Content-Type": "application/json",
				        },
				        body: JSON.stringify({ workCityNo }),
				    })
				        .then((response) => {
				            if (response.ok) {
				                clonedForm.remove(); // 서버 삭제 성공 시 폼 삭제
				            } else {
				                throw new Error("삭제 요청 실패");
				            }
				        })
				        .catch((error) => {
				            console.error("삭제 중 오류 발생:", error);
				        });
				} else {
				    clonedForm.remove();
				}

            }
        });
    });
}

		// 복제된 폼을 리스트 컨테이너에 추가
		workFieldList.appendChild(clonedForm);

		// 복제된 폼에 이벤트 초기화
		initializeWorkFormEvents(clonedForm);
	});

});


const uniInsertBtn = document.querySelector("#uniInsertBtn");

// 대학교 정보 전송
uniInsertBtn.addEventListener("click", function () {
  let uniFields = document.querySelectorAll("#uniField");
  let unisArr = [];
  let missingFields = []; // 누락된 필드를 저장할 배열

  for (let i = 0; i < uniFields.length; i++) {
    let uniField = uniFields[i];

    // 필수 입력값 가져오기
    let uniNm = uniField.querySelector("#uniNm").value.trim(); // 대학교명
    let uniMajorNm = uniField.querySelector("#uniMajorNm").value.trim(); // 전공명
    let uniSdt = uniField.querySelector("#uniSdt").value.trim(); // 입학일자
    let uniEdt = uniField.querySelector("#uniEdt").value.trim(); // 졸업일자

    // 필수 입력값 검증
    if (!uniNm) missingFields.push("대학교명");
    if (!uniMajorNm) missingFields.push("전공명");
    if (!uniSdt) missingFields.push("입학일자");
    if (!uniEdt) missingFields.push("졸업일자");

    // 누락된 항목이 없다면 객체 생성
    if (missingFields.length === 0) {
      let uniOne = {
        uniNo: uniField.querySelector("#uniNo").value, // 대학교번호
        uniNm: uniNm, // 대학교명
        uniType: uniField.querySelector("#uniType").value, // 대학교 타입
        uniSdt: uniSdt.replace(/-/g, ""), // 입학일자 (하이픈 제거)
        uniSStatus: uniField.querySelector("#uniSStatus").value, // 입학 여부
        uniEdt: uniEdt.replace(/-/g, ""), // 졸업일자 (하이픈 제거)
        uniEStatus: uniField.querySelector("#uniEStatus").value, // 졸업 여부
        uniMajorCategory: uniField.querySelector("#uniMajorCategory").value, // 전공 계열
        uniMajorNm: uniMajorNm, // 전공명
        uniMajorDegree: uniField.querySelector("#uniMajorDegree").value, // 석박사
        uniGpa: uniField.querySelector("#uniGpa").value, // 학점
        uniProjectNm: uniField.querySelector("#uniProjectNm").value, // 프로젝트 명
        uniProjectDesc: uniField.querySelector("#uniProjectDesc").value, // 프로젝트 내용
      };

      unisArr.push(uniOne);
    }
  }

  // 누락된 필드가 있을 경우 alert로 표시하고 함수 종료
  if (missingFields.length > 0) {
    alert("다음 필드를 입력하세요:\n- " + missingFields.join("\n- "));
    return;
  }

  let uniData = {
    uni: unisArr,
  };
  console.log(uniData);

  // 서버로 데이터 전송
  fetch("", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(uniData),
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
    })
    .then((data) => {
      if (data.status === "success") {
        loadUniList();
        clearFormField("uniField");
      }
    })
    .catch((error) => {
      console.error("Fetch Error: ", error);
      alert("요청 처리 중 오류가 발생했습니다.");
    });
});


const carerrInsertBtn = document.querySelector("#carerrInsertBtn");
// 경력사항 정보 전송
carerrInsertBtn.addEventListener("click", function() {
	let careerFields = document.querySelectorAll("#careerField");
	let careersArr = [];

	careerFields.forEach(function(career) {
		let careerOne = {
			careerNo: career.querySelector("#careerNo").value, // 회사명
			careerCompanyNm: career.querySelector("#careerCompanyNm").value, // 회사명
			careerIndustryType: career.querySelector("#careerIndustryType").value, // 업종
			careerSubIndustry: career.querySelector("#careerSubIndustry").value, // 상세업종
			careerCompanySize: career.querySelector("#careerCompanySize").value, // 기업규모
			careerCompanyType: career.querySelector("#careerCompanyType").value, // 기업형태
			careerListed: career.querySelector("#careerListed").value, // 상장여부
			careerCity: career.querySelector("#careerCity").value, // 시도
			careerDistrict: career.querySelector("#careerDistrict").value, // 구군
			careerStartDate: career.querySelector("#careerStartDate").value.replace(/-/g, ""), // 근무기간 시작일
			careerEndDate: career.querySelector("#careerEndDate")?.value.replace(/-/g, "") || null, // 근무기간 종료일
			careerTentre: career.querySelector("[name='careerTentre']:checked")?.value || 'N', // 재직여부
			careerJobTitle: career.querySelector("#careerJobTitle").value, // 담당업무
			careerJobDesc: career.querySelector("#careerJobDesc").value, // 담당업무 내용
			careerPosition: career.querySelector("#careerPosition").value, // 직급/직책
			careerDepartment: career.querySelector("#careerDepartment").value, // 근무부서
			careerType: career.querySelector("#careerType").value, // 근무형태
			careerSalary: career.querySelector("#careerSalary").value.replace(/,/g, ""), // 연봉
			careerReason: career.querySelector("#careerReason").value // 퇴사사유
		};
		careersArr.push(careerOne);
	});

	let careerData = {
		career: careersArr
	};
	console.log(careersArr);
	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(careerData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				loadCareerList();
				clearFormField("careerField");
				toggleEmploymentStatus();
			}
		})
		.catch((error) => {
			console.error("Fetch Error: ", error);
			alert("요청 처리 중 오류가 발생했습니다.");
		});

}); // 경력사항 ㅇㅋ

const certInsertBtn = document.querySelector("#certInsertBtn");
// 자격증 정보 전송
certInsertBtn.addEventListener("click", function() {
	let certFields = document.querySelectorAll("#certField");
	let certsArr = [];

	certFields.forEach(function(cert) {
		let certOne = {
			certNo: cert.querySelector("#certNo").value, // 자격증번호
			certNm: cert.querySelector("input[name='certNm']").value, // 자격증명
			certIssuer: cert.querySelector("#certIssuer").value, // 발행처
			certDate: cert.querySelector("#certDate").value.replace(/-/g, ""), // 취득일자
			certPassCd: cert.querySelector("#certPassCd").value // 합격구분

		};
		certsArr.push(certOne);
	});
	console.log(certsArr);

	let certData = {
		cert: certsArr
	};
	console.log(certsArr);
	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(certData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				loadCertList();
				clearFormField("certField");
			}
		})
		.catch((error) => {
			alert(error, "요청 처리 중 오류가 발생했습니다.");
		});

}); // 자격증 ㅇㅋ

const compInsertBtn = document.querySelector("#compInsertBtn");
// 컴활 정보 전송
compInsertBtn.addEventListener("click", function() {
	let compFields = document.querySelectorAll("#compField");
	let compsArr = [];

	compFields.forEach(function(comp) {
		let compOne = {
			compSkillNo: comp.querySelector("#compSkillNo").value, // 분야
			compSkillField: comp.querySelector("#compSkillField").value, // 분야
			compSkillDetail: comp.querySelector("#compSkillDetail").value, // 세부분야
			compSkillLevel: comp.querySelector("#compSkillLevel").value // 활용능력

		};
		compsArr.push(compOne);
	});
	let compData = {
		comp: compsArr
	};
	console.log(compData);

	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(compData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				loadCompList();
				clearFormField("compField");
			}
		})
		.catch((error) => {
			alert(error, "요청 처리 중 오류가 발생했습니다.");
		});

}); // 컴활 ㅇㅋ

const prefInsertBtn = document.querySelector("#prefInsertBtn");
// 취업우대사항 정보 전송
prefInsertBtn.addEventListener("click", function() {
	let prefFields = document.querySelectorAll("#prefField");
	let prefsArr = [];

	prefFields.forEach(function(pref) {
		let prefOne = {
			prefNo: pref.querySelector("#prefNo").value, // 번호
			prefMilitary: pref.querySelector("[name='prefMilitary']:checked").value, // 병역사항
			prefPatriot: pref.querySelector("[name='prefPatriot']:checked").value, // 보훈대상
			prefDisability: pref.querySelector("[name='prefDisability']:checked").value, // 장애여부
			prefEmploySupport: pref.querySelector("[name='prefEmploySupport']:checked").value, // 고용지원금
			prefHobbies: pref.querySelector("#prefHobbies").value, // 취미,관심
			prefSkills: pref.querySelector("#prefSkills").value // 특기

		};
		prefsArr.push(prefOne);
	});
	let prefData = {
		pref: prefsArr
	};

	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(prefData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				loadPrefList();
			}
		})
		.catch((error) => {
			alert(error, "요청 처리 중 오류가 발생했습니다.");
		});

}); // 취업우대 ㅇㅋ

const awardInsertBtn = document.querySelector("#awardInsertBtn");
// 수상경력 정보 전송
awardInsertBtn.addEventListener("click", function() {
	let awardFields = document.querySelectorAll("#awardField");
	let awardsArr = [];

	awardFields.forEach(function(award) {
		let awardOne = {
			awardNo: award.querySelector("#awardNo").value, // 수상내역
			awardTitle: award.querySelector("#awardTitle").value, // 수상내역
			awardIssuer: award.querySelector("#awardIssuer").value, // 수여기관
			awardDate: award.querySelector("#awardDate").value.replace(/-/g, "") // 수여날짜
		};
		awardsArr.push(awardOne);
	});
	let awardData = {
		award: awardsArr
	};

	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(awardData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				loadAwardList();
				clearFormField("awardField");
			}
		})
		.catch((error) => {
			alert(error, "요청 처리 중 오류가 발생했습니다.");
		});
}); // 수상경력 ㅇㅋ

const eduInsertBtn = document.querySelector("#eduInsertBtn");
// 교육수료사항 정보 전송
eduInsertBtn.addEventListener("click", function() {
	let eduFields = document.querySelectorAll("#eduField");
	let edusArr = [];

	eduFields.forEach(function(edu) {
		let eduOne = {
			eduNo: edu.querySelector("#eduNo").value, // 과정번호
			eduTitle: edu.querySelector("#eduTitle").value, // 과정명
			eduInstitution: edu.querySelector("#eduInstitution").value, // 과정내용
			eduSdt: edu.querySelector("#eduSdt").value.replace(/-/g, ""), // 시작날짜
			eduEdt: edu.querySelector("#eduEdt").value.replace(/-/g, ""), // 종료날짜
			eduDesc: edu.querySelector("#eduDesc").value // 과정내용
		};
		edusArr.push(eduOne);
	});
	let eduData = {
		edu: edusArr
	};

	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(eduData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				loadEduList();
				clearFormField("eduField");
			}
		})
		.catch((error) => {
			alert(error, "요청 처리 중 오류가 발생했습니다.");
		});

}); // 교육수료사항 ㅇㅋ

const langInsertBtn = document.querySelector("#langInsertBtn");
// 어학능력 정보 전송
langInsertBtn.addEventListener("click", function() {
	let langFields = document.querySelectorAll("#langField");
	let langTestFields = document.querySelectorAll("#langTestField");
	let langsArr = [];
	let langTestsArr = [];

	langFields.forEach(function(lang) {
		let langOne = {
			langNo: lang.querySelector("#langNo").value, // 외국어명
			langNm: lang.querySelector("#langNm").value, // 외국어명
			langSpeakingLevel: lang.querySelector("#langSpeakingLevel").value, // 회화능력
			langReadingLevel: lang.querySelector("#langReadingLevel").value, // 독해능력
			langWritingLevel: lang.querySelector("#langWritingLevel").value, // 작문능력
		};
		langsArr.push(langOne);
	});
	langTestFields.forEach(function(langTest) {
		let langTestOne = {
			langTestNo: langTest.querySelector("#langTestNo").value, // 종료날짜
			langTestName: langTest.querySelector("#langTestName").value, // 종료날짜
			langTestLevel: langTest.querySelector("#langTestLevel").value, // 종료날짜
			langTestDate: langTest.querySelector("#langTestDate").value.replace(/-/g, "") // 종료날짜
		};
		langTestsArr.push(langTestOne);
	});

	let langData = {
		language: langsArr,
		langTest: langTestsArr
	};

	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(langData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				loadLangList();
				loadLangTestList();
				clearFormField("langField");
				clearFormField("langTestField");
			}
		})
		.catch((error) => {
			alert(error, "요청 처리 중 오류가 발생했습니다.");
		});

}); // 어학능력 ㅇㅋ

const expInsertBtn = document.querySelector("#expInsertBtn");
// 해외연수 정보 전송
expInsertBtn.addEventListener("click", function() {
	let expFields = document.querySelectorAll("#expField");
	let expsArr = [];

	expFields.forEach(function(exp) {
		let expOne = {
			expNo: exp.querySelector("#expNo").value, // 국가
			expCountry: exp.querySelector("#expCountry").value, // 국가
			expSdt: exp.querySelector("#expSdt").value.replace(/-/g, ""), // 연수기간 시작
			expEdt: exp.querySelector("#expEdt").value.replace(/-/g, ""), // 연수기간 종료
			expDesc: exp.querySelector("#expDesc").value // 연수내용
		};
		expsArr.push(expOne);
	});
	let expData = {
		experience: expsArr,
	};

	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(expData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				loadExpList();
				clearFormField("expField");
			}
		})
		.catch((error) => {
			alert(error, "요청 처리 중 오류가 발생했습니다.");
		});

}); // 해외연수 ㅇㅋ

const actInsertBtn = document.querySelector("#actInsertBtn");
// 봉사활동 정보 전송
actInsertBtn.addEventListener("click", function() {
	let actFields = document.querySelectorAll("#actField");
	let actsArr = [];

	actFields.forEach(function(act) {
		let actOne = {
			activityNo: act.querySelector("#activityNo").value, // 기관,단체명
			activityOrganization: act.querySelector("#activityOrganization").value, // 기관,단체명
			activityDesc: act.querySelector("#activityDesc").value, // 활동내용
			activitySdt: act.querySelector("#activitySdt").value.replace(/-/g, ""), // 활동기간 시작
			activityEdt: act.querySelector("#activityEdt").value.replace(/-/g, "") || null // 활동기간 종료
		};
		actsArr.push(actOne);
	});
	let actData = {
		activity: actsArr,
	};

	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(actData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				loadActList();
				clearFormField("actField");
				toggleaAtivityStatus();
			}
		})
		.catch((error) => {
			alert(error, "요청 처리 중 오류가 발생했습니다.");
		});

}); // 봉사활동 ㅇㅋ

const urlBtn = document.querySelector("#urlBtn");

urlBtn.addEventListener("click", function() {
	let urlModal = document.querySelector("#urlModal");
	let urlArr = [];

	let urlOne = {
		portNm: urlModal.querySelector("#portNm").value, // 기관,단체명
		portUrl: urlModal.querySelector("#portUrl").value // 기관,단체명
	};

	urlArr.push(urlOne);

	let urlData = {
		portfolio: urlArr
	};
	console.log(urlData);
	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(urlData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				loadPortList();
			}
		})
		.catch((error) => {
			alert(error, "요청 처리 중 오류가 발생했습니다.");
		});

}); // URL

const allAddBtn = document.querySelector("#allAddBtn");

allAddBtn.addEventListener("click", function() {
	let quaArr = [];
	let highArr = [];

	if (hqBtn.checked) {
		let qualificationField = document.querySelectorAll("#qualificationField");
		qualificationField.forEach(function(qua) {
			let quaOne = {
				qualificationNo: qua.querySelector("#qualificationNo").value, // 검정고시 번호
				qualificationDt: qua.querySelector("#qualificationDt").value.replace(/-/g, ""), // 합격일자
			};
			quaArr.push(quaOne);
		});
	} else {
		let highschoolFields = document.querySelectorAll("#highschoolFields");
		highschoolFields.forEach(function(high) {
			let highOne = {
				highschoolNo: high.querySelector("#highschoolNo").value, // 고등학교번호
				highschoolNm: high.querySelector("#highschoolNm").value, // 고등학교명
				highschoolLocation: high.querySelector("#highschoolLocation").value, // 고등학교소재지
				highschoolPeriod: high.querySelector("#highschoolPeriod").value.replace(/-/g, ""), // 입학일자
				highschoolStatus: high.querySelector("#highschoolStatus").value, // 졸업 여부
				highschoolMajor: high.querySelector("#highschoolMajor").value, // 전공 계열
			};
			highArr.push(highOne);
		});
	}


	let uniFields = document.querySelectorAll("#uniField");
	let unisArr = [];
	for (let i = 0; i < uniFields.length; i++) {
		uniField = uniFields[i];
		let uniOne = {
			uniNo: uniField.querySelector("#uniNo").value, // 대학교명
			uniNm: uniField.querySelector("#uniNm").value, // 대학교명
			uniType: uniField.querySelector("#uniType").value, // 대학교 타입
			uniSdt: uniField.querySelector("#uniSdt").value.replace(/-/g, ""), // 입학일자
			uniSStatus: uniField.querySelector("#uniSStatus").value, // 입학 여부
			uniEdt: uniField.querySelector("#uniEdt").value.replace(/-/g, ""), // 졸업일자
			uniEStatus: uniField.querySelector("#uniEStatus").value, // 졸업 여부
			uniMajorCategory: uniField.querySelector("#uniMajorCategory").value, // 전공 계열
			uniMajorNm: uniField.querySelector("#uniMajorNm").value, // 전공명
			uniMajorDegree: uniField.querySelector("#uniMajorDegree").value, // 석박사
			uniGpa: uniField.querySelector("#uniGpa").value, // 학점
			uniProjectNm: uniField.querySelector("#uniProjectNm").value, // 프로젝트 명
			uniProjectDesc: uniField.querySelector("#uniProjectDesc").value // 프로젝트 내용
		};
		unisArr.push(uniOne);
	};

	let careerFields = document.querySelectorAll("#careerField");
	let careersArr = [];
	careerFields.forEach(function(career) {
		let careerOne = {
			careerNo: career.querySelector("#careerNo").value, // 회사명
			careerCompanyNm: career.querySelector("#careerCompanyNm").value, // 회사명
			careerIndustryType: career.querySelector("#careerIndustryType").value, // 업종
			careerSubIndustry: career.querySelector("#careerSubIndustry").value, // 상세업종
			careerCompanySize: career.querySelector("#careerCompanySize").value, // 기업규모
			careerCompanyType: career.querySelector("#careerCompanyType").value, // 기업형태
			careerListed: career.querySelector("#careerListed").value, // 상장여부
			careerCity: career.querySelector("#careerCity").value, // 시도
			careerDistrict: career.querySelector("#careerDistrict").value, // 구군
			careerStartDate: career.querySelector("#careerStartDate").value.replace(/-/g, ""), // 근무기간 시작일
			careerEndDate: career.querySelector("#careerEndDate")?.value.replace(/-/g, "") || new Date().toISOString().split('T')[0].replace(/-/g, ""), // 근무기간 종료일
			careerTentre: career.querySelector("[name='careerTentre']:checked")?.value || 'N', // 재직여부
			careerJobTitle: career.querySelector("#careerJobTitle").value, // 담당업무
			careerJobDesc: career.querySelector("#careerJobDesc").value, // 담당업무 내용
			careerPosition: career.querySelector("#careerPosition").value, // 직급/직책
			careerDepartment: career.querySelector("#careerDepartment").value, // 근무부서
			careerType: career.querySelector("#careerType").value, // 근무형태
			careerSalary: career.querySelector("#careerSalary").value.replace(/,/g, ""), // 연봉
			careerReason: career.querySelector("#careerReason").value // 퇴사사유
		};
		careersArr.push(careerOne);
	});
	console.log(careersArr)
	let certFields = document.querySelectorAll("#certField");
	let certsArr = [];
	certFields.forEach(function(cert) {
		let certOne = {
			certNo: cert.querySelector("#certNo").value, // 자격증번호
			certNm: cert.querySelector("input[name='certNm']").value, // 자격증명
			certIssuer: cert.querySelector("#certIssuer").value, // 발행처
			certDate: cert.querySelector("#certDate").value.replace(/-/g, ""), // 취득일자
			certPassCd: cert.querySelector("#certPassCd").value // 합격구분

		};
		certsArr.push(certOne);
	});
	let compFields = document.querySelectorAll("#compField");
	let compsArr = [];
	compFields.forEach(function(comp) {
		let compOne = {
			compSkillNo: comp.querySelector("#compSkillNo").value, // 분야
			compSkillField: comp.querySelector("#compSkillField").value, // 분야
			compSkillDetail: comp.querySelector("#compSkillDetail").value, // 세부분야
			compSkillLevel: comp.querySelector("#compSkillLevel").value // 활용능력

		};
		compsArr.push(compOne);
	});
	let prefFields = document.querySelectorAll("#prefField");
	let prefsArr = [];
	prefFields.forEach(function(pref) {
		let prefOne = {
			prefNo: pref.querySelector("#prefNo").value, // 번호
			prefMilitary: pref.querySelector("[name='prefMilitary']:checked")
				? pref.querySelector("[name='prefMilitary']:checked").value
				: null, // 병역사항
			prefPatriot: pref.querySelector("[name='prefPatriot']:checked").value, // 보훈대상
			prefDisability: pref.querySelector("[name='prefDisability']:checked").value, // 장애여부
			prefEmploySupport: pref.querySelector("[name='prefEmploySupport']:checked").value, // 고용지원금
			prefHobbies: pref.querySelector("#prefHobbies").value, // 취미,관심
			prefSkills: pref.querySelector("#prefSkills").value // 특기

		};
		prefsArr.push(prefOne);
	});
	let awardFields = document.querySelectorAll("#awardField");
	let awardsArr = [];
	awardFields.forEach(function(award) {
		let awardOne = {
			awardNo: award.querySelector("#awardNo").value, // 수상내역
			awardTitle: award.querySelector("#awardTitle").value, // 수상내역
			awardIssuer: award.querySelector("#awardIssuer").value, // 수여기관
			awardDate: award.querySelector("#awardDate").value.replace(/-/g, "") // 수여날짜
		};
		awardsArr.push(awardOne);
	});
	let eduFields = document.querySelectorAll("#eduField");
	let edusArr = [];
	eduFields.forEach(function(edu) {
		let eduOne = {
			eduNo: edu.querySelector("#eduNo").value, // 과정번호
			eduTitle: edu.querySelector("#eduTitle").value, // 과정명
			eduInstitution: edu.querySelector("#eduInstitution").value, // 과정내용
			eduSdt: edu.querySelector("#eduSdt").value.replace(/-/g, ""), // 시작날짜
			eduEdt: edu.querySelector("#eduEdt").value.replace(/-/g, ""), // 종료날짜
			eduDesc: edu.querySelector("#eduDesc").value // 과정내용
		};
		edusArr.push(eduOne);
	});
	let langFields = document.querySelectorAll("#langField");
	let langTestFields = document.querySelectorAll("#langTestField");
	let langsArr = [];
	let langTestsArr = [];
	langFields.forEach(function(lang) {
		let langOne = {
			langNo: lang.querySelector("#langNo").value, // 외국어명
			langNm: lang.querySelector("#langNm").value, // 외국어명
			langSpeakingLevel: lang.querySelector("#langSpeakingLevel").value, // 회화능력
			langReadingLevel: lang.querySelector("#langReadingLevel").value, // 독해능력
			langWritingLevel: lang.querySelector("#langWritingLevel").value, // 작문능력
		};
		langsArr.push(langOne);
	});
	langTestFields.forEach(function(langTest) {
		let langTestOne = {
			langTestNo: langTest.querySelector("#langTestNo").value, // 종료날짜
			langTestName: langTest.querySelector("#langTestName").value, // 종료날짜
			langTestLevel: langTest.querySelector("#langTestLevel").value, // 종료날짜
			langTestDate: langTest.querySelector("#langTestDate").value.replace(/-/g, "") // 종료날짜
		};
		langTestsArr.push(langTestOne);
	});
	let expFields = document.querySelectorAll("#expField");
	let expsArr = [];
	expFields.forEach(function(exp) {
		let expOne = {
			expNo: exp.querySelector("#expNo").value, // 국가
			expCountry: exp.querySelector("#expCountry").value, // 국가
			expSdt: exp.querySelector("#expSdt").value.replace(/-/g, ""), // 연수기간 시작
			expEdt: exp.querySelector("#expEdt").value.replace(/-/g, ""), // 연수기간 종료
			expDesc: exp.querySelector("#expDesc").value // 연수내용
		};
		expsArr.push(expOne);
	});
	let actFields = document.querySelectorAll("#actField");
	let actsArr = [];
	actFields.forEach(function(act) {
		let actOne = {
			activityNo: act.querySelector("#activityNo").value, // 기관,단체명
			activityOrganization: act.querySelector("#activityOrganization").value, // 기관,단체명
			activityDesc: act.querySelector("#activityDesc").value, // 활동내용
			activitySdt: act.querySelector("#activitySdt").value.replace(/-/g, ""), // 활동기간 시작
			activityEdt: act.querySelector("#activityEdt").value.replace(/-/g, "") // 활동기간 종료
		};
		actsArr.push(actOne);
	});

	let workField = document.querySelectorAll("#workField");
	let workCityArr = [];
	workField.forEach(function(workCity) {
		let workCityOne = {
			workCityNo: workCity.querySelector("#workCityNo").value, // 시군구 번호
			sidoCd: workCity.querySelector("#sidoCd").value, // 시도
			gugunCd: workCity.querySelector("#gugunCd").value, // 군구
		};
		workCityArr.push(workCityOne);
	});

	let workCondField = document.querySelectorAll("#workCondField");
	let workArr = [];
	workCondField.forEach(function(workCond) {
		let workOne = {
			workCondNo: workCond.querySelector("#workCondNo").value, // 근무 번호
			workCondRemote: workCond.querySelector("[name='workCondRemote']:checked")?.value || 'N', // 재택근무 여부
			workCondJobType: workCond.querySelector("#workCondJobType").value, // 직종
			workCondType: Array.from(workCond.querySelectorAll("input[name='workCondType']:checked"))
				.map(checkbox => checkbox.value)
				.join(','), // "1,2,3"
			workCondSalary: workCond.querySelector("#workCondSalary").value, // 희망연봉
			workCondSalaryVisible: workCond.querySelector("[name='workCondSalaryVisible']:checked")?.value || 'N' // 희망연봉공개여부
		};
		workArr.push(workOne);
	});


	let allData = {};
	if (hqBtn.checked) {
		allData = {
			qualification: quaArr,
			uni: unisArr,
			career: careersArr,
			cert: certsArr,
			comp: compsArr,
			pref: prefsArr,
			award: awardsArr,
			edu: edusArr,
			language: langsArr,
			langTest: langTestsArr,
			experience: expsArr,
			activity: actsArr,
			workCond: workArr,
			workCity: workCityArr
		};
	} else {
		allData = {
			highSchool: highArr,
			uni: unisArr,
			career: careersArr,
			cert: certsArr,
			comp: compsArr,
			pref: prefsArr,
			award: awardsArr,
			edu: edusArr,
			language: langsArr,
			langTest: langTestsArr,
			experience: expsArr,
			activity: actsArr,
			workCond: workArr,
			workCity: workCityArr
		};
	}
	
	console.log(allData);
	
	// 특정 PK 값들을 제외하고 null 또는 빈 값을 검사하는 함수
	const hasInvalidFields = (item, pkFields) => {
		return Object.entries(item).some(([key, value]) => {
			// PK 값은 검사에서 제외
			if (pkFields.includes(key) || key === 'careerReason') return false;
			// null, undefined, 빈 문자열이면 true 반환
			return value === null || value === undefined || value == '';
		});
	};

	// 배열에서 유효한 객체만 남기는 함수
	const filterInvalidEntries = (arr, pkFields) => {
	    if (!Array.isArray(arr)) return arr; // 배열이 아니면 그대로 반환
	
	    return arr.filter(item => {
	        if (typeof item === 'object' && item !== null) {
	            const isValid = !hasInvalidFields(item, pkFields);
	            if (!isValid) {
	                console.log("Excluded item:", item);
	            }
	            return isValid; // 유효한 데이터만 유지
	        }
	        return false; // 단순 값은 모두 제외
	    });
	};



	// 객체 필터링
	const filteredData = Object.fromEntries(
		Object.entries(allData).filter(([key, value]) => {
			// 배열인 경우 내부 필터링
			if (Array.isArray(value)) {
				// 각 배열에 대한 PK 필드 설정
				const pkFields = key === 'language' ? ['langNo']
					: key === 'edu' ? ['eduNo']
					: key === 'qualification' ? ['qualificationNo']
					: key === 'highSchool' ? ['highschoolNo']
					: key === 'uni' ? ['uniNo']
					: key === 'career' ? ['careerNo']
					: key === 'cert' ? ['certNo']
					: key === 'comp' ? ['compSkillNo']
					: key === 'pref' ? ['prefNo']
					: key === 'award' ? ['awardNo']
					: key === 'langTest' ? ['langTestNo']
					: key === 'experience' ? ['expNo']
					: key === 'activity' ? ['activityNo']
					: key === 'workCond' ? ['workCondNo']
					: key === 'workCity' ? ['workCityNo']
					: []; // 기본값

				const cleanedArray = filterInvalidEntries(value, pkFields); // 유효한 데이터만 남기기
				if (cleanedArray.length > 0) {
					allData[key] = cleanedArray; // 정리된 배열을 다시 저장
					return true; // 배열에 유효한 데이터가 있으면 유지
				}
				return false; // 배열이 비어 있으면 제거
			}
			// 배열이 아닌 경우, null, undefined, 빈 문자열이면 제외
			return value !== null && value !== undefined && value !== '';
		})
	);

	console.log(filteredData);

	fetch("", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(filteredData),
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				location.reload();
			}
		})
		.catch((error) => {
			alert(error, "요청 처리 중 오류가 발생했습니다.");
		});
});

