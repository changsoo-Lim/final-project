const baseUrl = document.body.dataset.url;


// 결제 방식 비율 차트
const paymentMethodCtx = document.getElementById('paymentMethodChart').getContext('2d');
fetch(`${baseUrl}/admin/mountAmountList`) // 결제 방식 비율 데이터 가져오기
	.then(response => response.json())
	.then(data => {
		new Chart(paymentMethodCtx, {
			type: 'bar',
			data: {
				labels: data.map(item => item.BUYMETHOD), // 결제 방식 라벨
				datasets: [{
					label: '결제 방식 사용 횟수', // 라벨 수정
					data: data.map(item => item.BUYCOUNT),  // 사용 횟수에 맞게 수정
					backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
					barThickness: 20, // 막대 굵기 고정 (단위: px)
					maxBarThickness: 30, // 막대의 최대 굵기 (단위: px)
				}],
			},
			options: {
				indexAxis: 'y', // 수평 막대 그래프
				scales: {
					x: {
						beginAtZero: true,
						title: {
							display: true,
							text: '사용 횟수', // X축 제목 추가
						}
					},
					y: {
						title: {
							display: true,
							text: '결제 방식' // Y축 제목 추가
						}
					}
				},
				plugins: {
					legend: {
						display: true,
						position: 'top'
					},
					tooltip: {
						callbacks: {
							label: function(tooltipItem) {
								return `사용 횟수: ${tooltipItem.raw}`;
							}
						}
					}
				}
			}
		});
	})
	.catch(error => console.error('Error fetching payment method data:', error));


const monthlyPaymentCtx = document.getElementById('monthlyPaymentChart').getContext('2d');
const yearSelect = document.getElementById('YearSelect');

let chartInstance = null; // Chart 인스턴스를 저장할 변수
let allData = []; // 모든 데이터를 저장할 변수

// 월별 결제 금액 데이터 가져오기
fetch(`${baseUrl}/admin/prodAmountList`)
	.then(response => response.json())
	.then(data => {
		allData = data; // 모든 데이터를 저장

		// Select 요소에 연도 옵션 추가
		const years = [...new Set(data.map(item => item.YEARMONTH.split('-')[0]))]; // 연도만 추출하고 중복 제거
		years.forEach(year => {
			const option = document.createElement('option');
			option.value = year;
			option.textContent = year;
			yearSelect.appendChild(option);
		});

		// 초기 차트 표시 (첫 번째 연도 선택)
		updateChart(years[0]);
	})
	.catch(error => console.error('Error fetching monthly payment data:', error));

// Select 요소의 변경 이벤트 핸들러
yearSelect.addEventListener('change', (event) => {
	const selectedYear = event.target.value;
	updateChart(selectedYear);
});

// 차트를 업데이트하는 함수
function updateChart(selectedYear) {
    const filteredData = allData.filter(item => item.YEARMONTH.startsWith(selectedYear)); // 선택된 연도의 데이터만 필터링

    const labels = filteredData.map(item => {
        const month = item.YEARMONTH.split('-')[1]; // 월만 추출
        return `${parseInt(month)}월`; // '1월', '2월' 형식으로 변환
    });

    const amounts = filteredData.map(item => item.TOTAL_AMOUNT); // 필터링된 월별 결제 금액

    if (chartInstance) {
        chartInstance.destroy(); // 기존 차트를 삭제
    }

    chartInstance = new Chart(monthlyPaymentCtx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '월별 결제 금액',
                data: amounts,
                backgroundColor: '#36A2EB',
                barThickness: 20, // 막대 굵기 고정 (단위: px)
                maxBarThickness: 30, // 막대의 최대 굵기 (단위: px)
            }],
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true, // Y축이 0부터 시작하도록 설정
                },
            },
        },
    });
}



/*// 통계 요약 데이터
fetch(`${baseUrl}/admin/totalList`) // 통계 요약 데이터 가져오기
  .then(response => response.json())
  .then(data => {
	document.querySelector('#totalAmount').textContent = `총 결제 금액: ${data.TOTAL_AMOUNT}원`;
	document.querySelector('#mostPurchasedProduct').textContent = `가장 많이 결제된 상품: ${data.mostPurchasedProduct}`;
	document.querySelector('#mostUsedPaymentMethod').textContent = `가장 많이 사용된 결제 방식: ${data.mostUsedPaymentMethod}`;
  })
  .catch(error => console.error('Error fetching total list data:', error));*/
