<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<html>
<body>
	<c:if test="${cnt == 1}">
		<script type="text/javascript">
			alert("인증 메일이 전송되었습니다.\n인증 후 로그인 가능합니다.");
			window.location="index?cnt=${cnt}";
		</script>
	</c:if>
	<c:if test="${cnt == 9}">
		<script type="text/javascript">
			errorAlert(email_chk_no_msg);
		</script>
	</c:if>
	<c:if test="${cnt != 1 && cnt != 9}">
		<script type="text/javascript">
			errorAlert("회원가입을 실패했습니다.\n다시 시도해주세요.");
		</script>
	</c:if>
</body>
</html>