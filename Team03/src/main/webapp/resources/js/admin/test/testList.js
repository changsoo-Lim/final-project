/**
 * 
 */
$(()=>{
	const baseUri = document.body.dataset.url;
	$(".tab-button").on("click", function() {
		const testCd = $(this).attr("data-testCd");
	    const baseUrl = window.location.pathname;  // 현재 URL의 경로
        
        // type 값을 쿼리 파라미터로 URL에 추가
        const queryString = `?testCd=${encodeURIComponent(testCd)}`;  // 쿼리 파라미터 생성
        
        // location.href를 사용하여 리다이렉트
        location.href = baseUrl + queryString;
	});		
	
	document.getElementById("deleteSelected").addEventListener("click", async function (e) {
	    e.preventDefault();
	
	    // 체크된 항목들의 값을 배열로 수집
	    const testNoList = Array.from(document.querySelectorAll("input[name='testNo']:checked"))
	        .map(checkbox => parseInt(checkbox.value, 10)); // 체크박스 값들을 정수로 변환
		console.log(testNoList);
	    if (testNoList.length === 0) {
	        await Swal.fire({
	            title: "선택된 항목이 없습니다.",
	            icon: "warning",
	        });
	        return;
	    }
	
	    // 삭제 여부 확인
	    const deleteResult = await Swal.fire({
	        title: "선택된 항목을 삭제하시겠습니까?",
	        text: "삭제 시 복구는 불가합니다.",
	        icon: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#d33",
	        cancelButtonColor: "#3085d6",
	        confirmButtonText: "삭제",
	        cancelButtonText: "취소",
	    });
	
	    if (deleteResult.isConfirmed) {
	        try {
	            // Axios DELETE 요청
	            const response = await axios.delete(`${baseUri}/admin/test/deleteMultiple`, {
	                data: testNoList, // 선택된 값 배열 전송
	                headers: {
	                    "Content-Type": "application/json", // JSON 데이터 전송
	                },
	            });
	
	            if (response.status === 200 && response.data.success) {
	                await Swal.fire({
	                    title: "삭제되었습니다.",
	                    icon: "success",
	                });
	
	                // 삭제 후 페이지 새로고침 또는 특정 UI 업데이트
	                window.location.reload(); // 새로고침
	            } else {
	                await Swal.fire({
	                    title: "삭제 실패",
	                    text: response.data.message || "문제가 발생했습니다.",
	                    icon: "error",
	                });
	            }
	        } catch (error) {
	            console.error("Error deleting items:", error);
	            await Swal.fire({
	                title: "삭제 실패",
	                text: "서버와의 통신 중 문제가 발생했습니다.",
	                icon: "error",
	            });
	        }
	    }
	});
})