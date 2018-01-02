<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="/bookstore/css/board.css">

	<style>
		section{margin-top:20px;}

		.item_title{width:100%;font-size:14px;font-weight:bold;margin:20px 0;}
		.cart_list > ul> li{margin:15px 0;}
		.middle{lign-height:110px;}
		
		#chk,.chk{float:left;width:30px;text-align:center}
		#title,.title{float:left;width:auto;}
		.title a > div{float:left;width:auto;word-wrap:break-word;text-align:left;}
		.title img{float:left;width:90px;height:120px;margin-right:10px;}
		#price,.price{float:right;width:80px;text-align:center}
		#count,.count{float:right;width:85px;text-align:center}
		#total,.total{float:right;width:80px;text-align:center}
		
		.price,.count,.total,.item_btn_area{line-height:110px;}

		#all_order{width:300px;height:50px;margin:0 auto;}

		#item_btn_area,.item_btn_area{float:right;width:50px;text-align:center;}
		.btn_area + div{text-align:center;margin:10px 0 20px 0;}
		
		.temp{font-size:24px;margin:0 auto;padding-top:200px;text-align:center;}
		.wrap .delBtn{width:50px;height:20px;color:dimgrey;border:1px solid darkgray;border-radius:0.5em;font-size:9px;text-align:center;}
		
		@media(max-width:640px) {
			.total{display:none;}
		}
		
		@media(max-width:400px) {
			.cart_list{font-size:9px;}
			.title img{width:40px;height:60px;}
			.item_btn_area{display:none;}
		}
	</style>
</head>
<body>
<%@include file="/jsp/common/header.jsp" %>

<section>
<div class="wrap">
<c:if test="${carts != null}">
	<form action="cartPro.go?cmd=up" name="cartForm" method="post">
		<div class="cart_list">
			<ul>
				<li>
					<ul class="item_title clearfix">
						<li id="chk"><input type="checkbox" name="all_check" onclick="cartAllCheck();"></li>
						<li id="title">도서명</li>
						<li id="item_btn_area">선택</li>
						<li id="total">합계</li>
						<li id="count">수량</li>
						<li id="price">가격</li>
					</ul>
					<hr>
				</li>
				<c:forEach var="cart" items="${carts}">
					<li>
						<ul class="clearfix">
							<li class="chk">
								<input class="list" type="checkbox" name="cart_check" value="${cart.no}">
							</li>
							<li class="title">
								<a href="detail.do?no=${cart.no}" class="clearfix">
									<img src="${imageBook}${cart.image}">
									<div>${cart.title}</div>
								</a>
							</li>
							<li class="item_btn_area">
								<input class="delBtn" type="button" value="삭제" onclick="cartDelete('${cart.no}');">
							</li>
							<li class="total">${cart.price * cart.count}</li>
							<li class="count">
								<input type="hidden" name="no" value="${cart.no}">
								<input class="inputNumber" id="${cart.no}" type="number" name="count" min="1" max="100" value="${cart.count}" onchange="cartCountChange('${cart.no}');">
							</li>
							<li class="price">${cart.price}</li>
						</ul>
					</li>
				</c:forEach>
			</ul>
			
			<hr>
		</div>
		
		<div class="btn_area clearfix">
			<input class="smallButton" type="button" value="선택 도서 삭제" onclick="cartCheckDelete();">
			<input class="smallButton" type="button" value="선택 도서 찜하기" onclick="cartCheckF();">
		</div>
		
		<div>
			<input class="inputButton" type="button" value="구매하기" onclick="cartOrder();">
		</div>
	</form>
</c:if>
<c:if test="${carts == null}">
	<div class="temp">보관 중인 도서가 없습니다.</div>
</c:if>
</div>
</section>

<%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>