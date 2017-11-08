<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>동네 서점</title>

	<link rel="stylesheet" href="../../css/reset.css">
	<link rel="stylesheet" href="../../css/common.css">
	<style>
		.content{width:900px;margin:0 auto;padding-bottom:50px;}
		.content li{float:left;padding:0 30px;}
		.content img{width:110px;height:170px;background-color:#f0ffff;}
		
		.slider{background-color:#f0ffff;margin-bottom:50px;}
	</style>
</head>
<body>

<%@ include file="../common/header.jsp" %>

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

<%@include file="../common/footer.jsp" %>

</body>
</html>
<!-- 
float layout을 사용하면 부모에게 clearfix 주기.
inline-block은 되도록 사용하지 않기!
-->
<!--
style과 script는 따로 파일 만들어서 작성하기. html 내에서 하는 건 인라인이라 좋지 않음
-->
<!-- 
정적 include와 동적 include
https://m.blog.naver.com/PostView.nhn?blogId=elren&logNo=220923789050&proxyReferer=https%3A%2F%2Fwww.google.co.kr%2F
차이점: 동적 include가 빠르다. 변수가 겹쳐 문제가 발생할 수 있다.
http://12bme.tistory.com/135
-->