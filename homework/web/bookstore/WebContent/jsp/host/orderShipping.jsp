<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/jsp/common/setting.jsp" %>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="/bookstore/css/common.css">
	<style type="text/css">
		form{text-align:center;line-height:50px;}
		.input{width:300px;height:50px;margin-top:10px;font-size:18px;}
		.inputButton{width:100px;height:25px;border:1px solid white;background:white;margin-top:10px;}
	</style>
</head>
<body onload="shippingFocus();">
<form action="shippingPro.ho" name="shippingForm" method="post" onsubmit="return shippingCheck();">
	<input type="hidden" name="orderNum" value="${order_num}">
	
	송장 번호<br>
	<input class="input" type="text" name="shippingNum" placeholder="4949-1231-2345"><br>
	<input class="inputButton" type="submit" value="확인">
</form>
</body>
</html>