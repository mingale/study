<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<html>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
		alert("작업을 실패했습니다.");
	</script>
</c:if>
<c:redirect url="cart" />
</body>
</html>