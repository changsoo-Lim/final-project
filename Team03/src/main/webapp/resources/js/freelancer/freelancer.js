// Alert 함수
let alertMessage = function(msg, type) {
	Swal.fire({
		title: '',
		text: msg,
		icon: type,
		timer: 1500,
		customClass: {
			popup: 'sweet-size'
		},
		showConfirmButton: false
	});
};

$(function() {

	$(document).on('click', '.input-btn', function() {
		// 기본정보
	    // 직군 값을 설정하고 change 이벤트를 트리거
	    fieldSelect.value = 'jo02';
	    fieldSelect.dispatchEvent(new Event('change')); // change 이벤트 호출
		jobSelect.value = 'jo02181';
	    jobSelect.dispatchEvent(new Event('change')); // change 이벤트 호출
		
		// 근무 정보
		$('select[name="freeStyle"]').val('FS03'); // 근무 방식
		$('select[name="freeType"]').val('FT01'); // 근무 형태
		$('#freeSalarytype').val('SA03'); // 희망 급여 타입
		$('#freeSalary').val('5,000,000'); // 희망 급여
		$('input[name="freeSdt"]').val('2025-01-17'); // 프로젝트 시작 가능일

		// 경력 및 보유 스킬
		$('input[name="freeExperience"][value="Y"]').prop('checked', true); // 프리랜서 경험 있음
		$('input[name="freeCareer"]').val('3'); // 직무 경력
		setFreeDetailValue('안녕하세요. 프리랜서 등록입니다.');
	});



	// 초기 글자 수 업데이트
	var content = $('#freeDetail').val();
	$('.textCount').text(content.length + '자');

	// 상세 소개 글자수 제한
	$('#freeDetail').keyup(function(e) {
		updateTextCount($(this));
	});

	// 함수로 분리: 글자 수 업데이트
	function updateTextCount($textarea) {
		var content = $textarea.val();
		var maxLength = 500;

		// 글자 수 표시
		if (content.length === 0) {
			$('.textCount').text('0자');
		} else {
			$('.textCount').text(content.length + '자');
		}

		// 글자 수 제한
		if (content.length > maxLength) {
			$textarea.val(content.substring(0, maxLength));
			alertMessage('글자수는 500자까지 입력 가능합니다.', 'warning');
		}
	}

	// 값 설정 시 글자 수 업데이트도 함께 동작하도록 처리
	function setFreeDetailValue(value) {
		var $textarea = $('#freeDetail');
		$textarea.val(value); // 값 설정
		updateTextCount($textarea); // 글자 수 업데이트
	}

	$('#freeSalarytype').change(function() {
		// freeSalary 값을 0으로 설정
		$('#freeSalary').val('0');
	});
	// 희망 급여 세자리마다 ,(콤마) 적용
	$('#freeSalary').on('input', function() {
		var value = $(this).val();

		// 숫자만 허용하고, 쉼표를 추가
		value = value.replace(/[^0-9]/g, ''); // 숫자 외의 문자 제거
		var formattedValue = Number(value).toLocaleString(); // 숫자에 쉼표 추가

		$(this).val(formattedValue);
	});


	// ======================
	// 1) 스킬 추가/삭제 로직
	// ======================
	const skillAddButton = document.querySelector('.skill-add-Btn');
	const skillUl = document.querySelector('.skill-ul');
	const skillTemplate = document.getElementById("skillTemplate");
	// 숨겨진 템플릿 (display:none) - 스킬 추가 시 cloneNode할 요소

	/**
	 * reIndexSkills():
	 *   - 모든 스킬(freelancer-skill)을 0..N-1로 순회하며
	 *   - select, radio의 name/id를 `freeskills[i]..` 형태로 재설정
	 *   - 라디오 체크 상태가 풀리지 않도록, 먼저 체크값을 기억 후 복원
	 */
	function reIndexSkills() {
		const skillItems = skillUl.querySelectorAll('.freelancer-skill');

		// (1) 현재 라디오 체크 상태 저장
		let checkedValues = [];
		skillItems.forEach(skillItem => {
			const radios = skillItem.querySelectorAll('input[type="radio"]');
			let checkedVal = null;
			radios.forEach(rb => {
				if (rb.checked) {
					checkedVal = rb.value;
				}
			});
			checkedValues.push(checkedVal);
		});

		// (2) 인덱스 재부여
		skillItems.forEach((skillItem, i) => {
			// 라벨: "스킬 1, 2..."
			const label = skillItem.querySelector('.freelancer-skill-top label');
			if (label) {
				label.textContent = `스킬 ${i + 1}`;
			}

			// 스킬 select
			const selectEl = skillItem.querySelector('select.skill-dropdown');
			if (selectEl) {
				selectEl.name = `freeskills[${i}].freeskillsType`;
			}

			// 라디오들
			const radios = skillItem.querySelectorAll('input[type="radio"]');
			radios.forEach(rb => {
				const val = rb.value || '';
				rb.name = `freeskills[${i}].freeskillsProf`;
				rb.id = `freeProficiency${i}_${val}`;
				const lb = rb.nextElementSibling;
				if (lb) {
					lb.setAttribute('for', `freeProficiency${i}_${val}`);
				}
			});
		});

		// (3) 라디오 체크값 복원
		skillItems.forEach((skillItem, i) => {
			let wantVal = checkedValues[i];
			if (wantVal) {
				const radios = skillItem.querySelectorAll('input[type="radio"]');
				radios.forEach(rb => {
					if (rb.value === wantVal) {
						rb.checked = true;
						rb.defaultChecked = true; // optional
					}
				});
			} else {
				// 만약 어떤 것도 체크 안 했으면 첫 라디오만 체크할 수도 있음
				const radios = skillItem.querySelectorAll('input[type="radio"]');
				if (radios.length > 0) {
					radios[0].checked = true;
					radios[0].defaultChecked = true;
				}
			}
		});

		updateSkillLabelsAndButtons();
	}

	/**
	 * updateSkillLabelsAndButtons():
	 *   - 스킬이 1개뿐이면 삭제 버튼을 숨기는 등 UI 갱신
	 */
	function updateSkillLabelsAndButtons() {
		const skillItems = skillUl.querySelectorAll('.freelancer-skill');
		const deleteButtons = skillUl.querySelectorAll('.skill-del-btn');

		deleteButtons.forEach(btn => {
			btn.style.display = (skillItems.length > 1) ? 'inline-block' : 'none';
		});
	}

	/**
	 * bindDeleteEvents():
	 *   - 모든 스킬 항목의 삭제 버튼에 클릭 핸들러를 붙임
	 *   - 스킬 삭제 후 reIndex
	 */
	function bindDeleteEvents() {
		const skillItems = skillUl.querySelectorAll('.freelancer-skill');
		skillItems.forEach(skillItem => {
			const deleteBtn = skillItem.querySelector('.skill-del-btn');
			if (!deleteBtn) return;
			// 혹시 기존 리스너 중복방지
			deleteBtn.onclick = null;

			deleteBtn.addEventListener('click', () => {
				const total = skillUl.querySelectorAll('.freelancer-skill').length;
				if (total > 1) {
					skillItem.remove();
					reIndexSkills(); // 삭제 후 재인덱스
				}
			});
		});
	}

	/**
	 * createNewSkillForm():
	 *   - skillTemplate을 cloneNode(true) -> 임시 name 지정 -> skillUl에 append -> reIndex
	 */
	function createNewSkillForm() {
		if (!skillTemplate) return;
		const newSkill = skillTemplate.cloneNode(true);
		newSkill.style.display = "block";

		// (1) 임시 name 설정 (DOM 삽입 전)
		const sel = newSkill.querySelector('select.skill-dropdown');
		if (sel) {
			sel.name = "tempSkillSelect";
			sel.selectedIndex = 0;
		}
		const radios = newSkill.querySelectorAll('input[type="radio"]');
		radios.forEach((rb, idx) => {
			rb.name = "tempSkillRadio";
			rb.checked = (idx === 0);
		});

		// (2) DOM에 삽입
		skillUl.insertBefore(newSkill, skillAddButton.parentElement);

		// (3) reIndex + bindDelete + UI
		reIndexSkills();
		bindDeleteEvents();
	}

	// [INIT] 페이지 로드 시
	// 기존 스킬에 대해서도 reIndex, 삭제 이벤트 한번씩 세팅
	reIndexSkills();
	bindDeleteEvents();

	// 스킬 추가 버튼
	skillAddButton.addEventListener('click', () => {
		createNewSkillForm();
	});


	// ======================
	// 2) 직군/직무 처리 로직
	// ======================
	const fieldSelect = document.querySelector("select[name='freeField']"); // 직군 select
	const jobSelect = document.querySelector("select[name='freeJobSelect']"); // 직무 select
	const jobContainer = document.querySelector('.selected-jobs-container'); // 직무 박스 표시
	const freeJobInput = document.getElementById("freeJobHidden"); // 숨김 input

	let selectedJobs = new Set();

	// 직군 변경 시 초기화
	fieldSelect.addEventListener('change', () => {
		const selectedField = fieldSelect.value;
		jobSelect.innerHTML = `<option value="">직무를 선택해 주세요.</option>`;
		jobContainer.innerHTML = '';
		selectedJobs.clear();
		updateHiddenInput();

		if (selectedField) {
			const filteredJobs = codeList.filter(cd => cd.codeParent === selectedField);
			filteredJobs.forEach(job => {
				const option = document.createElement('option');
				option.value = job.codeCd;
				option.textContent = job.codeNm;
				jobSelect.appendChild(option);
			});
		}
	});

	// 직무 선택 시 박스 생성
	jobSelect.addEventListener('change', () => {
		const selectedVal = jobSelect.value;
		const selectedTxt = jobSelect.options[jobSelect.selectedIndex].text;
		if (!selectedVal || selectedJobs.has(selectedVal)) return;

		// 박스 생성
		const jobBox = document.createElement('div');
		jobBox.className = 'selected-job-box';
		jobBox.setAttribute('data-value', selectedVal);
		jobBox.innerHTML = `
            ${selectedTxt}
            <button type="button" class="remove-btn" style="margin-left:10px;">X</button>
        `;
		jobBox.querySelector('.remove-btn').addEventListener('click', () => {
			selectedJobs.delete(selectedVal);
			jobBox.remove();
			updateHiddenInput();
		});

		jobContainer.appendChild(jobBox);
		selectedJobs.add(selectedVal);
		updateHiddenInput();
	});

	// Hidden input 업데이트
	function updateHiddenInput() {
		freeJobInput.value = Array.from(selectedJobs).join(",");
	}

	// ======================
	// 3) submit 시 ,(콤마) 제거
	// ======================
	$('form').on('submit', function() {
		const freeSalaryInput = $('#freeSalary');
		if (freeSalaryInput.length > 0) {
			// comma 제거
			let salaryValue = freeSalaryInput.val().replace(/,/g, '');
			freeSalaryInput.val(salaryValue);
		}
	});
});

// 파일 업로드 미리보기
document.getElementById('uploadFiles').addEventListener('change', function(event) {
	const fileList = event.target.files;
	const output = document.querySelector('[data-target="fileInfo"]');
	output.innerHTML = '';
	Array.from(fileList).forEach((file, index) => {
		const fileInfo = document.createElement('p');
		fileInfo.textContent = `파일 #${index + 1}: ${file.name} (size: ${file.size} bytes)`;
		output.appendChild(fileInfo);
	});
});
