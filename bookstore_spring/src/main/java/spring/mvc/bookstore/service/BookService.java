package spring.mvc.bookstore.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface BookService {

	//홈
	public void indexView(HttpServletRequest req, Model model);
	
	//도서 검색
	public void bookSearch(HttpServletRequest req, Model model);
	
	//public void bookNo(HttpServletRequest req, HttpServletResponse res);
	
	//도서 정보 출력
	public void detailView(HttpServletRequest req, Model model);
	
	//도서 목록 출력
	public void bookListView(HttpServletRequest req, Model model);
	
	//재고 관리 목록 출력
	public void hostStockView(HttpServletRequest req, Model model);
	
	//도서 수정 처리
	public void bookUpdate(HttpServletRequest req, Model model);
	
	//도서 삭제 처리
	public void bookDelete(HttpServletRequest req, Model model);
	
	//도서 추가 화면
	public void bookInsertView(HttpServletRequest req, Model model);
}
