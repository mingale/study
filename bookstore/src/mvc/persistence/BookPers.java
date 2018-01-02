package mvc.persistence;

import java.util.ArrayList;
import java.util.Map;

import mvc.vo.Book;
import mvc.vo.BookSub;
import mvc.vo.RecentBook;

public interface BookPers {
/*
 * 도서 목록 : 순서 필요 > ArrayList
 * 주문 목록 : 순서 불필요? > Map
 */
	//도서 검색
	public ArrayList<Book> bookSearch(String search, int start, int end);
	
	//도서 검색 결과수
	public int bookSearchCount(String search);
	
	//모든 도서 목록
	public ArrayList<Book> getAllBook();
	
	//해당 도서 정보
	public Book getBookInfo(String no);
	
	//해당 도서 부가 정보
	public BookSub getBookSubInfo(String no);

	//해당 도서 조회수 증가
	public int bookViewsUpdate(String no);
	
	//최신 도서 목록
	public ArrayList<Book> getNewBook(int start, int end);
	
	//베스트셀러(인기) 도서 목록 : 판매량 기준 > 조회수 기준
	public ArrayList<Book> getBestBook(int start, int end);
	
	//추천 도서 목록
	public ArrayList<Book> getGoodTagBook(int start, int end);
	
	//tag_main 수
	public int getTag_mainCount();
	
	//tag_main 목록
	public ArrayList<BookSub> getTag_mainList();
	
	//tag_main 도서 목록
	public ArrayList<Book> getTagMainBook(String tagMain, int start, int end);
	
	//국내 도서
	public ArrayList<Book> getDomesticBook(int start, int end);
	
	//도서 목록
	public ArrayList<Book> getBookList(int start, int end);
	
	//도서 부가 정보 목록
	public ArrayList<BookSub> getBookSubList(int start, int end);
	
	//베스트(인기) 도서 개수
	public int getBestCount();
	
	//신간 도서 개수
	public int getNewCount();
	
	//추천 도서 개수
	public int getGoodCount();
	
	//tag_main 도서 개수
	public int getTagMainCount(String tag);

	//도서 개수
	public int getBookCnt();
	
	//도서 수정
	public int bookUpdate(Book book);
	
	//주문 결제 완료 시 도서 수량 수정
	public int orderBookCountMul(String order_num);
	
	//환불 완료 시 도서 수량 수정
	public int refundBookCountAdd(String order_num);
	
	//도서 삭제
	public int bookDelete(String no);
	
	//도서 다수 삭제
	public int bookAllDelete(String[] nos);
	
	//도서 추가
	public int bookInsert(Book book);
	
	//도서 부가 정보 수정
	public int bookSubUpdate(BookSub bSub);
	
	//도서 부가 정보 추가
	public int bookSubInsert(BookSub bSub);

	//최근 본 도서 수정
	public ArrayList<RecentBook> recentUpdate(ArrayList<RecentBook> list, Book b);

	//no, title
	public Map<String, String> getNoTitle();
	
	//총 도서 판매량
	public int getSalesTotalCount();

	//총 매출액
	public int getSalesTotalAmount();

	//태그별 판매량
	public Map<String, Integer> getTagSalesTotal();
	
	//태그별 환불량
	public Map<String, Integer> getTagRefundTotal();
	
	//연간 매출액
	public Map<String, Integer> getYearSalesTotalAmount();
	
	//연간 판매량
	public Map<String, Integer> getYearSalesTotalCount();
	
	//연간 환불액
	public Map<String, Integer> getYearRefundTotalAmount();
	
	//주간 태그별 판매량
	public Map<String, Integer> getTagWeeklyCount();
}
