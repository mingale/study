package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Book;
import spring.mvc.bookstore.vo.BookSub;
import spring.mvc.bookstore.vo.Notice;
import spring.mvc.bookstore.vo.RecentBook;
import spring.mvc.bookstore.vo.StringInt;

@Repository
public class BookPersImpl implements BookPers {
	@Autowired
	private SqlSession sqlSession;

	/*
	 * private BookPers mapper = sqlSession.getMapper(BookPers.class); Error 발생:
	 * java.lang.NullPointerException 원인: Mapper를 멤버변수에서 선언하면 Bean 생성이 되지 않아 사용 불가능
	 * Type Exception Report Message Servlet.init() for servlet [appServlet] threw
	 * exception Description The server encountered an unexpected condition that
	 * prevented it from fulfilling the request. Exception
	 * javax.servlet.ServletException: Servlet.init() for servlet [appServlet] threw
	 * exception
	 * org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.
	 * java:478)
	 * 
	 * ...
	 * 
	 * 12월 28, 2017 11:58:58 오전 org.apache.catalina.core.StandardContext
	 * loadOnStartup 심각: Servlet [appServlet] in web application [/bookstore] threw
	 * load() exception java.lang.NullPointerException at
	 * spring.mvc.bookstore.persistence.BookPersImpl.<init>(BookPersImpl.java:33)
	 */

	// 도서 검색
	public ArrayList<Book> bookSearch(Map<String, Object> map) {
		System.out.println("BookPersImple START - bookSearch");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.bookSearch(map);

		System.out.println("BookPersImple END - bookSearch");
		return list;
	}

	// 도서 검색 결과수
	public int bookSearchCount(String keyword) {
		System.out.println("BookPersImple START - bookSearchCount");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookSearchCount(keyword);

		System.out.println("BookPersImple END - bookSearchCount");
		return cnt;
	}

	// 해당 도서 조회수 증가
	public int bookViewsUpdate(String no) {
		System.out.println("BookPersImple START - bookViewsUpdate");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookViewsUpdate(no);

		System.out.println("BookPersImple END - bookViewsUpdate");
		return cnt;
	}

	// 모든 도서 목록
	public ArrayList<Book> getAllBook() {
		System.out.println("BookPersImple START - getAllBook");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getAllBook();

		System.out.println("BookPersImple END - getAllBook");
		return list;
	}

	/*
	 * Error [java.sql.SQLException: 전방향 전용 결과 집합에 부적합한 작업이 수행되었습니다.] 출처:
	 * http://hanuli7.tistory.com/entry/javasqlSQLException-전방향-전용-결과-집합에-부적합한-작업이-
	 * 수행되었습니다-first [잡.학.창.고.] ResultSet은 기본적으로 후방향 탐색만 가능하도록 만들어진 객체 정적 필드 두 개를
	 * 넘겨주면 임의로 전, 후방향 탐색을 할 수 있다.
	 */

	// 도서 정보
	@Override
	public Book getBookInfo(String no) {
		System.out.println("BookPersImple START - getBookInfo");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		Book b = mapper.getBookInfo(no);
		String image = b.getImage();
		if (image == null)
			b.setImage("000.jpg");

		String intro = b.getBookSub().getIntro();
		String list = b.getBookSub().getList();
		String pub_intro = b.getBookSub().getPub_intro();
		String review = b.getBookSub().getReview();

		if (intro == null)
			b.getBookSub().setIntro("준비 중입니다.");
		if (list == null)
			b.getBookSub().setList("준비 중입니다.");
		if (pub_intro == null)
			b.getBookSub().setPub_intro("준비 중입니다.");
		if (review == null)
			b.getBookSub().setReview("준비 중입니다.");

		System.out.println("BookPersImpl END - getBookInfo");
		return b;
	}

	// 신간 도서 목록
	@Override
	public ArrayList<Book> getNewBook(Map<String, Object> map) {
		System.out.println("BookPersImple START - getNewBook");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getNewBook(map);
		System.out.println("BookPersImple END - getNewBook");
		return list;
	}

	// 베스트셀러(인기) 도서 목록
	@Override
	public ArrayList<Book> getBestBook(Map<String, Object> map) {
		System.out.println("BookPersImple START - getBestBook");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getBestBook(map);
		System.out.println("BookPersImple END - getBestBook");
		return list;
	}

	// 추천 도서 목록
	@Override
	public ArrayList<Book> getGoodTagBook(Map<String, Object> map) {
		System.out.println("BookPersImple START - getGoodTagBook");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getGoodTagBook(map);
		System.out.println("BookPersImple END - getGoodTagBook");
		return list;
	}

	// 도서 목록
	@Override
	public ArrayList<Book> getBookList(Map<String, Object> map) {
		System.out.println("BookPersImple START - getBookList");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> books = mapper.getBookList(map);

		System.out.println("BookPersImple END - getBookList");
		return books;
	}

	// 도서 부가 정보 목록
	/*
	 * @Override public ArrayList<BookSub> getBookSubList(Map<String, Object> map) {
	 * System.out.println("BookPersImple START - getBookSubList"); BookPers mapper =
	 * sqlSession.getMapper(BookPers.class); ArrayList<BookSub> bSubs =
	 * dao.getBookSubList(map);
	 * System.out.println("BookPersImple END - getBookSubList"); return bSubs; }
	 */

	// tag_main 수
	public int getTag_mainCount() {
		System.out.println("BookPersImple START - getTag_mainCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getTag_mainCount();
		System.out.println("BookPersImple END - getTag_mainCount");
		return cnt;
	}

	// 메인태그 목록
	@Override
	public ArrayList<BookSub> getTag_mainList() {
		System.out.println("BookPersImple START - getTag_mainList");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<BookSub> tags = mapper.getTag_mainList();
		System.out.println("BookPersImple END - getTag_mainList");
		return tags;
	}

	// tag 목록
	/*
	 * @Override public ArrayList<String> getTagList() { ArrayList<String> tags =
	 * new ArrayList<>();
	 * 
	 * Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
	 * 
	 * try { conn = datasource.getConnection();
	 * 
	 * String sql = "SELECT * FROM tag"; ps = conn.prepareStatement(sql);
	 * 
	 * rs = ps.executeQuery(); while(rs.next()) { tags.add(rs.getString(1)); } }
	 * catch(SQLException e) { e.printStackTrace();
	 * System.out.println("BookPersImpl - getBookTag() 실패"); } finally { try { if(rs
	 * != null) rs.close(); if(ps != null) ps.close(); if(conn != null)
	 * conn.close(); } catch(SQLException e) { e.printStackTrace(); } }
	 * 
	 * return tags; }
	 */

	// 베스트(인기) 도서 개수
	@Override
	public int getBestCount() {
		System.out.println("BookPersImple START - getBestCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getBestCount();
		System.out.println("BookPersImple END - getBestCount");
		return cnt;
	}

	// 신간 도서 개수
	@Override
	public int getNewCount() {
		System.out.println("BookPersImple START - getNewCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getNewCount();
		System.out.println("BookPersImple END - getNewCount");
		return cnt;
	}

	// 추천 도서 개수
	@Override
	public int getGoodCount() {
		System.out.println("BookPersImple START - getGoodCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getGoodCount();
		System.out.println("BookPersImple END - getGoodCount");
		return cnt;
	}

	// tag/tag_main 도서 개수
	@Override
	public int getTagMainCount(String tag) {
		System.out.println("BookPersImple START - getTagMainCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getTagMainCount(tag);
		System.out.println("BookPersImple END - getTagMainCount");
		return cnt;
	}

	// tag_main 도서 목록
	@Override
	public ArrayList<Book> getTagMainBook(Map<String, Object> map) {
		System.out.println("BookPersImple START - getTagMainBook");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getTagMainBook(map);
		System.out.println("BookPersImple END - getTagMainBook");
		return list;
	}

	// 국내도서 목록
	@Override
	public ArrayList<Book> getDomesticBook(Map<String, Object> map) {
		System.out.println("BookPersImple START - getDomesticBook");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getDomesticBook(map);
		System.out.println("BookPersImple END - getDomesticBook");
		return list;
	}

	// 도서 개수
	@Override
	public int getBookCnt() {
		System.out.println("BookPersImple START - getBookCnt");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getBookCnt();
		System.out.println("BookPersImple END - getBookCnt");
		return cnt;
	}

	// 도서 수정
	@Override
	public int bookUpdate(Book book) {
		System.out.println("BookPersImple START - bookUpdate");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookUpdate(book);
		System.out.println("BookPersImple END - bookUpdate");
		return cnt;
	}

	// 주문후 도서 수량 수정-1) 주문후 수량 = 재고량, 주문수량
	@Override
	public ArrayList<Bespeak> getOrderBookCount(String order_num) {
		System.out.println("BookPersImple START - getOrderNoCount");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Bespeak> list = mapper.getOrderBookCount(order_num);

		System.out.println("BookPersImple END - getOrderNoCount");
		return list;
	}
	/*
	 * @Override public Map<String, Object> getOrderNoCount(String order_num) {
	 * System.out.println("BookPersImple START - getOrderNoCount");
	 * System.out.println(order_num);
	 * 
	 * BookPers mapper = sqlSession.getMapper(BookPers.class); Map<String, Object>
	 * map = mapper.getOrderNoCount(order_num);
	 * 
	 * //DB의 NUMBER 타입은 반드시 캐스트 변환이 아닌 메소드 사용 //Map으로 가져온 Column은 대문자. int bookCount
	 * = Integer.parseInt(String.valueOf(map.get("BC"))); int orderCount =
	 * Integer.parseInt(String.valueOf(map.get("ORC"))); int count = bookCount -
	 * orderCount; map.put("count", count);
	 * 
	 * System.out.println("BookPersImple END - getOrderNoCount"); return map; }
	 */

	// 주문후 도서 수량 수정-2) 도서 수량 수정 처리
	@Override
	public int orderBookCountMul(Map<String, Object> map) {
		System.out.println("BookPersImple START - orderBookCountMul");
		System.out.println((String) map.get("order_num"));

		BookPers mapper = sqlSession.getMapper(BookPers.class);

		int cnt = 0;

		// 주문 내역 + 도서 정보
		ArrayList<Bespeak> list = getOrderBookCount((String) map.get("order_num"));

		// 재고가 충분한 지 확인
		boolean flg = false;
		for (Bespeak order : list) {
			int count = order.getBook().getCount() - order.getOrder_count();
			// 재고가 충분하면 수량 수정 (주문후 수량 = 재고량 - 주문수량)
			if (count < 0) {
				order.getBook().setCount(count);
				flg = true;
			}
		}

		// 재고가 충분하면 수량 감소
		if (!flg) {
			for (Bespeak order : list) {
				System.out.println("orderBookCountMul 수량 확인 필요 - " + order.getBook().getCount());
				map = new HashMap<>();
				map.put("no", order.getBook().getNo());
				map.put("count", order.getBook().getCount());
				cnt = mapper.orderBookCountMul(map);
			}
		} else {
			cnt = 9;
		}
		System.out.println("BookPersImple END - orderBookCountMul");
		return cnt;
	}

	// 환불 도서 수량
	@Override
	public int refundBookCountAdd(Map<String, Object> map) {
		System.out.println("BookPersImple START - refundBookCountAdd");

		BookPers mapper = sqlSession.getMapper(BookPers.class);

		// 주문 내역 + 도서 정보
		ArrayList<Bespeak> list = getOrderBookCount((String) map.get("order_num"));

		int cnt = 0;
		// 도서 수량 다시 추가하기 (환불할 주문의 도서 수량 + 재고량)
		for (Bespeak order : list) {
			map = new HashMap<>();
			map.put("count", order.getBook().getCount() + order.getOrder_count());
			map.put("no", order.getBook().getNo());
			cnt = mapper.refundBookCountAdd(map);
		}
		System.out.println("BookPersImple END - refundBookCountAdd");
		return cnt;
	}

	// 도서 삭제
	@Override
	public int bookDelete(String no) {
		System.out.println("BookPersImple START - bookDelete");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookDelete(no);
		System.out.println("BookPersImple END - bookDelete");
		return cnt;
	}

	// 도서 추가
	@Override
	public int bookInsert(Book book) {
		System.out.println("BookPersImple START - bookInsert");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookInsert(book);

		System.out.println("BookPersImple END - bookInsert");
		return cnt;
	}

	// 도서 부가 정보 수정
	@Override
	public int bookSubUpdate(BookSub bSub) {
		System.out.println("BookPersImple START - bookSubUpdate");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookSubUpdate(bSub);
		System.out.println("BookPersImple END - bookSubUpdate");
		return cnt;
	}

	// 도서 부가 정보 추가
	@Override
	public int bookSubInsert(BookSub bSub) {
		System.out.println("BookPersImple START - bookSubInsert");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookSubInsert(bSub);
		System.out.println("BookPersImple END - bookSubInsert");
		return cnt;
	}

	// 최근 본 도서 수정
	@Override
	public ArrayList<RecentBook> recentUpdate(ArrayList<RecentBook> list, Book b) {
		System.out.println("BookPersImple START - recentUpdate");

		ArrayList<RecentBook> reb = list;
		RecentBook rb = new RecentBook();
		rb.setNo(b.getNo());
		rb.setImage(b.getImage());

		try {
			if (reb != null & reb.size() > 0) {
				// 최근과 중복X
				RecentBook last = reb.get(reb.size() - 1);
				if (!last.getNo().equals(b.getNo())) {

					// 다섯 개면
					if (reb.size() >= 5) {
						reb.remove(0);
					}
					reb.add(rb);
				}
			} else {
				reb.add(rb);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("캐시 완전 비우기할 경우 발생");
		}
		System.out.println("BookPersImple END - recentUpdate");
		return reb;
	}

	// 총 판매 도서수
	@Override
	public int getSalesTotalCount() {
		System.out.println("BookPersImple START - getSalesTotalCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int sales = mapper.getSalesTotalCount();
		System.out.println("BookPersImple END - getSalesTotalCount");
		return sales;
	}

	// 총 매출액
	@Override
	public int getSalesTotalAmount() {
		System.out.println("BookPersImple START - getSalesTotalAmount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int sales = mapper.getSalesTotalAmount();
		System.out.println("BookPersImple END - getSalesTotalAmount");
		return sales;
	}

	// no-title
	public Map<String, String> getNoTitle() {
		System.out.println("BookPersImple START - getNoTitle");

		Map<String, String> map = new HashMap<>();

		ArrayList<Book> list = getAllBook();
		for (Book b : list) {
			String no = b.getNo();
			String title = b.getTitle();
			map.put(no, title);
		}

		System.out.println("BookPersImple END - getNoTitle");
		return map;
	}

	// 올해 태그별 판매량
	public ArrayList<StringInt> getTagSalesTotal() {
		System.out.println("BookPersImple START - getTagSalesTotal");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getTagSalesTotal();
		for (int i = 0; i < list.size(); i += 1) {
			list.get(i).setStr(list.get(i).getStr() + "S");
		}
		System.out.println("BookPersImple END - getTagSalesTotal");
		return list;
	}

	// 올해 태그별 환불량
	@Override
	public ArrayList<StringInt> getTagRefundTotal() {
		System.out.println("BookPersImple START - getTagRefundTotal");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getTagRefundTotal();
		for (int i = 0; i < list.size(); i += 1) {
			list.get(i).setStr(list.get(i).getStr() + "R");
		}

		System.out.println("BookPersImple END - getTagRefundTotal");
		return list;
	}

	// 연간 매출액
	@Override
	public ArrayList<StringInt> getYearSalesTotalAmount() {
		System.out.println("BookPersImple START - getYearSalesTotalAmount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getYearSalesTotalAmount();
		System.out.println("BookPersImple END - getYearSalesTotalAmount");
		return list;
	}

	// 연간 판매량
	@Override
	public ArrayList<StringInt> getYearSalesTotalCount() {
		System.out.println("BookPersImple START - getYearSalesTotalCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getYearSalesTotalCount();
		System.out.println("BookPersImple END - getYearSalesTotalCount");
		return list;
	}

	// 연간 환불액
	@Override
	public ArrayList<StringInt> getYearRefundTotalAmount() {
		System.out.println("BookPersImple START - getYearRefundTotalAmount");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getYearRefundTotalAmount();

		System.out.println("BookPersImple END - getYearRefundTotalAmount");
		return list;
	}

	// 주간별 태그별 매출액-1
	@Override
	public ArrayList<StringInt> getTagWeeklyCount() {
		System.out.println("BookPersImple START - getTagWeeklyCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getTagWeeklyCount();

		Calendar cal = Calendar.getInstance();
		for (int i = 0; i < list.size(); i += 1) {
			// 주간 구하기
			String date = list.get(i).getMd();

			int year = Integer.parseInt(date.substring(0, 4));
			int month = Integer.parseInt(date.substring(4, 6));
			int day = Integer.parseInt(date.substring(6, 8));

			cal.set(year, month, day);
			String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));

			// 주간 + 태그명. 주간별 태그 중복 제거 작업1
			list.get(i).setStr(week + list.get(i).getStr());

			System.out.println("getTagWeeklyCount");
			System.out.println(year + "/ " + month + "/ " + day + " : " + week + "주 - " + list.get(i).getStr());
		}
		System.out.println("BookPersImple END - getTagWeeklyCount");
		return list;
	}

	// 주간별 태그별 매출액-2) 주간별 태그 중복 제거 작업2
	@Override
	public Map<String, Integer> getTagWeeklyCountDistinct() {
		System.out.println("BookPersImple START - getTagWeeklyCountDistinct");
		Map<String, Integer> map = new HashMap<>();

		ArrayList<StringInt> list = getTagWeeklyCount();
		for (StringInt tmp : list) {
			String tag = tmp.getStr();
			int sales = tmp.getCount();

			// 첫 put이 아니면 중복 확인 후 put
			if (map.size() > 0) {
				boolean flg = false;
				// key가 겹치면 value 더하기
				for (Entry<String, Integer> e : map.entrySet()) {
					if (e.getKey().equals(tag)) {
						e.setValue(e.getValue() + sales);
						flg = true;
					}
				}
				// key가 겹치지 않으면 추가
				if (!flg)
					map.put(tag, sales);
			} else {
				map.put(tag, sales);
			}
		}

		System.out.println("----------test---------");
		// TEST
		for (Entry<String, Integer> e : map.entrySet()) {
			String week = e.getKey().substring(0, 1);
			String tag = e.getKey().substring(1);

			System.out.println(week + "주 - " + tag + " : " + e.getValue());
		}
		System.out.println("BookPersImple END - getTagWeeklyCountDistinct");
		return map;
	}

	@Override
	public int getNoticeCnt() {
		System.out.println("BookPers START - getNoticeCnt()");
		int cnt = 0;
		
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		cnt = mapper.getNoticeCnt();
	
		System.out.println("BookPers END - getNoticeCnt()");
		return cnt;
	}
	
	// 공지사항
	@Override
	public ArrayList<Notice> getNotice(Map<String, Object> map) {
		System.out.println("BookPersImple START- getNotice()");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Notice> list = mapper.getNotice(map);

		System.out.println("BookPersImple END - getNotice()");
		return list;
	}
	

	//공지사항 글쓰기 처리
	@Override
	public int noticeWritePro(Notice notice) {
		System.out.println("BookPersImpl START - noticeWritePro()");
		
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.noticeWritePro(notice);
		
		System.out.println("BookPersImpl END - noticeWritePro()");
		return cnt;
	}

	//공지사항 상세보기
	@Override
	public Notice noticeView(String idx) {
		System.out.println("BookPersImpl START - noticeView()");
		
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		Notice notice = mapper.noticeView(idx);
		
		System.out.println("BookPersImpl END - noticeView()");
		return notice;
	}
}
// ROWNUM 이 있는절에는 WHERE절과, ORDER BY 절이 붙으면 안된다.