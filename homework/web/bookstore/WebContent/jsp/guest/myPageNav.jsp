<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">
	section .my_list_nav{float:left;width:100px;padding:20px;}
	section .my_list_nav a{display:block;width:100px;height:40px;line-height:40px;text-align:center;background:powderblue;}
</style>

<div class="my_list_nav">
	<a class="mp" href="myPagePwd.do" onmouseover="navOver('mp');" onmouseout="navOut('mp')">회원 정보</a>
	<a class="mo" href="myOrder.go" onmouseover="navOver('mo');" onmouseout="navOut('mo')">주문 내역</a>
	<a class="mr" href="myRefund.go" onmouseover="navOver('mr');" onmouseout="navOut('mr')">환불 내역</a>
</div>