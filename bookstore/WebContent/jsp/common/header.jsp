<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/jsp/common/setting.jsp" %>
<head>
	<link type="text/css" rel="stylesheet" href="/bookstore/css/common.css">
	<link type="text/css" rel="stylesheet" href="/bookstore/css/paging.css">
	
	<title>동네 서점</title>
</head>

<header id="header">
<div class="wrap">
	<div class="menu_gnb clearfix">
		<a href="index.do" class="logo">BMS</a>

	<!-- 기본 -->
	<c:if test="${sessionScope.memId == null}">
		<nav class="gnb">
			<a href="signIn.do">로그인</a>
			<a href="help.do">고객센터</a>
		</nav>
	</c:if>
	
	<!-- 로그인 -->
	<c:if test="${sessionScope.memId != null}">
		<nav class="gnb">
			<a href="myPagePwd.do">마이페이지</a>
			<a href="cart.go">장바구니</a>
			<a href="help.do">고객센터</a>
			<a href="signOut.do">로그아웃</a>
		</nav>
	</c:if>
	</div>
	
	<form class="search_area" action="bookSearch.do" name="indexForm" method="post">
		<input class="search" type="text" name="search"><input class="searButton" type="submit" value="검색">
	</form>
	<nav class="sub_gnb">
		<a href="bookList.do?tagName=베스트셀러&tag=best">베스트셀러</a>
		<a href="bookList.do?tagName=신간도서&tag=new">신간도서</a>
		<a href="bookList.do?tagName=추천도서&tag=good">추천도서</a>
		<a href="bookList.do?tagName=국내도서&tag=dom">국내도서</a>
		<a href="bookList.do?tagName=외국도서&tag=fore">외국도서</a>
		<!-- <a href="bookList.do?tagName=중고장터&tag=used">중고장터</a> -->
	</nav>
</div>
</header>