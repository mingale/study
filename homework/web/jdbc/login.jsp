<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>

<%!
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	String driver = "oracle.jdbc.driver.OracleDriver"; 
	
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userId = "scott";
	String userPwd = "tiger";
	
	boolean idCheck = false;
%>

<%
	String strId = request.getParameter("id");
	String strPwd = request.getParameter("pwd");

	 try {
	 	Class.forName(driver); // JDBC 드라이버 메모리에 로딩
	 	conn = DriverManager.getConnection(url, userId, userPwd); // 데이터베이트 컨넥션 생성. 접속

		String sql = "SELECT * FROM login WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, strId);
		
		rs = ps.executeQuery(); //SELECT문 실행
		while(rs.next()){
			String pwd = rs.getString("pwd");
			 
			if(strPwd.equals(pwd)) {
				response.sendRedirect("../jsp/main/index.jsp");
				out.println("로그인");
				idCheck = true;
			}
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
	 
	if(!idCheck) {
%>
		<script type="text/javascript">
			alert("회원 정보가 올바르지 않습니다.");
			window.history.back();
		</script>
<%	} %>
