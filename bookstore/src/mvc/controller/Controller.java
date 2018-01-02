package mvc.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.service.BookService;
import mvc.service.BookServiceImpl;
import mvc.service.GuestService;
import mvc.service.GuestServiceImpl;
import mvc.service.HostService;
import mvc.service.HostServiceImpl;

/**
 * 
 * BMS : Bookstore Management System
 *
 */
@WebServlet("*.do")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HostService hs = new HostServiceImpl();
	GuestService ms = new GuestServiceImpl();
	BookService bs = new BookServiceImpl();
       
    public Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	public void actionDo(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		
		String viewPage = null;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		
		String url = uri.substring(contextPath.length()); //�Ķ���Ͱ��� url �ּҿ� ���ԵǼ� �Ѿ���� �ʴ´�.

/*
		//<a href="/bookstore/detail.do?no=111">
		System.out.println(uri);		 // - /bookstore/detail.do
		System.out.println(contextPath); // - /bookstore
		System.out.println(url);		 // - /detail.do
*/
		
		//main
		if(url.equals("/index.do")) {
			System.out.println("index.do");
			
			bs.indexView(req, res);
			
			viewPage = "/jsp/main/index.jsp";

		//���� �˻�
		} else if(url.equals("/bookSearch.do")) {
			System.out.println("bookSearch.do");
			
			bs.bookSearch(req, res);
			
			viewPage = "/jsp/main/bookListSearch.jsp";
			
		//���� �� ����
		} else if(url.equals("/detail.do")) {
			System.out.println("detail.do");
			
			bs.detailView(req, res);

			viewPage = "/jsp/main/detail.jsp";
			
		//���� ����Ʈ
		} else if(url.equals("/bookList.do")) {
			System.out.println("bookList.do");
			
			bs.bookListView(req, res);
			
			viewPage = "/jsp/main/bookList.jsp";
			
		//�α��� ȭ��
		} else if(url.equals("/signIn.do")) {
			System.out.println("signIn.do");
		
			viewPage = "/jsp/join/signIn.jsp";
			
		//�α��� ó��
		} else if(url.equals("/signInPro.do")) {
			System.out.println("signInPro.do");

			hs.signIn(req, res);

			viewPage = "/jsp/process/signInPro.jsp";	
		
		//�α׾ƿ�
		} else if(url.equals("/signOut.do")) {
			System.out.println("signOut.do");
			
			req.getSession().invalidate();
			viewPage = "/index.do";
			
		//ȸ�� ���� ���
		} else if(url.equals("/join.do")) {
			System.out.println("join");
			
			viewPage = "/jsp/join/join.jsp";
		
		//ȸ�� ���� ȭ��
		} else if(url.equals("/signUp.do")) {
			System.out.println("signUp");

			viewPage = "/jsp/join/signUp.jsp";
			
		//ȸ�� ���� ó��
		} else if(url.equals("/signUpPro.do")) {
			System.out.println("signUpPro");
			
			hs.signUp(req, res);
			
			viewPage = "/jsp/process/signUpPro.jsp";
			
		//���̵� �ߺ� Ȯ��
		} else if(url.equals("/confirmId.do")) {
			System.out.println("confirmId.do");
			
			hs.confirmId(req, res);
			
			viewPage = "/jsp/process/confirmId.jsp";

		//�̸��� �ߺ� Ȯ��
		} else if(url.equals("/confirmEmail.do")) {
			System.out.println("confirmEmail.do");
			
			hs.confirmEmail(req, res);
			
			viewPage = "/jsp/process/confirmEmail.jsp";
		//�̸��� ���� Ȯ��
		} else if(url.equals("/emailCheck.do")) {
			System.out.println("emailCheck.do");
			
			hs.confirmEmailKey(req, res);
			
			viewPage = "/jsp/join/emailCheck.jsp";
			
		//���̵�, ��й�ȣ ã�� ȭ��
		} else if(url.equals("/signFindView.do")) {
			System.out.println("signFindView.do");
			
			viewPage = "/jsp/join/signFindView.jsp";
			
		//���̵�/��й�ȣ ã��
		} else if(url.equals("/signFind.do")) {
			System.out.println("signFind.do");
			
			hs.memberFindEmailKey(req, res);
			
			viewPage = "/jsp/join/signFind.jsp";
			
		//���������� ��й�ȣ Ȯ��
		} else if(url.equals("/myPagePwd.do")){
			System.out.println("myPagePwd.do");
			
			viewPage = "/jsp/process/myPagePwd.jsp";
			
		//ȸ�� ���� ���
		} else if(url.equals("/myPageView.do")) {
			System.out.println("myPageView.do");
			
			hs.memberQuery(req, res);
			
			viewPage = "/jsp/guest/myPageView.jsp";
			
		//ȸ�� ���� ����
		} else if(url.equals("/myPagePro.do")) {
			System.out.println("myPagePro.do");
			
			hs.memberUpdate(req, res);
			
			viewPage = "/jsp/process/myPagePro.jsp";
			
		//ȸ�� Ż��
		} else if(url.equals("/memOutPro.do")) {
			System.out.println("memOutPro.do");
			
			hs.memberDelete(req, res);
			
			viewPage = "/jsp/process/memOutPro.jsp";
		
		//������
		} else if(url.equals("/help.do")) {
			System.out.println("help.do");

			hs.helpView(req, res);
			
			viewPage = "/jsp/main/help.jsp";
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage); 
		dispatcher.forward(req, res);
	}
}
