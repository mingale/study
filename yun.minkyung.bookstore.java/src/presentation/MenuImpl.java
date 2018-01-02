package presentation;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import domain.Book;
import domain.Code;
import service.GuestImpl;
import service.HostImpl;

public class MenuImpl implements Menu, Code{
	private Map<String, String> login = new HashMap<>();
	
	public static PrintWriter prW = Console.output();
	
	private HostImpl h = HostImpl.getInstance();
	private GuestImpl g = GuestImpl.getInstance();
	
	// 메서드 호출 모음
	@Override
	public void commonMenu(int menu) {
		switch(menu) {
		// 로그인
		case SHOP_LOGIN: loginMenu(); break;
		case HOST_MENU: hostMenu(); break;
		// 재고 관리
		case HOST_STOCK_MENU: hostStockMenu(); break;
		case HOST_BOOK_lIST: 
			h.bookList();
			hostStockMenu();break;
		case HOST_BOOK_ADD:
			h.bookAdd(); 
			hostStockMenu();break;
		case HOST_BOOK_UPDATE: 
			try {
				h.bookUpdate(); 
			}catch(NullPointerException e) {}
			hostStockMenu();break;
		case HOST_BOOK_DEL: 
			h.bookDel(); 
			hostStockMenu();break;
		// 주문 관리
		case HOST_ORDER_MENU: 
			hostOrderMenu(); break;
		case HOST_ORDER_LIST: 
			h.orderList();
			hostOrderMenu(); break;
		case HOST_ORDER_CONFIRM: 
			h.orderConfirm();
			hostOrderMenu(); break;
		case HOST_ORDER_CANCLE:  
			h.orderRefund(); 
			hostOrderMenu(); break;
		case HOST_SALE_TOTAL:  
			h.salesTotal(); 
			hostOrderMenu(); break;
		case GUEST_MENU:  
			guestMenu(); break;
		// 장바구니
		case GUEST_CART_LIST:
			guestCartMenu();break;
		case GUEST_CART_ADD: 
			g.cartAdd();   
			guestCartMenu();break;
		case GUEST_CART_DEL: 
			g.cartDel(); 
			guestCartMenu();break;
		case GUEST_CART_BUY:   
			g.cartBuy();
			guestCartMenu(); break;
		// 구매
		case GUEST_NOW_BUY:   
			g.nowBuy();
			guestMenu();break;
		case GUEST_BUY_LIST:   
			g.buyList();
			guestMenu();break;
		// 환불
		case GUEST_REFUND:
			g.refund();
			guestMenu();break;
		}
	}

	// 로그인 메뉴 출력문
	@Override
	public void loginMenu() {
		print("");
		
		print("────────────────────────── 로그인 ──────────────────────────");
		print("\t1.고객           2.관리자           3.회원가입           4.종료");
		print("───────────────────────────────────────────────────────────");

		int choice = consoleTryCatch("\t메뉴 번호를 입력하세요. : ");
		
		String id = "", pw = "";
		
		// 메뉴 선택
		switch(choice) {
		case 1:
			System.out.print("\tID : ");
			id = Console.input();
			System.out.print("\tPW : ");
			pw = Console.input();
			prW.println("\tID : " + id);
			prW.println("\tPW : " + pw);

			print("===========================================================");
					
			for(Map.Entry<String, String> map : login.entrySet()) {
				if(map.getKey().equals(id) && map.getValue().equals(pw)) {
					print("\t로그인 되었습니다.");
					print("===========================================================");
					g.id = id;
					commonMenu(GUEST_MENU); return;
				} 
			}
			print("\t잘못된 정보입니다.");
			print("===========================================================");

			commonMenu(SHOP_LOGIN); break;
		case 2:
			System.out.print("\t관리자 ID : ");
			id = Console.input();
			System.out.print("\t관리자 PW : ");
			pw = Console.input();
			prW.println("\t관리자 ID : " + id);
			prW.println("\t관리자 PW : "+ pw);

			print("===========================================================");
			if(h.ID.equals(id) && h.PASSWORD.equals(pw)) {
				print("\t로그인 되었습니다.");
				print("===========================================================");
				commonMenu(HOST_MENU); return;
			}
			print("\t잘못된 정보입니다.");
			print("===========================================================");
			
			commonMenu(SHOP_LOGIN); break;
		case 3:
			print("===========================================================");
			print("\t회원 가입");
			print("===========================================================");
			
			while(true) {
				System.out.print("\tID : ");
				id = Console.input();
				prW.println("\tID : " + id);
				
				if(!login.containsKey(id)) {
					break;
				} else {
					print("\t이미 존재하는 ID 입니다.");
				}
			}

			System.out.print("\tPW : ");
			pw = Console.input();
			prW.println("\tPW : " + pw);

			print("===========================================================");
			if(!id.equals("") && !pw.equals("")) {
				login.put(id, pw);
				print("\t회원 가입 완료");
			} else {
				print("\t회원 가입 실패 (공백 사용 불가)");
			}
			print("===========================================================");
			
			commonMenu(SHOP_LOGIN); 
			break;
		case 4: 
			print("\t프로그램을 종료합니다.");
			prW.flush(); 
			break;
		default: loginMenu(); break;
		}
	}

	// 관리자 메뉴 출력
	@Override
	public void hostMenu() {
		print("");
		
		print("──────────────────────── 관리자 메뉴 ─────────────────────────");
		print("\t1.재고관리" + "\t2.주문관리" + "\t3.로그아웃");
		print("───────────────────────────────────────────────────────────");
		
		int choice = consoleTryCatch("\t메뉴 번호를 입력하세요. : ");
		
		switch(choice) {
		case 1: commonMenu(HOST_STOCK_MENU); break;
		case 2: commonMenu(HOST_ORDER_MENU); break;
		case 3: commonMenu(SHOP_LOGIN); break;
		default : hostMenu(); break;
		}
	}

	// 관리자 메뉴 - 재고 관리 출력
	@Override
	public void hostStockMenu() {
		print("");
		
		print("───────────────────────── 재고 관리 ─────────────────────────");
		print("\t1.목록" + "\t2.추가" + "\t3.수정" + "\t4.삭제" + "\t5.이전");
		print("───────────────────────────────────────────────────────────");
		
		int choice = consoleTryCatch("\t메뉴 번호를 입력하세요. : ");
		
		switch(choice) {
		case 1: commonMenu(HOST_BOOK_lIST); break;
		case 2: commonMenu(HOST_BOOK_ADD); break;
		case 3: commonMenu(HOST_BOOK_UPDATE); break;
		case 4: commonMenu(HOST_BOOK_DEL); break;
		case 5: hostMenu(); break;
		default : hostStockMenu(); break;
		}
	}

	// 관리자 메뉴 - 주문 관리 출력
	@Override
	public void hostOrderMenu() {
		print("");
		
		print("───────────────────────── 주문 관리 ─────────────────────────");
		print("\t1.주문목록       2.결제승인       3.결제취소        4.결산       5.이전");
		print("───────────────────────────────────────────────────────────");
		
		int choice = consoleTryCatch("\t메뉴 번호를 입력하세요. : ");
		
		switch(choice) {
		case 1: commonMenu(HOST_ORDER_LIST); break;
		case 2: commonMenu(HOST_ORDER_CONFIRM); break;
		case 3: commonMenu(HOST_ORDER_CANCLE); break;
		case 4: commonMenu(HOST_SALE_TOTAL); break;
		case 5: hostMenu(); break;
		default: hostOrderMenu(); break;
		}
	}

	// 고객 메뉴
	@Override
	public void guestMenu() {
		print("");
		
		print("───────────────────────── 고객 메뉴 ─────────────────────────");
		print("\t1.장바구니            2.구매            3.환불            4.로그아웃");
		print("───────────────────────────────────────────────────────────");
		
		int choice = consoleTryCatch("\t메뉴 번호를 입력하세요. : ");
		switch(choice) {
		case 1: commonMenu(GUEST_CART_LIST); break;
		case 2: commonMenu(GUEST_NOW_BUY); break;
		case 3: commonMenu(GUEST_REFUND); break;
		case 4: commonMenu(SHOP_LOGIN); break;
		default: guestMenu(); break;
		}
	}

	// 고객 장바구니 메뉴
	@Override
	public void guestCartMenu() {
		print("");
		
		print("======================== 장바구니 목록 ========================");
		print("\t번호" + "\t도서명" + "\t저자" + "\t가격" + "\t수량");
		print("===========================================================");
		
		g.cartList();

		print("────────────────────────── 장바구니 ─────────────────────────");
		print("\t1.추가" + "\t2.삭제" + "\t3.구매" + "\t4.이전");
		print("───────────────────────────────────────────────────────────");
		
		int choice = consoleTryCatch("\t메뉴 번호를 입력하세요. : ");
		
		switch(choice) {
		case 1: commonMenu(GUEST_CART_ADD); break;
		case 2: commonMenu(GUEST_CART_DEL); break;
		case 3: commonMenu(GUEST_CART_BUY); break;
		case 4: guestMenu(); break;
		default: guestCartMenu(); break;
		}
	}
	
	// console에서 int 외의 입력 오류 방지 
	public static int consoleTryCatch(String s) {
		int input = 0;
		System.out.print(s);
		
		try {
			input = Integer.valueOf(Console.input());
		} catch(NumberFormatException e) {
			print("\t잘못된 입력입니다.");
		}
		
		prW.println(s + input);
		return input;
	}
	
	public static void print(String s) {
		System.out.println(s);
		prW.println(s);
	}
}
