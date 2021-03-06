<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		.item .state{width:100px;float:right;text-align:center;}
		
		#all_ok{margin-left:10px;margin-right:3px;}
	</style>
	
	<script src="${projectRes}script.js"></script>
</head>

<body onload="navFocus('mr');">
<c:if test="${sessionScope.memId != null}">
	<%@include file="../common/header.jsp" %>
	
	<section>
	<div class="wrap clearfix">
	
		<%@ include file="../guest/myPageNav.jsp" %>
	
		<div class="container clearfix">
		<c:if test="${refunds.size() > 0}">
	        <ul class="content">
	            <li>
	                <ul class="item zero clearfix">
	                    <li class="chk">선택</li>
	                    <li class="number">주문번호</li>
	                    <li class="date">주문일</li>
	                    <li class="title">도서명</li>
	                    <li class="state">상태</li>
	                </ul>
	            </li>
	            <c:forEach var="re" items="${refunds}">
		            <li>
		                <ul class="item clearfix">
		                    <li class="chk"><input type="checkbox" id="book1" class="list"></li>
		                    <li class="number">${re.order_num}</li>
		                    <li class="date">${re.order_num.substring(0, 8)}</li>
		                    <li class="title">
		                    		<a href="#" onclick="orderDetailView('${re.order_num}')">${re.book.title} ${re.book.no}</a>
		                    </li>
		                    <li class="state">${re.getState()}</li>
		                </ul>
		            </li>
	            </c:forEach>
	        </ul>
	    </c:if>
	    <c:if test="${refunds.size() == 0}">
	    	<div class="div">환불 내역이 존재하지 않습니다.</div>
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