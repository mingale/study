<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<aside>
<div class="wrap">
	<span>다시 보기</span>
	<div class="quick_area">
		<c:forEach var="reB" items="${sessionScope.recent}">
			<a href="detail.do?no=${reB.no}">
				<img src="${imageBook}${reB.image}">
			</a>
		</c:forEach>
	</div>
	
	<a class="btn" href="#header">TOP</a>
	<a class="btn" href="#footer">DOWN</a>
</div>
</aside>