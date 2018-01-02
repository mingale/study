package mvc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GuestService {

	//�ֹ� ���
	public void orderView(HttpServletRequest req, HttpServletResponse res);
	
	//��ٱ��� ���
	public void cartView(HttpServletRequest req, HttpServletResponse res);
	
	//���� ��ư (��ٱ��� ���, ���ϱ�)
	public void bookBtnPro(HttpServletRequest req, HttpServletResponse res);
	
	//��ٱ��� ó��
	public void cartPro(HttpServletRequest req, HttpServletResponse res);
	
	//�ֹ� ó��
	public void addOrder(HttpServletRequest req, HttpServletResponse res);

	//�ֹ� ����
	public void myOrderView(HttpServletRequest req, HttpServletResponse res);
	
	//�ֹ� �� ������
	public void orderDetailView(HttpServletRequest req, HttpServletResponse res);
	
	//ȸ�� ȯ�� ��û
	public void orderRefund(HttpServletRequest req, HttpServletResponse res);
	
	//ȸ�� ȯ�� ����
	public void getRefundView(HttpServletRequest req, HttpServletResponse res);
	
}