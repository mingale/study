<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/jsp/common/setting.jsp" %>
<html>
<head>
    <title>동네 서점</title>
    <link type="text/css" rel="stylesheet" href="/bookstore/css/join.css">
    
	<style>
		.container{width:400px;padding:10px 20px;}
		.container div{border:1px solid #c6c6c6;margin-bottom:20px;}
		
		section .searchBtn{width:55px;height:25px;color:dimgrey;border:1px solid darkgray;border-radius:0.5em;font-size:9px;margin-right:5px;}
		section .searchIn{width:330px;}
		
		@media(max-width:400px){
			.container{width:300px;}
			section .searchIn{width:230px;}
		}
	</style>
	
	<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js" ></script>
</head>
<body onload="signUpFocus();">
<section class="wrap">
	<form class="container" action="signUpPro.do" name="signUpForm" method="post" onsubmit="return signUpCheck();">
		<h1 class="logo"><a href="index.do">BMS</a></h1>
	
		<div>
			<input class="input" type="text" name="id" maxlength="15" placeholder="아이디" onblur="confirmId();">
			<input class="input" type="password" name="pwd" maxlength="20" placeholder="비밀번호">
			<input class="input" type="password" name="repwd" maxlength="20" placeholder="비밀번호 재확인">
		</div>
	
		<div>
			<input class="input" type="text" name="name" maxlength="10" placeholder="홍길동">
			<input class="input" type="text" name="phone" maxlength="14" placeholder="010-1111-1111" onblur="confirmPh();">
			<input class="input" type="text" name="email" maxlength="30" placeholder="ghd@gmail.com" onblur="confirmEmail();">
			<input class="input searchIn" type="text" name="address" placeholder="서울 금천구 벚꽃로">
			<input class="searchBtn" type="button" value="주소 찾기" onclick="addressSearch();">
		</div>
	
		<input class="inputButton" type="submit" value="회원가입">
	</form>
</section>
</body>
</html>