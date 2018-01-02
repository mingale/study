package spring.mvc.bookstore.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface GuestService {

	//�ֹ� ���
	public void orderView(HttpServletRequest req, Model model);
	
	//��ٱ��� ���
	public void cartView(HttpServletRequest req, Model model);
	
	//���� ��ư (��ٱ��� ���, ���ϱ�)
	public void bookBtnPro(HttpServletRequest req, Model model);
	
	//��ٱ��� ó��
	public void cartPro(HttpServletRequest req, Model model);
	
	//�ֹ� ó��
	public void addOrder(HttpServletRequest req, Model model);

	//�ֹ� ����
	public void myOrderView(HttpServletRequest req, Model model);
	
	//�ֹ� �� ������
	public void orderDetailView(HttpServletRequest req, Model model);
	
	//ȸ�� ȯ�� ��û
	public void orderRefund(HttpServletRequest req, Model model);
	
	//ȸ�� ȯ�� ����
	public void getRefundView(HttpServletRequest req, Model model);
	
}