<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width initial-scale=1">
	
	<title>관리자 - 공지 사항</title>
	
	<style type="text/css">
		section{margin:50px auto;text-align:center;}
		
		.content{width:500px;padding:5px;margin:5px;}
		.area{height:500px;}
		.btn{width:120px;height:25px;margin:10px 0;background:lightblue;}
	</style>
</head>
<body>
<%@ include file="header.jsp" %>

<section>
	<ul>
		<li>
			<div class="content">제목 : ${notice.title}</div>
		</li>
		<li>
			<div class="content area">${notice.content}</div>
		</li>
		<li><button class="btn" onclick="window.location='notice?pageNum=${pageNum}';">돌아가기</button></li>
	</ul>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>