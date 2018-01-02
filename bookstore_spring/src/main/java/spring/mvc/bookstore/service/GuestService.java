package spring.mvc.bookstore.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface GuestService {

	//주문 목록
	public void orderView(HttpServletRequest req, Model model);
	
	//장바구니 목록
	public void cartView(HttpServletRequest req, Model model);
	
	//도서 버튼 (장바구니 담기, 찜하기)
	public void bookBtnPro(HttpServletRequest req, Model model);
	
	//장바구니 처리
	public void cartPro(HttpServletRequest req, Model model);
	
	//주문 처리
	public void addOrder(HttpServletRequest req, Model model);

	//주문 내역
	public void myOrderView(HttpServletRequest req, Model model);
	
	//주문 상세 페이지
	public void orderDetailView(HttpServletRequest req, Model model);
	
	//회원 환불 신청
	public void orderRefund(HttpServletRequest req, Model model);
	
	//회원 환불 내역
	public void getRefundView(HttpServletRequest req, Model model);
	
}