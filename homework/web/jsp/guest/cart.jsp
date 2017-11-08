<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>동네 서점 - 장바구니</title>

	<link rel="stylesheet" href="../../css/reset.css">
	<link rel="stylesheet" href="../../css/common.css">
	<link rel="stylesheet" href="../../css/a_board.css">

	<style>
		section{margin-top:20px;}

		.item_title{width:100%;font-size:12px;}

		.item{text-align:center;}
		.title{width:550px;}
		.title a > div{float:left;width:450px;word-wrap:break-word;text-align:left;}
		.title img{float:left;width:90px;height:120px;margin-right:10px;}
		.price{width:80px;}
		.count{width:120px;}
		.count input{width:40px;}
		.total{width:80px;}
		/* .item_btn_area{width:100px;font-size:12px;} */

		#all_order{width:300px;height:50px;margin:0 auto;}

		.btn_area + div{text-align:center;margin:10px 0 20px 0;}
	</style>
</head>
<body>

<%@include file="../common/header.jsp" %>

<section>
<div class="wrap">
	<div class="cart_list">
		<ul>
			<li>
				<ul class="item_title item clearfix">
					<li class="chk"><input type="checkbox" id="all_check" name="all_check"></li>
					<li class="title">도서명</li>
					<li class="price">가격</li>
					<li class="count">수량</li>
					<li class="total">합계</li>
					<li class="item_btn_area">선택</li>
				</ul>
				<hr>
			</li>
			<li>
				<ul class="item clearfix">
					<li class="chk"><input type="checkbox" id="book1" name="cart_check" class="list"></li>
					<li class="title">
						<a href="#" class="clearfix">
							<img src="../images/book1.jpg">
							<div>도서1dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd</div>
						</a>
					</li>
					<li class="price">1000</li>
					<li class="count"><input type="number" min="1" max="100">
						<input type="button" id="btn_up" value="변경">
					</li>
					<li class="total">1000</li>
					<li class="item_btn_area">
						<input type="button" id="btn_no" value="삭제">
					</li>
				</ul>
			</li>
		</ul>
		
		<hr>
	</div>
	
	<div class="btn_area clearfix">
		<input type="button" id="all_del" value="선택 도서 삭제">
		<input type="button" id="all_wish" value="선택 도서 찜하기">
	</div>
	
	<div>
		<input type="button" id="all_order" value="구매하기">
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

<%@ include file="../common/footer.jsp" %>
</body>
</html>