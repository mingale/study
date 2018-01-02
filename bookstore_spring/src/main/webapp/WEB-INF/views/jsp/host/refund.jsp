<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resources/setting.jsp" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>관리자 - 환불 관리</title>
	<link type="text/css" rel="stylesheet" href="${projectRes}css/board.css">
	<link type="text/css" rel="stylesheet" href="${projectRes}css/a_common.css">
	<link type="text/css" rel="stylesheet" href="${projectRes}css/paging.css">
	
	<style>
		.num{width:80px;text-align:center;}
		.date{width:80px;text-align:center;}
		.id{width:50px;}
		.title{width:280px;}
		.state{width:100px;}
		.item_btn_area{width:110px;}
	</style>
</head>
<body onload="navFocus('r');">
<%@ include file="../host/header.jsp" %>

<section class="wrap clearfix">
<%@ include file="../host/stockNav.jsp" %>
	
<c:if test="${refunds != null}">
	<div class="contain clearfix">
		<ul class="content">
			<li>
				<ul class="item clearfix">
					<li class="num">주문번호</li>
					<li class="date">주문날짜</li>
					<li class="id">주문자</li>
					<li class="title">정보</li>
					<li class="state">상태</li>
					<li class="item_btn_area">비고</li>
				</ul>
			</li>
			<c:forEach var="o" items="${refunds}">
				<li>
					<ul class="item clearfix">
						<li class="num">${o.order_num}</li>
						<li class="date">${o.order_num.substring(0, 8)}</li>
						<li class="id">${o.id}</li>
						<li class="title">
							<a href="#" onclick="orderDetailView('${o.order_num}')">${o.book.title} ${o.book.no}</a>
						</li>
						<li class="state">${o.getState()}</li>
						<li class="item_btn_area">
							<c:if test="${o.order_state == 8}">
								<input class="boardButton" type="button" value="승인" onclick="window.location='refundOk?order_num=${o.order_num}'">
								<input class="boardButton" type="button" value="거부" onclick="window.location='refundNo?order_num=${o.order_num}'">
							</c:if>
						</li>
					</ul>
				</li>
			</c:forEach>
		</ul>
			
		<div class="paging">
			<c:if test="${startPage > pageNav}">
				<a href="refund">시작</a>
				<a href="refund?pageNum=${startPage - pageNav}">전</a>
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}">
				<a href="refund?pageNum=${i}">${i}</a>
			</c:forEach>
			<c:if test="${pageCnt > endPage}">
				<a href="refund?pageNum=${endPage + pageNav}">후</a>
				<a href="refund?pageNum=${pageCnt}">끝</a>
			</c:if>
		</div>
	
	</div>
</c:if>
<c:if test="${refunds == null}">
	<div class="div">환불 내역이 존재하지 않습니다.</div>
</c:if>
</section>

<%@ include file="../host/footer.jsp" %>
</html>