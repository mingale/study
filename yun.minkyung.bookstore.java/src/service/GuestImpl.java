package service;

import java.util.Map;

import domain.Book;
import domain.Cart;
import domain.Order;
import domain.Refund;
import domain.Sales;
import domain.Shelf;
import presentation.MenuImpl;

public class GuestImpl implements Guest {
	public String id;
	boolean flag = true;

	private static GuestImpl guest = new GuestImpl();

	private GuestImpl() {
	}

	public static GuestImpl getInstance() {
		return guest;
	}

	// ��ٱ��� ���
	@Override
	public void cartList() {
		for (Map.Entry<Book, String> cart : Cart.map.entrySet()) {
			if (cart.getValue().equals(id)) {
				MenuImpl.print(cart.getKey().toString());
			}
		}
	}

	// ��ٱ��Ͽ� ���
	@Override
	public void cartAdd() {
		tmpBookList();

		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t��ٱ��Ͽ� ���� å�� �ڵ带 �Է��ϼ���. [����:0] : ");
			if (bookNo == 0) {
				return;
			}

			for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
				if (shelf.getKey().getBookNo() == bookNo) {
					int count = MenuImpl.consoleTryCatch("\t������ �Է��ϼ���. : ");
					if (count == 0) {
						flag = false;
						MenuImpl.print("\t������ 1 �̻� �Է����ּ���.");
						break;
					}

					int bookCount = shelf.getKey().getBookCount() - count;

					if (bookCount > 0) {
						CartCheckAdd(shelf.getKey(), count);
					} else if (bookCount == 0) {
						CartCheckAdd(shelf.getKey(), count);
					} else {
						MenuImpl.print("\t������ �����մϴ�.");
					}
					flag = false;
					break;
				}
			}
			bookNoNull();
		}
	}

	// ��ٱ��Ͽ��� ����
	@Override
	public void cartDel() {
		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t�����Ϸ��� å�� �ڵ带 �Է��ϼ���. [����:0] : ");
			if (bookNo == 0) {
				return;
			}

			int count = 0;
			for (Map.Entry<Book, String> cart : Cart.map.entrySet()) {
				if (cart.getValue().equals(id)) {
					if (cart.getKey().getBookNo() == bookNo) {
						Cart.map.remove(cart.getKey());

						flag = false;
						printLine("\t" + bookNo + "�� ������ ��Ͽ��� ���� �Ǿ����ϴ�.");
						break;
					}
				}
			}
			bookNoNull();
		}
	}

	// ��ٱ��� ����
	@Override
	public void cartBuy() {
		while (true) {
			boolean orderNullCheck = true;
			int stockCount = 0;
			Book getOrder = null;

			int bookNo = MenuImpl.consoleTryCatch("\t������ å�� �ڵ带 �Է��ϼ���. [����:0] : ");
			if (bookNo == 0) {
				return;
			}

			Book getCart = null;
			// ��ٱ��Ͽ� ������ å�� ������
			for (Map.Entry<Book, String> cart : Cart.map.entrySet()) {
				if (cart.getValue().equals(id)) {
					if (cart.getKey().getBookNo() == bookNo) {
						getCart = cart.getKey();

						// �̹� ���� ��û�� ���� �ֳ� Ȯ��
						for (Map.Entry<Book, String> order : Order.map.entrySet()) {
							if (order.getValue().equals(id)) {
								if (order.getKey().getBookNo() == bookNo) {
									getOrder = order.getKey();
									orderNullCheck = false;
									break;
								}
							}
						}

						// ��� ���� Ȯ��
						for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
							if (shelf.getKey().getBookNo() == bookNo) {
								stockCount = shelf.getKey().getBookCount();
								break;
							}
						}
						flag = false;
						break;
					}
				}
			}

			// ��� ������ return
			if (stockCount == 0) {
				Cart.map.remove(getCart);
				printLine("\t" + bookNo + "�� ������ �������� �ʽ��ϴ�.");
				break;
			}

			// �����Ϸ��� ���� + �̹� ���� ��û�� ����
			Cart cart = (Cart) getCart;
			int totalCount = 0; // �� �ֹ� ����
			if (getOrder == null) {
				totalCount = cart.getBookCount();
			} else {
				totalCount = getOrder.getBookCount() + cart.getBookCount();
			}

			// ��� ����
			if (orderNullCheck) { // ���� ��û�� �׸�� �ߺ����� ����
				if (totalCount <= stockCount) {
					Order order = new Order();
					order.setBookNo(cart.getBookNo());
					order.setBookTitle(cart.getBookTitle());
					order.setBookAuthor(cart.getBookAuthor());
					order.setBookPrice(cart.getBookPrice());
					order.setBookCount(cart.getBookCount());
					Order.map.put(order, id);

					Cart.map.remove(getCart);
					printLine("\t���� ��û �Ǿ����ϴ�.");
				} else {
					printLine("\t������ �� �ִ� ������ �Ѿ���ϴ�.");
				}
			} else { // ���� ��û�� �׸�� �ߺ���
				if (totalCount <= stockCount) {
					getOrder.setBookCount(totalCount);

					Cart.map.remove(getCart);
					printLine("\t���� ��û �Ǿ����ϴ�.");
				} else {
					printLine("\t������ �� �ִ� ������ �Ѿ���ϴ�.");
				}
			}
			bookNoNull();
		}
	}

	// ���� ��û�� ���
	@Override
	public void buyList() {
		for (Map.Entry<Book, String> order : Order.map.entrySet()) {
			if (order.getValue().equals(id)) {
				MenuImpl.print(order.getKey().toString());
			}
		}
	}

	// �ٷ� �����ϱ�
	@Override
	public void nowBuy() {
		tmpBookList();

		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t������ å�� �ڵ带 �Է��ϼ���. [����:0] : ");
			if (bookNo == 0) {
				return;
			}

			for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
				if (shelf.getKey().getBookNo() == bookNo) {
					int count = MenuImpl.consoleTryCatch("\t������ �Է��ϼ��� : ");
					if (count == 0) {
						flag = false;
						MenuImpl.print("\t������ 1 �̻� �Է����ּ���.");
						break;
					}

					// ��� �ľ� �� ���� ��û
					int bookCount = shelf.getKey().getBookCount() - count;
					if (bookCount > 0) {
						nowBuyOrderCheckAdd(shelf.getKey(), count, bookNo);
					} else if (bookCount == 0) {
						nowBuyOrderCheckAdd(shelf.getKey(), count, bookNo);
					} else {
						MenuImpl.print("\t������ �����մϴ�.");
					}

					flag = false;
					break;
				}
			}
			bookNoNull();
		}
	}

	// Ȯ�� ��û
	@Override
	public void refund() {
		MenuImpl.print("========================== ���� ��� =========================");
		MenuImpl.print("\t��ȣ" + "\t������" + "\t����" + "\t����" + "\t����");
		MenuImpl.print("===========================================================");

		// ���� �Ϸ�
		for (Map.Entry<Book, String> sales : Sales.map.entrySet()) {
			if (sales.getValue().equals(id)) {
				MenuImpl.print(sales.getKey().toString());
			}
		}

		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\tȯ�� ��û�� å�� �ڵ带 �Է��ϼ���. [����:0] : ");
			if (bookNo == 0) {
				return;
			}

			for (Map.Entry<Book, String> sales : Sales.map.entrySet()) {
				if (sales.getValue().equals(id)) {
					if (sales.getKey().getBookNo() == bookNo) {
						Refund refund = new Refund();
						refund.setBookNo(bookNo);
						refund.setBookTitle(sales.getKey().getBookTitle());
						refund.setBookAuthor(sales.getKey().getBookAuthor());
						refund.setBookPrice(sales.getKey().getBookPrice());
						refund.setBookCount(sales.getKey().getBookCount());

						Refund.map.put(refund, id);
						printLine("\tȯ�� ��û �Ǿ����ϴ�.");

						flag = false;
						break;
					}
				}
			}
			bookNoNull();
		}
	}

	public void printLine(String s) {
		MenuImpl.print("===========================================================");
		MenuImpl.print("\t" + s);
		MenuImpl.print("===========================================================");
	}

	// ��ٱ��� �ߺ�
	public void CartCheckAdd(Book shelf, int count) {
		boolean newCheck = true;
		int bookNo = shelf.getBookNo();

		// ��ٱ��� �ߺ� Ȯ��
		for (Map.Entry<Book, String> cart : Cart.map.entrySet()) {
			if (cart.getValue().equals(id)) {
				if (cart.getKey().getBookNo() == bookNo) {
					int cartCount = cart.getKey().getBookCount() + count;
					if (cartCount <= shelf.getBookCount()) { // ��� Ȯ��
						cart.getKey().setBookCount(cartCount);
						printLine("\t��ٱ��Ͽ� �����ϴ�.");
					} else {
						printLine("\t������ �� �ִ� ������ �Ѿ���ϴ�.");
					}
					newCheck = false;
					break;
				}
			}
		}

		// �ߺ� �ƴϸ� ���� �߰�
		if (newCheck) {
			Cart cart = new Cart();
			cart.setBookNo(bookNo);
			cart.setBookTitle(shelf.getBookTitle());
			cart.setBookAuthor(shelf.getBookAuthor());
			cart.setBookPrice(shelf.getBookPrice());
			cart.setBookCount(count);
			Cart.map.put(cart, id);
			printLine("\t��ٱ��Ͽ� �����ϴ�.");
		}

	}

	// �ٷ� ������ �� �̹� �ֹ��� ���� �ֳ� Ȯ��
	public void nowBuyOrderCheckAdd(Book shelf, int count, int bookNo) {
		boolean newCheck = true;

		// �ֹ��� �� ������ �ֹ��� ��ġ��
		for (Map.Entry<Book, String> order : Order.map.entrySet()) {
			if (order.getValue().equals(id)) {
				if (order.getKey().getBookNo() == bookNo) {
					int orderTotalCount = order.getKey().getBookCount() + count;
					if (orderTotalCount <= shelf.getBookCount()) {
						order.getKey().setBookCount(orderTotalCount);
						printLine("\t���� ��û �Ǿ����ϴ�.");
					} else {
						printLine("\t������ �� �ִ� ������ �Ѿ���ϴ�.");
					}
					newCheck = false;
				}
			}
		}

		// �ֹ��� �� ������ ���� �ֹ��ϱ�
		if (newCheck) {
			Order order = new Order();
			order.setBookNo(bookNo);
			order.setBookTitle(shelf.getBookTitle());
			order.setBookAuthor(shelf.getBookAuthor());
			order.setBookPrice(shelf.getBookPrice());
			order.setBookCount(count);
			Order.map.put(order, id);
			printLine("\t���� ��û �Ǿ����ϴ�.");
		}
	}

	// ���� ������ ���� ���
	public void tmpBookList() {
		MenuImpl.print("========================== ���� ��� =========================");
		MenuImpl.print("\t��ȣ" + "\t������" + "\t����" + "\t����" + "\t����");
		MenuImpl.print("===========================================================");

		for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
			int bookNo = shelf.getKey().getBookNo();
			int totalCount = shelf.getKey().getBookCount();

			// ���� �̵� �ľ�
			// ��Ͽ� ǥ���� ���� = ��� - ��ٱ��Ͽ� ���� ���� - �ֹ��ص� ���� - ���� �Ϸ�� ����
			for (Map.Entry<Book, String> cart : Cart.map.entrySet()) {
				if (bookNo == cart.getKey().getBookNo()) {
					totalCount -= cart.getKey().getBookCount();
					break;
				}
			}
			for (Map.Entry<Book, String> order : Order.map.entrySet()) {
				if (bookNo == order.getKey().getBookNo()) {
					totalCount -= order.getKey().getBookCount();
				}
			}

			// '��ٱ��Ͽ� ���� ���� + �ֹ��ص� ���� > ���' �� ���� ��Ͽ� ǥ��
			if(totalCount > 0) {
				MenuImpl.print("\t" + bookNo + "\t" + shelf.getKey().getBookTitle() + "\t" + shelf.getKey().getBookAuthor()
						+ "\t" + shelf.getKey().getBookPrice() + "\t" + totalCount);	
			}
			
		}
	}

	public void bookNoNull() {
		if (flag) {
			MenuImpl.print("\t�������� �ʴ� �ڵ��Դϴ�.");
		}
		flag = true;
	}
}
