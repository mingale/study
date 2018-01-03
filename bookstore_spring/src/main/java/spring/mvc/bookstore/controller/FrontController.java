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
	
	//도서 검색
	@RequestMapping("bookSearch")
	public String bookSearch(HttpServletRequest req, Model model) {
		System.out.println("bookSearch()");
		
		bs.bookSearch(req, model);
		
		return "jsp/main/bookListSearch";
	}
	
	//도서 상세 보기
	@RequestMapping("detail")
	public String detail(HttpServletRequest req, Model model) {
		System.out.println("detail()");
		
		bs.detailView(req, model);
		
		return "jsp/main/detail";
	} 
	
	//도서 리스트
	@RequestMapping("bookList")
	public String bookList(HttpServletRequest req, Model model) {
		System.out.println("bookList()");
		
		bs.bookListView(req, model);
		
		return "jsp/main/bookList";
	}
	
	//로그인 화면
	@RequestMapping("signIn")
	public String signIn() {
		System.out.println("signIn()");
		return "jsp/join/signIn";
	}
	
	//로그인 처리
	@RequestMapping("signInPro")
	public String signInPro(HttpServletRequest req, Model model) {
		System.out.println("signInPro()");
		
		hs.signIn(req, model);
		
		return "jsp/process/signInPro";	
	}
	
	//로그아웃
	@RequestMapping("signOut")
	public String signOut(HttpServletRequest req, Model model) {
		System.out.println("signOut()");
		
		req.getSession().invalidate();
		return index(req, model);
	}
	
	//회원 가입 약관
	@RequestMapping("join")
	public String join(HttpServletRequest req, Model model) {
		System.out.println("join()");
		return "jsp/join/join";
	}
	
	//회원 가입 화면
	@RequestMapping("signUp")
	public String signUp() {
		System.out.println("signUp()");
		return "jsp/join/signUp";
	}	
	
	//회원 가입 처리
	@RequestMapping("signUpPro")
	public String signUpPro(HttpServletRequest req, Model model) {
		System.out.println("signUpPro()");
			
		hs.signUp(req, model);
			
		return "jsp/process/signUpPro";
	}
	
	//아이디 중복 확인
	@RequestMapping("confirmId")
	public String confirmId(HttpServletRequest req, Model model) {
		System.out.println("confirmId()");
		
		hs.confirmId(req, model);
		
		return "jsp/process/confirmId";
	}
	
	//이메일 중복 확인
	@RequestMapping("confirmEmail")
	public String confirmEmail(HttpServletRequest req, Model model) {
		System.out.println("confirmEmail()");
		
		hs.confirmEmail(req, model);
		
		return "jsp/process/confirmEmail";
	}
	
	//이메일 인증 확인
	@RequestMapping("emailCheck")
	public String emailCheck(HttpServletRequest req, Model model) {
		System.out.println("emailCheck()");
		
		hs.confirmEmailKey(req, model);
		
		return "jsp/join/emailCheck";
	}
	
	//아이디, 비밀번호 찾기 화면
	@RequestMapping("signFindView")
	public String signFindView(HttpServletRequest req, Model model) {
		System.out.println("signFindView()");
		
		return "jsp/join/signFindView";
	}

	//아이디/비밀번호 찾기
	@RequestMapping("signFind")
	public String signFind(HttpServletRequest req, Model model) {
		System.out.println("signFind()");
		
		hs.memberFindEmailKey(req, model);
		
		return "jsp/join/signFind";
	}
	
	//마이페이지 비밀번호 확인
	@RequestMapping("myPagePwd")
	public String myPagePwd(HttpServletRequest req, Model model) {
		System.out.println("myPagePwd()");
		
		return "jsp/process/myPagePwd";
	}
	
	//회원 정보 출력
	@RequestMapping("myPageView")
	public String myPageView(HttpServletRequest req, Model model) {
		System.out.println("myPageView()");
		
		hs.memberQuery(req, model);
		
		return "jsp/guest/myPageView";
	}
	
	//회원 정보 수정
	@RequestMapping("myPagePro")
	public String myPagePro(HttpServletRequest req, Model model) {
		System.out.println("myPagePro()");
		
		hs.memberUpdate(req, model);
		
		return "jsp/process/myPagePro";
	}
	
	//회원 탈퇴
	@RequestMapping("memOutPro")
	public String memOutPro(HttpServletRequest req, Model model) {
		System.out.println("memOutPro()");
		
		hs.memberDelete(req, model);
		
		return "jsp/process/memOutPro";
	}
	
	//고객센터
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
	
	//장바구니에서 구매 버튼
	@RequestMapping("order")
	public String order(HttpServletRequest req, Model model) {
		System.out.println("order()");
		
		gs.orderView(req, model);
		
		return "jsp/guest/order";
	} 
	
	//주문 처리
	@RequestMapping("orderPro")
	public String orderPro(HttpServletRequest req, Model model) {
		System.out.println("orderPro()");
		
		gs.addOrder(req, model);
		
		return "jsp/guest/orderPro";
	} 

	//장바구니 목록
	@RequestMapping("cart")
	public String cart(HttpServletRequest req, Model model) {
		System.out.println("cart()");
		
		gs.cartView(req, model);
		
		return "jsp/guest/cart";
	} 
	
	//바로구매,장바구니,찜하기 버튼 처리
	@RequestMapping("bookBtnPro")
	public String cartBtn(HttpServletRequest req, Model model) {
		System.out.println("bookBtnPro()");
		String id = (String) req.getSession().getAttribute("memId");
		
		if(id != null) {
			String btn = req.getParameter("btn");
			if(btn.equals("바로 구매")) {
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
	
	//장바구니 삭제 또는 수정
	@RequestMapping("cartPro")
	public String cartPro(HttpServletRequest req, Model model) {
		System.out.println("cartPro()");
		
		gs.cartPro(req, model);
		
		return "jsp/guest/cartPro";
	}
	
	//주문 내역
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
	
	//주문 취소
	@RequestMapping("myOrderDelete")
	public String myOrderDelete(HttpServletRequest req, Model model) {
		System.out.println("myOrderDelete()");
		
		hs.orderDelete(req, model);
		
		return "jsp/guest/myOrderDelete";
	}
	
	//환불 신청
	@RequestMapping("myOrderRefund")		
	public String myOrderRefund(HttpServletRequest req, Model model) {
		System.out.println("myOrderRefund()");
		
		gs.orderRefund(req, model);

		return "jsp/guest/refund";
	}
	
	//환불 내역
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
	
	//재고 관리 페이지
	@RequestMapping("stock")
	public String stock(HttpServletRequest req, Model model) {
		System.out.println("stock()");
		
		bs.hostStockView(req, model); //도서 정보와 서브 정보의 총개수가 다르면, 가져온 목록에 차이가 생겨서 서브 정보가 올바르게 표시되지 못한다.
		
		return "jsp/host/stock";
	}
		
	//도서 수정 페이지
	@RequestMapping("stockUpdate")
	public String stockUpdate(HttpServletRequest req, Model model) {
		System.out.println("stockUpdate()");
		
		bs.detailView(req, model);
		
		return "jsp/host/stockUpdate";
	}
		
	//도서 수정 처리
	@RequestMapping("stockUpdatePro")
	public String stockUpdatePro(MultipartHttpServletRequest req, Model model) {
		System.out.println("stockUpdatePro()");
		
		bs.bookUpdate(req, model);
		
		return "jsp/host/stockUpdatePro";
	}
	
	//도서 삭제
	@RequestMapping("stockDelete")
	public String stockDelete(HttpServletRequest req, Model model) {
		System.out.println("stockDelete()");
		
		bs.bookDelete(req, model);
		
		return "jsp/host/stockDelete";
	}
	
	//도서 추가
	@RequestMapping("stockAdd")
	public String stockAdd(HttpServletRequest req, Model model) {
		System.out.println("stockAdd()");
		
		bs.bookInsertView(req, model);
		
		return "jsp/host/stockUpdate";
	}
	
	//주문 관리 페이지  order.ho => hostOrder
	@RequestMapping("hostOrder")
	public String hostOrder(HttpServletRequest req, Model model) {
		System.out.println("hostOrder()");
		
		hs.hostOrderView(req, model);
		
		return "jsp/host/order";			
	}
	
	//주문 승인
	@RequestMapping("orderOk")
	public String orderOk(HttpServletRequest req, Model model) {
		System.out.println("orderOk()");
		
		hs.orderStateUpdate(req, model);
		
		return "jsp/host/orderOk";
	}
	
	//주문 삭제
	@RequestMapping("orderDelete")
	public String orderDelete(HttpServletRequest req, Model model) {
		System.out.println("orderDelete()");
		
		hs.orderDelete(req, model);
		
		return "jsp/host/orderDelete";
	}
	
	//관리자 주문목록의 배송 시작 버튼
	@RequestMapping("orderShipping")
	public String orderShipping(HttpServletRequest req, Model model) {
		System.out.println("orderShipping()");
		
		model.addAttribute("order_num", req.getParameter("order_num"));
		
		return "jsp/host/orderShipping";
	}
	
	//배송시작 송장번호
	@RequestMapping("shippingPro")
	public String shippingPro(HttpServletRequest req, Model model) {
		System.out.println("shippingPro()");
		
		hs.shippingPro(req, model);
		
		return "jsp/host/shippingPro";
	}
	
	//환불 목록
	@RequestMapping("refund")
	public String refund(HttpServletRequest req, Model model) {
		System.out.println("refund()");
		
		hs.getHostRefundView(req, model);
		
		return "jsp/host/refund";
	}
	
	//환불 완료
	@RequestMapping("refundOk")
	public String refundOk(HttpServletRequest req, Model model) {
		System.out.println("refundOk()");
		
		hs.refundOk(req, model);
		
		return "jsp/host/refundOk";
	}
	
	//환불 거부
	@RequestMapping("refundNo")
	public String refundNo(HttpServletRequest req, Model model) {
		System.out.println("refundNo()");
		
		hs.refundNo(req, model);
		
		return "jsp/host/refundNo";
	}
	
	//회원 관리 페이지
	@RequestMapping("member")
	public String member(HttpServletRequest req, Model model) {
		System.out.println("member()");
		
		hs.getMemberView(req, model);
		
		return "jsp/host/member";
	}
	
	//회원 관리 수정
	@RequestMapping("memberUpdate")
	public String memberUpdate(HttpServletRequest req, Model model) {
		System.out.println("memberUpdate()");

		hs.setHostMemberUpdate(req, model);

		return "jsp/host/memberUpdate";
	}
	
	//회원 강제 탈퇴
	@RequestMapping("memberDelete")
	public String memberDelete(HttpServletRequest req, Model model) {
		System.out.println("memberDelete()");
		
		hs.memberDelete(req, model);
		
		return "jsp/host/memberDelete";
	}
	
	//결산
	@RequestMapping("result")
	public String result(HttpServletRequest req, Model model) {
		System.out.println("result()");
		
		hs.getResultTotal(req, model);
		
		return "jsp/host/result";
	}
	
	//공지사항
	@RequestMapping("notice") 
	public String notice(HttpServletRequest req, Model model){
		System.out.println("notice()");
		
		hs.getNotice(req, model);
		
		return "jsp/host/notice";
	}
	
	//공지사항 글쓰기
	@RequestMapping("noticeWrite")
	public String noticeWrite(HttpServletRequest req, Model model) {
		System.out.println("noticeWrite()");
		return "jsp/host/noticeWrite";
	}
	
	//공지사항 글쓰기 처리
	@RequestMapping("noticeWritePro")
	public String noticeWritePro(HttpServletRequest req, Model model) {
		System.out.println("noticeWritePro()");
		
		hs.noticeWritePro(req, model);
		
		return "jsp/host/noticeWritePro";
	}
	
	//공지사항 상세보기
	@RequestMapping("noticeView")
	public String noticeView(HttpServletRequest req, Model model) {
		System.out.println("noticeView()");
		
		hs.noticeView(req, model);
		return "jsp/host/noticeView";
	}
}
