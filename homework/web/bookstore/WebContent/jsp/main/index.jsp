<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<style>
		.container h3{font-size:18px;font-weight:bold;}
		.content{width:auto;margin:0 auto;padding-bottom:50px;}
		.content ul{padding-left:20px;}
		.content li{float:left;width:130px;margin:10px 18px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;} /* 텍스트 영역 정리 안하면 자동 정렬 때 이상하게 됨 */
		.content img{width:auto;height:170px;background-color:#f0ffff;margin-bottom:5px;}
		
		span{margin-bottom:5px;}
		
		.slider{background-color:#f0ffff;margin-bottom:50px;}
		
		.bTitle{margin-bottom:8px;}
		.bPrice{color:cornflowerblue;}
	</style>
</head>
<body onload="showSlides();">
<%@ include file="/jsp/common/header.jsp" %>

<div class="slideContainer">
	<div class="mySlides fade">
		<img class="slideImg" src="/bookstore/images/slide/slide01.jpg">
	</div>
	<div class="mySlides fade">
		<img class="slideImg" src="/bookstore/images/slide/slide02.jpg">
	</div>
	<div class="mySlides fade">
		<img class="slideImg" src="/bookstore/images/slide/slide03.jpg">
	</div>
	<div class="mySlides fade">
		<img class="slideImg" src="/bookstore/images/slide/slide04.jpg">
	</div>		
</div>

<section>
<div class="wrap">
	<c:set var="start" value="0"/>
	<c:set var="end" value="5"/>

	<div class="container">
		<div class="content">
		<h3>베스트 셀러</h3>
		<hr>
			<c:if test="${best != null}">
				<ul class="clearfix">
					<c:forEach var="best" items="${best}" begin="${start}" end="${end}">
						<li>
							<a href="detail.do?no=${best.no}">
								<img src="${imageBook}${best.image}">
								<div class="bTitle">${best.title}</div>
								<span class="bPrice"><fmt:formatNumber value="${best.price}" type="number"/></span>
							</a>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${best == null}">
				상품 준비 중입니다.
			</c:if>
		</div>

		<div class="content">
		<h3>신간 도서</h3>
		<hr>
			<c:if test="${newB != null}">
				<ul class="clearfix">
					<c:forEach var="newB" items="${newB}" begin="${start}" end="${end}">
					<li>
						<a href="detail.do?no=${newB.no}">
							<img src="${imageBook}${newB.image}">
							<div class="bTitle">${newB.title}</div>
							<span class="bPrice"><fmt:formatNumber value="${newB.price}" type="number"/></span>
						</a>
					</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${newB == null}">
				상품 준비 중입니다.
			</c:if>
		</div>

		<div class="content">
		<h3>추천 도서</h3>
		<hr>
			<c:if test="${good != null}">
				<ul class="clearfix">
					<c:forEach var="good" items="${good}" begin="${start}" end="${end}">
					<li>
						<a href="detail.do?no=${good.no}">
							<img src="${imageBook}${good.image}">
							<div class="bTitle">${good.title}</div>
							<span class="bPrice"><fmt:formatNumber value="${good.price}" type="number"/></span>
						</a>
					</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${good == null}">
				상품 준비 중입니다.
			</c:if>
		</div>
	</div>
</div>
</section>

<c:if test="${sessionScope.recent != null}">
	<%@ include file="/jsp/common/aside.jsp" %>
</c:if>

<%@include file="/jsp/common/footer.jsp" %>
</body>
</html>
<!-- 
float layout을 사용하면 부모에게 clearfix 주기.
inline-block은 되도록 사용하지 않기!
-->
<!--
style과 script는 따로 파일 만들어서 작성하기. html 내에서 하는 건 인라인이라 좋지 않음
-->
<!-- 
정적 include와 동적 include
https://m.blog.naver.com/PostView.nhn?blogId=elren&logNo=220923789050&proxyReferer=https%3A%2F%2Fwww.google.co.kr%2F
차이점: 동적 include가 빠르다. 변수가 겹쳐 문제가 발생할 수 있다.
http://12bme.tistory.com/135
-->