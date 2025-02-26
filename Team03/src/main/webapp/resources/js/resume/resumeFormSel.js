function formatDate(dateStr) {
	if (!dateStr) {
        return '';
    }
	if (dateStr.length === 8) {
		const year = dateStr.substring(0, 4);
		const month = dateStr.substring(4, 6);
		const day = dateStr.substring(6, 8);
		return `${year}-${month}-${day}`;
	}
	return dateStr; // 변환이 필요 없는 경우 원본 반환
} // 날짜 포맷팅

// 대학교
document.querySelector("#uniTable tbody").addEventListener("click", function(e) {
	// 삭제 버튼 클릭
	if (e.target.classList.contains("uniDelBtn")) {
		e.stopPropagation(); // 이벤트 전파 방지
		e.preventDefault();

		// 클릭된 버튼의 행에서 데이터 가져오기
		const row = e.target.closest("tr");
		const uniNo = row.querySelector(".uniNo").value;
		const baseUrl = document.body.dataset.url;

		const isConfirmed = confirm("정말 삭제 하시겠습니까?");
		if (isConfirmed) {
			fetch(`${baseUrl}/uni/${uniNo}`, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ uniNo: uniNo }),
			})
				.then(response => {
					if (response.ok) {
						row.remove(); // 테이블에서 해당 행 삭제
						clearForm();
					} else {
						alert("삭제에 실패했습니다. 다시 시도해주세요.");
					}
				})
				.catch(error => {
					console.error("삭제 중 오류 발생:", error);
				});
		}
		return; // 아래 tr 클릭 이벤트를 방지
	}

	// tr 클릭 이벤트 처리 (삭제 버튼 클릭이 아닌 경우)
	const row = e.target.closest("tr");
	if (row) {
		const uniNo = row.dataset.uniNo;
		const baseUrl = document.body.dataset.url;

		fetch(`${baseUrl}/uni/${uniNo}`)
			.then(response => {
				if (response.ok) {
					return response.json();
				} else {
					throw new Error("데이터 로드 실패");
				}
			})
			.then(data => {
				document.querySelector("input[name='uniNo']").value = data.uniNo;
				document.querySelector("input[name='uniNm']").value = data.uniNm;
				document.querySelector("select[name='uniType']").value = data.uniType;
				document.querySelector("input[name='uniSdt']").value = formatDate(data.uniSdt);
				document.querySelector("select[name='uniSStatus']").value = data.uniSStatus;
				document.querySelector("input[name='uniEdt']").value = formatDate(data.uniEdt);
				document.querySelector("select[name='uniEStatus']").value = data.uniEStatus;
				document.querySelector("select[name='uniMajorCategory']").value = data.uniMajorCategory;
				document.querySelector("input[name='uniMajorNm']").value = data.uniMajorNm;
				document.querySelector("select[name='uniMajorDegree']").value = data.uniMajorDegree;
				document.querySelector("input[name='uniGpa']").value = data.uniGpa;
				document.querySelector("input[name='uniProjectNm']").value = data.uniProjectNm;
				document.querySelector("textarea[name='uniProjectDesc']").value = data.uniProjectDesc;
				console.log("폼 데이터가 채워졌습니다:", data);
			})
			.catch(error => {
				console.error("데이터를 가져오는 중 오류 발생:", error);
				alert("데이터를 불러오는 데 실패했습니다. 다시 시도해주세요.");
			});
	}
});


// 경력사항
document.querySelector("#careerTable tbody").addEventListener("click", function(e) {
	const baseUrl = document.body.dataset.url;

	// 삭제 버튼 클릭 처리
	if (e.target.classList.contains("careerDelBtn")) {
		e.stopPropagation();
		e.preventDefault();

		// 클릭된 행에서 데이터 가져오기
		const row = e.target.closest("tr");
		const careerNo = row.querySelector(".careerNo").value;

		const isConfirmed = confirm("정말 삭제 하시겠습니까?");
		if (isConfirmed) {
			fetch(`${baseUrl}/career/${careerNo}`, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ careerNo }),
			})
				.then(response => {
					if (response.ok) {
						row.remove(); // 성공 시 행 삭제

					} else {
						throw new Error("삭제 요청 실패");
					}
				})
				.catch(error => {
					console.error("삭제 중 오류 발생:", error);
				});
		}
		return;
	}

	// 행 클릭 처리 (삭제 버튼이 아닌 경우)
	const row = e.target.closest("tr");
	if (row) {
		const careerNo = row.dataset.careerNo;

		fetch(`${baseUrl}/career/${careerNo}`)
			.then(response => {
				if (!response.ok) {
					throw new Error("데이터 로드 실패");
				}
				return response.json();
			})
			.then(data => {
				// 폼 채우기
				document.querySelector("input[name='careerNo']").value = data.careerNo;
				document.querySelector("input[name='careerCompanyNm']").value = data.careerCompanyNm;
				document.querySelector("select[name='careerIndustryType']").value = data.careerIndustryType;
				document.querySelector("select[name='careerSubIndustry']").value = data.careerSubIndustry;
				document.querySelector("select[name='careerCompanySize']").value = data.careerCompanySize;
				document.querySelector("select[name='careerCompanyType']").value = data.careerCompanyType;
				document.querySelector("select[name='careerListed']").value = data.careerListed;
				document.querySelector("select[name='careerCity']").value = data.careerCity;
				document.querySelector("select[name='careerDistrict']").value = data.careerDistrict;
				document.querySelector("input[name='careerStartDate']").value = formatDate(data.careerStartDate);

				if (data.careerEndDate === null) {
					document.querySelector("#ingBox").checked = true; // 체크박스 체크
					toggleEmploymentStatus();
					
				} else {
					document.querySelector("input[name='careerEndDate']").value = formatDate(data.careerEndDate);
					document.querySelector("#ingBox").checked = false; // 체크박스 해제
					toggleEmploymentStatus();
				}

				document.querySelector("input[name='careerTentre']").checked = data.careerTentre === "Y";
				document.querySelector("input[name='careerJobTitle']").value = data.careerJobTitle;
				document.querySelector("textarea[name='careerJobDesc']").value = data.careerJobDesc;
				document.querySelector("input[name='careerPosition']").value = data.careerPosition;
				document.querySelector("input[name='careerDepartment']").value = data.careerDepartment;
				document.querySelector("select[name='careerType']").value = data.careerType;
				document.querySelector("input[name='careerSalary']").value = parseInt(data.careerSalary, 10).toLocaleString("en-US");
				document.querySelector("select[name='careerReason']").value = data.careerReason;
				calculateWorkDuration();
				console.log("폼 데이터가 채워졌습니다:", data);
			})
			.catch(error => {
				console.error("데이터를 가져오는 중 오류 발생:", error);
			});
	}
});

// 자격증
document.querySelector("#certTable tbody").addEventListener("click", function(e) {
	const baseUrl = document.body.dataset.url;

	// 삭제 버튼 클릭 처리
	if (e.target.classList.contains("certDelBtn")) {
		e.stopPropagation();
		e.preventDefault();

		// 클릭된 행에서 데이터 가져오기
		const row = e.target.closest("tr");
		const certNo = row.querySelector(".certNo").value;

		const isConfirmed = confirm("정말 삭제 하시겠습니까?");
		if (isConfirmed) {
			fetch(`${baseUrl}/cert/${certNo}`, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ certNo }),
			})
				.then(response => {
					if (response.ok) {
						row.remove(); // 성공 시 행 삭제
					} else {
						throw new Error("삭제 요청 실패");
					}
				})
				.catch(error => {
					console.error("삭제 중 오류 발생:", error);
				});
		}
		return;
	}

	// 행 클릭 처리 (삭제 버튼이 아닌 경우)
	const row = e.target.closest("tr");
	if (row) {
		const certNo = row.dataset.certNo;

		fetch(`${baseUrl}/cert/${certNo}`)
			.then(response => {
				if (!response.ok) {
					throw new Error("데이터 로드 실패");
				}
				return response.json();
			})
			.then(data => {
				// 폼 채우기
				document.querySelector("input[name='certNo']").value = data.certNo;
				document.querySelector("input[name='certCode']").value = data.certCode;
				document.querySelector("input[name='certNm']").value = data.certNm;
				document.querySelector("input[name='certIssuer']").value = data.certIssuer;
				document.querySelector("input[name='certDate']").value = formatDate(data.certDate);
				document.querySelector("select[name='certPassCd']").value = data.certPassCd;

				console.log("폼 데이터가 채워졌습니다:", data);
			})
			.catch(error => {
				console.error("데이터를 가져오는 중 오류 발생:", error);
			});
	}
});

document.querySelector("#compTable tbody").addEventListener("click", function(e) {
	const baseUrl = document.body.dataset.url;

	// 삭제 버튼 클릭 처리
	if (e.target.classList.contains("compDelBtn")) {
		e.stopPropagation();
		e.preventDefault();

		// 클릭된 행에서 데이터 가져오기
		const row = e.target.closest("tr");
		const compNo = row.querySelector(".compNo").value;

		const isConfirmed = confirm("정말 삭제 하시겠습니까?");
		if (isConfirmed) {
			fetch(`${baseUrl}/comp/${compNo}`, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ compNo }),
			})
				.then(response => {
					if (response.ok) {
						row.remove(); // 성공 시 행 삭제
					} else {
						throw new Error("삭제 요청 실패");
					}
				})
				.catch(error => {
					console.error("삭제 중 오류 발생:", error);
				});
		}
		return;
	}

	// 행 클릭 처리 (삭제 버튼이 아닌 경우)
	const row = e.target.closest("tr");
	if (row) {
		const compNo = row.dataset.compNo;

		fetch(`${baseUrl}/comp/${compNo}`)
			.then(response => {
				if (!response.ok) {
					throw new Error("데이터 로드 실패");
				}
				return response.json();
			})
			.then(data => {
				// 폼 채우기
				document.querySelector("input[name='compSkillNo']").value = data.compSkillNo;
				document.querySelector("select[name='compSkillField']").value = data.compSkillField;
				document.querySelector("select[name='compSkillDetail']").value = data.compSkillDetail;
				document.querySelector("select[name='compSkillLevel']").value = data.compSkillLevel;

				console.log("폼 데이터가 채워졌습니다:", data);
			})
			.catch(error => {
				console.error("데이터를 가져오는 중 오류 발생:", error);
			});
	}
});

document.querySelector("#awardTable tbody").addEventListener("click", function(e) {
	const baseUrl = document.body.dataset.url;

	// 삭제 버튼 클릭 처리
	if (e.target.classList.contains("awardDelBtn")) {
		e.stopPropagation();
		e.preventDefault();

		// 클릭된 행에서 데이터 가져오기
		const row = e.target.closest("tr");
		const awardNo = row.querySelector(".awardNo").value;

		const isConfirmed = confirm("정말 삭제 하시겠습니까?");
		if (isConfirmed) {
			fetch(`${baseUrl}/award/${awardNo}`, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ awardNo }),
			})
				.then(response => {
					if (response.ok) {
						row.remove(); // 성공 시 행 삭제
					} else {
						throw new Error("삭제 요청 실패");
					}
				})
				.catch(error => {
					console.error("삭제 중 오류 발생:", error);
				});
		}
		return;
	}

	// 행 클릭 처리 (삭제 버튼이 아닌 경우)
	const row = e.target.closest("tr");
	if (row) {
		const awardNo = row.dataset.awardNo;

		fetch(`${baseUrl}/award/${awardNo}`)
			.then(response => {
				if (!response.ok) {
					throw new Error("데이터 로드 실패");
				}
				return response.json();
			})
			.then(data => {
				// 폼 채우기
				document.querySelector("input[name='awardNo']").value = data.awardNo;
				document.querySelector("input[name='awardTitle']").value = data.awardTitle;
				document.querySelector("input[name='awardIssuer']").value = data.awardIssuer;
				document.querySelector("input[name='awardDate']").value = formatDate(data.awardDate);

				console.log("폼 데이터가 채워졌습니다:", data);
			})
			.catch(error => {
				console.error("데이터를 가져오는 중 오류 발생:", error);
			});
	}
});

document.querySelector("#eduTable tbody").addEventListener("click", function(e) {
	const baseUrl = document.body.dataset.url;

	// 삭제 버튼 클릭 처리
	if (e.target.classList.contains("eduDelBtn")) {
		e.stopPropagation();
		e.preventDefault();

		// 클릭된 행에서 데이터 가져오기
		const row = e.target.closest("tr");
		const eduNo = row.querySelector(".eduNo").value;

		const isConfirmed = confirm("정말 삭제 하시겠습니까?");
		if (isConfirmed) {
			fetch(`${baseUrl}/edu/${eduNo}`, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ eduNo }),
			})
				.then(response => {
					if (response.ok) {
						row.remove(); // 성공 시 행 삭제
					} else {
						throw new Error("삭제 요청 실패");
					}
				})
				.catch(error => {
					console.error("삭제 중 오류 발생:", error);
				});
		}
		return;
	}

	// 행 클릭 처리 (삭제 버튼이 아닌 경우)
	const row = e.target.closest("tr");
	if (row) {
		const eduNo = row.dataset.eduNo;

		fetch(`${baseUrl}/edu/${eduNo}`)
			.then(response => {
				if (!response.ok) {
					throw new Error("데이터 로드 실패");
				}
				return response.json();
			})
			.then(data => {
				// 폼 채우기
				document.querySelector("input[name='eduNo']").value = data.eduNo;
				document.querySelector("input[name='eduTitle']").value = data.eduTitle;
				document.querySelector("input[name='eduInstitution']").value = data.eduInstitution;
				document.querySelector("input[name='eduSdt']").value = formatDate(data.eduSdt);
				document.querySelector("input[name='eduEdt']").value = formatDate(data.eduEdt);
				document.querySelector("textarea[name='eduDesc']").value = data.eduDesc;

				console.log("폼 데이터가 채워졌습니다:", data);
			})
			.catch(error => {
				console.error("데이터를 가져오는 중 오류 발생:", error);
			});
	}
});

document.querySelector("#langTable tbody").addEventListener("click", function(e) {
	const baseUrl = document.body.dataset.url;

	// 삭제 버튼 클릭 처리
	if (e.target.classList.contains("langDelBtn")) {
		e.stopPropagation();
		e.preventDefault();

		// 클릭된 행에서 데이터 가져오기
		const row = e.target.closest("tr");
		const langNo = row.querySelector(".langNo").value;

		const isConfirmed = confirm("정말 삭제 하시겠습니까?");
		if (isConfirmed) {
			fetch(`${baseUrl}/lang/${langNo}`, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ langNo }),
			})
				.then(response => {
					if (response.ok) {
						row.remove(); // 성공 시 행 삭제
					} else {
						throw new Error("삭제 요청 실패");
					}
				})
				.catch(error => {
					console.error("삭제 중 오류 발생:", error);
				});
		}
		return;
	}

	// 행 클릭 처리 (삭제 버튼이 아닌 경우)
	const row = e.target.closest("tr");
	if (row) {
		const langNo = row.dataset.langNo;

		fetch(`${baseUrl}/lang/${langNo}`)
			.then(response => {
				if (!response.ok) {
					throw new Error("데이터 로드 실패");
				}
				return response.json();
			})
			.then(data => {
				// 폼 채우기
				document.querySelector("input[name='langNo']").value = data.langNo;
				document.querySelector("select[name='langNm']").value = data.langNm;
				document.querySelector("select[name='langSpeakingLevel']").value = data.langSpeakingLevel;
				document.querySelector("select[name='langReadingLevel']").value = data.langReadingLevel;
				document.querySelector("select[name='langWritingLevel']").value = data.langWritingLevel;

				console.log("폼 데이터가 채워졌습니다:", data);
			})
			.catch(error => {
				console.error("데이터를 가져오는 중 오류 발생:", error);
			});
	}
});

document.querySelector("#langTestTable tbody").addEventListener("click", function(e) {
	const baseUrl = document.body.dataset.url;

	// 삭제 버튼 클릭 처리
	if (e.target.classList.contains("langTestDelBtn")) {
		e.stopPropagation();
		e.preventDefault();

		// 클릭된 행에서 데이터 가져오기
		const row = e.target.closest("tr");
		const langTestNo = row.querySelector(".langTestNo").value;

		const isConfirmed = confirm("정말 삭제 하시겠습니까?");
		if (isConfirmed) {
			fetch(`${baseUrl}/langTest/${langTestNo}`, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ langTestNo }),
			})
				.then(response => {
					if (response.ok) {
						row.remove(); // 성공 시 행 삭제
					} else {
						throw new Error("삭제 요청 실패");
					}
				})
				.catch(error => {
					console.error("삭제 중 오류 발생:", error);
				});
		}
		return;
	}

	// 행 클릭 처리 (삭제 버튼이 아닌 경우)
	const row = e.target.closest("tr");
	if (row) {
		const langTestNo = row.dataset.langtestNo;

		fetch(`${baseUrl}/langTest/${langTestNo}`)
			.then(response => {
				if (!response.ok) {
					throw new Error("데이터 로드 실패");
				}
				return response.json();
			})
			.then(data => {
				// 폼 채우기
				document.querySelector("input[name='langTestNo']").value = data.langTestNo;
				document.querySelector("select[name='langTestName']").value = data.langTestName;
				document.querySelector("input[name='langTestLevel']").value = data.langTestLevel;
				document.querySelector("input[name='langTestDate']").value = formatDate(data.langTestDate);

				console.log("폼 데이터가 채워졌습니다:", data);
			})
			.catch(error => {
				console.error("데이터를 가져오는 중 오류 발생:", error);
			});
	}
});

document.querySelector("#expTable tbody").addEventListener("click", function(e) {
	const baseUrl = document.body.dataset.url;

	// 삭제 버튼 클릭 처리
	if (e.target.classList.contains("expDelBtn")) {
		e.stopPropagation();
		e.preventDefault();

		// 클릭된 행에서 데이터 가져오기
		const row = e.target.closest("tr");
		const expNo = row.querySelector(".expNo").value;

		const isConfirmed = confirm("정말 삭제 하시겠습니까?");
		if (isConfirmed) {
			fetch(`${baseUrl}/exp/${expNo}`, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ expNo }),
			})
				.then(response => {
					if (response.ok) {
						row.remove(); // 성공 시 행 삭제
					} else {
						throw new Error("삭제 요청 실패");
					}
				})
				.catch(error => {
					console.error("삭제 중 오류 발생:", error);
				});
		}
		return;
	}

	// 행 클릭 처리 (삭제 버튼이 아닌 경우)
	const row = e.target.closest("tr");
	if (row) {
		const expNo = row.dataset.expNo;

		fetch(`${baseUrl}/exp/${expNo}`)
			.then(response => {
				if (!response.ok) {
					throw new Error("데이터 로드 실패");
				}
				return response.json();
			})
			.then(data => {
				// 폼 채우기
				document.querySelector("input[name='expNo']").value = data.expNo;
				document.querySelector("select[name='expCountry']").value = data.expCountry;
				document.querySelector("input[name='expSdt']").value = formatDate(data.expSdt);
				document.querySelector("input[name='expEdt']").value = formatDate(data.expEdt);
				document.querySelector("textarea[name='expDesc']").value = data.expDesc;

				console.log("폼 데이터가 채워졌습니다:", data);
			})
			.catch(error => {
				console.error("데이터를 가져오는 중 오류 발생:", error);
			});
	}
});

document.querySelector("#actTable tbody").addEventListener("click", function (e) {
	const baseUrl = document.body.dataset.url;

	// 삭제 버튼 클릭 처리
	if (e.target.classList.contains("actDelBtn")) {
		e.stopPropagation();
		e.preventDefault();

		// 클릭된 행에서 데이터 가져오기
		const row = e.target.closest("tr");
		const actNo = row.querySelector(".actNo").value;

		// SweetAlert2 삭제 확인 메시지
		Swal.fire({
			title: "정말 삭제 하시겠습니까?",
			icon: "warning",
			showCancelButton: true, // 취소 버튼 추가
			confirmButtonText: "네, 삭제합니다.",
			cancelButtonText: "아니요, 취소합니다.",
		}).then((result) => {
			if (result.isConfirmed) {
				// 사용자가 삭제를 확인한 경우
				fetch(`${baseUrl}/act/${actNo}`, {
					method: "DELETE",
					headers: {
						"Content-Type": "application/json",
					},
					body: JSON.stringify({ actNo }),
				})
					.then((response) => {
						if (response.ok) {
							row.remove(); // 성공 시 행 삭제
						} else {
							throw new Error("삭제 요청 실패");
						}
					})
					.catch((error) => {
						console.error("삭제 중 오류 발생:", error);
					});
			}
		});

		return; // 삭제 이벤트 처리 후 반환
	}

	// 행 클릭 처리 (삭제 버튼이 아닌 경우)
	const row = e.target.closest("tr");
	if (row) {
		const actNo = row.dataset.actNo;

		fetch(`${baseUrl}/act/${actNo}`)
			.then((response) => {
				if (!response.ok) {
					throw new Error("데이터 로드 실패");
				}
				return response.json();
			})
			.then((data) => {
				// 폼 채우기
				document.querySelector("input[name='activityNo']").value = data.activityNo;
				document.querySelector("input[name='activityOrganization']").value =
					data.activityOrganization;
				document.querySelector("input[name='activityDesc']").value = data.activityDesc;
				document.querySelector("input[name='activitySdt']").value = formatDate(data.activitySdt);
				if (data.activityEdt) {
					// 종료일 데이터가 존재하면 설정
					document.querySelector("#activityBox").checked = false; // 체크박스 해제
					document.querySelector("input[name='activityEdt']").value = formatDate(data.activityEdt);
					toggleaAtivityStatus(); // 상태 업데이트
				} else {
					// 종료일 데이터가 없으면 체크박스 체크
					document.querySelector("#activityBox").checked = true;
					toggleaAtivityStatus(); // 상태 업데이트
				}

				console.log("폼 데이터가 채워졌습니다:", data);
			})
			.catch((error) => {
				console.error("데이터를 가져오는 중 오류 발생:", error);
			});
	}
});


document.querySelector(".portContainer").addEventListener('click', function(e) {
	if (e.target.classList.contains('portDelBtn')) {
		const portDiv = e.target.closest('.row'); // 삭제 버튼이 속한 .row
		const portNo = portDiv.querySelector('.portNo').value;

		Swal.fire({
			title: "정말 삭제하시겠습니까?",
			icon: "warning",
			showCancelButton: true, // 취소 버튼 추가
			confirmButtonText: "네, 삭제합니다.",
			cancelButtonText: "아니요, 취소합니다.",
		}).then((result) => {
			if (result.isConfirmed) {
				fetch(`${baseUrl}/port/${portNo}`, {
					method: 'DELETE',
					headers: {
						'Content-Type': 'application/json',
					},
				})
					.then(response => {
						if (response.ok) {
							portDiv.remove(); // 성공적으로 삭제되면 div 제거
						} else {
							throw new Error("삭제 요청 실패");
						}
					})
					.catch(error => {
						console.error('Error deleting portfolio:', error);
					});
			}
		});

	}
});