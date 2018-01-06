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
	 * Mapper�� ����������� �����ϸ� Bean ������ ���� �ʾ� ��� �Ұ���
	 * 
	 */


	// ȸ�� ����
	@Override
	public Member getMemberInfo(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		Member tmp = dao.getMemberInfo(id);
		return tmp;
	}

	// ȸ�� ����
	@Override
	public int memberUpdate(Member m) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.memberUpdate(m);
		return cnt;
	}

	// ȸ�� Ż�� - id �����
	// email�� unique�� �״�� ��. NOT NULL
	@Override
	public int memberDelete(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.memberDelete(id);
		return tmp;
	}

	// ��ٱ��� �߰�
	@Override
	public int setCart(Cart cart) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setCart(cart);
		return tmp;
	}

	// ��ٱ��� ���
	@Override
	public ArrayList<Cart> getCartList(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Cart> tmp = dao.getCartList(id);
		return tmp;
	}

	// ��ٱ��� ����
	@Override
	public int cartDelete(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.cartDelete(map);
		return tmp;
	}

	// ��ٱ��� ���� ����
	@Override
	public int cartCountUpdate(Cart cart) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.cartCountUpdate(cart);
		return tmp;
	}

	// ��ٱ��� �ߺ� Ȯ��
	@Override
	public String cartCheck(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.cartCheck(map);
		return tmp;
	}

	// ��ٱ��� ���� �߰�
	@Override
	public int setCartCount(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setCartCount(map);
		return tmp;
	}

	// �� �߰�
	@Override
	public int setWishlist(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setWishlist(map);
		return tmp;
	}

	// �� �ߺ� Ȯ��
	@Override
	public int wishlistCheck(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.wishlistCheck(map);
		if (cnt != 0) {
			cnt = 9;
		}
		return cnt;
	}

	// �ֹ� �߰�-1) �ֹ���ȣ
	@Override
	public void orderNumberCreate() {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		dao.orderNumberCreate();
	}

	// �ֹ� �߰�-2) ��ٱ��� ��� �� �ش� ǰ��
	@Override
	public Map<String, Object> cartListBook(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		Map<String, Object> tmp = dao.cartListBook(map);
		return tmp;
	}

	// �ֹ� �߰�-3) Order�� �����ϱ�
	@Override
	public int setOrder(Bespeak order) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setOrder(order);
		return tmp;
	}

	// �ٷ� �ֹ� �߰�
	@Override
	public int setNowOrder(Bespeak order) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setNowOrder(order);
		return tmp;
	}

	// �ֹ� ����-1) �ߺ� ���� �ֹ���ȣ ���
	@Override
	public ArrayList<String> getMemberOrderDistinctList(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<String> tmp = dao.getMemberOrderDistinctList(id);
		return tmp;
	}

	// �ֹ� ����-2) ȸ���� �ֹ� ����
	@Override
	public ArrayList<Bespeak> getMemberOrderList(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = new ArrayList<>();

		ArrayList<String> numList = getMemberOrderDistinctList(id);
		ArrayList<Bespeak> allList = dao.getMemberOrderList(id);

		int index = 0; // �Խ��� list index
		// numList�� ���� �ֹ� ��ȣ�� ���. �ߺ� �ֹ���ȣ(���� ���� �ֹ�) ó��
		for (int i = 0; i < numList.size(); i += 1) { // �ߺ� ���� �ֹ� ��ȣ List
			int count = 0; // �ߺ� ��

			for (int j = 0; j < allList.size(); j += 1) { // ��� �ֹ� list
				String num = allList.get(j).getOrder_num();

				// numlist�� ���� �ֹ���ȣ�� allList�� �ֹ���ȣ�� ������
				if (numList.get(i).equals(num)) {
					// �ֹ� ��ȣ�� �ߺ��Ǹ�
					if (count > 0) {
						list.get(index - 1).getBook().setNo(" �� " + count + "��"); // �ߺ��̹Ƿ� �ܱ� ǥ��
					}
					count += 1;

					// �ֹ���ȣ�� ������(�ߺ�X) - �Խ��� list �߰�
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

	// �ش� �ֹ� ����
	@Override
	public ArrayList<Bespeak> getOrderDetail(String order_num) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = dao.getOrderDetail(order_num);
		return list;
	}

	// ȸ�� ȯ�� ����-1) �ش� ȸ���� �ߺ� ���� �ֹ� ���
	@Override
	public ArrayList<String> getRefundDistinctList(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<String> list = dao.getRefundDistinctList(id);
		return list;
	}

	// ȸ�� ȯ�� ����-2
	@Override
	public ArrayList<Bespeak> getRefundList(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = new ArrayList<>();

		ArrayList<String> numList = getRefundDistinctList(id);
		ArrayList<Bespeak> allList = dao.getRefundList(id);

		int index = 0; // �Խ��� list index
		// numList�� ���� �ֹ� ��ȣ�� ���. �ߺ� �ֹ���ȣ(���� ���� �ֹ�) ó��
		for (int i = 0; i < numList.size(); i += 1) { // �ߺ� ���� �ֹ� ��ȣ List
			int count = 0; // �ߺ� ��

			for (int j = 0; j < allList.size(); j += 1) { // ��� �ֹ� list
				String num = allList.get(j).getOrder_num();

				// numlist�� ���� �ֹ���ȣ�� allList�� �ֹ���ȣ�� ������
				if (numList.get(i).equals(num)) {
					// �ֹ� ��ȣ�� �ߺ��Ǹ�
					if (count > 0) {
						list.get(index - 1).getBook().setNo(" �� " + count + "��"); // �ߺ��̹Ƿ� �ܱ� ǥ��
					}
					count += 1;

					// �ֹ���ȣ�� ������(�ߺ�X) - �Խ��� list �߰�
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
