package spring.mvc.bookstore.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import spring.mvc.bookstore.persistence.BookPers;
import spring.mvc.bookstore.persistence.HostPers;
import spring.mvc.bookstore.persistence.MemberPers;
import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Book;
import spring.mvc.bookstore.vo.Cart;
import spring.mvc.bookstore.vo.Member;

@Service
public class GuestServiceImpl implements GuestService {

	@Autowired
	MemberPers mDao;
	@Autowired
	BookPers bDao;
	@Autowired
	HostPers hDao;
	
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public void orderView(HttpServletRequest req, Model model) {
		String id = (String) req.getSession().getAttribute("memId");
		String carts = req.getParameter("carts"); // ��ٱ��Ͽ��� ������ ������, �ٷα��� ����

		// �ٷ� ���ſ��� �Ѿ�� ����
		String btn = req.getParameter("btn");
		if (btn != null) {
			int nowCount = Integer.parseInt(req.getParameter("count"));
			carts = req.getParameter("no");

			model.addAttribute("btn", btn);
			model.addAttribute("nowCount", nowCount);
		} // else : ��ٱ��Ͽ��� �� ������

		// ȸ�� ����
		Member mem = mDao.getMemberInfo(id);

		model.addAttribute("carts", carts);
		model.addAttribute("mem", mem);
	}

	// ��ٱ��� ���
	@Override
	public void cartView(HttpServletRequest req, Model model) {
		String id = (String) req.getSession().getAttribute("memId");

		ArrayList<Cart> carts = mDao.getCartList(id);

		model.addAttribute("carts", carts);
	}

	// ��ٱ��� ó��
	@Override
	public void cartPro(HttpServletRequest req, Model model) {
		String cmd = req.getParameter("cmd");

		String id = (String) req.getSession().getAttribute("memId");
		String no = req.getParameter("no");
		String count = req.getParameter("count");

		int cnt = 0;
		Map<String, Object> map = null;
		// ��ٱ��� ����
		if (cmd.equals("de")) {
			// ���� ��ǰ�� ����
			if (no == null) {
				no = req.getParameter("nos");
				String[] nos = no.split(",");

				for (String num : nos) {
					map = new HashMap<>();
					map.put("id", id);
					map.put("no", num);
					cnt = mDao.cartDelete(map);
				}
				// ���� ����
			} else {
				map = new HashMap<>();
				map.put("id", id);
				map.put("no", no);
				cnt = mDao.cartDelete(map);
			}
			// ��ٱ��� ���� ����
		} else if (cmd.equals("up")) {
			String[] nos = req.getParameterValues("no");
			
			Cart cart = new Cart();
			cart.setId(id);
			cart.setNo(no);
			cart.setCart_count(Integer.parseInt(count));
			cnt = mDao.cartCountUpdate(cart);
			
			log.debug(id + "/" + no + "/" + count);
			log.debug(nos.length);
		}

		model.addAttribute("cnt", cnt);
	}

	// �ֹ� ó��
	@Override
	public void addOrder(HttpServletRequest req, Model model) {
		String btn = req.getParameter("btn");// �ٷ� ���ſ��� �Ѿ�� ������

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

		int cnt = 0;
		Map<String, Object> map = null;
		// ��ٱ��Ͽ��� ���ŷ� �Ѿ���� ���
		if (!btn.equals("�ٷ� ����")) {
			//�ֹ� �߰�-1) �ֹ���ȣ
			mDao.orderNumberCreate();
			for(String no : nos) {
				//�ֹ� �߰�-2) ��ٱ��� ��� �� �ش� ǰ��
				map = new HashMap<>();
				map.put("id", order.getId());
				map.put("no", no);
				Map<String, Object> tmp = mDao.cartListBook(map);
				
				//�ֹ� �߰�-3) Order�� �����ϱ�
				order.setNo(no);
				//java.lang.ClassCastException: java.math.BigDecimal cannot be cast to java.lang.Integer �Ǵ� String
				// = INT�� �÷� �����͸� HashMap Ÿ������ �޾� java���� ����Ϸ��� �� �� ���� �߻�
				//   NUMBER(INT) Ÿ���� ��ٷ� ĳ��Ʈ ��ȯ�Ϸ��� �Ҷ� �߻��ϹǷ� ĳ��Ʈ ��ȯ�� �ƴ� �޼ҵ� ���
				//Map���� ������ Column�� �빮��. 
				order.setOrder_count(Integer.parseInt(String.valueOf(tmp.get("CART_COUNT"))));
				order.setOrder_price(Integer.parseInt(String.valueOf(tmp.get("CART_PRICE"))));
				cnt = mDao.setOrder(order);
			}

			// �ֹ� �߰��� �������̸� ��ٱ��� ����
			if (cnt != 0) {
				for (String no : nos) {
					map = new HashMap<>();
					map.put("id", id);
					map.put("no", no);
					cnt = mDao.cartDelete(map);
				}
			}

		// �ٷ� ���ſ��� �Ѿ���� ���
		} else {
			String no = carts;
			Book book = bDao.getBookInfo(no);
			
			order.setNo(no);
			order.setOrder_price(book.getPrice());
			order.setOrder_count(Integer.parseInt(req.getParameter("nowCount")));
			cnt = mDao.setNowOrder(order);
		}

		model.addAttribute("cnt", cnt);
	}

	@Override
	public void myOrderView(HttpServletRequest req, Model model) {
		String id = (String) req.getSession().getAttribute("memId");

		ArrayList<Bespeak> orders = mDao.getMemberOrderList(id);

		//Map<String, String> books = bDao.getNoTitle();

		//model.addAttribute("books", books);
		model.addAttribute("orders", orders);
	}

	// �ֹ� �� ������
	public void orderDetailView(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("order_num");

		// �ֹ� ����
		ArrayList<Bespeak> orders = mDao.getOrderDetail(order_num);

		// �����ȣ
		String shipping = hDao.getShipping(order_num);
		if (shipping == null)
			shipping = " - ";

		model.addAttribute("orders", orders);
		model.addAttribute("shipping", shipping);
	}

	// ���� ��ư ó��
	@Override
	public void bookBtnPro(HttpServletRequest req, Model model) {
		String btn = req.getParameter("btn");
		String id = (String) req.getSession().getAttribute("memId");
		String no = req.getParameter("no");
		
		Map<String, Object> map = null;

		// �ٷα��� ����
		int count = 1;
		String tmpCount = req.getParameter("count");
		if (tmpCount != null) {
			count = Integer.parseInt(tmpCount);
		} else {
			count = Integer.parseInt(req.getParameter("count"));
		} /////////////////// ���� �󼼿��� ��ٱ��� ���
		log.debug("bookBtnPro : " + count);

		int cnt = 0;
		if (btn.equals("��ٱ��� ���")) {
			Book b = bDao.getBookInfo(no);

			// ��ٱ��� �ߺ� Ȯ���ϰ� ���� ��������
			map =  new HashMap<>();
			map.put("id", id);
			map.put("no", no);
			String countChk = mDao.cartCheck(map);

			// ��ٱ��Ͽ� �߰�
			if (countChk == null) {
				Cart cart = new Cart();
				cart.setId(id);
				cart.setNo(b.getNo());
				cart.setTitle(b.getTitle());
				cart.setCart_price(b.getPrice());
				cart.setCart_count(count);
				cart.setImage(b.getImage());
				cnt = mDao.setCart(cart);
				
			// �ߺ��̸� ���� �߰�
			} else {
				map.put("id", id); 
				map.put("no", no);
				map.put("cart_count", Integer.parseInt(countChk) + count);
				cnt = mDao.setCartCount(map);
			}
		// ���ϱ�
		} else {
			map =  new HashMap<>();
			map.put("id", id); 
			map.put("no", no);
			cnt = mDao.wishlistCheck(map);
			if (cnt != 9) {
				cnt = mDao.setWishlist(map);
			}
		}

		model.addAttribute("btn", btn);
		model.addAttribute("cnt", cnt);
	}

	// ȸ�� ȯ�� ��û
	@Override
	public void orderRefund(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("order_num");

		Map<String, Object> map = new HashMap<>();
		map.put("state", 8);
		map.put("num", order_num);
		int cnt = hDao.orderStateUpdate(map);

		model.addAttribute("cnt", cnt);
	}

	// ȸ�� ȯ�� ����
	@Override
	public void getRefundView(HttpServletRequest req, Model model) {
		String id = (String) req.getSession().getAttribute("memId");

		ArrayList<Bespeak> refunds = mDao.getRefundList(id);

		model.addAttribute("refunds", refunds);
	}

}