<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/jsp/common/setting.jsp"%>
<html>
<head>
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
		head가 없으면  Uncaught Error: one or more fonts could not be loaded 발생.
		[Google Charts API for some visualizations requires a <head></head> element on the page. When it goes to load fonts - they are necessary whether you have instructed it to or not - it tries to append some font references to your header, but since your head is missing, it throws that error and then renders nothing in the chart's place. It is easily fixed by adding a head element right after your HTML tag.]
		[Google Charts API는 글꼴을 참조할 때 head를 이용하기 때문에, 만일 head가 빠져 있으면 오류가 발생하고 차트에 아무것도 렌더링하지 않는다.]
	-->
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
		google.charts.load('current', {packages:['corechart', 'bar']});
	</script>
</head>
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
		
		<%-- <div class="chart" id="firstChart">firstChart</div>
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
			</script> --%>
		
		
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
			<script type="text/javascript">
				google.charts.setOnLoadCallback(drawThirdChart);
	
				function drawThirdChart() {
					var data = google.visualization.arrayToDataTable([
						['Element', '소설','장르소설','경제/경영','자기계발','시/에세이','인문','외국어','정치/사회','역사/문화','자연과학/공학','컴퓨터/인터넷','건강','가정/육아','요리','취미/실용/스포츠','여행','예술/대중문화','잡지','종교','취업/수험서','외국도서','청소년','유아' ,'아동','만화','eBook','e-오디오북'],
						['1주', ${t11},${t12},${t13},${t14},${t15},${t16},${t17},${t18},${t19},${t110},${t111},${t112},${t113},${t114},${t115},${t116},${t117},${t118},${t119},${t120},${t121},${t122},${t123},${t124},${t125},${t126},${t127}],
						['2주', ${t21},${t22},${t23},${t24},${t25},${t26},${t27},${t28},${t29},${t210},${t211},${t212},${t213},${t214},${t215},${t216},${t217},${t218},${t219},${t220},${t221},${t222},${t223},${t224},${t225},${t226},${t227}],
						['3주', ${t31},${t32},${t33},${t34},${t35},${t36},${t37},${t38},${t39},${t310},${t311},${t312},${t313},${t314},${t315},${t316},${t317},${t318},${t319},${t320},${t321},${t322},${t323},${t324},${t325},${t326},${t327}],
						['4주', ${t41},${t42},${t43},${t44},${t45},${t46},${t47},${t48},${t49},${t410},${t411},${t412},${t413},${t414},${t415},${t416},${t417},${t418},${t419},${t420},${t421},${t422},${t423},${t424},${t425},${t426},${t427}]	
					]);
	
					var options = {
						title : '주간 분야별 매출액',
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