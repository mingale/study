package mvc.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.service.GuestService;
import mvc.service.GuestServiceImpl;
import mvc.service.HostService;
import mvc.service.HostServiceImpl;

@WebServlet("*.go")
public class GFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GFrontController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionGo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionGo(request, response);
	}

	protected void actionGo(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");

		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = uri.substring(contextPath.length());

		String viewPage = "";

		//장바구니에서 구매 버튼
		if (url.equals("/order.go")) {
			System.out.println("order.go");
			
			GuestService service = new GuestServiceImpl();
			service.orderView(req, res);
			
			viewPage = "/jsp/guest/order.jsp";
		
		//주문 처리
		} else if(url.equals("/orderPro.go")) {
			System.out.println("orderPro.go");
			
			GuestService service = new GuestServiceImpl();
			service.addOrder(req, res);
			
			viewPage = "/jsp/guest/orderPro.jsp";
			
		//장바구니 목록
		} else if (url.equals("/cart.go")) {
			System.out.println("cart.go");
			
			GuestService service = new GuestServiceImpl();
			service.cartView(req, res);
			
			viewPage = "/jsp/guest/cart.jsp";

		//바로구매,장바구니,찜하기 버튼 처리
		} else if(url.equals("/bookBtnPro.go")) {
			System.out.println("cartBtn.go");
			String id = (String) req.getSession().getAttribute("memId");
			
			if(id != null) {
				GuestService service = new GuestServiceImpl();
				
				String btn = req.getParameter("btn");
				if(btn.equals("바로 구매")) {
					service.orderView(req, res);
					viewPage = "/jsp/guest/order.jsp";
				} else {
					service.bookBtnPro(req, res);
					viewPage = "/jsp/guest/cartBtn.jsp";
				}
			} else {
				viewPage = "/jsp/guest/cartBtn.jsp";
			}
			
		//장바구니 삭제 또는 수정
		} else if (url.equals("/cartPro.go")) {
			System.out.println("cartPro.go");
			
			GuestService service = new GuestServiceImpl();
			service.cartPro(req, res);
			
			viewPage = "/jsp/guest/cartPro.jsp";
		
		//주문 내역
		} else if(url.equals("/myOrder.go")) {
			System.out.println("myOrder.go");
			
			GuestService service = new GuestServiceImpl();
			service.myOrderView(req, res);

			viewPage = "/jsp/guest/myOrder.jsp";
			
		} else if(url.equals("/orderDetail.go")) {
			System.out.println("orderDetail.go");
			
			GuestService service = new GuestServiceImpl();
			service.orderDetailView(req, res);
			
			viewPage= "/jsp/guest/orderDetail.jsp";
			
		//주문 취소
		} else if(url.equals("/myOrderDelete.go")) {
			System.out.println("myOrderDelete.go");
			
			HostService service = new HostServiceImpl();
			service.orderDelete(req, res);
			
			viewPage = "/jsp/guest/myOrderDelete.jsp";
			
		//환불 신청
		} else if(url.equals("/myOrderRefund.go")) {
			System.out.println("myOrderRefund.go");

			GuestService service = new GuestServiceImpl();
			service.orderRefund(req, res);
			
			viewPage = "/jsp/guest/refund.jsp";

		//환불 내역
		} else if(url.equals("/myRefund.go")) {
			System.out.println("myRefund.go");
			
			GuestService service = new GuestServiceImpl();
			service.getRefundView(req, res);
			
			viewPage = "/jsp/guest/myRefund.jsp";
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);

	}
}
