package mvc.service;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.EmailCheckHandler;
import mvc.persistence.BookPers;
import mvc.persistence.BookPersImpl;
import mvc.persistence.MemberPers;
import mvc.persistence.MemberPersImpl;
import mvc.vo.Book;
import mvc.vo.Cart;
import mvc.vo.Member;
import mvc.vo.RecentBook;
import mvc.vo.Shipping;
import mvc.vo.Bespeak;

public class GuestServiceImpl implements GuestService {

	MemberPers mDao = MemberPersImpl.getInstance();
	
	@Override
	public void orderView(HttpServletRequest req, HttpServletResponse res) {
		String id = (String) req.getSession().getAttribute("memId");
		String carts = req.getParameter("carts"); //��ٱ��Ͽ��� ������ ������, �ٷα��� ����

		//�ٷ� ���ſ��� �Ѿ�� ����
		String btn = req.getParameter("btn");
		if(btn != null) { 
			int nowCount = Integer.parseInt(req.getParameter("count"));
			carts = req.getParameter("no");
			
			req.setAttribute("btn", btn);
			req.setAttribute("nowCount", nowCount);
		}//else : ��ٱ��Ͽ��� �� ������
		
		//ȸ�� ����
		MemberPers dao = MemberPersImpl.getInstance();
		Member mem = dao.getMemberInfo(id);
		
		req.setAttribute("carts", carts);
		req.setAttribute("mem", mem);
	}

	//��ٱ��� ���
	@Override
	public void cartView(HttpServletRequest req, HttpServletResponse res) {
		String id = (String) req.getSession().getAttribute("memId");
		
		MemberPers dao = MemberPersImpl.getInstance();
		ArrayList<Cart> carts = dao.getCartList(id);
		
		req.setAttribute("carts", carts);
	}

	//��ٱ��� ó��
	@Override
	public void cartPro(HttpServletRequest req, HttpServletResponse res) {
		String cmd = req.getParameter("cmd");
		
		String id = (String) req.getSession().getAttribute("memId");
		String no = req.getParameter("no");
		String count = req.getParameter("count");
		
		int cnt = 0;
		MemberPers dao = MemberPersImpl.getInstance();
		//��ٱ��� ����
		if(cmd.equals("de")) {
			//���� ��ǰ�� ����
			if(no == null) {
				no = req.getParameter("nos");
				String[] nos = no.split(",");
				
				for(String num : nos) {
					cnt = dao.cartDelete(id, num);
				}
			//���� ����
			} else {
				cnt = dao.cartDelete(id, no);
			}
		//��ٱ��� ���� ����
		} else if(cmd.equals("up")) {
			String[] nos = req.getParameterValues("no");
			cnt = dao.cartCountUpdate(id, no, Integer.parseInt(count));
			System.out.println(id+"/"+no+"/"+count);
			System.out.println(nos.length);
		}
		
		req.setAttribute("cnt", cnt);
	}

	//�ֹ� ó��
	@Override
	public void addOrder(HttpServletRequest req, HttpServletResponse res) {
		String btn = req.getParameter("btn");//�ٷ� ���ſ��� �Ѿ�� ������
		
		String carts = req.getParameter("carts");
		String[] nos = carts.split(",");

		String id = (String) req.getSession().getAttribute("memId");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		String etc = req.getParameter("etc");
		String bank = req.getParameter("bank");
		int account = Integer.parseInt(req.getParameter("account"));

		Bespeak order = new Bespeak();
		order.setId(id);
		order.setName(name);
		order.setPhone(phone);
		order.setAddress(address);
		order.setEtc(etc);
		order.setBank(bank);
		order.setAccount(account);
		
		MemberPers dao = MemberPersImpl.getInstance();
		//BookPers bDao = BookPersImpl.getInstance();
		
		int cnt = 0;
		//��ٱ��Ͽ��� ���ŷ� �Ѿ���� ���
		if(!btn.equals("�ٷ� ����")) {
			//�ֹ� �߰�
			cnt = dao.setOrder(nos, order);
			
			//�ֹ� �߰��� �������̸� ��ٱ��� ����
			if(cnt != 0) {
				for(String no : nos) {
					cnt = dao.cartDelete(id, no);
				}
			}
			
		//�ٷ� ���ſ��� �Ѿ���� ���
		} else {
			int nowCount = Integer.parseInt(req.getParameter("nowCount"));
			cnt = dao.setNowOrder(carts, order, nowCount);
		}

		req.setAttribute("cnt", cnt);
	}

	@Override
	public void myOrderView(HttpServletRequest req, HttpServletResponse res) {
		String id = (String) req.getSession().getAttribute("memId");
		
		MemberPers dao = MemberPersImpl.getInstance();
		ArrayList<Bespeak> orders = dao.getMemberOrderList(id);
		
		BookPers bDao = BookPersImpl.getInstance();
		Map<String, String> books = bDao.getNoTitle();
		
		req.setAttribute("books", books);
		req.setAttribute("orders", orders);
	}

	//�ֹ� �� ������
	public void orderDetailView(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("order_num");
		
		//�ֹ� ����
		MemberPers dao = MemberPersImpl.getInstance();
		ArrayList<Bespeak> orders = dao.getOrderDetail(order_num);
		
		//�����ȣ
		String shipping = dao.getShipping(order_num);
		if(shipping == null) shipping = " - ";
		
		req.setAttribute("orders", orders);
		req.setAttribute("shipping", shipping);
	}
		
	//���� ��ư ó��
	@Override
	public void bookBtnPro(HttpServletRequest req, HttpServletResponse res) {
		String btn = req.getParameter("btn");
		String id = (String) req.getSession().getAttribute("memId");
		String no = req.getParameter("no");
		
		BookPers bDao = BookPersImpl.getInstance();
		MemberPers dao = MemberPersImpl.getInstance();

		//�ٷα��� ����
		int count = 1;
		String tmpCount = req.getParameter("count");
		if(tmpCount != null) { count = Integer.parseInt(tmpCount);}
		else { count = Integer.parseInt(req.getParameter("count")); } ///////////////////���� �󼼿��� ��ٱ��� ���
		System.out.println("bookBtnPro : " + count);
		
		int cnt = 0;
		if(btn.equals("��ٱ��� ���")) {
			Book b = bDao.getBookInfo(no);
			
			//��ٱ��� �ߺ� Ȯ���ϰ� ���� ��������
			int countChk = dao.cartCheck(id, no);
			
			//��ٱ��Ͽ� �߰�
			if(countChk == 0) cnt = dao.setCart(id, b, count);
			//�ߺ��̸� ���� �߰�
			else cnt = dao.setCartCount(id, no, countChk+count);
			
		//���ϱ�
		} else {
			cnt = dao.wishlistCheck(id, no);
			if(cnt != 9) cnt = dao.setWishlist(id, no);
		}
		
		req.setAttribute("btn", btn);
		req.setAttribute("cnt", cnt);
	}

	//ȸ�� ȯ�� ��û
	@Override
	public void orderRefund(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("order_num");
		
		MemberPers dao = MemberPersImpl.getInstance();
		int cnt = dao.orderStateUpdate(order_num, 8);
		
		req.setAttribute("cnt", cnt);
	}

	//ȸ�� ȯ�� ����
	@Override
	public void getRefundView(HttpServletRequest req, HttpServletResponse res) {
		String id = (String) req.getSession().getAttribute("memId");
		
		MemberPers dao = MemberPersImpl.getInstance();
		ArrayList<Bespeak> refunds = dao.getRefundList(id);
		
		req.setAttribute("refunds", refunds);
	}


}