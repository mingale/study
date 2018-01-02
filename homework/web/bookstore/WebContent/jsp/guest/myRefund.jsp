<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/bookstore/css/board.css">
	<style>
        .wrap .container{float:left;width:80%;}
        .div{font-size:14px;line-height:400px;text-align:center;}
        
        .item li{float:left;}
		.number{width:80px;text-align:center;}
        .date{width:80px;text-align:center;}
		.title{width:auto;}
		.item .state{width:100px;float:right;text-align:center;}
		
		#all_ok{margin-left:10px;margin-right:3px;}
	</style>
</head>

<body onload="navFocus('mr');">
<%@include file="/jsp/common/header.jsp" %>

<section>
<div class="wrap clearfix">

	<%@ include file="/jsp/guest/myPageNav.jsp" %>

	<div class="container clearfix">
	<c:if test="${refunds != null}">
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
	                    		<a href="#">${re.nos}</a>
	                    </li>
	                    <li class="state">${re.getState()}</li>
	                </ul>
	            </li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${refunds == null}">
    	<div class="div">환불 내역이 존재하지 않습니다.</div>
    </c:if>
    </div>
</div>
</section>

<%@include file="/jsp/common/footer.jsp" %>
</body>
</html>