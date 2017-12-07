<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<title>관리자 - 주문 관리</title>

	<link type="text/css" rel="stylesheet" href="/bookstore/css/board.css">
	<link type="text/css" rel="stylesheet" href="/bookstore/css/paging.css">
	
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
<body onload="navFocus('o');">
<%@ include file="/jsp/host/header.jsp" %>

<section class="wrap clearfix">
<%@ include file="/jsp/host/stockNav.jsp" %>

<c:if test="${cnt != 0}">
	<div class="contain clearfix">
		<ul class="content">
			<li>
				<ul class="item clearfix">
					<li class="chk">선택</li>
					<li class="number">주문번호</li>
					<li class="date">주문일</li>
					<li class="user">주문자</li>
					<li class="title">도서명</li>
					<li class="state">상태</li>
					<li class="item_btn_area">비고</li>
				</ul>
			</li>
			
			<c:forEach var="order" items="${orders}">
				<li>
					<ul class="item clearfix">
						<li class="chk"><input type="checkbox" name="list" class="list"></li>
						<li class="number">${order.order_num}</li>
						<li class="date">${fn:substring(order.order_num, 0, 8)}</li>
						<li class="user">${order.id}</li>
						<li class="title">
							<a href="#">${order.nos}</a>
						</li>
						<li class="state">${order.getState()}</li>
						<li class="item_btn_area">
							<c:if test="${order.order_state == 0}">
								<input type="button" value="승인" onclick="window.location='orderOk.ho?order_num=${order.order_num}';">
							</c:if>
							<c:if test="${order.order_state == 1}">
								<input type="button" value="배송 시작" onclick="hostOrderShipping(${order.order_num});">
							</c:if>
							<input type="button" value="삭제" onclick="hostOrderDelete(${order.order_num});">
						</li>
					</ul>
				</li>
			</c:forEach>
		</ul>
		
		<div class="btn_area">
			<input class="smallButton s1" type="button" value="전체 승인" onmouseover="navOver('s1');" onmouseout="navOut('s1');">
			<input class="smallButton s2" type="button" value="전체 삭제" onmouseover="navOver('s2');" onmouseout="navOut('s2');">
		</div>
	
		<div class="paging">
			<c:if test="${startPage > pageNav}">
				<a href="order.ho">시작</a>
				<a href="order.ho?pageNum=${startPage - pageNav}">전</a>			
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}">
				<a href="order.ho?pageNum=${i}">${i}</a>
			</c:forEach>
			<c:if test="${pageCnt > endPage}">
				<a href="order.ho?pageNum=${endPage + pageNav}">후</a>
				<a href="order.ho?pageNum=${pageCnt}">끝</a>				
			</c:if>
		</div>
	</div>
</c:if>

<c:if test="${cnt == 0}">
	<div class="contain div">주문 내역이 존재하지 않습니다.</div>
</c:if>
</section>

<%@ include file="/jsp/host/footer.jsp" %>
</body>
</html>