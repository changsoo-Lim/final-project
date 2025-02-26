document.addEventListener("DOMContentLoaded", function () {
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

    function filterVChatCards(employNo, fieldNo) {
        if (!employNo) {
            $(".vchat-card").show(); // employNo가 없으면 모든 카드 보여주기
            return;
        }

        $(".vchat-card").each(function () {
            const cardEmployNo = $(this).find("#employNo").val();
            const cardFieldNo = $(this).data("field");

            if (cardEmployNo == employNo) {
                if (!fieldNo || cardFieldNo == fieldNo) {
                    $(this).show(); // employNo가 맞고 fieldNo도 맞으면 보여줌
                } else {
                    $(this).hide(); // fieldNo가 맞지 않으면 숨김
                }
            } else {
                $(this).hide(); // employNo가 맞지 않으면 숨김
            }
        });
    }

    function setupFilterEvent($employSelect, $fieldSelect, $hiddenInput) {
        $employSelect.on("change", (event) => {
            const selectedEmployNo = event.target.value;
            const selectedOption = $employSelect.find("option:selected");
            const filterClass = selectedEmployNo ? selectedOption.data("code") : null;

            filterOptions($fieldSelect, filterClass);
            $hiddenInput.val(selectedEmployNo || ""); // hidden input 값 업데이트
            filterVChatCards(selectedEmployNo, null); // employNo 기준으로 필터링
        });

        $fieldSelect.on("change", (event) => {
            const selectedFieldNo = event.target.value;
            const selectedEmployNo = $employSelect.val();

            filterVChatCards(selectedEmployNo, selectedFieldNo); // employNo와 fieldNo 모두 필터링
        });

        // 초기 로드 시 상세 옵션 숨기기
        if ($employSelect.val() === "") {
            filterOptions($fieldSelect, null);
            filterVChatCards(null, null); // employNo가 비어있을 때 모든 방 표시
        }
    }

    // 채용 공고와 모집 분야 필터링 설정
    const $empSelect = $("#emp");
    const $filedNoSelect = $("#filedNo");
    const $hiddenInput = $("#selectedEmployCode");

    const $employ = $("#employ");
    const $filedSelList = $("#filedSelList");
    const $selectedEmpCode = $("#selectedEmpCode");

    setupFilterEvent($empSelect, $filedNoSelect, $hiddenInput);
    setupFilterEvent($employ, $filedSelList, $selectedEmpCode);
});

const baseUrl = document.body.dataset.url;
// 버튼 클릭 이벤트 바인딩
document.querySelectorAll("#vchatBtn").forEach(function(vchatBtn) {
	vchatBtn.addEventListener("click", function(e) {
		e.preventDefault();

		// 입력값 가져오기
		let apiUserId = this.parentElement.querySelector("#apiUserId").value;
		let username = this.parentElement.querySelector("#username").value;
		let roomId = this.parentElement.querySelector("#roomId").value;


		// API 호출 옵션 설정
		const options = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				Accept: "application/json",
			},
			body: JSON.stringify({
				roleId: "participant",
				apiUserId: apiUserId,
				ignorePasswd: false,
				roomId: roomId,
				username: username,
			}),
		};

		// 프록시 서버로 API 호출
		fetch(`${baseUrl}/vchat/url`, options)
			.then((res) => {
				if (!res.ok) {
					throw new Error(`HTTP 오류! 상태 코드: ${res.status}`);
				}
				return res.json();
			})
			.then((res) => {
				let res2 = JSON.parse(res);
				console.log(res2);
				if (res2.resultCode === 'GRM_200') {
					// URL이 정상적으로 생성되었으면 새 창으로 열기
					window.open(res2.data.url, "_blank");
				}
			})
			.catch((err) => {
				console.error("오류 발생:", err);
				alert("오류가 발생했습니다. 다시 시도하세요.");
			});
	});
});

$(document).ready(function() {
	$('#filedSelList').on('change', function() {
		var selectedFieldNo = $(this).val();

		if (selectedFieldNo === "") {
			$('.vchat-card').show();
		} else {
			$('.vchat-card').hide();
			$('.vchat-card[data-field="' + selectedFieldNo + '"]').show();
		}
	});
});