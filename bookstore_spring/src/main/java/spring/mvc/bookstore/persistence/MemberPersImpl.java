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
	 * Mapper�� ����������� �����ϸ� Bean ������ ���� �ʾ� ��� �Ұ���
	 * 
	 */

	private MemberPersImpl() {
	}

	// ���̵� �ߺ� Ȯ��
	@Override
	public int confirmId(String id) {
		log.debug("MemberPersImple START - confirmId");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.confirmId(id);

		log.debug("MemberPersImple END - confirmId");
		return tmp;
	}

	// �̸��� ����key ���� Ȯ��-2) �ش� ������ ���� ��� UP
	@Override
	public int emailKeyRatingUp(String e_key) {
		log.debug("MemberPersImple START - emailKeyRatingUp");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.emailKeyRatingUp(e_key);

		log.debug("MemberPersImple END - emailKeyRatingUp");
		return tmp;
	}

	// �̸��� ����key ���� Ȯ��-1
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

	// �α���
	@Override
	public int signIn(Map<String, Object> map) {
		log.debug("MemberPersImple START - signIn");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.signIn(map);

		log.debug("MemberPersImple END - signIn");
		return tmp;
	}

	// ȸ������
	@Override
	public int signUp(Member m) {
		log.debug("MemberPersImple START - signUp");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.signUp(m);

		log.debug("MemberPersImple END - signUp");
		return tmp;
	}

	// ȸ�� ����
	@Override
	public Member getMemberInfo(String id) {
		log.debug("MemberPersImple START - getMemberInfo");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		Member tmp = dao.getMemberInfo(id);
		;

		log.debug("MemberPersImple END - getMemberInfo");
		return tmp;
	}

	// �̸��� ���� ����
	@Override
	public String confirmEmail(String email) {
		log.debug("MemberPersImple START - confirmEmail");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.confirmEmail(email);
		log.debug("MemberPersImple END - confirmEmail");
		return tmp;
	}

	// ����Ű ����
	@Override
	public int memberEmailKeyUpdate(Map<String, Object> map) {
		log.debug("MemberPersImple START - memberEmailKeyUpdate");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.memberEmailKeyUpdate(map);

		log.debug("MemberPersImple END - memberEmailKeyUpdate");
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
		log.debug("MemberPersImple START - sendGmail");
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
			log.debug("Email ����");
			sendMail.send();
			log.debug("Email ���� �Ϸ�");
			cnt = 1;
		} catch (Exception e) { //throws MessagingException, UnsupportedEncodingException
			e.printStackTrace();
		}

		log.debug("MemberPersImple END - sendGmail");
		return cnt;
	}

	// ������ ���̵� ��������
	@Override
	public String getId(String key) {
		log.debug("MemberPersImple START - getId");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.getId(key);

		log.debug("MemberPersImple END - getId");
		return tmp;
	}

	// ������ ��й�ȣ ��������
	@Override
	public String getPwd(String key) {
		log.debug("MemberPersImple START - getPwd");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.getPwd(key);

		log.debug("MemberPersImple END - getPwd");
		return tmp;
	}

	// ȸ�� ����
	@Override
	public int memberUpdate(Member m) {
		log.debug("MemberPersImple START - memberUpdate");
		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		log.debug("MemberPersImple END - memberUpdate");
		return dao.memberUpdate(m);
	}

	// ȸ�� Ż�� - id �����
	// email�� unique�� �״�� ��. NOT NULL
	@Override
	public int memberDelete(String id) {
		log.debug("MemberPersImple START - memberDelete");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.memberDelete(id);

		log.debug("MemberPersImple END - memberDelete");
		return tmp;
	}

	// ��ٱ��� �߰�
	@Override
	public int setCart(Cart cart) {
		log.debug("MemberPersImple START - setCart");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setCart(cart);

		log.debug("MemberPersImple END - setCart");
		return tmp;
	}

	// ��ٱ��� ���
	@Override
	public ArrayList<Cart> getCartList(String id) {
		log.debug("MemberPersImple START - getCartList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Cart> tmp = dao.getCartList(id);

		log.debug("MemberPersImple END - getCartList");
		return tmp;
	}

	// ��ٱ��� ����
	@Override
	public int cartDelete(Map<String, Object> map) {
		log.debug("MemberPersImple START - cartDelete");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.cartDelete(map);

		log.debug("MemberPersImple END - cartDelete");
		return tmp;
	}

	// ��ٱ��� ���� ����
	@Override
	public int cartCountUpdate(Cart cart) {
		log.debug("MemberPersImple START - cartCountUpdate");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.cartCountUpdate(cart);

		log.debug("MemberPersImple END - cartCountUpdate");
		return tmp;
	}

	// ��ٱ��� �ߺ� Ȯ��
	@Override
	public String cartCheck(Map<String, Object> map) {
		log.debug("MemberPersImple START - cartCheck");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.cartCheck(map);

		log.debug("MemberPersImple END - cartCheck");
		return tmp;
	}

	// ��ٱ��� ���� �߰�
	@Override
	public int setCartCount(Map<String, Object> map) {
		log.debug("MemberPersImple START - setCartCount");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setCartCount(map);

		log.debug("MemberPersImple END - setCartCount");
		return tmp;
	}

	// �� �߰�
	@Override
	public int setWishlist(Map<String, Object> map) {
		log.debug("MemberPersImple START - setWishlist");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setWishlist(map);

		log.debug("MemberPersImple END - setWishlist");
		return tmp;
	}

	// �� �ߺ� Ȯ��
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

	// �ֹ� �߰�-1) �ֹ���ȣ
	@Override
	public void orderNumberCreate() {
		log.debug("MemberPersImple START - orderNumberCreate");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		dao.orderNumberCreate();

		log.debug("MemberPersImple END - orderNumberCreate");
	}

	// �ֹ� �߰�-2) ��ٱ��� ��� �� �ش� ǰ��
	@Override
	public Map<String, Object> cartListBook(Map<String, Object> map) {
		log.debug("MemberPersImple START - cartListBook");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		Map<String, Object> tmp = dao.cartListBook(map);

		log.debug("MemberPersImple END - cartListBook");
		return tmp;
	}

	// �ֹ� �߰�-3) Order�� �����ϱ�
	@Override
	public int setOrder(Bespeak order) {
		log.debug("MemberPersImple START- setOrder");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setOrder(order);

		log.debug("MemberPersImple END- setOrder");
		return tmp;
	}

	// �ٷ� �ֹ� �߰�
	@Override
	public int setNowOrder(Bespeak order) {
		log.debug("MemberPersImple START - setNowOrder");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.setNowOrder(order);

		log.debug("MemberPersImple END - setNowOrder");
		return tmp;
	}

	// �ֹ� ����-1) �ߺ� ���� �ֹ���ȣ ���
	@Override
	public ArrayList<String> getMemberOrderDistinctList(String id) {
		log.debug("MemberPersImple - getMemberOrderDistinctList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<String> tmp = dao.getMemberOrderDistinctList(id);

		log.debug("MemberPersImple - getMemberOrderDistinctList");
		return tmp;
	}

	// �ֹ� ����-2) ȸ���� �ֹ� ����
	@Override
	public ArrayList<Bespeak> getMemberOrderList(String id) {
		log.debug("MemberPersImple START - getMemberOrderList");

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
		// getNoTitle(list);

		log.debug("MemberPersImple END - getMemberOrderList");
		return list;
	}

	// �ش� �ֹ� ����
	@Override
	public ArrayList<Bespeak> getOrderDetail(String order_num) {
		log.debug("MemberPersImple START - getOrderDetail");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> list = dao.getOrderDetail(order_num);

		log.debug("MemberPersImple END - getOrderDetail");
		return list;
	}

	// ���� ��ȣ
	@Override
	public String getShipping(String order_num) {
		log.debug("MemberPersImple - getShipping");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		String tmp = dao.getShipping(order_num);

		log.debug("MemberPersImple - getShipping");
		return tmp;
	}

	// ������ �ֹ� ����-1
	@Override
	public ArrayList<Bespeak> getHostOrderList() {
		log.debug("MemberPersImple - getHostOrderList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> tmp = dao.getHostOrderList();

		log.debug("MemberPersImple - getHostOrderList");
		return tmp;
	}

	// ������ �ֹ� ����-2
	@Override
	public ArrayList<Bespeak> getHostOrderDistinctList(Map<String, Object> map) {
		log.debug("MemberPersImple START - getHostOrderDistinctList");

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

		// �ֹ� ���� �̸�
		// getNoTitle(list);

		log.debug("MemberPersImple END - getHostOrderDistinctList");
		return list;
	}

	// �ֹ� ���� �� ����
	@Override
	public int getHostOrderCnt() {
		log.debug("MemberPersImple START - getHostOrderCnt");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.getHostOrderCnt();

		log.debug("MemberPersImple END - getHostOrderCnt");
		return tmp;
	}

	// ������ �ֹ� ���� ����
	@Override
	public int orderStateUpdate(Map<String, Object> map) {
		log.debug("MemberPersImple START - orderStateUpdate");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.orderStateUpdate(map);

		log.debug("MemberPersImple END - orderStateUpdate");
		return tmp;
	}

	// ��� ����
	@Override
	public int shippingInsert(Map<String, Object> map) {
		log.debug("MemberPersImple START - shippingInsert");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.shippingInsert(map);

		log.debug("MemberPersImple END - shippingInsert");
		return tmp;
	}

	// ������ ȯ�� ���� �� ����
	@Override
	public int getRefundCnt() {
		log.debug("MemberPersImple START - getRefundCnt");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int tmp = dao.getRefundCnt();

		log.debug("MemberPersImple END - getRefundCnt");
		return tmp;
	}

	// ������ ȯ�ҳ���-1) ��� �ֹ� ����
	@Override
	public ArrayList<Bespeak> getHostRefundList() {
		log.debug("MemberPersImple START - getHostRefundList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Bespeak> tmp = dao.getHostRefundList();

		log.debug("MemberPersImple END - getHostRefundList");
		return tmp;
	}

	// ������ ȯ�� ����-2
	@Override
	public ArrayList<Bespeak> getHostRefundDistinctList(Map<String, Object> map) {
		log.debug("MemberPersImple START - getHostRefundDistinctList");
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
		// getNoTitle(list);

		log.debug("MemberPersImple END - getHostRefundDistinctList");
		return list;
	}

	// ȸ�� ���
	@Override
	public ArrayList<Member> getMemberList(Map<String, Object> map) {
		log.debug("MemberPersImple START - getMemberList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<Member> list = dao.getMemberList(map);

		log.debug("MemberPersImple END - getMemberList");
		return list;
	}

	// ȸ����
	@Override
	public int getMemberCnt() {
		log.debug("MemberPersImple START - getMemberCnt");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.getMemberCnt();

		log.debug("MemberPersImple END - getMemberCnt");
		return cnt;
	}

	// ������ ȸ�� ����
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
		log.debug("MemberPersImple START - orderDelete");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		int cnt = dao.orderDelete(order_num);

		log.debug("MemberPersImple END - orderDelete");
		return cnt;
	}

	// ȸ�� ȯ�� ����-1) �ش� ȸ���� �ߺ� ���� �ֹ� ���
	@Override
	public ArrayList<String> getRefundDistinctList(String id) {
		log.debug("MemberPersImple START - getRefundDistinctList");

		MemberPers dao = sqlSession.getMapper(MemberPers.class);
		ArrayList<String> list = dao.getRefundDistinctList(id);

		log.debug("MemberPersImple END - getRefundDistinctList");
		return list;
	}

	// ȸ�� ȯ�� ����-2
	@Override
	public ArrayList<Bespeak> getRefundList(String id) {
		log.debug("MemberPersImple START - getRefundList");

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
		// getNoTitle(list);

		log.debug("MemberPersImple END - getRefundList");
		return list;
	}

	// ���� �̸�
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
