<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<style>
		form{margin:0 auto;width:370px;padding:10px 20px;}
		
		.ul{margin:30px 0;border:1px solid #c6c6c6;padding:1px;}
		#span{float:right;line-height:16px;}
		#input{float:right;width:20px;height:20px;margin-left:10px;}
		
		.wrap .account{width:260px;}
		.wrap .searchBtn{width:55px;height:25px;color:dimgrey;border:1px solid darkgray;border-radius:0.5em;font-size:9px;margin-right:5px;}
		.wrap .searchIn{width:300px;}
		section .bank{width:80px;height:25px;color:dimgrey;border:1px solid darkgray;border-radius:0.5em;font-size:12px;margin:5px;}
	</style>

	<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js" ></script>
<body onload="orderFocus();">
<%@ include file="../common/header.jsp" %>

<section>
<div class="wrap">
	<form action="orderPro" name="orderForm" onsubmit="return orderCheck();">
		<input type="hidden" name="carts" value="${carts}">
		<input type="hidden" name="btn" value="${btn}">
		<input type="hidden" name="nowCount" value="${nowCount}">
		
	    <ul class="ul">
	        <li><input class="input" type="text" name="name1" value="${mem.name}"></li>
	        <li><input class="input" type="text" name="phone1" value="${mem.phone}"></li>
	        <li><input class="input" type="text" name="address1" value="${mem.address}"></li>
	    </ul>
	    
		<div class="clearfix"><input id="input" type="checkbox" name="nameAuto" onclick="orderText();"><span id="span"> 주문자와 동일 </span></div>
	    <ul class="ul">
	        <li><input class="input" type="text" name="name" placeholder="수령인"></li>
	        <li><input class="input" type="text" name="phone" placeholder="수령인 연락처"></li>
	        <li>
	        	<input class="input searchIn" type="text" name="address" placeholder="배송지">
	        	<input class="searchBtn" type="button" value="주소 찾기" onclick="addressSearch();">
	        </li>
	        <li><input class="input" type="text" name="etc" placeholder="배송 메시지"></li>
	    </ul>
	    
	    <ul class="ul">
	    	<li>
	    		<select class="bank" name="bank">
	    			<option value="선택">은행선택</option>
	    			<option value="국민은행">국민은행</option>
	    			<option value="우리은행">우리은행</option>
	    			<option value="신한은행">신한은행</option>
	    			<option value="하나은행">하나은행</option>
	    			<option value="농협은행">농협은행</option>
	    		</select>
	    	<!-- </li> -->
	        <!-- <li> --><input class="input account" type="text" name="account" maxlength="38" placeholder="환불 계좌(ex: 99111101111111)"></li>
	    </ul>
	    <input class="inputButton" type="submit" value="주문하기">
	</form>
</div>
</section>

<%@ include file="../common/footer.jsp" %>
</body>
</html>