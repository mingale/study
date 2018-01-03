package spring.mvc.bookstore.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface HostService {
	
		//아이디 중복 검사
		public void confirmId(HttpServletRequest req, Model model);
		
		//이메일 중복 검사
		public void confirmEmail(HttpServletRequest req, Model model);
		
		//이메일 인증 key 확인
		public void confirmEmailKey(HttpServletRequest req, Model model);
		
		//로그인
		public void signIn(HttpServletRequest req, Model model);
		
		//로그아웃
		public void signUp(HttpServletRequest req, Model model);

		//아이디/비밀번호 찾기
		public void memberFindEmailKey(HttpServletRequest req, Model model);
		
		//관리자 메인
		public void mainView(HttpServletRequest req, Model model);
		
		//회원 정보 찾기
		public void memberQuery(HttpServletRequest req, Model model);
		
		//회원 정보 수정
		public void memberUpdate(HttpServletRequest req, Model model);
		
		//회원 탈퇴 / 삭제
		public void memberDelete(HttpServletRequest req, Model model);

		//관리자 주문 내역
		public void hostOrderView(HttpServletRequest req, Model model);
		
		//관리자 주문 상태 수정
		public void orderStateUpdate(HttpServletRequest req, Model model);
		
		//관리자 주문 배송 시작
		public void shippingPro(HttpServletRequest req, Model model);

		//관리자 환불 내역
		public void getHostRefundView(HttpServletRequest req, Model model);
		
		//관리자 회원 목록
		public void getMemberView(HttpServletRequest req, Model model);

		//관리자 회원 수정
		public void setHostMemberUpdate(HttpServletRequest req, Model model);
		
		//관리자 주문 삭제
		public void orderDelete(HttpServletRequest req, Model model);

		//환불 완료
		public void refundOk(HttpServletRequest req, Model model);
		
		//환불 거부
		public void refundNo(HttpServletRequest req, Model model);
		
		//고객센터
		public void helpView(HttpServletRequest req, Model model);
		
		//결산
		public void getResultTotal(HttpServletRequest req, Model model);
		
		//공지사항
		public void getNotice(HttpServletRequest req, Model model);
		
		//공지사항 글쓰기 처리
		public void noticeWritePro(HttpServletRequest req, Model model);
		
		//공지사항 상세보기
		public void noticeView(HttpServletRequest req, Model model);
}
