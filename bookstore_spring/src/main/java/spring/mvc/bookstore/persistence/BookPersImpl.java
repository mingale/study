package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
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
	
	private Logger log = Logger.getLogger(this.getClass());

	/*
	 * private BookPers mapper = sqlSession.getMapper(BookPers.class); Error �߻�:
	 * java.lang.NullPointerException ����: Mapper�� ����������� �����ϸ� Bean ������ ���� �ʾ� ��� �Ұ���
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
	 * 12�� 28, 2017 11:58:58 ���� org.apache.catalina.core.StandardContext
	 * loadOnStartup �ɰ�: Servlet [appServlet] in web application [/bookstore] threw
	 * load() exception java.lang.NullPointerException at
	 * spring.mvc.bookstore.persistence.BookPersImpl.<init>(BookPersImpl.java:33)
	 */

	// �˻��� ����
	public ArrayList<String> getKeywordSuggest(String keyword) {
		log.debug("BookPersImpl START - getKeywordSuggest()");
		
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<String> list = mapper.getKeywordSuggest(keyword);
		
		log.debug("BookPersImpl END - getKeywordSuggest()");
		return list;
	}
	
	// ���� �˻�
	public ArrayList<Book> bookSearch(Map<String, Object> map) {
		log.debug("BookPersImple START - bookSearch");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.bookSearch(map);

		log.debug("BookPersImple END - bookSearch");
		return list;
	}

	// ���� �˻� �����
	public int bookSearchCount(String keyword) {
		log.debug("BookPersImple START - bookSearchCount");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookSearchCount(keyword);

		log.debug("BookPersImple END - bookSearchCount");
		return cnt;
	}

	// �ش� ���� ��ȸ�� ����
	public int bookViewsUpdate(String no) {
		log.debug("BookPersImple START - bookViewsUpdate");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookViewsUpdate(no);

		log.debug("BookPersImple END - bookViewsUpdate");
		return cnt;
	}

	// ��� ���� ���
	public ArrayList<Book> getAllBook() {
		log.debug("BookPersImple START - getAllBook");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getAllBook();

		log.debug("BookPersImple END - getAllBook");
		return list;
	}

	/*
	 * Error [java.sql.SQLException: ������ ���� ��� ���տ� �������� �۾��� ����Ǿ����ϴ�.] ��ó:
	 * http://hanuli7.tistory.com/entry/javasqlSQLException-������-����-���-���տ�-��������-�۾���-
	 * ����Ǿ����ϴ�-first [��.��.â.��.] ResultSet�� �⺻������ �Ĺ��� Ž���� �����ϵ��� ������� ��ü ���� �ʵ� �� ����
	 * �Ѱ��ָ� ���Ƿ� ��, �Ĺ��� Ž���� �� �� �ִ�.
	 */

	// ���� ����
	@Override
	public Book getBookInfo(String no) {
		log.debug("BookPersImple START - getBookInfo");
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
			b.getBookSub().setIntro("�غ� ���Դϴ�.");
		if (list == null)
			b.getBookSub().setList("�غ� ���Դϴ�.");
		if (pub_intro == null)
			b.getBookSub().setPub_intro("�غ� ���Դϴ�.");
		if (review == null)
			b.getBookSub().setReview("�غ� ���Դϴ�.");

		log.debug("BookPersImpl END - getBookInfo");
		return b;
	}

	// �Ű� ���� ���
	@Override
	public ArrayList<Book> getNewBook(Map<String, Object> map) {
		log.debug("BookPersImple START - getNewBook");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getNewBook(map);
		log.debug("BookPersImple END - getNewBook");
		return list;
	}

	// ����Ʈ����(�α�) ���� ���
	@Override
	public ArrayList<Book> getBestBook(Map<String, Object> map) {
		log.debug("BookPersImple START - getBestBook");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getBestBook(map);
		log.debug("BookPersImple END - getBestBook");
		return list;
	}

	// ��õ ���� ���
	@Override
	public ArrayList<Book> getGoodTagBook(Map<String, Object> map) {
		log.debug("BookPersImple START - getGoodTagBook");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getGoodTagBook(map);
		log.debug("BookPersImple END - getGoodTagBook");
		return list;
	}

	// ���� ���
	@Override
	public ArrayList<Book> getBookList(Map<String, Object> map) {
		log.debug("BookPersImple START - getBookList");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> books = mapper.getBookList(map);

		log.debug("BookPersImple END - getBookList");
		return books;
	}

	// ���� �ΰ� ���� ���
	/*
	 * @Override public ArrayList<BookSub> getBookSubList(Map<String, Object> map) {
	 * log.debug("BookPersImple START - getBookSubList"); BookPers mapper =
	 * sqlSession.getMapper(BookPers.class); ArrayList<BookSub> bSubs =
	 * dao.getBookSubList(map);
	 * log.debug("BookPersImple END - getBookSubList"); return bSubs; }
	 */

	// tag_main ��
	public int getTag_mainCount() {
		log.debug("BookPersImple START - getTag_mainCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getTag_mainCount();
		log.debug("BookPersImple END - getTag_mainCount");
		return cnt;
	}

	// �����±� ���
	@Override
	public ArrayList<BookSub> getTag_mainList() {
		log.debug("BookPersImple START - getTag_mainList");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<BookSub> tags = mapper.getTag_mainList();
		log.debug("BookPersImple END - getTag_mainList");
		return tags;
	}

	// tag ���
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
	 * log.debug("BookPersImpl - getBookTag() ����"); } finally { try { if(rs
	 * != null) rs.close(); if(ps != null) ps.close(); if(conn != null)
	 * conn.close(); } catch(SQLException e) { e.printStackTrace(); } }
	 * 
	 * return tags; }
	 */

	// ����Ʈ(�α�) ���� ����
	@Override
	public int getBestCount() {
		log.debug("BookPersImple START - getBestCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getBestCount();
		log.debug("BookPersImple END - getBestCount");
		return cnt;
	}

	// �Ű� ���� ����
	@Override
	public int getNewCount() {
		log.debug("BookPersImple START - getNewCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getNewCount();
		log.debug("BookPersImple END - getNewCount");
		return cnt;
	}

	// ��õ ���� ����
	@Override
	public int getGoodCount() {
		log.debug("BookPersImple START - getGoodCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getGoodCount();
		log.debug("BookPersImple END - getGoodCount");
		return cnt;
	}

	// tag/tag_main ���� ����
	@Override
	public int getTagMainCount(String tag) {
		log.debug("BookPersImple START - getTagMainCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getTagMainCount(tag);
		log.debug("BookPersImple END - getTagMainCount");
		return cnt;
	}

	// tag_main ���� ���
	@Override
	public ArrayList<Book> getTagMainBook(Map<String, Object> map) {
		log.debug("BookPersImple START - getTagMainBook");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getTagMainBook(map);
		log.debug("BookPersImple END - getTagMainBook");
		return list;
	}

	// �������� ���
	@Override
	public ArrayList<Book> getDomesticBook(Map<String, Object> map) {
		log.debug("BookPersImple START - getDomesticBook");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = mapper.getDomesticBook(map);
		log.debug("BookPersImple END - getDomesticBook");
		return list;
	}

	// ���� ����
	@Override
	public int getBookCnt() {
		log.debug("BookPersImple START - getBookCnt");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.getBookCnt();
		log.debug("BookPersImple END - getBookCnt");
		return cnt;
	}

	// ���� ����
	@Override
	public int bookUpdate(Book book) {
		log.debug("BookPersImple START - bookUpdate");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookUpdate(book);
		log.debug("BookPersImple END - bookUpdate");
		return cnt;
	}

	// �ֹ��� ���� ���� ����-1) �ֹ��� ���� = ���, �ֹ�����
	@Override
	public ArrayList<Bespeak> getOrderBookCount(String order_num) {
		log.debug("BookPersImple START - getOrderNoCount");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Bespeak> list = mapper.getOrderBookCount(order_num);

		log.debug("BookPersImple END - getOrderNoCount");
		return list;
	}
	/*
	 * @Override public Map<String, Object> getOrderNoCount(String order_num) {
	 * log.debug("BookPersImple START - getOrderNoCount");
	 * log.debug(order_num);
	 * 
	 * BookPers mapper = sqlSession.getMapper(BookPers.class); Map<String, Object>
	 * map = mapper.getOrderNoCount(order_num);
	 * 
	 * //DB�� NUMBER Ÿ���� �ݵ�� ĳ��Ʈ ��ȯ�� �ƴ� �޼ҵ� ��� //Map���� ������ Column�� �빮��. int bookCount
	 * = Integer.parseInt(String.valueOf(map.get("BC"))); int orderCount =
	 * Integer.parseInt(String.valueOf(map.get("ORC"))); int count = bookCount -
	 * orderCount; map.put("count", count);
	 * 
	 * log.debug("BookPersImple END - getOrderNoCount"); return map; }
	 */

	// �ֹ��� ���� ���� ����-2) ���� ���� ���� ó��
	@Override
	public int orderBookCountMul(Map<String, Object> map) {
		log.debug("BookPersImple START - orderBookCountMul");
		log.debug((String) map.get("order_num"));

		BookPers mapper = sqlSession.getMapper(BookPers.class);

		int cnt = 0;

		// �ֹ� ���� + ���� ����
		ArrayList<Bespeak> list = getOrderBookCount((String) map.get("order_num"));

		// ��� ����� �� Ȯ��
		boolean flg = false;
		for (Bespeak order : list) {
			int count = order.getBook().getCount() - order.getOrder_count();
			// ��� ����ϸ� ���� ���� (�ֹ��� ���� = ��� - �ֹ�����)
			if (count < 0) {
				order.getBook().setCount(count);
				flg = true;
			}
		}

		// ��� ����ϸ� ���� ����
		if (!flg) {
			for (Bespeak order : list) {
				log.debug("orderBookCountMul ���� Ȯ�� �ʿ� - " + order.getBook().getCount());
				map = new HashMap<>();
				map.put("no", order.getBook().getNo());
				map.put("count", order.getBook().getCount());
				cnt = mapper.orderBookCountMul(map);
			}
		} else {
			cnt = 9;
		}
		log.debug("BookPersImple END - orderBookCountMul");
		return cnt;
	}

	// ȯ�� ���� ����
	@Override
	public int refundBookCountAdd(Map<String, Object> map) {
		log.debug("BookPersImple START - refundBookCountAdd");

		BookPers mapper = sqlSession.getMapper(BookPers.class);

		// �ֹ� ���� + ���� ����
		ArrayList<Bespeak> list = getOrderBookCount((String) map.get("order_num"));

		int cnt = 0;
		// ���� ���� �ٽ� �߰��ϱ� (ȯ���� �ֹ��� ���� ���� + ���)
		for (Bespeak order : list) {
			map = new HashMap<>();
			map.put("count", order.getBook().getCount() + order.getOrder_count());
			map.put("no", order.getBook().getNo());
			cnt = mapper.refundBookCountAdd(map);
		}
		log.debug("BookPersImple END - refundBookCountAdd");
		return cnt;
	}

	// ���� ����
	@Override
	public int bookDelete(String no) {
		log.debug("BookPersImple START - bookDelete");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookDelete(no);
		log.debug("BookPersImple END - bookDelete");
		return cnt;
	}

	// ���� �߰�
	@Override
	public int bookInsert(Book book) {
		log.debug("BookPersImple START - bookInsert");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookInsert(book);

		log.debug("BookPersImple END - bookInsert");
		return cnt;
	}

	// ���� �ΰ� ���� ����
	@Override
	public int bookSubUpdate(BookSub bSub) {
		log.debug("BookPersImple START - bookSubUpdate");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookSubUpdate(bSub);
		log.debug("BookPersImple END - bookSubUpdate");
		return cnt;
	}

	// ���� �ΰ� ���� �߰�
	@Override
	public int bookSubInsert(BookSub bSub) {
		log.debug("BookPersImple START - bookSubInsert");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.bookSubInsert(bSub);
		log.debug("BookPersImple END - bookSubInsert");
		return cnt;
	}

	// �ֱ� �� ���� ����
	@Override
	public ArrayList<RecentBook> recentUpdate(ArrayList<RecentBook> list, Book b) {
		log.debug("BookPersImple START - recentUpdate");

		ArrayList<RecentBook> reb = list;
		RecentBook rb = new RecentBook();
		rb.setNo(b.getNo());
		rb.setImage(b.getImage());

		try {
			if (reb != null & reb.size() > 0) {
				// �ֱٰ� �ߺ�X
				RecentBook last = reb.get(reb.size() - 1);
				if (!last.getNo().equals(b.getNo())) {

					// �ټ� ����
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
			log.debug("ĳ�� ���� ������ ��� �߻�");
		}
		log.debug("BookPersImple END - recentUpdate");
		return reb;
	}

	// �� �Ǹ� ������
	@Override
	public int getSalesTotalCount() {
		log.debug("BookPersImple START - getSalesTotalCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int sales = mapper.getSalesTotalCount();
		log.debug("BookPersImple END - getSalesTotalCount");
		return sales;
	}

	// �� �����
	@Override
	public int getSalesTotalAmount() {
		log.debug("BookPersImple START - getSalesTotalAmount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int sales = mapper.getSalesTotalAmount();
		log.debug("BookPersImple END - getSalesTotalAmount");
		return sales;
	}

	// no-title
	public Map<String, String> getNoTitle() {
		log.debug("BookPersImple START - getNoTitle");

		Map<String, String> map = new HashMap<>();

		ArrayList<Book> list = getAllBook();
		for (Book b : list) {
			String no = b.getNo();
			String title = b.getTitle();
			map.put(no, title);
		}

		log.debug("BookPersImple END - getNoTitle");
		return map;
	}

	// ���� �±׺� �Ǹŷ�
	public ArrayList<StringInt> getTagSalesTotal() {
		log.debug("BookPersImple START - getTagSalesTotal");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getTagSalesTotal();
		for (int i = 0; i < list.size(); i += 1) {
			list.get(i).setStr(list.get(i).getStr() + "S");
		}
		log.debug("BookPersImple END - getTagSalesTotal");
		return list;
	}

	// ���� �±׺� ȯ�ҷ�
	@Override
	public ArrayList<StringInt> getTagRefundTotal() {
		log.debug("BookPersImple START - getTagRefundTotal");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getTagRefundTotal();
		for (int i = 0; i < list.size(); i += 1) {
			list.get(i).setStr(list.get(i).getStr() + "R");
		}

		log.debug("BookPersImple END - getTagRefundTotal");
		return list;
	}

	// ���� �����
	@Override
	public ArrayList<StringInt> getYearSalesTotalAmount() {
		log.debug("BookPersImple START - getYearSalesTotalAmount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getYearSalesTotalAmount();
		log.debug("BookPersImple END - getYearSalesTotalAmount");
		return list;
	}

	// ���� �Ǹŷ�
	@Override
	public ArrayList<StringInt> getYearSalesTotalCount() {
		log.debug("BookPersImple START - getYearSalesTotalCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getYearSalesTotalCount();
		log.debug("BookPersImple END - getYearSalesTotalCount");
		return list;
	}

	// ���� ȯ�Ҿ�
	@Override
	public ArrayList<StringInt> getYearRefundTotalAmount() {
		log.debug("BookPersImple START - getYearRefundTotalAmount");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getYearRefundTotalAmount();

		log.debug("BookPersImple END - getYearRefundTotalAmount");
		return list;
	}

	// �ְ��� �±׺� �����-1
	@Override
	public ArrayList<StringInt> getTagWeeklyCount() {
		log.debug("BookPersImple START - getTagWeeklyCount");
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = mapper.getTagWeeklyCount();

		Calendar cal = Calendar.getInstance();
		for (int i = 0; i < list.size(); i += 1) {
			// �ְ� ���ϱ�
			String date = list.get(i).getMd();

			int year = Integer.parseInt(date.substring(0, 4));
			int month = Integer.parseInt(date.substring(4, 6));
			int day = Integer.parseInt(date.substring(6, 8));

			cal.set(year, month, day);
			String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));

			// �ְ� + �±׸�. �ְ��� �±� �ߺ� ���� �۾�1
			list.get(i).setStr(week + list.get(i).getStr());

			log.debug("getTagWeeklyCount");
			log.debug(year + "/ " + month + "/ " + day + " : " + week + "�� - " + list.get(i).getStr());
		}
		log.debug("BookPersImple END - getTagWeeklyCount");
		return list;
	}

	// �ְ��� �±׺� �����-2) �ְ��� �±� �ߺ� ���� �۾�2
	@Override
	public Map<String, Integer> getTagWeeklyCountDistinct() {
		log.debug("BookPersImple START - getTagWeeklyCountDistinct");
		Map<String, Integer> map = new HashMap<>();

		ArrayList<StringInt> list = getTagWeeklyCount();
		for (StringInt tmp : list) {
			String tag = tmp.getStr();
			int sales = tmp.getCount();

			// ù put�� �ƴϸ� �ߺ� Ȯ�� �� put
			if (map.size() > 0) {
				boolean flg = false;
				// key�� ��ġ�� value ���ϱ�
				for (Entry<String, Integer> e : map.entrySet()) {
					if (e.getKey().equals(tag)) {
						e.setValue(e.getValue() + sales);
						flg = true;
					}
				}
				// key�� ��ġ�� ������ �߰�
				if (!flg)
					map.put(tag, sales);
			} else {
				map.put(tag, sales);
			}
		}

		log.debug("----------test---------");
		// TEST
		for (Entry<String, Integer> e : map.entrySet()) {
			String week = e.getKey().substring(0, 1);
			String tag = e.getKey().substring(1);

			log.debug(week + "�� - " + tag + " : " + e.getValue());
		}
		log.debug("BookPersImple END - getTagWeeklyCountDistinct");
		return map;
	}

	@Override
	public int getNoticeCnt() {
		log.debug("BookPers START - getNoticeCnt()");
		int cnt = 0;
		
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		cnt = mapper.getNoticeCnt();
	
		log.debug("BookPers END - getNoticeCnt()");
		return cnt;
	}
	
	// ��������
	@Override
	public ArrayList<Notice> getNotice(Map<String, Object> map) {
		log.debug("BookPersImple START- getNotice()");

		BookPers mapper = sqlSession.getMapper(BookPers.class);
		ArrayList<Notice> list = mapper.getNotice(map);

		log.debug("BookPersImple END - getNotice()");
		return list;
	}
	

	//�������� �۾��� ó��
	@Override
	public int noticeWritePro(Notice notice) {
		log.debug("BookPersImpl START - noticeWritePro()");
		
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		int cnt = mapper.noticeWritePro(notice);
		
		log.debug("BookPersImpl END - noticeWritePro()");
		return cnt;
	}

	//�������� �󼼺���
	@Override
	public Notice noticeView(String idx) {
		log.debug("BookPersImpl START - noticeView()");
		
		BookPers mapper = sqlSession.getMapper(BookPers.class);
		Notice notice = mapper.noticeView(idx);
		
		log.debug("BookPersImpl END - noticeView()");
		return notice;
	}
}
// ROWNUM �� �ִ������� WHERE����, ORDER BY ���� ������ �ȵȴ�.