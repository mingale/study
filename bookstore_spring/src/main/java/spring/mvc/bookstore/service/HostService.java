package spring.mvc.bookstore.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface HostService {
	
		//���̵� �ߺ� �˻�
		public void confirmId(HttpServletRequest req, Model model);
		
		//�̸��� �ߺ� �˻�
		public void confirmEmail(HttpServletRequest req, Model model);
		
		//�̸��� ���� key Ȯ��
		public void confirmEmailKey(HttpServletRequest req, Model model);
		
		//�α���
		public void signIn(HttpServletRequest req, Model model);
		
		//�α׾ƿ�
		public void signUp(HttpServletRequest req, Model model);

		//���̵�/��й�ȣ ã��
		public void memberFindEmailKey(HttpServletRequest req, Model model);
		
		//������ ����
		public void mainView(HttpServletRequest req, Model model);
		
		//ȸ�� ���� ã��
		public void memberQuery(HttpServletRequest req, Model model);
		
		//ȸ�� ���� ����
		public void memberUpdate(HttpServletRequest req, Model model);
		
		//ȸ�� Ż�� / ����
		public void memberDelete(HttpServletRequest req, Model model);

		//������ �ֹ� ����
		public void hostOrderView(HttpServletRequest req, Model model);
		
		//������ �ֹ� ���� ����
		public void orderStateUpdate(HttpServletRequest req, Model model);
		
		//������ �ֹ� ��� ����
		public void shippingPro(HttpServletRequest req, Model model);

		//������ ȯ�� ����
		public void getHostRefundView(HttpServletRequest req, Model model);
		
		//������ ȸ�� ���
		public void getMemberView(HttpServletRequest req, Model model);

		//������ ȸ�� ����
		public void setHostMemberUpdate(HttpServletRequest req, Model model);
		
		//������ �ֹ� ����
		public void orderDelete(HttpServletRequest req, Model model);

		//ȯ�� �Ϸ�
		public void refundOk(HttpServletRequest req, Model model);
		
		//ȯ�� �ź�
		public void refundNo(HttpServletRequest req, Model model);
		
		//������
		public void helpView(HttpServletRequest req, Model model);
		
		//���
		public void getResultTotal(HttpServletRequest req, Model model);
		
		//��������
		public void getNotice(HttpServletRequest req, Model model);
		
		//�������� �۾��� ó��
		public void noticeWritePro(HttpServletRequest req, Model model);
		
		//�������� �󼼺���
		public void noticeView(HttpServletRequest req, Model model);
}
