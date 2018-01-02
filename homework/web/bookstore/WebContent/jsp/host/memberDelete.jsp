<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/jsp/common/setting.jsp" %>
<html>
<body>
<c:if test="${cnt == 0}">
	<script type="text/javascript">
		errorAlert("작업을 실패했습니다.");
	</script>
</c:if>
<c:if test="${cnt != 0}">
	<script type="text/javascript">
		alert("회원을 탈퇴시켰습니다.");
		window.location="member.ho";
	</script>
</c:if>
</body>
</html>