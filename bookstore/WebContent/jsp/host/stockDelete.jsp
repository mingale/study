<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/jsp/common/setting.jsp" %>
<html>
<body>
	<c:if test="${cnt == 0}">
		<script type="text/javascript">
			errorAlert(deleteError);
		</script>
	</c:if>
	<c:if test="${cnt != 0}">
		<script type="text/javascript">
			alert("삭제 작업을 완료했습니다.");
			window.location="stock.ho";
		</script>
	</c:if>
</body>
</html>