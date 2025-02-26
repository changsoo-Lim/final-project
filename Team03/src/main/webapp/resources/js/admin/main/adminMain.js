const baseUrl = document.body.dataset.url;

// 미처리된 신고 갯수
fetch(`${baseUrl}/admin/reportList`)
	.then(response => response.json())
	.then(data => {
		console.log('Report List Response:', data);

		document.getElementById('unprocessedReports').innerText = data[0].REPORTCOUNT;
	})
	.catch(error => {
		console.error('Error fetching report list:', error);
	});

// 미처리된 면접후기승인 갯수
fetch(`${baseUrl}/admin/reviewList`)
	.then(response => response.json())
	.then(data => {
		console.log('Report List Response:', data);

		document.getElementById('unprocessedReviews').innerText = data[0].REVIEWCOUNT;
	})
	.catch(error => {
		console.error('Error fetching report list:', error);
	});
// 미처리된 면접후기승인 갯수
fetch(`${baseUrl}/admin/noticeList`)
	.then(response => response.json())
	.then(data => {
		console.log('Report List Response:', data);

		document.getElementById('unprocessedInquiries').innerText = data[0].NOTICECOUNT;
	})
	.catch(error => {
		console.error('Error fetching report list:', error);
	});

let allUserData = []; // 전체 데이터를 저장할 변수

// 데이터 가져오기 및 초기화
fetch(`${baseUrl}/admin/user`)
	.then(response => response.json())
	.then(data => {
		allUserData = data.sort((a, b) => a.YEARMONTH.localeCompare(b.YEARMONTH)); // 연도-월 정렬
		populateUserYearSelect(allUserData); // 연도 선택 셀렉트 박스 채우기
		renderUserChart(); // 기본 차트 렌더링
	})
	.catch(error => console.error('Error fetching user data:', error));

// 연도 선택 셀렉트 박스 채우기
function populateUserYearSelect(data) {
	const yearSet = new Set();
	data.forEach(row => {
		const year = row.YEARMONTH.split('-')[0]; // 연도만 추출
		yearSet.add(year);
	});

	const yearSelect = document.getElementById('userYearConunt');
	yearSelect.innerHTML = ''; // 기존 옵션 초기화
	yearSet.forEach(year => {
		const option = document.createElement('option');
		option.value = year;
		option.textContent = year;

		// 2025년인 경우 selected로 설정
		if (year === '2025') {
			option.selected = true;
		}

		yearSelect.appendChild(option);
	});

	// 기본 연도 선택
	if (yearSelect.options.length > 0 && !yearSelect.value) {
		yearSelect.value = yearSelect.options[0].value; // 첫 번째 옵션 선택
	}

	// 셀렉트 박스 값 변경 시 차트 다시 렌더링
	yearSelect.addEventListener('change', renderUserChart);
}


// 차트 렌더링
function renderUserChart() {
	const selectedYear = document.getElementById('userYearConunt').value;
	const filteredData = allUserData.filter(row => row.YEARMONTH.startsWith(selectedYear)); // 선택된 연도의 데이터 필터링

	const labels = []; // 월 레이블 (1월, 2월, ...)
	const prevMonthData = []; // 이전 달 가입자 수
	const currentMonthData = []; // 이번 달 가입자 수

	// 이전 달 데이터를 직접 계산
	for (let i = 0; i < filteredData.length; i++) {
		const [, month] = filteredData[i].YEARMONTH.split('-'); // 월만 추출
		labels.push(`${month}월`);

		// 이번 달 가입자 수
		currentMonthData.push(filteredData[i].TOTALUSER);

		// 이전 달 가입자 수 (이전 인덱스의 TOTALUSER 사용)
		if (i === 0) {
			prevMonthData.push(0); // 첫 번째 월은 이전 달 데이터가 없으므로 0
		} else {
			prevMonthData.push(filteredData[i - 1].TOTALUSER);
		}
	}

	// 기존 차트가 있으면 삭제하고 새로 생성
	const usersCtx = document.getElementById('usersChart').getContext('2d');
	if (window.usersChartInstance) {
		window.usersChartInstance.destroy(); // 기존 차트 삭제
	}

	// 새로운 차트 생성
	window.usersChartInstance = new Chart(usersCtx, {
		type: 'line',
		data: {
			labels: labels,
			datasets: [
				{
					label: '이전 달 가입자 수',
					data: prevMonthData,
					borderColor: 'rgba(54, 162, 235, 1)',
					backgroundColor: 'rgba(54, 162, 235, 0.2)',
					fill: true,
					tension: 0.3
				},
				{
					label: '이번 달 가입자 수',
					data: currentMonthData,
					borderColor: 'rgba(255, 99, 132, 1)',
					backgroundColor: 'rgba(255, 99, 132, 0.2)',
					fill: true,
					tension: 0.3
				}
			]
		},
		options: {
			responsive: true,
			plugins: {
				legend: {
					display: true,
					position: 'top'
				}
			},
			scales: {
				y: {
					beginAtZero: true,
					title: {
						display: true,
						text: '가입자 수'
					}
				},
				x: {
					title: {
						display: true,
						text: '월'
					}
				}
			}
		}
	});
}


let allRegionData = []; // 전체 데이터를 저장할 변수

// 데이터 가져오기 및 초기화
fetch(`${baseUrl}/admin/sido`)
	.then(response => response.json())
	.then(data => {
		allRegionData = data.sort((a, b) => a.YEARMONTH.localeCompare(b.YEARMONTH)); // 연도-월 정렬
		populateRegionYearSelect(allRegionData); // 연도 선택 셀렉트 박스 채우기
		renderRegionChart(); // 기본 차트 렌더링
	})
	.catch(error => console.error('Error fetching region data:', error));

// 연도 선택 셀렉트 박스 채우기
function populateRegionYearSelect(data) {
	const yearSet = new Set();
	data.forEach(row => {
		const year = row.YEARMONTH.split('-')[0]; // 연도만 추출
		yearSet.add(year);
	});

	const yearSelect = document.getElementById('regionYearSelect');
	yearSelect.innerHTML = ''; // 기존 옵션 초기화
	yearSet.forEach(year => {
		const option = document.createElement('option');
		option.value = year;
		option.textContent = year;

		// 2025년인 경우 selected로 설정
		if (year === '2025') {
			option.selected = true;
		}

		yearSelect.appendChild(option);
	});

	// 기본 연도 선택
	if (yearSelect.options.length > 0 && !yearSelect.value) {
		yearSelect.value = yearSelect.options[0].value; // 첫 번째 옵션 선택
	}

	// 연도 변경 시 월 셀렉트 박스와 차트를 업데이트
	yearSelect.addEventListener('change', () => {
		populateRegionMonthSelect();
		renderRegionChart();
	});

	// 초기 월 셀렉트 박스 채우기
	populateRegionMonthSelect();
}


// 월 선택 셀렉트 박스 채우기
function populateRegionMonthSelect() {
	const selectedYear = document.getElementById('regionYearSelect').value;
	const monthSet = new Set();

	// 선택된 연도에 해당하는 데이터만 월 추출
	allRegionData
		.filter(row => row.YEARMONTH.startsWith(selectedYear))
		.forEach(row => {
			const month = row.YEARMONTH.split('-')[1]; // 월만 추출
			monthSet.add(month);
		});

	const monthSelect = document.getElementById('regionMonthSelect');
	monthSelect.innerHTML = ''; // 기존 옵션 초기화
	monthSet.forEach(month => {
		const option = document.createElement('option');
		option.value = month;
		option.textContent = `${month}월`;
		monthSelect.appendChild(option);
	});

	// 기본 월 선택
	if (monthSelect.options.length > 0) {
		monthSelect.value = monthSelect.options[0].value; // 첫 번째 옵션 선택
	}

	// 월 변경 시 차트를 다시 렌더링
	monthSelect.addEventListener('change', renderRegionChart);
}

// 차트 렌더링
function renderRegionChart() {
	const selectedYear = document.getElementById('regionYearSelect').value;
	const selectedMonth = document.getElementById('regionMonthSelect').value;

	// 선택된 연도와 월에 해당하는 데이터 필터링
	const filteredData = allRegionData.filter(
		row => row.YEARMONTH === `${selectedYear}-${selectedMonth}`
	);

	const labels = filteredData.map(row => row.REGION); // 지역 레이블
	const employData = filteredData.map(row => row.EMPLOY); // 공고 수 데이터
	const memberData = filteredData.map(row => row.MEMBER); // 지원자 수 데이터

	// 기존 차트가 있으면 삭제하고 새로 생성
	const regionCtx = document.getElementById('regionChart').getContext('2d');
	if (window.regionChartInstance) {
		window.regionChartInstance.destroy(); // 기존 차트 삭제
	}

	// 새로운 차트 생성
	window.regionChartInstance = new Chart(regionCtx, {
		type: 'bar',
		data: {
			labels: labels,
			datasets: [
				{
					label: '공고 수',
					data: employData,
					backgroundColor: 'rgba(54, 162, 235, 0.5)',
					borderColor: 'rgba(54, 162, 235, 1)',
					borderWidth: 1
				},
				{
					label: '지원자 수',
					data: memberData,
					backgroundColor: 'rgba(255, 99, 132, 0.5)',
					borderColor: 'rgba(255, 99, 132, 1)',
					borderWidth: 1
				}
			]
		},
		options: {
			responsive: true,
			plugins: {
				legend: {
					display: true,
					position: 'top'
				}
			},
			scales: {
				y: {
					beginAtZero: true,
					title: {
						display: true,
						text: '수량'
					}
				},
				x: {
					title: {
						display: true,
						text: '지역'
					}
				}
			}
		}
	});
}


// 공통 데이터 관리 객체
const dataHandlers = {
	monthUser: {
		data: [],
		selectId: 'userYearSelect',
		tableId: 'userTable',
		columns: ['TOTALUSER', 'MEMBER', 'COMPANY'],
		cumulativeKeys: ['MEMBER', 'COMPANY'], // 누적 합계가 필요한 컬럼
		apiUrl: `${baseUrl}/admin/monthUser`,
		renderRow: (row, cumulative) => `
            <td>${row.month}월</td>
            <td>${row.TOTALUSER}</td>
            <td>${cumulative.MEMBER}</td>
            <td>${cumulative.COMPANY}</td>
        `
	},
	monthEmploy: {
		data: [],
		selectId: 'employYearSelect',
		tableId: 'employTable',
		columns: ['TOTALEMPLOY', 'INGEMPLOY', 'ENDEMPLOY'],
		cumulativeKeys: [], // 누적 합계가 필요하지 않은 경우
		apiUrl: `${baseUrl}/admin/monthEmploy`,
		renderRow: (row) => `
            <td>${row.month}월</td>
            <td>${row.TOTALEMPLOY}</td>
            <td>${row.INGEMPLOY}</td>
            <td>${row.ENDEMPLOY}</td>
        `
	}
};

// 데이터 가져오기 및 초기화 함수
function initData(handlerKey) {
	const handler = dataHandlers[handlerKey];

	fetch(handler.apiUrl)
		.then(response => response.json())
		.then(data => {
			handler.data = data.sort((a, b) => a.YEARMONTH.localeCompare(b.YEARMONTH)); // 연도-월 정렬
			populateYearSelect(handler, handlerKey); // 연도 옵션 채움 (handlerKey 추가)
			filterAndRenderTable(handlerKey); // 기본 테이블 렌더링
		})
		.catch(error => console.error(`Error fetching ${handlerKey} data:`, error));
}

// 연도 선택 셀렉트 박스에 옵션 추가
function populateYearSelect(handler, handlerKey) {
	const yearSet = new Set();
	handler.data.forEach(row => {
		const year = row.YEARMONTH.split('-')[0]; // 'YYYY-MM'에서 연도 추출
		yearSet.add(year);
	});

	const yearSelect = document.getElementById(handler.selectId);
	yearSelect.innerHTML = ''; // 기존 옵션 초기화
	yearSet.forEach(year => {
		const option = document.createElement('option');
		option.value = year;
		option.textContent = year;

		// 2025년인 경우 selected로 설정
		if (year === '2025') {
			option.selected = true;
		}

		yearSelect.appendChild(option);
	});

	// 셀렉트 박스 값 변경 시 테이블 필터링
	yearSelect.addEventListener('change', () => filterAndRenderTable(handlerKey)); // handlerKey를 사용
}


function filterAndRenderTable(handlerKey) {
	const handler = dataHandlers[handlerKey];
	const selectedYear = document.getElementById(handler.selectId).value;
	const tableBody = document.querySelector(`#${handler.tableId} tbody`);
	tableBody.innerHTML = ''; // 기존 내용 초기화

	// 누적 합계 변수 초기화
	const cumulative = {};
	const totalSum = {}; // 각 컬럼의 합계를 저장할 객체
	handler.columns.forEach(key => totalSum[key] = 0); // 초기화

	handler.cumulativeKeys.forEach(key => cumulative[key] = 0); // 누적 합계 초기화

	let lastCumulative = {}; // 마지막 누적 값을 저장할 객체

	// 선택된 연도의 데이터를 필터링하면서 이전 연도부터 누적 합계 계산
	handler.data.forEach(row => {
		const [year, month] = row.YEARMONTH.split('-');
		row.month = month; // 월만 추출하여 추가

		// 누적 합계 계산 (연도 상관없이 모든 데이터 누적)
		handler.cumulativeKeys.forEach(key => cumulative[key] += row[key]);

		// 선택된 연도의 데이터만 테이블에 출력
		if (year === selectedYear) {
			// 각 컬럼의 합계 계산
			handler.columns.forEach(key => totalSum[key] += row[key]);

			// 마지막 누적 값 업데이트
			lastCumulative = { ...cumulative };

			const tr = document.createElement('tr');
			tr.innerHTML = handler.renderRow(row, cumulative);
			tableBody.appendChild(tr);
		}
	});

	// 합계 행 추가 (MEMBER와 COMPANY는 마지막 누적 값을 사용)
	const totalRow = document.createElement('tr');
	totalRow.innerHTML = `
        <td>합계</td>
        <td>${totalSum.TOTALUSER || totalSum.TOTALEMPLOY}</td>
        <td>${lastCumulative.MEMBER || totalSum.INGEMPLOY}</td>
        <td>${lastCumulative.COMPANY || totalSum.ENDEMPLOY}</td>
    `;
	totalRow.style.fontWeight = 'bold'; // 합계 행 강조
	tableBody.appendChild(totalRow);
}


// 초기화 호출
initData('monthUser'); // 회원 현황 초기화
initData('monthEmploy'); // 채용 공고 현황 초기화