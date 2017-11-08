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
	<style>
		.container{width:400px;padding:10px 20px;margin:0 auto;}
		.container div{border:1px solid #c6c6c6;margin-bottom:20px;}
		.container input{width:388px;height:50px;border:none;padding:5px;}
		.container .btn_area a{width:100%;}

	</style>
</head>
<body>
<header>
		<h1><a href="../common/index.jsp">logo</a></h1>
</header>

<section>
<div class="container">
	<div>
		<input type="text" name="id" placeholder="아이디">
		<input type="password" name="pwd" placeholder="비밀번호">
		<input type="password" name="pwdRe" placeholder="비밀번호 재확인">
	</div>

	<div>
		<input type="text" name="name" placeholder="이름">
		<input type="tel" name="phone" placeholder="휴대폰 번호">
		<input type="email" name="email" placeholder="이메일">
		<input type="text" name="adress" placeholder="주소">
	</div>

	<div class="btn_area">
		<a href="../common/index.jsp" onclick="">회원가입</a>
	</div>
</div>
</section>

<footer>
</footer>
</div>
</body>