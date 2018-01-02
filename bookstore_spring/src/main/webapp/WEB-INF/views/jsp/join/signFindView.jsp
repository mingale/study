<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/resources/setting.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script src="${projectRes}script.js"></script>
	<style>
		.wrap{text-align:center;margin:10px 0;}
		input{width:55%;height:40px;margin-bottom:5px;padding:2px;}
		.wrap .inputButton{background:lightblue;}
	</style>
</head>
<body class="wrap">
<form action="signFind" method="post" name="findForm" onsubmit="return findInputCheck();">
	<input id="eInput" type="email" name="email" placeholder="등록한 이메일">
	<input class="inputButton" type="submit" value="인증 메일 전송">
	<input id="idfind" class="inputButton" type="button"  onclick="idInput();" value="비밀번호 찾기">
	<input id="idCheck" type="hidden" name="idCheck" value="n">
</form>
</body>
</html>