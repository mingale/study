<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/resources/setting.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width initial-scale=1">
	
	<title>관리자 - 공지 사항</title>
	
	<link rel="stylesheet" href="${projectRes}css/notice.css">
	<style type="text/css">
		section .content{margin:20px 0;border:1px solid lightgray;border-radius:0.5em;padding:10px;}
		section h1{font-weight:bold;font-size:18px;}
		section h2{font-size:14px;}
		
		.input{width:500px;}
		.btn_comment{width:85px;height:50px;}
	</style>
	
	<script src="${projectRes}ajax.js"></script>
	<script type="text/javascript">
		function noticeCommentPro() {
			var idx = "idx=" + document.getElementById('index').value;
			var comment = 'comment=' + document.getElementById('comment').value;
			sendRequest(commentUp, 'noticeCommentPro', 'GET', idx+'&'+comment);
		}
		
		function commentUp() {
			if(httpRequest.readyState == 4) {
				if(httpRequest.status == 200) {
					var commentArea = document.getElementById('commentArea');
					var date = httpRequest.responseText;
					
					//작성자, 댓글 내용
					var arr = date.split(',');
					
					
					var nodeLi = document.createElement('li');
					var nodeDiv = document.createElement('div');
					var nodeDivText = document.createTextNode(date);
					nodeDiv.appendChild(nodeDivText);
					nodeDiv.className = 'content';
					nodeDiv.innerHTML = '<b>' + arr[0] + '</b> : ' + arr[1];
					nodeLi.appendChild(nodeDiv);
					
					if(date != '0') {
						commentArea.after(nodeLi);
					}
				} else {
					console.log('에러 발생');
				}
			} else {
				console.log('에러 상태 : ' + httpRequest.readyState);
			}
		}
	</script>
</head>
<body>
<%@ include file="header.jsp" %>

<section>
	<div class="wrap">
		<ul>
			<li><p>제목</p></li> 
			<li>
				<div class="content"><h1>${notice.title}</h1></div>
			</li>
			<li><p>내용</p></li>
			<li>
				<div class="content area"><h2>${notice.content}</h2></div>
			</li>
			<li class="li_btn">
				<c:if test="${notice.id == sessionScope.memId}">
					<button class="btn" onclick="window.location='noticeWrite?idx=${notice.idx}';">수정</button>
				</c:if>
				<button class="btn" onclick="window.location='notice?pageNum=${pageNum}';">돌아가기</button>
			</li>
			<li><p>댓글</p></li>
			<li id="commentArea">
				<input id="index" type="hidden" value="${notice.idx}">
				<textarea id="comment" class="input"></textarea>
				<button class="btn btn_comment" onclick="noticeCommentPro();">등록</button>
			</li>
			<c:if test="${notice.noticeComment != null}">
				<c:forEach var="nComment" items="${notice.noticeComment}">
					<li><div class="content"><b>${nComment.writer_id}</b> : ${nComment.com_content}</div></li>
				</c:forEach>
			</c:if>
		</ul>
	</div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>