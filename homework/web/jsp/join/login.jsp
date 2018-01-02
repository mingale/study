<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width initial-scale=1">
    <title>동네 서점</title>

	<link rel="stylesheet" href="../../css/reset.css">
    <link rel="stylesheet" href="../../css/join.css">
    <link rel="stylesheet" href="../../css/login.css">
</head>
<body>
<div class="wrap">
	<div class="container">
		<h1><a href="../common/index.jsp">BMS</a></h1>
	
		<form action="../../jdbc/login.jsp" name="myForm" method="post" class="content">
			<div class="login">
		        <input type="text" placeholder="아이디" name="id">
		        <input type="password" placeholder="비밀번호" name="pwd">
		    </div>
		    
		    <div class="join">
		        <input type="submit" name="login" value="로그인" onclick="loginCheck();">
		        <input type="submit" name="member" value="회원가입" onclick="joinCheck();">
		    </div>
		</form>
	</div>
</div>
</body>
</html>