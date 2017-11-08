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
		section{top:50%;left:50%;}
        .wrap{width:400px;padding:10px 20px;margin:0 auto;}
		.wrap .content{margin-bottom:20px;border:1px solid #c6c6c6;}
        .wrap input{width:388px;height:50px;padding:5px;border:none;}
        .join input{width:100%;border:1px solid #c6c6c6;}

    </style>
    
    <script type="text/javascript">
        function loginCheck(){
            var id = document.myForm.id.value;
            var pwd = document.myForm.pwd.value;

            if(id == "admin" && pwd == "admin") {
                alert("관리자님 반갑습니다.");
                //window.location.href = './admin/main.html';
                document.myForm.setAttribute("action", "../host/main.jsp");
            } else if(id == "guest") {
                //login
                document.myForm.setAttribute("action", "../common/index.jsp");
            } else {
                alert("회원정보가 일치하지 않습니다.");
            }
        }

        function joinCheck(){
            document.myForm.setAttribute("action", "./memJoin.jsp");
        }
    </script>
</head>
<body>
<header>
	<h1><a href="../common/index.jsp">logo</a></h1>
</header>

<section>
<form name="myForm" method="post" class="wrap">
	<div class="content">
        <input type="text" placeholder="아이디" name="id">
        <input type="password" placeholder="비밀번호" name="pwd">
    </div>
    
    <div class="join">
        <input type="submit" name="login" value="로그인" onclick="loginCheck();">
        <input type="submit" name="member" value="회원가입" onclick="joinCheck();">
    </div>
</form>
</section>

<footer>
</footer>
</body>
</html>