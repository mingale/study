<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/jsp/common/setting.jsp" %>
<head>
	<meta name="viewport" content="width=device-width initial-scale=1">
	<link type="text/css" rel="stylesheet" href="/bookstore/css/a_common.css">
</head>
<header>
<div class="wrap clearfix">
	<a href="index.do?memId=${memId}" class="logo">BMS</a>
	<nav class="gnb">
		<ul class="clearfix">
			<li><a href="main.ho">메인</a></li>
			<li><a href="stock.ho">재고 관리</a></li>
			<li><a href="order.ho">주문 관리</a></li>
			<li><a href="member.ho">회원 관리</a></li>
			<li><a href="signOut.ho">로그아웃</a></li>
		</ul>
	</nav>
</div>
</header>