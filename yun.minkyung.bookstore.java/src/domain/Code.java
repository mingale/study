package domain;

public interface Code {
	public static final int SHOP_LOGIN = 999;  // 로그인
//	public static final int SHOP_LOGOUT = 998; // 로그아웃
	
	public static final int HOST_MENU = 100;  // 주인
	
	public static final int HOST_STOCK_MENU = 110;  // 재고 관리
	public static final int HOST_BOOK_lIST = 111;   // 책 목록
	public static final int HOST_BOOK_ADD = 112;    // 책 추가
	public static final int HOST_BOOK_UPDATE = 113; // 책 수정
	public static final int HOST_BOOK_DEL = 114;    // 책 삭제
	
	public static final int HOST_ORDER_MENU = 120;    // 주문 관리
	public static final int HOST_ORDER_LIST = 121;    // 주문 목록
	public static final int HOST_ORDER_CONFIRM = 122; // 결제 승인
	public static final int HOST_ORDER_CANCLE = 123;  // 결제 취소
	public static final int HOST_SALE_TOTAL = 124;   // 결산
	
	public static final int GUEST_MENU = 200;  // 손님
	
	public static final int GUEST_CART_LIST = 210;  // 장바구니 리스트
	public static final int GUEST_CART_ADD = 211;   // 장바구니 담기
	public static final int GUEST_CART_DEL = 212;   // 장바구니 삭제
	public static final int GUEST_CART_BUY = 213;   // 장바구니 구매
	
	public static final int GUEST_NOW_BUY = 220;    // 바로 구매
	public static final int GUEST_BUY_LIST = 221; // 구매 목록
	public static final int GUEST_REFUND = 230;     // 환불
}
