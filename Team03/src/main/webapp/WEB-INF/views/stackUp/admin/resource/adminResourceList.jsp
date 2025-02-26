<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/admin/resource/adminResourceList.css"> 

	<div>
		<h2>한 번에 합격하는 이력서 양식</h2>
		<p>최신 업데이트 되는 기업/직무별 이력서양식!</p>
	</div>
		<div class="search-area d-flex justify-content-end">
			 <!-- 총 게시물 수 표시 -->
				<form:select path="condition.searchType" class="form-select w-auto me-2">
					<form:option value="resourceTitle" label="제목"></form:option>
				</form:select>
				<form:input path="condition.searchWord" class="form-control w-auto me-2" placeholder="검색어 입력"/>
				<button type="submit" class="btn btn-primary search-btn" >검색</button>
		</div>
	<%-- <div class="total-count">
        <p>총 ${totalCount} 건</p>  
    </div> --%>
<hr>
		<a href="${pageContext.request.contextPath }/admin/resource/adminResourceForm" class="btn btn-primary me-2" >📄 새 양식 등록</a>
		
		
		<!--모달-->
  <button type="button" class="btn btn-primary" data-bs-toggle="modal"
    data-bs-target="#exampleModalLong">
     📈 다운로드 수 차트
  </button>
  <!--modal-->
  <div class="modal fade" id="exampleModalLong" tabindex="-1"
    aria-labelledby="exampleModalLongLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLongLabel">📈 이력서 다운로드 수</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal"
            aria-label="Close"></button>
        </div>
        <div class="modal-body" style="min-height: 450px;">
          <p>다운로드 차트 </p>
          <canvas id="downloadChart" width="50" height="50"></canvas>
          		<c:forEach items="${adminResourceList}" var="resource" varStatus="status">
		                <a href="<c:url value='/admin/resource/${resource.resourceNo}'/>" class="glightbox rounded-3">
		        		</a>
		        </c:forEach>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        </div>
      </div>
    </div>
  </div>
  
<br>
<br>
	<div class="row gy-3">
    <c:if test="${not empty adminResourceList}">
        <c:forEach items="${adminResourceList}" var="resource" varStatus="status">
            <div class="col-lg-3 ${status.index < 3 && (param.page == 1 || param.page == null) ? 'best-highlight' : ''}">
                <a href="<c:url value='/admin/resource/${resource.resourceNo}'/>" class="glightbox rounded-3">
                    <div class="rounded-3 card-lift">
                        <img src='<c:url value="/images/resource/${resource.file.fileDetails[0].streFileNm}" />' class="custom-image-style">
                    </div>
                </a>
                <c:choose>
                    <c:when test="${status.index < 3 && (param.page == 1 || param.page == null)}">
                       <!--  <span class="best-badge">👍Best</span> Best 문구 -->
                    </c:when>
                </c:choose>
                <a class="resource-title" href="<c:url value='resource/${resource.resourceNo}'/>">${resource.resourceTitle}</a>
            </div>
        </c:forEach>
    </c:if>
	
	</div>

	<div class="paging-area">
		${pagingHTML}
	</div>

	<form id="searchForm" style="display:none">
		<form:input path="condition.searchType" placeholder="searchType"/>
		<form:input path="condition.searchWord" placeholder="searchWord"/>
		<input type="text" name="page" placeholder="page"/>
	</form>
	
	
<script>
    document.addEventListener("DOMContentLoaded", function() {
        // 다운로드 수 데이터를 JSP에서 JavaScript로 전달
        const labels = [];
        const data = [];
        const backgroundColors = [];
        let randomColor;
        <c:forEach items="${adminResourceList}" var="resource">
	        labels.push("${resource.resourceTitle}");  // 이력서 제목
	        data.push(${resource.fileDwncnt});  // 다운로드 수
	
	        // 각 항목에 대해 색상 추가 (임의로 색상 지정)
	        randomColor = 'rgba(' + Math.floor(Math.random() * 256) + ',' + Math.floor(Math.random() * 256) + ',' + Math.floor(Math.random() * 256) + ', 0.5)';
	        backgroundColors.push(randomColor);  // 각 항목에 다른 색상을 넣기
	    </c:forEach>

        // 차트를 생성하는 함수
        const ctx = document.getElementById('downloadChart').getContext('2d');
	    new Chart(ctx, {
	        type: 'bar',  // 원하는 차트 유형: bar, line, pie 등
	        data: {
	            labels: labels,  // 이력서 제목
	            datasets: [{
	                label: '다운로드 수',
	                data: data,  // 다운로드 수 데이터
	                backgroundColor: backgroundColors,  // 각 데이터 항목에 다른 색상 적용
	                borderColor: 'rgba(75, 192, 192, 1)',  // 테두리 색상
	                borderWidth: 1
	            }]
	        },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true,
                        position: 'top',
                    },
                    tooltip: {
                        enabled: true,
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true // y축 시작을 0으로 설정
                    }
                }
            }
        });
    });
</script>

<script src="${pageContext.request.contextPath }/resources/js/admin/resource/adminResourceList.js"></script>



