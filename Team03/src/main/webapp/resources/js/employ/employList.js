// =================================== 함수 ===================================
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
// Confirm 함수
let confirmMessage = function(msg, title, result) {
    Swal.fire({
        title: title,
        text: msg,
        icon: 'question',
        showCancelButton: true,
        confirmButtonClass: 'btn-danger',
        confirmButtonText: '예',
        cancelButtonText: '아니오',
        customClass: {
            confirmButton: 'btn btn-danger'
        }
    }).then(function(response) {
        if (response.isConfirmed) { // 사용자가 "예"를 클릭했을 때
            result(true); // 콜백에 true 전달
        } else { // 사용자가 "아니오"를 클릭했을 때
            result(false); // 콜백에 false 전달
        }
    });
};

// *** (A) 공통 선택 처리 함수
function handleItemClick($container, $this, $targetArea, $targetItem, $hiddenArea) {
	let selectedCodeCd = $this.attr('data-codeCd');
	let selectedCodeNm = $this.attr('data-codeNm');

	// 기존 항목에서 selected 제거
	$container.removeClass('selected');

	// 현재 클릭된 항목에 selected 추가
	$this.addClass('selected');

	// 대상 영역의 항목들 숨기기
	$targetArea.find($targetItem).hide();
	// parent가 selectedCodeCd와 일치하는 것만 보이기
	$targetArea.find($targetItem).filter(function() {
		return $(this).attr('data-parent') === selectedCodeCd;
	}).show();
	
	// parentNm 요소 삽입
	$targetItem.attr('data-parentNm', selectedCodeNm);
	
	// 대상 영역 표시
	$targetArea.show();
}

$(function() {
	const contextPath = document.body.dataset.url || "";

	// ========== 업종 대분류 클릭 ==========
	$(document).on('click', '.job1-item', function() {
		$('#job3-area').hide();
		handleItemClick($('.job1-item'), $(this), $('#job2-area'), $('.job2-item'));
	});
	// ========== 업종 중분류 클릭 ==========
	$(document).on('click', '.job2-item', function() {
		handleItemClick($('.job2-item'), $(this), $('#job3-area'), $('.job3-item'));
	});
	// ========== 지역 대분류 클릭 ==========
	$(document).on('click', '.region-item', function() {
		handleItemClick($('.region-item'), $(this), $('#city-gungu-area'), $('.gungu-item'));
	});
	// ========== 상세조건 클릭 ==========
	$(document).on('click', '.detail1-item', function() {
		$('.detail2-box, .detail3-box').hide();
		handleItemClick($('.detail1-item'), $(this), $('#detail4-area'), $('.detail4-item'));
	});
	// ========== 복리후생 클릭 ==========
	$(document).on('click', '.detail1-item', function() {
		if ($(this).hasClass('bft-item')) {
			$('.detail2-box').show();
		}
		handleItemClick($('.detail1-item'), $(this), $('#detail2-area'), $('.detail2-item'));
	});
	// ========== 복리후생 상위 클릭 ==========
	$(document).on('click', '.detail2-item', function() {
		$('.detail3-box').show();
		handleItemClick($('.detail2-item'), $(this), $('#detail3-area'), $('.detail3-item'));
	});

	// ======================================== 검색 조건 박스 추가 ========================================
	// ========== 업종 소분류 체크 ==========
	$("#job3-area").on("click", "li.job3-item", function() {
		toggleCheckboxState($(this));
	});
	// ========== 지역 중분류 체크 ==========
	$("#city-gungu-area").on("click", "li.gungu-item", function() {
		toggleCheckboxState($(this));
	});
	// ========== 고용형태 체크 ==========
	$("#detail4-area").on("click", "li.workType1-item", function() {
		toggleCheckboxState($(this));
	});
	// ========== 학력 체크 ==========
	$("#detail4-area").on("click", "li.workType2-item", function() {
		toggleSingleCheckboxState($(this), ".workType2-item");
	});
	// ========== 연봉 체크 ==========
	$("#detail4-area").on("click", "li.salary-item", function() {
		toggleSingleCheckboxState($(this), ".salary-item");
	});
	// ========== 복리후생 하위 체크 ==========
	$("#detail3-area").on("click", "li.detail3-item", function() {
		toggleCheckboxState($(this));
	});

	/******************************************************
	 * 함수명: toggleSingleCheckboxState
	 * 목적  : "단일 선택"용.
	 * ----------------------------------------------------
	 *  1) 같은 "그룹"의 모든 항목 해제
	 *  2) 현재 항목만 체크 활성화
	 *  3) selected-box도 해당 그룹 것은 모두 제거 후,
	 *     현재 항목만 새로 생성
	 ******************************************************/
	function toggleSingleCheckboxState($element, groupSelector) {
		// groupSelector: 예) ".workType2-item", ".salary-item" 등

		// 1) 같은 그룹의 모든 항목을 찾아서 체크 해제 + selected-box 제거
		$(groupSelector).each(function() {
			let codeCd = $(this).data("codecd");
			
			// li 해제
			$(this).removeClass("checked");
			$(this).find("input[type='checkbox']").prop("checked", false);
			// box 제거
			$(`.selected-container .selected-box[data-value='${codeCd}']`).remove();
		});

		// 2) 현재 항목만 체크 활성화
		$element.addClass("checked");
		$element.find("input[type='checkbox']").prop("checked", true);

		// 3) selected-box 생성
		let codeCd = $element.data("codecd");
		let codeNm = $element.data("codenm") || codeCd;
		let parentCd = $element.attr("data-parent");
		let parentNm = $element.attr("data-parentNm");
		let key = $element.attr("data-key");
		
		let boxHtml = `
        <div class="selected-box" data-key="${key}" data-value="${codeCd}" data-parentCd="${parentCd}" data-parentNm="${parentNm}">
            ${parentNm} > ${codeNm}
            <button type="button" class="remove-btn">X</button>
        </div>
    `;
		$(".selected-container").append(boxHtml);
	}
	// 체크박스( li.job3-item, li.gungu-item )를 클릭할 때 "선택 / 해제"를 반전하는 함수
	function toggleCheckboxState($element) {
		let $checkbox = $element.find("input[type='checkbox']");
		const isChecked = $checkbox.prop("checked");
		const newState = !isChecked;

		// 체크박스 반전
		$checkbox.prop("checked", newState);
		// li에 'checked' 클래스 토글
		$element.toggleClass("checked", newState);

		// 체크된 값의 codeCd / codeNm 추출
		let codeCd = $element.data("codecd");
		let codeNm = $element.data("codenm") || codeCd; // codeNm이 없으면 codeCd 사용
		let parentNm = $element.attr("data-parentNm");
		let key = $element.attr("data-key");

		// 만약 체크된 상태(newState === true)이면 => selected-box 생성
		if (newState) {
			// 중복 방지: 이미 추가된 box가 있는지 확인
			if ($(`.selected-container .selected-box[data-value='${codeCd}']`).length === 0) {
				let boxHtml = `
                <div class="selected-box" data-key="${key}" data-value="${codeCd}">
                    ${parentNm} > ${codeNm}
                    <button type="button" class="remove-btn">X</button>
                </div>
            `;
				$(".selected-container").append(boxHtml);
			}
		} else {
			// 해제된 상태 => 해당 box 제거
			$(`.selected-container .selected-box[data-value='${codeCd}']`).remove();
		}
	}

	// selected-box의 X 버튼 클릭 시 => 원본 체크 해제
	$(document).on("click", ".selected-container .remove-btn", function() {
		let $box = $(this).closest(".selected-box");
		let codeCd = $box.data("value");

		// selected-box 제거
		$box.remove();

		// 원본 항목에서 체크 해제 + 'checked' 클래스 제거
		let $li = $(`[data-codecd='${codeCd}']`);
		$li.removeClass("checked");
		$li.find("input[type='checkbox']").prop("checked", false);
	});


	// 검색 조건 초기화
	$(document).on('click', '.reset-btn', function() {
		// 모든 체크박스 및 선택된 항목 초기화
		$("input[type='checkbox']").prop("checked", false); // 모든 체크박스 해제
		$(".items").removeClass("checked");
		$(".selected-container .selected-box").each(function() {
	        let codeCd = $(this).data("value");
	        console.log("Removing: ", codeCd);  // 제거될 요소 출력
	
	        // 요소 제거
	        $(this).remove();
	    });
	});

	// 정렬 순 버튼 (열기/닫기)
	$('#sort-btn').on('click', function(e) {
		e.stopPropagation();
		const isDropdownVisible = $('#sort-dropdown').css('display') === 'block';

		// 모든 드롭다운 닫기
		$('.sel-dropdown').hide();

		// 클릭한 드롭다운 토글
		$('#sort-dropdown').toggle(!isDropdownVisible);

		// 화살표 방향
		$('#sort-btn .arrow').text(isDropdownVisible ? '▼' : '▲');
	});
	// 드롭다운 항목 클릭
	$('#sort-dropdown').on('click', '.sort-item', function() {
		const selectedText = $(this).text();

		$('#sort-btn').html(`${selectedText} <span class="arrow">▼</span>`);

		// 드롭다운 닫기
		$('#sort-dropdown').hide();
	});
	// 문서 클릭 => 드롭다운 닫기
	$(document).on('click', function(e) {
		if (!$(e.target).closest('.sort-box').length) {
			$('#sort-dropdown').hide();
			$('#sort-btn .arrow').text('▼');
		}
	});
	
	let searchMap = {};  // 필터 값을 저장할 객체
    let sortBy = "";  // 정렬 기준 초기화
    let currentPage = 1;  // 페이지 기본값

    // 페이지네이션 클릭 이벤트
    $(document).on('click', '.page-link', function(e) {
		e.preventDefault();
        currentPage = $(this).text()
        sendsearchMap();  // 필터와 페이지를 서버에 전송
    });

    // 정렬 기준 선택 클릭 이벤트
    $(document).on('click', '.sort-item', function() {
        sortBy = $(this).data('value');  // 선택된 정렬 기준
		currentPage = 1;
        sendsearchMap();  // 필터와 정렬 기준을 서버에 전송
    });
    // 정렬 기준 선택 클릭 이벤트
    $(document).on('click', '#search-btn', function() {
		searchMap = {};
		currentPage = 1;
		$('.selected-box').each(function() {
            let key = $(this).data('key');  // data-key 값
            let value = $(this).data('value');  // data-value 값

            // 필터 객체에 key가 없으면 새로 생성, 있으면 value를 추가
            if (!searchMap[key]) {
                searchMap[key] = [];
            }
            searchMap[key].push(value);  // 해당 key에 value 추가
        });
        sendsearchMap();  // 필터와 정렬 기준을 서버에 전송
    });

    // 필터 및 페이지 값 서버로 전송
    function sendsearchMap() {
        // 필터와 페이지 값 추가
		console.log(searchMap);
        // AJAX 요청
        $.ajax({
            url: contextPath + '/employ/list/search',  // 실제 요청할 URL
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
				searchMap : searchMap,
				sortBy : sortBy,
				page : currentPage
			}),  // 필터 값을 JSON으로 전송
            success: function(response) {
                $('#list-area').html(response);  // 기존 내용을 새로 업데이트
            },
            error: function(error) {
                console.error("서버 요청 오류:", error);
            }
        });
	}
	
	// 바로지원 버튼 클릭 이벤트
	let loginCompany = $("#loginCompany").val();
    let loginMember = $("#loginMember").val();
	$(document).on('click', '.submit-btn', function() {
		let employNo = $(this).attr('data-employNo');
    	let filedNo = $(`#fieldSelect${employNo}`).val();
	    if (loginMember !== null && loginMember !== "") {
	        confirmMessage("지원하시겠습니까?", "", function(result) {
	            if (result) {
					$.ajax({
			            url: contextPath + '/company/apply/applyInsert',
			            type: 'POST',
			            data: JSON.stringify({
							filedNo : filedNo
						}),
						contentType: 'application/json; charset=UTF-8',
    					dataType: 'json',
			            success: function(response) {
			                if (response.status === "success") {
			                    alertMessage(response.message, 'success');
								location.reload();
			                } else if(response.status === "fail"){
			                    alertMessage(response.message, 'error');
			                } else{
			                    alertMessage(response.message, 'error');
			                }
			            },
			            error: function() {
			                alertMessage("서버에서 문제가 발생했습니다. 잠시 후 다시 시도해주세요.", "error");
			            }
			        });
	            } else {
	                return false;
	            }
	        });
	    }else if(loginCompany !== null && loginCompany !== ""){
			 alertMessage("일반회원만 지원가능합니다.", "warning");
		}else{
			location.href = `${contextPath}/login`;
		}
	});
});
