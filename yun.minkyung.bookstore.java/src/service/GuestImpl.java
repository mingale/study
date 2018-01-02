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

	// 장바구니 목록
	@Override
	public void cartList() {
		for (Map.Entry<Book, String> cart : Cart.map.entrySet()) {
			if (cart.getValue().equals(id)) {
				MenuImpl.print(cart.getKey().toString());
			}
		}
	}

	// 장바구니에 담기
	@Override
	public void cartAdd() {
		tmpBookList();

		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t장바구니에 담을 책의 코드를 입력하세요. [이전:0] : ");
			if (bookNo == 0) {
				return;
			}

			for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
				if (shelf.getKey().getBookNo() == bookNo) {
					int count = MenuImpl.consoleTryCatch("\t수량을 입력하세요. : ");
					if (count == 0) {
						flag = false;
						MenuImpl.print("\t수량을 1 이상 입력해주세요.");
						break;
					}

					int bookCount = shelf.getKey().getBookCount() - count;

					if (bookCount > 0) {
						CartCheckAdd(shelf.getKey(), count);
					} else if (bookCount == 0) {
						CartCheckAdd(shelf.getKey(), count);
					} else {
						MenuImpl.print("\t수량이 부족합니다.");
					}
					flag = false;
					break;
				}
			}
			bookNoNull();
		}
	}

	// 장바구니에서 삭제
	@Override
	public void cartDel() {
		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t삭제하려는 책의 코드를 입력하세요. [이전:0] : ");
			if (bookNo == 0) {
				return;
			}

			int count = 0;
			for (Map.Entry<Book, String> cart : Cart.map.entrySet()) {
				if (cart.getValue().equals(id)) {
					if (cart.getKey().getBookNo() == bookNo) {
						Cart.map.remove(cart.getKey());

						flag = false;
						printLine("\t" + bookNo + "번 도서가 목록에서 삭제 되었습니다.");
						break;
					}
				}
			}
			bookNoNull();
		}
	}

	// 장바구니 구매
	@Override
	public void cartBuy() {
		while (true) {
			boolean orderNullCheck = true;
			int stockCount = 0;
			Book getOrder = null;

			int bookNo = MenuImpl.consoleTryCatch("\t구매할 책의 코드를 입력하세요. [이전:0] : ");
			if (bookNo == 0) {
				return;
			}

			Book getCart = null;
			// 장바구니에 구매할 책이 있으면
			for (Map.Entry<Book, String> cart : Cart.map.entrySet()) {
				if (cart.getValue().equals(id)) {
					if (cart.getKey().getBookNo() == bookNo) {
						getCart = cart.getKey();

						// 이미 구매 요청한 적이 있나 확인
						for (Map.Entry<Book, String> order : Order.map.entrySet()) {
							if (order.getValue().equals(id)) {
								if (order.getKey().getBookNo() == bookNo) {
									getOrder = order.getKey();
									orderNullCheck = false;
									break;
								}
							}
						}

						// 재고 수량 확인
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

			// 재고 없으면 return
			if (stockCount == 0) {
				Cart.map.remove(getCart);
				printLine("\t" + bookNo + "번 도서가 존재하지 않습니다.");
				break;
			}

			// 구매하려는 수량 + 이미 구매 요청한 수량
			Cart cart = (Cart) getCart;
			int totalCount = 0; // 총 주문 수량
			if (getOrder == null) {
				totalCount = cart.getBookCount();
			} else {
				totalCount = getOrder.getBookCount() + cart.getBookCount();
			}

			// 재고 있음
			if (orderNullCheck) { // 구매 요청한 항목과 중복되지 않음
				if (totalCount <= stockCount) {
					Order order = new Order();
					order.setBookNo(cart.getBookNo());
					order.setBookTitle(cart.getBookTitle());
					order.setBookAuthor(cart.getBookAuthor());
					order.setBookPrice(cart.getBookPrice());
					order.setBookCount(cart.getBookCount());
					Order.map.put(order, id);

					Cart.map.remove(getCart);
					printLine("\t구매 요청 되었습니다.");
				} else {
					printLine("\t구매할 수 있는 수량을 넘어섰습니다.");
				}
			} else { // 구매 요청한 항목과 중복됨
				if (totalCount <= stockCount) {
					getOrder.setBookCount(totalCount);

					Cart.map.remove(getCart);
					printLine("\t구매 요청 되었습니다.");
				} else {
					printLine("\t구매할 수 있는 수량을 넘어섰습니다.");
				}
			}
			bookNoNull();
		}
	}

	// 구매 요청한 목록
	@Override
	public void buyList() {
		for (Map.Entry<Book, String> order : Order.map.entrySet()) {
			if (order.getValue().equals(id)) {
				MenuImpl.print(order.getKey().toString());
			}
		}
	}

	// 바로 구매하기
	@Override
	public void nowBuy() {
		tmpBookList();

		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t구매할 책의 코드를 입력하세요. [이전:0] : ");
			if (bookNo == 0) {
				return;
			}

			for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
				if (shelf.getKey().getBookNo() == bookNo) {
					int count = MenuImpl.consoleTryCatch("\t수량을 입력하세요 : ");
					if (count == 0) {
						flag = false;
						MenuImpl.print("\t수량을 1 이상 입력해주세요.");
						break;
					}

					// 재고 파악 후 구매 요청
					int bookCount = shelf.getKey().getBookCount() - count;
					if (bookCount > 0) {
						nowBuyOrderCheckAdd(shelf.getKey(), count, bookNo);
					} else if (bookCount == 0) {
						nowBuyOrderCheckAdd(shelf.getKey(), count, bookNo);
					} else {
						MenuImpl.print("\t수량이 부족합니다.");
					}

					flag = false;
					break;
				}
			}
			bookNoNull();
		}
	}

	// 확불 요청
	@Override
	public void refund() {
		MenuImpl.print("========================== 도서 목록 =========================");
		MenuImpl.print("\t번호" + "\t도서명" + "\t저자" + "\t가격" + "\t수량");
		MenuImpl.print("===========================================================");

		// 결제 완료
		for (Map.Entry<Book, String> sales : Sales.map.entrySet()) {
			if (sales.getValue().equals(id)) {
				MenuImpl.print(sales.getKey().toString());
			}
		}

		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t환불 요청할 책의 코드를 입력하세요. [이전:0] : ");
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
						printLine("\t환불 요청 되었습니다.");

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

	// 장바구니 중복
	public void CartCheckAdd(Book shelf, int count) {
		boolean newCheck = true;
		int bookNo = shelf.getBookNo();

		// 장바구니 중복 확인
		for (Map.Entry<Book, String> cart : Cart.map.entrySet()) {
			if (cart.getValue().equals(id)) {
				if (cart.getKey().getBookNo() == bookNo) {
					int cartCount = cart.getKey().getBookCount() + count;
					if (cartCount <= shelf.getBookCount()) { // 재고 확인
						cart.getKey().setBookCount(cartCount);
						printLine("\t장바구니에 담겼습니다.");
					} else {
						printLine("\t구매할 수 있는 수량을 넘어섰습니다.");
					}
					newCheck = false;
					break;
				}
			}
		}

		// 중복 아니면 새로 추가
		if (newCheck) {
			Cart cart = new Cart();
			cart.setBookNo(bookNo);
			cart.setBookTitle(shelf.getBookTitle());
			cart.setBookAuthor(shelf.getBookAuthor());
			cart.setBookPrice(shelf.getBookPrice());
			cart.setBookCount(count);
			Cart.map.put(cart, id);
			printLine("\t장바구니에 담겼습니다.");
		}

	}

	// 바로 구매할 때 이미 주문한 적이 있나 확인
	public void nowBuyOrderCheckAdd(Book shelf, int count, int bookNo) {
		boolean newCheck = true;

		// 주문한 적 있으면 주문량 합치기
		for (Map.Entry<Book, String> order : Order.map.entrySet()) {
			if (order.getValue().equals(id)) {
				if (order.getKey().getBookNo() == bookNo) {
					int orderTotalCount = order.getKey().getBookCount() + count;
					if (orderTotalCount <= shelf.getBookCount()) {
						order.getKey().setBookCount(orderTotalCount);
						printLine("\t구매 요청 되었습니다.");
					} else {
						printLine("\t구매할 수 있는 수량을 넘어섰습니다.");
					}
					newCheck = false;
				}
			}
		}

		// 주문한 적 없으면 새로 주문하기
		if (newCheck) {
			Order order = new Order();
			order.setBookNo(bookNo);
			order.setBookTitle(shelf.getBookTitle());
			order.setBookAuthor(shelf.getBookAuthor());
			order.setBookPrice(shelf.getBookPrice());
			order.setBookCount(count);
			Order.map.put(order, id);
			printLine("\t구매 요청 되었습니다.");
		}
	}

	// 구매 가능한 도서 목록
	public void tmpBookList() {
		MenuImpl.print("========================== 도서 목록 =========================");
		MenuImpl.print("\t번호" + "\t도서명" + "\t저자" + "\t가격" + "\t수량");
		MenuImpl.print("===========================================================");

		for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
			int bookNo = shelf.getKey().getBookNo();
			int totalCount = shelf.getKey().getBookCount();

			// 수량 이동 파악
			// 목록에 표시할 수량 = 재고량 - 장바구니에 담은 수량 - 주문해둔 수량 - 결제 완료된 수량
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

			// '장바구니에 담은 수량 + 주문해둔 수량 > 재고' 일 때만 목록에 표시
			if(totalCount > 0) {
				MenuImpl.print("\t" + bookNo + "\t" + shelf.getKey().getBookTitle() + "\t" + shelf.getKey().getBookAuthor()
						+ "\t" + shelf.getKey().getBookPrice() + "\t" + totalCount);	
			}
			
		}
	}

	public void bookNoNull() {
		if (flag) {
			MenuImpl.print("\t존재하지 않는 코드입니다.");
		}
		flag = true;
	}
}
