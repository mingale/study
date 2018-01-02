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

@WebServlet("*.ho")
public class HFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HFrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionHo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionHo(request, response);
	}

	protected void actionHo(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html, charset=UTF-8");
		
		String viewPage = null;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		
		String url = uri.substring((contextPath).length());

		if(url.equals("/main.ho")) {
			System.out.println("main.ho");
			
			HostService service = new HostServiceImpl();
			service.mainView(req, res);
			
			viewPage = "/jsp/host/main.jsp";
		
		//��� ���� ������
		} else if(url.equals("/stock.ho")) {
			System.out.println("stock.ho");
			
			BookService service = new BookServiceImpl();
			service.hostStockView(req, res); //���� ������ ���� ������ �Ѱ����� �ٸ���, ������ ��Ͽ� ���̰� ���ܼ� ���� ������ �ùٸ��� ǥ�õ��� ���Ѵ�.
			
			viewPage = "/jsp/host/stock.jsp";
			
		//���� ���� ������
		} else if(url.equals("/stockUpdate.ho")) {
			System.out.println("stockUpdate.ho");
			
			BookService service = new BookServiceImpl();
			service.detailView(req, res);
			
			viewPage = "/jsp/host/stockUpdate.jsp";
			
		//���� ���� ó��
		} else if(url.equals("/stockUpdatePro.ho")) {
			System.out.println("stockUpdatePro.ho");
			
			BookService service = new BookServiceImpl();
			service.bookUpdate(req, res);
			
			viewPage = "/jsp/host/stockUpdatePro.jsp";
			
		//���� ����
		} else if(url.equals("/stockDelete.ho")) {
			System.out.println("stockDelete.ho");
			
			BookService service = new BookServiceImpl();
			service.bookDelete(req, res);
			
			viewPage = "jsp/host/stockDelete.jsp";
			
		//���� �߰�
		} else if(url.equals("/stockAdd.ho")) {
			System.out.println("stockAdd.ho");
			
			BookService service = new BookServiceImpl();
			service.bookInsertView(req, res);
			
			viewPage = "/jsp/host/stockUpdate.jsp";
			
		//�ֹ� ���� ������
		} else if(url.equals("/order.ho")) {
			System.out.println("order.ho");
			
			HostService service = new HostServiceImpl();
			service.hostOrderView(req, res);
			
			viewPage = "/jsp/host/order.jsp";			

		//�ֹ� ����
		} else if(url.equals("/orderOk.ho")) {
			System.out.println("orderOk.ho");
			
			HostService service = new HostServiceImpl();
			service.orderStateUpdate(req, res);
			
			viewPage = "/jsp/host/orderOk.jsp";
			
		//�ֹ� ����
		} else if(url.equals("/orderDelete.ho")) {
			System.out.println("orderDelete.ho");
			
			HostService service = new HostServiceImpl();
			service.orderDelete(req, res);
			
			viewPage = "/jsp/host/orderDelete.jsp";

		//������ �ֹ������ ��� ���� ��ư
		} else if(url.equals("/orderShipping.ho")) {
			System.out.println("orderShipping.ho");
			
			req.setAttribute("order_num", req.getParameter("order_num"));
			
			viewPage = "/jsp/host/orderShipping.jsp";
			
		//��۽��� �����ȣ
		} else if(url.equals("/shippingPro.ho")) {
			System.out.println("shippingPro.ho");
			
			HostService service = new HostServiceImpl();
			service.shippingPro(req, res);
			
			viewPage = "/jsp/host/shippingPro.jsp";
			
		//ȯ�� ���
		} else if(url.equals("/refund.ho")) {
			System.out.println("refund.ho");
			
			HostService service = new HostServiceImpl();
			service.getHostRefundView(req, res);
			
			viewPage = "/jsp/host/refund.jsp";
			
		//ȯ�� �Ϸ�
		} else if(url.equals("/refundOk.ho")) {
			System.out.println("refundOk.ho");
			
			HostService service = new HostServiceImpl();
			service.refundOk(req, res);
			
			viewPage = "/jsp/host/refundOk.jsp";
			
		//ȯ�� �ź�
		} else if(url.equals("/refundNo.ho")) {
			System.out.println("refundNo.ho");
			
			HostService service = new HostServiceImpl();
			service.refundNo(req, res);
			
			viewPage = "/jsp/host/refundNo.jsp";
			
		//ȸ�� ���� ������
		} else if(url.equals("/member.ho")) {
			System.out.println("member.ho");
			
			HostService service = new HostServiceImpl();
			service.getMemberView(req, res);
			
			viewPage = "/jsp/host/member.jsp";
			
		//ȸ�� ���� ����
		} else if(url.equals("/memberUpdate.ho")) {
			System.out.println("memberUpdate.ho");

			HostService service = new HostServiceImpl();
			service.setHostMemberUpdate(req, res);

			viewPage = "/jsp/host/memberUpdate.jsp";

		//ȸ�� ���� Ż��
		} else if(url.equals("/memberDelete.ho")) {
			System.out.println("memberDelete.ho");
			
			HostService service = new HostServiceImpl();
			service.memberDelete(req, res);
			
			viewPage = "/jsp/host/memberDelete.jsp";
			
		// �α׾ƿ�
		} else if(url.equals("/signOut.ho")) {
			System.out.println("signOut.ho");
			
			req.getSession().invalidate();
			viewPage = "index.do";
		
		//���
		} else if(url.equals("/result.ho")) {
			System.out.println("result.ho");
			
			HostService service = new HostServiceImpl();
			service.getResultTotal(req, res);
			
			viewPage = "/jsp/host/result.jsp";
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
	}
}
