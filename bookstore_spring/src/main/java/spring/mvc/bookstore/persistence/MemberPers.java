package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Map;

import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Cart;
import spring.mvc.bookstore.vo.Member;

public interface MemberPers {

	// 회원 정보
	public Member getMemberInfo(String id);

	// 회원 수정
	public int memberUpdate(Member m);

	// 회원 탈퇴 - id 남기기
	public int memberDelete(String id);

	// 장바구니 추가
	public int setCart(Cart cart);

	// 장바구니 목록
	public ArrayList<Cart> getCartList(String id);

	// 장바구니 삭제
	public int cartDelete(Map<String, Object> map);

	// 장바구니 수량 수정
	public int cartCountUpdate(Cart cart);

	// 장바구니 중복 확인
	public String cartCheck(Map<String, Object> map);

	// 장바구니 수량 추가
	public int setCartCount(Map<String, Object> map);

	// 찜 추가
	public int setWishlist(Map<String, Object> map);

	// 찜 중복 확인
	public int wishlistCheck(Map<String, Object> map);

	// 주문 추가-1) 주문번호
	public void orderNumberCreate();

	// 주문 추가-2) 장바구니 목록 중 해당 품목
	public Map<String, Object> cartListBook(Map<String, Object> map);

	// 주문 추가-3
	public int setOrder(Bespeak order);

	// 바로 구매 주문 추가
	public int setNowOrder(Bespeak order);

	// 주문 내역-1) 중복 없는 주문번호 목록
	public ArrayList<String> getMemberOrderDistinctList(String id);

	// 주문 내역-2) 회원의 주문 내역
	public ArrayList<Bespeak> getMemberOrderList(String id);

	// 해당 주문 정보
	public ArrayList<Bespeak> getOrderDetail(String order_num);

	// 회원 환불 내역-1) 해당 회원의 중복 없는 주문 목록
	public ArrayList<String> getRefundDistinctList(String id);

	// 회원 환불 내역-2
	public ArrayList<Bespeak> getRefundList(String id);

}
