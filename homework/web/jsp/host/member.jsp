<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewpost" content="width=device-width initial-scale=1">

	<title>관리자 - 회원 관리</title>

	<link rel="stylesheet" href="../../css/reset.css">
	<link rel="stylesheet" href="../../css/a_common.css">
	<link rel="stylesheet" href="../../css/a_board.css">
	<link rel="stylesheet" href="../../css/paging.css">
	
	<style>
		.date{width:80px;}
		.state{width:80px;}
		.user{width:50px;}
		.memo{width:280px;}
		.rank{width:40px;}
		.item_btn_area{width:100px;}
		
		#all_up{margin-left:10px;margin-right:3px;}
	</style>
</head>
<body>

<%@ include file="header.jsp" %>

<section>
<div class="wrap clearfix">
	<nav class="sub_nav">
		<a href="">회원 목록</a>
	</nav>

	<div class="container clearfix">
		<ul class="content">
			<li>
				<ul class="item zero clearfix">
					<li class="chk">선택</li>
					<li class="date">가입일</li>
					<li class="state">최근주문일</li>
					<li class="user">주문자</li>
					<li class="memo">메모</li>
					<li class="rank">등급</li>
					<li class="item_btn_area">비고</li>
				</ul>
			</li>
			<li>
				<ul class="item clearfix">
					<li class="chk"><input type="checkbox" id="book1" class="list"></li>
					<li class="date">2017.11.03</li>
					<li class="state">2017.11.03</li>
					<li class="user"><a href="">홍길동</a></li>
					<li class="memo">블랙컨슈머</li>
					<li class="rank">1</li>
					<li class="item_btn_area">
						<input type="button" id="btn_up" value="수정">
						<input type="button" id="btn_out" value="탈퇴">
					</li>
				</ul>
			</li>
		</ul>
		
		<div class="btn_area">
			<input type="button" id="all_up" value="전체 수정">
			<input type="button" id="all_out" value="전체 탈퇴">
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