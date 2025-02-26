<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

<form:form modelAttribute="benefit">
	<c:if test="${benefit[0].cmpbftNo == null}">
		<c:if test="${not empty benefit}">
		</c:if>
		<h2>복리후생 작성하기</h2>
	</c:if>

	<c:if test="${benefit[0].cmpbftNo != null}">
		<div>
			복리후생 정보 <a class="btn btn-light btn-sm"
				href='<c:url value="/benefit/${benefit[0].compId }/detail" />'>
				복리후생 작성 (링크연결) </a>
		</div>
		<c:forEach var="bft" items="${benefit}">
			<div class="bftItem" data-cmpbft-no="${bft.cmpbftNo}"
				data-bs-toggle="modal" data-bs-backdrop="static"
				data-bs-target="#staticBackdrop">
				<ul>
					<li hidden="hidden">복리후생 번호: ${bft.cmpbftNo}</li>
					<li>복리후생 제목: ${bft.cmpbftTile}</li>
					<li>복리후생 내용: ${bft.cmpbftCont}</li>
				</ul>
			</div>
			<hr />
		</c:forEach>
	</c:if>

</form:form>

<form:form modelAttribute="benefit">
	<c:if test="${benefit[0].cmpbftNo == null}">
		<h2>복리후생 작성하기</h2>
	</c:if>

	<c:if test="${benefit[0].cmpbftNo != null}">
		<div>
			복리후생 정보 <a class="btn btn-light btn-sm"
				href='<c:url value="/benefit/${benefit[0].compId }/detail" />'>
				복리후생 작성 (링크연결) </a>
		</div>

		<!-- 체크박스 목록 -->
		<fieldset>
			<div>
				<h2>복리후생 목록</h2>
				<c:forEach var="bft" items="${benefit}">
					<div >
						<label> <input type="checkbox" name="selectedBenefits"
							value="${bft.cmpbftNo}"> ${bft.cmpbftTile} -
							${bft.cmpbftCont}
						</label>
					</div>
				</c:forEach>
			</div>
		</fieldset>
	</c:if>
</form:form>






<!--modal compoments-->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static"
	data-bs-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="staticBackdropLabel"></h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>

			<c:url value="/benefit/update" var="update" />
			<form:form method="post" action="${update }"
				modelAttribute="benefitVO">
				<input type="hidden" name="_method" value="put" />
				<div class="modal-body">
					<div>
						<form:input id="bftNo" path="cmpbftNo" type="hidden" />
						<form:input id="compId" path="compId" value="compId" type="hidden" />
						<label>제목</label>
						<form:input id="bftTitle" path="cmpbftTile" type="text" />
					</div>
					<div>
						<label>내용</label>
						<form:textarea id="bftcont" path="cmpbftCont"
							placeholder="내용을 입력 하세요" />
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">작성 취소</button>
					<a id="deletebtn" class="btn btn-danger"> 삭제 </a>
					<button type="submit" class="btn btn-primary">작성</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script src="../resources/js/benefit/benefitList.js">
	
</script>
