package spring.mvc.bookstore.persistence;

import java.util.ArrayList;
import java.util.Map;

import spring.mvc.bookstore.vo.Bespeak;
import spring.mvc.bookstore.vo.Member;
import spring.mvc.bookstore.vo.Notice;
import spring.mvc.bookstore.vo.NoticeComment;

public interface HostPers {

	// 아이디 중복 확인
	public int confirmId(String id);

	// 이메일 인증key 존재 확인-1
	public int confirmEmailKey(String e_key);

	// 이메일 인증key 존재 확인-2
	public int emailKeyRatingUp(String e_key);

	// 로그인
	public String signIn(Map<String, Object> map);

	// 회원가입
	public int signUp(Member m);

	// id 찾기 전단계 - 이메일 존재 여부
	public String confirmEmail(String email);

	// 비밀번호 찾기 이메일 전송
	public int memberEmailKeyUpdate(Map<String, Object> map);

	// 메일 인증
	public int sendGmail(String toEmail, String key, int view);

	// 인증시 아이디 가져오기
	public String getId(String key);

	// 인증시 비밀번호 가져오기
	public String getPwd(String key);

	// 송장번호
	public String getShipping(String order_num);

	// 관리자 주문 내역-1) 모든 내역
	public ArrayList<Bespeak> getHostOrderList();

	// 관리자 주문 내역-2) 중복 없는 내역
	public ArrayList<Bespeak> getHostOrderDistinctList(Map<String, Object> map);

	// 주문 내역 총 개수
	public int getHostOrderCnt();

	// 주문 상태 수정 (관리, 회원 환불 신청)
	public int orderStateUpdate(Map<String, Object> map);

	// 관리자 배송 시작
	public int shippingInsert(Map<String, Object> map);

	// 관리자 환불내역 총 개수
	public int getRefundCnt();

	// 관리자 환불내역-1) 모든 주문 내역
	public ArrayList<Bespeak> getHostRefundList();

	// 관리자 환불내역-2) 중복 제거 주문 내역
	public ArrayList<Bespeak> getHostRefundDistinctList(Map<String, Object> map);

	// 회원 목록
	public ArrayList<Member> getMemberList(Map<String, Object> map);

	// 회원수
	public int getMemberCnt();

	// 관리자 회원 수정
	public int setHostMemberUpdate(Map<String, Object> map);

	// 주문 삭제
	public int orderDelete(String order_num);

	//공지사항 게시글 총수
	public int getNoticeCnt(); 
	
	//공지사항
	public ArrayList<Notice> getNotice(Map<String, Object> map);
	
	//공지사항 글쓰기 처리
	public int noticeWritePro(Notice notice);

	//공지사항 상세보기 + 댓글
	public Notice noticeAndCommentView(String idx);
	
	//공지사항 상세보기
	public Notice noticeView(String idx);
	
	///공지사항 글 수정
	public int noticeUpdate(Notice notice);
	
	//공지사항 댓글 추가
	public int noticeCommentAdd(NoticeComment nco);
	
	//공지사항 해당댓글
	public ArrayList<NoticeComment> noticeCommentList(String notice_idx);
}
