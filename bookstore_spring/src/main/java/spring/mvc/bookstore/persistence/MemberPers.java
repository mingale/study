package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Map;

import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Cart;
import spring.mvc.bookstore.vo.Member;

public interface MemberPers {

	// ȸ�� ����
	public Member getMemberInfo(String id);

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

	// ȸ�� ȯ�� ����-1) �ش� ȸ���� �ߺ� ���� �ֹ� ���
	public ArrayList<String> getRefundDistinctList(String id);

	// ȸ�� ȯ�� ����-2
	public ArrayList<Bespeak> getRefundList(String id);

}
