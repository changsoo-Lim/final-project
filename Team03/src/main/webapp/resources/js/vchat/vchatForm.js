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
            $(".vchat-card").show(); // employNo가 비어 있으면 모든 카드 보여주기
            return;
        }

        $(".vchat-card").each(function () {
            const cardEmployNo = $(this).find("#employNo").val();
            const cardFieldNo = $(this).data("field");

            if (cardEmployNo == employNo) {
                if (!fieldNo || cardFieldNo == fieldNo) {
                    $(this).show(); // employNo가 맞고 fieldNo도 비어있거나 맞으면 보여줌
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

            if (!selectedEmployNo) {
                // employNo가 비어 있으면 모든 옵션과 방 보여주기
                filterOptions($fieldSelect, null);
                filterVChatCards(null, null);
                return;
            }

            filterOptions($fieldSelect, filterClass);
            $hiddenInput.val(selectedEmployNo || ""); // hidden input 값 업데이트
            filterVChatCards(selectedEmployNo, null); // employNo 기준으로 먼저 필터링
        });

        $fieldSelect.on("change", (event) => {
            const selectedFieldNo = event.target.value;
            const selectedEmployNo = $employSelect.val();

            if (!selectedFieldNo) {
                // fieldNo가 비어 있으면 employNo에 맞는 모든 카드 보여주기
                filterVChatCards(selectedEmployNo, null);
                return;
            }

            filterVChatCards(selectedEmployNo, selectedFieldNo); // employNo와 fieldNo 모두 필터링
        });

        // 초기 로드 시 상세 옵션 숨기기 및 모든 방 표시
        if ($employSelect.val() === "") {
            filterOptions($fieldSelect, null);
            filterVChatCards(null, null); // 초기 로드시 모든 방 표시
        }
    }

    const $employ = $("#employ");
    const $filedSelList = $("#filedSelList");
    const $selectedEmpCode = $("#selectedEmpCode");

    setupFilterEvent($employ, $filedSelList, $selectedEmpCode);
});

const baseUrl = document.body.dataset.url;
// 버튼 클릭 이벤트 바인딩
document.querySelectorAll("#vchatBtn").forEach(function(vchatBtn) {
    vchatBtn.addEventListener("click", function(e) {
        e.preventDefault();
        
        // 랜덤한 고유 값 생성 (UUID 방식)
        function generateUUID() {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                let r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        }

        // 입력값 가져오기
        let apiUserId = generateUUID();  // 랜덤 UUID 할당
        let username = this.parentElement.querySelector("#username").value;
        let roomId = this.parentElement.querySelector("#roomId").value;
        let fieldNo = this.parentElement.querySelector("#fieldNo").value;
        let vchatNo = this.parentElement.querySelector("#vchatNo").value;
        
        // API 호출 옵션 설정
        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json",
            },
            body: JSON.stringify({
                roleId: "emcee",
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
                    // 화면 크기 계산
                    let screenWidth = window.screen.availWidth;  // 실제 사용 가능한 화면 너비
                    let screenHeight = window.screen.availHeight;  // 실제 사용 가능한 화면 높이

                    let borderSize = 30;  // 좌우 테두리 합계

                    let leftWidth = Math.floor(screenWidth * 0.75) - borderSize;
                    let leftHeight = screenHeight;
                    window.open(res2.data.url, "_blank", `width=${leftWidth},height=${leftHeight},top=0,left=0,resizable=yes,scrollbars=yes`);

                    let rightWidth = screenWidth - leftWidth - borderSize;
                    let rightHeight = screenHeight;
                    window.open(`${baseUrl}/evalCate/new/${fieldNo}/${vchatNo}`, "_blank", `width=${rightWidth},height=${rightHeight},top=0,left=${leftWidth + borderSize},resizable=yes,scrollbars=yes`);

                }
            })
            .catch((err) => {
                console.error("오류 발생:", err);
                alert("오류가 발생했습니다. 다시 시도하세요.");
            });
    });
});



document.querySelectorAll("#vchatDelBtn").forEach(button => {
    button.addEventListener("click", function(e) {
        e.preventDefault();

        // 버튼의 상위 요소 중에서 vchatNo 값을 선택
        const vchatNo = this.closest(".row").querySelector("#vchatNo").value;
        const baseUrl = document.body.dataset.url;
        console.log(vchatNo);
        Swal.fire({
            title: '삭제 확인',
            text: '정말 삭제하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: '삭제',
            cancelButtonText: '취소',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                fetch(`${baseUrl}/vchat/${vchatNo}`, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ vchatNo: vchatNo }),
                })
                    .then(response => {
                        if (response.ok) {
                            Swal.fire({
                                title: '삭제 완료',
                                text: '삭제되었습니다.',
                                icon: 'success',
                                confirmButtonText: '확인'
                            }).then(() => {
                                window.location.href = ""; // 성공 시 리다이렉트
                            });
                        } else {
                            Swal.fire({
                                title: '삭제 실패',
                                text: '삭제에 실패했습니다. 다시 시도해주세요.',
                                icon: 'error',
                                confirmButtonText: '확인'
                            });
                        }
                    })
                    .catch(error => {
                        console.error("삭제 중 오류 발생:", error);
                        Swal.fire({
                            title: '오류 발생',
                            text: '오류가 발생했습니다. 관리자에게 문의하세요.',
                            icon: 'error',
                            confirmButtonText: '확인'
                        });
                    });
            }
        });

    });
});

$(document).ready(function() {
	$('#filedSelList').on('change', function() {
		var selectedFieldNo = $(this).val();

		// 선택된 값이 없거나 빈 문자열일 경우 모든 카드 표시
		if (!selectedFieldNo) {
			$('.vchat-card').show();
		} else {
			// 선택된 값에 해당하는 카드만 표시
			$('.vchat-card').hide();
			$('.vchat-card[data-field="' + selectedFieldNo + '"]').show();
		}
	});
});
