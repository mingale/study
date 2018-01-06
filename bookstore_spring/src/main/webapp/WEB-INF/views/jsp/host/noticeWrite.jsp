<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width initial-scale=1">
	
	<title>관리자 - 공지 사항</title>
	
	<link type="text/css" rel="stylesheet" href="${projectRes}css/notice.css">
</head>
<body>
<%@ include file="header.jsp" %>

<section>
	<div class="wrap">
		<c:if test="${notice == null}">
			<form action="noticeWritePro" method="post">
				<ul>
					<li><p>제목</p></li> 
					<li><input class="input" type="text" name="title" maxlength="50"></li>
					<li><p>내용</p></li>
					<li><textarea class="input area" name="content"></textarea></li>
					<li class="li_btn"><button class="btn">글쓰기</button></li>
				</ul>
			</form>
		</c:if>
		<c:if test="${notice != null}">
			<form action="noticeWritePro" method="post">
				<input type="hidden" name="idx" value="${notice.idx}">
				<ul>
					<li><p>제목</p></li> 
					<li><input class="input" type="text" name="title" value="${notice.title}" maxlength="50"></li>
					<li><p>내용</p></li>
					<li><textarea class="input area" name="content">${notice.content}</textarea></li>
					<li class="li_btn"><button class="btn">수정</button></li>
				</ul>
			</form>
		</c:if>
	</div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>