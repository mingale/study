package spring.mvc.bookstore.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import spring.mvc.bookstore.persistence.BookPers;
import spring.mvc.bookstore.vo.Book;
import spring.mvc.bookstore.vo.BookSub;
import spring.mvc.bookstore.vo.RecentBook;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	BookPers dao;

	// Ȩ
	@Override
	public void indexView(HttpServletRequest req, Model model) {
		int start = 1;
		int end = 6;

		Map<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		ArrayList<Book> best = dao.getBestBook(map);
		ArrayList<Book> good = dao.getGoodTagBook(map);
		ArrayList<Book> newB = dao.getNewBook(map);

		model.addAttribute("best", best);
		model.addAttribute("good", good);
		model.addAttribute("newB", newB);
	}

	// ���� �˻�
	public void bookSearch(HttpServletRequest req, Model model) {
		String keyword = req.getParameter("search");

		int postCnt = dao.bookSearchCount(keyword);
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

			model.addAttribute("pageNav", pageNav);
			model.addAttribute("pageCount", pageCnt);
			model.addAttribute("pageNum", pageNum);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);

			Map<String, Object> map = new HashMap<>();
			map.put("keyword", keyword);
			map.put("start", start);
			map.put("end", end);
			ArrayList<Book> book = dao.bookSearch(map);
			model.addAttribute("book", book);
		}

		ArrayList<BookSub> tag_mains = dao.getTag_mainList();
		model.addAttribute("tag_mains", tag_mains);

		model.addAttribute("cnt", postCnt);
	}

	// ���� �� ������ ó��
	@Override
	public void detailView(HttpServletRequest req, Model model) {
		String bookNo = req.getParameter("no");

		// �ش� book ����
		Book b = dao.getBookInfo(bookNo);
		
		// tag_main ����
		ArrayList<BookSub> tag_mains = dao.getTag_mainList();

		model.addAttribute("book", b);
		model.addAttribute("tag_mains", tag_mains);

		//�ֱ� �� ����
		// recentBookAdd
		@SuppressWarnings("unchecked")
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
	public void bookListView(HttpServletRequest req, Model model) {
		String tag = req.getParameter("tag");
		String tagName = req.getParameter("tagName");

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

			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			ArrayList<Book> books = null;
			//����Ʈ ����, ��õ����, �Ű� ���� ���
			if (tag.equals("best")) {
				books = dao.getBestBook(map);
			} else if (tag.equals("new")) {
				books = dao.getNewBook(map);
			} else if (tag.equals("good")) {
				books = dao.getGoodTagBook(map);
			//���� ���� ���
			} else if (tag.equals("dom")) {
				books = dao.getDomesticBook(map);
			//�ܱ� ����
			} else {
				map.put("tagNum", "21");
				books = dao.getTagMainBook(map);
			}
			model.addAttribute("books", books);

			model.addAttribute("pageNav", pageNav);
			model.addAttribute("pageCount", pageCount);
			model.addAttribute("pageNum", pageNum);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
		}
		model.addAttribute("cnt", cnt);
		model.addAttribute("tag_mains", tag_mains);
		model.addAttribute("tag", tag);
		model.addAttribute("tagName", tagName);
	}

	// ������ ������
	@Override
	public void hostStockView(HttpServletRequest req, Model model) {
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
			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			
			ArrayList<Book> books = dao.getBookList(map);
			model.addAttribute("books", books);

			// ����¡
			int pageCount = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0);
			int startPage = (currentPage / pageNav) * pageNav + 1;
			if (currentPage % pageNav == 0)
				startPage -= pageNav;
			int endPage = startPage + pageNav - 1;
			if (endPage > pageCount)
				endPage = pageCount;

			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("pageCount", pageCount);
			model.addAttribute("currentPage", currentPage);
		}
		model.addAttribute("cnt", cnt);
		model.addAttribute("number", number);
		model.addAttribute("pageNum", pageNum);
	}
/*
	// ���� ���� ���� or �߰�
	@Override
	public void bookUpdate(HttpServletRequest req, Model model) {
		int cnt = 0;

		String no = req.getParameter("no"); // -1�̸� ���� �߰�. �ƴϸ� ���� ����

		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String publisher = req.getParameter("publisher");
		Date pub_date = Date.valueOf(req.getParameter("pub_date"));
		int price = Integer.parseInt(req.getParameter("price"));
		int count = Integer.parseInt(req.getParameter("count"));
		String image = req.getParameter("image");
		if (image.equals("null.jpg")) {
			//image = req.getFilesystemName(fileName);
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

		if (!no.equals("-1")) { // ���� ����
			cnt = dao.bookUpdate(b);
		} else { // ���� �߰�
			cnt = dao.bookInsert(b);
		}

		// book_sub
		if (cnt != 0) {

			String tag = req.getParameter("tag");
			String tag_main = req.getParameter("tag_main");
			String intro = req.getParameter("intro");
			String list = req.getParameter("list");
			String pub_intro = req.getParameter("pub_intro");
			String review = req.getParameter("review");

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
		model.addAttribute("cnt", cnt);
	}
*/
	// ���� ���� ó��
	@Override
	public void bookDelete(HttpServletRequest req, Model model) {
		int chk = Integer.parseInt(req.getParameter("chk"));
		String no = req.getParameter("no");

		int cnt = 0;

		if (chk == 0) {
			cnt = dao.bookDelete(no);
		} else {
			String[] nos = no.split(",");
			for (String n : nos) {
				cnt = dao.bookDelete(n);
			}
		}
		model.addAttribute("cnt", cnt);
	}

	// ���� �߰� ������
	@Override
	public void bookInsertView(HttpServletRequest req, Model model) {
		ArrayList<BookSub> tag_mains = dao.getTag_mainList();

		model.addAttribute("no", req.getParameter("no"));
		model.addAttribute("tag_mains", tag_mains);
	}




	//���� ���� ���� or �߰�
	@Override
	public void bookUpdate(MultipartHttpServletRequest req, Model model) {
		System.out.println("===============================================================");
		int cnt = 0;

		MultipartFile file = req.getFile("img");

		String saveDir = req.getRealPath("/resources/images/book/"); // ���� ���. ������ ���
		String realDir = "C:\\Dev\\workspace\\bookstore\\WebContent\\images\\book\\"; // ���ε� ��ġ - �������� ���

		try {
			String no = req.getParameter("no"); // -1�̸� ���� �߰�. �ƴϸ� ���� ����

			String title = req.getParameter("title");
			String author = req.getParameter("author");
			String publisher = req.getParameter("publisher");
			Date pub_date = Date.valueOf(req.getParameter("pub_date"));
			int price = Integer.parseInt(req.getParameter("price"));
			int count = Integer.parseInt(req.getParameter("count"));
			String image = req.getParameter("image");
			if (!image.equals("null.jpg")) {
				file.transferTo(new File(saveDir + file.getOriginalFilename()));;
				
				FileInputStream fis = new FileInputStream(saveDir + file.getOriginalFilename()); // �ӽ� ����
				FileOutputStream fos = new FileOutputStream(realDir + file.getOriginalFilename());
				int data = 0;

				// �ӽ� ������ ������ ������ ��η� ����
				while ((data = fis.read()) != -1) {
					fos.write(data);
				}
				fis.close();
				fos.close();

				image = file.getOriginalFilename();
				System.out.println("**************************** bookUpdate - " + image);
			}
			System.out.println("**************************** bookUpdate - " + image);

			Book b = new Book();
			b.setNo(no);
			b.setTitle(title);
			b.setAuthor(author);
			b.setPublisher(publisher);
			b.setPub_date(pub_date);
			b.setPrice(price);
			b.setCount(count);
			b.setImage(image);

			if (!no.equals("-1")) { // ���� ����
				cnt = dao.bookUpdate(b);
			} else { // ���� �߰�
				cnt = dao.bookInsert(b);
			}

			// book_sub
			if (cnt != 0) {

				String tag = req.getParameter("tag");
				String tag_main = req.getParameter("tag_main");
				String intro = req.getParameter("intro");
				String list = req.getParameter("list");
				String pub_intro = req.getParameter("pub_intro");
				String review = req.getParameter("review");

				BookSub bSub = new BookSub();
				bSub.setNo(no);
				bSub.setTag(tag);
				bSub.setTag_main(tag_main);
				bSub.setIntro(intro);
				bSub.setList(list);
				bSub.setPub_intro(pub_intro);
				bSub.setReview(review);

				if (!no.equals("-1")) { // ���� ����
					cnt = dao.bookSubUpdate(bSub);
				} else { // ���� �߰�
					cnt = dao.bookSubInsert(bSub);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("bookUpdate() ����");
		}
		model.addAttribute("cnt", cnt);
	}
}
