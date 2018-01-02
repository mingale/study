<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/jsp/common/setting.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	
	<link type="text/css" rel="stylesheet" href="/bookstore/css/common.css">
	<style>
		h3{text-align:center;font-size:18px;font-weight:bold;margin:15px;}
		
		.content > li{margin-bottom:15px;}
		.item > li{float:left;padding:8px;}
		
		.title{width:100px;background:lightblue;}
		
		.image{width:10%;}
		.image > img{width:60px;height:90px;}
		.number{width:50%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;}
		.count{width:10%;text-align:center;}
		.price{width:10%;text-align:center;}
		
		.content1 > li{border:1px solid #c6c6c6;}
		.lineCen{line-height:80px;}
	</style>
</head>
<body>
<h3>주문 상세 정보</h3>

	<ul class="content">
		<li>
			<ul class="item clearfix">
				<li class="title">주문 번호</li>
				<li>${orders.get(0).order_num}</li>
			</ul>
			<ul class="item clearfix">
				<li class="title">주문 상태</li>
				<li>${orders.get(0).getState()}</li>
			</ul>
			<ul class="item clearfix">
				<li class="title">송장 번호</li>
				<li>${shipping}</li>
			</ul>
		</li>
		<li>
			<ul class="item clearfix">
				<li class="title">수령인</li>
				<li>${orders.get(0).name}</li>
			</ul>
			<ul class="item clearfix">
				<li class="title">배송지</li>
				<li>${orders.get(0).address}</li>
			</ul>
			<ul class="item clearfix">
				<li class="title">연락처</li>
				<li>${orders.get(0).phone}</li>
			</ul>
			<ul class="item clearfix">
				<li class="title">배송 메시지</li>
				<li class="etc_memo">${orders.get(0).etc}</li>
			</ul>
		</li>
	</ul>
	<ul class="content1">
		<li>
			<ul class="item clearfix">
				<li class="image"></li>
				<li class="number">도서명</li>
				<li class="count">수량</li>
				<li class="price">가격</li>
			</ul>
		</li>
		<c:forEach var="order" items="${orders}">
			<li>
				<ul class="item clearfix">
					<li class="image lineCen"><img src="${imageBook}${order.id}"></li>
					<li class="number lineCen">${order.nos}</li>
					<li class="count lineCen">${order.count}</li>
					<li class="price lineCen">${order.price}</li>
				</ul>
			</li>
		</c:forEach>
	</ul>
</body>
</html>