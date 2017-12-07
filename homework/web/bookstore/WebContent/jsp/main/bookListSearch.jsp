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
		.info_area{float:left;width:600px;margin:5px 20px;word-wrap:break-word;}
		/* word-wrap:break-word
		가로 사이즈나 엘러먼트에 맞춰서 강제 줄 바꿈 해준다. word-break:break-all; 같은결과물
		 */
		.btn_area{float:left;width:150px;}
		.btn_area input{width:100%;float:left;}
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
				<c:forEach var="b" items="${book}">
					<li class="item clearfix">
						<!-- <input type="checkbox" id="check" name="list_check"> -->
		
						<a href="detail.do?no=${b.no}">
							<img src="${imageBook}${b.image}">
							
							<div class="info_area">
								${b.title}<br>
								${b.author}<br>
								${b.price}<br>
							</div>
						</a>
						
						<div class="btn_area">
							<form action="bookBtnPro.go">
								<input type="hidden" name="no" value="${b.no}">
								<input type="hidden" name="count" value="1">

								<input class="smallButton" type="submit" name="btn" value="바로 구매">
								<input class="smallButton" type="submit" name="btn" value="장바구니 담기">
								<input class="smallButton" type="submit" name="btn" value="찜하기">
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
			검색하신 상품이 존재하지 않습니다.
		</div>
	</c:if>
</div>
</section>

<c:if test="${sessionScope.recent != null}">
<aside>
<div class="wrap">
	최근 본 도서
	<div class="quick_area">
		<c:forEach var="reB" items="${sessionScope.recent}">
			<a href="detail.do?no=${reB.no}"><img src="${imageBook}${reB.image}"></a>
		</c:forEach>
	</div>
	
	<a href="#header">TOP</a><br>
	<a href="#footer">DOWN</a>
</div>
</aside>
</c:if>

<%@ include file="/jsp/common/footer.jsp" %>

</body>
</html>