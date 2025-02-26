<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/resume/resume.css" />
            <div class="container mt-4">
                <div class="fixed-box">
                    <ul id="categoryList">
                        <li data-id="btncheck1">자격증</li>
                        <li data-id="btncheck2">컴퓨터활용능력</li>
                        <li data-id="btncheck3">취업우대사항</li>
                        <li data-id="btncheck4">수상경력</li>
                        <li data-id="btncheck5">교육수료사항</li>
                        <li data-id="btncheck6">어학능력</li>
                        <li data-id="btncheck7">해외연수 및 경험</li>
                        <li data-id="btncheck8">봉사 및 주요활동</li>
                        <li data-id="btncheck9">포트폴리오</li>
                    </ul>
                    <button type="reset" class="btn btn-outline-danger" id="resetBtn">초기화</button>
                    <button type="button" class="btn btn-outline-primary" id="allAddBtn">모두저장</button>
                    <button type="button" class="btn btn-outline-primary" id="dataBtn">데이터입력</button>
                </div>
            </div>
            <h2 class="fw-bold mb-2">이력서 관리</h2>
            <br>
            <form class="form">
                <div class="row">
                    <div class="col-12">
                        <h3 class="fw-bold mb-2">기본정보</h3>
                        <p class="text-muted small">인사담당자가 연락하는 이메일과 휴대폰 정보가 올바르게 입력되어 있는지 확인하세요.</p>
                    </div>
                </div>
                <hr style="border: 2px solid black;">

                <div class="row">
                    <!-- 사진 등록 영역 (왼쪽) -->
                    <div class="col-md-3 text-center border-end">
                        <!-- 사진 미리보기 -->
                        <div class="mb-3">
						    <img id="profileImage"
						        src="<c:url value='/images/MemberImage/${member.file.fileDetails[0].streFileNm }' />"
						        class="img-fluid rounded border" style="width: 150px; height: 180px;"
						        onerror="this.onerror=null; this.src='<c:url value='/resources/images/basic-Image.png' />';">
						</div>
                        <!-- 버튼 -->
                        <div>
                            <label class="btn btn-secondary btn-sm me-2">
                                등록
                                <input type="file" id="fileBtn" name="uploadFiles" accept="image/*"
                                    style="display: none;">
                            </label>
                        </div>
                    </div>

                    <!-- 입력 필드 영역 (오른쪽) -->
                    <div class="col-md-9">
                        <div class="row mb-3">
                            <input type="hidden" name="memId" value="${member.memId}">
                            <label for="memNm" class="col-md-3 col-form-label fw-bold">이름</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="memNm" name="memNm" placeholder="한글"
                                    readonly value="${member.memNm}">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="memRegno" class="col-md-3 col-form-label fw-bold">생년월일</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="memRegno" name="memRegno"
                                    placeholder="ex) 19850213" readonly value="${member.memRegno}">
                                <div class="mt-2">
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="memGen" id="memGenMale"
                                            value="M" ${member.memGen=='M' ? 'checked' : '' } disabled>
                                        <label class="form-check-label" for="memGenMale">남자</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="memGen" id="memGenFemale"
                                            value="F" ${member.memGen=='F' ? 'checked' : '' } disabled>
                                        <label class="form-check-label" for="memGenFemale">여자</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="memHp" class="col-md-3 col-form-label fw-bold">휴대폰번호</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="memHp" name="memHp"
                                    placeholder="ex) 01012341234" readonly value="${member.memHp}">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="memEmail" class="col-md-3 col-form-label fw-bold">이메일</label>
                            <div class="col-md-9">
                                <input type="email" class="form-control" id="memEmail" name="memEmail"
                                    placeholder="이메일 주소를 입력하세요" readonly value="${member.memEmail}">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="memAddr1" class="col-md-3 col-form-label fw-bold">주소</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control mb-2" id="memAddr1" name="memAddr1"
                                    placeholder="기본주소" readonly value="${member.memAddr1}">
                                <input type="text" class="form-control" id="memAddr2" name="memAddr2" placeholder="상세주소"
                                    readonly value="${member.memAddr2}">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="memUrl" class="col-md-3 col-form-label fw-bold">홈페이지/SNS</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" id="memUrl" name="memUrl"
                                    placeholder="http://www.example.com" readonly value="${member.memUrl}">
                            </div>
                        </div>
                    </div>
                </div>
                <hr style="border: 1px solid black;">
            </form> <!-- 기본정보 -->

            <br>

            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home-tab-pane"
                        type="button" role="tab" aria-controls="home-tab-pane" aria-selected="true">학력사항</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile-tab-pane"
                        type="button" role="tab" aria-controls="profile-tab-pane" aria-selected="false">경력사항</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact-tab-pane"
                        type="button" role="tab" aria-controls="contact-tab-pane" aria-selected="false">선택사항</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="disabled-tab" data-bs-toggle="tab" data-bs-target="#disabled-tab-pane"
                        type="button" role="tab" aria-controls="disabled-tab-pane" aria-selected="false">희망근무조건</button>
                </li>
            </ul>

            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab"
                    tabindex="0">
                    <br><br><br>
                    <form class="form">
                        <div class="row mb-3">
                            <div class="col-10">
                                <h3 class="fw-bold mb-2">학력사항</h3>
                                <p class="text-muted small">작성된 이력에서는 <strong>졸업한 순서</strong>대로 보여집니다.</p>
                            </div>
                            <div class="col-md-2" id="hqBtnCheck">
                                <input type="checkbox" id="hqBtn" onclick="toggleFields();">
                                <label for="hqBtn" class="col-form-label pt-6 fw-bold ms-2">검정고시</label>
                            </div>
                        </div>
                        <hr style="border: 2px solid black;">
                        <div id="highschoolFields">
                            <div class="row mb-3 align-items-center">
                                <input type="hidden" id="highschoolNo" name="highschoolNo" value="">
                                <label for="highschoolNm" class="col-md-2 col-form-label fw-bold">고등학교</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" id="highschoolNm" name="highschoolNm"
                                        placeholder="고등학교명을 입력해주세요">
                                </div>
                                <label for="highschoolLocation" class="col-md-1 col-form-label fw-bold">소재지</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control" id="highschoolLocation"
                                        name="highschoolLocation" placeholder="ex) 대전">
                                </div>
                            </div>

                            <div class="row mb-3 align-items-center" id="schoolDateFields">

                                <label for="highschoolSd" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">재학기간</label>

                                <div class="col-md-3">
                                    <input type="date" class="form-control" id="highschoolPeriod" name="highschoolPeriod">
                                </div>

                                <div class="col-md-2">
                                    <select class="form-select" id="highschoolStatus" name="highschoolStatus">
                                        <option value="">선택</option>
                                        <c:forEach items="${graduationList}" var="graduation">
                                            <option value="${graduation.codeCd}">${graduation.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                            </div>
                            <div class="row mb-3" id="majorFields">
                                <label for="highschoolNm" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">전공</label>
                                <div class="col-md-3">
                                    <select class="form-select" id="highschoolMajor" name="highschoolMajor">
                                        <option value="">계열선택</option>
                                        <c:forEach items="${highMajorList}" var="highMajor">
                                            <option value="${highMajor.codeCd}">${highMajor.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </form><!-- 고등학교 -->

                    <form class="form">
                        <div id="qualificationField">
                            <div class="row mb-3 align-items-center" id="examPassDate" style="display: none;">
                                <label for="qualificationDt" class="col-md-3 col-form-label fw-bold"
                                    data-required="true">검정고시 합격일자</label>
                                <input type="hidden" id="qualificationNo" name="qualificationNo" value="">
                                <div class="col-md-4">
                                    <input type="date" class="form-control" id="qualificationDt" name="qualificationDt">
                                </div>
                            </div>
                        </div>
                    </form><!-- 검정고시 -->

                    <hr style="border: 2px solid black;">

                    <form class="form" id="uniForm"> <!-- 대학교 -->
                        <div id="uniField">
                            <div class="row mb-3 align-items-center">
                                <input type="hidden" id="uniNo" name="uniNo">
                                <label for="uniNm" class="col-md-2 col-form-label fw-bold" data-required="true">대학교</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" id="uniNm" name="uniNm"
                                        placeholder="대학,대학교,대학원명을 입력해주세요" required>
                                </div>
                                <div class="col-md-4">
                                    <select class="form-select" id="uniType" name="uniType">
                                        <option value="">선택</option>
                                        <c:forEach items="${univTypeList}" var="univType">
                                            <option value="${univType.codeCd}">${univType.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="row mb-3 align-items-center" id="schoolDateFields">
                                <!-- 입학일자 -->
                                <label for="uniSdt" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">재학기간</label>
                                <div class="col-md-3">
                                    <input type="date" class="form-control" id="uniSdt" name="uniSdt" required>
                                </div>
                                <div class="col-md-2">
                                    <select class="form-select" id="uniSStatus" name="uniSStatus" required>
                                        <option value="">선택</option>
                                        <c:forEach items="${admissionList}" var="admission">
                                            <option value="${admission.codeCd}">${admission.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- 졸업일자 -->
                                <div class="col-md-3">
                                    <input type="date" class="form-control" id="uniEdt" name="uniEdt" required>
                                </div>
                                <div class="col-md-2">
                                    <select class="form-select" id="uniEStatus" name="uniEStatus" required>
                                        <option value="">선택</option>
                                        <c:forEach items="${graduationList}" var="graduation">
                                            <option value="${graduation.codeCd}">${graduation.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <!-- 전공 라벨 -->
                                <label for="uniMajorCategory" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">전공</label>

                                <!-- 전공 계열 선택 -->
                                <div class="col-md-3">
                                    <select class="form-select" id="uniMajorCategory" name="uniMajorCategory" required>
                                        <option value="">선택</option>
                                        <c:forEach items="${univMajorList}" var="univMajor">
                                            <option value="${univMajor.codeCd}">${univMajor.codeNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- 전공명 입력 -->
                                <div class="col-md-3">
                                    <input type="text" class="form-control" id="uniMajorNm" name="uniMajorNm"
                                        placeholder="전공명을 입력해주세요" required>
                                </div>

                                <div class="col-md-2">
                                    <select class="form-select" id="uniMajorDegree" name="uniMajorDegree" required>
                                        <option value="">선택</option>
                                        <c:forEach items="${univDegreeList}" var="univDegree">
                                            <option value="${univDegree.codeCd}">${univDegree.codeNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- GPA 선택 -->
                                <div class="col-md-2">
                                    <input type="text" id="uniGpa" name="uniGpa" class="form-control"
                                        placeholder="ex) 4.5" required>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="uniProjectNm" class="col-md-2 col-form-label fw-bold">수업 및
                                    프로젝트</label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="uniProjectNm" name="uniProjectNm"
                                        placeholder="수업명을 입력하세요.">
                                    <textarea id="uniProjectDesc" name="uniProjectDesc" class="form-control"
                                        placeholder="수업내용을 자세히 입력하세요." rows="5"></textarea>
                                </div>
                            </div>
                            <hr style="border: 1px solid black;">
                        </div>
                        <div align="right">
                            <button type="button" class="btn btn-primary btn-sm" id="uniInsertBtn">이 부분만 저장</button>
                        </div>
                    </form> <!-- 학력사항 -->
                    <table class="table table-hover" id="uniTable">
                        <thead>
                            <tr>
                                <th class="uni-name"><strong>대학교명</strong></th>
                                <th class="uni-major"><strong>전공</strong></th>
                                <th class="date-column"><strong>입학일자</strong></th>
                                <th class="date-column"><strong>졸업일자</strong></th>
                                <th class="graduation-status"><strong>졸업상태</strong></th>
                                <th class="del-status"></th>
                            </tr>
                        </thead>
                        <tbody id="uniTableBody">

                        </tbody>
                    </table>
                </div>

                <br><br><br>
                <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab"
                    tabindex="0">
                    <form class="form">
                        <div class="row mb-3">
                            <div class="col-12">
                                <h3 class="fw-bold mb-2">경력사항</h3>
                                <p class="text-muted small">작성된 이력에서는 <strong>최근 근무일을 우선</strong>으로 보여집니다.</p>
                            </div>
                        </div>

                        <hr style="border: 2px solid black;">

                        <div class="row mb-3">
                            <div class="col-md-12">
                                <!-- 경력여부 라벨 -->
                                <label for="career" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">경력여부</label>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="career" id="careerNew" value="신입"
                                        ${empty careerList ? 'checked' : ''} onclick="toggleCareerDiv();">
                                    <label class="form-check-label" for="careerNew">신입</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="career" id="careerOld" value="경력"
                                        ${not empty careerList ? 'checked' : ''} onclick="toggleCareerDiv();">
                                    <label class="form-check-label" for="careerOld">경력</label>
                                </div>
                            </div>
                        </div>

                        <!-- 경력 선택 -->
                        <div id="careerField" style="display: none;">
                            <div class="row mb-3">
                                <input type="hidden" id="careerNo" name="careerNo" value="${career.careerNo}">
                                <label for="career" class="col-md-2 col-form-label fw-bold">회사명</label>
                                <div class="col-md-7">
                                    <input type="text" class="form-control" id="careerCompanyNm" name="careerCompanyNm"
                                        placeholder="회사명을 입력하세요.">
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-3">
                                <div class="col-md-3 col-form-label fw-bold">
                                    <select class="form-select" id="careerIndustryType" name="careerIndustryType">
                                        <option value="">업종</option>
                                        <c:forEach items="${industryList}" var="industry">
                                            <option value="${industry.codeCd}" data-code="${industry.codeCd}">
                                                ${industry.codeNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" id="selectedIndustryCode" name="industryCode" value="">
                                </div>

                                <div class="col-md-3 col-form-label fw-bold">
                                    <select class="form-select" id="careerSubIndustry" name="careerSubIndustry">
                                        <option value="">상세업종</option>
                                        <c:forEach items="${industryDetailList}" var="industryDetail">
                                            <option class="${industryDetail.codeParent}"
                                                value="${industryDetail.codeCd}">
                                                ${industryDetail.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-2 col-form-label fw-bold">
                                    <select class="form-select" id="careerCompanySize" name="careerCompanySize">
                                        <option value="">기업규모</option>
                                        <c:forEach items="${companySizeList}" var="companySize">
                                            <option value="${companySize.codeCd}">${companySize.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-2 col-form-label fw-bold">
                                    <select class="form-select" id="careerCompanyType" name="careerCompanyType">
                                        <option value="">기업 형태</option>
                                        <c:forEach items="${companyTypeList}" var="companyType">
                                            <option value="${companyType.codeCd}">${companyType.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-2 col-form-label fw-bold">
                                    <select class="form-select" id="careerListed" name="careerListed">
                                        <option value="">상장 여부</option>
                                        <c:forEach items="${companyListingList}" var="companyListing">
                                            <option value="${companyListing.codeCd}">${companyListing.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-3 col-form-label fw-bold">
                                    <select class="form-select" id="careerCity" name="careerCity">
                                        <option value="">지역 선택</option>
                                        <c:forEach items="${cityList}" var="city">
                                            <option value="${city.codeCd}" data-code="${city.codeCd}">${city.codeNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" id="selectedCityCode" name="cityCode" value="">
                                </div>

                                <div class="col-md-3 col-form-label fw-bold">
                                    <select class="form-select" id="careerDistrict" name="careerDistrict">
                                        <option value="">구,군별 선택</option>
                                        <c:forEach items="${cityDetailList}" var="cityDetail">
                                            <option class="${cityDetail.codeParent}" value="${cityDetail.codeCd}">
                                                ${cityDetail.codeNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <hr>
                            <div class="row mb-3">
                                <label for="career" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">근무기간</label>
                                <div class="col-md-3">
                                    <input type="date" class="form-control" id="careerStartDate" name="careerStartDate">
                                </div>
                                <label for="career" class="col-md-1 col-form-label fw-bold">
                                    <span>~</span>
                                </label>
                                <div class="col-md-3">
                                    <input type="date" class="form-control" id="careerEndDate" name="careerEndDate">
                                    <label for="career" class="col-form-label fw-bold">
                                        <span id="ingDate" style="display: none;">현재까지</span>
                                    </label>
                                </div>
                                <label for="career" class="col-md-2 col-form-label fw-bold">
                                    <input type="checkbox" id="ingBox" name="careerTentre" value="Y"
                                        onclick="toggleEmploymentStatus()">
                                    <span>재직중</span> (<span id="ingSpan" class="fw-bold text-primary"></span>)
                                </label>

                            </div>
                            <div class="row mb-3">
                                <label for="uniProjectNm" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">담당업무</label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="careerJobTitle" name="careerJobTitle"
                                        placeholder="담당업무를 입력하세요.">
                                    <textarea class="form-control" id="careerJobDesc" name="careerJobDesc" rows="5"
                                        placeholder="아래의 예시를 참고하시어 업무내용을 자세히 입력하세요.&#10;인크루트 모바일 서비스 개편(24. 06 ~ 25. 01)&#10;- StackUp 채용정보 브라우징 서비스 개편&#10;- 브라우징 개편을 통한 UV/PV 약 20% 향상"></textarea>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="careerPosition" class="col-md-2 col-form-label fw-bold">직급/직책</label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control" id="careerPosition" name="careerPosition"
                                        placeholder="ex) 주임/대리">
                                </div>

                                <label for="careerDepartment" class="col-md-2 col-form-label fw-bold">근무부서</label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control" id="careerDepartment"
                                        name="careerDepartment" placeholder="ex) 대덕인재개발원본부 기획">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label for="careerType" class="col-md-2 col-form-label fw-bold">근무형태</label>
                                <div class="col-md-4">
                                    <select class="form-select" id="careerType" name="careerType">
                                        <option value="">선택</option>
                                        <c:forEach items="${workTypeList}" var="workType">
                                            <option value="${workType.codeCd}">${workType.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <label for="careerSalary" class="col-md-2 col-form-label fw-bold">연봉</label>
                                <div class="col-md-2">
                                    <input type="text" class="form-control" id="careerSalary" name="careerSalary"
                                        placeholder="ex) 3000">
                                </div>
                                <span class="col-md-2 col-form-label fw-bold">만원</span>
                            </div>
                            <div class="row mb-3" id="careerReasonDiv">
                                <label for="careerReason" class="col-md-2 col-form-label fw-bold">퇴사사유</label>
                                <div class="col-md-4">
                                    <select class="form-select" id="careerReason" name="careerReason">
                                        <option value="">퇴사사유</option>
                                        <c:forEach items="${resignReasonList}" var="resignReason">
                                            <option value="${resignReason.codeCd}">${resignReason.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <hr style="border: 1px solid black;">
                        </div>
                        <div align="right">
                            <button type="button" class="btn btn-primary btn-sm" id="carerrInsertBtn">이 부분만 저장</button>
                        </div>
                    </form>
                    <table class="table table-hover" id="careerTable" style="display: none;">
                        <thead>
                            <tr>
                                <th class="career-name"><strong>회사명</strong></th>
                                <th class="career-position"><strong>직급/직책</strong></th>
                                <th class="career-Title"><strong>담당업무</strong></th>
                                <th class="career-date"><strong>입사일자</strong></th>
                                <th class="career-date"><strong>퇴사일자</strong></th>
                                <th class="career-status"><strong>퇴사사유</strong></th>
                                <th class="del-status"></th>
                            </tr>
                        </thead>
                        <tbody id="careerTableBody">
                            
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="contact-tab-pane" role="tabpanel" aria-labelledby="contact-tab"
                    tabindex="0">
                    <div class="row mb-3">
                        <div class="col-12">
                            <h3 class="fw-bold mb-2">선택사항</h3>
                            <p class="text-muted small"><strong>보다 자세한 이력서로 인사담당자에게 어필하세요.</strong></p>
                        </div>
                    </div>

                    <div class="btn-group" role="group" aria-label="Basic checkbox toggle button group">
                        <input type="checkbox" class="btn-check" id="btncheck1" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btncheck1">자격증</label>

                        <input type="checkbox" class="btn-check" id="btncheck2" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btncheck2">컴퓨터 활용능력</label>

                        <input type="checkbox" class="btn-check" id="btncheck3" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btncheck3">취업우대 및 특이사항</label>

                        <input type="checkbox" class="btn-check" id="btncheck4" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btncheck4">수상경력</label>

                        <input type="checkbox" class="btn-check" id="btncheck5" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btncheck5">교육 수료사항</label>

                        <input type="checkbox" class="btn-check" id="btncheck6" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btncheck6">어학능력</label>

                        <input type="checkbox" class="btn-check" id="btncheck7" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btncheck7">해외연수 및 경험</label>

                        <input type="checkbox" class="btn-check" id="btncheck8" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btncheck8">봉사활동 및 주요활동</label>

                        <input type="checkbox" class="btn-check" id="btncheck9" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btncheck9">포트폴리오(URL)</label>
                    </div>

                    <form class="form" id="cretForm" style="display: none;">
                        <br>
                        <div class="row mb-3">
                            <div class="col-12">
                                <h3 class="fw-bold mb-2">자격증</h3>
                                <p class="text-muted small">작성된 이력서에는 최근 취득일 순으로 보여집니다.</p>
                            </div>
                        </div>
                        <hr style="border: 2px solid black;">

                        <div id="certField">
                            <div class="row mb-3">
                                <input type="hidden" id="certNo" name="certNo" value="">
                                <label for="certNm" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">자격증명</label>
                                <div class="col-md-8 position-relative">
                                    <input type="text" class="form-control" id="certInput" name="certCode" oninput="searchQualifications()" placeholder="자격증명을 입력해주세요.">
                                    <input type="hidden" id="certNm" name="certNm" value="">
                                    <ul id="certList" class="autocomplete-list">
                                        <c:forEach var="cert" items="${certList}">
                                            <li class="cert-item" data-code="${cert.codeCd}" value="${cert.codeCd}">${cert.codeNm}</li>
                                        </c:forEach>
                                    </ul>
                                </div>

                            </div>

                            <div class="row mb-3">
                                <label for="certIssuer" class="col-md-2 col-form-label fw-bold">발행처</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="certIssuer" name="certIssuer" placeholder="발행처를 입력하세요.">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="certDate" class="col-md-2 col-form-label fw-bold">취득일자</label>
                                <div class="col-md-4">
                                    <input type="date" class="form-control" id="certDate" name="certDate">
                                </div>
                                <div class="col-md-4">
                                    <select class="form-select" id="certPassCd" name="certPassCd">
                                        <option value="">합격구분</option>
                                        <c:forEach items="${cretList}" var="cret">
                                            <option value="${cret.codeCd}">${cret.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <hr style="border: 1px solid black;">
                        </div>
                        <div align="right">
                            <button type="button" class="btn btn-primary btn-sm" id="certInsertBtn">이 부분만 저장</button>
                        </div>
                        <table class="table table-hover" id="certTable">
                            <thead>
                                <tr>
                                    <th class="cert-name"><strong>자격증명</strong></th>
                                    <th class="cert-issuer"><strong>발행처</strong></th>
                                    <th class="cert-date"><strong>취득일자</strong></th>
                                    <th class="cert-PassCd"><strong>합격구분</strong></th>
                                    <th class="del-status"></th>
                                </tr>
                            </thead>
                            <tbody id="certTableBody">
                                
                            </tbody>
                        </table>
                    </form>

                    <form class="form" id="compForm" style="display: none;">
                        <br>
                        <div class="row mb-3">
                            <div class="col-12">
                                <h3 class="fw-bold mb-2">컴퓨터 활용능력</h3>
                                <p class="text-muted small">자신 있는 분야 및 활용능력을 기재하여 인사담당자에게 어필하세요.</p>
                            </div>
                        </div>
                        <hr style="border: 2px solid black;">
                        <div id="compField">
                            <div class="row mb-3">
                                <input type="hidden" id="compSkillNo" name="compSkillNo" value="">
                                <label for="compSkill" class="col-md-3 col-form-label fw-bold" data-required="true">분야
                                    및
                                    활용능력</label>
                                <div class="row mb-3">
                                    <div class="col-md-3">
                                        <select class="form-select" name="compSkillField" id="compSkillField">
                                            <option value="">분야 선택</option>
                                            <c:forEach items="${comList}" var="com">
                                                <option value="${com.codeCd}" data-code="${com.codeCd}">${com.codeNm}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <input type="hidden" id="selectedComCode" name="comCode" value="">
                                    </div>
                                    <div class="col-md-3">
                                        <select class="form-select" name="compSkillDetail" id="compSkillDetail">
                                            <option value="">선택</option>
                                            <c:forEach items="${comDetailList}" var="comDetail">
                                                <option class="${comDetail.codeParent}" value="${comDetail.codeCd}">
                                                    ${comDetail.codeNm}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <select class="form-select" name="compSkillLevel" id="compSkillLevel">
                                            <option value="">활용능력수준선택</option>
                                            <c:forEach items="${comLVList}" var="comLV">
                                                <option value="${comLV.codeCd}">${comLV.codeNm}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <hr style="border: 1px solid black;">
                        </div>
                        <div align="right">
                            <button type="button" class="btn btn-primary btn-sm" id="compInsertBtn">이 부분만 저장</button>
                        </div>
                        <table class="table table-hover" id="compTable">
                            <thead>
                                <tr>
                                    <th class="comp-compSkillField"><strong>컴퓨터 활용 분야</strong></th>
                                    <th class="comp-compSkillDetail"><strong>컴퓨터 활용 능력</strong></th>
                                    <th class="comp-compSkillLevel"><strong>활용능력수준</strong></th>
                                    <th class="del-status"></th>
                                </tr>
                            </thead>
                            <tbody id="compTableBody">
                                
                            </tbody>
                        </table>
                    </form>

                    <form class="form" id="prefForm" style="display: none;">
                        <br>
                        <div class="row mb-3">
                            <div class="col-12">
                                <h3 class="fw-bold mb-2">취업우대 및 특이사항</h3>
                                <p class="text-muted small">포함되는 항목이 있는지 확인하세요.</p>
                            </div>
                        </div>
                        <hr style="border: 2px solid black;">
                        <div id="prefField">
                            <div class="row mb-3">
                                <div class="col-md-12">
                                    <input type="hidden" id="prefNo" name="prefNo" value="">
                                    <label for="prefMilitary" class="col-md-2 col-form-label fw-bold">병역사항</label>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" class="form-check-input" name="prefMilitary" value="미필"> 미필
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" class="form-check-input" name="prefMilitary" value="면제"> 면제
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" class="form-check-input" name="prefMilitary" value="복무중">
                                        복무중
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" class="form-check-input" name="prefMilitary" value="필"> 필
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-12">
                                    <label for="prefPatriot" class="col-md-2 col-form-label fw-bold">보훈대상</label>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" class="form-check-input" name="prefPatriot" value="Y"> 보훈대상
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" class="form-check-input" name="prefPatriot" value="N"
                                            checked>
                                        보훈비대상
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-12">
                                    <label for="prefDisability" class="col-md-2 col-form-label fw-bold">장애여부</label>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" class="form-check-input" name="prefDisability" value="Y">
                                        장애인
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" class="form-check-input" name="prefDisability" value="N"
                                            checked>
                                        비장애인
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-12">
                                    <label for="prefEmploySupport" class="col-md-2 col-form-label fw-bold">고용지원금</label>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" class="form-check-input" name="prefEmploySupport" value="Y">
                                        고용지원금 대상자
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" class="form-check-input" name="prefEmploySupport" value="N"
                                            checked>
                                        고용지원금 비대상자
                                    </div>
                                </div>

                            </div>

                            <div class="row mb-3">
                                <label for="prefHobbies" class="col-md-2 col-form-label fw-bold">취미,관심</label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="prefHobbies" name="prefHobbies"
                                        placeholder="ex) 빅테이터,사용자경험,디자인">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label for="prefSkills" class="col-md-2 col-form-label fw-bold">특기</label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="prefSkills" name="prefSkills"
                                        placeholder="ex) QA, 인터넷정보검색">
                                </div>
                            </div>
                            <hr style="border: 1px solid black;">
                        </div>
                        <div align="right">
                            <button type="button" class="btn btn-primary btn-sm" id="prefInsertBtn">이 부분만 저장</button>
                        </div>
                    </form>

                    <form class="form" id="awardForm" style="display: none;">
                        <br>
                        <div class="row mb-3">
                            <div class="col-12">
                                <h3 class="fw-bold mb-2">수상경력</h3>
                                <p class="text-muted small">작성된 이력서에는 최근 수상날짜를 우선으로 보여집니다.</p>
                            </div>
                        </div>
                        <hr style="border: 2px solid black;">
                        <div id="awardField">
                            <div class="row mb-3">
                                <input type="hidden" id="awardNo" name="awardNo" value="">
                                <label for="awardTitle" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">수상내역</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="awardTitle" name="awardTitle"
                                        placeholder="ex) 대덕인재개발원 우수상">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="awardIssuer" class="col-md-2 col-form-label fw-bold">수여기관</label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control" id="awardIssuer" name="awardIssuer"
                                        placeholder="ex) 중앙일보">
                                </div>
                                <label for="awardDate" class="col-md-2 col-form-label fw-bold">수상날짜</label>
                                <div class="col-md-4">
                                    <input type="date" class="form-control" id="awardDate" name="awardDate">
                                </div>
                            </div>
                            <hr style="border: 1px solid black;">
                        </div>
                        <div align="right">
                            <button type="button" class="btn btn-primary btn-sm" id="awardInsertBtn">이 부분만 저장</button>
                        </div>
                        <table class="table table-hover" id="awardTable">
                            <thead>
                                <tr>
                                    <th class="award-awardTitle"><strong>수상내역</strong></th>
                                    <th class="award-awardIssuer"><strong>수여기관</strong></th>
                                    <th class="award-awardDate"><strong>수상날짜</strong></th>
                                    <th class="del-status"></th>
                                </tr>
                            </thead>
                            <tbody id="awardTableBody">
                                
                            </tbody>
                        </table>
                    </form>

                    <form class="form" id="eduForm" style="display: none;">
                        <br>
                        <div class="row mb-3">
                            <div class="col-12">
                                <h3 class="fw-bold mb-2">교육수료사항</h3>
                                <p class="text-muted small">작성된 이력서에는 최근 교육기간을 우선으로 보여집니다.</p>
                            </div>
                        </div>
                        <hr style="border: 2px solid black;">
                        <div id="eduField">
                            <div class="row mb-3">
                                <input type="hidden" id="eduNo" name="eduNo" value="">
                                <label for="eduTitle" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">과정명</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="eduTitle" name="eduTitle"
                                        placeholder="ex) 풀스택 개발자 양성과정">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="eduInstitution" class="col-md-2 col-form-label fw-bold">교육기관</label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control" id="eduInstitution" name="eduInstitution"
                                        placeholder="ex) 대덕인재개발원">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="eduDate" class="col-md-2 col-form-label fw-bold">교육기간</label>
                                <div class="col-md-4">
                                    <input type="date" class="form-control" id="eduSdt" name="eduSdt">
                                </div>
                                <label for="eduDate" class="col-md-1 col-form-label fw-bold">~</label>
                                <div class="col-md-4">
                                    <input type="date" class="form-control" id="eduEdt" name="eduEdt">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="eduDesc" class="col-md-2 col-form-label fw-bold">내용</label>
                                <div class="col-md-10">
                                    <textarea id="eduDesc" name="eduDesc" class="form-control"
                                        placeholder="수업내용을 자세히 입력하세요." rows="5"></textarea>
                                </div>
                            </div>
                            <hr style="border: 1px solid black;">
                        </div>
                        <div align="right">
                            <button type="button" class="btn btn-primary btn-sm" id="eduInsertBtn">이 부분만 저장</button>
                        </div>
                        <table class="table table-hover" id="eduTable">
                            <thead>
                                <tr>
                                    <th class="edu-eduTitle"><strong>과정명</strong></th>
                                    <th class="edu-eduInstitution"><strong>교육기관</strong></th>
                                    <th class="edu-eduSdt"><strong>교육기간 시작일</strong></th>
                                    <th class="edu-eduEdt"><strong>교육기간 종료일</strong></th>
                                    <th class="del-status"></th>
                                </tr>
                            </thead>
                            <tbody id="eduTableBody">
                                
                            </tbody>
                        </table>
                    </form>

                    <form class="form" id="langForm" style="display: none;">
                        <br>
                        <div class="row mb-3">
                            <div class="col-md-12">
                                <h3 class="fw-bold mb-2">어학능력</h3>
                                <p class="text-muted small">작성된 이력서에는 어학능력, 어학시험 최근 취득일자를 우선으로 보여집니다.</p>
                            </div>
                        </div>

                        <hr style="border: 2px solid black;">
                        <div id="langField">
                            <div class="row mb-3">
                                <input type="hidden" id="langNo" name="langNo" value="">
                                <label for="lang" class="col-md-2 col-form-label fw-bold">어학능력</label>
                                <div class="col-md-3">
                                    <select id="langNm" name="langNm" class="form-select">
                                        <option value="">외국어명</option>
                                        <c:forEach items="${langList}" var="lang">
                                            <option value="${lang.codeCd}">${lang.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <select id="langSpeakingLevel" name="langSpeakingLevel" class="form-select">
                                        <option value="">회화수준</option>
                                        <c:forEach items="${speakList}" var="speak">
                                            <option value="${speak.codeCd}">${speak.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="lang" class="col-md-2 col-form-label fw-bold"></label>
                                <div class="col-md-3">
                                    <select id="langReadingLevel" name="langReadingLevel" class="form-select">
                                        <option value="">독해수준</option>
                                        <c:forEach items="${readList}" var="read">
                                            <option value="${read.codeCd}">${read.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <select id="langWritingLevel" name="langWritingLevel" class="form-select">
                                        <option value="">작문수준</option>
                                        <c:forEach items="${writeList}" var="write">
                                            <option value="${write.codeCd}">${write.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <hr style="border: 1px solid black;">
                        </div>
                        <div id="langTestField">
                            <div class="row mb-3">
                                <input type="hidden" id="langTestNo" name="langTestNo" value="">
                                <label for="lang" class="col-md-2 col-form-label fw-bold">어학시험</label>
                                <div class="col-md-3">
                                    <select id="langTestName" name="langTestName" class="form-select">
                                        <option value="">공인시험명</option>
                                        <c:forEach items="${langTestList}" var="langTest">
                                            <option value="${langTest.codeCd}">${langTest.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <input type="text" class="form-control" id="langTestLevel" name="langTestLevel"
                                        placeholder="점수 및 등급 입력하세요.">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="lang" class="col-md-2 col-form-label fw-bold">취득일자</label>
                                <div class="col-md-3">
                                    <input type="date" class="form-control" id="langTestDate" name="langTestDate">
                                </div>
                            </div>
                            <hr style="border: 1px solid black;">
                        </div>
                        <div align="right">
                            <button type="button" class="btn btn-primary btn-sm" id="langInsertBtn">이 부분만 저장</button>
                        </div>
                        <table class="table table-hover" id="langTable">
                            <thead>
                                <tr>
                                    <th class="lang-langTitle"><strong>외국어명</strong></th>
                                    <th class="lang-langInstitution"><strong>회화수준</strong></th>
                                    <th class="lang-langReadingLevel"><strong>독해수준</strong></th>
                                    <th class="lang-langWritingLevel"><strong>작문수준</strong></th>
                                    <th class="del-status"></th>
                                </tr>
                            </thead>
                            <tbody id="langTableBody">
                                
                            </tbody>
                        </table>
                        <br>
                        <table class="table table-hover" id="langTestTable">
                            <thead>
                                <tr>
                                    <th class="langTest-langTestName"><strong>어학시험명</strong></th>
                                    <th class="langTest-langTestLevel"><strong>점수 및 등급</strong></th>
                                    <th class="langTest-langTestDate"><strong>취득일자</strong></th>
                                    <th class="del-status"></th>
                                </tr>
                            </thead>
                            <tbody id="langTestTableBody">
                                
                            </tbody>
                        </table>
                    </form>

                    <form class="form" id="expForm" style="display: none;">
                        <br>
                        <div class="row mb-3">
                            <div class="col-12">
                                <h3 class="fw-bold mb-2">해외연수 및 경험</h3>
                                <p class="text-muted small">작성된 이력서에는 최근 연수기간을 우선으로 보여집니다.</p>
                            </div>
                        </div>
                        <hr style="border: 2px solid black;">
                        <div id="expField">
                            <div class="row mb-3">
                                <input type="hidden" id="expNo" name="expNo" value="">
                                <label for="expCountry" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">연수국가</label>
                                <div class="col-md-3">
                                    <select id="expCountry" name="expCountry" class="form-select">
                                        <option value="">국가명선택</option>
                                        <c:forEach items="${countryList}" var="country">
                                            <option value="${country.codeCd}">${country.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="expDate" class="col-md-2 col-form-label fw-bold">연수기간</label>
                                <div class="col-md-3">
                                    <input type="date" id="expSdt" name="expSdt" class="form-control">
                                </div>
                                <div class="col-md-1">
                                    ~
                                </div>
                                <div class="col-md-3">
                                    <input type="date" id="expEdt" name="expEdt" class="form-control">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="expDesc" class="col-md-2 col-form-label fw-bold">목적 및 내용</label>
                                <div class="col-md-10">
                                    <textarea id="expDesc" name="expDesc" class="form-control"
                                        placeholder="해외연수 및 경험 관련 상세내용을 입력하세요." rows="5"></textarea>
                                </div>
                            </div>
                            <hr style="border: 1px solid black;">
                        </div>
                        <div align="right">
                            <button type="button" class="btn btn-primary btn-sm" id="expInsertBtn">이 부분만 저장</button>
                        </div>
                        <table class="table table-hover" id="expTable">
                            <thead>
                                <tr>
                                    <th class="exp-expCountry"><strong>국가명</strong></th>
                                    <th class="exp-expdt"><strong>연수 기간 시작</strong></th>
                                    <th class="exp-expdt"><strong>연수 기간 종료</strong></th>
                                    <th class="del-status"></th>
                                </tr>
                            </thead>
                            <tbody id="expTableBody">
                                
                            </tbody>
                        </table>
                    </form>

                    <form class="form" id="actForm" style="display: none;">
                        <br>
                        <div class="row mb-3">
                            <div class="col-12">
                                <h3 class="fw-bold mb-2">봉사활동 및 주요활동</h3>
                                <p class="text-muted small">자신 있는 분야 및 활용능력을 기재하여 인사담당자에게 어필하세요.</p>
                            </div>
                        </div>
                        <hr style="border: 2px solid black;">
                        <div id="actField">
                            <div class="row mb-3">
                                <input type="hidden" id="activityNo" name="activityNo" value="">
                                <label for="activity" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">기관,단체명</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="activityOrganization"
                                        name="activityOrganization" placeholder="기관, 단체명을 입력하세요.">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="activity" class="col-md-2 col-form-label fw-bold">활동내용</label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="activityDesc" name="activityDesc"
                                        placeholder="ex) 아프리카 기아 난민을 위한 봉사활동">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="row mb-3">
                                    <label for="activityDate" class="col-md-2 col-form-label fw-bold"
                                        data-required="true">활동기간</label>

                                    <div class="col-md-3">
                                        <input type="date" class="form-control" id="activitySdt" name="activitySdt">
                                    </div>
                                    <label for="activity" class="col-md-1 col-form-label fw-bold">
                                        <span>~</span>
                                    </label>
                                    <div class="col-md-3">
                                        <input type="date" class="form-control" id="activityEdt" name="activityEdt">
                                        <label for="activity" class="col-form-label fw-bold">
                                            <span id="activitySpan" style="display: none;">현재까지</span>
                                        </label>
                                    </div>

                                    <label for="activity" class="col-md-2 col-form-label fw-bold">
                                        <input type="checkbox" id="activityBox" onchange="toggleaAtivityStatus();"> 참여중
                                    </label>
                                </div>
                            </div>
                            <hr style="border: 1px solid black;">
                        </div>
                        <div align="right">
                            <button type="button" class="btn btn-primary btn-sm" id="actInsertBtn">이 부분만 저장</button>
                        </div>
                        <table class="table table-hover" id="actTable">
                            <thead>
                                <tr>
                                    <th class="act-activityOrganization"><strong>기관,단체명</strong></th>
                                    <th class="act-actdt"><strong>연수 기간 시작</strong></th>
                                    <th class="act-actdt"><strong>연수 기간 종료</strong></th>
                                    <th class="del-status"></th>
                                </tr>
                            </thead>
                            <tbody id="actTableBody">
                                
                            </tbody>
                        </table>
                    </form>

                    <form class="form" id="porForm" style="display: none;">
                        <br>
                        <div class="row mb-3">
                            <div class="col-12">
                                <h3 class="fw-bold mb-2">포트폴리오(URL)</h3>
                                <p class="text-muted small">이력서에 첨부할 포트폴리오(URL)를 선택하세요.</p>
                            </div>
                        </div>
                        <hr style="border: 2px solid black;">
                        <div class="row mb-3">
                            <div class="col-12">
                                <div class="shadow-none p-3 mb-5 bg-light rounded">
                                    <strong>01.</strong> 첨부한 포트폴리오는 자동으로 공개됩니다.
                                    <br>
                                    <strong>02.</strong> 항목 삭제는 개인 회원 홈> 이력서/자소서> <strong>[이력서 관리]</strong>메뉴에서 가능합니다.
                                </div>
                            </div>
                        </div>
                        <hr style="border: 1px solid black;">
                        <div id="portContainer" class="portContainer">
                        
                        </div>
                        <hr style="border: 1px solid black;">
                        <div align="right">
                            <button type="button" class="btn btn-secondary btn-sm" id="urlAddBtn" data-bs-toggle="modal"
                                data-bs-target="#urlModal">
                                URL 추가
                            </button>
                        </div>
                    </form>
                    <!-- 모달창 -->
                    <div class="modal fade" id="urlModal" tabindex="-1" aria-labelledby="urlModalLabel"
                        aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="urlModalLabel">URL 추가</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form>
                                        <div class="mb-3">
                                            <label for="url-type" class="form-label">구분</label>
                                            <select class="form-select" id="portNm" name="portNm">
                                                <option value="">종류선택</option>
                                                <c:forEach items="${URLList}" var="URL">
                                                    <option value="${URL.codeCd}" data-code="${URL.codeCd}">
                                                        ${URL.codeNm}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label for="url-input" class="form-label">URL</label>
                                            <input type="text" class="form-control" id="portUrl" name="portUrl" placeholder="http://" value="http://">
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                                    <button type="button" class="btn btn-primary" id="urlBtn">등록</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="disabled-tab-pane" role="tabpanel" aria-labelledby="disabled-tab"
                    tabindex="0">
                    <form class="form">
                        <div class="row mb-3">
                            <div class="col-12">
                                <h3 class="fw-bold mb-2">희망근무조건</h3>
                                <p class="text-muted small">희망근무조건을 설정하시면 기업 인사담당자에게 면접제의를 받으실 수 있습니다.</p>
                            </div>
                        </div>
                        <hr style="border: 2px solid black;">
                        <div id="workField">
                            <div class="row mb-3">
                                <label for="Work_Cond" class="col-md-2 col-form-label fw-bold" data-required="true"
                                    id="workLable">근무지역</label>
                                <label for="Work_Cond" class="col-md-2 col-form-label fw-bold" id="workLableFake"
                                    style="display: none;"></label>
                                <div class="col-3">
                                    <input type="hidden" id="workCityNo" name="workCityNo" value="">
                                    <select class="form-select" id="sidoCd" name="sidoCd">
                                        <option value="">지역선택</option>
                                        <c:forEach items="${workList}" var="work">
                                            <option value="${work.codeCd}" data-code="${work.codeCd}">${work.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" id="selectedWorkCode" name="workCode" value="">
                                </div>
                                <div class="col-3">
                                    <select class="form-select" id="gugunCd" name="gugunCd">
                                        <option value="">구,군별선택</option>
                                        <c:forEach items="${workDetailList}" var="workDetail">
                                            <option class="${workDetail.codeParent}" value="${workDetail.codeCd}">${workDetail.codeNm}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-4 d-flex justify-content-end align-items-center">
								    <button type="button" class="btn btn-secondary" id="workAddBtn">추가</button>
								    <button type="button" id="workDelBtn" class="btn btn-secondary ms-2" style="display: none;">삭제</button>
								</div>
                            </div>
                        </div>
                        <div id="workFieldList"></div>
                        <div class="row mb-3">
                            <label for="Work_Cond" class="col-md-2 col-form-label fw-bold"></label>
                            <div class="col-10">
                                <p class="text-muted small"><strong>※</strong> 최대 5개까지 설정하실 수 있습니다.</p>
                            </div>
                        </div>
                        <div id="workCondField">
                            <div class="row mb-3">
                                <input type="hidden" id="workCondNo" name="workCondNo" value="">
                                <label for="Work_Cond" class="col-md-2 col-form-label fw-bold">재택근무</label>
                                <div class="col-md-2">
                                    <input type="checkbox" id="workCondRemote" name="workCondRemote" value="Y">
                                    <label for="workCondRemote" class="col-form-label fw-bold ms-2">재택근무 가능</label>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="Work_Cond" id="workLabel" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">직종<span id="jobTypeCount">(0/5)</span></label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="workCondJobType" name="workCondJobType"
                                        placeholder="ex) 워드, 웹개발">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="Work_Cond" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">근무형태<span id="workTypeCount">(0/3)</span></label>
                                <div class="col-md-10">
                                    <c:forEach items="${workTypeList}" var="workType">
									    <label class="form-check-label me-3">
									        <input type="checkbox" id="workCondType" name="workCondType" value="${workType.codeCd}">
									        <span class="fw-bold">${workType.codeNm}</span>
									    </label>
									</c:forEach>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="Work_Cond" class="col-md-2 col-form-label fw-bold"
                                    data-required="true">희망연봉</label>
                                <div class="col-md-4">
                                    <select class="form-select" id="workCondSalary" name="workCondSalary">
                                        <option value="">희망연봉을 선택하세요</option>
                                        <c:forEach items="${salaryList}" var="salary">
                                            <option class="${salary.codeParent}" value="${salary.codeCd}">
                                                ${salary.codeNm}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <input type="checkbox" id="workCondSalaryVisible" name="workCondSalaryVisible"
                                        value="Y">
                                    <label for="hqBtn" class="col-form-label fw-bold ms-2">비공개</label>
                                </div>
                            </div>
                        </div>
                    </form>
                    <hr style="border: 1px solid black;">
                </div>
            </div>


            <script src="${pageContext.request.contextPath}/resources/js/resume/resumeFormList.js"></script>
            <script src="${pageContext.request.contextPath}/resources/js/resume/resumeForm.js"></script>
            <script src="${pageContext.request.contextPath}/resources/js/resume/resumeFormClone.js"></script>
            <script src="${pageContext.request.contextPath}/resources/js/resume/resumeFormSel.js"></script>
            <script src="${pageContext.request.contextPath}/resources/js/resume/resumeFormDate.js"></script>