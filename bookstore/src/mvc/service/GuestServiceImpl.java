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
		String carts = req.getParameter("carts"); //장바구니에서 선택한 도서들, 바로구매 도서

		//바로 구매에서 넘어온 도서
		String btn = req.getParameter("btn");
		if(btn != null) { 
			int nowCount = Integer.parseInt(req.getParameter("count"));
			carts = req.getParameter("no");
			
			req.setAttribute("btn", btn);
			req.setAttribute("nowCount", nowCount);
		}//else : 장바구니에서 온 데이터
		
		//회원 정보
		MemberPers dao = MemberPersImpl.getInstance();
		Member mem = dao.getMemberInfo(id);
		
		req.setAttribute("carts", carts);
		req.setAttribute("mem", mem);
	}

	//장바구니 목록
	@Override
	public void cartView(HttpServletRequest req, HttpServletResponse res) {
		String id = (String) req.getSession().getAttribute("memId");
		
		MemberPers dao = MemberPersImpl.getInstance();
		ArrayList<Cart> carts = dao.getCartList(id);
		
		req.setAttribute("carts", carts);
	}

	//장바구니 처리
	@Override
	public void cartPro(HttpServletRequest req, HttpServletResponse res) {
		String cmd = req.getParameter("cmd");
		
		String id = (String) req.getSession().getAttribute("memId");
		String no = req.getParameter("no");
		String count = req.getParameter("count");
		
		int cnt = 0;
		MemberPers dao = MemberPersImpl.getInstance();
		//장바구니 삭제
		if(cmd.equals("de")) {
			//선택 상품들 삭제
			if(no == null) {
				no = req.getParameter("nos");
				String[] nos = no.split(",");
				
				for(String num : nos) {
					cnt = dao.cartDelete(id, num);
				}
			//개별 삭제
			} else {
				cnt = dao.cartDelete(id, no);
			}
		//장바구니 개별 수정
		} else if(cmd.equals("up")) {
			String[] nos = req.getParameterValues("no");
			cnt = dao.cartCountUpdate(id, no, Integer.parseInt(count));
			System.out.println(id+"/"+no+"/"+count);
			System.out.println(nos.length);
		}
		
		req.setAttribute("cnt", cnt);
	}

	//주문 처리
	@Override
	public void addOrder(HttpServletRequest req, HttpServletResponse res) {
		String btn = req.getParameter("btn");//바로 구매에서 넘어온 데이터
		
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
		//장바구니에서 구매로 넘어왔을 경우
		if(!btn.equals("바로 구매")) {
			//주문 추가
			cnt = dao.setOrder(nos, order);
			
			//주문 추가가 성공적이면 장바구니 삭제
			if(cnt != 0) {
				for(String no : nos) {
					cnt = dao.cartDelete(id, no);
				}
			}
			
		//바로 구매에서 넘어왔을 경우
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

	//주문 상세 페이지
	public void orderDetailView(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("order_num");
		
		//주문 정보
		MemberPers dao = MemberPersImpl.getInstance();
		ArrayList<Bespeak> orders = dao.getOrderDetail(order_num);
		
		//송장번호
		String shipping = dao.getShipping(order_num);
		if(shipping == null) shipping = " - ";
		
		req.setAttribute("orders", orders);
		req.setAttribute("shipping", shipping);
	}
		
	//도서 버튼 처리
	@Override
	public void bookBtnPro(HttpServletRequest req, HttpServletResponse res) {
		String btn = req.getParameter("btn");
		String id = (String) req.getSession().getAttribute("memId");
		String no = req.getParameter("no");
		
		BookPers bDao = BookPersImpl.getInstance();
		MemberPers dao = MemberPersImpl.getInstance();

		//바로구매 수량
		int count = 1;
		String tmpCount = req.getParameter("count");
		if(tmpCount != null) { count = Integer.parseInt(tmpCount);}
		else { count = Integer.parseInt(req.getParameter("count")); } ///////////////////도서 상세에서 장바구니 담기
		System.out.println("bookBtnPro : " + count);
		
		int cnt = 0;
		if(btn.equals("장바구니 담기")) {
			Book b = bDao.getBookInfo(no);
			
			//장바구니 중복 확인하고 수량 가져오기
			int countChk = dao.cartCheck(id, no);
			
			//장바구니에 추가
			if(countChk == 0) cnt = dao.setCart(id, b, count);
			//중복이면 수량 추가
			else cnt = dao.setCartCount(id, no, countChk+count);
			
		//찜하기
		} else {
			cnt = dao.wishlistCheck(id, no);
			if(cnt != 9) cnt = dao.setWishlist(id, no);
		}
		
		req.setAttribute("btn", btn);
		req.setAttribute("cnt", cnt);
	}

	//회원 환불 신청
	@Override
	public void orderRefund(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("order_num");
		
		MemberPers dao = MemberPersImpl.getInstance();
		int cnt = dao.orderStateUpdate(order_num, 8);
		
		req.setAttribute("cnt", cnt);
	}

	//회원 환불 내역
	@Override
	public void getRefundView(HttpServletRequest req, HttpServletResponse res) {
		String id = (String) req.getSession().getAttribute("memId");
		
		MemberPers dao = MemberPersImpl.getInstance();
		ArrayList<Bespeak> refunds = dao.getRefundList(id);
		
		req.setAttribute("refunds", refunds);
	}


}