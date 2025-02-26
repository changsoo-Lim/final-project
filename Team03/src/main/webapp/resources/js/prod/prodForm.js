document.addEventListener('DOMContentLoaded', () => {
	// 문자 입력시 경고창
	const productPrice = document.getElementById('productPrice');

	productPrice.addEventListener('input', (event) => {
		const value = event.target.value;

		if (/[^0-9]/.test(value)) {
			Swal.fire({
				title: "입력 오류",
				text: "숫자만 입력 가능합니다.",
				icon: "error",
				confirmButtonText: "확인",
			});

			const prodValue = value.replace(/[^0-9]/g, '');
			productPrice.value = prodValue;
		} else {
			productPrice.value = value;
		}
	});
});

// 이미지 미리보기
function previewImage(event) {
	const imagePreview = document.getElementById('imagePreview');
	const file = event.target.files[0];

	if (!file) {
		imagePreview.innerHTML = '<span>이미지 미리보기</span>';
		return;
	}

	if (!file.type.startsWith('image/')) {
		alert('이미지 파일만 업로드할 수 있습니다.');
		imagePreview.innerHTML = '<span>이미지 미리보기</span>';
		return;
	}

	const reader = new FileReader();

	reader.onload = function(e) {
		imagePreview.innerHTML = `<img src="${e.target.result}" alt="미리보기 이미지">`;
	};


	reader.readAsDataURL(file);
}

const addBtn = document.querySelector("#addBtn");
const editBtn = document.querySelector("#editBtn");
// 테이블 tr 선택시 입력폼으로 값 올라가기
document.querySelectorAll("#prodTalble tbody tr").forEach(row => {
	row.addEventListener("click", function() {
		const productCd = this.dataset.productCd;
		const baseUrl = document.body.dataset.url;
		
		// 서버에 요청 보내기
		fetch(`${baseUrl}/admin/products/${productCd}/detail`)
			.then(response => {
				if (response.ok) {
					return response.json();
				}
				throw new Error("서버 응답 오류");
			})
			.then(data => {
				// 받은 데이터로 폼 채우기
				document.querySelector("input[name='productCd']").value = data.productCd;
				document.querySelector("input[name='productNm']").value = data.productNm;
				document.querySelector("input[name='productPeriod']").value = data.productPeriod;
				document.querySelector("input[name='productPrice']").value = data.productPrice;
				document.querySelector("select[name='productType']").value = data.productType;
				document.querySelector("input[name='atchFileNo']").value = data.atchFileNo;
				console.log(data)

				// 이미지 미리보기 업데이트
				const imagePreview = document.querySelector("#imagePreview");
				if (imagePreview) {
					// fileDetails 배열의 첫 번째 요소 접근
					const fileDetail = data.file?.fileDetails?.[0];
					console.log(fileDetail)
					if (fileDetail) {
						const imageUrl = fileDetail.fileStreCours.replace(/\\/g, '/');
						imagePreview.innerHTML = `
							<img id="product-image" src="${imageUrl}" 
							     alt="${data.productNm} 이미지" style="max-width: 100%; height: auto;">
						`;
						console.log(imageUrl)
					} else {
						imagePreview.innerHTML = `<p></p>`;
					}
				}
				
				addBtn.style.display = "none";
				editBtn.style.display = "block";

				console.log("폼 데이터가 채워졌습니다:", data);
			})
			.catch(error => {
				console.error("데이터를 가져오는 중 오류 발생:", error);
				alert("데이터를 불러오는 데 실패했습니다. 다시 시도해주세요.");
			});
	});
});


editBtn.addEventListener("click", function (e) {
	e.preventDefault();

	const prodField = document.querySelector("#prodField");
	const productCd = prodField.querySelector("#productCd").value;
	const baseUrl = document.body.dataset.url;

	// FormData 객체 생성
	const formData = new FormData();

	// 텍스트 데이터 추가
	formData.append("productCd", prodField.querySelector("#productCd").value);
	formData.append("productNm", prodField.querySelector("#productNm").value);
	formData.append("productPeriod", prodField.querySelector("#productPeriod").value);
	formData.append("productPrice", prodField.querySelector("#productPrice").value);
	formData.append("productType", prodField.querySelector("#productType").value);
	formData.append("atchFileNo", prodField.querySelector("#atchFileNo").value);

	// 파일 데이터 추가
	const uploadFiles = prodField.querySelector("#uploadFiles").files;
	if (uploadFiles.length > 0) {
		for (let i = 0; i < uploadFiles.length; i++) {
			formData.append("uploadFiles", uploadFiles[i]);
		}
	}

	// fetch 요청
	fetch(`${baseUrl}/admin/products/${productCd}/edit`, {
		method: "PUT",
		body: formData,
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
		})
		.then((data) => {
			if (data.status === "success") {
				location.reload();
			}
		})
		.catch((error) => {
			console.error("Fetch Error: ", error);
			alert("요청 처리 중 오류가 발생했습니다.");
		});
});



// 삭제
document.querySelectorAll("#delBtn").forEach((button, index) => {
	button.addEventListener("click", function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();
		const productCd = document.querySelectorAll(".productCd")[index].value;
		const baseUrl = document.body.dataset.url;

		const isConfirmed = confirm("정말 삭제 하시겠습니까?");
		if (isConfirmed) {
			fetch(`${baseUrl}/admin/products/${productCd}/delete`, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ productCd: productCd }),
			})
				.then((response) => {
					if (response.ok) {
						location.reload();
					}
				})
				.catch((error) => {
					console.error("삭제 중 오류 발생:", error);
					alert("오류가 발생했습니다. 관리자에게 문의하세요.");
				});
		}
	});

});
