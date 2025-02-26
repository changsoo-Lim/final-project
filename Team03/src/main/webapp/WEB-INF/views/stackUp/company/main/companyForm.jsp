<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

<link rel="stylesheet" type="text/css"
	href="../resources/css/company/form/companyForm.css">
<script
	src="${pageContext.request.contextPath}/resources/ckeditor5-builder-44.0.0/ckeditor5/ckeditor5.js"></script>

<div class="container border mt-5">
	<!-- 탭 리스트 -->
	<ul class="nav nav-tabs" id="filterTabs" role="tablist">
		<li class="nav-item" role="presentation">
			<button class="nav-link active" id="company-detail-tab"
				data-bs-toggle="tab" data-bs-target="#company-detail-type"
				type="button" role="tab" aria-controls="company-detail-type"
				aria-selected="true">기업 정보관리</button>
		</li>
		<li class="nav-item" role="presentation">
			<button class="nav-link" id="content-tab" data-bs-toggle="tab"
				data-bs-target="#content" type="button" role="tab"
				aria-controls="content" aria-selected="false">기업 소개관리</button>
		</li>
		<li class="nav-item" role="presentation">
			<button class="nav-link" id="benefit-tab" data-bs-toggle="tab"
				data-bs-target="#benefit-search" type="button" role="tab"
				aria-controls="benefit-search" aria-selected="false">복리후생관리</button>
		</li>
		<li class="nav-item" role="presentation">
			<button class="nav-link" id="review-search-tab" data-bs-toggle="tab"
				data-bs-target="#review-search" type="button" role="tab"
				aria-controls="review-search" aria-selected="false">면접리뷰</button>
		</li>
		<li class="nav-item" role="presentation">
			<button class="nav-link" id="buy-search-tab" data-bs-toggle="tab"
				data-bs-target="#buy-search" type="button" role="tab"
				aria-controls="buy-search" aria-selected="false">결제내역</button>
		</li>
	</ul>
	<div class="tab-content" id="filterTabsContent">
		<div class="tab-pane fade show active" id="company-detail-type"
			role="tabpanel" aria-labelledby="company-detail-tab">
			<!-- 기업 정보 관리 내용 -->

			<div class="section text-center">
				<!-- 로고 및 기본 정보 -->
				<div class="d-flex align-items-start border-bottom pb-3 mb-4">
					<!-- 로고 영역 -->
					<div class="me-4">
						<c:choose>
							<c:when
								test="${not empty company.file.fileDetails and not empty company.file.fileDetails[0].streFileNm}">
								<img
									src="<c:url value='/images/memberImage/${company.file.fileDetails[0].streFileNm}' />"
									alt="이미지 설명" class="img-fluid rounded" id="logoImg">
							</c:when>
							<c:otherwise>
								<img src="<c:url value='/resources/images/basic-Image.png' />"
									alt="기본 이미지" class="img-fluid rounded" id="logoImg">
							</c:otherwise>
						</c:choose>

						<div class="text-center mt-2">
							<!-- 실제 파일 선택 입력 -->
							<input type="file" id="logoInput" class="d-none" accept="image/*">

							<!-- 커스텀 버튼 -->
							<button type="button" class="btn btn-outline-secondary btn-sm"
								id="logoButton">선택</button>
							<button type="button" class="btn btn-outline-info btn-sm"
								id="logoUpdate">저장</button>
						</div>
					</div>
					<!-- 기본 정보 -->
					<div class="flex-grow-1">
						<div class="row mb-3">
							<label class="col-md-3 col-form-label">기업명</label>
							<div class="col-md-9">
								<input type="text" class="form-control bg-light"
									value="${company.compNm }" readonly="readonly">
							</div>
						</div>
						<div class="row mb-3">
							<label class="col-md-3 col-form-label">아이디</label>
							<div class="col-md-9">
								<input type="text" class="form-control bg-light"
									data-compId="${company.compId }" value="${company.compId }"
									readonly>
							</div>
						</div>
						<div class="row mb-3">
							<label class="col-md-3 col-form-label">대표자명</label>
							<div class="col-md-9">
								<input type="text" class="form-control bg-light"
									value="${company.compRepresentative }" readonly="readonly">
							</div>
						</div>
						<div class="row mb-3">
							<label class="col-md-3 col-form-label">사업자 등록번호</label>
							<div class="col-md-9">
								<input type="text" class="form-control bg-light"
									value="${company.compNum }" readonly="readonly">
							</div>
						</div>
					</div>
				</div>


				<div class="row mb-3">
					<label class="col-md-2 col-form-label">기업구분</label>
					<div class="col-md-4">
						<input type="email" class="form-control bg-light"
							value="${company.compType }" readonly="readonly" />
					</div>
					<label class="col-md-2 col-form-label">직종</label>
					<div class="col-md-4">
						<input type="text" class="form-control bg-light"
							value="${company.compInd }" readonly="readonly">
					</div>
				</div>

				<div class="row mb-3">
					<label class="col-md-2 col-form-label">직무</label>
					<div class="col-md-4">
						<input type="text" class="form-control bg-light"
							value="${company.compJob }" readonly="readonly">
					</div>
					<label class="col-md-2 col-form-label">직무상세</label>
					<div class="col-md-4">
						<input type="text" class="form-control bg-light"
							value="${company.compJobDetail }" readonly="readonly">
					</div>
				</div>

				<!-- 주소 정보 ,.-->
				<div class="row mb-3">
					<label class="col-md-2 col-form-label">우편번호</label>
					<div class="col-md-4">
						<div class="input-group">
							<input type="text" class="form-control bg-light"
								value="${company.compZip }" id="sample4_postcode"
								placeholder="우편번호" readonly="readonly">

						</div>
					</div>
				</div>
				<%-- <div class="row mb-3">
					<label class="col-md-2 col-form-label">도로명 주소</label>
					<div class="col-md-10">
						<input type="text" class="form-control"
							value="${company.compAddr1 }" id="sample4_roadAddress" placeholder="도로명주소">
					</div>
				</div> --%>
				<div class="row mb-3">
					<label class="col-md-2 col-form-label">지번 주소</label>
					<div class="col-md-10">
						<input type="text" class="form-control bg-light"
							value="${company.compAddr1 }" id="sample4_jibunAddress"
							placeholder="지번주소" readonly="readonly">
					</div>
				</div>
				<div class="row mb-3">
					<label class="col-md-2 col-form-label">상세 주소</label>
					<div class="col-md-10">
						<input type="text" class="form-control bg-light"
							value="${company.compAddr2 }" id="sample4_detailAddress"
							placeholder="상세주소" readonly="readonly">
					</div>
				</div>

				<!-- 이메일 및 연락처 -->
				<div class="row mb-3 ">
					<label class="col-md-2 col-form-label">담당자 이메일</label>
					<div class="col-md-4">
						<input type="email" class="form-control border border-primary"
							value="${company.compEmail }" id="compEmail">
					</div>
					<label class="col-md-2 col-form-label">담당자 전화번호</label>
					<div class="col-md-4">
						<input type="text" class="form-control border border-primary"
							value="${company.compMobile }" id="compMobile">
					</div>
				</div>

				<div class="row mb-3">
					<label class="col-md-2 col-form-label">사무실 전화번호</label>
					<div class="col-md-4">
						<input type="text" class="form-control border border-primary"
							value="${company.compPhone }" id="compPhone">
					</div>
					<div hidden="true" id="hiddenLabel"></div>
				</div>
				<!-- 저장 버튼 -->
				<div class="text-center mt-4">
					<button type="button" class="btn btn-primary px-4" id="savebtn">저장</button>
				</div>
			</div>

		</div>
		<div class="tab-pane fade" id="content" role="tabpanel"
			aria-labelledby="content">
			<!-- 기업 소개 내용 -->


			<div class="section">
				<h3 class="mb-3">기업 소개</h3>
				<!-- 수정 가능한 입력 영역 -->
				<textarea id="companyIntro" class="form-control mb-3 " rows="5">${company.compCont}</textarea>
				<!-- 버튼 -->
				<button id="editIntroBtn" class="btn btn-primary btn-sm">저장</button>
			</div>

		</div>



		<div class="tab-pane fade" id="benefit-search" role="tabpanel"
			aria-labelledby="benefit-search-tab">

			<c:set var="categories"
				value="${['급여제도', '교육/생활', '근무 환경', '리프레시', '선물', '조직문화', '지원금/보험', '출퇴근']}" />
			<div class="section">
				<h2>기업 복리후생 목록</h2>
				<div class="benefits-container">
					<!-- 복리후생 카테고리 목록 -->

					<!-- 각 카테고리별 출력 -->
					<c:forEach var="category" items="${categories}">
						<div class="category-block mb-4" data-categorie="${category}">
							<!-- 제목 -->
							<div class="category-title mb-2">
								<strong>${category}</strong>
							</div>

							<!-- 복리후생 목록 -->
							<ul id="benefits-${category}" class="list-unstyled">
								<c:set var="hasBenefits" value="false" />
								<c:set var="count" value="0" />

								<c:forEach var="code" items="${codeList}">
									<c:if test="${code.codeParent eq category}">
										<c:forEach var="benefit" items="${company.bftList}">
											<c:if test="${benefit.cmpbftTile eq code.codeCd}">
												<c:set var="count" value="${count + 1}" />

												<c:if test="${count <= 5}">
													<li>${code.codeNm}</li>
													<c:set var="hasBenefits" value="true" />
												</c:if>

												<!-- "더보기" 표시 -->
												<c:if test="${count == 6}">
													<div class="more-benefits text-muted">더보기...</div>
													<c:set var="count" value="999" />
												</c:if>
											</c:if>
										</c:forEach>
									</c:if>
								</c:forEach>

								<!-- 복리후생이 없을 경우 -->
								<c:if test="${!hasBenefits}">
									<li class="text-muted">목록이 없습니다.</li>
								</c:if>
							</ul>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>

		<div class="tab-pane fade" id="review-search" role="tabpanel"
			aria-labelledby="review-search-tab">

			<div class="section my-5">
				<h3 class="mb-4">면접 리뷰</h3>
				<c:choose>
					<c:when test="${not empty company.reviewList[0].reviewNo}">
						<div class="row g-4">
							<c:forEach items="${company.reviewList}" var="item">
								<div class="col-lg-4 col-md-6 col-sm-12">
									<div class="card review-card h-100"
										data-review-id="${item.reviewNo}">
										<div class="card-body">
											<h5 class="card-title">${item.reviewTitle}</h5>
											<h6 class="card-subtitle mb-2 text-muted">
											    평점: <span class="stars-container" data-rating="${item.reviewRating}"></span>
											</h6>

											<p class="card-text">
												<small class="text-muted">작성일: ${item.reviewDt}</small>
											</p>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:when>

					<c:otherwise>
						<p class="text-center text-muted">현재 등록된 면접 리뷰가 없습니다.</p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>


		<div class="tab-pane fade" id="buy-search" role="tabpanel"
			aria-labelledby="buy-search-tab">
			
			<div class="section my-5">
				<h3 class="mb-4">결제 내역</h3>
				<a href="${pageContext.request.contextPath}/buy/${company.compId }/list" class="btn btn-outline-info btn-sm">결제 내역 관리</a>
				<div>
					<c:choose>
						<c:when test="${not empty company.buyList[0].buyNo}">
							<div class="row g-4">
								<c:forEach items="${company.buyList}" var="item">
									<div class="col-lg-4 col-md-6 col-sm-12">
										<div class="card h-100">
											<div class="card-body">
												<h5 class="card-title">공고명 : </h5>
												<p class="card-text">상품 : ${item.productCd }</p>
												<p class="card-text">가격 : ${item.buyAmount }</p>
												<p class="card-text">결제수단 : ${item.buyMethod }</p>
												<p class="card-text">결제일시 : ${item.buyDate }</p>
												<p class="card-text">상품수량 : ${item.buyQty}</p>
												<p class="card-text">상품시작일자 : ${item.buySdt }</p>
												<p class="card-text">상품종료일자 : ${item.buyEdt }</p>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:when>

						<c:otherwise>
							<p class="text-center text-muted">구매한 상품이 없습니다.</p>
						</c:otherwise>
					</c:choose>

				</div>

			</div>
		</div>


	</div>
</div>
<!--Modal-->
<div class="modal fade" id="addModal" tabindex="-1"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalCenter">포지션 제안</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<div id="benefit-list"></div>
			</div>
			<div class="add-modal-footer d-flex justify-content-end p-3">
				<button type="button" class="btn btn-secondary me-2"
					data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" id="update-button">수정</button>
			</div>
		</div>
	</div>
</div>

<input type="hidden" id="currentCategory" value="">

<script src="${pageContext.request.contextPath }/resources/js/ckEditor/ckEditorScript.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/company/main/companyForm.js"></script>