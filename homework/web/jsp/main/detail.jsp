<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>동네 서점</title>
	
	<link rel="stylesheet" href="../../css/reset.css">
	<link rel="stylesheet" href="../../css/common.css">
	<style>
		.content{padding: 10px 30px;}
		
		.location_zone{padding:10px;}
		.location_zone li{float:left;font-size:12px;}
		.location_zone .lo{border:1px solid #c6c6c6;padding:2px 8px;margin:3px 5px;}
		.location{position:absolute;}
		
		.detail{margin:10px 30px;}
		.detail div{float:left;word-wrap:break-word;}
		.detail img{max-width:220px;margin:10px 50px 10px 10px;}
		.detail_info{width:600px;}
		.detail_info li{width:100%;margin-bottom:15px;}
		.detail_info span{padding-right:15px;}
		.title{font-size:24px;font-weight:bold;}
		.price{color:red;font-weight:bold;font-size:18px;}
		.price_origin{font-size:12px;text-decoration:line-through;}
		
		/* general */
		.general_list{width:1024px;padding:0;}
		.general_list li{float:left;width:202.8px;height:30px;text-align:center;border:1px solid #c6c6c6;}
		.general_list a{margin:auto 0;font-size:12px;line-height:30px;}
		.content div{padding-bottom:50px;}
		.content p{font-size:14px;}
	</style>
</head>
<body>

<%@ include file="../common/header.jsp" %>

<section>
<div class="wrap">
	<nav class="location_zone">
		<ul class="clearfix">
			<li><a href=""> 홈 </a> &gt; </li>
			<li>
				<select>
					<option>에세이</option>
					<option selected>FS</option>
				</select> &gt; 
			</li>
			<li>
				<select>
					<option>에세이</option>
					<option></option>
				</select> &gt; 
			</li>
			<li>
				<select>
					<option>에세이</option>
					<option></option>
				</select>
			</li>
		</ul>
	</nav>
	
	<div class="detail clearfix">
		<div>
			<img src="../../images/book1.jpg">
		</div>
		
		<div class="detail_info">
			<ul class="clearfix">
				<li><span class="title">책제목</span></li>
				<li>지은이 지음 | 출판사 | 옮긴이 옮김 | 2017년 11월 3일 출간</li>
				<li><span class="price">판매가</span> <span class="price_origin">정가</span></li>
				<li>배송료 무료</li>
				<li><sapn>수량</sapn> <input type="number" name="count" min="0" max="100">권</li>
				<li>
					<input type="button" value="바로 구매">
					<input type="button" value="장바구니 담기">
					<input type=button value="찜하기">
				</li>
			</ul>
		</div>
	</div>
	
	<ul class="general_list clearfix">
		<li><a href="#abstractDiv">책소개</a></li>
		<li><a href="#indexDiv">목차</a></li>
		<li><a href="#authorDiv">저자소개</a></li>
		<li><a href="#readDiv">출판사서평</a></li>
		<li><a href="#reviewDiv">리뷰</a></li>
	</ul>
	
	<div class="content">
		<div id="abstractDiv">
			<h3>책소개</h3>
			<hr>
			
			<p>셰릴의 개인적인 상실과 극복에 대한 이야기와 더불어 이 책은 역경과 상실에 직면한 사람들이 흔히 당면하는 문제와 극복 방안에 대해서도 실질적인 조언을 제공한다. 집단 따돌림, 질병, 실직, 이혼, 성폭력, 자연재해, 성적소수자에게 가해지는 차별, 사랑하는 이의 죽음, 난민 생활 등 인생에서 우연히 맞닥뜨릴 수 있는 다양한 역경과 극복의 지혜에 대해서도 살펴본다. 더불어 고통을 겪는 사람들이 솔직하게 자신의 상황을 이야기하는 법, 고통을 겪는 지인에게 진정한 위로와 도움을 제공할 수 있는 법에 대해 조언하고 가정에서는 가족들이 어떻게 서로를 지지하고 도움을 줘야 하는지, 직장과 사회는 고통 받는 직원들에게 어떤 도움을 제공해야 하는지에 대해서도 심도 있게 고찰한다. 이를 통해 개인과 공동체가 언제 어떤 형태로 옵션 B의 상황에 맞닥뜨리더라도, 최선의 삶을 살아낼 수 있는 방안을 강구하며 새로운 희망의 근거를 제시한다.</p> 
		</div>
		
		<div id="indexDiv">
			<h3>목차</h3>
			<hr>
			
			<p>서문 당신의 언어 온도는 몇 도쯤 될까요<br>
			<br>
			1부 말(言), 마음에 새기는 것<br>
			더 아픈 사람<br>
			말도 의술이 될 수 있을까<br>
			사랑은 변명하지 않는다<br>
			틈 그리고 튼튼함<br>
			</p>
		</div>
		
		<div id="authorDiv">
			<h3>저자소개</h3>
			<hr>
			<p>마크 맨슨</p>
			<p>1984년 텍사스 주 출생. 보스턴 대학교를 졸업했다. 200만 명이 넘는 구독자를 지닌, 미국에서 가장 영향력 있는 파워블로거 중 하나다. 각종 매체에 지속적으로 칼럼을 기고했으며, 날카로운 통찰력과 직설적인 문체로 CNN, 뉴욕타임스, 타임, 포브스, 월스트리트저널 등 주요 언론의 주목을 받고 있다. 또한 글로벌 컨설팅 회사인 Infinity Squared Media LLC를 설립하여 운영 중이다. 
		</div>
		
		<div id="readDiv">
			<h3>출판사 서평</h3>
			<hr>
		</div>
		
		<div id="reviewDiv">
			<h3>리뷰</h3>
			<hr>
		</div>
	</div>
</div>
</section>

<aside>
<div class="wrap">
	최근 본 도서
	<div id="quick_area">
		<a href=""><img src=""></a>
	</div>
	
	<input type="button" id="up" value="TOP"><br>
	<input type="button" id="down" value="DOWN">
</div>
</aside>

<%@ include file="../common/footer.jsp" %>

</body>
</html>