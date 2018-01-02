<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<style>
		.book_list{padding-left:50px;font-size:12px;margin-bottom:25px;border:1px solid #c6c6c6;background-color:#fff;}
		.book_list li{float:left;width:100px;padding:5px 3px}
		
		.page_title{padding:10px 0;font-size:18px;}
		
		.item{margin:30px 0;}
		.item > input{float:left;}
		.item img{float:left;width:120px;height:150px;padding-left:30px;}
		.info_area{float:left;width:auto;margin:5px 20px;word-wrap:break-word;}
		
		.btn_area{float:right;width:150px;margin:22px 0;}
		.btn_area input{width:100%;float:left;}
		
		@media(max-width:400px){
			.info_area{width:185px;}
			.btn_area{display:none;}
		}
	</style>
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>

<section>
<div class="wrap">
	<div class="book_list">
		<ul class="clearfix">
			<c:forEach var="tag_main" items="${tag_mains}">
				<li><a href="#">${tag_main}</a></li>
			</c:forEach>
		</ul>
	</div>
	
	<b class="page_title">${tagName}</b>

	<!-- <div class="check_area clearfix">
		<input type="button" id="all_check" value="전체 선택">
		<input type="button" id="all_wish" value="선택 찜하기" class="check_right">
		<input type="button" id="all_order" value="선택 바로 구매하기" class="check_right">
		<input type="button" id="all_cart" value="선택 장바구니 담기" class="check_right">
	</div> -->
	
	<hr>
	<c:if test="${cnt != 0}">
		<div class="container">
			<ul class="clearfix">
				<c:forEach var="book" items="${books}">
					<li class="item clearfix">
						<!-- <input type="checkbox" id="check" name="list_check"> -->
		
						<a href="detail.do?no=${book.no}">
							<img src="${imageBook}${book.image}">
							
							<div class="info_area">
								${book.title}<br>
								${book.author}<br>
								${book.price}<br>
							</div>
						</a>
						
						<div class="btn_area">
							<form action="bookBtnPro.go">
								<input type="hidden" name="no" value="${book.no}">
								<input type="hidden" name="count" value="1">

								<input class="middleButton" type="submit" name="btn" value="바로 구매">
								<input class="middleButton" type="submit" name="btn" value="장바구니 담기">
								<input class="middleButton" type="submit" name="btn" value="찜하기">
								<!--input class="middleButton" type="button" id="btn_cart" value="장바구니 담기"
							onclick="window.open('cartAdd.go?no=${book.no}&count=1', 'cartAdd', 'menubar=no, top=' + window.screenTop + ', left=' + window.screenLeft +' width=300, height=100, resizable=no');"-->
							</form>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		
		<hr>
		
		<div class="paging">
			<c:if test="${startPage > pageNav}">
				<a href="bookList.do?tag=${tag}">시작</a>
				<a href="bookList.do?tag=${tag}&pageNum=${startPage - pageNav}">전</a>
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}">
				<a href="bookList.do?tag=${tag}&pageNum=${i}">${i}</a>
			</c:forEach>
			<c:if test="${endPage < pageCount}">
				<a href="bookList.do?tag=${tag}&pageNum=${endPage + 1}">후</a>
				<a href="bookList.do?tag=${tag}&pageNum=${pageCount}">끝</a>
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
	<%@ include file="/jsp/common/aside.jsp" %>
</c:if>

<%@ include file="/jsp/common/footer.jsp" %>

</body>
</html>