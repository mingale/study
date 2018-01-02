<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewpost" content="width=device-width initial-scale=1">

	<title>관리자 - 재고 관리</title>

	<link rel="stylesheet" href="../../css/reset.css">
	<link rel="stylesheet" href="../../css/a_common.css">
	<link rel="stylesheet" href="../../css/a_board.css">
	<link rel="stylesheet" href="../../css/paging.css">
	
	<style>
		.title{width:40%;}
		.author{width:10%;}
		.price{width:10%;}
		.count{width:10%;}
		.item_btn_area{width:100px;;}
		
		#all_del{float:left;margin-left:10px;}
		#add{float:right;margin-right:10px;}
	</style>
</head>
<body>

<%@ include file="header.jsp" %>

<section>
<div class="wrap clearfix">
	<nav class="sub_nav">
		<a href="">재고 목록</a>
		<a href="">입고 현황</a>
	</nav>

	<div class="container clearfix">
		<ul class="content">
			<li>
				<ul class="item zero clearfix">
					<li class="chk">선택</li>
					<li class="title">도서명</li>
					<li class="author">저자</li>
					<li class="price">가격</li>
					<li class="count">수량</li>
					<li class="item_btn_area">비고</li>
				</ul>
			</li>
			<li>
				<ul class="item clearfix">
					<li class="chk"><input type="checkbox" id="book1" class="list"></li>
					<li class="title"><a href="">도서1</a></li>
					<li class="author">저팔계</li>
					<li class="price">1000</li>
					<li class="count">1</li>
					<li class="item_btn_area">
						<input type="button" id="btn_up" value="수정">
						<input type="button" id="btn_del" value="삭제">
					</li>
				</ul>
			</li>
		</ul>
		
		<div class="btn_area">
			<input type="button" id="all_del" value="전체 삭제">
			<input type="button" id="add" value="추가">
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