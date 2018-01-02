package mvc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GuestService {

	//주문 목록
	public void orderView(HttpServletRequest req, HttpServletResponse res);
	
	//장바구니 목록
	public void cartView(HttpServletRequest req, HttpServletResponse res);
	
	//도서 버튼 (장바구니 담기, 찜하기)
	public void bookBtnPro(HttpServletRequest req, HttpServletResponse res);
	
	//장바구니 처리
	public void cartPro(HttpServletRequest req, HttpServletResponse res);
	
	//주문 처리
	public void addOrder(HttpServletRequest req, HttpServletResponse res);

	//주문 내역
	public void myOrderView(HttpServletRequest req, HttpServletResponse res);
	
	//주문 상세 페이지
	public void orderDetailView(HttpServletRequest req, HttpServletResponse res);
	
	//회원 환불 신청
	public void orderRefund(HttpServletRequest req, HttpServletResponse res);
	
	//회원 환불 내역
	public void getRefundView(HttpServletRequest req, HttpServletResponse res);
	
}