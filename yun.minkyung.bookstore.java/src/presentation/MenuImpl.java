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
	
	// �޼��� ȣ�� ����
	@Override
	public void commonMenu(int menu) {
		switch(menu) {
		// �α���
		case SHOP_LOGIN: loginMenu(); break;
		case HOST_MENU: hostMenu(); break;
		// ��� ����
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
		// �ֹ� ����
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
		// ��ٱ���
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
		// ����
		case GUEST_NOW_BUY:   
			g.nowBuy();
			guestMenu();break;
		case GUEST_BUY_LIST:   
			g.buyList();
			guestMenu();break;
		// ȯ��
		case GUEST_REFUND:
			g.refund();
			guestMenu();break;
		}
	}

	// �α��� �޴� ��¹�
	@Override
	public void loginMenu() {
		print("");
		
		print("���������������������������������������������������� �α��� ����������������������������������������������������");
		print("\t1.��           2.������           3.ȸ������           4.����");
		print("����������������������������������������������������������������������������������������������������������������������");

		int choice = consoleTryCatch("\t�޴� ��ȣ�� �Է��ϼ���. : ");
		
		String id = "", pw = "";
		
		// �޴� ����
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
					print("\t�α��� �Ǿ����ϴ�.");
					print("===========================================================");
					g.id = id;
					commonMenu(GUEST_MENU); return;
				} 
			}
			print("\t�߸��� �����Դϴ�.");
			print("===========================================================");

			commonMenu(SHOP_LOGIN); break;
		case 2:
			System.out.print("\t������ ID : ");
			id = Console.input();
			System.out.print("\t������ PW : ");
			pw = Console.input();
			prW.println("\t������ ID : " + id);
			prW.println("\t������ PW : "+ pw);

			print("===========================================================");
			if(h.ID.equals(id) && h.PASSWORD.equals(pw)) {
				print("\t�α��� �Ǿ����ϴ�.");
				print("===========================================================");
				commonMenu(HOST_MENU); return;
			}
			print("\t�߸��� �����Դϴ�.");
			print("===========================================================");
			
			commonMenu(SHOP_LOGIN); break;
		case 3:
			print("===========================================================");
			print("\tȸ�� ����");
			print("===========================================================");
			
			while(true) {
				System.out.print("\tID : ");
				id = Console.input();
				prW.println("\tID : " + id);
				
				if(!login.containsKey(id)) {
					break;
				} else {
					print("\t�̹� �����ϴ� ID �Դϴ�.");
				}
			}

			System.out.print("\tPW : ");
			pw = Console.input();
			prW.println("\tPW : " + pw);

			print("===========================================================");
			if(!id.equals("") && !pw.equals("")) {
				login.put(id, pw);
				print("\tȸ�� ���� �Ϸ�");
			} else {
				print("\tȸ�� ���� ���� (���� ��� �Ұ�)");
			}
			print("===========================================================");
			
			commonMenu(SHOP_LOGIN); 
			break;
		case 4: 
			print("\t���α׷��� �����մϴ�.");
			prW.flush(); 
			break;
		default: loginMenu(); break;
		}
	}

	// ������ �޴� ���
	@Override
	public void hostMenu() {
		print("");
		
		print("������������������������������������������������ ������ �޴� ��������������������������������������������������");
		print("\t1.������" + "\t2.�ֹ�����" + "\t3.�α׾ƿ�");
		print("����������������������������������������������������������������������������������������������������������������������");
		
		int choice = consoleTryCatch("\t�޴� ��ȣ�� �Է��ϼ���. : ");
		
		switch(choice) {
		case 1: commonMenu(HOST_STOCK_MENU); break;
		case 2: commonMenu(HOST_ORDER_MENU); break;
		case 3: commonMenu(SHOP_LOGIN); break;
		default : hostMenu(); break;
		}
	}

	// ������ �޴� - ��� ���� ���
	@Override
	public void hostStockMenu() {
		print("");
		
		print("�������������������������������������������������� ��� ���� ��������������������������������������������������");
		print("\t1.���" + "\t2.�߰�" + "\t3.����" + "\t4.����" + "\t5.����");
		print("����������������������������������������������������������������������������������������������������������������������");
		
		int choice = consoleTryCatch("\t�޴� ��ȣ�� �Է��ϼ���. : ");
		
		switch(choice) {
		case 1: commonMenu(HOST_BOOK_lIST); break;
		case 2: commonMenu(HOST_BOOK_ADD); break;
		case 3: commonMenu(HOST_BOOK_UPDATE); break;
		case 4: commonMenu(HOST_BOOK_DEL); break;
		case 5: hostMenu(); break;
		default : hostStockMenu(); break;
		}
	}

	// ������ �޴� - �ֹ� ���� ���
	@Override
	public void hostOrderMenu() {
		print("");
		
		print("�������������������������������������������������� �ֹ� ���� ��������������������������������������������������");
		print("\t1.�ֹ����       2.��������       3.�������        4.���       5.����");
		print("����������������������������������������������������������������������������������������������������������������������");
		
		int choice = consoleTryCatch("\t�޴� ��ȣ�� �Է��ϼ���. : ");
		
		switch(choice) {
		case 1: commonMenu(HOST_ORDER_LIST); break;
		case 2: commonMenu(HOST_ORDER_CONFIRM); break;
		case 3: commonMenu(HOST_ORDER_CANCLE); break;
		case 4: commonMenu(HOST_SALE_TOTAL); break;
		case 5: hostMenu(); break;
		default: hostOrderMenu(); break;
		}
	}

	// �� �޴�
	@Override
	public void guestMenu() {
		print("");
		
		print("�������������������������������������������������� �� �޴� ��������������������������������������������������");
		print("\t1.��ٱ���            2.����            3.ȯ��            4.�α׾ƿ�");
		print("����������������������������������������������������������������������������������������������������������������������");
		
		int choice = consoleTryCatch("\t�޴� ��ȣ�� �Է��ϼ���. : ");
		switch(choice) {
		case 1: commonMenu(GUEST_CART_LIST); break;
		case 2: commonMenu(GUEST_NOW_BUY); break;
		case 3: commonMenu(GUEST_REFUND); break;
		case 4: commonMenu(SHOP_LOGIN); break;
		default: guestMenu(); break;
		}
	}

	// �� ��ٱ��� �޴�
	@Override
	public void guestCartMenu() {
		print("");
		
		print("======================== ��ٱ��� ��� ========================");
		print("\t��ȣ" + "\t������" + "\t����" + "\t����" + "\t����");
		print("===========================================================");
		
		g.cartList();

		print("���������������������������������������������������� ��ٱ��� ��������������������������������������������������");
		print("\t1.�߰�" + "\t2.����" + "\t3.����" + "\t4.����");
		print("����������������������������������������������������������������������������������������������������������������������");
		
		int choice = consoleTryCatch("\t�޴� ��ȣ�� �Է��ϼ���. : ");
		
		switch(choice) {
		case 1: commonMenu(GUEST_CART_ADD); break;
		case 2: commonMenu(GUEST_CART_DEL); break;
		case 3: commonMenu(GUEST_CART_BUY); break;
		case 4: guestMenu(); break;
		default: guestCartMenu(); break;
		}
	}
	
	// console���� int ���� �Է� ���� ���� 
	public static int consoleTryCatch(String s) {
		int input = 0;
		System.out.print(s);
		
		try {
			input = Integer.valueOf(Console.input());
		} catch(NumberFormatException e) {
			print("\t�߸��� �Է��Դϴ�.");
		}
		
		prW.println(s + input);
		return input;
	}
	
	public static void print(String s) {
		System.out.println(s);
		prW.println(s);
	}
}
