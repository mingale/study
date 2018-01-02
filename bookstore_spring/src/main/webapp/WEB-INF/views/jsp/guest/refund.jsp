<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/resources/setting.jsp" %>
<html>
<script src="${projectRes}script.js"></script>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
		errorAlert("환불 신청을 실패했습니다.");
	</script>
</c:if>
<c:if test="${cnt != 0}">
	<script type="text/javascript">
		alert("환불 신청을 완료했습니다.");
		window.location="myRefund";
	</script>
</c:if>
</body>
</html>