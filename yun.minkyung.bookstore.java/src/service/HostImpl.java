package service;

import java.util.Map;

import domain.Book;
import domain.Order;
import domain.Refund;
import domain.Sales;
import domain.Shelf;
import presentation.Console;
import presentation.MenuImpl;

public class HostImpl implements Host {
	boolean flag = true;

	private static HostImpl host = new HostImpl();

	private HostImpl() {
	}

	public static HostImpl getInstance() {
		return host;
	}

	// ���� ��� ���
	@Override
	public void bookList() {
		MenuImpl.print("========================== ���� ��� =========================");
		MenuImpl.print("\t��ȣ" + "\t������" + "\t����" + "\t����" + "\t����");
		MenuImpl.print("===========================================================");

		for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
			MenuImpl.print(shelf.getKey().toString());
		}
	}

	// ���� �߰�
	@Override
	public void bookAdd() {
		MenuImpl.print("========================== ���� ��� =========================");

		System.out.print("\tå ����: ");
		String bookTitle = Console.input();
		System.out.print("\tå ����: ");
		String bookAuthor = Console.input();
		MenuImpl.prW.println("\tå ����: " + bookTitle);
		MenuImpl.prW.println("\tå ����: " + bookAuthor);

		int bookPrice = MenuImpl.consoleTryCatch("\tå ����: ");
		int bookCount = MenuImpl.consoleTryCatch("\tå ����: ");

		MenuImpl.print("===========================================================");
		if (bookCount != 0) {
			Shelf shelf = new Shelf();
			shelf.setBookTitle(bookTitle);
			shelf.setBookAuthor(bookAuthor);
			shelf.setBookPrice(bookPrice);
			shelf.setBookCount(bookCount);
			Shelf.map.put(shelf, null);
			MenuImpl.print("\t������ ��ϵǾ����ϴ�.");

		} else {
			MenuImpl.print("\t���� ��� ���� (0 �Է� �Ұ�)");
		}
		MenuImpl.print("===========================================================");

	}

	// ���� ����
	/*
	 * ConcurrentModificationException ��ü�� ���¸� ���ÿ� �����ϴ� ���� ������� ���� �� �߻� �ϳ��� �����尡
	 * �÷����� ��ȸ ���� �� �ٸ� �ϳ��� �����尡 �÷����� �����ϴ� ���� ������� �ʴ´�.
	 */
	@Override
	public void bookUpdate() {
		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t�����Ϸ��� å�� �ڵ带 �Է��ϼ���. [����:0] : ");
			if (bookNo == 0) {
				return;
			}

			for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
				if (shelf.getKey().getBookNo() == bookNo) {
					MenuImpl.print("========================== ���� ���� =========================");

					System.out.print("\tå ����: ");
					String bookTitle = Console.input();
					System.out.print("\tå ����: ");
					String bookAuthor = Console.input();
					MenuImpl.prW.println("\tå ����: " + bookTitle);
					MenuImpl.prW.println("\tå ����: " + bookAuthor);

					int bookPrice = MenuImpl.consoleTryCatch("\tå ����: ");
					int bookCount = MenuImpl.consoleTryCatch("\tå ����: ");

					// ���� ������ 0���� �Է��ϴ� �� ����
					MenuImpl.print("===========================================================");
					if (bookCount != 0) {
						shelf.getKey().setBookTitle(bookTitle);
						shelf.getKey().setBookAuthor(bookAuthor);
						shelf.getKey().setBookPrice(bookPrice);
						shelf.getKey().setBookCount(bookCount);
						MenuImpl.print("\t" + bookNo + "�� ������ �����Ǿ����ϴ�.");
					} else {
						MenuImpl.print("\t���� ���� ���� (0 �Է� �Ұ�)");
					}
					MenuImpl.print("===========================================================");

					flag = false;
					break; // ConcurrentModificationException ����
				}
			}
			bookNoNull();
		}
	}

	// ���� ����
	@Override
	public void bookDel() {
		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t�����Ϸ��� å�� �ڵ带 �Է��ϼ���. [����:0] : ");
			if (bookNo == 0) {
				return;
			}

			for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
				if (shelf.getKey().getBookNo() == bookNo) {
					Shelf.map.remove(shelf.getKey());

					MenuImpl.print("========================== ���� ���� =========================");
					MenuImpl.print("\t" + bookNo + "�� ������ �����Ǿ����ϴ�.");
					MenuImpl.print("===========================================================");

					flag = false;
					break;
				}
			}
			bookNoNull();
		}
	}

	// Guest�� ���� ��û�� ��� ���
	@Override
	public void orderList() {
		MenuImpl.print("======================== ���� ��û ��� =======================");
		MenuImpl.print("\t��ȣ" + "\t������" + "\t����" + "\t����" + "\t����" + "\t��ID");
		MenuImpl.print("===========================================================");

		for (Map.Entry<Book, String> order : Order.map.entrySet()) {
			MenuImpl.print(order.getKey().toString() + "\t" + order.getValue());
		}
	}

	/*
	 * ���� ��û�� �����Ͽ� ���� �Ϸ� ó�� ù��° for������ ���� ��û���� ������ ��ȣ�� ã�� �ι�° for������ �ش� ������ ��� �ִ� ��
	 * Ȯ�� �� ���� ����/����
	 */
	@Override
	public void orderConfirm() {
		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t���� ������ �ڵ带 �Է��ϼ���. [����:0] : ");
			if (bookNo == 0) {
				return;
			}

			printInput("\t��ID�� �Է��ϼ��� : ");
			String id = Console.input();

			boolean shelfCheck = true;
			Book o = null;

			// ���� ��û���� ������ ���� �ִ� �� Ȯ��
			for (Map.Entry<Book, String> order : Order.map.entrySet()) {
				if (order.getValue().equals(id)) {
					if (order.getKey().getBookNo() == bookNo) {
						o = order.getKey(); // ���� ��û

						// ���� Ȯ�� �� ���� ����
						for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
							if (shelf.getKey().getBookNo() == bookNo) {
								int count = shelf.getKey().getBookCount() - o.getBookCount();

								if (count > 0) {
									orderConfirmOk(o, bookNo, order.getValue());
									shelf.getKey().setBookCount(count);
								} else if (count == 0) {
									orderConfirmOk(o, bookNo, order.getValue());
									Shelf.map.remove(shelf.getKey());
								} else {
									MenuImpl.print("\t������ �����մϴ�.");
									break;
								}
								shelfCheck = false;
								break;
							}
						}
						flag = false;
						break;
					}
				}
			}

			// ��� ���� ���
			if (shelfCheck) {
// ������ �ߴ� ����. �ذ��ؾ���.
//				MenuImpl.print("\t��� �����ϴ�.");
//				int input = MenuImpl.consoleTryCatch("\t���԰� �Ͻðڽ��ϱ�? [��:1, �ƴϿ�:2] : ");
//
//				switch (input) {
//				case 1:
//					Shelf shelf = new Shelf();
//					shelf.setBookNo(bookNo);
//					shelf.setBookTitle(o.getBookTitle());
//					shelf.setBookAuthor(o.getBookAuthor());
//					shelf.setBookPrice(o.getBookPrice());
//					shelf.setBookCount(o.getBookCount());
//					Shelf.getShelf().put(shelf, null);
//					MenuImpl.print("\t" + o.getBookCount() + "�� ���԰� �Ϸ�Ǿ����ϴ�.");
//					break;
//				case 2:
//					Order.getOrder().remove(o);
//					MenuImpl.print("\t���� ��û�� �źεǾ����ϴ�.");
//					break;
//				default:
					MenuImpl.print("\t�߸��� �Է��Դϴ�.");
//					break;
//				}
			}
			bookNoNull();
		}
	}

	// ȯ�� ��û�� ó��
	@Override
	public void orderRefund() {
		MenuImpl.print("======================== ȯ�� ��û ��� =======================");
		MenuImpl.print("\t��ȣ" + "\t������" + "\t����" + "\t����" + "\t���� " + "\t��ID");
		MenuImpl.print("===========================================================");

		for (Map.Entry<Book, String> refund : Refund.map.entrySet()) {
			MenuImpl.print(refund.getKey().toString() + "\t" + refund.getValue());
		}

		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\tȯ�� ó���� �ڵ带 �Է��ϼ���. [����:0] : ");
			if (bookNo == 0) {
				return;
			}

			printInput("\t��ID�� �Է��ϼ��� : ");
			String id = Console.input();

			int count = 0;

			int idCount = 0;
			// ���� ���
			for (Map.Entry<Book, String> sales : Sales.map.entrySet()) {
				if (id.equals(sales.getValue())) {
					if (sales.getKey().getBookNo() == bookNo) {
						Sales.map.remove(sales.getKey());
						break;
					}
				}
			}

			// ��� ��û ó��
			for (Map.Entry<Book, String> refund : Refund.map.entrySet()) {
				if (id.equals(refund.getValue())) {
					if (refund.getKey().getBookNo() == bookNo) {
						count = refund.getKey().getBookCount();

						// ���� ����
						boolean stockCheck = true;
						for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) { // ���� ������ �߰�
							if (shelf.getKey().getBookNo() == bookNo) {
								shelf.getKey().setBookCount(shelf.getKey().getBookCount() + count);
								stockCheck = false;
								break;
							}
						}

						if (stockCheck) {// ǰ�� ������ ��� ���԰� ó��
							Shelf shelf = new Shelf();
							shelf.setBookNo(bookNo);
							shelf.setBookTitle(refund.getKey().getBookTitle());
							shelf.setBookAuthor(refund.getKey().getBookAuthor());
							shelf.setBookPrice(refund.getKey().getBookPrice());
							shelf.setBookCount(count);
							Shelf.map.put(shelf, null);
						}

						Refund.map.remove(refund.getKey());

						MenuImpl.print("===========================================================");
						MenuImpl.print("\tȯ�� ó���� �Ϸ�Ǿ����ϴ�.");
						MenuImpl.print("===========================================================");
						flag = false;
						break;
					}
				}
			}
			bookNoNull();
		}
	}

	// ���
	@Override
	public void salesTotal() {
		int total = 0;

		for (Map.Entry<Book, String> sales : Sales.map.entrySet()) {
			total += (sales.getKey().getBookPrice() * sales.getKey().getBookCount());
		}

		MenuImpl.print("\t��� : " + total + "��");
	}

	public void orderConfirmOk(Book order, int bookNo, String id) {
		Sales sales = new Sales();
		sales.setBookNo(bookNo);
		sales.setBookTitle(order.getBookTitle());
		sales.setBookAuthor(order.getBookAuthor());
		sales.setBookPrice(order.getBookPrice());
		sales.setBookCount(order.getBookCount());
		Sales.map.put(sales, id);// ���� ��û Order >>> Sales ���� �Ϸ�
		Order.map.remove(order);

		MenuImpl.print("===========================================================");
		MenuImpl.print("\t������ ���εǾ����ϴ�.");
		MenuImpl.print("===========================================================");
	}

	// �ڵ� ��ȣ �߸� �Է½� ó��
	public void bookNoNull() {
		if (flag) {
		}
		flag = true;
	}

	public void printInput(String str) {
		Console.output().print(str);
		System.out.print(str);
	}
}
