package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Map;

import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Member;
import spring.mvc.bookstore.vo.Notice;
import spring.mvc.bookstore.vo.NoticeComment;

public interface HostPers {

	// ���̵� �ߺ� Ȯ��
	public int confirmId(String id);

	// �̸��� ����key ���� Ȯ��-1
	public int confirmEmailKey(String e_key);

	// �̸��� ����key ���� Ȯ��-2
	public int emailKeyRatingUp(String e_key);

	// �α���
	public String signIn(Map<String, Object> map);

	// ȸ������
	public int signUp(Member m);

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

	// �ֹ� ����
	public int orderDelete(String order_num);

	//�������� �Խñ� �Ѽ�
	public int getNoticeCnt(); 
	
	//��������
	public ArrayList<Notice> getNotice(Map<String, Object> map);
	
	//�������� �۾��� ó��
	public int noticeWritePro(Notice notice);

	//�������� �󼼺��� + ���
	public Notice noticeAndCommentView(String idx);
	
	//�������� �󼼺���
	public Notice noticeView(String idx);
	
	///�������� �� ����
	public int noticeUpdate(Notice notice);
	
	//�������� ��� �߰�
	public int noticeCommentAdd(NoticeComment nco);
	
	//�������� �ش���
	public ArrayList<NoticeComment> noticeCommentList(String notice_idx);
}
