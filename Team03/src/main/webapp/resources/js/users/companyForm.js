/**
 * 
 */
// select 박스 초기화
function clearSelectBox(selectBox, text) {
  selectBox.innerHTML = `<option value="">${text} 선택</option>`;
}

// select 박스 데이터 추가
function insertSelectBox(selectBox, options) {
  options.forEach(option => {
    const opt = document.createElement('option');
    opt.value = option.codeCd;
    opt.textContent = option.codeNm;
    selectBox.appendChild(opt);
  });
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

document.addEventListener("DOMContentLoaded", async ()=>{
	const baseUrl       = document.body.dataset.url;
	const compInd       = document.getElementById('signupCompIndInput');
	const compJob       = document.getElementById('signupCompJobInput');
	const compJobDetail = document.getElementById('signupCompJobDetailInput');
	
	let data;
	await axios.get(`${baseUrl}/industryCodeList`)
	.then(function (response){
		if(response.status == 200){
			data = response.data;
		}
	}).catch(error => {
	    if (error.response) {
	        // 서버가 응답했지만 상태 코드는 2xx가 아님
	        console.error('Status:', error.response.status);
	    } else if (error.request) {
	        // 요청이 전송되었지만 응답이 없음
	        console.error('Error request:', error.request);
	    }
	});
	
	insertSelectBox(compInd, data.filter(item => item.codeParent === 'industry'));
	
	compInd.addEventListener('change', function () {
	  const selectedValue = compInd.value;
	  clearSelectBox(compJob, '직무');
	  clearSelectBox(compJobDetail, '직무상세');
	
	  if (selectedValue) {
	    const compJobOptions = data.filter(item => item.codeParent === selectedValue);
	    insertSelectBox(compJob, compJobOptions);
	  }
	});
	
	compJob.addEventListener('change', function () {
	  const selectedValue = compJob.value;
	  clearSelectBox(compJobDetail, '직무상세');
	
	  if (selectedValue) {
	    const compJobDetailOptions = data.filter(item => item.codeParent === selectedValue);
	    insertSelectBox(compJobDetail, compJobDetailOptions);
	  }
	});
	
	// 사업자 등록번호
	const num = document.getElementById("signupCompNumInput");
	// 등록번호 찾기 버튼
	const compNumCheck = document.getElementById("compNumCheck");
	// 사업자 정보 xml 값
	//const companyJson = document.getElementById("companyJson");
	console.log(companyJson);


	// 데이터 jsp에 찍기
	/*companyJson.result.forEach(item => {
		const { adres, corp_name, bizr_no, jurir_no } = item;

		// 테이블 본문 가져오기
		const tableBody = document.querySelector("#outputTable tbody");

		// 새로운 행 생성
		const row = document.createElement("tr");

		// 각 열 데이터를 채우기
		row.innerHTML = `
      <td>${adres}</td>
      <td>${corp_name}</td>
      <td>${bizr_no}</td>
      <td>${jurir_no}</td>
    `;

		// 테이블에 행 추가
		tableBody.appendChild(row);
	});*/




	compNumCheck.addEventListener("click", async () => {
		console.log(1);
		// companyJson.result.bizr_no 사업자 등록번호
		// companyJson.result.corp_name 기업명
		// companyJson.result.jurir_no 법인 등록 번호
		
		const result = companyJson.result.find(item => item.bizr_no === num.value);

		// 공공 데이터 서비스키
		const servicekey = "kF5hdEWcAGAEzdiCWoTIsTLek3yGlqBKi1vqAlP2M86rVR%2FKkBPPO%2BXeTA6wrE7ddQC%2BE%2BSUAi9BCEqtbQ6iiw%3D%3D";
		$('#signupCompNmInput').val('');
		$('#signupCompRepInput').val('');
		$('#signupCompTypeInput').val('');
		$('#signupCompIndInput').val('');
		$('#signupCompJobInput').val('');
		$('#signupCompJobDetailInput').val('');
		$('#signupCompTelInput').val('');
		if (result) {
			console.log("검색 성공:", result.corp_name);
			let jurir_no = result.jurir_no // 법인 등록번호 


			let api = `https://apis.data.go.kr/1160100/service/GetCorpBasicInfoService_V2/getCorpOutline_V2?ServiceKey=${servicekey}&pageNo=1&numOfRows=1&resultType=json&crno=${jurir_no}`;

			let response = await fetch(api)

			if (response.ok) {
				let jsonData = await response.json();
				let data = await jsonData.response.body.items.item[0];

				console.log("우편 번호 : ", data.enpOzpno, "주소 : ", data.enpBsadr, "상세 주소 : ", data.enpDtadr);
				console.log(data);

				const { address1, address2 } = splitAddress(data.enpBsadr);
				console.log("주소1 : ", address1, ", 주소 2 :", address2)
				
				console.log("-----------------------------");
				console.log("회사명 : ", data.corpNm );
				console.log("대표자명 : ", data.enpRprFnm );
				console.log("기업구분 : ",  );
				console.log("직종 : ",  );
				console.log("직무 : ",  );
				console.log("직무상세 : ",  );
				console.log("우편번호 : ", data.enpOzpno );
				console.log("기본주소 : ", data.enpBsadr );
				console.log("상세주소 : ", data.enpDtadr );
				console.log("회사번호 : ", data.enpTlno );
				console.log("-----------------------------");
				
				$('#signupCompNmInput').val(data.corpNm);
				$('#signupCompRepInput').val(data.enpRprFnm);
				$('#signupZipCodeInput').val(data.enpOzpno);
				$('#signupAddr1Input').val(data.enpBsadr);
				$('#signupAddr2Input').val(data.enpDtadr);
				$('#signupCompTelInput').val(data.enpTlno.replace(/-/g, ""));
				
			}



			/*			getCorpOutline_V2_response{
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
			sweatAlert("warning", "일치하는 사업자가 없습니다.");
		}
	});
	
});