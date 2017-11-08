<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width initial-scale=1">
    <title>동네 서점</title>

	<link rel="stylesheet" href="../../css/reset.css">
    <link rel="stylesheet" href="../../css/join.css">
	<style>
		section .container{width:400px;padding:10px 20px;margin:0 auto;border:1px solid #c6c6c6;}
		.content{overflow:scroll;font-size:12px;text-align:left;}
		li{margin:20px 0;}
		li > div{width:400px;height:100px;margin-top:10px;border:1px solid #c6c6c6;}
		li input{float:right;}
		
		.top{width:100%;}
		.top h3{width:100%;float:left;}
		.top label{float:right;}
		
		.btn_area{width:440px;height:60px;text-align:center;margin:20px auto;}
		.btn_area a{width:218px;border:1px solid #c6c6c6;}
		.btn_default{float:left;}
		.btn_primary{float:left;}
	</style>
</head>
<body>
<header>
	<h1><a href="../common/index.jsp">logo</a></h1>
</header>

<section>
<div class="wrap">
	<div class="container">
		<div class="top clearfix">
				<h3>약관 동의</h3>
				<label>전체 선택 <input type="checkbox"></label>
			</div>
			<hr>
		
			<ul>
				<li class="clearfix">
					<label for="check1">이용약관 동의(필수)</label>
					<input type="checkbox" id="check1" name="agree">
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
					<input type="checkbox" id="check2" name="agree">
					<div class="content">
						<ol>
							<li></li>
						</ol>
					</div>
				</li>
				<li class="clearfix">
					<label for="check3">위치정보 이용약관 동의(선택)</label>
					<input type="checkbox" id="check3" name="agree">
					<div class="content">
						<ol>
							<li></li>
						</ol>
					</div>
				</li>
				<li class="clearfix">
					<label for="check4">이벤트 등 프로모션 알림 메일 수신(선택)</label>
					<input type="checkbox" id="check4" name="agree">
					<div class="content">
						<ol>
							<li></li>
						</ol>
					</div>
				</li>
			</ul>
	</div>

	<div class="btn_area clearfix">
		<a href="../common/index.jsp" class="btn_default">비동의</a>
		<a href="./membership.jsp" onclick="" class="btn_primary">동의</a>
	</div>
</div>
</section>

<footer>
</footer>
</body>
</html>