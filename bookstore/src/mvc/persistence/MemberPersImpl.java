package mvc.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import mvc.vo.Book;
import mvc.vo.Cart;
import mvc.vo.Member;
import mvc.vo.Bespeak;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberPersImpl implements MemberPers{

	DataSource datasource;

	private static MemberPersImpl member = new MemberPersImpl();
	
	public static MemberPersImpl getInstance() {
		return member;
	}
	
	private MemberPersImpl() {
		try {
			Context context = new InitialContext();
			datasource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g/bms");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//아이디 중복 확인
	@Override
	public int confirmId(String strId) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * FROM member WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, strId);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				cnt = 1;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl confirmId 실패");
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

	//이메일 인증key 존재 확인
	public int confirmEmailKey(String key) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
		 	conn = datasource.getConnection();
		 	
		 	String sql = "SELECT * FROM member WHERE e_key=?";
		 	ps = conn.prepareStatement(sql);
		 	ps.setString(1, key);
		 	
		 	rs = ps.executeQuery();
		 	
		 	//인증키가 존재하면
		 	if(rs.next()) {
		 		String id = rs.getString("id");
		 		
		 		ps.close();
		 		
		 		//해당 계정의 권한 등급 UP
		 		sql = "UPDATE member SET rating=3 WHERE id=?";
		 		ps = conn.prepareStatement(sql);
		 		ps.setString(1, id);
		 		
		 		cnt = ps.executeUpdate();
		 	}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("confirmEmailKey() 실패");
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
	
	//로그인
	@Override
	public int signIn(String strId, String strPwd) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * FROM member WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, strId);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				if(strPwd.equals(rs.getString("pwd"))) {
					cnt = 1;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - SignIn 실패");
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
	
	//회원가입
	@Override
	public int signUp(Member m) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "INSERT INTO member (id, pwd, name, phone, email, address, e_key) VALUES (?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, m.getId());
			ps.setString(2, m.getPwd());
			ps.setString(3, m.getName());
			ps.setString(4, m.getPhone());
			ps.setString(5, m.getEmail());
			ps.setString(6, m.getAddress());
			ps.setString(7, m.getKey());
			
			cnt = ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl signUp 실패");
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
	
	//회원 정보
	@Override
	public Member getMemberInfo(String strId) {
		Member m = new Member();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * FROM member WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, strId);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				m.setId(strId);
				m.setPwd(rs.getString("pwd"));
				m.setName(rs.getString("name"));
				m.setPhone(rs.getString("Phone"));
				m.setEmail(rs.getString("email"));
				m.setAddress(rs.getString("address"));
				m.setMemo(rs.getString("memo"));
				m.setRating(rs.getInt("rating"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null)  conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}

	//이메일 존재 여부
	public String confirmEmail(String email) {
		String id = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn =datasource.getConnection();
			
			String sql = "SELECT id FROM member WHERE email=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getString("id");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("confirmEmail() 실패");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
	//인증키 수정
	public int memberEmailKeyUpdate(String id, String key) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "UPDATE member SET e_key=? WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, key);
			ps.setString(2, id);
			
			cnt = ps.executeUpdate();
			System.out.println(id + " - " + key + ": 아아디/비밀번호 인증 메일");
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("pwdFindEmailKey() 실패");
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
	
	//메일 인증
	/*
	 * gmail 이용 전용 설정
	 * gmail > 환경 설정 > 전달 및 pop/IMAP > IMAP 액세스 > IMAP 사용으로 변경
	 * 내 계정 > 로그인 및 보안 > 연결된 앱 및 사이트 > 보안 수준이 낮은 앱 허용 : 사용으로 변경(2단계 보안 사용 중이면 설정 못함)
	 */
	@Override
	public int sendGmail(String toEmail, String key, int view) {
		int cnt = 0;
		
		//gmail 계정으로 이메일 전송
		final String userName = "promessa7393";
		final String password = "test7393";
		
		Properties p = new Properties();
		p.put("mail.smtp.user", userName);
		p.put("mail.smtp.password", password);
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "25");
		p.put("mail.debug", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.EnableSSL.enable", "true");
		p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.setProperty("mail.smtp.socketFactory.fallback", "false");
		p.setProperty("mail.smtp.port", "465");
		p.setProperty("mail.smtp.socketFactory.port", "465");
		
		Session session = Session.getInstance(p, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		
		try {
			Message msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress("admin@bookstore.com")); //보내는 이???? 보내는 이에 gmail 계정으로 되어있음
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); //받는 이
			
			String content = "";
			
			if(view == 0) {
			content = "BMS 회원가입 인증 메일입니다. 인증을 눌러 회원가입을 완료하세요.<br>"
					+ "<a href='http://localhost:8080/bookstore/emailCheck.do?key=" + key + "&view=" + view + "'>인증</a>"; //email 내용
			msg.setSubject("BMS 회원가입 인증 메일"); //email 제목
			} else if(view == 1) {
				content = "BMS 아이디 찾기 인증 메일입니다. 인증을 눌러 아이디를 찾으세요.<br>"
						+ "<a href='http://localhost:8080/bookstore/emailCheck.do?key=" + key + "&view=" + view + "'>인증</a>"; //email 내용
				msg.setSubject("BMS 아이디 찾기 인증 메일"); //email 제목
			} else if(view == 2) {
				content = "BMS 비밀번호 찾기 인증 메일입니다. 인증을 눌러 비밀번호를 찾으세요.<br>"
						+ "<a href='http://localhost:8080/bookstore/emailCheck.do?key=" + key + "&view=" + view + "'>인증</a>"; //email 내용
				msg.setSubject("BMS 비밀번호 찾기 인증 메일"); //email 제목
			}
			
			msg.setContent(content, "text/html; charset=UTF-8");
			
			System.out.println("Email 전송");
			Transport.send(msg);
			
			System.out.println("Email 전송 완료");
			cnt = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	//인증시 아이디 가져오기
	public String getId(String key) {
		String id = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT id FROM member WHERE e_key=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, key);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getString("id");
				System.out.println(id);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("getId() 실패");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
	//인증시 비밀번호 가져오기
	public String getPwd(String key) {
		String pwd = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT pwd FROM member WHERE e_key=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, key);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				pwd = rs.getString("pwd");
				System.out.println(pwd);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("getPwd() 실패");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return pwd;
	}
	
	//회원 수정
	@Override
	public int memberUpdate(Member m) {
		int cnt = 0;
		
		Connection  conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "UPDATE member SET pwd=?, name=?, phone=?, email=?, address=? WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, m.getPwd());
			ps.setString(2, m.getName());
			ps.setString(3, m.getPhone());
			ps.setString(4, m.getEmail());
			ps.setString(5, m.getAddress());
			ps.setString(6, m.getId());
			
			cnt = ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl memberUpdate 실패");
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
	
	// 회원 탈퇴 - id 남기기
	// email이 unique라 그대로 둠. NOT NULL
	@Override
	public int memberDelete(String strId) {
		int cnt = 0;
		
		Connection  conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "UPDATE member "
						+ "SET pwd='x', name='x', phone='010-0000-0000', address='x', "
							+ "memo='x', rating=3, e_key='0' "
						+ "WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, strId);
			
			cnt = ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl memberUpdate 실패");
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

	//장바구니 추가
	@Override
	public int setCart(String id, Book b, int count) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();

			String sql = "INSERT INTO cart VALUES (?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, b.getNo());
			ps.setString(3, b.getTitle());
			ps.setInt(4, b.getPrice());
			ps.setInt(5, count);
			ps.setString(6, b.getImage());
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - setCart() 실패");
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

	//장바구니 목록
	@Override
	public ArrayList<Cart> getCartList(String id) {
		ArrayList<Cart> list = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * " + 
						 "FROM cart c, book b " + 
						 "WHERE c.no = b.no AND id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					Cart c = new Cart();
					c.setId(rs.getString("id"));
					c.setNo(rs.getString("no"));
					c.setTitle(rs.getString("title"));
					c.setPrice(rs.getInt("price"));
					c.setCount(rs.getInt("count"));
					c.setImage(rs.getString("image"));
					
					list.add(c);
				} while(rs.next());
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - getCartList() 실패");
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

	//장바구니 삭제
	@Override
	public int cartDelete(String id, String no) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "DELETE FROM cart WHERE id=? AND no=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, no);
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - cartDelete() 실패");
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

	//장바구니 수량 수정
	@Override
	public int cartCountUpdate(String id, String no, int count) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "UPDATE cart SET count=? WHERE id=? AND no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, count);
			ps.setString(2, id);
			ps.setString(3, no);
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - cartCountUpdate() 실패");
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

	//장바구니 중복 확인
	@Override
	public int cartCheck(String id, String no) {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * FROM cart WHERE id=? AND no=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, no);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - cartCheck() 실패");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	//장바구니 수량 추가
	@Override
	public int setCartCount(String id, String no, int count) {
		int cnt = 0;
		String sql = "";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			sql = "UPDATE cart SET count=? WHERE id=? AND no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, count);
			ps.setString(2, id);
			ps.setString(3, no);
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - setCartCount() 실패");
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

	//찜 추가
	public int setWishlist(String id, String no){
		int cnt = 0;

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = datasource.getConnection();
			String sql = "INSERT INTO wishlist VALUES (?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, no);
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("setWishlist() 실패");
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

	//찜 중복 확인
	public int wishlistCheck(String id, String no) {
		int cnt = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			conn = datasource.getConnection();
			String sql = "SELECT * FROM wishlist WHERE id=? AND no=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, no);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				cnt = 9;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("setWishlist() 실패");
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
	
	//주문 추가
	@Override
	public int setOrder(String[] nos, Bespeak order) {
		int cnt = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = datasource.getConnection();

			String sql = "";
			int count = 0, price = 0;

			//여러 개 도서 주문시 주문번호를 동일하기 위함
			sql = "SELECT order_sq.nextval FROM dual";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			rs.close();
			ps.close();
			
			for(String no : nos) {
				//cart 정보 가져오기
				sql = "SELECT * FROM cart WHERE id=? AND no=? ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, order.getId());
				ps.setString(2, no);

				rs = ps.executeQuery();
				if(rs.next()) {
					count = rs.getInt("count");
					price = rs.getInt("price");
				}

				ps.close();
				
				//order에 저장하기
				sql = "INSERT INTO bespeak (order_num, id, name, phone, address, no,  count, price, etc, bank, account) "
						+ "VALUES (TO_CHAR(sysdate, 'yyyyMMdd') || LPAD(order_sq.currval, 3, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, order.getId());
				ps.setString(2, order.getName());
				ps.setString(3, order.getPhone());
				ps.setString(4, order.getAddress());
				ps.setString(5, no);
				ps.setInt(6, count);
				ps.setInt(7, price);
				ps.setString(8, order.getEtc());
				ps.setString(9, order.getBank());
				ps.setInt(10, order.getAccount());

				cnt = ps.executeUpdate();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - setOrder() 실패");
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
	
	//바로 주문 추가
	@Override
	public int setNowOrder(String no, Bespeak order, int nowCount) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "";
			int price = 0;
			
			//가격 가져오기
			sql = "SELECT * FROM book WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, no);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				price = rs.getInt("price");
			}
			ps.close();
			
			//추가
			sql = "INSERT INTO bespeak bespeak (order_num, id, name, phone, address, no,  count, price, etc, bank, account) "
					+ "VALUES (TO_CHAR(sysdate, 'yyyyMMdd') || LPAD(order_sq.nextval, 3, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, order.getId());
			ps.setString(2, order.getName());
			ps.setString(3, order.getPhone());
			ps.setString(4, order.getAddress());
			ps.setString(5, no);
			ps.setInt(6, nowCount);
			ps.setInt(7, price);
			ps.setString(8, order.getEtc());
			ps.setString(9, order.getBank());
			ps.setInt(10, order.getAccount());
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - setNowOrder() 실패");
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
	
	//주문 내역
	@Override
	public ArrayList<Bespeak> getMemberOrderList(String id) {
		ArrayList<Bespeak> list = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ArrayList<String> numList = new ArrayList<>();
			ArrayList<Bespeak> allList = new ArrayList<>();
			
			conn = datasource.getConnection();
			
			String sql = "SELECT DISTINCT order_num "
					    + "FROM bespeak "
					    + "WHERE id = ? AND order_state NOT IN(7, 8, 9) "
					    + "ORDER BY order_num DESC";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();

			if(rs.next()) {
				list = new ArrayList<>();
				do {
					numList.add(rs.getString(1));
				} while(rs.next());
				rs.close();
			}
			ps.close();

			sql = "SELECT * "
				+ "FROM bespeak "
				+ "WHERE id = ? AND order_state NOT IN(7, 8, 9) "
				+ "ORDER BY order_num DESC";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				Bespeak o = new Bespeak();

				o.setOrder_num(rs.getString("order_num"));
				o.setId(id);
				o.setName(rs.getString("name"));
				o.setPhone(rs.getString("phone"));
				o.setAddress(rs.getString("address"));
				o.setNo(rs.getString("no"));
				o.setCount(rs.getInt("count"));
				o.setPrice(rs.getInt("price"));
				o.setEtc(rs.getString("etc"));
				o.setAccount(rs.getInt("account"));
				o.setOrder_state(rs.getInt("order_state"));
				
				allList.add(o);
			}
			rs.close();
			ps.close();

			int index = 0; //게시판 list index
			//numList와 같은 주문 번호만 담기. 중복 주문번호(여러 종류 주문) 처리
			for(int i = 0; i < numList.size(); i += 1) { //중복 없는 주문 번호 List
				int count = 0; //중복 수
				
				for(int j = 0; j < allList.size(); j += 1) { //모둔 주문 list
					String num = allList.get(j).getOrder_num();
					
					//numlist의 현재 주문번호와 allList의 주문번호가 같으면
					if(numList.get(i).equals(num)) {
						//주문 번호가 중복되면
						if(count > 0) {
							list.get(index-1).setNos(" 외 " + count + "권"); //중복이므로 외권 표시
						}
						count += 1;

						//주문번호가 같으면(중복X) - 게시판 list 추가
						if(count == 1) {
							list.add(allList.get(j));
							index += 1;
						}
					} else {
						count = 0;
					}
				}
			}

			getNoTitle(list, conn, ps, rs);
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - getOrder() 실패");
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

	//해당 주문 정보
	public ArrayList<Bespeak> getOrderDetail(String order_num){
		ArrayList<Bespeak> list = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * FROM bespeak WHERE order_num = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, order_num);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					Bespeak o = new Bespeak();
					o.setOrder_num(rs.getString("order_num"));
					o.setId(rs.getString("id"));
					o.setName(rs.getString("name"));
					o.setPhone(rs.getString("phone"));
					o.setAddress(rs.getString("address"));
					o.setNo(rs.getString("no"));
					o.setCount(rs.getInt("count"));
					o.setPrice(rs.getInt("price"));
					o.setEtc(rs.getString("etc"));
					o.setBank(rs.getString("bank"));
					o.setAccount(rs.getInt("account"));
					o.setOrder_state(rs.getInt("order_state"));
					
					list.add(o);
				} while(rs.next());
				rs.close();
			}
			ps.close();
			
			//도서명
			sql = "SELECT title, image FROM book WHERE no = ?";
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < list.size(); i += 1) {
				ps.setString(1, list.get(i).getNo());
				rs = ps.executeQuery();
				if(rs.next()) {
					list.get(i).setNos(rs.getString(1));
					list.get(i).setId(rs.getString(2));
					rs.close();
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - getOrderDetail() 실패");
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
	
	//송장 번호
	public String getShipping(String order_num) {
		String ship_num = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT * FROM shipping WHERE order_num = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, order_num);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				ship_num = rs.getString("ship_num");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - getShipping() 실패");
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return ship_num;
	}
	
	//관리자 주문 내역
	@Override
	public ArrayList<Bespeak> getHostOrderList(int start, int end) {
		ArrayList<Bespeak> list = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			//주문 목록 - 첫번째 도서만 도서 번호 표기
			//** rownum에 별칭을 주지 않으면 다음 페이지 쿼리문이 제대로 돌아가지 않는다.
			
			ArrayList<String> numList = new ArrayList<>();
			ArrayList<Bespeak> allList = new ArrayList<>();
			
			String sql = "SELECT * "
					   + "FROM(SELECT order_num, rownum as num "
					        + "FROM (SELECT DISTINCT order_num "
					              + "FROM bespeak "
					              + "WHERE order_state NOT IN(7, 8, 9) "
					              + "ORDER BY order_num DESC)) "
					   + "WHERE num >= " + start +" AND num <= " + end;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if(rs.next()) {
				list = new ArrayList<>();
				do {
					numList.add(rs.getString(1));
				} while(rs.next());
				rs.close();
			}
			ps.close();

			sql = "SELECT order_num, id, name, phone, address, no, count, price, etc, account, order_state FROM (SELECT * FROM bespeak WHERE order_state NOT IN (7, 8, 9) ORDER BY order_num DESC)";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				Bespeak order = new Bespeak();
				
				order.setOrder_num(rs.getString("order_num"));
				order.setId(rs.getString("id"));
				order.setName(rs.getString("name"));
				order.setPhone(rs.getString("phone"));
				order.setAddress(rs.getString("address"));
				order.setNo(rs.getString("no"));
				order.setCount(rs.getInt("count"));
				order.setPrice(rs.getInt("price"));
				order.setEtc(rs.getString("etc"));
				order.setAccount(rs.getInt("account"));
				order.setOrder_state(rs.getInt("order_state"));
				
				allList.add(order);
			}
			rs.close();
			ps.close();

			int index = 0; //게시판 list index
			//numList와 같은 주문 번호만 담기. 중복 주문번호(여러 종류 주문) 처리
			for(int i = 0; i < numList.size(); i += 1) { //중복 없는 주문 번호 List
				int count = 0; //중복 수
				
				for(int j = 0; j < allList.size(); j += 1) { //모둔 주문 list
					String num = allList.get(j).getOrder_num();
					
					//numlist의 현재 주문번호와 allList의 주문번호가 같으면
					if(numList.get(i).equals(num)) {
						//주문 번호가 중복되면
						if(count > 0) {
							list.get(index-1).setNos(" 외 " + count + "권"); //중복이므로 외권 표시
						}
						count += 1;

						//주문번호가 같으면(중복X) - 게시판 list 추가
						if(count == 1) {
							list.add(allList.get(j));
							index += 1;
						}
					} else {
						count = 0;
					}
				}
			}

			//주문 도서 이름
			getNoTitle(list, conn, ps, rs);
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - getOrderList() 실패");
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

	//주문 내역 총 개수
	@Override
	public int getHostOrderCnt() {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			 conn = datasource.getConnection();
			 
			 String sql = "SELECT COUNT(DISTINCT order_num) FROM bespeak WHERE order_state NOT IN(7, 8, 9)";
			 ps = conn.prepareStatement(sql);
			 
			 rs = ps.executeQuery();
			 if(rs.next()) {
				 cnt = rs.getInt(1);
			 }
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("getOrderCnt() 실패");
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

	//관리주 주문 상태 수정
	@Override
	public int orderStateUpdate(String num, int state) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "UPDATE bespeak SET order_state=? WHERE order_num=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, state);
			ps.setString(2, num);
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("orderStateUpdate() 실패");
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
	
	//배송 시작
	@Override
	public int shippingInsert(String order, String ship) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "INSERT INTO shipping VALUES (?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, order);
			ps.setString(2, ship);
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("shippingInsert() 실패");
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
	
	//관리자 환불 내역 총 개수
	public int getRefundCnt() {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT COUNT(DISTINCT order_num) FROM bespeak WHERE order_state IN(7, 8, 9)";
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("getRefundCnt() 실패");
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
	
	//관리자 환불 내역
	public ArrayList<Bespeak> getHostRefundList(int start, int end) {
		ArrayList<Bespeak> list = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			ArrayList<String> numList = new ArrayList<>();
			ArrayList<Bespeak> allList = new ArrayList<>();
			
			String sql = "SELECT * "
					   + "FROM(SELECT order_num, rownum as num "
					        + "FROM (SELECT DISTINCT order_num "
					              + "FROM bespeak "
					              + "WHERE order_state IN(7, 8, 9) "
					              + "ORDER BY order_num DESC)) "
					   + "WHERE num >= " + start +" AND num <= " + end;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if(rs.next()) {
				list = new ArrayList<>();
				do {
					numList.add(rs.getString(1));
				} while(rs.next());
				rs.close();
			}
			ps.close();

			sql = "SELECT order_num, id, name, phone, address, no, count, price, etc, account, order_state FROM (SELECT * FROM bespeak WHERE order_state IN (7, 8, 9) ORDER BY order_num DESC)";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				Bespeak order = new Bespeak();
				
				order.setOrder_num(rs.getString("order_num"));
				order.setId(rs.getString("id"));
				order.setName(rs.getString("name"));
				order.setPhone(rs.getString("phone"));
				order.setAddress(rs.getString("address"));
				order.setNo(rs.getString("no"));
				order.setCount(rs.getInt("count"));
				order.setPrice(rs.getInt("price"));
				order.setEtc(rs.getString("etc"));
				order.setAccount(rs.getInt("account"));
				order.setOrder_state(rs.getInt("order_state"));
				
				allList.add(order);
			}
			rs.close();
			ps.close();

			int index = 0; //게시판 list index
			//numList와 같은 주문 번호만 담기. 중복 주문번호(여러 종류 주문) 처리
			for(int i = 0; i < numList.size(); i += 1) { //중복 없는 주문 번호 List
				int count = 0; //중복 수
				
				for(int j = 0; j < allList.size(); j += 1) { //모둔 주문 list
					String num = allList.get(j).getOrder_num();
					
					//numlist의 현재 주문번호와 allList의 주문번호가 같으면
					if(numList.get(i).equals(num)) {
						//주문 번호가 중복되면
						if(count > 0) {
							list.get(index-1).setNos(" 외 " + count + "권"); //중복이므로 외권 표시
						}
						count += 1;

						//주문번호가 같으면(중복X) - 게시판 list 추가
						if(count == 1) {
							list.add(allList.get(j));
							index += 1;
						}
					} else {
						count = 0;
					}
				}
			}

			getNoTitle(list, conn, ps, rs);
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("getHostRefundList() 실패");
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
	
	//회원 목록
	@Override
	public ArrayList<Member> getMemberList(int start, int end) {
		ArrayList<Member> list = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * " + 
						 "FROM (SELECT id, pwd, name, phone, email, address, memo, rating, rownum as num FROM member) " + 
						 "WHERE num >= " + start + " AND num <= " + end;
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					Member m = new Member();
					m.setId(rs.getString("id"));
					m.setPwd(rs.getString("pwd"));
					m.setName(rs.getString("name"));
					m.setPhone(rs.getString("phone"));
					m.setEmail(rs.getString("email"));
					m.setAddress(rs.getString("address"));
					m.setMemo(rs.getString("memo"));
					m.setRating(rs.getInt("rating"));
					
					list.add(m);
				} while(rs.next());
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("getMemberList() 실패");
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

	//회원수
	@Override
	public int getMemberCnt() {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT COUNT(*) FROM member";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("getMemberCnt() 실패");
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

	//관리자 회원 수정
	@Override
	public int setHostMemberUpdate(String id, String memo, int rating) {
		int cnt = 0;

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = datasource.getConnection();

			String sql = "UPDATE member SET memo=?, rating=? WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, memo);
			ps.setInt(2, rating);
			ps.setString(3, id);

			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("setHostMemberUpdate() 실패");
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

	//관리자 회원 다수 수정
	@Override
	public int setHostMemberAllUpdate(String id, String memo, String rating) {
		int cnt = 0;
		
		String[] ids = id.split(",");
		String[] memos = memo.split(",");
		String[] ratings = rating.split(",");

		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "UPDATE member SET memo=?, rating=? WHERE id=?";
			ps = conn.prepareStatement(sql);

			for(int i = 0; i < ids.length; i += 1) {
				ps.setString(1, memos[i]);
				ps.setInt(2, Integer.parseInt(ratings[i]));
				ps.setString(3, ids[i]);
				
				System.out.println(ids[i]);
				
				cnt = ps.executeUpdate();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("setHostMemberAllUpdate() 실패");
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
	
	/*
	 * ERROR - ORA-02292: integrity constraint (BMS.SYS_C007739) violated - child record found
	 * FK에 ON DELETE CASCADE 필수!
	 */
	//관리자 회원 다수 삭제
	public int setHostMemberAllDelete(String id) {
		int cnt = 0;
		
		String[] ids = id.split(",");
		
		Connection conn = null;
		PreparedStatement ps = null;
				
		try {
			conn = datasource.getConnection();
			
			String sql = "DELETE FROM member WHERE id=?";
			ps = conn.prepareStatement(sql);
			
			for(String strId : ids) {
				ps.setString(1, strId);
				
				cnt = ps.executeUpdate();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("setHostMemberAllDelete() 실패");
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
	
	//주문 삭제
	@Override
	public int orderDelete(String order_num) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "DELETE FROM bespeak WHERE order_num=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, order_num);
			
			cnt = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("orderDelete() 실패");
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

	//회원 환불 내역
	@Override
	public ArrayList<Bespeak> getRefundList(String id) {
		ArrayList<Bespeak> list = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			ArrayList<String> numList = new ArrayList<>();
			ArrayList<Bespeak> allList = new ArrayList<>();
			
			String sql = "SELECT DISTINCT order_num FROM bespeak WHERE order_state IN(7, 8, 9) AND id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					numList.add(rs.getString(1));
				} while(rs.next());
				rs.close();
			}
			ps.close();

			sql = "SELECT * FROM bespeak WHERE order_state IN(7, 8, 9) AND id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			while(rs.next()){
				Bespeak order = new Bespeak();
				
				order.setOrder_num(rs.getString("order_num"));
				order.setId(rs.getString("id"));
				order.setName(rs.getString("name"));
				order.setPhone(rs.getString("phone"));
				order.setAddress(rs.getString("address"));
				order.setNo(rs.getString("no"));
				order.setCount(rs.getInt("count"));
				order.setPrice(rs.getInt("price"));
				order.setEtc(rs.getString("etc"));
				order.setAccount(rs.getInt("account"));
				order.setOrder_state(rs.getInt("order_state"));
				
				allList.add(order);
			}
			rs.close();
			ps.close();

			int index = 0; //게시판 list index
			//numList와 같은 주문 번호만 담기. 중복 주문번호(여러 종류 주문) 처리
			for(int i = 0; i < numList.size(); i += 1) { //중복 없는 주문 번호 List
				int count = 0; //중복 수
				
				for(int j = 0; j < allList.size(); j += 1) { //모둔 주문 list
					String num = allList.get(j).getOrder_num();
					
					//numlist의 현재 주문번호와 allList의 주문번호가 같으면
					if(numList.get(i).equals(num)) {
						//주문 번호가 중복되면
						if(count > 0) {
							list.get(index-1).setNos(" 외 " + count + "권"); //중복이므로 외권 표시
						}
						count += 1;

						//주문번호가 같으면(중복X) - 게시판 list 추가
						if(count == 1) {
							list.add(allList.get(j));
							index += 1;
						}
					} else {
						count = 0;
					}
				}
			}

			getNoTitle(list, conn, ps, rs);
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("getRefundList() 실패");
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

	//도서 이름
	@Override
	public void getNoTitle(ArrayList<Bespeak> list, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException{
		if(list != null) {
			String sql = "SELECT title FROM book WHERE no = ?";
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < list.size(); i += 1) {
				Bespeak b = list.get(i);
				
				ps.setString(1, b.getNo());
				rs = ps.executeQuery();
				
				if(rs.next()) {
					String title = rs.getString("title");
					b.setNos(title + b.getNos());
					list.set(i, b);
					
					rs.close();
				}
			}
		}
	}
}
