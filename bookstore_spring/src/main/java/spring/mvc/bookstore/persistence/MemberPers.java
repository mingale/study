package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Map;

import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Cart;
import spring.mvc.bookstore.vo.Member;

public interface MemberPers {

	// ���̵� �ߺ� Ȯ��
	public int confirmId(String id);

	// �̸��� ����key ���� Ȯ��-1
	public int confirmEmailKey(String e_key);

	// �̸��� ����key ���� Ȯ��-2
	public int emailKeyRatingUp(String e_key);

	// �α���
	public int signIn(Map<String, Object> map);

	// ȸ������
	public int signUp(Member m);

	// ȸ�� ����
	public Member getMemberInfo(String id);

	// id ã�� ���ܰ� - �̸��� ���� ����
	public String confirmEmail(String email);

	// ��й�ȣ ã�� �̸��� ����
	public int memberEmailKeyUpdate(Map<String, Object> map);

	// ���� ����
	public int sendGmail(String toEmail, String key, int view);

	// ������ ���̵� ��������
	public String getId(String key);

	// ������ ��й�ȣ ��������
	public String getPwd(String key);

	// ȸ�� ����
	public int memberUpdate(Member m);

	// ȸ�� Ż�� - id �����
	public int memberDelete(String id);

	// ��ٱ��� �߰�
	public int setCart(Cart cart);

	// ��ٱ��� ���
	public ArrayList<Cart> getCartList(String id);

	// ��ٱ��� ����
	public int cartDelete(Map<String, Object> map);

	// ��ٱ��� ���� ����
	public int cartCountUpdate(Cart cart);

	// ��ٱ��� �ߺ� Ȯ��
	public String cartCheck(Map<String, Object> map);

	// ��ٱ��� ���� �߰�
	public int setCartCount(Map<String, Object> map);

	// �� �߰�
	public int setWishlist(Map<String, Object> map);

	// �� �ߺ� Ȯ��
	public int wishlistCheck(Map<String, Object> map);

	// �ֹ� �߰�-1) �ֹ���ȣ
	public void orderNumberCreate();

	// �ֹ� �߰�-2) ��ٱ��� ��� �� �ش� ǰ��
	public Map<String, Object> cartListBook(Map<String, Object> map);

	// �ֹ� �߰�-3
	public int setOrder(Bespeak order);

	// �ٷ� ���� �ֹ� �߰�
	public int setNowOrder(Bespeak order);

	// �ֹ� ����-1) �ߺ� ���� �ֹ���ȣ ���
	public ArrayList<String> getMemberOrderDistinctList(String id);

	// �ֹ� ����-2) ȸ���� �ֹ� ����
	public ArrayList<Bespeak> getMemberOrderList(String id);

	// �ش� �ֹ� ����
	public ArrayList<Bespeak> getOrderDetail(String order_num);

	// �����ȣ
	public String getShipping(String order_num);

	// ������ �ֹ� ����-1) ��� ����
	public ArrayList<Bespeak> getHostOrderList();

	// ������ �ֹ� ����-2) �ߺ� ���� ����
	public ArrayList<Bespeak> getHostOrderDistinctList(Map<String, Object> map);

	// �ֹ� ���� �� ����
	public int getHostOrderCnt();

	// �ֹ� ���� ���� (����, ȸ�� ȯ�� ��û)
	public int orderStateUpdate(Map<String, Object> map);

	// ������ ��� ����
	public int shippingInsert(Map<String, Object> map);

	// ������ ȯ�ҳ��� �� ����
	public int getRefundCnt();

	// ������ ȯ�ҳ���-1) ��� �ֹ� ����
	public ArrayList<Bespeak> getHostRefundList();

	// ������ ȯ�ҳ���-2) �ߺ� ���� �ֹ� ����
	public ArrayList<Bespeak> getHostRefundDistinctList(Map<String, Object> map);

	// ȸ�� ���
	public ArrayList<Member> getMemberList(Map<String, Object> map);

	// ȸ����
	public int getMemberCnt();

	// ������ ȸ�� ����
	public int setHostMemberUpdate(Map<String, Object> map);

	// ������ ȸ�� �ټ� ����
	// public int setHostMemberAllDelete(String id);

	// �ֹ� ����
	public int orderDelete(String order_num);

	// ȸ�� ȯ�� ����-1) �ش� ȸ���� �ߺ� ���� �ֹ� ���
	public ArrayList<String> getRefundDistinctList(String id);

	// ȸ�� ȯ�� ����-2
	public ArrayList<Bespeak> getRefundList(String id);

	// ������
	// public void getNoTitle(ArrayList<Bespeak> list);
}
