<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/resources/setting.jsp" %>
<html>
<head>
	<script src="${projectRes}script.js"></script>
</head>
<body>
<c:if test="${cnt == 0}">
	<script	type="text/javascript">
		errorAlert("사용할 수 있는 이메일입니다.");
	</script>
</c:if>
<c:if test="${cnt != 0}">
	<script type="text/javascript">
		errorAlert(email_chk_no_msg);
	</script>
</c:if>
</body>
</html>