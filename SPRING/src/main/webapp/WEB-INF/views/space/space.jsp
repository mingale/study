<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	
	<link rel="stylesheet" href="/test/resources/css/reset.css">
	<style type="text/css">
		.clear{clear:both}
		li{float:left;width:500px;height:200px;}
		img{width:30px;height:30px;}
		button{background:transparent;border:transparent;}
		
		.div{float:left;}
	</style>
	
	<script src="/test/resources/script.js"></script>
	<script src="/test/resources/ajax.js"></script>
	<script type="text/javascript">
		function spaceTypeChange() {
			var param = 'typeNum=' + typeNum;
			sendRequest(space_callback, 'spaceChange', 'GET', param);
			
		}
		
		function space_callback() {
			if(httpRequest.readyState == 4) {
				if(httpRequest.status == 200) { 
					var date = httpRequest.responseText;
					
				} else {
					console.log('에러 발생');
				}
			} else {
				console.log('에러 상태 : ' + httpRequest.readySate);
			}
		}
	</script>
</head>
<body>
	<ul class="clear">
		<li id="spaceDiv">
		</li>
		<li>
			가로 : <input id="widthX" type="number" min="1" max="100" value="0" onchange="spaceDivChange();">
			세로 : <input id="heightY" type="number" min="1" max="100" value="0" onchange="spaceDivChange();"><br>
			<hr>
			<button onclick="spaceType('3')"><img src="/test/resources/images/icon1.png"></button>
			<button onclick="spaceType('2')"><img src="/test/resources/images/icon2.png"></button>
			<button onclick="spaceType('1')"><img src="/test/resources/images/icon3.png"></button>
			<button onclick="spaceType('0')"><img src="/test/resources/images/icon4.png"></button>
		</li>
	</ul>
</body>
</html>