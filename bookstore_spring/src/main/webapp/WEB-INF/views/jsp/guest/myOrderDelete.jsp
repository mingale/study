<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<html>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
		errorAlert("주문 취소를 실패했습니다.\n잠시 후에 다시 시도하세요.");
	</script>
</c:if>
<c:if test="${cnt != 0}">
	<script type="text/javascript">
		alert("주문을 취소했습니다.");
	</script>
	<c:redirect url="myOrder" />
</c:if>
</body>
</html>