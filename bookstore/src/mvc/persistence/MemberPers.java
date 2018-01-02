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

	// ���̵� �ߺ� Ȯ��
	public int confirmId(String strId);

	// �̸��� ����key ���� Ȯ��
	public int confirmEmailKey(String key);

	// �α���
	public int signIn(String strId, String strPwd);

	// ȸ������
	public int signUp(Member m);

	// ȸ�� ����
	public Member getMemberInfo(String strId);

	//id ã�� ���ܰ� - �̸��� ���� ����
	public String confirmEmail(String email);
	
	//��й�ȣ ã�� �̸��� ����
	public int memberEmailKeyUpdate(String id, String key);
	
	// ���� ����
	public int sendGmail(String toEmail, String key, int view);

	//������ ���̵� ��������
	public String getId(String key);
	
	//������ ��й�ȣ ��������
	public String getPwd(String key);
	
	// ȸ�� ����
	public int memberUpdate(Member m);

	// ȸ�� Ż�� - id �����
	public int memberDelete(String strId);

	// ��ٱ��� �߰�
	public int setCart(String id, Book b, int count);

	// ��ٱ��� ���
	public ArrayList<Cart> getCartList(String id);

	// ��ٱ��� ����
	public int cartDelete(String id, String no);

	// ��ٱ��� ���� ����
	public int cartCountUpdate(String id, String no, int count);

	// ��ٱ��� �ߺ� Ȯ��
	public int cartCheck(String id, String no);

	// ��ٱ��� ���� �߰�
	public int setCartCount(String id, String no, int count);

	//�� �߰�
	public int setWishlist(String id, String no);
	
	//�� �ߺ� Ȯ��
	public int wishlistCheck(String id, String no);
	
	// �ֹ� �߰�
	public int setOrder(String[] nos, Bespeak order);

	// �ٷ� ���� �ֹ� �߰�
	public int setNowOrder(String no, Bespeak order, int nowCount);

	// �ֹ� ����
	public ArrayList<Bespeak> getMemberOrderList(String id);

	//�ش� �ֹ� ����
	public ArrayList<Bespeak> getOrderDetail(String order_num);
	
	//�����ȣ
	public String getShipping(String order_num);
	
	// ������ �ֹ� ����
	public ArrayList<Bespeak> getHostOrderList(int start, int end);

	// �ֹ� ���� �� ����
	public int getHostOrderCnt();

	// �ֹ� ���� ���� (����, ȸ�� ȯ�� ��û)
	public int orderStateUpdate(String num, int state);

	// ������ ��� ����
	public int shippingInsert(String order, String ship);

	// ������ ȯ�ҳ��� �� ����
	public int getRefundCnt();

	// ������ ȯ�ҳ���
	public ArrayList<Bespeak> getHostRefundList(int start, int end);

	// ȸ�� ���
	public ArrayList<Member> getMemberList(int start, int end);

	// ȸ����
	public int getMemberCnt();

	// ������ ȸ�� ����
	public int setHostMemberUpdate(String id, String memo, int rating);

	// ������ ȸ�� �ټ� ����
	public int setHostMemberAllUpdate(String id, String memo, String rating);

	// ������ ȸ�� �ټ� ����
	public int setHostMemberAllDelete(String id);

	// �ֹ� ����
	public int orderDelete(String order_num);

	// ȸ�� ȯ�� ����
	public ArrayList<Bespeak> getRefundList(String id);
	
	//������ 
	public void getNoTitle(ArrayList<Bespeak> list, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException;
}
