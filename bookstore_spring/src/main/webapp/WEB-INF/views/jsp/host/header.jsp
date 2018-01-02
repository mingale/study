<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<head>
	<meta name="viewport" content="width=device-width initial-scale=1">
	<link type="text/css" rel="stylesheet" href="${projectRes}css/a_common.css">
</head>
<header>
<div class="wrap clearfix">
	<a href="index?memId=${memId}" class="logo">BMS</a>
	<nav class="gnb">
		<ul class="clearfix">
			<li><a href="main">메인</a></li>
			<li><a href="stock">재고 관리</a></li>
			<li><a href="hostOrder">주문 관리</a></li>
			<li><a href="member">회원 관리</a></li>
			<li><a href="signOut">로그아웃</a></li>
		</ul>
	</nav>
</div>
</header>