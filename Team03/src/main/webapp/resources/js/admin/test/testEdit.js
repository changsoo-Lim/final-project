$(document).ready(function () {
	const baseUrl = document.body.dataset.url;
	console.log(testQuestnList);
    let questionIndex = 0;
    let itemIndex = 0;
    let keywordIndex = 0;

	testQuestnList.forEach((question, qIndex) => {
    if (!question.itemList || !Array.isArray(question.itemList)) {
	        return; // Skip processing this question
	    }
	    question.itemList.forEach((item, itemIndex) => {
	        if (!item.itemFile) {
	            item.itemFile = { fileDetails: [] }; // 기본값 설정
	        }
	        if (!item.itemFile.fileDetails || !item.itemFile.fileDetails.length) {
	            item.itemFile.fileDetails = []; // 기본값 설정
	        }
	    });
	});
		

    // 페이지 로드 시 데이터를 초기화
    if (testQuestnList && Array.isArray(testQuestnList)) {
        testQuestnList.forEach((question, qIndex) => {
            questionIndex = qIndex + 1;

			const isPersonalityTest = $('#testCd').val() === 'TE01' ? true : false;
			console.log("isPersonalityTest : " + isPersonalityTest);
            const isObjective = question.itemList[0]?.queType === 'QT01'; // 객관식 여부
            const isSubjective = question.itemList[0]?.queType === 'QT02'; // 주관식 여부
			let questionHtml;
			
			let optionsHtml = '';
			if(isPersonalityTest){
		        for (let i = 0; i < 5; i++) {
		            optionsHtml += `
		                <div class="input-group mb-2 align-items-center">
		                    <input type="text" class="form-control" placeholder="선택지 ${i + 1}" name="testQuestnList[${questionIndex}].itemList[${i}].itemCont" value="${question?.itemList?.[i]?.itemCont || ''}">
							<input type="hidden" name="testQuestnList[${questionIndex}].itemList[${i}].queType" value="QT01">
		                    <input type="hidden" name="testQuestnList[${questionIndex}].itemList[${i}].itemYn" value="Y" >
		                    <input type="number" class="form-control" placeholder="점수" name="testQuestnList[${questionIndex}].itemList[${i}].itemScore" style="max-width: 80px;" value="${question?.itemList?.[i]?.itemScore || ''}">
		                </div>`;
						// <input type="file" class="form-control" name="testQuestnList[${questionIndex}].itemList[${i}].uploadFiles">
		        }
			}
			
			
            // 문제 HTML 생성
			if(isPersonalityTest){
				questionHtml = `
	            <div class="card mb-3" data-question-index="${questionIndex}" id="question${questionIndex}">
					<input type="hidden" name="testQuestnList[${questionIndex}].queNo" id="queNo${questionIndex}" value="${question?.queNo || ''}">
	                <div class="card-header card-header-test">
	                    문제 ${questionIndex}
	                    <button type="button" class="btn-close float-end" aria-label="Close" onclick="removeQuestion(${questionIndex})"></button>
	                </div>
					<!-- 문제 파일 업로드 -->
	                <div class="card-body">
	                    <input type="hidden" name="testQuestnList[${questionIndex}].queTurn" value="${question.queTurn || questionIndex}">
						<label for="questionFile${questionIndex}" class="form-label me-3">문제 파일 업로드</label>
	                    <div class="input-group mb-3">
							<!-- 파일명 표시 -->
							<input type="test" id="questionFile${questionIndex}-name" class="form-control" readonly value="선택된 파일이 없음" />
						    <!-- 파일 업로드 -->
						    <input type="file" id="questionFile${questionIndex}" class="form-control d-none file-upload" name="testQuestnList[${questionIndex}].uploadFiles" data-target-id="questionFile${questionIndex}">
						    <!-- 이미지 선택 버튼 -->
							<input type="hidden" id="questionFile${questionIndex}Path" value="${question?.questionFile?.fileDetails?.[0]?.fileStreCours || ''}">
							<input type="hidden" id="questionFile${questionIndex}Name" value="${question?.questionFile?.fileDetails?.[0]?.orignlFileNm || ''}">
							<input type="hidden" id="questionFile${questionIndex}AtchFileNo" value="${question?.questionFile?.fileDetails?.[0]?.atchFileNo || ''}">
							<input type="hidden" id="questionFile${questionIndex}FileSn" value="${question?.questionFile?.fileDetails?.[0]?.fileSn || ''}">
						    <button type="button" class="btn btn-primary file-upload-btn" data-target-id="questionFile${questionIndex}">이미지 선택</button>
	                    </div>
						<!-- 문제 지문 -->
	                    <div class="mb-3">
	                        <label for="questionText${questionIndex}" class="form-label">문제 지문</label>
	                        <textarea id="questionText${questionIndex}" name="testQuestnList[${questionIndex}].queCont" class="form-control" rows="2">${question.queCont || ''}</textarea>
	                    </div>
						<!-- 문제 유형 -->
	                    <div class="mb-3">
	                        <label class="form-label">문제 유형</label>
	                        <select class="form-select questionType readonly-select" name="testQuestnList[${questionIndex}].queType" data-index="${questionIndex}" onchange="toggleOptions(${questionIndex})">
	                            <option value="QT01" ${isObjective ? 'selected' : ''}>객관식</option>
	                            <option value="QT02" ${isSubjective ? 'selected' : ''}>주관식</option>
	                        </select>
	                    </div>
						<!-- 객관식 문제 점수 -->
	                    <div id="queScoreContainer${questionIndex}" class="mb-3 ${isPersonalityTest || isObjective  ? 'd-none' : ''}">
	                        <label for="queScore${questionIndex}" class="form-label">문제 점수</label>
	                        <input type="number" id="queScore${questionIndex}" name="testQuestnList[${questionIndex}].queScore" class="form-control" min="0" value="${question.itemList?.find(item => item.itemScore != null && item.itemScore !== 0)?.itemScore || ''}">
	                    </div>
	                    <div class="objective-options" id="objectiveOptions${questionIndex}">
	                        <div class="options-container" id="optionsContainer${questionIndex}">
	                            ${optionsHtml}
	                        </div>
	                        ${isPersonalityTest ? '' : '<button type="button" class="btn btn-secondary btn-sm mb-2" onclick="addOption(${questionIndex})">선택지 추가</button>'}
	                    </div>
	                </div>
	            </div>`;
			} else {
	            questionHtml = `
	                <div class="card mb-3" data-question-index="${questionIndex}" id="question${questionIndex}">
						<input type="hidden" name="testQuestnList[${questionIndex}].queNo" id="queNo${questionIndex}" value="${question?.queNo || ''}">
	                    <div class="card-header card-header-test">
	                        문제 ${questionIndex}
	                        <button type="button" class="btn-close float-end" aria-label="Close" onclick="removeQuestion(${questionIndex})"></button>
	                    </div>
	                    <div class="card-body">
	                        <!-- 문제 순번 -->
	                        <input type="hidden" name="testQuestnList[${questionIndex}].queTurn" value="${question.queTurn || questionIndex}">
	
	                        <!-- 문제 파일 업로드 -->
							<label for="questionFile${questionIndex}" class="form-label me-3">문제 파일 업로드</label>
	                        <div class="input-group mb-3">
							    <!-- 파일명 표시 -->
								<input type="test" id="questionFile${questionIndex}-name" class="form-control" readonly value="선택된 파일이 없음" />
							    <!-- 파일 업로드 -->
							    <input type="file" id="questionFile${questionIndex}" class="form-control d-none file-upload" name="testQuestnList[${questionIndex}].uploadFiles" data-target-id="questionFile${questionIndex}">
							    <!-- 이미지 선택 버튼 -->
								<input type="hidden" id="questionFile${questionIndex}Path" value="${question?.questionFile?.fileDetails?.[0]?.fileStreCours || ''}">
								<input type="hidden" id="questionFile${questionIndex}Name" value="${question?.questionFile?.fileDetails?.[0]?.orignlFileNm || ''}">
								<input type="hidden" id="questionFile${questionIndex}AtchFileNo" value="${question?.questionFile?.fileDetails?.[0]?.atchFileNo || ''}">
								<input type="hidden" id="questionFile${questionIndex}FileSn" value="${question?.questionFile?.fileDetails?.[0]?.fileSn || ''}">
							    <button type="button" class="btn btn-primary file-upload-btn" data-target-id="questionFile${questionIndex}">이미지 선택</button>
							</div>
	
	                        <!-- 문제 지문 -->
	                        <div class="mb-3">
	                            <label for="questionText${questionIndex}" class="form-label">문제 지문</label>
	                            <textarea id="questionText${questionIndex}" name="testQuestnList[${questionIndex}].queCont" class="form-control" rows="2">${question.queCont || ''}</textarea>
	                        </div>
	
	                        <!-- 문제 유형 -->
	                        <div class="mb-3">
	                            <label class="form-label">문제 유형</label>
	                            <select class="form-select questionType" name="testQuestnList[${questionIndex}].queType" data-index="${questionIndex}" onchange="toggleOptions(${questionIndex})">
	                                <option value="QT01" ${isObjective ? 'selected' : ''}>객관식</option>
	                                <option value="QT02" ${isSubjective ? 'selected' : ''}>주관식</option>
	                            </select>
	                        </div>
	
	                        <!-- 객관식 문제 점수 -->
	                        <div id="queScoreContainer${questionIndex}" class="mb-3 ${isObjective ? '' : 'd-none'}">
	                            <label for="queScore${questionIndex}" class="form-label">문제 점수</label>
	                            <input type="number" id="queScore${questionIndex}" name="testQuestnList[${questionIndex}].queScore" class="form-control" min="0" value="${question.itemList?.find(item => item.itemScore != null && item.itemScore !== 0)?.itemScore || ''}">
	                        </div>
	
	                        <!-- 객관식 선택지 -->
	                        <div class="objective-options ${isObjective ? '' : 'd-none'}" id="objectiveOptions${questionIndex}">
								<input type="hidden" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].queType" value="${question.itemList[0]?.queType}">
	                            <button type="button" class="btn btn-secondary btn-sm mb-2" onclick="addOption(${questionIndex}, ${itemIndex})">선택지 추가</button>
	                            <div class="options-container" id="optionsContainer${questionIndex}">
	                                ${question.itemList
	                                    ?.map(
	                                        (item, itemIndex) => `
	                                    <div class="input-group mb-2 align-items-center">
											<input type="checkbox" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].itemYn" value="Y" class="form-check-input me-2 answerCheckbox" id="optionCheck${questionIndex}-${itemIndex}"  ${item.itemYn === 'Y' ? 'checked' : ''}>
											<input type="hidden" id="optionFile${questionIndex}_${itemIndex}Path"       value="${item?.itemFile?.fileDetails?.[0]?.streFileNm || ''}" />
											<input type="hidden" id="optionFile${questionIndex}_${itemIndex}Name"       value="${item?.itemFile?.fileDetails?.[0]?.orignlFileNm || ''}" />
											<input type="hidden" id="optionFile${questionIndex}_${itemIndex}AtchFileNo" value="${item?.atchFileNo || ''}">
											<input type="hidden" id="optionFile${questionIndex}_${itemIndex}FileSn"     value="${item?.itemFile?.fileDetails?.[0]?.fileSn || ''}">
	                                        <input type="text" class="form-control test-item" placeholder="선택지 내용" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].itemCont" value="${item.itemCont || ''}">
											<input type="hidden" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].itemScore" id="itemScore${questionIndex}_${itemIndex}"  value="${item.itemScore || ''}" />
											<input type="hidden" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].queNo" id="queNo${questionIndex}_${itemIndex}"  value="${item.queNo || ''}" />
											<input type="hidden" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].itemNo" id="itemNo${questionIndex}_${itemIndex}"  value="${item.itemNo || ''}" />
											<input type="file" id="optionFile${questionIndex}_${itemIndex}" class="form-control d-none file-upload" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].uploadFiles" data-target-id="optionFile${questionIndex}_${itemIndex}" />
											<button type="button" class="btn btn-primary file-upload-btn" data-target-id="optionFile${questionIndex}_${itemIndex}" >이미지 선택</button>
									        <button type="button" class="btn btn-danger" onclick="$(this).closest('.input-group').remove()">삭제</button>
	                                    </div>`
	                                    )
	                                    .join('') || `
	                                    <div class="input-group mb-2 align-items-center">
											<input type="checkbox" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].itemYn" value="Y" class="form-check-input me-2 answerCheckbox" id="optionCheck${questionIndex}-${itemIndex}"  ${item.itemYn === 'Y' ? 'checked' : ''}>
	                                        <input type="text" class="form-control test-item" placeholder="기본 선택지" name="testQuestnList[${questionIndex}].itemList[0].itemCont" value="${item.itemCont || ''}">
											<input type="file" id="optionFile${questionIndex}_${itemIndex}" class="form-control d-none file-upload" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].uploadFiles" data-target-id="optionFile${questionIndex}_${itemIndex}" />
											<button type="button" class="btn btn-primary file-upload-btn" data-target-id="optionFile${questionIndex}_${itemIndex}" >이미지 선택</button>
	                                    </div>`}
	                            </div>
								<div class="mb-2">
									<small class="text-muted">※ "정답" 체크박스를 선택하여 정답을 지정하세요.</small>
								</div>
	                        </div>
	
	                        <!-- 주관식 키워드 -->
	                        <div class="subjective-options ${isSubjective ? '' : 'd-none'}" id="subjectiveOptions${questionIndex}">
								<input type="hidden" name="testQuestnList[${questionIndex}].queType" value="${question.itemList[0]?.queType}">
	                            <button type="button" class="btn btn-secondary btn-sm mb-2" onclick="addKeyword(${questionIndex}, ${itemIndex}, ${keywordIndex})">키워드 추가</button>
	                            <div class="keyword-container">
	                                ${question.itemList?.[0]?.keywordList
	                                    ?.map(
	                                        (keyword, keywordIndex) => `
	                                    <div class="input-group mb-2">
											<input type="hidden" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].keywordList[${keywordIndex}].keywdNo" value="${keyword?.keywdNo || ''}">
	                                        <input type="text" class="form-control keyword-input w-75" placeholder="키워드 내용" name="testQuestnList[${questionIndex}].itemList[0].keywordList[${keywordIndex}].keywdCont" value="${keyword.keywdCont || ''}">
	                                        <input type="number" class="form-control keyword-score" placeholder="점수" name="testQuestnList[${questionIndex}].itemList[0].keywordList[${keywordIndex}].keywdScore" value="${keyword.keywdScore || ''}">
									        <button type="button" class="btn btn-danger btn-sm" onclick="$(this).closest('.input-group').remove()">삭제</button>
	                                    </div>`
	                                    )
	                                    .join('') || `
	                                    <div class="input-group mb-2">
											<input type="text" class="form-control keyword-input w-75" placeholder="기본 키워드" name="testQuestnList[${questionIndex}].itemList[0].keywordList[0].keywdCont" value="${keyword.keywdCont || ''}">
	                                		<input type="number" class="form-control keyword-score" placeholder="점수" name="testQuestnList[${questionIndex}].itemList[0].keywordList[0].keywdScore" value="${keyword.keywdScore || ''}">
	                                    </div>`
									}
	                            </div>
	                        </div>
	                    </div>
	                </div>`;
			}

            // 문제 추가
            $('#questionsArea').append(questionHtml);

			// 객관식 선택지 처리
            if (isObjective) {
                $(`#optionsContainer${questionIndex} .input-group`).each((index, element) => {
                    if (index === 0) {
                        // 첫 번째 선택지 삭제 버튼 제거
                        $(element).find('button.btn-danger').remove();
                    }
                });
            }

            // 주관식 키워드 처리
            if (isSubjective) {
                $(`#subjectiveOptions${questionIndex} .keyword-container .input-group`).each((index, element) => {
                    if (index === 0) {
                        // 첫 번째 키워드 삭제 버튼 제거
                        $(element).find('button.btn-danger').remove();
                    }
                });
            }

			// 문제 파일 정보 초기화
			if (question?.questionFile?.fileDetails?.length > 0) {
			    const filePath = question.questionFile.fileDetails[0].streFileNm || '';
			    const fileName = question.questionFile.fileDetails[0].orignlFileNm || '';
			
			    // 파일명이 있다면 "이미지 선택" 버튼 옆에 표시
			    $(`#questionFile${questionIndex}-name`).val(fileName);
			    $(`#questionFile${questionIndex}Path`).val(filePath);
			} else {
			    // 값이 없을 경우 초기화
			    $(`#questionFile${questionIndex}-name`).val('선택된 파일이 없음');
			    $(`#questionFile${questionIndex}Path`).val('');
			}

            // 각 선택지 파일 정보 초기화
			question.itemList.forEach((item, itemIndex) => {
			    if (item?.itemFile?.fileDetails?.length > 0) {
			        const filePath = item.itemFile.fileDetails[0].streFileNm || '';
			        const fileName = item.itemFile.fileDetails[0].orignlFileNm || '';
			
			        $(`#optionFile${questionIndex}_${itemIndex}-name`).val(fileName);
			        $(`#optionFile${questionIndex}_${itemIndex}Path`).val(filePath);
			    } else {
			        // 값이 없을 경우 초기화
			        $(`#optionFile${questionIndex}_${itemIndex}-name`).val('선택된 파일이 없음');
			        $(`#optionFile${questionIndex}_${itemIndex}Path`).val('');
			    }
			});
			
            // 네비게이션 버튼 추가
            const navButtonHtml = `
                <div class="col-xl-2 m-1 d-flex justify-content-center align-items-center">
                    <button type="button" class="btn btn-link nav-link text-center test-nav-btn active" id="navBtn${questionIndex}" onclick="showQuestion(${questionIndex})">
                        문제 ${questionIndex}
                    </button>
                </div>`;
            $('#questionsNavigation').append(navButtonHtml);
        });
    }

	// 검사 유형 변경 시 동작
    $('#testCd').on('change', function () {
        $('#questionsArea').empty();
        $('#questionsNavigation').empty();
        questionIndex = 0;

        
        addQuestion();
    });

    // 초기 문제 생성
    if (questionIndex === 0) {
		$('# ')
		console.log("questionIndex : " + (questionIndex === 0));
        $('#addQuestion').trigger('click'); // 데이터가 없으면 기본 문제 추가
    }

    // 문제 추가 버튼 클릭 이벤트
    $('#addQuestion').on('click', function () {
        if (questionIndex >= 100) {
            alert('문제는 최대 100개까지만 추가할 수 있습니다.');
            return;
        }

        questionIndex++;

		const isPersonalityTest = $('#testCd').val() === 'TE01' ? true : false;
		let questionHtml;
		
		window.generateOptionsHtml = function (questionIndex, includeScore = false) {
	        let optionsHtml = '';
	        for (let i = 0; i < 5; i++) {
	            optionsHtml += `
	                <div class="input-group mb-2 align-items-center">
	                    <input type="text" class="form-control" placeholder="선택지 ${i + 1}" name="testQuestnList[${questionIndex}].itemList[${i}].itemCont">
	                    <input type="hidden" name="testQuestnList[${questionIndex}].itemList[${i}].itemYn" value="Y">
						<input type="hidden" name="testQuestnList[${questionIndex}].itemList[${i}].queType" value="QT01">
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
						<!-- 문제 순번 (hidden) -->
	                    <input type="hidden" name="testQuestnList[${questionIndex}].queTurn" value="${questionIndex}">
	                    
						<!-- 문제 파일 업로드 -->
						<label for="questionFile${questionIndex}" class="form-label me-3">문제 파일 업로드</label>
	                    <div class="input-group mb-3">
						    <!-- 파일명 표시 -->
							<input type="test" id="questionFile${questionIndex}-name" class="form-control" readonly value="선택된 파일이 없음" />
						    <!-- 파일 업로드 -->
						    <input type="file" id="questionFile${questionIndex}" class="form-control d-none file-upload" name="testQuestnList[${questionIndex}].uploadFiles" data-target-id="questionFile${questionIndex}">
						    <!-- 이미지 선택 버튼 -->
							<input type="hidden" id="questionFile${questionIndex}Path" value="">
							<input type="hidden" id="questionFile${questionIndex}Name" value="">
						    <button type="button" class="btn btn-primary file-upload-btn" data-target-id="questionFile${questionIndex}">이미지 선택</button>
						</div>
						<!-- 문제 지문 -->
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
						<label for="questionFile${questionIndex}" class="form-label me-3">문제 파일 업로드</label>
	                    <div class="input-group mb-3">
						    <!-- 파일명 표시 -->
							<input type="test" id="questionFile${questionIndex}-name" class="form-control" readonly value="선택된 파일이 없음" />
						    <!-- 파일 업로드 -->
						    <input type="file" id="questionFile${questionIndex}" class="form-control d-none file-upload" name="testQuestnList[${questionIndex}].uploadFiles" data-target-id="questionFile${questionIndex}">
						    <!-- 이미지 선택 버튼 -->
							<input type="hidden" id="questionFile${questionIndex}Path" value="">
							<input type="hidden" id="questionFile${questionIndex}Name" value="">
						    <button type="button" class="btn btn-primary file-upload-btn" data-target-id="questionFile${questionIndex}">이미지 선택</button>
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
	                                <input type="text" class="form-control test-item" placeholder="기본 선택지" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].itemCont" value="">
									<input type="file" id="optionFile${questionIndex}_${itemIndex}" class="form-control d-none file-upload" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].uploadFiles" data-target-id="optionFile${questionIndex}_${itemIndex}" />
									<button type="button" class="btn btn-primary file-upload-btn" data-target-id="optionFile${questionIndex}_${itemIndex}" >이미지 선택</button>
	                            </div>
	                        </div>
							<div class="mb-2">
								<small class="text-muted">※ "정답" 체크박스를 선택하여 정답을 지정하세요.</small>
							</div>
	                    </div>
	
	                    <!-- 주관식 키워드 -->
	                    <div class="subjective-options${questionIndex} d-none" id="subjectiveOptions${questionIndex}">
	                        <input type="hidden" name="testQuestnList[${questionIndex}].queType" value="QT02">
	                        <button type="button" class="btn btn-secondary btn-sm mb-2" onclick="addKeyword(${questionIndex}, ${itemIndex}, ${keywordIndex})">키워드 추가</button>
	                        <div class="keyword-container">
	                            <!-- 기본 키워드 (삭제 버튼 없음) -->
	                            <div class="input-group mb-2">
	                                <input type="text" class="form-control keyword-input w-75" placeholder="기본 키워드" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].keywordList[${keywordIndex}].keywdCont" value="">
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
	        <input type="text" class="form-control test-item" placeholder="선택지 내용" name="testQuestnList[${questionIndex}].itemList[${optionsCount}].itemCont">
			<input type="file" id="optionFile${questionIndex}_${optionsCount}" class="form-control d-none file-upload" name="testQuestnList[${questionIndex}].itemList[${optionsCount}].uploadFiles" data-target-id="optionFile${questionIndex}_${optionsCount}" />
			<button type="button" class="btn btn-primary file-upload-btn" data-target-id="optionFile${questionIndex}_${optionsCount}" >이미지 선택</button>
			
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
	        <input type="text" class="form-control keyword-input w-75" placeholder="키워드 내용" name="testQuestnList[${questionIndex}].itemList[${itemIndex}].keywordList[${keywordsCount}].keywdCont">
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
	
	let selectedFile = null; // 선택된 파일
    let activeInputId = null; // 현재 활성화된 파일 input ID
	const defaultImageUrl = `${baseUrl}/resources/images/default-image.png`; // 기본 이미지 경로
	
    /*// 이미지 선택 버튼 클릭 시 모달 열기
    $(document).on('click', '.file-upload-btn', function () {
        activeInputId = $(this).data('target-id');
        const fileInput = document.getElementById(activeInputId);
        const file = fileInput.files[0];
        const modalImage = $('#imagePreview');
        const modalFileName = $('#imageFileName');
        const modal = new bootstrap.Modal(document.getElementById('imagePreviewModal'));

        if (file && file.type.startsWith('image/')) {
            const reader = new FileReader();
            reader.onload = function (e) {
                modalImage.attr('src', e.target.result); // 이미지 미리보기 설정
                modalFileName.text(file.name); // 파일명 표시
            };
            reader.readAsDataURL(file);
        } else {
            modalImage.attr('src', `${defaultImageUrl}`); // 이미지 없음
            modalFileName.text('선택된 파일이 없음');
        }
        modal.show(); // 모달 열기
    });*/

 	// 이미지 선택 버튼 클릭 시 파일 정보 로드
	$(document).on('click', '.file-upload-btn', function () {
	    activeInputId = $(this).data('target-id');
		const deleteButton = $('#deleteButton');
	    const filePath = $(`#${activeInputId}Path`).val();
	    const fileName = $(`#${activeInputId}Name`).val();

		// 삭제용 데이터
		const fileAtchFileNo = $(`#${activeInputId}AtchFileNo`).val();
		const fileSn = $(`#${activeInputId}FileSn`).val();
		
		deleteButton.attr({ 'data-atch-file-no': fileAtchFileNo, 'data-file-sn': fileSn });
		
	    //console.log('기존 파일 경로:', filePath);
	    //console.log('기존 파일 이름:', fileName);
	
	    const modalImage = $('#imagePreview');
	    const modalFileName = $('#imageFileName');
	    const modal = new bootstrap.Modal(document.getElementById('imagePreviewModal'));
	
	    if (filePath && fileName) {
	        modalImage.attr('src', `${baseUrl}/images/adminTest/${filePath}`); // 기존 파일 경로 사용
	        modalFileName.text(fileName); // 기존 파일명 표시
	    } else {
	        modalImage.attr('src', `${defaultImageUrl}`); // 기본 이미지
	        modalFileName.text('선택된 파일이 없음');
	    }
	
	    modal.show(); // 모달 열기
	});
	
	// 새 업로드 버튼 클릭 시
	$('#uploadNewButton').on('click', function () {
	    const fileInput = document.getElementById(activeInputId);
	    fileInput.click(); // 파일 선택 창 열기
	
	    // 파일 선택 시 이벤트 처리
	    $(fileInput).off('change').on('change', function () {
	        const file = fileInput.files[0];
	
	        if (file && file.type.startsWith('image/')) {
	            const reader = new FileReader();
	            reader.onload = function (e) {
	                $('#imagePreview').attr('src', e.target.result); // 새 이미지 미리보기
	                $('#imageFileName').text(file.name); // 새 파일명
	                $(`#${activeInputId}-name`).val(file.name); // 파일명 업데이트
	                $(`#${activeInputId}Path`).val(''); // 새 파일 선택 시 경로 초기화
	            };
	            reader.readAsDataURL(file);
	        } else {
	            // 파일 선택이 취소되었을 경우 기존 데이터를 유지
	            const prevFilePath = $(`#${activeInputId}Path`).val();
	            const prevFileName = $(`#${activeInputId}Name`).val();
	
	            $('#imagePreview').attr('src', `${baseUrl}/images/question/${prevFilePath || ''}`);
	            $('#imageFileName').text(prevFileName || '선택된 파일이 없음');
	        }
	    });
	});
	
	/*// 삭제 버튼 클릭 시
	$('#deleteButton').on('click', function () {
	    const fileInput = document.getElementById(activeInputId);
	    fileInput.value = ''; // 파일 초기화
	    $('#imagePreview').attr('src', `${defaultImageUrl}`); // 기본 이미지 설정
	    $('#imageFileName').text('선택된 파일이 없음'); // 파일명 초기화
	    $(`#${activeInputId}-name`).val('선택된 파일이 없음'); // 파일명 필드 초기화
	    $(`#${activeInputId}Path`).val(''); // 경로 필드 초기화
	});*/
	
	// 취소 버튼 클릭 시
	$('#cancelButton').on('click', function () {
	    const modal = bootstrap.Modal.getInstance(document.getElementById('imagePreviewModal'));
	    modal.hide(); // 모달 닫기
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

	document.querySelectorAll("[data-atch-file-no][data-file-sn]").forEach(el => {
		el.addEventListener("click", async (e) => {
			e.preventDefault();
			const deleteResult = await Swal.fire({
			  title: "이미지 파일을 삭제하시겠습니까??",
			  text: "삭제 시에 파일 복구는 불가합니다.",
			  icon: "question",
			  showCancelButton: true,
			  confirmButtonColor: "#3085d6",
			  cancelButtonColor: "#d33",
			  confirmButtonText: "삭제",
			  cancelButtonText: "취소"
			});
			if (deleteResult.isConfirmed) {
		        await Swal.fire({
		            title: "삭제되었습니다.",
		            icon: "success"
		        });
		    } else {
		        return; // 취소 시 동작
		    }
			
			let atchFileNo = el.dataset.atchFileNo;
			let fileSn = el.dataset.fileSn;
			let url = `${baseUrl}/adminTest/123/file/${atchFileNo}/${fileSn}`.replace(/\/+/g, '/');
			//console.log(url);
			try {
				let resp = await fetch(url, {
					method: "DELETE",
					headers: {
						"accept": "application/json"
					}
				});

				if (resp.ok) {
					let obj = await resp.json();
					if (obj.success) {
						const fileInput = document.getElementById(activeInputId);
					    fileInput.value = ''; // 파일 초기화
					    $('#imagePreview').attr('src', `${defaultImageUrl}`); // 기본 이미지 설정
					    $('#imageFileName').text('선택된 파일이 없음'); // 파일명 초기화
					    $(`#${activeInputId}-name`).val('선택된 파일이 없음'); // 파일명 필드 초기화
					    $(`#${activeInputId}Path`).val(''); // 경로 필드 초기화
						atchFileNo = '';
						fileSn = '';
					}
				} else {
					console.error('Failed to delete file');
				}
			} catch (error) {
				console.error('Error:', error);
			}
		});
	});
	
	document.getElementById("deleteTest").addEventListener("click", async function (e) {
	    e.preventDefault();

	    // SweetAlert로 확인 메시지 표시
	    const deleteResult = await Swal.fire({
	        title: "시험을 삭제하시겠습니까?",
	        text: "삭제 시 복구는 불가합니다.",
	        icon: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#d33",
	        cancelButtonColor: "#3085d6",
	        confirmButtonText: "삭제",
	        cancelButtonText: "취소",
	    });
	
	    // 삭제 확인 시
	    if (deleteResult.isConfirmed) {
	        const testNo = this.dataset.testno;
	        const deleteUrl = `${baseUrl}/admin/test/${testNo}`; // PathVariable URL 생성
	
	        try {
	            // Axios로 DELETE 요청 보내기
	            const response = await axios.delete(deleteUrl, {
	                headers: {
	                    "Content-Type": "application/json", // 필요한 경우 추가
	                    Accept: "application/json",
	                },
	            });
	
	            // 요청 성공 시 처리
	            if (response.status === 200) {
	                await Swal.fire({
	                    title: "삭제되었습니다.",
	                    icon: "success",
	                });
	
	                // 삭제 후 다른 페이지로 이동 또는 화면 갱신
	                window.location.href = `${baseUrl}/admin/test/list`; // 목록 페이지로 이동
	            }
	        } catch (error) {
	            console.error("Error deleting test:", error);
	            await Swal.fire({
	                title: "삭제 실패",
	                text: "문제가 발생했습니다. 다시 시도해주세요.",
	                icon: "error",
	            });
	        }
	    } else {
	        // 취소 시 아무 작업도 하지 않음
	        //console.log("삭제 취소됨");
	    }
	});
	
	// 모든 select 요소에 대해 readonly 효과 적용
	document.addEventListener('mousedown', function (e) {
        if (e.target.classList.contains('readonly-select')) {
            e.preventDefault(); // 선택 방지
        }
    });
});
