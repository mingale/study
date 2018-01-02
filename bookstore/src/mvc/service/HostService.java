package mvc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HostService {
	
		//아이디 중복 검사
		public void confirmId(HttpServletRequest req, HttpServletResponse res);
		
		//이메일 중복 검사
		public void confirmEmail(HttpServletRequest req, HttpServletResponse res);
		
		//이메일 인증 key 확인
		public void confirmEmailKey(HttpServletRequest req, HttpServletResponse res);
		
		//로그인
		public void signIn(HttpServletRequest req, HttpServletResponse res);
		
		//로그아웃
		public void signUp(HttpServletRequest req, HttpServletResponse res);

		//아이디/비밀번호 찾기
		public void memberFindEmailKey(HttpServletRequest req, HttpServletResponse res);
		
		//관리자 메인
		public void mainView(HttpServletRequest req, HttpServletResponse res);
		
		//회원 정보 찾기
		public void memberQuery(HttpServletRequest req, HttpServletResponse res);
		
		//회원 정보 수정
		public void memberUpdate(HttpServletRequest req, HttpServletResponse res);
		
		//회원 탈퇴 / 삭제
		public void memberDelete(HttpServletRequest req, HttpServletResponse res);

		//관리자 주문 내역
		public void hostOrderView(HttpServletRequest req, HttpServletResponse res);
		
		//관리자 주문 상태 수정
		public void orderStateUpdate(HttpServletRequest req, HttpServletResponse res);
		
		//관리자 주문 배송 시작
		public void shippingPro(HttpServletRequest req, HttpServletResponse res);

		//관리자 환불 내역
		public void getHostRefundView(HttpServletRequest req, HttpServletResponse res);
		
		//관리자 회원 목록
		public void getMemberView(HttpServletRequest req, HttpServletResponse res);

		//관리자 회원 수정
		public void setHostMemberUpdate(HttpServletRequest req, HttpServletResponse res);
		
		//관리자 주문 삭제
		public void orderDelete(HttpServletRequest req, HttpServletResponse res);

		//환불 완료
		public void refundOk(HttpServletRequest req, HttpServletResponse res);
		
		//환불 거부
		public void refundNo(HttpServletRequest req, HttpServletResponse res);
		
		//고객센터
		public void helpView(HttpServletRequest req, HttpServletResponse res);
		
		//결산
		public void getResultTotal(HttpServletRequest req, HttpServletResponse res);
}
