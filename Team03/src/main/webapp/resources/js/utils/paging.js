/*
*
*
*/
/*function paging(page){
	console.log(page);
	searchForm.page.value = page;
	searchForm.requestSubmit();
}

document.addEventListener("DOMContentLoaded", ()=>{
	let $searchForm = $("#searchForm");
	let $searchBtn = $(".search-btn");	
	$searchBtn.on("click", function(){
		let $parent = $(this).parents(".search-area"); 
		$parent.find(":input[name]").each(function(index, ipt){
			ipt.name
			ipt.value
			console.log(ipt.name, ipt.value);
			//$searchForm.find(`[name="${ipt.name}"]`).val(ipt.value);
			if(searchForm[ipt.name]){
				searchForm[ipt.name].value=ipt.value;
			}
			//searchForm.requestSubmit();
			$searchForm.submit();
		});
	});
});
*/
/*
*--------------------------------------------위 에는 엔터 안먹히던거------------------------------------------------
*
*/
function paging(page){
	console.log(page);
	searchForm.page.value = page;
	searchForm.requestSubmit();
}

document.addEventListener("DOMContentLoaded", () => {
    let $searchForm = $("#searchForm");
    let $searchBtn = $(".search-btn");
    let $searchArea = $(".search-area"); // 부모 영역 지정

    // 검색 버튼 클릭 시 동작
    $searchBtn.on("click", function () {
        handleSearch();
    });

    // 엔터 키 입력 시 동작
    $searchArea.on("keypress", ":input[name]", function (event) {
        if (event.key === "Enter") {
            event.preventDefault(); // 기본 엔터 동작 막기
            handleSearch();
        }
    });

    // 검색 처리 함수
    function handleSearch() {
        $searchArea.find(":input[name]").each(function (index, ipt) {
            if (searchForm[ipt.name]) {
                searchForm[ipt.name].value = ipt.value;
            }
        });
        console.log("Submitting form...");
        $searchForm.submit(); // 폼 제출
    }
});