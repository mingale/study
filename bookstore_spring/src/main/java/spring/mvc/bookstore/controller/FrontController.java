package spring.mvc.bookstore.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import spring.mvc.bookstore.service.BookService;
import spring.mvc.bookstore.service.GuestService;
import spring.mvc.bookstore.service.HostService;

/**
 * 
 * BMS : Bookstore Management System
 *
 */

@Controller
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	BookService bs;
	@Autowired
	HostService hs;
	@Autowired
	GuestService gs;
	
	//main
	@RequestMapping("index")
	public String index(HttpServletRequest req, Model model) {
		System.out.println("index()");
		
		bs.indexView(req, model);
		
		return "jsp/main/index";
	}
	
	//���� �˻�
	@RequestMapping("bookSearch")
	public String bookSearch(HttpServletRequest req, Model model) {
		System.out.println("bookSearch()");
		
		bs.bookSearch(req, model);
		
		return "jsp/main/bookListSearch";
	}
	
	//���� �� ����
	@RequestMapping("detail")
	public String detail(HttpServletRequest req, Model model) {
		System.out.println("detail()");
		
		bs.detailView(req, model);
		
		return "jsp/main/detail";
	} 
	
	//���� ����Ʈ
	@RequestMapping("bookList")
	public String bookList(HttpServletRequest req, Model model) {
		System.out.println("bookList()");
		
		bs.bookListView(req, model);
		
		return "jsp/main/bookList";
	}
	
	//�α��� ȭ��
	@RequestMapping("signIn")
	public String signIn() {
		System.out.println("signIn()");
		return "jsp/join/signIn";
	}
	
	//�α��� ó��
	@RequestMapping("signInPro")
	public String signInPro(HttpServletRequest req, Model model) {
		System.out.println("signInPro()");
		
		hs.signIn(req, model);
		
		return "jsp/process/signInPro";	
	}
	
	//�α׾ƿ�
	@RequestMapping("signOut")
	public String signOut(HttpServletRequest req, Model model) {
		System.out.println("signOut()");
		
		req.getSession().invalidate();
		return index(req, model);
	}
	
	//ȸ�� ���� ���
	@RequestMapping("join")
	public String join(HttpServletRequest req, Model model) {
		System.out.println("join()");
		return "jsp/join/join";
	}
	
	//ȸ�� ���� ȭ��
	@RequestMapping("signUp")
	public String signUp() {
		System.out.println("signUp()");
		return "jsp/join/signUp";
	}	
	
	//ȸ�� ���� ó��
	@RequestMapping("signUpPro")
	public String signUpPro(HttpServletRequest req, Model model) {
		System.out.println("signUpPro()");
			
		hs.signUp(req, model);
			
		return "jsp/process/signUpPro";
	}
	
	//���̵� �ߺ� Ȯ��
	@RequestMapping("confirmId")
	public String confirmId(HttpServletRequest req, Model model) {
		System.out.println("confirmId()");
		
		hs.confirmId(req, model);
		
		return "jsp/process/confirmId";
	}
	
	//�̸��� �ߺ� Ȯ��
	@RequestMapping("confirmEmail")
	public String confirmEmail(HttpServletRequest req, Model model) {
		System.out.println("confirmEmail()");
		
		hs.confirmEmail(req, model);
		
		return "jsp/process/confirmEmail";
	}
	
	//�̸��� ���� Ȯ��
	@RequestMapping("emailCheck")
	public String emailCheck(HttpServletRequest req, Model model) {
		System.out.println("emailCheck()");
		
		hs.confirmEmailKey(req, model);
		
		return "jsp/join/emailCheck";
	}
	
	//���̵�, ��й�ȣ ã�� ȭ��
	@RequestMapping("signFindView")
	public String signFindView(HttpServletRequest req, Model model) {
		System.out.println("signFindView()");
		
		return "jsp/join/signFindView";
	}

	//���̵�/��й�ȣ ã��
	@RequestMapping("signFind")
	public String signFind(HttpServletRequest req, Model model) {
		System.out.println("signFind()");
		
		hs.memberFindEmailKey(req, model);
		
		return "jsp/join/signFind";
	}
	
	//���������� ��й�ȣ Ȯ��
	@RequestMapping("myPagePwd")
	public String myPagePwd(HttpServletRequest req, Model model) {
		System.out.println("myPagePwd()");
		
		return "jsp/process/myPagePwd";
	}
	
	//ȸ�� ���� ���
	@RequestMapping("myPageView")
	public String myPageView(HttpServletRequest req, Model model) {
		System.out.println("myPageView()");
		
		hs.memberQuery(req, model);
		
		return "jsp/guest/myPageView";
	}
	
	//ȸ�� ���� ����
	@RequestMapping("myPagePro")
	public String myPagePro(HttpServletRequest req, Model model) {
		System.out.println("myPagePro()");
		
		hs.memberUpdate(req, model);
		
		return "jsp/process/myPagePro";
	}
	
	//ȸ�� Ż��
	@RequestMapping("memOutPro")
	public String memOutPro(HttpServletRequest req, Model model) {
		System.out.println("memOutPro()");
		
		hs.memberDelete(req, model);
		
		return "jsp/process/memOutPro";
	}
	
	//������
	@RequestMapping("help")
	public String help(HttpServletRequest req, Model model) {
		System.out.println("help()");

		hs.helpView(req, model);
		
		return "jsp/main/help";
	}
	
	/*
	 * 
	 *	GUEST
	 * 
	 * 
	 */
	
	//��ٱ��Ͽ��� ���� ��ư
	@RequestMapping("order")
	public String order(HttpServletRequest req, Model model) {
		System.out.println("order()");
		
		gs.orderView(req, model);
		
		return "jsp/guest/order";
	} 
	
	//�ֹ� ó��
	@RequestMapping("orderPro")
	public String orderPro(HttpServletRequest req, Model model) {
		System.out.println("orderPro()");
		
		gs.addOrder(req, model);
		
		return "jsp/guest/orderPro";
	} 

	//��ٱ��� ���
	@RequestMapping("cart")
	public String cart(HttpServletRequest req, Model model) {
		System.out.println("cart()");
		
		gs.cartView(req, model);
		
		return "jsp/guest/cart";
	} 
	
	//�ٷα���,��ٱ���,���ϱ� ��ư ó��
	@RequestMapping("bookBtnPro")
	public String cartBtn(HttpServletRequest req, Model model) {
		System.out.println("bookBtnPro()");
		String id = (String) req.getSession().getAttribute("memId");
		
		if(id != null) {
			String btn = req.getParameter("btn");
			if(btn.equals("�ٷ� ����")) {
				gs.orderView(req, model);
				return "jsp/guest/order";
			} else {
				gs.bookBtnPro(req, model);
				return "jsp/guest/cartBtn";
			}
		} else {
			return "jsp/guest/cartBtn";
		}
	}
	
	//��ٱ��� ���� �Ǵ� ����
	@RequestMapping("cartPro")
	public String cartPro(HttpServletRequest req, Model model) {
		System.out.println("cartPro()");
		
		gs.cartPro(req, model);
		
		return "jsp/guest/cartPro";
	}
	
	//�ֹ� ����
	@RequestMapping("myOrder")
	public String myOrder(HttpServletRequest req, Model model) {
		System.out.println("myOrder()");
		
		gs.myOrderView(req, model);

		return "jsp/guest/myOrder";
	} 
	
	@RequestMapping("orderDetail")
	public String orderDetail(HttpServletRequest req, Model model) { 
		System.out.println("orderDetail()");
		
		gs.orderDetailView(req, model);
		
		return "jsp/guest/orderDetail";
	}
	
	//�ֹ� ���
	@RequestMapping("myOrderDelete")
	public String myOrderDelete(HttpServletRequest req, Model model) {
		System.out.println("myOrderDelete()");
		
		hs.orderDelete(req, model);
		
		return "jsp/guest/myOrderDelete";
	}
	
	//ȯ�� ��û
	@RequestMapping("myOrderRefund")		
	public String myOrderRefund(HttpServletRequest req, Model model) {
		System.out.println("myOrderRefund()");
		
		gs.orderRefund(req, model);

		return "jsp/guest/refund";
	}
	
	//ȯ�� ����
	@RequestMapping("myRefund")
	public String myRefund(HttpServletRequest req, Model model) {
		System.out.println("myRefund()");
		
		gs.getRefundView(req, model);
		
		return "jsp/guest/myRefund";
	}
	
	
	/*
	 * 
	 * HOST
	 * 
	 */
	@RequestMapping("main")
	public String main(HttpServletRequest req, Model model) {
		System.out.println("main()");
		
		hs.mainView(req, model);
		
		return "jsp/host/main";
	}
	
	//��� ���� ������
	@RequestMapping("stock")
	public String stock(HttpServletRequest req, Model model) {
		System.out.println("stock()");
		
		bs.hostStockView(req, model); //���� ������ ���� ������ �Ѱ����� �ٸ���, ������ ��Ͽ� ���̰� ���ܼ� ���� ������ �ùٸ��� ǥ�õ��� ���Ѵ�.
		
		return "jsp/host/stock";
	}
		
	//���� ���� ������
	@RequestMapping("stockUpdate")
	public String stockUpdate(HttpServletRequest req, Model model) {
		System.out.println("stockUpdate()");
		
		bs.detailView(req, model);
		
		return "jsp/host/stockUpdate";
	}
		
	//���� ���� ó��
	@RequestMapping("stockUpdatePro")
	public String stockUpdatePro(MultipartHttpServletRequest req, Model model) {
		System.out.println("stockUpdatePro()");
		
		bs.bookUpdate(req, model);
		
		return "jsp/host/stockUpdatePro";
	}
	
	//���� ����
	@RequestMapping("stockDelete")
	public String stockDelete(HttpServletRequest req, Model model) {
		System.out.println("stockDelete()");
		
		bs.bookDelete(req, model);
		
		return "jsp/host/stockDelete";
	}
	
	//���� �߰�
	@RequestMapping("stockAdd")
	public String stockAdd(HttpServletRequest req, Model model) {
		System.out.println("stockAdd()");
		
		bs.bookInsertView(req, model);
		
		return "jsp/host/stockUpdate";
	}
	
	//�ֹ� ���� ������  order.ho => hostOrder
	@RequestMapping("hostOrder")
	public String hostOrder(HttpServletRequest req, Model model) {
		System.out.println("hostOrder()");
		
		hs.hostOrderView(req, model);
		
		return "jsp/host/order";			
	}
	
	//�ֹ� ����
	@RequestMapping("orderOk")
	public String orderOk(HttpServletRequest req, Model model) {
		System.out.println("orderOk()");
		
		hs.orderStateUpdate(req, model);
		
		return "jsp/host/orderOk";
	}
	
	//�ֹ� ����
	@RequestMapping("orderDelete")
	public String orderDelete(HttpServletRequest req, Model model) {
		System.out.println("orderDelete()");
		
		hs.orderDelete(req, model);
		
		return "jsp/host/orderDelete";
	}
	
	//������ �ֹ������ ��� ���� ��ư
	@RequestMapping("orderShipping")
	public String orderShipping(HttpServletRequest req, Model model) {
		System.out.println("orderShipping()");
		
		model.addAttribute("order_num", req.getParameter("order_num"));
		
		return "jsp/host/orderShipping";
	}
	
	//��۽��� �����ȣ
	@RequestMapping("shippingPro")
	public String shippingPro(HttpServletRequest req, Model model) {
		System.out.println("shippingPro()");
		
		hs.shippingPro(req, model);
		
		return "jsp/host/shippingPro";
	}
	
	//ȯ�� ���
	@RequestMapping("refund")
	public String refund(HttpServletRequest req, Model model) {
		System.out.println("refund()");
		
		hs.getHostRefundView(req, model);
		
		return "jsp/host/refund";
	}
	
	//ȯ�� �Ϸ�
	@RequestMapping("refundOk")
	public String refundOk(HttpServletRequest req, Model model) {
		System.out.println("refundOk()");
		
		hs.refundOk(req, model);
		
		return "jsp/host/refundOk";
	}
	
	//ȯ�� �ź�
	@RequestMapping("refundNo")
	public String refundNo(HttpServletRequest req, Model model) {
		System.out.println("refundNo()");
		
		hs.refundNo(req, model);
		
		return "jsp/host/refundNo";
	}
	
	//ȸ�� ���� ������
	@RequestMapping("member")
	public String member(HttpServletRequest req, Model model) {
		System.out.println("member()");
		
		hs.getMemberView(req, model);
		
		return "jsp/host/member";
	}
	
	//ȸ�� ���� ����
	@RequestMapping("memberUpdate")
	public String memberUpdate(HttpServletRequest req, Model model) {
		System.out.println("memberUpdate()");

		hs.setHostMemberUpdate(req, model);

		return "jsp/host/memberUpdate";
	}
	
	//ȸ�� ���� Ż��
	@RequestMapping("memberDelete")
	public String memberDelete(HttpServletRequest req, Model model) {
		System.out.println("memberDelete()");
		
		hs.memberDelete(req, model);
		
		return "jsp/host/memberDelete";
	}
	
	//���
	@RequestMapping("result")
	public String result(HttpServletRequest req, Model model) {
		System.out.println("result()");
		
		hs.getResultTotal(req, model);
		
		return "jsp/host/result";
	}
	
	//��������
	@RequestMapping("notice") 
	public String notice(HttpServletRequest req, Model model){
		System.out.println("notice()");
		
		hs.getNotice(req, model);
		
		return "jsp/host/notice";
	}
	
	//�������� �۾���
	@RequestMapping("noticeWrite")
	public String noticeWrite(HttpServletRequest req, Model model) {
		System.out.println("noticeWrite()");
		return "jsp/host/noticeWrite";
	}
	
	//�������� �۾��� ó��
	@RequestMapping("noticeWritePro")
	public String noticeWritePro(HttpServletRequest req, Model model) {
		System.out.println("noticeWritePro()");
		
		hs.noticeWritePro(req, model);
		
		return "jsp/host/noticeWritePro";
	}
	
	//�������� �󼼺���
	@RequestMapping("noticeView")
	public String noticeView(HttpServletRequest req, Model model) {
		System.out.println("noticeView()");
		
		hs.noticeView(req, model);
		return "jsp/host/noticeView";
	}
}
