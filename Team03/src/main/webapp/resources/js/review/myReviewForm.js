// 별점 선택 스크립트
document.addEventListener('DOMContentLoaded', function() {
	const stars = document.querySelector('.stars');
	const ratingValue = document.getElementById('rating-value');
	const reviewRatingInput = document.getElementById('reviewRating');

	// 초기 상태
	let currentRating = 0;

	// 마우스 오버 이벤트
	stars.addEventListener('mousemove', function(e) {
		const rect = stars.getBoundingClientRect();
		const starWidth = rect.width / 5;
		const hoverRating = Math.ceil((e.clientX - rect.left) / starWidth);
		stars.innerText = '⭐⭐⭐⭐⭐'.slice(0, hoverRating) + '☆☆☆☆☆'.slice(hoverRating);
	});

	// 마우스 클릭 이벤트 (별점 확정)
	stars.addEventListener('click', function(e) {
		const rect = stars.getBoundingClientRect();
		const starWidth = rect.width / 5;
		currentRating = Math.ceil((e.clientX - rect.left) / starWidth);
		stars.innerText = '⭐⭐⭐⭐⭐'.slice(0, currentRating) + '☆☆☆☆☆'.slice(currentRating);
		ratingValue.innerText = `(${currentRating}점)`;
		reviewRatingInput.value = currentRating;
	});

	// 마우스 아웃 이벤트 (기존 평점 유지)
	stars.addEventListener('mouseleave', function() {
		stars.innerText = '⭐⭐⭐⭐⭐'.slice(0, currentRating) + '☆☆☆☆☆'.slice(currentRating);
	});
});

function updateCompanyName() {
	const selectElement = document.getElementById('compId');
	const reviewTitle = document.getElementById('reviewTitle');
	const selectedOption = selectElement.options[selectElement.selectedIndex].text;

	if (selectElement.value) {
		reviewTitle.value = `[${selectedOption}]`;
	} else {
		reviewTitle.value = ''; // 선택 안 했을 때 빈 값으로 설정
	}
}