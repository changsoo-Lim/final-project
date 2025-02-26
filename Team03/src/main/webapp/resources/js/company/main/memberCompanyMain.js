document.addEventListener("DOMContentLoaded", () => {
	const path = document.body.dataset.url; // base URL

	const clientid = '9Lu8p11qOlDuWlSZpL1JtuhGd6o9wVcr';
	const clientsecret = 'wfCDVDPTa2TFpTAiUwTs7JfFkDbLP6NT';


	// 기업 경영 지표
	const jsonData = {
		"items": {
			"count": "5",
			"item": [
				{
					"aettamt": "85064635000",
					"audit_eng": "Unqualified",
					"audit_kor": "적정",
					"auditor": "한영회계법인",
					"cptlamt_fvl": "1488993000",
					"dbt_ttl_fvl": "24277466000",
					"fds_ttl_fvl": "60787169000",
					"nrf": "7343003000",
					"num": "1",
					"sales_fvl": "78033758000",
					"sales_nrf_rt_frt": "9.41",
					"sales_slsprft_frt": "8.55",
					"slsprft_fvl": "6670971000",
					"stac_date": "20231231",
					"ty_gubun": "0",
					"upchecd": "380954"
				},
				{
					"aettamt": "83412349000",
					"audit_eng": "Unqualified",
					"audit_kor": "적정",
					"auditor": "삼정회계법인",
					"cptlamt_fvl": "1488993000",
					"dbt_ttl_fvl": "27656534000",
					"fds_ttl_fvl": "55755815000",
					"nrf": "3701958000",
					"num": "2",
					"sales_fvl": "65308350000",
					"sales_nrf_rt_frt": "5.67",
					"sales_slsprft_frt": "4.33",
					"slsprft_fvl": "2828569000",
					"stac_date": "20221231",
					"ty_gubun": "0",
					"upchecd": "380954"
				},
				{
					"aettamt": "79758300000",
					"audit_eng": "Unqualified",
					"audit_kor": "적정",
					"auditor": "삼정회계법인",
					"cptlamt_fvl": "1488993000",
					"dbt_ttl_fvl": "27083288000",
					"fds_ttl_fvl": "52675012000",
					"nrf": "645526000",
					"num": "3",
					"sales_fvl": "55605120000",
					"sales_nrf_rt_frt": "1.16",
					"sales_slsprft_frt": "1.19",
					"slsprft_fvl": "661623000",
					"stac_date": "20211231",
					"ty_gubun": "0",
					"upchecd": "380954"
				},
				{
					"aettamt": "78252289000",
					"audit_eng": "Unqualified",
					"audit_kor": "적정",
					"auditor": "삼정회계법인",
					"cptlamt_fvl": "1488993000",
					"dbt_ttl_fvl": "25063780000",
					"fds_ttl_fvl": "53188509000",
					"nrf": "526975000",
					"num": "4",
					"sales_fvl": "50661002000",
					"sales_nrf_rt_frt": "1.04",
					"sales_slsprft_frt": "1.52",
					"slsprft_fvl": "768626000",
					"stac_date": "20201231",
					"ty_gubun": "0",
					"upchecd": "380954"
				},
				{
					"aettamt": "73758825000",
					"audit_eng": "Unqualified",
					"audit_kor": "적정",
					"auditor": "삼정회계법인",
					"cptlamt_fvl": "1488993000",
					"dbt_ttl_fvl": "20238210000",
					"fds_ttl_fvl": "53520615000",
					"nrf": "2767666000",
					"num": "5",
					"sales_fvl": "49155693000",
					"sales_nrf_rt_frt": "5.63",
					"sales_slsprft_frt": "3.21",
					"slsprft_fvl": "1580164000",
					"stac_date": "20191231",
					"ty_gubun": "0",
					"upchecd": "380954"
				}
			]
		}
	};
	const sales = document.getElementById("sales");
	const salesP = document.getElementById("salesP");
	const sales_fvl = jsonData.items.item[0].sales_fvl; // 매출액
	const stac_date = jsonData.items.item[0].stac_date; // 기준 연월일


	// 조/억 단위 변환 함수
	function formatToEok(num) {
		const realAmount = num * 1000; // 천 원 단위를 원 단위로 변환
		const jo = Math.floor(realAmount / 1e12); // 조 단위 계산
		const eok = Math.floor((realAmount % 1e12) / 1e8); // 억 단위 계산

		// 조 단위가 있으면 조와 억 출력
		if (jo > 0) {
			return `${jo.toLocaleString()}조 ${eok.toLocaleString()}억`;
		}
		// 조 단위가 없으면 억만 출력
		return `${eok.toLocaleString()}억`;
	}

	// 매출액 출력
	sales.textContent = formatToEok(sales_fvl);

	salesP.textContent = `${stac_date.substring(0, 4)}년 ${stac_date.substring(4, 6)}월 ${stac_date.substring(6, 8)}일 기준`;

	/*sales.textContent =*/

	// 차트 찍기
	// 데이터 추출 및 정렬
	const items = jsonData.items.item; // 데이터 배열
	const labels = items.map(item => `${item.stac_date.slice(0, 4)}년`).reverse(); // 결산연도 뒤에 "년" 추가 (역순 정렬)
	const salesData = items.map(item => parseInt(item.sales_fvl, 10)).reverse(); // 매출액 데이터 - 역순 정렬

	// 차트 생성
	const ctx = document.getElementById('salesChart').getContext('2d');
	new Chart(ctx, {
		type: 'line', // 선형 차트
		data: {
			labels: labels, // x축: 결산연도 (2019년, 2020년 등)
			datasets: [{
				label: '매출액 (단위: 조)', // 데이터 설명
				data: salesData, // 매출액 데이터
				borderColor: '#8041f6', // 선 색상
				backgroundColor: 'rgba(128, 65, 246, 0.2)', // 투명한 배경색
				fill: true, // 그래프 아래 채우기
				tension: 0.2 // 선의 부드러움
			}]
		},
		options: {
			responsive: true, // 반응형
			plugins: {
				legend: {
					display: true, // 범례 표시
					position: 'top'
				},
				tooltip: {
					callbacks: {
						title: function(context) {
							// 툴팁의 연도에 "년" 추가
							return `${context[0].label}`;
						},
						label: function(context) {
							// 툴팁에서 매출액 값을 조 단위로 변환
							const valueInJo = (context.raw * 1000 / 1e12).toFixed(1); // 조 단위 변환
							return `매출액: ${valueInJo}조`;
						}
					}
				}
			},
			scales: {
				x: {
					title: {
						display: true,
						text: '연도'
					}
				},
				y: {
					beginAtZero: false,
					title: {
						display: true,
					},
					ticks: {
						callback: function(value) {
							// y축 값에 조 단위로 변환
							const valueInJo = (value * 1000 / 1e12).toFixed(1); // 조 단위 변환
							return `${valueInJo}조`;
						}
					}
				}
			}
		}
	});












	// 기업 약식정보
	const response = {
		items: {
			count: "1",
			item: [
				{
					amnisuyn: "N",
					bizno: "1018109147",
					bnk_brnm: "양재남",
					bnknm: "한국외환은행",
					bzdnm: "",
					bzdnm2: "",
					chulja: "0",
					crpno: "1101110085450",
					crprgrnstscd: "00",
					dtlcont: "현대자동차써비스(주)(620041)을 흡수합병함:99.4.1/현대상용엔진(주)(175530)을 흡수합병함:2004.11.5",
					eml: "",
					empnum: "72188",
					empnum_bse_date: "20240630",
					eng_bnknm: "Korea Exchange Bank",
					eng_grpnm: "Hyundai Motor",
					eng_idscdnm: "Manufacture of passenger motor vehicles",
					eng_itemnm: "HyundaiMtr",
					eng_mainpdtpcl: "Commercial Vechicle",
					eng_scl: "Large enterprise",
					engaddr: "12, Heolleung-ro Seocho-gu Seoul",
					engaddr2: "700, Yeompo-ro Buk-gu Ulsan",
					engaddr3: "150, Hyundaiyeonguso-ro Namyang-eup Hwaseong-si Gyeonggi",
					engentrnm: "Hyundai Motor Company",
					engreprnm: "Chung,Eui Sun/Lee,Dong Seok/Jose Munoz",
					epr_cnu_yn: "Y",
					eprdtldivcd: "511",
					eprmdydivcd: "1",
					etbDate: "19671229",
					etl_ipc_yn: "Y",
					faBseDate: "20231231",
					fadivcd: "00",
					fadivnm: "제조",
					fax: "02-3464-3414",
					fax2: "052-280-4111",
					fax3: "031-368-5107",
					fssentrcd: "00164742",
					gicd: "BH1",
					grpnm: "현대자동차",
					homepurl: "www.hyundai.com",
					hupegbn: "일반과세자",
					idscd: "C30121",
					idscdid: "10C3012100",
					idscdid_big: "10C0000000",
					idscdid_detail: "10C3012000",
					idscdid_mdetail: "10C3012100",
					idscdid_medium: "10C3000000",
					idscdid_small: "10C3010000",
					idscdnm_big: "C 제조업(10~34)",
					idscdnm_detail: "자동차 제조업",
					idscdnm_mdetail: "승용차 및 기타 여객용 자동차 제조업",
					idscdnm_medium: "자동차 및 트레일러 제조업",
					idscdnm_small: "자동차용 엔진 및 자동차 제조업",
					kiscode: "380954",
					korIdscdnm: "승용차 및 기타 여객용 자동차 제조업",
					kor_itemnm: "현대차",
					koraddr: "서울 서초구 헌릉로 12",
					koraddr2: "울산 북구 염포로 700",
					koraddr3: "경기 화성시 남양읍 현대연구소로 150",
					korentrnm: "현대자동차(주)",
					korreprcd: "144767/557605/663299",
					korreprnm: "정의선/이동석/호세무뇨스",
					logo: "http://image.kisline.com/logos/busi_b/s_7/thum/th_1018109147.jpg",
					logossl: "https://image.kisline.com/logos/busi_b/s_7/thum/th_1018109147.jpg",
					ltg_date: "19740628",
					ltgmktdivcd: "1",
					mainpdtpcl: "자동차(승용차,버스,트럭,특장차),자동차부품,자동차전착도료 제조,차량정비사업/항공기,부속품 도소매/별정통신,부가통신/부동산 임대",
					mainupche: "현대자동차(주)",
					nolt_engaddr: "231, Yangjae-dong Seocho-gu Seoul",
					nolt_engaddr2: "700, Yangjeong-dong Buk-gu Ulsan",
					nolt_engaddr3: "772-1, Jangdeok-ri Namyang-eup Hwaseong-si Gyeonggi",
					nolt_koraddr: "서울 서초구 양재동 231번지",
					nolt_koraddr2: "울산 북구 양정동 700번지",
					nolt_koraddr3: "경기 화성시 남양읍 장덕리 772-1번지",
					nts_sbqcdivcd: "03",
					obz_date: "19671229",
					opt_entrnm: "현대자동차",
					scl: "대기업",
					sido: "서울",
					stacmm: "12",
					stkcd: "005380",
					tel: "02-3464-1114",
					tel2: "052-215-2114",
					tel3: "031-368-5114",
					upt_dtm: "2024-12-20 21:03:01.0"
				}
			]
		}
	};


	// 국민연금 급여정보
	const result = {
		items: {
			count: "1",
			item: [
				{
					aslifosrc: "NPS",
					avg_aslamt: "74594078",
					bizlo_rgs_date: "19880101",
					bizlo_scsn_date: "00010101",
					bse_yymm: "202411",
					fer_gjob_tnpnum: "7834",
					fer_gjobrt: "11.38",
					fer_rtm_tnpnum: "7207",
					fer_rtmrt: "10.47",
					lst_aslifosrc: "IFRS산출",
					lst_avg_aslamt: "117818591",
					lst_new_emp_avg_aslamt: "76582084",
					mm_clby_gjob_tnpnum: "944",
					mmly_rtm_tnpnum: "984",
					new_emp_avg_aslamt: "48486151",
					sbrnum: "69671",
					tmnnfcamt: "36772789260",
					upchecd: "380954",
					upt_dtm: "Dec 27 2024  1:39PM"
				}
			]
		}
	};
	// 차트 데이터


	// 2. API 데이터 추출 
	// 반환 데이터 리스트 변수로 지정
	const item = result.items.item[0];

	// 데이터 추출
	const ferGjobrt = parseFloat(item.fer_gjobrt); // 입사율
	const ferRtmrt = parseFloat(item.fer_rtmrt); // 퇴직율
	const avgAslamt = parseInt(item.avg_aslamt); // 평균연봉
	const lstAvgAslamt = parseInt(item.lst_avg_aslamt); // 최종평균연봉
	const lstNewEmpAvgAslamt = parseInt(item.lst_new_emp_avg_aslamt); // 최종신규사원평균연봉
	const mmClbyGjobTnpnum = parseInt(item.mm_clby_gjob_tnpnum); // 월별취업인원수
	const mmlyRtmTnpnum = parseInt(item.mmly_rtm_tnpnum); // 월별퇴직인원수
	const sbrnum = parseInt(item.sbrnum); // 가입자수
	const tmnnfcamt = parseInt(item.tmnnfcamt); // 당월고지금액



	// 차트 2: 평균 연봉 비교
	const ctx2 = document.getElementById('chart2').getContext('2d');
	new Chart(ctx2, {
		type: 'bar',
		data: {
			labels: ['평균연봉', '신입사원평균연봉'],
			datasets: [{
				label: '연봉 (만원)',
				data: [
					Math.floor(lstAvgAslamt / 10000),
					Math.floor(lstNewEmpAvgAslamt / 10000)
				],
				backgroundColor: [
					'rgba(54, 162, 235, 0.2)',
					'rgba(255, 206, 86, 0.2)'
				],
				borderColor: [
					'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)'
				],
				borderWidth: 1
			}]
		},
		options: {
			responsive: true,
			scales: {
				y: {
					beginAtZero: true,
					ticks: {
						callback: function(value) {
							return value.toLocaleString() + ' 만원'; // y축 값에 "만원" 표시
						}
					}
				}
			}
		}
	});





	// 차트 함수






	//   연혁  
	const history = {
		"items": {
			"count": "144",
			"item": [
				{
					"contents": "'2024 코나' 출시",
					"date": "20240000"
				},
				{
					"contents": "'더 뉴 아이오닉5' 출시",
					"date": "20240000"
				},
				{
					"contents": "'2024 엑시언트 프로' 출시",
					"date": "20240000"
				},
				{
					"contents": "'ST1 카고 및 카고 냉동' 출시",
					"date": "20240000"
				},
				{
					"contents": "제네시스, '제네시스 G90 블랙' 출시",
					"date": "20240000"
				},
				{
					"contents": "제네시스, 'GV70 부분변경 모델' 출시",
					"date": "20240000"
				},
				{
					"contents": "'2024 넥쏘' 출시",
					"date": "20230000"
				},
				{
					"contents": "'2023 마이티' 출시",
					"date": "20230000"
				},
				{
					"contents": "'2023 캐스퍼' 출시",
					"date": "20230000"
				},
				{
					"contents": "'더 뉴 아반떼' 출시",
					"date": "20230000"
				},
				{
					"contents": "'2024 팰리세이드' 출시",
					"date": "20230000"
				},
				{
					"contents": "제네시스, '2023 G90' 출시",
					"date": "20230000"
				},
				{
					"contents": "'유니버스 수소전기버스' 출시",
					"date": "20230000"
				},
				{
					"contents": "'디 올 뉴 코나 일렉트릭' 공개",
					"date": "20230000"
				},
				{
					"contents": "'디 올 뉴 코나 일렉트릭' 출시",
					"date": "20230000"
				},
				{
					"contents": "'디 올 뉴 코나' 세계 최초 공개",
					"date": "20230000"
				},
				{
					"contents": "제네시스, '2023 G70·G70슈팅 브레이크' 출시",
					"date": "20230000"
				},
				{
					"contents": "'2022 코나' 출시",
					"date": "20220000"
				},
				{
					"contents": "'2023 투싼' 출시",
					"date": "20220000"
				},
				{
					"contents": "'캐스퍼 밴' 출시",
					"date": "20220000"
				},
				{
					"contents": "'2022 그랜저' 출시",
					"date": "20220000"
				},
				{
					"contents": "'2023 싼타페' 출시",
					"date": "20220000"
				},
				{
					"contents": "'2023 스타리아' 출시",
					"date": "20220000"
				},
				{
					"contents": "'2023 아이오닉 5' 출시",
					"date": "20220000"
				},
				{
					"contents": "'중장기 전동화 전략' 공개",
					"date": "20220000"
				},
				{
					"contents": "'2023 쏘나타 센슈어스' 출시",
					"date": "20220000"
				},
				{
					"contents": "'아이오닉 6' 세계 최초 공개",
					"date": "20220000"
				},
				{
					"contents": "CES 2022에서 로보틱스 비전 발표",
					"date": "20220000"
				},
				{
					"contents": "제네시스, 'G70 슈팅 브레이크' 출시",
					"date": "20220000"
				},
				{
					"contents": "스타리아 라운지 리무진 및 캠퍼 모델 출시",
					"date": "20220000"
				},
				{
					"contents": "대표이사 하언태 사임",
					"date": "20211231"
				},
				{
					"contents": "대표이사 장재훈 선임",
					"date": "20210324"
				},
				{
					"contents": "스타리아 출시",
					"date": "20210000"
				},
				{
					"contents": "2021 그랜저 출시",
					"date": "20210000"
				},
				{
					"contents": "'2022 싼타페' 출시",
					"date": "20210000"
				},
				{
					"contents": "아이오닉 5 세계 최초 공개",
					"date": "20210000"
				},
				{
					"contents": "엔트리 SUV  '캐스퍼' 출시",
					"date": "20210000"
				},
				{
					"contents": "제네시스, '2022 GV80' 출시",
					"date": "20210000"
				},
				{
					"contents": "제네시스, 'G80 전동화 모델' 출시",
					"date": "20210000"
				},
				{
					"contents": "21년형 엑시언트 수소전기트럭 출시",
					"date": "20210000"
				}
			]
		}
	}


	// 국민연금 검색 시 필요 파라미터 , 기업 약식정보에서 가져오기 전역변수로 설정후 fetch 보낸 값으로 다시 
	// 국민역금 파라미터로 사용 해야함 
	const kiscode = response.items.item[0].kiscode;
	// Url 설정	
	const homeUrl = document.getElementById("homeUrl");
	const url = response.items.item[0].homepurl;
	// href 속성 설정
	homeUrl.href = `http://${url}`;
	// 링크 텍스트 설정
	homeUrl.innerText = url;

	// 업력 설정
	const sysdate = new Date().getFullYear();
	const eDate = response.items.item[0].etbDate;
	const etbDate = document.getElementById("etbDate");
	const bDate = document.getElementById("bDate");
	const companyAge = sysdate - parseInt(eDate.substring(0, 4), 10);
	etbDate.textContent = companyAge + " 년차";
	bDate.textContent = `${eDate.substring(0, 4)}년 ${eDate.substring(4, 6)}월 ${eDate.substring(6, 8)}일 설립`;


	const empnum = response.items.item[0].empnum;
	const empno = document.getElementById("empno");
	empno.textContent = Number(empnum).toLocaleString() + " 명";

	const empnoP = document.getElementById("empnoP");
	const empDate = response.items.item[0].empnum_bse_date;
	empnoP.textContent = `${empDate.substring(0, 4)}년 ${empDate.substring(4, 6)}월 ${empDate.substring(6, 8)}일 기준`;




	// 연혁 찍어주기
	// history 데이터에서 필요한 부분 가져오기
	const timelineData = history.items.item;

	// <ul> 요소와 "더보기" 버튼 선택
	const historyTimeline = document.getElementById("historyTimeline");
	const toggleButton = document.getElementById("toggleButton");

	// 초기 상태
	const initialDisplayCount = 10;
	let isExpanded = false;

	// 데이터 렌더링 함수
	const renderTimeline = (start, end) => {
		historyTimeline.innerHTML = ""; // 기존 내용 삭제
		timelineData.slice(start, end).forEach((entry) => {
			const listItem = document.createElement("li");
			const formattedDate = entry.date.slice(0, 4) + "년";
			listItem.textContent = `${formattedDate}: ${entry.contents}`;
			historyTimeline.appendChild(listItem);
		});
	};

	// 초기 10개 표시
	renderTimeline(0, initialDisplayCount);

	// 버튼 클릭 이벤트
	toggleButton.addEventListener("click", () => {
		if (isExpanded) {
			// 줄어든 상태로 변경
			renderTimeline(0, initialDisplayCount);
			toggleButton.textContent = "더보기";
			isExpanded = false;
		} else {
			// 전체 데이터 표시
			renderTimeline(0, timelineData.length);
			toggleButton.textContent = "줄이기";
			isExpanded = true;
		}
	});







	const introText = document.getElementById("introText");
	const toggleIntroButton = document.getElementById("toggleIntro");

	if (!introText || !toggleIntroButton) {
		console.error("introText 또는 toggleIntroButton 요소를 찾을 수 없습니다.");
		return;
	}

	// 데이터 읽기 및 줄바꿈 처리
	const fullText = introText.dataset.fullText.replace(/\n/g, '<br>').trim(); // 줄바꿈을 <br>로 변환

	const maxLines = 10;

	// <br> 기준으로 텍스트 분리
	const lines = fullText.split('<br>');

	const shortText = lines.length > maxLines
		? lines.slice(0, maxLines).join('<br>') + "..."
		: fullText;


	// 초기 설정
	introText.innerHTML = shortText; // HTML로 렌더링
	let isIntroExpandedState = false; // 고유 상태 변수 사용

	// 더보기 버튼 이벤트
	toggleIntroButton.addEventListener("click", () => {

		if (!isIntroExpandedState) {
			introText.innerHTML = fullText; // 전체 텍스트 표시
			toggleIntroButton.textContent = "줄이기";
		} else {
			introText.innerHTML = shortText; // 축약 텍스트 표시
			toggleIntroButton.textContent = "더보기";
		}

		// DOM 강제 업데이트
		introText.style.display = 'none';
		requestAnimationFrame(() => {
			introText.style.display = 'block';
		});

		isIntroExpandedState = !isIntroExpandedState; // 상태 변경
	});




	// api 키값

	const options = {
		method: 'GET',
		headers: {
			accept: 'application/json'
			, 'client-id': clientid
			, 'client-secret': clientsecret

		},

	};
	/* 기업 정보 약식 조회 api
	fetch(`https://api.nicebizline.com/nice/sb/v1/api/emEprOtlIfo/companyShortOutline?bizno=${bizno}`, options)
		.then(response => response.json())
		.then(response => console.log(response){
			
			
			
		})
		.catch(err => console.error(err));
	*/
	/*
		fetch('https://api.nicebizline.com/nice/sb/v1/api/erpPltIfo/itgAslIfo', options)
			.then(response => response.json())
			.then(response => console.log(response))
			.catch(err => console.error(err));*/



	// modelData.result.bizr_no 사업자 등록번호
	// modelData.result.corp_name 기업명
	// modelData.result.jurir_no 법인 등록 번호

	console.log(companyData);
	const resultAPI = companyData.result.find(item => item.bizr_no === String(bizno));

	const servicekey = "kF5hdEWcAGAEzdiCWoTIsTLek3yGlqBKi1vqAlP2M86rVR%2FKkBPPO%2BXeTA6wrE7ddQC%2BE%2BSUAi9BCEqtbQ6iiw%3D%3D";
	// 공공 데이터 서비스키
	if (resultAPI) {
		console.log("검색 성공:", resultAPI.corp_name);
		let jurir_no = resultAPI.jurir_no // 법인 등록번호 


		let api = `https://apis.data.go.kr/1160100/service/GetCorpBasicInfoService_V2/getCorpOutline_V2?ServiceKey=${servicekey}&pageNo=1&numOfRows=1&resultType=json&crno=${jurir_no}`;

		$.ajax({
			url: api, // API URL
			method: "GET",
			dataType: "json",
			success: function(response) {
				//console.log(response.response.body.items.item[0]);
				const empeAvgCnwkTermCtt = response.response.body.items.item[0].empeAvgCnwkTermCtt; // 평균 근속년수
				const AvgSlry = document.getElementById("AvgSlry");
				AvgSlry.textContent = empeAvgCnwkTermCtt + "년"

			},
			error: function(xhr, status, error) {
				console.error("Error: ", error);
			}
		});


		/*			
		금융 위원회 api	
		getCorpOutline_V2_response{
		header	{...}
		body	{
				description:	
				body
		
				numOfRows	[...]
				pageNo	[...]
				totalCount	[...]
				items	{
						description:	
						items
		
				item	{
						description:	
						item
		
					actnAudpnNm	string
					회계 감사를 실시한 감사인의 명칭
					
					audtRptOpnnCtt	string
					회계감사에 대한 감사인의 의견
					
					enpMainBizNm	string
					기업이 영위하고 있는 주요 사업의 명칭
					
					enpKrxLstgAbolDt	string
					기업의 KONEX(자본시장을 통한 초기 중소기업 지원을 강화하여 창조경제 생태계 기반을 조성하기 위해 개설된 중소기업전용 주식시장) 상장 폐지 일자
					
					smenpYn	string
					해당 기업이 중소기업인지를 관리하는 여부
					
					enpMntrBnkNm	string
					기업의 주거래 은행 명칭
					
					enpEmpeCnt	string
					기업의 종업원 인원수
					
					empeAvgCnwkTermCtt	string
					기업의 종업원의 평균 근속 년수
					
					enpPn1AvgSlryAmt	string
					기업의 1인 평균 급여 금액
					
					fstOpegDt	string
					최초개방일자
					
					lastOpegDt	string
					최종개방일자
					
					crno	string
					법인등록번호
					
					corpNm	string
					법인(法人)의 명칭
					
					corpEnsnNm	string
					법인(法人)의 영문 표기 명
					
					enpPbanCmpyNm	string
					기업 공시 회사의 이름
					
					enpRprFnm	string
					기업 대표자의 이름
					
					corpRegMrktDcd	string
					법인이 어느 시장에 등록되었는지를 관리하는 코드
					
					corpRegMrktDcdNm	string
					법인이 어느 시장에 등록되었는지를 관리하는 코드의 명칭
					
					corpDcd	string
					법인등록번호(5,6 자리)내 법인종류별분류번호(5,6 자리)
					
					corpDcdNm	string
					법인구분코드명
					
					bzno	string
					세무에서, 신규로 개업하는 사업자에게 부여하는 사업체의 고유번호
					
					enpOzpno	string
					기업의 소재지 구우편번호 (6자리)
					
					enpBsadr	string
					기업의 소재지로 우편번호에 대응되는 기본주소
					
					enpDtadr	string
					기업의 소재지로 우편번호에 대응되는 기본주소외의 상세주소
					
					enpHmpgUrl	string
					기업의 홈페이지 주소
					
					enpTlno	string
					기업의 전화번호
					
					enpFxno	string
					기업의 팩스 번호
					
					sicNm	string
					산업 주체들이 모든 산업활동을 그 성질에 따라 유형화한 분류 이름
					
					enpEstbDt	string
					기업의 설립일자
					
					enpStacMm	string
					기업의 결산 월
					
					enpXchgLstgDt	string
					기업의 거래소 상장 일자
					
					enpXchgLstgAbolDt	string
					기업의 거래소 상장 폐지 일자
					
					enpKosdaqLstgDt	string
					기업의 주식이 코스닥 시장에 상장 등록된 일자
					
					enpKosdaqLstgAbolDt	string
					기업의 주식이 코스닥 시장에 상장 페지된 일자
					
					enpKrxLstgDt	string
					기업의 KONEX(자본시장을 통한 초기 중소기업 지원을 강화하여 창조경제 생태계 기반을 조성하기 위해 개설된 중소기업전용 주식시장) 상장 일자
					
					fssCorpUnqNo	string
					금융감독원에서 관리하는 법인의 고유번호
					
					fssCorpChgDtm	string
					금융감독원에서 관리하는 법인 정보의 변경일시
					
		*/

	} else {
		console.log("일치하는 데이터가 없습니다.");
	}

	// 시군구 자르는 함수 

	function splitAddress(address) {
		if (!address || typeof address !== "string") {
			return { address1: "잘못된 입력", address2: "" };
		}

		// 주소를 공백으로 나눔
		const parts = address.trim().split(/\s+/);

		// 광역시 목록
		const 광역시_목록 = [
			"서울특별시", "부산광역시", "대구광역시", "인천광역시",
			"광주광역시", "대전광역시", "울산광역시", "세종특별자치시",
			"제주특별자치도"
		];

		let address1 = "";
		let address2 = "";

		if (광역시_목록.includes(parts[0])) {
			// 광역시인 경우
			if (parts[2] && (parts[2].endsWith("동") || parts[2].endsWith("리") || parts[2].endsWith("읍") || parts[2].endsWith("면") || parts[2].endsWith("구"))) {
				address1 = parts.slice(0, 3).join(" "); // 시/도 + 시/군/구 + 동/리/읍/면
				address2 = parts.slice(3).join(" ");   // 나머지
			} else {
				address1 = parts.slice(0, 2).join(" "); // 시/도 + 시/군/구
				address2 = parts.slice(2).join(" ");   // 나머지
			}
		} else if (parts[0].endsWith("도")) {
			// 도의 경우
			if (parts[2] && (parts[2].endsWith("동") || parts[2].endsWith("리") || parts[2].endsWith("읍") || parts[2].endsWith("면") || parts[2].endsWith("구"))) {
				address1 = parts.slice(0, 3).join(" "); // 도 + 시/군/구 + 동/리/읍/면
				address2 = parts.slice(3).join(" ");   // 나머지
			} else {
				address1 = parts.slice(0, 2).join(" "); // 도 + 시/군/구
				address2 = parts.slice(2).join(" ");   // 나머지
			}
		} else {
			// 형식에 맞지 않는 경우 기본적으로 2개로 나눔
			address1 = parts.slice(0, 2).join(" "); // 기본 시/도 + 시/군/구
			address2 = parts.slice(2).join(" ");   // 나머지
		}

		return { address1, address2 };
	}



	const reviewCards = document.querySelectorAll('.review-card');
	// review 클릭 이벤트
	reviewCards.forEach(card => {
		card.addEventListener('click', () => {
			const reviewId = card.getAttribute('data-review-id');
			// 서버로 요청을 보내는 fetch 예제
			window.location.href = `${path}/review/${reviewId}`
		});
	});
	// 별 표시 함수
	function renderStars(container, rating, maxStars = 5) {
		container.innerHTML = ""; // 기존 내용 초기화
		for (let i = 1; i <= maxStars; i++) {
			const star = document.createElement("span");
			star.classList.add("star");
			if (i <= rating) {
				star.classList.add("yellow"); // 노란 별
			} else {
				star.classList.add("gray"); // 회색 별
			}
			star.textContent = "★";
			container.appendChild(star);
		}
	}

	// 모든 별 컨테이너 업데이트
	document.querySelectorAll(".stars-container").forEach(container => {
		const rating = parseFloat(container.getAttribute("data-rating"));
		renderStars(container, rating);
	});

	// 모든 공고 카드를 선택
	document.querySelectorAll('.position-card').forEach(card => {
		card.addEventListener('click', function() {
			const empNo = this.getAttribute('data-empNo');
			if (empNo) {
				console.log(empno)
				window.location.href = `${path}/employ/detail/${empNo}`; // 해당 URL로 이동
			}
		});
	});
}); 