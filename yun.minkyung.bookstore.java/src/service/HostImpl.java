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

	// 도서 목록 출력
	@Override
	public void bookList() {
		MenuImpl.print("========================== 도서 목록 =========================");
		MenuImpl.print("\t번호" + "\t도서명" + "\t저자" + "\t가격" + "\t수량");
		MenuImpl.print("===========================================================");

		for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
			MenuImpl.print(shelf.getKey().toString());
		}
	}

	// 도서 추가
	@Override
	public void bookAdd() {
		MenuImpl.print("========================== 도서 등록 =========================");

		System.out.print("\t책 제목: ");
		String bookTitle = Console.input();
		System.out.print("\t책 저자: ");
		String bookAuthor = Console.input();
		MenuImpl.prW.println("\t책 제목: " + bookTitle);
		MenuImpl.prW.println("\t책 저자: " + bookAuthor);

		int bookPrice = MenuImpl.consoleTryCatch("\t책 가격: ");
		int bookCount = MenuImpl.consoleTryCatch("\t책 수량: ");

		MenuImpl.print("===========================================================");
		if (bookCount != 0) {
			Shelf shelf = new Shelf();
			shelf.setBookTitle(bookTitle);
			shelf.setBookAuthor(bookAuthor);
			shelf.setBookPrice(bookPrice);
			shelf.setBookCount(bookCount);
			Shelf.map.put(shelf, null);
			MenuImpl.print("\t도서가 등록되었습니다.");

		} else {
			MenuImpl.print("\t도서 등록 실패 (0 입력 불가)");
		}
		MenuImpl.print("===========================================================");

	}

	// 도서 수정
	/*
	 * ConcurrentModificationException 객체의 상태를 동시에 수정하는 것을 허용하지 않을 때 발생 하나의 쓰레드가
	 * 컬렉션을 순회 중일 때 다른 하나의 쓰레드가 컬렉션을 수정하는 것을 허용하지 않는다.
	 */
	@Override
	public void bookUpdate() {
		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t수정하려는 책의 코드를 입력하세요. [이전:0] : ");
			if (bookNo == 0) {
				return;
			}

			for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
				if (shelf.getKey().getBookNo() == bookNo) {
					MenuImpl.print("========================== 도서 수정 =========================");

					System.out.print("\t책 제목: ");
					String bookTitle = Console.input();
					System.out.print("\t책 저자: ");
					String bookAuthor = Console.input();
					MenuImpl.prW.println("\t책 제목: " + bookTitle);
					MenuImpl.prW.println("\t책 저자: " + bookAuthor);

					int bookPrice = MenuImpl.consoleTryCatch("\t책 가격: ");
					int bookCount = MenuImpl.consoleTryCatch("\t책 수량: ");

					// 도서 수량을 0으로 입력하는 것 방지
					MenuImpl.print("===========================================================");
					if (bookCount != 0) {
						shelf.getKey().setBookTitle(bookTitle);
						shelf.getKey().setBookAuthor(bookAuthor);
						shelf.getKey().setBookPrice(bookPrice);
						shelf.getKey().setBookCount(bookCount);
						MenuImpl.print("\t" + bookNo + "번 도서가 수정되었습니다.");
					} else {
						MenuImpl.print("\t도서 수정 실패 (0 입력 불가)");
					}
					MenuImpl.print("===========================================================");

					flag = false;
					break; // ConcurrentModificationException 방지
				}
			}
			bookNoNull();
		}
	}

	// 도서 삭제
	@Override
	public void bookDel() {
		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t삭제하려는 책의 코드를 입력하세요. [이전:0] : ");
			if (bookNo == 0) {
				return;
			}

			for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) {
				if (shelf.getKey().getBookNo() == bookNo) {
					Shelf.map.remove(shelf.getKey());

					MenuImpl.print("========================== 도서 삭제 =========================");
					MenuImpl.print("\t" + bookNo + "번 도서가 삭제되었습니다.");
					MenuImpl.print("===========================================================");

					flag = false;
					break;
				}
			}
			bookNoNull();
		}
	}

	// Guest가 구매 요청한 목록 출력
	@Override
	public void orderList() {
		MenuImpl.print("======================== 구매 요청 목록 =======================");
		MenuImpl.print("\t번호" + "\t도서명" + "\t저자" + "\t가격" + "\t수량" + "\t고객ID");
		MenuImpl.print("===========================================================");

		for (Map.Entry<Book, String> order : Order.map.entrySet()) {
			MenuImpl.print(order.getKey().toString() + "\t" + order.getValue());
		}
	}

	/*
	 * 구매 요청을 승인하여 결제 완료 처리 첫번째 for문에서 구매 요청받은 도서의 번호를 찾아 두번째 for문에서 해당 도서의 재고가 있는 지
	 * 확인 후 결제 승인/거절
	 */
	@Override
	public void orderConfirm() {
		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t구매 승인할 코드를 입력하세요. [이전:0] : ");
			if (bookNo == 0) {
				return;
			}

			printInput("\t고객ID를 입력하세요 : ");
			String id = Console.input();

			boolean shelfCheck = true;
			Book o = null;

			// 구매 요청받은 도서를 갖고 있는 지 확인
			for (Map.Entry<Book, String> order : Order.map.entrySet()) {
				if (order.getValue().equals(id)) {
					if (order.getKey().getBookNo() == bookNo) {
						o = order.getKey(); // 구매 요청

						// 수량 확인 후 구매 승인
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
									MenuImpl.print("\t수량이 부족합니다.");
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

			// 재고가 없을 경우
			if (shelfCheck) {
// 무조건 뜨는 현상. 해결해야함.
//				MenuImpl.print("\t재고가 없습니다.");
//				int input = MenuImpl.consoleTryCatch("\t재입고 하시겠습니까? [예:1, 아니오:2] : ");
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
//					MenuImpl.print("\t" + o.getBookCount() + "권 재입고 완료되었습니다.");
//					break;
//				case 2:
//					Order.getOrder().remove(o);
//					MenuImpl.print("\t구매 요청이 거부되었습니다.");
//					break;
//				default:
					MenuImpl.print("\t잘못된 입력입니다.");
//					break;
//				}
			}
			bookNoNull();
		}
	}

	// 환불 요청건 처리
	@Override
	public void orderRefund() {
		MenuImpl.print("======================== 환불 요청 목록 =======================");
		MenuImpl.print("\t번호" + "\t도서명" + "\t저자" + "\t가격" + "\t수량 " + "\t고객ID");
		MenuImpl.print("===========================================================");

		for (Map.Entry<Book, String> refund : Refund.map.entrySet()) {
			MenuImpl.print(refund.getKey().toString() + "\t" + refund.getValue());
		}

		while (true) {
			int bookNo = MenuImpl.consoleTryCatch("\t환불 처리할 코드를 입력하세요. [이전:0] : ");
			if (bookNo == 0) {
				return;
			}

			printInput("\t고객ID를 입력하세요 : ");
			String id = Console.input();

			int count = 0;

			int idCount = 0;
			// 결제 취소
			for (Map.Entry<Book, String> sales : Sales.map.entrySet()) {
				if (id.equals(sales.getValue())) {
					if (sales.getKey().getBookNo() == bookNo) {
						Sales.map.remove(sales.getKey());
						break;
					}
				}
			}

			// 취소 신청 처리
			for (Map.Entry<Book, String> refund : Refund.map.entrySet()) {
				if (id.equals(refund.getValue())) {
					if (refund.getKey().getBookNo() == bookNo) {
						count = refund.getKey().getBookCount();

						// 수량 복구
						boolean stockCheck = true;
						for (Map.Entry<Book, String> shelf : Shelf.map.entrySet()) { // 기존 수량에 추가
							if (shelf.getKey().getBookNo() == bookNo) {
								shelf.getKey().setBookCount(shelf.getKey().getBookCount() + count);
								stockCheck = false;
								break;
							}
						}

						if (stockCheck) {// 품절 상태일 경우 재입고 처리
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
						MenuImpl.print("\t환불 처리가 완료되었습니다.");
						MenuImpl.print("===========================================================");
						flag = false;
						break;
					}
				}
			}
			bookNoNull();
		}
	}

	// 결산
	@Override
	public void salesTotal() {
		int total = 0;

		for (Map.Entry<Book, String> sales : Sales.map.entrySet()) {
			total += (sales.getKey().getBookPrice() * sales.getKey().getBookCount());
		}

		MenuImpl.print("\t결산 : " + total + "원");
	}

	public void orderConfirmOk(Book order, int bookNo, String id) {
		Sales sales = new Sales();
		sales.setBookNo(bookNo);
		sales.setBookTitle(order.getBookTitle());
		sales.setBookAuthor(order.getBookAuthor());
		sales.setBookPrice(order.getBookPrice());
		sales.setBookCount(order.getBookCount());
		Sales.map.put(sales, id);// 구매 요청 Order >>> Sales 결제 완료
		Order.map.remove(order);

		MenuImpl.print("===========================================================");
		MenuImpl.print("\t결제가 승인되었습니다.");
		MenuImpl.print("===========================================================");
	}

	// 코드 번호 잘못 입력시 처리
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
