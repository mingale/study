package mvc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BookService {

	//Ȩ
	public void indexView(HttpServletRequest req, HttpServletResponse res);
	
	//���� �˻�
	public void bookSearch(HttpServletRequest req, HttpServletResponse res);
	
	//
	//public void bookNo(HttpServletRequest req, HttpServletResponse res);
	
	//���� ���� ���
	public void detailView(HttpServletRequest req, HttpServletResponse res);
	
	//���� ��� ���
	public void bookListView(HttpServletRequest req, HttpServletResponse res);
	
	//��� ���� ��� ���
	public void hostStockView(HttpServletRequest req, HttpServletResponse res);
	
	//���� ���� ó��
	public void bookUpdate(HttpServletRequest req, HttpServletResponse res);
	
	//���� ���� ó��
	public void bookDelete(HttpServletRequest req, HttpServletResponse res);
	
	//���� �߰� ȭ��
	public void bookInsertView(HttpServletRequest req, HttpServletResponse res);
}
