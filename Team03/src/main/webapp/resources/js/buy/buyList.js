document.addEventListener("DOMContentLoaded", function() {
	const today = new Date();
	const todayStr = today.toISOString().slice(0, 10).replace(/-/g, ""); // YYYYMMDD 형식으로 변환

	document.querySelectorAll(".buy-end-date").forEach(function(element) {
		const buyEndDate = element.dataset.date;

		if (buyEndDate < todayStr) {
			const cardFooter = element.closest(".card").querySelector(".view-employ-btn");
			cardFooter.classList.add("disabled"); // 비활성화 스타일 추가
			cardFooter.setAttribute("aria-disabled", "true");
			cardFooter.setAttribute("href", "javascript:void(0);"); // 링크 비활성화
			cardFooter.innerText = "기간 만료됨"; // 버튼 텍스트 변경
		}
	});
	
	const sortSelect = document.getElementById("sortSelect");
	const buyListContainer = document.getElementById("buyListContainer");

	// 정렬 기준 선택 이벤트
	sortSelect.addEventListener("change", function() {
		const selectedSort = sortSelect.value; // 선택된 정렬 기준
		const cards = Array.from(buyListContainer.getElementsByClassName("col"));

		// 정렬 로직
		cards.sort((a, b) => {
			const aValue = a.querySelector(".card").dataset[selectedSort];
			const bValue = b.querySelector(".card").dataset[selectedSort];

			// 날짜 비교 (내림차순: 최신순)
			return bValue.localeCompare(aValue);
		});

		// 정렬된 카드들을 컨테이너에 다시 추가
		buyListContainer.innerHTML = "";
		cards.forEach(card => buyListContainer.appendChild(card));
	});
});
