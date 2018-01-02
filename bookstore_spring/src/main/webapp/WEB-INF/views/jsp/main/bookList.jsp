<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/resources/setting.jsp" %>
<html>
<head>

<link type="text/css" rel="stylesheet" href="${projectRes}css/bookList.css">

</head>
<body>
<%@ include file="../common/header.jsp" %>

<section>
<div class="wrap">
	<div class="book_list">
		<ul class="clearfix">
			<c:forEach var="tagM" items="${tag_mains}">
				<li><a href="${tagM.tag_main}">${tagM.getTagMainName()}</a></li>
			</c:forEach>
		</ul>
	</div>
	
	<b class="page_title">${tagName}</b>
	
	<hr>
	<c:if test="${cnt != 0}">
		<div class="container">
			<ul class="clearfix">
				<c:forEach var="book" items="${books}">
					<li class="item clearfix">
						<!-- <input type="checkbox" id="check" name="list_check"> -->
		
						<a href="detail?no=${book.no}">
							<img src="${imageBook}${book.image}">
							
							<div class="info_area">
								${book.title}<br>
								${book.author}<br>
								${book.price}<br>
							</div>
						</a>
						
						<div class="btn_area">
							<form action="bookBtnPro">
								<input type="hidden" name="no" value="${book.no}">
								<input type="hidden" name="count" value="1">

								<input class="middleButton" type="submit" name="btn" value="바로 구매">
								<input class="middleButton" type="submit" name="btn" value="장바구니 담기">
								<input class="middleButton" type="submit" name="btn" value="찜하기">
							</form>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		
		<hr>
		
		<div class="paging">
			<c:if test="${startPage > pageNav}">
				<a href="bookList?tag=${tag}">시작</a>
				<a href="bookList?tag=${tag}&pageNum=${startPage - pageNav}">전</a>
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}">
				<a href="bookList?tag=${tag}&pageNum=${i}">${i}</a>
			</c:forEach>
			<c:if test="${endPage < pageCount}">
				<a href="bookList?tag=${tag}&pageNum=${endPage + 1}">후</a>
				<a href="bookList?tag=${tag}&pageNum=${pageCount}">끝</a>
			</c:if>
		</div>
	</c:if>
	<c:if test="${cnt == 0}">
		<div class="container">
			상품 준비 중입니다.
		</div>
	</c:if>
</div>
</section>

<c:if test="${sessionScope.recent != null}">
	<%@ include file="../common/aside.jsp" %>
</c:if>

<%@ include file="../common/footer.jsp" %>

</body>
</html>