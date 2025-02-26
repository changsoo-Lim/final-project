document.querySelectorAll('.star-rating').forEach(function(element) {
	var rating = parseInt(element.getAttribute('data-rating')); // 평점 값 가져오기
	var maxStars = 5; // 최대 별 개수
	var starsHtml = '';

	// 별 생성 (⭐과 ☆)
	for (var i = 1; i <= maxStars; i++) {
		if (i <= rating) {
			starsHtml += '⭐'; // 노란색 별
		} else {
			starsHtml += '☆'; // 회색 별
		}
	}

	element.innerHTML = starsHtml; // 별 표시
});