<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/jsp/common/setting.jsp" %>
<html>
<head>
    <title>동네 서점</title>
    <link type="text/css" rel="stylesheet" href="/bookstore/css/join.css">
    
    <style>
    	.wrap .find{line-height:0;font-size:11px;color:gray;font-weight:normal;text-align:right;}
    </style>
</head>
<body onload="signInFocus();">
<section class="wrap">
	<div class="container">
		<h1 class="logo"><a href="index.do">BMS</a></h1>
	
		<form action="/bookstore/signInPro.do" name="signInForm" method="post" class="content" onsubmit="return signInCheck();">
			<div class="login">
		        <input class="input" type="text" name="id" maxlength="15" placeholder="아이디">
		        <input class="input" type="password" name="pwd" maxlength="20" placeholder="비밀번호">
		    </div>
		    
		    <div class="join">
		        <input class="inputButton" type="submit" value="로그인">
		        <input class="inputButton" type="button" value="회원가입" onclick="window.location='join.do'">
		    </div>
		    
		    <div>
		    	<a class="find" href="" onclick="idPwdFindView()">아아디 / 비밀번호 찾기</a>
		    </div>
		</form>
	</div>
</section>
</body>
</html>