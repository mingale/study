<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/jsp/common/setting.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>관리자 페이지</title>
<style>
	form{margin:30px 0 30px 120px;}
	form li{margin:30px 0;}
	.book ul{float:left;}
	.book .image{float:left;width:300px;height:270px;margin:30px 0 0 30px;}
	.image input{width:100%;}
	.book img{width:220px;height:270px;}
	textarea{resize:none;width:650px;height:100;}
	span{display:block;width:100px;max-width:100px;float:left;font-size:14px;}
	input{width:400px;height:30px;}
	.inputButton{display:block;margin:0 auto;}
</style>
</head>
<body>
<%@ include file="header.jsp" %>
<section>
<div class="wrap clearfix" onload="updateFocus();">
	<form action="stockUpdatePro.ho" name="stockUpdateForm" method="post" enctype="multipart/form-data" onsubmit="return updateCheck();">

		<c:if test="${no != -1}">
			<input type="hidden" name="no" value="${book.no}">

			<div class="book clearfix">
				<ul class="clearfix">
					<li><span>도서명</span><input type="text" name="title" value="${book.title}"></li>
					<li><span>저자</span><input type="text" name="author" value="${book.author}"></li>
					<li><span>출판사</span><input type="text" name="publisher" value="${book.publisher}"></li>
					<li><span>출판일</span><input type="date" name="pub_date" value="${book.pub_date}"></li>
					<li><span>가격</span><input type="text" name="price" value="${book.price}"></li>
					<li><span>수량</span><input type="text" name="count" value="${book.count}"></li>
					<li>
						<span>태그</span>
						<select name="tag_main">
							<c:forEach var="tag_main" items="${tag_mains}">
								<c:if test="${tag_main eq bookSub.tag_main}">
									<option value="${tag_main}" selected>${tag_main}</option>
								</c:if>
								<c:if test="${tag_main ne bookSub.tag_main}">
									<option value="${tag_main}">${tag_main}</option>
								</c:if>
							</c:forEach>
						</select>
						<select name="tag">
							<c:forEach var="tag" items="${tags}">
								<c:if test="${tag eq bookSub.tag}">
									<option value="${tag}" selected>${tag}</option>
								</c:if>
								<c:if test="${tag ne bookSub.tag}">
									<option value="${tag}">${tag}</option>
								</c:if>
							</c:forEach>
						</select>
					</li>
				</ul>
				<div class="image">
					<input type="file" name="imgFile" onchange="imageLoad(this);">
					<img id="loadImg" src="${imageBook}${book.image}">
					<input type="hidden" name="image" value="${book.image}">
				</div>
			</div>
			<ul>
				<li><span>책소개</span><textarea name="intro">${bookSub.intro}</textarea></li>
				<li><span>목차</span><textarea name="list">${bookSub.list}</textarea></li>
				<li><span>저자 소개</span><textarea name="pub_intro">${bookSub.pub_intro}</textarea></li>
				<li><span>출판사 서평</span><textarea name="review">${bookSub.review}</textarea></li>
				<li>
					<input type="submit" value="수정">
					<input type="reset"  value="취소">
				</li>
			</ul>
		</c:if>
		
		<c:if test="${no == -1}">
			<input type="hidden" name="no" value="${no}">
			
			<div class="book clearfix">
				<ul class="clearfix">
					<li><span>도서명</span><input type="text" name="title"></li>
					<li><span>저자</span><input type="text" name="author"></li>
					<li><span>출판사</span><input type="text" name="publisher"></li>
					<li><span>출판일</span><input type="date" name="pub_date" value="${book.pub_date}"></li>
					<li><span>가격</span><input type="text" name="price"></li>
					<li><span>수량</span><input type="text" name="count"></li>
					<li>
						<span>태그</span>
						<select name="tag_main">
							<c:forEach var="tag_main" items="${tag_mains}">
								<option value="${tag_main}">${tag_main}</option>
							</c:forEach>
						</select>
						<select name="tag">
							<c:forEach var="tag" items="${tags}">
								<c:if test="${tag eq 'null'}">
									<option value="${tag}" selected>${tag}</option>
								</c:if>
								<c:if test="${tag ne 'null'}">
									<option value="${tag}">${tag}</option>
								</c:if>
							</c:forEach>
						</select>
					</li>
				</ul>
				<div class="image">
					<input type="file" name="imgFile" onchange="imageLoad(this);">
					<img id="loadImg">
					<input type="hidden" name="image" value="000.jpg">
				</div>
			</div>
			<ul>
				<li><span>책소개</span><textarea name="intro"></textarea></li>
				<li><span>목차</span><textarea name="list"></textarea></li>
				<li><span>저자 소개</span><textarea name="pub_intro"></textarea></li>
				<li><span>출판사 서평</span><textarea name="review"></textarea></li>
			</ul>	
					<input class="inputButton" type="submit" value="추가">
		</c:if>
	</form>
</div>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>