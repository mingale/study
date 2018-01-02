<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<html>
<script src="${projectRes}script.js"></script>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
		errorAlert("주문 삭제를 실패했습니다.");
	</script>
</c:if>
<c:if test="${cnt != 0}">
	<script type="text/javascript">
		alert("주문을 삭제했습니다.");
		window.location="hostOrder";
	</script>
</c:if>
</body>
</html>