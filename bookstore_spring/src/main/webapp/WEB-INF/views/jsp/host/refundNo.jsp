<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/resources/setting.jsp" %>
<script src="${projectRes}script.js"></script>

<c:if test="${cnt == 0}">
	<script type="text/javascript">
		errorAlert(errorMsg);
	</script>
</c:if>
<c:if test="${cnt != 0}">
	<script type="text/javascript">
		alert("환불을 거부했습니다.");
		window.location="refund";
	</script>
</c:if>