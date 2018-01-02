<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
	.contain{float:left;width:780px;margin-left:20px}
	.div{height:250px;margin:0 auto;text-align:center;padding-top:100px;font-size:18px;}
</style>

<nav class="sub_nav">
	<a class="o" href="hostOrder" onmouseover="navOver('o');" onmouseout="navOut('o')">주문 목록</a>
	<a class="r" href="refund" onmouseover="navOver('r');" onmouseout="navOut('r')">환불 목록</a>
	<!-- <a href="">교환/반품</a> -->
	<a class="rs" href="result" onmouseover="navOver('rs');" onmouseout="navOut('rs')">결산 내역</a><!-- 결제완료, 배송완료, 주문후 14일 경과 -->
</nav>