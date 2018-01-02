package mvc.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import mvc.persistence.BookPers;
import mvc.persistence.BookPersImpl;
import mvc.persistence.MemberPersImpl;
import mvc.vo.Bespeak;
import mvc.vo.Book;
import mvc.vo.BookSub;
import mvc.vo.RecentBook;

public class BookServiceImpl implements BookService {

	// 홈
	@SuppressWarnings("unchecked")
	@Override
	public void indexView(HttpServletRequest req, HttpServletResponse res) {
		int start = 1;
		int end = 6;

		BookPers dao = BookPersImpl.getInstance();
		ArrayList<Book> best = dao.getBestBook(start, end);
		ArrayList<Book> good = dao.getGoodTagBook(start, end);
		ArrayList<Book> newB = dao.getNewBook(start, end);

		req.setAttribute("best", best);
		req.setAttribute("good", good);
		req.setAttribute("newB", newB);
	}

	// 도서 검색
	public void bookSearch(HttpServletRequest req, HttpServletResponse res) {
		String search = req.getParameter("search");

		BookPers dao = BookPersImpl.getInstance();

		int postCnt = dao.bookSearchCount(search);
		if (postCnt != 0) {
			String pageNum = req.getParameter("pageNum");
			if (pageNum == null) {
				pageNum = "1";
			}
			int currentPage = Integer.parseInt(pageNum);

			int pageSize = 15;
			int pageNav = 10;

			int start = (currentPage - 1) * pageSize + 1;
			int end = start + pageSize - 1;

			int pageCnt = (postCnt / pageSize) + (postCnt % pageSize > 0 ? 1 : 0);
			int startPage = (currentPage / pageNav) * pageNav + 1;
			if (currentPage % pageNav == 0)
				startPage -= pageNav;
			int endPage = startPage + pageNav - 1;
			if (endPage > pageCnt) {
				endPage = pageCnt;
			}

			req.setAttribute("pageNav", pageNav);
			req.setAttribute("pageCount", pageCnt);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("startPage", startPage);
			req.setAttribute("endPage", endPage);

			ArrayList<Book> book = dao.bookSearch(search, start, end);
			req.setAttribute("book", book);
		}

		ArrayList<BookSub> tag_mains = dao.getTag_mainList();
		req.setAttribute("tag_mains", tag_mains);

		req.setAttribute("cnt", postCnt);
	}

	// 메인
	/*@Override
	public void bookNo(HttpServletRequest req, HttpServletResponse res) {
		BookPers book = BookPersImpl.getInstance();
		ArrayList<Book> arr = book.getBook15();
		req.setAttribute("arr", arr);
	}*/

	// 도서 상세 페이지 처리
	@SuppressWarnings("unchecked")
	@Override
	public void detailView(HttpServletRequest req, HttpServletResponse res) {
		String bookNo = req.getParameter("no");

		// 해당 book 정보
		BookPers dao = BookPersImpl.getInstance();
		Book b = dao.getBookInfo(bookNo);
		
		// 해당 book sub 정보
		BookSub bSub = dao.getBookSubInfo(bookNo);

		// tag_main 정보
		ArrayList<BookSub> tag_mains = dao.getTag_mainList();

		req.setAttribute("book", b);
		req.setAttribute("bookSub", bSub);
		req.setAttribute("tag_mains", tag_mains);

		//최근 본 도서
		// recentBookAdd
		ArrayList<RecentBook> recent = (ArrayList<RecentBook>) req.getSession().getAttribute("recent");
		if (recent == null) {
			recent = new ArrayList<>();
		}
		recent = dao.recentUpdate(recent, b);
		req.getSession().setAttribute("recent", recent);
		
		//조회수 증가
		dao.bookViewsUpdate(bookNo);
	}

	// 상단 메뉴 리스트 처리
	@Override
	public void bookListView(HttpServletRequest req, HttpServletResponse res) {
		String tag = req.getParameter("tag");
		String tagName = req.getParameter("tagName");
		BookPers dao = BookPersImpl.getInstance();

		// 태그 목록
		ArrayList<BookSub> tag_mains = dao.getTag_mainList();
		
		int cnt = 0;
		if (tag.equals("best")) {
			cnt = dao.getBestCount();
		} else if (tag.equals("new")) {
			cnt = dao.getNewCount();
		} else if (tag.equals("good")) {
			cnt = dao.getGoodCount();
		} else if (tag.equals("dom")) {
			cnt = dao.getBookCnt() - dao.getTagMainCount("21");
		} else {
			cnt = dao.getTagMainCount("21");
		}

		if (cnt != 0) {

			// 페이징
			int pageSize = 10;
			int pageNav = 10;

			String pageNum = req.getParameter("pageNum");
			if (pageNum == null)
				pageNum = "1";
			int currentPage = Integer.parseInt(pageNum);

			int start = (currentPage - 1) * pageSize + 1;
			int end = start + pageSize - 1;
			if (end > cnt)
				end = cnt;

			int pageCount = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0);
			int startPage = (currentPage / pageNav) * pageNav + 1;
			if (currentPage % pageNav == 0)
				startPage -= pageNav;
			int endPage = startPage + pageNav - 1;
			if (endPage > pageCount)
				endPage = pageCount;

			ArrayList<Book> books = null;
			//베스트 셀러, 추천도서, 신간 도서 목록
			if (tag.equals("best")) {
				books = dao.getBestBook(start, end);
			} else if (tag.equals("new")) {
				books = dao.getNewBook(start, end);
			} else if (tag.equals("good")) {
				books = dao.getGoodTagBook(start, end);
			//국내 도서 목록
			} else if (tag.equals("dom")) {
				books = dao.getDomesticBook(start, end);
			//외국 도서
			} else {
				books = dao.getTagMainBook("21", start, end);
			}
			req.setAttribute("books", books);

			req.setAttribute("pageNav", pageNav);
			req.setAttribute("pageCount", pageCount);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("startPage", startPage);
			req.setAttribute("endPage", endPage);
		}
		req.setAttribute("cnt", cnt);
		req.setAttribute("tag_mains", tag_mains);
		req.setAttribute("tag", tag);
		req.setAttribute("tagName", tagName);
	}

	// 재고관리 페이지
	@Override
	public void hostStockView(HttpServletRequest req, HttpServletResponse res) {
		BookPers dao = BookPersImpl.getInstance();

		// 1P
		int pageSize = 15;
		int pageNav = 10; // 페이징 개수

		// 현재 페이지
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		int previousPage = currentPage - 1;

		// 목록 행번호
		int cnt = dao.getBookCnt();
		int start = previousPage * pageSize + 1; // 1p 시작 행번호
		int end = start + pageSize - 1; // 1p 마지막 최대 행번호
		if (end > cnt)
			end = cnt; // 1p 마지막 실제 행번호
		int number = cnt - (currentPage - 1) * pageSize;

		// 재고 존재시
		if (cnt > 0) {
			ArrayList<Book> books = dao.getBookList(start, end);
			req.setAttribute("books", books);

			ArrayList<BookSub> bookSubs = dao.getBookSubList(start, end);
			req.setAttribute("bookSubs", bookSubs);

			// 페이징
			int pageCount = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0);
			int startPage = (currentPage / pageNav) * pageNav + 1;
			if (currentPage % pageNav == 0)
				startPage -= pageNav;
			int endPage = startPage + pageNav - 1;
			if (endPage > pageCount)
				endPage = pageCount;

			req.setAttribute("startPage", startPage);
			req.setAttribute("endPage", endPage);
			req.setAttribute("pageCount", pageCount);
			req.setAttribute("currentPage", currentPage);
		}
		req.setAttribute("cnt", cnt);
		req.setAttribute("number", number);
		req.setAttribute("pageNum", pageNum);
	}

	// 도서 정보 수정 or 추가
	@Override
	public void bookUpdate(HttpServletRequest req, HttpServletResponse res) {
		int cnt = 0;

		/*
		 * form의 encType을 지정했을 경우, 해당 타입으로 값을 가져와야 한다. request가 아닌 MultipartRequest로 값을
		 * 받는다.
		 */
		MultipartRequest mr = null;

		String fileName = "imgFile";
		int maxSize = 10 * 1024 * 1024; // 업로드 파일 최대 사이즈 (10*1024*1024=10MB)
		String saveDir = req.getRealPath("/images/book/"); // 임시 파일 저장 위치 - 논리적인 경로
		String realDir = "C:\\Dev\\workspace\\bookstore\\WebContent\\images\\book\\"; // 업로드 위치 - 물리적인 경로
		String encType = "UTF-8";

		try {
			/*
			 * new DefaultFileRenamePolicy() : 중복된 파일명이 있을 경우 자동으로 파일명을 변경한다. ex)
			 * filename.png가 이미 존재할 경우 filename1.png
			 */
			mr = new MultipartRequest(req, saveDir, maxSize, encType, new DefaultFileRenamePolicy());

			String no = mr.getParameter("no"); // -1이면 도서 추가. 아니면 도서 수정

			String title = mr.getParameter("title");
			String author = mr.getParameter("author");
			String publisher = mr.getParameter("publisher");
			Date pub_date = Date.valueOf(mr.getParameter("pub_date"));
			int price = Integer.parseInt(mr.getParameter("price"));
			int count = Integer.parseInt(mr.getParameter("count"));
			String image = mr.getParameter("image");
			if (image.equals("null.jpg")) {
				FileInputStream fis = new FileInputStream(saveDir + mr.getFilesystemName(fileName)); // 임시 저장
				FileOutputStream fos = new FileOutputStream(realDir + mr.getFilesystemName(fileName));
				int data = 0;

				// 임시 저장한 파일을 물리적 경로로 복사
				while ((data = fis.read()) != -1) {
					fos.write(data);
				}
				fis.close();
				fos.close();

				image = mr.getFilesystemName(fileName);
			}

			Book b = new Book();
			b.setNo(no);
			b.setTitle(title);
			b.setAuthor(author);
			b.setPublisher(publisher);
			b.setPub_date(pub_date);
			b.setPrice(price);
			b.setCount(count);
			b.setImage(image);

			BookPers dao = BookPersImpl.getInstance();
			if (!no.equals("-1")) { // 도서 수정
				cnt = dao.bookUpdate(b);
			} else { // 도서 추가
				cnt = dao.bookInsert(b);
			}

			// book_sub
			if (cnt != 0) {

				String tag = mr.getParameter("tag");
				String tag_main = mr.getParameter("tag_main");
				String intro = mr.getParameter("intro");
				String list = mr.getParameter("list");
				String pub_intro = mr.getParameter("pub_intro");
				String review = mr.getParameter("review");

				BookSub bSub = new BookSub();
				bSub.setNo(no);
				bSub.setTag(tag);
				bSub.setTag_main(tag_main);
				bSub.setIntro(intro);
				bSub.setList(list);
				bSub.setPub_intro(pub_intro);
				bSub.setReview(review);

				System.out.println(intro + " / " + list + " / " + pub_intro + " / " + review);
				
				if (!no.equals("-1")) { // 도서 수정
					cnt = dao.bookSubUpdate(bSub);
				} else { // 도서 추가
					cnt = dao.bookSubInsert(bSub);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("bookUpdate() 실패");
		}
		req.setAttribute("cnt", cnt);
	}

	// 도서 삭제 처리
	@Override
	public void bookDelete(HttpServletRequest req, HttpServletResponse res) {
		int chk = Integer.parseInt(req.getParameter("chk"));
		String no = req.getParameter("no");

		int cnt = 0;

		if (chk == 0) {
			BookPers dao = BookPersImpl.getInstance();
			cnt = dao.bookDelete(no);
		} else {
			String[] nos = no.split(",");

			BookPers dao = BookPersImpl.getInstance();
			cnt = dao.bookAllDelete(nos);
		}
		req.setAttribute("cnt", cnt);
	}

	// 도서 추가 페이지
	@Override
	public void bookInsertView(HttpServletRequest req, HttpServletResponse res) {
		BookPers dao = BookPersImpl.getInstance();
		ArrayList<BookSub> tag_mains = dao.getTag_mainList();

		req.setAttribute("no", req.getParameter("no"));
		req.setAttribute("tag_mains", tag_mains);
	}
}
