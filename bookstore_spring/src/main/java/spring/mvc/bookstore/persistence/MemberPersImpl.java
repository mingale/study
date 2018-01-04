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
	
	/*
	 * MemberPers dao = sqlSession.getMapper(MemberPers.class); BookPers bDao =
	 * sqlSession.getMapper(BookPers.class);
	 * 
	 * Mapper�� ����������� �����ϸ� Bean ������ ���� �ʾ� ��� �Ұ���
	 * 
	 */

	private MemberPersImpl() {
	}

	// ���̵� �ߺ� Ȯ��
	@Override
	public int confirmId(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.confirmId(id);
		return tmp;
	}

	// �̸��� ����key ���� Ȯ��-2) �ش� ������ ���� ��� UP
	@Override
	public int emailKeyRatingUp(String e_key) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.emailKeyRatingUp(e_key);
		return tmp;
	}

	// �̸��� ����key ���� Ȯ��-1
	@Override
	public int confirmEmailKey(String e_key) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.confirmEmailKey(e_key);
		if (cnt > 0) {
			emailKeyRatingUp(e_key);
		}
		return cnt;
	}

	// �α���
	@Override
	public int signIn(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.signIn(map);
		return tmp;
	}

	// ȸ������
	@Override
	public int signUp(Member m) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.signUp(m);
		return tmp;
	}

	// ȸ�� ����
	@Override
	public Member getMemberInfo(String id) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		Member tmp = dao.getMemberInfo(id);
		return tmp;
	}

	// �̸��� ���� ����
	@Override
	public String confirmEmail(String email) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.confirmEmail(email);
		return tmp;
	}

	// ����Ű ����
	@Override
	public int memberEmailKeyUpdate(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.memberEmailKeyUpdate(map);
		return tmp;
	}

	// ���� ����
	/*
	 * gmail �̿� ���� ���� gmail > ȯ�� ���� > ���� �� pop/IMAP > IMAP �׼��� > IMAP ������� ���� �� ���� >
	 * �α��� �� ���� > ����� �� �� ����Ʈ > ���� ������ ���� �� ��� : ������� ����(2�ܰ� ���� ��� ���̸� ���� ����)
	 */
	@Autowired
	JavaMailSender mailSender;

	@Override
	public int sendGmail(String toEmail, String key, int view) {
		int cnt = 0;

		try {
			EmailHandler sendMail = new EmailHandler(mailSender);
			if (view == 0) {
				sendMail.setSubject("BMS ȸ������ ���� ����");
				sendMail.setText(new StringBuffer().append("BMS ȸ������ ���� �����Դϴ�. ������ ���� ȸ�������� �Ϸ��ϼ���.<br>")
						.append("<a href='http://localhost:8081/bookstore/emailCheck?key=").append(key)
						.append("&view=" + view + "'>����</a>").toString());
			} else if (view == 1) {
				sendMail.setSubject("BMS ���̵� ã�� ���� ����");
				sendMail.setText(new StringBuffer().append("BMS ���̵� ã�� ���� �����Դϴ�. ������ ���� ���̵� ã������.<br>")
						.append("<a href='http://localhost:8081/bookstore/emailCheck?key=").append(key)
						.append("&view=" + view + "'>����</a>").toString());
			} else if (view == 2) {
				sendMail.setSubject("BMS ��й�ȣ ã�� ���� ����");
				sendMail.setText(new StringBuffer().append("BMS ��й�ȣ ã�� ���� �����Դϴ�. ������ ���� ��й�ȣ�� ã������.<br>")
						.append("<a href='http://localhost:8081/bookstore/emailCheck?key=").append(key)
						.append("&view=" + view + "'>����</a>").toString());
			}
			sendMail.setFrom("admin@bookstore.com", "������");
			sendMail.setTo(toEmail);
			System.out.println("Email ����");
			sendMail.send();
			System.out.println("Email ���� �Ϸ�");
			cnt = 1;
		} catch (Exception e) { //throws MessagingException, UnsupportedEncodingException
			e.printStackTrace();
		}
		return cnt;
	}

	// ������ ���̵� ��������
	@Override
	public String getId(String key) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.getId(key);
		return tmp;
	}

	// ������ ��й�ȣ ��������
	@Override
	public String getPwd(String key) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.getPwd(key);
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

	// ���� ��ȣ
	@Override
	public String getShipping(String order_num) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.getShipping(order_num);
		return tmp;
	}

	// ������ �ֹ� ����-1
	@Override
	public ArrayList<Bespeak> getHostOrderList() {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> tmp = dao.getHostOrderList();
		return tmp;
	}

	// ������ �ֹ� ����-2
	@Override
	public ArrayList<Bespeak> getHostOrderDistinctList(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = new ArrayList<>();

		// �ֹ� ��� - ù��° ������ ���� ��ȣ ǥ��
		// ** rownum�� ��Ī�� ���� ������ ���� ������ �������� ����� ���ư��� �ʴ´�.

		ArrayList<Bespeak> numList = dao.getHostOrderDistinctList(map);
		ArrayList<Bespeak> allList = getHostOrderList();

		int index = 0; // �Խ��� list index
		// numList�� ���� �ֹ� ��ȣ�� ���. �ߺ� �ֹ���ȣ(���� ���� �ֹ�) ó��
		for (int i = 0; i < numList.size(); i += 1) { // �ߺ� ���� �ֹ� ��ȣ List
			int count = 0; // �ߺ� ��

			for (int j = 0; j < allList.size(); j += 1) { // ��� �ֹ� list
				String num = allList.get(j).getOrder_num();

				// numlist�� ���� �ֹ���ȣ�� allList�� �ֹ���ȣ�� ������
				if (numList.get(i).getOrder_num().equals(num)) {
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

	// �ֹ� ���� �� ����
	@Override
	public int getHostOrderCnt() {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.getHostOrderCnt();
		return tmp;
	}

	// ������ �ֹ� ���� ����
	@Override
	public int orderStateUpdate(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.orderStateUpdate(map);
		return tmp;
	}

	// ��� ����
	@Override
	public int shippingInsert(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.shippingInsert(map);
		return tmp;
	}

	// ������ ȯ�� ���� �� ����
	@Override
	public int getRefundCnt() {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.getRefundCnt();
		return tmp;
	}

	// ������ ȯ�ҳ���-1) ��� �ֹ� ����
	@Override
	public ArrayList<Bespeak> getHostRefundList() {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> tmp = dao.getHostRefundList();
		return tmp;
	}

	// ������ ȯ�� ����-2
	@Override
	public ArrayList<Bespeak> getHostRefundDistinctList(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = new ArrayList<>();

		ArrayList<Bespeak> numList = dao.getHostRefundDistinctList(map);
		ArrayList<Bespeak> allList = getHostRefundList();

		int index = 0; // �Խ��� list index
		// numList�� ���� �ֹ� ��ȣ�� ���. �ߺ� �ֹ���ȣ(���� ���� �ֹ�) ó��
		for (int i = 0; i < numList.size(); i += 1) { // �ߺ� ���� �ֹ� ��ȣ List
			int count = 0; // �ߺ� ��

			for (int j = 0; j < allList.size(); j += 1) { // ��� �ֹ� list
				String num = allList.get(j).getOrder_num();

				// numlist�� ���� �ֹ���ȣ�� allList�� �ֹ���ȣ�� ������
				if (numList.get(i).getOrder_num().equals(num)) {
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

	// ȸ�� ���
	@Override
	public ArrayList<Member> getMemberList(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Member> list = dao.getMemberList(map);
		return list;
	}

	// ȸ����
	@Override
	public int getMemberCnt() {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.getMemberCnt();
		return cnt;
	}

	// ������ ȸ�� ����
	@Override
	public int setHostMemberUpdate(Map<String, Object> map) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.setHostMemberUpdate(map);
		return cnt;
	}

	/*
	 * ERROR - ORA-02292: integrity constraint (BMS.SYS_C007739) violated - child
	 * record found FK�� ON DELETE CASCADE �ʼ�!
	 */
	// ������ ȸ�� �ټ� ����
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
	 * log.debug("setHostMemberAllDelete() ����"); } finally { try { if (ps
	 * != null) ps.close(); if (conn != null) conn.close(); } catch (SQLException e)
	 * { e.printStackTrace(); } } return cnt; }
	 */

	// �ֹ� ����
	@Override
	public int orderDelete(String order_num) {
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.orderDelete(order_num);
		return cnt;
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
