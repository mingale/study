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

	//도서 검색
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
			System.out.println("BookPersImpl - bookSearch() 실패");
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

	//도서 검색 결과수
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
			System.out.println("BookPersImpl - bookSearchCount() 실패");
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

	//해당 도서 조회수 증가
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
			System.out.println("BookPersImpl - bookViewsUpdate() 실패");
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
	
	//모든 도서 목록
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
			System.out.println("BookPersImpl - getAllBook() 실패");
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
	 * Error [java.sql.SQLException: 전방향 전용 결과 집합에 부적합한 작업이 수행되었습니다.] 출처:
	 * http://hanuli7.tistory.com/entry/javasqlSQLException-전방향-전용-결과-집합에-부적합한-작업이-
	 * 수행되었습니다-first [잡.학.창.고.] ResultSet은 기본적으로 후방향 탐색만 가능하도록 만들어진 객체 정적 필드 두 개를
	 * 넘겨주면 임의로 전, 후방향 탐색을 할 수 있다.
	 */
	// 최신 도서 15권
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
			do { // 맨마지막 요소는 건너뛰게 됨
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
			System.out.println("BookPersImpl - getBookNos 실패");
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

	// 도서 정보
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
			System.out.println("BookPersImpl - getBookInfo 실패");
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

	//해당 도서 부가 정보
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
				bSub.setIntro(intro != null ? intro : "준비 중입니다.");
				bSub.setList(list != null ? list : "준비 중입니다.");
				bSub.setPub_intro(pub_intro != null ? pub_intro : "준비 중입니다.");
				bSub.setReview(review != null ? review : "준비 중입니다.");
				bSub.setAdd_date(rs.getDate("add_date"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getBookSubInfo() 실패");
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

	//신간 도서 목록
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
			System.out.println("BookPersImpl - getNewBook() 실패");
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
	
	//베스트셀러(인기) 도서 목록
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
		
	//추천 도서 목록
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
			System.out.println("BookPersImpl - getGoodTagBook() 실패");
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

	//도서 목록
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
			System.out.println("BookPersImpl - getBookList() 실패");
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

	//도서 부가 정보 목록
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
			System.out.println("BookPersImpl - getBookSubList 실패");
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

	//tag_main 수
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
			System.out.println("BookPersImpl - getTag_mainCount() 실패");
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
	
	//메인태그 목록
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
			System.out.println("BookPersImpl - getBookTag_main() 실패");
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

	//tag 목록
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
			System.out.println("BookPersImpl - getBookTag() 실패");
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
	
	//베스트(인기) 도서 개수
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
			System.out.println("BookPersImpl - getBestCount() 실패");
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
	
	//신간 도서 개수
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
			System.out.println("BookPersImpl - getNewCount() 실패");
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

	//추천 도서 개수
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
			System.out.println("BookPersImpl - getGoodCount() 실패");
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
		
	//tag/tag_main 도서 개수
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
			System.out.println("BookPersImpl - getTagMainCount 실패");
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

	//tag_main 도서 목록
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
			System.out.println("BookPersImpl - getTagMainBook() 실패");
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

	//국내도서 목록
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
			System.out.println("BookPersImpl - getDomesticBook() 실패");
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

	//도서 개수
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
			System.out.println("BookPersImpl - getBookCnt 실패");
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

	//도서 수정
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
			System.out.println("BookPersImpl - bookUpdate 실패");
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

	//도서 수량 수정
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
			
			//주문후 수량 = 재고량 - 주문수량
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
			
			//재고가 충분한 지 확인
			boolean flg = false;
			for(Entry<String, Integer> e : map.entrySet()) {
				//재고가 충분하면 수량 수정
				if(e.getValue() < 0) {
					flg = true;
				}
			}
			
			//재고가 충분하면 수량 감소
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
			System.out.println("BookPersImpl - bookCountMul() 실패");
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
			
			//환불할 주문의 도서 수량 + 재고량
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
			
			//도서 수량 다시 추가하기
			sql = "UPDATE book SET count=? WHERE no=?";
			ps = conn.prepareStatement(sql);
			for(Entry<String, Integer> e : map.entrySet()) {
				ps.setInt(1, e.getValue());
				ps.setString(2, e.getKey());

				cnt = ps.executeUpdate();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - refundBookCountAdd() 실패");
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
	
	//도서 삭제
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
			System.out.println("BookPersImpl - bookDelete() 실패");
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
	
	//도서 다수 삭제
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
			System.out.println("BookPersImpl - bookAllDelete() 실패");
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

	//도서 추가
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
			System.out.println("BookPersImpl - bookInsert() 실패");
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

	//도서 부가 정보 수정
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
			System.out.println("BookPersImpl - bookSubUpdate() 실패");
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

	//도서 부가 정보 추가
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
			System.out.println("BookPersImpl - bookSubInsert() 실패");
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

	//최근 본 도서 수정
	@Override
	public ArrayList<RecentBook> recentUpdate(ArrayList<RecentBook> list, Book b) {
		ArrayList<RecentBook> reb = list;
		RecentBook rb = new RecentBook();
		rb.setNo(b.getNo());
		rb.setImage(b.getImage());
		
		try {
			if(reb != null & reb.size() > 0) {
				//최근과 중복X
				RecentBook last = reb.get(reb.size() - 1);
				if(!last.getNo().equals(b.getNo())) {

					//다섯 개면
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
			System.out.println("캐시 완전 비우기할 경우 발생");
		}
		return reb; 
	}

	//총 판매 도서수
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
			System.out.println("BookPersImpl - getTotalSalesCount() 실패");
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

	//총 매출액
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
			System.out.println("BookPersImpl - getTotalSalesAmount() 실패");
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
			System.out.println("BookPersImpl - getNoTitle() 실패");
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

	//올해 태그별 판매량
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
			System.out.println("BookPersImpl - getTagTotal() 실패");
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
	
	//올해 태그별 환불량
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
			System.out.println("BookPersImpl - getTagRefundTotal() 실패");
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

	//연간 매출액
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

	//연간 판매량
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

	//연간 환불액
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
			System.out.println("BookPersImpl - getYearRefundTotalCount() 실패");
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
	
	//주간별 태그별 매출액
	@Override
	public Map<String, Integer> getTagWeeklyCount() {
		Map<String, Integer> map = new HashMap<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			//이달 일별 태그별 매출액
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
				//주간 구하기
				String date = rs.getString("md");
				
				int year = Integer.parseInt(date.substring(0, 4));
				int month = Integer.parseInt(date.substring(4, 6));
				int day = Integer.parseInt(date.substring(6, 8));
				
				cal.set(year, month, day);
				String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
				
				//Map.Key = 주간 + 태그명. 주간별 태그 중복 차단
				String tag = week + rs.getString("tag_main");
				int sales = rs.getInt("sales");

				//System.out.println(year + "/ " + month+ "/ " + day + " : " + week + "주 - " + tag);
				
				//첫 put이 아니면 중복 확인 후 put
				if(map.size() > 0) {
					boolean flg = false;
					//key가 겹치면 value 더하기
					for(Entry<String, Integer> e : map.entrySet()) {
						if(e.getKey().equals(tag)) {
							e.setValue(e.getValue() + sales);
							flg = true;
						}
					}
					//key가 겹치지 않으면 추가
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
				
				System.out.println(week + "주 - " + tag + " : " + e.getValue());
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("BookPersImpl - getTagWeeklyCount() 실패");
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
//ROWNUM 이 있는절에는 WHERE절과, ORDER BY 절이 붙으면 안된다.