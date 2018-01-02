<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script src="/bookstore/jsp/common/script.js"></script>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
	errorAlert(updateError);
	</script>
</c:if>
<c:if test="${cnt != 0}">
	<script type="text/javascript">
		alert("수정되었습니다.");
		window.location="member.ho";
	</script>
</c:if>
</body>
</html>