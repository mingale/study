<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewpost" content="width=device-width initial-scale=1">

	<title>관리자 - 주문 관리</title>

	<link rel="stylesheet" href="../../css/reset.css">
	<link rel="stylesheet" href="../../css/a_common.css">
	<link rel="stylesheet" href="../../css/a_board.css">
	<link rel="stylesheet" href="../../css/paging.css">
	
	<style>
		.number{width:80px;}
		.date{width:80px;}
		.user{width:50px;}
		.title{width:235px;}
		.state{width:100px;}
		.item_btn_area{width:100px;}
		
		#all_ok{margin-left:10px;margin-right:3px;}
	</style>
</head>
<body>

<%@ include file="header.jsp" %>

<section>
<div class="wrap clearfix">
	<nav class="sub_nav">
		<a href="">주문 목록</a><!-- 주문 요청, 주문 결제 완료 -->
		<a href="">환불 목록</a><!-- 결제 취소 / 환불 요청 -->
		<a href="">교환/반품</a>
		<a href="">배송 현황</a>
		<a href="">결산 내역</a><!-- 결제완료, 배송완료, 주문후 14일 경과 -->
	</nav>

	<div class="container clearfix">
		<ul class="content">
			<li>
				<ul class="item zero clearfix">
					<li class="chk">선택</li>
					<li class="number">주문번호</li>
					<li class="date">주문일</li>
					<li class="user">주문자</li>
					<li class="title">도서명</li>
					<li class="state">상태</li>
					<li class="item_btn_area">비고</li>
				</ul>
			</li>
			<li>
				<ul class="item clearfix">
					<li class="chk"><input type="checkbox" id="book1" class="list"></li>
					<li class="number">20171106</li>
					<li class="date">2017.11.03</li>
					<li class="user"><a href="">홍길동</a></li>
					<li class="title"><a href="">도서1</a></li>
					<li class="state">결제 요청 중</li>
					<li class="item_btn_area">
						<input type="button" id="btn_ok" value="승인">
						<input type="button" id="btn_no" value="삭제">
					</li>
				</ul>
			</li>
		</ul>
		
		<div class="btn_area">
			<input type="button" id="all_ok" value="전체 승인">
			<input type="button" id="all_del" value="전체 삭제">
		</div>
	
		<div class="paging">
			<a href="" class="prev">전</a>
			<a href="">1</a>
			<a href="">2</a>
			<a href="">3</a>
			<a href="">4</a>
			<a href="">5</a>
			<a href="" class="last">후</a>
		</div>
	</div>
</div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>