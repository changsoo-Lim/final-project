$(document).ready(function () {
    let questionIndex = 0;

    // 문제 추가 버튼 클릭 이벤트
    $('#addQuestion').on('click', function () {
        if (questionIndex >= 100) {
            alert('문제는 최대 100개까지만 추가할 수 있습니다.');
            return;
        }
		
        questionIndex++;
        let itemIndex = 0;
        let keywordIndex = 0;
		
		const isPersonalityTest = $('#testCd').val() === 'TE01' ? true : false;
		let questionHtml;
		
		window.generateOptionsHtml = function (questionIndex, includeScore = false) {
	        let optionsHtml = '';
	        for (let i = 0; i < 5; i++) {
	            optionsHtml += `
	                <div class="input-group mb-2 align-items-center">
	                    <input type="text" class="form-control" placeholder="선택지 ${i + 1}" name="testQuestnList[${questionIndex}].itemList[${i}].itemCont">
	                    <input type="hidden" name="testQuestnList[${questionIndex}].itemList[${i}].itemYn" value="Y">
	                    ${includeScore ? `<input type="number" class="form-control" placeholder="점수" name="testQuestnList[${questionIndex}].itemList[${i}].itemScore" style="max-width: 80px;">` : ''}
	                </div>`;
					// <input type="file" class="form-control" name="testQuestnList[${questionIndex}].itemList[${i}].uploadFiles">
	        }
	        return optionsHtml;
	    }
		
		if(isPersonalityTest){
			questionHtml = `
	            <div class="card mb-3" data-question-index="${questionIndex}" id="question${questionIndex}">
	                <div class="card-header card-header-test">
	                    문제 ${questionIndex}
	                    <button type="button" class="btn-close float-end" aria-label="Close" onclick="removeQuestion(${questionIndex})"></button>
	                </div>
	                <div class="card-body">
	                    <input type="hidden" name="testQuestnList[${questionIndex}].queTurn" value="${questionIndex}">
	                    <div class="mb-3">
	                        <label for="questionFile${questionIndex}" class="form-label">문제 파일 업로드</label>
	                        <input type="file" id="questionFile${questionIndex}" name="testQuestnList[${questionIndex}].uploadFiles" class="form-control">
	                    </div>
	                    <div class="mb-3">
	                        <label for="questionText${questionIndex}" class="form-label">문제 지문</label>
	                        <textarea id="questionText${questionIndex}" name="testQuestnList[${questionIndex}].queCont" class="form-control" rows="2"></textarea>
	                    </div>
	                    <div class="mb-3">
	                        <label class="form-label">문제 유형</label>
	                        <select class="form-select questionType readonly-select" name="testQuestnList[${questionIndex}].queType" data-index="${questionIndex}" onchange="toggleOptions(${questionIndex})">
	                            <option value="QT01" selected>객관식</option>
	                            <option value="QT02">주관식</option>
	                        </select>
	                    </div>
	                    <div id="queScoreContainer${questionIndex}" class="mb-3 ${isPersonalityTest ? 'd-none' : ''}">
	                        <label for="queScore${questionIndex}" class="form-label">문제 점수</label>
	                        <input type="number" id="queScore${questionIndex}" name="testQuestnList[${questionIndex}].queScore" class="form-control" min="0">
	                    </div>
	                    <div class="objective-options" id="objectiveOptions${questionIndex}">
							<input type="hidden" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].queType" value="QT01">
	                        <div class="options-container" id="optionsContainer${questionIndex}">
	                            ${isPersonalityTest ? generateOptionsHtml(questionIndex, true) : ''}
	                        </div>
	                        ${isPersonalityTest ? '' : '<button type="button" class="btn btn-secondary btn-sm mb-2" onclick="addOption(${questionIndex})">선택지 추가</button>'}
	                    </div>
	                </div>
	            </div>`;
			
		} else {
	        questionHtml = `
	            <div class="card mb-3" data-question-index="${questionIndex}" id="question${questionIndex}">
	                <div class="card-header card-header-test">
	                    문제 ${questionIndex}
	                    <button type="button" class="btn-close float-end" aria-label="Close" onclick="removeQuestion(${questionIndex})"></button>
	                </div>
	                <div class="card-body">
	                    <!-- 문제 순번 (hidden) -->
	                    <input type="hidden" name="testQuestnList[${questionIndex}].queTurn" value="${questionIndex}">
	                    
	                    <!-- 문제 파일 업로드 -->
	                    <div class="mb-3">
	                        <label for="questionFile${questionIndex}" class="form-label">문제 파일 업로드</label>
	                        <input type="file" id="questionFile${questionIndex}" name="testQuestnList[${questionIndex}].uploadFiles" class="form-control" onchange="handleFileChange(${questionIndex}, 'question')">
	                    </div>
	
	                    <!-- 문제 지문 -->
	                    <div class="mb-3">
	                        <label for="questionText${questionIndex}" class="form-label">문제 지문</label>
	                        <textarea id="questionText${questionIndex}" name="testQuestnList[${questionIndex}].queCont" class="form-control" rows="2"></textarea>
	                    </div>
	
	                    <!-- 문제 유형 선택 -->
						<div class="mb-3">
						    <label class="form-label">문제 유형</label>
						    <select class="form-select questionType" name="testQuestnList[${questionIndex}].queType" data-index="${questionIndex}" onchange="toggleOptions(${questionIndex})">
						        <option value="QT01">객관식</option>
						        <option value="QT02">주관식</option>
						    </select>
						</div>
	
	                    <!-- 객관식 문제 점수 입력 -->
						<div id="queScoreContainer${questionIndex}" class="mb-3">
						    <label for="queScore${questionIndex}" class="form-label">문제 점수</label>
						    <input type="number" id="queScore${questionIndex}" name="testQuestnList[${questionIndex}].queScore" class="form-control" min="0">
						</div>
	
	                    <!-- 객관식 선택지 -->
	                    <div class="objective-options" id="objectiveOptions${questionIndex}">
	                        <input type="hidden" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].queType" value="QT01">
	                        <button type="button" class="btn btn-secondary btn-sm mb-2" onclick="addOption(${questionIndex}, ${itemIndex})">선택지 추가</button>
	                        <div class="options-container" id="optionsContainer${questionIndex}">
	                            <!-- 기본 선택지 (삭제 버튼 없음) -->
	                            <div class="input-group mb-2 align-items-center">
	                                <input type="checkbox" class="form-check-input me-2 answerCheckbox" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].itemYn" value="Y" checked />
	                                <input type="text" class="form-control" placeholder="기본 선택지" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].itemCont" value="">
	                                <input type="file" class="form-control" id="optionFile${questionIndex}" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].uploadFiles">
	                            </div>
	                        </div>
	                    </div>
	
	                    <!-- 주관식 키워드 -->
	                    <div class="subjective-options${questionIndex} d-none" id="subjectiveOptions${questionIndex}">
	                        <input type="hidden" name="testQuestnList[${questionIndex}].queType" value="QT02">
	                        <button type="button" class="btn btn-secondary btn-sm mb-2" onclick="addKeyword(${questionIndex}, ${itemIndex}, ${keywordIndex})">키워드 추가</button>
	                        <div class="keyword-container">
	                            <!-- 기본 키워드 (삭제 버튼 없음) -->
	                            <div class="input-group mb-2">
	                                <input type="text" class="form-control keyword-input" placeholder="기본 키워드" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].keywordList[${keywordIndex}].keywdCont" value="">
	                                <input type="number" class="form-control keyword-score" placeholder="점수" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].keywordList[${keywordIndex}].keywdScore" value="">
	                            </div>
	                        </div>
	                    </div>
	
	                </div>
	            </div>`;
			
		}


        // 문제 내용 추가
        $('#questionsArea').append(questionHtml);

        // 네비게이션 버튼 추가
        const navButtonHtml = `
            <div class="col-xl-2 m-1 d-flex justify-content-center align-items-center">
                <button type="button" class="btn btn-link nav-link text-center test-nav-btn active" id="navBtn${questionIndex}" onclick="showQuestion(${questionIndex})">
                    문제 ${questionIndex}
                </button>
            </div>`;

        $('#questionsNavigation').append(navButtonHtml);

        // 첫 번째 문제는 기본으로 활성화
        if (questionIndex === 1) { // 첫 번째 문제 활성화
		    $('#navBtn1').addClass('active');
		    $('#question1').removeClass('d-none').addClass('d-block');
		}
	});
	
	// 검사 유형 변경 시 동작
    $('#testCd').on('change', function () {
        $('#questionsArea').empty();
        $('#questionsNavigation').empty();
        questionIndex = 0;

        
        addQuestion();
    });

	// 초기 문제 생성
    $('#addQuestion').trigger('click'); // 첫 번째 문제를 자동 추가

    // 객관식 선택지 추가
	window.addOption = function (questionIndex) {
	    const optionsCount = $(`#optionsContainer${questionIndex} .input-group`).length;
	    if (optionsCount >= 5) {
	        alert('객관식 선택지는 최대 5개까지만 추가할 수 있습니다.');
	        return;
	    }
	
	    const optionHtml = `
	    <div class="input-group mb-2 align-items-center">
	        <input type="checkbox" class="form-check-input me-2 answerCheckbox" name="testQuestnList[${questionIndex}].itemList[${optionsCount}].itemYn" value="Y" />
	        <input type="text" class="form-control" placeholder="선택지 내용" name="testQuestnList[${questionIndex}].itemList[${optionsCount}].itemCont">
	        <input type="file" class="form-control" name="testQuestnList[${questionIndex}].itemList[${optionsCount}].uploadFiles">
	        <button type="button" class="btn btn-danger" onclick="$(this).closest('.input-group').remove()">삭제</button>
	    </div>`;
	    $(`#optionsContainer${questionIndex}`).append(optionHtml);
	};

    // 주관식 키워드 추가
    window.addKeyword = function (questionIndex, itemIndex) {
	    const keywordsCount = $(`#subjectiveOptions${questionIndex} .keyword-container .input-group`).length;
	    if (keywordsCount >= 5) {
	        alert('주관식 키워드는 최대 5개까지만 추가할 수 있습니다.');
	        return;
	    }
	
	    const keywordHtml = `
	    <div class="input-group mb-2">
	        <input type="text" class="form-control keyword-input" placeholder="키워드 내용" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].keywordList[${keywordsCount}].keywdCont">
	        <input type="number" class="form-control keyword-score" placeholder="점수" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].keywordList[${keywordsCount}].keywdScore">
	        <button type="button" class="btn btn-danger btn-sm" onclick="$(this).closest('.input-group').remove()">삭제</button>
	    </div>`;
	
	    $(`#subjectiveOptions${questionIndex} .keyword-container`).append(keywordHtml);
	};

   // 문제 유형 변경 시 동작
	window.toggleOptions = function (index) {
	    const selectedType = $(`.questionType[data-index="${index}"]`).val();
		// 문제 점수 초기화
        $(`#queScoreContainer${index} input[name="testQuestnList[${index}].queScore"]`).val("");
		$(`#subjectiveOptions${index} .keyword-container .input-group:first`).find('input').val("");
        $(`#subjectiveOptions${index} .keyword-container .input-group`).not(':first').remove();

        // 기본 선택지 내용 및 파일 초기화
        $(`#optionsContainer${index} .input-group:first`).find('input[type="text"], input[type="file"]').val(""); 
	    if (selectedType === 'QT01') { // 객관식
	        // 객관식 옵션 활성화
	        $(`#objectiveOptions${index}`).removeClass('d-none');
	        $(`#subjectiveOptions${index}`).addClass('d-none');
	        $(`#queScoreContainer${index}`).removeClass('d-none'); // 점수 입력 필드 표시
	
	        // 모든 itemList의 queType을 QT01로 설정
	        $(`input[name^="testQuestnList[${index}].itemList"][name$=".queType"]`).each(function () {
	            $(this).val("QT01");
	        });
	
	        // 객관식일 때 checkbox의 value를 Y로 설정
	        $(`#optionsContainer${index} input[type="checkbox"][name*="itemYn"]`).each(function () {
	            $(this).prop('checked', true);
	            $(this).val("Y");
	        });
	
	        // 기존 주관식 데이터를 초기화 (기본 키워드는 유지)
	        $(`#subjectiveOptions${index} .keyword-container .input-group`).not(':first').remove(); // 기본 키워드 제외 제거

	    } else { // 주관식
	        // 주관식 옵션 활성화
	        $(`#objectiveOptions${index}`).addClass('d-none');
	        $(`#subjectiveOptions${index}`).removeClass('d-none');
	        $(`#queScoreContainer${index}`).addClass('d-none'); // 점수 입력 필드 숨기기
	
	        // 모든 itemList의 queType을 QT02로 설정
	        $(`input[name^="testQuestnList[${index}].itemList"][name$=".queType"]`).each(function () {
	            $(this).val("QT02");
	        });

			// 주관식일 때 checkbox의 value를 N로 설정
	        $(`#optionsContainer${index} input[type="checkbox"][name*="itemYn"]`).each(function () {
	            $(this).prop('checked', true);
	            $(this).val("N");
	        });
	
	        // 기존 객관식 데이터를 초기화 (기본 선택지는 유지)
        	$(`#optionsContainer${index} .input-group`).not(':first').remove(); // 기본 선택지 제외 제거
	    }
	};

   // 문제 점수 변경 시 선택지의 itemScore 업데이트
	 /*window.updateItemScores = function (questionIndex) {
	    const itemScore = $(`#itemScore${questionIndex}`).val();
	
	    // 점수가 올바르게 입력되었는지 확인
	    if (isNaN(itemScore) || itemScore <= 0) {
	        alert('문제 점수는 0보다 큰 숫자여야 합니다.');
	        return;
	    }
	
	    // 선택된 정답(`itemYn: Y`)에 문제 점수 적용
	    $(`#optionsContainer${questionIndex} input[type="radio"]:checked`)
	        .closest('.input-group')
	        .find(`input[name^="testQuestnList[${questionIndex}].itemList"].itemScore`)
	        .val(itemScore);
	
	    // 선택되지 않은 정답의 점수는 초기화
	    $(`#optionsContainer${questionIndex} input[type="radio"]:not(:checked)`)
	        .closest('.input-group')
	        .find(`input[name^="testQuestnList[${questionIndex}].itemList"].itemScore`)
	        .val(0);
	};*/
	
	// 정답 여부 변경 시 itemScore 값 업데이트
	/*$(document).on('change', 'input[name^="testQuestnList"][name$=".itemYn"]', function () {
	    const questionIndex = $(this).closest('.card').data('question-index');
	    const itemIndex = $(this).closest('.input-group').index(); // itemIndex 구하기
	    const itemScore = $(`#itemScore${questionIndex}`).val();
		console.log('=======================================');
	    console.log('questionIndex : ' + questionIndex);
		console.log('itemIndex : ' + itemIndex);
		console.log('itemScore : ' + itemScore);
		console.log('=======================================');
	    // 정답 여부가 변경되었을 때, 이전 선택지의 itemScore를 초기화하고, 새 정답 선택지에 점수를 적용
	    if ($(this).val() === "Y") {
	        // 정답 선택지를 선택한 경우, itemScore 자동 업데이트
	        $(this).closest('.input-group')
	            .find(`input[name="testQuestnList[${questionIndex}].itemList[${itemIndex}].itemScore"]`)
	            .val(itemScore);
	    } else {
	        // 정답을 해제한 경우, itemScore 초기화
	        $(this).closest('.input-group')
	            .find(`input[name="testQuestnList[${questionIndex}].itemList[${itemIndex}].itemScore"]`)
	            .val('');
	    }
	});*/

    // 문제 네비게이션 클릭 시 문제 영역에 표시
    window.showQuestion = function(index) {
        // 모든 문제의 active 클래스를 제거
        $('#questionsNavigation .nav-link').removeClass('active');
        $(`#navBtn${index}`).addClass('active');

        // 모든 문제를 숨기고 해당 문제만 표시
        $('#questionsArea .card').removeClass('d-block').addClass('d-none');
        $(`#question${index}`).removeClass('d-none').addClass('d-block');
    };

    // "모두 보기" 버튼 클릭 시 모든 문제 보기
    window.showAllQuestions = function() {
        $('#questionsArea .card').removeClass('d-none').addClass('d-block');
        $('#questionsNavigation .nav-link').addClass('active');
    };
    
    // 문제 보기 버튼에 클릭 이벤트 리스너 추가
    $('#showAllQuestions').on('click', function () {
        showAllQuestions(); // showAllQuestions 함수 호출
    });

    // 문제 제거
    window.removeQuestion = function (index) {
        $(`#question${index}`).remove();
        $(`#navBtn${index}`).parent().remove();

        // 문제 및 네비게이션 번호 재정렬
        $('#questionsArea .card').each(function (i) {
            const newIndex = i + 1;
            $(this).attr('data-question-index', newIndex).attr('id', `question${newIndex}`);
            $(this).find('.card-header').html(`
                문제 ${newIndex}
                <button type="button" class="btn-close float-end" aria-label="Close" onclick="removeQuestion(${newIndex})"></button>
            `);
            $(this).find('.form-control, .form-select').each(function () {
                const name = $(this).attr('name');
                if (name) {
                    $(this).attr('name', name.replace(/\d+/, newIndex));
                }
                const id = $(this).attr('id');
                if (id) {
                    $(this).attr('id', id.replace(/\d+/, newIndex));
                }
            });
        });

        $('#questionsNavigation .test-nav-btn').each(function (i) {
            const newIndex = i + 1;
            $(this).text(`문제 ${newIndex}`);
            $(this).attr('id', `navBtn${newIndex}`).attr('onclick', `showQuestion(${newIndex})`);
        });

        questionIndex = $('#questionsArea .card').length;
    };

    // 폼 제출 이벤트
    $('#testForm').on('submit', function (e) {
        e.preventDefault();

        // 입력받은 시험시간(분)을 가져오기
        const testTimeMinutes = parseInt($('#testTime').val(), 10);

        if (isNaN(testTimeMinutes) || testTimeMinutes <= 0) {
            alert('시험시간을 올바르게 입력해주세요.');
            return;
        }

        // 분을 초로 변환
        const testTimeSeconds = testTimeMinutes * 60;

        // 변환한 값을 hidden input으로 추가
        $('<input>')
            .attr('type', 'hidden')
            .attr('name', 'test.testTime')
            .val(testTimeSeconds)
            .appendTo('#testForm');

        // 폼 제출
        this.submit();
    });

	// 모든 select 요소에 대해 readonly 효과 적용
	document.addEventListener('mousedown', function (e) {
        if (e.target.classList.contains('readonly-select')) {
            e.preventDefault(); // 선택 방지
        }
    });

	// 체크박스 최소 1개 최대 총 개수 - 1
	$(document).on('change', '.answerCheckbox', function () {
	    const optionsContainer = $(this).closest('.options-container'); // 현재 선택지가 포함된 영역
	    const totalOptions = optionsContainer.find('.answerCheckbox').length; // 선택지 총 개수
	    const maxAnswers = totalOptions - 1; // 최대 허용 정답 개수 (총 개수 - 1)
	    const checkedCount = optionsContainer.find('.answerCheckbox:checked').length; // 현재 체크된 정답 개수
	
	    if (checkedCount > maxAnswers) {
	        sweatAlert('warning', `정답은 최대 ${maxAnswers}개까지만 선택할 수 있습니다.`);
	        $(this).prop('checked', false); // 선택을 취소
	    } else if (checkedCount === 0) {
	        sweatAlert('warning', '최소 1개의 정답은 반드시 선택해야 합니다.');
	        $(this).prop('checked', true); // 최소 하나는 체크되도록 유지
	    }
	});
});
