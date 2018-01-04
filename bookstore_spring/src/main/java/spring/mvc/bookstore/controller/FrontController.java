package spring.mvc.bookstore.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
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
	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	BookService bs;
	@Autowired
	HostService hs;
	@Autowired
	GuestService gs;
	
	//main
	@RequestMapping("index")
	public String index(HttpServletRequest req, Model model) {
		log.debug("index()");
		
		bs.indexView(req, model);
		
		return "jsp/main/index";
	}
	
	//�˻��� ����
	@RequestMapping("suggest")
	public String suggest(HttpServletRequest req, Model model) {
		log.debug("suggest()");
		
		bs.keywordSuggest(req, model);
		
		return "jsp/main/suggest";
	}
	
	//���� �˻�
	@RequestMapping("bookSearch")
	public String bookSearch(HttpServletRequest req, Model model) {
		log.debug("bookSearch()");
		
		bs.bookSearch(req, model);
		
		return "jsp/main/bookListSearch";
	}
	
	//���� �� ����
	@RequestMapping("detail")
	public String detail(HttpServletRequest req, Model model) {
		log.debug("detail()");
		
		bs.detailView(req, model);
		
		return "jsp/main/detail";
	} 
	
	//���� ����Ʈ
	@RequestMapping("bookList")
	public String bookList(HttpServletRequest req, Model model) {
		log.debug("bookList()");
		
		bs.bookListView(req, model);
		
		return "jsp/main/bookList";
	}
	
	//�α��� ȭ��
	@RequestMapping("signIn")
	public String signIn() {
		log.debug("signIn()");
		return "jsp/join/signIn";
	}
	
	//�α��� ó��
	@RequestMapping("signInPro")
	public String signInPro(HttpServletRequest req, Model model) {
		log.debug("signInPro()");
		
		hs.signIn(req, model);
		
		return "jsp/process/signInPro";	
	}
	
	//�α׾ƿ�
	@RequestMapping("signOut")
	public String signOut(HttpServletRequest req, Model model) {
		log.debug("signOut()");
		
		req.getSession().invalidate();
		return index(req, model);
	}
	
	//ȸ�� ���� ���
	@RequestMapping("join")
	public String join(HttpServletRequest req, Model model) {
		log.debug("join()");
		return "jsp/join/join";
	}
	
	//ȸ�� ���� ȭ��
	@RequestMapping("signUp")
	public String signUp() {
		log.debug("signUp()");
		return "jsp/join/signUp";
	}	
	
	//ȸ�� ���� ó��
	@RequestMapping("signUpPro")
	public String signUpPro(HttpServletRequest req, Model model) {
		log.debug("signUpPro()");
			
		hs.signUp(req, model);
			
		return "jsp/process/signUpPro";
	}
	
	//���̵� �ߺ� Ȯ��
	@RequestMapping("confirmId")
	public String confirmId(HttpServletRequest req, Model model) {
		log.debug("confirmId()");
		
		hs.confirmId(req, model);
		
		return "jsp/process/confirmId";
	}
	
	//�̸��� �ߺ� Ȯ��
	@RequestMapping("confirmEmail")
	public String confirmEmail(HttpServletRequest req, Model model) {
		log.debug("confirmEmail()");
		
		hs.confirmEmail(req, model);
		
		return "jsp/process/confirmEmail";
	}
	
	//�̸��� ���� Ȯ��
	@RequestMapping("emailCheck")
	public String emailCheck(HttpServletRequest req, Model model) {
		log.debug("emailCheck()");
		
		hs.confirmEmailKey(req, model);
		
		return "jsp/join/emailCheck";
	}
	
	//���̵�, ��й�ȣ ã�� ȭ��
	@RequestMapping("signFindView")
	public String signFindView(HttpServletRequest req, Model model) {
		log.debug("signFindView()");
		
		return "jsp/join/signFindView";
	}

	//���̵�/��й�ȣ ã��
	@RequestMapping("signFind")
	public String signFind(HttpServletRequest req, Model model) {
		log.debug("signFind()");
		
		hs.memberFindEmailKey(req, model);
		
		return "jsp/join/signFind";
	}
	
	//���������� ��й�ȣ Ȯ��
	@RequestMapping("myPagePwd")
	public String myPagePwd(HttpServletRequest req, Model model) {
		log.debug("myPagePwd()");
		
		return "jsp/process/myPagePwd";
	}
	
	//ȸ�� ���� ���
	@RequestMapping("myPageView")
	public String myPageView(HttpServletRequest req, Model model) {
		log.debug("myPageView()");
		
		hs.memberQuery(req, model);
		
		return "jsp/guest/myPageView";
	}
	
	//ȸ�� ���� ����
	@RequestMapping("myPagePro")
	public String myPagePro(HttpServletRequest req, Model model) {
		log.debug("myPagePro()");
		
		hs.memberUpdate(req, model);
		
		return "jsp/process/myPagePro";
	}
	
	//ȸ�� Ż��
	@RequestMapping("memOutPro")
	public String memOutPro(HttpServletRequest req, Model model) {
		log.debug("memOutPro()");
		
		hs.memberDelete(req, model);
		
		return "jsp/process/memOutPro";
	}
	
	//������
	@RequestMapping("help")
	public String help(HttpServletRequest req, Model model) {
		log.debug("help()");

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
		log.debug("order()");
		
		gs.orderView(req, model);
		
		return "jsp/guest/order";
	} 
	
	//�ֹ� ó��
	@RequestMapping("orderPro")
	public String orderPro(HttpServletRequest req, Model model) {
		log.debug("orderPro()");
		
		gs.addOrder(req, model);
		
		return "jsp/guest/orderPro";
	} 

	//��ٱ��� ���
	@RequestMapping("cart")
	public String cart(HttpServletRequest req, Model model) {
		log.debug("cart()");
		
		gs.cartView(req, model);
		
		return "jsp/guest/cart";
	} 
	
	//�ٷα���,��ٱ���,���ϱ� ��ư ó��
	@RequestMapping("bookBtnPro")
	public String cartBtn(HttpServletRequest req, Model model) {
		log.debug("bookBtnPro()");
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
		log.debug("cartPro()");
		
		gs.cartPro(req, model);
		
		return "jsp/guest/cartPro";
	}
	
	//�ֹ� ����
	@RequestMapping("myOrder")
	public String myOrder(HttpServletRequest req, Model model) {
		log.debug("myOrder()");
		
		gs.myOrderView(req, model);

		return "jsp/guest/myOrder";
	} 
	
	@RequestMapping("orderDetail")
	public String orderDetail(HttpServletRequest req, Model model) { 
		log.debug("orderDetail()");
		
		gs.orderDetailView(req, model);
		
		return "jsp/guest/orderDetail";
	}
	
	//�ֹ� ���
	@RequestMapping("myOrderDelete")
	public String myOrderDelete(HttpServletRequest req, Model model) {
		log.debug("myOrderDelete()");
		
		hs.orderDelete(req, model);
		
		return "jsp/guest/myOrderDelete";
	}
	
	//ȯ�� ��û
	@RequestMapping("myOrderRefund")		
	public String myOrderRefund(HttpServletRequest req, Model model) {
		log.debug("myOrderRefund()");
		
		gs.orderRefund(req, model);

		return "jsp/guest/refund";
	}
	
	//ȯ�� ����
	@RequestMapping("myRefund")
	public String myRefund(HttpServletRequest req, Model model) {
		log.debug("myRefund()");
		
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
		log.debug("main()");
		
		hs.mainView(req, model);
		
		return "jsp/host/main";
	}
	
	//��� ���� ������
	@RequestMapping("stock")
	public String stock(HttpServletRequest req, Model model) {
		log.debug("stock()");
		
		bs.hostStockView(req, model); //���� ������ ���� ������ �Ѱ����� �ٸ���, ������ ��Ͽ� ���̰� ���ܼ� ���� ������ �ùٸ��� ǥ�õ��� ���Ѵ�.
		
		return "jsp/host/stock";
	}
		
	//���� ���� ������
	@RequestMapping("stockUpdate")
	public String stockUpdate(HttpServletRequest req, Model model) {
		log.debug("stockUpdate()");
		
		bs.detailView(req, model);
		
		return "jsp/host/stockUpdate";
	}
		
	//���� ���� ó��
	@RequestMapping("stockUpdatePro")
	public String stockUpdatePro(MultipartHttpServletRequest req, Model model) {
		log.debug("stockUpdatePro()");
		
		bs.bookUpdate(req, model);
		
		return "jsp/host/stockUpdatePro";
	}
	
	//���� ����
	@RequestMapping("stockDelete")
	public String stockDelete(HttpServletRequest req, Model model) {
		log.debug("stockDelete()");
		
		bs.bookDelete(req, model);
		
		return "jsp/host/stockDelete";
	}
	
	//���� �߰�
	@RequestMapping("stockAdd")
	public String stockAdd(HttpServletRequest req, Model model) {
		log.debug("stockAdd()");
		
		bs.bookInsertView(req, model);
		
		return "jsp/host/stockUpdate";
	}
	
	//�ֹ� ���� ������  order.ho => hostOrder
	@RequestMapping("hostOrder")
	public String hostOrder(HttpServletRequest req, Model model) {
		log.debug("hostOrder()");
		
		hs.hostOrderView(req, model);
		
		return "jsp/host/order";			
	}
	
	//�ֹ� ����
	@RequestMapping("orderOk")
	public String orderOk(HttpServletRequest req, Model model) {
		log.debug("orderOk()");
		
		hs.orderStateUpdate(req, model);
		
		return "jsp/host/orderOk";
	}
	
	//�ֹ� ����
	@RequestMapping("orderDelete")
	public String orderDelete(HttpServletRequest req, Model model) {
		log.debug("orderDelete()");
		
		hs.orderDelete(req, model);
		
		return "jsp/host/orderDelete";
	}
	
	//������ �ֹ������ ��� ���� ��ư
	@RequestMapping("orderShipping")
	public String orderShipping(HttpServletRequest req, Model model) {
		log.debug("orderShipping()");
		
		model.addAttribute("order_num", req.getParameter("order_num"));
		
		return "jsp/host/orderShipping";
	}
	
	//��۽��� �����ȣ
	@RequestMapping("shippingPro")
	public String shippingPro(HttpServletRequest req, Model model) {
		log.debug("shippingPro()");
		
		hs.shippingPro(req, model);
		
		return "jsp/host/shippingPro";
	}
	
	//ȯ�� ���
	@RequestMapping("refund")
	public String refund(HttpServletRequest req, Model model) {
		log.debug("refund()");
		
		hs.getHostRefundView(req, model);
		
		return "jsp/host/refund";
	}
	
	//ȯ�� �Ϸ�
	@RequestMapping("refundOk")
	public String refundOk(HttpServletRequest req, Model model) {
		log.debug("refundOk()");
		
		hs.refundOk(req, model);
		
		return "jsp/host/refundOk";
	}
	
	//ȯ�� �ź�
	@RequestMapping("refundNo")
	public String refundNo(HttpServletRequest req, Model model) {
		log.debug("refundNo()");
		
		hs.refundNo(req, model);
		
		return "jsp/host/refundNo";
	}
	
	//ȸ�� ���� ������
	@RequestMapping("member")
	public String member(HttpServletRequest req, Model model) {
		log.debug("member()");
		
		hs.getMemberView(req, model);
		
		return "jsp/host/member";
	}
	
	//ȸ�� ���� ����
	@RequestMapping("memberUpdate")
	public String memberUpdate(HttpServletRequest req, Model model) {
		log.debug("memberUpdate()");

		hs.setHostMemberUpdate(req, model);

		return "jsp/host/memberUpdate";
	}
	
	//ȸ�� ���� Ż��
	@RequestMapping("memberDelete")
	public String memberDelete(HttpServletRequest req, Model model) {
		log.debug("memberDelete()");
		
		hs.memberDelete(req, model);
		
		return "jsp/host/memberDelete";
	}
	
	//���
	@RequestMapping("result")
	public String result(HttpServletRequest req, Model model) {
		log.debug("result()");
		
		hs.getResultTotal(req, model);
		
		return "jsp/host/result";
	}
	
	//��������
	@RequestMapping("notice") 
	public String notice(HttpServletRequest req, Model model){
		log.debug("notice()");
		
		hs.getNotice(req, model);
		
		return "jsp/host/notice";
	}
	
	//�������� �۾���
	@RequestMapping("noticeWrite")
	public String noticeWrite(HttpServletRequest req, Model model) {
		log.debug("noticeWrite()");
		return "jsp/host/noticeWrite";
	}
	
	//�������� �۾��� ó��
	@RequestMapping("noticeWritePro")
	public String noticeWritePro(HttpServletRequest req, Model model) {
		log.debug("noticeWritePro()");
		
		hs.noticeWritePro(req, model);
		
		return "jsp/host/noticeWritePro";
	}
	
	//�������� �󼼺���
	@RequestMapping("noticeView")
	public String noticeView(HttpServletRequest req, Model model) {
		log.debug("noticeView()");
		
		hs.noticeView(req, model);
		return "jsp/host/noticeView";
	}
}
