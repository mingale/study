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

	// Ȩ
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

	// ���� �˻�
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

	// ����
	/*@Override
	public void bookNo(HttpServletRequest req, HttpServletResponse res) {
		BookPers book = BookPersImpl.getInstance();
		ArrayList<Book> arr = book.getBook15();
		req.setAttribute("arr", arr);
	}*/

	// ���� �� ������ ó��
	@SuppressWarnings("unchecked")
	@Override
	public void detailView(HttpServletRequest req, HttpServletResponse res) {
		String bookNo = req.getParameter("no");

		// �ش� book ����
		BookPers dao = BookPersImpl.getInstance();
		Book b = dao.getBookInfo(bookNo);
		
		// �ش� book sub ����
		BookSub bSub = dao.getBookSubInfo(bookNo);

		// tag_main ����
		ArrayList<BookSub> tag_mains = dao.getTag_mainList();

		req.setAttribute("book", b);
		req.setAttribute("bookSub", bSub);
		req.setAttribute("tag_mains", tag_mains);

		//�ֱ� �� ����
		// recentBookAdd
		ArrayList<RecentBook> recent = (ArrayList<RecentBook>) req.getSession().getAttribute("recent");
		if (recent == null) {
			recent = new ArrayList<>();
		}
		recent = dao.recentUpdate(recent, b);
		req.getSession().setAttribute("recent", recent);
		
		//��ȸ�� ����
		dao.bookViewsUpdate(bookNo);
	}

	// ��� �޴� ����Ʈ ó��
	@Override
	public void bookListView(HttpServletRequest req, HttpServletResponse res) {
		String tag = req.getParameter("tag");
		String tagName = req.getParameter("tagName");
		BookPers dao = BookPersImpl.getInstance();

		// �±� ���
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

			// ����¡
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
			//����Ʈ ����, ��õ����, �Ű� ���� ���
			if (tag.equals("best")) {
				books = dao.getBestBook(start, end);
			} else if (tag.equals("new")) {
				books = dao.getNewBook(start, end);
			} else if (tag.equals("good")) {
				books = dao.getGoodTagBook(start, end);
			//���� ���� ���
			} else if (tag.equals("dom")) {
				books = dao.getDomesticBook(start, end);
			//�ܱ� ����
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

	// ������ ������
	@Override
	public void hostStockView(HttpServletRequest req, HttpServletResponse res) {
		BookPers dao = BookPersImpl.getInstance();

		// 1P
		int pageSize = 15;
		int pageNav = 10; // ����¡ ����

		// ���� ������
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		int previousPage = currentPage - 1;

		// ��� ���ȣ
		int cnt = dao.getBookCnt();
		int start = previousPage * pageSize + 1; // 1p ���� ���ȣ
		int end = start + pageSize - 1; // 1p ������ �ִ� ���ȣ
		if (end > cnt)
			end = cnt; // 1p ������ ���� ���ȣ
		int number = cnt - (currentPage - 1) * pageSize;

		// ��� �����
		if (cnt > 0) {
			ArrayList<Book> books = dao.getBookList(start, end);
			req.setAttribute("books", books);

			ArrayList<BookSub> bookSubs = dao.getBookSubList(start, end);
			req.setAttribute("bookSubs", bookSubs);

			// ����¡
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

	// ���� ���� ���� or �߰�
	@Override
	public void bookUpdate(HttpServletRequest req, HttpServletResponse res) {
		int cnt = 0;

		/*
		 * form�� encType�� �������� ���, �ش� Ÿ������ ���� �����;� �Ѵ�. request�� �ƴ� MultipartRequest�� ����
		 * �޴´�.
		 */
		MultipartRequest mr = null;

		String fileName = "imgFile";
		int maxSize = 10 * 1024 * 1024; // ���ε� ���� �ִ� ������ (10*1024*1024=10MB)
		String saveDir = req.getRealPath("/images/book/"); // �ӽ� ���� ���� ��ġ - ������ ���
		String realDir = "C:\\Dev\\workspace\\bookstore\\WebContent\\images\\book\\"; // ���ε� ��ġ - �������� ���
		String encType = "UTF-8";

		try {
			/*
			 * new DefaultFileRenamePolicy() : �ߺ��� ���ϸ��� ���� ��� �ڵ����� ���ϸ��� �����Ѵ�. ex)
			 * filename.png�� �̹� ������ ��� filename1.png
			 */
			mr = new MultipartRequest(req, saveDir, maxSize, encType, new DefaultFileRenamePolicy());

			String no = mr.getParameter("no"); // -1�̸� ���� �߰�. �ƴϸ� ���� ����

			String title = mr.getParameter("title");
			String author = mr.getParameter("author");
			String publisher = mr.getParameter("publisher");
			Date pub_date = Date.valueOf(mr.getParameter("pub_date"));
			int price = Integer.parseInt(mr.getParameter("price"));
			int count = Integer.parseInt(mr.getParameter("count"));
			String image = mr.getParameter("image");
			if (image.equals("null.jpg")) {
				FileInputStream fis = new FileInputStream(saveDir + mr.getFilesystemName(fileName)); // �ӽ� ����
				FileOutputStream fos = new FileOutputStream(realDir + mr.getFilesystemName(fileName));
				int data = 0;

				// �ӽ� ������ ������ ������ ��η� ����
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
			if (!no.equals("-1")) { // ���� ����
				cnt = dao.bookUpdate(b);
			} else { // ���� �߰�
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
				
				if (!no.equals("-1")) { // ���� ����
					cnt = dao.bookSubUpdate(bSub);
				} else { // ���� �߰�
					cnt = dao.bookSubInsert(bSub);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("bookUpdate() ����");
		}
		req.setAttribute("cnt", cnt);
	}

	// ���� ���� ó��
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

	// ���� �߰� ������
	@Override
	public void bookInsertView(HttpServletRequest req, HttpServletResponse res) {
		BookPers dao = BookPersImpl.getInstance();
		ArrayList<BookSub> tag_mains = dao.getTag_mainList();

		req.setAttribute("no", req.getParameter("no"));
		req.setAttribute("tag_mains", tag_mains);
	}
}
