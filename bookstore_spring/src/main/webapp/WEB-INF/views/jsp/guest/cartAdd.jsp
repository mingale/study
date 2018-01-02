<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/resources/setting.jsp" %>
<html>
<style>
	body{padding:auto o;text-align:center;}
</style>
<body>
<c:if test="${cnt == 0}">
	장바구니 담기를 실패했습니다.
</c:if>
<c:if test="${cnt != 0}">
	<br>
	상품을 장바구니에 담았습니다.
	<br>
	<br>
	<input type="button" value="확인" onclick="self.close()">
</c:if>
</body>
</html>