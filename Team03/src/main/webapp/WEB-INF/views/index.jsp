<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<link rel="stylesheet"
   href="https://unpkg.com/swiper/swiper-bundle.min.css" />

<link rel="stylesheet" href="<c:url value="/resources/css/main/index/index.css" />" />
<div class="tab-content mt-4 p-5 rounded-2"
   id="pills-tabTwoContent">
   <div class="tab-pane tab-example-preview fade active show"
      id="pills-event-carousel-preview" role="tabpanel"
      aria-labelledby="pills-event-carousel-preview-tab">

      <section>
         <!-- 배너 -->
         <div class="swiper-container swiper" id="swiper-1"
            data-pagination-type="" data-speed="400" data-space-between="100"
            data-pagination="true" data-navigation="false" data-autoplay="true"
            data-autoplay-delay="5000"
            data-breakpoints='{"480": {"slidesPerView": 1}, "768": {"slidesPerView": 1}, "1024": {"slidesPerView": 1}}'>
            <div class="swiper-wrapper pb-6 mx-auto">

                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/goodbyebanner1.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/ddit.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/end.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/YU.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/goodbyebanner1.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/ddit.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/end.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/YU.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/goodbyebanner1.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/ddit.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/end.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="swiper-slide">
                     <div class="card shadow-sm overflow-hidden">
                        <div class="row g-0">

                           <!-- 텍스트 DIV 크기 col-xl-2  이미지+텍스트 크기 최대 11 까지    -->
                           <!-- 이미지 DIV 크기  -->
                           <div class=""
                              style="background-image: url(${pageContext.request.contextPath }/resources/images/YU.png); background-size: cover; background-repeat: no-repeat; background-position: center; min-height: 15rem;">
                              <!-- for mobile img-->
                           </div>
                        </div>
                     </div>
                  </div>

            </div>
            <!-- Add Pagination -->
            <div class="swiper-pagination"></div>
            <!-- Add Navigation -->
            <div class="swiper-navigation">
               <div class="swiper-button-next"></div>
               <div class="swiper-button-prev"></div>
            </div>
         </div>


      </section>
   </div>
</div>

<!---------------------------------------------여기부터 프레지스티-------------------------------------------------------------------------------->

<!-- 프레스티지 입니다~ -->
<div>
	<!-- 프레스티지 섹션 위아래 간격 my-xl-2 -->
	<section class="my-xl-2 my-5">
		<div class="container">
			<div class="section">
<hr>
				<div class="col-lg-12 col-md-12 col-12">
					<h4 class="mb-lg-7">프레스티지</h4>
				</div>

				<!-- Blog Card -->
				<div class="table-responsive-lg">
					<div class="row g-5 flex-nowrap pb-4 pb-lg-0 me-5 me-lg-0">

<!---------------------------------------------프레지스티-------------------------------------------------------------------------------->
						<!--  반복 구간 -->
							<article class="col-lg-4 col-md-6 col-12">
								<figure class="mb-4 zoom-img">
									<a href="../blog-single.html"> <img
										src="${pageContext.request.contextPath }/resources/images/빗썸.jpg"
										class="img-fluid rounded-3" />
									</a>
								</figure>

								<a href="#!"
									class="badge bg-primary-subtle text-primary-emphasis rounded-pill text-uppercase">Startup</a>
								<h3 class="my-3 lh-base h4">
									<a href="../blog-single.html" class="text-reset">빗썸</a>
								</h3>
								<div
									class="d-flex align-items-center justify-content-between mb-3 mb-md-0">
									<div class="d-flex align-items-center">
										<div class="ms-2">
											<a href="#" class="text-reset fs-6">[빗썸] 빗썸 가상자산 기술 전문가 양성 교육생 모집 </a>
										</div>
									</div>
									<div class="ms-3">
										<span class="fs-6">D-6</span>
									</div>
								</div>
							</article>
<!--------------------------------------------------------------------------->							
							<article class="col-lg-4 col-md-6 col-12">
								<figure class="mb-4 zoom-img">
									<a href="../blog-single.html"> <img
										src="${pageContext.request.contextPath }/resources/images/제주신화월드.jpg"
										class="img-fluid rounded-3" />
									</a>
								</figure>

								<a href="#!"
									class="badge bg-primary-subtle text-primary-emphasis rounded-pill text-uppercase">Startup</a>
								<h3 class="my-3 lh-base h4">
									<a href="../blog-single.html" class="text-reset">제주신화월드</a>
								</h3>
								<div
									class="d-flex align-items-center justify-content-between mb-3 mb-md-0">
									<div class="d-flex align-items-center">
										<div class="ms-2">
											<a href="#" class="text-reset fs-6">[제주신화월드] 랜딩카지노 신입 및 경력 사원 모집</a>
										</div>
									</div>
									<div class="ms-3">
										<span class="fs-6">D-25</span>
									</div>
								</div>
							</article>
<!------------------------------------------------------------------------------->
							<article class="col-lg-4 col-md-6 col-12">
								<figure class="mb-4 zoom-img">
									<a href="../blog-single.html"> <img
										src="${pageContext.request.contextPath }/resources/images/대한상공회의소.jpg"
										class="img-fluid rounded-3" />
									</a>
								</figure>

								<a href="#!"
									class="badge bg-primary-subtle text-primary-emphasis rounded-pill text-uppercase">Startup</a>
								<h3 class="my-3 lh-base h4">
									<a href="../blog-single.html" class="text-reset">대한상공회의소</a>
								</h3>
								<div
									class="d-flex align-items-center justify-content-between mb-3 mb-md-0">
									<div class="d-flex align-items-center">
										<div class="ms-2">
											<a href="#" class="text-reset fs-6">[전액국비] 첨단기술 교육생 모집(반도체/AI/IoT)</a>
										</div>
									</div>
									<div class="ms-3">
										<span class="fs-6">D-17</span>
									</div>
								</div>
							</article>

					</div>
				</div>
			</div>
		</div>
	</section>
</div>
<!---------------------------------------------프레지스티-------------------------------------------------------------------------------->

<hr>
<!----------------------------------------- 슈페리어 -------------------------------------------------->
<section class="mb-xl-9 my-5">
	<div class="container">
		<div class="row g-4">
			<div class="col-lg-12 col-md-12 col-12">
				<h4 class="mb-lg-7">슈페리어</h4>
			</div>
		
			<!--  반복 구간  -->
				<div class="col-lg-3 col-md-6 col-12">
					<figure class="mb-3 card-lift">
						<img
							src="${pageContext.request.contextPath }/resources/images/그랜드 하얏트.jpg"
							class="img-fluid rounded-3" />
					</figure>
					<h3 class="mb-3">그랜드 하얏트</h3>
					<div class="d-flex">
						<div class="ms-2">
							<span>[그랜드 하얏트] 고객 서비스 및 호텔 운영 전문가 모집</span>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-6 col-12">
					<figure class="mb-3 card-lift">
						<img
							src="${pageContext.request.contextPath }/resources/images/SOOP.jpg"
							class="img-fluid rounded-3" />
					</figure>
					<h3 class="mb-3">SOOP</h3>
					<div class="d-flex">
						<div class="ms-2">
							<span>[SOOP] 창의적 콘텐츠 기획자 및 마케팅 전문가 모집</span>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-6 col-12">
					<figure class="mb-3 card-lift">
						<img
							src="${pageContext.request.contextPath }/resources/images/쥬비스 다이어트.jpg"
							class="img-fluid rounded-3" />
					</figure>
					<h3 class="mb-3">쥬비스 다이어트</h3>
					<div class="d-flex">
						<div class="ms-2">
							<span>[쥬비스 다이어트] 피트니스 트레이너 및 상담사 모집</span>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-6 col-12">
					<figure class="mb-3 card-lift">
						<img
							src="${pageContext.request.contextPath }/resources/images/한국수력원자력.jpg"
							class="img-fluid rounded-3" />
					</figure>
					<h3 class="mb-3">한국수력원자력</h3>
					<div class="d-flex">
						<div class="ms-2">
							<span>[한국수력원자력] 에너지 기술 전문가 및 엔지니어 모집</span>
						</div>
					</div>
				</div>
		<!--  반복 구간  -->


		</div>
	</div>
</section>
<!--location-->

<!----------------------------------------- 슈페리어 -------------------------------------------------->




<hr>
<!------------------------------------------------------------------슈프림 시작------------------------------------------------------------------------>
<section class="my-lg-9 my-5">
	<div class="container">

		<!-- 상단 -->
		<div class="row">
			<div class="col-lg-12 col-md-12 col-12">
				<h4 class="mb-lg-7">슈프림</h4>
			</div>
		</div>
		<!-- 상단 -->

		<div class="row g-3">
<!--------------------------------------------슈프림1------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/(주)골든듀.jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">(주)골든듀</a>
						</h5>
						<small>[공개채용]디자이너, 마케팅 직군 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림2------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/(주)루텍.jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">(주)루텍</a>
						</h5>
						<small>[경력]연구개발 직군 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림3------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/HD현대일렉트릭.png"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">HD현대일렉트릭</a>
						</h5>
						<small>[경력]전기 엔지니어, 기술지원 직군 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림4------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/IBM X Red Hat.jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">IBM X Red Hat</a>
						</h5>
						<small>[경력]소프트웨어 개발 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림5------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/KOTRA외투기업.png"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">KOTRA외투기업</a>
						</h5>
						<small>[경력]해외 진출 지원 인력 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림6------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/KT파트너.png"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">KT파트너</a>
						</h5>
						<small>[신입]시스템 운영 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림7------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/LG CNS 협력회사.png"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">LG CNS</a>
						</h5>
						<small>[신입]개발자, 시스템 엔지니어 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림8------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/NTECH SERVICE.jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">NTECH SERVICE</a>
						</h5>
						<small>[신입]기술 지원, 시스템 엔지니어 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림9------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/S&I corp..jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">S&I corp.</a>
						</h5>
						<small>[공개채용]글로벌 마케팅, 비즈니스 개발 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림10------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/Sejong.jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">Sejong</a>
						</h5>
						<small>[공개채용]전자제품 제조, 연구개발 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림11------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/UNI SEM.jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">UNI SEM</a>
						</h5>
						<small>[공개채용]반도체, 연구개발 직군 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림12------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/가온그룹(주).jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">가온그룹(주)</a>
						</h5>
						<small>[공개채용]생산관리, 품질관리 직군 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림13------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/고용노동부.png"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">고용노동부</a>
						</h5>
						<small>[신입]정책지원, HR 관련 직군 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림14------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/대방건설.jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">대방건설</a>
						</h5>
						<small>[신입]건축 설계, 현장 관리 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림15------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/부루벨코리아.jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">부루벨코리아</a>
						</h5>
						<small>[신입]기술직, 생산직 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림16------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/새마을금고복지회.gif"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">새마을금고복지회</a>
						</h5>
						<small>[경력]사회복지, 금융서비스 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림17------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/중소기업중앙회.png"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">중소기업중앙회</a>
						</h5>
						<small>[경력]정책지원, 경영 컨설턴트 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림18------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/킨코스코리아(주).gif"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">킨코스코리아(주)</a>
						</h5>
						<small>[신입]기술 엔지니어, 생산직 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림19------------------------------------------------------>
			<div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/한국철도기술연구원.jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">한국철도기술연구원</a>
						</h5>
						<small>[경력]연구직, 기술개발 모집</small>
					</div>
				</div>
			</div>
<!--------------------------------------------슈프림20------------------------------------------------------>
			 <div class="col-lg-3 col-md-6">
				<div class="card text-center card-lift">
					<div class="card-body py-4">
						<figure class="mb-4">
							<img
								src="${pageContext.request.contextPath }/resources/images/현대자동차.jpg"
								alt="avatar" class="avatar avatar-xxl" />
						</figure>

						<h5 class="mb-1">
							<a href="">현대자동차</a>
						</h5>
						<small>[공개채용] 현대자동차 생산직, 전산직 모집</small>
					</div>
				</div>
			</div>  

			
			
			
			
			
			
			
			
			
		</div>
	</div>
</section>
<!--save your time-->
<!-------------------------------------------슈프림---------------------------------------------------->

<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>


<!-- 1. 인덱스로 들어가고 보안 로그인 넣을려면 webxml에서 설정해줘야됨 -->

<!-- 2. 로그아웃이 되지않으면 로그인 페이지로 들어가야함 -->

<!-- 3. 로그인이 됬으면  pricipal을 사용해서 사용자 정보를 보여주어야함 -->