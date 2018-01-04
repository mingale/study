package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import spring.mvc.bookstore.controller.EmailHandler;
import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Cart;
import spring.mvc.bookstore.vo.Member;

@Repository
public class MemberPersImpl implements MemberPers {
	@Autowired
	SqlSession sqlSession;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	/*
	 * MemberPers dao = sqlSession.getMapper(MemberPers.class); BookPers bDao =
	 * sqlSession.getMapper(BookPers.class);
	 * 
	 * Mapper를 멤버변수에서 선언하면 Bean 생성이 되지 않아 사용 불가능
	 * 
	 */

	private MemberPersImpl() {
	}

	// 아이디 중복 확인
	@Override
	public int confirmId(String id) {
		log.debug("MemberPersImple START - confirmId");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.confirmId(id);

		log.debug("MemberPersImple END - confirmId");
		return tmp;
	}

	// 이메일 인증key 존재 확인-2) 해당 계정의 권한 등급 UP
	@Override
	public int emailKeyRatingUp(String e_key) {
		log.debug("MemberPersImple START - emailKeyRatingUp");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.emailKeyRatingUp(e_key);

		log.debug("MemberPersImple END - emailKeyRatingUp");
		return tmp;
	}

	// 이메일 인증key 존재 확인-1
	@Override
	public int confirmEmailKey(String e_key) {
		log.debug("MemberPersImple START- confirmEmailKey");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.confirmEmailKey(e_key);
		if (cnt > 0) {
			emailKeyRatingUp(e_key);
		}

		log.debug("MemberPersImple END - confirmEmailKey");
		return cnt;
	}

	// 로그인
	@Override
	public int signIn(Map<String, Object> map) {
		log.debug("MemberPersImple START - signIn");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.signIn(map);

		log.debug("MemberPersImple END - signIn");
		return tmp;
	}

	// 회원가입
	@Override
	public int signUp(Member m) {
		log.debug("MemberPersImple START - signUp");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.signUp(m);

		log.debug("MemberPersImple END - signUp");
		return tmp;
	}

	// 회원 정보
	@Override
	public Member getMemberInfo(String id) {
		log.debug("MemberPersImple START - getMemberInfo");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		Member tmp = dao.getMemberInfo(id);
		;

		log.debug("MemberPersImple END - getMemberInfo");
		return tmp;
	}

	// 이메일 존재 여부
	@Override
	public String confirmEmail(String email) {
		log.debug("MemberPersImple START - confirmEmail");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.confirmEmail(email);
		log.debug("MemberPersImple END - confirmEmail");
		return tmp;
	}

	// 인증키 수정
	@Override
	public int memberEmailKeyUpdate(Map<String, Object> map) {
		log.debug("MemberPersImple START - memberEmailKeyUpdate");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.memberEmailKeyUpdate(map);

		log.debug("MemberPersImple END - memberEmailKeyUpdate");
		return tmp;
	}

	// 메일 인증
	/*
	 * gmail 이용 전용 설정 gmail > 환경 설정 > 전달 및 pop/IMAP > IMAP 액세스 > IMAP 사용으로 변경 내 계정 >
	 * 로그인 및 보안 > 연결된 앱 및 사이트 > 보안 수준이 낮은 앱 허용 : 사용으로 변경(2단계 보안 사용 중이면 설정 못함)
	 */
	@Autowired
	JavaMailSender mailSender;

	@Override
	public int sendGmail(String toEmail, String key, int view) {
		log.debug("MemberPersImple START - sendGmail");
		int cnt = 0;

		try {
			EmailHandler sendMail = new EmailHandler(mailSender);
			if (view == 0) {
				sendMail.setSubject("BMS 회원가입 인증 메일");
				sendMail.setText(new StringBuffer().append("BMS 회원가입 인증 메일입니다. 인증을 눌러 회원가입을 완료하세요.<br>")
						.append("<a href='http://localhost:8081/bookstore/emailCheck?key=").append(key)
						.append("&view=" + view + "'>인증</a>").toString());
			} else if (view == 1) {
				sendMail.setSubject("BMS 아이디 찾기 인증 메일");
				sendMail.setText(new StringBuffer().append("BMS 아이디 찾기 인증 메일입니다. 인증을 눌러 아이디를 찾으세요.<br>")
						.append("<a href='http://localhost:8081/bookstore/emailCheck?key=").append(key)
						.append("&view=" + view + "'>인증</a>").toString());
			} else if (view == 2) {
				sendMail.setSubject("BMS 비밀번호 찾기 인증 메일");
				sendMail.setText(new StringBuffer().append("BMS 비밀번호 찾기 인증 메일입니다. 인증을 눌러 비밀번호를 찾으세요.<br>")
						.append("<a href='http://localhost:8081/bookstore/emailCheck?key=").append(key)
						.append("&view=" + view + "'>인증</a>").toString());
			}
			sendMail.setFrom("admin@bookstore.com", "관리자");
			sendMail.setTo(toEmail);
			log.debug("Email 전송");
			sendMail.send();
			log.debug("Email 전송 완료");
			cnt = 1;
		} catch (Exception e) { //throws MessagingException, UnsupportedEncodingException
			e.printStackTrace();
		}

		log.debug("MemberPersImple END - sendGmail");
		return cnt;
	}

	// 인증시 아이디 가져오기
	@Override
	public String getId(String key) {
		log.debug("MemberPersImple START - getId");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.getId(key);

		log.debug("MemberPersImple END - getId");
		return tmp;
	}

	// 인증시 비밀번호 가져오기
	@Override
	public String getPwd(String key) {
		log.debug("MemberPersImple START - getPwd");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.getPwd(key);

		log.debug("MemberPersImple END - getPwd");
		return tmp;
	}

	// 회원 수정
	@Override
	public int memberUpdate(Member m) {
		log.debug("MemberPersImple START - memberUpdate");
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		log.debug("MemberPersImple END - memberUpdate");
		return dao.memberUpdate(m);
	}

	// 회원 탈퇴 - id 남기기
	// email이 unique라 그대로 둠. NOT NULL
	@Override
	public int memberDelete(String id) {
		log.debug("MemberPersImple START - memberDelete");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.memberDelete(id);

		log.debug("MemberPersImple END - memberDelete");
		return tmp;
	}

	// 장바구니 추가
	@Override
	public int setCart(Cart cart) {
		log.debug("MemberPersImple START - setCart");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setCart(cart);

		log.debug("MemberPersImple END - setCart");
		return tmp;
	}

	// 장바구니 목록
	@Override
	public ArrayList<Cart> getCartList(String id) {
		log.debug("MemberPersImple START - getCartList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Cart> tmp = dao.getCartList(id);

		log.debug("MemberPersImple END - getCartList");
		return tmp;
	}

	// 장바구니 삭제
	@Override
	public int cartDelete(Map<String, Object> map) {
		log.debug("MemberPersImple START - cartDelete");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.cartDelete(map);

		log.debug("MemberPersImple END - cartDelete");
		return tmp;
	}

	// 장바구니 수량 수정
	@Override
	public int cartCountUpdate(Cart cart) {
		log.debug("MemberPersImple START - cartCountUpdate");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.cartCountUpdate(cart);

		log.debug("MemberPersImple END - cartCountUpdate");
		return tmp;
	}

	// 장바구니 중복 확인
	@Override
	public String cartCheck(Map<String, Object> map) {
		log.debug("MemberPersImple START - cartCheck");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.cartCheck(map);

		log.debug("MemberPersImple END - cartCheck");
		return tmp;
	}

	// 장바구니 수량 추가
	@Override
	public int setCartCount(Map<String, Object> map) {
		log.debug("MemberPersImple START - setCartCount");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setCartCount(map);

		log.debug("MemberPersImple END - setCartCount");
		return tmp;
	}

	// 찜 추가
	@Override
	public int setWishlist(Map<String, Object> map) {
		log.debug("MemberPersImple START - setWishlist");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setWishlist(map);

		log.debug("MemberPersImple END - setWishlist");
		return tmp;
	}

	// 찜 중복 확인
	@Override
	public int wishlistCheck(Map<String, Object> map) {
		log.debug("MemberPersImple START - wishlistCheck");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.wishlistCheck(map);
		if (cnt != 0) {
			cnt = 9;
		}

		log.debug("MemberPersImple END - wishlistCheck");
		return cnt;
	}

	// 주문 추가-1) 주문번호
	@Override
	public void orderNumberCreate() {
		log.debug("MemberPersImple START - orderNumberCreate");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		dao.orderNumberCreate();

		log.debug("MemberPersImple END - orderNumberCreate");
	}

	// 주문 추가-2) 장바구니 목록 중 해당 품목
	@Override
	public Map<String, Object> cartListBook(Map<String, Object> map) {
		log.debug("MemberPersImple START - cartListBook");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		Map<String, Object> tmp = dao.cartListBook(map);

		log.debug("MemberPersImple END - cartListBook");
		return tmp;
	}

	// 주문 추가-3) Order에 저장하기
	@Override
	public int setOrder(Bespeak order) {
		log.debug("MemberPersImple START- setOrder");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setOrder(order);

		log.debug("MemberPersImple END- setOrder");
		return tmp;
	}

	// 바로 주문 추가
	@Override
	public int setNowOrder(Bespeak order) {
		log.debug("MemberPersImple START - setNowOrder");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setNowOrder(order);

		log.debug("MemberPersImple END - setNowOrder");
		return tmp;
	}

	// 주문 내역-1) 중복 없는 주문번호 목록
	@Override
	public ArrayList<String> getMemberOrderDistinctList(String id) {
		log.debug("MemberPersImple - getMemberOrderDistinctList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<String> tmp = dao.getMemberOrderDistinctList(id);

		log.debug("MemberPersImple - getMemberOrderDistinctList");
		return tmp;
	}

	// 주문 내역-2) 회원의 주문 내역
	@Override
	public ArrayList<Bespeak> getMemberOrderList(String id) {
		log.debug("MemberPersImple START - getMemberOrderList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = new ArrayList<>();

		ArrayList<String> numList = getMemberOrderDistinctList(id);
		ArrayList<Bespeak> allList = dao.getMemberOrderList(id);

		int index = 0; // 게시판 list index
		// numList와 같은 주문 번호만 담기. 중복 주문번호(여러 종류 주문) 처리
		for (int i = 0; i < numList.size(); i += 1) { // 중복 없는 주문 번호 List
			int count = 0; // 중복 수

			for (int j = 0; j < allList.size(); j += 1) { // 모둔 주문 list
				String num = allList.get(j).getOrder_num();

				// numlist의 현재 주문번호와 allList의 주문번호가 같으면
				if (numList.get(i).equals(num)) {
					// 주문 번호가 중복되면
					if (count > 0) {
						list.get(index - 1).getBook().setNo(" 외 " + count + "권"); // 중복이므로 외권 표시
					}
					count += 1;

					// 주문번호가 같으면(중복X) - 게시판 list 추가
					if (count == 1) {
						allList.get(j).getBook().setNo("");
						list.add(allList.get(j));
						index += 1;
					}
				} else {
					count = 0;
				}
			}
		}
		// getNoTitle(list);

		log.debug("MemberPersImple END - getMemberOrderList");
		return list;
	}

	// 해당 주문 정보
	@Override
	public ArrayList<Bespeak> getOrderDetail(String order_num) {
		log.debug("MemberPersImple START - getOrderDetail");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = dao.getOrderDetail(order_num);

		log.debug("MemberPersImple END - getOrderDetail");
		return list;
	}

	// 송장 번호
	@Override
	public String getShipping(String order_num) {
		log.debug("MemberPersImple - getShipping");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.getShipping(order_num);

		log.debug("MemberPersImple - getShipping");
		return tmp;
	}

	// 관리자 주문 내역-1
	@Override
	public ArrayList<Bespeak> getHostOrderList() {
		log.debug("MemberPersImple - getHostOrderList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> tmp = dao.getHostOrderList();

		log.debug("MemberPersImple - getHostOrderList");
		return tmp;
	}

	// 관리자 주문 내역-2
	@Override
	public ArrayList<Bespeak> getHostOrderDistinctList(Map<String, Object> map) {
		log.debug("MemberPersImple START - getHostOrderDistinctList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = new ArrayList<>();

		// 주문 목록 - 첫번째 도서만 도서 번호 표기
		// ** rownum에 별칭을 주지 않으면 다음 페이지 쿼리문이 제대로 돌아가지 않는다.

		ArrayList<Bespeak> numList = dao.getHostOrderDistinctList(map);
		ArrayList<Bespeak> allList = getHostOrderList();

		int index = 0; // 게시판 list index
		// numList와 같은 주문 번호만 담기. 중복 주문번호(여러 종류 주문) 처리
		for (int i = 0; i < numList.size(); i += 1) { // 중복 없는 주문 번호 List
			int count = 0; // 중복 수

			for (int j = 0; j < allList.size(); j += 1) { // 모둔 주문 list
				String num = allList.get(j).getOrder_num();

				// numlist의 현재 주문번호와 allList의 주문번호가 같으면
				if (numList.get(i).getOrder_num().equals(num)) {
					// 주문 번호가 중복되면
					if (count > 0) {
						list.get(index - 1).getBook().setNo(" 외 " + count + "권"); // 중복이므로 외권 표시
					}
					count += 1;

					// 주문번호가 같으면(중복X) - 게시판 list 추가
					if (count == 1) {
						allList.get(j).getBook().setNo("");
						list.add(allList.get(j));
						index += 1;
					}
				} else {
					count = 0;
				}
			}
		}

		// 주문 도서 이름
		// getNoTitle(list);

		log.debug("MemberPersImple END - getHostOrderDistinctList");
		return list;
	}

	// 주문 내역 총 개수
	@Override
	public int getHostOrderCnt() {
		log.debug("MemberPersImple START - getHostOrderCnt");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.getHostOrderCnt();

		log.debug("MemberPersImple END - getHostOrderCnt");
		return tmp;
	}

	// 관리자 주문 상태 수정
	@Override
	public int orderStateUpdate(Map<String, Object> map) {
		log.debug("MemberPersImple START - orderStateUpdate");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.orderStateUpdate(map);

		log.debug("MemberPersImple END - orderStateUpdate");
		return tmp;
	}

	// 배송 시작
	@Override
	public int shippingInsert(Map<String, Object> map) {
		log.debug("MemberPersImple START - shippingInsert");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.shippingInsert(map);

		log.debug("MemberPersImple END - shippingInsert");
		return tmp;
	}

	// 관리자 환불 내역 총 개수
	@Override
	public int getRefundCnt() {
		log.debug("MemberPersImple START - getRefundCnt");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.getRefundCnt();

		log.debug("MemberPersImple END - getRefundCnt");
		return tmp;
	}

	// 관리자 환불내역-1) 모든 주문 내역
	@Override
	public ArrayList<Bespeak> getHostRefundList() {
		log.debug("MemberPersImple START - getHostRefundList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> tmp = dao.getHostRefundList();

		log.debug("MemberPersImple END - getHostRefundList");
		return tmp;
	}

	// 관리자 환불 내역-2
	@Override
	public ArrayList<Bespeak> getHostRefundDistinctList(Map<String, Object> map) {
		log.debug("MemberPersImple START - getHostRefundDistinctList");
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = new ArrayList<>();

		ArrayList<Bespeak> numList = dao.getHostRefundDistinctList(map);
		ArrayList<Bespeak> allList = getHostRefundList();

		int index = 0; // 게시판 list index
		// numList와 같은 주문 번호만 담기. 중복 주문번호(여러 종류 주문) 처리
		for (int i = 0; i < numList.size(); i += 1) { // 중복 없는 주문 번호 List
			int count = 0; // 중복 수

			for (int j = 0; j < allList.size(); j += 1) { // 모둔 주문 list
				String num = allList.get(j).getOrder_num();

				// numlist의 현재 주문번호와 allList의 주문번호가 같으면
				if (numList.get(i).getOrder_num().equals(num)) {
					// 주문 번호가 중복되면
					if (count > 0) {
						list.get(index - 1).getBook().setNo(" 외 " + count + "권"); // 중복이므로 외권 표시
					}
					count += 1;

					// 주문번호가 같으면(중복X) - 게시판 list 추가
					if (count == 1) {
						allList.get(j).getBook().setNo("");
						list.add(allList.get(j));
						index += 1;
					}
				} else {
					count = 0;
				}
			}
		}
		// getNoTitle(list);

		log.debug("MemberPersImple END - getHostRefundDistinctList");
		return list;
	}

	// 회원 목록
	@Override
	public ArrayList<Member> getMemberList(Map<String, Object> map) {
		log.debug("MemberPersImple START - getMemberList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Member> list = dao.getMemberList(map);

		log.debug("MemberPersImple END - getMemberList");
		return list;
	}

	// 회원수
	@Override
	public int getMemberCnt() {
		log.debug("MemberPersImple START - getMemberCnt");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.getMemberCnt();

		log.debug("MemberPersImple END - getMemberCnt");
		return cnt;
	}

	// 관리자 회원 수정
	@Override
	public int setHostMemberUpdate(Map<String, Object> map) {
		log.debug("MemberPersImple START - setHostMemberUpdate");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.setHostMemberUpdate(map);

		log.debug("MemberPersImple END - setHostMemberUpdate");
		return cnt;
	}

	/*
	 * ERROR - ORA-02292: integrity constraint (BMS.SYS_C007739) violated - child
	 * record found FK에 ON DELETE CASCADE 필수!
	 */
	// 관리자 회원 다수 삭제
	/*
	 * public int setHostMemberAllDelete(String id) { int cnt = 0;
	 * 
	 * String[] ids = id.split(",");
	 * 
	 * Connection conn = null; PreparedStatement ps = null;
	 * 
	 * try { conn = datasource.getConnection();
	 * 
	 * String sql = "DELETE FROM member WHERE id=?"; ps =
	 * conn.prepareStatement(sql);
	 * 
	 * for (String strId : ids) { ps.setString(1, strId);
	 * 
	 * cnt = ps.executeUpdate(); } } catch (SQLException e) { e.printStackTrace();
	 * log.debug("setHostMemberAllDelete() 실패"); } finally { try { if (ps
	 * != null) ps.close(); if (conn != null) conn.close(); } catch (SQLException e)
	 * { e.printStackTrace(); } } return cnt; }
	 */

	// 주문 삭제
	@Override
	public int orderDelete(String order_num) {
		log.debug("MemberPersImple START - orderDelete");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.orderDelete(order_num);

		log.debug("MemberPersImple END - orderDelete");
		return cnt;
	}

	// 회원 환불 내역-1) 해당 회원의 중복 없는 주문 목록
	@Override
	public ArrayList<String> getRefundDistinctList(String id) {
		log.debug("MemberPersImple START - getRefundDistinctList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<String> list = dao.getRefundDistinctList(id);

		log.debug("MemberPersImple END - getRefundDistinctList");
		return list;
	}

	// 회원 환불 내역-2
	@Override
	public ArrayList<Bespeak> getRefundList(String id) {
		log.debug("MemberPersImple START - getRefundList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = new ArrayList<>();

		ArrayList<String> numList = getRefundDistinctList(id);
		ArrayList<Bespeak> allList = dao.getRefundList(id);

		int index = 0; // 게시판 list index
		// numList와 같은 주문 번호만 담기. 중복 주문번호(여러 종류 주문) 처리
		for (int i = 0; i < numList.size(); i += 1) { // 중복 없는 주문 번호 List
			int count = 0; // 중복 수

			for (int j = 0; j < allList.size(); j += 1) { // 모둔 주문 list
				String num = allList.get(j).getOrder_num();

				// numlist의 현재 주문번호와 allList의 주문번호가 같으면
				if (numList.get(i).equals(num)) {
					// 주문 번호가 중복되면
					if (count > 0) {
						list.get(index - 1).getBook().setNo(" 외 " + count + "권"); // 중복이므로 외권 표시
					}
					count += 1;

					// 주문번호가 같으면(중복X) - 게시판 list 추가
					if (count == 1) {
						allList.get(j).getBook().setNo("");
						list.add(allList.get(j));
						index += 1;
					}
				} else {
					count = 0;
				}
			}
		}
		// getNoTitle(list);

		log.debug("MemberPersImple END - getRefundList");
		return list;
	}

	// 도서 이름
	/*
	 * @Override public void getNoTitle(ArrayList<Bespeak> list) {
	 * log.debug("MemberPersImple START - getNoTitle");
	 * 
	 * BookPers bDao = sqlSession.getMapper(BookPers.class);
	 * 
	 * if (list.size() > 0) { for (int i = 0; i < list.size(); i += 1) { Bespeak
	 * order = list.get(i);
	 * 
	 * Book book = bDao.getBookInfo(order.getNo());
	 * 
	 * order.setNos(book.getTitle() + order.getNos()); list.set(i, order);
	 * 
	 * } }
	 * 
	 * log.debug("MemberPersImple END - getNoTitle"); }
	 */
}
