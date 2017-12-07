<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/jsp/common/setting.jsp"%>
<html>
	
	<link type="text/css" rel="stylesheet" href="/bookstore/css/board.css">
	<style type="text/css">
		.wrap .container{float:left;width:800px;margin:0 auto;}
		.wrap h3{margin:20px 0 20px 50px;font-size:14px;font-weight:bold}
		#chart{text-align:center;}
		.item{text-align:center;margin:0 0 50px 50px;}
		.item li{border:1px solid #c6c6c6;font-size:14px;margin:0;padding:8px;}
		.chart{width:800px;height:400px;}
		#firstChart{padding:10px;}
	</style>
	
	<!-- 
		Google Chart 
		Chart의 width, height을 options에서 주었을 때 처리 속도가 저하되고 제대로 나오지 않았음.
	-->
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
		google.charts.load('current', {packages:['corechart', 'bar']});
	</script>

<body onload="navFocus('rs');">
<%@ include file="header.jsp" %>

<section>
<div class="wrap clearfix">
	<%@ include file="/jsp/host/stockNav.jsp" %>
	
	<div class="container">
		<h3>결산 내역</h3>
		<ul class="item clearfix">
			<li>총 판매량 ${total}권</li>
			<li>총 매출액 <fmt:formatNumber value="${totalSale}" type="number"/>원</li>
			<li>${year}년도 판매량 <fmt:formatNumber value="${currYearCount}" type="number"/>권</li>
			<li>${year}년도 매출액 <fmt:formatNumber value="${currYearAmount}" type="number"/>원</li>
		</ul>
		
		<div class="chart" id="firstChart">firstChart</div>
			<!-- 그래프 요소 값 -->
			<c:set var="newS" value="${tagMap['newS']}"/> <!-- MapName['key'] : value -->
			<c:set var="bestS" value="${tagMap['bestS']}"/>
			<c:set var="goodS" value="${tagMap['goodS']}"/>
			<c:set var="nullS" value="${tagMap['nullS']}"/>
			
			<c:set var="newR" value="${tagRefund['newR']}"/>
			<c:set var="bestR" value="${tagRefund['bestR']}"/>
			<c:set var="goodR" value="${tagRefund['goodR']}"/>
			<c:set var="nullR" value="${tagRefund['nullR']}"/>
		
			<script type="text/javascript">
				//로딩 완료시 함수 실행하여 차트 생성 
				google.charts.setOnLoadCallback(drawfirstChart);
				
				function drawfirstChart() {
					var data = google.visualization.arrayToDataTable([
						['메인 도서', '판매량', '환불량'],	//그래프 요소 설명
						['베스트도서', ${bestS}, ${bestR}],		//그래프 막대 요소
						['신간도서', ${newS}, ${newR}],
						['추천도서', ${goodS}, ${goodR}],
						['그외', ${nullS}, ${nullR}]
					]);

					var options = {	//그래프 설정
						chart: {
							title	 : '올해 메인 도서 판매량 & 환불량',		//그래프 제목
							subtitle : '올해 메인 도서의 판매된 도서수와 환불된 도서수'//,
						}
					};
					
									//new google.visualization.ChartName(); 차트 유형
					var chart = new google.charts.Bar(document.getElementById('firstChart'));
					chart.draw(data, google.charts.Bar.convertOptions(options)); //그래프에 표현될 데이터, 차트 옵션
				}
			</script>
		
		
		<div class="chart" id="secontChart">secontChart</div>
			<c:if test="${y2017 == null}"><c:set var="y2017" value="0"/></c:if>
			<c:if test="${y2016 == null}"><c:set var="y2016" value="0"/></c:if>
			<c:if test="${y2015 == null}"><c:set var="y2015" value="0"/></c:if>
			<c:if test="${yr2017 == null}"><c:set var="yr2017" value="0"/></c:if>
			<c:if test="${yr2016 == null}"><c:set var="yr2016" value="0"/></c:if>
			<c:if test="${yr2015 == null}"><c:set var="yr2015" value="0"/></c:if>
			
			<script type="text/javascript">
				google.charts.setOnLoadCallback(drawSecontChart);
				
				function drawSecontChart() {
					var data = google.visualization.arrayToDataTable([
						['Element', '매출액', '환불액'],
						['2015', ${y2015}, ${yr2015}],
						['2016', ${y2016}, ${yr2016}],
						['2017', ${y2017}, ${yr2017}]
					]);
					
					var options = {
							title : '연간 매출액',
							curveType : 'function',
							legend : { position : 'bottom' }
					};
					
					var chart = new google.visualization.LineChart(document.getElementById('secontChart'));
					chart.draw(data, options);
				}
			</script>
		
		<div class="chart" id="thirdChart"></div>
			<c:if test="${t1good == null}"><c:set var="t1good" value="0"/></c:if>
			<c:if test="${t1null == null}"><c:set var="t1null" value="0"/></c:if>
			<c:if test="${t1new == null}"><c:set var="t1new" value="0"/></c:if>
			<c:if test="${t1best == null}"><c:set var="t1best" value="0"/></c:if>
			<c:if test="${t2good == null}"><c:set var="t2good" value="0"/></c:if>
			<c:if test="${t2null == null}"><c:set var="t2null" value="0"/></c:if>
			<c:if test="${t2new == null}"><c:set var="t2new" value="0"/></c:if>
			<c:if test="${t2best == null}"><c:set var="t2best" value="0"/></c:if>
			<c:if test="${t3good == null}"><c:set var="t3good" value="0"/></c:if>
			<c:if test="${t3null == null}"><c:set var="t3null" value="0"/></c:if>
			<c:if test="${t3new == null}"><c:set var="t3new" value="0"/></c:if>
			<c:if test="${t3best == null}"><c:set var="t3best" value="0"/></c:if>
			<c:if test="${t4good == null}"><c:set var="t4good" value="0"/></c:if>
			<c:if test="${t4null == null}"><c:set var="t4null" value="0"/></c:if>
			<c:if test="${t4new == null}"><c:set var="t4new" value="0"/></c:if>
			<c:if test="${t4best == null}"><c:set var="t4best" value="0"/></c:if>
			<c:if test="${t4best == null}"><c:set var="t4best" value="0"/></c:if>
			
			<script type="text/javascript">
				google.charts.setOnLoadCallback(drawThirdChart);
	
				function drawThirdChart() {
					var data = google.visualization.arrayToDataTable([
						['Element', '베스트도서', '신간도서', '추천도서', '그외'],
						['1주', ${t1best}, ${t1new}, ${t1good}, ${t1null}],
						['2주', ${t2best}, ${t2new}, ${t2good}, ${t2null}],
						['3주', ${t3best}, ${t3new}, ${t3good}, ${t3null}],
						['4주', ${t4best}, ${t4new}, ${t4good}, ${t4null}]	
					]);
	
					var options = {
						title : '주간 메인 도서별 매출액',
						curveType : 'function',
						legend : {position : 'buttom'}
					};
		
					var chart = new google.visualization.LineChart(document.getElementById('thirdChart'));
					chart.draw(data, options);
				} 
			</script>
	</div>
</div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>