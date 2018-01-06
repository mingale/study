<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<head>
	<link type="text/css" rel="stylesheet" href="/bookstore/resources/css/common.css">
	<link type="text/css" rel="stylesheet" href="/bookstore/resources/css/paging.css">
	
	<title>동네 서점</title>
	
	<script src="${projectRes}ajax.js"></script>
	<script type="text/javascript">
		var checkFirst = true; //처음
		var loopStop = true; //작업완료후엔 0.5초마다 반복했던 작업을 중단
		var lastKeyword = null; //마지막키워드

		function keywordSuggest() {
			
			if(checkFirst == true) { //처음
				loopStop = false;  //0.5초마다 반복해라	
				setTimeout('sendKeyword()', 500); //0.5초마다 sendKeyword() 반복
			}
			checkFirst = false; //작업종료
		}	
		
		function sendKeyword() {
			//반복 종료
			if(loopStop == true) return false; ////작업종료시 false로 셋팅
			
			var keyword = document.indexForm.search.value; //검색어
			
			//검색어를 지운 경우 출력결과를 지운다(=출력위치를 숨겨라)
			if(keyword == null || keyword == '') {
				hide('suggest');
				lastKeyword = '';
			}
			//똑같은 검색어는 계속 검색하지 않도록 함
			else if(keyword != lastKeyword) { //현재 검색어 != 이전 검색어
				lastKeyword = keyword;
				var params = 'keyword=' + keyword;
				
				sendRequest(result_callback, 'suggest', 'GET', params); //suggest.jsp으로 요청한 결과가 result로 돌아온다.
			}
			setTimeout('sendKeyword()', 500); //재귀호출.(function안에서 호출하므로)
		}		

		//콜백함수
		function result_callback() {
			var suggestList = document.getElementById('suggest');
			
			if(httpRequest.readyState == 4) { //completed : 전 데이터가 취득완료된 상태
				if(httpRequest.status == 200) { //200 : 정상종료
			
					var books = '';
					var data = httpRequest.responseText; //결과값. suggest.jsp의 list
					var dataList = data.split('|'); //list.size()|단어-단어-단어,...
					var count = dataList[0]; //건수. list.size()
					
					if(count > 0) {
						var bookList = dataList[1].split('_');
						for(var i = 0; i < count; i += 1) {
							//결과 리스트 중에서, 검색하고자 하는 키워드를 클릭하면, input 박스로 키워드가 들어간다.
							books += "<a href=\"javascript:select('" + bookList[i] +"')\">" //javascript의 select 함수에 책이름 전달
									+ bookList[i]
									+ "</a>"
									+ "<br>";
						}
						show('suggest');
						
						suggestList.innerHTML = books;
					} else {
						hide('suggest');
					}
				} else {
					console.log('에러 발생');
				}
			} else {
				console.log('에러 상태 : ' + httpRequest.readyState);
			}
		}

		//작업완료
		//검색작업이 완료되었으므로 검색단어가 input 박스에 들어가고, 결과 list는 숨긴다.
		function select(book) {
			document.indexForm.search.value = book;
			loopStop = true;
			checkFirst = true;
			hide('suggest');
		}
		
		function show(str) {
			var element = document.getElementById(str);
			if(element) {
				element.style.display = '';
				element.style.background = 'white';
			}
		}		

		function hide(str) {
			var element = document.getElementById(str);
			if(element) {
				element.style.display = 'none';
			}
		}
	</script>
</head>

<header id="header">
<div class="wrap">
	<div class="menu_gnb clearfix">
		<a href="index" class="logo">BMS</a>

	<!-- 기본 -->
	<c:if test="${sessionScope.memId == null}">
		<nav class="gnb">
			<a href="signIn">로그인</a>
			<a href="help">고객센터</a>
		</nav>
	</c:if>
	
	<!-- 로그인 -->
	<c:if test="${sessionScope.memId != null}">
		<nav class="gnb">
			<c:if test="${sessionScope.rating == 1}">
				<a href="main">관리페이지</a>
			</c:if>
			<a href="myPagePwd">마이페이지</a>
			<a href="cart">장바구니</a>
			<a href="help">고객센터</a>
			<a href="signOut">로그아웃</a>
		</nav>
	</c:if>
	</div>
	
	<form class="search_area" action="bookSearch" name="indexForm" method="post">
		<input class="search" type="text" name="search" onkeydown="keywordSuggest();"><input class="searButton" type="submit" value="검색">
		<div id="suggest" class="keyword"></div>
	</form>
	<nav class="sub_gnb">
		<a href="bookList?tagName=베스트셀러&tag=best">베스트셀러</a>
		<a href="bookList?tagName=신간도서&tag=new">신간도서</a>
		<a href="bookList?tagName=추천도서&tag=good">추천도서</a>
		<a href="bookList?tagName=국내도서&tag=dom">국내도서</a>
		<a href="bookList?tagName=외국도서&tag=fore">외국도서</a>
		<!-- <a href="bookList?tagName=중고장터&tag=used">중고장터</a> -->
	</nav>
</div>
</header>