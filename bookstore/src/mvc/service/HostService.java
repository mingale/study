package mvc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HostService {
	
		//���̵� �ߺ� �˻�
		public void confirmId(HttpServletRequest req, HttpServletResponse res);
		
		//�̸��� �ߺ� �˻�
		public void confirmEmail(HttpServletRequest req, HttpServletResponse res);
		
		//�̸��� ���� key Ȯ��
		public void confirmEmailKey(HttpServletRequest req, HttpServletResponse res);
		
		//�α���
		public void signIn(HttpServletRequest req, HttpServletResponse res);
		
		//�α׾ƿ�
		public void signUp(HttpServletRequest req, HttpServletResponse res);

		//���̵�/��й�ȣ ã��
		public void memberFindEmailKey(HttpServletRequest req, HttpServletResponse res);
		
		//������ ����
		public void mainView(HttpServletRequest req, HttpServletResponse res);
		
		//ȸ�� ���� ã��
		public void memberQuery(HttpServletRequest req, HttpServletResponse res);
		
		//ȸ�� ���� ����
		public void memberUpdate(HttpServletRequest req, HttpServletResponse res);
		
		//ȸ�� Ż�� / ����
		public void memberDelete(HttpServletRequest req, HttpServletResponse res);

		//������ �ֹ� ����
		public void hostOrderView(HttpServletRequest req, HttpServletResponse res);
		
		//������ �ֹ� ���� ����
		public void orderStateUpdate(HttpServletRequest req, HttpServletResponse res);
		
		//������ �ֹ� ��� ����
		public void shippingPro(HttpServletRequest req, HttpServletResponse res);

		//������ ȯ�� ����
		public void getHostRefundView(HttpServletRequest req, HttpServletResponse res);
		
		//������ ȸ�� ���
		public void getMemberView(HttpServletRequest req, HttpServletResponse res);

		//������ ȸ�� ����
		public void setHostMemberUpdate(HttpServletRequest req, HttpServletResponse res);
		
		//������ �ֹ� ����
		public void orderDelete(HttpServletRequest req, HttpServletResponse res);

		//ȯ�� �Ϸ�
		public void refundOk(HttpServletRequest req, HttpServletResponse res);
		
		//ȯ�� �ź�
		public void refundNo(HttpServletRequest req, HttpServletResponse res);
		
		//������
		public void helpView(HttpServletRequest req, HttpServletResponse res);
		
		//���
		public void getResultTotal(HttpServletRequest req, HttpServletResponse res);
}
