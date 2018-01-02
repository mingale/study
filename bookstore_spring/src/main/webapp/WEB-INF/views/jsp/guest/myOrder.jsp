<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/resources/setting.jsp" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="${projectRes}css/board.css">
	<style>
        .wrap .container{float:left;width:80%;}
        .div{font-size:14px;line-height:400px;text-align:center;}
        
		.number{width:80px;text-align:center;}
        .date{width:80px;text-align:center;}
		.title{width:auto;}
		.item .count{width:50px;float:right;text-align:center;}
		.item .state{width:100px;float:right;text-align:center;}
		.item .item_btn_area{width:70px;float:right;text-align:center;}
		
		#all_ok{margin-left:10px;margin-right:3px;}
	</style>
	
	<script src="${projectRes}script.js"></script>
</head>

<body onload="navFocus('mo');">
<c:if test="${sessionScope.memId != null}">
	<%@include file="../common/header.jsp" %>	
	
	<section>
	<div class="wrap clearfix">
	
		<%@ include file="../guest/myPageNav.jsp" %>
	
		<div class="container clearfix">
			<c:if test="${orders.size() > 0}">
				<form method="post">
			        <ul class="content">
			            <li>
			                <ul class="item zero clearfix">
			                    <li class="chk">선택</li>
			                    <li class="number">주문번호</li>
			                    <li class="date">주문일</li>
			                    <li class="title">도서명</li>
			                    <li class="item_btn_area">요청</li>
			                    <li class="state">상태</li>
			                    <li class="count">수량</li>
			                </ul>
			            </li>
			            <c:forEach var="order" items="${orders}">
						<c:if test="${order.order_state != 9}">
				            <li>
				                <ul class="item clearfix">
				                    <li class="chk"><input type="checkbox" name="list" class="list"></li>
				                    <li class="number">${order.order_num}</li>
				                    <li class="date">${fn:substring(order.order_num, 0, 8)}</li>
				                    <li class="title">
					                    <a href="#" onclick="orderDetailView('${order.order_num}')">${order.book.title} ${order.book.no}</a>
				                    </li>
				                    <li class="item_btn_area">
				                        <!-- <input type="button" value="재주문"> -->
				                        <c:if test="${order.order_state != 0}">
				                        	<input class="boardButton" type="button" value="환불 신청" onclick="myOrderRefund('${order.order_num}');">
				                        </c:if>
				                        <c:if test="${order.order_state == 0}">
				                        	<input class="boardButton" type="button" value="취소" onclick="myOrderDelete('${order.order_num}');">
				                        </c:if>
				                    </li>
				                    <li class="state">${order.getState()}</li>
				                    <li class="count">${order.order_count}</li>
				                </ul>
				            </li>
	                    </c:if>
			            </c:forEach>
			        </ul>
				</form>
			</c:if>
			<c:if test="${orders.size() == 0}">
				<div class="div">주문 내역이 존재하지 않습니다.</div>
			</c:if>
	    </div>
	</div>
	</section>
	
	<%@include file="../common/footer.jsp" %>
</c:if>
<c:if test="${sessionScope.memId == null}">
	<script type="text/javascript">
		errorAlert(loginMsg);
	</script>
</c:if>
</body>
</html>