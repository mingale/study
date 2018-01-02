<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<c:if test="${cnt == 1}">
		<script type="text/javascript">
			alert("회원 정보가 수정되었습니다.");
			window.history.back();
		</script>
	</c:if>
	<c:if test="">
		<script type="text/javascript">
			errorAlert("작업 실패. 다시 시도해주세요.");
		</script>
	</c:if>
</body>
</html>