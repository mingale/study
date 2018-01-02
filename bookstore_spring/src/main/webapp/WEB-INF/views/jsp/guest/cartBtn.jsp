<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<html>
<body>
<c:if test="${sessionScope.memId == null}">
	<script type="text/javascript">
		errorAlert(loginMsg);
	</script>
</c:if>
<c:if test="${sessionScope.memId != null}">
	<c:if test="${btn eq '장바구니 담기'}">
		<c:if test="${cnt == 0}">
			<script type="text/javascript">
				errorAlert("장바구니 담기에 실패했습니다.\n잠시 후에 다시 시도해주세요.");
			</script>
		</c:if>
		<c:if test="${cnt != 0}">
			<script type="text/javascript">
				alert("장바구니에 담았습니다.");
				window.history.back();
			</script>
		</c:if>
	</c:if>
	<c:if test="${btn eq '찜하기'}">
		<c:if test="${cnt == 0}">
			<script type="text/javascript">
				errorAlert("찜하기 작업을 실패했습니다.\n잠시 후에 다시 시도해주세요.");
			</script>
		</c:if>
		<c:if test="${cnt != 0 && cnt != 9}">
			<script type="text/javascript">
				alert("찜했습니다.");
				window.history.back();
			</script>
		</c:if>
		<c:if test="${cnt == 9}">
			<script type="text/javascript">
				alert("이미 찜한 도서입니다.");
				window.history.back();
			</script>
		</c:if>
	</c:if>
</c:if>
</body>
</html>