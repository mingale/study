<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width initial-scale=1">
    <title>동네 서점</title>

	<link rel="stylesheet" href="../../css/reset.css">
    <link rel="stylesheet" href="../../css/common.css">
    <link rel="stylesheet" href="../../css/a_board.css">
	<style>
		section .list_nav{float:left;width:100px;padding:20px;}
		section .list_nav a{display:block;width:100px;height:40px;line-height:40px;text-align:center;}

        section .container{float:left;width:80%;}
        .item li{float:left;}
		.number{width:80px;}
        .date{width:80px;}
		.title{width:275px;}
		.state{width:100px;}
		.item_btn_area{width:120px;}
		
		#all_ok{margin-left:10px;margin-right:3px;}
	</style>
</head>

<body>
<%@include file="../common/header.jsp" %>	

<section>
<div class="wrap clearfix">

	<div class="list_nav">
		<a href="./mypage.jsp">회원 정보</a>
		<a href="./order.jsp">주문 내역</a>
		<a href="./refund.jsp">환불 내역</a>
	</div>

	<div class="container clearfix">
        <ul class="content">
            <li>
                <ul class="item zero clearfix">
                    <li class="chk">선택</li>
                    <li class="number">주문번호</li>
                    <li class="date">주문일</li>
                    <li class="title">도서명</li>
                    <li class="state">상태</li>
                    <li class="item_btn_area">요청</li>
                </ul>
            </li>
            <li>
                <ul class="item clearfix">
                    <li class="chk"><input type="checkbox" id="book1" class="list"></li>
                    <li class="number">20171106</a></li>
                    <li class="date">2017.11.03</li>
                    <li class="title"><a href="">도서1</a></li>
                    <li class="state">배송 중</li>
                    <li class="item_btn_area">
                        <input type="button" id="btn_or" value="재주문">
                        <input type="button" id="btn_re" value="환불">
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
</section>

<%@include file="../common/footer.jsp" %>
</body>
</html>