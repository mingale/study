package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import spring.mvc.bookstore.controller.EmailHandler;
import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Member;
import spring.mvc.bookstore.vo.Notice;
import spring.mvc.bookstore.vo.NoticeComment;

@Repository
public class HostPersImpl implements HostPers {
	@Autowired
	SqlSession sqlSession;
	
	// ���̵� �ߺ� Ȯ��
	@Override
	public int confirmId(String id) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.confirmId(id);
		return tmp;
	}

	// �̸��� ����key ���� Ȯ��-2) �ش� ������ ���� ��� UP
	@Override
	public int emailKeyRatingUp(String e_key) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.emailKeyRatingUp(e_key);
		return tmp;
	}

	// �̸��� ����key ���� Ȯ��-1
	@Override
	public int confirmEmailKey(String e_key) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int cnt = dao.confirmEmailKey(e_key);
		if (cnt > 0) {
			emailKeyRatingUp(e_key);
		}
		return cnt;
	}

	// �α���
	@Override
	public String signIn(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		String rating = dao.signIn(map);
		return rating;
	}

	// ȸ������
	@Override
	public int signUp(Member m) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.signUp(m);
		return tmp;
	}

	// �̸��� ���� ����
	@Override
	public String confirmEmail(String email) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		String tmp = dao.confirmEmail(email);
		return tmp;
	}

	// ����Ű ����
	@Override
	public int memberEmailKeyUpdate(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
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
		HostPers dao = sqlSession.getMapper(HostPers.class);
		String tmp = dao.getId(key);
		return tmp;
	}

	// ������ ��й�ȣ ��������
	@Override
	public String getPwd(String key) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		String tmp = dao.getPwd(key);
		return tmp;
	}


	// ���� ��ȣ
	@Override
	public String getShipping(String order_num) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		String tmp = dao.getShipping(order_num);
		return tmp;
	}

	// ������ �ֹ� ����-1
	@Override
	public ArrayList<Bespeak> getHostOrderList() {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		ArrayList<Bespeak> tmp = dao.getHostOrderList();
		return tmp;
	}

	// ������ �ֹ� ����-2
	@Override
	public ArrayList<Bespeak> getHostOrderDistinctList(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
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
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.getHostOrderCnt();
		return tmp;
	}

	// ������ �ֹ� ���� ����
	@Override
	public int orderStateUpdate(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.orderStateUpdate(map);
		return tmp;
	}

	// ��� ����
	@Override
	public int shippingInsert(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.shippingInsert(map);
		return tmp;
	}

	// ������ ȯ�� ���� �� ����
	@Override
	public int getRefundCnt() {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.getRefundCnt();
		return tmp;
	}

	// ������ ȯ�ҳ���-1) ��� �ֹ� ����
	@Override
	public ArrayList<Bespeak> getHostRefundList() {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		ArrayList<Bespeak> tmp = dao.getHostRefundList();
		return tmp;
	}

	// ������ ȯ�� ����-2
	@Override
	public ArrayList<Bespeak> getHostRefundDistinctList(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
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
		HostPers dao = sqlSession.getMapper(HostPers.class);
		ArrayList<Member> list = dao.getMemberList(map);
		return list;
	}

	// ȸ����
	@Override
	public int getMemberCnt() {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int cnt = dao.getMemberCnt();
		return cnt;
	}

	// ������ ȸ�� ����
	@Override
	public int setHostMemberUpdate(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int cnt = dao.setHostMemberUpdate(map);
		return cnt;
	}

	/*
	 * ERROR - ORA-02292: integrity constraint (BMS.SYS_C007739) violated - child
	 * record found FK�� ON DELETE CASCADE �ʼ�!
	 */

	// �ֹ� ����
	@Override
	public int orderDelete(String order_num) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int cnt = dao.orderDelete(order_num);
		return cnt;
	}

	@Override
	public int getNoticeCnt() {
		int cnt = 0;
		
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		cnt = mapper.getNoticeCnt();
		return cnt;
	}
	
	// ��������
	@Override
	public ArrayList<Notice> getNotice(Map<String, Object> map) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		ArrayList<Notice> list = mapper.getNotice(map);
		return list;
	}
	

	//�������� �۾��� ó��
	@Override
	public int noticeWritePro(Notice notice) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		int cnt = mapper.noticeWritePro(notice);
		return cnt;
	}

	//�������� �� ���� + ���
	@Override
	public Notice noticeAndCommentView(String idx) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		Notice notice = mapper.noticeAndCommentView(idx);
		return notice;
	}
	
	//�������� �󼼺���
	@Override
	public Notice noticeView(String idx) {
		
		//��� ������ ���۸� ��������
		Notice notice = noticeAndCommentView(idx);
		if(notice == null) {
			HostPers mapper = sqlSession.getMapper(HostPers.class);
			notice = mapper.noticeView(idx);
		}
		return notice;
	}

	///�������� �� ����
	@Override
	public int noticeUpdate(Notice notice) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		int cnt = mapper.noticeUpdate(notice);
		return cnt;
	}

	//�������� ��� �߰�
	public int noticeCommentAdd(NoticeComment nco) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		int cnt = mapper.noticeCommentAdd(nco);
		return cnt;
	}

	//�������� �ش���
	public ArrayList<NoticeComment> noticeCommentList(String notice_idx) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		ArrayList<NoticeComment> list = mapper.noticeCommentList(notice_idx);
		return list;
	}
	
}
