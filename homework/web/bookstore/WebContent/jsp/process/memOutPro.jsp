<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${cnt == 1}">
<%
	response.sendRedirect("/bookstore/index.do?cnt=0");
%>
</c:if>
<c:if test="${cnt != 1}">
	<script type="text/javascript">
		errorAlert("회원 탈퇴 작업이 실패했습니다. 다시 시도해주세요.");
	</script> 
</c:if>