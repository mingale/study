<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/resources/setting.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewpost" content="width=device-width initial-scale=1">

	<title>관리자</title>
	
	<style>
		/* layout */
		section{padding:10px;}
		span{font-weight:bold;font-size:14px;}
		
		.new{float:left;width:490px;height:250px;margin-top:30px;padding:0 10px;}
		.new > ul{margin-top:20px;border:1px solid #c6c6c6;}
		.div{width:100%;height:100%;line-height:180px;margin:auto 0;text-align:center}
		.notice{float:right;width:100%;margin-top:20px;padding-bottom:20px;}
		
		.item > li{width:100%;font-size:14px;}
		.item li li{float:left;margin:5px 0;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;}

		.number{width:25%;text-align:center}
		.date{width:100px;text-align:center}
		.name{width:20%;}
		.state{width:15%;}
		.total{width:15%;}
		
		.title{width:245px;}
		.price{width:70px;text-align:center}
		.count{width:70px;text-align:center}
	</style>
</head>
<body>

<%@ include file="header.jsp" %>

<section>
<div class="wrap clearfix">
	<div class="new">
		<span>최근 주문</span>
		<c:if test="${orders != null}">
			<ul class="item clearfix">
				<li>
					<ul class="clearfix">
						<li class="number">주문번호</li>
						<li class="date">주문일</li>
						<li class="name">주문자</li>
						<li class="state">상태</li>
						<li class="total">합계</li>
					</ul>
				</li>
				<c:forEach var="order" items="${orders}">
					<li>
						<ul class="clearfix">
							<li class="number">${order.order_num}</li>
							<li class="date">${fn:substring(order.order_num, 0, 8)}</li>
							<li class="name">${order.id}</li>
							<li class="state">${order.order_state}</li>
							<li class="total">10000</li>
						</ul>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${orders == null}">
			<div class="div">주문량 늘리시게</div>
		</c:if>
	</div>

	<div class="new">
		<span>최근 등록 도서</span>
		<c:if test="${books != null}">
			<ul class="item clearfix">
				<li>
					<ul class="clearfix">
						<li class="date">등록일</li>
						<li class="title">도서</li>
						<li class="price">가격</li>
						<li class="count">수량</li>
					</ul>
				</li>
				<c:forEach var="book" items="${books}">
					<li>
						<ul class="clearfix">
							<li class="date">
										${book.bookSub.add_date}
							</li>
							<li class="title">${book.title}</li>
							<li class="price">${book.price}</li>
							<li class="count">${book.count}</li>
						</ul>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${books == null}">
			<div class="div">판매하려면 도서를 구비해야하지 않겠는가</div>
		</c:if>
	</div>
	
	<div class="notice">
		<span>공지사항</span>
		<hr>
		<c:forEach var="notice" items="${notices}">
			<a href="noticeView?idx=${notice.idx}">${notice.title} (${notice.add_date})</a><br>
		</c:forEach>
	</div>
</div>
</section>

<%@ include file="footer.jsp" %>

</body>
</html>