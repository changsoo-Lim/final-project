/**
 * 
 */
$(()=>{
	document.getElementById("resetBtn").addEventListener("click", function (e) {
	    e.preventDefault(); // 기본 동작 방지
	
	    // 폼 초기화
	    const form = document.getElementById("searchForm");
	    form.reset();
	
	    // 필드 값 강제로 초기화
	    form.querySelectorAll("input, select").forEach((field) => {
	        field.value = ""; // 모든 필드 값을 빈 문자열로 초기화
	    });
	
	    // URL의 쿼리스트링 제거
	    const url = new URL(window.location.href);
	    url.search = ""; // 쿼리스트링 초기화
	    history.replaceState(null, "", url); // URL 업데이트
	});
});

