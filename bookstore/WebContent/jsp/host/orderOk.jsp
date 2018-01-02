<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/jsp/common/setting.jsp" %>
<html>
<script src="/bookstore/jsp/common/script.js"></script>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
		errorAlert("승인 실패");
	</script>
</c:if>
<c:if test="${cnt != 0 && cnt != 9}">
	<c:redirect url="order.ho" />
</c:if>
<c:if test="${cnt == 9}">
	<script type="text/javascript">
		errorAlert("재고가 부족합니다.");
	</script>
</c:if>
</body>
</html>