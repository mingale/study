<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script src="${projectRes}script.js" ></script>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
		errorAlert("송장 입력 작업을 실패했습니다.\n잠시 후 다시 시도하세요.");
	</script>
</c:if>
<c:if test="${cnt != 0}">
	<script type="text/javascript">
		alert("배송을 시작합니다.");
		opener.parent.location.reload(); //부모 window 새로고침
		self.close();
	</script>
</c:if>
</body>
</html>