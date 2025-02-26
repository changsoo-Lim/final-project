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

document.addEventListener("DOMContentLoaded", () => {
    const contextPath = document.body.dataset.url || "";
	
	$('form').on('submit', function() {
        var freeSalaryInput = $('#freeSalary');
        
        if (freeSalaryInput.length > 0) {
            // freeSalary 값에서 ','를 제거하고 숫자만 남기기
            var salaryValue = freeSalaryInput.val().replace(/,/g, '');
            freeSalaryInput.val(salaryValue);
        }
    });
	
	var content = $('#freeDetail').val();
    $('.textCount').text(content.length + '자');
	// 상세 소개 글자수 제한
	$('#freeDetail').keyup(function (e) {
		content = $(this).val();
	    $('.textCount').text(content.length + '자');
	    // 글자수 세기
	    if (content.length == 0 || content == '') {
	    	$('.textCount').text('0자');
	    } else {
	    	$('.textCount').text(content.length + '자');
	    }
	    
	    // 글자수 제한
	    if (content.length > 500) {
	    	// 500자 부터는 타이핑 되지 않도록
	        $(this).val($(this).val().substring(0, 500));
	        // 500자 넘으면 알림창 뜨도록
	        alertMessage('글자수는 500자까지 입력 가능합니다.', 'warning');
	    };
	});

	$('#freeSalarytype').change(function() {
      // freeSalary 값을 0으로 설정
      $('#freeSalary').val('0');
    });

	var freeSalary = $('#freeSalary').val();

    // 숫자에 쉼표 추가
    if (freeSalary) {
        freeSalary = Number(freeSalary).toLocaleString();
        $('#freeSalary').val(freeSalary);  // 쉼표 추가된 값으로 업데이트
    }
	// 희망 급여 세자리마다 ,(콤마) 적용
	$('#freeSalary').on('input', function() {
		var value = $(this).val();

		// 숫자만 허용하고, 쉼표를 추가
		value = value.replace(/[^0-9]/g, ''); // 숫자 외의 문자 제거
		var formattedValue = Number(value).toLocaleString(); // 숫자에 쉼표 추가

		$(this).val(formattedValue);
	});

    // 2) 스킬 로직
    const skillAddButton = document.querySelector('.skill-add-Btn');
    const skillUl = document.querySelector('.skill-ul');
    const skillTemplate = document.getElementById("skillTemplate");

    /** (A) 라디오 체크 상태 임시 저장 → reIndex 후 복원 */
    function reIndexSkills() {
        const skillItems = skillUl.querySelectorAll('.freelancer-skill');
        let skillCheckedValues = [];
        skillItems.forEach(skillItem => {
            const radios = skillItem.querySelectorAll('input[type="radio"]');
            let checkedVal = null;
            radios.forEach(rb => {
                if (rb.checked) checkedVal = rb.value;
            });
            skillCheckedValues.push(checkedVal);
        });

        // 인덱스 재부여
        skillItems.forEach((skillItem, i) => {
            // 라벨
            const labelEl = skillItem.querySelector('.freelancer-skill-top label');
            if (labelEl) labelEl.textContent = `스킬 ${i + 1}`;

            // select
            const sel = skillItem.querySelector('select.skill-dropdown');
            if (sel) {
                sel.name = `freeskills[${i}].freeskillsType`;
            }
            // 라디오
            const radios = skillItem.querySelectorAll('input[type="radio"]');
            radios.forEach(rb => {
                const val = rb.value || '';
                rb.name = `freeskills[${i}].freeskillsProf`;
                rb.id = `freeProficiency${i}_${val}`;
                const lbl = rb.nextElementSibling;
                if (lbl) lbl.setAttribute('for', `freeProficiency${i}_${val}`);
            });
        });

        // 라디오 체크 복원
        skillItems.forEach((skillItem, i) => {
            let wantVal = skillCheckedValues[i];
            if (wantVal) {
                const radios = skillItem.querySelectorAll('input[type="radio"]');
                radios.forEach(rb => {
                    if (rb.value === wantVal) {
                        rb.checked = true;
                        rb.defaultChecked = true; // optional
                    }
                });
            }
        });
    }

    /** (B) 스킬 삭제 버튼 이벤트 */
    function bindDeleteEvents() {
        const skillItems = skillUl.querySelectorAll('.freelancer-skill');
        skillItems.forEach(skillItem => {
            const delBtn = skillItem.querySelector('.skill-del-btn');
            if (!delBtn) return;
            delBtn.onclick = null;
            delBtn.addEventListener('click', () => {
                const total = skillUl.querySelectorAll('.freelancer-skill').length;
                if (total > 1) {
                    skillItem.remove();
                    reIndexSkills();
                    updateSkillLabelsAndButtons();
                }
            });
        });
    }

    /** (C) 스킬 라벨/버튼 UI 업데이트 */
    function updateSkillLabelsAndButtons() {
        const skillItems = skillUl.querySelectorAll('.freelancer-skill');
        const delBtns = skillUl.querySelectorAll('.skill-del-btn');
        delBtns.forEach(btn => {
            btn.style.display = (skillItems.length > 1) ? 'inline-block' : 'none';
        });
    }

    /** (D) 새 스킬 추가 (임시 name 방식) */
    function createNewSkillForm() {
        if (!skillTemplate) return;
        // cloneNode
        const newSkill = skillTemplate.cloneNode(true);
        newSkill.style.display = "block"; // 보이기

        // (1) DOM 삽입 전, 임시 name 설정
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

        // (2) DOM에 추가
        skillUl.insertBefore(newSkill, skillAddButton.parentElement);

        // (3) 재인덱싱 + 삭제 이벤트 + UI
        reIndexSkills();
        bindDeleteEvents();
        updateSkillLabelsAndButtons();
    }

    // 초기
    reIndexSkills();
    bindDeleteEvents();
    updateSkillLabelsAndButtons();

    skillAddButton.addEventListener('click', () => {
        createNewSkillForm();
    });


    // =======================
    // 직군/직무, 파일 업로드 등
    // =======================
    const freeJobHidden = document.getElementById("freeJobHidden");
    const jobContainer = document.querySelector('.selected-jobs-container');
    const jobSelect = document.querySelector("select[name='freeJobSelect']");
    const fieldSelect = document.querySelector("select[name='freeField']");

    const initialJobs = jobContainer.dataset.initialJobs ? jobContainer.dataset.initialJobs.split(",") : [];
    const selectedField = fieldSelect.value;
    let selectedJobs = new Set(initialJobs);

    if (selectedField) {
        updateJobSelectOptions(selectedField);
        initialJobs.forEach(jobCode => {
            if (jobCode.trim() !== "") addJobBox(jobCode, getJobNameFromCode(jobCode));
        });
        if (initialJobs.length > 0) {
            jobSelect.value = initialJobs[initialJobs.length - 1];
        }
    }

    fieldSelect.addEventListener('change', () => {
        const newField = fieldSelect.value;
        jobSelect.innerHTML = `<option value="">직무를 선택해 주세요.</option>`;
        jobContainer.innerHTML = '';
        selectedJobs.clear();
        updateHiddenInput();
        if (newField) {
            updateJobSelectOptions(newField);
        }
    });

    jobSelect.addEventListener('change', () => {
        const val = jobSelect.value;
        const txt = jobSelect.options[jobSelect.selectedIndex].text;
        if (!val || selectedJobs.has(val)) return;
        addJobBox(val, txt);
    });

    function updateHiddenInput() {
        freeJobHidden.value = Array.from(selectedJobs).join(",");
    }
    function addJobBox(value, text) {
        const jobBox = document.createElement('div');
        jobBox.className = 'selected-box';
        jobBox.innerHTML = `
            ${text}
            <button type="button" class="remove-btn" style="margin-left: 10px;">X</button>
        `;
        jobBox.querySelector('.remove-btn').addEventListener('click', () => {
            selectedJobs.delete(value);
            jobBox.remove();
            updateHiddenInput();
        });
        jobContainer.appendChild(jobBox);
        selectedJobs.add(value);
        updateHiddenInput();
    }
    function updateJobSelectOptions(fieldCode) {
        const filteredJobs = codeList.filter(code => code.codeParent === fieldCode);
        filteredJobs.forEach(job => {
            const option = document.createElement('option');
            option.value = job.codeCd;
            option.textContent = job.codeNm;
            jobSelect.appendChild(option);
        });
        if (filteredJobs.length > 0) {
            const initJob = initialJobs.find(jobCode => filteredJobs.some(j => j.codeCd === jobCode));
            if (initJob) {
                jobSelect.value = initJob;
            }
        }
    }
    function getJobNameFromCode(code) {
        const job = codeList.find(item => item.codeCd === code);
        return job ? job.codeNm : "";
    }


    // 파일 처리
    const uploadFilesInput = document.getElementById('uploadFiles');
    const fileInfoOutput = document.querySelector('[data-target="fileInfo"]');
    if(uploadFilesInput) {
        uploadFilesInput.value = "";
        uploadFilesInput.addEventListener('change', function(e){
            const fileList = e.target.files;
            fileInfoOutput.innerHTML = '';
            Array.from(fileList).forEach((file, idx)=>{
                const p = document.createElement('p');
                p.textContent = `파일 #${idx+1}: ${file.name} (${file.size} bytes)`;
                fileInfoOutput.appendChild(p);
            });
        });
    }

    document.querySelectorAll("[data-atch-file-no][data-file-sn]").forEach(el => {
        el.addEventListener("click", async (e) => {
            e.preventDefault();
            let atchFileNo = el.dataset.atchFileNo;
            let fileSn = el.dataset.fileSn;
            let resp = await fetch(`${contextPath}/freelancer/1/file/${atchFileNo}/${fileSn}`, {
                method: "delete",
                headers: {"accept":"application/json"}
            });
            if (resp.ok) {
                let obj = await resp.json();
                if (obj.success) {
                    el.parentElement.remove();
                }
            }
        });
    });

});
