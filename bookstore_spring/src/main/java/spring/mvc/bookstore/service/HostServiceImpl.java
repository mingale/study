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

	// 아이디 중복 확인
	public void confirmId(HttpServletRequest req, Model model) {
		String id = req.getParameter("id");

		int cnt = hDao.confirmId(id);
		model.addAttribute("cnt", cnt);
	}

	// 이메일 중복 확인
	public void confirmEmail(HttpServletRequest req, Model model) {
		String email = req.getParameter("email");

		String id = hDao.confirmEmail(email);

		if (id != null) { // 이미 존재
			model.addAttribute("cnt", 1);
		} else { // 사용 가능
			model.addAttribute("cnt", 0);
			req.getSession().setAttribute("echk", "Y");
		}
	}

	// 로그인
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

			// 세션에 ID, rating 저장
			req.getSession().setAttribute("memId", id);
			req.getSession().setAttribute("rating", rating);

			// 최근 도서
			ArrayList<RecentBook> recent = null;
			req.getSession().setAttribute("recent", recent);
		}
		model.addAttribute("cnt", cnt);
	}

	// 회원가입
	@Override
	public void signUp(HttpServletRequest req, Model model) {
		String email = req.getParameter("email");
		String echk = (String) req.getSession().getAttribute("echk"); // 이메일 중복 확인 여부

		int cnt = 0;
		if (echk != null) {
			// 인증키 랜덤
			EmailHandler ech = new EmailHandler();
			String key = ech.randomKey();

			// 회원가입 정보
			Member m = new Member();
			m.setId(req.getParameter("id"));
			m.setPwd(req.getParameter("pwd"));
			m.setName(req.getParameter("name"));
			m.setPhone(req.getParameter("phone"));
			m.setEmail(email);
			m.setAddress(req.getParameter("address"));
			m.setE_key(key);

			// 회원가입
			cnt = hDao.signUp(m);

			// 회원가입 성공 시 인증 메일 전송
			if (cnt == 1) {
				hDao.sendGmail(email, key, 0);
			}

			req.getSession().invalidate();
		} else {
			cnt = 9;
		}
		model.addAttribute("cnt", cnt);
	}

	// 이메일 인증key 존재 확인
	public void confirmEmailKey(HttpServletRequest req, Model model) {
		String key = req.getParameter("key");
		int view = Integer.parseInt(req.getParameter("view"));

		int cnt = hDao.confirmEmailKey(key);

		// 인증키 존재하면
		if (cnt != 0) {
			// 아이디 찾기일 경우
			if (view == 1) {
				String id = hDao.getId(key);
				model.addAttribute("id", id);
			}
			// 비밀번호 찾기 일 경우
			if (view == 2) {
				String id = hDao.getId(key);
				String pwd = hDao.getPwd(key);

				model.addAttribute("id", id);
				model.addAttribute("pwd", pwd);
			}
		}

		log.debug("키: " + key + " / 인증 : " + view + " / 성공여부: " + cnt);

		model.addAttribute("cnt", cnt);
		model.addAttribute("view", view);
	}

	// 아이디/비밀번호 찾기 이메일 전송
	public void memberFindEmailKey(HttpServletRequest req, Model model) {
		String id = req.getParameter("id");
		String email = req.getParameter("email");
		String idPwd = req.getParameter("idCheck");

		int cnt = 0;
		int view;

		// ID 찾기
		if (idPwd.equals("n")) {
			// 이메일 존재 여부 확인 후 아이디 가져오기
			id = hDao.confirmEmail(email);
			view = 1;

			// PWD 찾기
		} else {
			// 아이디 존재 여부 확인
			cnt = hDao.confirmId(id);
			if (cnt == 0)
				id = null;
			view = 2;
		}

		// 회원이 존재하면 인증 메일 전송
		if (id != null) {
			// 인증키 랜덤
			EmailHandler ech = new EmailHandler();
			String key = ech.randomKey();

			// 인증 이메일 전송
			cnt = hDao.sendGmail(email, key, view);

			// 전송 성공하면 인증키 저장하기
			Map<String, Object> map = new HashMap<>();
			map.put("id", id);
			map.put("key", key);
			if (cnt != 0)
				cnt = hDao.memberEmailKeyUpdate(map);
		}

		model.addAttribute("email", email);
		model.addAttribute("cnt", cnt);
	}

	// 관리자 메인
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

	// 회원 강제 탈퇴
	@Override
	public void memberDelete(HttpServletRequest req, Model model) {
		String tmpChk = req.getParameter("chk");
		int chk = 0;
		if (tmpChk != null) {
			chk = Integer.parseInt(tmpChk);
		}

		int cnt = 0;
		// 회원 탈퇴
		if (chk == 0) {
			String strId = (String) req.getSession().getAttribute("memId");
			cnt = mDao.memberDelete(strId);

			// 관리자용 회원 탈퇴(id만 남기기)
		} else {
			String id = req.getParameter("id");
			String[] ids = id.split(",");

			for (String tmp : ids) {
				cnt = mDao.memberDelete(tmp);
				// cnt = mDao.setHostMemberAllDelete(tmp); 완전 삭제
			}
		}

		model.addAttribute("cnt", cnt);
	}

	// 관리자 주문 목록
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

			model.addAttribute("pageNum", pageNum); // 페이지 이동
			model.addAttribute("number", number); // 화면에 표시할 글번호
			model.addAttribute("currentPage", currentPage); // 현재 페이지
			model.addAttribute("startPage", startPage); // 페이징 처음 버튼
			model.addAttribute("endPage", endPage); // 페이징 마지막 버튼
			model.addAttribute("pageNav", pageNav); // 페이징 개수
			model.addAttribute("pageCnt", pageCnt); // 페이지 총개수

			// 주문 내역
			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			ArrayList<Bespeak> orders = hDao.getHostOrderDistinctList(map);
			model.addAttribute("orders", orders);
		}
		model.addAttribute("cnt", cnt);
	}

	// 관리자 주문 상태 수정
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
			// 주문 수량이 확보되어 있을 시에만 재고 감소
			map.put("order_num", order_num);
			cnt = bDao.orderBookCountMul(map);

			// 주문 상태 수정
			if (cnt == 1) {
				map = new HashMap<>();
				map.put("state", 1);
				map.put("num", order_num);
				cnt = hDao.orderStateUpdate(map);
			}
		}

		model.addAttribute("cnt", cnt);
	}

	// 관리자 배송 시작
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

	// 관리자 환불 내역
	@Override
	public void getHostRefundView(HttpServletRequest req, Model model) {
		// 총 게시글수
		int postCnt = hDao.getRefundCnt();
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

	// 회원 목록
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

			// 목록
			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("end", end);
			ArrayList<Member> members = hDao.getMemberList(map);
			model.addAttribute("members", members);
		}
		model.addAttribute("cnt", cnt);
	}

	// 관리자 회원 수정
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

	// 관리자 주문 삭제
	@Override
	public void orderDelete(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("order_num");

		int cnt = 0;

		// 중복 삭제
		if (order_num == null) {
			order_num = req.getParameter("order_nums");
			String[] nums = order_num.split(",");
			for (String num : nums) {
				cnt = hDao.orderDelete(num);
			}
			// 개별 삭제
		} else {
			cnt = hDao.orderDelete(order_num);
		}
		model.addAttribute("cnt", cnt);
	}

	// 환불 완료
	@Override
	public void refundOk(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("order_num");

		// 재고 다시 추가
		Map<String, Object> map = new HashMap<>();
		map.put("order_num", order_num);
		int cnt = bDao.refundBookCountAdd(map);

		// 추가 완료되면 관리자 주문 상태 수정
		if (cnt != 0) {
			map = new HashMap<>();
			map.put("state", 9);
			map.put("num", order_num);
			cnt = hDao.orderStateUpdate(map);
		}

		model.addAttribute("cnt", cnt);
	}

	// 환불 거부
	@Override
	public void refundNo(HttpServletRequest req, Model model) {
		String order_num = req.getParameter("order_num");

		Map<String, Object> map = new HashMap<>();
		map.put("state", 7);
		map.put("num", order_num);
		int cnt = hDao.orderStateUpdate(map);

		model.addAttribute("cnt", cnt);
	}

	// 고객 센터
	@Override
	public void helpView(HttpServletRequest req, Model model) {
		String googleApiUrl = "https://maps.googleapis.com/maps/api/staticmap?";
		String ne = "37.478928,126.878965"; // 위도 경도

		// String center = "center=" + ne; //marker 없는 경우 필수
		// String zoom = "&zoom=" + "17"; //marker 없는 경우 필수. 20:건물, 15:도로, 10:구/군/시,
		// 5:대륙, 1:세계
		String size = "&size=" + "1200x800";
		String marker = "&markers=" + "color:blue" + "%7C" + "label:C" + "%7C" + ne; // %7C = 하이픈(|) : 마커 설정 구분자. URL
																						// 인코딩
		String key = "&key=" + "AIzaSyD-iJfYjArAMU7227--yo5AELs54bgFiC4"; // Google Static Maps API Key

		// String url = googleApiUrl + center + zoom + size + key;
		String url = googleApiUrl + marker + size + key;

		model.addAttribute("url", url);
	}

	// 결산
	@Override
	public void getResultTotal(HttpServletRequest req, Model model) {
		String chart = req.getParameter("chart");

		int total = bDao.getSalesTotalCount(); // 도서 판매량
		int totalSale = bDao.getSalesTotalAmount(); // 매출액

		model.addAttribute("total", total);
		model.addAttribute("totalSale", totalSale);
		model.addAttribute("chart", chart);

		/*
		 * //태그별 판매량 String[] tagName = {"newS", "bestS", "goodS", "nullS"}; Map<String,
		 * Integer> tagMap = dao.getTagSalesTotal(); for(String name : tagName) { //판매량이
		 * 없는 태그는 0 삽입 boolean flg = false; for(Entry<String, Integer> e :
		 * tagMap.entrySet()) { if(name.equals(e.getKey())) flg = true; } if(!flg)
		 * tagMap.put(name, 0); } model.addAttribute("tagMap", tagMap);
		 * 
		 * //태그별 환불량 tagName = new String[] {"newR", "bestR", "goodR", "nullR"};
		 * Map<String, Integer> tagRefund = dao.getTagRefundTotal(); for(String name :
		 * tagName) { //환불량이 없는 태그는 0 삽입 boolean flg = false; for(Entry<String, Integer>
		 * e : tagRefund.entrySet()) { if(name.equals(e.getKey())) flg = true; }
		 * if(!flg) tagRefund.put(name, 0); } model.addAttribute("tagRefund",
		 * tagRefund);
		 */

		// 현재 년도, 월
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH));
		model.addAttribute("year", year);
		model.addAttribute("month", month);

		// c:ser의 var을 숫자로만 입력하면 정상 작동 안함
		// 연간 매출액
		ArrayList<StringInt> tmp = bDao.getYearSalesTotalAmount();
		Map<String, Integer> yearAmount = new HashMap<>();
		for (int i = 0; i < tmp.size(); i += 1) {
			String yearStr = tmp.get(i).getStr();
			int count = tmp.get(i).getCount();

			if (yearStr.equals(year)) {
				model.addAttribute("currYearAmount", count); // 현재 년도의 매출액
			}
			model.addAttribute("y" + yearStr, count); // 연간 매출액
			yearAmount.put(yearStr, count);
		}
		model.addAttribute("yearAmount", yearAmount);

		// 연간 판매량
		ArrayList<StringInt> tmp1 = bDao.getYearSalesTotalCount();
		Map<String, Integer> yearCount = new HashMap<>();
		for (int i = 0; i < tmp1.size(); i += 1) {
			String yearStr = tmp1.get(i).getStr();
			int count = tmp1.get(i).getCount();

			if (yearStr.equals(year)) {
				model.addAttribute("currYearCount", count); // 현재 년도의 판매량
			}
			model.addAttribute("c" + yearStr, count); // 연간 판매량
			yearCount.put(yearStr, count);
		}
		model.addAttribute("yearCount", yearCount);

		// 연간 환불액
		ArrayList<StringInt> yearRefund = bDao.getYearRefundTotalAmount();
		for (int i = 0; i < yearRefund.size(); i += 1) {
			model.addAttribute("yr" + yearRefund.get(i).getStr(), yearRefund.get(i).getCount());
		}

		// 분야 개수
		int tagCount = bDao.getTag_mainCount();
		// 주간 태그별 판매량
		Map<String, Integer> tagWeeklyCount = bDao.getTagWeeklyCountDistinct();
		for (int i = 1; i <= 4; i += 1) { // 주간
			for (int j = 1; j <= tagCount; j += 1) { // 분야수
				boolean flg = false; // 매출 여부 확인
				String str = i + "" + j;

				for (Entry<String, Integer> e : tagWeeklyCount.entrySet()) {
					if (e.getKey().equals(str)) {
						model.addAttribute("t" + e.getKey(), e.getValue());
						flg = true;
						break;
						// log.debug("t" + e.getKey() + " : MAP");
					}
				}

				if (!flg) { // 매출 없는 분야
					model.addAttribute("t" + str, 0);
					// log.debug("t" + str);
				}
			}
		}
	}

	// 공지사항
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

	// 공지사항 글쓰기 처리
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
			// 글 수정 처
		} else {
			notice.setIdx(idx);
			cnt = hDao.noticeUpdate(notice);
		}
		model.addAttribute("cnt", cnt);
	}

	// 공지사항 상세보기
	@Override
	public void noticeView(HttpServletRequest req, Model model) {
		String idx = req.getParameter("idx");

		Notice notice = hDao.noticeView(idx);

		model.addAttribute("pageNum", req.getParameter("pageNum"));
		model.addAttribute("notice", notice);
	}

	// 공지사항 글 수정
	@Override
	public void noticeUpdate(HttpServletRequest req, Model model) {
		String idx = req.getParameter("idx");
		if (idx != null) {
			Notice notice = hDao.noticeView(idx);
			model.addAttribute("notice", notice);
		}
	}

	// 공지사항 댓글 추가
	@Override
	public void noticeCommentPro(HttpServletRequest req, Model model) {
		String comment = req.getParameter("comment");
		String id = (String) req.getSession().getAttribute("memId");
		
		// 댓글 추가
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