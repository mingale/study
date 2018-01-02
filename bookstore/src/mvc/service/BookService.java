package mvc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BookService {

	//홈
	public void indexView(HttpServletRequest req, HttpServletResponse res);
	
	//도서 검색
	public void bookSearch(HttpServletRequest req, HttpServletResponse res);
	
	//
	//public void bookNo(HttpServletRequest req, HttpServletResponse res);
	
	//도서 정보 출력
	public void detailView(HttpServletRequest req, HttpServletResponse res);
	
	//도서 목록 출력
	public void bookListView(HttpServletRequest req, HttpServletResponse res);
	
	//재고 관리 목록 출력
	public void hostStockView(HttpServletRequest req, HttpServletResponse res);
	
	//도서 수정 처리
	public void bookUpdate(HttpServletRequest req, HttpServletResponse res);
	
	//도서 삭제 처리
	public void bookDelete(HttpServletRequest req, HttpServletResponse res);
	
	//도서 추가 화면
	public void bookInsertView(HttpServletRequest req, HttpServletResponse res);
}
