package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
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
	
	/*
	 * MemberPers dao = sqlSession.getMapper(MemberPers.class); BookPers bDao =
	 * sqlSession.getMapper(BookPers.class);
	 * 
	 * Mapper를 멤버변수에서 선언하면 Bean 생성이 되지 않아 사용 불가능
	 * 
	 */


	// 회원 정보
	@Override
	public Member getMemberInfo(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		Member tmp = dao.getMemberInfo(id);
		return tmp;
	}

	// 회원 수정
	@Override
	public int memberUpdate(Member m) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.memberUpdate(m);
		return cnt;
	}

	// 회원 탈퇴 - id 남기기
	// email이 unique라 그대로 둠. NOT NULL
	@Override
	public int memberDelete(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.memberDelete(id);
		return tmp;
	}

	// 장바구니 추가
	@Override
	public int setCart(Cart cart) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setCart(cart);
		return tmp;
	}

	// 장바구니 목록
	@Override
	public ArrayList<Cart> getCartList(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Cart> tmp = dao.getCartList(id);
		return tmp;
	}

	// 장바구니 삭제
	@Override
	public int cartDelete(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.cartDelete(map);
		return tmp;
	}

	// 장바구니 수량 수정
	@Override
	public int cartCountUpdate(Cart cart) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.cartCountUpdate(cart);
		return tmp;
	}

	// 장바구니 중복 확인
	@Override
	public String cartCheck(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.cartCheck(map);
		return tmp;
	}

	// 장바구니 수량 추가
	@Override
	public int setCartCount(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setCartCount(map);
		return tmp;
	}

	// 찜 추가
	@Override
	public int setWishlist(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setWishlist(map);
		return tmp;
	}

	// 찜 중복 확인
	@Override
	public int wishlistCheck(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.wishlistCheck(map);
		if (cnt != 0) {
			cnt = 9;
		}
		return cnt;
	}

	// 주문 추가-1) 주문번호
	@Override
	public void orderNumberCreate() {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		dao.orderNumberCreate();
	}

	// 주문 추가-2) 장바구니 목록 중 해당 품목
	@Override
	public Map<String, Object> cartListBook(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		Map<String, Object> tmp = dao.cartListBook(map);
		return tmp;
	}

	// 주문 추가-3) Order에 저장하기
	@Override
	public int setOrder(Bespeak order) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setOrder(order);
		return tmp;
	}

	// 바로 주문 추가
	@Override
	public int setNowOrder(Bespeak order) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setNowOrder(order);
		return tmp;
	}

	// 주문 내역-1) 중복 없는 주문번호 목록
	@Override
	public ArrayList<String> getMemberOrderDistinctList(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<String> tmp = dao.getMemberOrderDistinctList(id);
		return tmp;
	}

	// 주문 내역-2) 회원의 주문 내역
	@Override
	public ArrayList<Bespeak> getMemberOrderList(String id) {
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
		return list;
	}

	// 해당 주문 정보
	@Override
	public ArrayList<Bespeak> getOrderDetail(String order_num) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = dao.getOrderDetail(order_num);
		return list;
	}

	// 회원 환불 내역-1) 해당 회원의 중복 없는 주문 목록
	@Override
	public ArrayList<String> getRefundDistinctList(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<String> list = dao.getRefundDistinctList(id);
		return list;
	}

	// 회원 환불 내역-2
	@Override
	public ArrayList<Bespeak> getRefundList(String id) {
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
		return list;
	}
}
