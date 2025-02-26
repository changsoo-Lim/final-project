document.addEventListener('DOMContentLoaded', () => {
	const dateInputs = document.querySelectorAll('[id^="dateInput_"]');

	const today = new Date(); // 현재 날짜 
	const yyyy = today.getFullYear();
	const mm = String(today.getMonth() + 1).padStart(2, '0'); // 월(1~12)
	const dd = String(today.getDate()).padStart(2, '0');      // 일(01~31)
	const startDate = `${yyyy}-${mm}-${dd}`; // yyyy-mm-dd

	dateInputs.forEach(input => {
		input.value = startDate;
	});
});


let selectedCard = null;

function showImage(card, imageUrl, productName) {

	if (selectedCard === card) {
		return;
	}
	const baseUrl = document.body.dataset.url;
	const imageElement = document.getElementById('product-image');
	imageElement.src = `${baseUrl}${imageUrl}`;
	imageElement.alt = productName + " 이미지";

	if (selectedCard) {
		selectedCard.classList.remove('selected');
	}

	selectedCard = card;
	selectedCard.classList.add('selected');
}


const badgeData = {
	0: [
		{ class: "bg-danger text-light", label: "타겟팅효과🎯" }
	],
	1: [
		{ class: "bg-warning text-dark", label: "최대효과⭐" }
	],
	2: [
		{ class: "bg-primary", label: "재구매율🔥" }
	],
	3: [
		{ class: "bg-dark text-light", label: "최다 판매🏅" }
	]
};

// 배지 추가 함수
Object.keys(badgeData).forEach(index => {
	const spanDiv = document.getElementById(`spanDiv_${index}`);
	if (spanDiv) {
		badgeData[index].forEach(badge => {
			const span = document.createElement("span");
			span.className = `badge ${badge.class}`;
			span.textContent = badge.label;
			spanDiv.appendChild(span);
		});
	}
});

document.querySelectorAll(".days-input").forEach(input => {
	input.addEventListener("input", function() {
		const days = parseInt(this.value, 10);
		const index = this.dataset.index;
		const priceInput = document.getElementById(`price_${index}`);

		if (priceInput) {
			const pricePerDay = parseInt(priceInput.value, 10);
			const totalPrice = days * pricePerDay;


			const priceDisplay = document.getElementById(`productPrice_${index}`);
			if (priceDisplay) {
				priceDisplay.textContent = totalPrice.toLocaleString() + " ₩";
			}
		}
	});
});

// 상품몸에 있는 값이 모달창폼에 띄우기
document.querySelectorAll(".modalBtn").forEach((button, index) => {
	button.addEventListener("click", () => {

		const employNo = document.querySelector(`#employNo`).value; // 구매 일수
		console.log(employNo);
		
		const productCd = document.querySelector(`#productCd_${index}`).value; // 구매 일수

		const productName = document.querySelectorAll("#productNm")[index].textContent.trim(); // 상품명

		const buyQty = document.querySelector(`#daysInput_${index}`).value; // 구매 일수

		const buySdt = document.querySelector(`#dateInput_${index}`).value; // 시작 날짜

		const productPrice = document.querySelector(`#price_${index}`).value; // 가격

		const startDate = new Date(buySdt);
		startDate.setDate(startDate.getDate() + parseInt(buyQty, 10) - 1);
		const formattedEndDate = startDate.toISOString().split("T")[0];

		const buyAmount = (parseInt(productPrice, 10) * parseInt(buyQty, 10));

		document.querySelector(".employNo").value = employNo;
		document.getElementById("productCd").value = productCd;
		document.getElementById("prodNm").value = productName;
		document.getElementById("buyQty").value = buyQty;
		document.getElementById("buySdt").value = buySdt.replace(/-/g, "");
		document.getElementById("buyEdt").value = formattedEndDate.replace(/-/g, "");
		document.getElementById("buyAmount").value = `${buyAmount}`.replace(/,/g, "");
	});
});
