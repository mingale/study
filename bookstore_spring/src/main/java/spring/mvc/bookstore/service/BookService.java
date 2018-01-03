package spring.mvc.bookstore.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface BookService {

	//Ȩ
	public void indexView(HttpServletRequest req, Model model);
	
	//���� �˻�
	public void bookSearch(HttpServletRequest req, Model model);
	
	//public void bookNo(HttpServletRequest req, HttpServletResponse res);
	
	//���� ���� ���
	public void detailView(HttpServletRequest req, Model model);
	
	//���� ��� ���
	public void bookListView(HttpServletRequest req, Model model);
	
	//��� ���� ��� ���
	public void hostStockView(HttpServletRequest req, Model model);
	
	//���� ���� ó��
	public void bookUpdate(MultipartHttpServletRequest req, Model model);
	
	//���� ���� ó��
	public void bookDelete(HttpServletRequest req, Model model);
	
	//���� �߰� ȭ��
	public void bookInsertView(HttpServletRequest req, Model model);
}
