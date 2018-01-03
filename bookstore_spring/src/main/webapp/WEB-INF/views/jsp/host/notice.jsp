<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewpost" content="width=device-width initial-scale=1">

	<title>관리자 - 공지 사항</title>

	<link type="text/css" rel="stylesheet" href="${boardC}">
	<link type="text/css" rel="stylesheet" href="${paginC}">
	
	<style>
		.div{height:250px;margin:0 auto;text-align:center;padding-top:100px;font-size:18px;}

		.title{width:730px;}
		.title_center{text-align:center;}
		.id{width:100px;text-align:center;}
		.date{width:100px;text-align:center;}
		
		#all_up{margin-left:10px;margin-right:3px;}
		
		.btn{width:120px;height:25px;margin-right:10px;margin-bottom:5px;background:lightblue;}
		.btn_margin{margin-top:20px;}
		.btn_right{float:right;}
		
		.nullContent{text-align:center;margin:50px 0;}
	</style>
</head>
<body>
<%@ include file="header.jsp" %>

<section>
<div class="wrap clearfix">
	<div class="container clearfix">
	<c:if test="${postCnt > 0}">
			<form class="clearfix" name="noticeForm" action="noticeWrite">
				<ul class="content">
					<li>
						<ul class="item zero clearfix">
							<li class="chk">번호</li>
							<li class="title title_center">제목</li>
							<li class="id">id</li>
							<li class="date">등록일</li>
						</ul>
					</li>
					<c:forEach var="i" items="${notices}">
						<li>
							<ul class="item clearfix">
								<li class="chk">
									${num}
									<c:set var="num" value="${num + 1}"/>
								</li>
								<li class="title"><a href="noticeView?pageNum=${pageNum}&idx=${i.idx}">${i.title}</a></li>
								<li class="id">${i.id}</li>
								<li class="date">${i.add_date}</li>
							</ul>
						</li>
					</c:forEach>
				</ul>
				<button class="btn btn_right" name="noticeWrite">글쓰기</button>
			</form>
		
		<div class="paging">
			<c:if test="${startPage > pageNav}">
				<a href="notice">시작</a>
				<a href="notice?pageNum=${startPage - pageNav}">전</a>
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}">
				<a href="notice?pageNum=${i}">${i}</a>
			</c:forEach>
			<c:if test="${endPage < navCnt}">
				<a href="notice?pageNum=${endPage + 1}">후</a>
				<a href="notice?pageNum=${navCnt}">끝</a>
			</c:if>
		</div>
	</c:if>
	<c:if test="${postCnt == 0}">
		<div class="nullContent">
			존재하는 공지 글이 없습니다.<br>
		<%-- <c:if test=""> --%>
			<button class="btn btn_margin" name="noticeWrite" onclick="window.location='noticeWrite'">글쓰기</button>
		<%-- </c:if> --%>
		</div>
	</c:if>
	</div>
</div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>