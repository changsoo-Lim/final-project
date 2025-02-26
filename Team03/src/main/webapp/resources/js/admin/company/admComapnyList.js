function paging(page){
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
			console.log(ipt.name, ipt.value);
			// $searchForm.find(`[name="${ipt.name}"]`).val(ipt.value);
			if(searchForm[ipt.name]){
				searchForm[ipt.name].value=ipt.value;
			}	
			// searchForm.requestSubmit();
			$searchForm.submit(); 	
		});
	});
});
/*
	function resetSelection(elements) {
		elements.forEach(element => element.classList.remove('selected'));
	}

	// 최상위 필터 클릭 이벤트: 이벤트 위임
	document.querySelector('.filter-container').addEventListener('click', function(e) {
		if (e.target.classList.contains('top-filter')) {
			const selectedCode = e.target.getAttribute('data-code');
			resetSelection(document.querySelectorAll('.top-filter'));
			e.target.classList.add('selected');

			// 2차 필터 표시
			secondList.classList.remove('hidden');
			resetSelection(document.querySelectorAll('.second-filter'));
			document.querySelectorAll('.second-filter').forEach(filter => {
				const parentCode = filter.getAttribute('data-parent');
				filter.classList.toggle('hidden', parentCode !== selectedCode);
			});

			// 3차 필터 초기화
			thirdList.classList.add('hidden');
			resetSelection(document.querySelectorAll('.third-filter'));
		}
	});

	// 2차 필터 클릭 이벤트: 이벤트 위임
	secondList.addEventListener('click', function(e) {
		if (e.target.classList.contains('second-filter')) {
			const selectedCode = e.target.getAttribute('data-code');
			resetSelection(document.querySelectorAll('.second-filter'));
			e.target.classList.add('selected');

			// 3차 필터 표시
			thirdList.classList.remove('hidden');
			resetSelection(document.querySelectorAll('.third-filter'));
			document.querySelectorAll('.third-filter').forEach(filter => {
				const parentCode = filter.getAttribute('data-parent');
				filter.classList.toggle('hidden', parentCode !== selectedCode);
			});
		}
	});

	// 3차 필터 단일 선택 이벤트: 이벤트 위임
	thirdList.addEventListener('click', function(e) {
		if (e.target.classList.contains('third-filter')) {
			resetSelection(document.querySelectorAll('.third-filter'));
			e.target.classList.add('selected');
		}
	});




	// 필터 선택 처리
	function handleFilterSelection(container, filterClass, resetClass) {
		container.addEventListener("click", function(e) {
			if (e.target.classList.contains(filterClass)) {
				// 기존 선택 초기화
				document.querySelectorAll(`.${resetClass}`).forEach(el => el.classList.remove("selected"));
				// 선택 추가
				e.target.classList.add("selected");
			}
		});
	}

	// 1차 필터
	handleFilterSelection(document.querySelector(".filter-container"), "top-filter", "top-filter");

	// 2차 필터
	secondList.addEventListener("click", function(e) {
		if (e.target.classList.contains("second-filter")) {
			// 기존 선택 초기화
			document.querySelectorAll(".second-filter").forEach(el => el.classList.remove("selected"));
			e.target.classList.add("selected");
		}
	});

	// 3차 필터
	thirdList.addEventListener("click", function(e) {
		if (e.target.classList.contains("third-filter")) {
			document.querySelectorAll(".third-filter").forEach(el => el.classList.remove("selected"));
			e.target.classList.add("selected");
		}
	});*/

	// 검색 버튼 클릭 시


