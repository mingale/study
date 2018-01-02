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

	//���̵� �ߺ� Ȯ��
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
			System.out.println("MemberPersImpl confirmId ����");
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

	//�̸��� ����key ���� Ȯ��
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
		 	
		 	//����Ű�� �����ϸ�
		 	if(rs.next()) {
		 		String id = rs.getString("id");
		 		
		 		ps.close();
		 		
		 		//�ش� ������ ���� ��� UP
		 		sql = "UPDATE member SET rating=3 WHERE id=?";
		 		ps = conn.prepareStatement(sql);
		 		ps.setString(1, id);
		 		
		 		cnt = ps.executeUpdate();
		 	}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("confirmEmailKey() ����");
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
	
	//�α���
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
			System.out.println("MemberPersImpl - SignIn ����");
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
	
	//ȸ������
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
			System.out.println("MemberPersImpl signUp ����");
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
	
	//ȸ�� ����
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

	//�̸��� ���� ����
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
			System.out.println("confirmEmail() ����");
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
	
	//����Ű ����
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
			System.out.println(id + " - " + key + ": �ƾƵ�/��й�ȣ ���� ����");
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("pwdFindEmailKey() ����");
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
	
	//���� ����
	/*
	 * gmail �̿� ���� ����
	 * gmail > ȯ�� ���� > ���� �� pop/IMAP > IMAP �׼��� > IMAP ������� ����
	 * �� ���� > �α��� �� ���� > ����� �� �� ����Ʈ > ���� ������ ���� �� ��� : ������� ����(2�ܰ� ���� ��� ���̸� ���� ����)
	 */
	@Override
	public int sendGmail(String toEmail, String key, int view) {
		int cnt = 0;
		
		//gmail �������� �̸��� ����
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
			
			msg.setFrom(new InternetAddress("admin@bookstore.com")); //������ ��???? ������ �̿� gmail �������� �Ǿ�����
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); //�޴� ��
			
			String content = "";
			
			if(view == 0) {
			content = "BMS ȸ������ ���� �����Դϴ�. ������ ���� ȸ�������� �Ϸ��ϼ���.<br>"
					+ "<a href='http://localhost:8080/bookstore/emailCheck.do?key=" + key + "&view=" + view + "'>����</a>"; //email ����
			msg.setSubject("BMS ȸ������ ���� ����"); //email ����
			} else if(view == 1) {
				content = "BMS ���̵� ã�� ���� �����Դϴ�. ������ ���� ���̵� ã������.<br>"
						+ "<a href='http://localhost:8080/bookstore/emailCheck.do?key=" + key + "&view=" + view + "'>����</a>"; //email ����
				msg.setSubject("BMS ���̵� ã�� ���� ����"); //email ����
			} else if(view == 2) {
				content = "BMS ��й�ȣ ã�� ���� �����Դϴ�. ������ ���� ��й�ȣ�� ã������.<br>"
						+ "<a href='http://localhost:8080/bookstore/emailCheck.do?key=" + key + "&view=" + view + "'>����</a>"; //email ����
				msg.setSubject("BMS ��й�ȣ ã�� ���� ����"); //email ����
			}
			
			msg.setContent(content, "text/html; charset=UTF-8");
			
			System.out.println("Email ����");
			Transport.send(msg);
			
			System.out.println("Email ���� �Ϸ�");
			cnt = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	//������ ���̵� ��������
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
			System.out.println("getId() ����");
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
	
	//������ ��й�ȣ ��������
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
			System.out.println("getPwd() ����");
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
	
	//ȸ�� ����
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
			System.out.println("MemberPersImpl memberUpdate ����");
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
	
	// ȸ�� Ż�� - id �����
	// email�� unique�� �״�� ��. NOT NULL
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
			System.out.println("MemberPersImpl memberUpdate ����");
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

	//��ٱ��� �߰�
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
			System.out.println("MemberPersImpl - setCart() ����");
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

	//��ٱ��� ���
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
			System.out.println("MemberPersImpl - getCartList() ����");
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

	//��ٱ��� ����
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
			System.out.println("MemberPersImpl - cartDelete() ����");
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

	//��ٱ��� ���� ����
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
			System.out.println("MemberPersImpl - cartCountUpdate() ����");
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

	//��ٱ��� �ߺ� Ȯ��
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
			System.out.println("MemberPersImpl - cartCheck() ����");
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

	//��ٱ��� ���� �߰�
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
			System.out.println("MemberPersImpl - setCartCount() ����");
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

	//�� �߰�
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
			System.out.println("setWishlist() ����");
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

	//�� �ߺ� Ȯ��
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
			System.out.println("setWishlist() ����");
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
	
	//�ֹ� �߰�
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

			//���� �� ���� �ֹ��� �ֹ���ȣ�� �����ϱ� ����
			sql = "SELECT order_sq.nextval FROM dual";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			rs.close();
			ps.close();
			
			for(String no : nos) {
				//cart ���� ��������
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
				
				//order�� �����ϱ�
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
			System.out.println("MemberPersImpl - setOrder() ����");
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
	
	//�ٷ� �ֹ� �߰�
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
			
			//���� ��������
			sql = "SELECT * FROM book WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, no);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				price = rs.getInt("price");
			}
			ps.close();
			
			//�߰�
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
			System.out.println("MemberPersImpl - setNowOrder() ����");
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
	
	//�ֹ� ����
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

			int index = 0; //�Խ��� list index
			//numList�� ���� �ֹ� ��ȣ�� ���. �ߺ� �ֹ���ȣ(���� ���� �ֹ�) ó��
			for(int i = 0; i < numList.size(); i += 1) { //�ߺ� ���� �ֹ� ��ȣ List
				int count = 0; //�ߺ� ��
				
				for(int j = 0; j < allList.size(); j += 1) { //��� �ֹ� list
					String num = allList.get(j).getOrder_num();
					
					//numlist�� ���� �ֹ���ȣ�� allList�� �ֹ���ȣ�� ������
					if(numList.get(i).equals(num)) {
						//�ֹ� ��ȣ�� �ߺ��Ǹ�
						if(count > 0) {
							list.get(index-1).setNos(" �� " + count + "��"); //�ߺ��̹Ƿ� �ܱ� ǥ��
						}
						count += 1;

						//�ֹ���ȣ�� ������(�ߺ�X) - �Խ��� list �߰�
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
			System.out.println("MemberPersImpl - getOrder() ����");
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

	//�ش� �ֹ� ����
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
			
			//������
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
			System.out.println("MemberPersImpl - getOrderDetail() ����");
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
	
	//���� ��ȣ
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
			System.out.println("MemberPersImpl - getShipping() ����");
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
	
	//������ �ֹ� ����
	@Override
	public ArrayList<Bespeak> getHostOrderList(int start, int end) {
		ArrayList<Bespeak> list = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			//�ֹ� ��� - ù��° ������ ���� ��ȣ ǥ��
			//** rownum�� ��Ī�� ���� ������ ���� ������ �������� ����� ���ư��� �ʴ´�.
			
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

			int index = 0; //�Խ��� list index
			//numList�� ���� �ֹ� ��ȣ�� ���. �ߺ� �ֹ���ȣ(���� ���� �ֹ�) ó��
			for(int i = 0; i < numList.size(); i += 1) { //�ߺ� ���� �ֹ� ��ȣ List
				int count = 0; //�ߺ� ��
				
				for(int j = 0; j < allList.size(); j += 1) { //��� �ֹ� list
					String num = allList.get(j).getOrder_num();
					
					//numlist�� ���� �ֹ���ȣ�� allList�� �ֹ���ȣ�� ������
					if(numList.get(i).equals(num)) {
						//�ֹ� ��ȣ�� �ߺ��Ǹ�
						if(count > 0) {
							list.get(index-1).setNos(" �� " + count + "��"); //�ߺ��̹Ƿ� �ܱ� ǥ��
						}
						count += 1;

						//�ֹ���ȣ�� ������(�ߺ�X) - �Խ��� list �߰�
						if(count == 1) {
							list.add(allList.get(j));
							index += 1;
						}
					} else {
						count = 0;
					}
				}
			}

			//�ֹ� ���� �̸�
			getNoTitle(list, conn, ps, rs);
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("MemberPersImpl - getOrderList() ����");
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

	//�ֹ� ���� �� ����
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
			System.out.println("getOrderCnt() ����");
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

	//������ �ֹ� ���� ����
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
			System.out.println("orderStateUpdate() ����");
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
	
	//��� ����
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
			System.out.println("shippingInsert() ����");
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
	
	//������ ȯ�� ���� �� ����
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
			System.out.println("getRefundCnt() ����");
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
	
	//������ ȯ�� ����
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

			int index = 0; //�Խ��� list index
			//numList�� ���� �ֹ� ��ȣ�� ���. �ߺ� �ֹ���ȣ(���� ���� �ֹ�) ó��
			for(int i = 0; i < numList.size(); i += 1) { //�ߺ� ���� �ֹ� ��ȣ List
				int count = 0; //�ߺ� ��
				
				for(int j = 0; j < allList.size(); j += 1) { //��� �ֹ� list
					String num = allList.get(j).getOrder_num();
					
					//numlist�� ���� �ֹ���ȣ�� allList�� �ֹ���ȣ�� ������
					if(numList.get(i).equals(num)) {
						//�ֹ� ��ȣ�� �ߺ��Ǹ�
						if(count > 0) {
							list.get(index-1).setNos(" �� " + count + "��"); //�ߺ��̹Ƿ� �ܱ� ǥ��
						}
						count += 1;

						//�ֹ���ȣ�� ������(�ߺ�X) - �Խ��� list �߰�
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
			System.out.println("getHostRefundList() ����");
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
	
	//ȸ�� ���
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
			System.out.println("getMemberList() ����");
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

	//ȸ����
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
			System.out.println("getMemberCnt() ����");
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

	//������ ȸ�� ����
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
			System.out.println("setHostMemberUpdate() ����");
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

	//������ ȸ�� �ټ� ����
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
			System.out.println("setHostMemberAllUpdate() ����");
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
	 * FK�� ON DELETE CASCADE �ʼ�!
	 */
	//������ ȸ�� �ټ� ����
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
			System.out.println("setHostMemberAllDelete() ����");
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
	
	//�ֹ� ����
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
			System.out.println("orderDelete() ����");
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

	//ȸ�� ȯ�� ����
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

			int index = 0; //�Խ��� list index
			//numList�� ���� �ֹ� ��ȣ�� ���. �ߺ� �ֹ���ȣ(���� ���� �ֹ�) ó��
			for(int i = 0; i < numList.size(); i += 1) { //�ߺ� ���� �ֹ� ��ȣ List
				int count = 0; //�ߺ� ��
				
				for(int j = 0; j < allList.size(); j += 1) { //��� �ֹ� list
					String num = allList.get(j).getOrder_num();
					
					//numlist�� ���� �ֹ���ȣ�� allList�� �ֹ���ȣ�� ������
					if(numList.get(i).equals(num)) {
						//�ֹ� ��ȣ�� �ߺ��Ǹ�
						if(count > 0) {
							list.get(index-1).setNos(" �� " + count + "��"); //�ߺ��̹Ƿ� �ܱ� ǥ��
						}
						count += 1;

						//�ֹ���ȣ�� ������(�ߺ�X) - �Խ��� list �߰�
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
			System.out.println("getRefundList() ����");
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

	//���� �̸�
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
