<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>동네 서점</title>

	<link rel="stylesheet" href="../../css/reset.css">
	<link rel="stylesheet" href="../../css/common.css">
	<link rel="stylesheet" href="../../css/paging.css">
	<style>
		.book_list{padding-left:50px;font-size:12px;border:1px solid #c6c6c6;background-color:#fff;}
		.book_list li{float:left;width:100px;padding:5px 3px}
		
		section .wrap > h3{padding:10px 0;}
		
	/* 	.check_area{width:1004px;padding:10px 10px 0 10px;}
		.check_area .all_check{float:left;}
		.check_area .check_right{display:block;float:right;} */
		
		.item > input{float:left;}
		.item img{float:left;width:120px;height:150px;padding-left:30px;}
		.info_area{float:left;width:600px;margin:5px 20px;word-wrap:break-word;}
		/* word-wrap:break-word
		가로 사이즈나 엘러먼트에 맞춰서 강제 줄 바꿈 해준다. word-break:break-all; 같은결과물
		 */
		.btn_area{float:left;width:150px;}
		.btn_area input{width:100%;float:left;}
	</style>
</head>
<body>

<%@ include file="../common/header.jsp" %>

<section>
<div class="wrap">
	<div class="book_list">
		<ul class="clearfix">
			<li><a href="">소설</a></li>
			<li><a href="">장르소설</a></li>
			<li><a href="">경제경영</a></li>
			<li><a href="">자기계발</a></li>
			<li><a href="">시/에세이</a></li>
			<li><a href="">인문</a></li>
			<li><a href="">국어/외국어</a></li>
			<li><a href="">정치/사회</a></li>
			<li><a href="">역사/문화</a></li>
			<li><a href="">자연과학/공학</a></li>
			<li><a href="">컴퓨터/인터넷</a></li>
			<li><a href="">건강/의학</a></li>
			<li><a href="">가정/생활/요리</a></li>
			<li><a href="">여행/취미</a></li>
			<li><a href="">예술/대중문화</a></li>
			<li><a href="">매거진</a></li>
			<li><a href="">종교</a></li>
			<li><a href="">교재/수험서</a></li>
			<li><a href="">청소년교양</a></li>
			<li><a href="">유아</a></li>
			<li><a href="">아동</a></li>
			<li><a href="">코믹스</a></li>
			<li><a href="">외국도서</a></li>
			<li><a href="">북모닝CEO(PDF)</a></li>
			<li><a href="">멀티eBook</a></li>
			<li><a href="">e-오디오북</a></li>
		</ul>
	</div>
	
	<h3>소설</h3>

	<!-- <div class="check_area clearfix">
		<input type="button" id="all_check" value="전체 선택">
		<input type="button" id="all_wish" value="선택 찜하기" class="check_right">
		<input type="button" id="all_order" value="선택 바로 구매하기" class="check_right">
		<input type="button" id="all_cart" value="선택 장바구니 담기" class="check_right">
	</div> -->
	
	<hr>
	
	<div class="container">
		<ul class="clearfix">
			<li class="item clearfix">
				<!-- <input type="checkbox" id="check" name="list_check"> -->

				<a href="./detail.jsp">
					<img src="../../images/book1.jpg">
					
					<div class="info_area">
						<p>dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd</p>
					</div>
				</a>
				
				<div class="btn_area">
					<input type="button" id="btn_now" value="바로 구매">
					<input type="button" id="btn_cart" value="장바구니 담기">
					<input type="button" id="btn_wish" value="찜하기">
				</div>
			</li>
		</ul>
	</div>
	
	<hr>
	
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