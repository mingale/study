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
		String carts = req.getParameter("carts"); // 장바구니에서 선택한 도서들, 바로구매 도서

		// 바로 구매에서 넘어온 도서
		String btn = req.getParameter("btn");
		if (btn != null) {
			int nowCount = Integer.parseInt(req.getParameter("count"));
			carts = req.getParameter("no");

			model.addAttribute("btn", btn);
			model.addAttribute("nowCount", nowCount);
		} // else : 장바구니에서 온 데이터

		// 회원 정보
		Member mem = mDao.getMemberInfo(id);

		model.addAttribute("carts", carts);
		model.addAttribute("mem", mem);
	}

	// 장바구니 목록
	@Override
	public void cartView(HttpServletRequest req, Model model) {
		String id = (String) req.getSession().getAttribute("memId");

		ArrayList<Cart> carts = mDao.getCartList(id);

		model.addAttribute("carts", carts);
	}

	// 장바구니 처리
	@Override
	public void cartPro(HttpServletRequest req, Model model) {
		String cmd = req.getParameter("cmd");

		String id = (String) req.getSession().getAttribute("memId");
		String no = req.getParameter("no");
		String count = req.getParameter("count");

		int cnt = 0;
		Map<String, Object> map = null;
		// 장바구니 삭제
		if (cmd.equals("de")) {
			// 선택 상품들 삭제
			if (no == null) {
				no = req.getParameter("nos");
				String[] nos = no.split(",");

				for (String num : nos) {
					map = new HashMap<>();
					map.put("id", id);
					map.put("no", num);
					cnt = mDao.cartDelete(map);
				}
				// 개별 삭제
			} else {
				map = new HashMap<>();
				map.put("id", id);
				map.put("no", no);
				cnt = mDao.cartDelete(map);
			}
			// 장바구니 개별 수정
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

	// 주문 처리
	@Override
	public void addOrder(HttpServletRequest req, Model model) {
		String btn = req.getParameter("btn");// 바로 구매에서 넘어온 데이터

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
		// 장바구니에서 구매로 넘어왔을 경우
		if (!btn.equals("바로 구매")) {
			//주문 추가-1) 주문번호
			mDao.orderNumberCreate();
			for(String no : nos) {
				//주문 추가-2) 장바구니 목록 중 해당 품목
				map = new HashMap<>();
				map.put("id", order.getId());
				map.put("no", no);
				Map<String, Object> tmp = mDao.cartListBook(map);
				
				//주문 추가-3) Order에 저장하기
				order.setNo(no);
				//java.lang.ClassCastException: java.math.BigDecimal cannot be cast to java.lang.Integer 또는 String
				// = INT형 컬럼 데이터를 HashMap 타입으로 받아 java에서 사용하려고 할 때 오류 발생
				//   NUMBER(INT) 타입을 곧바로 캐스트 변환하려고 할때 발생하므로 캐스트 변환이 아닌 메소드 사용
				//Map으로 가져온 Column은 대문자. 
				order.setOrder_count(Integer.parseInt(String.valueOf(tmp.get("CART_COUNT"))));
				order.setOrder_price(Integer.parseInt(String.valueOf(tmp.get("CART_PRICE"))));
				cnt = mDao.setOrder(order);
			}

			// 주문 추가가 성공적이면 장바구니 삭제
			if (cnt != 0) {
				for (String no : nos) {
					map = new HashMap<>();
					map.put("id", id);
					map.put("no", no);
					cnt = mDao.cartDelete(map);
				}
			}

		// 바로 구매에서 넘어왔을 경우
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

	// 주문 상세 페이지
	public void orderDetailView(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("order_num");

		// 주문 정보
		ArrayList<Bespeak> orders = mDao.getOrderDetail(order_num);

		// 송장번호
		String shipping = hDao.getShipping(order_num);
		if (shipping == null)
			shipping = " - ";

		model.addAttribute("orders", orders);
		model.addAttribute("shipping", shipping);
	}

	// 도서 버튼 처리
	@Override
	public void bookBtnPro(HttpServletRequest req, Model model) {
		String btn = req.getParameter("btn");
		String id = (String) req.getSession().getAttribute("memId");
		String no = req.getParameter("no");
		
		Map<String, Object> map = null;

		// 바로구매 수량
		int count = 1;
		String tmpCount = req.getParameter("count");
		if (tmpCount != null) {
			count = Integer.parseInt(tmpCount);
		} else {
			count = Integer.parseInt(req.getParameter("count"));
		} /////////////////// 도서 상세에서 장바구니 담기
		log.debug("bookBtnPro : " + count);

		int cnt = 0;
		if (btn.equals("장바구니 담기")) {
			Book b = bDao.getBookInfo(no);

			// 장바구니 중복 확인하고 수량 가져오기
			map =  new HashMap<>();
			map.put("id", id);
			map.put("no", no);
			String countChk = mDao.cartCheck(map);

			// 장바구니에 추가
			if (countChk == null) {
				Cart cart = new Cart();
				cart.setId(id);
				cart.setNo(b.getNo());
				cart.setTitle(b.getTitle());
				cart.setCart_price(b.getPrice());
				cart.setCart_count(count);
				cart.setImage(b.getImage());
				cnt = mDao.setCart(cart);
				
			// 중복이면 수량 추가
			} else {
				map.put("id", id); 
				map.put("no", no);
				map.put("cart_count", Integer.parseInt(countChk) + count);
				cnt = mDao.setCartCount(map);
			}
		// 찜하기
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

	// 회원 환불 신청
	@Override
	public void orderRefund(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("order_num");

		Map<String, Object> map = new HashMap<>();
		map.put("state", 8);
		map.put("num", order_num);
		int cnt = hDao.orderStateUpdate(map);

		model.addAttribute("cnt", cnt);
	}

	// 회원 환불 내역
	@Override
	public void getRefundView(HttpServletRequest req, Model model) {
		String id = (String) req.getSession().getAttribute("memId");

		ArrayList<Bespeak> refunds = mDao.getRefundList(id);

		model.addAttribute("refunds", refunds);
	}

}