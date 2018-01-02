<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<style>
		.content{width:400px;margin:0 auto;padding-top:200px;}
		.content button{width:100%;display:block;text-align:center;}
		.wrap .input{border:1px solid #c6c6c6;}
	</style>
</head>

<body onload="myPagePwdFocus();">
<%@ include file="/jsp/common/header.jsp" %>

<section>
<div class="wrap">
	<form class="content" action="/bookstore/myPageView.do" name="confirmPwdForm" onsubmit="return pwdCheck();">
			<input class="input" type="password" name="pwd" placeholder="비밀번호">
			<button class="inputButton" type="submit">확인</button>
	</form>
</div>
</section>

<%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>