<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<style>
		.content{float:left;width:400px;padding:100px 200px;}
		.dv{width:400px;border:1px solid #c6c6c6;margin-bottom:20px;}
		.btn_area .memout{float:right;}
	</style>
</head>

<body onload="navFocus('mp');">
<%@ include file="/jsp/common/header.jsp" %>
<c:if test="${cnt == 1}">

	<section>
	<div class="wrap clearfix">
	
	<%@ include file="/jsp/guest/myPageNav.jsp" %>
	
		<form class="content" action="/bookstore/myPagePro.do" name="signUpForm" onsubmit="return signUpCheck();">
			<div class="dv">
				<input class="input" name="id" value="${m.getId()}">
				<input class="input" type="password" name="pwd" value="${m.getPwd()}">
				<input class="input" type="password" name="repwd" value="${m.getPwd()}">
			</div>
		
			<div class="dv">
				<input class="input" type="text" name="name" value="${m.getName()}">
				<c:if test="${m.getPhone() != null}">
					<input class="input" type="tel" name="phone" value="${m.getPhone()}" onblur="confirmPh()">
				</c:if>
				<c:if test="${m.getPhone() == null}">
					<input class="input" type="tel" name="phone" placeholder="휴대폰 번호">
				</c:if>
				<input class="input" type="email" name="email" value="${m.getEmail()}">
				<input class="input" type="text" name="address" value="${m.getAddress()}">
			</div>
		
			<div class="btn_area clearfix">
				<button class="inputButton" type="submit">수정</button>
				<input class="memout" type="button" value="회원 탈퇴" onclick="window.location='/bookstore/memOutPro.do'">
			</div>
		</form>
	</div>
	</section>
	
</c:if>

<c:if test="${cnt != 1}">
	<script type="text/javascript">
		errorAlert(pwdCheck_msg);
	</script>
</c:if>
<%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>