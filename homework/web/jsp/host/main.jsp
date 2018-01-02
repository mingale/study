<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewpost" content="width=device-width initial-scale=1">

	<title>관리자</title>
	
	<link rel="stylesheet" href="../../css/reset.css">
	<link rel="stylesheet" href="../../css/common.css">
	<style>
		/* layout */
		section{padding:10px;}
		.wrap > div > ul{border:1px solid #c6c6c6;}
		.new_order{float:left;width:48%;height:500px;}
		.new_add{float:right;width:48%;height:500px;}
		.notice{float:right;width:100%;}
		
		.item > li{width:100%;padding-left:10px;font-size:14px;}
		.item li li{float:left;margin:5px 0;}

		.number{width:25%;}
		.date{width:25%;}
		.name{width:20%;}
		.state{width:15%;}
		.total{width:15%;}
	</style>
</head>
<body>

<%@ include file="header.jsp" %>

<section>
<div class="wrap clearfix">
	<div class="new_order">
		<h3>최근 주문</h3>
		<ul class="item clearfix">
			<li>
				<ul class="clearfix">
					<li class="number">주문번호</li>
					<li class="date">주문일</li>
					<li class="name">주문자</li>
					<li class="state">상태</li>
					<li class="total">합계</li>
				</ul>
			</li>
			<li>
				<ul class="clearfix">
					<li class="number">20171103</li>
					<li class="date">2017.11.03</li>
					<li class="name">홍길동</li>
					<li class="state">입금중</li>
					<li class="total">10000</li>
				</ul>
			</li>
		</ul>
	</div>

	<div class="new_add">
		<h3>최근 입고</h3>
		<ul class="item clearfix">
			<li>
				<ul class="clearfix">
					<li class="number">주문번호</li>
					<li class="date">주문일</li>
					<li class="name">도서</li>
					<li class="state">상태</li>
					<li class="total">합계</li>
				</ul>
			</li>
			<li>
				<ul class="clearfix">
					<li class="number">20171103</li>
					<li class="date">2017.11.03</li>
					<li class="name">자바</li>
					<li class="state">입금중</li>
					<li class="total">10000</li>
				</ul>
			</li>
		</ul>
	</div>
	
	<div class="notice">
		<h3>공지사항</h3>
		<hr>
		
		<p>배고픔</p>
	</div>
</div>
</section>

<%@ include file="footer.jsp" %>

</body>
</html>