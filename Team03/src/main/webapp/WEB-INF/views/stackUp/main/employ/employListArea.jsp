<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/scrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/employ/employList.css" />
<c:forEach var="emp" items="${employList}" varStatus="sttus">
	<c:if test="${sttus.first}">
		<div>${emp.totalCnt} 건 검색</div>
	</c:if>
    <ul class="view-ul">
        <li class="view-li">
            <div class="view-first">
                <div class="tab-company">
                    <span>${emp.compNm}</span>
                    <button class="inter-btn" data-pk-no="${emp.compId}"
                            data-scrap-status="${emp.interCompDel}">
                        <i class="bi
                           ${empty emp.interCompDel || emp.interCompDel eq 'Y'
                               ? 'bi-heart'
                               : 'bi-heart-fill'}">
                        </i>
                    </button>
                </div>
            </div>
            <div class="view-mid">
                <div class="tab-subject">
                    <a href="<c:url value='/employ/detail/${emp.employNo}'/>">${emp.employTitle}</a>
                    <button class="scrap-btn" data-pk-no="${emp.employNo}"
                            data-scrap-status="${emp.empscrapDel}">
                        <i class="bi
                           ${empty emp.empscrapDel || emp.empscrapDel eq 'Y'
                               ? 'bi-star'
                               : 'bi-star-fill'}">
                        </i>
                    </button>
                </div>
                <div class="tab-jobs">
                    <!-- 경력 (employExperience) 콤마 구분 -->
                    <c:forTokens var="exp" items="${emp.employExperience}"
                                 delims="," varStatus="sttus">
                        <em>
                            <c:choose>
                                <c:when test="${sttus.first and exp eq 'mt01'}">
                                    신입 /
                                </c:when>
                                <c:when test="${exp eq 'mt01'}">
                                    신입
                                </c:when>
                                <c:when test="${exp eq 'mt02'}">
                                    경력
                                </c:when>
                                <c:when test="${exp eq 'all'}">
                                    (경력무관)
                                </c:when>
                            </c:choose>
                        </em>
                    </c:forTokens>

                    <em>&nbsp;|&nbsp;</em>
                    <em>${emp.employEducationNm}</em>
                    <em>&nbsp;|&nbsp;</em>

                    <!-- 모집분야 중 첫 번째만 표기 -->
                    <c:forEach var="field" items="${emp.fieldList}" varStatus="sttus">
                        <c:if test="${sttus.first}">
                            <em>${field.filedRegionNm}&nbsp;${field.filedRegionGunguNm}</em>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="view-last">
                <c:choose>
                    <c:when test="${emp.employDday > 0}">
                        <span class="d-day">D-${emp.employDday}</span>
                    </c:when>
                    <c:when test="${emp.employDday == 0}">
                        <span class="d-day">오늘 마감</span>
                    </c:when>
                </c:choose>

                <c:if test="${emp.employApplication eq 'APLC01'}">
                    <button class="btn btn-primary btn-lg apply-btn stackUp-btn" data-bs-toggle="modal" data-bs-target="#apply-modal${emp.employNo}">
						바로 지원
					</button>
                </c:if>
            </div>
        </li>
    </ul>
	<!--공고 지원 Modal-->
		<div class="modal fade" id="apply-modal${emp.employNo}" tabindex="-1" aria-labelledby="apply-modal" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="modal-title fs-5">${emp.compNm} 입사지원</h1>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<label for="fieldSelect${emp.employNo}">모집 분야</label>
						<select id="fieldSelect${emp.employNo}" class="form-select">
							<option value="">모집 분야 선택</option>
							<c:forEach var="field" items="${emp.fieldList}">
								<option value="${field.filedNo}">${field.filedNm}</option>
							</c:forEach>
						</select>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary submit-btn" data-employNo="${emp.employNo}">바로지원</button>
					</div>
				</div>
			</div>
		</div>
</c:forEach>

<c:if test="${empty employList}">
    <div>등록된 채용공고가 없습니다.</div>
</c:if>

<!-- 페이징 영역 -->
<div class="paging-area">
    ${pagingHTML}
</div>
<input type="hidden" id="loginCompany" value="${loginCompany}">
<input type="hidden" id="loginMember" value="${loginMember}">
<script>
	const endDateStr = `${employ.employEd}`;
</script>
<script src="${pageContext.request.contextPath}/resources/js/employ/employList.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/scrap.js"></script>