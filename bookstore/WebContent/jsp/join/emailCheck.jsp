<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script src="/bookstore/jsp/common/script.js"></script>
<link type="text/css" href="/bookstore/css/reset.css" rel="stylesheet">
<style type="text/css">
	span{font-size:30px;color:steelblue;margin:10px;}
	div{margin:20px;}
</style>
<body>
<!-- 회원 가입 인증 -->
<c:if test="${view == 0}">
	<c:if test="${cnt == 0}">
		<script type="text/javascript">
			alert(emailKeyError);
			window.close();
		</script>
	</c:if>
	
	<c:if test="${cnt != 0}">
		<script type="text/javascript">
			alert("이메일 인증을 완료했습니다.");
			window.location="index.do";
		</script>
	</c:if>
</c:if>

<!-- 아이디 찾기 인증 -->
<c:if test="${view == 1}">
	<c:if test="${cnt == 0}">
		<script type="text/javascript">
			alert(emailKeyError);
			window.close();
		</script>
	</c:if>
	
	<c:if test="${cnt != 0}">
		<div>
			회원 아이디는 <span>${id}</span>입니다.
		 </div>
	</c:if>
</c:if>

<!-- 비밀번호 찾기 인증 -->
<c:if test="${view == 2}">
	<c:if test="${cnt == 0}">
		<script type="text/javascript">
			alert(emailKeyError);
			window.close();
		</script>
	</c:if>
	
	<c:if test="${cnt != 0}">
		<div>
			<span>${id}</span>의 비밀번호는
			<span>${pwd}</span>입니다.
		</div>
	</c:if>
</c:if>
</body>
</html>