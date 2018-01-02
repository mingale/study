<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/jsp/common/setting.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewpost" content="width=device-width initial-scale=1">

	<title>관리자 - 회원 관리</title>

	<link type="text/css" rel="stylesheet" href="${boardC}">
	<link type="text/css" rel="stylesheet" href="${paginC}">
	
	<style>
		.div{height:250px;margin:0 auto;text-align:center;padding-top:100px;font-size:18px;}

		.id{width:120px;}
		.name{width:100px;}
		.phone{width:100px;}
		.email{width:180px;}
		.memo{width:240px;}
		.memo input{width:100%;border:none;background:ghostwhite;}
		.rank{width:40px;text-align:center;}
		.item_btn_area{width:115px;}
		
		#all_up{margin-left:10px;margin-right:3px;}
	</style>
</head>
<body>
<%@ include file="header.jsp" %>

<section>
<div class="wrap clearfix">
	<c:if test="${cnt != 0}">
		<div class="container clearfix">
			<form name="memUpForm">  <!--  action="memberUpdate.ho" -->
				<ul class="content">
					<li>
						<ul class="item zero clearfix">
							<li class="chk"><input type="checkbox" name="memAll" onclick="memAllCheck();"></li>
							<li class="id">아이디</li>
							<li class="name">이름</li>
							<li class="phone">번호</li>
							<li class="email">이메일</li>
							<li class="memo">메모</li>
							<li class="rank">등급</li>
							<li class="item_btn_area">비고</li>
						</ul>
					</li>
					<c:forEach var="mem" items="${members}">
						<li>
							<ul class="item clearfix">
								<li class="chk">
									<input class="list" type="checkbox" name="list" value="${mem.id}">
									<!-- admin의 checkbox를 없애면 전체 수정 때 for문의 i값이 맞지 않는 현상 발생 -->
								</li>
								<li class="id">
									${mem.id}
									<input type="hidden" name="id" value="${mem.id}">
								</li>
								<li class="name">${mem.name}</li>
								<li class="phone">${mem.phone}</li>
								<li class="email">${mem.email}</li>
								<li class="memo"><input type="text" name="memo" value="${mem.memo}" id="memo${mem.id}"></li>
								<li class="rank">
									<c:if test="${mem.id eq 'admin'}">
										1
										<input type="hidden" name="rating" value="1" id="rating${mem.id}">
									</c:if>
									<c:if test="${mem.id ne 'admin'}">
										<input type="number" name="rating" min="1" max="4" value="${mem.rating}" id="rating${mem.id}">
									</c:if>
								</li>
								<li class="item_btn_area">
									<input class="boardButton" type="button" value="수정" onclick="memUpdate('${mem.id}');">
									<input class="boardButton" type="button" value="강퇴" onclick="window.location='memberDelete.ho?chk=0&id=${mem.id}';">
								</li>
							</ul>
						</li>
					</c:forEach>
				</ul>
			</form>
			
			<div class="btn_area">
				<input class="smallButton s1" type="button" value="선택 수정" onclick="allMemberUpdate();" onmouseover="navOver('s1');" onmouseout="navOut('s1');">
				<input class="smallButton s2" type="button" value="선택 탈퇴" onclick="allMemberDelete();" onmouseover="navOver('s2');" onmouseout="navOut('s2');">
			</div>
		
			<div class="paging">
				<c:if test="${startPage > pageNav}">
					<a href="member.ho">시작</a>
					<a href="member.ho?pageNum=${startPage - pageNav}">전</a>
				</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}">
					<a href="member.ho?pageNum=${i}">${i}</a>
				</c:forEach>
				<c:if test="${endPage < pageCnt}">
					<a href="member.ho?pageNum=${endPage + 1}">후</a>
					<a href="member.ho?pageNum=${pageCnt}">끝</a>
				</c:if>
			</div>
		</div>
	</c:if>
	
	<c:if test="${cnt == 0}">
		<div class="div">회원을 모으시게나</div>
	</c:if>
</div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>