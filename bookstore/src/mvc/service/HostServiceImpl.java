package mvc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.EmailCheckHandler;
import mvc.persistence.BookPers;
import mvc.persistence.BookPersImpl;
import mvc.persistence.MemberPers;
import mvc.persistence.MemberPersImpl;
import mvc.vo.Bespeak;
import mvc.vo.Book;
import mvc.vo.BookSub;
import mvc.vo.Member;
import mvc.vo.RecentBook;

public class HostServiceImpl implements HostService {

	MemberPers mDao = MemberPersImpl.getInstance();

	// ���̵� �ߺ� Ȯ��
	public void confirmId(HttpServletRequest req, HttpServletResponse res) {
		String id = req.getParameter("id");

		MemberPers mp = MemberPersImpl.getInstance();
		int cnt = mp.confirmId(id);
		req.setAttribute("cnt", cnt);
	}
	
	//�̸��� �ߺ� Ȯ��
	public void confirmEmail(HttpServletRequest req, HttpServletResponse res) {
		String email = req.getParameter("email");
		
		MemberPers dao = MemberPersImpl.getInstance();
		String id = dao.confirmEmail(email);
		
		if(id != null) { 
			req.setAttribute("cnt", 1);
		} else {
			req.setAttribute("cnt", 0);
		}
	}
	
	//�α���
	@Override
	public void signIn(HttpServletRequest req, HttpServletResponse res) {
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");

		int cnt = mDao.signIn(id, pwd);
		if (cnt == 1) {
			// ���̵� ���ǿ� ����
			req.getSession().setAttribute("memId", id);

			// ������ Ȯ���� ���� ȸ�� ����Ʈ
			MemberPers dao = MemberPersImpl.getInstance();
			Member m = dao.getMemberInfo(id);
			req.setAttribute("rating", m.getRating());

			// �ֱ� ����
			ArrayList<RecentBook> recent = null;
			req.getSession().setAttribute("recent", recent);
		}
		req.setAttribute("cnt", cnt);
	}

	// ȸ������
	@Override
	public void signUp(HttpServletRequest req, HttpServletResponse res) {
		String email = req.getParameter("email");

		// ����Ű ����
		EmailCheckHandler ech = new EmailCheckHandler();
		String key = ech.randomKey();

		// ȸ������ ����
		Member m = new Member();
		m.setId(req.getParameter("id"));
		m.setPwd(req.getParameter("pwd"));
		m.setName(req.getParameter("name"));
		m.setPhone(req.getParameter("phone"));
		m.setEmail(email);
		m.setAddress(req.getParameter("address"));
		m.setKey(key);

		// ȸ������
		int cnt = mDao.signUp(m);

		// ȸ������ ���� �� ���� ���� ����
		if (cnt == 1) {
			mDao.sendGmail(email, key, 0);
		}
		req.setAttribute("cnt", cnt);
	}

	// �̸��� ����key ���� Ȯ��
	public void confirmEmailKey(HttpServletRequest req, HttpServletResponse res) {
		String key = req.getParameter("key");
		int view = Integer.parseInt(req.getParameter("view"));

		MemberPers dao = MemberPersImpl.getInstance();
		int cnt = dao.confirmEmailKey(key);
		
		//����Ű �����ϸ�
		if(cnt != 0) {
			//���̵� ã���� ���
			if(view == 1) {
				String id = dao.getId(key);
				req.setAttribute("id", id);
			}
			//��й�ȣ ã�� �� ���
			if(view == 2) {
				String id = dao.getId(key);
				String pwd = dao.getPwd(key);
				
				req.setAttribute("id", id);
				req.setAttribute("pwd", pwd);
			}
		}

		System.out.println("Ű: " + key + " / ���� : " + view + " / ��������: " + cnt);
		
		req.setAttribute("cnt", cnt);
		req.setAttribute("view", view);
	}

	//���̵�/��й�ȣ ã�� �̸��� ����
	public void memberFindEmailKey(HttpServletRequest req, HttpServletResponse res) {
		String id = req.getParameter("id");
		String email = req.getParameter("email");
		String idPwd = req.getParameter("idCheck");
		
		int cnt = 0;
		int view;
		
		MemberPers dao = MemberPersImpl.getInstance();
		
		//ID ã��
		if(idPwd.equals("n")) {
			//�̸��� ���� ���� Ȯ�� �� ���̵� ��������
			id = dao.confirmEmail(email);
			view = 1;
			
		//PWD ã��
		} else {
			//���̵� ���� ���� Ȯ��
			cnt = dao.confirmId(id);
			if(cnt == 0) id = null;
			view = 2;
		}
		
		//ȸ���� �����ϸ� ���� ���� ����
		if(id != null) {
			// ����Ű ����
			EmailCheckHandler ech = new EmailCheckHandler();
			String key = ech.randomKey();
			
			// ���� �̸��� ����
			cnt = dao.sendGmail(email, key, view);
			//���� �����ϸ� ����Ű �����ϱ�
			if(cnt != 0) cnt = dao.memberEmailKeyUpdate(id, key);
		}
		
		req.setAttribute("email", email);
		req.setAttribute("cnt", cnt);
	}

	// ������ ����
	@Override
	public void mainView(HttpServletRequest req, HttpServletResponse res) {
		int start = 1;
		int end = 7;

		BookPers dao = BookPersImpl.getInstance();

		ArrayList<Book> books = dao.getBookList(start, end);
		ArrayList<BookSub> bookSubs = dao.getBookSubList(start, end);
		ArrayList<Bespeak> orders = MemberPersImpl.getInstance().getHostOrderList(start, end);

		req.setAttribute("books", books);
		req.setAttribute("bookSubs", bookSubs);
		req.setAttribute("orders", orders);
	}

	
	@Override
	public void memberQuery(HttpServletRequest req, HttpServletResponse res) {
		String id = (String) req.getSession().getAttribute("memId");
		String pwd = req.getParameter("pwd");

		int cnt = mDao.signIn(id, pwd);
		if (cnt == 1) {
			Member m = mDao.getMemberInfo(id);
			req.setAttribute("m", m);
		}
		req.setAttribute("cnt", cnt);
	}

	@Override
	public void memberUpdate(HttpServletRequest req, HttpServletResponse res) {
		Member m = new Member();
		m.setId(req.getParameter("id"));
		m.setPwd(req.getParameter("pwd"));
		m.setName(req.getParameter("name"));
		m.setPhone(req.getParameter("phone"));
		m.setEmail(req.getParameter("email"));
		m.setAddress(req.getParameter("address"));

		int cnt = mDao.memberUpdate(m);
		req.setAttribute("cnt", cnt);
	}

	// ȸ�� ���� Ż��
	@Override
	public void memberDelete(HttpServletRequest req, HttpServletResponse res) {
		String tmpChk = req.getParameter("chk");
		int chk = 0;
		if(tmpChk != null) {
			chk = Integer.parseInt(tmpChk);
		}

		int cnt = 0;
		//ȸ�� Ż��
		if (chk == 0) {
			String strId = (String) req.getSession().getAttribute("memId");
			cnt = mDao.memberDelete(strId);
			
		//�����ڿ� ȸ�� Ż��(id�� �����)
		} else {
			String id = req.getParameter("id");
			String[] ids = id.split(",");
			
			for(String tmp : ids) {
				cnt = mDao.memberDelete(tmp);
				//cnt = mDao.setHostMemberAllDelete(tmp); ���� ����
			}
		}

		req.setAttribute("cnt", cnt);
	}

	// ������ �ֹ� ���
	@Override
	public void hostOrderView(HttpServletRequest req, HttpServletResponse res) {
		MemberPers dao = MemberPersImpl.getInstance();

		int pageSize = 15;
		int pageNav = 10;

		String pageNum = req.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);

		int cnt = dao.getHostOrderCnt();

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

			req.setAttribute("pageNum", pageNum); // ������ �̵�
			req.setAttribute("number", number); // ȭ�鿡 ǥ���� �۹�ȣ
			req.setAttribute("currentPage", currentPage); // ���� ������
			req.setAttribute("startPage", startPage); // ����¡ ó�� ��ư
			req.setAttribute("endPage", endPage); // ����¡ ������ ��ư
			req.setAttribute("pageNav", pageNav); // ����¡ ����
			req.setAttribute("pageCnt", pageCnt); // ������ �Ѱ���

			// �ֹ� ����
			ArrayList<Bespeak> orders = dao.getHostOrderList(start, end);
			req.setAttribute("orders", orders);
		}
		req.setAttribute("cnt", cnt);
	}

	// ������ �ֹ� ���� ����
	public void orderStateUpdate(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("order_num");
		
		BookPers bDao = BookPersImpl.getInstance();
		MemberPers dao = MemberPersImpl.getInstance();
		int cnt = 0;
		
		if(order_num == null) {
			order_num = req.getParameter("order_nums");
			String[] nums = order_num.split(",");
			for(String num : nums) {
				cnt = bDao.orderBookCountMul(num);
				if(cnt == 1) {
					cnt = dao.orderStateUpdate(num, 1);
				}	
			}
		} else {
			//�ֹ� ������ Ȯ���Ǿ� ���� �ÿ��� ��� ����
			cnt = bDao.orderBookCountMul(order_num);

			//�ֹ� ���� ����
			if(cnt == 1) {
				cnt = dao.orderStateUpdate(order_num, 1);
			}
		}
		
		req.setAttribute("cnt", cnt);
	}

	// ������ ��� ����
	@Override
	public void shippingPro(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("orderNum");
		String ship_num = req.getParameter("shippingNum");

		MemberPers dao = MemberPersImpl.getInstance();
		int cnt = dao.shippingInsert(order_num, ship_num);
		;
		if (cnt != 0) {
			cnt = dao.orderStateUpdate(order_num, 2);
		}

		req.setAttribute("cnt", cnt);
	}

	// ������ ȯ�� ����
	@Override
	public void getHostRefundView(HttpServletRequest req, HttpServletResponse res) {
		MemberPers dao = MemberPersImpl.getInstance();

		// �� �Խñۼ�
		int postCnt = dao.getRefundCnt();
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

			ArrayList<Bespeak> refunds = dao.getHostRefundList(start, end);
			req.setAttribute("refunds", refunds);

			req.setAttribute("pageNum", pageNum);
			req.setAttribute("pageNav", pageNav);
			req.setAttribute("pageCnt", pageCnt);
			req.setAttribute("startPage", startPage);
			req.setAttribute("endPage", endPage);
		}
	}

	// ȸ�� ���
	@Override
	public void getMemberView(HttpServletRequest req, HttpServletResponse res) {
		MemberPers dao = MemberPersImpl.getInstance();

		int pageSize = 15;
		int pageNav = 10;

		String pageNum = req.getParameter("pageNum");
		if (pageNum == null)
			pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);

		int cnt = dao.getMemberCnt();
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

			req.setAttribute("pageCnt", pageCnt);
			req.setAttribute("startPage", startPage);
			req.setAttribute("endPage", endPage);
			req.setAttribute("pageNav", pageNav);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("number", number);

			// ���
			ArrayList<Member> members = dao.getMemberList(start, end);
			req.setAttribute("members", members);
		}
		req.setAttribute("cnt", cnt);
	}

	// ������ ȸ�� ����
	@Override
	public void setHostMemberUpdate(HttpServletRequest req, HttpServletResponse res) {
		int chk = Integer.parseInt(req.getParameter("chk"));
		String id = req.getParameter("id");
		String memo = req.getParameter("memo");
		String rating = req.getParameter("rating");

		MemberPers dao = MemberPersImpl.getInstance();
		int cnt = 0;

		if (chk == 0) {
			cnt = dao.setHostMemberUpdate(id, memo, Integer.parseInt(rating));
		} else {
			cnt = dao.setHostMemberAllUpdate(id, memo, rating);
		}

		req.setAttribute("cnt", cnt);

		System.out.println(chk);
	}

	// ������ �ֹ� ����
	@Override
	public void orderDelete(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("order_num");
		
		MemberPers dao = MemberPersImpl.getInstance();
		int cnt = 0;
		
		//�ߺ� ����
		if(order_num == null) { 
			order_num = req.getParameter("order_nums");
			String[] nums = order_num.split(",");
			for(String num : nums) {
				cnt = dao.orderDelete(num);
			}
		//���� ����
		} else {
			cnt = dao.orderDelete(order_num);
		}
		req.setAttribute("cnt", cnt);
	}

	// ȯ�� �Ϸ�
	@Override
	public void refundOk(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("order_num");

		//��� �ٽ� �߰�
		BookPers bDao = BookPersImpl.getInstance();
		int cnt = bDao.refundBookCountAdd(order_num);
		
		//�߰� �Ϸ�Ǹ� ������ �ֹ� ���� ����
		if(cnt != 0) {
			MemberPers dao = MemberPersImpl.getInstance();
			cnt = dao.orderStateUpdate(order_num, 9);
		}

		req.setAttribute("cnt", cnt);
	}

	//ȯ�� �ź�
	@Override
	public void refundNo(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("order_num");
		
		MemberPers dao = MemberPersImpl.getInstance();
		int cnt = dao.orderStateUpdate(order_num, 7);
		
		req.setAttribute("cnt", cnt);
	}
	
	// �� ����
	@Override
	public void helpView(HttpServletRequest req, HttpServletResponse res) {
		String googleApiUrl = "https://maps.googleapis.com/maps/api/staticmap?";
		String ne = "37.478928,126.878965"; //���� �浵
		
		String center = "center=" + ne; //marker ���� ��� �ʼ� 
		String zoom = "&zoom=" + "17"; 	//marker ���� ��� �ʼ�. 20:�ǹ�, 15:����, 10:��/��/��, 5:���, 1:����
		String size = "&size=" + "1200x800";
		String marker = "&markers=" + "color:blue" + "%7C" + "label:C" + "%7C" + ne; //%7C = ������(|) : ��Ŀ ���� ������. URL ���ڵ�
		String key = "&key=" + "AIzaSyD-iJfYjArAMU7227--yo5AELs54bgFiC4"; // Google Static Maps API Key
		

		//String url = googleApiUrl + center + zoom + size + key;
		String url = googleApiUrl + marker + size + key;
		
		req.setAttribute("url", url);
	}

	// ���
	@Override
	public void getResultTotal(HttpServletRequest req, HttpServletResponse res) {
		BookPers dao = BookPersImpl.getInstance();
		String chart = req.getParameter("chart");

		int total = dao.getSalesTotalCount(); // ���� �Ǹŷ�
		int totalSale = dao.getSalesTotalAmount(); // �����

		req.setAttribute("total", total);
		req.setAttribute("totalSale", totalSale);
		req.setAttribute("chart", chart);
		
		/*
		//�±׺� �Ǹŷ�
		String[] tagName = {"newS", "bestS", "goodS", "nullS"};
		Map<String, Integer> tagMap = dao.getTagSalesTotal();
		for(String name : tagName) { //�Ǹŷ��� ���� �±״� 0 ���� 
			boolean flg = false;
			for(Entry<String, Integer> e : tagMap.entrySet()) {
				if(name.equals(e.getKey())) flg = true;
			}
			if(!flg) tagMap.put(name, 0);
		}
		req.setAttribute("tagMap", tagMap);
		
		//�±׺� ȯ�ҷ�
		tagName = new String[] {"newR", "bestR", "goodR", "nullR"};
		Map<String, Integer> tagRefund = dao.getTagRefundTotal();
		for(String name : tagName) { //ȯ�ҷ��� ���� �±״� 0 ����
			boolean flg = false;
			for(Entry<String, Integer> e : tagRefund.entrySet()) {
				if(name.equals(e.getKey())) flg = true;
			}
			if(!flg) tagRefund.put(name, 0);
		}
		req.setAttribute("tagRefund", tagRefund);*/

		//���� �⵵, ��
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH));
		req.setAttribute("year", year);
		req.setAttribute("month", month);
		
		//c:ser�� var�� ���ڷθ� �Է��ϸ� ���� �۵� ����
		//���� �����
		Map<String, Integer> yearAmount = dao.getYearSalesTotalAmount();
		req.setAttribute("yearAmount", yearAmount);
		for(Entry<String, Integer> e : yearAmount.entrySet()) {
			if(e.getKey().equals(year)) {
				req.setAttribute("currYearAmount", e.getValue());	//���� �⵵�� �����
			}
			req.setAttribute("y" + e.getKey(), e.getValue());		//���� �����
		}
		
		//���� �Ǹŷ�
		Map<String, Integer> yearCount = dao.getYearSalesTotalCount();
		req.setAttribute("yearCount", yearCount);
		for(Entry<String, Integer> e : yearCount.entrySet()) {
			if(e.getKey().equals(year)) {
				req.setAttribute("currYearCount", e.getValue());	//���� �⵵�� �Ǹŷ�
			}
			req.setAttribute("c" + e.getKey(), e.getValue());		//���� �Ǹŷ�
		}
		
		//���� ȯ�Ҿ�
		Map<String, Integer> yearRefund = dao.getYearRefundTotalAmount();
		for(Entry<String, Integer> e : yearRefund.entrySet()) {
			req.setAttribute("yr" + e.getKey(), e.getValue());
		}
		
		
		//�о� ����
		int tagCount = dao.getTag_mainCount();
		//�ְ� �±׺� �Ǹŷ�
		Map<String, Integer> tagWeeklyCount = dao.getTagWeeklyCount();
		for(int i = 1; i <= 4; i += 1) { //�ְ�
			for(int j = 1; j <= tagCount; j += 1) { //�о߼�
				boolean flg = false; //���� ���� Ȯ��
				String str = i + "" + j;
				
				for(Entry<String, Integer> e : tagWeeklyCount.entrySet()) {
					if(e.getKey().equals(str)) {
						req.setAttribute("t" + e.getKey(), e.getValue());
						flg = true;
						break;
						//System.out.println("t" + e.getKey() + " : MAP");
					}
				}

				if(!flg) { //���� ���� �о�
					req.setAttribute("t" + str, 0);
					//System.out.println("t" + str);
				}
			}
		}
	}
}
