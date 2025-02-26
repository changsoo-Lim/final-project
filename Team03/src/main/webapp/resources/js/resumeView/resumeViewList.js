/**
 * 
 */

function paging(page) {
	console.log(page);
	searchListForm.page.value = page;
	searchListForm.requestSubmit();
}


document.addEventListener("DOMContentLoaded", () => {
	const memCards = document.querySelectorAll(".card-body.p-4");
	const path = document.body.dataset.url; // base URL
	// 클릭 이벤트 
	memCards.forEach(card => {
		card.addEventListener("click", () => {
			const memId = card.getAttribute("data-mem-id"); // data-mem-id 값 가져오기
			window.location.href = `${path}/talent/${memId}/detail`;
		});
	});
});