package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Map;

import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Cart;
import spring.mvc.bookstore.vo.Member;

public interface MemberPers {

	// 아이디 중복 확인
	public int confirmId(String id);

	// 이메일 인증key 존재 확인-1
	public int confirmEmailKey(String e_key);

	// 이메일 인증key 존재 확인-2
	public int emailKeyRatingUp(String e_key);

	// 로그인
	public int signIn(Map<String, Object> map);

	// 회원가입
	public int signUp(Member m);

	// 회원 정보
	public Member getMemberInfo(String id);

	// id 찾기 전단계 - 이메일 존재 여부
	public String confirmEmail(String email);

	// 비밀번호 찾기 이메일 전송
	public int memberEmailKeyUpdate(Map<String, Object> map);

	// 메일 인증
	public int sendGmail(String toEmail, String key, int view);

	// 인증시 아이디 가져오기
	public String getId(String key);

	// 인증시 비밀번호 가져오기
	public String getPwd(String key);

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

	// 송장번호
	public String getShipping(String order_num);

	// 관리자 주문 내역-1) 모든 내역
	public ArrayList<Bespeak> getHostOrderList();

	// 관리자 주문 내역-2) 중복 없는 내역
	public ArrayList<Bespeak> getHostOrderDistinctList(Map<String, Object> map);

	// 주문 내역 총 개수
	public int getHostOrderCnt();

	// 주문 상태 수정 (관리, 회원 환불 신청)
	public int orderStateUpdate(Map<String, Object> map);

	// 관리자 배송 시작
	public int shippingInsert(Map<String, Object> map);

	// 관리자 환불내역 총 개수
	public int getRefundCnt();

	// 관리자 환불내역-1) 모든 주문 내역
	public ArrayList<Bespeak> getHostRefundList();

	// 관리자 환불내역-2) 중복 제거 주문 내역
	public ArrayList<Bespeak> getHostRefundDistinctList(Map<String, Object> map);

	// 회원 목록
	public ArrayList<Member> getMemberList(Map<String, Object> map);

	// 회원수
	public int getMemberCnt();

	// 관리자 회원 수정
	public int setHostMemberUpdate(Map<String, Object> map);

	// 관리자 회원 다수 삭제
	// public int setHostMemberAllDelete(String id);

	// 주문 삭제
	public int orderDelete(String order_num);

	// 회원 환불 내역-1) 해당 회원의 중복 없는 주문 목록
	public ArrayList<String> getRefundDistinctList(String id);

	// 회원 환불 내역-2
	public ArrayList<Bespeak> getRefundList(String id);

	// 도서명
	// public void getNoTitle(ArrayList<Bespeak> list);
}
