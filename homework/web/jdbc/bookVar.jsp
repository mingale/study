<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %>

<%!
	Connection conn = null;
	PreparedStatement ps = null;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userId = "";
	String userPwd = "";
%>

<%
	String bookNo = request.getParameter("bookNo");
	String bookTitle = request.getParameter("bookTitle");
	String bookAuthor = request.getParameter("bookAuthor");
	int bookPrice = Integer.parseInt(request.getParameter("bookPrice"));
	int bookCount = Integer.parseInt(request.getParameter("bookCount"));
%>