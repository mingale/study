<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/jsp/common/setting.jsp" %>
<html>
<body>
<c:if test="${cnt == 0}">
    <script type="text/javascript">
        errorAlert("주문 작업을 실패했습니다.\n잠시 후 다시 시도해주세요.");
    </script>
</c:if>
<c:if test="${cnt != 0}">
    <script type="text/javascript">
        alert("주문이 완료되었습니다.");
        window.location="myOrder.go";
    </script>
</c:if>
</body>
</html>