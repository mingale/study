<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<html>
<body>
	<c:if test="${cnt == 0}">
		<script type="text/javascript">
			errorAlert(updateError);
		</script>
	</c:if>
	
	<c:if test="${cnt != 0}">
		<script type="text/javascript">
			alert("작업이 완료되었습니다.");
			window.location="stock";
		</script>
	</c:if>
</body>
</html>