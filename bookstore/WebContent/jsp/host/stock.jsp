<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/jsp/common/setting.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewpost" content="width=device-width initial-scale=1">

	<title>관리자 - 재고 관리</title>

	<link type="text/css" rel="stylesheet" href="${boardC}">
	<link type="text/css" rel="stylesheet" href="${paginC}">
	
	<style>
		.number{width:20px;text-align:center;}
		.no{width:60px;text-align:center;}
		.title{width:380px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;}
		.author{width:100px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;text-align:center;}
		/* 
			white-space:nowrap-영역 상관없이 한줄 쓰기
			overflow:hidden 영역 벗어나면 문자 자르기
			text-overflow:ellipsis - 영역 벗어나면 말줄임표 사용
		 */
		.price{width:75px;text-align:center;}
		.count{width:75px;text-align:center;}
		.date{width:80px;text-align:center;}
		.item_btn_area{width:115px;text-align:center;}
		
		.current{color:red;}
		 
		#all_del{float:left;}
		#add{float:right;}
	</style>
</head>
<body>
<%@ include file="header.jsp" %>

<section>
<div class="wrap clearfix">

	<div class="container clearfix">
	
	<!-- 도서 재고가 존재하면 -->
	<c:if test="${cnt != 0}">
	<form name="stockForm" method="post">
		<ul class="content">
				<li>
				<ul class="item zero clearfix">
					<li class="number"></li>
					<li class="chk"><input type="checkbox" onclick="stockAll();"></li>
					<li class="no">번호</li>
					<li class="title">도서명</li>
					<li class="author">저자</li>
					<li class="price">가격</li>
					<li class="count">수량</li>
					<li class="date">등록일</li>
					<li class="item_btn_area">비고</li>
				</ul>
			</li>
			<c:forEach var="book" items="${books}">
				<li>
					<ul class="item clearfix">
						<li class="number">
							${number}
							<c:set var="number" value="${number-1}"/>
						</li>
						<li class="chk"><input class="list" type="checkbox" name="list" value="${book.no}"></li>
						<li class="no">
							${book.no}
							<input type="hidden" name="no" value="${book.no}">
						</li>
						<li class="title"><a href="detail.do?no=${book.no}">${book.title}</a></li>
						<li class="author">${book.author}</li>
						<li class="price">${book.price}</li>
						<li class="count">${book.count}</li>
						<li class="date">
							<c:forEach var="bSub" items="${bookSubs}">
								<c:if test="${book.no == bSub.no}">
									${bSub.add_date}
								</c:if>
							</c:forEach>
						</li>
						<li class="item_btn_area">
							<input class="boardButton" type="button" name="btn_up" value="수정" onclick="window.location='stockUpdate.ho?no=${book.no}';">
							<input class="boardButton" type="button" name="btn_del" value="삭제" onclick="stockDelete('${book.no}');">
						</li>
					</ul>
				</li>
			</c:forEach>
		</ul>
		
		<div class="btn_area">
			<input class="smallButton s1" type="button" name="all_del" value="선택 삭제" onmouseover="navOver('s1');" onmouseout="navOut('s1');" onclick="stockAllDelete();">
			<input class="smallButton s2" type="button" name="add" value="추가" onmouseover="navOver('s2');" onmouseout="navOut('s2');" onclick="window.location='stockAdd.ho?no=-1';">
		</div>
	
		<div class="paging">
			<c:if test="${startPage > pageNav}">
				<a href="stock.ho">처음</a>
				<a href="stock.ho?pageNum=${startPage - pageNav}">전</a>
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}">
				<c:if test="${i == currentPage}">
					<a class="current" href="stock.ho?pageNum=${i}">${i}</a>
				</c:if>
				<c:if test="${i != currentPage}">
					<a href="stock.ho?pageNum=${i}">${i}</a>
				</c:if>
			</c:forEach>
			<c:if test="${pageCount > endPage}">
				<a href="stock.ho?pageNum=${startPage + pageNav}">후</a>
				<a href="stock.ho?pageNum=${pageCount}">끝</a>
			</c:if>
		</div>
	</form>
	</c:if>
	
	<!-- 도서 재고가 존재하지 않으면 -->
	<c:if test="${cnt == 0}">
		<div class="btn_area">
			<input type="button" name="add" value="추가" onclick="window.location='stockAdd.ho?no=-1';">
		</div>
	</c:if>
	</div>
</div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>