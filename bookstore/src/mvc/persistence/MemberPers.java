package mvc.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import mvc.vo.Book;
import mvc.vo.Cart;
import mvc.vo.Member;
import mvc.vo.Bespeak;

public interface MemberPers {

	// 아이디 중복 확인
	public int confirmId(String strId);

	// 이메일 인증key 존재 확인
	public int confirmEmailKey(String key);

	// 로그인
	public int signIn(String strId, String strPwd);

	// 회원가입
	public int signUp(Member m);

	// 회원 정보
	public Member getMemberInfo(String strId);

	//id 찾기 전단계 - 이메일 존재 여부
	public String confirmEmail(String email);
	
	//비밀번호 찾기 이메일 전송
	public int memberEmailKeyUpdate(String id, String key);
	
	// 메일 인증
	public int sendGmail(String toEmail, String key, int view);

	//인증시 아이디 가져오기
	public String getId(String key);
	
	//인증시 비밀번호 가져오기
	public String getPwd(String key);
	
	// 회원 수정
	public int memberUpdate(Member m);

	// 회원 탈퇴 - id 남기기
	public int memberDelete(String strId);

	// 장바구니 추가
	public int setCart(String id, Book b, int count);

	// 장바구니 목록
	public ArrayList<Cart> getCartList(String id);

	// 장바구니 삭제
	public int cartDelete(String id, String no);

	// 장바구니 수량 수정
	public int cartCountUpdate(String id, String no, int count);

	// 장바구니 중복 확인
	public int cartCheck(String id, String no);

	// 장바구니 수량 추가
	public int setCartCount(String id, String no, int count);

	//찜 추가
	public int setWishlist(String id, String no);
	
	//찜 중복 확인
	public int wishlistCheck(String id, String no);
	
	// 주문 추가
	public int setOrder(String[] nos, Bespeak order);

	// 바로 구매 주문 추가
	public int setNowOrder(String no, Bespeak order, int nowCount);

	// 주문 내역
	public ArrayList<Bespeak> getMemberOrderList(String id);

	//해당 주문 정보
	public ArrayList<Bespeak> getOrderDetail(String order_num);
	
	//송장번호
	public String getShipping(String order_num);
	
	// 관리자 주문 내역
	public ArrayList<Bespeak> getHostOrderList(int start, int end);

	// 주문 내역 총 개수
	public int getHostOrderCnt();

	// 주문 상태 수정 (관리, 회원 환불 신청)
	public int orderStateUpdate(String num, int state);

	// 관리자 배송 시작
	public int shippingInsert(String order, String ship);

	// 관리자 환불내역 총 개수
	public int getRefundCnt();

	// 관리자 환불내역
	public ArrayList<Bespeak> getHostRefundList(int start, int end);

	// 회원 목록
	public ArrayList<Member> getMemberList(int start, int end);

	// 회원수
	public int getMemberCnt();

	// 관리자 회원 수정
	public int setHostMemberUpdate(String id, String memo, int rating);

	// 관리자 회원 다수 수정
	public int setHostMemberAllUpdate(String id, String memo, String rating);

	// 관리자 회원 다수 삭제
	public int setHostMemberAllDelete(String id);

	// 주문 삭제
	public int orderDelete(String order_num);

	// 회원 환불 내역
	public ArrayList<Bespeak> getRefundList(String id);
	
	//도서명 
	public void getNoTitle(ArrayList<Bespeak> list, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException;
}
