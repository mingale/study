package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import spring.mvc.bookstore.controller.EmailHandler;
import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Member;
import spring.mvc.bookstore.vo.Notice;
import spring.mvc.bookstore.vo.NoticeComment;

@Repository
public class HostPersImpl implements HostPers {
	@Autowired
	SqlSession sqlSession;
	
	// 아이디 중복 확인
	@Override
	public int confirmId(String id) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.confirmId(id);
		return tmp;
	}

	// 이메일 인증key 존재 확인-2) 해당 계정의 권한 등급 UP
	@Override
	public int emailKeyRatingUp(String e_key) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.emailKeyRatingUp(e_key);
		return tmp;
	}

	// 이메일 인증key 존재 확인-1
	@Override
	public int confirmEmailKey(String e_key) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int cnt = dao.confirmEmailKey(e_key);
		if (cnt > 0) {
			emailKeyRatingUp(e_key);
		}
		return cnt;
	}

	// 로그인
	@Override
	public String signIn(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		String rating = dao.signIn(map);
		return rating;
	}

	// 회원가입
	@Override
	public int signUp(Member m) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.signUp(m);
		return tmp;
	}

	// 이메일 존재 여부
	@Override
	public String confirmEmail(String email) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		String tmp = dao.confirmEmail(email);
		return tmp;
	}

	// 인증키 수정
	@Override
	public int memberEmailKeyUpdate(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.memberEmailKeyUpdate(map);
		return tmp;
	}

	// 메일 인증
	/*
	 * gmail 이용 전용 설정 gmail > 환경 설정 > 전달 및 pop/IMAP > IMAP 액세스 > IMAP 사용으로 변경 내 계정 >
	 * 로그인 및 보안 > 연결된 앱 및 사이트 > 보안 수준이 낮은 앱 허용 : 사용으로 변경(2단계 보안 사용 중이면 설정 못함)
	 */
	@Autowired
	JavaMailSender mailSender;

	@Override
	public int sendGmail(String toEmail, String key, int view) {
		int cnt = 0;

		try {
			EmailHandler sendMail = new EmailHandler(mailSender);
			if (view == 0) {
				sendMail.setSubject("BMS 회원가입 인증 메일");
				sendMail.setText(new StringBuffer().append("BMS 회원가입 인증 메일입니다. 인증을 눌러 회원가입을 완료하세요.<br>")
						.append("<a href='http://localhost:8081/bookstore/emailCheck?key=").append(key)
						.append("&view=" + view + "'>인증</a>").toString());
			} else if (view == 1) {
				sendMail.setSubject("BMS 아이디 찾기 인증 메일");
				sendMail.setText(new StringBuffer().append("BMS 아이디 찾기 인증 메일입니다. 인증을 눌러 아이디를 찾으세요.<br>")
						.append("<a href='http://localhost:8081/bookstore/emailCheck?key=").append(key)
						.append("&view=" + view + "'>인증</a>").toString());
			} else if (view == 2) {
				sendMail.setSubject("BMS 비밀번호 찾기 인증 메일");
				sendMail.setText(new StringBuffer().append("BMS 비밀번호 찾기 인증 메일입니다. 인증을 눌러 비밀번호를 찾으세요.<br>")
						.append("<a href='http://localhost:8081/bookstore/emailCheck?key=").append(key)
						.append("&view=" + view + "'>인증</a>").toString());
			}
			sendMail.setFrom("admin@bookstore.com", "관리자");
			sendMail.setTo(toEmail);
			System.out.println("Email 전송");
			sendMail.send();
			System.out.println("Email 전송 완료");
			cnt = 1;
		} catch (Exception e) { //throws MessagingException, UnsupportedEncodingException
			e.printStackTrace();
		}
		return cnt;
	}

	// 인증시 아이디 가져오기
	@Override
	public String getId(String key) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		String tmp = dao.getId(key);
		return tmp;
	}

	// 인증시 비밀번호 가져오기
	@Override
	public String getPwd(String key) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		String tmp = dao.getPwd(key);
		return tmp;
	}


	// 송장 번호
	@Override
	public String getShipping(String order_num) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		String tmp = dao.getShipping(order_num);
		return tmp;
	}

	// 관리자 주문 내역-1
	@Override
	public ArrayList<Bespeak> getHostOrderList() {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		ArrayList<Bespeak> tmp = dao.getHostOrderList();
		return tmp;
	}

	// 관리자 주문 내역-2
	@Override
	public ArrayList<Bespeak> getHostOrderDistinctList(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		ArrayList<Bespeak> list = new ArrayList<>();

		// 주문 목록 - 첫번째 도서만 도서 번호 표기
		// ** rownum에 별칭을 주지 않으면 다음 페이지 쿼리문이 제대로 돌아가지 않는다.

		ArrayList<Bespeak> numList = dao.getHostOrderDistinctList(map);
		ArrayList<Bespeak> allList = getHostOrderList();

		int index = 0; // 게시판 list index
		// numList와 같은 주문 번호만 담기. 중복 주문번호(여러 종류 주문) 처리
		for (int i = 0; i < numList.size(); i += 1) { // 중복 없는 주문 번호 List
			int count = 0; // 중복 수

			for (int j = 0; j < allList.size(); j += 1) { // 모둔 주문 list
				String num = allList.get(j).getOrder_num();

				// numlist의 현재 주문번호와 allList의 주문번호가 같으면
				if (numList.get(i).getOrder_num().equals(num)) {
					// 주문 번호가 중복되면
					if (count > 0) {
						list.get(index - 1).getBook().setNo(" 외 " + count + "권"); // 중복이므로 외권 표시
					}
					count += 1;

					// 주문번호가 같으면(중복X) - 게시판 list 추가
					if (count == 1) {
						allList.get(j).getBook().setNo("");
						list.add(allList.get(j));
						index += 1;
					}
				} else {
					count = 0;
				}
			}
		}
		return list;
	}

	// 주문 내역 총 개수
	@Override
	public int getHostOrderCnt() {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.getHostOrderCnt();
		return tmp;
	}

	// 관리자 주문 상태 수정
	@Override
	public int orderStateUpdate(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.orderStateUpdate(map);
		return tmp;
	}

	// 배송 시작
	@Override
	public int shippingInsert(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.shippingInsert(map);
		return tmp;
	}

	// 관리자 환불 내역 총 개수
	@Override
	public int getRefundCnt() {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int tmp = dao.getRefundCnt();
		return tmp;
	}

	// 관리자 환불내역-1) 모든 주문 내역
	@Override
	public ArrayList<Bespeak> getHostRefundList() {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		ArrayList<Bespeak> tmp = dao.getHostRefundList();
		return tmp;
	}

	// 관리자 환불 내역-2
	@Override
	public ArrayList<Bespeak> getHostRefundDistinctList(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		ArrayList<Bespeak> list = new ArrayList<>();

		ArrayList<Bespeak> numList = dao.getHostRefundDistinctList(map);
		ArrayList<Bespeak> allList = getHostRefundList();

		int index = 0; // 게시판 list index
		// numList와 같은 주문 번호만 담기. 중복 주문번호(여러 종류 주문) 처리
		for (int i = 0; i < numList.size(); i += 1) { // 중복 없는 주문 번호 List
			int count = 0; // 중복 수

			for (int j = 0; j < allList.size(); j += 1) { // 모둔 주문 list
				String num = allList.get(j).getOrder_num();

				// numlist의 현재 주문번호와 allList의 주문번호가 같으면
				if (numList.get(i).getOrder_num().equals(num)) {
					// 주문 번호가 중복되면
					if (count > 0) {
						list.get(index - 1).getBook().setNo(" 외 " + count + "권"); // 중복이므로 외권 표시
					}
					count += 1;

					// 주문번호가 같으면(중복X) - 게시판 list 추가
					if (count == 1) {
						allList.get(j).getBook().setNo("");
						list.add(allList.get(j));
						index += 1;
					}
				} else {
					count = 0;
				}
			}
		}
		return list;
	}

	// 회원 목록
	@Override
	public ArrayList<Member> getMemberList(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		ArrayList<Member> list = dao.getMemberList(map);
		return list;
	}

	// 회원수
	@Override
	public int getMemberCnt() {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int cnt = dao.getMemberCnt();
		return cnt;
	}

	// 관리자 회원 수정
	@Override
	public int setHostMemberUpdate(Map<String, Object> map) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int cnt = dao.setHostMemberUpdate(map);
		return cnt;
	}

	/*
	 * ERROR - ORA-02292: integrity constraint (BMS.SYS_C007739) violated - child
	 * record found FK에 ON DELETE CASCADE 필수!
	 */

	// 주문 삭제
	@Override
	public int orderDelete(String order_num) {
		HostPers dao = sqlSession.getMapper(HostPers.class);
		int cnt = dao.orderDelete(order_num);
		return cnt;
	}

	@Override
	public int getNoticeCnt() {
		int cnt = 0;
		
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		cnt = mapper.getNoticeCnt();
		return cnt;
	}
	
	// 공지사항
	@Override
	public ArrayList<Notice> getNotice(Map<String, Object> map) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		ArrayList<Notice> list = mapper.getNotice(map);
		return list;
	}
	

	//공지사항 글쓰기 처리
	@Override
	public int noticeWritePro(Notice notice) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		int cnt = mapper.noticeWritePro(notice);
		return cnt;
	}

	//공지사항 상세 보기 + 댓글
	@Override
	public Notice noticeAndCommentView(String idx) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		Notice notice = mapper.noticeAndCommentView(idx);
		return notice;
	}
	
	//공지사항 상세보기
	@Override
	public Notice noticeView(String idx) {
		
		//댓글 없으면 본글만 가져오기
		Notice notice = noticeAndCommentView(idx);
		if(notice == null) {
			HostPers mapper = sqlSession.getMapper(HostPers.class);
			notice = mapper.noticeView(idx);
		}
		return notice;
	}

	///공지사항 글 수정
	@Override
	public int noticeUpdate(Notice notice) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		int cnt = mapper.noticeUpdate(notice);
		return cnt;
	}

	//공지사항 댓글 추가
	public int noticeCommentAdd(NoticeComment nco) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		int cnt = mapper.noticeCommentAdd(nco);
		return cnt;
	}

	//공지사항 해당댓글
	public ArrayList<NoticeComment> noticeCommentList(String notice_idx) {
		HostPers mapper = sqlSession.getMapper(HostPers.class);
		ArrayList<NoticeComment> list = mapper.noticeCommentList(notice_idx);
		return list;
	}
	
}
