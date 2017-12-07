<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<c:if test="${cnt == 1}">
		<script type="text/javascript">
			alert("이미 사용 중이거나 탈퇴한 아이디입니다.");
			window.history.back();
		</script>
	</c:if>
	<c:if test="${cnt != 1}">
		<script type="text/javascript">
			alert("사용할 수 있는 아이디입니다.");
			window.history.back();
		</script>
	</c:if>
</body>
</html>