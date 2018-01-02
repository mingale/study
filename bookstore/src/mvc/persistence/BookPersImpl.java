package mvc.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mvc.vo.Book;
import mvc.vo.BookSub;
import mvc.vo.RecentBook;

public class BookPersImpl implements BookPers {
	DataSource datasource;

	public static BookPersImpl bookPers = new BookPersImpl();

	private BookPersImpl() {
		try {
			Context context = new InitialContext();
			datasource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g/bms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BookPersImpl getInstance() {
		return bookPers;
	}

	//���� �˻�
	public ArrayList<Book> bookSearch(String search, int start, int end) {
		ArrayList<Book> list = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * " + 
						 "FROM (SELECT no, title, author, publisher, pub_date, price, count, image, rownum FROM book WHERE title LIKE ?) " + 
						 "WHERE rownum >= " + start +" "
						 		+ "AND rownum <= " + end;
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + search + "%");
			
			rs = ps.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					Book b = new Book();
					b.setNo(rs.getString("no"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					b.setPublisher(rs.getString("publisher"));
					b.setPub_date(rs.getDate("pub_date"));
					b.setCount(rs.getInt("count"));
					b.setPrice(rs.getInt("price"));
					b.setImage(rs.getString("image"));
					
					list.add(b);
				} while(rs.next());
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - bookSearch() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	//���� �˻� �����
	public int bookSearchCount(String search) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT COUNT(*) FROM book WHERE title LIKE ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + search + "%");
			
			rs = ps.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - bookSearchCount() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
	}

	//�ش� ���� ��ȸ�� ����
	public int bookViewsUpdate(String no) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "UPDATE book_sub SET views=views+1 WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, no);
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - bookViewsUpdate() ����");
		} finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
	
	//��� ���� ���
	public ArrayList<Book> getAllBook() {
		ArrayList<Book> list = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
				
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * FROM book";
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
			list = new ArrayList<>();
				do {
					Book b = new Book();
					b.setNo(rs.getString("no"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					b.setPublisher(rs.getString("publisher"));
					b.setPub_date(rs.getDate("pub_date"));
					b.setPrice(rs.getInt("price"));
					b.setCount(rs.getInt("count"));
					b.setImage(rs.getString("image"));
					
					list.add(b);
				} while(rs.next());
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getAllBook() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	/*
	 * Error [java.sql.SQLException: ������ ���� ��� ���տ� �������� �۾��� ����Ǿ����ϴ�.] ��ó:
	 * http://hanuli7.tistory.com/entry/javasqlSQLException-������-����-���-���տ�-��������-�۾���-
	 * ����Ǿ����ϴ�-first [��.��.â.��.] ResultSet�� �⺻������ �Ĺ��� Ž���� �����ϵ��� ������� ��ü ���� �ʵ� �� ����
	 * �Ѱ��ָ� ���Ƿ� ��, �Ĺ��� Ž���� �� �� �ִ�.
	 */
	// �ֽ� ���� 15��
	/*@Override
	public ArrayList<Book> getBook15() {
		ArrayList<Book> arr = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = datasource.getConnection();

			String sql = "SELECT * FROM book";
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			rs.last();

			int count = 0;
			do { // �Ǹ����� ��Ҵ� �ǳʶٰ� ��
				Book b = new Book();
				b.setNo(rs.getString("no"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author"));
				b.setPublisher(rs.getString("publisher"));
				b.setPub_date(rs.getDate("pub_date"));
				b.setPrice(rs.getInt("price"));
				b.setCount(rs.getInt("count"));

				String image = rs.getString("image");
				if (image != null)
					b.setImage(image);
				else
					b.setImage("000.jpg");

				arr.add(b);
				if (count > 15)
					break;
				count += 1;
			} while (rs.previous());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getBookNos ����");
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arr;
	}*/

	// ���� ����
	@Override
	public Book getBookInfo(String no) {
		Book b = new Book();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = datasource.getConnection();

			String sql = "SELECT * FROM book WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, no);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				b.setNo(no);
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author"));
				b.setPublisher(rs.getString("publisher"));
				b.setPub_date(rs.getDate("pub_date"));
				b.setPrice(rs.getInt("price"));
				b.setCount(rs.getInt("count"));

				String image = rs.getString("image");
				if (image != null)
					b.setImage(image);
				else
					b.setImage("000.jpg");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getBookInfo ����");
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	//�ش� ���� �ΰ� ����
	@Override
	public BookSub getBookSubInfo(String no) {
		BookSub bSub = new BookSub();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * FROM book_sub WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, no);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				String intro = rs.getString("intro");
				String list = rs.getString("list");
				String pub_intro = rs.getString("pub_intro");
				String review = rs.getString("review");
				
				bSub.setViews(rs.getInt("views"));
				bSub.setTag(rs.getString("tag"));
				bSub.setTag_main(rs.getString("tag_main"));
				bSub.setIntro(intro != null ? intro : "�غ� ���Դϴ�.");
				bSub.setList(list != null ? list : "�غ� ���Դϴ�.");
				bSub.setPub_intro(pub_intro != null ? pub_intro : "�غ� ���Դϴ�.");
				bSub.setReview(review != null ? review : "�غ� ���Դϴ�.");
				bSub.setAdd_date(rs.getDate("add_date"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getBookSubInfo() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return bSub;
	}

	//�Ű� ���� ���
	@Override
	public ArrayList<Book> getNewBook(int start, int end) {
		ArrayList<Book> list = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT * " + 
						 "FROM(SELECT no, title, author, publisher, pub_date, price, count, image, rownum as num " + 
						 	  "FROM book " + 
						 	  "WHERE pub_date > ADD_MONTHS(sysdate, -18) " + 
						 	  "ORDER BY pub_date DESC, TITLE) " + 
						 "WHERE num >= " + start +" AND num <= " + end;
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					Book b = new Book();
					b.setNo(rs.getString("no"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					b.setPublisher(rs.getString("publisher"));
					b.setPub_date(rs.getDate("pub_date"));
					b.setPrice(rs.getInt("price"));
					b.setCount(rs.getInt("count"));
					b.setImage(rs.getString("image"));
					
					list.add(b);
				} while(rs.next());
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getNewBook() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//����Ʈ����(�α�) ���� ���
	@Override
	public ArrayList<Book> getBestBook(int start, int end) {
		ArrayList<Book> list = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * "
					   + "FROM (SELECT no, title, author, publisher, pub_date, price, count, image, rownum as num "
					   		 + "FROM(SELECT b.no, b.title, b.author, b.publisher, b.pub_date, b.price, b.count, b.image, bs.views, order_count "
					   		 	  + "FROM (SELECT b.no, sum(o.count) as order_count "
					   		 	  	    + "FROM book b, bespeak o "
					   		 	  	    + "WHERE b.no = o.no "
					   		 	  	    + "GROUP BY b.no) p, book b, book_sub bs "
					   		 	  + "WHERE b.no = bs.no AND b.no = p.no "
					   		 	  + "ORDER BY order_count DESC, views DESC)) "
					   + "WHERE num >= " + start + " AND num <= " + end;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					Book b = new Book();
					b.setNo(rs.getString("no"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					b.setPublisher(rs.getString("publisher"));
					b.setPub_date(rs.getDate("pub_date"));
					b.setPrice(rs.getInt("price"));
					b.setCount(rs.getInt("count"));
					b.setImage(rs.getString("image"));
					
					list.add(b);
				} while(rs.next());
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
		
	//��õ ���� ���
	@Override
	public ArrayList<Book> getGoodTagBook(int start, int end) {
		ArrayList<Book> list = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = datasource.getConnection();

			String sql = "SELECT * "
					   + "FROM (SELECT b.no, title, author, publisher, pub_date, price, count, image, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num "
					   		 + "FROM book_sub bs, book b "
					   		 + "WHERE b.no = bs.no AND tag = 1 "
					   		 + "ORDER BY add_date) "
					   + "WHERE num >= " + start + " AND num <= " + end;
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				
				do {
					Book b = new Book();
					b.setNo(rs.getString("no"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					b.setPublisher(rs.getString("publisher"));
					b.setPub_date(rs.getDate("pub_date"));
					b.setPrice(rs.getInt("price"));
					b.setCount(rs.getInt("count"));
					b.setImage(rs.getString("image"));
					
					list.add(b);
				} while(rs.next());
			}
			/*
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs.last();
			do {
				Book b = new Book();
				b.setNo(rs.getString("no"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author"));
				b.setPublisher(rs.getString("publisher"));
				b.setPub_date(rs.getDate("pub_date"));
				b.setPrice(rs.getInt("price"));
				b.setCount(rs.getInt("count"));
				b.setImage(rs.getString("image"));
				
				books.add(b);
			} while (rs.previous());*/
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getGoodTagBook() ����");
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	//���� ���
	@Override
	public ArrayList<Book> getBookList(int start, int end) {
		ArrayList<Book> books = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = datasource.getConnection();

			String sql = "SELECT * "
					+ "FROM (SELECT no, title, author, publisher, pub_date, price, count, image, rownum as num "
					+ "FROM (SELECT * FROM book ORDER BY no DESC)) " + "WHERE num >= " + start + " AND num <= " + end;
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();
			while (rs.next()) {
				Book b = new Book();

				b.setNo(rs.getString("no"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author"));
				b.setPublisher(rs.getString("publisher"));
				b.setPub_date(rs.getDate("pub_date"));
				b.setPrice(rs.getInt("price"));
				b.setCount(rs.getInt("count"));
				b.setImage(rs.getString("image"));

				books.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getBookList() ����");
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return books;
	}

	//���� �ΰ� ���� ���
	@Override
	public ArrayList<BookSub> getBookSubList(int start, int end) {
		ArrayList<BookSub> bSubs = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * " + 
						 "FROM (SELECT no, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num " + 
						 		"FROM (SELECT * FROM book_sub ORDER BY no DESC)) " + 
						 "WHERE num >= " + start + " AND num <= " + end;
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				BookSub bs = new BookSub();
				bs.setNo(rs.getString("no"));
				bs.setTag(rs.getString("tag"));
				bs.setTag_main(rs.getString("tag_main"));
				bs.setIntro(rs.getString("intro"));
				bs.setList(rs.getString("list"));
				bs.setPub_intro(rs.getString("pub_intro"));
				bs.setReview(rs.getString("review"));
				bs.setAdd_date(rs.getDate("add_date"));
				
				bSubs.add(bs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getBookSubList ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
		return bSubs;
	}

	//tag_main ��
	public int getTag_mainCount() {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT COUNT(*) FROM tag_main";
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getTag_mainCount() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
	}
	
	//�����±� ���
	@Override
	public ArrayList<BookSub> getTag_mainList() {
		ArrayList<BookSub> tags = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * FROM tag_main";
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				BookSub bs = new BookSub();
				bs.setTag_main(rs.getString(1));
				tags.add(bs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getBookTag_main() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return tags;
	}

	//tag ���
	/*@Override
	public ArrayList<String> getTagList() {
		ArrayList<String> tags = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * FROM tag";
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) { 
				tags.add(rs.getString(1));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getBookTag() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return tags;
	}*/
	
	//����Ʈ(�α�) ���� ����
	@Override
	public int getBestCount() {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT COUNT(*) "
				       + "FROM(SELECT b.no, views, sum(o.count) as order_count "
				       		+ "FROM book_sub b, bespeak o "
				       		+ "WHERE b.no = o.no "
				       		+ "GROUP BY b.no, views)";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getBestCount() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
	}
	
	//�Ű� ���� ����
	@Override
	public int getNewCount() {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT COUNT(*) FROM book WHERE pub_date > ADD_MONTHS(sysdate, -18)";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch(SQLException e) { 
			e.printStackTrace();
			System.out.println("BookPersImpl - getNewCount() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	//��õ ���� ����
	@Override
	public int getGoodCount() {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT COUNT(*) FROM book b, book_sub bs WHERE b.no = bs.no AND bs.tag = 1";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getGoodCount() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
		
	//tag/tag_main ���� ����
	@Override
	public int getTagMainCount(String tag) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT COUNT(*) FROM book_sub WHERE tag_main = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, tag);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getTagMainCount ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	//tag_main ���� ���
	@Override
	public ArrayList<Book> getTagMainBook(String tagMain, int start, int end) {
		ArrayList<Book> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * " + 
						 "FROM (SELECT b.no, title, author, publisher, pub_date, price, count, image, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num " + 
						 		"FROM (SELECT * FROM book ORDER BY no DESC) b join (SELECT * FROM book_sub ORDER BY no DESC) bs " + 
						 				"on b.no = bs.no " + 
						 		"WHERE tag_main = ?) " + 
						 "WHERE num >= " + start +" AND num <= " + end;
			ps = conn.prepareStatement(sql);
			ps.setString(1, tagMain);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Book b = new Book();
				b.setNo(rs.getString("no"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author"));
				b.setPublisher(rs.getString("publisher"));
				b.setPub_date(rs.getDate("pub_date"));
				b.setPrice(rs.getInt("price"));
				b.setCount(rs.getInt("count"));
				b.setImage(rs.getString("image"));

				list.add(b);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getTagMainBook() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	//�������� ���
	@Override
	public ArrayList<Book> getDomesticBook(int start, int end) {
		ArrayList<Book> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * " + 
						 "FROM (SELECT b.no, title, author, publisher, pub_date, price, count, image, tag, tag_main, intro, list, pub_intro, review, add_date, rownum as num " + 
						 		"FROM (SELECT * FROM book ORDER BY no DESC) b join (SELECT * FROM book_sub ORDER BY no DESC) bs " + 
						 			"on b.no = bs.no " + 
						 		"WHERE tag_main != '21') " + 
						 "WHERE num >= " + start + " AND num <= " + end;
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Book b = new Book();
				b.setNo(rs.getString("no"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author"));
				b.setPublisher(rs.getString("publisher"));
				b.setPub_date(rs.getDate("pub_date"));
				b.setPrice(rs.getInt("price"));
				b.setCount(rs.getInt("count"));
				b.setImage(rs.getString("image"));
				
				list.add(b);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getDomesticBook() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	//���� ����
	@Override
	public int getBookCnt() {
		int cnt = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
				
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT COUNT(*) FROM book";
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getBookCnt ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return cnt;
	}

	//���� ����
	@Override
	public int bookUpdate(Book book) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "UPDATE book SET title=?, author=?, publisher=?, pub_date=?, price=?, count=?, image=? WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getPublisher());
			ps.setDate(4, book.getPub_date());
			ps.setInt(5, book.getPrice());
			ps.setInt(6, book.getCount());
			ps.setString(7, book.getImage());
			ps.setString(8, book.getNo());
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - bookUpdate ����");
		} finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	//���� ���� ����
	@Override
	public int orderBookCountMul(String order_num) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			Map<String, Integer> map = new HashMap<>();
			String sql = "SELECT b.no, bs.count as orc, b.count as bc FROM bespeak bs, book b WHERE bs.no = b.no AND order_num = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, order_num);
			
			//�ֹ��� ���� = ��� - �ֹ�����
			rs = ps.executeQuery();
			if(rs.next()) {
				do {
					String no = rs.getString("no");
					int count = rs.getInt("bc") - rs.getInt("orc");
					map.put(no, count);
				} while(rs.next());
				rs.close();
			}
			ps.close();
			
			//��� ����� �� Ȯ��
			boolean flg = false;
			for(Entry<String, Integer> e : map.entrySet()) {
				//��� ����ϸ� ���� ����
				if(e.getValue() < 0) {
					flg = true;
				}
			}
			
			//��� ����ϸ� ���� ����
			if(!flg) {
				sql = "UPDATE book SET count=? WHERE no=?";
				ps = conn.prepareStatement(sql);
				for(Entry<String, Integer> e : map.entrySet()) {
					ps.setInt(1, e.getValue());
					ps.setString(2, e.getKey());
					
					cnt = ps.executeUpdate();
				}
			} else {
				cnt = 9;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - bookCountMul() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
	
	@Override
	public int refundBookCountAdd(String order_num) {
		Map<String, Integer> map = new HashMap<>();
		int cnt = 0;
		String sql = "";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			//ȯ���� �ֹ��� ���� ���� + ���
			sql = "SELECT b.no, bk.count as orc, b.count as bc FROM bespeak bk, book b WHERE bk.no = b.no AND bk.order_num = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, order_num);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				do {
					String no = rs.getString("no");
					int count = rs.getInt("orc") + rs.getInt("bc");
					map.put(no, count);
				} while(rs.next());
			}
			ps.close();
			
			//���� ���� �ٽ� �߰��ϱ�
			sql = "UPDATE book SET count=? WHERE no=?";
			ps = conn.prepareStatement(sql);
			for(Entry<String, Integer> e : map.entrySet()) {
				ps.setInt(1, e.getValue());
				ps.setString(2, e.getKey());

				cnt = ps.executeUpdate();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - refundBookCountAdd() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
	
	//���� ����
	@Override
	public int bookDelete(String no) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "DELETE FROM book WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, no);
			
			cnt = ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - bookDelete() ����");
		} finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
	
	//���� �ټ� ����
	public int bookAllDelete(String[] nos) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "DELETE FROM book WHERE no = ?";
			ps = conn.prepareStatement(sql);
			
			for(String no : nos) {
				ps.setString(1, no);
				cnt = ps.executeUpdate();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - bookAllDelete() ����");
		} finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
	}

	//���� �߰�
	@Override
	public int bookInsert(Book book) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "INSERT INTO book VALUES (LPAD(book_sq.nextval, 4, '0'), ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getPublisher());
			ps.setDate(4, book.getPub_date());
			ps.setInt(5, book.getPrice());
			ps.setInt(6, book.getCount());
			ps.setString(7, book.getImage());
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - bookInsert() ����");
		} finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	//���� �ΰ� ���� ����
	@Override
	public int bookSubUpdate(BookSub bSub) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "UPDATE book_sub SET tag=?, tag_main=?, intro=?, list=?, pub_intro=?, review=? WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, bSub.getTag());
			ps.setString(2, bSub.getTag_main());
			ps.setString(3, bSub.getIntro());
			ps.setString(4, bSub.getList());
			ps.setString(5, bSub.getPub_intro());
			ps.setString(6, bSub.getReview());
			ps.setString(7, bSub.getNo());
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - bookSubUpdate() ����");
		} finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	//���� �ΰ� ���� �߰�
	@Override
	public int bookSubInsert(BookSub bSub) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "INSERT INTO book_sub (no, tag, tag_main, intro, list, pub_intro, review) "
					   + "VALUES (LPAD(book_sq.currval, 4, '0'), ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, bSub.getTag());
			ps.setString(2, bSub.getTag_main());
			ps.setString(3, bSub.getIntro());
			ps.setString(4, bSub.getList());
			ps.setString(5, bSub.getPub_intro());
			ps.setString(6, bSub.getReview());
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - bookSubInsert() ����");
		} finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	//�ֱ� �� ���� ����
	@Override
	public ArrayList<RecentBook> recentUpdate(ArrayList<RecentBook> list, Book b) {
		ArrayList<RecentBook> reb = list;
		RecentBook rb = new RecentBook();
		rb.setNo(b.getNo());
		rb.setImage(b.getImage());
		
		try {
			if(reb != null & reb.size() > 0) {
				//�ֱٰ� �ߺ�X
				RecentBook last = reb.get(reb.size() - 1);
				if(!last.getNo().equals(b.getNo())) {

					//�ټ� ����
					if(reb.size() >= 5) {
						reb.remove(0);
					}	
					reb.add(rb);
				}
			} else {
				reb.add(rb);
			}
		} catch(NullPointerException e) {
			e.printStackTrace();
			System.out.println("ĳ�� ���� ������ ��� �߻�");
		}
		return reb; 
	}

	//�� �Ǹ� ������
	@Override
	public int getSalesTotalCount() {
		int sales = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT SUM(count) FROM bespeak WHERE order_state NOT IN(0, 8, 9)";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				sales = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getTotalSalesCount() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return sales;
	}

	//�� �����
	@Override
	public int getSalesTotalAmount() {
		int sales = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT SUM(price * count) FROM bespeak WHERE order_state NOT IN(0, 8, 9)";
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				sales = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getTotalSalesAmount() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return sales;
	}
	
	//no-title
	public Map<String, String> getNoTitle() {
		Map<String, String> map = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
				
		try {
			 conn = datasource.getConnection();
			 
			 String sql = "SELECT no, title FROM book";
			 ps = conn.prepareStatement(sql);
			 
			 rs = ps.executeQuery();
			 if(rs.next()) {
				 map = new HashMap<>();
				 do {
					 String no = rs.getString("no");
					 String title = rs.getString("title");
					 map.put(no, title);
				 } while(rs.next());
			 }
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getNoTitle() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return map;
	}

	//���� �±׺� �Ǹŷ�
	public Map<String, Integer> getTagSalesTotal() {
		Map<String, Integer> map = new HashMap<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT tag, sum(count) as count " + 
						 "FROM (SELECT * "
						 	 + "FROM bespeak b, book_sub bs "
						 	 + "WHERE b.no = bs.no AND order_state NOT IN(0, 8, 9)) " + 
						 "GROUP BY tag, substr(order_num, 1, 4) " + 
						 "HAVING substr(order_num, 1, 4) = TO_CHAR(sysdate, 'yyyy')";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String tag = rs.getString("tag");
				int count = rs.getInt("count");
				map.put(tag + "S", count);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getTagTotal() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	//���� �±׺� ȯ�ҷ�
	@Override
	public Map<String, Integer> getTagRefundTotal() {
		Map<String, Integer> map = new HashMap<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT tag, sum(count) as count " + 
						 "FROM (SELECT * "
						 	 + "FROM bespeak b, book_sub bs "
						 	 + "WHERE b.no = bs.no AND order_state IN(8, 9)) " + 
						 "GROUP BY tag, substr(order_num, 1, 4) " + 
						 "HAVING substr(order_num, 1, 4) = TO_CHAR(sysdate, 'yyyy')";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String tag = rs.getString("tag");
				int count = rs.getInt("count");
				map.put(tag + "R", count);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getTagRefundTotal() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return map;
	}

	//���� �����
	@Override
	public Map<String, Integer> getYearSalesTotalAmount() {
		Map<String, Integer> map = new HashMap<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT substr(order_num, 1, 4) as year, SUM(count * PRICE) as sales " + 
						 "FROM (SELECT * "
						 	 + "FROM bespeak "
						 	 + "WHERE order_state NOT IN(0, 8, 9)) " + 
						 "GROUP BY substr(order_num, 1, 4)";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String year = rs.getString("year");
				int sales = rs.getInt("sales");
				map.put(year, sales);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("getYearSalesTotal()");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	//���� �Ǹŷ�
	@Override
	public Map<String, Integer> getYearSalesTotalCount() {
		Map<String, Integer> map = new HashMap<>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT substr(order_num, 1, 4) as year, SUM(count) as count " + 
						 "FROM (SELECT * "
						 	 + "FROM bespeak "
						 	 + "WHERE order_state NOT IN(0, 8, 9)) " + 
						 "GROUP BY substr(order_num, 1, 4)";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String year = rs.getString("year");
				int count = rs.getInt("count");
				map.put(year, count);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("getYearSalesTotal()");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	//���� ȯ�Ҿ�
	@Override
	public Map<String, Integer> getYearRefundTotalAmount() {
		Map<String, Integer> map = new HashMap<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT substr(order_num, 1, 4) as year, SUM(price*count) as sales " + 
						 "FROM bespeak " + 
						 "WHERE order_state IN(8, 9) " + 
						 "GROUP BY substr(order_num, 1, 4)";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String year = rs.getString("year");
				int sales = rs.getInt("sales");
				map.put(year, sales);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getYearRefundTotalCount() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	//�ְ��� �±׺� �����
	@Override
	public Map<String, Integer> getTagWeeklyCount() {
		Map<String, Integer> map = new HashMap<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			//�̴� �Ϻ� �±׺� �����
			String sql = "SELECT md, SUM(price * count) as sales, tag_main " + 
						 "FROM (SELECT substr(b.order_num, 1, 8) as md, b.price, b.count, bs.tag_main " + 
						 		"FROM bespeak b, book_sub bs " + 
						 		"WHERE b.no = bs.no " + 
						 			"AND TO_DATE(SUBSTR(b.order_num, 1, 8), 'yyyyMMdd') >= TO_CHAR(TRUNC(sysdate,'MM'),'YYYYMMdd') " + 
						 			"AND TO_DATE(SUBSTR(b.order_num, 1, 8), 'yyyyMMdd') <= LAST_DAY(sysdate)) " + 
						 "GROUP BY md, tag_main " + 
						 "ORDER BY md, tag_main";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			Calendar cal = Calendar.getInstance();
			while(rs.next()) {
				//�ְ� ���ϱ�
				String date = rs.getString("md");
				
				int year = Integer.parseInt(date.substring(0, 4));
				int month = Integer.parseInt(date.substring(4, 6));
				int day = Integer.parseInt(date.substring(6, 8));
				
				cal.set(year, month, day);
				String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
				
				//Map.Key = �ְ� + �±׸�. �ְ��� �±� �ߺ� ����
				String tag = week + rs.getString("tag_main");
				int sales = rs.getInt("sales");

				//System.out.println(year + "/ " + month+ "/ " + day + " : " + week + "�� - " + tag);
				
				//ù put�� �ƴϸ� �ߺ� Ȯ�� �� put
				if(map.size() > 0) {
					boolean flg = false;
					//key�� ��ġ�� value ���ϱ�
					for(Entry<String, Integer> e : map.entrySet()) {
						if(e.getKey().equals(tag)) {
							e.setValue(e.getValue() + sales);
							flg = true;
						}
					}
					//key�� ��ġ�� ������ �߰�
					if(!flg) map.put(tag, sales);
				} else {
					map.put(tag, sales);
				}
			}
			
			System.out.println("----------test---------");	
			//TEST
			for(Entry<String, Integer> e : map.entrySet()) {
				String week = e.getKey().substring(0, 1);
				String tag = e.getKey().substring(1);
				
				System.out.println(week + "�� - " + tag + " : " + e.getValue());
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getTagWeeklyCount() ����");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
//ROWNUM �� �ִ������� WHERE����, ORDER BY ���� ������ �ȵȴ�.