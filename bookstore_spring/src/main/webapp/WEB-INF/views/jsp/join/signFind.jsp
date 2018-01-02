<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/resources/setting.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script src="${projectRes}script.js"></script>
	<style type="text/css">
		div{text-align:center;margin-top:50px;}
		span{color:steelblue;font-size:24px;}
	</style>
</head>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
		errorAlert(emailSendError + "\n정보를 다시 한 번 확인해주세요.");
	</script>
</c:if>
<c:if test="${cnt != 0}">
	<div>
		<span>${email}</span>
		<p>로 인증 메일이 전송되었습니다.</p>	
	</div>	
</c:if>
</body>
</html>