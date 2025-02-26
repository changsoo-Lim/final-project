document.addEventListener('DOMContentLoaded', () => {
	loadQuaList();
	loadHighSchoolList();
	loadUniList();
	loadCareerList();
	loadCertList();
	loadCompList();
	loadPrefList();
	loadAwardList();
	loadEduList();
	loadLangList();
	loadLangTestList();
	loadExpList();
	loadActList();
	loadPortList();
	loadWorkCondList();
	loadWorkCityList();

});
const baseUrl = document.body.dataset.url;

// 검정고시
function loadQuaList() {
	fetch(`${baseUrl}/qua/quaOne`)
		.then(response => {
			if (!response.ok) {
				throw new Error("데이터를 불러오는 데 실패했습니다.");
			}
			if (response.headers.get("Content-Length") === "0") {
                console.warn("Empty response received.");
                return; // 빈 응답이면 함수 종료
            }
			return response.json();
		})
		.then(data => {
			if (!data || (Array.isArray(data) && data.length === 0)) {
                // 데이터가 없으면 함수 종료
                return;
            }
			
			if (Array.isArray(data) && data.length > 0) {
			    fillQuaForm(data[0]); // 배열인 경우 첫 번째 데이터를 사용
			    document.getElementById("hqBtn").checked = true;
			    document.getElementById("hqBtnCheck").style.display = 'none'; // 버튼 숨기기
			} else if (data && typeof data === "object") {
			    fillQuaForm(data); // 객체인 경우 바로 사용
			    document.getElementById("hqBtn").checked = true;
			    document.getElementById("hqBtnCheck").style.display = 'none'; // 버튼 숨기기
			} else {
			    clearFormField("qualificationField");
			    document.getElementById("hqBtn").checked = false;
			    document.getElementById("hqBtnCheck").style.display = 'block'; // 버튼 보이기
			}
			toggleFields();
		})
		.catch(error => {
			console.error("Error loading preference list:", error);
		});
		
}

// 검정고시 폼에 데이터를 채우는 함수
function fillQuaForm(data) {
    document.querySelector("input[name='qualificationNo']").value = data.qualificationNo || "";
    document.querySelector("input[name='qualificationDt']").value = formatDate(data.qualificationDt) || "";
}
// 고등학교
function loadHighSchoolList() {
  fetch(`${baseUrl}/highSchool`)
    .then(response => {
      if (!response.ok) {
        throw new Error("데이터를 불러오는 데 실패했습니다.");
      }
      if (response.headers.get("Content-Length") === "0") {
        console.warn("Empty response received.");
        return; // 빈 응답이면 함수 종료
      }
      return response.json();
    })
    .then(data => {
      if (!data || (Array.isArray(data) && data.length === 0)) {
        // 데이터가 없으면 함수 종료
        return;
      }

      // highschoolNo가 있는지 확인
      let highschoolNo = null;
      if (Array.isArray(data) && data.length > 0) {
        highschoolNo = data[0].highschoolNo; // 배열인 경우 첫 번째 데이터에서 highschoolNo 가져오기
        fillHighschoolForm(data[0]);
      } else if (data && typeof data === "object") {
        highschoolNo = data.highschoolNo; // 객체인 경우 highschoolNo 가져오기
        fillHighschoolForm(data);
      } else {
        clearFormField("highschoolFields");
      }

      // highschoolNo가 있으면 hqBtnCheck 버튼 숨기기
      if (highschoolNo) {
        document.getElementById('hqBtnCheck').style.display = 'none';
      }
    })
    .catch(error => {
      console.error("Error loading preference list:", error);
    });
}


// 고등학교 폼에 데이터를 채우는 함수
function fillHighschoolForm(data) {
    document.querySelector("input[name='highschoolNo']").value = data.highschoolNo || "";
    document.querySelector("input[name='highschoolNm']").value = data.highschoolNm || "";
    document.querySelector("input[name='highschoolLocation']").value = data.highschoolLocation || "";
    document.querySelector("input[name='highschoolPeriod']").value = formatDate(data.highschoolPeriod) || "";
    document.querySelector("select[name='highschoolStatus']").value = data.highschoolStatus || "";
    document.querySelector("select[name='highschoolMajor']").value = data.highschoolMajor || "";
}

// 대학교
function loadUniList() {
	fetch(`${baseUrl}/uni/uniList`)
		.then(response => response.json())
		.then(data => {
			const tbody = document.querySelector('#uniTableBody');
			tbody.innerHTML = ''; // 기존 내용 제거

			data.forEach(uni => {
				const row = `
                    <tr data-uni-no="${uni.uniNo}">
                        <td class="uni-name">${uni.uniNm}</td>
                        <td class="uni-major">${uni.uniMajorNm}</td>
                        <td class="date-column" data-uni-sdt="${uni.uniSdt}">
                            ${formatDate(uni.uniSdt)}
                        </td>
                        <td class="date-column" data-uni-edt="${uni.uniEdt}">
                            ${formatDate(uni.uniEdt)}
                        </td>
                        <td class="graduation-status">${uni.uniEStatus}</td>
                        <td class="del-status">
							<input type="hidden" class="uniNo" required value="${uni.uniNo}" />
                            <button type="button" class="btn btn-outline-danger btn-sm uniDelBtn">삭제</button>
                        </td>
                    </tr>`;
				tbody.insertAdjacentHTML('beforeend', row);
			});
		})
		.catch(error => console.error('Error loading university list:', error));
}
// 경력사항
function loadCareerList() {
	fetch(`${baseUrl}/career/careerList`)
		.then(response => response.json())
		.then(data => {
			const tbody = document.querySelector('#careerTableBody');
			tbody.innerHTML = ''; // 기존 내용 제거

			data.forEach(career => {
				const row = `
                    <tr data-career-no="${career.careerNo}">
                        <td class="career-name">${career.careerCompanyNm}</td>
                        <td class="career-position">${career.careerPosition}</td>
                        <td class="career-title">${career.careerJobTitle}</td>
                        <td class="date-column" data-career-sdt="${career.careerStartDate}">
                            ${formatDate(career.careerStartDate)}
                        </td>
                        <td class="date-column" data-career-edt="${career.careerEndDate || ''}">
                            ${career.careerEndDate ? formatDate(career.careerEndDate) : '재직중'}
                        </td>
                        <td class="career-status">${career.careerReason || '-'}</td>
                        <td class="del-status">
                            <input type="hidden" class="careerNo" required value="${career.careerNo}" />
                            <button type="button" class="btn btn-outline-danger btn-sm careerDelBtn">삭제</button>
                        </td>
                    </tr>`;
				tbody.insertAdjacentHTML('beforeend', row);
			});

			if (data.length > 0) {
				document.getElementById("careerOld").checked = true; // 경력 라디오 버튼 선택
			} else {
				document.getElementById("careerNew").checked = true; // 신입 라디오 버튼 선택
			}

			// 토글 상태 갱신
			toggleCareerDiv();
		})
		.catch(error => console.error('Error loading career list:', error));
}
// 자격증
function loadCertList() {
	fetch(`${baseUrl}/cert/certList`)
		.then(response => response.json())
		.then(data => {
			const tbody = document.querySelector('#certTableBody');
			tbody.innerHTML = '';

			data.forEach(cert => {
				const row = `
                    <tr data-cert-no="${cert.certNo}">
                        <td class="cert-name">${cert.certNm}</td>
                        <td class="cert-issuer">${cert.certIssuer}</td>
                        <td class="cert-date">${formatDate(cert.certDate)}</td>
                        <td class="cert-pass-cd">${cert.certPassCd}</td>
                        <td class="del-status">
                            <input type="hidden" class="certNo" required value="${cert.certNo}" />
                            <button type="button" class="btn btn-outline-danger btn-sm certDelBtn">삭제</button>
                        </td>
                    </tr>`;
				tbody.insertAdjacentHTML('beforeend', row); // 새 행을 tbody에 추가합니다.
			});
		})
		.catch(error => console.error('Error loading certificate list:', error));
}

// 컴활
function loadCompList() {
	fetch(`${baseUrl}/comp/compList`) // 서버에서 컴퓨터 스킬 목록 데이터를 가져옵니다.
		.then(response => response.json())
		.then(data => {
			const tbody = document.querySelector('#compTableBody'); // 테이블의 tbody 선택
			tbody.innerHTML = ''; // 기존 내용을 제거합니다.

			data.forEach(comp => {
				const row = `
                    <tr data-comp-no="${comp.compSkillNo}">
                        <td class="comp-name">${comp.compSkillField}</td>
                        <td class="comp-compSkillDetail">${comp.compSkillDetail}</td>
                        <td class="comp-compSkillLevel">${comp.compSkillLevel}</td>
                        <td class="del-status">
                            <input type="hidden" class="compNo" required value="${comp.compSkillNo}" />
                            <button type="button" class="btn btn-outline-danger btn-sm compDelBtn">삭제</button>
                        </td>
                    </tr>`;
				tbody.insertAdjacentHTML('beforeend', row); // 새 행을 tbody에 추가
			});
		})
		.catch(error => console.error('Error loading computer skills list:', error));
}

// 취업우대 및 특이사항
function loadPrefList() {
	fetch(`${baseUrl}/pref/prefList`) // 서버에서 선호도(prefList) 데이터를 가져옵니다.
		.then(response => {
			if (!response.ok) {
				throw new Error("데이터를 불러오는 데 실패했습니다.");
			}
			if (response.headers.get("Content-Length") === "0") {
                console.warn("Empty response received.");
                return; // 빈 응답이면 함수 종료
            }
			return response.json();
		})
		.then(data => {
			if (!data || (Array.isArray(data) && data.length === 0)) {
                // 데이터가 없으면 함수 종료
                return;
            }
			
			if (Array.isArray(data) && data.length > 0) {
				fillPrefForm(data[0]); // 배열인 경우 첫 번째 데이터를 사용
			} else if (data && typeof data === "object") {
				fillPrefForm(data); // 객체인 경우 바로 사용
			} else {
				alert("데이터가 없습니다.");
				clearFormField("prefField");
			}
		})
		.catch(error => {
			console.error("Error loading preference list:", error);
			alert("데이터를 불러오는 데 실패했습니다. 다시 시도해주세요.");
		});
}

// 폼에 데이터를 채우는 함수
function fillPrefForm(data) {
	document.querySelector("input[name='prefNo']").value = data.prefNo || "";
	document.querySelector(`input[name='prefMilitary'][value='${data.prefMilitary}']`).checked = true;
	document.querySelector(`input[name='prefPatriot'][value='${data.prefPatriot}']`).checked = true;
	document.querySelector(`input[name='prefDisability'][value='${data.prefDisability}']`).checked = true;
	document.querySelector(`input[name='prefEmploySupport'][value='${data.prefEmploySupport}']`).checked = true;
	document.querySelector("input[name='prefHobbies']").value = data.prefHobbies || "";
	document.querySelector("input[name='prefSkills']").value = data.prefSkills || "";
}

// 수상경력
function loadAwardList() {
	fetch(`${baseUrl}/award/awardList`) // 서버에서 수상 내역 데이터를 가져옵니다.
		.then(response => {
			if (!response.ok) {
				throw new Error("데이터를 불러오는 데 실패했습니다.");
			}
			return response.json();
		})
		.then(data => {
			const tbody = document.querySelector('#awardTableBody'); // 테이블의 tbody 선택
			tbody.innerHTML = ''; // 기존 내용 초기화

			data.forEach(award => {
				const row = `
                        <tr data-award-no="${award.awardNo}">
                            <td class="award-awardTitle">${award.awardTitle || '-'}</td>
                            <td class="award-awardIssuer">${award.awardIssuer || '-'}</td>
                            <td class="award-awardDate">${formatDate(award.awardDate)}</td>
                            <td class="del-status">
                                <input type="hidden" class="awardNo" value="${award.awardNo}" />
                                <button type="button" class="btn btn-outline-danger btn-sm awardDelBtn">삭제</button>
                            </td>
                        </tr>`;
				tbody.insertAdjacentHTML('beforeend', row); // 새 행 추가
			});

		})
		.catch(error => {
			console.error("Error loading award list:", error);
			alert("데이터를 불러오는 데 실패했습니다. 다시 시도해주세요.");
		});
}

// 교육사항
function loadEduList() {
	fetch(`${baseUrl}/edu/eduList`) // 서버에서 교육 내역 데이터를 가져옵니다.
		.then(response => {
			if (!response.ok) {
				throw new Error("데이터를 불러오는 데 실패했습니다.");
			}
			return response.json();
		})
		.then(data => {
			const tbody = document.querySelector('#eduTableBody'); // 테이블의 tbody 선택
			tbody.innerHTML = ''; // 기존 내용 초기화

			data.forEach(edu => {
				const row = `
                    <tr data-edu-no="${edu.eduNo}">
                        <td class="edu-eduTitle">${edu.eduTitle || '-'}</td>
                        <td class="edu-eduInstitution">${edu.eduInstitution || '-'}</td>
                        <td class="edu-eduSdt" data-edu-sdt="${edu.eduSdt}">${formatDate(edu.eduSdt)}</td>
                        <td class="edu-eduEdt" data-edu-edt="${edu.eduEdt}">${formatDate(edu.eduEdt)}</td>
                        <td class="del-status">
                            <input type="hidden" class="eduNo" value="${edu.eduNo}" />
                            <button type="button" class="btn btn-outline-danger btn-sm eduDelBtn">삭제</button>
                        </td>
                    </tr>`;
				tbody.insertAdjacentHTML('beforeend', row); // 새 행 추가
			});
		})
		.catch(error => {
			console.error("Error loading education list:", error);
			alert("데이터를 불러오는 데 실패했습니다. 다시 시도해주세요.");
		});
}

// 어학능력
function loadLangList() {
	fetch(`${baseUrl}/lang/langList`) // 서버에서 언어 능력 데이터를 가져옵니다.
		.then(response => {
			if (!response.ok) {
				throw new Error("데이터를 불러오는 데 실패했습니다.");
			}
			return response.json();
		})
		.then(data => {
			const tbody = document.querySelector('#langTableBody'); // 테이블의 tbody 선택
			tbody.innerHTML = ''; // 기존 내용 초기화

			data.forEach(lang => {
				const row = `
                    <tr data-lang-no="${lang.langNo}">
                        <td class="lang-langTitle">${lang.langNm || '-'}</td>
                        <td class="lang-langInstitution">${lang.langSpeakingLevel || '-'}</td>
                        <td class="lang-langReadingLevel">${lang.langReadingLevel || '-'}</td>
                        <td class="lang-langWritingLevel">${lang.langWritingLevel || '-'}</td>
                        <td class="del-status">
                            <input type="hidden" class="langNo" value="${lang.langNo}" />
                            <button type="button" class="btn btn-outline-danger btn-sm langDelBtn">삭제</button>
                        </td>
                    </tr>`;
				tbody.insertAdjacentHTML('beforeend', row); // 새 행 추가
			});
		})
		.catch(error => {
			console.error("Error loading language list:", error);
			alert("데이터를 불러오는 데 실패했습니다. 다시 시도해주세요.");
		});
}
// 언허 시험
function loadLangTestList() {
	fetch(`${baseUrl}/langTest/langTestList`) // 서버에서 언어 시험 데이터를 가져옵니다.
		.then(response => {
			if (!response.ok) {
				throw new Error("데이터를 불러오는 데 실패했습니다.");
			}
			return response.json();
		})
		.then(data => {
			const tbody = document.querySelector('#langTestTableBody'); // 테이블의 tbody 선택
			tbody.innerHTML = ''; // 기존 내용 초기화

			data.forEach(langTest => {
				const row = `
                    <tr data-langTest-no="${langTest.langTestNo}">
                        <td class="langTest-langTestName">${langTest.langTestName || '-'}</td>
                        <td class="langTest-langTestLevel">${langTest.langTestLevel || '-'}</td>
                        <td class="langTest-langTestDate">${formatDate(langTest.langTestDate)}</td>
                        <td class="del-status">
                            <input type="hidden" class="langTestNo" value="${langTest.langTestNo}" />
                            <button type="button" class="btn btn-outline-danger btn-sm langTestDelBtn">삭제</button>
                        </td>
                    </tr>`;
				tbody.insertAdjacentHTML('beforeend', row); // 새 행 추가
			});
		})
		.catch(error => {
			console.error("Error loading language test list:", error);
			alert("데이터를 불러오는 데 실패했습니다. 다시 시도해주세요.");
		});
}

// 해외연수
function loadExpList() {
	fetch(`${baseUrl}/exp/expList`) // 서버에서 경험 데이터(expList) 가져오기
		.then(response => {
			if (!response.ok) {
				throw new Error("데이터를 불러오는 데 실패했습니다.");
			}
			return response.json();
		})
		.then(data => {
			const tbody = document.querySelector('#expTableBody');
			tbody.innerHTML = ''; // 기존 내용을 제거합니다.

			data.forEach(exp => {
				const row = `
                        <tr data-exp-no="${exp.expNo}">
                            <td class="exp-expCountry">${exp.expCountry || '-'}</td>
                            <td class="exp-expdt" data-exp-sdt="${exp.expSdt}">
                                ${formatDate(exp.expSdt)}
                            </td>
                            <td class="exp-expdt" data-exp-edt="${exp.expEdt}">
                                ${formatDate(exp.expEdt)}
                            </td>
                            <td class="del-status">
                                <input type="hidden" class="expNo" required value="${exp.expNo}" />
                                <button type="button" class="btn btn-outline-danger btn-sm expDelBtn">삭제</button>
                            </td>
                        </tr>`;
				tbody.insertAdjacentHTML('beforeend', row);
			});
		})
		.catch(error => console.error('Error loading experience list:', error));
}

// 봉사활동
function loadActList() {
	fetch(`${baseUrl}/act/actList`) // 서버에서 활동 데이터(actList) 가져오기
		.then(response => {
			if (!response.ok) {
				throw new Error("데이터를 불러오는 데 실패했습니다.");
			}
			return response.json();
		})
		.then(data => {
			const tbody = document.querySelector('#actTableBody'); // 테이블의 tbody
			tbody.innerHTML = ''; // 기존 내용을 제거합니다.

			// 데이터를 테이블 행으로 추가
			data.forEach(act => {
				const row = `
                        <tr data-act-no="${act.activityNo}">
                            <td class="act-activityOrganization">${act.activityOrganization}</td>
                            <td class="act-actdt" data-activity-sdt="${act.activitySdt}">
                                ${formatDate(act.activitySdt)}
                            </td>
                            <td class="act-actdt" data-activity-edt="${act.activityEdt}">
                                ${act.activityEdt ? formatDate(act.activityEdt) : '참여중'}
                            </td>
                            <td class="del-status">
                                <input type="hidden" class="actNo" required value="${act.activityNo}" />
                                <button type="button" class="btn btn-outline-danger btn-sm actDelBtn">삭제</button>
                            </td>
                        </tr>`;
				tbody.insertAdjacentHTML('beforeend', row); // 행을 tbody에 추가
			});
		})
		.catch(error => console.error('Error loading activity list:', error));
}

// 포폴
function loadPortList() {
    fetch(`${baseUrl}/port/portList`) // 서버에서 포트폴리오 데이터(portList) 가져오기
        .then(response => {
            if (!response.ok) {
                throw new Error("데이터를 불러오는 데 실패했습니다.");
            }
            return response.json();
        })
        .then(data => {
            const container = document.querySelector('#portContainer'); // 포트폴리오 컨테이너
            container.innerHTML = ''; // 기존 내용을 제거합니다.

            if (data.length > 0) {
                // 데이터를 동적으로 추가
                data.forEach(port => {
                    const portDiv = `
                        <div class="row mb-3">
                            <label for="Work_Cond" class="col-md-2 col-form-label fw-bold">${port.portNm || '-'}</label>
                            <div class="col-6">
                                <label for="url" class="col-form-label fw-bold">
                                    <a href="${port.portUrl || '#'}" target="_blank">${port.portUrl || '-'}</a>
                                </label>
                            </div>
                            <div class="col-4" align="right">
                                <input type="hidden" class="portNo" required value="${port.portNo}" />
                                <button type="button" class="btn btn-outline-danger btn-sm portDelBtn">삭제</button>
                            </div>
                        </div>`;
                    container.insertAdjacentHTML('beforeend', portDiv); // 새 포트폴리오 div 추가
                });
            } else {
                // 데이터가 없을 경우 빈 메시지 표시
                container.innerHTML = `
                    <div class="text-center">포트폴리오 데이터가 없습니다.</div>`;
            }
        })
        .catch(error => console.error('Error loading portfolio list:', error));
}

// 희망근무지역
function loadWorkCondList() {
    fetch(`${baseUrl}/workCond/workCondList`)
        .then(response => {
            if (!response.ok) {
                throw new Error("데이터를 불러오는 데 실패했습니다.");
            }
            return response.json();
        })
        .then(data => {
            if (Array.isArray(data) && data.length > 0) {
                fillWorkCondForm(data[0]); // 배열인 경우 첫 번째 데이터를 사용
            } else if (data && typeof data === "object") {
                fillWorkCondForm(data); // 객체인 경우 바로 사용
            } else {
                alert("데이터가 없습니다.");
                clearFormField("workCondField");
            }
        })
        .catch(error => {
            console.error("Error loading work condition list:", error);
            alert("데이터를 불러오는 데 실패했습니다. 다시 시도해주세요.");
        });
}

// 폼에 데이터를 채우는 함수
function fillWorkCondForm(data) {
    // work_cond_type 필드를 배열로 분리
    const workCondTypes = data.workCondType ? data.workCondType.split(',') : [];

    // 체크박스 값 설정 및 change 이벤트 트리거
    document.querySelectorAll("input[name='workCondType']").forEach(checkbox => {
        checkbox.checked = workCondTypes.includes(checkbox.value);
        checkbox.dispatchEvent(new Event("change")); // 강제로 change 이벤트 트리거
    });

    // 히든 필드 값 설정
    const workCondNoField = document.querySelector("input[name='workCondNo']");
    workCondNoField.value = data.workCondNo || "";

    // 텍스트 필드 값 설정 및 input 이벤트 트리거
    const workCondJobTypeField = document.querySelector("input[name='workCondJobType']");
    workCondJobTypeField.value = data.workCondJobType || "";
    workCondJobTypeField.dispatchEvent(new Event("input")); // 강제로 input 이벤트 트리거

    // 라디오 버튼 값 설정 및 change 이벤트 트리거
    const workCondRemoteField = document.querySelector(`input[name='workCondRemote'][value='Y']`);
    workCondRemoteField.checked = data.workCondRemote === 'Y';
    workCondRemoteField.dispatchEvent(new Event("change")); // 강제로 change 이벤트 트리거

    const workCondSalaryVisibleField = document.querySelector(`input[name='workCondSalaryVisible'][value='Y']`);
    workCondSalaryVisibleField.checked = data.workCondSalaryVisible === 'Y';
    workCondSalaryVisibleField.dispatchEvent(new Event("change")); // 강제로 change 이벤트 트리거

    // 셀렉트 박스 값 설정 및 change 이벤트 트리거
    const workCondSalaryField = document.querySelector("select[name='workCondSalary']");
    workCondSalaryField.value = data.workCondSalary || "";
    workCondSalaryField.dispatchEvent(new Event("change")); // 강제로 change 이벤트 트리거
}

function loadWorkCityList() {
    fetch(`${baseUrl}/workCity/workCityList`)
        .then(response => {
            if (!response.ok) {
                throw new Error("데이터를 불러오는 데 실패했습니다.");
            }
            return response.json();
        })
        .then(data => {

            if (Array.isArray(data) && data.length > 0) {
                // 첫 번째 데이터는 원본 폼에 채우기
                const firstForm = document.querySelector("#workField");
                fillWorkCityForm(firstForm, data[0]);

                // 나머지 데이터는 추가된 폼에 채우기
                data.slice(1).forEach(workCity => {
                    // "추가" 버튼 클릭하여 새 폼 생성
                    document.querySelector("#workAddBtn").click();

                    // 마지막 추가된 폼 선택
                    const lastForm = document.querySelector("#workFieldList").lastElementChild;

                    // 새로 생성된 폼에 데이터 채우기
                    fillWorkCityForm(lastForm, workCity);
                });
            } else if (data && typeof data === "object") {
                // 단일 객체 데이터 처리 (원본 폼만 사용)
                const firstForm = document.querySelector("#workField");
                fillWorkCityForm(firstForm, data);
            } else {
                alert("데이터가 없습니다.");
                clearFormField("workField");
            }
        })
        .catch(error => {
            console.error("Error loading work condition list:", error);
            alert("데이터를 불러오는 데 실패했습니다. 다시 시도해주세요.");
        });
}



// 특정 폼에 데이터를 채우는 함수
function fillWorkCityForm(form, data) {

    form.querySelector("input[name='workCityNo']").value = data.workCityNo || "";
    form.querySelector("select[name='sidoCd']").value = data.sidoCd || "";
    form.querySelector("select[name='gugunCd']").value = data.gugunCd || "";
}


// 날짜 데이터 포맷
function formatDate(dateString) {
	if (!dateString) {
        return '';
    }
	return `${dateString.slice(0, 4)}-${dateString.slice(4, 6)}-${dateString.slice(6)}`;
}

// 입력폼 비우기
function clearFormField(containerId) {
	const container = document.getElementById(containerId);

	if (!container) {
		console.error(`ID가 '${containerId}'인 컨테이너를 찾을 수 없습니다.`);
		return;
	}

	container.querySelectorAll("input, select, textarea").forEach(field => {
		if (field.tagName === "SELECT") {
			field.selectedIndex = 0; // 첫 번째 옵션 선택
		} else if (field.tagName === "TEXTAREA" || field.type === "text" || field.type === "number" || field.type === "hidden") {
			field.value = ""; // 텍스트 입력 필드 및 숨겨진 필드 초기화
		} else if (field.type === "date") {
			field.value = ""; // 날짜 필드 초기화
		} else if (field.type === "checkbox") {
			field.checked = false; // 체크박스 초기화
		} else if (field.type === "radio") {
			document.querySelectorAll(`input[name='${field.name}']`).forEach(radio => {
				radio.checked = false; // 동일한 이름을 가진 라디오 버튼 초기화
			});
		} else {
			console.warn(`필드 유형 '${field.type || field.tagName}'은 지원되지 않습니다.`);
		}
	});
}