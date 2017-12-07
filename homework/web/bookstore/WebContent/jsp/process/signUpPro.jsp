<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/jsp/common/setting.jsp" %>
<html>
<body>
	<c:if test="${cnt == 1}">
		<script type="text/javascript">
			alert("회원가입을 축하드립니다. 로그인하세요.");
		</script>
		<c:redirect url="index.do?cnt=${cnt}"/>
	</c:if>
	<c:if test="${cnt != 1}">
		<script type="text/javascript">
			errorAlert("회원가입을 실패했습니다. 다시 시도해주세요.");
		</script>
	</c:if>
</body>
</html>