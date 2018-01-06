package spring.mvc.bookstore.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import spring.mvc.bookstore.controller.EmailHandler;
import spring.mvc.bookstore.persistence.BookPers;
import spring.mvc.bookstore.persistence.HostPers;
import spring.mvc.bookstore.persistence.MemberPers;
import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Book;
import spring.mvc.bookstore.vo.Member;
import spring.mvc.bookstore.vo.Notice;
import spring.mvc.bookstore.vo.NoticeComment;
import spring.mvc.bookstore.vo.RecentBook;
import spring.mvc.bookstore.vo.StringInt;

@Service
public class HostServiceImpl implements HostService {

	@Autowired
	MemberPers mDao;
	@Autowired
	BookPers bDao;
	@Autowired
	HostPers hDao;

	private Logger log = Logger.getLogger(this.getClass());

	// ���̵� �ߺ� Ȯ��
	public void confirmId(HttpServletRequest req, Model model) {
		String id = req.getParameter("id");

		int cnt = hDao.confirmId(id);
		model.addAttribute("cnt", cnt);
	}

	// �̸��� �ߺ� Ȯ��
	public void confirmEmail(HttpServletRequest req, Model model) {
		String email = req.getParameter("email");

		String id = hDao.confirmEmail(email);

		if (id != null) { // �̹� ����
			model.addAttribute("cnt", 1);
		} else { // ��� ����
			model.addAttribute("cnt", 0);
			req.getSession().setAttribute("echk", "Y");
		}
	}

	// �α���
	@Override
	public void signIn(HttpServletRequest req, Model model) {
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");

		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("pwd", pwd);

		int cnt = 0;
		String rating = hDao.signIn(map);
		if (rating != null) {
			cnt = 1;

			// ���ǿ� ID, rating ����
			req.getSession().setAttribute("memId", id);
			req.getSession().setAttribute("rating", rating);

			// �ֱ� ����
			ArrayList<RecentBook> recent = null;
			req.getSession().setAttribute("recent", recent);
		}
		model.addAttribute("cnt", cnt);
	}

	// ȸ������
	@Override
	public void signUp(HttpServletRequest req, Model model) {
		String email = req.getParameter("email");
		String echk = (String) req.getSession().getAttribute("echk"); // �̸��� �ߺ� Ȯ�� ����

		int cnt = 0;
		if (echk != null) {
			// ����Ű ����
			EmailHandler ech = new EmailHandler();
			String key = ech.randomKey();

			// ȸ������ ����
			Member m = new Member();
			m.setId(req.getParameter("id"));
			m.setPwd(req.getParameter("pwd"));
			m.setName(req.getParameter("name"));
			m.setPhone(req.getParameter("phone"));
			m.setEmail(email);
			m.setAddress(req.getParameter("address"));
			m.setE_key(key);

			// ȸ������
			cnt = hDao.signUp(m);

			// ȸ������ ���� �� ���� ���� ����
			if (cnt == 1) {
				hDao.sendGmail(email, key, 0);
			}

			req.getSession().invalidate();
		} else {
			cnt = 9;
		}
		model.addAttribute("cnt", cnt);
	}

	// �̸��� ����key ���� Ȯ��
	public void confirmEmailKey(HttpServletRequest req, Model model) {
		String key = req.getParameter("key");
		int view = Integer.parseInt(req.getParameter("view"));

		int cnt = hDao.confirmEmailKey(key);

		// ����Ű �����ϸ�
		if (cnt != 0) {
			// ���̵� ã���� ���
			if (view == 1) {
				String id = hDao.getId(key);
				model.addAttribute("id", id);
			}
			// ��й�ȣ ã�� �� ���
			if (view == 2) {
				String id = hDao.getId(key);
				String pwd = hDao.getPwd(key);

				model.addAttribute("id", id);
				model.addAttribute("pwd", pwd);
			}
		}

		log.debug("Ű: " + key + " / ���� : " + view + " / ��������: " + cnt);

		model.addAttribute("cnt", cnt);
		model.addAttribute("view", view);
	}

	// ���̵�/��й�ȣ ã�� �̸��� ����
	public void memberFindEmailKey(HttpServletRequest req, Model model) {
		String id = req.getParameter("id");
		String email = req.getParameter("email");
		String idPwd = req.getParameter("idCheck");

		int cnt = 0;
		int view;

		// ID ã��
		if (idPwd.equals("n")) {
			// �̸��� ���� ���� Ȯ�� �� ���̵� ��������
			id = hDao.confirmEmail(email);
			view = 1;

			// PWD ã��
		} else {
			// ���̵� ���� ���� Ȯ��
			cnt = hDao.confirmId(id);
			if (cnt == 0)
				id = null;
			view = 2;
		}

		// ȸ���� �����ϸ� ���� ���� ����
		if (id != null) {
			// ����Ű ����
			EmailHandler ech = new EmailHandler();
			String key = ech.randomKey();

			// ���� �̸��� ����
			cnt = hDao.sendGmail(email, key, view);

			// ���� �����ϸ� ����Ű �����ϱ�
			Map<String, Object> map = new HashMap<>();
			map.put("id", id);
			map.put("key", key);
			if (cnt != 0)
				cnt = hDao.memberEmailKeyUpdate(map);
		}

		model.addAttribute("email", email);
		model.addAttribute("cnt", cnt);
	}

	// ������ ����
	@Override
	public void mainView(HttpServletRequest req, Model model) {
		Map<String, Object> map = new HashMap<>();
		map.put("start", 1);
		map.put("end", 2);

		ArrayList<Book> books = bDao.getBookList(map);
		ArrayList<Bespeak> orders = hDao.getHostOrderDistinctList(map);
		ArrayList<Notice> notices = hDao.getNotice(map);

		model.addAttribute("books", books);
		model.addAttribute("orders", orders);
		model.addAttribute("notices", notices);
	}

	@Override
	public void memberQuery(HttpServletRequest req, Model model) {
		String id = (String) req.getSession().getAttribute("memId");
		String pwd = req.getParameter("pwd");

		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("pwd", pwd);

		String rating = hDao.signIn(map);
		int cnt = 0;
		if (rating != null) {
			cnt = 1;

			Member m = mDao.getMemberInfo(id);
			model.addAttribute("m", m);
		}
		model.addAttribute("cnt", cnt);
	}

	@Override
	public void memberUpdate(HttpServletRequest req, Model model) {
		Member m = new Member();
		m.setId(req.getParameter("id"));
		m.setPwd(req.getParameter("pwd"));
		m.setName(req.getParameter("name"));
		m.setPhone(req.getParameter("phone"));
		m.setEmail(req.getParameter("email"));
		m.setAddress(req.getParameter("address"));

		int cnt = mDao.memberUpdate(m);
		model.addAttribute("cnt", cnt);
	}

	// ȸ�� ���� Ż��
	@Override
	public void memberDelete(HttpServletRequest req, Model model) {
		String tmpChk = req.getParameter("chk");
		int chk = 0;
		if (tmpChk != null) {
			chk = Integer.parseInt(tmpChk);
		}

		int cnt = 0;
		// ȸ�� Ż��
		if (chk == 0) {
			String strId = (String) req.getSession().getAttribute("memId");
			cnt = mDao.memberDelete(strId);

			// �����ڿ� ȸ�� Ż��(id�� �����)
		} else {
			String id = req.getParameter("id");
			String[] ids = id.split(",");

			for (String tmp : ids) {
				cnt = mDao.memberDelete(tmp);
				// cnt = mDao.setHostMemberAllDelete(tmp); ���� ����
			}
		}

		model.addAttribute("cnt", cnt);
	}

	// ������ �ֹ� ���
	@Override
	public void hostOrderView(HttpServletRequest req, Model model) {
		int pageSize = 15;
		int pageNav = 10;

		String pageNum = req.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);

		int cnt = hDao.getHostOrderCnt();

		// ������ �����ϸ�
		if (cnt > 0) {
			int start = (currentPage - 1) * pageSize + 1;
			int end = start + pageSize - 1;
			if (end > cnt)
				end = cnt;
			int number = cnt - (currentPage - 1) * pageSize;

			int pageCnt = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0);
			int startPage = (currentPage / pageNav) * pageNav + 1;
			if (currentPage % pageNav == 0)// ���� ���� ������ ��ȣ�� ����¡������ ������ �� �� �������ٸ� ���� ������ ��ȣ�� ù����(1~pageNav)�� ������ ��°��� ��
				startPage -= pageNav; // ���� 1�� ����� ���� ����¡������ ����.
			int endPage = startPage + pageNav - 1;
			if (endPage > pageCnt)
				endPage = pageCnt;

			model.addAttribute("pageNum", pageNum); // ������ �̵�
			model.addAttribute("number", number); // ȭ�鿡 ǥ���� �۹�ȣ
			model.addAttribute("currentPage", currentPage); // ���� ������
			model.addAttribute("startPage", startPage); // ����¡ ó�� ��ư
			model.addAttribute("endPage", endPage); // ����¡ ������ ��ư
			model.addAttribute("pageNav", pageNav); // ����¡ ����
			model.addAttribute("pageCnt", pageCnt); // ������ �Ѱ���

			// �ֹ� ����
			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			ArrayList<Bespeak> orders = hDao.getHostOrderDistinctList(map);
			model.addAttribute("orders", orders);
		}
		model.addAttribute("cnt", cnt);
	}

	// ������ �ֹ� ���� ����
	public void orderStateUpdate(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("order_num");

		int cnt = 0;

		Map<String, Object> map = new HashMap<>();
		if (order_num == null) {
			order_num = req.getParameter("order_nums");
			String[] nums = order_num.split(",");
			for (String num : nums) {
				map.put("order_num", num);
				cnt = bDao.orderBookCountMul(map);
				if (cnt == 1) {
					map = new HashMap<>();
					map.put("state", 1);
					map.put("num", num);
					cnt = hDao.orderStateUpdate(map);
				}
			}
		} else {
			// �ֹ� ������ Ȯ���Ǿ� ���� �ÿ��� ��� ����
			map.put("order_num", order_num);
			cnt = bDao.orderBookCountMul(map);

			// �ֹ� ���� ����
			if (cnt == 1) {
				map = new HashMap<>();
				map.put("state", 1);
				map.put("num", order_num);
				cnt = hDao.orderStateUpdate(map);
			}
		}

		model.addAttribute("cnt", cnt);
	}

	// ������ ��� ����
	@Override
	public void shippingPro(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("orderNum");
		String ship_num = req.getParameter("shippingNum");

		Map<String, Object> map = new HashMap<>();
		map.put("order", order_num);
		map.put("ship", ship_num);
		int cnt = hDao.shippingInsert(map);

		if (cnt != 0) {
			map = new HashMap<>();
			map.put("state", 2);
			map.put("num", order_num);
			cnt = hDao.orderStateUpdate(map);
		}

		model.addAttribute("cnt", cnt);
	}

	// ������ ȯ�� ����
	@Override
	public void getHostRefundView(HttpServletRequest req, Model model) {
		// �� �Խñۼ�
		int postCnt = hDao.getRefundCnt();
		if (postCnt != 0) {

			String pageNum = req.getParameter("pageNum");
			if (pageNum == null)
				pageNum = "1";
			int currentPage = Integer.parseInt(pageNum);

			int pageSize = 15; // �� �������� �Խñۼ�
			int pageNav = 10; // �� �������� ����¡��

			int start = (currentPage - 1) * pageSize + 1;
			int end = start + pageSize - 1;
			if (end > postCnt)
				end = postCnt;

			// �� ����¡��
			int pageCnt = (postCnt / pageSize) + (postCnt % pageSize > 0 ? 1 : 0);
			int startPage = (currentPage / pageNav) * pageNav + 1;
			if (currentPage % pageNav == 0)
				startPage -= pageNav;
			int endPage = startPage + pageNav - 1;
			if (endPage > pageCnt)
				endPage = pageCnt;

			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			ArrayList<Bespeak> refunds = hDao.getHostRefundDistinctList(map);
			model.addAttribute("refunds", refunds);

			model.addAttribute("pageNum", pageNum);
			model.addAttribute("pageNav", pageNav);
			model.addAttribute("pageCnt", pageCnt);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
		}
	}

	// ȸ�� ���
	@Override
	public void getMemberView(HttpServletRequest req, Model model) {
		int pageSize = 15;
		int pageNav = 10;

		String pageNum = req.getParameter("pageNum");
		if (pageNum == null)
			pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);

		int cnt = hDao.getMemberCnt();
		if (cnt != 0) {
			int start = (currentPage - 1) * pageSize + 1;
			int end = start + pageSize - 1;
			int number = cnt - (currentPage - 1) * pageSize;

			int pageCnt = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0);
			int startPage = (currentPage / pageNav) * pageNav + 1;
			if (currentPage % pageNav == 0)
				startPage -= pageNav;
			int endPage = startPage + pageNav - 1;
			if (endPage > pageCnt)
				endPage = pageCnt;

			model.addAttribute("pageCnt", pageCnt);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("pageNav", pageNav);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("number", number);

			// ���
			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			ArrayList<Member> members = hDao.getMemberList(map);
			model.addAttribute("members", members);
		}
		model.addAttribute("cnt", cnt);
	}

	// ������ ȸ�� ����
	@Override
	public void setHostMemberUpdate(HttpServletRequest req, Model model) {
		int chk = Integer.parseInt(req.getParameter("chk"));
		String id = req.getParameter("id");
		String memo = req.getParameter("memo");
		String rating = req.getParameter("rating");

		int cnt = 0;

		Map<String, Object> map = null;
		if (chk == 0) {
			map = new HashMap<>();
			map.put("id", id);
			map.put("memo", memo);
			map.put("rating", Integer.parseInt(rating));
			cnt = hDao.setHostMemberUpdate(map);
		} else {
			String[] ids = id.split(",");
			String[] memos = memo.split(",");
			String[] ratings = rating.split(",");

			for (int i = 0; i < ids.length; i += 1) {
				map = new HashMap<>();
				map.put("id", ids[i]);
				map.put("memo", memos[i]);
				map.put("rating", Integer.parseInt(ratings[i]));
				cnt = hDao.setHostMemberUpdate(map);
			}
		}

		model.addAttribute("cnt", cnt);

		log.debug(chk);
	}

	// ������ �ֹ� ����
	@Override
	public void orderDelete(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("order_num");

		int cnt = 0;

		// �ߺ� ����
		if (order_num == null) {
			order_num = req.getParameter("order_nums");
			String[] nums = order_num.split(",");
			for (String num : nums) {
				cnt = hDao.orderDelete(num);
			}
			// ���� ����
		} else {
			cnt = hDao.orderDelete(order_num);
		}
		model.addAttribute("cnt", cnt);
	}

	// ȯ�� �Ϸ�
	@Override
	public void refundOk(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("order_num");

		// ��� �ٽ� �߰�
		Map<String, Object> map = new HashMap<>();
		map.put("order_num", order_num);
		int cnt = bDao.refundBookCountAdd(map);

		// �߰� �Ϸ�Ǹ� ������ �ֹ� ���� ����
		if (cnt != 0) {
			map = new HashMap<>();
			map.put("state", 9);
			map.put("num", order_num);
			cnt = hDao.orderStateUpdate(map);
		}

		model.addAttribute("cnt", cnt);
	}

	// ȯ�� �ź�
	@Override
	public void refundNo(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("order_num");

		Map<String, Object> map = new HashMap<>();
		map.put("state", 7);
		map.put("num", order_num);
		int cnt = hDao.orderStateUpdate(map);

		model.addAttribute("cnt", cnt);
	}

	// �� ����
	@Override
	public void helpView(HttpServletRequest req, Model model) {
		String googleApiUrl = "https://maps.googleapis.com/maps/api/staticmap?";
		String ne = "37.478928,126.878965"; // ���� �浵

		// String center = "center=" + ne; //marker ���� ��� �ʼ�
		// String zoom = "&zoom=" + "17"; //marker ���� ��� �ʼ�. 20:�ǹ�, 15:����, 10:��/��/��,
		// 5:���, 1:����
		String size = "&size=" + "1200x800";
		String marker = "&markers=" + "color:blue" + "%7C" + "label:C" + "%7C" + ne; // %7C = ������(|) : ��Ŀ ���� ������. URL
																						// ���ڵ�
		String key = "&key=" + "AIzaSyD-iJfYjArAMU7227--yo5AELs54bgFiC4"; // Google Static Maps API Key

		// String url = googleApiUrl + center + zoom + size + key;
		String url = googleApiUrl + marker + size + key;

		model.addAttribute("url", url);
	}

	// ���
	@Override
	public void getResultTotal(HttpServletRequest req, Model model) {
		String chart = req.getParameter("chart");

		int total = bDao.getSalesTotalCount(); // ���� �Ǹŷ�
		int totalSale = bDao.getSalesTotalAmount(); // �����

		model.addAttribute("total", total);
		model.addAttribute("totalSale", totalSale);
		model.addAttribute("chart", chart);

		/*
		 * //�±׺� �Ǹŷ� String[] tagName = {"newS", "bestS", "goodS", "nullS"}; Map<String,
		 * Integer> tagMap = dao.getTagSalesTotal(); for(String name : tagName) { //�Ǹŷ���
		 * ���� �±״� 0 ���� boolean flg = false; for(Entry<String, Integer> e :
		 * tagMap.entrySet()) { if(name.equals(e.getKey())) flg = true; } if(!flg)
		 * tagMap.put(name, 0); } model.addAttribute("tagMap", tagMap);
		 * 
		 * //�±׺� ȯ�ҷ� tagName = new String[] {"newR", "bestR", "goodR", "nullR"};
		 * Map<String, Integer> tagRefund = dao.getTagRefundTotal(); for(String name :
		 * tagName) { //ȯ�ҷ��� ���� �±״� 0 ���� boolean flg = false; for(Entry<String, Integer>
		 * e : tagRefund.entrySet()) { if(name.equals(e.getKey())) flg = true; }
		 * if(!flg) tagRefund.put(name, 0); } model.addAttribute("tagRefund",
		 * tagRefund);
		 */

		// ���� �⵵, ��
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH));
		model.addAttribute("year", year);
		model.addAttribute("month", month);

		// c:ser�� var�� ���ڷθ� �Է��ϸ� ���� �۵� ����
		// ���� �����
		ArrayList<StringInt> tmp = bDao.getYearSalesTotalAmount();
		Map<String, Integer> yearAmount = new HashMap<>();
		for (int i = 0; i < tmp.size(); i += 1) {
			String yearStr = tmp.get(i).getStr();
			int count = tmp.get(i).getCount();

			if (yearStr.equals(year)) {
				model.addAttribute("currYearAmount", count); // ���� �⵵�� �����
			}
			model.addAttribute("y" + yearStr, count); // ���� �����
			yearAmount.put(yearStr, count);
		}
		model.addAttribute("yearAmount", yearAmount);

		// ���� �Ǹŷ�
		ArrayList<StringInt> tmp1 = bDao.getYearSalesTotalCount();
		Map<String, Integer> yearCount = new HashMap<>();
		for (int i = 0; i < tmp1.size(); i += 1) {
			String yearStr = tmp1.get(i).getStr();
			int count = tmp1.get(i).getCount();

			if (yearStr.equals(year)) {
				model.addAttribute("currYearCount", count); // ���� �⵵�� �Ǹŷ�
			}
			model.addAttribute("c" + yearStr, count); // ���� �Ǹŷ�
			yearCount.put(yearStr, count);
		}
		model.addAttribute("yearCount", yearCount);

		// ���� ȯ�Ҿ�
		ArrayList<StringInt> yearRefund = bDao.getYearRefundTotalAmount();
		for (int i = 0; i < yearRefund.size(); i += 1) {
			model.addAttribute("yr" + yearRefund.get(i).getStr(), yearRefund.get(i).getCount());
		}

		// �о� ����
		int tagCount = bDao.getTag_mainCount();
		// �ְ� �±׺� �Ǹŷ�
		Map<String, Integer> tagWeeklyCount = bDao.getTagWeeklyCountDistinct();
		for (int i = 1; i <= 4; i += 1) { // �ְ�
			for (int j = 1; j <= tagCount; j += 1) { // �о߼�
				boolean flg = false; // ���� ���� Ȯ��
				String str = i + "" + j;

				for (Entry<String, Integer> e : tagWeeklyCount.entrySet()) {
					if (e.getKey().equals(str)) {
						model.addAttribute("t" + e.getKey(), e.getValue());
						flg = true;
						break;
						// log.debug("t" + e.getKey() + " : MAP");
					}
				}

				if (!flg) { // ���� ���� �о�
					model.addAttribute("t" + str, 0);
					// log.debug("t" + str);
				}
			}
		}
	}

	// ��������
	@Override
	public void getNotice(HttpServletRequest req, Model model) {
		int postCnt = hDao.getNoticeCnt();

		if (postCnt != 0) {
			int pageSize = 15;
			int pageNav = 10;

			String pageNum = req.getParameter("pageNum");
			if (pageNum == null)
				pageNum = "1";
			int currentPage = Integer.parseInt(pageNum);

			int start = (currentPage - 1) * pageSize + 1;
			int end = start + pageSize - 1;
			if (end > postCnt)
				end = postCnt;

			int navCnt = (postCnt / pageSize) + (postCnt % pageSize > 0 ? 1 : 0);
			int startPage = (currentPage / pageNav) * pageNav + 1;
			if (startPage % pageNav == 0)
				startPage -= pageNav;
			int endPage = startPage + pageNav - 1;
			if (endPage > navCnt)
				endPage = navCnt;

			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			ArrayList<Notice> list = hDao.getNotice(map);

			model.addAttribute("notices", list);
			model.addAttribute("num", start);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("pageNav", pageNav);
			model.addAttribute("navCnt", navCnt);
			model.addAttribute("pageNum", pageNum);
		}
		model.addAttribute("postCnt", postCnt);
	}

	// �������� �۾��� ó��
	@Override
	public void noticeWritePro(HttpServletRequest req, Model model) {
		String id = (String) req.getSession().getAttribute("memId");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String idx = req.getParameter("idx");

		int cnt = 0;

		Notice notice = new Notice();
		notice.setId(id);
		notice.setTitle(title);
		notice.setContent(content);

		System.out.println(notice);
		if (idx == null) {
			cnt = hDao.noticeWritePro(notice);
			// �� ���� ó
		} else {
			notice.setIdx(idx);
			cnt = hDao.noticeUpdate(notice);
		}
		model.addAttribute("cnt", cnt);
	}

	// �������� �󼼺���
	@Override
	public void noticeView(HttpServletRequest req, Model model) {
		String idx = req.getParameter("idx");

		Notice notice = hDao.noticeView(idx);

		model.addAttribute("pageNum", req.getParameter("pageNum"));
		model.addAttribute("notice", notice);
	}

	// �������� �� ����
	@Override
	public void noticeUpdate(HttpServletRequest req, Model model) {
		String idx = req.getParameter("idx");
		if (idx != null) {
			Notice notice = hDao.noticeView(idx);
			model.addAttribute("notice", notice);
		}
	}

	// �������� ��� �߰�
	@Override
	public void noticeCommentPro(HttpServletRequest req, Model model) {
		String comment = req.getParameter("comment");
		String id = (String) req.getSession().getAttribute("memId");
		
		// ��� �߰�
		NoticeComment nco = new NoticeComment();
		nco.setNotice_idx(req.getParameter("idx"));
		nco.setWriter_id(id);
		nco.setCom_content(comment);
		nco.setCom_step(0);
		nco.setRef_com_idx(0);
		int cnt = hDao.noticeCommentAdd(nco);

		model.addAttribute("writer_id", id);
		model.addAttribute("comment", comment);
		model.addAttribute("cnt", cnt);
	}
}