<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/jsp/common/setting.jsp" %>
<html>
<body>

	<c:if test="${cnt != 1}">
		<script type="text/javascript">
			errorAlert("회원 정보가 일치하지 않습니다.");
		</script>
	</c:if>
	
	<c:if test="${cnt == 1}">
		<c:if test="${rating == 1}">
			<c:redirect url="main.ho"/>
		</c:if>
		<c:if test="${rating == 4}">
			<script type="text/javascript">
				errorAlert("email 인증이 필요합니다.\n인증 후 다시 시도해주세요");
			</script>
		</c:if>
		<c:if test="${rating != 1 && rating != 4}">
			<c:redirect url="index.do"/>
		</c:if>
	</c:if>

</body>
</html>