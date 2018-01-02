package domain;

public class Book {

	private int bookNo = (int) (Math.random() * 2000 + 1000); // 책 번호. 랜덤으로 받음
	private String bookTitle;
	private String bookAuthor;
	private int bookPrice;
	private int bookCount;

	public Book() {
	}

	public Book(String bookTitle, String bookAuthor, int bookPrice, int bookCount) {
		this.bookTitle = bookTitle;
		this.bookAuthor = bookAuthor;
		this.bookPrice = bookPrice;
		this.bookCount = bookCount;
	}
	
	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}
	
	public int getBookNo() {
		return bookNo;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}

	public int getBookPrice() {
		return bookPrice;
	}

	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

	public int getBookCount() {
		return bookCount;
	}

	@Override
	public String toString() {
		return "\t" + bookNo + "\t" + bookTitle + "\t" + bookAuthor + "\t" + bookPrice + "\t" + bookCount;
	}
}