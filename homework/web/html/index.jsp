<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>동네 서점</title>

	<link rel="stylesheet" href="../css/reset.css">
	<link rel="stylesheet" href="../css/common.css">
	<style>
		.content{width:900px;margin:0 auto;padding-bottom:50px;}
		.content li{float:left;padding:0 30px;}
		.content img{width:110px;height:170px;background-color:#f0ffff;}
		
		.slider{background-color:#f0ffff;margin-bottom:50px;}
	</style>
</head>
<body>

<%-- <%@include file= %> --%>
<section>
<div class="wrap">
	<div class="slider">슬라이드</div>

	<div class="container">
		<div class="content">
		<h3>베스트 셀러</h3>
		<hr>
			<ul class="clearfix">
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
			</ul>
		</div>

		<div class="content">
		<h3>신간 도서</h3>
		<hr>
			<ul class="clearfix">
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
			</ul>
		</div>

		<div class="content">
		<h3>추천 도서</h3>
		<hr>
			<ul class="clearfix">
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
				<li><a href=""><img src=""><br>도서1<br>가격</a></li>
			</ul>
		</div>
	</div>
</div>
</section>

<aside>
<div class="wrap">
	최근 본 도서
	<div id="quick_area">
		<a href=""><img src=""></a>
	</div>
	
	<input type="button" id="up" value="TOP"><br>
	<input type="button" id="down" value="DOWN">
</div>
</aside>

<footer>
<nav>
	<a href="">회사소개</a>
	<a href="">이용약관</a>
	<a href="">개인정보처리방침</a>
	<a href="">광고안내</a>
	<a href="">서비스전체</a>
</nav>

<div class="wrap clearfix">
	<div class="f_left">
		<ul>
			<li>상호 : 동네 서점</li>
			<li>대표이사 : 홍길동</li>
			<li>사업자등록번호 : 201-71-03120</li>
			<li>대표전화 : 1544-7979 (발신자 부담전화)</li>
			<li>팩스 : 0505-112-1711 (지역번호공통)</li>
			<li>개인정보보호최고책임자 : 춘향이 cnsgiddl@book.co.kr</li>
		</ul>
	</div>

	<div class="f_right">
		<p>구매안전 서비스(고객님은 안전거래를 위해 현금으로 5만원 이상 결제시 구매자가 보호를 받을 수 있는 구매안전 서비스를(쇼핑몰보증보험) 이용하실수 있습니다.)</p>
	</div>
</div>
</footer>
</body>
</html>
<!-- 
float layout을 사용하면 부모에게 clearfix 주기.
inline-block은 되도록 사용하지 않기!
-->