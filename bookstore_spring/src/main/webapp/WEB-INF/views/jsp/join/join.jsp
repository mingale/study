<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<html>
<head>
    <title>동네 서점</title>
    <link type="text/css" rel="stylesheet" href="${projectRes}css/join.css">
    
	<style>
		.container{padding:40px 0;}
	
		.content{overflow:scroll;font-size:12px;text-align:left;}
		li{margin:20px 0;}
		li label{font-size:14px;font-weight:bold;}
		li > div{width:440px;height:100px;margin-top:10px;border:1px solid #c6c6c6;}
		li input{float:right;}
		
		.top{width:100%;}
		.top h3{width:100%;float:left;}
		.top label{float:right;}
		
		.btn_area{width:auto;height:60px;text-align:center;margin:20px auto;}
		.btn_area .inputButton{float:left;width:49%;height:60px;font-size:24px;background:lightblue;margin:2px;}
		
		@media(max-width:400px) {
			.container{width:300px;}
			.content{width:auto;height:40px;}
		}
	</style>
</head>
<body>
<div class="wrap">
	<form class="container" action="signUp" name="joinForm" method="post" onsubmit="return joinCheck();">

		<h1 class="logo"><a href="index">BMS</a></h1>
	
		<div class="top clearfix">
			<h3>약관 동의</h3>
			<label>전체 선택 <input type="checkbox" name="allAgree" onclick="joinAll();"></label>
		</div>
		<hr>
	
		<div>
			<ul>
				<li class="clearfix">
					<label for="check1">이용약관 동의(필수)</label>
					<input type="checkbox" name="agree">
					<div class="content">
						<p>제 1 조 (목적)</p>
						<p>이 약관은 제반 서비스의 이용과 관련하여 회사와 회원과의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다.</p>
						<p>제 2 조 (정의)</p>
						<p>제 3 조 (약관의 게시와 개정)</p>
						<p>제 4 조 (약관의 해석)</p>
					</div>
				</li>
				<li class="clearfix">
					<label for="check2">개인정보 수집 및 이용에 대한 안내(필수)</label>
					<input type="checkbox" name="agree">
					<div class="content">
						<p>제 1 조 (목적)</p>
						<p>이 약관은 제반 서비스의 이용과 관련하여 회사와 회원과의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다.</p>
						<p>제 2 조 (정의)</p>
						<p>제 3 조 (약관의 게시와 개정)</p>
						<p>제 4 조 (약관의 해석)</p>
					</div>
				</li>
				<li class="clearfix">
					<label for="check3">위치정보 이용약관 동의(선택)</label>
					<input type="checkbox" name="agree">
					<div class="content">
						<p>제 1 조 (목적)</p>
						<p>이 약관은 제반 서비스의 이용과 관련하여 회사와 회원과의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다.</p>
						<p>제 2 조 (정의)</p>
						<p>제 3 조 (약관의 게시와 개정)</p>
						<p>제 4 조 (약관의 해석)</p>
					</div>
				</li>
				<li class="clearfix">
					<label for="check4">이벤트 등 프로모션 알림 메일 수신(선택)</label>
					<input type="checkbox" name="agree">
					<div class="content">
						<p>제 1 조 (목적)</p>
						<p>이 약관은 제반 서비스의 이용과 관련하여 회사와 회원과의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다.</p>
						<p>제 2 조 (정의)</p>
						<p>제 3 조 (약관의 게시와 개정)</p>
						<p>제 4 조 (약관의 해석)</p>
					</div>
				</li>
			</ul>
			</div>
	
		<div class="btn_area clearfix">
			<input class="inputButton" type="button" value="비동의" onclick="window.location='index'">
			<input class="inputButton" type="submit" value="동의">
		</div>
	</form>
</div>
</body>
</html>