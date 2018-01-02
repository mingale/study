<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/jsp/common/setting.jsp" %>
<html>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
		errorAlert("승인 실패");
		window.history.back();
	</script>
</c:if>
<c:redirect url="order.ho" />
</body>
</html>