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
import spring.mvc.bookstore.vo.RecentBook;
import spring.mvc.bookstore.vo.StringInt;

@Repository
public class BookPersImpl implements BookPers {
	@Autowired
	private SqlSession sqlSession;

	/*
	 * private BookPers dao = sqlSession.getMapper(BookPers.class); Error �߻�:
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

	// ���� �˻�
	public ArrayList<Book> bookSearch(Map<String, Object> map) {
		System.out.println("BookPersImple START - bookSearch");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = dao.bookSearch(map);
		System.out.println("BookPersImple END - bookSearch");
		return list;
	}

	// ���� �˻� �����
	public int bookSearchCount(String keyword) {
		System.out.println("BookPersImple START - bookSearchCount");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.bookSearchCount(keyword);
		System.out.println("BookPersImple END - bookSearchCount");
		return cnt;
	}

	// �ش� ���� ��ȸ�� ����
	public int bookViewsUpdate(String no) {
		System.out.println("BookPersImple START - bookViewsUpdate");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.bookViewsUpdate(no);
		System.out.println("BookPersImple END - bookViewsUpdate");
		return cnt;
	}

	// ��� ���� ���
	public ArrayList<Book> getAllBook() {
		System.out.println("BookPersImple START - getAllBook");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = dao.getAllBook();
		System.out.println("BookPersImple END - getAllBook");
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
		System.out.println("BookPersImple START - getBookInfo");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		Book b = dao.getBookInfo(no);
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

		System.out.println("BookPersImpl END - getBookInfo : " + b.getImage());
		return b;
	}

	// �Ű� ���� ���
	@Override
	public ArrayList<Book> getNewBook(Map<String, Object> map) {
		System.out.println("BookPersImple START - getNewBook");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = dao.getNewBook(map);
		System.out.println("BookPersImple END - getNewBook");
		return list;
	}

	// ����Ʈ����(�α�) ���� ���
	@Override
	public ArrayList<Book> getBestBook(Map<String, Object> map) {
		System.out.println("BookPersImple START - getBestBook");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = dao.getBestBook(map);
		System.out.println("BookPersImple END - getBestBook");
		return list;
	}

	// ��õ ���� ���
	@Override
	public ArrayList<Book> getGoodTagBook(Map<String, Object> map) {
		System.out.println("BookPersImple START - getGoodTagBook");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = dao.getGoodTagBook(map);
		System.out.println("BookPersImple END - getGoodTagBook");
		return list;
	}

	// ���� ���
	@Override
	public ArrayList<Book> getBookList(Map<String, Object> map) {
		System.out.println("BookPersImple START - getBookList");

		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> books = dao.getBookList(map);

		System.out.println("BookPersImple END - getBookList");
		return books;
	}

	// ���� �ΰ� ���� ���
	/*
	 * @Override public ArrayList<BookSub> getBookSubList(Map<String, Object> map) {
	 * System.out.println("BookPersImple START - getBookSubList"); BookPers dao =
	 * sqlSession.getMapper(BookPers.class); ArrayList<BookSub> bSubs =
	 * dao.getBookSubList(map);
	 * System.out.println("BookPersImple END - getBookSubList"); return bSubs; }
	 */

	// tag_main ��
	public int getTag_mainCount() {
		System.out.println("BookPersImple START - getTag_mainCount");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.getTag_mainCount();
		System.out.println("BookPersImple END - getTag_mainCount");
		return cnt;
	}

	// �����±� ���
	@Override
	public ArrayList<BookSub> getTag_mainList() {
		System.out.println("BookPersImple START - getTag_mainList");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<BookSub> tags = dao.getTag_mainList();
		System.out.println("BookPersImple END - getTag_mainList");
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
	 * System.out.println("BookPersImpl - getBookTag() ����"); } finally { try { if(rs
	 * != null) rs.close(); if(ps != null) ps.close(); if(conn != null)
	 * conn.close(); } catch(SQLException e) { e.printStackTrace(); } }
	 * 
	 * return tags; }
	 */

	// ����Ʈ(�α�) ���� ����
	@Override
	public int getBestCount() {
		System.out.println("BookPersImple START - getBestCount");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.getBestCount();
		System.out.println("BookPersImple END - getBestCount");
		return cnt;
	}

	// �Ű� ���� ����
	@Override
	public int getNewCount() {
		System.out.println("BookPersImple START - getNewCount");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.getNewCount();
		System.out.println("BookPersImple END - getNewCount");
		return cnt;
	}

	// ��õ ���� ����
	@Override
	public int getGoodCount() {
		System.out.println("BookPersImple START - getGoodCount");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.getGoodCount();
		System.out.println("BookPersImple END - getGoodCount");
		return cnt;
	}

	// tag/tag_main ���� ����
	@Override
	public int getTagMainCount(String tag) {
		System.out.println("BookPersImple START - getTagMainCount");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.getTagMainCount(tag);
		System.out.println("BookPersImple END - getTagMainCount");
		return cnt;
	}

	// tag_main ���� ���
	@Override
	public ArrayList<Book> getTagMainBook(Map<String, Object> map) {
		System.out.println("BookPersImple START - getTagMainBook");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = dao.getTagMainBook(map);
		System.out.println("BookPersImple END - getTagMainBook");
		return list;
	}

	// �������� ���
	@Override
	public ArrayList<Book> getDomesticBook(Map<String, Object> map) {
		System.out.println("BookPersImple START - getDomesticBook");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<Book> list = dao.getDomesticBook(map);
		System.out.println("BookPersImple END - getDomesticBook");
		return list;
	}

	// ���� ����
	@Override
	public int getBookCnt() {
		System.out.println("BookPersImple START - getBookCnt");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.getBookCnt();
		System.out.println("BookPersImple END - getBookCnt");
		return cnt;
	}

	// ���� ����
	@Override
	public int bookUpdate(Book book) {
		System.out.println("BookPersImple START - bookUpdate");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.bookUpdate(book);
		System.out.println("BookPersImple END - bookUpdate");
		return cnt;
	}

	// �ֹ��� ���� ���� ����-1) �ֹ��� ���� = ���, �ֹ�����
	@Override
	public ArrayList<Bespeak> getOrderBookCount(String order_num) {
		System.out.println("BookPersImple START - getOrderNoCount");

		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<Bespeak> list = dao.getOrderBookCount(order_num);

		System.out.println("BookPersImple END - getOrderNoCount");
		return list;
	}
	/*
	 * @Override public Map<String, Object> getOrderNoCount(String order_num) {
	 * System.out.println("BookPersImple START - getOrderNoCount");
	 * System.out.println(order_num);
	 * 
	 * BookPers dao = sqlSession.getMapper(BookPers.class); Map<String, Object> map
	 * = dao.getOrderNoCount(order_num);
	 * 
	 * //DB�� NUMBER Ÿ���� �ݵ�� ĳ��Ʈ ��ȯ�� �ƴ� �޼ҵ� ��� //Map���� ������ Column�� �빮��. int bookCount
	 * = Integer.parseInt(String.valueOf(map.get("BC"))); int orderCount =
	 * Integer.parseInt(String.valueOf(map.get("ORC"))); int count = bookCount -
	 * orderCount; map.put("count", count);
	 * 
	 * System.out.println("BookPersImple END - getOrderNoCount"); return map; }
	 */

	// �ֹ��� ���� ���� ����-2) ���� ���� ���� ó��
	@Override
	public int orderBookCountMul(Map<String, Object> map) {
		System.out.println("BookPersImple START - orderBookCountMul");
		System.out.println((String) map.get("order_num"));

		BookPers dao = sqlSession.getMapper(BookPers.class);

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
				System.out.println("orderBookCountMul ���� Ȯ�� �ʿ� - " + order.getBook().getCount());
				map = new HashMap<>();
				map.put("no", order.getBook().getNo());
				map.put("count", order.getBook().getCount());
				cnt = dao.orderBookCountMul(map);
			}
		} else {
			cnt = 9;
		}
		System.out.println("BookPersImple END - orderBookCountMul");
		return cnt;
	}

	// ȯ�� ���� ����
	@Override
	public int refundBookCountAdd(Map<String, Object> map) {
		System.out.println("BookPersImple START - refundBookCountAdd");

		BookPers dao = sqlSession.getMapper(BookPers.class);

		// �ֹ� ���� + ���� ����
		ArrayList<Bespeak> list = getOrderBookCount((String) map.get("order_num"));
		
		int cnt = 0;
		// ���� ���� �ٽ� �߰��ϱ� (ȯ���� �ֹ��� ���� ���� + ���)
		for (Bespeak order : list) {
			map = new HashMap<>();
			map.put("count", order.getBook().getCount() + order.getOrder_count());
			map.put("no", order.getBook().getNo());
			cnt = dao.refundBookCountAdd(map);
		}
		System.out.println("BookPersImple END - refundBookCountAdd");
		return cnt;
	}

	// ���� ����
	@Override
	public int bookDelete(String no) {
		System.out.println("BookPersImple START - bookDelete");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.bookDelete(no);
		System.out.println("BookPersImple END - bookDelete");
		return cnt;
	}

	// ���� �߰�
	@Override
	public int bookInsert(Book book) {
		System.out.println("BookPersImple START - bookInsert");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.bookInsert(book);
		System.out.println("BookPersImple END - bookInsert");
		return cnt;
	}

	// ���� �ΰ� ���� ����
	@Override
	public int bookSubUpdate(BookSub bSub) {
		System.out.println("BookPersImple START - bookSubUpdate");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.bookSubUpdate(bSub);
		System.out.println("BookPersImple END - bookSubUpdate");
		return cnt;
	}

	// ���� �ΰ� ���� �߰�
	@Override
	public int bookSubInsert(BookSub bSub) {
		System.out.println("BookPersImple START - bookSubInsert");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int cnt = dao.bookSubInsert(bSub);
		System.out.println("BookPersImple END - bookSubInsert");
		return cnt;
	}

	// �ֱ� �� ���� ����
	@Override
	public ArrayList<RecentBook> recentUpdate(ArrayList<RecentBook> list, Book b) {
		System.out.println("BookPersImple START - recentUpdate");
		
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
			System.out.println("ĳ�� ���� ������ ��� �߻�");
		}
		System.out.println("BookPersImple END - recentUpdate");
		return reb;
	}

	// �� �Ǹ� ������
	@Override
	public int getSalesTotalCount() {
		System.out.println("BookPersImple START - getSalesTotalCount");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int sales = dao.getSalesTotalCount();
		System.out.println("BookPersImple END - getSalesTotalCount");
		return sales;
	}

	// �� �����
	@Override
	public int getSalesTotalAmount() {
		System.out.println("BookPersImple START - getSalesTotalAmount");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		int sales = dao.getSalesTotalAmount();
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

	// ���� �±׺� �Ǹŷ�
	public ArrayList<StringInt> getTagSalesTotal() {
		System.out.println("BookPersImple START - getTagSalesTotal");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = dao.getTagSalesTotal();
		for (int i = 0; i < list.size(); i += 1) {
			list.get(i).setStr(list.get(i).getStr() + "S");
		}
		System.out.println("BookPersImple END - getTagSalesTotal");
		return list;
	}

	// ���� �±׺� ȯ�ҷ�
	@Override
	public ArrayList<StringInt> getTagRefundTotal() {
		System.out.println("BookPersImple START - getTagRefundTotal");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = dao.getTagRefundTotal();
		for (int i = 0; i < list.size(); i += 1) {
			list.get(i).setStr(list.get(i).getStr() + "R");
		}

		System.out.println("BookPersImple END - getTagRefundTotal");
		return list;
	}

	// ���� �����
	@Override
	public ArrayList<StringInt> getYearSalesTotalAmount() {
		System.out.println("BookPersImple START - getYearSalesTotalAmount");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = dao.getYearSalesTotalAmount();
		System.out.println("BookPersImple END - getYearSalesTotalAmount");
		return list;
	}

	// ���� �Ǹŷ�
	@Override
	public ArrayList<StringInt> getYearSalesTotalCount() {
		System.out.println("BookPersImple START - getYearSalesTotalCount");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = dao.getYearSalesTotalCount();
		System.out.println("BookPersImple END - getYearSalesTotalCount");
		return list;
	}

	// ���� ȯ�Ҿ�
	@Override
	public ArrayList<StringInt> getYearRefundTotalAmount() {
		System.out.println("BookPersImple START - getYearRefundTotalAmount");
		
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = dao.getYearRefundTotalAmount();
		
		System.out.println("BookPersImple END - getYearRefundTotalAmount");
		return list;
	}

	// �ְ��� �±׺� �����-1
	@Override
	public ArrayList<StringInt> getTagWeeklyCount() {
		System.out.println("BookPersImple START - getTagWeeklyCount");
		BookPers dao = sqlSession.getMapper(BookPers.class);
		ArrayList<StringInt> list = dao.getTagWeeklyCount();

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

			System.out.println("getTagWeeklyCount");
			System.out.println(year + "/ " + month + "/ " + day + " : " + week + "�� - " + list.get(i).getStr());
		}
		System.out.println("BookPersImple END - getTagWeeklyCount");
		return list;
	}

	// �ְ��� �±׺� �����-2) �ְ��� �±� �ߺ� ���� �۾�2
	@Override
	public Map<String, Integer> getTagWeeklyCountDistinct() {
		System.out.println("BookPersImple START - getTagWeeklyCountDistinct");
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

		System.out.println("----------test---------");
		// TEST
		for (Entry<String, Integer> e : map.entrySet()) {
			String week = e.getKey().substring(0, 1);
			String tag = e.getKey().substring(1);

			System.out.println(week + "�� - " + tag + " : " + e.getValue());
		}
		System.out.println("BookPersImple END - getTagWeeklyCountDistinct");
		return map;
	}
}
// ROWNUM �� �ִ������� WHERE����, ORDER BY ���� ������ �ȵȴ�.