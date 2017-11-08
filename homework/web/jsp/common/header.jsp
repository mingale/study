<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<header>
<div class="wrap">
	<div class="menu_gnb clearfix">
		<a href="../main/index.jsp" class="logo">logo</a>
	
		<nav class="gnb">
			<a href="../join/login.jsp"">로그인</a><!-- 로그인 완료 -> 회원명 -->
			<a href="../guest/mypage.jsp">마이페이지</a>
			<a href="../guest/cart.jsp">장바구니</a>
			<a href="../main/help.jsp">고객센터</a>
			<a href="../main/index.jsp" id="logout">로그아웃</a><!-- 로그인 활성화 -> 로그아웃 비활성화 -->
		</nav>
	</div>
	<form name="index" method="post" class="search_area">
		<input type="text" id="search"><input type="submit" value="검색">
	</form>
	<nav class="sub_gnb">
		<a href="../main/bookList.jsp">베스트셀러</a>
		<a href="../main/bookList.jsp">신간도서</a>
		<a href="../main/bookList.jsp">추천도서</a>
		<a href="../main/bookList.jsp">국내도서</a>
		<a href="../main/bookList.jsp">외국도서</a>
		<a href="../main/bookList.jsp">중고장터</a>
	</nav>
</div>
</header>