<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<style>
		form{margin:0 auto;width:400px;padding:10px 20px;}
		
		.ul{margin:30px;border:1px solid #c6c6c6;padding:1px;}
		#span{float:right;}
		#input{float:right;}
	</style>

<body onload="orderFocus();">
<%@ include file="/jsp/common/header.jsp" %>

<section>
<div class="wrap">
	<form action="orderPro.go" name="orderForm" onsubmit="return orderCheck();">
		<input type="hidden" name="carts" value="${carts}">
		<input type="hidden" name="btn" value="${btn}">
		
	    <ul class="ul">
	        <li><input class="input" type="text" name="name1" value="${mem.name}"></li>
	        <li><input class="input" type="text" name="phone1" value="${mem.phone}"></li>
	        <li><input class="input" type="text" name="address1" value="${mem.address}"></li>
	    </ul>
	    
		<div class="clearfix"><input id="input" type="checkbox" name="nameAuto" onclick="orderText();"><span id="span"> 주문자와 동일 </span></div>
	    <ul class="ul">
	        <li><input class="input" type="text" name="name" placeholder="받는 이"></li>
	        <li><input class="input" type="text" name="phone" placeholder="받는 이 연락처"></li>
	        <li><input class="input" type="text" name="address" placeholder="받는 이 주소"></li>
	        <li><input class="input" type="text" name="etc" placeholder="배송 메시지"></li>
	    </ul>
	    
	    <ul class="ul">
	        <li><input class="input" type="text" name="account" placeholder="환불 계좌(ex: 99111101111111)"></li>
	    </ul>
	    <input class="inputButton" type="submit" value="주문하기">
	</form>
</div>
</section>

<%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>