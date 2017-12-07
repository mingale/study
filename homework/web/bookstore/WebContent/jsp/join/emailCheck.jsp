<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script src="/bookstore/jsp/common/script.js"></script>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
		alert("이메일 인증을 실패했습니다.");
		window.close();
	</script>
</c:if>

<c:if test="${cnt != 0}">
	<script type="text/javascript">
		alert("이메일 인증을 완료했습니다.");
		window.location="index.do";
	</script>
</c:if>
</body>
</html>