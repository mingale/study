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

	// 아이디 중복 확인
	public void confirmId(HttpServletRequest req, HttpServletResponse res) {
		String id = req.getParameter("id");

		MemberPers mp = MemberPersImpl.getInstance();
		int cnt = mp.confirmId(id);
		req.setAttribute("cnt", cnt);
	}
	
	//이메일 중복 확인
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
	
	//로그인
	@Override
	public void signIn(HttpServletRequest req, HttpServletResponse res) {
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");

		int cnt = mDao.signIn(id, pwd);
		if (cnt == 1) {
			// 아이디 세션에 저장
			req.getSession().setAttribute("memId", id);

			// 관리자 확인을 위한 회원 리스트
			MemberPers dao = MemberPersImpl.getInstance();
			Member m = dao.getMemberInfo(id);
			req.setAttribute("rating", m.getRating());

			// 최근 도서
			ArrayList<RecentBook> recent = null;
			req.getSession().setAttribute("recent", recent);
		}
		req.setAttribute("cnt", cnt);
	}

	// 회원가입
	@Override
	public void signUp(HttpServletRequest req, HttpServletResponse res) {
		String email = req.getParameter("email");

		// 인증키 랜덤
		EmailCheckHandler ech = new EmailCheckHandler();
		String key = ech.randomKey();

		// 회원가입 정보
		Member m = new Member();
		m.setId(req.getParameter("id"));
		m.setPwd(req.getParameter("pwd"));
		m.setName(req.getParameter("name"));
		m.setPhone(req.getParameter("phone"));
		m.setEmail(email);
		m.setAddress(req.getParameter("address"));
		m.setKey(key);

		// 회원가입
		int cnt = mDao.signUp(m);

		// 회원가입 성공 시 인증 메일 전송
		if (cnt == 1) {
			mDao.sendGmail(email, key, 0);
		}
		req.setAttribute("cnt", cnt);
	}

	// 이메일 인증key 존재 확인
	public void confirmEmailKey(HttpServletRequest req, HttpServletResponse res) {
		String key = req.getParameter("key");
		int view = Integer.parseInt(req.getParameter("view"));

		MemberPers dao = MemberPersImpl.getInstance();
		int cnt = dao.confirmEmailKey(key);
		
		//인증키 존재하면
		if(cnt != 0) {
			//아이디 찾기일 경우
			if(view == 1) {
				String id = dao.getId(key);
				req.setAttribute("id", id);
			}
			//비밀번호 찾기 일 경우
			if(view == 2) {
				String id = dao.getId(key);
				String pwd = dao.getPwd(key);
				
				req.setAttribute("id", id);
				req.setAttribute("pwd", pwd);
			}
		}

		System.out.println("키: " + key + " / 인증 : " + view + " / 성공여부: " + cnt);
		
		req.setAttribute("cnt", cnt);
		req.setAttribute("view", view);
	}

	//아이디/비밀번호 찾기 이메일 전송
	public void memberFindEmailKey(HttpServletRequest req, HttpServletResponse res) {
		String id = req.getParameter("id");
		String email = req.getParameter("email");
		String idPwd = req.getParameter("idCheck");
		
		int cnt = 0;
		int view;
		
		MemberPers dao = MemberPersImpl.getInstance();
		
		//ID 찾기
		if(idPwd.equals("n")) {
			//이메일 존재 여부 확인 후 아이디 가져오기
			id = dao.confirmEmail(email);
			view = 1;
			
		//PWD 찾기
		} else {
			//아이디 존재 여부 확인
			cnt = dao.confirmId(id);
			if(cnt == 0) id = null;
			view = 2;
		}
		
		//회원이 존재하면 인증 메일 전송
		if(id != null) {
			// 인증키 랜덤
			EmailCheckHandler ech = new EmailCheckHandler();
			String key = ech.randomKey();
			
			// 인증 이메일 전송
			cnt = dao.sendGmail(email, key, view);
			//전송 성공하면 인증키 저장하기
			if(cnt != 0) cnt = dao.memberEmailKeyUpdate(id, key);
		}
		
		req.setAttribute("email", email);
		req.setAttribute("cnt", cnt);
	}

	// 관리자 메인
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

	// 회원 강제 탈퇴
	@Override
	public void memberDelete(HttpServletRequest req, HttpServletResponse res) {
		String tmpChk = req.getParameter("chk");
		int chk = 0;
		if(tmpChk != null) {
			chk = Integer.parseInt(tmpChk);
		}

		int cnt = 0;
		//회원 탈퇴
		if (chk == 0) {
			String strId = (String) req.getSession().getAttribute("memId");
			cnt = mDao.memberDelete(strId);
			
		//관리자용 회원 탈퇴(id만 남기기)
		} else {
			String id = req.getParameter("id");
			String[] ids = id.split(",");
			
			for(String tmp : ids) {
				cnt = mDao.memberDelete(tmp);
				//cnt = mDao.setHostMemberAllDelete(tmp); 완전 삭제
			}
		}

		req.setAttribute("cnt", cnt);
	}

	// 관리자 주문 목록
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

		// 내역이 존재하면
		if (cnt > 0) {
			int start = (currentPage - 1) * pageSize + 1;
			int end = start + pageSize - 1;
			if (end > cnt)
				end = cnt;
			int number = cnt - (currentPage - 1) * pageSize;

			int pageCnt = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0);
			int startPage = (currentPage / pageNav) * pageNav + 1;
			if (currentPage % pageNav == 0)// 만일 현재 페이지 번호를 페이징개수로 나눴을 때 딱 떨어진다면 현재 페이지 번호가 첫구간(1~pageNav)의 마지막 번째라는 뜻
				startPage -= pageNav; // 따라서 1만 남기기 위해 페이징개수를 뺀다.
			int endPage = startPage + pageNav - 1;
			if (endPage > pageCnt)
				endPage = pageCnt;

			req.setAttribute("pageNum", pageNum); // 페이지 이동
			req.setAttribute("number", number); // 화면에 표시할 글번호
			req.setAttribute("currentPage", currentPage); // 현재 페이지
			req.setAttribute("startPage", startPage); // 페이징 처음 버튼
			req.setAttribute("endPage", endPage); // 페이징 마지막 버튼
			req.setAttribute("pageNav", pageNav); // 페이징 개수
			req.setAttribute("pageCnt", pageCnt); // 페이지 총개수

			// 주문 내역
			ArrayList<Bespeak> orders = dao.getHostOrderList(start, end);
			req.setAttribute("orders", orders);
		}
		req.setAttribute("cnt", cnt);
	}

	// 관리자 주문 상태 수정
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
			//주문 수량이 확보되어 있을 시에만 재고 감소
			cnt = bDao.orderBookCountMul(order_num);

			//주문 상태 수정
			if(cnt == 1) {
				cnt = dao.orderStateUpdate(order_num, 1);
			}
		}
		
		req.setAttribute("cnt", cnt);
	}

	// 관리자 배송 시작
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

	// 관리자 환불 내역
	@Override
	public void getHostRefundView(HttpServletRequest req, HttpServletResponse res) {
		MemberPers dao = MemberPersImpl.getInstance();

		// 총 게시글수
		int postCnt = dao.getRefundCnt();
		if (postCnt != 0) {

			String pageNum = req.getParameter("pageNum");
			if (pageNum == null)
				pageNum = "1";
			int currentPage = Integer.parseInt(pageNum);

			int pageSize = 15; // 한 페이지당 게시글수
			int pageNav = 10; // 한 페이지당 페이징수

			int start = (currentPage - 1) * pageSize + 1;
			int end = start + pageSize - 1;
			if (end > postCnt)
				end = postCnt;

			// 총 페이징수
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

	// 회원 목록
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

			// 목록
			ArrayList<Member> members = dao.getMemberList(start, end);
			req.setAttribute("members", members);
		}
		req.setAttribute("cnt", cnt);
	}

	// 관리자 회원 수정
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

	// 관리자 주문 삭제
	@Override
	public void orderDelete(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("order_num");
		
		MemberPers dao = MemberPersImpl.getInstance();
		int cnt = 0;
		
		//중복 삭제
		if(order_num == null) { 
			order_num = req.getParameter("order_nums");
			String[] nums = order_num.split(",");
			for(String num : nums) {
				cnt = dao.orderDelete(num);
			}
		//개별 삭제
		} else {
			cnt = dao.orderDelete(order_num);
		}
		req.setAttribute("cnt", cnt);
	}

	// 환불 완료
	@Override
	public void refundOk(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("order_num");

		//재고 다시 추가
		BookPers bDao = BookPersImpl.getInstance();
		int cnt = bDao.refundBookCountAdd(order_num);
		
		//추가 완료되면 관리자 주문 상태 수정
		if(cnt != 0) {
			MemberPers dao = MemberPersImpl.getInstance();
			cnt = dao.orderStateUpdate(order_num, 9);
		}

		req.setAttribute("cnt", cnt);
	}

	//환불 거부
	@Override
	public void refundNo(HttpServletRequest req, HttpServletResponse res) {
		String order_num = req.getParameter("order_num");
		
		MemberPers dao = MemberPersImpl.getInstance();
		int cnt = dao.orderStateUpdate(order_num, 7);
		
		req.setAttribute("cnt", cnt);
	}
	
	// 고객 센터
	@Override
	public void helpView(HttpServletRequest req, HttpServletResponse res) {
		String googleApiUrl = "https://maps.googleapis.com/maps/api/staticmap?";
		String ne = "37.478928,126.878965"; //위도 경도
		
		String center = "center=" + ne; //marker 없는 경우 필수 
		String zoom = "&zoom=" + "17"; 	//marker 없는 경우 필수. 20:건물, 15:도로, 10:구/군/시, 5:대륙, 1:세계
		String size = "&size=" + "1200x800";
		String marker = "&markers=" + "color:blue" + "%7C" + "label:C" + "%7C" + ne; //%7C = 하이픈(|) : 마커 설정 구분자. URL 인코딩
		String key = "&key=" + "AIzaSyD-iJfYjArAMU7227--yo5AELs54bgFiC4"; // Google Static Maps API Key
		

		//String url = googleApiUrl + center + zoom + size + key;
		String url = googleApiUrl + marker + size + key;
		
		req.setAttribute("url", url);
	}

	// 결산
	@Override
	public void getResultTotal(HttpServletRequest req, HttpServletResponse res) {
		BookPers dao = BookPersImpl.getInstance();
		String chart = req.getParameter("chart");

		int total = dao.getSalesTotalCount(); // 도서 판매량
		int totalSale = dao.getSalesTotalAmount(); // 매출액

		req.setAttribute("total", total);
		req.setAttribute("totalSale", totalSale);
		req.setAttribute("chart", chart);
		
		/*
		//태그별 판매량
		String[] tagName = {"newS", "bestS", "goodS", "nullS"};
		Map<String, Integer> tagMap = dao.getTagSalesTotal();
		for(String name : tagName) { //판매량이 없는 태그는 0 삽입 
			boolean flg = false;
			for(Entry<String, Integer> e : tagMap.entrySet()) {
				if(name.equals(e.getKey())) flg = true;
			}
			if(!flg) tagMap.put(name, 0);
		}
		req.setAttribute("tagMap", tagMap);
		
		//태그별 환불량
		tagName = new String[] {"newR", "bestR", "goodR", "nullR"};
		Map<String, Integer> tagRefund = dao.getTagRefundTotal();
		for(String name : tagName) { //환불량이 없는 태그는 0 삽입
			boolean flg = false;
			for(Entry<String, Integer> e : tagRefund.entrySet()) {
				if(name.equals(e.getKey())) flg = true;
			}
			if(!flg) tagRefund.put(name, 0);
		}
		req.setAttribute("tagRefund", tagRefund);*/

		//현재 년도, 월
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH));
		req.setAttribute("year", year);
		req.setAttribute("month", month);
		
		//c:ser의 var을 숫자로만 입력하면 정상 작동 안함
		//연간 매출액
		Map<String, Integer> yearAmount = dao.getYearSalesTotalAmount();
		req.setAttribute("yearAmount", yearAmount);
		for(Entry<String, Integer> e : yearAmount.entrySet()) {
			if(e.getKey().equals(year)) {
				req.setAttribute("currYearAmount", e.getValue());	//현재 년도의 매출액
			}
			req.setAttribute("y" + e.getKey(), e.getValue());		//연간 매출액
		}
		
		//연간 판매량
		Map<String, Integer> yearCount = dao.getYearSalesTotalCount();
		req.setAttribute("yearCount", yearCount);
		for(Entry<String, Integer> e : yearCount.entrySet()) {
			if(e.getKey().equals(year)) {
				req.setAttribute("currYearCount", e.getValue());	//현재 년도의 판매량
			}
			req.setAttribute("c" + e.getKey(), e.getValue());		//연간 판매량
		}
		
		//연간 환불액
		Map<String, Integer> yearRefund = dao.getYearRefundTotalAmount();
		for(Entry<String, Integer> e : yearRefund.entrySet()) {
			req.setAttribute("yr" + e.getKey(), e.getValue());
		}
		
		
		//분야 개수
		int tagCount = dao.getTag_mainCount();
		//주간 태그별 판매량
		Map<String, Integer> tagWeeklyCount = dao.getTagWeeklyCount();
		for(int i = 1; i <= 4; i += 1) { //주간
			for(int j = 1; j <= tagCount; j += 1) { //분야수
				boolean flg = false; //매출 여부 확인
				String str = i + "" + j;
				
				for(Entry<String, Integer> e : tagWeeklyCount.entrySet()) {
					if(e.getKey().equals(str)) {
						req.setAttribute("t" + e.getKey(), e.getValue());
						flg = true;
						break;
						//System.out.println("t" + e.getKey() + " : MAP");
					}
				}

				if(!flg) { //매출 없는 분야
					req.setAttribute("t" + str, 0);
					//System.out.println("t" + str);
				}
			}
		}
	}
}
