package service;

public interface Guest {

	public void cartList(); // 장바구니 목록
	public void cartAdd();  // 장바구니 담기
	public void cartDel();  // 장바구니 삭제
	public void cartBuy();  // 장바구니 구매
	public void buyList();  // 구매 목록
	public void nowBuy();   // 바로 구매
	public void refund();   // 환불
}
