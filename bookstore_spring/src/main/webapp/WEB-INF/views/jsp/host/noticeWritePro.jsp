<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%@ include file="/resources/setting.jsp" %>
<html>
	<head>
	</head>
	<script src="${projectRes}script.js"></script>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
		errorAlert("글쓰기 실패");
	</script>
</c:if>
<c:if test="${cnt != 0}">
	<script type="text/javascript">
		alert("글 등록 완료");
		window.location='notice';
	</script>
</c:if>
</body>
</html>