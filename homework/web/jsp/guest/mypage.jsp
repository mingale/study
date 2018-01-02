<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width initial-scale=1">
    <title>동네 서점</title>

	<link rel="stylesheet" href="../../css/reset.css">
	<link rel="stylesheet" href="../../css/common.css">
	<style>
		section .list{float:left;width:100px;padding:20px;}
		section .list a{display:block;width:100px;height:40px;line-height:40px;text-align:center;}

		.content{float:left;width:400px;padding:10px 200px;}
		.content div{width:400px;border:1px solid #c6c6c6;margin-bottom:20px;}
		.content input{width:388px;height:50px;border:none;padding:5px;}
		.content .btn_area a{width:100%;display:block;height:50px;line-height:50px;text-align:center;}

	</style>
</head>

<body>

<%@ include file="../common/header.jsp" %>

<section>
<div class="wrap clearfix">

	<div class="list">
		<a href="./mypage.jsp">회원 정보</a>
		<a href="./order.jsp">주문 내역</a>
		<a href="./refund.jsp">환불 내역</a>
	</div>

	<div class="content">
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
			<a href="../main/index.jsp" onclick="">수정</a>
		</div>
	</div>
</div>
</section>

<%@ include file="../common/footer.jsp" %>

</body>
</html>