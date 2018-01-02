<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<script src="/bookstore/jsp/common/script.js"></script>
</head>
<body>
<c:if test="${cnt == 0}">
	<script	type="text/javascript">
		errorAlert("사용할 수 있는 이메일입니다.");
	</script>
</c:if>
<c:if test="${cnt != 0}">
	<script type="text/javascript">
	errorAlert("이미 사용 중이거나 탈퇴한 이메일입니다.");
	</script>
</c:if>
</body>
</html>